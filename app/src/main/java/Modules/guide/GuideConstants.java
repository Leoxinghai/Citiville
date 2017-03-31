package Modules.guide;

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

    final public class GuideConstants
    {
        public static  int MASK_STYLE_ELLIPSE =0;
        public static  int MASK_STYLE_RECTANGLE =1;
        public static  int MASK_GAME =0;
        public static  int MASK_GAME_AND_BOTTOMBAR =1;
        public static  int MASK_ALL_UI =2;
        public static  int MASK_SPECIFIC =3;
        public static  int MASK_LOT_SITES =4;
        public static  double DEFAULT_MASK_ALPHA =0;

        public  GuideConstants ()
        {
            return;
        }//end

    }



