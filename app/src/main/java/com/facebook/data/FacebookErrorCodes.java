package com.facebook.data;

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

    public class FacebookErrorCodes
    {
        public static  double API_EC_VERSION =12;
        public static  double API_EC_PERMISSION_DENIED =10;
        public static  double API_EC_HOST_UP =7;
        public static  double API_EC_SERVICE =2;
        public static  double API_EC_RATE =9;
        public static  double API_EC_METHOD =3;
        public static  double API_EC_HOST_API =6;
        public static  double API_EC_SUCCESS =0;
        public static  double SERVER_ERROR =-1;
        public static  double API_EC_UNKNOWN =1;
        public static  double API_EC_DEPRECATED =11;
        public static  double API_EC_SECURE =8;
        public static  double API_EC_TOO_MANY_CALLS =4;
        public static  double API_EC_BAD_IP =5;

        public  FacebookErrorCodes ()
        {
            return;
        }//end

    }


