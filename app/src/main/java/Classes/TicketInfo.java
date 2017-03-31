package Classes;

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

    public class TicketInfo
    {
        protected String m_theme =null ;
        protected String m_currency =null ;
        protected double m_capacity =0;
        protected Array m_levels =null ;
        protected Array m_rewards =null ;
        protected Array m_bundles =null ;
        protected String m_limited =null ;

        public  TicketInfo (String param1 ,String param2 ,Array param3 ,Array param4 ,Array param5 ,String param6 )
        {
            this.m_theme = param1;
            this.m_currency = param2;
            this.m_levels = param3;
            this.m_rewards = param4;
            this.m_bundles = param5;
            this.m_limited = param6;
            this.m_capacity = 0;
            if (this.m_levels && this.m_levels.length())
            {
                this.m_capacity = this.m_levels.get((this.m_levels.length - 1));
            }
            return;
        }//end

        public String  theme ()
        {
            return this.m_theme;
        }//end

        public String  currency ()
        {
            return this.m_currency;
        }//end

        public double  capacity ()
        {
            return this.m_capacity;
        }//end

        public Array  levels ()
        {
            return this.m_levels.slice(0);
        }//end

        public Array  rewards ()
        {
            return this.m_rewards.slice(0);
        }//end

        public Array  bundles ()
        {
            return this.m_bundles.slice(0);
        }//end

        public String  limited ()
        {
            return this.m_limited;
        }//end

    }


