package Engine.Transactions;

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

import Engine.*;
import Engine.Managers.*;
//import flash.events.*;
//import flash.utils.*;

    public class Transaction extends EventDispatcher
    {
        protected  int ERROR_USER_DATA_MISSING =2;
        protected  int ERROR_INVALID_STATE =3;
        protected  int INVALID_DATA =4;
        protected  int MISSING_DATA =5;
        protected  int ACTION_CLASS_ERROR =6;
        protected  int ACTION_METHOD_ERROR =7;
        protected  int ERROR_RESOURCE_DATA_MISSING =8;
        protected  int NOT_ENOUGH_MONEY =9;
        protected  int TRANSPORT_FAILURE_GENERAL =25;
        protected int m_id ;
        private boolean m_canPerform =true ;
        protected String m_functionName ;
        protected int m_amfWaitHandle =0;
        protected Object m_rawResult =null ;
        protected double m_enqueueTime ;
        protected String m_questsAffected ="";
        public static  int NO_ERROR =0;
        public static  int ERROR_AUTH =1;
        public static  int OUTDATED_GAME_VERSION =10;
        public static  int AUTH_NO_USER_ID =26;
        public static  int AUTH_NO_SESSION =27;
        public static  int RETRY_TRANSACTION =28;
        public static  int FORCE_RELOAD =29;
        public static int m_transactionId =1;

        public  Transaction ()
        {
            super(this);
            this.m_id = Utilities.generateUniqueId();
            m_functionName = "transaction";
            return;
        }//end

        public double  getId ()
        {
            return this.m_id;
        }//end

        protected void  signedCall (String param1 ,...args )
        {
            this.m_functionName = param1;
            this.logEvent("Batching AMF call");
            TransactionManager.batchCall(this, param1, args, this.onAmfComplete, this.onAmfFault);
            return;
        }//end

        public void  startZaspLogging (boolean param1 ,boolean param2 )
        {
            if (param1 !=null)
            {
                if (param2)
                {
                    this.m_amfWaitHandle = GlobalEngine.zaspManager.zaspWaitStart("AMF-STP", getQualifiedClassName(this));
                }
                else
                {
                    this.m_amfWaitHandle = GlobalEngine.zaspManager.zaspWaitStart("AMF", getQualifiedClassName(this));
                }
            }
            return;
        }//end

        public void  endZaspLogging ()
        {
            if (this.m_amfWaitHandle != 0)
            {
                GlobalEngine.zaspManager.zaspWaitEnd(this.m_amfWaitHandle);
                this.m_amfWaitHandle = 0;
            }
            return;
        }//end

        public void  perform ()
        {
            return;
        }//end

        public void  canPerform (boolean param1 )
        {
            this.m_canPerform = param1;
            return;
        }//end

        public boolean  canPerform ()
        {
            return this.m_canPerform;
        }//end

        public Object  rawResult ()
        {
            return this.m_rawResult;
        }//end

        public double  enqueueTime ()
        {
            return this.m_enqueueTime;
        }//end

        public void  addQuestToHeader (String param1 )
        {
            if (this.m_questsAffected != "")
            {
                this.m_questsAffected = this.m_questsAffected + ",";
            }
            this.m_questsAffected = this.m_questsAffected + param1;
            return;
        }//end

        public String  questList ()
        {
            return this.m_questsAffected;
        }//end

        protected void  onAmfComplete (Object param1 )
        {
            this.m_rawResult = param1;
            this.endZaspLogging();
            if (param1 !=null)
            {
                if (param1.serverTime != null)
                {
                    GlobalEngine.setInternalServerTime(param1.serverTime);
                }
                if (param1.errorType == NO_ERROR)
                {
                    this.logEvent("Complete");
                    if (param1.data != null)
                    {
                        this.onComplete(param1.data);
                    }
                }
                else
                {
                    if (param1.errorData)
                    {
                        this.logEvent("Error " + param1.errorType + " " + param1.errorData);
                    }
                    else
                    {
                        this.logEvent("Error " + param1.errorType);
                    }
                    this.onFault(param1.errorType, param1.errorData);
                }
            }
            return;
        }//end

        protected void  logEvent (String param1 )
        {
            GlobalEngine.log("Transaction", this.getId() + " (" + this.m_functionName + "): " + param1);
            return;
        }//end

        final protected void  onAmfFault (Object param1 ,int param2 =25,String param3 ="onAmfFault was called")
        {
            String _loc_4 =null ;
            this.logEvent("Fault " + param1);
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_4 = param1.get(i0);

                GlobalEngine.log("Transaction", _loc_4 + " : " + param1.get(_loc_4));
            }
            this.endZaspLogging();
            this.onFault(param2, param3);
            return;
        }//end

        protected void  onComplete (Object param1 )
        {
            return;
        }//end

        public boolean  isFaultable ()
        {
            return true;
        }//end

        protected void  onFault (int param1 ,String param2 )
        {
            return;
        }//end

        public boolean  isInitTransaction ()
        {
            return false;
        }//end

        public Object  getBatchCallParams ()
        {
            return {};
        }//end

        public void  preAddTransaction ()
        {
            this.m_enqueueTime = GlobalEngine.serverTime;
            return;
        }//end

        public void  postAddTransaction ()
        {
            return;
        }//end

        public String  funName ()
        {
            return m_functionName;
        }//end

        public void  funName (String param1 )
        {
            m_functionName = param1;
        }//end


    }


