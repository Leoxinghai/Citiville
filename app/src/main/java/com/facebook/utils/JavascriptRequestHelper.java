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

//import flash.net.*;
//import flash.utils.*;

    public class JavascriptRequestHelper
    {

        public  JavascriptRequestHelper ()
        {
            return;
        }//end

        public static String  objectToString (Object param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3 + ": " + quote(param1.get(_loc_3)) + "");
            }
            return "{" + _loc_2.join(", ") + " }";
        }//end

        public static String  quote (String param1 )
        {
            _loc_2 = [/\\\\"\r\n]/g;
            return "\"" + param1.replace(_loc_2, "") + "\"";
        }//end

        public static String  formatParams (Array param1 )
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_2 =new Array();
            _loc_3 = param1.length ;
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = param1.get(_loc_4);
                _loc_6 = getQualifiedClassName(_loc_5);
                switch(_loc_6)
                {
                    case "Array":
                    {
                        _loc_5 = "[" + _loc_5.join(", ") + "]";
                        break;
                    }
                    case "Object":
                    {
                        _loc_5 = objectToString(_loc_5);
                        break;
                    }
                    case "String":
                    {
                    }
                    default:
                    {
                        _loc_5 = "\"" + _loc_5 + "\"";
                        break;
                        break;
                    }
                }
                _loc_2.push(_loc_5);
                _loc_4 = _loc_4 + 1;
            }
            return _loc_2.join(", ");
        }//end

        public static String  formatURLVariables (URLVariables param1 )
        {
            String _loc_5 =null ;
            Object _loc_2 ={method true ,sig ,api_key ,call_id };
            boolean _loc_3 =false ;
            Object _loc_4 ={};
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_5 = param1.get(i0);

                if (_loc_2.get(_loc_5))
                {
                    continue;
                }
                _loc_3 = true;
                _loc_4.put(_loc_5,  param1.get(_loc_5));
            }
            return _loc_3 ? (objectToString(_loc_4)) : ("null");
        }//end

    }


