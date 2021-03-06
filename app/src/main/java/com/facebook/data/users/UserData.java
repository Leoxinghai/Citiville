package com.facebook.data.users;

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

    public class UserData
    {
        public AffiliationCollection affiations ;
        public String name ;
        public String uid ;
        public double timezone ;
        public String first_name ;
        public String last_name ;

        public  UserData ()
        {
            return;
        }//end

        public String  toString ()
        {
            return "[ UserData uid: " + this.uid + " affiation:" + this.affiations + " first_name:" + this.first_name + " last_name:" + this.last_name + " name:" + this.name + " timezone: " + this.timezone + "]";
        }//end

    }


