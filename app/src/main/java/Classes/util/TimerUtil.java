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

//import flash.events.*;
//import flash.utils.*;

    public class TimerUtil
    {

        public  TimerUtil ()
        {
            return;
        }//end

        public static Timer  callLater (Function param1 ,int param2 )
        {
            Timer timer ;
            thunk = param1;
            delayMs = param2;
            timer = new Timer(delayMs);
            timer .addEventListener (TimerEvent .TIMER ,void  (TimerEvent event )
            {
                timer.removeEventListener(TimerEvent.TIMER, arguments.callee);
                timer.stop();
                timer = null;
                thunk();
                return;
            }//end
            );
            timer.start();
            return timer;
        }//end

        public static Timer  callHandlerLater (Function param1 ,int param2 )
        {
            Timer timer ;
            thunk = param1;
            delayMs = param2;
            timer = new Timer(delayMs);
            timer .addEventListener (TimerEvent .TIMER ,void  (TimerEvent event )
            {
                timer.removeEventListener(TimerEvent.TIMER, arguments.callee);
                timer.stop();
                timer = null;
                thunk(event);
                return;
            }//end
            );
            timer.start();
            return timer;
        }//end

    }



