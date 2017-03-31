package Engine.Managers;

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

import com.xiyu.logic.EventDispatcher;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import root.GlobalEngine;

//import flash.events.*;
//import flash.system.*;
//import flash.utils.*;

    public class ErrorManager extends EventDispatcher
    {
        private static  int REPORT_DELAY =10000;
        public static  int ERROR_UNKNOWN =0;
        public static  int ERROR_FAILED_TO_LOAD =1;
        public static  int ERROR_LOAD_TIMED_OUT =2;
        public static  int ERROR_LOAD_SECURITY_ERROR =3;
        public static  int ERROR_LOAD_HTTP_ERROR =4;
        public static  int ERROR_LOAD_IO_ERROR =5;
        public static  int ERROR_REMOTEOBJECT_FAULT =6;
        public static Array m_errors =new Array ();
        public static Timer m_timer =new Timer(REPORT_DELAY );
        public static ErrorManager m_instance ;

        public  ErrorManager ()
        {
            m_timer.addEventListener(TimerEvent.TIMER, onReport, false, 0, true);
            m_timer.start();
            return;
        }//end

        public static ErrorManager  getInstance ()
        {
            if (m_instance == null)
            {
                m_instance = new ErrorManager();
            }
            return m_instance;
        }//end

        public static void  addError (String param1 ,int param2,Object param3 )
        {
            m_errors.push({message:param1, type:param2, version:Capabilities.version, fields:param3});
            GlobalEngine.log("ErrorManager", param1 + " (Type: " + param2 + ")");
            getInstance().dispatchEvent(new ErrorEvent(ErrorEvent.ERROR, false, false, param1));
            return;
        }//end

        private static void  onReport (TimerEvent event )
        {
            if (m_errors.length())
            {
                m_errors = new Array();
            }
            return;
        }//end

    }



