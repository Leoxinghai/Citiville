package Classes.effects;

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

import Classes.*;
import com.zynga.skelly.util.color.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class ColorMatrixEffect extends MapResourceEffect
    {
        protected boolean m_isAnimationComplete =false ;
        protected BitmapData m_originalBitmapData ;
        protected BitmapData m_effectBitmapData ;
        protected double m_value ;

        public  ColorMatrixEffect (MapResource param1 ,double param2 )
        {
            super(param1);
            this.m_value = param2;
            this.animate(1);
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (this.m_isAnimationComplete)
            {
                return false;
            }
            if (isMapResourceLoaded)
            {
                if (this.isDrawable)
                {
                    this.drawEffect();
                }
                return false;
            }
            else
            {
                return true;
            }
        }//end

         public void  reattach ()
        {
            if (this.isDrawable)
            {
                this.drawEffect();
            }
            return;
        }//end

         public void  cleanUp ()
        {
            _loc_1 = this.getContentBitmap ();
            if (_loc_1 && this.m_originalBitmapData)
            {
                _loc_1.bitmapData = this.m_originalBitmapData;
            }
            m_mapResource = null;
            this.m_originalBitmapData = null;
            this.m_isAnimationComplete = true;
            return;
        }//end

        protected boolean  isDrawable ()
        {
            boolean _loc_1 =true ;
            _loc_2 = this.getContentBitmap ();
            if (!_loc_2)
            {
                _loc_1 = false;
            }
            else if (this.m_effectBitmapData && this.m_effectBitmapData == _loc_2.bitmapData)
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

        protected void  drawEffect ()
        {
            Point _loc_2 =null ;
            _loc_1 = this.getContentBitmap ();
            this.m_originalBitmapData = _loc_1.bitmapData;
            if (this.bitmapDictionary.get(this.m_originalBitmapData))
            {
                this.m_effectBitmapData = this.bitmapDictionary.get(this.m_originalBitmapData);
            }
            else
            {
                this.m_effectBitmapData = this.m_originalBitmapData.clone();
                _loc_2 = new Point();
                this.m_effectBitmapData.applyFilter(this.m_effectBitmapData, this.m_effectBitmapData.rect, _loc_2, this.colorMatrix.filter);
                this.bitmapDictionary.put(this.m_originalBitmapData,  this.m_effectBitmapData);
            }
            _loc_1.bitmapData = this.m_effectBitmapData;
            return;
        }//end

        protected ColorMatrix  colorMatrix ()
        {
            return null;
        }//end

        protected Dictionary  bitmapDictionary ()
        {
            return null;
        }//end

        protected Bitmap  getContentBitmap ()
        {
            Bitmap _loc_2 =null ;
            if (!m_mapResource)
            {
                return null;
            }
            _loc_1 = m_mapResource.content;
            if (_loc_1 instanceof Bitmap)
            {
                _loc_2 =(Bitmap) _loc_1;
            }
            else if (_loc_1 instanceof CompositeItemImage)
            {
                _loc_2 =(Bitmap)(CompositeItemImage).getBuildingImageByType(ItemImageInstance.BUILDING_IMAGE)) (_loc_1;
            }
            return _loc_2;
        }//end

    }



