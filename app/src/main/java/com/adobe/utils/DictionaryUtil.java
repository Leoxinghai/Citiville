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

//import flash.utils.*;
    public class DictionaryUtil
    {

        public  DictionaryUtil ()
        {
            return;
        }//end

        public static Array  getKeys (Dictionary param1 )
        {
            Object _loc_3 =null ;
            _loc_2 = new Array ();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

        public static Array  getValues (Dictionary param1 )
        {
            Object _loc_3 =null ;
            _loc_2 = new Array ();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_2.push(_loc_3);
            }
            return _loc_2;
        }//end

    }

