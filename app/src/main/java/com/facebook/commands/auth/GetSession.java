package com.facebook.commands.auth;

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

import com.facebook.net.*;
    public class GetSession extends FacebookCall
    {
        public String auth_token ;
        public static  Array SCHEMA;
        public static  String METHOD_NAME ="auth.getSession";

        public  GetSession (String param1 )
        {
            super(METHOD_NAME,null);
            String[] temp = {"auth_token"};
            SCHEMA = new Array(temp);
            this.auth_token = param1;
            return;
        }//end  

    }


