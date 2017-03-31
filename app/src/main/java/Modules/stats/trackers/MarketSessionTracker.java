package Modules.stats.trackers;

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

import Engine.Managers.*;
//import flash.utils.*;

    public class MarketSessionTracker
    {
        private double m_startTime ;
        private double m_itemCount ;

        public void  MarketSessionTracker ()
        {
            return;
        }//end

        public void  startSession ()
        {
            this.m_startTime = getTimer();
            this.m_itemCount = 0;
            return;
        }//end

        public void  trackItem ()
        {
            this.m_itemCount++;
            return;
        }//end

        public void  endSession (String param1 ="")
        {
            _loc_2 = getTimer();
            _loc_3 = _loc_2-this.m_startTime ;
            StatsManager.count("MarketSession", "time", param1, "", "", "", _loc_3);
            StatsManager.count("MarketSession", "clicks", "", "", "", "", this.m_itemCount);
            return;
        }//end

    }



