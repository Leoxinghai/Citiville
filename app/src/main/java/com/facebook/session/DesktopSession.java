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
import com.facebook.commands.auth.*;
import com.facebook.commands.users.*;
import com.facebook.data.*;
import com.facebook.data.auth.*;
import com.facebook.delegates.*;
import com.facebook.errors.*;
import com.facebook.events.*;
import com.facebook.net.*;
//import flash.net.*;

    public class DesktopSession extends WebSession implements IFacebookSession
    {
        protected String _auth_token ;
        protected IFacebookCallDelegate loginRequest ;
        protected boolean _waiting_for_login =false ;
        protected boolean _offline_access =false ;

        public  DesktopSession (String param1 ,String param2 ,String param3 =null )
        {
            super(param1, null);
            this._is_connected = false;
            this._secret = param2;
            if (param3)
            {
                this._session_key = param3;
            }
            return;
        }//end

         public IFacebookCallDelegate  post (FacebookCall param1 )
        {
            rest_url = REST_URL;
            if (param1 instanceof IUploadPhoto)
            {
                return new WebImageUploadDelegate(param1, this);
            }
            if (param1 instanceof IUploadVideo)
            {
                rest_url = VIDEO_URL;
                return new VideoUploadDelegate(param1, this);
            }
            return new DesktopDelegate(param1, this);
        }//end

         public boolean  waiting_for_login ()
        {
            return this._waiting_for_login;
        }//end

        protected void  onLogin (FacebookEvent event )
        {
            URLRequest _loc_2 =null ;
            String _loc_3 =null ;
            event.target.removeEventListener(FacebookEvent.COMPLETE, this.onLogin);
            if (event.success)
            {
                this._auth_token = ((StringResultData)event.data).value;
                _loc_2 = new URLRequest();
                _loc_3 = "?";
                if (this._offline_access)
                {
                    _loc_3 = _loc_3 + "ext_perm=offline_access&";
                }
                _loc_2.url = login_url + _loc_3 + "api_key=" + api_key + "&v=" + api_version + "&auth_token=" + this._auth_token;
                navigateToURL(_loc_2, "_blank");
                this._waiting_for_login = true;
                dispatchEvent(new FacebookEvent(FacebookEvent.WAITING_FOR_LOGIN));
            }
            else
            {
                this.onConnectionError(event.error);
            }
            return;
        }//end

        protected void  onConnectionError (FacebookError param1 )
        {
            _is_connected = false;
            dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, false, null, param1));
            return;
        }//end

        protected void  tokenCreated ()
        {
            navigateToURL(new URLRequest(login_url));
            return;
        }//end

         public void  login (boolean param1 )
        {
            this._offline_access = param1;
            _session_key = null;
            CreateToken _loc_2 =new CreateToken ();
            _loc_2.session = this;
            FacebookCall _loc_3 =_loc_2 ;
            _loc_3.initialize();
            _loc_2.addEventListener(FacebookEvent.COMPLETE, this.onLogin);
            this.post(_loc_2);
            return;
        }//end

        protected void  onVerifyLogin (FacebookEvent event )
        {
            FacebookEvent _loc_2 =new FacebookEvent(FacebookEvent.CONNECT );
            _loc_2.success = event.success;
            if (event.success)
            {
                //facebook_internal::_uid = ((StringResultData)event.data).value;
                _loc_2.data = event.data;
                _is_connected = true;
            }
            else
            {
                _loc_2.error = event.error;
                _is_connected = false;
            }
            dispatchEvent(_loc_2);
            return;
        }//end

         public void  verifySession ()
        {
            FacebookCall _loc_1 =null ;
            if (_session_key)
            {
                _loc_1 = new GetLoggedInUser();
                _loc_1.session = this;
                _loc_1.initialize();
                _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onVerifyLogin, false, 0, true);
                this.post(_loc_1);
                dispatchEvent(new FacebookEvent(FacebookEvent.VERIFYING_SESSION));
            }
            else
            {
                _is_connected = false;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT));
            }
            return;
        }//end

        protected void  validateSessionReply (FacebookEvent event )
        {
            GetSessionData _loc_2 =null ;
            if (event.success)
            {
                _loc_2 =(GetSessionData) event.data;
                //facebook_internal::_uid = _loc_2.uid;
                this._session_key = _loc_2.session_key;
                this._expires = _loc_2.expires;
                this._secret = _loc_2.secret == null || _loc_2.secret == "" ? (this._secret) : (_loc_2.secret);
                _is_connected = true;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true, _loc_2));
            }
            else
            {
                this.onConnectionError(event.error);
            }
            return;
        }//end

         public void  refreshSession ()
        {
            this._waiting_for_login = false;
            GetSession _loc_1 =new GetSession(this._auth_token );
            _loc_1.session = this;
            _loc_1.initialize();
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.validateSessionReply);
            this.post(_loc_1);
            return;
        }//end

    }


