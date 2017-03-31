package com.xiyu.flash.framework.graphics;
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
import android.graphics.Region;
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
//import android.graphics.*;


import com.xiyu.util.*;

import android.graphics.Point;


//import flash.geom.Rectangle;
//import flash.geom.Point;
//import flash.geom.Matrix;
//import flash.display.BitmapData;
//import flash.geom.ColorTransform;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageInst;

    public class Graphics2D {

        private static Rectangle mScratchRect =new Rectangle ();
        private static Point mScratchPoint =new Point ();
        private static Matrix mScratchMatrix =new Matrix ();
        private static BitmapData mScratchData ;
        private static ColorTransform mScratchColorTransform =new ColorTransform ();
        private Canvas canvas;

        public void  popState (){
            if (this.stackPos == 0)
            {
                //throw (new Error("Unable to pop empty stack."));
            };
            this.stackPos = (this.stackPos + -1);
            this.state=(GraphicsState)this.stateStack.elementAt(this.stackPos);
            canvas.restore();
        }
        public void  clearClipRect (){
        	this.state.clipRect = new Rectangle(0, 0, this.state.clipRect.width, this.state.clipRect.height);
//            this.state.clipRect = new Rectangle(0, 0, this.data.width, this.data.height);
        }

        private Array stateStack ;

        public void  fillRect (int x ,int y ,int w ,int h ,int color ){
            mScratchRect.x = 0;
            mScratchRect.y = 0;
            mScratchRect.width = (int)w;
            mScratchRect.height = (int)h;
            mScratchPoint.x = (int)x;
            mScratchPoint.y = (int)y;
            mScratchData.fillRect(mScratchRect, color);
            this.data.copyPixels(mScratchData, mScratchRect, mScratchPoint, null, null, true);
        }
        
        public void drawCircle(int x, int y, int radius) {
        	Paint paint = new Paint();
        	paint.setColor(345565);
        	canvas.drawCircle(x, y, radius, paint);
        }
        
        public void  scale (double sX ,double sY ){
            this.state.affineMatrix.scale(sX, sY);
        }
        public void  clear (){

        	this.fillRect(0, 0, this.state.clipRect.width, this.state.clipRect.height, 0xFF000000);
//            this.fillRect(0, 0, this.data.width, this.data.height, 0xFF000000);
        }
        public void  translate (double tX ,double tY ){
            this.state.affineMatrix.translate(tX, tY);
        }
        public void  reset (){
        	float[] values = new float[2];
        	this.canvas.getMatrix().mapPoints(values);

        	return;
        	/*
            this.stackPos = 0;
            this.state=(GraphicsState)this.stateStack.elementAt(this.stackPos);
            this.state.affineMatrix.identity();
            this.state.clipRect.x = 0;
            this.state.clipRect.y = 0;
            if(this.data !=null) {
	            this.state.clipRect.width = this.data.width;
	            this.state.clipRect.height = this.data.height;
            } else if(canvas !=null) {
	            this.state.clipRect.width = this.canvas.getWidth();
	            this.state.clipRect.height = this.canvas.getHeight();
            }
            this.state.font = null;
            */
        }
        public void  setFont (FontInst font ){
            this.state.font = font;
        }
        public void  setClipRect (int x ,int y ,int w ,int h ,boolean clear){
            Rectangle newClip =new Rectangle(x ,y ,w ,h );
            if (clear)
            {
                this.state.clipRect = newClip;
//                System.out.println("setClipRect." + newClip.x+":"+newClip.y+":"+newClip.width+":"+newClip.height);
//                canvas.drawText("test", x, y, new Paint());
//                canvas.clipRect(newClip.rect);
				if(x!=0 && y!=0) {
//            	    canvas.clipRect(x,y,w,h,Region.Op.DIFFERENCE);
				}
                return;
            };
            this.state.clipRect = this.state.clipRect.intersection(newClip);
            canvas.clipRect(newClip.rect);
        }
        public void  setClipRect (double x ,double y ,double w ,double h ,boolean clear){
            Rectangle newClip =new Rectangle(x ,y ,w*3 ,h*3 );
            if (clear)
            {
                this.state.clipRect = newClip;
                return;
            };
            this.state.clipRect = this.state.clipRect.intersection(newClip);
            canvas.clipRect(newClip.rect);
        }

        public void  setClipRect2 (double x ,double y ,double w ,double h ,boolean clear){
            Rectangle newClip =new Rectangle(x ,y ,w*3 ,h*3 );
            this.state.clipRect = newClip;
            //this.state.clipRect = this.state.clipRect.intersection(newClip);
            canvas.clipRect(newClip.rect);
        }
        
        public void  pushState (){
            GraphicsState newState ;
            this.stackPos = (this.stackPos + 1);
            if (this.stackPos >= this.stateStack.length())
            {
                newState = new GraphicsState();
                this.stateStack.push(newState);
            };
            GraphicsState oldState =this.state ;
            this.state=(GraphicsState)this.stateStack.elementAt(this.stackPos);
            this.state.affineMatrix.a = oldState.affineMatrix.a;
            this.state.affineMatrix.b = oldState.affineMatrix.b;
            this.state.affineMatrix.c = oldState.affineMatrix.c;
            this.state.affineMatrix.d = oldState.affineMatrix.d;
            this.state.affineMatrix.tx = oldState.affineMatrix.tx;
            this.state.affineMatrix.ty = oldState.affineMatrix.ty;
            this.state.clipRect.x = oldState.clipRect.x;
            this.state.clipRect.y = oldState.clipRect.y;
            this.state.clipRect.width = oldState.clipRect.width();
            this.state.clipRect.height = oldState.clipRect.height();
            this.state.font = oldState.font;
            canvas.save();
        }

        public GraphicsState state ;

        public void  rotate (double angle ){
            this.state.affineMatrix.rotate(angle);
        }
        public void  blitImage (ImageInst img ,double tX,double tY){
            BitmapData pixels =img.pixels();
            Rectangle srcRect =img.srcRect();
            Point destPt =mScratchPoint ;
            if (img.useColor)
            {
                destPt.x = 0;
                destPt.y = 0;
                mScratchData.copyPixels(pixels, srcRect, destPt);
                mScratchColorTransform.alphaMultiplier = (int)img.alpha();
                mScratchColorTransform.redMultiplier = (int)img.red();
                mScratchColorTransform.greenMultiplier = (int)img.green();
                mScratchColorTransform.blueMultiplier = (int)img.blue();
                mScratchData.colorTransform(srcRect, mScratchColorTransform);
                pixels = mScratchData;
            };
            destPt.x = (int)((this.state.affineMatrix.tx + tX) + img.destPt.x);
            destPt.y = (int)((this.state.affineMatrix.ty + tY) + img.destPt.y);
            this.state.affineMatrix.translate(destPt.x, destPt.y);
            //this.data.copyPixels(pixels, srcRect, destPt, null, null, true);

            Paint paint = new Paint();
            canvas.drawBitmap(img.pixels().bitmap, this.state.affineMatrix.getMatrix(), paint);
            this.state.affineMatrix.translate(-destPt.x, -destPt.y);

        }
        public void  setTransform (Matrix matrix ){
            this.state.affineMatrix = matrix;//matrix.clone();
        }
        public void  transform (Matrix matrix ){
            Matrix first =matrix.clone ();
            first.concat(this.state.affineMatrix);
            this.state.affineMatrix = first;
        }

        private BitmapData data ;
        private int stackPos ;

        public void  drawString (String str ,int x ,int y ){
            if (this.state.font == null)
            {
                return;
            };
//            this.state.font.draw(this, x, y, str, this.state.clipRect);
            Paint paint = new Paint();
            paint.setTextSize(24);
            canvas.drawText(str, 0, str.length(), x, y, paint);
        }
        public Matrix  getTransform (){
            return (this.state.affineMatrix.clone());
        }
        public void  drawImage (ImageInst img ,double x,double y){
            if ((((img.width() == 0)) || ((img.height() == 0))))
            {
                return;
            };
            ColorTransform aColorTrans =null;
            if (img.useColor)
            {
                mScratchColorTransform.alphaMultiplier = (int)img.alpha();
                mScratchColorTransform.redMultiplier = (int)img.red();
                mScratchColorTransform.greenMultiplier = (int)img.green();
                mScratchColorTransform.blueMultiplier = (int)img.blue();
                aColorTrans = mScratchColorTransform;
            };
            this.state.affineMatrix.translate(x, y);
//            this.data.draw(img.pixels(), this.state.affineMatrix, aColorTrans, null, this.state.clipRect, img.doSmoothing);
            Paint paint = new Paint();
//            paint.setColor(android.graphics.Color.);
//            if(img.useColor) {
//            	paint.setColor(img.red()|img.green()|img.blue());
//            }
//            this.state.affineMatrix.getMatrix().setRotate(45);
            paint.setTextSize(24);
//            this.state.affineMatrix.getMatrix().postRotate(24);

//            Bitmap tmpBmp = Bitmap.createBitmap(img.pixels().bitmap,0,0,img.pixels().bitmap.getWidth(),img.pixels().bitmap.getHeight(),this.state.affineMatrix.getMatrix(),true);
            //canvas.drawText("image "+this.state.affineMatrix.getMatrix(), 100, 100, paint);
            if(this.state.clipRect.width!=0 && this.state.clipRect.height!=0) {
//            	canvas.clipRect(this.state.clipRect.x,this.state.clipRect.y,this.state.clipRect.width,this.state.clipRect.height,Region.Op.DIFFERENCE);
			}
            canvas.drawBitmap(img.pixels().bitmap, this.state.affineMatrix.getMatrix(), paint);
//            android.graphics.Matrix mm = new android.graphics.Matrix();
//            mm.setTranslate((float)x, (float)y);
//            mm.postScale(0.3f, 0.3f);
//            canvas.drawBitmap(tmpBmp, mm, paint);

            this.state.affineMatrix.translate(-(x), -(y));
        }
        public void  identity (){
            this.state.affineMatrix = new Matrix();
        }

        public  Graphics2D (BitmapData data ){
            this.data = data;
            this.canvas = new Canvas(data.bitmap);
            this.stateStack = new Array();
            this.stackPos = 0;
            this.state = new GraphicsState();
            this.state.clipRect.width = data.width;
            this.state.clipRect.height = data.height;
            if (mScratchData == null)
            {
                mScratchData = new BitmapData(data.width, data.height, true, 0);
            };
            this.stateStack.push(this.state);
        }

        public  Graphics2D (Canvas _canvas){
        	this.canvas = _canvas;

//            this.data = data;
            this.stateStack = new Array();
            this.stackPos = 0;
            this.state = new GraphicsState();
            this.state.clipRect.width = canvas.getWidth();//data.width;
            this.state.clipRect.height = canvas.getHeight();//data.height;
            if (mScratchData == null)
            {
                mScratchData = new BitmapData(canvas.getWidth(), canvas.getHeight(), true, 0);
            };
            this.stateStack.push(this.state);
        }
    }


