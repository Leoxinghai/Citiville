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

import Engine.Events.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import plugin.*;

import com.xinghai.Debug;

    public class ItemImageAsset extends EventDispatcher
    {
        public BitmapData bitmapData ;
        public Class contentClass ;
        public DisplayObject content ;
        public String symbolName ;
        public String name ;
        public int type =0;
        private boolean m_loaded ;
        private boolean m_loadStarted ;
        private Object m_asset ;

        //change by xinghai
        //private String m_url ;
        public String m_url ;

        public static  int TYPE_UNKNOWN =0;
        public static  int TYPE_BITMAPDATA =1;
        public static  int TYPE_MOVIECLIP =2;
        public static int s_count =0;
        public static int s_pixels =0;
        private static Dictionary m_loadedAssets =new Dictionary ();

        public boolean isDebug =false

        public void  ItemImageAsset (XML param1 ,boolean param2 =false )
        {
            String _loc_3 =null ;
            isDebug = param2;

            if (param1.@url != "")
            {
            if(isDebug) {
                this.m_url = Global.getAssetURL(param1.@url);
            	Debug.debug4("ItemImageAsset."+m_url);
            } else {
                this.m_url = Global.getAssetURL(param1.@url);
            }

            }

            this.name = String(param1.@name);
            _loc_2 = (String)(param1.@loadClass);
            if (_loc_2.length > 0)
            {
                this.type = TYPE_BITMAPDATA;
                if (_loc_2 == "mc")
                {
                    this.type = TYPE_MOVIECLIP;
                }
                _loc_3 = String(param1.@itemName);
                this.symbolName = this.name + "_" + _loc_3;
            }
            return;
        }//end

        public void  load ()
        {
            Loader _loc_1 =null ;
            if(isDebug) {
            	Debug.debug4("ItemImageAsset.load "+m_url);
            }


            if (this.m_loaded == false || this.m_loadStarted == false)
            {
                this.m_asset = this.getAssetByUrl(this.m_url);
                if (this.m_asset == null)
                {
                    _loc_1 = LoadingManager.load(this.m_url, this.onImageLoaded);
                    this.m_asset = {loaded:false, loader:_loc_1, url:this.m_url, content:null};
                    m_loadedAssets.put(this.m_url,  this.m_asset);
                }
                else if (this.m_asset.loader)
                {
                    this.m_asset.loader.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onImageLoaded);
                }
                else
                {
                    this.handleContent();
                }
                this.m_loadStarted = true;
            }
            else if (this.m_loaded)
            {
                dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            }
            return;
        }//end

        public boolean  isLoaded ()
        {
            return this.m_loaded;
        }//end

        protected void  loadImage (String param1 )
        {
            return;
        }//end

        protected Object  getAssetByUrl (String param1 )
        {
            return m_loadedAssets.get(param1);
        }//end

        protected void  onImageLoaded (Event event )
        {
            if(isDebug) {
                Debug.debug4("ItemImageAsset.onImageLoaded " + m_url);
            }
            _loc_2 = (LoaderInfo)event.currentTarget
            _loc_3 = _loc_2.loader;
            _loc_3.removeEventListener(Event.COMPLETE, this.onImageLoaded);
            if (this.m_asset)
            {
                this.m_asset.loader = null;
                this.m_asset.content = _loc_3.content;
                this.handleContent();
            }
            return;
        }//end

        protected void  handleContent ()
        {
            MovieClip _loc_2 =null ;
            Class _loc_3 =null ;
            Object _loc_4 =null ;
            this.content =(DisplayObject) this.m_asset.content;
            int _loc_1 =0;
            if (this.content instanceof Bitmap)
            {
                this.bitmapData = ((Bitmap)this.content).bitmapData;
                this.type = TYPE_BITMAPDATA;
            }
            else if (this.content instanceof MovieClip)
            {
                _loc_2 =(MovieClip) this.content;
                if (this.symbolName != null)
                {
                    if (_loc_2.loaderInfo.applicationDomain.hasDefinition(this.symbolName))
                    {
                        _loc_3 =(Class) _loc_2.loaderInfo.applicationDomain.getDefinition(this.symbolName);
                        if (_loc_3)
                        {
                            if (this.type == TYPE_MOVIECLIP)
                            {
                                this.contentClass = _loc_3;
                            }
                            else
                            {
                                this.bitmapData = new _loc_3(0, 0);
                                if (this.bitmapData == null || this.bitmapData.width == 0 || this.bitmapData.height == 0)
                                {
                                    for(int i0 = 0; i0 < " + this.m_url.size(); i0++)
                                    {
                                    		; = " + this.m_url.get(i0);
                            }
                        }
                    }
                    else
                    {
                        ErrorManager.addError("Symbol " + this.symbolName + " not found in " + this.m_url);
                    }
                }
            }
            this.m_loaded = true;
            dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        public static void  trackAsset (String param1 ,Object param2 )
        {
            _loc_3 = (Bitmap)param2
            _loc_4 = ConsoleStub.getInstance();
            if (_loc_3 == null)
            {
                return;
            }
            _loc_6 = s_count+1;
            s_count = _loc_6;
            s_pixels = s_pixels + _loc_3.width * _loc_3.height;
            if (_loc_4 != null)
            {
                _loc_4.channel("Asset", "Locator = " + param1 + ", Pixels = " + (_loc_3.width * _loc_3.height * 4 / 1024) + "KB");
            }
            return;
        }//end

        public static void  printStats ()
        {
            _loc_1 = ConsoleStub.getInstance();
            if (_loc_1 != null)
            {
                _loc_1.channel("Asset", "[ImageAsset] Bitmap = " + s_count + ", Pixels = " + (s_pixels * 4 / 1024) + "KB", 3);
            }
            return;
        }//end

    }



