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

import Engine.Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import zasp.Bridge.*;
import zasp.Classes.*;

    public class ZaspManager
    {
        protected double m_reportInterval =5000;
        protected int m_batchSize =4;
        protected boolean m_active =false ;
        protected boolean m_sendFPSMessage =false ;
        protected Array m_batchedReports ;
        protected ZaspTrackerBase m_baseTracker ;
        protected ZaspTrackerGPI m_gpiTracker =null ;
        protected double m_reportStart ;
        protected double m_previousFrameStart ;
        protected double m_frameStart =0;
        protected double m_frameEnd =0;
        protected boolean m_gameStateActive =true ;
        protected int m_lastActivity =0;
        protected int m_lastInactivity =0;
        protected Function m_gameDetailCallback =null ;
        protected Function m_gameNumberCallback =null ;
        protected BridgeAgent m_bridgeAgent =null ;
        public static  int DEFAULT_SAMPLE_RATE =5000;

        public  ZaspManager (DisplayObject param1 )
        {
            this.m_batchedReports = new Array();
            _loc_2 = (int)(GlobalEngine.getFlashVar("zaspSampleRate"));
            if (_loc_2 > 0)
            {
                if (_loc_2 == 1 || Math.random() * _loc_2 <= 1)
                {
                    this.activate();
                }
            }
            _loc_2 = int(GlobalEngine.getFlashVar("zaspSamplePeriod"));
            if (_loc_2 > 0)
            {
                this.m_reportInterval = _loc_2;
            }
            this.m_gameDetailCallback = this.gameDetail;
            this.m_gameNumberCallback = this.gameNumber;
            this.addStageListeners(param1);
            if (GlobalEngine.getFlashVar("bridgeAgent") == "true")
            {
                this.m_bridgeAgent = new BridgeAgent(param1);
                this.m_bridgeAgent.start();
            }
            return;
        }//end

        protected void  activate ()
        {
            this.m_active = true;
            this.m_baseTracker = new ZaspTrackerBase(String(GlobalEngine.getFlashVar("zaspSessionKey")), this.m_reportInterval);
            if (GlobalEngine.getFlashVar("zaspDetail") == "true")
            {
                this.m_baseTracker.activateDetailTracking();
            }
            if (GlobalEngine.getFlashVar("zaspFPS") == "true")
            {
                this.m_sendFPSMessage = true;
            }
            if (GlobalEngine.getFlashVar("zaspGPI") == "true")
            {
                this.m_gpiTracker = new ZaspTrackerGPI(String(GlobalEngine.getFlashVar("zaspSessionKey")), this.m_reportInterval, false);
                this.m_gpiTracker.activate(GlobalEngine.getFlashVar("zaspSession") == "true", GlobalEngine.getFlashVar("zaspWait") == "true", GlobalEngine.getFlashVar("zaspWF") == "true");
                trace("activating GPI reports");
            }
            return;
        }//end

        public int  zaspWaitStart (String param1 ,String param2 )
        {
            if (this.m_active && this.m_gpiTracker)
            {
                return this.m_gpiTracker.zaspWaitStart(param1, param2);
            }
            return 0;
        }//end

        public void  zaspWaitEnd (int param1 )
        {
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.zaspWaitEnd(param1);
            }
            return;
        }//end

        public void  zaspPopup ()
        {
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.popup();
            }
            return;
        }//end

        public void  zaspInteractive ()
        {
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.interactive();
            }
            if (this.m_bridgeAgent)
            {
                this.m_bridgeAgent.saveLoadTime(getTimer());
            }
            return;
        }//end

        public void  zaspGameStateDeactivate (Event event )
        {
            this.m_gameStateActive = false;
            this.m_lastInactivity = getTimer();
            return;
        }//end

        public void  zaspGameStateActivate (Event event )
        {
            this.zaspActivity();
            this.m_gameStateActive = true;
            return;
        }//end

        public void  zaspOnMouseClick (Event event )
        {
            this.zaspClick();
            return;
        }//end

        public void  zaspOnMouseMove (Event event )
        {
            this.zaspMove();
            return;
        }//end

        public boolean  zaspIsGameActive ()
        {
            return this.m_gameStateActive;
        }//end

        public void  zaspMove ()
        {
            this.zaspActivity();
            return;
        }//end

        public void  zaspClick ()
        {
            this.zaspActivity();
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.click();
            }
            return;
        }//end

        public void  zaspActivity ()
        {
            this.m_lastActivity = getTimer();
            return;
        }//end

        public void  trackTimingStart (String param1 )
        {
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.trackLoadTimingStart(param1);
            }
            return;
        }//end

        public void  trackTimingStop (String param1 )
        {
            if (this.m_active && this.m_gpiTracker)
            {
                this.m_gpiTracker.trackLoadTimingStop(param1);
            }
            return;
        }//end

        public void  preUpdate ()
        {
            if (this.m_active)
            {
                this.m_previousFrameStart = this.m_frameStart;
                this.m_frameStart = getTimer();
            }
            return;
        }//end

        public void  postUpdate ()
        {
            if (this.m_active && this.m_previousFrameStart > 0)
            {
                this.m_frameEnd = getTimer();
                if (this.m_baseTracker.isDetailActive())
                {
                    this.updateGameState();
                }
                this.m_baseTracker.sample(this.m_frameStart - this.m_previousFrameStart, this.m_frameEnd - this.m_frameStart);
                if (this.m_gpiTracker)
                {
                    this.m_gpiTracker.setFPSData(this.m_baseTracker.f2f, this.m_baseTracker.oef, this.m_baseTracker.mem, this.m_baseTracker.f2f5s, this.m_baseTracker.oef5s, this.m_lastActivity, this.gameDefCon());
                    this.m_gpiTracker.sample();
                }
                if (!this.m_reportStart)
                {
                    this.m_reportStart = this.m_frameEnd;
                }
                else if (this.m_frameEnd - this.m_reportStart > this.m_reportInterval)
                {
                    this.cacheReport();
                    this.m_reportStart = this.m_frameEnd;
                }
            }
            return;
        }//end

        protected void  cacheReport ()
        {
            Object _loc_1 =null ;
            if (this.m_baseTracker)
            {
                if (this.m_sendFPSMessage)
                {
                    _loc_1 = this.m_baseTracker.report();
                }
                if (_loc_1)
                {
                    this.modifyReport(_loc_1);
                    this.m_batchedReports.push(_loc_1);
                }
            }
            _loc_1 = null;
            if (this.m_gpiTracker)
            {
                _loc_1 = this.m_gpiTracker.report();
                if (_loc_1)
                {
                    this.modifyReport(_loc_1);
                    this.m_batchedReports.push(_loc_1);
                }
            }
            if (this.m_batchedReports.length >= this.m_batchSize)
            {
                this.sendReports();
            }
            return;
        }//end

        protected void  modifyReport (Object param1 )
        {
            if (param1.get("type") == "fps")
            {
                param1.put("constants",  param1.put("constants", param1.put("constants",  param1.get("constants") + " V:0"));
                param1.put("string5",  "");
                param1.put("string6",  "");
                param1.put("string7",  "");
                param1.put("string8",  "");
                param1.put("number7",  0);
                param1.put("number8",  0);
            }
            else if (param1.get("type") == "GPI")
            {
            }
            return;
        }//end

        protected String  gameDetail ()
        {
            return "";
        }//end

        protected double  gameNumber ()
        {
            return 0;
        }//end

        protected int  gameDefCon ()
        {
            return -1;
        }//end

        private void  updateGameState ()
        {
            double _loc_1 =0;
            if (this.m_gameNumberCallback != null)
            {
                _loc_1 = this.m_gameNumberCallback();
            }
            String _loc_2 ="";
            if (this.m_gameDetailCallback != null)
            {
                _loc_2 = this.m_gameDetailCallback();
            }
            this.m_baseTracker.setGameState(_loc_2, _loc_1);
            return;
        }//end

        public void  sendReports ()
        {
            if (this.m_active && TransactionManager.canSendTransactions())
            {
                TransactionManager.addTransaction(new TSendZaspReport(this.m_batchedReports));
                this.m_batchedReports = new Array();
            }
            return;
        }//end

        public void  batchSize (int param1 )
        {
            this.m_batchSize = param1;
            return;
        }//end

        public void  reportInterval (double param1 )
        {
            this.m_reportInterval = param1;
            return;
        }//end

        public ZaspTrackerBase  zasp ()
        {
            return this.m_baseTracker;
        }//end

        public void  gameDetailCallback (Function param1 )
        {
            this.m_gameDetailCallback = param1;
            return;
        }//end

        public void  gameNumberCallback (Function param1 )
        {
            this.m_gameNumberCallback = param1;
            return;
        }//end

        public void  addStageListeners (DisplayObject param1 )
        {
            param1.addEventListener(Event.ACTIVATE, this.zaspGameStateActivate);
            param1.addEventListener(Event.DEACTIVATE, this.zaspGameStateDeactivate);
            param1.addEventListener(MouseEvent.CLICK, this.zaspOnMouseClick, false, 3);
            param1.addEventListener(MouseEvent.MOUSE_MOVE, this.zaspOnMouseMove, false, 3);
            return;
        }//end

        public void  forceActivateDebugTracking (DisplayObjectContainer param1)
        {
            if (this.m_active == false)
            {
                this.activate();
            }
            this.m_baseTracker.activateDebugTracking(param1);
            return;
        }//end

    }



