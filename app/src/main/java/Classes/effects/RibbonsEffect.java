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
import Engine.Events.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.geom.*;

    public class RibbonsEffect extends MapResourceEffect
    {
        private boolean m_isLoading =false ;
        private boolean m_isItemImagesLoaded =false ;
        private Object m_itemImageLoadedHash ;
        private Object m_bitmapDataHash ;
        private boolean m_isAnimationCompleted =false ;
        private Bitmap m_backBitmap ;
        private Bitmap m_frontBitmap ;

        public  RibbonsEffect (MapResource param1 )
        {
            super(param1);
            this.animate(1);
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (this.m_isAnimationCompleted)
            {
                return false;
            }
            if (isMapResourceLoaded && !this.m_isLoading)
            {
                this.loadAnimationEffect();
                return false;
            }
            return true;
        }//end

         public void  cleanUp ()
        {
            this.m_itemImageLoadedHash = null;
            this.m_bitmapDataHash = null;
            if (this.m_frontBitmap)
            {
                if (this.m_frontBitmap.parent)
                {
                    this.m_frontBitmap.parent.removeChild(this.m_frontBitmap);
                }
                this.m_frontBitmap.bitmapData.dispose();
                this.m_frontBitmap = null;
            }
            if (this.m_backBitmap)
            {
                if (this.m_backBitmap.parent)
                {
                    this.m_backBitmap.parent.removeChild(this.m_backBitmap);
                }
                this.m_backBitmap.bitmapData.dispose();
                this.m_backBitmap = null;
            }
            this.m_isAnimationCompleted = true;
            super.cleanUp();
            return;
        }//end

         public void  reattach ()
        {
            BitmapData _loc_21 =null ;
            String _loc_24 =null ;
            int _loc_25 =0;
            if (!this.m_isItemImagesLoaded)
            {
                return;
            }
            _loc_1 = m_mapResource.getReference().getSize();
            _loc_2 = IsoRect.getIsoRectFromSize(_loc_1);
            double _loc_3 =12;
            double _loc_4 =10;
            _loc_5 = _loc_3>>1;
            _loc_6 = _loc_4>>1;
            _loc_7 = this.m_bitmapDataHash.get("1SW").width;
            _loc_8 = this.m_bitmapDataHash.get("1SW").height;
            _loc_9 = _loc_7-_loc_3 ;
            _loc_10 = _loc_8-_loc_4;
            double _loc_11 =0;
            _loc_12 = _loc_2.height-_loc_2.bottom.y+(_loc_10>>1)-_loc_4;
            _loc_13 = _loc_2.width;
            _loc_14 = _loc_2.height+_loc_4;
            BitmapData _loc_15 =new BitmapData(_loc_13 ,_loc_14 ,true ,0);
            BitmapData _loc_16 =new BitmapData(_loc_13 ,_loc_14 ,true ,0).clone ();
            Point _loc_17 =new Point ();
            _loc_18 = _loc_1.x*IsoRect.TILE_SIZE;
            _loc_19 = _loc_1(-.y)*IsoRect.TILE_SIZE;
            Point _loc_20 =new Point ();
            double _loc_22 =0;
            double _loc_23 =0;
            _loc_21 = this.m_bitmapDataHash.get("1SW");
            _loc_22 = -_loc_5;
            _loc_23 = -_loc_10 + _loc_6;
            _loc_25 = _loc_1.x;
            if (_loc_25 > 0)
            {
                while (_loc_25--)
                {

                    _loc_20 = IsoRect.tilePosToPixelPos(((_loc_1.x - 1) - _loc_25) * IsoRect.TILE_SIZE, _loc_19, 0);
                    _loc_17.x = _loc_20.x + _loc_11 + _loc_22;
                    _loc_17.y = _loc_20.y + _loc_12 + _loc_23;
                    _loc_16.copyPixels(_loc_21, _loc_21.rect, _loc_17, null, null, true);
                }
            }
            _loc_21 = this.m_bitmapDataHash.get("1NW");
            _loc_22 = 0;
            _loc_23 = 0;
            _loc_25 = _loc_1.y;
            if (_loc_25 > 0)
            {
                while (_loc_25--)
                {

                    _loc_20 = IsoRect.tilePosToPixelPos(0, (-((_loc_1.y - 1) - _loc_25)) * IsoRect.TILE_SIZE, 0);
                    _loc_17.x = _loc_20.x + _loc_11 + _loc_22;
                    _loc_17.y = _loc_20.y + _loc_12 + _loc_23;
                    _loc_16.copyPixels(_loc_21, _loc_21.rect, _loc_17, null, null, true);
                }
            }
            _loc_21 = this.m_bitmapDataHash.get("1NE");
            _loc_22 = 0;
            _loc_23 = -_loc_10 + _loc_6;
            _loc_25 = _loc_1.x;
            if (_loc_25 > 0)
            {
                while (_loc_25--)
                {

                    _loc_20 = IsoRect.tilePosToPixelPos(_loc_25 * IsoRect.TILE_SIZE, 0, 0);
                    _loc_17.x = _loc_20.x + _loc_11 + _loc_22;
                    _loc_17.y = _loc_20.y + _loc_12 + _loc_23;
                    _loc_15.copyPixels(_loc_21, _loc_21.rect, _loc_17, null, null, true);
                }
            }
            _loc_21 = this.m_bitmapDataHash.get("1SE");
            _loc_22 = 0;
            _loc_23 = 0;
            _loc_25 = _loc_1.y;
            if (_loc_25 > 0)
            {
                while (_loc_25--)
                {

                    _loc_20 = IsoRect.tilePosToPixelPos(_loc_18, (-((_loc_1.y - 1) - _loc_25)) * IsoRect.TILE_SIZE, 0);
                    _loc_17.x = _loc_20.x + _loc_11 + _loc_22;
                    _loc_17.y = _loc_20.y + _loc_12 + _loc_23;
                    _loc_15.copyPixels(_loc_21, _loc_21.rect, _loc_17, null, null, true);
                }
            }
            _loc_26 = (Sprite)m_mapResource.getDisplayObject()
            _loc_27 = m_mapResource(-.displayObjectOffsetY)/m_mapResource.displayObject.scaleY;
            this.m_frontBitmap = new Bitmap(_loc_16);
            this.m_frontBitmap.y = _loc_27 - this.m_frontBitmap.height;
            _loc_26.addChild(this.m_frontBitmap);
            this.m_backBitmap = new Bitmap(_loc_15);
            this.m_backBitmap.y = _loc_27 - this.m_backBitmap.height;
            _loc_26.addChildAt(this.m_backBitmap, _loc_26.getChildIndex(m_mapResource.content));
            return;
        }//end

        protected void  loadAnimationEffect ()
        {
            XML _loc_2 =null ;
            ItemImage _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 = Global.gameSettings().getRibbonSetByName("ribbonA");
            this.m_itemImageLoadedHash = {};
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_4 = _loc_2.attribute("name");
                this.m_itemImageLoadedHash.put(_loc_4,  false);
            }
            this.m_bitmapDataHash = {};
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = new ItemImage(_loc_2);
                _loc_3.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                _loc_3.load();
            }
            this.m_isLoading = true;
            return;
        }//end

        protected void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            if (!this.m_isItemImagesLoaded)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                if (this.m_isAnimationCompleted)
                {
                    return;
                }
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                this.m_bitmapDataHash.put(_loc_2.name,  ((Bitmap)_loc_3.image).bitmapData);
                _loc_4 = _loc_2.name;
                this.m_itemImageLoadedHash.put(_loc_4,  true);
                _loc_5 = true;
                for(int i0 = 0; i0 < this.m_itemImageLoadedHash.size(); i0++)
                {
                	_loc_6 = this.m_itemImageLoadedHash.get(i0);

                    if (!_loc_6)
                    {
                        _loc_5 = false;
                        break;
                    }
                }
                if (_loc_5)
                {
                    this.m_isLoading = false;
                    this.m_isItemImagesLoaded = true;
                    this.reattach();
                }
            }
            return;
        }//end

    }




