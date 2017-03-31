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

import com.adobe.crypto.*;
import com.facebook.net.*;
import com.facebook.session.*;
//import flash.display.*;
//import flash.net.*;
//import flash.utils.*;

    public class RequestHelper
    {
public static int callID =0;

        public  RequestHelper ()
        {
            return;
        }//end

        public static void  formatRequest (FacebookCall param1 )
        {
            _loc_2 = param1.session ;
            param1.setRequestArgument("v", _loc_2.api_version);
            if (_loc_2.api_key != null)
            {
                param1.setRequestArgument("api_key", _loc_2.api_key);
            }
            if (_loc_2.session_key != null && param1.useSession)
            {
                param1.setRequestArgument("session_key", _loc_2.session_key);
            }
            _loc_3 = new Date ().time +(callID ++).toString ();
            param1.setRequestArgument("call_id", _loc_3);
            param1.setRequestArgument("method", param1.method);
            param1.setRequestArgument("sig", formatSig(param1));
            return;
        }//end

        public static String  formatSig (FacebookCall param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            _loc_6 = null;
            _loc_2 = param1.session ;
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < param1.args.size(); i0++)
            {
            		_loc_4 = param1.args.get(i0);

                _loc_6 = param1.args.get(_loc_4);
                if (_loc_4 !== "sig" && !(_loc_6 instanceof ByteArray) && !(_loc_6 instanceof FileReference) && !(_loc_6 instanceof BitmapData) && !(_loc_6 instanceof Bitmap))
                {
                    _loc_3.push(_loc_4 + "=" + _loc_6.toString());
                }
            }
            _loc_3.sort();
            _loc_5 = _loc_3.join("");
            if (_loc_2.secret != null)
            {
                _loc_5 = _loc_5 + _loc_2.secret;
            }
            return MD5.hash(_loc_5);
        }//end

    }


