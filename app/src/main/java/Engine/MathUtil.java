package Engine;

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

//import flash.geom.*;
    public class MathUtil
    {

        public  MathUtil ()
        {
            return;
        }//end

        public static double  clamp (double param1 ,double param2 ,double param3 )
        {
            return Math.min(Math.max(param2, param1), param3);
        }//end

        public static double  wrap (double param1 ,double param2 ,double param3 =0)
        {
            _loc_4 = param2-param3 ;
            while (param1 >= param2)
            {

                param1 = param1 - _loc_4;
            }
            while (param1 < param3)
            {

                param1 = param1 + _loc_4;
            }
            return param1;
        }//end

        public static double  interpolate (double param1 ,double param2 ,double param3 )
        {
            return (1 - param1) * param2 + param1 * param3;
        }//end

        public static int  random (int param1 ,int param2)
        {
            return Math.floor(Math.random() * (param1 - param2)) + param2;
        }//end

        public static int  randomIncl (int param1 ,int param2 )
        {
            return Math.floor(Math.random() * (param1 - param2 + 1)) + param2;
        }//end

        public static int  randomEpsilon (int param1 ,int param2 )
        {
            return random(param1 + param2, param1 - param2);
        }//end

        public static int  randomIndex (Array param1 )
        {
            return random(param1.length());
        }//end

        public static Object randomElement (Array param1 )
        {
            return param1.length == 0 ? (null) : (param1.get(random(param1.length())));
        }//end

        public static int  randomIndexWeighed (Array param1 )
        {
            double _loc_5 =0;
            double _loc_2 =0;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = _loc_2 + param1.get(_loc_3);
                _loc_3++;
            }
            _loc_4 = Math.random ()*_loc_2 ;
            _loc_3 = 0;
            while (_loc_3 < param1.length())
            {

                _loc_5 = param1.get(_loc_3);
                if (_loc_4 < _loc_5)
                {
                    return _loc_3;
                }
                _loc_4 = _loc_4 - _loc_5;
                _loc_3++;
            }
            return -1;
        }//end

        public static double  randomWobble (double param1 ,double param2 )
        {
            _loc_3 = param2Object param1 ;
            return param1 + (2 * Math.random() - 1) * _loc_3;
        }//end

        public static boolean  inEpsilonRange (double param1 ,double param2 ,double param3 )
        {
            return Math.abs(param1 - param2) < param3;
        }//end

        public static double  distance (Point param1 ,Point param2 ,int param3 =2)
        {
            _loc_4 = param1.x -param2.x ;
            _loc_5 = param1.y -param2.y ;
            if (param3 == 2)
            {
                return Math.sqrt(_loc_4 * _loc_4 + _loc_5 * _loc_5);
            }
            if (param3 == 1)
            {
                return Math.abs(_loc_4) + Math.abs(_loc_5);
            }
            if (param3 > 0)
            {
                return Math.pow(Math.pow(Math.abs(_loc_4), param3) + Math.pow(Math.abs(_loc_5), param3), 1 / param3);
            }
            return _loc_4 > _loc_5 ? (_loc_4) : (_loc_5);
        }//end

    }



