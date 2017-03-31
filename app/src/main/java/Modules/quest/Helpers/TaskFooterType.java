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

    public class TaskFooterType
    {
        public static  String PLACE_NOW_FROM_INVENTORY ="placeNowFromInventory";
        public static  String SCROLL_TO_ANNOUNCER ="scrollToAnnouncer";
        public static  String PLACE_NOW_FROM_INVENTORY_NOPIC ="placeNowFromInventoryNoPic";
        public static  String BEGIN_MINI_GAME ="beginMiniGame";
        public static  String OPEN_MARKET ="openMarket";
        public static  String OPEN_PICK_THINGS ="openPickThings";
        public static  String OPEN_CARNIVAL_DIALOG ="openCarnivalDialog";
        public static  String PLACE_NOW_FROM_INVENTORY_OR_PAN ="placeNowFromInventoryOrPan";

        public  TaskFooterType ()
        {
            return;
        }//end

    }



