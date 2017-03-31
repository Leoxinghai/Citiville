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
    public class DecoratorInjector extends Object implements ValueInjector
    {
        private boolean background ;

        public  DecoratorInjector (String param1 )
        {
            this.background = param1 == "back";
            return;
        }//end

        public void  inject (Component param1 , Object param2)
        {
            _loc_3 = param2;
            if (this.background)
            {
                param1.setBackgroundDecorator(_loc_3);
            }
            else
            {
                param1.setForegroundDecorator(_loc_3);
            }
            return;
        }//end

    }


