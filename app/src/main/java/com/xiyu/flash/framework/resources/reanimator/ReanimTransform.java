package com.xiyu.flash.framework.resources.reanimator;
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
//import android.graphics.Matrix;

import com.xiyu.util.Array;
import com.xiyu.util.*;

//import flash.geom.Matrix;
import com.xiyu.flash.framework.resources.images.ImageInst;

    public class ReanimTransform {

        public Matrix matrix ;
        public double sX ;
        public double sY ;
        public ImageInst cache ;
        public double kX ;
        public double kY ;

		public double okX ;
		public double okY ;

        public void  fillInFrom (ReanimTransform other ){
            if (other == null)
            {
                other = new ReanimTransform();
                other.tX = 0;
                other.tY = 0;
                other.kX = 0;
                other.okX = 0;
                other.kY = 0;
                other.okY = 0;
                other.sX = 1;
                other.sY = 1;
                other.frame = 0;
                other.alpha = 1;
            };
            if(this.tX == -999) {
            	this.tX = other.tX;
            }
            if(this.tY == -999) {
            	this.tY = other.tY;
            }
            if(this.kX == -999) {
            	this.kX = other.kX;
            	this.okX = other.okX;
            }
            if(this.kY == -999) {
            	this.kY = other.kY;
            	this.okY = other.okY;
            }
            if(this.sX == -999) {
            	this.sX = other.sX;
            }
            if(this.sY == -999) {
            	this.sY = other.sY;
            }

            if(this.frame == -999) {
            	this.frame = other.frame;
            }
            if(this.alpha == -999) {
            	this.alpha = other.alpha;
            }            
            if(this.image == null) { 
            	this.image = other.image;
            }
            this.calcMatrix();
        }
        public void  calcMatrix (){
//            this.matrix = new Matrix((Math.cos(this.kX) * this.sX), (-(Math.sin(this.kX)) * this.sX), (Math.sin(this.kY) * this.sY), (Math.cos(this.kY) * this.sY), this.tX, this.tY);
              this.matrix = new Matrix(kX, this.kY, this.sX, this.sY, this.tX, this.tY);
        }

        public ImageInst image ;
        public int alpha ;
        public int tX ;
        public int tY ;
        public int frame ;

        public String  toString (){
            return (((((((("[" + this.frame) + "] x:") + this.tX) + " y:") + this.tY) + " image:") + this.image));
        }

        public String imageID ;

        
        public ReanimTransform clone() {
        	ReanimTransform tmp = new ReanimTransform();
        	tmp.tX = this.tX;
        	tmp.tY = this.tY;
        	tmp.kX = this.kX;
        	tmp.kY = this.kY;
        	tmp.sX = this.sX;
        	tmp.sY = this.sY;
        	tmp.frame = this.frame;
        	tmp.alpha = this.alpha;
        	tmp.image = this.image;
        	tmp.okX = this.okX;
        	tmp.okY = this.okY;
//        	tmp.matrix = this.matrix;
        	tmp.cache = this.cache;
        	return tmp;
        }
        
        public  ReanimTransform (){
            this.tX = 0;
            this.tY = 0;
            this.kX = 0;
            this.kY = 0;
            this.sX = 0;
            this.sY = 0;
            this.frame = 0;
            this.alpha = 0;
            this.image = null;
        }
    }


