package root;

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

import ZLocalization.*;
    public class ZLoc
    {
        private static  String DEFAULT_GENDER ="masc";
        private static  int NO_COUNT =-1;
        public static Localizer instance ;

        public  ZLoc ()
        {
            return;
        }//end

        public static String  t (String param1 ,String param2 ,Object param3 =null )
        {
            return instance.translate(param1, param2, param3);
        }//end

        public static LocalizationObjectToken  tk (String param1 ,String param2 ,String param3 ="",int param4 =-1)
        {
            return instance.createToken(param1, param2, param3, param4);
        }//end

        public static LocalizationName  tn (String param1 ,String param2 ="masc")
        {
            return instance.createName(param1, param2);
        }//end

        public static String  ti (String param1 )
        {
            return instance.translateImagePath(param1);
        }//end

        public static boolean  exists (String param1 ,String param2 )
        {
            return instance.exists(param1, param2);
        }//end

    }



