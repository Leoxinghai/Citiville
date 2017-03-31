package Modules.garden.ui;

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
    public class GardenDialogEvent extends Event
    {
        public String m_flowerItem ;
        public String hotspotLabel ;
        public String m_data ;
        public static  String REMOVE_FLOWER ="gdeRemoveFlower";
        public static  String ADD_FLOWER ="gdeAddFlower";
        public static  String MOUSE_OVER_HOTSPOT ="gdeMouseOverHotspot";
        public static  String DISPLAY_UPDATE ="gdeDisplayUpdate";
        public static  String BUY_NEW_FLOWER ="gdeBuyFlower";

        public  GardenDialogEvent (String param1 ,String param2 ="",boolean param3 =false ,boolean param4 =false )
        {
            this.m_data = param2;
            super(param1, param3, param4);
            return;
        }//end  

    }



