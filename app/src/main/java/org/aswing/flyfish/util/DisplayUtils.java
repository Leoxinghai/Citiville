package org.aswing.flyfish.util;

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

//import flash.display.*;
    public class DisplayUtils extends Object
    {

        public  DisplayUtils ()
        {
            return;
        }//end

        public static DisplayObject  createNoneAssetShape (double param1 =16)
        {
            _loc_2 = param1;
            _loc_3 = new Shape ();
            _loc_4 = _loc_3.graphics ;
            _loc_3.graphics.beginFill(16777215);
            _loc_4.drawRect(0, 0, _loc_2, _loc_2);
            _loc_4.endFill();
            _loc_4.lineStyle(1, 0, 1);
            _loc_4.moveTo(2, 2);
            _loc_4.lineTo(_loc_2 - 2, _loc_2 - 2);
            _loc_4.moveTo(_loc_2 - 2, 2);
            _loc_4.lineTo(2, _loc_2 - 2);
            _loc_4.lineStyle();
            return _loc_3;
        }//end

    }


