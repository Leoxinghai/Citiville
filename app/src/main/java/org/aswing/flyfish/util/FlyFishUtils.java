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

import org.aswing.flyfish.*;
    public class FlyFishUtils extends Object
    {

        public  FlyFishUtils ()
        {
            return;
        }//end

        public static String  upperFirstChar (String param1 )
        {
            _loc_2 = param1.charAt(0);
            _loc_2 = _loc_2.toUpperCase();
            param1 = param1.substr(1);
            return _loc_2 + param1;
        }//end

        public static void  setValue (Object param1 ,String param2 ,*)param3
        {
            String methodName ;
            o = param1;
            name = param2;
            v = param3;
            methodName = "set" + upperFirstChar(name);
            try
            {
                _loc_5 = o;
                _loc_5.o.get(methodName)(v);
            }
            catch (er:Error)
            {
                AGLog.error("Can not find method of \'" + methodName + "\' on " + o);
            }
            return;
        }//end

        public static Object getValue (Object param1 ,String param2 )
        {
            _loc_3 = upperFirstChar"get"+(param2);
            _loc_4 = param1;
            return _loc_4.param1.get(_loc_3)();
        }//end

        public static boolean  isValue (Object param1 ,String param2 )
        {
            _loc_3 = upperFirstChar"is"+(param2);
            _loc_4 = param1;
            return _loc_4.param1.get(_loc_3)();
        }//end

    }


