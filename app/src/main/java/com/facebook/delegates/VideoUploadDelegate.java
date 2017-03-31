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

import com.facebook.commands.video.*;
import com.facebook.net.*;
import com.facebook.session.*;
import com.facebook.utils.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class VideoUploadDelegate extends AbstractFileUploadDelegate
    {

        public  VideoUploadDelegate (FacebookCall param1 ,WebSession param2 )
        {
            super(param1, param2);
            return;
        }//end

         protected String  getExt ()
        {
            return ((UploadVideo)call).ext;
        }//end

         protected String  getContentType ()
        {
            return "Content-Type: video/" + ((UploadVideo)call).ext;
        }//end

         protected void  sendRequest ()
        {
            ByteArray _loc_1 =null ;
            URLRequest _loc_2 =new URLRequest(_session.rest_url );
            _loc_3 = call.args.data;
            if (PlayerUtils.majorVersion == 9 && _loc_3 instanceof FileReference)
            {
                throw new TypeError("Uploading FileReference with Player 9 instanceof unsupported.  Use ByteArray.");
            }
            if (PlayerUtils.majorVersion == 10 && _loc_3 instanceof FileReference)
            {
                _loc_4 = _loc_3as FileReference ;
                _loc_1 = _loc_4.((FileReference)_loc_3).get("load")();
                fileRef =(FileReference) _loc_3;
                fileRef.addEventListener(Event.COMPLETE, onFileRefComplete);
            }
            else if (_loc_3 instanceof ByteArray)
            {
                uploadByteArray((ByteArray)_loc_3);
            }
            else
            {
                throw new Error("Error data type " + call.args.data + " instanceof not supported.  Please use one of the following types:  FileReference or ByteArray.");
            }
            return;
        }//end

    }


