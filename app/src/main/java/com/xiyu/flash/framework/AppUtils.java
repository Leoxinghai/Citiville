package com.xiyu.flash.framework;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

    public class AppUtils {
        public static double  asNumber (String value ,int defaultValue){
            if (value == null)
            {
                return (defaultValue);
            };
            double num = 0.0;
            try {
            	num = Double.parseDouble(value);
            } catch(Exception ex) {
                return (defaultValue);
            };
            return (num);
        }
        public static boolean  asBoolean (String value ,boolean defaultValue){
            if ((((value == null)) || ((value.length() == 0))))
            {
                return (defaultValue);
            };
            if (value.toLowerCase() == "true")
            {
                return (true);
            };
            double num = 0.0;
            try {
            	num = Double.parseDouble(value);
            } catch(Exception ex) {
                return (false);
            };
            if (num > 0)
            {
                return (true);
            };
            return (false);
        }

    }


