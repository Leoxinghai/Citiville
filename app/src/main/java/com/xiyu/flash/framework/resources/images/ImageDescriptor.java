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

//import flash.display.Bitmap;
//import flash.display.BitmapData;
//import flash.display.BitmapDataChannel;
//import flash.geom.Point;
//import flash.geom.Rectangle;

    public class ImageDescriptor {

        private static Rectangle srcRect =new Rectangle ();
        private static Point destPt =new Point ();

        private int mRows ;
        private Class mAlphaClass ;
        private Class mRGBClass ;

        private void  sliceCels (BitmapData bd ,int rows ,int cols ,ImageData data ){
            int row ;
            int col ;
            BitmapData subPixels ;
            Rectangle srcRect ;
            int celWidth =(bd.width /this.mCols );
            int celHeight =(bd.height /this.mRows );
            Rectangle celRect =new Rectangle(0,0,(int)celWidth ,(int)celHeight );
            Point destPt =new Point(0,0);
            data.rows = rows;
            data.cols = cols;
            Array cels =new Array ();
            int numCels =(int)(rows *cols );
            int i =0;
            while (i < numCels)
            {
                row = (int)((i / cols));
                col = (i % cols);
                subPixels = new BitmapData(celWidth, celHeight,false,1);
                srcRect = new Rectangle((col * celWidth), (row * celHeight), celWidth, celHeight);
                subPixels.copyPixels(bd, srcRect, destPt);
                cels.add(i,subPixels);
                i = (i + 1);
            };
            data.celWidth = celWidth;
            data.celHeight = celHeight;
            data.cels = cels;
            bd.dispose();
        }
        public ImageData  createData (){
			BitmapData _loc_1 ;
			BitmapData _loc_2 ;
			BitmapData _loc_3 ;
			ImageData _loc_4 = null ;
/*
			if (this.mRGBClass != null)
			{
				_loc_2 =(BitmapData) new this.mRGBClass();
				if (_loc_2 == null) {
					_loc_2 = (new this.mRGBClass() as Bitmap).bitmapData;
				}
			}
			if (this.mAlphaClass != null)
			{
				_loc_3 =(BitmapData)  new this.mAlphaClass();
				if (_loc_3 == null)
				{
					_loc_3 = (new this.mAlphaClass() as Bitmap).bitmapData;
				}
			}


			if (_loc_2 != null)
			{
				_loc_1 = new BitmapData(_loc_2.width(), _loc_2.height(), true);
			}
			else if (_loc_3 != null)
			{
				_loc_1 = new BitmapData(_loc_3.width(), _loc_3.height(), true);
			}
			if (_loc_1 == null)
			{
				//throw new Error("Image is empty.");
				return null;
			}
			srcRect.width() = _loc_1.width();
			srcRect.height() = _loc_1.height();
			if (_loc_2 != null)
			{
				_loc_1.copyPixels(_loc_2, srcRect, destPt);
			}
			if (_loc_3 != null)
			{
				if (_loc_2 == null)
				{
					_loc_1.copyPixels(_loc_3, srcRect, destPt);
				}
				_loc_1.copyChannel(_loc_3, srcRect, destPt, BitmapDataChannel.RED, BitmapDataChannel.ALPHA);
			}
			ImageData _loc_4 =new ImageData(null ,this.mRows ,this.mCols );
			this.sliceCels(_loc_1, this.mRows, this.mCols, _loc_4);
 */
			return _loc_4;
        }

        private int mCols ;

        public  ImageDescriptor (Class rgbClass, Class alphaClass , int rows,int cols){
            this.mRGBClass = rgbClass;
            this.mAlphaClass = alphaClass;
            this.mRows = rows;
            this.mCols = cols;
        }
    }


