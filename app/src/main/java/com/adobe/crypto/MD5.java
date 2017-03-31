package com.adobe.crypto;

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

import com.adobe.utils.*;
    public class MD5
    {

        public  MD5 ()
        {
            return;
        }//end

        public static String  hash (String param1 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_2 =1732584193;
            int _loc_3 =-271733879;
            int _loc_4 =-1732584194;
            int _loc_5 =271733878;
            _loc_10 = createBlocks(param1);
            _loc_11 = createBlocks(param1).length;
            int _loc_12 =0;
            while (_loc_12 < _loc_11)
            {

                _loc_6 = _loc_2;
                _loc_7 = _loc_3;
                _loc_8 = _loc_4;
                _loc_9 = _loc_5;
                _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 0), 7, -680876936);
                _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get((_loc_12 + 1)), 12, -389564586);
                _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 2), 17, 606105819);
                _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 3), 22, -1044525330);
                _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 4), 7, -176418897);
                _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 5), 12, 1200080426);
                _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 6), 17, -1473231341);
                _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 7), 22, -45705983);
                _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 8), 7, 1770035416);
                _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 9), 12, -1958414417);
                _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 10), 17, -42063);
                _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 11), 22, -1990404162);
                _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 12), 7, 1804603682);
                _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 13), 12, -40341101);
                _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 14), 17, -1502002290);
                _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 15), 22, 1236535329);
                _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get((_loc_12 + 1)), 5, -165796510);
                _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 6), 9, -1069501632);
                _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 11), 14, 643717713);
                _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 0), 20, -373897302);
                _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 5), 5, -701558691);
                _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 10), 9, 38016083);
                _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 15), 14, -660478335);
                _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 4), 20, -405537848);
                _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 9), 5, 568446438);
                _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 14), 9, -1019803690);
                _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 3), 14, -187363961);
                _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 8), 20, 1163531501);
                _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 13), 5, -1444681467);
                _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 2), 9, -51403784);
                _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 7), 14, 1735328473);
                _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 12), 20, -1926607734);
                _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 5), 4, -378558);
                _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 8), 11, -2022574463);
                _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 11), 16, 1839030562);
                _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 14), 23, -35309556);
                _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get((_loc_12 + 1)), 4, -1530992060);
                _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 4), 11, 1272893353);
                _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 7), 16, -155497632);
                _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 10), 23, -1094730640);
                _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 13), 4, 681279174);
                _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 0), 11, -358537222);
                _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 3), 16, -722521979);
                _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 6), 23, 76029189);
                _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 9), 4, -640364487);
                _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 12), 11, -421815835);
                _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 15), 16, 530742520);
                _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 2), 23, -995338651);
                _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 0), 6, -198630844);
                _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 7), 10, 1126891415);
                _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 14), 15, -1416354905);
                _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 5), 21, -57434055);
                _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 12), 6, 1700485571);
                _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 3), 10, -1894986606);
                _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 10), 15, -1051523);
                _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get((_loc_12 + 1)), 21, -2054922799);
                _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 8), 6, 1873313359);
                _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 15), 10, -30611744);
                _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 6), 15, -1560198380);
                _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 13), 21, 1309151649);
                _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10.get(_loc_12 + 4), 6, -145523070);
                _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10.get(_loc_12 + 11), 10, -1120210379);
                _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10.get(_loc_12 + 2), 15, 718787259);
                _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10.get(_loc_12 + 9), 21, -343485551);
                _loc_2 = _loc_2 + _loc_6;
                _loc_3 = _loc_3 + _loc_7;
                _loc_4 = _loc_4 + _loc_8;
                _loc_5 = _loc_5 + _loc_9;
                _loc_12 = _loc_12 + 16;
            }
            return IntUtil.toHex(_loc_2) + IntUtil.toHex(_loc_3) + IntUtil.toHex(_loc_4) + IntUtil.toHex(_loc_5);
        }//end

        private static int  f (int param1 ,int param2 ,int param3 )
        {
            return param1 & param2 | ~param1 & param3;
        }//end

        private static int  g (int param1 ,int param2 ,int param3 )
        {
            return param1 & param3 | param2 & ~param3;
        }//end

        private static int  h (int param1 ,int param2 ,int param3 )
        {
            return param1 ^ param2 ^ param3;
        }//end

        private static int  i (int param1 ,int param2 ,int param3 )
        {
            return param2 ^ (param1 | ~param3);
        }//end

        private static int  transform (Function param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 ,int param8 )
        {
            _loc_9 = param2+param1(param3,param4,param5)+param6+param8;
            return IntUtil.rol(_loc_9, param7) + param3;
        }//end

        private static int  ff (int param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 )
        {
            return transform(f, param1, param2, param3, param4, param5, param6, param7);
        }//end

        private static int  gg (int param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 )
        {
            return transform(g, param1, param2, param3, param4, param5, param6, param7);
        }//end

        private static int  hh (int param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 )
        {
            return transform(h, param1, param2, param3, param4, param5, param6, param7);
        }//end

        private static int  ii (int param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 )
        {
            return transform(i, param1, param2, param3, param4, param5, param6, param7);
        }//end

        private static Array  createBlocks (String param1 )
        {
            _loc_2 = new Array ();
            _loc_3 = param1.length *8;
            int _loc_4 =255;
            int _loc_5 =0;
            while (_loc_5 < _loc_3)
            {

                _loc_2.put(_loc_5 >> 5,  _loc_2.get(_loc_5 >> 5) | (param1.charCodeAt(_loc_5 / 8) & _loc_4) << _loc_5 % 32);
                _loc_5 = _loc_5 + 8;
            }
            _loc_2.put(_loc_3 >> 5,  _loc_2.get(_loc_3 >> 5) | 128 << _loc_3 % 32);
            _loc_2.put((_loc_3 + 64 >>> 9 << 4) + 14,  _loc_3);
            return _loc_2;
        }//end

    }

