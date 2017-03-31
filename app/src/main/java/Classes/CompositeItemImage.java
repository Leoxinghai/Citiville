package Classes;

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

import Engine.Classes.*;
import Engine.Interfaces.*;
//import flash.display.*;
//import flash.geom.*;
import com.xinghai.Debug;

    public class CompositeItemImage extends MovieClip implements IDynamicDisplayObject
    {
        private Rectangle m_dirtyRect ;

        public  CompositeItemImage ()
        {
            this.m_dirtyRect = new Rectangle();
            return;
        }//end

        public void  onUpdate (double param1 )
        {
            IDynamicDisplayObject _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.numChildren)
            {

                _loc_3 =(IDynamicDisplayObject) this.getChildAt(_loc_2);
                if (_loc_3)
                {
                    _loc_3.onUpdate(param1);
                }
                _loc_2++;
            }
            return;
        }//end

        public Rectangle  getDirtyRect ()
        {
            Rectangle _loc_1 =null ;
            int _loc_2 =0;
            DisplayObject _loc_3 =null ;
            AnimatedBitmap _loc_4 =null ;
            if (this.m_dirtyRect.width == 0 && this.m_dirtyRect.height == 0)
            {
                _loc_2 = 0;
                while (_loc_2 < this.numChildren)
                {

                    _loc_3 = this.getChildAt(_loc_2);
                    _loc_4 =(AnimatedBitmap) _loc_3;
                    if (_loc_4)
                    {
                        _loc_1 = _loc_4.targetBitmapData.rect.clone();
                        _loc_1.x = _loc_4.x;
                        _loc_1.y = _loc_4.y;
                        this.m_dirtyRect = _loc_1.union(this.m_dirtyRect);
                    }
                    _loc_2++;
                }
            }
            return this.m_dirtyRect.clone();
        }//end

        public DisplayObject  getBaseImage ()
        {
            DisplayObject _loc_1 =null ;
            Debug.debug4("CompositeItemImage."+"getBaseImage");
            if (numChildren)
            {
                _loc_1 = getChildByName("base");
                if (!_loc_1)
                {
                    _loc_1 = getChildAt(0);
                }
            }
            return _loc_1;
        }//end

        public DisplayObject  getBuildingImageByType (String param1 )
        {
            DisplayObject _loc_2 =null ;
            Debug.debug4("CompositeItemImage."+"getBuildingImageByType");
            if (numChildren)
            {
                _loc_2 = getChildByName(param1);
                if (!_loc_2)
                {
                    _loc_2 = getChildAt(0);
                }
            }
            return _loc_2;
        }//end

        public void  renderCiToBuffer (RenderContext param1 ,double param2 ,double param3 ,Point param4 ,Point param5 ,double param6 )
        {

            Debug.debug4("CompositeItemImage."+"renderCiToBuffer");

            DisplayObject _loc_10 =null ;
            AnimatedBitmap _loc_11 =null ;
            _loc_7 = this.numChildren ;
            Point _loc_8 =new Point ();
            int _loc_9 =0;
            while (_loc_9 < this.numChildren)
            {

                _loc_10 = this.getChildAt(_loc_9);
                _loc_11 =(AnimatedBitmap) _loc_10;
                if (_loc_11)
                {
                    _loc_8.x = param5.x;
                    _loc_8.y = param5.y;
                    _loc_8.x = _loc_8.x + param2 * _loc_11.x;
                    _loc_8.y = _loc_8.y + param3 * _loc_11.y;
                    if (param6 > 0.2)
                    {
                        if (param1.alphaBuffer)
                        {
                            param1.targetBuffer.copyPixels(_loc_11.targetBitmapData, _loc_11.targetBitmapData.rect, _loc_8, param1.alphaBuffer, _loc_8, false);
                        }
                        else
                        {
                            param1.targetBuffer.copyPixels(_loc_11.targetBitmapData, _loc_11.targetBitmapData.rect, _loc_8);
                        }
                    }
                }
                else if (_loc_10 instanceof Bitmap)
                {
                }
                _loc_9++;
            }
            return;
        }//end

        public void  renderCiToBufferOld (BitmapData param1 ,BitmapData param2 ,Matrix param3 ,double param4 )
        {
            DisplayObject _loc_8 =null ;
            AnimatedBitmap _loc_9 =null ;
            Point _loc_10 =null ;
            ColorTransform _loc_11 =null ;
            Bitmap _loc_12 =null ;
            Point _loc_13 =null ;
            ColorTransform _loc_14 =null ;
            _loc_5 = this.numChildren ;
            Matrix _loc_6 =new Matrix ();
            int _loc_7 =0;
            while (_loc_7 < this.numChildren)
            {

                _loc_8 = this.getChildAt(_loc_7);
                _loc_9 =(AnimatedBitmap) _loc_8;
                _loc_6 = param3;
                if (_loc_9)
                {
                    _loc_6.tx = _loc_6.tx + _loc_6.a * _loc_9.x;
                    _loc_6.ty = _loc_6.ty + _loc_6.d * _loc_9.y;
                    if (param2)
                    {
                        _loc_10 = new Point(_loc_6.tx, _loc_6.ty);
                        param1.copyPixels(_loc_9.targetBitmapData, _loc_9.targetBitmapData.rect, _loc_10, param2, _loc_10, false);
                    }
                    else if (param4 < 1)
                    {
                        _loc_11 = new ColorTransform();
                        _loc_11.alphaMultiplier = param4;
                        param1.draw(_loc_9.targetBitmapData, _loc_6, _loc_11);
                    }
                    else
                    {
                        param1.draw(_loc_9.targetBitmapData, _loc_6);
                    }
                }
                else if (_loc_8 instanceof Bitmap)
                {
                    _loc_6.tx = _loc_6.tx + _loc_6.a * _loc_8.x;
                    _loc_6.ty = _loc_6.ty + _loc_6.d * _loc_8.y;
                    _loc_12 =(Bitmap) _loc_8;
                    if (param2)
                    {
                        _loc_13 = new Point(_loc_6.tx, _loc_6.ty);
                        param1.copyPixels(_loc_12.bitmapData, _loc_12.bitmapData.rect, _loc_13, param2, _loc_13, false);
                    }
                    else if (param4 < 1)
                    {
                        _loc_14 = new ColorTransform();
                        _loc_14.alphaMultiplier = param4;
                        param1.draw(_loc_12.bitmapData, _loc_6, _loc_14);
                    }
                    else
                    {
                        param1.draw(_loc_12.bitmapData, _loc_6);
                    }
                }
                _loc_7++;
            }
            return;
        }//end

    }



