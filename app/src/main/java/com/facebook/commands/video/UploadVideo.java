package com.facebook.commands.video;

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

import com.facebook.net.*;
    public class UploadVideo extends FacebookCall implements IUploadVideo
    {
        protected String _title ;
        protected String _ext ;
        protected Object _data ;
        protected String _description ;
        public static  Array SCHEMA =.get( "data","title","description") ;
        public static  String METHOD_NAME ="video.upload";

        public  UploadVideo (String param1 ,Object param2 ,String param3 =null ,String param4 =null )
        {
            super(METHOD_NAME);
            this.ext = param1;
            this.data = param2;
            this.title = param3;
            this.description = param4;
            return;
        }//end  

        public String  ext ()
        {
            return this._ext;
        }//end  

        public void  description (String param1 )
        {
            this._description = param1;
            return;
        }//end  

        public Object  data ()
        {
            return this._data;
        }//end  

        public void  title (String param1 )
        {
            this._title = param1;
            return;
        }//end  

        public void  ext (String param1 )
        {
            this._ext = param1;
            return;
        }//end  

        public void  data (Object param1 )
        {
            this._data = param1;
            return;
        }//end  

        public String  title ()
        {
            return this._title;
        }//end  

        public String  description ()
        {
            return this._description;
        }//end  

    }


