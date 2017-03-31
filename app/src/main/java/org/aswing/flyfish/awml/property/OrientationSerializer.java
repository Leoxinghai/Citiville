package org.aswing.flyfish.awml.property;

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

import org.aswing.flyfish.awml.*;
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class OrientationSerializer extends IntSerializer
    {
        private static HashMap values =null ;

        public  OrientationSerializer ()
        {
            return;
        }//end  

         public String  isSimpleOneLine (ValueModel param1 ,ProModel param2 )
        {
            return getValues().getValue(MathUtils.parseInteger(String(param1)));
        }//end  

        private static HashMap  getValues ()
        {
            if (values == null)
            {
                values = new HashMap();
            }
            values.put(0, "AsWingConstants.HORIZONTAL");
            values.put(1, "AsWingConstants.VERTICAL");
            return values;
        }//end  

    }


