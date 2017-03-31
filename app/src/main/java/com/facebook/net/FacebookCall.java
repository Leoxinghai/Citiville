package com.facebook.net;

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
import com.facebook.delegates.*;
import com.facebook.errors.*;
import com.facebook.events.*;
import com.facebook.session.*;
//import flash.events.*;
//import flash.net.*;

    public class FacebookCall extends EventDispatcher
    {
        public String method ;
        public boolean success =false ;
        public URLVariables args ;
        public IFacebookCallDelegate delegate ;
        public FacebookError error ;
        public boolean useSession =true ;
        public IFacebookSession session ;
        public FacebookData result ;

        public  FacebookCall (String param1,URLVariables param2 )
        {
            this.method = param1;
            this.args = param2 != null ? (param2) : (new URLVariables());
            return;
        }//end

        public void  handleError (FacebookError param1 )
        {
            this.error = param1;
            this.success = false;
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, param1));
            return;
        }//end

        public void  handleResult (FacebookData param1 )
        {
            this.result = param1;
            this.success = true;
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, true, param1));
            return;
        }//end

        public void  setRequestArgument (String param1 ,Object param2 )
        {
            if (param2 instanceof Number && isNaN(param2 as Number))
            {
                return;
            }
            if (param1 && param2 != null && String(param2).length > 0)
            {
                this.args.put(param1,  param2);
            }
            return;
        }//end

        public void  clearRequestArguments ()
        {
            this.args = new URLVariables();
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        protected void  applySchema (Array param1 ,...args )
        {
            int argslen =param1.length ;
            int _loc_4 =0;
            while (_loc_4 < argslen)
            {

                this.setRequestArgument(param1.get(_loc_4), args.get(_loc_4));
                _loc_4 = _loc_4 + 1;
            }
            return;
        }//end

    }


