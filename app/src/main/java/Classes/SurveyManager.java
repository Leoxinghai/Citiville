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

//import flash.events.*;
//import flash.external.*;
//import flash.geom.*;
//import flash.utils.*;

    public class SurveyManager
    {
        private String m_overlaySurveyUrl =null ;
        private boolean m_isInitialized =false ;
        private int m_delay =0;
        private Timer m_timer ;
        private int m_interval ;
        private Timer m_intervalChecker ;
        private int m_lastActiveTime ;
        private Point m_lastMousePos ;
        private static SurveyManager m_instance =null ;

        public  SurveyManager ()
        {
            return;
        }//end

        private boolean  checkIfMouseMoved ()
        {
            Point _loc_1 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
            boolean _loc_2 =true ;
            if (this.m_lastMousePos && _loc_1.equals(this.m_lastMousePos))
            {
                _loc_2 = false;
            }
            this.m_lastMousePos = _loc_1;
            return _loc_2;
        }//end

        public void  initializeSurveyOverlay (String param1 )
        {
            if (!this.m_isInitialized && param1.length > 0)
            {
                this.m_overlaySurveyUrl = param1;
                this.m_delay = Global.gameSettings().getInt("surveyOverlayDelay", 30000);
                this.m_lastActiveTime = GlobalEngine.getTimer();
                this.m_intervalChecker = new Timer(1000);
                this.m_intervalChecker.addEventListener(TimerEvent.TIMER, this.intervalCheckerTimerHandler);
                this.m_intervalChecker.start();
                this.m_isInitialized = true;
            }
            return;
        }//end

        public void  displaySurveyOverlay (boolean param1 =false )
        {
            if (param1 || this.m_overlaySurveyUrl && ExternalInterface.available)
            {
                ExternalInterface.call("theFarmOverlay.showSurveyOverlay", this.m_overlaySurveyUrl);
            }
            return;
        }//end

        private void  intervalCheckerTimerHandler (TimerEvent event )
        {
            if (this.checkIfMouseMoved())
            {
                this.m_lastActiveTime = GlobalEngine.getTimer();
            }
            else if (this.m_lastActiveTime + this.m_delay < GlobalEngine.getTimer())
            {
                this.displaySurveyOverlay();
                this.m_intervalChecker.stop();
            }
            return;
        }//end

        public static SurveyManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new SurveyManager;
            }
            return m_instance;
        }//end

    }


