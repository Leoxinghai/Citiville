package com.xinghai.net;

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

import com.xinghai.Debug;

    public class XHAMFConnection
    {
        protected NetConnection m_netConnection ;
        protected Responder m_responder ;
        protected Function m_statusCallback =null ;
        protected Function m_resultCallback =null ;
        protected String m_functionName ;
        private  int TRANSPORT_FAILURE_AMF_FAULT =1;
        private  int TRANSPORT_FAILURE_NET_STATUS =2;
        private  int TRANSPORT_FAILURE_IO_ERROR =4;
        private  int TRANSPORT_FAILURE_ASYNC_ERROR =4;
        private  int TRANSPORT_FAILURE_SECURITY_ERROR =5;

        public void  XHAMFConnection (Function param1 ,Function param2 )
        {
            this.m_netConnection = new NetConnection();
            this.m_statusCallback = param2;
            this.m_resultCallback = param1;
            this.m_responder = new Responder(this.onResult, this.onFault);
            this.m_netConnection.connect(Config.SERVICES_CITYCHANGE_PATH);
            this.m_netConnection.addEventListener(NetStatusEvent.NET_STATUS, this.onNetStatus, false, 0, true);
            this.m_netConnection.addEventListener(IOErrorEvent.IO_ERROR, this.onIOError, false, 0, true);
            this.m_netConnection.addEventListener(AsyncErrorEvent.ASYNC_ERROR, this.onAsyncError, false, 0, true);
            this.m_netConnection.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onSecurityError, false, 0, true);
            return;
        }//end

        protected void  onResult (Object param1 )
        {
            this.logEvent("Result received.");
            if (this.m_resultCallback != null)
            {
                this.m_resultCallback(param1);
            }
            return;
        }//end

        protected void  onFault (Object param1 )
        {
            String _loc_2 =null ;
            this.logEvent("Fault received.");
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                this.logEvent(_loc_2 + ": " + param1.get(_loc_2));
            }
            if (this.m_statusCallback != null)
            {
                this.m_statusCallback(param1, this.TRANSPORT_FAILURE_AMF_FAULT, "transport failure");
            }
            return;
        }//end

        protected void  onAsyncError (IOErrorEvent event )
        {
            this.logEvent("Async error: " + event.toString());
            if (this.m_statusCallback != null)
            {
                this.m_statusCallback(event, this.TRANSPORT_FAILURE_ASYNC_ERROR, "async failure");
            }
            return;
        }//end

        protected void  onIOError (IOErrorEvent event )
        {
            this.logEvent("IO error: " + event.toString());
            if (this.m_statusCallback != null)
            {
                this.m_statusCallback(event, this.TRANSPORT_FAILURE_IO_ERROR, "io failure");
            }
            return;
        }//end

        protected void  onNetStatus (NetStatusEvent event )
        {
            this.logEvent("Net status error: " + event.toString());
            if (this.m_statusCallback != null)
            {
                this.m_statusCallback(event, this.TRANSPORT_FAILURE_NET_STATUS, "net status failure");
            }
            return;
        }//end

        protected void  onSecurityError (SecurityErrorEvent event )
        {
            this.logEvent("Security error: " + event.toString());
            if (this.m_statusCallback != null)
            {
                this.m_statusCallback(event, this.TRANSPORT_FAILURE_SECURITY_ERROR, "security failure");
            }
            return;
        }//end

        protected void  logEvent (String param1 )
        {
            GlobalEngine.log("AMFConnection", this.m_functionName + ": " + param1);
            return;
        }//end

        public void  call (String param1 ,...args )
        {
            Array argsvalue =.get(param1 ,this.m_responder) ;
            this.m_functionName = param1;
            argsvalue = argsvalue.concat(args);
            this.m_netConnection.call.apply(this.m_netConnection, argsvalue);
            this.logEvent("Call: " + param1);
            return;
        }//end

    }


