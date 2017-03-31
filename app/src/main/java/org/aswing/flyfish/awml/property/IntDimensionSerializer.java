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
import org.aswing.flyfish.util.*;
import org.aswing.geom.*;

    public class IntDimensionSerializer extends Object implements PropertySerializer, DefaultValueHelper
    {

        public  IntDimensionSerializer ()
        {
            return;
        }//end

        public ValueModel  decodeValue (XML param1 ,ProModel param2 )
        {
            _loc_3 = param1+"";
            _loc_4 = _loc_3.split(",");
            _loc_5 = new IntDimension(MathUtils.parseInteger(_loc_4.get(0) ),MathUtils.parseInteger(_loc_4.get(1) ));
            return new SimpleValue(_loc_5);
        }//end

        public XML  encodeValue (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = param1.getValue ();
            _loc_4 = XML(_loc_3.width+","+_loc_3.height);
            return XML(_loc_3.width + "," + _loc_3.height);
        }//end

        public Array  getCodeLines (ValueModel param1 ,ProModel param2 )
        {
            return null;
        }//end

        public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            _loc_3 = param1.getValue ();
            return "new IntDimension(" + _loc_3.width + ", " + _loc_3.height + ")";
        }//end

        public ValueModel  getDefaultValue (String param1 ,Model param2 )
        {
            if (param2 is ComModel && param1 == "PreferredSize")
            {
                return new SimpleValue(null);
            }
            throw new Error("It is not default value helped!");
        }//end

        public boolean  isNeedHelp (String param1 ,Model param2 )
        {
            if (param2 is ComModel && param1 == "PreferredSize")
            {
                return true;
            }
            return false;
        }//end

    }


