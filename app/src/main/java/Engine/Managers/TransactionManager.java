package Engine.Managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.Classes.*;
import Engine.Events.*;
import Engine.Transactions.*;
//import flash.events.*;
//import flash.utils.*;
import Transactions.*;

import com.xinghai.Debug;

    public class TransactionManager extends EventDispatcher
    {
        public static  int DEFAULT_MAX_QUEUED =30;
        public static  int MIN_AMF_MAX_WAIT =1000;
        public static  int MAX_AMF_MAX_WAIT =5000;
        private static int m_maxQueued =30;
        private static int m_amfMaxPerBatch =8;
        private static int m_amfMaxWait =5000;
        private static int m_amfMaxRetries =2;
        private static int m_amfRetryAlertLimit =1;
        private static AMFConnection m_connection ;
        private static Array m_transactions =new Array();
        private static Array m_batchedAmfCalls =new Array();
        private static int m_sequenceNumber =0;
        private static Timer m_amfBatchTimer =null ;
        private static Timer m_retryTimer =null ;
        private static Timer m_inactivityTimer =null ;
        private static double m_ageOfOldestCall =0;
        private static int m_numBatchCalls =0;
        private static int m_retrySequence =0;
        private static int m_retryCount =0;
        private static TransactionManager m_instance ;
        public static String m_lastFunc ="0";
        public static String m_lastError ="0";
        public static boolean m_inSync =true ;
        public static boolean m_queueLimitExceeded =false ;
        private static boolean m_initialized =false ;
        private static Object m_addtionalSignedParams ={};

        public  TransactionManager ()
        {
            return;
        }//end

        public static void  initialize ()
        {
            String _loc_2 =null ;
            Transaction _loc_3 =null ;
            _loc_1 = GlobalEngine.zyParams ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                m_addtionalSignedParams.put(_loc_2,  _loc_1.get(_loc_2));
            }
            m_inactivityTimer = new Timer(Config.TRANSACTION_INACTIVITY_SECONDS);
            m_inactivityTimer.addEventListener(TimerEvent.TIMER, inactivityTimerComplete);
            m_inactivityTimer.start();
            while (m_transactions.length > 0)
            {

                _loc_3 = m_transactions.shift();
                _loc_3.endZaspLogging();
            }
            m_batchedAmfCalls = new Array();
            m_sequenceNumber = 0;
            m_retrySequence = 0;
            m_retryCount = 0;
            m_lastFunc = "0";
            m_lastError = "0";
            m_inSync = true;
            m_queueLimitExceeded = false;
            m_initialized = false;
            return;
        }//end

        public static TransactionManager  getInstance ()
        {
            if (m_instance == null)
            {
                m_instance = new TransactionManager;
            }
            return m_instance;
        }//end

        public static String  lastError ()
        {
            return m_lastError;
        }//end

        public static String  lastFunc ()
        {
            return m_lastFunc;
        }//end

        public static boolean  inSync ()
        {
            return m_inSync;
        }//end

        public static void  inSync (boolean param1 )
        {
            m_inSync = param1;
            if (param1 == false)
            {
                getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.OUT_OF_SYNC));
            }
            return;
        }//end

        public static void  additionalSignedParams (Object param1 )
        {
            m_addtionalSignedParams = param1;
            return;
        }//end

        public static Object  additionalSignedParams ()
        {
            return m_addtionalSignedParams;
        }//end

        public static void  addTransaction (Transaction param1 ,boolean param2 =false ,boolean param3 =false )
        {
            if (param1.isInitTransaction() == true)
            {
                m_initialized = true;
            }
            if (!canSendTransactions() && Config.DEBUG_MODE)
            {
                trace("Error: transaction added before transactions have been initialized.  This will fail on the back end.");
            }
            if (m_amfBatchTimer == null)
            {
                m_amfBatchTimer = new Timer(1000);
                m_amfBatchTimer.addEventListener(TimerEvent.TIMER, onAmfBatchSend);
                m_amfBatchTimer.start();
            }
            if (inSync == true)
            {
                param1.preAddTransaction();
                if (m_transactions.length == 0)
                {
                    m_ageOfOldestCall = GlobalEngine.serverTime;
                }
                GlobalEngine.log("TransactionManager", "Adding transaction " + getQualifiedClassName(param1));
                m_transactions.push(param1);
                getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.ADDED, param1));
                param1.startZaspLogging(param2, param3);
                if (param2 == true)
                {
                    sendAllTransactions(false);
                }
                else if (m_transactions.length >= m_amfMaxPerBatch && m_batchedAmfCalls.length == 0)
                {
                    if (param3)
                    {
                        GlobalEngine.log("TransactionManager", "User blocking transaction queued without perform immediately " + getQualifiedClassName(param1));
                    }
                    sendAllTransactions(true);
                }
                if (m_transactions.length >= m_maxQueued && m_queueLimitExceeded == false)
                {
                    getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.QUEUE_LIMIT_EXCEEDED));
                    m_queueLimitExceeded = true;
                }
                param1.postAddTransaction();
            }
            else
            {
                GlobalEngine.log("TransactionManager", "Suppressing transaction because we\'re oos");
            }
            return;
        }//end

        private static int  incrementSequence ()
        {
            _loc_2 = m_sequenceNumber+1;
            m_sequenceNumber = _loc_2;
            return m_sequenceNumber;
        }//end

        private static Object  generateSignedParams ()
        {
            _loc_2 = null;
            Object _loc_3 =null ;
            Object _loc_1 =new Object ();
            for(int i0 = 0; i0 < m_addtionalSignedParams.size(); i0++)
            {
            	_loc_2 = m_addtionalSignedParams.get(i0);

                _loc_1.put(_loc_2,  m_addtionalSignedParams.get(_loc_2));
            }
            _loc_3 = GlobalEngine.getFlashVar("flashRevision");
            _loc_1.flashRevision = null;
            if (_loc_3 != null)
            {
                _loc_1.flashRevision = parseInt(_loc_3.toString());
            }
            return _loc_1;
        }//end

        public static void  batchCall (Transaction param1 ,String param2 ,Array param3 ,Function param4 ,Function param5 =null )
        {
            Object _loc_6 =null ;
            Object _loc_7 =null ;
            String _loc_8 =null ;
            if (inSync)
            {
                _loc_6 = {};
                _loc_6.put("enqueueTime",  param1.enqueueTime / 1000);
                _loc_6.put("stamp",  GlobalEngine.serverTime);
                _loc_6.put("functionName",  param2);
                _loc_6.put("params",  param3);
                _loc_6.put("onSuccess",  param4);
                _loc_6.put("onFailure",  param5);
                _loc_6.put("transaction",  param1);
                _loc_6.put("sequence",  TransactionManager.incrementSequence());
                _loc_6.put("quests",  param1.questList);
                _loc_7 = param1.getBatchCallParams();
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                	_loc_8 = _loc_7.get(i0);

                    _loc_6.put(_loc_8,  _loc_7.get(_loc_8));
                }
                m_batchedAmfCalls.push(_loc_6);
            }
            else
            {
                ErrorManager.addError("Unable to batch call because client instanceof out-of-sync! (function: " + param2 + ")");
            }
            return;
        }//end

        private static void  onAmfBatchSend (Event event =null ,boolean param2 =false ,boolean param3 =false )
        {
            double _loc_4 =0;
            Object _loc_5 =null ;
            Array _loc_6 =null ;
            int _loc_7 =0;
            TransactionBatchEvent _loc_8 =null ;
            Transaction _loc_9 =null ;
            Object _loc_10 =null ;

            if (m_batchedAmfCalls.length > 0) {
                    _loc_7 = 0;
                    while (_loc_7 < m_batchedAmfCalls.length())
                    {

                        _loc_10 = m_batchedAmfCalls.get(_loc_7);
            	        String _funname =_loc_10.functionName ;
            	        Debug.debug7("onAmfBatchSend." + _funname);

            	        Debug.debug7("onAmfBatchSend  ." + m_batchedAmfCalls.length());
                        _loc_7++;
                    }

            }

            if (m_batchedAmfCalls.length == 0 && m_transactions.length > 0 || param3)
            {
                _loc_4 = GlobalEngine.serverTime - m_ageOfOldestCall;
                if (m_ageOfOldestCall == 0 || _loc_4 >= m_amfMaxWait || param2 == true)
                {
                    m_amfBatchTimer.stop();
                    while (m_transactions.length > 0 && m_batchedAmfCalls.length < m_amfMaxPerBatch)
                    {

                        _loc_9 = m_transactions.shift();
                        if (_loc_9.canPerform)
                        {
                            _loc_9.perform();
                            getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.DISPATCHED, _loc_9));
                        }
                    }
                    GlobalEngine.log("AMFBatch", "Executing " + m_batchedAmfCalls.length + " in a batch");
                    _loc_5 = TransactionManager.generateSignedParams();
                    _loc_6 = new Array();
                    _loc_7 = 0;
                    while (_loc_7 < m_batchedAmfCalls.length())
                    {

                        _loc_10 = m_batchedAmfCalls.get(_loc_7);
                        _loc_6.push(_loc_10);
                        _loc_7++;
                    }
                    if (m_connection == null)
                    {
                        m_connection = new AMFConnection(onAmfBatchComplete, onAmfBatchFault);
                    }


                    m_connection.call.apply(m_connection, [GlobalEngine.engineOptions.baseService, _loc_5, _loc_6, m_retryCount]);
                    _loc_8 = new TransactionBatchEvent(TransactionBatchEvent.BATCH_DISPATCHED);
                    _loc_8.dispatchedBatchData = _loc_6;
                    getInstance().dispatchEvent(_loc_8);
                    m_ageOfOldestCall = GlobalEngine.serverTime;
                    m_amfBatchTimer.start();
                }
            }
            return;
        }//end

        private static void  onAmfBatchComplete (Object param1 )
        {
            Array _loc_7 =null ;
            int _loc_8 =0;
            Object _loc_9 =null ;
            Function _loc_10 =null ;
            boolean _loc_2 =false ;
            _loc_3 = Transaction.NO_ERROR;
            String _loc_4 ="batch";
            String _loc_5 ="";



            TransactionBatchEvent _loc_6 =new TransactionBatchEvent(TransactionBatchEvent.BATCH_COMPLETE );
            _loc_6.responseBatchData = param1;
            getInstance().dispatchEvent(_loc_6);
            if (param1 != null && param1.errorType == Transaction.NO_ERROR)
            {
                m_inactivityTimer.stop();
                m_inactivityTimer.reset();
                m_inactivityTimer.start();
                _loc_7 =(Array) param1.data;
                if (_loc_7 != null && _loc_7.length == m_batchedAmfCalls.length())
                {
                    if (param1.zySig)
                    {
                        GlobalEngine.zyParams = param1.zySig;
                    }
                    _loc_8 = 0;
                    while (_loc_8 < _loc_7.length())
                    {

                        param1 = _loc_7.get(_loc_8);
                        _loc_9 = {};
                        if (param1.errorType == Transaction.RETRY_TRANSACTION)
                        {
                            _loc_3 = param1.errorType;
                            _loc_5 = param1.errorData;
                            _loc_9 = m_batchedAmfCalls.get(0);
                            _loc_4 = _loc_9.get("functionName");
                            _loc_2 = true;
                            break;
                        }
                        else if (param1.errorType == Transaction.FORCE_RELOAD)
                        {
                            _loc_3 = param1.errorType;
                            _loc_5 = param1.errorData;
                            m_retryCount = m_amfMaxRetries + 1;
                            _loc_2 = true;
                        }
                        else
                        {
                            _loc_9 = m_batchedAmfCalls.shift();

                            _loc_10 = _loc_9.get("onSuccess");
                            _loc_10.apply(_loc_9.get("transaction"), [param1]);
                            if (m_numBatchCalls > 0)
                            {
                                _loc_12 = m_numBatchCalls-1;
                                m_numBatchCalls = _loc_12;
                            }
                            getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.COMPLETED, _loc_9.get("transaction") as Transaction));
                        }
                        _loc_8++;
                    }
                    if (m_numBatchCalls > 0)
                    {
                        onAmfBatchSend(null, true);
                    }
                }
                else
                {
                    GlobalEngine.log("AMFBatch", "ERROR - batch results instanceof not an array of length " + m_batchedAmfCalls.length());
                }
            }
            else if (param1 != null)
            {
                _loc_3 = param1.errorType;
                _loc_5 = param1.errorData;
                if (_loc_3 == Transaction.AUTH_NO_USER_ID || _loc_3 == Transaction.AUTH_NO_SESSION)
                {
                    inSync = false;
                }
                else if (_loc_3 == Transaction.OUTDATED_GAME_VERSION)
                {
                    inSync = false;
                    getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.VERSION_MISMATCH));
                }
                else
                {
                    _loc_2 = true;
                }
            }
            else
            {
                _loc_3 = Transaction.RETRY_TRANSACTION;
                _loc_2 = true;
            }
            if (_loc_2 == true)
            {
                m_lastError = _loc_3.toString();
                m_lastFunc = _loc_4;
                onAmfBatchFault(null, _loc_3, _loc_5);
            }
            else
            {
                if (m_retryCount > 0)
                {
                    getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.RETRY_SUCCESS));
                }
                m_retrySequence = 0;
                m_retryCount = 0;
            }
            if (m_transactions.length < m_maxQueued && m_queueLimitExceeded)
            {
                getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.QUEUE_LIMIT_NORMAL));
                m_queueLimitExceeded = false;
            }


            return;
        }//end

        private static void  onAmfBatchFault (Object param1 ,int param2 ,String param3 )
        {
            Object _loc_4 =null ;
            Transaction _loc_5 =null ;
            Function _loc_6 =null ;
            boolean _loc_7 =false ;



            if (m_batchedAmfCalls.length())
            {
                m_retrySequence = m_batchedAmfCalls.get(0).sequence;
                _loc_9 = m_retryCount+1;
                m_retryCount = _loc_9;
                if (m_retryCount <= m_amfMaxRetries)
                {
                    onRetryPause();
                    if (m_retryCount >= m_amfRetryAlertLimit)
                    {
                        getInstance().dispatchEvent(new TransactionFaultEvent(TransactionFaultEvent.RETRY, null, param2, param3));
                    }
                }
                else
                {
                    _loc_4 = m_batchedAmfCalls.shift();
                    _loc_5 =(Transaction) _loc_4.get("transaction");
                    if (_loc_5)
                    {
                        _loc_7 = _loc_5.isFaultable();
                        if (_loc_7)
                        {
                            getInstance().dispatchEvent(new TransactionFaultEvent(TransactionFaultEvent.FAULT, _loc_4.get("transaction"), param2, param3));
                            inSync = false;
                        }
                    }
                    _loc_6 = _loc_4.get("onFailure");
                    _loc_6.apply(_loc_4.get("transaction"), [param1, param2, param3]);
                }
            }
            GlobalEngine.log("AMFBatch", "Batch exec FAIL result = " + param1);
            return;
        }//end

        public static void  sendAllTransactions (boolean param1 )
        {
            if (param1 !=null)
            {
                m_numBatchCalls = m_transactions.length;
            }
            onAmfBatchSend(null, true);
            return;
        }//end

        private static void  onRetryPause ()
        {
            _loc_1 = Math.pow(2,m_retryCount )*1000;
            if (Config.DEBUG_MODE == true)
            {
                _loc_1 = 1;
            }
            if (m_retryTimer == null)
            {
                m_retryTimer = new Timer(_loc_1, 1);
                m_retryTimer.addEventListener(TimerEvent.TIMER_COMPLETE, onRetrySend);
                m_retryTimer.start();
                GlobalEngine.log("AMFBatch", "Waiting " + _loc_1 + "ms before retry");
            }
            return;
        }//end

        private static void  onRetrySend (TimerEvent event )
        {
            if (m_retryTimer != null)
            {
                m_retryTimer.stop();
                m_retryTimer = null;
            }
            GlobalEngine.log("AMFBatch", "Retrying transaction #" + m_retrySequence + ". " + m_retryCount + " time(s)");
            onAmfBatchSend(null, true, true);
            return;
        }//end

        public static boolean  canSendTransactions ()
        {
            return m_initialized;
        }//end

        public static void  disable ()
        {
            m_amfBatchTimer.stop();
            return;
        }//end

        public static void  enable ()
        {
            m_amfBatchTimer.start();
            return;
        }//end

        private static void  inactivityTimerComplete (TimerEvent event )
        {
            getInstance().dispatchEvent(new TransactionEvent(TransactionEvent.INACTIVE));
            return;
        }//end

        public static int  transactionQueueLength ()
        {
            return m_transactions.length;
        }//end

        public static int  maxQueued ()
        {
            return m_maxQueued;
        }//end

        public static int  amfMaxPerBatch ()
        {
            return m_amfMaxPerBatch;
        }//end

        public static int  amfMaxWait ()
        {
            return m_amfMaxWait;
        }//end

        public static int  amfMaxRetries ()
        {
            return m_amfMaxRetries;
        }//end

        public static int  amfRetryAlertLimit ()
        {
            return m_amfRetryAlertLimit;
        }//end

        public static void  maxQueued (int param1 )
        {
            m_maxQueued = param1;
            return;
        }//end

        public static void  amfMaxPerBatch (int param1 )
        {
            m_amfMaxPerBatch = param1;
            return;
        }//end

        public static void  amfMaxWait (int param1 )
        {
            m_amfMaxWait = Math.max(Math.min(param1, MAX_AMF_MAX_WAIT), MIN_AMF_MAX_WAIT);
            return;
        }//end

        public static void  amfMaxRetries (int param1 )
        {
            m_amfMaxRetries = param1;
            return;
        }//end

        public static void  amfRetryAlertLimit (int param1 )
        {
            m_amfRetryAlertLimit = param1;
            return;
        }//end

    }



