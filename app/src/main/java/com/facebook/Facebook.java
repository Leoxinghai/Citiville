package com.facebook;

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

import com.facebook.commands.auth.*;
import com.facebook.delegates.*;
import com.facebook.events.*;
import com.facebook.net.*;
import com.facebook.session.*;
//import flash.events.*;
//import flash.net.*;

    public class Facebook extends EventDispatcher
    {
        public boolean waiting_for_login ;
        protected IFacebookSession _currentSession ;
        public String connectionErrorMessage ;

        public void  Facebook ()
        {
            return;
        }//end

        public FacebookCall  post (FacebookCall param1 )
        {
            IFacebookCallDelegate _loc_2 =null ;
            if (this._currentSession)
            {
                param1.session = this._currentSession;
                param1.initialize();
                _loc_2 = this._currentSession.post(param1);
                param1.delegate = _loc_2;
            }
            else
            {
                throw new Error("Cannot post a call; no session has been set.");
            }
            return param1;
        }//end

        public void  startSession (IFacebookSession param1 )
        {
            this._currentSession = param1;
            if (this._currentSession.is_connected)
            {
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true));
            }
            else
            {
                this._currentSession.addEventListener(FacebookEvent.CONNECT, this.onSessionConnected);
                this._currentSession.addEventListener(FacebookEvent.WAITING_FOR_LOGIN, this.onWaitingForLogin);
            }
            return;
        }//end

        public void  grantExtendedPermission (String param1 )
        {
            navigateToURL(new URLRequest("http://www.facebook.com/authorize.php?api_key=" + this.api_key + "&v=" + this.api_version + "&ext_perm=" + param1), "_blank");
            return;
        }//end

        public void  refreshSession ()
        {
            this._currentSession.refreshSession();
            return;
        }//end

        public void  logout ()
        {
            ExpireSession _loc_1 =new ExpireSession ();
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onLoggedOut, false, 0, true);
            this.post(_loc_1);
            return;
        }//end

        public String  api_version ()
        {
            return this._currentSession ? (this._currentSession.api_version) : (null);
        }//end

        protected void  onLoggedOut (FacebookEvent event )
        {
            if (event.success == true)
            {
                this._currentSession.session_key = null;
            }
            dispatchEvent(new FacebookEvent(FacebookEvent.LOGOUT, false, false, event.success, event.data, event.error));
            return;
        }//end

        protected void  onWaitingForLogin (FacebookEvent event )
        {
            this.waiting_for_login = true;
            dispatchEvent(new FacebookEvent(FacebookEvent.WAITING_FOR_LOGIN));
            return;
        }//end

        public void  login (boolean param1 )
        {
            this._currentSession.login(param1);
            return;
        }//end

        public String  secret ()
        {
            return this._currentSession ? (this._currentSession.secret) : (null);
        }//end

        public void  grantPermission (boolean param1 )
        {
            String _loc_2 ="http://www.facebook.com/login.php?return_session="+(param1 ? (1) : (0)) + "&api_key=" + this.api_key;
            navigateToURL(new URLRequest(_loc_2), "_blank");
            return;
        }//end

        public boolean  is_connected ()
        {
            return this._currentSession ? (this._currentSession.is_connected) : (false);
        }//end

        public String  session_key ()
        {
            return this._currentSession ? (this._currentSession.session_key) : (null);
        }//end

        public String  uid ()
        {
            return this._currentSession ? (this._currentSession.uid) : (null);
        }//end

        protected void  onSessionConnected (FacebookEvent event )
        {
            _loc_2 =(IFacebookSession) event.target;
            dispatchEvent(event);
            return;
        }//end

        public String  api_key ()
        {
            return this._currentSession ? (this._currentSession.api_key) : (null);
        }//end

        public Date  expires ()
        {
            return this._currentSession ? (this._currentSession.expires) : (new Date());
        }//end

    }


