package Engine.Managers;

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

import Engine.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

    public class LocalizationManager
    {
        private static XML m_xml ;
        private static Function m_callBackFunc ;
        private static URLLoader m_urlLoader ;
        private static Dictionary m_dictionary ;
        private static boolean m_fileIsCompressed ;

        public  LocalizationManager ()
        {
            return;
        }//end

        public static void  init (String param1 ,Function param2 ,boolean param3 =false )
        {
            m_fileIsCompressed = param3;
            m_urlLoader = new URLLoader();
            if (m_fileIsCompressed)
            {
                m_urlLoader.dataFormat = URLLoaderDataFormat.BINARY;
            }
            m_urlLoader.addEventListener(Event.COMPLETE, xmlParseCallBack);
            m_callBackFunc = param2;
            m_urlLoader.load(new URLRequest(param1));
            return;
        }//end

        public static void  xmlParseCallBack (Event event )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            m_urlLoader.removeEventListener(Event.COMPLETE, xmlParseCallBack);
            if (m_fileIsCompressed)
            {
                _loc_3 = Utilities.uncompress(event.target.data);
                _loc_2 = new XML(_loc_3);
            }
            else
            {
                GlobalEngine.msg("Loaded uncompressed localization file. Size:", event.target.data.length());
                _loc_2 = new XML(event.target.data);
            }
            m_xml = _loc_2;
            LocalizationManager.setLocaleXml(m_xml);
            m_dictionary = new Dictionary();
            m_callBackFunc();
            return;
        }//end

        public static void  setLocaleXml (XML param1 )
        {
            m_xml = param1;
            return;
        }//end

        private static String  getLocalizationString (String param1 ,String param2 )
        {
            String hashKey ;
            XMLList bundleList ;
            XML bundleXML ;
            XMLList bundleLookup ;
            bundleName = param1;
            resource = param2;
            String stringResult ;
            Object _loc_7;
            if (m_xml)
            {
                hashKey = bundleName + ":" + resource;
                stringResult = m_dictionary.get(hashKey);
                if (stringResult == null)
                {
                    int _loc_5 =0;
                    _loc_6 = m_xml.bundle;
                    XMLList _loc_4 =new XMLList("");
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    	_loc_7 = _loc_6.get(i0);


                        with (_loc_7)
                        {
                            if (@name == bundleName)
                            {
                                _loc_4.put(_loc_5++,  _loc_7);
                            }
                        }
                    }
                    bundleList = _loc_4;
                    if (bundleList)
                    {
                        bundleXML = bundleList.get(0);
                        if (bundleXML != null && bundleXML.bundleLine)
                        {
                            _loc_5 = 0;
                            _loc_6 = bundleXML.bundleLine;
                            _loc_4 = new XMLList("");
                            for(int i0 = 0; i0 < _loc_6.size(); i0++)
                            {
                            	_loc_7 = _loc_6.get(i0);


                                with (_loc_7)
                                {
                                    if (@key == resource)
                                    {
                                        _loc_4.put(_loc_5++,  _loc_7);
                                    }
                                }
                            }
                            bundleLookup = _loc_4;
                            if (bundleLookup)
                            {
                                stringResult = bundleLookup.value;
                            }
                            m_dictionary.put(hashKey,  stringResult);
                        }
                    }
                }
                if (stringResult == "")
                {
                    stringResult;
                }
            }
            return stringResult;
        }//end

        public static boolean  hasLocalizationString (String param1 ,String param2 )
        {
            return getLocalizationString(param1, param2) != null;
        }//end

        public static String  quickLocalize (String param1 ,String param2 ,Array param3 =null )
        {
            RegExp _loc_5 =null ;
            int _loc_6 =0;
            _loc_4 = getLocalizationString(param1,param2);
            if (getLocalizationString(param1, param2))
            {
                if (param3)
                {
                    _loc_6 = 0;
                    while (_loc_6 < param3.length())
                    {

                        if (_loc_4)
                        {
                            _loc_5 = new RegExp("\\{" + _loc_6 + "\\}", "gim");
                            _loc_4 = _loc_4.replace(_loc_5, param3.get(_loc_6));
                        }
                        _loc_6++;
                    }
                }
            }
            else if (Config.DEBUG_MODE == true)
            {
                _loc_4 = "Could not find localization data for " + param1 + ":" + param2;
                GlobalEngine.log("localization", "bundleName: " + param1 + " resource: " + param2);
            }
            else
            {
                _loc_4 = "";
            }
            return _loc_4;
        }//end

    }



