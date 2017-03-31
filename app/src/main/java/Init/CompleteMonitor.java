package Init;

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
    public class CompleteMonitor extends EventDispatcher
    {
        protected int dispatcherCount ;
        protected int numCompleted ;

        public  CompleteMonitor ()
        {
            int _loc_1 =0;
            this.numCompleted = 0;
            this.dispatcherCount = _loc_1;
            return;
        }//end  

        public void  addDispatcher (IEventDispatcher param1 )
        {
            (this.dispatcherCount + 1);
            param1.addEventListener(Event.COMPLETE, this.onComplete);
            return;
        }//end  

        protected void  onComplete (Event event )
        {
            (this.numCompleted + 1);
            if (this.numCompleted == this.dispatcherCount)
            {
                dispatchEvent(new Event(Event.COMPLETE));
            }
            return;
        }//end  

        public int  getDispatcherCount ()
        {
            return this.dispatcherCount;
        }//end  

    }



