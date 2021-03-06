﻿package Modules.guide.actions;

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

    public class GAPause extends GuideAction
    {
        protected double m_currTime ;
        protected double m_pauseTime ;
        protected boolean m_engaged ;

        public  GAPause ()
        {
            this.m_engaged = false;
            this.m_currTime = 0;
            this.m_pauseTime = 0;
            return;
        }//end  

         public void  enter ()
        {
            this.m_currTime = 0;
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"pause");
            if (!_loc_2)
            {
                return false;
            }
            this.m_pauseTime = parseInt(_loc_2.@duration);
            return true;
        }//end  

         public void  update (double param1 )
        {
            super.update(param1);
            if (this.m_currTime > this.m_pauseTime)
            {
                removeState(this);
            }
            else
            {
                this.m_currTime = this.m_currTime + param1;
            }
            return;
        }//end  

    }



