package Modules.quest.Display.QuestManager;

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
import Display.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;

    public class QuestHideButton extends SimpleButton implements IToolTipTarget
    {
        protected String m_tooltip ;

        public  QuestHideButton (Function param1 )
        {
            _loc_2 = new EmbeddedArt.littleClose_up ();
            _loc_3 = new EmbeddedArt.littleClose_over ();
            _loc_4 = new EmbeddedArt.littleClose_down ();
            super(_loc_2, _loc_3, _loc_4, _loc_2);
            this.addEventListener(MouseEvent.CLICK, param1, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false, 0, true);
            this.m_tooltip = ZLoc.t("Quest", "quest_manager_hide_tooltip");
            return;
        }//end

        protected void  onRollOver (MouseEvent event )
        {
            Global.ui.showToolTip(this);
            return;
        }//end

        protected void  onRollOut (MouseEvent event )
        {
            Global.ui.hideToolTip(this);
            return;
        }//end

        public String  getToolTipStatus ()
        {
            return this.m_tooltip;
        }//end

        public String  getToolTipHeader ()
        {
            return null;
        }//end

        public String  getToolTipNotes ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            return null;
        }//end

        public boolean  toolTipFollowsMouse ()
        {
            return true;
        }//end

        public Vector3  getToolTipPosition ()
        {
            return new Vector3(this.x, this.y);
        }//end

        public int  getToolTipFloatOffset ()
        {
            return 0;
        }//end

        public Vector3  getDimensions ()
        {
            return null;
        }//end

        public boolean  getToolTipVisibility ()
        {
            return true;
        }//end

        public Vector2  getToolTipScreenOffset ()
        {
            return new Vector2(-10, 30);
        }//end

        public ToolTipSchema  getToolTipSchema ()
        {
            return null;
        }//end

    }



