package org.aswing.flyfish.css.property.filter;

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

import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class NumberArrayPro extends FilterProBase implements FilterProperty
    {

        public  NumberArrayPro (String param1 )
        {
            super(param1);
            return;
        }//end

        public Object decode (String param1 )
        {
            String _loc_4 =null ;
            param1 = trimArrayString(param1);
            if (param1 == "" || param1 == null)
            {
                return new Array();
            }
            _loc_2 = param1.split(new RegExp("\\s+"));
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_3.push(MathUtils.parseNumber(_loc_4));
            }
            return _loc_3;
        }//end

        public static String  trimArrayString (String param1 )
        {
            if (param1 == "" || param1 == null || param1 == "[]" || param1 == "[ ]" || param1 == "[" || param1 == "]")
            {
                return "";
            }
            if (param1.charAt(0) == "[")
            {
                param1 = param1.substr(1);
            }
            if (param1.charAt((param1.length - 1)) == "]")
            {
                param1 = param1.substr(0, (param1.length - 1));
            }
            return StringUtils.trim(param1);
        }//end

    }


