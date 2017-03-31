package Modules.hotels;

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

    public class HotelUpgradeFlashData
    {
        private int m_timeDelay =0;
        private int m_window =0;
        private int m_prizeIndex =0;

        public  HotelUpgradeFlashData (int param1 ,int param2 ,int param3 )
        {
            this.m_timeDelay = param1;
            this.m_window = param2;
            this.m_prizeIndex = param3;
            return;
        }//end

        public int  timeDelay ()
        {
            return this.m_timeDelay;
        }//end

        public void  timeDelay (int param1 )
        {
            this.m_timeDelay = param1;
            return;
        }//end

        public int  window ()
        {
            return this.m_window;
        }//end

        public void  window (int param1 )
        {
            this.m_window = param1;
            return;
        }//end

        public int  prizeIndex ()
        {
            return this.m_prizeIndex;
        }//end

        public void  prizeIndex (int param1 )
        {
            this.m_prizeIndex = param1;
            return;
        }//end

    }



