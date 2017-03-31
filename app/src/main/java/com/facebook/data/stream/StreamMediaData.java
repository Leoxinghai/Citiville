package com.facebook.data.stream;

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

    public class StreamMediaData
    {
        public String src ;
        public MusicMedia music ;
        public String alt ;
        public String href ;
        public FlashMedia flash ;
        public PhotoMedia photo ;
        public String type ;
        public VideoMedia video ;

        public  StreamMediaData ()
        {
            return;
        }//end

        public String  toString ()
        {
            return .get("type: " + this.type, "href: " + this.href, "src: " + this.src, "alt: " + this.alt, "photo: " + this.photo, "video: " + this.video, "flash: " + this.flash).join(": ");
        }//end

    }


