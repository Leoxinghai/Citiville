package com.facebook.events;

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
import com.facebook.errors.*;
//import flash.events.*;

    public class FacebookEvent extends Event
    {
        public boolean success ;
        public FacebookError error ;
        public boolean hasPermission ;
        public FacebookData data ;
        public String permission ;
        public static  String PERMISSIONS_LOADED ="permissionsLoaded";
        public static  String LOGOUT ="logout";
        public static  String CONNECT ="connect";
        public static  String VERIFYING_SESSION ="verifyingSession";
        public static  String WAITING_FOR_LOGIN ="waitingForLogin";
        public static  String PERMISSION_CHANGE ="permissionChanged";
        public static  String PERMISSION_STATUS ="permissionStatus";
        public static  String LOGIN_SUCCESS ="loginSuccess";
        public static  String ERROR ="facebookEventError";
        public static  String PERMISSIONS_WINDOW_SHOW ="permissionsWindowShow";
        public static  String LOGIN_FAILURE ="loginFailure";
        public static  String LOGIN_WINDOW_SHOW ="loginWindoShow";
        public static  String COMPLETE ="complete";

        public  FacebookEvent (String param1 ,boolean param2 =false ,boolean param3 =false ,boolean param4 =false ,FacebookData param5 =null ,FacebookError param6 =null ,String param7 ="",boolean param8 =false )
        {
            this.success = param4;
            this.data = param5;
            this.error = param6;
            this.permission = param7;
            this.hasPermission = param8;
            super(param1, param2, param3);
            return;
        }//end  

         public String  toString ()
        {
            return formatToString("FacebookEvent", "type", "success", "data", "error");
        }//end  

         public Event  clone ()
        {
            return new FacebookEvent(type, bubbles, cancelable, this.success, this.data, this.error);
        }//end  

    }


