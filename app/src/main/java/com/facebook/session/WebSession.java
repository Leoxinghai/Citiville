package com.facebook.session;

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

import com.facebook.*;
import com.facebook.delegates.*;
import com.facebook.events.*;
import com.facebook.net.*;
//import flash.events.*;

    public class WebSession extends EventDispatcher implements IFacebookSession
    {
        protected boolean _is_connected =false ;
        public String login_url ="http://www.facebook.com/login.php";
        protected String _secret ;
        protected String _rest_url ="http://api.facebook.com/restserver.php";
        protected String _api_version ="1.0";
        protected Date _expires ;
        protected String _session_key ;
        protected String _api_key ;
        public static  String VIDEO_URL ="http://api-video.facebook.com/restserver.php";
        public static  String REST_URL ="http://api.facebook.com/restserver.php";

        public  WebSession (String param1 ,String param2 ,String param3 =null )
        {
            this._api_key = param1;
            this._session_key = param3;
            this.secret = param2;
            return;
        }//end

        public boolean  waiting_for_login ()
        {
            return false;
        }//end

        public String  rest_url ()
        {
            return this._rest_url;
        }//end

        public void  rest_url (String param1 )
        {
            this._rest_url = param1;
            return;
        }//end

        public IFacebookCallDelegate  post (FacebookCall param1 )
        {
            this.rest_url = REST_URL;
            if (param1 instanceof IUploadPhoto)
            {
                return new WebImageUploadDelegate(param1, this);
            }
            if (param1 instanceof IUploadVideo)
            {
                this.rest_url = VIDEO_URL;
                return new VideoUploadDelegate(param1, this);
            }
            return new WebDelegate(param1, this);
        }//end

        public String  secret ()
        {
            return this._secret;
        }//end

        public Date  expires ()
        {
            return this._expires;
        }//end

        public String  api_key ()
        {
            return this._api_key;
        }//end

        public void  refreshSession ()
        {
            return;
        }//end

        public String  session_key ()
        {
            return this._session_key;
        }//end

        public String  uid ()
        {
            return "1234";
            //return facebook_internal::_uid;
        }//end

        public String  api_version ()
        {
            return this._api_version;
        }//end

        public boolean  is_connected ()
        {
            return this._is_connected;
        }//end

        public void  secret (String param1 )
        {
            this._secret = param1;
            return;
        }//end

        public void  verifySession ()
        {
            if (this._session_key)
            {
                this._is_connected = true;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true));
            }
            else
            {
                this._is_connected = false;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, false));
            }
            return;
        }//end

        public void  api_version (String param1 )
        {
            this._api_version = param1;
            return;
        }//end

        public void  login (boolean param1 )
        {
            return;
        }//end

        public void  session_key (String param1 )
        {
            this._session_key = param1;
            return;
        }//end

    }


