package Display.aswingui.inline.style;

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
    public class FontStyle
    {
        public static  String NONE ="none";
        public static  String BOLD ="bold";
        public static  String NORMAL ="normal";
        public static  String ITALIC ="italic";
        public static  String UNDERLINE ="underline";
        public static  int ALIGN_RIGHT =4;
        public static  int ALIGN_CENTER =0;
        public static  int ALIGN_LEFT =2;
        public static  String UPPERCASE ="uppercase";
        public static  String LOWERCASE ="lowercase";
        public static  String SMALLCAPS ="smallcaps";

        public  FontStyle ()
        {
            return;
        }//end

    }


