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
import com.facebook.commands.users.*;
import com.facebook.delegates.*;
import com.facebook.events.*;
import com.facebook.net.*;
//import flash.events.*;

    public class JSSession extends EventDispatcher implements IFacebookSession
    {
        public String _api_key ;
        public String as_swf_name ;
        protected String _session_key ;

        public  JSSession (String param1 ,String param2 )
        {
            this._api_key = param1;
            this.as_swf_name = param2;
            return;
        }//end  

        public boolean  waiting_for_login ()
        {
            return true;
        }//end  

        public Date  expires ()
        {
            return null;
        }//end  

        public String  rest_url ()
        {
            return null;
        }//end  

        public String  session_key ()
        {
            return this._session_key;
        }//end  

        public void  rest_url (String param1 )
        {
            return;
        }//end  

        public void  refreshSession ()
        {
            return;
        }//end  

        protected void  onVerifyLogin (FacebookEvent event )
        {
            if (event.success)
            {
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true));
            }
            else
            {
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, false));
            }
            return;
        }//end  

        public String  uid ()
        {
            return null;
        }//end  

        public boolean  is_sessionless ()
        {
            return true;
        }//end  

        public void  verifySession ()
        {
            GetLoggedInUser _loc_1 =new GetLoggedInUser ();
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onVerifyLogin);
            _loc_1.session = this;
            _loc_1.initialize();
            this.post(_loc_1);
            return;
        }//end  

        public void  secret (String param1 )
        {
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

        public IFacebookCallDelegate  post (FacebookCall param1 )
        {
            return new JSDelegate(param1, this);
        }//end  

        public String  secret ()
        {
            return null;
        }//end  

        public String  api_version ()
        {
            return "1.0";
        }//end  

        public String  api_key ()
        {
            return this._api_key;
        }//end  

        public boolean  is_connected ()
        {
            return true;
        }//end  

    }


