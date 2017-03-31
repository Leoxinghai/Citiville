package zasp.Util;

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
    public class BloomFilter
    {
        private Array m_hashFns ;
        private Array m_bloomFns ;
        private ByteArray m_results ;
        private int m_len ;
        public static int RS =0;
        public static int JS =1;
        public static int PJW =2;
        public static int ELF =3;
        public static int BKDR =4;
        public static int SDBM =5;
        public static int DJB =6;
        public static int DEK =7;
        public static int BP =8;
        public static int FNV =9;
        public static int AP =10;

        public  BloomFilter (Array param1 ,int param2 =45)
        {
            hashFns = param1;
            len = param2;
            this.m_hashFns = new Array(RSHash, JSHash, PJWHash, ELFHash, BKDRHash, SDBMHash, DJBHash, DEKHash, BPHash, FNVHash, APHash);
            this.m_bloomFns = new Array();
            insertFns = function(param1param2*,,param3)
            {
                _loc_4 = int(param1);
                m_bloomFns.push(m_hashFns.get(_loc_4));
                return;
            }//end
            ;
            hashFns.forEach(insertFns, this);
            this.m_results = new ByteArray();
            int i ;
            while (i < len)
            {

                this.m_results.writeByte(0);
                i = (i + 1);
            }
            this.m_len = len;
            return;
        }//end

        public void  BloomAdd (String param1 )
        {
            String str =param1 ;
            Function setBit =function(param1 param2 *,,param3 )
            {
                _loc_4 = m_results.get(param2);
                _loc_5 = param1;
                _loc_6 = str.length% (m_len * 8);
                _loc_7 = str.length% (m_len * 8) / 8;
                _loc_8 = _loc_6% 8;
                m_results.put(_loc_7,  m_results.get(_loc_7) | 1 << _loc_8);
                return;
            }//end
            ;
            this.m_bloomFns.forEach(setBit, this);
            return;
        }//end

        public void  BloomClear ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_len)
            {

                this.m_results.put(_loc_1,  0);
                _loc_1++;
            }
            return;
        }//end

        public boolean  BloomCheck (String param1 )
        {
            str = param1;
            getBit = function(param1param2*,,param3)
            {
                _loc_4 = param1;
                int _loc_5 =param1.length % (m_len * 8);
                _loc_6 = m_results.get(int(_loc_5 /8)) ;
                if ((m_results.get(int(_loc_5 / 8)) & 1 << _loc_5 % 8) > 0)
                {
                    return true;
                }
                return false;
            }//end
            ;
            return this.m_bloomFns.every(getBit, this);
        }//end

        public String  checkString ()
        {
            String _loc_1 ="";
            _loc_1 = this.m_results.get(0).toString();
            int _loc_2 =1;
            while (_loc_2 < this.m_len)
            {

                _loc_1 = _loc_1 + ("," + this.m_results.get(_loc_2));
                _loc_2++;
            }
            return _loc_1;
        }//end

        public String  toString ()
        {
            _loc_1 = Base64.encodeByteArray(this.m_results);
            return _loc_1;
        }//end

        public static int  RSHash (String param1 )
        {
            int _loc_2 =378551;
            int _loc_3 =63689;
            int _loc_4 =0;
            int _loc_5 =0;
            while (_loc_5 < param1.length())
            {

                _loc_4 = _loc_4 * _loc_3 + uint(param1.charCodeAt(_loc_5));
                _loc_3 = _loc_3 * _loc_2;
                _loc_5++;
            }
            return _loc_4;
        }//end

        public static int  JSHash (String param1 )
        {
            int _loc_2 =1315423911;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = _loc_2 ^ (_loc_2 << 5) + uint(param1.charCodeAt(_loc_3)) + (_loc_2 >> 2 & 1073741823);
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  PJWHash (String param1 )
        {
            _loc_2 = (int)(4*8);
            _loc_3 = (Int)(_loc_2 *3/4);
            _loc_4 = int(_loc_2/8);
            _loc_5 = int(4294967295)<<uint(_loc_2-_loc_4);
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            int _loc_11 =0;
            while (_loc_11 < param1.length())
            {

                _loc_6 = uint(_loc_6 << _loc_4) + uint(param1.charCodeAt(_loc_11));
                _loc_12 = int(_loc_6& _loc_5);
                _loc_7 = uint(_loc_6 & _loc_5);
                if (_loc_12 != 0)
                {
                    _loc_8 = _loc_7 >> _loc_3 & 255;
                    _loc_9 = ~_loc_5;
                    _loc_10 = _loc_6 ^ _loc_8;
                    _loc_6 = _loc_10 & _loc_9;
                }
                _loc_11++;
            }
            return _loc_6;
        }//end

        public static int  ELFHash (String param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_2 = uint(_loc_2 << 4) + uint(param1.charCodeAt(_loc_4));
                _loc_3 = _loc_2 & 4026531840;
                if (_loc_3 != 0)
                {
                    _loc_2 = _loc_2 ^ _loc_3 >> 24 & 255;
                }
                _loc_2 = _loc_2 & ~_loc_3;
                _loc_4++;
            }
            return _loc_2;
        }//end

        public static int  BKDRHash (String param1 )
        {
            int _loc_2 =131;
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_3 = uint(_loc_3 * _loc_2) + uint(param1.charCodeAt(_loc_4));
                _loc_4++;
            }
            return _loc_3;
        }//end

        public static int  SDBMHash (String param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = uint(param1.charCodeAt(_loc_3)) + (_loc_2 << 6) + (_loc_2 << 16) - _loc_2;
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  DJBHash (String param1 )
        {
            int _loc_2 =5381;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = (_loc_2 << 5) + _loc_2 + uint(param1.charCodeAt(_loc_3));
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  DEKHash (String param1 )
        {
            _loc_2 = param1.length ;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = _loc_2 << 5 ^ _loc_2 >> 27 & 31 ^ uint(param1.charCodeAt(_loc_3));
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  BPHash (String param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_2 = _loc_2 << 7 ^ uint(param1.charCodeAt(_loc_3));
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  FNVHash (String param1 )
        {
            int _loc_2 =2166136261;
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_3 = _loc_3 * _loc_2;
                _loc_3 = _loc_3 ^ uint(param1.charCodeAt(_loc_4));
                _loc_4++;
            }
            return _loc_3;
        }//end

        public static int  APHash (String param1 )
        {
            int _loc_3 =0;
            int _loc_2 =2863311530;
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_3 = uint(param1.charCodeAt(_loc_4));
                if ((_loc_4 & 1) == 0)
                {
                    _loc_2 = _loc_2 ^ (_loc_2 << 7 ^ _loc_3 * (_loc_2 >> 3 & 536870911));
                }
                else
                {
                    _loc_2 = _loc_2 ^ ~((_loc_2 << 11) + _loc_3 ^ _loc_2 >> 5 & 134217727);
                }
                _loc_4++;
            }
            return _loc_2;
        }//end

    }




