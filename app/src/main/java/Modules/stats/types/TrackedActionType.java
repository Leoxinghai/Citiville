package Modules.stats.types;

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

    public class TrackedActionType
    {
        public static  String PLACE ="place";
        public static  String BUILD ="build";
        public static  String MOVE ="move";
        public static  String ROTATE ="rotate";
        public static  String HARVEST ="harvest";
        public static  String SUPPLY ="supply";
        public static  String CLEAR ="clear";
        public static  String PLANT ="plant";
        public static  String FIRST_PLANT ="firstPlant";
        public static  String SELL ="sellDelete";
        public static  String CONSTRUCTION_COMPLETE ="construction_complete";
        public static  String STORE ="place_into_storage";
        public static  String RESTORE ="remove_from_storage";
        public static  String START ="start";
        public static  String SENT_BUS ="sent_bus";
        public static  String REVIVE_CROP ="revive_crop";
        public static  String WATER_CROP ="water_crop";
        public static  String HARVEST_CROP ="harvest_crop";
        public static  String REMOVE_WILDERNESS ="remove_wilderness";
        public static  String COLLECT_RENT ="collect_rent";
        public static  String HELP_BUILD ="help_build";
        public static  String LEAVE_MESSAGE ="leave_message";
        public static  String SEND_THANKS ="send_thanks";
        public static  String HOTEL_CHECKIN ="hotel_checkin";
        public static  String HOTEL_UPGRADE ="hotel_upgrade";
        public static  String HOTEL_VIPREQUEST ="hotel_VIPrequest";
        public static  String HOTEL_VIPACCEPT ="hotel_VIPaccept";
        public static  String HOTEL_VIPGRANT ="hotel_VIPgrant";

        public  TrackedActionType ()
        {
            return;
        }//end

    }



