package Classes;

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

    final public class ItemImageInstance
    {
        public Object image ;
        public Object lowResImage ;
        public double forcedWidth =0;
        public double forcedHeight =0;
        public double offsetX =0;
        public double offsetY =0;
        public static  String BASE_IMAGE ="base";
        public static  String BUILDING_IMAGE ="building";

        public String name ;

        public  ItemImageInstance ()
        {
            return;
        }//end

    }


