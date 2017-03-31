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
    public class UIEvent extends Event
    {
        public String label ;
        public static  String TAB_CLICK ="tabClick";
        public static  String ACTION_MENU_CLICK ="actionMenuClick";
        public static  String OPEN_ACTION_MENU ="openActionMenu";
        public static  String AUTO ="auto";
        public static  String MOVE ="move";
        public static  String ROTATE ="rotate";
        public static  String REMOVE ="remove";
        public static  String STORE ="store";
        public static  String HEARTS ="hearts";
        public static  String REMODEL ="remodel";
        public static  String CURSOR ="cursor";
        public static  String OTHER ="other";
        public static  String SHOW_COLLECTIONS ="showCollections";
        public static  String SHOW_INVENTORY ="showInventory";
        public static  String SHOW_STORAGE ="showStorage";
        public static  String SHOW_FRANCHISE ="showFranchise";
        public static  String REFRESH_DIALOG ="refreshDialog";
        public static  String ASK_FOR_CREW ="askForCrew";
        public static  String BUY_CREW ="buyCrew";
        public static  String CHANGE_CREW_STATE ="changeCrewState";
        public static  String BUY_DONUT ="boughtADonut";
        public static  String SETTINGS_TOGGLE_CLICK ="settingsToggleClick";
        public static  String PREPARE ="prepare";
        public static  String UPDATE_FRIENDBAR ="UIEvent.updateFriendBar";

        public  UIEvent (String param1 ,String param2 ,boolean param3 =false ,boolean param4 =false )
        {
            this.label = param2;
            super(param1, param3, param4);
            return;
        }//end  

    }



