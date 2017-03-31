package Classes.util;

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

import com.adobe.serialization.json.*;
    public class BracketFinder
    {

        public  BracketFinder ()
        {
            return;
        }//end

        public static Array  getBracketedSubstrings (String param1 ,boolean param2 =false )
        {
            int _loc_6 =0;
            String _loc_3 ="{.get(^})*}";
            RegExp _loc_4 =new RegExp(_loc_3 ,"g");
            _loc_5 = param1.match(_loc_4 );
            if (_loc_5 == null)
            {
                throw new Error("bogus pattern " + _loc_3);
            }
            if (!param2)
            {
                _loc_6 = 0;
                while (_loc_6 < _loc_5.length())
                {

                    _loc_5.put(_loc_6,  _loc_5.get(_loc_6).substr(1, _loc_5.get(_loc_6).length - 2));
                    _loc_6++;
                }
            }
            return _loc_5;
        }//end

        public static String  substituteBracketedSubstrings (String param1 ,Array param2 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_3 = getBracketedSubstrings(param1,true);
            if (_loc_3.length != param2.length())
            {
                throw new Error("mismatch " + _loc_3.length + " != " + param2.length + ": " + param1);
            }
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = param1.indexOf(_loc_3.get(_loc_4));
                _loc_6 = _loc_3.get(_loc_4).length;
                param1 = param1.substr(0, _loc_5) + param2.get(_loc_4) + param1.substr(_loc_5 + _loc_6);
                _loc_4++;
            }
            return param1;
        }//end

        public static void  test_getBracketedSubstrings ()
        {
            Array _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            Array _loc_1 =[[ "",[]] ,[ "foo",[]] ,[ "{bar}",[ "bar"]] ,[ "{bar}foo",[ "bar"]] ,[ "foo{bar}",[ "bar"]] ,[ "foo{bar}baz",[ "bar"]] ,[ "foo{bar}baz{bang}bing",[ "bar","bang"]] ,[ "foo{{bar}}bing",[ "{bar"]]] ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = _loc_2.get(0);
                _loc_4 = _loc_2.get(1);
                _loc_5 = getBracketedSubstrings(_loc_3);
                _loc_6 = com.adobe.serialization.json.JSON.encode(_loc_4);
                _loc_7 = com.adobe.serialization.json.JSON.encode(_loc_5);
                if (_loc_6 != _loc_7)
                {
                    throw new Error("get input " + _loc_3 + " mismatch: expected " + _loc_6 + " got " + _loc_7);
                }
            }
            return;
        }//end

        public static void  test_substituteBracketedSubstrings ()
        {
            Array _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_1 =[[ "",[] ,""] ,[ "{bar}",[123] ,"123"] ,[ "foo{bar}baz{bang}bing",[1 ,2] ,"foo1baz2bing"]] ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = _loc_2.get(0);
                _loc_4 = _loc_2.get(1);
                _loc_5 = _loc_2.get(2);
                _loc_6 = substituteBracketedSubstrings(_loc_3, _loc_4);
                if (_loc_6 != _loc_5)
                {
                    throw new Error("subst mismatch " + _loc_5 + " != " + _loc_6);
                }
            }
            return;
        }//end

    }



