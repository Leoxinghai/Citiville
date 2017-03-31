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

import org.aswing.*;
import org.aswing.flyfish.awml.*;
import org.aswing.flyfish.util.*;

    public class ColorSerializer extends Object implements PropertySerializer
    {

        public  ColorSerializer ()
        {
            return;
        }//end

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            ASColor _loc_4 =null ;
            Array _loc_5 =null ;
            _loc_3 = param1+"";
            if (_loc_3 == "null")
            {
                _loc_4 = null;
            }
            else
            {
                _loc_5 = _loc_3.split(",");
                _loc_4 = new ASColor(MathUtils.parseInteger(_loc_5.get(0), 16), MathUtils.parseNumber(_loc_5.get(1)));
            }
            return new SimpleValue(_loc_4);
        }//end

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            String _loc_4 =null ;
            _loc_3 = param1.getValue ();
            if (_loc_3 == null)
            {
                _loc_4 = "null";
            }
            else
            {
                _loc_4 = _loc_3.getRGB().toString(16) + "," + _loc_3.getAlpha();
            }
            return XML(_loc_4);
        }//end

        public Array  getCodeLines (ValueModel param1 ,ProModel param2 )
        {
            return null;
        }//end

        public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = param1.getValue ();
            if (_loc_3 == null)
            {
                return "null";
            }
            return "new ASColor(0x" + _loc_3.getRGB().toString(16) + ", " + _loc_3.getAlpha() + ")";
        }//end

    }


