package com.xiyu.flash.framework.resources.fonts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

//import flash.utils.Dictionary;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.geom.Point;
//import flash.geom.Rectangle;

    public class CharData {

        public Dictionary mKerningOffsets ;
        public int mOrder ;
        public ImageInst mImage ;
        public Point mOffset ;
        public int mCharCode ;
        public int mWidth ;
        public Rectangle mImageRect ;

        public  CharData (){
            this.mCharCode = -1;
            this.mImageRect = new Rectangle();
            this.mOffset = new Point();
            this.mKerningOffsets = new Dictionary();
            this.mWidth = 0;
            this.mOrder = 0;
            this.mImage = null;
        }
    }


