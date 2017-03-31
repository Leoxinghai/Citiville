package by.blooddy.crypto.image;

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

//import flash.system.*;
//import flash.utils.*;

    public class JPEGTableHelper
    {

        public void  JPEGTableHelper ()
        {
            return;
        }//end

        public static ByteArray  createQuantTable (int param1 )
        {
            int _loc_6 =0;
            int _loc_8 =0;
            double _loc_9 =0;
            if (param1 > 100)
            {
                Error.throwError(RangeError, 2006, "quality");
            }
            _loc_2 = param1<=1? (5000) : (param1 < 50 ? (5000 / param1) : (200 - (param1 << 1)));
            _loc_3 = ApplicationDomain.currentDomain.domainMemory;
            ByteArray _loc_4 =new ByteArray ();
            _loc_4.position = 130;
            _loc_4.writeUTFBytes("\x10\x0b\n\x10\x18(3=\f\f\x0e\x13\x1a:<7\x0e\r\x10\x18(9E8\x0e\x11\x16\x1d3WP>\x12\x16%8DmgM\x18#7@Qhq\\1@NWgyxeH\\_bpdgc");
            _loc_4.writeUTFBytes("\x11\x12\x18/cccc\x12\x15\x1aBcccc\x18\x1a8ccccc/Bcccccccccccccccccccccccccccccccccccccc");
            _loc_4.position = 1154;
            _loc_4.writeUTFBytes("");
            _loc_4.length = _loc_4.length + 64;
            if (_loc_4.length < ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH)
            {
                _loc_4.length = ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH;
            }
            ApplicationDomain.currentDomain.domainMemory = _loc_4;
            int _loc_5 =0;
            do
            {

                _loc_6 = ((130 + _loc_5) * _loc_2 + 50) / 100;
                if (_loc_6 < 1)
                {
                    _loc_6 = 1;
                    continue;
                }
                if (_loc_6 > 255)
                {
                    _loc_6 = 255;
                }
            }while (_loc_5++ < 64)

            _loc_5 = 0;
            do
            {

                _loc_6 = ((194 + _loc_5++) * _loc_2 + 50) / 100;
                if (_loc_6 < 1)
                {
                    _loc_6 = 1;
                    continue;
                }
                if (_loc_6 > 255)
                {
                    _loc_6 = 255;
                }
            }while (_loc_5++ < 64)
            _loc_5 = 0;
            int _loc_7 =0;
            do
            {

                _loc_8 = 0;
                do
                {

                    _loc_9 = (1218 + _loc_7) * (1218 + _loc_8) * 8;
                    _loc_5 = _loc_5 + 1;
                    _loc_8 = _loc_8 + 8;
                }while (_loc_8 < 64)
                _loc_7 = _loc_7 + 8;
            }while (_loc_7 < 64)
            ApplicationDomain.currentDomain.domainMemory = _loc_3;
            _loc_4.length = 1154;
            ByteArray _loc_10 =new ByteArray ();
            _loc_10.writeBytes(_loc_4);
            _loc_10.position = 0;
            return _loc_10;
        }//end

        public static ByteArray  createZigZagTable ()
        {
            ByteArray _loc_1 =new ByteArray ();
            _loc_1.writeUTFBytes("");
            _loc_1.position = 0;
            return _loc_1;
        }//end

        public static ByteArray  createHuffmanTable ()
        {
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            _loc_1 = ApplicationDomain.currentDomain.domainMemory;
            ByteArray _loc_2 =new ByteArray ();
            _loc_2.length = 1994;
            if (_loc_2.length < ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH)
            {
                _loc_2.length = ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH;
            }
            ApplicationDomain.currentDomain.domainMemory = _loc_2;
            (_loc_2.position + 1);
            _loc_2.writeUTFBytes("");
            _loc_2.writeUTFBytes("");
            (_loc_2.position + 1);
            _loc_2.writeUTFBytes("");
            Array _loc_4 =.get(197121 ,302321924,104935713,123818259,840200482,144806273,-1045347805,-254717419,1919038244,369756546,437852183,673654309,892611113,959985462,1162101562,1229473606,1431589706,1498961750,1701077850,1768449894,1970565994,2037938038,-2054913158,-1987541114,-1802268022,-1734895979,-1549624679,-1482250844,-1297438296,-1229605709,-1162233673,-976960574,-909588538,-724315446,-656943403,-488514855,-421141277,-353769241,-185339151,-117967115,64249) ;
            int _loc_3 =0;
            do
            {

                _loc_3++;
            }while (_loc_3 < 41)
            _loc_2.position = 208;
            (_loc_2.position + 1);
            _loc_2.writeUTFBytes("");
            _loc_2.writeBytes(_loc_2, 17, 12);
            (_loc_2.position + 1);
            _loc_2.writeUTFBytes("");
            _loc_4 = .get(50462976, 553976849, 1091700273, 1902184273, -2127420909, -1857940472, 163688865, -263048413, -781032939, 874780170, 401679841, 639244568, 707340327, 943142453, 1145256505, 1212630597, 1414744649, 1482118741, 1684232793, 1751606885, 1953720937, 2021095029, -2088600967, -2021227132, -1836414584, -1768581997, -1701209961, -1515936862, -1448564826, -1263291734, -1195919691, -1010648391, -943274556, -758462008, -690629421, -623257385, -437984286, -370612250, -185339158, -117967115, 64249);
            _loc_3 = 0;
            do
            {

                _loc_3++;
            }while (_loc_3 < 41)
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =1;
            do
            {

                _loc_9 = _loc_7;
                _loc_8 = 1;
                while (_loc_8 <= _loc_9)
                {

                    _loc_10 = 416 + (17 + _loc_6) * 3;
                    _loc_6++;
                    _loc_5++;
                    _loc_8 = _loc_8 + 1;
                }
                _loc_5 = _loc_5 << 1;
            }while (_loc_7++ <= 16)
            _loc_5 = 0;
            _loc_6 = 0;
            _loc_7 = 1;
            do
            {

                _loc_9 = 29 + _loc_7++;
                _loc_8 = 1;
                while (_loc_8 <= _loc_9)
                {

                    _loc_10 = 452 + (46 + _loc_6) * 3;
                    _loc_6++;
                    _loc_5++;
                    _loc_8 = _loc_8 + 1;
                }
                _loc_5 = _loc_5 << 1;
            }while (_loc_7++ <= 16)
            _loc_5 = 0;
            _loc_6 = 0;
            _loc_7 = 1;
            do
            {

                _loc_9 = 208 + _loc_7++;
                _loc_8 = 1;
                while (_loc_8 <= _loc_9)
                {

                    _loc_10 = 1205 + (225 + _loc_6) * 3;
                    _loc_6++;
                    _loc_5++;
                    _loc_8 = _loc_8 + 1;
                }
                _loc_5 = _loc_5 << 1;
            }while (_loc_7++ <= 16)
            _loc_5 = 0;
            _loc_6 = 0;
            _loc_7 = 1;
            do
            {

                _loc_9 = 237 + _loc_7++;
                _loc_8 = 1;
                while (_loc_8 <= _loc_9)
                {

                    _loc_10 = 1241 + (254 + _loc_6) * 3;
                    _loc_6++;
                    _loc_5++;
                    _loc_8 = _loc_8 + 1;
                }
                _loc_5 = _loc_5 << 1;
            }while (_loc_7++ <= 16)
            _loc_2.position = 0;
            ApplicationDomain.currentDomain.domainMemory = _loc_1;
            return _loc_2;
        }//end

        public static ByteArray  createCategoryTable ()
        {
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_1 = ApplicationDomain.currentDomain.domainMemory;
            ByteArray _loc_2 =new ByteArray ();
            _loc_2.length = 196605;
            if (_loc_2.length < ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH)
            {
                _loc_2.length = ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH;
            }
            ApplicationDomain.currentDomain.domainMemory = _loc_2;
            int _loc_3 =1;
            int _loc_4 =2;
            int _loc_8 =1;
            do
            {

                _loc_6 = _loc_3;
                _loc_7 = _loc_4;
                do
                {

                    _loc_5 = (32767 + _loc_6) * 3;
                    _loc_6++;
                }while (_loc_6 < _loc_7)
                _loc_6 = -_loc_4 + 1;
                _loc_7 = -_loc_3;
                do
                {

                    _loc_5 = (32767 + _loc_6) * 3;
                    _loc_6++;
                }while (_loc_6 <= _loc_7)
                _loc_3 = _loc_3 << 1;
                _loc_4 = _loc_4 << 1;
            }while (_loc_8++ <= 15)
            _loc_2.position = 0;
            ApplicationDomain.currentDomain.domainMemory = _loc_1;
            return _loc_2;
        }//end

    }



