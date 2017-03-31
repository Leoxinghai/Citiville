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

    public class BooleanPro extends FilterProBase implements FilterProperty
    {

        public  BooleanPro (String param1 )
        {
            super(param1);
            return;
        }//end

        public Object decode (String param1 )
        {
            if (param1 == null)
            {
                return false;
            }
            param1 = param1.toLowerCase();
            return param1 == "true";
        }//end

    }


