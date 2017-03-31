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

import Engine.Classes.*;
//import flash.display.*;
//import flash.events.*;

    public class AutoAnimatedBitmap extends AnimatedBitmap
    {
        protected double curTime ;

        public  AutoAnimatedBitmap (BitmapData param1 ,int param2 ,int param3 ,int param4 ,double param5 )
        {
            super(param1, param2, param3, param4, param5);
            this.curTime = GlobalEngine.getTimer();
            this.addEventListener(Event.ENTER_FRAME, this.updateMe, false, 0, true);
            return;
        }//end

         public void  play ()
        {
            super.play();
            return;
        }//end

        public void  updateMe (Event event )
        {
            _loc_2 = GlobalEngine.getTimer ();
            _loc_3 = _loc_2-this.curTime ;
            this.curTime = _loc_2;
            super.onUpdate(_loc_3 / 1000);
            return;
        }//end

         public void  stop ()
        {
            super.stop();
            return;
        }//end

         public void  reset ()
        {
            super.reset();
            return;
        }//end

        public void  cleanUp ()
        {
            this.removeEventListener(Event.ENTER_FRAME, this.updateMe);
            return;
        }//end

    }



