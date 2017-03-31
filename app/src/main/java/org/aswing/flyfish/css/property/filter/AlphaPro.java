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

import org.aswing.flyfish.*;
import org.aswing.flyfish.util.*;

    public class AlphaPro extends FilterProBase implements FilterProperty
    {

        public  AlphaPro (String param1 )
        {
            super(param1);
            return;
        }//end

        public Object decode (String param1 )
        {
            _loc_2 = MathUtils.parseNumber(param1);
            if (_loc_2 < 0 || _loc_2 > 1)
            {
                AGLog.warn("Alpha value must be .get(0, 1), you\'v set a " + param1);
            }
            return _loc_2;
        }//end

    }


