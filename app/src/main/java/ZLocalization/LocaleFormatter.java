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

    public class LocaleFormatter
    {
        public static  String CURRENT_LOCALE ="current_locale";
        public static  String CURRENCY_DELIM ="{n}";
        public static  String LONG_DATE ="longDate";
public static  String PACKAGE_NAME ="ZLocDateTimeFormats";
public static  String CURRENCY ="currency";
public static  String DATE ="date";
public static  String NUMBER ="number";
public static  String DURIATION ="duriation";
public static  String TIME ="time";
public static  String AM ="am";
public static  String PM ="pm";
        private static Object currencyFormats ={"en_US""${n}","fr""{n}","it""{n}","de""{n} ?","es""{n}","pt_BR""R$ {n}","zh_TW""NTD {n}","zh_CN""{n}","id""Rp{n}","tr""{n} TL","ko""{n}","ms""RM {n}","ja""n}","th""{n}"};
        private static Array monthNames =new Array("January","February","March","April","May","June","July","August","September","October","November","December");
        private static Object dateFormats ={d dd "j","d"m ,"n",M "M","m","Y"};
        private static Object timeFormats ={h hh "g","h"mm ,"i",ss "s","G","H","a","aa"};

        public  LocaleFormatter ()
        {
            return;
        }//end

        private static String  getFormat (String param1 )
        {
            _loc_2 = ZLoc.instance.getString(PACKAGE_NAME,param1);
            if (!_loc_2)
            {
                return null;
            }
            return _loc_2.getVariation(LocalizedString.ORIGINAL);
        }//end

        public static String  formatDate (Date param1 ,String param2 ="date")
        {
            String _loc_5 =null ;
            String _loc_7 =null ;
            Array _loc_8 =null ;
            _loc_3 = getFormat(param2);
            if (!_loc_3)
            {
                _loc_3 = getFormat(DATE);
            }
            _loc_4 = _loc_3.match(newRegExp("{(.get(^})+)}","g"));
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                _loc_7 = _loc_4.get(_loc_6);
                _loc_5 = getFormattedDateTime(dateFormats.get(_loc_7), param1);
                _loc_8 = _loc_3.split(_loc_7);
                _loc_3 = _loc_8.join(_loc_5);
                _loc_6++;
            }
            return _loc_3;
        }//end

        public static String  formatTime (Date param1 ,boolean param2 =true )
        {
            String _loc_5 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            _loc_3 = getFormat(TIME);
            if (!param2)
            {
                _loc_7 = _loc_3.replace(":{ss}", "");
                if (_loc_7 == _loc_3)
                {
                    _loc_3 = _loc_3.replace(new RegExp("({ss}.get(^{)*)"), "");
                }
                else
                {
                    _loc_3 = _loc_7;
                }
            }
            _loc_4 = _loc_3.match(new RegExp("{(.get(^})+)}","g"));
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                _loc_8 = _loc_4.get(_loc_6);
                _loc_5 = getFormattedDateTime(timeFormats.get(_loc_8), param1);
                _loc_9 = _loc_3.split(_loc_8);
                if (_loc_5 == "pm")
                {
                    _loc_5 = getFormat(PM);
                }
                else if (_loc_5 == "am")
                {
                    _loc_5 = getFormat(AM);
                }
                _loc_3 = _loc_9.join(_loc_5);
                _loc_6++;
            }
            return _loc_3;
        }//end

        public static String  formatDuration (int param1 ,boolean param2 =true ,boolean param3 =true )
        {
            String _loc_8 =null ;
            Array _loc_9 =null ;
            _loc_4 = ZLoc.instance.langCode ;
            _loc_5 = param1% 60;
            _loc_6 = param1/60% 60;
            _loc_7 = Math.floor(param1 /3600);
            _loc_10 = getFormat(DURIATION);
            if (!param3)
            {
                if (_loc_10 == "{h}:{mm}:{ss}")
                {
                    _loc_10 = "{h}:{mm}";
                }
                else
                {
                    _loc_10 = _loc_10.replace(new RegExp("({ss}.get(^{)*)"), "");
                }
            }
            else
            {
                _loc_8 = _loc_5 < 10 ? ("0" + _loc_5) : (_loc_5.toString());
                _loc_9 = _loc_10.split("{ss}");
                _loc_10 = _loc_9.join(_loc_8);
            }
            if (!param2 && _loc_7 == 0)
            {
                _loc_10 = _loc_10.replace(new RegExp("(.get(^{)*{h}.get(^{)*)"), "");
                _loc_8 = _loc_6.toString();
                _loc_9 = _loc_10.split("{mm}");
                _loc_10 = _loc_9.join(_loc_8);
            }
            else
            {
                _loc_8 = _loc_6 < 10 ? ("0" + _loc_6) : (_loc_6.toString());
                _loc_9 = _loc_10.split("{mm}");
                _loc_10 = _loc_9.join(_loc_8);
                _loc_8 = _loc_7.toString();
                _loc_9 = _loc_10.split("{h}");
                _loc_10 = _loc_9.join(_loc_8);
            }
            return _loc_10;
        }//end

        public static String  formatNumber (double param1 )
        {
            String _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_12 =null ;
            _loc_2 = getFormat(NUMBER);
            _loc_3 = _loc_2.split("|");
            _loc_4 = _loc_3.get(0);
            _loc_5 = _loc_3.get(1);
            _loc_6 = param1.toString ().split(".");
            if (param1.toString().split(".").length > 1)
            {
                _loc_7 = _loc_6.get(0);
                _loc_8 = _loc_6.get(1);
            }
            else
            {
                _loc_5 = "";
                _loc_7 = _loc_6.get(0);
                _loc_8 = "";
            }
            _loc_9 = _loc_7.length ;
            _loc_10 = _loc_7.length % 3;
            _loc_11 = _loc_7.substr(0,_loc_10);
            if (_loc_7.substr(0, _loc_10).length == 0)
            {
                _loc_12 = new Array();
            }
            else
            {
                _loc_12 = new Array(_loc_11);
            }
            _loc_13 = _loc_10;
            while (_loc_13 < _loc_9)
            {

                _loc_12.push(_loc_7.substr(_loc_13, 3));
                _loc_13 = _loc_13 + 3;
            }
            _loc_14 = _loc_12.join(_loc_4)+_loc_5+_loc_8;
            return _loc_12.join(_loc_4) + _loc_5 + _loc_8;
        }//end

        public static String  formatCurrency (double param1 ,String param2 )
        {
            String _loc_3 =null ;
            if (param2 == CURRENT_LOCALE)
            {
                _loc_3 = getFormat(CURRENCY);
            }
            else
            {
                if (!currencyFormats.hasOwnProperty(param2))
                {
                    throw new Error("Locale: " + param2 + ", is unsupported in LocaleFormatter.currencyFormats");
                }
                _loc_3 = currencyFormats.get(param2);
            }
            _loc_4 = formatNumber(param1);
            _loc_5 = _loc_3.split(CURRENCY_DELIM );
            return _loc_3.split(CURRENCY_DELIM).join(_loc_4);
        }//end

        public static Array  getMultiFormattedDateTime (Array param1 ,Date param2 )
        {
            Array _loc_3 =new Array ();
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_3.put(_loc_4,  getFormattedDateTime(param1.get(_loc_4), param2));
                _loc_4++;
            }
            return _loc_3;
        }//end

        public static String  getFormattedDateTime (String param1 ,Date param2 )
        {
            String _loc_3 =null ;
            boolean _loc_4 =false ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            String _loc_8 =null ;
            switch(param1)
            {
                case "a":
                case "aa":
                {
                    _loc_4 = param2.getHours() < 12;
                    if (_loc_4)
                    {
                        _loc_3 = "am";
                    }
                    else
                    {
                        _loc_3 = "pm";
                    }
                    break;
                }
                case "j":
                {
                    _loc_3 = param2.getDate().toString();
                    if (_loc_3 == "1")
                    {
                        _loc_8 = getFormat("first");
                        if (_loc_8)
                        {
                            _loc_3 = _loc_3 + _loc_8;
                        }
                    }
                    break;
                }
                case "d":
                {
                    _loc_3 = param2.getDate().toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                case "n":
                {
                    _loc_3 = ((param2.getMonth() + 1)).toString();
                    break;
                }
                case "m":
                {
                    _loc_3 = ((param2.getMonth() + 1)).toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                case "M":
                {
                    _loc_5 = ((param2.getMonth() + 1)).toString();
                    _loc_3 = ZLoc.instance.t(PACKAGE_NAME, monthNames.get(_loc_5));
                    break;
                }
                case "Y":
                {
                    _loc_3 = param2.getFullYear().toString();
                    break;
                }
                case "g":
                {
                    _loc_6 = param2.getHours() % 12;
                    if (_loc_6 == 0)
                    {
                        _loc_6 = 12;
                    }
                    _loc_3 = _loc_6.toString();
                    break;
                }
                case "h":
                {
                    _loc_7 = param2.getHours() % 12;
                    if (_loc_7 == 0)
                    {
                        _loc_7 = 12;
                    }
                    _loc_3 = _loc_7.toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                case "i":
                {
                    _loc_3 = param2.getMinutes().toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                case "s":
                {
                    _loc_3 = param2.getSeconds().toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                case "G":
                {
                    _loc_3 = param2.getHours().toString();
                    break;
                }
                case "H":
                {
                    _loc_3 = param2.getHours().toString();
                    if (_loc_3.length == 1)
                    {
                        _loc_3 = "0" + _loc_3;
                    }
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return _loc_3;
        }//end

    }



