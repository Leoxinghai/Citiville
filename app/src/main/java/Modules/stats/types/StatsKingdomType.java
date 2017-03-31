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

    public class StatsKingdomType
    {
        public static  String SETTINGS ="settings_clicks";
        public static  String NEIGHBOR_LADDER ="neighborladder_clicks";
        public static  String ACTION_MENU ="action_menu_clicks";
        public static  String STORAGE_MENU ="storage_menu_clicks";
        public static  String MODE_BUTTON ="mode_button_clicks";
        public static  String GAME_ACTIONS ="game_actions";
        public static  String NEIGHBOR_VISIT ="neighbor_visits_made";
        public static  String BONUS_AWARDED ="awarded";
        public static  String ZMC_EVENT ="zmc_event";
        public static  String ACH_NEW_STATE ="newstate";
        public static  String ACH_PURCHASE_FINISH ="purchase";
        public static  String ACH_CLAIM_REWARD ="claim_reward";
        public static  String TRANSACTIONS ="transaction_fault";
        public static  String TRANSACTIONS_USERINIT ="transaction_fault_userinit";
        public static  String SECURERAND ="secure_rand_fault";
        public static  String DOOBER_MISMATCH ="doober_mismatch";
        public static  String STARTUP_FIRST ="first";
        public static  String STARTUP_ALL ="all";
        public static  String D2NEIGHBOR ="second_day_neighbor_prompt";
        public static  String LIMITED_ITEM ="limited_item";
        public static  String OUT_OF_ENERGY ="out_of_energy";
        public static  String VDAY_2011 ="vday_2011";
        public static  String DIALOG_STATS ="dialog";
        public static  String DIALOG_TOASTER ="toaster";
        public static  String DIALOG_MINITOASTER ="mini_toaster";
        public static  String DIALOG_NEIGHBOR_PICK ="neighbor_visit_pick";
        public static  String QUEST_ICONS ="quest_icons";
        public static  String VISIT_NEIGHBOR ="visit_neighbor";
        public static  String LOADAPP ="loadapp";
        public static  String INTERACTIVE ="interactive";
        public static  String LOADCOMPLETE ="loadcomplete";
        public static  String INITCOMPLETE ="initcomplete";
        public static  String WORLDLOADED ="worldloaded";
        public static  String ANNOUNCEMENT ="announcement";
        public static  String MFI ="mutual_friend_invite";
        public static  String ACTIVE_BUILDING ="active_building";
        public static  String CRUISESHIPS ="cruiseships";
        public static  String BRIDGE ="bridge";
        public static  String QUEST_HIDING ="quest_hiding";
        public static  String FEED ="feed";
        public static  String WALL_TO_WALL ="wall_to_wall";
        public static  String ICON_CLICK ="icon_click";
        public static  String XPROMO ="xpromo";
        public static  String ASK_BUILDING_BUDDIES ="ask_building_buddies";
        public static  String FRIEND_ADDED ="friend_added";

        public  StatsKingdomType ()
        {
            return;
        }//end

    }



