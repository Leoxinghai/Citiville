package com.facebook.utils;

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

//import flash.utils.*;
    public class PostRequest
    {
        protected String _boundary ="-----";
        protected ByteArray postData ;

        public  PostRequest ()
        {
            this.createPostData();
            return;
        }//end

        public ByteArray  getPostData ()
        {
            this.postData.position = 0;
            return this.postData;
        }//end

        protected void  writeBoundary ()
        {
            this.writeDoubleDash();
            double _loc_1 =0;
            while (_loc_1 < this.boundary.length())
            {

                this.postData.writeByte(this.boundary.charCodeAt(_loc_1));
                _loc_1 = _loc_1 + 1;
            }
            return;
        }//end

        protected void  writeDoubleDash ()
        {
            this.postData.writeShort(11565);
            return;
        }//end

        public void  writeFileData (String param1 ,ByteArray param2 ,String param3 )
        {
            String _loc_4 =null ;
            this.writeBoundary();
            this.writeLineBreak();
            _loc_4 = "Content-Disposition: form-data; filename=\"";
            double _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                this.postData.writeByte(_loc_4.charCodeAt(_loc_5));
                _loc_5 = _loc_5 + 1;
            }
            this.postData.writeUTFBytes(param1);
            this.writeQuotationMark();
            this.writeLineBreak();
            _loc_4 = param3;
            _loc_5 = 0;
            while (_loc_5 < _loc_4.length())
            {

                this.postData.writeByte(_loc_4.charCodeAt(_loc_5));
                _loc_5 = _loc_5 + 1;
            }
            this.writeLineBreak();
            this.writeLineBreak();
            param2.position = 0;
            this.postData.writeBytes(param2, 0, param2.length());
            this.writeLineBreak();
            return;
        }//end

        public void  createPostData ()
        {
            this.postData = new ByteArray();
            this.postData.endian = Endian.BIG_ENDIAN;
            return;
        }//end

        public void  writePostData (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            this.writeBoundary();
            this.writeLineBreak();
            _loc_3 = "Content-Disposition: form-data; name=\"" + param1 + "\"";
            _loc_4 = _loc_3.length ;
            double _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                this.postData.writeByte(_loc_3.charCodeAt(_loc_5));
                _loc_5 = _loc_5 + 1;
            }
            this.writeLineBreak();
            this.writeLineBreak();
            this.postData.writeUTFBytes(param2);
            this.writeLineBreak();
            return;
        }//end

        public String  boundary ()
        {
            return this._boundary;
        }//end

        protected void  writeLineBreak ()
        {
            this.postData.writeShort(3338);
            return;
        }//end

        public void  close ()
        {
            this.writeBoundary();
            this.writeDoubleDash();
            return;
        }//end

        protected void  writeQuotationMark ()
        {
            this.postData.writeByte(34);
            return;
        }//end

        public void  boundary (String param1 )
        {
            this._boundary = param1;
            return;
        }//end

    }


