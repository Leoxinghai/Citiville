package com.facebook.data.events;

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

import com.facebook.data.*;
    public class EventData
    {
        public String eid ;
        public Date update_time ;
        public double nid ;
        public String pic ;
        public String name ;
        public String tagline ;
        public Date start_time ;
        public Date end_time ;
        public String event_subtype ;
        public String pic_small ;
        public String pic_big ;
        public String host ;
        public double creator ;
        public FacebookLocation venue ;
        public String location ;
        public String description ;
        public String event_type ;

        public  EventData ()
        {
            return;
        }//end

    }


