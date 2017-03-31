package com.facebook.errors;

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
//import flash.events.*;
//import flash.net.*;

    public class FacebookError extends FacebookData
    {
        public String errorMsg ;
        public String reason ;
        public Error error ;
        public URLVariables requestArgs ;
        public double errorCode ;
        public ErrorEvent errorEvent ;

        public  FacebookError ()
        {
            return;
        }//end  

    }


