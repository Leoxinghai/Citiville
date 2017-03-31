package Modules.quest.Display;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Modules.quest.Managers.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class QuestPopup extends GenericDialog
    {
        public Class checkBox ;
        private MovieClip m_window ;
        private Object m_data ;
        private Array m_rowData ;
        private Array m_rewardData ;
        private int m_imagesLoaded =0;
        private boolean m_rowDataProcessed =false ;
        private Sprite m_rewardContainer ;
        private MovieClip m_rewardBubble ;
        private GenericButton m_accept ;
        private GenericButton m_invite ;
        private GenericButton m_skip ;
        private boolean m_questCompleted =false ;
        private Object m_feedData =null ;
        private Object m_assetInfo =null ;
        private Object m_rewardIcon =null ;
        private Object m_npcInfo =null ;
        private boolean m_isIntro ;
        private int m_numAssetLoads =3;
        public static  int OK =1;
        public static  int LATER =0;
        public static  int TYPE_OK =0;
        public static  int TYPE_YESNO =1;

        public  QuestPopup (Object param1 ,boolean param2 =false )
        {
            GameQuest _loc_3 =null ;
            this.m_data = param1;
            this.m_questCompleted = param2;
            if (this.m_data.name)
            {
                _loc_3 = Global.questManager.getQuestByName(this.m_data.name);
                this.m_data.quest = _loc_3;
            }
            super("");
            return;
        }//end

         protected void  loadAssets ()
        {
            int _loc_1 =0;
            String _loc_2 =null ;
            Object _loc_5 =null ;
            QuestTierConfig _loc_6 =null ;
            Array _loc_7 =null ;
            XML _loc_8 =null ;
            Object _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            if (!this.m_questCompleted)
            {
                showLoadingDialog();
            }
            this.m_rowData = new Array();
            int _loc_3 =0;
            if (String(this.m_data.finishIcon))
            {
                this.m_numAssetLoads++;
                this.m_rewardIcon = new Object();
                _loc_2 = Global.getAssetURL(this.m_data.finishIcon);
                this.m_rewardIcon.image = LoadingManager.load(_loc_2, this.onAssetsLoaded);
            }
            if (this.m_data.timeData)
            {
                if (this.m_data.timeData.tiers)
                {
                    _loc_1 = 0;
                    while (_loc_1 < this.m_data.timeData.tiers.length())
                    {

                        _loc_5 = this.m_data.timeData.tiers.get(_loc_1);
                        _loc_6 = Global.gameSettings().getQuestTierConfig(_loc_5.data.name);
                        if (_loc_6)
                        {
                            if (_loc_6.largeRewardIcon.length > 0)
                            {
                                this.m_numAssetLoads++;
                                _loc_5.largeRewardIcon = LoadingManager.load(Global.getAssetURL(_loc_6.largeRewardIcon), this.onAssetsLoaded);
                            }
                            if (_loc_6.smallRewardIcon.length > 0)
                            {
                                this.m_numAssetLoads++;
                                _loc_5.smallRewardIcon = LoadingManager.load(Global.getAssetURL(_loc_6.smallRewardIcon), this.onAssetsLoaded);
                            }
                        }
                        _loc_7 = _loc_5.data.rewardData;
                        if (_loc_7)
                        {
                            _loc_5.rewardData = new Array();
                            this.loadRewardData(_loc_7, _loc_5.rewardData);
                        }
                        this.m_data.put("tier_" + _loc_5.data.name,  _loc_5);
                        _loc_1++;
                    }
                }
                if (this.m_data.quest.canShowTimerUI && this.m_data.timeData || this.m_data.timeData.activatable || this.m_data.timeData.tiers.length > 0)
                {
                    this.m_numAssetLoads++;
                    this.m_data.timeData.clock = LoadingManager.load(Global.getAssetURL("assets/dialogs/quests/timed/quests2_timed_clock.png"), this.onAssetsLoaded);
                }
            }
            _loc_1 = 0;
            while (_loc_1 < this.m_data.tasks.length())
            {

                _loc_8 = this.m_data.tasks.get(_loc_1);
                _loc_9 = new Object();
                _loc_9.name = _loc_8.@action;
                if (!Global.questManager.isTaskActionVisible(String(_loc_8.@action)))
                {
                    _loc_3++;
                }
                else
                {
                    _loc_9.text = ZLoc.t("Quest", this.m_data.name + "_task" + (_loc_1 + (1 - _loc_3)));
                    _loc_2 = Global.getAssetURL(_loc_8.@icon_url);
                    this.m_numAssetLoads++;
                    _loc_9.image = LoadingManager.load(_loc_2, this.onAssetsLoaded);
                }
                _loc_10 = this.m_data.name + "_task" + (_loc_1 + (1 - _loc_3)) + "_hint";
                if (ZLoc.instance.getString("Quest", _loc_10))
                {
                    _loc_11 = ZLoc.t("Quest", _loc_10);
                    _loc_9.hint = _loc_11;
                }
                if (_loc_8.hasOwnProperty("@footerId"))
                {
                    _loc_12 = _loc_8.attribute("footerId").get(0).toString();
                    _loc_9.footerId = _loc_12;
                }
                this.m_rowData.push(_loc_9);
                _loc_1++;
            }
            this.m_npcInfo = new Object();
            this.m_npcInfo.name = this.m_data.npcName;
            if (!this.m_questCompleted)
            {
                _loc_2 = Global.getAssetURL(this.m_data.npcActiveUrl);
            }
            else
            {
                _loc_2 = Global.getAssetURL(this.m_data.npcCompleteUrl);
            }
            this.m_npcInfo.image = LoadingManager.load(_loc_2, this.onAssetsLoaded);
            this.m_rewardData = new Array();
            this.loadRewardData((Array)this.m_data.rewards, this.m_rewardData);
            this.m_assetInfo = new Object();
            Global.delayedAssets.get(DelayedAssetLoader.NEW_QUEST_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.MARKET_ASSETS, makeAssets);
            _loc_4 =Global.getAssetURL(this.m_data.url );
            this.m_assetInfo.icon = LoadingManager.load(_loc_4, this.onAssetsLoaded);
            return;
        }//end

        private void  loadRewardData (Array param1 ,Array param2 )
        {
            String _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Object _loc_7 =null ;
            Item _loc_8 =null ;
            int _loc_3 =0;
            _loc_3 = 0;
            while (_loc_3 < param1.length())
            {

                _loc_5 = param1.get(_loc_3);
                if (_loc_5.get("resource") != "xpromo")
                {
                    _loc_6 = ZLoc.t("Quest", "reward_" + String(_loc_5.resource), {amount:Utilities.formatNumber(int(_loc_5.amount))});
                    if (_loc_5.itemName != null && _loc_5.itemName != "")
                    {
                        _loc_8 = Global.gameSettings().getItemByName(_loc_5.itemName);
                        _loc_6 = _loc_8.localizedName;
                    }
                    if (this.m_questCompleted && _loc_5.locCompleted != "" && _loc_5.locCompleted != null)
                    {
                        _loc_6 = ZLoc.t("Dialogs", _loc_5.locCompleted, {reward:_loc_6});
                    }
                    else if (!this.m_questCompleted && _loc_5.locInProgress != "" && _loc_5.locInProgress != null)
                    {
                        _loc_6 = ZLoc.t("Dialogs", _loc_5.locInProgress, {reward:_loc_6});
                    }
                    else if (_loc_5.loc != "" && _loc_5.loc != null)
                    {
                        _loc_6 = ZLoc.t("Dialogs", _loc_5.loc, {reward:_loc_6});
                    }
                    _loc_7 = new Object();
                    _loc_7.text = _loc_6;
                    _loc_4 = Global.getAssetURL(_loc_5.url);
                    this.m_numAssetLoads++;
                    _loc_7.image = LoadingManager.load(_loc_4, this.onAssetsLoaded);
                    _loc_7.resource = _loc_5.get("resource");
                    param2.push(_loc_7);
                }
                _loc_3++;
            }
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =new Array();
            _loc_1.push(DelayedAssetLoader.INVENTORY_ASSETS, DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS);
            return _loc_1;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_numAssetLoads)
            {
                return;
            }
            closeLoadingDialog();
            Dictionary _loc_2 =new Dictionary ();
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.NEW_QUEST_ASSETS) ;
            _loc_2.put("checkMark",  _loc_3.quest_check);
            _loc_2.put("placeNowArrow",  _loc_3.quest_arrow_place);
            _loc_2.put("horizontalRule",  _loc_3.quest_horizontal_rule);
            _loc_2.put("verticalRule", (DisplayObject) new _loc_3.quest_vertical_rule());
            _loc_2.put("rewardItemBG", (DisplayObject) new _loc_3.quest_item_card());
            _loc_2.put("imageBG_0", (DisplayObject) new _loc_3.quest_bg_slim());
            _loc_2.put("imageBG_1", (DisplayObject) new _loc_3.quest_bg_single());
            _loc_2.put("imageBG_2", (DisplayObject) new _loc_3.quest_bg_half());
            _loc_2.put("imageBG_3", (DisplayObject) new _loc_3.quest_bg_full());
            _loc_2.put("tasksBG",  _loc_3.quest_tasks_bg);
            _loc_2.put("speechBG", (DisplayObject) new _loc_3.quest_speech_bubble());
            _loc_2.put("speechTail", (DisplayObject) new _loc_3.quest_speech_tail());
            _loc_2.put("checkList",  _loc_3.quest_check_list);
            _loc_2.put("tierAvailable",  _loc_3.quest_reward_available);
            _loc_2.put("tierUnavailable",  _loc_3.quest_reward_unavailable);
            _loc_2.put("tierCurrent",  _loc_3.quest_reward_current);
            _loc_2.put("currentQuestBG",  _loc_3.currentQuestBG);
            _loc_2.put("questGroupHolderBG",  _loc_3.questGroupHolderBG);
            _loc_2.put("questGroupHolderBGNoBack",  _loc_3.questGroupHolderBGNoBack);
            _loc_2.put("btnBack",  _loc_3.btnBack);
            _loc_2.put("taskInfo",  this.m_rowData);
            _loc_2.put("rewardInfo",  this.m_rewardData);
            _loc_2.put("mainIcon",  this.m_assetInfo.icon.content);
            _loc_2.put("npcName",  this.m_npcInfo.name);
            _loc_2.put("npcIcon",  this.m_npcInfo.image.content);
            _loc_2.put("locObjects",  Global.questManager.getLocObjects());
            _loc_2.put("questComplete",  this.m_questCompleted);
            if (this.m_rewardIcon)
            {
                _loc_2.put("rewardIcon",  this.m_rewardIcon.image.content);
            }
            if (this.m_questCompleted == true)
            {
                m_jpanel = new QuestCompleteView(_loc_2, this.m_data);
            }
            else if (!this.m_data.isActivated)
            {
                m_jpanel = new QuestStartView(_loc_2, this.m_data);
            }
            else
            {
                m_jpanel = new QuestPopupView(_loc_2, this.m_data);
            }
            m_jpanel.addEventListener(Event.CLOSE, this.closePopup, false, 0, true);
            finalizeAndShow();
            return;
        }//end

         public Point  getDialogOffset ()
        {
            return new Point(0, -5);
        }//end

        private void  closePopup (Event event =null )
        {
            double sfxVolume ;
            e = event;
            int _loc_4 =0;
            _loc_5 =Global.gameSettings().getXML ().sounds.sound ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == "click1")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            sfxVolume = _loc_3.@volume;
            Sounds.play("click1");
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, 0));
            close();
            return;
        }//end

         protected void  onShow ()
        {
            super.onShow();
            Global.ui.putUnderToolTip(this);
            return;
        }//end

        protected void  onAccept (MouseEvent event )
        {
            this.closePopup();
            return;
        }//end

        protected void  onTellFriends (MouseEvent event )
        {
            this.m_accept.getButton().removeEventListener(MouseEvent.CLICK, this.onTellFriends);
            this.m_skip.getButton().removeEventListener(MouseEvent.CLICK, this.onSkip);
            this.closePopup();
            return;
        }//end

        protected void  onSkip (MouseEvent event )
        {
            this.m_accept.getButton().removeEventListener(MouseEvent.CLICK, this.onTellFriends);
            this.m_skip.getButton().removeEventListener(MouseEvent.CLICK, this.onSkip);
            this.closePopup();
            return;
        }//end

         protected String  getDialogStatsTrackingString ()
        {
            _loc_1 = this"quest_"+.m_data.name ;
            if (this.m_questCompleted)
            {
                _loc_1 = _loc_1 + "_complete";
            }
            else if (this.m_isIntro)
            {
                _loc_1 = _loc_1 + "_intro";
            }
            else
            {
                _loc_1 = _loc_1 + "_info";
            }
            return _loc_1;
        }//end

         public boolean  isLockable ()
        {
            return true;
        }//end

    }



