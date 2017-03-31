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

//import flash.geom.Point;
import com.xiyu.flash.framework.resources.images.ImageInst;

    public class FontLayer {

        public int mPointSize ;
        public int mColorMult ;
        public int mColorAdd ;
        public String mLayerName ;
        public int mBaseOrder ;
        public int mMinPointSize ;
        public int mDefaultHeight ;
        public int mSpacing ;
        public Point mOffset ;

        public CharData  getCharData (int charCode ){
        	CharData data=(CharData)this.mCharData.elementAt(charCode);
            if (data == null)
            {
                data = new CharData();
                data.mCharCode = charCode;
                this.mCharData.add(charCode,data);
            };
            return (data);
        }

        public int mAscent ;
        public String mImageId ;
        public int mAscentPadding ;
        public Array mCharData ;
        public ImageInst mImage ;
        public int mLineSpacingOffset ;
        public int mMaxPointSize ;
        public int mHeight ;

        public int height (){
            if (this.mHeight == 0)
            {
                this.mHeight = this.mImage.height();
            };
            return (this.mHeight);
        }

        public  FontLayer (){
            this.mCharData = new Array();
            this.mOffset = new Point();
            this.mSpacing = 0;
            this.mPointSize = 0;
            this.mAscent = 0;
            this.mAscentPadding = 0;
            this.mMinPointSize = -1;
            this.mMaxPointSize = -1;
            this.mHeight = 0;
            this.mDefaultHeight = 0;
            this.mColorMult = 0xFFFFFFFF;
            this.mColorAdd = 0;
            this.mLineSpacingOffset = 0;
            this.mBaseOrder = 0;
        }
    }


