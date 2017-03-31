package Display.aswingui;

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
import Events.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;

    public class JCountdownText extends JText
    {
        private EventHandler countdownHandler ;
        private Date expirationTime ;
        private ITimeFormatter timeFormatter ;
        private static  int UPDATE_INTERVAL =1000;
        private static  Timer countdown =new Timer(UPDATE_INTERVAL );

        public  JCountdownText (Date param1 ,ITimeFormatter param2 )
        {
            this.timeFormatter = param2;
            this.expirationTime = param1;

            this.countdownHandler.source = countdown;
            if (!countdown.running)
            {
                countdown.start();
            }
            this.updateTime();
            param2.styleTime(this.getTextField());
            return;
        }//end  

        private void  updateTime (boolean param1 =false )
        {
            _loc_2 = this.expirationTime? (this.expirationTime.getTime() - GlobalEngine.serverTime) : (0);
            _loc_2 = Math.max(0, _loc_2);
            _loc_3 = this.timeFormatter.formatTime(_loc_2);
            this.setText(_loc_3);
            _loc_4 = this.getTextField();
            this.getTextField().text = _loc_3;
            if (!_loc_2 || param1 && !stage)
            {
                this.countdownHandler.destroy();
                this.countdownHandler = null;
                if (!countdown.hasEventListener(TimerEvent.TIMER))
                {
                    countdown.reset();
                }
                if (!_loc_2)
                {
                    dispatchEvent(new Event(Event.COMPLETE));
                }
            }
            return;
        }//end  

        private void  label_countdownHandler (TimerEvent event )
        {
            this.updateTime(true);
            return;
        }//end  

    }


