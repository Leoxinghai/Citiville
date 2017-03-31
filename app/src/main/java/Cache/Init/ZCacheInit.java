package Cache.Init;

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
import Cache.Managers.*;
import Engine.Init.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.errors.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;

    public class ZCacheInit extends InitializationAction
    {
        protected String m_url ;
        protected String m_namespace ;
        private Loader m_loader ;
        protected Array m_evictKeys ;
        private IZCache m_zCache ;
        public static  String INIT_ID ="ZCacheInit";
        private static  String LOG_SECTION ="ZCache";

        public  ZCacheInit (String param1 ,String param2 ,Array param3 =null )
        {
            super(INIT_ID);
            this.m_url = param1;
            this.m_namespace = param2;
            this.m_evictKeys = param3;
            return;
        }//end

         public void  execute ()
        {
            this.loadSWF();
            return;
        }//end

        private void  loadSWF ()
        {
            this.m_loader = new Loader();
            this.m_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onSWFLoaded);
            this.m_loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, this.onSWFLoadError);
            LoaderContext context =new LoaderContext ();
            try
            {
                GlobalEngine.log(LOG_SECTION, "Downloading ZCache");
                this.m_loader.load(new URLRequest(this.m_url), context);
            }
            catch (ioe:IOError)
            {
                GlobalEngine.error(LOG_SECTION, "IOError loading ZCache: " + ioe);
                StatsManager.count("errors", "zcache", "io", ioe.message);
                ;
            }
            catch (se:SecurityError)
            {
                GlobalEngine.error(LOG_SECTION, "SecurityError loading ZCache: " + se);
                StatsManager.count("errors", "zcache", "security", se.message);
            }
            return;
        }//end

        private void  removeLoaderEventListeners ()
        {
            this.m_loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, this.onSWFLoaded);
            this.m_loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, this.onSWFLoadError);
            return;
        }//end

        private void  onSWFLoadError (IOErrorEvent event )
        {
            GlobalEngine.error(LOG_SECTION, "IOErrorEvent loading ZCache: " + event);
            StatsManager.count("errors", "zcache", "io", event.text);
            this.removeLoaderEventListeners();
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        private void  onSWFLoaded (Event event )
        {
            if (this.m_loader.contentLoaderInfo.childAllowsParent)
            {
                this.onZCacheLoaded(new ZCacheClient(this.m_loader.content));
            }
            else
            {
                GlobalEngine.error(LOG_SECTION, "Access from the current domain has not been allowed.");
                StatsManager.count("errors", "zcache", "allowed_domain");
            }
            this.removeLoaderEventListeners();
            this.m_loader = null;
            this.onInitComplete();
            return;
        }//end

        protected void  onZCacheLoaded (IZCache param1 )
        {
            LoadingManager _loc_2 =null ;
            String _loc_3 =null ;
            Object _loc_4 =null ;
            if (param1.init(this.m_namespace))
            {
                if (this.m_evictKeys && this.m_evictKeys.length > 0)
                {
                    for(int i0 = 0; i0 < this.m_evictKeys.size(); i0++)
                    {
                    		_loc_3 = this.m_evictKeys.get(i0);

                        if (_loc_3 == "*")
                        {
                            param1.clear();
                            continue;
                        }
                        param1.remove(_loc_3);
                    }
                }
                _loc_2 = LoadingManager.getInstance();
                if (_loc_2 instanceof ZCacheLoadingManager)
                {
                    GlobalEngine.log(LOG_SECTION, "Set ZCache to LoadingManager");
                    ((ZCacheLoadingManager_loc_2).zCache = param1;
                }
                if (param1.allowed)
                {
                    _loc_4 = param1.stats;
                    StatsManager.count("zcache", "stats", "cardinality", String(_loc_4.cardinality));
                    StatsManager.count("zcache", "stats", "size", String(Math.round(_loc_4.size / 1024 / 1024)));
                    StatsManager.count("zcache", "status", "enabled");
                }
                else
                {
                    StatsManager.count("zcache", "status", "disabled");
                }
                this.m_zCache = param1;
                GlobalZCache.instance = this.m_zCache;
                this.onZCacheInit(param1);
                GlobalEngine.log(LOG_SECTION, "Init Complete");
            }
            else
            {
                GlobalEngine.error(LOG_SECTION, "Init failed");
                StatsManager.count("errors", "zcache", "init");
            }
            return;
        }//end

        protected void  onZCacheInit (IZCache param1 )
        {
            return;
        }//end

        protected void  onInitComplete ()
        {
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



