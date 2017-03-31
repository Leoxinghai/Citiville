package Classes.util;

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

    public class DisplayObjectContainerUtil
    {

        public  DisplayObjectContainerUtil ()
        {
            return;
        }//end

        public static DisplayObject Vector  getChildren (DisplayObjectContainer param1 ).<>
        {
            _loc_2 = param1.numChildren ;
            Vector _loc_3.<DisplayObject >=new Vector<DisplayObject >(_loc_2 );
            while (_loc_2--)
            {

                _loc_3.put(_loc_2,  param1.getChildAt(_loc_2));
            }
            return _loc_3;
        }//end

    }



