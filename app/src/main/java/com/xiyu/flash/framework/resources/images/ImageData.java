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

import com.xiyu.util.Array;
import com.xiyu.util.*;

//import flash.display.BitmapData;
    public class ImageData {

        public Array cels ;
        public int cols ;

        public String  toString (){
            return ((((((("Data .get(" + this.rows) + "x") + this.cols) + ", ") + this.cels) + ")"));
        }

        public int rows ;
        public int celWidth ;
        public int celHeight;

        public  ImageData (BitmapData pixels,int rows, int cols){
            this.cels = new Array(pixels);
        	splitImageData(rows, cols);
        	
            this.rows = rows;
            this.cols = cols;
        }
        
        public void splitImageData( int rows, int cols) {
        	BitmapData pixels = (BitmapData)this.cels.elementAt(0);
        	Array images = new Array();
        	int chunkHeight = pixels.height/rows;
        	int chunkWidth = pixels.width/cols;
        	int yCoord = 0;
        	for(int x =0; x <rows; x++) {
        		int xCoord = 0;
        		for(int y =0; y <cols; y++) {
        			Bitmap tmpBitmap = Bitmap.createBitmap(pixels.bitmap,xCoord,yCoord,chunkWidth,chunkHeight);
        			BitmapData tmpBitmapData = new BitmapData(tmpBitmap);
        			xCoord +=chunkWidth;
        			images.push(tmpBitmapData);
        		}
        		yCoord +=chunkHeight;
        	}
        	cels = images;
        }

        public void createScaleImage(int sw, int sh) {
        	BitmapData bitmapdata = (BitmapData)cels.elementAt(0);
        	bitmapdata = bitmapdata.createScaleBitmap(sw, sh);
        	cels = new Array(bitmapdata);
         }
        
        public  ImageData (BitmapData pixels){
            this.cels = new Array(pixels);
            this.rows = 1;
            this.cols = 1;
        }
    }


