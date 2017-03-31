package com.facebook.data.application;

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
    public class GetPublicInfoData extends FacebookData
    {
        public double monthly_active_users ;
        public double weekly_active_users ;
        public String company_name ;
        public String logo_url ;
        public String canvas_name ;
        public String display_name ;
        public String icon_url ;
        public String developers ;
        public double daily_active_users ;
        public String app_id ;
        public String api_key ;
        public String description ;

        public  GetPublicInfoData ()
        {
            return;
        }//end  

    }


