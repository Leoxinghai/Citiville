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

    public class State
    {
        public static  int FULL_INTERRUPT =1;
        public static  int NORMAL_INTERRUPT =2;
        public static  int NO_INTERRUPT =3;

        public  State ()
        {
            return;
        }//end

        public void  added ()
        {
            return;
        }//end

        public void  removed ()
        {
            return;
        }//end

        public void  enter ()
        {
            return;
        }//end

        public void  exit ()
        {
            return;
        }//end

        public void  reenter ()
        {
            return;
        }//end

        public void  update (double param1 )
        {
            return;
        }//end

        public int  getInterrupt ()
        {
            return NORMAL_INTERRUPT;
        }//end

        public boolean  allowCancelQueueAndMove ()
        {
            return false;
        }//end

    }



