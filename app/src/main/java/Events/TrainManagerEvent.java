package Events;

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

//import flash.events.*;
    public class TrainManagerEvent extends Event
    {
        public static String TIME_UPDATE ="tme_time_update";
        public static String REWARD_UPDATE ="tme_reward_update";
        public static String TRAIN_STATE_UPDATE ="tme_train_state_update";
        public static String TRAIN_STATION_LIST_UPDATE ="tme_train_station_list_update";

        public  TrainManagerEvent (String param1 ,boolean param2 =false ,boolean param3 =false )
        {
            super(param1, param2, param3);
            return;
        }//end  

    }



