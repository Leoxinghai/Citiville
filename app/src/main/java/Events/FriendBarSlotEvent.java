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

import Display.NeighborUI.*;
//import flash.events.*;
//import flash.geom.*;

    public class FriendBarSlotEvent extends Event
    {
        public FriendCell slot ;
        public String m_counter ="";
        public String m_kingdom ="";
        public String m_zClass ="";
        public Point friendPoint ;
        public static  String FRIEND_BAR_SLOT_CLICK ="friendBarSlotClick";
        public static  String FRIEND_BAR_SLOT_ROLLOVER ="friendBarSlotRollover";
        public static  String FRIEND_BAR_SLOT_ROLLOUT ="friendBarSlotRollout";
        public static  String FRIEND_BAR_VISIT_CLICK ="friendBarVisitClick";
        public static  String FRIEND_BAR_GIFT_CLICK ="friendBarGiftClick";

        public  FriendBarSlotEvent (String param1 ,FriendCell param2 ,Point param3 ,String param4 ,String param5 ,String param6 ,boolean param7 =false ,boolean param8 =false )
        {
            this.slot = param2;
            this.friendPoint = param3;
            this.m_counter = param4;
            this.m_kingdom = param5;
            this.m_zClass = param6;
            super(param1, param7, param8);
            return;
        }//end  

    }


