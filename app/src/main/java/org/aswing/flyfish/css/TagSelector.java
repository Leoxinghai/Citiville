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
    public class TagSelector extends Object implements ISelector
    {
        protected String tag ;
        public static  String WILDCARD ="*";

        public  TagSelector (String param1 )
        {
            if (param1 == null || param1 == "")
            {
                param1 = WILDCARD;
            }
            this.tag = param1;
            return;
        }//end  

        public int  getWeight ()
        {
            return 1;
        }//end  

        public String  getKey ()
        {
            return this.tag;
        }//end  

        public boolean  isSelected (Component param1 )
        {
            if (WILDCARD == this.tag)
            {
                return true;
            }
            return this.tag == param1.awml_info.tag;
        }//end  

    }


