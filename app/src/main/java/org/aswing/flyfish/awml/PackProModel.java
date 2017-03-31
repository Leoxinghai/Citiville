package org.aswing.flyfish.awml;

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

import org.aswing.geom.*;
    public class PackProModel extends ProModel
    {

        public  PackProModel ()
        {
            super(new PackProDefinition());
            return;
        }//end

         public void  valueChanged (ValueModel param1 )
        {
            _loc_2 = (ComModel)owner
            _loc_2.getDisplay().pack();
            _loc_3 = _loc_2.getDisplay ().getSize ();
            _loc_4 = _loc_2.getProperty("Size");
            _loc_5 = _loc_3.width +","+_loc_3.height ;
            _loc_4.parse(XML(_loc_5));
            return;
        }//end

         public void  bindTo (Model param1 ,boolean param2 )
        {
            owner = param1;
            return;
        }//end

         protected ValueModel  captureDefaultProperty (String param1 ,boolean param2 =false )
        {
            return null;
        }//end

    }


