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
import org.aswing.flyfish.util.*;

    public class NumberDecoder extends Object implements ValueDecoder
    {

        public  NumberDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            return MathUtils.parseNumber(param1);
        }//end

        public boolean  mutabble ()
        {
            return false;
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

    }


