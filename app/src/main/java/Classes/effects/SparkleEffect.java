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
import Engine.Classes.*;
import Engine.Events.*;
import com.zynga.skelly.util.color.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;

    public class SparkleEffect extends MapResourceEffect
    {
        private boolean isActive =true ;
        private BitmapData bmask ;
        private Bitmap contentBitmap ;
        private Bitmap lastContentBitmap ;
        private BitmapData sourceBitmapData ;
        private BitmapData contentBitmapData ;
        private double position =-500;
        private double velocity =0;
        private double startVelocity =20;
        private double gravity =-2;
        private ItemImage itemImage ;
        private DisplayObject effectImage ;
        private Point effectPoint ;
        private double lastAnimatedFrame =0;
        private String _effectText ="";
        private static String effectName ="sparkle";
        private static ItemImage itemImage ;
        private static Dictionary owners =new Dictionary(true );
        private static Point zeroPt =new Point ();

        public  SparkleEffect (MapResource param1 ,String param2)
        {
            this._effectText = param2;
            super(param1);
            return;
        }//end

         public boolean  animate (int param1 )
        {
            double _loc_4 =0;
            AnimatedBitmap _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            BitmapData _loc_8 =null ;
            CompositeItemImage _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            int _loc_11 =0;
            int _loc_12 =0;
            int _loc_13 =0;
            ColorMatrix _loc_14 =null ;
            TextField _loc_15 =null ;
            TextFormat _loc_16 =null ;
            if (!this.isActive)
            {
                return this.isActive;
            }
            _loc_2 = m_mapResource.content;
            _loc_3 = boolean(_loc_2isBitmap|| _loc_2 instanceof CompositeItemImage);
            if (_loc_3)
            {
                if (_loc_2 instanceof Bitmap)
                {
                    this.contentBitmap =(Bitmap) _loc_2;
                    this.contentBitmapData = this.contentBitmap.bitmapData;
                    _loc_9 = new CompositeItemImage();
                    _loc_9.addChild(_loc_2);
                    m_mapResource.replaceContent(_loc_9);
                }
                else if (_loc_2 instanceof CompositeItemImage)
                {
                    _loc_10 = ((CompositeItemImage)_loc_2).getBuildingImageByType(ItemImageInstance.BUILDING_IMAGE);
                    this.contentBitmap =(Bitmap) _loc_10;
                    this.contentBitmapData = this.contentBitmap.bitmapData;
                }
                if (this.lastContentBitmap && this.lastContentBitmap.bitmapData != this.contentBitmap.bitmapData)
                {
                    this.bmask = null;
                    if (this.sourceBitmapData)
                    {
                        this.sourceBitmapData.dispose();
                        this.sourceBitmapData = null;
                    }
                }
                this.lastContentBitmap = this.contentBitmap;
                if (!this.bmask)
                {
                    this.effectPoint = new Point();
                    this.effectPoint.x = int(this.contentBitmapData.width * 0.66);
                    _loc_11 = 0;
                    _loc_12 = this.contentBitmapData.height;
                    while (_loc_11 < _loc_12)
                    {

                        _loc_13 = this.contentBitmapData.getPixel32(this.effectPoint.x, _loc_11++);
                        if (_loc_13)
                        {
                            this.effectPoint.y = _loc_11;
                            break;
                        }
                    }
                    this.loadAnimationEffect();
                    this.sourceBitmapData = this.contentBitmapData.clone();
                    if (!owners.get(this.contentBitmapData))
                    {
                        this.bmask = this.sourceBitmapData.clone();
                        _loc_14 = new ColorMatrix();
                        _loc_14.adjustBrightness(30, 30, 30);
                        _loc_14.adjustSaturation(1);
                        _loc_14.adjustContrast(0.5, 0.5, 0.5);
                        this.bmask.applyFilter(this.bmask, this.bmask.rect, zeroPt, _loc_14.filter);
                        this.bmask.threshold(this.bmask, this.bmask.rect, zeroPt, "<=", 4286611584, 0, 4294967295);
                        if (this._effectText)
                        {
                            _loc_15 = new TextField();
                            _loc_15.textColor = 2284834;
                            _loc_15.thickness = 1;
                            _loc_15.text = this._effectText;
                            _loc_15.alpha = 0.33;
                            _loc_16 = new TextFormat("Arial", 24);
                            _loc_16.bold = true;
                            _loc_15.setTextFormat(_loc_16);
                            _loc_15.filters = .get(new GlowFilter(13056, 1, 3, 3, 3, 2));
                            this.bmask.draw(_loc_15, new Matrix(1, 0, 0, 1, this.sourceBitmapData.width / 2 - 10, this.sourceBitmapData.height / 2 - 15), null, null, null, true);
                        }
                        owners.put(this.contentBitmapData,  this.bmask);
                    }
                    else
                    {
                        this.bmask =(BitmapData) owners.get(this.contentBitmapData);
                    }
                    this.contentBitmap.bitmapData = this.bmask.clone();
                    this.contentBitmapData = this.contentBitmap.bitmapData;
                }
                if (!this.effectImage || !this.itemImage || !this.itemImage.getInstance())
                {
                    return true;
                }
                this.velocity = this.velocity + this.gravity;
                this.position = this.position + this.velocity;
                _loc_4 = this.position * 0.01;
                _loc_5 =(AnimatedBitmap) this.effectImage;
                if (this.position < 0)
                {
                    _loc_4 = 0;
                    if (this.position < -1000)
                    {
                        this.position = 0;
                        this.velocity = this.startVelocity;
                    }
                }
                else if (this.position > 100)
                {
                    _loc_4 = 1;
                }
                else if (this.position < 80 && this.velocity < 0 && _loc_5 && !_loc_5.isPlaying)
                {
                    this.lastAnimatedFrame = -1;
                    _loc_5.reset();
                }
                if (_loc_5 && _loc_5.isPlaying)
                {
                    if (_loc_5.currentFrame < this.lastAnimatedFrame)
                    {
                        _loc_5.currentFrame = 0;
                        _loc_5.stop();
                        this.lastAnimatedFrame = -1;
                    }
                    else
                    {
                        this.lastAnimatedFrame = _loc_5.currentFrame;
                    }
                }
                _loc_6 = 255 * _loc_4;
                _loc_7 = _loc_6 << 24;
                _loc_8 = new BitmapData(this.sourceBitmapData.width, this.sourceBitmapData.height, true, _loc_7);
                this.contentBitmapData.copyPixels(this.sourceBitmapData, this.sourceBitmapData.rect, zeroPt, this.sourceBitmapData, zeroPt, false);
                this.contentBitmapData.copyPixels(this.bmask, this.bmask.rect, zeroPt, _loc_8, null, true);
                m_mapResource.renderBufferDirty = true;
            }
            if (!this.isActive)
            {
                this.cleanUp();
            }
            return this.isActive;
        }//end

         public void  cleanUp ()
        {
            if (this.contentBitmap)
            {
                this.contentBitmap.bitmapData = this.sourceBitmapData.clone();
            }
            if (this.effectImage && this.effectImage.parent)
            {
                this.effectImage.parent.removeChild(this.effectImage);
            }
            if (this.sourceBitmapData)
            {
                this.sourceBitmapData.dispose();
                this.sourceBitmapData = null;
            }
            this.contentBitmapData = null;
            this.bmask = null;
            this.isActive = false;
            return;
        }//end

        private void  loadAnimationEffect ()
        {
            XML _loc_1 =null ;
            XML _loc_2 =null ;
            if (!this.itemImage)
            {
                _loc_1 = Global.gameSettings().getEffectByName(effectName);
                _loc_2 = _loc_1.image.get(0);
                this.itemImage = new ItemImage(_loc_2);
                this.itemImage.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                this.itemImage.load();
            }
            else
            {
                this.processItemImage();
            }
            return;
        }//end

        private void  processItemImage ()
        {
            ItemImageInstance _loc_1 =null ;
            AnimatedBitmap _loc_2 =null ;
            CompositeItemImage _loc_3 =null ;
            if (!this.itemImage)
            {
                this.loadAnimationEffect();
            }
            if (m_mapResource.content instanceof CompositeItemImage)
            {
                _loc_1 = this.itemImage.getInstance();
                if (!_loc_1)
                {
                    return;
                }
                this.effectImage =(DisplayObject) this.itemImage.getInstance().image;
                if (!this.effectImage)
                {
                    return;
                }
                this.effectImage.x = this.effectPoint.x - (this.effectImage.width >> 1);
                this.effectImage.y = this.effectPoint.y - (this.effectImage.height >> 1);
                _loc_2 =(AnimatedBitmap) this.effectImage;
                if (_loc_2)
                {
                    _loc_2.stop();
                }
                _loc_3 =(CompositeItemImage) m_mapResource.content;
                _loc_3.addChild(this.effectImage);
            }
            else
            {
                this.isActive = false;
            }
            return;
        }//end

        private void  onItemImageLoaded (LoaderEvent event )
        {
            if (event.target == this.itemImage)
            {
                this.itemImage.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                this.processItemImage();
            }
            return;
        }//end

    }



