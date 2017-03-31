package Cache.Util;

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

//import flash.external.*;
//import flash.system.*;

    public class Util
    {
        private static  RegExp REGEX_FIREFOX =/Firefox[ \/\s](\d+\.\d+)""Firefox[\/\s](\d+\.\d+)/g;
        private static  RegExp REGEX_IE =/MSIE(\d+\.\d+);""MSIE (\d+\.\d+);/g;
        private static  RegExp REGEX_OPERA =/Opera[ \/\s](\d+\.\d+)""Opera[\/\s](\d+\.\d+)/g;
        private static  String GET_NAVIGATOR_INFO ="<![CDATA[";

        public  Util ()
        {
            return;
        }//end

        private static Object  getBrowserInfo ()
        {
            Object info ;
            try
            {
                if (ExternalInterface.available)
                {
                    info = ExternalInterface.call(GET_NAVIGATOR_INFO);
                }
            }
            catch (e:Error)
            {
            }
            return info;
        }//end

        private static boolean  isFirefox (Object param1 )
        {
            return param1.userAgent && REGEX_FIREFOX.test(param1.userAgent);
        }//end

        public static boolean  isZCacheCompatible ()
        {
            String _loc_5 =null ;
            boolean _loc_1 =true ;
            _loc_2 = Capabilities.os;
            _loc_3 = Capabilities.playerType;
            _loc_4 = _loc_2.indexOf("Mac OS")==0;
            if (_loc_2.indexOf("Mac OS") == 0)
            {
                _loc_5 = _loc_2.substring("Mac OS".length());
            }
            Object _loc_6 =null ;
            if (_loc_3 == "ActiveX" || _loc_3 == "PlugIn")
            {
                _loc_6 = getBrowserInfo();
                if (_loc_6)
                {
                    if (_loc_4 && isFirefox(_loc_6))
                    {
                        _loc_1 = false;
                    }
                }
            }
            return _loc_1;
        }//end

        public static void  traceActivity (String param1 ,String param2 )
        {
            return;
        }//end

        public static String  getFilenameFromUrl (String param1 )
        {
            return param1.substring((param1.lastIndexOf("/") + 1));
        }//end

        public static void  callback (Function param1 ,Object param2 ,Array param3 =null )
        {
            if (param1 !== null)
            {
                param1.apply(param2, param3);
            }
            return;
        }//end

        public static Function  bind (Function param1 ,Object param2 ,Array param3 =null )
        {
            //Function _ ;
            Function f =param1 ;
            context = param2;
            args = param3;
            if (f == null)
            {
                throw new Error("Cannot bind a null function!");
            }
            //_ = f;
            return Object (...args )
            {
                return f.apply(context, args ? (param3.concat(args)) : (param3));
            }//end
            //;
        }//end

    }




