package Modules.ajax;

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
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.net.*;

    public class AjaxRequest
    {
        private URLLoader m_loader ;
        private URLRequest m_request ;
        private Function m_callback ;
        private Object m_args ;
        private Object m_result ;

        public  AjaxRequest (String param1 ,Object param2 )
        {
            this.init(param1, param2);
            return;
        }//end

        public Object  results ()
        {
            return this.m_result;
        }//end

        public boolean  success ()
        {
            return this.m_result != null;
        }//end

        public Object  args ()
        {
            return this.m_args;
        }//end

        protected void  init (String param1 ,Object param2 )
        {
            String _loc_4 =null ;
            this.m_loader = new URLLoader();
            this.m_args = param2;
            URLVariables _loc_3 =new URLVariables ();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_4 = param2.get(i0);

                _loc_3.put(_loc_4,  com.adobe.serialization.json.JSON.encode(param2.get(_loc_4)));
            }
            _loc_3.zySig = TransactionManager.additionalSignedParams.zySig;
            _loc_3.zySnid = TransactionManager.additionalSignedParams.zySnid;
            _loc_3.zyAuthHash = TransactionManager.additionalSignedParams.zyAuthHash;
            this.m_request = new URLRequest(Config.BASE_PATH + param1);
            this.m_request.method = URLRequestMethod.POST;
            this.m_request.data = _loc_3;
            return;
        }//end

        public void  send (Function param1)
        {
            this.m_result = null;
            this.m_callback = param1;
            this.m_loader.addEventListener(Event.COMPLETE, this.onLoadComplete);
            this.m_loader.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
            this.m_loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
            this.m_loader.load(this.m_request);
            return;
        }//end

        private void  onLoadComplete (Event event )
        {
            e = event;
            if (this.m_loader.data && this.m_loader.data instanceof String)
            {
                try
                {
                    this.m_result = com.adobe.serialization.json.JSON.decode(this.m_loader.data);
                }
                catch (e:Error)
                {
                }
            }
            if (this.m_callback != null)
            {
                this.m_callback(this);
            }
            this.postComplete();
            return;
        }//end

        private void  onError (Event event )
        {
            if (this.m_callback != null)
            {
                this.m_callback(this);
            }
            this.postComplete();
            return;
        }//end

        private void  postComplete ()
        {
            this.m_callback = null;
            this.m_loader.removeEventListener(Event.COMPLETE, this.onLoadComplete);
            this.m_loader.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            this.m_loader.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
            return;
        }//end

    }



