package com.facebook.commands.friends;

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
    public class GetFriends extends FacebookCall
    {
        public String uid ;
        public String flid ;
        public static  Array SCHEMA = {"flid","uid"};
        public static  String METHOD_NAME ="friends.get";

        public  GetFriends (String param1 ,String param2 )
        {
            super(METHOD_NAME, null);
            this.flid = param1;
            this.uid = param2;
            return;
        }//end

    }


