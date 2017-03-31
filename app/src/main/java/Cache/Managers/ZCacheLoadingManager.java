package Cache.Managers;

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

import Cache.Classes.*;
import Cache.Interfaces.*;
import Cache.Util.*;
import Engine.Classes.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class ZCacheLoadingManager extends LoadingManager
    {
        private IZCache m_zCache ;
        private boolean m_override ;
        private Object m_waiting ;

        public  ZCacheLoadingManager ()
        {
            this.m_override = false;
            this.m_waiting = new Object();
            return;
        }//end

        public IZCache  zCache ()
        {
            return this.m_zCache;
        }//end

        public void  zCache (IZCache param1 )
        {
            this.m_zCache = param1;
            return;
        }//end

        public void  disable ()
        {
            this.m_override = true;
            return;
        }//end

        public void  enable ()
        {
            this.m_override = false;
            return;
        }//end

        public boolean  enabled ()
        {
            return this.m_zCache && !this.m_override;
        }//end

         protected Object loadFromUrlInternal (String param1 ,Object param2 )
        {
            boolean _loc_4 =false ;
            String _loc_5 =null ;
            _loc_3 = null;
            if (this.enabled)
            {
                if (!param2.hasOwnProperty("cache"))
                {
                    _loc_4 = false;
                    for(int i0 = 0; i0 < Config.ASSET_PATHS.size(); i0++)
                    {
                    	_loc_5 = Config.ASSET_PATHS.get(i0);

                        if (param1.indexOf(_loc_5) == 0)
                        {
                            _loc_4 = true;
                            break;
                        }
                    }
                    param2.cache = _loc_4;
                }
                if (param2.cache)
                {
                    _loc_3 = this.loadFromCache(param1, param2);
                }
            }
            if (!_loc_3)
            {
                _loc_3 = super.loadFromUrlInternal(param1, param2);
            }
            return _loc_3;
        }//end

        private Object loadFromCache (String param1 ,Object param2 )
        {
            URLLoader _loc_4 =null ;
            Loader _loc_5 =null ;
            SmartListener _loc_6 =null ;
            int _loc_7 =0;
            boolean _loc_8 =false ;
            Object _loc_9 =null ;
            _loc_10 = undefined;
            ByteArray _loc_11 =null ;
            IEventDispatcher _loc_3 =null ;
            if (this.m_zCache && this.m_zCache.allowed)
            {
                _loc_4 = null;
                _loc_5 = null;
                _loc_7 = param2.hasOwnProperty("priority") ? (param2.priority) : (PRIORITY_NORMAL);
                _loc_8 = param2.hasOwnProperty("resourceLoaderClass") && param2.resourceLoaderClass == URLResourceLoader;
                if (_loc_8)
                {
                    _loc_4 = new URLLoader();
                    _loc_4.dataFormat = URLLoaderDataFormat.BINARY;
                    _loc_6 = new SmartListener(_loc_4);
                    _loc_3 = _loc_4;
                    _loc_6.addEventListener(Event.COMPLETE, param2.completeCallback, true, true, [{url:param1}]);
                }
                else
                {
                    _loc_5 = new Loader();
                    _loc_6 = new SmartListener(_loc_5.contentLoaderInfo);
                    _loc_3 = _loc_5;
                    _loc_6.addEventListener(Event.COMPLETE, param2.completeCallback, true, true);
                }
                _loc_6.addEventListener(IOErrorEvent.IO_ERROR, param2.faultCallback, true);
                _loc_9 = {url:param1, loader:_loc_3, listener:_loc_6, priority:_loc_7};
                _loc_10 = this.m_zCache.get(param1);
                if (_loc_10)
                {
                    _loc_11 =(ByteArray) _loc_10;
                    _loc_11.position = 0;
                    if (_loc_11.bytesAvailable > 0)
                    {
                        if (_loc_4)
                        {
                            _loc_4.bytesLoaded = 0;
                            _loc_4.bytesTotal = _loc_11.length;
                        }
                        this.trackLoadStart(_loc_7);
                        setTimeout(Util.bind(this.onDataAvailable, null, [_loc_9, _loc_11]), 0);
                        return _loc_3;
                    }
                }
                if (this.m_waiting.hasOwnProperty(param1))
                {
                    this.trackLoadStart(_loc_7);
                    this.m_waiting.get(param1).push(_loc_9);
                }
                else
                {
                    this.m_waiting.put(param1,  .get(_loc_9));
                    _loc_4 =(URLLoader) super.loadFromUrlInternal(param1, {completeCallback:this.onDownloadComplete, priority:_loc_7, faultCallback:Util.bind(this.onDownloadFault, null, [param1]), resourceLoaderClass:URLResourceLoader, packUrl:param2.packUrl});
                    if (_loc_8)
                    {
                        _loc_3 = _loc_4;
                    }
                }
            }
            return _loc_3;
        }//end

        private void  onDownloadComplete (Event event ,Object param2 )
        {
            Object _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_10 =0;
            _loc_3 = (ByteArray)event.target.data
            _loc_4 = param2.url;
            _loc_5 = event.target.bytesTotal;
            _loc_6 = _loc_3.length;
            _loc_7 = _loc_5!= _loc_6;
            Debug.debug4("ZCacheLoadingManager.onDownloadComplete."+_loc_4);
            for(int i0 = 0; i0 < this.m_waiting.get(_loc_4).size(); i0++)
            {
            	_loc_8 = this.m_waiting.get(_loc_4).get(i0);

                this.onDataAvailable(_loc_8, _loc_3);
            }
            this.clearWaitingLoaders(_loc_4);
            if (_loc_7)
            {
                _loc_9 = _loc_4.substring((_loc_4.lastIndexOf("/") + 1), _loc_4.length());
                _loc_10 = param2.hasOwnProperty("retryCount") ? (param2.retryCount) : (0);
                StatsManager.count("errors", "zcache", "contentlength", _loc_5.toString(), _loc_6.toString(), _loc_9, _loc_10);
            }
            else if (this.m_zCache)
            {
                _loc_3.position = 0;
                this.m_zCache.put(_loc_4, _loc_3, _loc_5);
            }
            return;
        }//end

        private void  onDataAvailable (Object param1 ,ByteArray param2 )
        {
            Loader _loc_3 =null ;
            URLLoader _loc_4 =null ;
            param2.position = 0;
            if (param1.loader instanceof Loader)
            {
                _loc_3 =(Loader) param1.loader;
                param1.listener.addEventListener(Event.COMPLETE, this.onBytesLoaded, true, true, [param1, param2]);
                GlobalEngine.log("Loader", "Start cache load: " + param1.url);
                _loc_3.loadBytes(param2, new LoaderContext(false, ApplicationDomain.currentDomain, null));
            }
            else if (param1.loader instanceof URLLoader)
            {
                _loc_4 =(URLLoader) param1.loader;
                if (!_loc_4.data)
                {
                    GlobalEngine.log("Loader", "Start cache load: " + param1.url);
                    _loc_4.data = param2;
                    _loc_4.bytesLoaded = param2.length;
                    _loc_4.bytesTotal = param2.length;
                    _loc_4.dispatchEvent(new Event(Event.COMPLETE));
                    this.trackLoadComplete(param1.prioriy, param2.length());
                }
            }
            return;
        }//end

        private void  onBytesLoaded (Event event ,Object param2 ,ByteArray param3 )
        {
            this.trackLoadComplete(param2.priority, param3.length());
            return;
        }//end

        private void  onDownloadFault (String param1 ,Event param2 )
        {
            Object _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_waiting.get(param1).size(); i0++)
            {
            	_loc_3 = this.m_waiting.get(param1).get(i0);

                if (_loc_3.loader)
                {
                    if (_loc_3.loader instanceof Loader)
                    {
                        _loc_3.loader.dispatchEvent(new Event(param2.type, param2.bubbles, param2.cancelable));
                        continue;
                    }
                    _loc_3.loader.dispatchEvent(param2);
                }
            }
            this.clearWaitingLoaders(param1);
            return;
        }//end

        private void  clearWaitingLoaders (String param1 )
        {
            this.m_waiting.put(param1,  null);
            delete this.m_waiting.get(param1);
            return;
        }//end

        private void  trackLoadStart (int param1 )
        {
            _loc_3 = m_objectsStarted+1;
            m_objectsStarted = _loc_3;
            if (param1 != PRIORITY_LOW)
            {
                _loc_3 = m_requestsStarted + 1;
                m_requestsStarted = _loc_3;
            }
            return;
        }//end

        private void  trackLoadComplete (int param1 ,int param2 )
        {
            _loc_4 = m_objectsLoaded+1;
            m_objectsLoaded = _loc_4;
            m_bytesLoaded = m_bytesLoaded + param2;
            if (param1 != PRIORITY_LOW)
            {
                onHighPriorityAssetLoaded();
            }
            return;
        }//end

    }




