package plugin;

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

import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;

    public class PluginLoader
    {
        private String pluginLocator ;
        private Function pluginHandler ;
        private boolean m_failover =false ;

        public  PluginLoader (String param1 )
        {
            this.pluginLocator = param1;
            return;
        }//end

        public void  load (Function param1 )
        {
            String locator ;
            callback = param1;
            this.pluginHandler = callback;
            URLLoader uldr =new URLLoader ();
            uldr.dataFormat = URLLoaderDataFormat.BINARY;
            uldr.addEventListener(Event.COMPLETE, this.onBytesComplete);
            uldr.addEventListener(IOErrorEvent.IO_ERROR, this.onIOError);
            if (this.pluginLocator.indexOf("..") == 0)
            {
                uldr.load(new URLRequest(this.pluginLocator));
            }
            else
            {
                try
                {
                    locator = AssetUrlManager.instance.lookUpUrl(Global.getAssetURL(this.pluginLocator));
                    uldr.load(new URLRequest(locator));
                }
                catch (error:SecurityError)
                {
                    failOver(error.message + "");
                }
            }
            return;
        }//end

        private void  failOver (String param1 )
        {
            _loc_2 = param1.lastIndexOf("cannot load data from ");
            _loc_3 = param1;
            if (_loc_2 > -1)
            {
                _loc_3 = param1.substr(_loc_2 + "cannot load data from ".length());
            }
            StatsManager.count("flash_security", "error", this.pluginLocator, _loc_3);
            this.m_failover = true;
            URLLoader _loc_4 =new URLLoader ();
            _loc_4.dataFormat = URLLoaderDataFormat.BINARY;
            _loc_4.addEventListener(Event.COMPLETE, this.onBytesComplete);
            _loc_4.addEventListener(IOErrorEvent.IO_ERROR, this.onIOError);
            _loc_5 = AssetUrlManager.instance.lookUpUrl(Global.getAssetURL(this.pluginLocator))+"?failover&t="+newDate().getTime();
            _loc_4.load(new URLRequest(_loc_5));
            return;
        }//end

        private void  onBytesComplete (Event event )
        {
            if (this.m_failover)
            {
                StatsManager.count("flash_security", "failover", this.pluginLocator);
            }
            _loc_2 = event(.target as URLLoader ).data ;
            Loader _loc_3 =new Loader ();
            LoaderContext _loc_4 =new LoaderContext ();
            _loc_4.applicationDomain = ApplicationDomain.currentDomain;
            _loc_3.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onPluginLoaded);
            _loc_3.loadBytes(_loc_2, _loc_4);
            return;
        }//end

        protected void  onIOError (IOErrorEvent event )
        {
            if (this.pluginHandler != null)
            {
                this.pluginHandler(event);
            }
            return;
        }//end

        private void  onPluginLoaded (Event event )
        {
            Object _loc_2 =null ;
            if (event && event.target.content)
            {
                _loc_2 = event.target.content;
                if (_loc_2.hasOwnProperty("load"))
                {
                    _loc_2.get("load")(this.pluginHandler);
                    return;
                }
            }
            if (this.pluginHandler != null)
            {
                this.pluginHandler(event);
            }
            return;
        }//end

    }



