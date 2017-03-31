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
    public class SimpleInjector extends Object implements ValueInjector
    {
        private String methodName ;

        public  SimpleInjector (String param1 )
        {
            this.methodName = param1;
            return;
        }//end

        public void  inject (Component param1 , Object param2)
        {
            _loc_3 = param1;
            _loc_4 = _loc_3;
            _loc_4._loc_3.get(this.methodName)(param2);
            return;
        }//end

    }


