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
import org.aswing.util.*;

    public class FontSerializer extends Object implements PropertySerializer
    {

        public  FontSerializer ()
        {
            return;
        }//end

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            _loc_3 = param1+"";
            if (_loc_3 == "null")
            {
                return new SimpleValue(null);
            }
            _loc_3 = StringUtils.replace(_loc_3, "{", "<");
            _loc_3 = StringUtils.replace(_loc_3, "}", ">");
            _loc_4 = XML(_loc_3);
            _loc_5 = XML(_loc_3).@name;
            _loc_6 = MathUtils.parseInteger(_loc_4.@size);
            _loc_7 = _loc_4.@bold == "true";
            _loc_8 = _loc_4.@italic == "true";
            _loc_9 = _loc_4.@underline == "true";
            _loc_10 = _loc_4.@embedFonts == "true";
            _loc_11 = newASFont(_loc_5,_loc_6,_loc_7,_loc_8,_loc_9,_loc_10);
            return new SimpleValue(_loc_11);
        }//end

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            XML _loc_3 =new XML("<Font/>");
            if (param1.getValue() == null)
            {
                return XML("null");
            }
            _loc_4 = param1.getValue ();
            _loc_3.@name = _loc_4.getName();
            _loc_3.@size = _loc_4.getSize() + "";
            _loc_3.@bold = _loc_4.isBold() + "";
            _loc_3.@italic = _loc_4.isItalic() + "";
            _loc_3.@underline = _loc_4.isUnderline() + "";
            _loc_3.@embedFonts = _loc_4.isEmbedFonts() + "";
            String _loc_5 =_loc_3.toXMLString ();
            _loc_5 = StringUtils.replace(_loc_5, "<", "{");
            _loc_5 = StringUtils.replace(_loc_5, ">", "}");
            _loc_5 = StringUtils.replace(_loc_5, "\"", "\'");
            return XML(_loc_5);
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
            return "new ASFont(\"" + _loc_3.getName() + "\", " + _loc_3.getSize() + ", " + _loc_3.isBold() + ", " + _loc_3.isItalic() + ", " + _loc_3.isUnderline() + ", " + _loc_3.isEmbedFonts() + ")";
        }//end

    }


