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

    public class Delegate
    {

        public  Delegate ()
        {
            return;
        }//end

        public static Function  create (Object param1 ,Function param2 ,...args )
        {
            //args = new activation;
            target = param1;
            Function handler =param2 ;
            rest = args;
            Function delegate =function(...args2 )Function
            {
                args2 = args2.concat(rest);
                return handler.apply(target, args2);
            }//end
            ;
            return delegate;
        }//end

    }



