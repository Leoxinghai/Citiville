package com.xiyu.flash.games.pvz.renderables;
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

import com.xiyu.flash.framework.render.Renderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.utils.Utils;

    public class StringRenderable implements Renderable {

        public static  String JUSTIFY_CENTER ="center";
        public static  String JUSTIFY_LEFT ="left";
        public static  String JUSTIFY_RIGHT ="right";

        private ImageInst mTextImage ;
        private int mImageRed =1;

        public void  dead (boolean value ){
            this.mDead = value;
        }

        private Rectangle mBounds ;

        public void  draw (Graphics2D g ){
            if (this.mFont == null)
            {
                return;
            };
            if (this.mTextImage == null)
            {
                return;
            };
            g.blitImage(this.mTextImage, this.mTextBounds.x, this.mTextBounds.y);
        }

        private Rectangle mTextBounds ;
        private int mImageAlpha =1;
        private Array mLines ;

        public int  getDepth (){
            return (this.mDepth);
        }

        private int mDepth ;

        public void  update (){
        }

        public void  text (String value ){
            this.mText = value;
            this.redrawText();
        }

        private FontInst mFont ;

        public void  setColor (int a ,int r ,int g ,int b ){
            this.mImageAlpha = a;
            this.mImageRed = r;
            this.mImageGreen = g;
            this.mImageBlue = b;
            if (this.mTextImage != null)
            {
                this.mTextImage.setColor(a, r, g, b);
                this.mTextImage.useColor = true;
            };
        }

        private String mJustify ="center";

        public void  font (FontInst value ){
            this.mFont = value;
            this.redrawText();
        }

        private void  redrawText (){
            int justification =Utils.JUSTIFY_CENTER ;
            if (this.mJustify == JUSTIFY_LEFT)
            {
                justification = Utils.JUSTIFY_LEFT;
            }
            else
            {
                if (this.mJustify == JUSTIFY_RIGHT)
                {
                    justification = Utils.JUSTIFY_RIGHT;
                };
            };
            this.mTextImage = Utils.createStringImage(this.mText, this.mFont, this.mBounds, justification);
            if (this.mTextImage == null)
            {
                return;
            };
            this.mTextBounds.x = 0;
            this.mTextBounds.y = 0;
            this.mTextBounds.width = this.mTextImage.width();
            this.mTextBounds.height = this.mTextImage.height();
            Utils.align(this.mTextBounds, this.mBounds, justification, Utils.ALIGN_CENTER);
        }
        public boolean  getIsDisposable (){
            return (this.mDead);
        }

        private int mImageBlue =1;

        public void  justification (String value ){
            this.mJustify = value;
            this.redrawText();
        }
        public void  x (int value ){
            this.mBounds.x = value;
        }

        private boolean mDead ;

        public void  y (int value ){
            this.mBounds.y = value;
        }

        private int mImageGreen =1;

        public void  setBounds (int x ,int y ,int width ,int height ){
            this.mBounds.x = x;
            this.mBounds.y = y;
            this.mBounds.width = width;
            this.mBounds.height = height;
            this.redrawText();
        }
        public boolean  getIsVisible (){
            return (true);
        }

        private String mText ;

        public String  toString (){
            return (("String@" + this.mDepth));
        }

        public  StringRenderable (int depth){
            this.mDepth = depth;
            this.mBounds = new Rectangle();
            this.mTextBounds = new Rectangle();
            this.mDead = false;
            this.mLines = new Array();
        }
    }


