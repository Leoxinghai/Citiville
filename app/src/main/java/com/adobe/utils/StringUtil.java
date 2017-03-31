package com.adobe.utils;

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

    public class StringUtil
    {

        public  StringUtil ()
        {
            return;
        }//end

        public static boolean  stringsAreEqual (String param1 ,String param2 ,boolean param3 )
        {
            if (param3)
            {
                return param1 == param2;
            }
            return param1.toUpperCase() == param2.toUpperCase();
        }//end

        public static String  trim (String param1 )
        {
            return ltrim(rtrim(param1));
        }//end

        public static String  ltrim (String param1 )
        {
            _loc_2 = param1.length ;
            double _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                if (param1.charCodeAt(_loc_3) > 32)
                {
                    return param1.substring(_loc_3);
                }
                _loc_3 = _loc_3 + 1;
            }
            return "";
        }//end

        public static String  rtrim (String param1 )
        {
            _loc_2 = param1.length ;
            _loc_3 = _loc_2;
            while (_loc_3 > 0)
            {

                if (param1.charCodeAt((_loc_3 - 1)) > 32)
                {
                    return param1.substring(0, _loc_3);
                }
                _loc_3 = _loc_3 - 1;
            }
            return "";
        }//end

        public static boolean  beginsWith (String param1 ,String param2 )
        {
            return param2 == param1.substring(0, param2.length());
        }//end

        public static boolean  endsWith (String param1 ,String param2 )
        {
            return param2 == param1.substring(param1.length - param2.length());
        }//end

        public static String  remove (String param1 ,String param2 )
        {
            return replace(param1, param2, "");
        }//end

        public static String  replace (String param1 ,String param2 ,String param3 )
        {
            double _loc_9 =0;
            _loc_4 = new String ();
            boolean _loc_5 =false ;
            _loc_6 = param1.length ;
            _loc_7 = param2.length;
            double _loc_8 =0;
            while (_loc_8 < _loc_6)
            {

                if (param1.charAt(_loc_8) == param2.charAt(0))
                {
                    _loc_5 = true;
                    _loc_9 = 0;
                    while (_loc_9 < _loc_7)
                    {

                        if (param1.charAt(_loc_8 + _loc_9) != param2.charAt(_loc_9))
                        {
                            _loc_5 = false;
                            break;
                        }
                        _loc_9 = _loc_9 + 1;
                    }
                    if (_loc_5)
                    {
                        _loc_4 = _loc_4 + param3;
                        _loc_8 = _loc_8 + (_loc_7 - 1);
                        ;
                    }
                }
                _loc_4 = _loc_4 + param1.charAt(_loc_8);
                _loc_8 = _loc_8 + 1;
            }
            return _loc_4;
        }//end

    }

