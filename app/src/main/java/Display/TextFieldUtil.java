package Display;

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

//import flash.text.*;
    public class TextFieldUtil
    {
        public static  String WHITESPACE =" \t\n\r";

        public  TextFieldUtil ()
        {
            return;
        }//end

        public static void  formatSmallCaps (TextField param1 ,TextFormat param2 )
        {
            String _loc_5 =null ;
            String _loc_8 =null ;
            _loc_3 = param1.text ;
            _loc_4 = _loc_3.length ;
            String _loc_6 ="";
            if (Global.localizer.langCode == "ja")
            {
                return;
            }


            int _loc_7 =0;
            while (_loc_7 < _loc_3.length())
            {

                _loc_5 = _loc_3.charAt(_loc_7);
                switch(_loc_5)
                {
                    case "i":
                    {
                        break;
                    }
                    case "?":
                    {
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
                switch(_loc_5)
                {
                    case "?":
                    {
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
                _loc_6 = _loc_6 + _loc_8;
                _loc_7++;
            }
*/

            param1.text = _loc_3;
            while (_loc_4--)
            {

                _loc_5 = _loc_3.charAt(_loc_4);
                if (!TextFieldUtil.isLowerCase(_loc_5) || TextFieldUtil.isNumber(_loc_5))
                {
                    param1.setTextFormat(param2, _loc_4, (_loc_4 + 1));
                }
            }


            return;
        }//end

        public static String  formatSmallCapsString (String param1 )
        {
            String _loc_3 =null ;
            String _loc_6 =null ;
            _loc_2 = param1;
            String _loc_4 ="";
            if (Global.localizer.langCode == "ja")
            {
                return param1;
            }
            int _loc_5 =0;
            /*
            while (_loc_5 < _loc_2.length())
            {

                _loc_3 = _loc_2.charAt(_loc_5);
                switch(_loc_3)
                {
                    case "i":
                    {
                        break;
                    }
                    case "?":
                    {
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
                switch(_loc_3)
                {
                    case "?":
                    {
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
                _loc_4 = _loc_4 + _loc_6;
                _loc_5++;
            }
            return _loc_4;
            */
            return _loc_2;

        }//end

        public static void  autosize (TextField param1 ,int param2 ,int param3 )
        {
            _loc_4 = param1.getTextFormat ();
            while (param1.textWidth > param2 || param1.textHeight > param3)
            {

                _loc_4.size = int(_loc_4.size) - 1;
                param1.setTextFormat(_loc_4);
            }
            return;
        }//end

        public static void  limitLineCount (TextField param1 ,int param2 ,int param3 =1,double param4 =-1)
        {
            if (param1 && param2 > 0)
            {
                while (param1.numLines > param2 && param4 == -1 || param4 != -1 && param1.width < param4)
                {

                    param1.width = param1.width + param3;
                }
                if (param4 != -1)
                {
                    param1.width = param4;
                }
            }
            return;
        }//end

        public static boolean  isUpperCase (String param1 )
        {
            if (param1 != param1.toUpperCase())
            {
                return false;
            }
            return true;
        }//end

        public static String  trim (String param1 ,String param2 =" \t\n\r")
        {
            RegExp _loc_3 =new RegExp("^.get("+param2 +")+|.get("+param2 +")+$","g");
            return param1.replace(_loc_3, "");
        }//end

        public static boolean  isLowerCase (String param1 )
        {
            if (param1 != param1.toLowerCase())
            {
                return false;
            }
            return true;
        }//end

        public static boolean  isNumber (String param1 )
        {
            _loc_2 = TextFieldUtil.trim(param1);
            if (_loc_2.length < param1.length || param1.length == 0)
            {
                return false;
            }
            return !isNaN(Number(param1));
        }//end

        public static double  getLocaleFontSize (double param1 ,double param2 ,Array param3 )
        {
            double _loc_4 =0;
            int _loc_6 =0;
            Array _loc_5 =new Array();
            if (Global.localizer.langCode != "en")
            {
                if (param3 != null)
                {
                    _loc_6 = 0;
                    while (_loc_6 < param3.length())
                    {

                        _loc_5.push(param3.get(_loc_6).locale);
                        if (in_array(Global.localizer.langCode, _loc_5))
                        {
                            _loc_7 = param3.get(_loc_6).size ;
                            _loc_4 = param3.get(_loc_6).size;
                            return _loc_7;
                        }
                        _loc_6 = _loc_6 + 1;
                    }
                    if (!in_array(Global.localizer.langCode, _loc_5))
                    {
                        _loc_4 = param2;
                    }
                }
                else
                {
                    _loc_4 = param2;
                }
            }
            else
            {
                _loc_4 = param1;
            }
            return _loc_4;
        }//end

        public static double  getLocaleFontSizeByRatio (double param1 ,double param2 ,Array param3 )
        {
            double _loc_4 =0;
            int _loc_6 =0;
            Array _loc_5 =new Array();
            if (Global.localizer.langCode != "en")
            {
                if (param3 != null)
                {
                    _loc_6 = 0;
                    while (_loc_6 < param3.length())
                    {

                        _loc_5.push(param3.get(_loc_6).locale);
                        if (in_array(Global.localizer.langCode, _loc_5))
                        {
                            _loc_7 = (int)(param1 *param3.get(_loc_6).ratio );
                            _loc_4 = (int)(param1 * param3.get(_loc_6).ratio);
                            return _loc_7;
                        }
                        _loc_6 = _loc_6 + 1;
                    }
                    if (!in_array(Global.localizer.langCode, _loc_5))
                    {
                        _loc_4 = int(param1 * param2);
                    }
                }
                else
                {
                    _loc_4 = int(param1 * param2);
                }
            }
            else
            {
                _loc_4 = param1;
            }
            return _loc_4;
        }//end

        public static boolean  in_array (String param1 ,Array param2 )
        {
            int _loc_3 =0;
            while (_loc_3 < param2.length())
            {

                if (param2.get(_loc_3) == param1)
                {
                    return true;
                }
                if (param2.get(_loc_3) instanceof Array)
                {
                    return in_array(param1, param2.get(_loc_3));
                }
                _loc_3 = _loc_3 + 1;
            }
            return false;
        }//end

        public static int  mb_strwidth (String param1 )
        {
            double _loc_4 =0;
            _loc_2 = param1.length ;
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_4 = param1.substr(_loc_3, 1).charCodeAt();
                if (_loc_4 >= 8192 && _loc_4 <= 65376)
                {
                    _loc_2++;
                }
                else if (_loc_4 >= 65440)
                {
                    _loc_2++;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

    }



