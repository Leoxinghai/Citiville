package Display.GridlistUI;

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

    public class GridCellType
    {
        public static  int ITEM =1;
        public static  int SEND_GIFT =2;
        public static  int ASK_BUILDING_BUDDY =3;
        public static  int ADD_CREW =4;
        public static  int HIRED_CREW =5;
        public static  int BONUS_CREW =6;

        public  GridCellType ()
        {
            return;
        }//end

    }


