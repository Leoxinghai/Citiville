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

import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;

import com.adobe.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import pixelShader.*;
import com.xinghai.Debug;

    public class ItemImage extends EventDispatcher
    {
        public String name ;
        public String type ="";
        public Object lowResImage =null ;
        public double forcedWidth =0;
        public double forcedHeight =0;
        public double offsetX =0;
        public double offsetY =0;
        public int direction =0;
        public boolean allDirections =false ;
        public String phase ;
        public boolean allPhases =false ;
        private Dictionary m_hotspotMap =null ;
        private Vector<Vector3> m_hotspots;
        private XML m_xml ;
        private boolean m_loaded ;
        private boolean m_loadStarted ;
        private Array m_assets =null ;
        private Array m_images =null ;
        private Array m_unloadedAssets =null ;
        private String m_className ;
        private int counter =0;
        private static Array m_directionCodes =.get( "SW","SE","NE","NW","N","E","S","W","SWW","NWW","NNW","NNE","NEE","SEE","SSE","SSW") ;
        private static boolean s_lazyInit =false ;
        private static int countAll =0;
        public static boolean disposeSpriteSheets =false ;

        public boolean isDebug =false ;

        public void  ItemImage (XML param1 ,boolean param2 =false )
        {
            this.m_xml = param1;
            isDebug = param2;
            if (!lazyInit)
            {
                this.initAssets();
                this.initHotspots();
            }
            this.m_className = String(param1.@className);
            this.forcedWidth = int(param1.@width);
            this.forcedHeight = int(param1.@height);
            this.offsetX = int(param1.@offsetX);
            this.offsetY = int(param1.@offsetY);
            this.name = String(param1.@name);
            this.type = String(param1.@type);
            _loc_2 = String(param1.@direction);
            int _loc_3 =0;
            while (_loc_3 < Item.DIRECTION_16_MAX)
            {

                if (m_directionCodes.get(_loc_3) == _loc_2)
                {
                    this.direction = _loc_3;
                    break;
                }
                _loc_3++;
            }
            if (_loc_2 == "ALL")
            {
                this.allDirections = true;
            }
            _loc_4 = String(param1.@phase);
            if (String(param1.@phase) != null && _loc_4 != "")
            {
                this.phase = _loc_4;
            }
            else
            {
                this.allPhases = true;
            }
            return;
        }//end

        public Vector3 Vector  getHotspotsByName (String param1 ).<>
        {
            if (this.m_hotspotMap === null)
            {
                this.initHotspots();
            }
            if (this.m_hotspotMap.get(param1) != null && this.m_hotspotMap.get(param1) instanceof Vector<Vector3>)
            {
                return this.m_hotspotMap.get(param1);
            }
            return this.m_hotspots;
        }//end

        public void  load ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            IEventDispatcher _loc_3 =null ;
            Array _loc_4 =null ;
            _loc_5 = undefined;

            if (this.m_loaded == false && this.m_loadStarted == false)
            {
                this.m_loadStarted = true;
                if (this.m_assets === null)
                {
                    this.initAssets();
                }
                _loc_1 = 0;
                while (_loc_1 < this.m_assets.length())
                {

                    this.m_unloadedAssets.push(this.m_assets.get(_loc_1));
                    _loc_1++;
                }
                _loc_2 = 0;
                while (_loc_2 < this.m_images.length())
                {

                    this.m_unloadedAssets.push(this.m_images.get(_loc_2));
                    _loc_2++;
                }
                for(int i0 = 0; i0 < this.m_unloadedAssets.size(); i0++)
                {
                	_loc_3 = this.m_unloadedAssets.get(i0);

                    _loc_3.addEventListener(LoaderEvent.LOADED, this.onAssetLoaded);
                }
                _loc_4 = ArrayUtil.copyArray(this.m_unloadedAssets);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_5.load();
                }
            }
            return;
        }//end

        private void  initHotspots ()
        {
            XML _loc_1 =null ;
            int _loc_2 =0;
            String _loc_3 =null ;
            Vector _loc_4.<Vector3 >=null ;
            int _loc_5 =0;
            XML _loc_6 =null ;
            this.m_hotspotMap = new Dictionary();
            if (this.m_xml.hotspots.length() > 0)
            {
                for(int i0 = 0; i0 < this.m_xml.hotspots.size(); i0++)
                {
                	_loc_1 = this.m_xml.hotspots.get(i0);

                    _loc_2 = _loc_1.hotspot.length();
                    _loc_3 = _loc_1.@name.toString();
                    if (_loc_2 > 0)
                    {
                        _loc_4 = new Vector<Vector3>(_loc_2);
                        _loc_5 = 0;
                        while (_loc_5 < _loc_2)
                        {

                            _loc_6 = _loc_1.hotspot.get(_loc_5);
                            _loc_4.put(_loc_5,  new Vector3(Number(_loc_6.@x), Number(_loc_6.@y)));
                            _loc_5++;
                        }
                        this.m_hotspotMap.put(_loc_3,  _loc_4);
                        if (_loc_3 == "")
                        {
                            this.m_hotspots = _loc_4;
                        }
                        continue;
                    }
                    if (_loc_3 == "")
                    {
                        this.m_hotspots = Vector<Vector3>(.get(new Vector3(Number(this.m_xml.@hotX), Number(this.m_xml.@hotY))));
                        this.m_hotspotMap.put("",  this.m_hotspots);
                    }
                }
            }
            if (!this.m_hotspots)
            {
                this.m_hotspots = Vector<Vector3>(.get(new Vector3(Number(this.m_xml.@hotX), Number(this.m_xml.@hotY))));
                this.m_hotspotMap.put("",  this.m_hotspots);
            }
            return;
        }//end

        private void  initAssets ()
        {
            ItemImageAsset _loc_4 =null ;
            ItemImage _loc_5 =null ;
            this.m_assets = new Array();
            this.m_images = new Array();
            this.m_unloadedAssets = new Array();
            _loc_1 = this.m_xml.@url;
            if (_loc_1.toString().length > 0)
            {
                this.m_assets.push(new ItemImageAsset(this.m_xml));
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_xml.asset.length())
            {

                if(isDebug) {
                   _loc_4 = new ItemImageAsset(this.m_xml.asset.get(_loc_2),true);
	        } else {
	           _loc_4 = new ItemImageAsset(this.m_xml.asset.get(_loc_2));
	        }


                this.m_assets.push(_loc_4);
                _loc_2++;
            }
            int _loc_3 =0;
            while (_loc_3 < this.m_xml.image.length())
            {

                if(isDebug) {

                  _loc_5 = new ItemImage(this.m_xml.image.get(_loc_3),true);
                } else {
		   _loc_5 = new ItemImage(this.m_xml.image.get(_loc_3));

                }

                this.m_images.push(_loc_5);
                _loc_3++;
            }
            return;
        }//end

        protected void  onAssetLoaded (Event event )
        {
            IEventDispatcher _loc_3 =null ;
           if(isDebug) {
               Debug.debug4("ItemImage.onAssetLoaded."+m_xml);
           }

            event.stopImmediatePropagation();

            int _loc_2 =0;
            while (_loc_2 < this.m_unloadedAssets.length())
            {

                _loc_3 =(IEventDispatcher) this.m_unloadedAssets.get(_loc_2);
                if (_loc_3 == event.target)
                {
                    _loc_3.removeEventListener(LoaderEvent.LOADED, this.onAssetLoaded);
                    this.m_unloadedAssets.splice(_loc_2, 1);
                    break;
                }
                _loc_2++;
            }
            if (this.m_unloadedAssets.length == 0)
            {
                this.m_loaded = true;
                dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            }
            return;
        }//end

        public boolean  isLoaded ()
        {
            return this.m_loaded;
        }//end

        public boolean  isLoading ()
        {
            return this.m_loadStarted && !this.m_loaded;
        }//end

        public boolean  isMatching (String param1 ,int param2 ,String param3 )
        {
            if (param1 != this.name)
            {
                return false;
            }
            if (!this.allDirections && this.direction != param2)
            {
                return false;
            }
            if (!this.allPhases && this.phase != param3)
            {
                return false;
            }
            return true;
        }//end

        protected ItemImageAsset  getAssetByName (String param1 )
        {
            ItemImageAsset _loc_2 =null ;
            ItemImageAsset _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_assets.length())
            {

                _loc_4 =(ItemImageAsset) this.m_assets.get(_loc_3);
                if (_loc_4.name == param1)
                {
                    _loc_2 = _loc_4;
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        public ItemImageInstance  getInstance (MapResource param1)
        {
            MovieClip _loc_3 =null ;
            ItemImageInstance _loc_2 =new ItemImageInstance ();
            if (!this.isLoaded())
            {
                this.load();
            }
            if (this.isLoaded())
            {
                _loc_2.image = this.generateFinalImage(param1);
            }
            if (_loc_2.image != null)
            {
                _loc_3 = null;
                if (_loc_2 instanceof MovieClip)
                {
                    _loc_3 =(MovieClip) _loc_2;
                }
                if (this.forcedWidth == 0)
                {
                    if (_loc_2.lowResImage)
                    {
                        _loc_2.forcedWidth = _loc_2.lowResImage.width;
                    }
                    else
                    {
                        _loc_2.forcedWidth = _loc_2.image.width;
                    }
                }
                else
                {
                    _loc_2.forcedWidth = this.forcedWidth;
                }
                if (this.offsetX)
                {
                    _loc_2.offsetX = this.offsetX;
                }
                if (this.offsetY)
                {
                    _loc_2.offsetY = this.offsetY;
                }
                _loc_2.forcedHeight = this.forcedHeight > 0 ? (this.forcedHeight) : (_loc_2.image.height);
            }
            else
            {
                _loc_2 = null;
            }
            return _loc_2;
        }//end

        protected BitmapData  createBitmapDataFromMovieClip (MovieClip param1 )
        {
            BitmapData _loc_2 =new BitmapData(param1.width ,param1.height ,true ,0);
            _loc_3 = Global.stage.quality;
            GlobalEngine.stage.quality = StageQuality.HIGH;
            _loc_4 = (MovieClip)param1.getChildAt(0)
            if ((MovieClip)param1.getChildAt(0))
            {
                _loc_4.gotoAndPlay(0);
            }
            _loc_2.draw(param1, null, null, null, null, true);
            GlobalEngine.stage.quality = _loc_3;
            return _loc_2;
        }//end

        protected DisplayObject  generateFinalImage (MapResource param1 )
        {
            DisplayObject _loc_2 =null ;
            ItemImageAsset _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            double _loc_7 =0;
            boolean _loc_8 =false ;
            ItemImageAsset _loc_9 =null ;
            ItemImageAsset _loc_10 =null ;
            ItemImageAsset _loc_11 =null ;
            String _loc_12 =null ;
            int _loc_13 =0;
            Loader _loc_14 =null ;
            CompositeItemImage _loc_15 =null ;
            int _loc_16 =0;
            ItemImage _loc_17 =null ;
            ItemImageInstance _loc_18 =null ;
            if (this.isLoaded())
            {
                if (this.m_assets.length > 0)
                {
                    _loc_3 =(ItemImageAsset) this.m_assets.get(0);
                    _loc_4 = int(this.m_xml.@numFrames);
                    _loc_5 = int(this.m_xml.@frameWidth);
                    _loc_6 = int(this.m_xml.@frameHeight);
                    _loc_7 = Number(this.m_xml.@fps);
                    _loc_8 = this.m_xml.@playOnce.toString() == "true";

                    if(isDebug) {
                    	Debug.debug4("ItemImage.generateFinalImage."+this.m_className);
                    }
                    switch(this.m_className)
                    {
                        case "AnimatedBitmap":
                        {
			    if(isDebug) {
				Debug.debug4("ItemImage.generateFinalImage.2" + _loc_8);
			    }

                            _loc_2 = new AnimatedBitmapOptimized(_loc_3.bitmapData, _loc_4, _loc_5, _loc_6, _loc_7, disposeSpriteSheets);
                            if (param1 != null)
                            {
                                ((AnimatedBitmapOptimized)_loc_2).enable = param1.doFramerateAnimOpt;
                                ((AnimatedBitmapOptimized)_loc_2).roundRobbin = param1.doRoundRobbinAnimOpt;
                                ((AnimatedBitmapOptimized)_loc_2).playOnce = _loc_8;
                            }
                            break;
                        }
                        case "AnimatedShaderBitmap":
                        {
                            _loc_9 = this.getAssetByName("animation");
                            _loc_10 = this.getAssetByName("texture");

                            _loc_2 = new AnimatedShaderBitmap(_loc_9.bitmapData, _loc_10.bitmapData, _loc_4, _loc_5, _loc_6, _loc_7, disposeSpriteSheets);
                            if (param1 != null)
                            {
                                ((AnimatedShaderBitmap)_loc_2).enable = param1.doFramerateAnimOpt;
                                ((AnimatedShaderBitmap)_loc_2).roundRobbin = param1.doRoundRobbinAnimOpt;
                            }
                            break;
                        }
                        case "ColorMaskedBitmap":
                        {
                            _loc_11 = this.getAssetByName("mask");
                            if (_loc_11 != null && _loc_11.bitmapData)
                            {
                                _loc_12 = String(this.m_xml.@color);
                                _loc_13 = 16777215;
                                if (_loc_12.length > 0)
                                {
                                    _loc_13 = Utilities.hexColorToIntColor(_loc_12);
                                }
                                _loc_2 = new ColorMaskedBitmap(_loc_11.bitmapData, _loc_3.bitmapData, _loc_4, _loc_5, _loc_6, _loc_7, _loc_13);
                            }
                            break;
                        }
                        default:
                        {
                            if (_loc_3.bitmapData != null)
                            {
                                _loc_2 = new Bitmap(_loc_3.bitmapData);
                            }
                            else if (_loc_3.contentClass != null)
                            {
                                _loc_2 = new _loc_3.contentClass();
                            }
                            else if (_loc_3.isLoaded() && _loc_3.content && _loc_3.content.loaderInfo)
                            {
                                _loc_14 = new Loader();
                                _loc_14.loadBytes(_loc_3.content.loaderInfo.bytes);
                                _loc_2 = _loc_14;
                            }
                            break;
                            break;
                        }
                    }
                }
                else if (this.m_images.length > 0)
                {
                    _loc_15 = new CompositeItemImage();
                    _loc_16 = 0;
                    while (_loc_16 < this.m_images.length())
                    {

                        _loc_17 = this.m_images.get(_loc_16);
                        _loc_18 = _loc_17.getInstance(param1);
                        if (_loc_18 && _loc_18.image)
                        {
                            _loc_18.image.x = _loc_17.offsetX;
                            _loc_18.image.y = _loc_17.offsetY;
                            _loc_18.image.name = _loc_17.type;

                            if(isDebug) {
                                     Debug.debug4("ItemImage.CompositeItemImage.addChild" + _loc_18.image);
                            }

                            _loc_15.addChild((DisplayObject)_loc_18.image);
                        }
                        _loc_16++;
                    }
                    _loc_2 = _loc_15;
                }
            }
            return _loc_2;
        }//end

        private static boolean  lazyInit ()
        {
            return s_lazyInit;
        }//end

        public static String  getDirectionCode (int param1 )
        {
            String _loc_2 =null ;
            if (m_directionCodes && m_directionCodes.length > param1)
            {
                _loc_2 = m_directionCodes.get(param1);
            }
            return _loc_2;
        }//end

    }




