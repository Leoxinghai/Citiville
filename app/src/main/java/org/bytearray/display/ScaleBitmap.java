package org.bytearray.display;

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

//import flash.display.*;
//import flash.geom.*;

    public class ScaleBitmap extends Bitmap
    {
        protected BitmapData _originalBitmap ;
        protected Rectangle _scale9Grid =null ;

        public  ScaleBitmap (BitmapData param1 ,String param2 ="auto",boolean param3 =false )
        {
            super(param1, param2, param3);
            this._originalBitmap = param1.clone();
            return;
        }//end

         public void  bitmapData (BitmapData param1 )
        {
            this._originalBitmap = param1.clone();
            if (this._scale9Grid != null)
            {
                if (!this.validGrid(this._scale9Grid))
                {
                    this._scale9Grid = null;
                }
                this.setSize(param1.width, param1.height);
            }
            else
            {
                this.assignBitmapData(this._originalBitmap.clone());
            }
            return;
        }//end

         public void  width (double param1 )
        {
            if (param1 != width)
            {
                this.setSize(param1, height);
            }
            return;
        }//end

         public void  height (double param1 )
        {
            if (param1 != height)
            {
                this.setSize(width, param1);
            }
            return;
        }//end

         public void  scale9Grid (Rectangle param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (this._scale9Grid == null && param1 != null || this._scale9Grid != null && !this._scale9Grid.equals(param1))
            {
                if (param1 == null)
                {
                    _loc_2 = width;
                    _loc_3 = height;
                    this._scale9Grid = null;
                    this.assignBitmapData(this._originalBitmap.clone());
                    this.setSize(_loc_2, _loc_3);
                }
                else
                {
                    if (!this.validGrid(param1))
                    {
                        throw new Error("#001 - The _scale9Grid does not match the original BitmapData");
                    }
                    this._scale9Grid = param1.clone();
                    this.resizeBitmap(width, height);
                    scaleX = 1;
                    scaleY = 1;
                }
            }
            return;
        }//end

        private void  assignBitmapData (BitmapData param1 )
        {
            super.bitmapData.dispose();
            super.bitmapData = param1;
            return;
        }//end

        private boolean  validGrid (Rectangle param1 )
        {
            return param1.right <= this._originalBitmap.width && param1.bottom <= this._originalBitmap.height;
        }//end

         public Rectangle  scale9Grid ()
        {
            return this._scale9Grid;
        }//end

        public void  setSize (double param1 ,double param2 )
        {
            if (this._scale9Grid == null)
            {
                super.width = param1;
                super.height = param2;
            }
            else
            {
                param1 = Math.max(param1, this._originalBitmap.width - this._scale9Grid.width);
                param2 = Math.max(param2, this._originalBitmap.height - this._scale9Grid.height);
                this.resizeBitmap(param1, param2);
            }
            return;
        }//end

        public BitmapData  getOriginalBitmapData ()
        {
            return this._originalBitmap;
        }//end

        protected void  resizeBitmap (double param1 ,double param2 )
        {
            Rectangle _loc_8 =null ;
            Rectangle _loc_9 =null ;
            int _loc_12 =0;
            _loc_3 = new BitmapData(param1 ,param2 ,true ,0);
            Array _loc_4 =.get(0 ,this._scale9Grid.top ,this._scale9Grid.bottom ,this._originalBitmap.height) ;
            Array _loc_5 =.get(0 ,this._scale9Grid.left ,this._scale9Grid.right ,this._originalBitmap.width) ;
            Array _loc_6 =.get(0 ,this._scale9Grid.top ,param2 -(this._originalBitmap.height -this._scale9Grid.bottom ),param2) ;
            Array _loc_7 =.get(0 ,this._scale9Grid.left ,param1 -(this._originalBitmap.width -this._scale9Grid.right ),param1) ;
            _loc_10 = new Matrix ();
            int _loc_11 =0;
            while (_loc_11 < 3)
            {

                _loc_12 = 0;
                while (_loc_12 < 3)
                {

                    _loc_8 = new Rectangle(_loc_5.get(_loc_11), _loc_4.get(_loc_12), _loc_5.get((_loc_11 + 1)) - _loc_5.get(_loc_11), _loc_4.get((_loc_12 + 1)) - _loc_4.get(_loc_12));
                    _loc_9 = new Rectangle(_loc_7.get(_loc_11), _loc_6.get(_loc_12), _loc_7.get((_loc_11 + 1)) - _loc_7.get(_loc_11), _loc_6.get((_loc_12 + 1)) - _loc_6.get(_loc_12));
                    _loc_10.identity();
                    _loc_10.a = _loc_9.width / _loc_8.width;
                    _loc_10.d = _loc_9.height / _loc_8.height;
                    _loc_10.tx = _loc_9.x - _loc_8.x * _loc_10.a;
                    _loc_10.ty = _loc_9.y - _loc_8.y * _loc_10.d;
                    _loc_3.draw(this._originalBitmap, _loc_10, null, null, _loc_9, smoothing);
                    _loc_12++;
                }
                _loc_11++;
            }
            this.assignBitmapData(_loc_3);
            return;
        }//end

    }
}

