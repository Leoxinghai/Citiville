package Classes;

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

    public class WorldObjectTypes
    {
        public static  int RESIDENCE =9;
        public static  int BUSINESS =16;
        public static  int MALL =17;
        public static  int FACTORY =18;
        public static  int ATTRACTION =19;
        public static  int NEIGHBORHOOD =20;
        public static  int DECORATION =21;
        public static  int SHIP =22;
        public static  int HARVESTABLE_SHIP =23;
        public static  int PLOT =24;
        public static  int TRAIN_STATION =32;
        public static  int DOCK =50;
        public static  int DOCK_HOUSE =100;
        public static  int ROAD =128;
        public static  int SIDEWALK =144;
        public static  int ENERGY_TOGGLE_DECO =145;
        public static  int NPC =256;
        public static  int CONSTRUCTION_SITE =512;
        public static  int WILDERNESS =1024;
        public static  int LOT_SITE =1280;
        public static  int HEADQUARTER =1536;
        public static  int MUNICIPAL =2048;
        public static  int LANDMARK =2304;
        public static  int DOOBER =2457;
        public static  int ZOO_ENCLOSURE =39312;
        public static  int GARDEN =524288;
        public static  int HOTEL =524421;
        public static  int WONDER =524423;
        public static  int SOCIAL_BUSINESS =524437;

        public  WorldObjectTypes ()
        {
            return;
        }//end

    }


