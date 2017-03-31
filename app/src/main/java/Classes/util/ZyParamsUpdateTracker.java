package Classes.util;

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

import Engine.Events.*;
import Engine.Managers.*;
import Transactions.*;
//import flash.events.*;
//import flash.utils.*;

    public class ZyParamsUpdateTracker
    {
        public static  int CHECK_DELAY_MS =60000;
        public static  int MAX_DELAY_MS =300000;
public static double m_lastTransactionTime ;
public static Timer m_timer ;

        public  ZyParamsUpdateTracker ()
        {
            return;
        }//end

        public static void  initialize ()
        {
            m_lastTransactionTime = GlobalEngine.getTimer();
            TransactionManager.getInstance().addEventListener(TransactionEvent.ADDED, onTransactionAdded);
            m_timer = new Timer(CHECK_DELAY_MS);
            m_timer.addEventListener(TimerEvent.TIMER, onTimerEvent);
            m_timer.start();
            return;
        }//end

        public static void  shutdown ()
        {
            m_timer.stop();
            m_timer.removeEventListener(TimerEvent.TIMER, onTimerEvent);
            m_timer = null;
            TransactionManager.getInstance().removeEventListener(TransactionEvent.ADDED, onTransactionAdded);
            return;
        }//end

        public static void  onTimerEvent (TimerEvent event )
        {
            _loc_2 = GlobalEngine.getTimer ()-m_lastTransactionTime ;
            if (_loc_2 > MAX_DELAY_MS)
            {
                updateZyParams();
            }
            return;
        }//end

        public static void  onTransactionAdded (TransactionEvent event )
        {
            m_lastTransactionTime = GlobalEngine.getTimer();
            return;
        }//end

        public static void  updateZyParams ()
        {
            GameTransactionManager.addTransaction(new TUpdateEnergy(), true);
            return;
        }//end


    }


