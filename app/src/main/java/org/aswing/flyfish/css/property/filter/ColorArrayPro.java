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

import org.aswing.flyfish.css.property.*;
    public class ColorArrayPro extends FilterProBase implements FilterProperty
    {

        public  ColorArrayPro (String param1 )
        {
            super(param1);
            return;
        }//end

        public Object decode (String param1 )
        {
            String _loc_4 =null ;
            param1 = NumberArrayPro.trimArrayString(param1);
            if (param1 == null || param1 == "")
            {
                return new Array();
            }
            _loc_2 = param1.split(new RegExp("\\s+"));
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_3.push(ColorDecoder.staticDecode(_loc_4).getRGB());
            }
            return _loc_3;
        }//end

    }


