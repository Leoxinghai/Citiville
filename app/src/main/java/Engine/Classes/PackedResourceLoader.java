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

//import flash.display.*;
//import flash.events.*;

    public class PackedResourceLoader extends ResourceLoader
    {
        private String m_assetUrl ;

        public void  PackedResourceLoader (String param1 ,String param2 ,int param3 =0,Function param4 =null ,Function param5 =null )
        {
            this.m_assetUrl = param2;
            super(param1, param3, param4, param5);
            return;
        }//end  

         public boolean  isPackedResource ()
        {
            return true;
        }//end  

         protected double  timeoutLength ()
        {
            return LoaderConstants.PACKED_TIMEOUT_LENGTH;
        }//end  

         protected int  retryCount ()
        {
            return this.archiveLoader.retryCount;
        }//end  

         protected void  retryCount (int param1 )
        {
            this.archiveLoader.retryCount = param1;
            return;
        }//end  

         public String  getURL ()
        {
            return this.m_assetUrl;
        }//end  

         protected void  makeLoader ()
        {
            m_loader = ArchiveLoaderFactory.createLoader(m_url, this.m_assetUrl);
            return;
        }//end  

         protected EventDispatcher  getEventDispatcher ()
        {
            return ((Loader)m_loader).contentLoaderInfo;
        }//end  

         protected void  logError (String param1 ,int param2 ,Object param3 )
        {
            if (!this.archiveLoader.errorLogged)
            {
                this.archiveLoader.errorLogged = true;
                super.logError(param1, param2, param3);
            }
            return;
        }//end  

         protected void  addEventListeners ()
        {
            super.addEventListeners();
            m_loader.addEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHTTPStatus);
            m_loader.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
            m_loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
            return;
        }//end  

         protected void  removeEventListeners ()
        {
            super.removeEventListeners();
            m_loader.removeEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHTTPStatus);
            m_loader.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            m_loader.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
            return;
        }//end  

         protected void  onHTTPStatus (HTTPStatusEvent event )
        {
            super.onHTTPStatus(event);
            return;
        }//end  

         protected void  onError (IOErrorEvent event )
        {
            super.onError(event);
            return;
        }//end  

         protected void  onSecurityError (SecurityErrorEvent event )
        {
            super.onSecurityError(event);
            return;
        }//end  

        private IArchiveLoader  archiveLoader ()
        {
            return (IArchiveLoader)m_loader;
        }//end  

    }



