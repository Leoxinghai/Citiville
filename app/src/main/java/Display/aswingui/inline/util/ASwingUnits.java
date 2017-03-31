package Display.aswingui.inline.util;

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

import org.aswing.*;
import org.aswing.geom.*;

    public class ASwingUnits
    {
        private static  IntDimension s_dimensions =new IntDimension ();
        private static  IntPoint s_point =new IntPoint ();
        private static  Insets s_inset =new Insets ();

        public  ASwingUnits ()
        {
            return;
        }//end

        public static IntDimension  dimensions (int param1 ,int param2 )
        {
            s_dimensions.width = param1;
            s_dimensions.height = param2;
            return s_dimensions;
        }//end

        public static IntPoint  point (int param1 ,int param2 )
        {
            s_point.x = param1;
            s_point.y = param2;
            return s_point;
        }//end

        public static Insets  inset (int param1 ,int param2 ,int param3 ,int param4 )
        {
            s_inset.top = param1;
            s_inset.left = param2;
            s_inset.bottom = param3;
            s_inset.right = param4;
            return s_inset;
        }//end

    }


