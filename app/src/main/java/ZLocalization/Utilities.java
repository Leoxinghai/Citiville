package ZLocalization;

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
    public class Utilities
    {

        public  Utilities ()
        {
            return;
        }//end

        public static double  clamp (double param1 ,double param2 ,double param3 )
        {
            _loc_4 = Math.max(param1,param2);
            _loc_4 = Math.min(_loc_4, param3);
            return _loc_4;
        }//end

        public static String  formatNumber (int param1 )
        {
            double _loc_4 =0;
            _loc_2 = param1.toString ();
            Array _loc_3 =new Array ();
            _loc_5 = _loc_2.length ;
            while (_loc_5 > 0)
            {

                _loc_4 = Math.max(_loc_5 - 3, 0);
                _loc_3.unshift(_loc_2.slice(_loc_4, _loc_5));
                _loc_5 = _loc_4;
            }
            _loc_6 = _loc_3.join(",");
            return _loc_3.join(",");
        }//end

        public static Object  parseLocalizationString (String param1 )
        {
            _loc_2 = param1.split(":");
            Object _loc_3 =new Object ();
            _loc_3.filename = _loc_2.get(0);
            _loc_3.stringname = _loc_2.get(1);
            return _loc_3;
        }//end

        public static double  dateToNumber (String param1 ,boolean param2 =false )
        {
            Array _loc_4 =null ;
            double _loc_3 =0;
            if (param2)
            {
                _loc_4 = String(param1).split("/", 3);
                _loc_3 = Date.UTC(parseInt(_loc_4.get(2)), (parseInt(_loc_4.get(0)) - 1), parseInt(_loc_4.get(1)));
            }
            else
            {
                _loc_3 = new Date(param1).valueOf();
            }
            return _loc_3;
        }//end

        public static Array  xmlListToArray (XMLList param1 ,String param2)
        {
            XML _loc_4 =null ;
            Array _loc_3 =new Array ();
            if (param2 == "")
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_4 = param1.get(i0);

                    _loc_3.push(_loc_4);
                }
            }
            else
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_4 = param1.get(i0);

                    _loc_3.push(String(_loc_4.attribute(param2)));
                }
            }
            return _loc_3;
        }//end

        public static String  uncompress (ByteArray param1 )
        {
            ByteArray compressedData =param1 ;
            int compressedLength =compressedData.length ;
            try
            {
                compressedData.uncompress();
            }
            catch (e:Error)
            {
                throw e;
            }
            trace("Loaded compressed localization file. Originally: " + compressedLength + "Decompressed: " + compressedData.length());
            return compressedData.toString();
        }//end

        public static String  printr (Object param1 ,int param2 =0,String param3 ="")
        {
            _loc_6 = null;
            String _loc_7 =null ;
            if (param2 == 0)
            {
                param3 = "(" + Utilities.typeOf(param1) + ") {\n";
            }
            else if (param2 == 10)
            {
                return param3;
            }
            String _loc_4 ="\t";
            int _loc_5 =0;
            while (_loc_5 < param2)
            {

                _loc_5++;
                _loc_4 = _loc_4 + "\t";
            }
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_6 = param1.get(i0);

                param3 = param3 + (_loc_4 + "[" + _loc_6 + "] => (" + Utilities.typeOf(param1[_loc_6]) + ") ");
                if (Utilities.count(param1.get(_loc_6)) == 0)
                {
                    param3 = param3 + param1.get(_loc_6);
                }
                _loc_7 = "";
                if (typeof(param1.get(_loc_6)) != "xml")
                {
                    _loc_7 = Utilities.printr(param1.get(_loc_6), (param2 + 1));
                }
                if (_loc_7 != "")
                {
                    param3 = param3 + ("{\n" + _loc_7 + _loc_4 + "}");
                }
                param3 = param3 + "\n";
            }
            if (param2 == 0)
            {
                trace(param3 + "}\n");
            }
            else
            {
                return param3;
            }
            return "";
        }//end

        public static String  typeOf (Object param1)
        {
            if (param1 instanceof Array)
            {
                return "array";
            }
            if (param1 instanceof Date)
            {
                return "date";
            }
            return typeof(param1);
        }//end

        public static int  count (Object param1 )
        {
            int _loc_2 =0;
            _loc_3 = undefined;
            if (Utilities.typeOf(param1) == "array")
            {
                return param1.length;
            }
            _loc_2 = 0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                if (_loc_3 != "mx_internal_uid")
                {
                    _loc_2 = _loc_2 + 1;
                }
            }
            return _loc_2;
        }//end

        public static String  trim (String param1 )
        {
            return param1.replace(/^(\s|?)+|(\s|?)+$""^(\s|?)+|(\s|?)+$/g, "");
        }//end

        public static Object  arrayRand (Array param1 )
        {
            return param1.get(Math.floor(Math.random() * param1.length()));
        }//end

        public static Array  arrayDiff (Array param1 ,Array param2 )
        {
            int _loc_3 =0;
            Object _loc_6 =null ;
            Array _loc_4 =new Array ();
            Dictionary _loc_5 =new Dictionary ();
            _loc_3 = 0;
            while (_loc_3 < param1.length())
            {

                _loc_5.get(param1.put(_loc_3),  true);
                _loc_3++;
            }
            _loc_3 = 0;
            while (_loc_3 < param2.length())
            {

                if (_loc_5.get(param2.get(_loc_3)))
                {
                    delete _loc_5.get(param2.get(_loc_3));
                }
                else
                {
                    _loc_4.push(param2.get(_loc_3));
                }
                _loc_3++;
            }
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_4.push(_loc_6);
            }
            return _loc_4;
        }//end

        public static Array  arrayIntersect (Array param1 ,Array param2 )
        {
            int _loc_3 =0;
            Array _loc_4 =new Array ();
            Dictionary _loc_5 =new Dictionary ();
            _loc_3 = 0;
            while (_loc_3 < param1.length())
            {

                _loc_5.get(param1.put(_loc_3),  true);
                _loc_3++;
            }
            _loc_3 = 0;
            while (_loc_3 < param2.length())
            {

                if (_loc_5.get(param2.get(_loc_3)))
                {
                    _loc_4.push(param2.get(_loc_3));
                }
                _loc_3++;
            }
            return _loc_4;
        }//end

        public static Array  splitAndTrim (String param1 ,String param2 )
        {
            _loc_3 = param2.split(param1);
            int _loc_4 =0;
            _loc_5 = _loc_3.length ;
            while (_loc_4 < _loc_5)
            {

                _loc_3.put(_loc_4,  trim((String)_loc_3.get(_loc_4)));
                _loc_4++;
            }
            return _loc_3;
        }//end

    }



