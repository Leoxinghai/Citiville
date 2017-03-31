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

    public class StatsCounterType
    {
        public static  String DIALOG ="dialog";
        public static  String DIALOG_UNSAMPLED ="dialog_unsampled";
        public static  String GAME_ACTIONS ="game_actions";
        public static  String PLAYER_COUNTER ="user_start";
        public static  String HUD_COUNTER ="hud_clicks";
        public static  String FRANCHISES ="social_franchises";
        public static  String ERRORS ="errors";
        public static  String SOCIAL_INTERACTIONS ="social_interactions";
        public static  String DAILY_BONUS ="daily_bonus";
        public static  String PROMPTS ="user_prompts";
        public static  String ACHIEVEMENTS ="achievements";
        public static  String CARDMAKER ="cardmaker";
        public static  String SWEEPSTAKES ="sweepstakes";
        public static  String EXPANSION ="expansion";
        public static  String DEBUG ="debug";
        public static  String NEIGHBOR_BAR ="neighbor_bar";
        public static  String SOCIAL_VERB_VISIT_NEIGHBOR ="neighbor_visit";
        public static  String SOCIAL_VERB_UPGRADE ="upgrade";
        public static  String SOCIAL_VERB_GIVE_THANKS ="thanks";
        public static  String QUEST_HIDING ="quest_hiding";
        public static  String RECENTLY_PLAYED_MFS ="reqs2_recently_played";
        public static  String FLASH_MFS ="in_game_flash_mfs";

        public  StatsCounterType ()
        {
            return;
        }//end

    }



