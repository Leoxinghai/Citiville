package com.adobe.images;

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

//import flash.display.*;
//import flash.utils.*;

    public class PNGEncoder
    {
        private static Array crcTable ;
        private static boolean crcTableComputed =false ;

        public  PNGEncoder ()
        {
            return;
        }//end

        public static ByteArray  encode (BitmapData param1 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_2 = new ByteArray ();
            _loc_2.writeUnsignedInt(2303741511);
            _loc_2.writeUnsignedInt(218765834);
            _loc_3 = new ByteArray ();
            _loc_3.writeInt(param1.width);
            _loc_3.writeInt(param1.height);
            _loc_3.writeUnsignedInt(134610944);
            _loc_3.writeByte(0);
            writeChunk(_loc_2, 1229472850, _loc_3);
            _loc_4 = new ByteArray ();
            int _loc_5 =0;
            while (_loc_5 < param1.height)
            {

                _loc_4.writeByte(0);
                if (!param1.transparent)
                {
                    _loc_7 = 0;
                    while (_loc_7 < param1.width)
                    {

                        _loc_6 = param1.getPixel(_loc_7, _loc_5);
                        _loc_4.writeUnsignedInt(uint((_loc_6 & 16777215) << 8 | 255));
                        _loc_7++;
                    }
                }
                else
                {
                    _loc_7 = 0;
                    while (_loc_7 < param1.width)
                    {

                        _loc_6 = param1.getPixel32(_loc_7, _loc_5);
                        _loc_4.writeUnsignedInt(uint((_loc_6 & 16777215) << 8 | _loc_6 >>> 24));
                        _loc_7++;
                    }
                }
                _loc_5++;
            }
            _loc_4.compress();
            writeChunk(_loc_2, 1229209940, _loc_4);
            writeChunk(_loc_2, 1229278788, null);
            return _loc_2;
        }//end

        private static void  writeChunk (ByteArray param1 ,int param2 ,ByteArray param3 )
        {
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            if (!crcTableComputed)
            {
                crcTableComputed = true;
                crcTable = new Array();
                _loc_9 = 0;
                while (_loc_9 < 256)
                {

                    _loc_8 = _loc_9;
                    _loc_10 = 0;
                    while (_loc_10 < 8)
                    {

                        if (_loc_8 & 1)
                        {
                            _loc_8 = uint(uint(3988292384) ^ uint(_loc_8 >>> 1));
                        }
                        else
                        {
                            _loc_8 = uint(_loc_8 >>> 1);
                        }
                        _loc_10 = _loc_10 + 1;
                    }
                    crcTable.put(_loc_9,  _loc_8);
                    _loc_9 = _loc_9 + 1;
                }
            }
            int _loc_4 =0;
            if (param3 != null)
            {
                _loc_4 = param3.length;
            }
            param1.writeUnsignedInt(_loc_4);
            _loc_5 = param1.position ;
            param1.writeUnsignedInt(param2);
            if (param3 != null)
            {
                param1.writeBytes(param3);
            }
            _loc_6 = param1.position ;
            param1.position = _loc_5;
            _loc_8 = 4294967295;
            int _loc_7 =0;
            while (_loc_7 < _loc_6 - _loc_5)
            {

                _loc_8 = uint(crcTable.get((_loc_8 ^ param1.readUnsignedByte()) & uint(255)) ^ uint(_loc_8 >>> 8));
                _loc_7++;
            }
            _loc_8 = uint(_loc_8 ^ uint(4294967295));
            param1.position = _loc_6;
            param1.writeUnsignedInt(_loc_8);
            return;
        }//end

    }

