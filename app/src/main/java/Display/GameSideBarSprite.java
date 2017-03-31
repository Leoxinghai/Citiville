package Display;

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
import Modules.quest.Display.QuestManager.*;
import Modules.quest.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;

import com.xinghai.Debug;

    public class GameSideBarSprite extends GameSprite implements IToolTipTarget
    {
        protected SimpleButton m_hideButton ;
        public static  int HIDE_BUTTON_LEFT_OFFSET =60;
        public static  double HIDE_BUTTON_SCALE =0.9;

        public  GameSideBarSprite (String param1 )
        {
            this.name = param1;

            UI.questManagerView.addEventListener(QuestManagerView.QUEST_MANAGER_ENABLED, this.enableHideButton, false, 0, true);
            UI.questManagerView.addEventListener(QuestManagerView.QUEST_MANAGER_DISABLED, this.disableHideButton, false, 0, true);
            _loc_2 = new EmbeddedArt.littleClose_up ();
            _loc_3 = new EmbeddedArt.littleClose_over ();
            _loc_4 = new EmbeddedArt.littleClose_down ();
            this.m_hideButton = new QuestHideButton(this.onQuestHideClick);
            this.m_hideButton.x = HIDE_BUTTON_LEFT_OFFSET;
            this.m_hideButton.scaleX = HIDE_BUTTON_SCALE;
            this.m_hideButton.scaleY = HIDE_BUTTON_SCALE;
            if (UI.questManagerView.isShown)
            {
                this.enableHideButton();
            }
            return;
        }//end

         protected void  onRollOver (MouseEvent event )
        {
            if (UI.questManagerView.isShown)
            {
                return;
            }
            Global.ui.showToolTip(this);
            if (m_hideCursor)
            {
                UI.pushBlankCursor();
            }
            return;
        }//end

        public void  enableHideButton (Event event =null )
        {
            _loc_2 =Global.questManager.getQuestByName(this.name );
            if (_loc_2 && _loc_2.hideable)
            {
                this.addChild(this.m_hideButton);
            }
            return;
        }//end

        public void  disableHideButton (Event event =null )
        {
            if (this.contains(this.m_hideButton))
            {
                this.removeChild(this.m_hideButton);
            }
            return;
        }//end

        public void  onQuestHideClick (MouseEvent event )
        {
            _loc_2 = Global.questManager.getQuestByName(this.name);
            Global.questManager.trackQuestHidingAction(StatsPhylumType.QUEST_HIDE, this.name);
            _loc_2.setHidden(true);
            Global.hud.removeQuestSprite(_loc_2.name, true);
            Global.hud.addQuestSprite(_loc_2.name, _loc_2.iconDisplayObject, _loc_2.getToolTipText());
            event.stopPropagation();
            return;
        }//end

        protected void  onQuestManagerEnabled (Event event )
        {
            return;
        }//end

        protected void  onQuestManagerDisabled (Event event )
        {
            return;
        }//end

    }



