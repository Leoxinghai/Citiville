package com.facebook.commands.photos;

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
    public class CreateAlbum extends FacebookCall
    {
        public String name ;
        public String uid ;
        public String visible ;
        public String location ;
        public String description ;
        public static Array SCHEMA = { "name","location","description","visible","uid" } ;
        public static String METHOD_NAME ="photos.createAlbum";

        public  CreateAlbum (String param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 =null )
        {
            super(METHOD_NAME, null);
            this.name = param1;
            this.location = param2;
            this.description = param3;
            this.visible = param4;
            this.uid = param5;
            return;
        }//end  

    }


