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
    public class Base64
    {
        private static  String BASE64_CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_/-";
        public static  String version ="1.1.0";

        public  Base64 ()
        {
            throw new Error("Base64 class is static container only");
        }//end

        public static String  encode (String param1 )
        {
            ByteArray _loc_2 =new ByteArray ();
            _loc_2.writeUTFBytes(param1);
            return encodeByteArray(_loc_2);
        }//end

        public static String  encodeByteArray (ByteArray param1 )
        {
            Array _loc_3 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            String _loc_2 ="";
            Array _loc_4 =new Array(4);
            param1.position = 0;
            while (param1.bytesAvailable > 0)
            {

                _loc_3 = new Array();
                _loc_5 = 0;
                while (_loc_5 < 3 && param1.bytesAvailable > 0)
                {

                    _loc_3.put(_loc_5,  param1.readUnsignedByte());
                    _loc_5 = _loc_5 + 1;
                }
                _loc_4.put(0,  (_loc_3.get(0) & 252) >> 2);
                _loc_4.put(1,  (_loc_3.get(0) & 3) << 4 | _loc_3.get(1) >> 4);
                _loc_4.put(2,  (_loc_3.get(1) & 15) << 2 | _loc_3.get(2) >> 6);
                _loc_4.put(3,  _loc_3.get(2) & 63);
                _loc_6 = _loc_3.length;
                while (_loc_6 < 3)
                {

                    _loc_4.put((_loc_6 + 1),  64);
                    _loc_6 = _loc_6 + 1;
                }
                _loc_7 = 0;
                while (_loc_7 < _loc_4.length())
                {

                    _loc_2 = _loc_2 + BASE64_CHARS.charAt(_loc_4.get(_loc_7));
                    _loc_7 = _loc_7 + 1;
                }
            }
            return _loc_2;
        }//end

        public static String  decode (String param1 )
        {
            _loc_2 = decodeToByteArray(param1);
            return _loc_2.readUTFBytes(_loc_2.length());
        }//end

        public static ByteArray  decodeToByteArray (String param1 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            ByteArray _loc_2 =new ByteArray ();
            Array _loc_3 =new Array(4);
            Array _loc_4 =new Array(3);
            int _loc_5 =0;
            while (_loc_5 < param1.length())
            {

                _loc_6 = 0;
                while (_loc_6 < 4 && _loc_5 + _loc_6 < param1.length())
                {

                    _loc_3.put(_loc_6,  BASE64_CHARS.indexOf(param1.charAt(_loc_5 + _loc_6)));
                    _loc_6 = _loc_6 + 1;
                }
                _loc_4.put(0,  (_loc_3.get(0) << 2) + ((_loc_3.get(1) & 48) >> 4));
                _loc_4.put(1,  ((_loc_3.get(1) & 15) << 4) + ((_loc_3.get(2) & 60) >> 2));
                _loc_4.put(2,  ((_loc_3.get(2) & 3) << 6) + _loc_3.get(3));
                _loc_7 = 0;
                while (_loc_7 < _loc_4.length())
                {

                    if (_loc_3.get((_loc_7 + 1)) == 64)
                    {
                        break;
                    }
                    _loc_2.writeByte(_loc_4.get(_loc_7));
                    _loc_7 = _loc_7 + 1;
                }
                _loc_5 = _loc_5 + 4;
            }
            _loc_2.position = 0;
            return _loc_2;
        }//end

    }



