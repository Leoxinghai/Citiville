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

import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Display.hud.*;
import Display.hud.components.*;
import Events.*;
import Modules.guide.*;
import Modules.quest.Display.*;
import Modules.quest.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
//import org.aswing.*;
import org.aswing.JWindow;

    public class QuestManagerView extends Sprite
    {
        protected HUDQuestManagerComponent m_questManagerComponent ;
        protected QuestManagerUI m_content ;
        protected JWindow m_window ;
        protected Sprite m_holder ;
        protected Object m_comObject ;
        protected boolean m_shown =false ;
        protected Container m_sideBarContainer ;
        protected Sprite m_sideBar ;
        protected int m_cursorId =0;
        protected boolean m_assetsLoaded =false ;
        protected boolean m_showOnLoad =false ;
        public static  int SIDEBAR_LEFT_OFFSET =-90;
        public static  int SIDEBAR_TOP_OFFSET =30;
        public static  int QUEST_MANAGER_WIDTH =QuestManagerUI.NUM_COLUMNS *QuestManagerCell.CELL_WIDTH +20;
        public static  int QUEST_MANAGER_HEIGHT =QuestManagerUI.NUM_ROWS *QuestManagerCell.CELL_HEIGHT +20;
        public static  int QUEST_MANAGER_TOOLTIP_WIDTH =200;
        public static  int QUEST_MANAGER_TOOLTIP_HEIGHT =180;
        public static  String QUEST_MANAGER_ENABLED ="QuestManagerEnabled";
        public static  String QUEST_MANAGER_DISABLED ="QuestManagerDisabled";
        public static Dictionary assetDict ;

        public  QuestManagerView ()
        {
            this.m_questManagerComponent =(HUDQuestManagerComponent) Global.hud.getComponent(HUD.COMP_QUESTS);
            this.alpha = 0;
            this.loadAssets();
            this.m_content = new QuestManagerUI();
            this.m_sideBar = new Sprite();
            this.addChild(this.m_sideBar);
            this.m_sideBar.x = SIDEBAR_LEFT_OFFSET;
            this.m_sideBar.y = SIDEBAR_TOP_OFFSET;
            return;
        }//end  

        protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.QUEST_ASSETS, this.makeAssets);
            return;
        }//end  

        protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            this.m_comObject = param1;
            this.setChrome();
            return;
        }//end  

        protected void  setChrome ()
        {
            QuestManagerView.assetDict = new Dictionary(true);
            QuestManagerView.assetDict.put("questManagerBG",  this.m_comObject.questManagerBG);
            QuestManagerView.assetDict.put("questManagerCellBG",  this.m_comObject.questManagerCellBG);
            QuestManagerView.assetDict.put("questManagerHideableCellBG",  this.m_comObject.questManagerHideableCellBG);
            QuestManagerView.assetDict.put("questManagerSlotBG",  this.m_comObject.questManagerSlotBG);
            QuestManagerView.assetDict.put("questManagerTabActive",  this.m_comObject.questManagerTabActive);
            QuestManagerView.assetDict.put("questManagerTabInactive",  this.m_comObject.questManagerTabInactive);
            this.m_holder = new Sprite();
            this.addChild(this.m_holder);
            this.m_window = new JWindow(this.m_holder);
            this.m_window.setContentPane(this.m_content);
            ASwingHelper.prepare(this.m_window);
            this.m_window.show();
            this.m_content.init(this);
            this.m_cursorId = UI.setCursor(null);
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_1;
            this.m_assetsLoaded = true;
            if (this.m_showOnLoad)
            {
                this.show();
            }
            return;
        }//end  

        public boolean  isShown ()
        {
            return this.m_shown;
        }//end  

        public void  show ()
        {
            if (!this.m_assetsLoaded)
            {
                this.m_showOnLoad = true;
            }
            if (this.m_content.guideArrow)
            {
                Global.guide.removeArrows();
                this.m_content.guideArrow = null;
            }
            if (GameQuestManager.inHidingExperiment)
            {
                this.content.showHiddenQuests();
                this.content.showActiveQuests();
            }
            else
            {
                this.content.refreshIcons();
            }
            this.showTween();
            this.m_sideBarContainer = (Global.hud.getComponent("quest") as HUDQuestBarComponent).container;
            this.m_sideBar.addChild(this.m_sideBarContainer);
            return;
        }//end  

        public void  close ()
        {
            DisplayObject dispObj ;
            
            dispObj = (DisplayObject)this; // new DisplayObject();
            
            this .hideTween (void  ()
            {
                if (dispObj.parent)
                {
                    dispObj.parent.removeChild(dispObj);
                }
                dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
                onHide();
                return;
            }//end  
            );
            Global.player.saveQuestOrderSequence();
            (Global.hud.getComponent("quest") as HUDQuestBarComponent).addChild(this.m_sideBarContainer);
            this.m_sideBarContainer = null;
            return;
        }//end  

        protected void  showTween ()
        {
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_1;
            this.visible = true;
            this.centerPopup();
            TweenLite.to(this, 0.5, {alpha:1, onComplete:this.onShow});
            this.showMask();
            dispatchEvent(new Event(QUEST_MANAGER_ENABLED));
            Sounds.play("dialogSpawn");
            return;
        }//end  

        protected void  onShow ()
        {
            this.m_cursorId = UI.setCursor(null);
            this.m_shown = true;
            
            this.mouseChildren = true;
            this.mouseEnabled = true;
            
            Global.ui.mouseChildren = true;
            Global.ui.mouseEnabled = true;
            UI.popupLock();
            return;
        }//end  

        protected void  hideTween (Function param1 )
        {
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            if (this.m_content == null)
            {
                if (param1 != null)
                {
                    param1();
                }
            }
            else
            {
                TweenLite.to(this, 0.5, {alpha:0, onComplete:param1});
            }
            dispatchEvent(new Event(QUEST_MANAGER_DISABLED));
            this.hideMask();
            Sounds.play("dialogClose");
            return;
        }//end  

        protected void  onHide ()
        {
            if (this.m_shown == true)
            {
                this.m_shown = false;
                boolean _loc_1 =false ;
                this.mouseChildren = false;
                this.mouseEnabled = _loc_1;
            }
            UI.popupUnlock();
            return;
        }//end  

        public void  centerPopup ()
        {
            Rectangle _loc_1 =null ;
            if (parent)
            {
                _loc_1 = Global.hud.getRect(parent);
                this.x = _loc_1.x + 90;
                this.y = _loc_1.y + 60;
            }
            return;
        }//end  

        protected void  showMask ()
        {
            Global.guide.displayMaskUI(GuideConstants.MASK_GAME_AND_BOTTOMBAR, 0.5);
            return;
        }//end  

        protected void  hideMask ()
        {
            Global.guide.removeMask();
            return;
        }//end  

        public QuestManagerUI  content ()
        {
            return this.m_content;
        }//end  

    }



