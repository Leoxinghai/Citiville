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
import com.facebook.utils.*;

    public class GetAlbums extends FacebookCall
    {
        public String uid ;
        public Array aids ;
        public static Array SCHEMA =.get( "uid","aids") ;
        public static String METHOD_NAME ="photos.getAlbums";

        public  GetAlbums (String param1 ="",Array param2 )
        {
            super(METHOD_NAME);
            this.uid = param1;
            this.aids = param2;
            return;
        }//end

    }


