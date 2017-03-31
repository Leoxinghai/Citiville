package zasp.Classes;

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


//import flash.external.*;
//import flash.utils.*;
import zasp.Util.*;

    public class ZaspTrackerGPI
    {
        private int m_uiWaitHandle =0;
        private boolean m_userWait =false ;
        private Dictionary m_activeWFs ;
        private Dictionary m_activeWaits ;
        protected int m_numActiveWaits =0;
        protected int m_numActiveSTPWaits =0;
        protected int m_waitStartTime =0;
        protected int m_sTPWaitStartTime =0;
        protected int m_totalWaitTime =0;
        protected int m_sTPWaitTime =0;
        protected int m_playMsecs =0;
        protected int m_playInterval =10000;
        protected int m_numWFs =0;
        protected int m_numWaits =0;
        protected int m_numSTPs =0;
        protected int m_numLoads =0;
        protected int m_numDialogs =0;
        protected int m_numUnknownWaits =0;
        private Array m_queuedMessages ;
        private Array m_playMsecsBandDefinition ;
        private Vector<int> m_playMsecsBands;
        private Vector<int> m_playDefConMsecs;
        private int m_lastActivity =0;
        private int m_lastDefCon =0;
        private int m_sessionStartTime =0;
        private String m_sessionid ;
        private boolean m_failedInit =false ;
        private boolean m_trackAMFTotalWait =true ;
        protected Dictionary m_waitHandleDict ;
        private int m_numPopups =0;
        private int m_numClicks =0;
        private int m_loadTime =0;
        private boolean m_ignoreWait =false ;
        protected boolean m_active =false ;
        private int m_sessionInterval =0;
        private int m_lastSession =0;
        protected double m_f2f ;
        protected double m_mem ;
        protected double m_oef ;
        protected double m_f2f5s ;
        protected double m_oef5s ;
        protected double m_play_f2f_sum =0;
        protected double m_play_oef_sum =0;
        protected double m_play_samples =0;
        private boolean m_sendSessionMessage =true ;
        private boolean m_sendWaitMessage =true ;
        private boolean m_sendWFMessage =true ;
        private Object m_componentInitTimes =null ;
        private static int waitHandle =1;
        private static boolean m_logOnce =false ;

        public  ZaspTrackerGPI (String param1 ,int param2 =5000,boolean param3 =false )
        {
            if (ExternalInterface.available)
            {
                ExternalInterface.addCallback("waitStateChange", this.waitStateChange);
                ExternalInterface.addCallback("gameStateChange", this.zaspWorkFlowEvent);
                this.m_sessionStartTime = 0;
                this.m_waitHandleDict = new Dictionary();
                this.m_activeWFs = new Dictionary();
                this.m_activeWaits = new Dictionary();
                this.m_queuedMessages = new Array();
                this.m_playMsecsBandDefinition = new Array(21, 18, 15, 12, 9, 6, 3, 2);
                this.m_playMsecsBands = new Vector<int>(8);
                this.m_playDefConMsecs = new Vector<int>();
                this.m_sessionInterval = param2;
                this.m_sessionid = param1;
                this.m_trackAMFTotalWait = param3;
            }
            else
            {
                this.m_failedInit = true;
                this.m_active = false;
                trace("ERROR:ZaspGPIManager external interface failed to init");
            }
            return;
        }//end

        public Object  report ()
        {
            Object _loc_1 =null ;
            if (this.m_queuedMessages.length > 0)
            {
                _loc_1 = new Object();
                _loc_1.type = "GPI";
                _loc_1.data = this.m_queuedMessages;
                this.m_queuedMessages = new Array();
            }
            return _loc_1;
        }//end

        public void  sample ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            double _loc_3 =0;
            int _loc_4 =0;
            if (this.m_lastSession == 0)
            {
                this.m_lastSession = getTimer();
                this.queueSessionReport();
            }
            else
            {
                _loc_1 = getTimer();
                if (_loc_1 - this.m_lastSession > this.m_sessionInterval)
                {
                    if (_loc_1 - this.m_playInterval < this.m_lastActivity)
                    {
                        this.m_play_samples++;
                        this.m_play_f2f_sum = this.m_play_f2f_sum + this.m_f2f5s;
                        this.m_play_oef_sum = this.m_play_oef_sum + this.m_oef5s;
                        _loc_2 = _loc_1 - this.m_lastSession;
                        this.m_playMsecs = this.m_playMsecs + _loc_2;
                        if (this.m_lastDefCon >= 0)
                        {
                            while (this.m_playDefConMsecs.length < (this.m_lastDefCon + 1))
                            {

                                this.m_playDefConMsecs.put(this.m_playDefConMsecs.length,  0);
                            }
                            this.m_playDefConMsecs.put(this.m_lastDefCon,  this.m_playDefConMsecs.get(this.m_lastDefCon) + _loc_2);
                        }
                        _loc_3 = 1000 / this.m_f2f5s;
                        while (_loc_4 < this.m_playMsecsBandDefinition.length())
                        {

                            if (_loc_3 < this.m_playMsecsBandDefinition.get(_loc_4))
                            {
                                this.m_playMsecsBands.put(_loc_4,  this.m_playMsecsBands.get(_loc_4) + _loc_2);
                            }
                            else
                            {
                                break;
                            }
                            _loc_4 = _loc_4 + 1;
                        }
                    }
                    this.queueSessionReport();
                    this.m_lastSession = _loc_1;
                    if (this.m_componentInitTimes && m_logOnce)
                    {
                        this.queueMessage(this.m_componentInitTimes);
                        m_logOnce = false;
                    }
                }
            }
            return;
        }//end

        public void  popup ()
        {
            this.m_numPopups++;
            return;
        }//end

        public void  click ()
        {
            this.m_numClicks++;
            return;
        }//end

        public void  trackLoadTimingStart (String param1 )
        {
            if (param1 !=null)
            {
                if (!this.m_componentInitTimes)
                {
                    this.m_componentInitTimes = new Object();
                }
                this.m_componentInitTimes.put("type",  "loadstats");
                this.m_componentInitTimes.put(param1,  getTimer());
            }
            return;
        }//end

        public void  trackLoadTimingStop (String param1 )
        {
            if (param1 !=null)
            {
                if (this.m_componentInitTimes && this.m_componentInitTimes.get(param1))
                {
                    this.m_componentInitTimes.put(param1,  getTimer() - this.m_componentInitTimes.get(param1));
                }
            }
            return;
        }//end

        public void  interactive ()
        {
            this.m_loadTime = getTimer();
            this.m_sessionStartTime = this.m_loadTime;
            m_logOnce = true;
            if (this.m_numActiveSTPWaits)
            {
                this.m_waitStartTime = this.m_loadTime;
            }
            if (this.m_numActiveWaits)
            {
                this.m_sTPWaitStartTime = this.m_loadTime;
            }
            return;
        }//end

        public void  activate (boolean param1 ,boolean param2 ,boolean param3 )
        {
            if (!this.m_active && !this.m_failedInit)
            {
                this.m_active = true;
                this.m_sendSessionMessage = param1;
                this.m_sendWaitMessage = param2;
                this.m_sendWFMessage = param3;
            }
            return;
        }//end

        public void  deactivate ()
        {
            if (this.m_active && !this.m_failedInit)
            {
                this.m_active = false;
            }
            return;
        }//end

        private void  queueMessage (Object param1 )
        {
            this.m_queuedMessages.push(param1);
            return;
        }//end

        public void  ignoreWaits (boolean param1 )
        {
            this.m_ignoreWait = param1;
            return;
        }//end

        public boolean  waitsDisabled ()
        {
            return this.m_ignoreWait;
        }//end

        private void  queueWFMessage (int param1 ,String param2 ,int param3 ,int param4 ,String param5 )
        {
            Object _loc_6 =null ;
            if (this.m_sendWFMessage)
            {
                _loc_6 = new Object();
                _loc_6.sessionid = this.m_sessionid;
                _loc_6.workFlow = param2;
                _loc_6.timeStamp = param1;
                _loc_6.wfLength = param3;
                _loc_6.waitTime = param4;
                _loc_6.type = "gs";
                _loc_6.waits = param5;
                this.queueMessage(_loc_6);
            }
            return;
        }//end

        private void  queueWaitMessage (int param1 ,String param2 ,String param3 ,int param4 ,String param5 ,String param6 )
        {
            Object _loc_7 =null ;
            if (this.m_sendWaitMessage)
            {
                _loc_7 = new Object();
                _loc_7.sessionid = this.m_sessionid;
                _loc_7.timeStamp = param1;
                _loc_7.detail = param3;
                _loc_7.MD5 = param6;
                _loc_7.type = "gsWait";
                _loc_7.waitType = param2;
                _loc_7.waitTime = param4;
                _loc_7.wfs = param5;
                this.queueMessage(_loc_7);
            }
            return;
        }//end

        public void  setFPSData (double param1 ,double param2 ,double param3 ,double param4 ,double param5 ,int param6 ,int param7 )
        {
            this.m_f2f = param1;
            this.m_mem = param3;
            this.m_oef = param2;
            this.m_f2f5s = param4;
            this.m_oef5s = param5;
            this.m_lastActivity = param6;
            this.m_lastDefCon = param7;
            return;
        }//end

        private void  queueSessionReport ()
        {
            Object _loc_1 =null ;
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.m_sendSessionMessage && this.m_loadTime != 0)
            {
                _loc_1 = new Object();
                _loc_2 = getTimer();
                _loc_1.type = "gsSession";
                _loc_1.sessionid = this.m_sessionid;
                _loc_1.timeStamp = _loc_2;
                _loc_1.sessionTime = _loc_2 - this.m_sessionStartTime;
                if (this.m_numActiveWaits)
                {
                    _loc_1.waitTime = this.m_totalWaitTime + (_loc_2 - this.m_waitStartTime);
                }
                else
                {
                    _loc_1.waitTime = this.m_totalWaitTime;
                }
                _loc_1.numClicks = this.m_numClicks;
                _loc_1.numDialogs = this.m_numPopups;
                _loc_1.numLoads = this.m_numLoads;
                _loc_1.numSTPs = this.m_numSTPs;
                _loc_1.numUnknownWaits = this.m_numUnknownWaits;
                _loc_1.numWorkFlows = this.m_numWFs;
                if (this.m_numActiveSTPWaits)
                {
                    _loc_1.STPWaitTime = this.m_sTPWaitTime + (_loc_2 - this.m_sTPWaitStartTime);
                }
                else
                {
                    _loc_1.STPWaitTime = this.m_sTPWaitTime;
                }
                _loc_1.fps = this.m_f2f;
                _loc_1.mem = this.m_mem;
                _loc_1.oef = this.m_oef;
                if (this.m_play_samples > 0)
                {
                    _loc_1.play_oef = this.m_play_oef_sum / this.m_play_samples;
                    _loc_1.play_f2f = this.m_play_f2f_sum / this.m_play_samples;
                    _loc_1.play_msecs = this.m_playMsecs;
                    _loc_1.play_msec_bands = "";
                    _loc_1.play_msec_bands_b = "";
                    _loc_3 = 0;
                    while (_loc_3 < this.m_playMsecsBandDefinition.length / 2)
                    {

                        _loc_1.play_msec_bands = _loc_1.play_msec_bands + (this.m_playMsecsBandDefinition.get(_loc_3).toString() + "-" + this.m_playMsecsBands.get(_loc_3).toString() + "-");
                        _loc_3 = _loc_3 + 1;
                    }
                    _loc_3 = this.m_playMsecsBandDefinition.length / 2;
                    while (_loc_3 < this.m_playMsecsBandDefinition.length())
                    {

                        _loc_1.play_msec_bands_b = _loc_1.play_msec_bands_b + (this.m_playMsecsBandDefinition.get(_loc_3).toString() + "-" + this.m_playMsecsBands.get(_loc_3).toString() + "-");
                        _loc_3 = _loc_3 + 1;
                    }
                    _loc_1.play_defcon_msecs = "";
                    _loc_3 = 0;
                    while (_loc_3 < this.m_playDefConMsecs.length())
                    {

                        _loc_1.play_defcon_msecs = _loc_1.play_defcon_msecs + (this.m_playDefConMsecs.get(_loc_3).toString() + "-");
                        _loc_3 = _loc_3 + 1;
                    }
                }
                _loc_1.loadTime = this.m_loadTime;
                this.queueMessage(_loc_1);
            }
            return;
        }//end

        private void  wFStart (String param1 )
        {
            Object _loc_2 =null ;
            _loc_4 = undefined;
            Object _loc_5 =null ;
            _loc_3 = getTimer();
            if (!this.m_activeWFs.get(param1))
            {
                _loc_2 = new Object();
                this.m_numWFs++;
                if (this.m_numActiveWaits)
                {
                    _loc_2.waitStartTime = _loc_3;
                }
                else
                {
                    _loc_2.waitStartTime = 0;
                }
                _loc_2.waitTime = 0;
                _loc_2.startTime = _loc_3;
                _loc_2.name = param1;
                _loc_2.waits = new Array();
                this.m_activeWFs.put(param1,  _loc_2);
                for(int i0 = 0; i0 < this.m_activeWaits.size(); i0++)
                {
                		_loc_4 = this.m_activeWaits.get(i0);

                    _loc_5 = this.m_activeWaits.get(_loc_4);
                    _loc_5.wfs.push(_loc_2.name);
                    _loc_2.waits.push(_loc_5.MD5);
                }
            }
            else
            {
                trace("starting a workflow thats already started:" + param1);
            }
            return;
        }//end

        private void  wFEnd (String param1 )
        {
            Object _loc_2 =null ;
            BloomFilter _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_3 = getTimer();
            if (this.m_activeWFs.get(param1) != null)
            {
                _loc_2 = this.m_activeWFs.get(param1);
                if (this.m_numActiveWaits)
                {
                    if (_loc_2.waitStartTime)
                    {
                        _loc_2.waitTime = _loc_2.waitTime + (_loc_3 - _loc_2.waitStartTime);
                    }
                    else
                    {
                        _loc_2.waitTime = _loc_2.waitTime + (_loc_3 - this.m_waitStartTime);
                    }
                }
                _loc_4 = new BloomFilter(new Array(BloomFilter.DJB, BloomFilter.PJW, BloomFilter.DEK, BloomFilter.JS, BloomFilter.ELF), 20);
                _loc_5 = "";
                if (_loc_2.waits.length != 0)
                {
                    _loc_4.BloomAdd(_loc_2.waits.get(0));
                    _loc_7 = 1;
                    while (_loc_7 < _loc_2.waits.length())
                    {

                        _loc_4.BloomAdd(_loc_2.waits.get(_loc_7));
                        _loc_7++;
                    }
                    _loc_5 = _loc_4.toString();
                }
                _loc_6 = _loc_3 - _loc_2.startTime;
                this.queueWFMessage(_loc_3, param1, _loc_6, _loc_2.waitTime, _loc_5);
                delete this.m_activeWFs.get(param1);
            }
            else
            {
                trace("cant find a workflow to end:" + param1);
            }
            return;
        }//end

        private int  zaspGetWaitHandle (String param1 ,String param2 )
        {
            Object _loc_3 =new Object ();
            _loc_3.type = param1;
            _loc_3.detail = param2;
            (waitHandle + 1);
            this.m_waitHandleDict.put(waitHandle,  _loc_3);
            return waitHandle;
        }//end

        private Object  zaspGetWaitDetails (int param1 )
        {
            Object _loc_2 =null ;
            if (!this.m_waitHandleDict.get(param1))
            {
                trace("handle:" + param1.toString() + " not found in m_waitHandleDict");
            }
            _loc_2 = this.m_waitHandleDict.get(param1);
            return _loc_2;
        }//end

        private void  zaspRemoveWait (int param1 )
        {
            delete this.m_waitHandleDict.get(param1);
            return;
        }//end

        private void  incrementWaitCounters (String param1 )
        {
            this.m_numWaits++;
            switch(param1)
            {
                case "AMF":
                case "AMF-STP":
                {
                    this.m_numSTPs++;
                    break;
                }
                case "DIALOG":
                {
                    this.m_numDialogs++;
                    break;
                }
                case "ASSET":
                case "HIPRI_ASSET":
                {
                    this.m_numLoads++;
                    break;
                }
                default:
                {
                    this.m_numUnknownWaits++;
                    break;
                    break;
                }
            }
            return;
        }//end

        public int  zaspWaitStart (String param1 ,String param2 )
        {
            int _loc_4 =0;
            Object _loc_5 =null ;
            _loc_6 = undefined;
            Object _loc_7 =null ;
            int _loc_3 =0;
            if (this.m_active && (param1 != "ASSET" || this.m_userWait == true) && !this.waitsDisabled())
            {
                _loc_4 = this.zaspGetWaitHandle(param1, param2);
                _loc_5 = new Object();
                _loc_5.start = getTimer();
                _loc_5.wfs = new Array();
                _loc_5.detail = param2;
                _loc_5.MD5 = MD5.hash(param2);
                this.m_activeWaits.put(_loc_4,  _loc_5);
                if (this.m_trackAMFTotalWait || param1 != "AMF")
                {
                    this.m_numActiveWaits++;
                    if (!this.m_waitStartTime)
                    {
                        this.m_waitStartTime = _loc_5.start;
                    }
                }
                if (param1 == "AMF" || param1 == "AMF-STP")
                {
                    this.m_numActiveSTPWaits++;
                    if (!this.m_sTPWaitStartTime)
                    {
                        this.m_sTPWaitStartTime = _loc_5.start;
                    }
                }
                for(int i0 = 0; i0 < this.m_activeWFs.size(); i0++)
                {
                		_loc_6 = this.m_activeWFs.get(i0);

                    _loc_7 = this.m_activeWFs.get(_loc_6);
                    _loc_5.wfs.push(_loc_7.name);
                    _loc_7.waits.push(_loc_5.MD5);
                }
                this.incrementWaitCounters(param1);
                _loc_3 = _loc_4;
            }
            else
            {
                _loc_3 = 0;
            }
            return _loc_3;
        }//end

        private void  updateWFs (int param1 )
        {
            _loc_2 = undefined;
            Object _loc_3 =null ;
            int _loc_4 =0;
            for(int i0 = 0; i0 < this.m_activeWFs.size(); i0++)
            {
            		_loc_2 = this.m_activeWFs.get(i0);

                _loc_3 = this.m_activeWFs.get(_loc_2);
                if (_loc_3.waitStartTime != 0)
                {
                    _loc_3.waitTime = _loc_3.waitTime + (param1 - _loc_3.waitStartTime);
                    _loc_3.waitStartTime = 0;
                    continue;
                }
                _loc_4 = param1 - this.m_waitStartTime;
                _loc_3.waitTime = _loc_3.waitTime + _loc_4;
            }
            return;
        }//end

        private void  updateWaitState (int param1 )
        {
            if (this.m_numActiveWaits == 0)
            {
                this.updateWFs(param1);
                if (this.m_loadTime != 0)
                {
                    this.m_totalWaitTime = this.m_totalWaitTime + (param1 - this.m_waitStartTime);
                }
                this.m_waitStartTime = 0;
            }
            return;
        }//end

        private void  updateSTPWaitState (int param1 )
        {
            if (this.m_numActiveSTPWaits == 0)
            {
                this.updateWFs(param1);
                if (this.m_loadTime != 0)
                {
                    this.m_sTPWaitTime = this.m_sTPWaitTime + (param1 - this.m_sTPWaitStartTime);
                }
                this.m_sTPWaitStartTime = 0;
            }
            return;
        }//end

        public void  zaspWaitEnd (int param1 )
        {
            Object _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            int _loc_7 =0;
            if (this.m_active)
            {
                _loc_2 = this.m_activeWaits.get(param1);
                if (_loc_2)
                {
                    _loc_3 = getTimer();
                    _loc_4 = _loc_3 - _loc_2.start;
                    _loc_5 = this.zaspGetWaitDetails(param1);
                    if (this.m_trackAMFTotalWait || _loc_5.type != "AMF")
                    {
                        this.m_numActiveWaits--;
                        this.updateWaitState(_loc_3);
                    }
                    if (_loc_5.type == "AMF" || _loc_5.type == "AMF-STP")
                    {
                        this.m_numActiveSTPWaits--;
                        this.updateSTPWaitState(_loc_3);
                    }
                    _loc_6 = "";
                    if (_loc_2.wfs.length > 0)
                    {
                        _loc_6 = _loc_2.wfs.get(0);
                        _loc_7 = 1;
                        while (_loc_7 < _loc_2.wfs.length())
                        {

                            _loc_6 = _loc_6 + "," + _loc_2.wfs.get(_loc_7);
                            _loc_7++;
                        }
                    }
                    delete this.m_activeWaits.get(param1);
                    this.queueWaitMessage(_loc_3, _loc_5.type, _loc_5.detail, _loc_4, _loc_6, _loc_2.MD5);
                    this.zaspRemoveWait(param1);
                }
                else
                {
                    trace("Failed to find wait to end " + param1.toString());
                }
            }
            return;
        }//end

        public void  zaspUserWaitStart ()
        {
            this.m_userWait = true;
            return;
        }//end

        public void  zaspUserWaitEnd ()
        {
            this.m_userWait = false;
            return;
        }//end

        public void  zaspWorkFlowEvent (String param1 ,String param2 )
        {
            if (this.m_active)
            {
                if (param2 == "BEGIN")
                {
                    this.wFStart(param1);
                }
                else if (param2 == "END")
                {
                    this.wFEnd(param1);
                }
            }
            return;
        }//end

        public void  waitStateChange (boolean param1 ,String param2 )
        {
            if (this.m_active)
            {
                if (param1 !=null)
                {
                    this.m_uiWaitHandle = this.zaspWaitStart("JS", param2);
                }
                else if (this.m_uiWaitHandle)
                {
                    this.zaspWaitEnd(this.m_uiWaitHandle);
                }
            }
            return;
        }//end

    }




