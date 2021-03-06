﻿package Engine.Events;

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

//import flash.events.*;
    public class LoaderEvent extends Event
    {
        private Object m_eventData =null ;
        public static  String LOAD_QUEUE_EMPTY ="loadqueueempty";
        public static  String LOADED ="loaded";
        public static  String ALL_HIGH_PRIORITY_LOADED ="high_priority_loaded";
        public static  String ALL_TRACKED_LOADED ="tracked_loaded";

        public void  LoaderEvent (String param1 )
        {
            super(param1);
            return;
        }//end  

        public Object  eventData ()
        {
            return this.m_eventData;
        }//end  

        public void  eventData (Object param1 )
        {
            this.m_eventData = param1;
            return;
        }//end  

    }



