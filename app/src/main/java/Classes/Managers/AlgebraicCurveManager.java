package Classes.Managers;

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

    public class AlgebraicCurveManager
    {

        public  AlgebraicCurveManager ()
        {
            return;
        }//end

        public static double  yEqualsX (Object param1)
        {
            return Number(parseFloat(param1));
        }//end

        public static double  BaseCoinDooberValue (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = parseFloat(param1);
            if (_loc_2)
            {
                _loc_3 = 5 + Math.max(Math.ceil(_loc_2 / 5), 0) * 5;
                if (_loc_2 == 54)
                {
                    _loc_3 = _loc_3 + 20;
                }
                return _loc_3;
            }
            return 0;
        }//end

        public static double  grant3CoinDoobers (String param1 )
        {
            _loc_2 = parseFloat(param1);
            if (_loc_2 >= 1 && _loc_2 <= 15)
            {
                return 100;
            }
            return 0;
        }//end

        public static double  grant4CoinDoobers (String param1 )
        {
            _loc_2 = parseFloat(param1);
            if (_loc_2 >= 16 && _loc_2 <= 45)
            {
                return 100;
            }
            return 0;
        }//end

        public static double  grant5CoinDoobers (String param1 )
        {
            _loc_2 = parseFloat(param1);
            if (_loc_2 >= 46 && _loc_2 <= 54)
            {
                return 100;
            }
            return 0;
        }//end

        public static double  EnergyDoober1 (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = parseFloat(param1);
            if (_loc_2)
            {
                _loc_3 = 0.14 + Math.max(Math.ceil(_loc_2 / 10), 0) * 0.02;
                if (_loc_2 == 54)
                {
                    _loc_3 = _loc_3 + 0.04;
                }
                return _loc_3 * 100;
            }
            return 0;
        }//end

        public static double  EnergyDoober2 (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = parseFloat(param1);
            if (_loc_2)
            {
                _loc_3 = 0.04 + Math.max(Math.ceil(_loc_2 / 5), 0) * 0.01;
                if (_loc_2 == 54)
                {
                    _loc_3 = _loc_3 + 0.03;
                }
                return _loc_3 * 100;
            }
            return 0;
        }//end

        public static double  EnergyDoober3 (String param1 )
        {
            double _loc_3 =0;
            _loc_2 = parseFloat(param1);
            if (_loc_2)
            {
                _loc_3 = Math.max(Math.ceil(-0.0005 + Math.max(Math.ceil(_loc_2 / 3), 0) * 0.005), 2);
                if (_loc_2 == 54)
                {
                    _loc_3 = _loc_3 + 0.01;
                }
                return _loc_3;
            }
            return 0;
        }//end

    }



