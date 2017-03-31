package com.facebook.commands.photos;

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

import com.facebook.data.photos.*;
import com.facebook.net.*;

    public class UploadPhoto extends FacebookCall implements IUploadPhoto
    {
        public String aid ;
        protected Object _data ;
        public String uid ;
        public String caption ;
        public FacebookPhoto uploadedPhoto ;
        protected String _uploadType ="png";
        protected int _uploadQuality =80;
        public static  Array SCHEMA =.get( "data","aid","caption","uid") ;
        public static  String METHOD_NAME ="photos.upload";

        public  UploadPhoto (Object param1 ,String param2 ,String param3 =null ,String param4 =null )
        {
            super(METHOD_NAME);
            this.data = param1;
            this.aid = param2;
            this.caption = param3;
            this.uid = param4;
            return;
        }//end

        public String  uploadType ()
        {
            return this._uploadType;
        }//end

        public void  data (Object param1 )
        {
            this._data = param1;
            return;
        }//end

        public Object  data ()
        {
            return this._data;
        }//end

        public void  uploadType (String param1 )
        {
            this._uploadType = param1;
            return;
        }//end

        public void  uploadQuality (int param1 )
        {
            this._uploadQuality = param1;
            return;
        }//end

        public int  uploadQuality ()
        {
            return this._uploadQuality;
        }//end

    }


