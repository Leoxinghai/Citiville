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
    public class GenericObjectEvent extends Event
    {
        public Object obj ;
        public String subType ="";
        public static  String ROLLOVER_MARKET_CATEGORY ="rolloverMarketCategory";
        public static  String MARKET_SORT ="sortMarketCategory";
        public static  String SPECIALS_CLICK ="specialsMarketClick";
        public static  String SKINS_CLICK ="skinMarketCategory";
        public static  String MARKET_FILTER ="filterMarketCategory";
        public static  String REFRESH_ACHIEVEMENT ="refreshAchievement";
        public static  String REFRESH_BANDIT_INFO ="refreshBanditInfo";
        public static  String MECHANIC_DATA_CHANGED ="mechanicDataChanged";
        public static  String MULTIPICK_PICK_CHANGED ="multipickPickChanged";
        public static  String OBJECT_STORED ="objectStored";
        public static  String OBJECT_PLACED_FROM_STORAGE ="objectPlacedFromStorage";
        public static  String CREW_PURCHASE ="crewPurchased";
        public static  String BUILDING_UPGRADE ="buildingUpgrade";
        public static  String STORED_ITEM_UPGRADE ="storedItemUpgrade";
        public static  String STORED_ITEM_SUPPLY ="storedItemSupply";
        public static  String QUEST_EXPIRED_EVENT ="questExpired";

        public  GenericObjectEvent (String param1 ,Object param2 ,boolean param3 =false ,String param4 ="",boolean param5 =false )
        {
            this.subType = param4;
            this.obj = param2;
            super(param1, param3, param5);
            return;
        }//end  

    }



