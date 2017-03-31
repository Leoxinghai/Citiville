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
import org.aswing.util.*;

    public class StringEnumPro extends FilterProBase implements FilterProperty
    {
        private HashSet sets ;
        private String defaultV ;

        public  StringEnumPro (String param1 ,Array param2 )
        {
            super(param1);
            this.sets = new HashSet();
            this.sets.addAll(param2);
            this.defaultV = param2.get(0);
            if (param2.length < 1)
            {
                throw new Error("StringEnum values must has filled!");
            }
            return;
        }//end

        public Object decode (String param1 )
        {
            if (this.sets.contains(param1))
            {
                return param1;
            }
            AGLog.warn("The value must be one of \'" + this.sets.toArray() + "\', can not find : " + param1);
            return this.defaultV;
        }//end

    }


