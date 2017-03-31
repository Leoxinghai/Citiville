package Display.hud;

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

//import flash.events.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class HUDCounter
    {
        protected double m_targetValue =0;
        protected double m_currentValue =0;
        protected double m_changeRate =0;
        protected boolean m_startTimer =false ;
        protected Timer m_updateTimer ;
        protected Function m_updateCallback ;
        public static  int TIMER_TICK =50;
        public static  int COUNT_RATE =30;

        public  HUDCounter (Function param1 )
        {
            this.m_updateTimer = new Timer(TIMER_TICK);
            this.m_updateCallback = param1;
            this.m_updateTimer.addEventListener(TimerEvent.TIMER, this.updateCurrentValue);
            return;
        }//end

        public double  value ()
        {
            return this.m_targetValue;
        }//end

        public void  value (double param1 )
        {
            if (param1 == this.m_targetValue)
            {
                return;
            }
            this.m_targetValue = param1;
            this.m_startTimer = true;
            return;
        }//end

        public void  update (boolean param1 =false )
        {
            Debug.debug7("HUDCounter.update " + this.m_targetValue);
            if (param1 !=null)
            {
                this.m_currentValue = this.m_targetValue;
                this.m_updateCallback(this.m_currentValue);
            }
            else if (this.m_startTimer)
            {
                this.m_changeRate = this.calculateChangeRate(this.m_currentValue, this.m_targetValue);
                this.m_updateTimer.start();
            }
            this.m_startTimer = false;
            return;
        }//end

        protected void  updateCurrentValue (TimerEvent event )
        {
            this.m_currentValue = this.m_currentValue + this.m_changeRate;
            if (this.m_changeRate > 0 && this.m_currentValue > this.m_targetValue || this.m_changeRate < 0 && this.m_currentValue < this.m_targetValue)
            {
                this.m_currentValue = this.m_targetValue;
            }
            this.m_updateCallback(this.m_currentValue);
            if (this.m_currentValue == this.m_targetValue)
            {
                this.m_updateTimer.reset();
                this.m_updateTimer.stop();
            }
            return;
        }//end

        protected int  calculateChangeRate (int param1 ,int param2 )
        {
            int _loc_3 =0;
            if (param2 > param1)
            {
                _loc_3 = (int)Math.ceil((param2 - param1) / COUNT_RATE);
            }
            else if (param2 < param1)
            {
                _loc_3 = (int)Math.floor((param2 - param1) / COUNT_RATE);
            }
            return _loc_3;
        }//end

    }


