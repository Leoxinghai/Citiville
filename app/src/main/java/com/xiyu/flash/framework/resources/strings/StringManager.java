package com.xiyu.flash.framework.resources.strings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

//import flash.events.IEventDispatcher;
//import flash.events.Event;
import com.xiyu.flash.framework.AppBase;
//import flash.events.IOErrorEvent;
//import flash.net.URLLoader;
//import flash.utils.Dictionary;
//import flash.events.EventDispatcher;
//import flash.net.URLRequest;

    public class StringManager {

        private AppBase mApp ;
        private Dictionary mStrings ;

        public String  translateString (String key ){
        	String aValue=(String)this.mStrings.elementAt(key);
            if (aValue == null)
            {
                return ((("'" + key) + "' string has not been loaded."));
            };
            return (aValue);
        }

        public  StringManager (AppBase app ){
            this.mApp = app;
            this.mStrings = new Dictionary();
        }
    }


