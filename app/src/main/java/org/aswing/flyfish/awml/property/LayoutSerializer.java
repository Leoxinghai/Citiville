package org.aswing.flyfish.awml.property;

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

import org.aswing.flyfish.awml.*;
    public class LayoutSerializer extends Object implements PropertySerializer
    {

        public  LayoutSerializer ()
        {
            return;
        }//end

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            _loc_3 = param2.getValue ()as LayoutModel ;
            if (_loc_3 == null)
            {
                _loc_3 = new LayoutModel();
            }
            _loc_3.parse(param1);
            return _loc_3;
        }//end

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = param1as LayoutModel ;
            return _loc_3.encodeXML();
        }//end

        public Array  getCodeLines (ValueModel param1 ,ProModel param2 )
        {
            String _loc_8 =null ;
            Array _loc_9 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            _loc_3 = param1as LayoutModel ;
            _loc_12 = CodeGenerator;
            _loc_12.layout_id_counter = CodeGenerator.layout_id_counter + 1;
            String _loc_4 ="layout"+CodeGenerator.layout_id_counter ++;
            _loc_5 = _loc_3.getDef ().getShortClassName ();
            Array _loc_6 =new Array();
            _loc_6.push("var " + _loc_4 + ":" + _loc_5 + " = new " + _loc_5 + "();");
            _loc_7 = _loc_3.getProperties();
            for(int i0 = 0; i0 < _loc_7.size(); i0++) 
            {
            		param2 = _loc_7.get(i0);

                _loc_8 = param2.isSimpleOneLine();
                if (_loc_8 != null)
                {
                    _loc_6.push(_loc_4 + ".set" + param2.getName() + "(" + _loc_8 + ");");
                    continue;
                }
                _loc_9 = param2.getCodeLines();
                if (_loc_9 != null)
                {
                    _loc_10 = _loc_9.length - 1;
                    _loc_11 = 0;
                    while (_loc_11 < _loc_10)
                    {

                        _loc_6.push(_loc_9.get(_loc_11));
                        _loc_11++;
                    }
                    _loc_6.push(_loc_4 + ".set" + param2.getName() + "(" + _loc_9.get(_loc_10) + ");");
                }
            }
            _loc_6.push(_loc_4);
            return _loc_6;
        }//end

        public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            return null;
        }//end

    }


