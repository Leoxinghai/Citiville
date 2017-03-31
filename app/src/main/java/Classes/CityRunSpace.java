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

import Classes.util.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.util.*;

    public class CityRunSpace implements IRunspace
    {
        private Function reloadCallBack ;
        private Array swfBins ;
        private int loadIndex ;
        private URLLoader loader ;
        private boolean libLoaded ;
        private FlaEmbed fla ;
        private Array swfPaths ;
        private Dictionary m_localMCCache ;

        public  CityRunSpace ()
        {
            this.m_localMCCache = new Dictionary();
            this.swfPaths = .get(DelayedAssetLoader.BUTTON_ASSETS);
            return;
        }//end

        public void  loadLibs (Function param1 )
        {
            this.reloadCallBack = param1;
            this.fla = new FlaEmbed();
            this.loadIndex = -1;
            this.swfBins = new Array();
            this.loadNext();
            return;
        }//end

        public ILazyLoadManager  lazyLoadManager ()
        {
            return null;
        }//end

        private void  loadNext ()
        {
            this.loadIndex++;
            if (this.loadIndex >= this.swfPaths.length())
            {
                this.fla.init(this.swfBins, this.reloadCallBack);
                return;
            }
            Global.delayedAssets.get(this.swfPaths.get(this.loadIndex), this.onLoadComplete, 1);
            return;
        }//end

        private void  onLoadComplete (ByteArray param1 ,String param2 )
        {
            this.swfBins.push(param1);
            this.loadNext();
            return;
        }//end

        public ApplicationDomain  getApplicationDomain ()
        {
            return this.fla.getDomain();
        }//end

        public DisplayObject  getAssetChild (String param1 ,String param2 ,String param3 )
        {
            int _loc_8 =0;
            int _loc_9 =0;
            DisplayObject _loc_10 =null ;
            String _loc_11 =null ;
            Class _loc_12 =null ;
            _loc_4 = Global.delayedAssets.cache;
            _loc_5 = param2.length;
            if (!_loc_4.get(param1))
            {
                return null;
            }
            if (!this.m_localMCCache)
            {
                this.m_localMCCache = new Dictionary();
            }
            _loc_6 = (DisplayObjectContainer)_loc_4.get(param1);
            if (!this.m_localMCCache.get(param1))
            {
                this.m_localMCCache.put(param1,  new Dictionary());
                _loc_8 = _loc_6.numChildren;
                _loc_9 = 0;
                while (_loc_9 < _loc_8)
                {

                    _loc_10 = _loc_6.getChildAt(_loc_9);
                    _loc_11 = getQualifiedClassName(_loc_10);
                    _loc_12 =(Class) Object(_loc_10).constructor;
                    if (_loc_11.indexOf(param2) != -1)
                    {
                        this.m_localMCCache.get(param1).get(_loc_11.substr((_loc_5 + 1))) = _loc_12;
                    }
                    _loc_9++;
                }
            }
            _loc_7 = this.m_localMCCache.get(param1).get(param3) ;
            return (DisplayObject)this.m_localMCCache.get(param1).get(param3);
        }//end

        public DisplayObject  getAsset (String param1 )
        {
            String propName ;
            Dictionary cache ;
            String index ;
            DisplayObject obj ;
            Class data ;
            linkageID = param1;
            prefix = linkageID.split("_").get(0);
            prefixLength = prefix(.length+1);
            propName = linkageID.substr(prefixLength);
            cache = Global.delayedAssets.cache;
            nameCache = Global.delayedAssets.nameCache;
            index = nameCache.get(prefix);
            if (cache.get(index) == null)
            {
                Global .delayedAssets .(nameCache.get(prefix) ,void  (Object param1 ,String param2 )
            {
                obj =(DisplayObject) new cache.get(index).get(propName);
                return;
            }//end
            );
            }
            else if (cache.get(index).get(propName))
            {
                data = cache.get(index).get(propName);
                obj =(DisplayObject) new data;
            }
            else
            {
                obj = this.getAssetChild(index, prefix, propName);
            }
            return obj;
        }//end

        public Component  getCustomComponent (String param1 )
        {
            return (Component)this.fla.reflect(param1);
        }//end

        public Class  getClass (String param1 )
        {
            return this.fla.getDefinition(param1);
        }//end

        public String  getImageUrl (String param1 )
        {
            return AssetUrlManager.instance.lookUpUrl(Global.getAssetURL(param1));
        }//end

        public String  getSrcUrl (String param1 )
        {
            return null;
        }//end

    }




