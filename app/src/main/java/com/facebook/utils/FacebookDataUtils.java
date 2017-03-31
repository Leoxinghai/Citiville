package com.facebook.utils;

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

import com.adobe.serialization.json.*;
    public class FacebookDataUtils
    {

        public  FacebookDataUtils ()
        {
            return;
        }//end

        public static String  toJSONValuesArray (Array param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            Array _loc_2 =new Array();
            _loc_3 = param1.length ;
            double _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_2.push(com.adobe.serialization.json.JSON.encode(param1.get(_loc_4)));
                _loc_4 = _loc_4 + 1;
            }
            return _loc_2.join(",");
        }//end

        public static Date  formatDate (String param1 )
        {
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            if (param1 == "" || param1 == null)
            {
                return null;
            }
            Date _loc_2 =new Date ();
            _loc_3 = param1.split(" ");
            if (_loc_3.length == 2)
            {
                _loc_4 = _loc_3.get(0).split("-");
                _loc_5 = _loc_3.get(1).split(":");
                _loc_2.setFullYear(_loc_4.get(0));
                _loc_2.setMonth((_loc_4.get(1) - 1));
                _loc_2.setDate(_loc_4.get(2));
                _loc_2.setHours(_loc_5.get(0));
                _loc_2.setMinutes(_loc_5.get(1));
                _loc_2.setSeconds(_loc_5.get(2));
            }
            else
            {
                _loc_2.setTime(parseInt(param1) * 1000);
            }
            return _loc_2;
        }//end

        public static String  facebookCollectionToJSONArray (FacebookArrayCollection param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            return com.adobe.serialization.json.JSON.encode(param1.toArray());
        }//end

        public static String  toDateString (Date param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            param1.setDate((param1.date + 1));
            return param1 == null ? (null) : (param1.getTime().toString().slice(0, 10));
        }//end

        public static String  supplantString (String param1 ,Object param2 )
        {
            String _loc_4 =null ;
            _loc_3 = param1;
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_4 = param2.get(i0);

                _loc_3 = _loc_3.replace(new RegExp("\\{" + _loc_4 + "\\}", "g"), param2.get(_loc_4));
            }
            return _loc_3;
        }//end

        public static String  toArrayString (Array param1 )
        {
            return param1 == null ? (null) : (param1.join(","));
        }//end

    }


