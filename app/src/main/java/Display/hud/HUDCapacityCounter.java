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
    public class HUDCapacityCounter extends HUDCounter
    {
        protected double m_targetCap =0;
        protected double m_targetRealValue =0;
        protected double m_currentRealValue =0;
        protected double m_realValueRate =0;

        public  HUDCapacityCounter (Function param1 )
        {
            super(param1);
            return;
        }//end

        public double  capacity ()
        {
            return this.m_targetCap;
        }//end

        public void  capacity (double param1 )
        {
            if (param1 == this.m_targetCap)
            {
                return;
            }
            this.m_targetCap = param1;
            m_targetValue = this.getTargetValue(this.m_targetRealValue, this.m_targetCap);
            m_startTimer = true;
            return;
        }//end

         public double  value ()
        {
            return this.m_targetRealValue;
        }//end

         public void  value (double param1 )
        {
            if (param1 == this.m_targetRealValue)
            {
                return;
            }
            this.m_targetRealValue = param1;
            m_targetValue = this.getTargetValue(this.m_targetRealValue, this.m_targetCap);
            m_startTimer = true;
            return;
        }//end

         public void  update (boolean param1 =false )
        {
            if (param1 !=null)
            {
                m_currentValue = m_targetValue;
                this.m_currentRealValue = this.m_targetRealValue;
                m_updateCallback(m_currentValue, this.m_currentRealValue);
            }
            else if (m_startTimer)
            {
                m_changeRate = this.calculateChangeRateNumber(m_currentValue, m_targetValue);
                this.m_realValueRate = calculateChangeRate(this.m_currentRealValue, this.m_targetRealValue);
                m_updateTimer.start();
            }
            m_startTimer = false;
            return;
        }//end

         protected void  updateCurrentValue (TimerEvent event )
        {
            m_currentValue = m_currentValue + m_changeRate;
            this.m_currentRealValue = this.m_currentRealValue + this.m_realValueRate;
            if (m_changeRate > 0 && m_currentValue > m_targetValue || m_changeRate < 0 && m_currentValue < m_targetValue)
            {
                m_currentValue = m_targetValue;
            }
            if (this.m_realValueRate > 0 && this.m_currentRealValue > this.m_targetRealValue || this.m_realValueRate < 0 && this.m_currentRealValue < this.m_targetRealValue)
            {
                this.m_currentRealValue = this.m_targetRealValue;
            }
            m_updateCallback(m_currentValue, this.m_currentRealValue);
            if (m_currentValue == m_targetValue)
            {
                m_updateTimer.reset();
                m_updateTimer.stop();
            }
            return;
        }//end

        protected double  getTargetValue (double param1 ,double param2 )
        {
            return param1 / param2;
        }//end

        protected double  calculateChangeRateNumber (double param1 ,double param2 )
        {
            return (param2 - param1) / COUNT_RATE;
        }//end

    }


