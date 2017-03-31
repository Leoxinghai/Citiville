package Engine.Classes;

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

    public class BaseIdleTask
    {
        public static  double DEFAULT_QUEUE_COOLDOWN_MILLISECONDS =1000;
        public static  double DEFAULT_MOUSE_MILLISECONDS =5000;
        public static  double DEFAULT_ACTION_QUEUE_MILLISECONDS =5000;
        public static  double DEFAULT_POPUP_QUEUE_MILLISECONDS =5000;

        public  BaseIdleTask ()
        {
            return;
        }//end

        public boolean  requireMouseIdle ()
        {
            return true;
        }//end

        public double  minMouseIdleMilliseconds ()
        {
            return DEFAULT_MOUSE_MILLISECONDS;
        }//end

        public boolean  requireActionQueueIdle ()
        {
            return true;
        }//end

        public double  minActionIdleMilliseconds ()
        {
            return DEFAULT_ACTION_QUEUE_MILLISECONDS;
        }//end

        public boolean  requirePopupQueueEmpty ()
        {
            return true;
        }//end

        public double  minPopupIdleMilliseconds ()
        {
            return DEFAULT_POPUP_QUEUE_MILLISECONDS;
        }//end

        public double  queueCoolDownMilliseconds ()
        {
            return DEFAULT_QUEUE_COOLDOWN_MILLISECONDS;
        }//end

        public boolean  singleton ()
        {
            return true;
        }//end

        public boolean  singletonClobber ()
        {
            return false;
        }//end

        public boolean  shouldAdd ()
        {
            return true;
        }//end

        public boolean  canFire ()
        {
            return true;
        }//end

        public void  fire ()
        {
            throw new Error("BaseIdleTask fire() must be overwritten!");
        }//end

        final public void  submit ()
        {
            IdleTaskQueue.getInstance().addTask(this);
            return;
        }//end

    }



