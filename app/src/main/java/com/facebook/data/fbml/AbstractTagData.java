package com.facebook.data.fbml;

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

    public class AbstractTagData
    {
        public String type ;
        public String description ;
        public String name ;
        public String header_fbml ;
        public AttributeCollection attributes ;
        public String is_public ;
        public String footer_fbml ;

        public  AbstractTagData (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ="",String param6 ="",AttributeCollection param7 =null )
        {
            this.name = param1;
            this.type = param4;
            this.description = param5;
            this.is_public = param6;
            this.header_fbml = param2;
            this.footer_fbml = param3;
            this.attributes = param7;
            return;
        }//end

    }


