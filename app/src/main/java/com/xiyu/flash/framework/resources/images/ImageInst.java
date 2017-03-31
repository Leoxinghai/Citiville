package com.xiyu.flash.framework.resources.images;
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

import com.xiyu.util.*;
//import flash.geom.Point;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.ColorTransform;
//import flash.display.BitmapData;
//import flash.geom.Rectangle;

    public class ImageInst {

        private int mFrame ;
        public Point destPt ;
        private boolean isSplited = false;

        public void  x (int value ){
            this.destPt.x = value;
        }

        public int  green (){
            return (this.mColorTransform.greenMultiplier);
        }
        public Graphics2D  graphics (){
            if (this.mGraphics == null)
            {
                this.mGraphics = new Graphics2D(this.pixels());
            };
            return (this.mGraphics);
        }
        public int  width (){
            return (this.pixels().width);
        }

        public void  setColor (double a ,double r ,double g ,double b ){
            this.mColorTransform.alphaMultiplier = (int)a;
            this.mColorTransform.redMultiplier = (int)r;
            this.mColorTransform.greenMultiplier = (int)g;
            this.mColorTransform.blueMultiplier = (int)b;
        }

        public int  red (){
            return (this.mColorTransform.redMultiplier);
        }

        private ColorTransform mColorTransform ;
        private Graphics2D mGraphics ;
        public boolean doAdditiveBlend =false ;
        public boolean useColor =false ;

        public int  alpha (){
            return (this.mColorTransform.alphaMultiplier);
        }
        public int  blue (){
            return (this.mColorTransform.blueMultiplier);
        }
        public int  height (){
            return (this.pixels().height);
        }
        public Rectangle  srcRect (){
            BitmapData p ;
            if (this.mSrcRect == null)
            {
                p = this.pixels();
                this.mSrcRect = new Rectangle(0, 0, p.width, p.height);
            };
            return (this.mSrcRect);
        }

        public boolean doSmoothing =true ;

        public void  setFrame (int frame ,int col ,int row ){

        	int value = frame;
            this.mFrame = value;

        	if(col ==0 || row ==0) {
        		isSplited = true;
        		return;
        	}
        	if(!isSplited) {
        		this.mData.splitImageData(row, col);
        		isSplited = true;
        	}
        	/*
            int value =((frame +col )+(row *this.mData.cols ));
            int numFrames =this.mData.cels.length() ;
            value = (((value)<0) ? 0 : value);
            value = (((value)>=numFrames) ? (numFrames - 1) : value);
            */
            //this.mPixels=(BitmapData)this.mData.cels.elementAt(value);
            this.mSrcRect = null;
        }

        public BitmapData  pixels (){
            if (this.mPixels == null)
            {
                this.mPixels=(BitmapData)this.mData.cels.elementAt(0);
            };
            return (this.mPixels);
        }

        public void  y (int value ){
            this.destPt.y = value;
        }

        private ImageData mData ;

        public int  y (){
            return (this.destPt.y);
        }

        private BitmapData mPixels ;
        private Rectangle mSrcRect ;

        public void  frame (int value ){
        	//value = 0;
            if ((((value < 0)) || ((value >= this.mData.cels.length()))))
            {
                value = this.mData.cels.length() - 1;
            	//throw (new Error((((("Frame '" + value) + "' instanceof out of range .get(0, ") + (this.mData.cels.length() - 1)) + ")")));
            };
            if (this.mFrame == value)
            {
                return;
            };
            this.mFrame = value;
            this.mPixels=(BitmapData)this.mData.cels.elementAt(value);
            this.mSrcRect = null;
        }

        public int  x (){
            return (this.destPt.x);
        }

        public void createScaleImage(int sw, int sh) {
        	mData.createScaleImage(sw, sh);
        }

        public  ImageInst (ImageData data ){
            this.destPt = new Point();
            this.mData = data;
            this.mFrame = 0;
            this.mColorTransform = new ColorTransform();
        }
    }


