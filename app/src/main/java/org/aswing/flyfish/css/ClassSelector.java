package org.aswing.flyfish.css;

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
    public class ClassSelector extends TagSelector implements ISelector
    {
        protected String clazz ;

        public  ClassSelector (String param1 ,String param2 )
        {
            super(param1);
            this.clazz = param2;
            return;
        }//end  

         public int  getWeight ()
        {
            return 100;
        }//end  

         public String  getKey ()
        {
            return tag + "。" + this.clazz;
        }//end  

         public boolean  isSelected (Component param1 )
        {
            if (super.isSelected(param1))
            {
                return param1.awml_info.clazz == this.clazz;
            }
            return false;
        }//end  

    }


