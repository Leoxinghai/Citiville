package org.aswing.flyfish.event;

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
import org.aswing.flyfish.css.*;

    public class LazyLoadEvent extends Event
    {
        protected LazyLoadRequest _request ;
        public static  String LOAD_COMPLETE ="loadComplete";
        public static  String LOAD_ERROR ="loadError";
        public static  String LOAD_LIST_FINISH ="loadListFinish";

        public  LazyLoadEvent (String param1 ,LazyLoadRequest param2 )
        {
            super(param1, false, false);
            this._request = param2;
            return;
        }//end  

        public LazyLoadRequest  request ()
        {
            return this._request;
        }//end  

         public Event  clone ()
        {
            return new LazyLoadEvent(type, this._request);
        }//end  

    }


