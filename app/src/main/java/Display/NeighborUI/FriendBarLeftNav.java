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
import Events.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;

    public class FriendBarLeftNav extends JPanel
    {
        protected int m_rotation =180;
        protected int m_direction ;
        protected int m_end =0;
        protected DisplayObject btn1 ;
        protected DisplayObject btn1_over ;
        protected DisplayObject btn4 ;
        protected DisplayObject btn4_over ;
        protected DisplayObject btnE ;
        protected DisplayObject btnE_over ;

        public  FriendBarLeftNav ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 3, SoftBoxLayout.TOP));
            this.initClips();
            JButton _loc_1 =new JButton ();
            ASwingHelper.setEasyBorder(this, 5, 1, 0);
            SimpleButton _loc_2 =new SimpleButton(this.btn1 ,this.btn1_over ,this.btn1_over ,this.btn1_over );
            _loc_1.wrapSimpleButton(_loc_2);
            _loc_1.addActionListener(this.onStepLeft, 0, true);
            this.append(_loc_1);
            JButton _loc_3 =new JButton ();
            SimpleButton _loc_4 =new SimpleButton(this.btn4 ,this.btn4_over ,this.btn4_over ,this.btn4_over );
            _loc_3.wrapSimpleButton(_loc_4);
            _loc_3.addActionListener(this.onFourStepLeft, 0, true);
            this.append(_loc_3);
            JButton _loc_5 =new JButton ();
            SimpleButton _loc_6 =new SimpleButton(this.btnE ,this.btnE_over ,this.btnE_over ,this.btnE_over );
            _loc_5.wrapSimpleButton(_loc_6);
            _loc_5.addActionListener(this.onSkipEndLeft, 0, true);
            this.append(_loc_5);
            ASwingHelper.prepare(this);
            return;
        }//end  

        protected void  initClips ()
        {
            this.m_direction = -1;
            this.btn1 =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_1();
            this.btn1_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_1_down();
            this.btn4 =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_2();
            this.btn4_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_2_down();
            this.btnE =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_end();
            this.btnE_over =(DisplayObject) new EmbeddedArt.hud_cycleBttn_right_end_down();
            return;
        }//end  

        protected void  onStepLeft (AWEvent event )
        {
            event.stopPropagation();
            dispatchEvent(new FriendBarEvent(FriendBarEvent.MOVE, this.m_direction * FriendBar.SLOT_WIDTH, 1));
            return;
        }//end  

        protected void  onFourStepLeft (AWEvent event )
        {
            event.stopPropagation();
            dispatchEvent(new FriendBarEvent(FriendBarEvent.MOVE, this.m_direction * FriendBar.SLOT_WIDTH * 8, 4));
            return;
        }//end  

        protected void  onSkipEndLeft (AWEvent event )
        {
            event.stopPropagation();
            dispatchEvent(new FriendBarEvent(FriendBarEvent.MOVE_END, this.m_end));
            return;
        }//end  

    }



