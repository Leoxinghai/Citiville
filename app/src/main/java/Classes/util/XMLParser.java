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

    public class XMLParser
    {

        public  XMLParser ()
        {
            return;
        }//end

        public static Object  parseXMLElementRecursively (Object param1 )
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Object _loc_7 =null ;
            int _loc_8 =0;
            Object _loc_9 =null ;
            _loc_2 = param1.children ();
            _loc_3 = param1.attributes ();
            if (_loc_3.length() == 0 && (_loc_2.length() == 0 || childIsString(_loc_2)))
            {
                return param1.toString();
            }
            Object _loc_4 ={};
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_5 = _loc_3.get(i0);

                _loc_4.put(_loc_5.name().toString(),  _loc_5.toString());
            }
            _loc_6 = null;
            _loc_7 = null;
            if (_loc_2.length() == 1)
            {
                _loc_7 = _loc_2.get(0);
                _loc_6 = _loc_2.get(0).name().toString();
                if (_loc_7.attribute("id").toString().length > 0)
                {
                    _loc_6 = _loc_7.attribute("id").toString();
                }
                _loc_4.put(_loc_6,  parseXMLElementRecursively(_loc_7));
            }
            else
            {
                _loc_8 = 0;
                while (_loc_8 < _loc_2.length())
                {

                    _loc_7 = _loc_2.get(_loc_8);
                    _loc_6 = _loc_7.name();
                    if (_loc_7.attribute("id").toString().length > 0)
                    {
                        _loc_6 = _loc_7.attribute("id").toString();
                    }
                    else if (_loc_6 + "s" == param1.name().toString())
                    {
                        _loc_6 = String(_loc_8);
                    }
                    if (_loc_6 in _loc_4)
                    {
                        if (_loc_4.get(_loc_6) instanceof Array)
                        {
                            _loc_4.get(_loc_6).push(parseXMLElementRecursively(_loc_7));
                        }
                        else
                        {
                            _loc_9 = _loc_4.get(_loc_6);
                            _loc_4.put(_loc_6,  new Array());
                            _loc_4.get(_loc_6).push(_loc_9);
                            _loc_4.get(_loc_6).push(parseXMLElementRecursively(_loc_7));
                        }
                    }
                    else
                    {
                        _loc_4.put(_loc_6,  parseXMLElementRecursively(_loc_7));
                    }
                    _loc_8++;
                }
            }
            return _loc_4;
        }//end

        private static boolean  childIsString (XMLList param1 )
        {
            return param1.length() == 1 && param1.get(0).name() == null;
        }//end

    }



