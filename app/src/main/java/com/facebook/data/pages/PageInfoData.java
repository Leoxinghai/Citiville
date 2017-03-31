package com.facebook.data.pages;

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

    public class PageInfoData extends EventDispatcher
    {
        public String starring ;
        public String genre ;
        public String season ;
        public String bio ;
        public String name ;
        public String record_label ;
        public String band_members ;
        public String pic_small ;
        public String schedule ;
        public String website ;
        public String influences ;
        public String founded ;
        public String hours ;
        public String directed_by ;
        public FacebookLocation location ;
        public String mission ;
        public String type ;
        public String awards ;
        public String pic_large ;
        public String pic_big ;
        public String network ;
        public double page_id ;
        public String studio ;
        public String release_date ;
        public String products ;
        public String hometown ;
        public String produced_by ;
        public String pic_square ;
        public String company_overview ;
        public String plot_outline ;
        public boolean has_added_app ;
        public String written_by ;

        public  PageInfoData ()
        {
            return;
        }//end  

    }


