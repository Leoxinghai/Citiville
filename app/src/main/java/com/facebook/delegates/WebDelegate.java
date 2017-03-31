package com.facebook.delegates;

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

import com.facebook.data.*;
import com.facebook.errors.*;
import com.facebook.events.*;
import com.facebook.net.*;
import com.facebook.session.*;
//import flash.events.*;
//import flash.net.*;

    public class WebDelegate extends EventDispatcher implements IFacebookCallDelegate
    {
        protected FacebookCall _call ;
        protected URLLoader loader ;
        protected WebSession _session ;
        protected XMLDataParser parser ;
        protected FileReference fileRef ;

        public  WebDelegate (FacebookCall param1 ,WebSession param2 )
        {
            this.call = param1;
            this.session = param2;
            this.parser = new XMLDataParser();
            this.execute();
            return;
        }//end

        protected void  onDataComplete (Event event )
        {
            this.handleResult((String)event.target.data);
            return;
        }//end

        public void  call (FacebookCall param1 )
        {
            this._call = param1;
            return;
        }//end

        protected void  createURLLoader ()
        {
            this.loader = new URLLoader();
            this.loader.addEventListener(Event.COMPLETE, this.onDataComplete);
            this.loader.addEventListener(HTTPStatusEvent.HTTP_STATUS, this.onHTTPStatus);
            this.loader.addEventListener(IOErrorEvent.IO_ERROR, this.onError);
            this.loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
            return;
        }//end

        protected void  clean ()
        {
            if (this.loader == null)
            {
                return;
            }
            this.loader.removeEventListener(Event.COMPLETE, this.onDataComplete);
            this.loader.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            this.loader.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onError);
            return;
        }//end

        protected void  addOptionalArguments ()
        {
            this.call.setRequestArgument("ss", true);
            return;
        }//end

        public IFacebookSession  session ()
        {
            return this._session;
        }//end

        public FacebookCall  call ()
        {
            return this._call;
        }//end

        protected void  post ()
        {
            this.addOptionalArguments();
            RequestHelper.formatRequest(this.call);
            this.sendRequest();
            return;
        }//end

        protected void  sendRequest ()
        {
            this.createURLLoader();
            URLRequest _loc_1 =new URLRequest(this._session.rest_url );
            _loc_1.contentType = "application/x-www-form-urlencoded";
            _loc_1.method = URLRequestMethod.POST;
            _loc_1.data = this.call.args;
            trace(_loc_1.url + "?" + unescape(this.call.args.toString()));
            this.loader.dataFormat = URLLoaderDataFormat.TEXT;
            this.loader.load(_loc_1);
            return;
        }//end

        protected void  onError (ErrorEvent event )
        {
            this.clean();
            _loc_2 = this.parser.createFacebookError(event ,this.loader.data );
            this.call.handleError(_loc_2);
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, _loc_2));
            return;
        }//end

        public void  session (IFacebookSession param1 )
        {
            this._session =(WebSession) param1;
            return;
        }//end

        protected void  handleResult (String param1 )
        {
            FacebookData _loc_3 =null ;
            this.clean();
            _loc_2 = this.parser.validateFacebookResponce(param1 );
            if (_loc_2 == null)
            {
                _loc_3 = this.parser.parse(param1, this.call.method);
                this.call.handleResult(_loc_3);
            }
            else
            {
                this.call.handleError(_loc_2);
            }
            return;
        }//end

        protected void  execute ()
        {
            if (this.call == null)
            {
                throw new Error("No call defined.");
            }
            this.post();
            return;
        }//end

        public void  close ()
        {
            try
            {
                this.loader.close();
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        protected void  onHTTPStatus (HTTPStatusEvent event )
        {
            return;
        }//end

    }


