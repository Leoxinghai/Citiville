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

    public class Config
    {
        public static String SN_APP_URL ="";
        public static String BASE_PATH ="127.0.0.1";
        public static String SERVICES_GATEWAY_PATH =BASE_PATH +"flashservices/gateway.php";
        public static String ASSET_BASE_PATH ="";
        public static String RECORD_STATS_PATH =BASE_PATH +"record_stats.php";
        public static String ASSET_POLICY_FILE ="";
        public static Array ASSET_PATHS =new Array ();
        public static boolean DEBUG_MODE =false ;
        public static double TRANSACTION_INACTIVITY_SECONDS =3.6e +006;
        public static boolean verboseLogging =false ;
        public static int VIEWPORT_CLEAR_COLOR =4.28262e +009;
        public static Object TRACE_SECTIONS =null ;
        public static int TRACE_DEFAULT_LEVEL =GlobalEngine.LEVEL_INFO ;
        public static boolean INVERT_TRACE =false ;


        public static String WEIBO_PATH =BASE_PATH +"maingame.php";
        public static String SERVICES_CITYCHANGE_PATH =BASE_PATH +"/editgrid/games/citiville/citychange.jsp";

        public static String userid ="9999";
        public static String alias ="";

        public  Config ()
        {
            return;
        }//end

    }



