package Modules.sunset;

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

import Init.*;
    public class Sunset
    {
        public String theme ;
        private double m_endDate ;
        private double m_startDate ;

        public  Sunset (String param1 ,double param2 ,double param3 )
        {
            this.theme = param1;
            this.m_endDate = param2;
            this.m_startDate = param3;
            return;
        }//end

        public boolean  isInSunsetInterval ()
        {
            _loc_1 = GameSettingsInit.getCurrentTime();
            return this.m_startDate < _loc_1 && !this.isSunset();
        }//end

        public double  getSunsetTimeRemaining ()
        {
            _loc_1 = GameSettingsInit.getCurrentTime();
            return Math.max(this.m_endDate - _loc_1, 0);
        }//end

        public boolean  isSunset ()
        {
            _loc_1 = GameSettingsInit.getCurrentTime();
            return this.m_endDate < _loc_1;
        }//end

        public double  startDate ()
        {
            return this.m_startDate;
        }//end

        public double  endDate ()
        {
            return this.m_endDate;
        }//end

        public double  daysLeft ()
        {
            return (this.endDate - GameSettingsInit.getCurrentTime()) / (1000 * 3600 * 24);
        }//end

    }



