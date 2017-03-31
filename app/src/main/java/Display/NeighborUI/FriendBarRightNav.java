package Display.NeighborUI;

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

import Classes.*;
import Display.aswingui.*;
//import flash.display.*;

    public class FriendBarRightNav extends FriendBarLeftNav
    {

        public  FriendBarRightNav ()
        {
            m_rotation = 0;
            m_direction = 1;
            m_end = 1;
            ASwingHelper.setEasyBorder(this, 5, 0, 0);
            return;
        }//end  

         protected void  initClips ()
        {
            m_direction = 1;
            btn1 =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_1();
            btn1_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_1_down();
            btn4 =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_2();
            btn4_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_2_down();
            btnE =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_end();
            btnE_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_left_end_down();
            return;
        }//end  

    }



