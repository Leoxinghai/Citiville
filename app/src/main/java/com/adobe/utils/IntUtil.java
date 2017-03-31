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

    public class IntUtil
    {
        private static String hexChars ="0123456789abcdef";

        public  IntUtil ()
        {
            return;
        }//end

        public static int  rol (int param1 ,int param2 )
        {
            return param1 << param2 | param1 >>> 32 - param2;
        }//end

        public static int  ror (int param1 ,int param2 )
        {
            _loc_3 = param232-;
            return param1 << _loc_3 | param1 >>> 32 - _loc_3;
        }//end

        public static String  toHex (int param1 ,boolean param2 =false )
        {
            int _loc_4 =0;
            int _loc_5 =0;
            String _loc_3 ="";
            if (param2)
            {
                _loc_4 = 0;
                while (_loc_4 < 4)
                {

                    _loc_3 = _loc_3 + (hexChars.charAt(param1 >> (3 - _loc_4) * 8 + 4 & 15) + hexChars.charAt(param1 >> (3 - _loc_4) * 8 & 15));
                    _loc_4++;
                }
            }
            else
            {
                _loc_5 = 0;
                while (_loc_5 < 4)
                {

                    _loc_3 = _loc_3 + (hexChars.charAt(param1 >> _loc_5 * 8 + 4 & 15) + hexChars.charAt(param1 >> _loc_5 * 8 & 15));
                    _loc_5++;
                }
            }
            return _loc_3;
        }//end

    }

