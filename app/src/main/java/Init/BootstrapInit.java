package Init;

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
import Engine.Init.*;
import Engine.Managers.*;
//import flash.events.*;
//import flash.net.*;

    public class BootstrapInit extends InitializationAction
    {
        private String m_settingsUrl ;
        private XML m_rawXml ;
        private boolean m_failover =false ;
        public static  String INIT_ID ="BootstrapInit";

        public  BootstrapInit (String param1 )
        {
            super(INIT_ID);
            this.m_settingsUrl = param1;
            return;
        }//end

        public XML  setting ()
        {
            return this.m_rawXml;
        }//end

         public void  execute ()
        {
            URLLoader loader ;
            Global.bootstrap = this;
            try
            {
                loader = new URLLoader();
                loader.dataFormat = URLLoaderDataFormat.BINARY;
                loader.addEventListener(Event.COMPLETE, this.onConfigXmlLoaded);
                loader .addEventListener (SecurityErrorEvent .SECURITY_ERROR ,void  (SecurityErrorEvent event )
            {
                failOver(event.text);
                return;
            }//end
            );
                loader.load(new URLRequest(this.m_settingsUrl));
            }
            catch (error:SecurityError)
            {
                failOver(error.message + "");
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
            StatsManager.count("flash_security", "error", "bootstrap", _loc_3);
            this.m_failover = true;
            URLLoader _loc_4 =new URLLoader ();
            _loc_4.dataFormat = URLLoaderDataFormat.BINARY;
            _loc_4.addEventListener(Event.COMPLETE, this.onConfigXmlLoaded);
            _loc_4.load(new URLRequest(this.m_settingsUrl + "?failover&t=" + new Date().getTime()));
            return;
        }//end

        private void  onConfigXmlLoaded (Event event )
        {
            String raw ;
            event = event;
            if (this.m_failover)
            {
                StatsManager.count("flash_security", "failover", "bootstrap");
            }
            try
            {
                raw = Utilities.uncompress(event.target.data);
            }
            catch (error:Error)
            {
                raw = event.target.data;
            }
            xml = XML(raw);
            this.m_rawXml = xml;
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



