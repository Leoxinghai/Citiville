package com.facebook.data.photos;

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

    public class AlbumData
    {
        public double size ;
        public String cover_pid ;
        public Date modified ;
        public Date modified_major ;
        public String name ;
        public String edit_link ;
        public String aid ;
        public String type ;
        public Date created ;
        public String visible ;
        public String owner ;
        public String location ;
        public String link ;
        public String description ;

        public  AlbumData ()
        {
            return;
        }//end

    }


