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

import Display.*;
import Display.hud.*;
import Display.hud.components.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;

    public class QuestManagerSprite extends GameSprite
    {
        protected DisplayObject m_questIcon ;
        protected Function m_callBack ;
        protected String m_questToolTip ;
        protected boolean m_hidden ;
        protected boolean m_hideable ;
        public static  double GRAY_FILTER_LEVEL =0.33;

        public  QuestManagerSprite (String param1 ,String param2 ,DisplayObject param3 ,Function param4 )
        {
            this.m_questIcon = param3;
            this.m_questToolTip = param2;
            this.m_callBack = param4;
            this.name = param1;
            this.buttonMode = true;
            this.useHandCursor = true;
            this.hideCursor = true;
            this.addChild(this.m_questIcon);
            this.addEventListener(MouseEvent.CLICK, this.onQuestManagerIconClick, false, 0, true);
            this.m_hideable = Global.questManager.getQuestByName(this.name).hideable;
            if (Global.questManager.getQuestByName(this.name).getHidden())
            {
                this.hideQuest();
            }
            else
            {
                this.m_hidden = true;
                this.showQuest();
            }
            return;
        }//end

        public void  refresh ()
        {
            this.addChild(this.m_questIcon);
            return;
        }//end

        public void  onQuestHideClick (MouseEvent event )
        {
            _loc_2 =Global.questManager.getQuestByName(this.name );
            Global.questManager.trackQuestHidingAction(StatsPhylumType.QUEST_HIDE, this.name);
            _loc_2.setHidden(true);
            this.hideQuest();
            UI.questManagerView.content.addHiddenIcon(this);
            event.stopPropagation();
            return;
        }//end

        public DisplayObject  questIcon ()
        {
            return this.m_questIcon;
        }//end

        public Function  questCallBack ()
        {
            return this.m_callBack;
        }//end

        public void  hideQuest ()
        {
            Array _loc_1 =null ;
            ColorMatrixFilter _loc_2 =null ;
            if (!this.m_hidden)
            {
                _loc_1 = .get(GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, 0, 0, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, 0, 0, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, 0, 0, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, GRAY_FILTER_LEVEL, 1, 0);
                _loc_2 = new ColorMatrixFilter(_loc_1);
                this.m_questIcon.filters = .get(_loc_2);
                this.m_hidden = true;
            }
            return;
        }//end

        public void  showQuest ()
        {
            if (this.m_hidden)
            {
                this.m_questIcon.filters = new Array();
                this.m_hidden = false;
            }
            return;
        }//end

        public boolean  hidden ()
        {
            return this.m_hidden;
        }//end

        public String  questToolTip ()
        {
            return this.m_questToolTip;
        }//end

        protected void  onQuestManagerIconClick (MouseEvent event )
        {
            _loc_2 =Global.hud.getComponent(HUD.COMP_QUESTS )as HUDQuestManagerComponent ;
            if (!_loc_2)
            {
                return;
            }
            if (_loc_2.sideBarFull && !_loc_2.removeLastIconAndAddToQuestManager())
            {
                return;
            }
            if (Global.questManager.getQuestByName(this.name).getHidden())
            {
                Global.questManager.trackQuestHidingAction(StatsPhylumType.QUEST_SHOW, this.name);
            }
            UI.questManagerView.content.removeIcon(this, false);
            Global.questManager.getQuestByName(this.name).setHidden(false);
            this.showQuest();
            if (!_loc_2.addQuestManagerSprite(this))
            {
                this.addChild(this.m_questIcon);
                UI.questManagerView.content.addIcon(this);
            }
            StatsManager.count("quest_manager", "add_to_sidebar", this.name);
            return;
        }//end

    }



