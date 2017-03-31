package org.aswing.flyfish.util;

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

    public class MathUtils extends Object
    {

        public  MathUtils ()
        {
            return;
        }//end

        public static int  parseInteger (String param1 ,int param2)
        {
            _loc_3 = parseInt(param1 ,param2 );
            if (isNaN(_loc_3))
            {
                return 0;
            }
            return _loc_3;
        }//end

        public static double  parseNumber (String param1 )
        {
            _loc_2 = parseFloat(param1);
            if (isNaN(_loc_2))
            {
                return 0;
            }
            return _loc_2;
        }//end

    }


