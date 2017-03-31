package org.aswing.plaf;

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

    dynamic public class ArrayUIResource extends Array implements UIResource
    {

        public  ArrayUIResource (Array param1)
        {
            Object _loc_2;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_2 = param1.get(i0);

                    push(_loc_2);
                }
            }
            return;
        }//end

    }


