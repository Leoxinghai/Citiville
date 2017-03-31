package com.facebook.utils;

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

//import flash.system.*;
    public class PlayerUtils
    {
public static Object versionObj ;

        public  PlayerUtils ()
        {
            return;
        }//end

        public static double  internalBuildNumber ()
        {
            return parseVersionString().internalBuildNumber;
        }//end

        public static String  platform ()
        {
            return parseVersionString().platform;
        }//end

        public static double  buildNumber ()
        {
            return parseVersionString().buildNumber;
        }//end

        public static double  minorVersion ()
        {
            return parseVersionString().minorVersion;
        }//end

        public static Object  parseVersionString ()
        {
            if (versionObj != null)
            {
                return versionObj;
            }
            _loc_1 = Capabilities.version;
            versionObj = {};
            _loc_2 = _loc_1.split(" ");
            versionObj.platform = _loc_2.get(0);
            _loc_2.shift();
            _loc_2 = _loc_2.get(0).split(",");
            versionObj.majorVersion = Number(_loc_2.get(0));
            versionObj.minorVersion = Number(_loc_2.get(1));
            versionObj.buildNumber = Number(_loc_2.get(2));
            versionObj.internalBuildNumber = Number(_loc_2.get(3));
            return versionObj;
        }//end

        public static double  majorVersion ()
        {
            return parseVersionString().majorVersion;
        }//end

    }


