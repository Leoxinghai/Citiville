package Engine.Helpers;

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

import com.facebook.commands.photos.CreateAlbum;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

//import flash.geom.*;
    public class Vector2 extends Point
    {
        private static int allocCount =0;
        private static int allocTime =0;
        //private Creator<Vector2>;

        public  Vector2 (double param1,double param2)
        {
            super((int)param1, (int)param2);
            return;
        }//end

        public Vector2  cloneVec2 ()
        {
            return new Vector2(x, y);
        }//end

        public static Vector2  lerp (Vector2 param1 ,Vector2 param2 ,double param3 )
        {
            Vector2 _loc_4 = new Vector2(0,0);
            double _loc_5 = 1-param3;
            _loc_4.x = (int)(param1.x * _loc_5 + param2.x * param3);
            _loc_4.y = (int)(param1.y * _loc_5 + param2.y * param3);
            return _loc_4;
        }//end

    }



