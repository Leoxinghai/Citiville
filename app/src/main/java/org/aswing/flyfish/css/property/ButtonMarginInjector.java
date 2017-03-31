package org.aswing.flyfish.css.property;

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
import org.aswing.flyfish.util.*;

    public class ButtonMarginInjector extends Object implements ValueInjector
    {

        public  ButtonMarginInjector (String param1)
        {
            return;
        }//end

        public void  inject (Component param1 , Object param2)
        {
            staticInject(param1, param2);
            return;
        }//end

        public static void  staticInject (Component param1 ,String param2 )
        {
            _loc_3 = param1as AbstractButton ;
            if (param1 == null)
            {
                return;
            }
            _loc_4 = param2;
            if (param2 == "" || param2 == null)
            {
                return;
            }
            _loc_5 = _loc_4.split(new RegExp("\\s+"));
            _loc_3.setMargin(getMargin(_loc_5));
            return;
        }//end

        public static Insets  getMargin (Array param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            if (param1.length >= 1)
            {
                _loc_2 = MathUtils.parseInteger(param1.get(0));
            }
            if (param1.length >= 2)
            {
                _loc_3 = MathUtils.parseInteger(param1.get(1));
            }
            if (param1.length >= 3)
            {
                _loc_4 = MathUtils.parseInteger(param1.get(2));
            }
            if (param1.length >= 4)
            {
                _loc_5 = MathUtils.parseInteger(param1.get(3));
            }
            return new Insets(_loc_2, _loc_3, _loc_4, _loc_5);
        }//end

    }


