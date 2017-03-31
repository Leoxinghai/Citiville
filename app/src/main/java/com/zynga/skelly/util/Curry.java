package com.zynga.skelly.util;

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

    public class Curry
    {

        public  Curry ()
        {
            return;
        }//end

        public static Function  curry (Function param1 ,...args )
        {
            //args = new activation;
            Function closure =param1 ;
            args = args;
            return Function  (...args0 )
            {
                return closure.apply(null, args.concat(args0));
            }//end
            ;
        }//end

        public static Function  continuation (Function param1 ,Function param2 ,double param3 )
        {
            closure1 = param1;
            closure2 = param2;
            numArgs1 = param3;
            return void  (...args )
            {
                args = args.splice(0, numArgs1);
                closure1.apply(null, args);
                closure2.apply(null, args);
                //args = null;
                args = null;
                return;
            }//end
            ;
        }//end

    }


