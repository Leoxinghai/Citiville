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

import com.adobe.serialization.json.*;
import com.facebook.*;
import com.facebook.data.*;
import com.facebook.errors.*;
import com.facebook.net.*;
import com.facebook.session.*;
import com.facebook.utils.*;
//import flash.events.*;
//import flash.external.*;

    public class JSDelegate extends EventDispatcher implements IFacebookCallDelegate
    {
        protected FacebookCall _call ;
        protected JSSession _session ;
public static Object externalInterfaceCalls ={};
public static double externalInterfaceCallId =0;

        public  JSDelegate (FacebookCall param1 ,JSSession param2 )
        {
            this.call = param1;
            this.session = param2;
            this.execute();
            return;
        }//end

        public void  call (FacebookCall param1 )
        {
            this._call = param1;
            return;
        }//end

        protected void  onReceiveError (ErrorEvent event )
        {
            FacebookError _loc_2 =new FacebookError ();
            _loc_2.errorEvent = event;
            FacebookCall _loc_3 =this.call ;
            _loc_3.handleError(_loc_2);
            return;
        }//end

        public FacebookCall  call ()
        {
            return this._call;
        }//end

        protected void  postBridgeAsyncReply (Object param1 ,Object param2 ,int param3 )
        {
            JSONResultData _loc_5 =null ;
            FacebookError _loc_6 =null ;
            _loc_4 = externalInterfaceCalls.get(param3);
            FacebookCall _loc_7 ;
            if (param1 !=null)
            {
                _loc_5 = new JSONResultData();
                _loc_5.result = param1;
                _loc_7 = _loc_4;
                _loc_7.handleResult(_loc_5);
            }
            else
            {
                _loc_6 = new FacebookError();
                _loc_6.rawResult = com.adobe.serialization.json.JSON.encode(param2);
                _loc_7 = _loc_4;
                _loc_7.handleError(_loc_6);
            }
            delete externalInterfaceCalls.get(param3);
            return;
        }//end

        protected String  buildCall ()
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 = externalInterfaceCallId"bridgeFacebookCall_"+;
            RequestHelper.formatRequest(this.call);
            Object _loc_2 ={};
            for(int i0 = 0; i0 < this.call.args.size(); i0++)
            {
            		_loc_3 = this.call.args.get(i0);

                _loc_2.put(_loc_3,  this.call.args.get(_loc_3));
            }
            _loc_4 = "function " + _loc_1 + "() { " + "FB.Facebook.apiClient.callMethod(\"" + this.call.method + "\", " + JavascriptRequestHelper.formatURLVariables(this.call.args) + ", " + "function(result, exception) {" + "document." + (this._session as JSSession).as_swf_name + ".bridgeFacebookReply(result, exception, " + externalInterfaceCallId + ")" + "}" + ");" + "}";
            return _loc_4;
        }//end

        protected void  execute ()
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_1 =new Array();
            for(int i0 = 0; i0 < this.call.args.size(); i0++)
            {
            		_loc_2 = this.call.args.get(i0);

                _loc_1.push(_loc_2);
            }
            externalInterfaceCalls.put(++externalInterfaceCallId,  this.call);
            _loc_3 = this.buildCall();
            ExternalInterface.addCallback("bridgeFacebookReply", this.postBridgeAsyncReply);
            ExternalInterface.call(_loc_3);
            return;
        }//end

        protected void  onReceiveStatus (StatusEvent event )
        {
            FacebookError _loc_2 =null ;
            switch(event.level == "error")
            {
                case "error":
                {
                    _loc_2 = new FacebookError();
                    _loc_2.rawResult = event.level;
                    FacebookCall _loc_3 =this.call ;
                    _loc_3.handleError(_loc_2);
                    break;
                }
                case "warning":
                case "status":
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  session (IFacebookSession param1 )
        {
            this._session =(JSSession) param1;
            return;
        }//end

        public IFacebookSession  session ()
        {
            return this._session;
        }//end

        public void  close ()
        {
            return;
        }//end

    }


