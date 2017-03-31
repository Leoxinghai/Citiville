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

import Engine.Managers.*;
import com.adobe.net.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;

    public class ResourceLoader implements IEventDispatcher
    {
        private int m_retryCount =0;
        protected String m_url =null ;
        private int m_zaspLoadHandle =0;
        private String m_domain =null ;
        private int m_priority =0;
        protected EventDispatcher m_loader =null ;
        protected Function m_completeCallback =null ;
        private Function m_faultCallback =null ;
        protected URLRequest m_urlRequest =null ;
        private LoaderContext m_context =null ;
        private EventDispatcher m_dispatcher =null ;
        private boolean m_dispatchedEvent =false ;
        private Timer m_timeoutTimer ;
        private boolean m_receivedBytes =false ;
        private boolean m_loadStarted =false ;

        public  ResourceLoader (String param1 ,int param2 =0,Function param3 =null ,Function param4 =null )
        {
            url = param1;
            priority = param2;
            completeCallback = param3;
            faultCallback = param4;
            URI uri =new URI(url );
            this.m_dispatcher = new EventDispatcher(this);
            this.m_domain = uri.authority;
            this.m_url = url;
            this.m_priority = priority;
            this.m_completeCallback = completeCallback;
            this.m_faultCallback = faultCallback;
            if (GlobalEngine.zaspManager)
            {
                this.m_zaspLoadHandle = GlobalEngine.zaspManager.zaspWaitStart(priority == LoadingManager.PRIORITY_HIGH ? ("HIPRI_ASSET") : ("ASSET"), this.m_url);
            }
            this.makeLoader();
            this.addEventListeners();
            try
            {
                Security.allowDomain("*");
            }
            catch (error:SecurityError)
            {
            }
            this.m_context = new LoaderContext();
            if (Security.sandboxType == Security.REMOTE)
            {
                this.m_context.securityDomain = SecurityDomain.currentDomain;
            }
            this.m_context.applicationDomain = ApplicationDomain.currentDomain;
            this.m_context.checkPolicyFile = true;
            this.m_urlRequest = new URLRequest(url);
            return;
        }//end

        protected void  makeLoader ()
        {
            Loader _loc_1 =new Loader ();
            this.m_loader = _loc_1;
            return;
        }//end

        public EventDispatcher  getLoader ()
        {
            return this.m_loader;
        }//end

        public double  getPriority ()
        {
            return this.m_priority;
        }//end

        public String  getDomain ()
        {
            return this.m_domain;
        }//end

        public String  getURL ()
        {
            return this.m_url;
        }//end

        public int  getRetryCount ()
        {
            return this.retryCount;
        }//end

        public boolean  isPackedResource ()
        {
            return false;
        }//end

        public int  getbytesTotal ()
        {
            return (this.m_loader as Loader).contentLoaderInfo.bytesTotal;
        }//end

        public Object  getStats ()
        {
            return {url:this.getURL(), priority:this.getPriority(), retryCount:this.getRetryCount(), bytesTotal:this.getbytesTotal()};
        }//end

        protected EventDispatcher  getEventDispatcher ()
        {
            return (this.m_loader as Loader).contentLoaderInfo;
        }//end

        public void  startLoad ()
        {
            if (this.m_loadStarted)
            {
                this.resetLoader();
            }
            this.m_loadStarted = true;
            try
            {
                this.chooseLoad();
                this.m_timeoutTimer = new Timer(this.timeoutLength);
                this.m_timeoutTimer.addEventListener(TimerEvent.TIMER, this.onTimeout);
                this.m_timeoutTimer.start();
                if (GlobalEngine.zaspManager && !this.m_zaspLoadHandle)
                {
                    this.m_zaspLoadHandle = GlobalEngine.zaspManager.zaspWaitStart("ASSET", this.m_url);
                }
            }
            catch (error:Error)
            {
                GlobalEngine.log("Loader", "Start load error: " + m_url + " : " + error.toString());
                dispatchFault(new IOErrorEvent(IOErrorEvent.IO_ERROR, false, false, "ResourceLoader Dispatched IOError Event"), ErrorManager.ERROR_FAILED_TO_LOAD);
            }
            return;
        }//end

        protected void  chooseLoad ()
        {
            (this.m_loader as Loader).load(this.m_urlRequest, this.m_context);
            return;
        }//end

        protected void  chooseClose ()
        {
            (this.m_loader as Loader).close();
            return;
        }//end

        public void  stopLoad ()
        {
            this.stopTimeoutTimer();
            if (this.m_loader != null)
            {
                this.removeEventListeners();
                this.m_dispatchedEvent = true;
                try
                {
                    this.chooseClose();
                }
                catch (error:Error)
                {
                }
            }
            return;
        }//end

        protected void  resetLoader ()
        {
            this.m_dispatchedEvent = false;
            this.m_loadStarted = false;
            this.removeEventListeners();
            try
            {
                this.chooseClose();
            }
            catch (error:Error)
            {
            }
            this.addEventListeners();
            return;
        }//end

        protected double  timeoutLength ()
        {
            return LoaderConstants.TIMEOUT_LENGTH;
        }//end

        protected int  retryCount ()
        {
            return this.m_retryCount;
        }//end

        protected void  retryCount (int param1 )
        {
            this.m_retryCount = param1;
            return;
        }//end

        protected void  onOpen (Event event )
        {
            GlobalEngine.log("Loader", "Start load: " + this.m_url);
            this.stopTimeoutTimer();
            return;
        }//end

        private void  stopTimeoutTimer ()
        {
            if (this.m_zaspLoadHandle > 0)
            {
                GlobalEngine.zaspManager.zaspWaitEnd(this.m_zaspLoadHandle);
                this.m_zaspLoadHandle = 0;
            }
            if (this.m_timeoutTimer)
            {
                this.m_timeoutTimer.stop();
                this.m_timeoutTimer.removeEventListener(TimerEvent.TIMER, this.onTimeout);
                this.m_timeoutTimer = null;
            }
            return;
        }//end

        private void  onTimeout (Event event )
        {
            this.stopTimeoutTimer();
            if (this.m_dispatchedEvent == false)
            {
                GlobalEngine.log("Loader", "Load timed out (try: " + this.retryCount + "): " + this.m_url);
            }
            if (this.retryCount >= LoaderConstants.MAX_RETRIES)
            {
                this.dispatchFault(event, ErrorManager.ERROR_LOAD_TIMED_OUT);
            }
            else
            {
                this.retryCount++;
                this.startLoad();
            }
            return;
        }//end

        private void  onComplete (Event event )
        {
            this.stopTimeoutTimer();
            if (this.m_dispatchedEvent == false)
            {
                this.dispatchEvent(new Event(Event.COMPLETE));
                if (this.m_completeCallback != null)
                {
                    this.invokeCallback(event);
                    this.m_completeCallback = null;
                }
                this.m_dispatchedEvent = true;
                this.removeEventListeners();
            }
            return;
        }//end

        protected void  invokeCallback (Event event )
        {
            this.m_completeCallback(event);
            return;
        }//end

        protected void  onProgress (ProgressEvent event )
        {
            this.m_receivedBytes = true;
            this.stopTimeoutTimer();
            return;
        }//end

        private void  dispatchFault (Event event ,int param2 ,Object param3 =null )
        {
            this.stopTimeoutTimer();
            if (this.m_dispatchedEvent == false)
            {
                this.dispatchEvent(new IOErrorEvent(IOErrorEvent.IO_ERROR, false, false, "ResourceLoader Dispatched IOError Event"));
                this.logError(this.m_url, param2, param3);
                if (this.m_faultCallback != null && event instanceof IOErrorEvent)
                {
                    this.m_faultCallback(event);
                }
                this.m_dispatchedEvent = true;
            }
            return;
        }//end

        protected void  logError (String param1 ,int param2 ,Object param3 )
        {
            ErrorManager.addError(param1, param2, param3);
            return;
        }//end

        protected void  onError (IOErrorEvent event )
        {
            this.stopTimeoutTimer();
            if (this.m_dispatchedEvent == false)
            {
                GlobalEngine.log("Loader", "Load error (try: " + this.retryCount + "): " + this.m_url);
            }
            if (this.retryCount >= LoaderConstants.MAX_RETRIES)
            {
                this.dispatchFault(event, ErrorManager.ERROR_LOAD_IO_ERROR);
            }
            else
            {
                this.retryCount++;
                this.startLoad();
            }
            return;
        }//end

        protected void  onSecurityError (SecurityErrorEvent event )
        {
            if (this.m_dispatchedEvent == false)
            {
                this.stopTimeoutTimer();
                if (this.m_dispatchedEvent == false)
                {
                    GlobalEngine.log("Loader", "Security Load error (try: " + this.retryCount + "): " + this.m_url);
                }
                if (this.retryCount >= LoaderConstants.MAX_RETRIES)
                {
                    this.dispatchFault(event, ErrorManager.ERROR_LOAD_SECURITY_ERROR);
                }
                else
                {
                    this.retryCount++;
                    this.startLoad();
                }
            }
            return;
        }//end

        protected void  onHTTPStatus (HTTPStatusEvent event )
        {
            if (event.status > 0 && event.status != 200 && this.m_dispatchedEvent == false)
            {
                if (this.m_dispatchedEvent == false)
                {
                    GlobalEngine.log("Loader", "HTTP Status error (code: " + event.status + ", try: " + this.retryCount + "): " + this.m_url);
                }
                this.dispatchFault(event, ErrorManager.ERROR_LOAD_HTTP_ERROR, {status:event.status});
            }
            return;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,int param4 =0,boolean param5 =false )
        {
            this.m_dispatcher.addEventListener(param1, param2, param3, param4);
            return;
        }//end

        public boolean  dispatchEvent (Event event )
        {
            return this.m_dispatcher.dispatchEvent(event);
        }//end

        public boolean  hasEventListener (String param1 )
        {
            return this.m_dispatcher.hasEventListener(param1);
        }//end

        public void  removeEventListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            this.m_dispatcher.removeEventListener(param1, param2, param3);
            return;
        }//end

        public boolean  willTrigger (String param1 )
        {
            return this.m_dispatcher.willTrigger(param1);
        }//end

        protected void  addEventListeners ()
        {
            _loc_1 = this.getEventDispatcher ();
            _loc_1.addEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHTTPStatus);
            _loc_1.addEventListener(Event.COMPLETE, this.onComplete);
            _loc_1.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
            _loc_1.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
            _loc_1.addEventListener(ProgressEvent.PROGRESS, this.onProgress);
            _loc_1.addEventListener(Event.OPEN, this.onOpen);
            return;
        }//end

        protected void  removeEventListeners ()
        {
            _loc_1 = this.getEventDispatcher ();
            _loc_1.removeEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHTTPStatus);
            _loc_1.removeEventListener(Event.COMPLETE, this.onComplete);
            _loc_1.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            _loc_1.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError);
            _loc_1.removeEventListener(ProgressEvent.PROGRESS, this.onProgress);
            _loc_1.removeEventListener(Event.OPEN, this.onOpen);
            return;
        }//end

    }



