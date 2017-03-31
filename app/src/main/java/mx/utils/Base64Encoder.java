package mx.utils;

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
    public class Base64Encoder
    {
        public boolean insertNewLines =true ;
        private Array _buffers ;
        private int _count ;
        private int _line ;
        private Array _work ;
        public static  String CHARSET_UTF_8 ="UTF-8";
        public static int newLine =10;
        public static  int MAX_BUFFER_SIZE =32767;
        private static  double ESCAPE_CHAR_CODE =61;
        private static  Array ALPHABET_CHAR_CODES =.get(65 ,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,48,49,50,51,52,53,54,55,56,57,43,47) ;

        public  Base64Encoder ()
        {
            this._work = .get(0, 0, 0);
            this.reset();
            return;
        }//end

        public String  drain ()
        {
            Array _loc_3 =null ;
            String _loc_1 ="";
            int _loc_2 =0;
            while (_loc_2 < this._buffers.length())
            {

                _loc_3 =(Array) this._buffers.get(_loc_2);
                _loc_1 = _loc_1 + String.fromCharCode.apply(null, _loc_3);
                _loc_2 = _loc_2 + 1;
            }
            this._buffers = new Array();
            this._buffers.push([]);
            return _loc_1;
        }//end

        public void  encode (String param1 ,int param2 =0,int param3 =0)
        {
            if (param3 == 0)
            {
                param3 = param1.length;
            }
            _loc_4 = param2;
            _loc_5 = param2+param3 ;
            if (param2 + param3 > param1.length())
            {
                _loc_5 = param1.length;
            }
            while (_loc_4 < _loc_5)
            {

                this._work.put(this._count,  param1.charCodeAt(_loc_4));
                this._count++;
                if (this._count == this._work.length || _loc_5 - _loc_4 == 1)
                {
                    this.encodeBlock();
                    this._count = 0;
                    this._work.put(0,  0);
                    this._work.put(1,  0);
                    this._work.put(2,  0);
                }
                _loc_4 = _loc_4 + 1;
            }
            return;
        }//end

        public void  encodeUTFBytes (String param1 )
        {
            ByteArray _loc_2 =new ByteArray ();
            _loc_2.writeUTFBytes(param1);
            _loc_2.position = 0;
            this.encodeBytes(_loc_2);
            return;
        }//end

        public void  encodeBytes (ByteArray param1 ,int param2 =0,int param3 =0)
        {
            if (param3 == 0)
            {
                param3 = param1.length;
            }
            _loc_4 = param1.position ;
            param1.position = param2;
            _loc_5 = param2;
            _loc_6 = param2+param3 ;
            if (param2 + param3 > param1.length())
            {
                _loc_6 = param1.length;
            }
            while (_loc_5 < _loc_6)
            {

                this._work.put(this._count,  param1.get(_loc_5));
                this._count++;
                if (this._count == this._work.length || _loc_6 - _loc_5 == 1)
                {
                    this.encodeBlock();
                    this._count = 0;
                    this._work.put(0,  0);
                    this._work.put(1,  0);
                    this._work.put(2,  0);
                }
                _loc_5 = _loc_5 + 1;
            }
            param1.position = _loc_4;
            return;
        }//end

        public String  flush ()
        {
            if (this._count > 0)
            {
                this.encodeBlock();
            }
            _loc_1 = this.drain ();
            this.reset();
            return _loc_1;
        }//end

        public void  reset ()
        {
            this._buffers = new Array();
            this._buffers.push([]);
            this._count = 0;
            this._line = 0;
            this._work.put(0,  0);
            this._work.put(1,  0);
            this._work.put(2,  0);
            return;
        }//end

        public String  toString ()
        {
            return this.flush();
        }//end

        private void  encodeBlock ()
        {
            _loc_1 =(Array) this._buffers.get((this._buffers.length -1));
            if (_loc_1.length >= MAX_BUFFER_SIZE)
            {
                _loc_1 = new Array();
                this._buffers.push(_loc_1);
            }
            _loc_1.push(ALPHABET_CHAR_CODES.get((this._work.get(0) & 255) >> 2));
            _loc_1.push(ALPHABET_CHAR_CODES.get((this._work.get(0) & 3) << 4 | (this._work.get(1) & 240) >> 4));
            if (this._count > 1)
            {
                _loc_1.push(ALPHABET_CHAR_CODES.get((this._work.get(1) & 15) << 2 | (this._work.get(2) & 192) >> 6));
            }
            else
            {
                _loc_1.push(ESCAPE_CHAR_CODE);
            }
            if (this._count > 2)
            {
                _loc_1.push(ALPHABET_CHAR_CODES.get(this._work.get(2) & 63));
            }
            else
            {
                _loc_1.push(ESCAPE_CHAR_CODE);
            }
            if (this.insertNewLines)
            {
                _loc_2 = this._line +4;
                this._line = this._line + 4;
                if (_loc_2 == 76)
                {
                    _loc_1.push(newLine);
                    this._line = 0;
                }
            }
            return;
        }//end

    }



