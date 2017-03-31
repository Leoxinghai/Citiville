package Display.aswingui.inline.util;

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

import org.aswing.*;
    public class ASwingColor
    {
        public static  int ORANGE =12675344;
        public static  int BLUE =2335175;
        public static  int RED =12517378;
        public static  int DIALOG_HINT =16023072;
        public static  int FIRE_BRICK_RED =15608876;
        public static  int BROWN =7356965;
        public static  int FADED_BROWN =10059115;
        public static  int DARK_BROWN =7356965;
        public static  int GREEN_TEXT =46094;
        public static  int LIME_GREEN =6474564;
        public static  int DARK_BLUE =3638975;
        public static  int DARKER_BLUE =489375;
        public static  int YELLOW =16051981;
        public static  int DARK_GREY =1118481;
        public static  int PINK_TEXT =16344739;
        public static  int DARK_PINK =11218531;
        public static  int LIGHT_GRAY =7829624;
        public static  int LIGHT_BLUE =15398395;
        public static  int LIGHTER_BLUE =12969719;
        public static  int PALE_BLUE =3776202;
        public static  int LIGHT_ORANGE =16418589;
        public static  int FADED_ORANGE =14123308;
        public static  int DARK_BLUE_TOOLTIP =5738697;
        public static  int TEXT_SALE_COLOR =11278335;
        public static  int HOVER_GLOW_COLOR =16711935;
        public static  int KEYWORD_GLOW_COLOR =16057340;
        public static  int HIGHLIGHT_ORANGE =15034112;
        public static  int SUB_HIGHLIGHT_BLUE =3181242;
        public static  int ROLL_CALL_BLUE =12116719;
        public static  int ROLL_CALL_STATUS_BLUE =5749455;
        public static  int SALE_POPUP_BLUE =2533856;
        public static  int SALE_POPUP_DARK_BLUE =29116;
        public static  int SALE_POPUP_LIGHT_BLUE =5552087;
        public static  int SALE_POPUP_TEAL =34732;
        public static  int TITLE =16508714;
        public static  int HIGHLIGHT_COLOR =16755200;
        public static  int READY_HIGHLIGHT_COLOR =65496;
        public static  int UPGRADE_HIGHLIGHT_COLOR =589568;
        public static  int PEEP_HIGHLIGHT_COLOR =16771584;
        public static  int BORDER_MAIN_COLOR =872882;
        public static  int TEXT_MAIN_COLOR =872882;
        public static  int TOOL_TIP_STROKE =3355443;
        public static  int WHITE =16777215;
        public static  int VIEWPORT_CLEAR_COLOR =4.28629e +009;
        public static  int VISITOR_INTERACTED_HIGHLIGHT =711625;
        private static SmartASColor colorized ;

        public  ASwingColor ()
        {
            return;
        }//end

        public static ASColor  (int param1 ,double param2 =1)
        {
            if (!colorized)
            {
                colorized = new SmartASColor();
            }
            colorized.color = param1;
            colorized.changeAlpha(param2);
            return colorized;
        }//end

        public static ASColor  create (int param1 ,double param2 =1)
        {
            return get(param1, param2).clone();
        }//end

    }

import org.aswing.*;

class SmartASColor extends ASColor

     SmartASColor ()
    {
        return;
    }//end

    public void  color (int param1 )
    {
        this.rgb = param1;
        return;
    }//end




