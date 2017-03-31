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

//import flash.display.*;
//import flash.system.*;
//import flash.utils.*;
import zasp.Util.*;

    public class ZaspTrackerBase
    {
        protected double sma_dt =5000;
        protected int totalDt =0;
        protected int totalNum =0;
        protected String gameState ="";
        protected double gameNumber =0;
        protected int objectCount =0;
        protected EMA emaF2F ;
        protected EMA emaOEF ;
        protected EMA emaMEM ;
        protected SumSampler sum5sF2F =null ;
        protected SumSampler sum5sOEF =null ;
        protected ZaspTrackerDetail detailTracker =null ;
        protected ZaspTrackerDebug debugTracker =null ;
        private String m_sessionid ;
        public static  double ALPHA =0.07;

        public  ZaspTrackerBase (String param1 ,int param2 =5000)
        {
            this.emaF2F = new EMA(ALPHA);
            this.emaOEF = new EMA(ALPHA);
            this.emaMEM = new EMA(ALPHA);
            this.sma_dt = param2;
            this.m_sessionid = param1;
            this.sum5sF2F = new SumSampler(this.sma_dt);
            this.sum5sOEF = new SumSampler(this.sma_dt);
            return;
        }//end

        public void  sample (double param1 ,double param2 )
        {
            _loc_3 = System.totalMemory;
            this.totalDt = getTimer();
            this.totalNum++;
            this.emaF2F.sample(param1);
            this.emaOEF.sample(param2);
            this.emaMEM.sample(_loc_3);
            this.sum5sF2F.sample(param1);
            this.sum5sOEF.sample(param2);
            if (this.detailTracker)
            {
                this.detailTracker.sample(this.gameState, this.gameNumber, param1, _loc_3);
            }
            if (this.debugTracker)
            {
                this.debugTracker.sample(param1, param2);
            }
            return;
        }//end

        public void  activateDetailTracking ()
        {
            this.detailTracker = new ZaspTrackerDetail();
            return;
        }//end

        public boolean  isDetailActive ()
        {
            return this.detailTracker != null;
        }//end

        public void  activateDebugTracking (DisplayObjectContainer param1 ,boolean param2 =false )
        {
            this.debugTracker = new ZaspTrackerDebug(this, param2);
            if (param1 !=null)
            {
                param1.addChild(this.debugTracker.sprite);
                this.debugTracker.activateSpinners();
            }
            return;
        }//end

        public Object  report ()
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Object _loc_1 =new Object ();
            _loc_1.type = "fps";
            _loc_1.constants = "Alpha:" + ALPHA + " SMA_DT:" + this.sma_dt;
            _loc_1.totalDt = this.totalDt;
            _loc_1.totalNum = this.totalNum;
            _loc_1.ema_mem = this.emaMEM.value;
            _loc_1.game_state = this.gameState;
            _loc_1.game_number = this.gameNumber;
            _loc_1.object_count = this.objectCount;
            _loc_1.sessionid = this.m_sessionid;
            _loc_2 = this.emaF2F.value;
            _loc_1.ema_f2f_dt = _loc_2;
            if (_loc_2 > 0)
            {
                _loc_1.ema_f2f_fps = 1000 / _loc_2;
            }
            _loc_3 = this.emaOEF.value;
            _loc_1.ema_oef_dt = _loc_3;
            if (_loc_3 > 0)
            {
                _loc_1.ema_oef_fps = 1000 / _loc_3;
            }
            if (this.sum5sF2F.length > 0)
            {
                _loc_4 = this.sum5sF2F.value / this.sum5sF2F.length;
                _loc_1.s5s_f2f_dt = _loc_4;
                if (_loc_4 > 0)
                {
                    _loc_1.s5s_f2f_fps = 1000 / _loc_4;
                }
            }
            if (this.sum5sOEF.length > 0)
            {
                _loc_5 = this.sum5sOEF.value / this.sum5sOEF.length;
                _loc_1.s5s_oef_dt = _loc_5;
                if (_loc_5 > 0)
                {
                    _loc_1.s5s_oef_fps = 1000 / _loc_5;
                }
            }
            if (this.detailTracker)
            {
                _loc_1.detail = this.detailTracker.report();
            }
            return _loc_1;
        }//end

        public double  f2f ()
        {
            return this.emaF2F.value;
        }//end

        public double  f2f5s ()
        {
            return this.sum5sF2F.value / this.sum5sF2F.length;
        }//end

        public double  oef ()
        {
            return this.emaOEF.value;
        }//end

        public double  oef5s ()
        {
            return this.sum5sOEF.value / this.sum5sOEF.length;
        }//end

        public double  mem ()
        {
            return this.emaMEM.value;
        }//end

        public void  setGameState (String param1 ="",double param2)
        {
            this.gameState = param1;
            this.gameNumber = param2;
            return;
        }//end

        public void  setObjectCount (int param1 =0)
        {
            this.objectCount = param1;
            return;
        }//end

        public void  log_report ()
        {
            double _loc_3 =0;
            trace("PERFORMANCE REPORT BASE");
            trace("  ALPHA:         " + ALPHA);
            trace("  SMA_DT:        " + this.sma_dt);
            trace("  totalDt:       " + this.totalDt);
            trace("  totalNum:      " + this.totalNum);
            trace("  ema_mem:       " + this.emaMEM.value);
            _loc_1 = this.emaF2F.value;
            trace("  ema_f2f_dt:  " + _loc_1);
            trace("  object count: " + this.objectCount);
            if (_loc_1 > 0)
            {
                trace("  ema_f2f_fps:   " + 1000 / _loc_1);
            }
            _loc_2 = this.emaOEF.value;
            trace("  ema_oef_dt:    " + _loc_2);
            if (_loc_2 > 0)
            {
                trace("  ema_oef_fps:   " + 1000 / _loc_2);
            }
            if (this.sum5sF2F.length > 0)
            {
                _loc_3 = this.sum5sF2F.value / this.sum5sF2F.length;
                trace("  5s_f2f_dt:     " + _loc_3);
                if (_loc_3 > 0)
                {
                    trace("  5s_f2f_fps:    " + 1000 / _loc_3);
                }
            }
            if (this.detailTracker)
            {
                this.detailTracker.log_report();
            }
            if (this.debugTracker)
            {
                this.debugTracker.log_report();
            }
            return;
        }//end

    }




