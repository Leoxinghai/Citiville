package com.xinghai.chat;

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

import Classes.util.*;
import Engine.Init.*;
import Transactions.*;
//import flash.events.*;
import Init.CompleteMonitor;

    public class ToggleCity extends InitializationAction
    {
        public static  String INIT_ID ="ToggleCity";
        public static String cityName ="red";

        public  ToggleCity ()
        {
            super(INIT_ID);
            return;
        }//end  

         public void  execute ()
        {
            CompleteMonitor _loc_1 =null ;
            TInitUser _loc_2 =null ;
            if (GlobalEngine.socialNetwork)
            {
                _loc_1 = new CompleteMonitor();
                _loc_2 = new TInitUser(cityName);
                _loc_1.addDispatcher(_loc_2);
                _loc_1.addEventListener(Event.COMPLETE, this.onTransactionsComplete);
                GameTransactionManager.addTransaction(_loc_2, true, true);
                GlobalEngine.zaspManager.trackTimingStart("TINIT_USER");
            }
            if(cityName == "red") {
            	cityName = "blue";
            } else {
            	cityName = "red";
            }
            return;
        }//end  

        protected void  onTransactionsComplete (Event event )
        {
            dispatchEvent(new Event(Event.COMPLETE));
            GlobalEngine.zaspManager.trackTimingStop("TINIT_USER");
            return;
        }//end  

    }


