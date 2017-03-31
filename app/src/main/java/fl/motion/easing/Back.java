package fl.motion.easing;

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

    public class Back
    {

        public  Back ()
        {
            return;
        }//end

        public static double  easeOut (double param1 ,double param2 ,double param3 ,double param4 ,double param5 =0)
        {
            if (!param5)
            {
                param5 = 1.70158;
            }
            _loc_6 = param1/param4 -1;
            param1 = param1 / param4 - 1;
            return param3 * (_loc_6 * param1 * ((param5 + 1) * param1 + param5) + 1) + param2;
        }//end

        public static double  easeIn (double param1 ,double param2 ,double param3 ,double param4 ,double param5 =0)
        {
            if (!param5)
            {
                param5 = 1.70158;
            }
            _loc_6 = param1/param4 ;
            param1 = param1 / param4;
            return param3 * _loc_6 * param1 * ((param5 + 1) * param1 - param5) + param2;
        }//end

        public static double  easeInOut (double param1 ,double param2 ,double param3 ,double param4 ,double param5 =0)
        {
            if (!param5)
            {
                param5 = 1.70158;
            }
            _loc_6 = param1/(param4 /2);
            param1 = param1 / (param4 / 2);
            if (_loc_6 < 1)
            {
                _loc_6 = param5 * 1.525;
                param5 = param5 * 1.525;
                return param3 / 2 * (param1 * param1 * ((_loc_6 + 1) * param1 - param5)) + param2;
            }
            _loc_6 = param1 - 2;
            param1 = param1 - 2;
            _loc_6 = param5 * 1.525;
            param5 = param5 * 1.525;
            return param3 / 2 * (_loc_6 * param1 * ((_loc_6 + 1) * param1 + param5) + 2) + param2;
        }//end

    }



