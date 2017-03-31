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

import com.facebook.net.*;
import com.facebook.session.*;
import com.facebook.utils.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class AbstractFileUploadDelegate extends WebDelegate
    {
        protected ByteArray ba ;

        public  AbstractFileUploadDelegate (FacebookCall param1 ,WebSession param2 )
        {
            super(param1, param2);
            this.ba = new ByteArray();
            return;
        }//end

        protected void  uploadByteArray (ByteArray param1 )
        {
            String _loc_3 =null ;
            URLRequest _loc_4 =null ;
            PostRequest _loc_2 =new PostRequest ();
            for(int i0 = 0; i0 < call.args.size(); i0++)
            {
            		_loc_3 = call.args.get(i0);

                if (_loc_3 != "data")
                {
                    _loc_2.writePostData(_loc_3, call.args.get(_loc_3));
                }
            }
            _loc_2.writeFileData("fn" + call.args.get("call_id") + "." + this.getExt(), param1, this.getContentType());
            _loc_2.close();
            _loc_4 = new URLRequest();
            _loc_4.method = URLRequestMethod.POST;
            _loc_4.contentType = "multipart/form-data; boundary=" + _loc_2.boundary;
            _loc_4.data = _loc_2.getPostData();
            _loc_4.url = _session.rest_url;
            createURLLoader();
            loader.dataFormat = URLLoaderDataFormat.BINARY;
            loader.load(_loc_4);
            return;
        }//end

        protected void  onFileRefComplete (Event event )
        {
            fileRef =(FileReference) call.args.data;
            this.uploadByteArray(fileRef.get("data"));
            return;
        }//end

         protected void  onDataComplete (Event event )
        {
            String _loc_3 =null ;
            _loc_2 =(ByteArray) event.target.data;
            if (_loc_2 == null)
            {
                super.onDataComplete(event);
            }
            else
            {
                _loc_3 = _loc_2.readUTFBytes(_loc_2.length());
                _loc_2.length = 0;
                _loc_2 = null;
                handleResult(_loc_3);
            }
            return;
        }//end

        protected String  getExt ()
        {
            return null;
        }//end

        protected String  getContentType ()
        {
            return null;
        }//end

    }


