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

    public class StatsPhylumType
    {
        public static  String MISCELLANEOUS ="misc";
        public static  String SERVER_ERROR ="server_exception";
        public static  String DEBUG_ERROR ="debug_error";
        public static  String ENERGY_COMBO_ENABLED ="combo_enabled";
        public static  String ENERGY_COMBO_DISABLED ="combo_disabled";
        public static  String HUD ="hud";
        public static  String CARDMAKER ="cardmaker";
        public static  String ADMIRERS ="admirers";
        public static  String CARDVIEWER ="cardviewer";
        public static  String PRIZES ="prizes";
        public static  String VIEW ="view";
        public static  String HIRE ="hire";
        public static  String X ="x";
        public static  String FILL_ALL ="fill_all";
        public static  String FILL_ONE ="fill_1";
        public static  String VIRAL_UNLOCK ="viral_unlock";
        public static  String FEED_DIALOG_OPEN ="view";
        public static  String FEED_DIALOG_SHARE ="share";
        public static  String FEED_DIALOG_CLOSE ="close";
        public static  String NEIGHBORS2_TOASTER3 ="all_hearts_used";
        public static  String NEIGHBORS2_VISIT_HELP_PICK ="visit_help";
        public static  String NEIGHBORS2_VISIT_BACK_PICK ="visit_back";
        public static  String QUEST_HIDE ="hide_quest";
        public static  String QUEST_SHOW ="quest_shown";
        public static  String SWITCH_TAB ="switch_tab";
        public static  String ASK_FOR_CREW ="crew";
        public static  String ASK_FOR_BUILDABLES ="buildables";

        public  StatsPhylumType ()
        {
            return;
        }//end

    }



