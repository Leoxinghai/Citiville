﻿package org.aswing.flyfish.css;

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
    public class IDSelector extends TagSelector implements ISelector
    {
        protected String id ;

        public  IDSelector (String param1 ,String param2 )
        {
            super(param1);
            this.id = param2;
            return;
        }//end  

         public int  getWeight ()
        {
            return 10000;
        }//end  

         public String  getKey ()
        {
            return tag + "井" + this.id;
        }//end  

         public boolean  isSelected (Component param1 )
        {
            if (super.isSelected(param1))
            {
                return this.id == param1.awml_info.id;
            }
            return false;
        }//end  

    }


