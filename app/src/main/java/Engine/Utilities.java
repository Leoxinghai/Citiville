package Engine;

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

import root.GlobalEngine;

//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.geom.*;
//import flash.net.*;
//import flash.utils.*;

    public class Utilities
    {
        private static double m_idCounter =0;

        public  Utilities ()
        {
            return;
        }//end

        public static void  removeAllChildren (DisplayObjectContainer param1 )
        {
            int _loc_2 =0;
            if (param1 != null)
            {
                _loc_2 = param1.numChildren;
                while (_loc_2 > 0)
                {

                    param1.removeChildAt((_loc_2 - 1));
                    _loc_2 = _loc_2 - 1;
                }
            }
            return;
        }//end

        public static double  clamp (double param1 ,double param2 ,double param3 )
        {
            _loc_4 = Math.max(param1 ,param2 );
            _loc_4 = Math.min(_loc_4, param3);
            return _loc_4;
        }//end

        public static String  getStackTrace ()
        {
            Array stackTraceLines ;
            String lineToParse ;
            String stackTrace ;
            String result ;
            Error tempError =new Error ();
            try
            {
                stackTrace = tempError.getStackTrace();
            }
            catch (e:Error)
            {
            }
            if (stackTrace != null && stackTrace != "")
            {
                stackTraceLines = stackTrace.split("\n", 5);
                lineToParse = String(stackTraceLines.get(4));
                result = lineToParse.substring(4, lineToParse.indexOf("()", 4) + 2);
            }
            return result;
        }//end

        public static int  hexColorToIntColor (String param1 )
        {
            int _loc_2 =0;
            if (param1.length == 7)
            {
                _loc_2 = parseInt("0x" + param1.substr(1));
            }
            else if (param1.length == 6)
            {
                _loc_2 = parseInt("0x" + param1);
            }
            return _loc_2;
        }//end

        public static Bitmap  getSmoothableBitmap (DisplayObject param1 )
        {
            BitmapData _loc_2 =null ;

            if(param1.width == 0 ) {
                _loc_2 = new BitmapData(10, 10, true, 0);

            } else {
                _loc_2 = new BitmapData(param1.width, param1.height, true, 0);
                _loc_2.draw(param1, null, null, null, null, true);
	    }
            Bitmap _loc_3 =new Bitmap(_loc_2 ,"auto",true );
            return _loc_3;
        }//end

        public static String  intColorToHexColor (int param1 )
        {
            _loc_2 = param1.toString(16);
            while (_loc_2.length < 6)
            {

                _loc_2 = "0" + _loc_2;
            }
            return _loc_2.toUpperCase();
        }//end

        public static boolean  isURL (String param1 )
        {
            loc_2 = ftp/(|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?""(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/(.get(\w#!:.?+=&%@!\-\/)))?/;
            return _loc_2.test(param1);
        }//end

        public static void  launchURL (String param1 ,String param2 ="_blank",Object param3 =null )
        {
            String browser ;
            url = param1;
            target = param2;
            variables = param3;
            URLRequest request =new URLRequest(url );
            boolean useNavigateToURL =false ;
            if (variables)
            {
                request.data = variables;
            }
            try
            {
                if (ExternalInterface.available)
                {
                    browser = ExternalInterface.call("function a() {return navigator.userAgent;}");
                    if (browser != null && browser.indexOf("Firefox") < 1)
                    {
                        useNavigateToURL = true;
                    }
                    else
                    {
                        ExternalInterface.call("window.open", url, target, "");
                        useNavigateToURL = true;
                    }
                }
                else
                {
                    useNavigateToURL = true;
                }
            }
            catch (error:SecurityError)
            {
                useNavigateToURL = false;
            }
            if (useNavigateToURL)
            {
                target = "new";
                navigateToURL(request, target);
            }
            return;
        }//end

        public static void  centerWindow (DisplayObject param1 )
        {
            param1.x = (GlobalEngine.stage.stageWidth - param1.width) / 2;
            param1.y = (GlobalEngine.stage.stageHeight - param1.height) / 2;
            return;
        }//end

        public static String  extractBasicTag (String param1 ,String param2 )
        {
            int _loc_5 =0;
            String _loc_3 =null ;
            _loc_4 = param1.indexOf(param2 +"=\"");
            if (param1.indexOf(param2 + "=\"") != -1)
            {
                _loc_4 = _loc_4 + (param2.length + 2);
                _loc_5 = param1.indexOf("\"", _loc_4);
                if (_loc_5 != -1)
                {
                    _loc_3 = param1.substring(_loc_4, _loc_5);
                }
            }
            return _loc_3;
        }//end

        public static String  extractStyle (String param1 ,String param2 )
        {
            int _loc_5 =0;
            param1 = param1 + ";";
            String _loc_3 =null ;
            _loc_4 = param1.indexOf(param2 +":");
            if (param1.indexOf(param2 + ":") != -1)
            {
                _loc_4 = _loc_4 + (param2.length + 1);
                _loc_5 = param1.indexOf(";", _loc_4);
                if (_loc_5 != -1)
                {
                    _loc_3 = param1.substring(_loc_4, _loc_5);
                }
            }
            return _loc_3;
        }//end

        public static BitmapData  createSquareBitmapData (DisplayObject param1 )
        {
            _loc_2 = Math.min(param1.width ,param1.height );
            Point _loc_3 =new Point ((param1.width -_loc_2 )/2,(param1.height -_loc_2 )/2);
            Matrix _loc_4 =new Matrix ();
            _loc_4.translate(-_loc_3.x, -_loc_3.y);
            BitmapData _loc_5 =new BitmapData(_loc_2 ,_loc_2 ,true ,0);
            _loc_5.draw(param1, _loc_4);
            return _loc_5;
        }//end

        public static double  generateUniqueId ()
        {

            m_idCounter++;
            return m_idCounter;
        }//end

        public static void  setFullScreen (boolean param1 )
        {
            fullscreen = param1;
            if (GlobalEngine.stage)
            {
                try
                {
                    if (fullscreen)
                    {
                        GlobalEngine.stage.displayState = StageDisplayState.FULL_SCREEN;
                    }
                    else
                    {
                        GlobalEngine.stage.displayState = StageDisplayState.NORMAL;
                    }
                }
                catch (err:SecurityError)
                {
                }
                GlobalEngine.log("Engine", "Toggling Fullscreen, DisplayState: " + GlobalEngine.stage.displayState.toString());
            }
            return;
        }//end

        public static boolean  isFullScreen ()
        {
            return GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN;
        }//end

        public static void  toggleFullScreen ()
        {
            setFullScreen(isFullScreen() == false);
            return;
        }//end

        private static void  onFullScreenChanged (FullScreenEvent event )
        {
            GlobalEngine.viewport.centerViewport();
            return;
        }//end

        public static double  randBetween (double param1 ,double param2 )
        {
            return Math.random() * (param2 - param1) + param1;
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
            int compressedLength =compressedData.length() ;
            try
            {
                compressedData.uncompress();
            }
            catch (e:Error)
            {
                throw e;
            }
            GlobalEngine.msg("Loaded compressed localization file. Originally:", compressedLength, "Decompressed:", compressedData.length());
            return compressedData.toString();
        }//end

        public static Function  bind (Function param1 ,Object param2 ,Array param3 =null )
        {

            Function f =param1 ;
            context = param2;

            if (f == null)
            {
                throw new Error("Cannot bind a null function!");
            }

            return Object (...args )
            {

                return f.apply(context, args ? (param3.concat(args)) : (param3));
            }//end
            ;
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
            _loc_3 = null;
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
            _loc_3 = param2.split(param1 );
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



