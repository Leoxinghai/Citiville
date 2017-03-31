package Display.aswingui.inline.impl;

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

import Classes.util.*;
import Display.aswingui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import org.aswing.*;

    public class ASwingCountdownText extends ASwingObject implements IASwingCountdownText
    {
        private JCountdownText m_component ;
        private double m_timeRemaining =0;
        private ITimeFormatter m_timeFormatter ;
        private static  ITimeFormatter defaultFormatter =new SimpleTimeFormatter ();

        public  ASwingCountdownText (String param1)
        {
            super(param1);
            this.m_timeFormatter = defaultFormatter;
            return;
        }//end

         public void  destroy ()
        {
            this.m_component = null;
            this.m_timeFormatter = null;
            super.destroy();
            return;
        }//end

        public IASwingCountdownText  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end

        public IASwingCountdownText  position (int param1 ,int param2 )
        {
            m_x = param1;
            m_y = param2;
            return this;
        }//end

        public IASwingCountdownText  size (int param1 ,int param2 )
        {
            m_width = param1;
            m_height = param2;
            return this;
        }//end

        public IASwingCountdownText  timeRemaining (double param1 )
        {
            this.m_timeRemaining = param1;
            return this;
        }//end

        public IASwingCountdownText  timeFormatter (ITimeFormatter param1 )
        {
            this.m_timeFormatter = param1;
            return this;
        }//end

        public Component  component ()
        {
            Date _loc_1 =null ;
            if (!this.m_component)
            {
                _loc_1 = new Date(this.m_timeRemaining);
                this.m_component = new JCountdownText(_loc_1, this.m_timeFormatter);
                this.initialize(this.m_component);
            }
            return this.m_component;
        }//end

    }


