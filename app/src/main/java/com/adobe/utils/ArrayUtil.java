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

    public class ArrayUtil
    {

        public  ArrayUtil ()
        {
            return;
        }//end

        public static boolean  arrayContainsValue (Array param1 ,Object param2 )
        {
            return param1.indexOf(param2) != -1;
        }//end

        public static void  removeValueFromArray (Array param1 ,Object param2 )
        {
            _loc_3 = param1.length ;
            _loc_4 = _loc_3;
            while (_loc_4 > -1)
            {

                if (param1.get(_loc_4) === param2)
                {
                    param1.splice(_loc_4, 1);
                }
                _loc_4 = _loc_4 - 1;
            }
            return;
        }//end

        public static Array  createUniqueCopy (Array param1 )
        {
            Object _loc_4 =null ;
            _loc_2 = new Array ();
            _loc_3 = param1.length ;
            int _loc_5 =0;
            while (_loc_5 < _loc_3)
            {

                _loc_4 = param1.get(_loc_5);
                if (arrayContainsValue(_loc_2, _loc_4))
                {
                }
                else
                {
                    _loc_2.push(_loc_4);
                }
                _loc_5 = _loc_5 + 1;
            }
            return _loc_2;
        }//end

        public static Array  copyArray (Array param1 )
        {
            return param1.slice();
        }//end

        public static boolean  arraysAreEqual (Array param1 ,Array param2 )
        {
            if (param1.length != param2.length())
            {
                return false;
            }
            _loc_3 = param1.length ;
            double _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                if (param1.get(_loc_4) !== param2.get(_loc_4))
                {
                    return false;
                }
                _loc_4 = _loc_4 + 1;
            }
            return true;
        }//end

    }

