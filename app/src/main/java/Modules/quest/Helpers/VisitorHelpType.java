package Modules.quest.Helpers;

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

    public class VisitorHelpType
    {
        public static  String PLOT_HARVEST ="plotHarvest";
        public static  String PLOT_REVIVE ="plotRevive";
        public static  String PLOT_WATER ="plotWater";
        public static  String BUSINESS_SEND_TOUR ="businessSendTour";
        public static  String CONSTRUCTIONSITE_CONSTRUCT ="constructionSiteConstruct";
        public static  String CONSTRUCTIONSITE_ACCEL ="constructionSiteAccel";
        public static  String RESIDENCE_COLLECT_RENT ="residenceCollectRent";
        public static  String WILDERNESS_CLEAR ="wildernessClear";
        public static  String SHIP_HARVEST ="shipHarvest";
        public static  String SHIP_REVIVE ="shipRevive";
        public static  String SHIP_WATER ="shipWater";
        public static  String HOTEL_CHECKIN ="hotelCheckIn";

        public  VisitorHelpType ()
        {
            return;
        }//end

    }



