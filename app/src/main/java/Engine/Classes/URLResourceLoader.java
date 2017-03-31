package Engine.Classes;

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

//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class URLResourceLoader extends ResourceLoader
    {
        private URLLoader m_internalLoader =null ;
        private int m_bytesTotal =0;

        public void  URLResourceLoader (String param1 ,int param2 =0,Function param3 =null ,Function param4 =null )
        {
            super(param1, param2, param3, param4);
            return;
        }//end  

         protected void  makeLoader ()
        {
            URLLoader _loc_1 =new URLLoader ();
            _loc_1.dataFormat = URLLoaderDataFormat.BINARY;
            m_loader = _loc_1;
            return;
        }//end  

         public int  getbytesTotal ()
        {
            if (this.m_internalLoader !== null)
            {
                return this.m_internalLoader.bytesTotal;
            }
            return this.m_bytesTotal;
        }//end  

         protected void  chooseLoad ()
        {
            this.m_bytesTotal = 0;
            this.m_internalLoader = new URLLoader();
            this.m_internalLoader.dataFormat = URLLoaderDataFormat.BINARY;
            this.m_internalLoader.addEventListener(Event.COMPLETE, this.onInternalComplete);
            this.m_internalLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, onHTTPStatus);
            this.m_internalLoader.addEventListener(IOErrorEvent.IO_ERROR, onError);
            this.m_internalLoader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError);
            this.m_internalLoader.addEventListener(ProgressEvent.PROGRESS, onProgress);
            this.m_internalLoader.addEventListener(Event.OPEN, onOpen);
            this.m_internalLoader.load(m_urlRequest);
            return;
        }//end  

         protected EventDispatcher  getEventDispatcher ()
        {
            return m_loader;
        }//end  

         protected void  chooseClose ()
        {
            this.m_bytesTotal = this.m_internalLoader.bytesTotal;
            this.m_internalLoader.removeEventListener(Event.COMPLETE, this.onInternalComplete);
            this.m_internalLoader.removeEventListener(HTTPStatusEvent.HTTP_STATUS, onHTTPStatus);
            this.m_internalLoader.removeEventListener(IOErrorEvent.IO_ERROR, onError);
            this.m_internalLoader.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError);
            this.m_internalLoader.removeEventListener(ProgressEvent.PROGRESS, onProgress);
            this.m_internalLoader.removeEventListener(Event.OPEN, onOpen);
            this.m_internalLoader = null;
            return;
        }//end  

         protected void  invokeCallback (Event event )
        {
            m_completeCallback(event, getStats());
            return;
        }//end  

        private void  onInternalComplete (Event event )
        {
            _loc_2 = (URLLoader)m_loader
            _loc_2.data =(ByteArray) event.target.data;
            _loc_2.bytesLoaded = event.target.bytesLoaded;
            _loc_2.bytesTotal = event.target.bytesTotal;
            _loc_2.dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



