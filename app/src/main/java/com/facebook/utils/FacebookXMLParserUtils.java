package com.facebook.utils;

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

import com.facebook.data.*;
import com.facebook.data.photos.*;
//import flash.net.*;
import com.xinghai.Debug;

    public class FacebookXMLParserUtils
    {

        public  FacebookXMLParserUtils ()
        {
            return;
        }//end

        public static AlbumCollection  createAlbumCollection (XML param1 ,Namespace param2 )
        {
            _loc_4 = null;
            AlbumData _loc_5 =null ;
            AlbumCollection _loc_3 =new AlbumCollection ();
            for(int i0 = 0; i0 < param1..param2::album.size(); i0++) 
            {
            		_loc_4 = param1..param2::album.get(i0);

                _loc_5 = new AlbumData();
                _loc_5.aid = FacebookXMLParserUtils.toStringValue(param2::aid.get(0));
                _loc_5.cover_pid = FacebookXMLParserUtils.toStringValue(param2::cover_pid.get(0));
                _loc_5.owner = param2::owner;
                _loc_5.name = param2::name;
                _loc_5.created = FacebookXMLParserUtils.toDate(param2::created);
                _loc_5.modified = FacebookXMLParserUtils.toDate(param2::modified);
                _loc_5.description = param2::description;
                _loc_5.location = param2::location;
                _loc_5.link = param2::link;
                _loc_5.size = param2::size;
                _loc_5.visible = param2::visible;
                _loc_5.modified_major = FacebookXMLParserUtils.toDate(param2::modified_major);
                _loc_5.edit_link = param2::edit_link;
                _loc_5.type = param2::type;
                _loc_3.addAlbum(_loc_5);
            }
            return _loc_3;
        }//end

        public static double  toNumber (XML param1 )
        {
            if (param1 == null)
            {
                return NaN;
            }
            return Number(param1.toString());
        }//end

        public static String  toStringValue (XML param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            return param1.toString();
        }//end

        public static Date  toDate (String param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_2 = param1;
            while (_loc_2.length < 13)
            {

                _loc_2 = _loc_2 + "0";
            }
            Date _loc_3 =new Date(Number(_loc_2 ));
            return _loc_3;
        }//end

        public static Array  xmlListToObjectArray (XMLList param1 )
        {
            Array _loc_2 =new Array();
            if (param1 == null)
            {
                return _loc_2;
            }
            _loc_3 = param1.length ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_2.push(xmlToObject(param1.get(_loc_4)));
                _loc_4 = _loc_4 + 1;
            }
            return _loc_2;
        }//end

        public static Array  toUIDArray (XML param1 )
        {
            Array _loc_2 =new Array();
            if (param1 == null)
            {
                return _loc_2;
            }
            _loc_3 = param1.children ();
            _loc_4 = _loc_3.length ();
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_2.push(toNumber(_loc_3.get(_loc_5)));
                _loc_5 = _loc_5 + 1;
            }
            return _loc_2;
        }//end

        public static boolean  toBoolean (XML param1 )
        {
            if (param1 == null)
            {
                return false;
            }
            return param1.toString() == "1";
        }//end

        public static FacebookLocation  createLocation (XML param1 ,Namespace param2 )
        {
            FacebookLocation _loc_3 =new FacebookLocation ();
            if (param1 == null)
            {
                return _loc_3;
            }
            _loc_3.city = String(param2::city);
            _loc_3.state = String(param2::state);
            _loc_3.country = String(param2::country);
            _loc_3.zip = String(param2::zip);
            _loc_3.street = String(param2::street);
            return _loc_3;
        }//end

        public static Object  xmlToObject (XML param1 )
        {
            XML _loc_6 =null ;
            Object _loc_2 ={};
            _loc_3 = param1.children ();
            int _loc_4 =_loc_3.length ();
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_6 = _loc_3.get(_loc_5);

                _loc_2.put(_loc_6.localName(),  _loc_6);
                _loc_5 = _loc_5 + 1;
            }
            return _loc_2;
        }//end

        public static URLVariables  xmlToUrlVariables (XMLList param1 )
        {
            XML _loc_3 =null ;
            URLVariables _loc_2 =new URLVariables ();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_2.put(_loc_3.key.valueOf(),  _loc_3.value.valueOf());
            }
            return _loc_2;
        }//end

        public static Object  nodeToObject (XMLList param1 )
        {
            XML _loc_3 =null ;
            Object _loc_2 ={};
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);

                _loc_2.put(_loc_3.key.valueOf(),  _loc_3.value.valueOf());
            }
            return _loc_2;
        }//end

        public static Array  toArray (XML param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            return param1.toString().split(",");
        }//end

    }


