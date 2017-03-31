package Modules.quest.Managers;

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
import Classes.Managers.*;
import Classes.util.*;
//import Classes.xml.*;
import Display.*;
import Engine.Events.*;
import Init.*;
import Modules.minigames.*;
import Modules.quest.Display.*;
import Modules.quest.guide.*;
import Modules.saga.*;
import Modules.stats.experiments.*;
import Modules.sunset.*;

//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import validation.*;
import com.facebook.utils.*;

    public class GameQuest extends Quest
    {
        private  int EXPIRE =100;
        private  String DO_NOT_SHOW_COMPLETE ="doNotShowComplete";
        private  int INITIAL_BOTHER =-1;
        private boolean m_isActive =false ;
        private boolean m_everLookedAt =false ;
        private boolean m_hidden =false ;
        private boolean m_hideable =false ;
        private int m_botherCount =0;
        private Timer m_timer ;
        private QuestSignetSprite m_icon =null ;
        private String m_iconUrl ;
        private String m_toolTip ;
        private String m_customQuestDialog =null ;
        private String m_customQuestCompleteDialog =null ;
        private Array m_epilogueDialogs ;
        private XML m_resourceMods ;
        private String m_rewardMultiplier ;
        private int m_rewardMultiplierMax ;
        protected QuestGuide m_guide ;
        protected boolean m_hasIntro ;
        protected boolean m_popNews =false ;
        protected String m_newsImageUrl ;
        private Array m_uxFlags ;
        private Array m_uxFlagsComplete ;
        protected double m_completeWaitTime =0;
        protected boolean m_showCompleteDialog =true ;
        protected boolean m_arrowPersists =false ;
        protected boolean m_autoShowPopup =false ;
        protected boolean m_autoShowOnce =false ;
        protected XMLList m_consumption ;
        protected boolean m_hasConsumptions =false ;
        protected String m_customPickUrl ;
        protected boolean m_hideIcon =false ;
        protected boolean m_animateIcon =false ;
        protected Object m_animationObject =null ;
        protected boolean m_hasCompleted =false ;
        protected String m_questUpdatedTime ;
        protected Array m_notifications ;
        private Function m_introCloseCallback =null ;
        private Function m_completionCallback =null ;
        protected String m_startDate =null ;
        protected Array m_tiers ;
        protected Array m_taskFooters ;
        private GenericValidationScript m_validator ;
        private boolean m_showTimerUI =false ;
        private boolean m_showIconTimerUI =false ;
        private String m_showExtraTimeMessage ="";
        private Sunset m_sunset ;
        private boolean m_tierExpiration ;
        private String m_expireFlagName =null ;
        private Object m_expirationConfig ;
        public static  int NEWSPAPER_AUTOPOP_WAIT =1500;
        private static Array dialogs =.get(QuestGuideDialog ,CityNameDialog ,WelcomeTrainDialog ,QuestInviteFriendsGuideDialog ,QuestPictureDialog ,QuestLetterDialog ,QuestFreeGiftsDialog ,CenteredQuestPopup ,QuestXPromoEpilogueDialog ,QuestMiniGameCompleteDialog ,LandmarksTechTreeHelper) ;

        public  GameQuest (XML param1 )
        {
            XML attr ;
            Array arr ;
            XML notif ;
            Object notifDef ;
            boolean forced ;
            String tstamp ;
            XML xml ;
            int idx ;
            Object taskFooter ;
            String introcallbackName ;
            config = param1;
            this.m_epilogueDialogs = new Array();
            this.m_uxFlags = new Array();
            this.m_uxFlagsComplete = new Array();
            this.m_tiers = new Array();
            this.m_taskFooters = new Array();
            super(config);
            this.m_iconUrl = super.icon;
            this.m_validator = Global.validationManager.getValidator(config.@validate);
            this.m_sunset = Global.sunsetManager.getSunsetByQuestName(this.name);
            if (this.m_sunset && this.m_sunset.isInSunsetInterval())
            {
                this.m_showTimerUI = config.@countDownUI == "true";
                if (config.@extraTimeMessage.length())
                {
                    arr = String(config.@extraTimeMessage).split(":");
                    this.m_showExtraTimeMessage = ZLoc.t(arr.get(0), arr.get(1));
                }
                this.m_showIconTimerUI = config.@iconCountDownUI == "true";
            }
            this.m_tierExpiration = config.@tierExpiration == "true";
            this.m_notifications = new Array();
            if (config.child("notifications").child("notification").length() > 0)
            {
                int _loc_33 =0;
                _loc_43 = config.child("notifications").child("notification");
                for(int i0 = 0; i0 < config.child("notifications").child("notification").size(); i0++)
                {
                		notif = config.child("notifications").child("notification").get(i0);


                    notifDef = new Object();
                    notifDef.put("type",  notif.attribute("type").toString());
                    if (notif.attribute("experiment").length() > 0)
                    {
                        notifDef.put("experiment",  notif.attribute("experiment").toString());
                    }
                    if (notif.attribute("variant").length() > 0)
                    {
                        notifDef.put("variant",  parseInt(notif.attribute("variant").toString()));
                    }
                    this.m_notifications.push(notifDef);
                }
            }
            if (config.child("expirationConfig").length() > 0)
            {
                this.m_expirationConfig = FacebookXMLParserUtils.xmlToObject(config.child("expirationConfig").get(0));
            }
            else
            {
                this.m_expirationConfig = new Object();
                this.m_expirationConfig.put("titleKey",  "questExpired_default");
                this.m_expirationConfig.put("textKey",  "questExpired_default_text");
                this.m_expirationConfig.put("popMidGame",  "true");
            }
            if (GameQuestManager.inHidingExperiment && config.child("hideable").length() > 0)
            {
                forced = config.hideable.attribute("force").length() > 0 && config.hideable.attribute("force").toString() == "true";
                if (config.hideable.attribute("startDate").length() > 0)
                {
                    tstamp = config.hideable.attribute("startDate").toString();
                    if (TimestampEvents.checkTimestampBeforeUnixTime(tstamp, Global.player.currLogin * 1000))
                    {
                        this.m_hideable = true;
                        if (TimestampEvents.checkTimestampAfterUnixTime(tstamp, Global.player.lastLogin * 1000))
                        {
                            if (forced)
                            {
                                this.m_hidden = true;
                            }
                        }
                        else if (forced && Global.player.isQuestNew(this.name))
                        {
                            this.m_hidden = true;
                        }
                        else
                        {
                            this.m_hidden = Global.player.isQuestHidden(this.name);
                        }
                    }
                }
                else
                {
                    this.m_hideable = true;
                    if (forced && Global.player.isQuestNew(this.name))
                    {
                        this.m_hidden = true;
                    }
                    else
                    {
                        this.m_hidden = Global.player.isQuestHidden(this.name);
                    }
                }
            }
            int _loc_4 =0;
            _loc_5 = config.tasks.task;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@action == "seenQuest")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            if (config.intro.length() > 0 && _loc_3.length() > 0)
            {
                this.m_hasIntro = true;
            }
            else
            {
                this.m_hasIntro = false;
            }
            if (String(config.@popNews) == "true")
            {
                this.m_popNews = true;
                this.m_newsImageUrl = String(config.@newsImage);
            }
            enables = String(config.@enables);
            if (enables != "")
            {
                this.m_uxFlags = enables.split(",");
            }
            completeEnables = String(config.@complete_enables);
            if (completeEnables != "")
            {
                this.m_uxFlagsComplete = completeEnables.split(",");
            }
            this.m_completeWaitTime = m_xml.@completeWaitTime;
            this.m_questUpdatedTime = m_xml.@quest_updated_time;
            this.m_customQuestDialog = m_xml.customDialogs.customQuestDialog.@className;
            this.m_customQuestCompleteDialog = m_xml.customDialogs.customQuestCompleteDialog.@className;
            int _loc_31 =0;
            _loc_41 = m_xml.customDialogs.epilogueDialog.@className;
            for(int i0 = 0; i0 < m_xml.customDialogs.epilogueDialog.@className.size(); i0++)
            {
            		attr = m_xml.customDialogs.epilogueDialog.@className.get(i0);


                this.m_epilogueDialogs.push(attr.toString());
            }
            if (this.m_customQuestCompleteDialog == this.DO_NOT_SHOW_COMPLETE)
            {
                this.m_showCompleteDialog = false;
            }
            this.m_rewardMultiplier = String(m_xml.resourceModifiers.@rewardMultiplier);
            this.m_rewardMultiplierMax = int(m_xml.resourceModifiers.@rewardMultiplierMax);
            if (this.m_rewardMultiplierMax < 1)
            {
                this.m_rewardMultiplierMax = 1;
            }
            this.m_resourceMods = m_xml.resourceModifiers.questRewards.get(0);
            this.m_consumption = m_xml.consumption.item;
            if (this.m_consumption != null && this.m_consumption.length() > 0)
            {
                this.m_hasConsumptions = true;
            }
            int taskIdx ;
            while (taskIdx < config.tasks.task.length())
            {

                xml = m_xml.tasks.task.get(taskIdx);
                if (xml.@guide != null)
                {
                    tasks.get(taskIdx).guide = xml.@guide;
                }
                if (xml.@pick_url != null)
                {
                    this.m_customPickUrl = xml.@pick_url;
                }
                taskIdx = (taskIdx + 1);
            }
            if (config.hasOwnProperty("@startDate"))
            {
                this.m_startDate = String(config.@startDate);
            }
            if (config.hasOwnProperty("tiers"))
            {
                if (config.child("tiers").length() > 0)
                {
                    this.parseTiers(config.tiers.get(0));
                }
            }
            if (m_xml.hasOwnProperty("taskFooters"))
            {
                idx;
                while (idx < m_xml.taskFooters.taskFooter.length())
                {

                    xml = m_xml.taskFooters.taskFooter.get(idx);
                    taskFooter = new Object();
                    taskFooter.id = int(xml.@id);
                    taskFooter.action = String(xml.@action);
                    taskFooter.type = String(xml.@type);
                    this.m_taskFooters.push(taskFooter);
                    idx = (idx + 1);
                }
            }
            this.m_guide = new QuestGuide(config, this);
            this.m_botherCount = this.INITIAL_BOTHER;
            (Global.player.numQuestsLoading + 1);
            this.m_timer = new Timer(this.EXPIRE, 0);
            this.m_timer.addEventListener(TimerEvent.TIMER, this.onActivationTimerComplete);
            this.m_timer.start();
            this.m_arrowPersists = String(m_xml.@arrowPersists) == "true";
            this.m_autoShowPopup = String(m_xml.@autoShowPopup) == "true";
            this.m_autoShowOnce = String(m_xml.@autoShowOnce) == "true";
            this.m_animateIcon = String(m_xml.@animateIcon) == "true";
            if (this.m_animateIcon)
            {
                this.m_animationObject = new Object();
                this.m_animationObject.numFrames = int(m_xml.@numFrames);
                this.m_animationObject.frameWidth = int(m_xml.@frameWidth);
                this.m_animationObject.frameHeight = int(m_xml.@frameHeight);
                this.m_animationObject.fps = int(m_xml.@fps);
            }
            if (Global.experimentManager.filterXmlByExperiment(new XMLList(m_xml)).length() == 0)
            {
                this.m_hideIcon = true;
            }
            if (m_xml.child("intro") != null && m_xml.child("intro").length() > 0)
            {
                introcallbackName = String(m_xml.intro.@introCloseCallback).length > 0 ? (String(m_xml.intro.@introCloseCallback)) : (null);
                this.m_introCloseCallback = introcallbackName ? (GameQuestCallbacks.get(introcallbackName)) : (null);
            }
            if (String(m_xml.@onComplete).length != 0)
            {
                this.m_completionCallback = GameQuestCallbacks.get(String(m_xml.@onComplete)) instanceof Function ? (GameQuestCallbacks.get(String(m_xml.@onComplete))) : (null);
            }
            if (String(m_xml.@trackExpiredFlag).length != 0)
            {
                this.m_expireFlagName = String(m_xml.@trackExpiredFlag);
            }
            return;
        }//end

        private void  onActivationTimerComplete (TimerEvent event =null )
        {
            Object _loc_2 =null ;
            if (this.m_timer != null)
            {
                this.m_timer.stop();
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.onActivationTimerComplete);
                this.m_timer = null;
            }
            if (this.m_hasIntro)
            {
                _loc_2 = Global.questManager.getQuestProgressByName(this.name);
                if (m_xml.intro.@questIconUrl != "" && !this.hasDoneTask("seenQuest", m_xml.tasks.task, _loc_2.progress))
                {
                    this.m_iconUrl = m_xml.intro.@questIconUrl;
                }
            }
            if (this.icon && this.icon != "")
            {
                this.m_icon = new QuestSignetSprite(this.name);
                this.m_icon.addEventListener(LoaderEvent.LOADED, this.onIconLoaded, false, 0, true);
                this.m_icon.load();
            }
            else
            {
                (Global.player.numQuestsLoading - 1);
            }
            return;
        }//end

         public void  activate ()
        {
            super.activate();
            Global.questManager.timedQuestActivated_FinalSetup(this.name);
            return;
        }//end

        public String  getToolTipText ()
        {
            String _loc_4 =null ;
            Object _loc_5 =null ;
            _loc_1 =Global.questManager.getQuestProgressByName(this.name );
            _loc_2 = _loc_1==null ;
            String _loc_3 ="";
            if (this.visible && !_loc_2)
            {
                _loc_4 = SagaManager.instance.getSagaNameByQuestName(this.name);
                if (_loc_4)
                {
                    _loc_3 = ZLoc.t("Saga", _loc_4 + "_hover");
                }
                else
                {
                    _loc_5 = Global.questManager.getLocObjects();
                    _loc_3 = ZLoc.t("Quest", this.name + "_goal", _loc_5);
                }
            }
            return _loc_3;
        }//end

        public Array  getProgressStrings ()
        {
            Object _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            Array _loc_1 =new Array ();
            _loc_2 =Global.questManager.getQuestProgressByName(this.name );
            _loc_3 = _loc_2==null ;
            String _loc_4 ="";
            if (this.visible && !_loc_3)
            {
                _loc_5 = Global.questManager.getLocObjects();
                _loc_6 = 0;
                _loc_7 = 0;
                while (_loc_7 < 4)
                {

                    if (_loc_2.progress.get(_loc_6) != null)
                    {
                        _loc_4 = _loc_4 + ZLoc.t("Quest", this.name + "_task" + (_loc_7 + 1), _loc_5);
                        if (!Global.questManager.isTaskActionVisible(this.tasks.get(_loc_7).action))
                        {
                            _loc_6++;
                        }
                        _loc_4 = _loc_4 + (": " + Math.min(_loc_2.progress.get(_loc_6), this.tasks.get(_loc_6).total) + "/" + this.tasks.get(_loc_6).total);
                        _loc_1.push(_loc_4);
                        _loc_4 = "";
                    }
                    _loc_6++;
                    _loc_7++;
                }
            }
            return _loc_1;
        }//end

        private void  onIconLoaded (LoaderEvent event )
        {
            this.m_icon.removeEventListener(LoaderEvent.LOADED, this.onIconLoaded);
            (Global.player.numQuestsLoading - 1);
            this.m_toolTip = this.getToolTipText();
            this.addToQuestBar();
            this.updateProgressFlag();
            this.checkBotherTimer();
            return;
        }//end

        protected void  showInitialGoalOverlay ()
        {
            String arrowType ;
            String potentialNotificationType ;
            Sunset sunset ;
            GameQuest thiz ;
            String flagNameLessThanOneDay ;
            String flagNameMoreThanOneDay ;
            if (Global.player.allowQuests())
            {
             showTimeLeftArrow = function()void
             {
                Global.player.setLastActivationTime(thiz.name, int(GameSettingsInit.getCurrentTime() / 1000));
                _loc_3 = m_botherCount+1;
                m_botherCount = _loc_3;
                _loc_1 = sunset.daysLeft;
                if (_loc_1 <= 1)
                {
                    Global.hud.showGoalsProgressOverlayOnQuestIcon(thiz.name, "sunset", true);
                }
                else
                {
                    Global.hud.showGoalsProgressOverlayOnQuestIcon(thiz.name, "sunsets", true, {numDays:Math.ceil(_loc_1)});
                }
                return;
              }//end
              ;
                potentialNotificationType = this.getNotificationOverlayType();
                if (Global.player.getLastActivationTime(name) == -1 || this.m_arrowPersists)
                {
                    arrowType = this.getProgressOverlayType();
                    if (potentialNotificationType)
                    {
                        arrowType = potentialNotificationType;
                    }
                }
                else if (this.hasBeenUpdated())
                {
                    arrowType = this.getUpdateOverlayType();
                }
                else if (potentialNotificationType)
                {
                    arrowType = potentialNotificationType;
                }
                sunset = Global.sunsetManager.getSunsetByQuestName(this.name);
                thiz;
                if (sunset && sunset.isInSunsetInterval())
                {
                    flagNameLessThanOneDay = "sunset1_" + this.name;
                    flagNameMoreThanOneDay = "sunset2_" + this.name;
                    if (sunset.daysLeft < 1 && Global.player.getFlag(flagNameLessThanOneDay).value < 2)
                    {
                        showTimeLeftArrow();
                        Global.player.getFlag(flagNameLessThanOneDay).setAndSave((Global.player.getFlag(flagNameLessThanOneDay).value + 1));
                        return;
                    }
                    if (Global.player.getFlag(flagNameMoreThanOneDay).value < 2)
                    {
                        showTimeLeftArrow();
                        Global.player.getFlag(flagNameMoreThanOneDay).setAndSave((Global.player.getFlag(flagNameMoreThanOneDay).value + 1));
                        return;
                    }
                }
                if (arrowType)
                {
                    Global.player.setLastActivationTime(name, int(GlobalEngine.getTimer() / 1000));
                    Global.hud.showGoalsProgressOverlayOnQuestIcon(name, arrowType, this.m_arrowPersists);
                    this.m_botherCount++;
                }
            }
            return;
        }//end

        protected boolean  hasBeenUpdated ()
        {
            return this.m_questUpdatedTime && TimestampEvents.checkTimestampAfterUnixTime(this.m_questUpdatedTime, Global.player.getLastActivationTime(name) * 1000);
        }//end

        public void  resolveDependencies ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            XML _loc_5 =null ;
            GenericValidationScript _loc_6 =null ;
            String _loc_7 =null ;
            Vector<XML> _loc_1 =new Vector<XML>();
            _loc_1.push(m_xml);
            while (_loc_1.length > 0)
            {

                _loc_2 = _loc_1.pop();
                for(int i0 = 0; i0 < _loc_2.sequels.sequel.size(); i0++)
                {
                		_loc_3 = _loc_2.sequels.sequel.get(i0);

                    _loc_5 = QuestSettingsInit.getQuestXMLByName(_loc_3.@name);
                    _loc_6 = Global.validationManager.getValidator(_loc_5.@validate);
                    if (_loc_6 && !_loc_6.validate())
                    {
                        _loc_1.push(_loc_5);
                    }
                }
                _loc_4 = _loc_2.resourceModifiers.questRewards.get(0);
                if (_loc_4)
                {
                    _loc_7 = _loc_4.@itemUnlock;
                    if (_loc_7 && _loc_7.length > 0)
                    {
                        Global.player.setSeenSessionFlag(_loc_4.@itemUnlock);
                    }
                }
            }
            if (UI.m_catalog)
            {
                UI.m_catalog.updateChangedCells();
            }
            return;
        }//end

        public Object  getRewards (double param1 ,XML param2 )
        {
            GameQuestTier _loc_6 =null ;
            XMLList _loc_7 =null ;
            int _loc_8 =0;
            String _loc_9 =null ;
            if (param2 == null)
            {
                param2 = this.m_resourceMods;
                _loc_6 = this.getCurrentTierObject(param1);
                if (_loc_6)
                {
                    return _loc_6.questRewards;
                }
            }
            Object _loc_3 ={};
            int _loc_4 =1;
            _loc_5 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_PAYOUT_MULTIPLE );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_PAYOUT_MULTIPLE))
            {
                _loc_4 = _loc_5;
            }
            _loc_4 = _loc_4 * Math.min(this.getRewardMultiplier(this.m_rewardMultiplier), this.m_rewardMultiplierMax);
            if (param2)
            {
                _loc_7 = param2.attributes();
                _loc_8 = 0;
                while (_loc_8 < _loc_7.length())
                {

                    _loc_9 = _loc_7.get(_loc_8).name().toString();
                    switch(_loc_9)
                    {
                        case "xp":
                        case "gold":
                        case RegenerableResource.GAS:
                        case "goods":
                        case "energy":
                        {
                            _loc_3.put(_loc_9,  String(int(_loc_7.get(_loc_8).toString()) * _loc_4));
                            break;
                        }
                        default:
                        {
                            _loc_3.put(_loc_9,  _loc_7.get(_loc_8).toString());
                            break;
                            break;
                        }
                    }
                    _loc_8++;
                }
            }
            return _loc_3;
        }//end

        protected double  getRewardMultiplier (String param1 )
        {
            double _loc_2 =1;
            switch(param1)
            {
                case "minigameScore":
                {
                    if (MiniGame.getMiniGame())
                    {
                        _loc_2 = MiniGame.getMiniGame().getResults().score;
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_2;
        }//end


        public void  updateProgressFlag ()
        {
            _loc_1 =Global.questManager.getQuestProgressByName(this.name );
            if (_loc_1 == null)
            {
                return;
            }
            _loc_2 =(Array) _loc_1.progress;
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                _loc_3 = _loc_3 + _loc_2.get(_loc_4);
                _loc_4++;
            }
            this.m_everLookedAt = _loc_3 > 0;
            return;
        }//end

        public void  awardQuestRewards (double param1 )
        {
            Global.player.giveRewards(this.getRewards(param1), true);
            return;
        }//end

        private void  bubbleSagaData (String param1 )
        {
            String _loc_2 =null ;
            if (SagaManager.instance.getSagaNameByQuestName(param1))
            {
                _loc_2 = SagaManager.instance.getSagaHudNameByQuestName(param1);
                Global.hud.removeQuestSprite(_loc_2, false, true);
            }
            return;
        }//end

        public boolean  isLoaded ()
        {
            return this.m_icon && this.m_icon.loaded && name && this.m_toolTip && Global.player.allowQuests();
        }//end

        public void  addToQuestBar ()
        {
            String flag ;
            boolean allowAutoShow ;
            boolean noAutoShow ;
            boolean mustAutoShow ;
            if (this.isLoaded() && !this.m_hideIcon)
            {
                this.bubbleSagaData(name);
                this.updateQuestIconTimer();
                Global.hud.addQuestSprite(name, this.m_icon, this.m_toolTip);
                allowAutoShow = this.m_autoShowPopup || this.m_autoShowOnce;
                if (allowAutoShow && !Global.isVisiting())
                {
                    noAutoShow = ReactivationManager.isActionAllowed(ReactivateAction.DISABLE_POPUPS);
                    mustAutoShow = ReactivationManager.isPlayerFirstTime();
                    if (!noAutoShow || mustAutoShow)
                    {
                        if (!this.m_autoShowOnce || Global.player.getLastActivationTime(name) == -1)
                        {
                            TimerUtil .callLater (void  ()
            {
                Global.questManager.pumpActivePopup(name);
                return;
            }//end
            , NEWSPAPER_AUTOPOP_WAIT);
                            this.m_autoShowPopup = false;
                        }
                    }
                }
                this.showInitialGoalOverlay();
            }
            int _loc_2 =0;
            _loc_3 = this.m_uxFlags ;
            for(int i0 = 0; i0 < this.m_uxFlags.size(); i0++)
            {
            		flag = this.m_uxFlags.get(i0);


                Global.questManager.unlockUX(flag);
            }
            return;
        }//end

        private void  checkBotherTimer ()
        {
            _loc_1 =Global.gameSettings().getString("questAllowBother","true")=="true";
            if (!this.m_everLookedAt && _loc_1)
            {
                this.m_timer = new Timer(Global.gameSettings().getInt("questBotherTime", 30000), 0);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.onBotherTimerComplete);
                this.m_timer.start();
            }
            return;
        }//end

        private void  onBotherTimerComplete (TimerEvent event =null )
        {
            if (this.m_timer != null)
            {
                this.m_timer.stop();
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.onBotherTimerComplete);
                this.m_timer = null;
            }
            if (!this.m_everLookedAt)
            {
                Global.hud.showGoalsProgressOverlayOnQuestIcon(name, this.getProgressOverlayType());
                this.m_botherCount++;
            }
            this.checkBotherTimer();
            return;
        }//end

        private String  getProgressOverlayType ()
        {
            if (this.m_popNews)
            {
                return "news";
            }
            if (this.m_botherCount & 1 && this.m_botherCount != this.INITIAL_BOTHER)
            {
                return "click";
            }
            return "goals";
        }//end

        public boolean  canShowQuestIconUI ()
        {
            return this.m_showIconTimerUI;
        }//end

        private String  getNotificationOverlayType ()
        {
            Object _loc_1 =null ;
            if (this.m_notifications.length > 0)
            {
                _loc_1 = this.m_notifications.get(0);
                if (_loc_1.hasOwnProperty("experiment") && _loc_1.hasOwnProperty("variant"))
                {
                    if (Global.experimentManager.getVariant(_loc_1.get("experiment")) == _loc_1.get("variant"))
                    {
                        return _loc_1.get("type");
                    }
                }
                else
                {
                    return _loc_1.get("type");
                }
            }
            return null;
        }//end

        private String  getUpdateOverlayType ()
        {
            return "update";
        }//end

        public String Vector  getSequels ().<>
        {
            XML _loc_2 =null ;
            Vector<String> _loc_1 =new Vector<String>();
            for(int i0 = 0; i0 < m_xml.sequels.sequel.size(); i0++)
            {
            		_loc_2 = m_xml.sequels.sequel.get(i0);

                _loc_1.push(_loc_2.@name);
            }
            return _loc_1;
        }//end

        public Array  tiers ()
        {
            return this.m_tiers;
        }//end

        protected void  parseTiers (XML param1 )
        {
            XML _loc_3 =null ;
            GameQuestTier _loc_4 =null ;
            Array _loc_2 =new Array ();
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.tier.size(); i0++)
                {
                		_loc_3 = param1.tier.get(i0);

                    _loc_4 = new GameQuestTier();
                    _loc_4.name = String(_loc_3.@name);
                    if (_loc_3.hasOwnProperty("@duration"))
                    {
                        _loc_4.duration = DateUtil.convertTimeStrToSeconds(String(_loc_3.@duration));
                    }
                    if (_loc_3.hasOwnProperty("@lastChance"))
                    {
                        _loc_4.lastChance = DateUtil.convertTimeStrToSeconds(String(_loc_3.@lastChance));
                    }
                    if (_loc_3.hasOwnProperty("@minimumDuration"))
                    {
                        _loc_4.minimumDuration = DateUtil.convertTimeStrToSeconds(String(_loc_3.@minimumDuration));
                    }
                    if (_loc_3.hasOwnProperty("@pro_rate"))
                    {
                        _loc_4.pro_rate = String(_loc_3.@pro_rate);
                    }
                    if (_loc_3.hasOwnProperty("@hiddenTierIcon"))
                    {
                        _loc_4.hiddenTierIcon = String(_loc_3.@hiddenTierIcon) == "true";
                    }
                    if (_loc_3.hasOwnProperty("resourceModifiers"))
                    {
                        _loc_4.questRewards = this.getRewards(0, _loc_3.resourceModifiers.questRewards.get(0));
                    }
                    if (_loc_3.hasOwnProperty("rewards"))
                    {
                        _loc_4.rewardData = this.getRewardsData(_loc_4.questRewards, _loc_3.rewards.reward);
                        _loc_4.rewardIcon = String(_loc_3.rewards.@icon_url);
                    }
                    _loc_2.push(_loc_4);
                }
            }
            this.m_tiers = _loc_2;
            return;
        }//end

        protected Array  getRewardsData (Object param1 ,XMLList param2 )
        {
            String resource ;
            int count ;
            XMLList rewardList ;
            XML reward ;
            String url ;
            int amount ;
            String itemName ;
            String locCompleted ;
            String locInProgress ;
            rewardsDef = param1;
            sourceXML = param2;
            if (rewardsDef == null)
            {
                rewardsDef = this.getRewards(GlobalEngine.serverTime, null);
            }
            if (sourceXML == null)
            {
                sourceXML = m_xml.rewards.reward;
            }
            Object orderObject ;
            if (sourceXML.length() > 0 && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REWARDS_FIX))
            {
                count;
                while (count < sourceXML.length())
                {

                    orderObject.get(sourceXML.get(count).@name + "_" + sourceXML.put(count).@itemName,  count);
                    count = (count + 1);
                }
            }
            Array rewards ;
            int i ;
            int _loc_4 =0;
            _loc_5 = rewardsDef;
            for(int i0 = 0; i0 < rewardsDef.size(); i0++)
            {
            		resource = rewardsDef.get(i0);


                rewardList;
                if (sourceXML.hasOwnProperty("@name"))
                {
                    int _loc_7 =0;
                    _loc_8 = sourceXML;
                    XMLList _loc_6 =new XMLList("");
                    Object _loc_9;
                    for(int i0 = 0; i0 < _loc_8.size(); i0++)
                    {
                    		_loc_9 = _loc_8.get(i0);


                        with (_loc_9)
                        {
                            if (@name == resource)
                            {
                                _loc_6.put(_loc_7++,  _loc_9);
                            }
                        }
                    }
                    rewardList = _loc_6;
                }
                reward;
                if (rewardList && rewardList.length() > 0)
                {
                    reward = rewardList.get(0);
                }
                else
                {
                    reward = sourceXML.get(i);
                }
                if (reward)
                {
                    if (reward.@silent == "true")
                    {
                        continue;
                    }
                    url = reward.@url;
                    amount = int(rewardsDef.get(resource));
                    itemName = resource == "item" ? (rewardsDef.get(resource)) : (reward.@itemName);
                    locCompleted = String(reward.@locCompleted);
                    locInProgress = String(reward.@locInProgress);
                    if (orderObject.hasOwnProperty(resource + "_" + itemName))
                    {
                        if (rewards.indexOf(orderObject.get(resource + "_" + itemName)) != -1)
                        {
                            rewards.push({resource:resource, amount:amount, url:url, itemName:itemName, loc:locCompleted, locCompleted:locCompleted, locInProgress:locInProgress});
                        }
                        else
                        {
                            rewards.get(orderObject.put(resource + "_" + itemName),  {resource:resource, amount:amount, url:url, itemName:itemName, loc:locCompleted, locCompleted:locCompleted, locInProgress:locInProgress});
                        }
                    }
                    else
                    {
                        rewards.push({resource:resource, amount:amount, url:url, itemName:itemName, loc:locCompleted, locCompleted:locCompleted, locInProgress:locInProgress});
                    }
                }
                i = (i + 1);
            }
            return rewards;
        }//end

        public Array  getCurrentRewardsData ()
        {
            _loc_1 = this.getCurrentTierObject ();
            if (_loc_1)
            {
                return _loc_1.rewardData;
            }
            return this.getRewardsData();
        }//end

        public XML  getQuestData ()
        {
            return m_xml;
        }//end

        public Object  getPopupData ()
        {
            _loc_1 =Global.questManager.getQuestProgressByName(this.name );
            if (_loc_1 == null)
            {
                return null;
            }
            _loc_2 = this.getRewardsData ();
            Object _loc_3 =new Object ();
            _loc_3.guide = this.m_guide;
            _loc_4 = this.getFirstIncompleteTask ();
            if (this.getFirstIncompleteTask() != null)
            {
                _loc_3.guideName = _loc_4.guide;
            }
            _loc_3.name = name;
            _loc_3.isActivated = isActivated();
            _loc_3.url = this.icon;
            _loc_3.intro = m_xml.intro;
            _loc_3.tasks = m_xml.tasks.task;
            _loc_3.taskFooters = m_xml.taskFooters;
            _loc_3.npcName = m_xml.npc.@name;
            _loc_3.npcIntroUrl = m_xml.npc.intro.@url;
            _loc_3.npcActiveUrl = m_xml.npc.active.@url;
            _loc_3.npcCompleteUrl = m_xml.npc.complete.@url;
            _loc_3.finishIcon = m_xml.rewards.@icon_url;
            _loc_3.rewards = _loc_2;
            _loc_3.progress = _loc_1.progress;
            _loc_3.customPickUrl = this.m_customPickUrl;
            _loc_3.isEpilogueData = false;
            _loc_3.introCloseCallback = this.introCloseCallback;
            _loc_3.timeData = this.getPopupTimeData();
            _loc_5 = MiniGame.getMiniGame();
            if (MiniGame.getMiniGame())
            {
                _loc_3.minigameResults = _loc_5.getResults();
            }
            return _loc_3;
        }//end

        public Array  getEpiloguePopupData ()
        {
            XML _loc_2 =null ;
            Object _loc_3 =null ;
            XML _loc_4 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < m_xml.customDialogs.epilogueDialog.size(); i0++)
            {
            		_loc_2 = m_xml.customDialogs.epilogueDialog.get(i0);

                _loc_3 = new Object();
                for(int i0 = 0; i0 < _loc_2.@*.size(); i0++)
                {
                		_loc_4 = _loc_2.@*.get(i0);

                    if (_loc_4.name() != "className")
                    {
                        _loc_3.put(_loc_4.name().toString(),  _loc_4.toString());
                    }
                }
                _loc_3.isEpilogueData = true;
                _loc_1.push(_loc_3);
            }
            return _loc_1;
        }//end

        public void  doQuestCompleteConsumptions ()
        {
            XML _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            int _loc_4 =0;
            for(int i0 = 0; i0 < this.m_consumption.size(); i0++)
            {
            		_loc_1 = this.m_consumption.get(i0);

                _loc_2 = _loc_1.@type;
                _loc_3 = _loc_1.@name;
                _loc_4 = int(_loc_1.@number);
                switch(_loc_2)
                {
                    case "resource":
                    {
                        if (_loc_3 == "coin")
                        {
                            Global.player.gold = Global.player.gold - _loc_4;
                        }
                        else if (_loc_3 == "goods")
                        {
                            Global.player.commodities.remove("goods", _loc_4);
                        }
                        break;
                    }
                    case "inventory":
                    {
                        Global.player.inventory.removeItems(_loc_3, _loc_4);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public void  doQuestCompleteUxUnlocks ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_uxFlagsComplete.size(); i0++)
            {
            		_loc_1 = this.m_uxFlagsComplete.get(i0);

                Global.questManager.unlockUX(_loc_1);
            }
            return;
        }//end

        public void  doQuestExpired ()
        {
            if (!this.m_isActive)
            {
                return;
            }
            if (this.m_hasCompleted)
            {
                return;
            }
            Global.hud.removeQuestSprite(name);
            return;
        }//end

        public boolean  hasConsumptions ()
        {
            return this.m_hasConsumptions;
        }//end

        public DisplayObject  iconDisplayObject ()
        {
            return this.m_icon;
        }//end

        public Class  getCustomQuestDialog ()
        {
            if (this.m_customQuestDialog)
            {
                return getDefinitionByName("Modules.quest.Display." + this.m_customQuestDialog) as Class;
            }
            return null;
        }//end

        public Class  getCustomQuestCompleteDialog ()
        {
            if (this.m_customQuestCompleteDialog && this.m_customQuestCompleteDialog != this.DO_NOT_SHOW_COMPLETE)
            {
                return getDefinitionByName("Modules.quest.Display." + this.m_customQuestCompleteDialog) as Class;
            }
            return null;
        }//end

        public Array  getEpilogueDialogs ()
        {
            Array _loc_1 =null ;
            String _loc_2 =null ;
            if (this.m_epilogueDialogs.length > 0)
            {
                _loc_1 = new Array();
                for(int i0 = 0; i0 < this.m_epilogueDialogs.size(); i0++)
                {
                		_loc_2 = this.m_epilogueDialogs.get(i0);

                    _loc_1.push(getDefinitionByName("Modules.quest.Display." + _loc_2) as Class);
                }
                return _loc_1;
            }
            return null;
        }//end

        public boolean  shouldShowPopup (boolean param1 )
        {
            if (param1 && this.m_showCompleteDialog == false)
            {
                return false;
            }
            _loc_2 =!(tasks.length == 1 && tasks.get(0).action == "popNews");
            boolean _loc_3 =true ;
            return param1 && _loc_2 || !param1 && _loc_3;
        }//end

        public void  resetQuestIcon ()
        {
            if (this.icon != String(m_xml.@url))
            {
                this.m_iconUrl = String(m_xml.@url);
                this.m_icon = new QuestSignetSprite(this.name);
                this.m_icon.addEventListener(LoaderEvent.LOADED, this.swapQuestIconLoaded, false, 0, true);
                this.m_icon.load();
            }
            return;
        }//end

        private void  swapQuestIconLoaded (Event event )
        {
            this.m_icon.removeEventListener(LoaderEvent.LOADED, this.onIconLoaded);
            _loc_2 =Global.questManager.getQuestProgressByName(this.name );
            _loc_3 = _loc_2==null ;
            if (this.visible && !_loc_3)
            {
                Global.hud.removeQuestSprite(name, false);
                this.addToQuestBar();
            }
            return;
        }//end

        protected int  getTaskIndexByAction (String param1 ,XMLList param2 )
        {
            int _loc_3 =0;
            while (_loc_3 < param2.length())
            {

                if (param2.get(_loc_3).@action == param1)
                {
                    return _loc_3;
                }
                _loc_3++;
            }
            return -1;
        }//end

        public boolean  hasDoneTask (String param1 ,XMLList param2 ,Object param3 )
        {
            _loc_4 = this.getTaskIndexByAction(param1 ,param2 );
            if (param3.get(_loc_4) < Number(param2.get(_loc_4).@total))
            {
                return false;
            }
            return true;
        }//end

        public boolean  isTaskComplete (int param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 =Global.questManager.getQuestProgressByName(this.name );
            if (param1 < this.tasks.length())
            {
                if (_loc_3.get(param1) >= this.tasks.get(param1).total)
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public String  getTaskInventoryItemName (int param1 )
        {
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            String _loc_2 =null ;
            if (param1 < this.tasks.length())
            {
                _loc_3 = this.tasks.get(param1);
                if (_loc_3.action && _loc_3.type && _loc_3.footerId)
                {
                    for(int i0 = 0; i0 < this.m_taskFooters.size(); i0++)
                    {
                    		_loc_4 = this.m_taskFooters.get(i0);

                        if (_loc_4.id && _loc_4.action && _loc_4.type)
                        {
                            if (_loc_4.id == _loc_3.footerId)
                            {
                                if (_loc_4.action == "placeNowFromInventory")
                                {
                                    _loc_2 = _loc_4.type;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return _loc_2;
        }//end

        public Object  getExpirationConfig ()
        {
            return this.m_expirationConfig;
        }//end

        public boolean  isQuestComplete ()
        {
            Object _loc_3 =null ;
            _loc_1 =Global.questManager.getQuestProgressByName(this.name );
            int _loc_2 =0;
            while (_loc_2 < this.tasks.length())
            {

                _loc_3 = this.tasks.get(_loc_2);
                if (_loc_1.progress.get(_loc_2) < _loc_3.total)
                {
                    return false;
                }
                _loc_2++;
            }
            return true;
        }//end

        public boolean  isQuestTierExpired ()
        {
            if (this.tierExpiration && isActivatable() && isActivated() && this.getRemainingTieredTime() == 0)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isQuestValid ()
        {
            if (this.m_validator)
            {
                return this.m_validator.validate();
            }
            return true;
        }//end

        public boolean  isCurrentTierTimed ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.getCurrentTierObject ();
            if (_loc_2 && _loc_2.duration > 0)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public boolean  isLastChance ()
        {
            int _loc_3 =0;
            boolean _loc_1 =false ;
            _loc_2 = this.getCurrentTierObject ();
            if (_loc_2 && _loc_2.lastChance > 0)
            {
                _loc_3 = this.getRemainingTimeForTier(_loc_2.name);
                if (_loc_3 <= _loc_2.lastChance)
                {
                    _loc_1 = true;
                }
            }
            return _loc_1;
        }//end

        public boolean  hasTimedTiers ()
        {
            GameQuestTier _loc_3 =null ;
            boolean _loc_1 =false ;
            _loc_2 = this.tiers ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.duration > 0)
                {
                    _loc_1 = true;
                    break;
                }
            }
            return _loc_1;
        }//end

        public Object  getFirstIncompleteTask ()
        {
            Object _loc_3 =null ;
            _loc_1 =Global.questManager.getQuestProgressByName(this.name );
            if (_loc_1 == null)
            {
                return null;
            }
            int _loc_2 =0;
            while (_loc_2 < this.tasks.length())
            {

                _loc_3 = this.tasks.get(_loc_2);
                if (_loc_1.progress.get(_loc_2) < _loc_3.total)
                {
                    return _loc_3;
                }
                _loc_2++;
            }
            return null;
        }//end

        public void  updateGuide ()
        {
            if (this.m_guide)
            {
                this.m_guide.update();
            }
            return;
        }//end

        public void  onResize ()
        {
            if (this.m_guide)
            {
                this.m_guide.onResize();
            }
            return;
        }//end

        public int  getRemainingTieredTime ()
        {
            GameQuestTier _loc_3 =null ;
            _loc_1 = Math.ceil(GlobalEngine.serverTime /1000);
            int _loc_2 =0;
            if (!isActivatable())
            {
                return -1;
            }
            for(int i0 = 0; i0 < this.m_tiers.size(); i0++)
            {
            		_loc_3 = this.m_tiers.get(i0);

                if (_loc_3.duration == 0)
                {
                    return -1;
                }
                _loc_2 = _loc_2 + _loc_3.duration;
            }
            if (!isActivated())
            {
                return _loc_2;
            }
            _loc_4 = activatedTime+_loc_2;
            return Math.max(_loc_4 - _loc_1, 0);
        }//end

        public int  getRemainingTimeForTier (String param1 )
        {
            GameQuestTier _loc_4 =null ;
            int _loc_5 =0;
            _loc_2 = Math.ceil(GlobalEngine.serverTime /1000);
            int _loc_3 =0;
            for(int i0 = 0; i0 < this.m_tiers.size(); i0++)
            {
            		_loc_4 = this.m_tiers.get(i0);

                if (_loc_4.duration == 0)
                {
                    return -1;
                }
                _loc_3 = _loc_3 + _loc_4.duration;
                if (_loc_4.name == param1)
                {
                    if (!isActivated())
                    {
                        if (_loc_4.duration != 0)
                        {
                            return _loc_3;
                        }
                        return -1;
                    }
                    _loc_5 = activatedTime + _loc_3;
                    return Math.max(_loc_5 - _loc_2, 0);
                }
            }
            return -1;
        }//end

        public int  getRemainingTimeForExpirableQuest ()
        {
            int _loc_1 =0;
            if (this.m_sunset)
            {
                _loc_1 = this.m_sunset.getSunsetTimeRemaining();
                return _loc_1 / 1000;
            }
            return 0;
        }//end

        public GameQuestTier  getCurrentTierObject (double param1 =0)
        {
            GameQuestTier _loc_5 =null ;
            GameQuestTier _loc_6 =null ;
            int _loc_7 =0;
            _loc_2 = Math.ceil(param1 /1000);
            if (!isActivated())
            {
                if (this.m_tiers.length > 0)
                {
                    _loc_6 = this.m_tiers.get(0);
                    return _loc_6;
                }
                return null;
            }
            _loc_3 = _loc_2;
            if (_loc_3 == 0)
            {
                _loc_3 = Math.ceil(GlobalEngine.serverTime / 1000);
            }
            int _loc_4 =0;
            for(int i0 = 0; i0 < this.m_tiers.size(); i0++)
            {
            		_loc_5 = this.m_tiers.get(i0);

                if (_loc_5.duration == 0)
                {
                    return _loc_5;
                }
                _loc_4 = _loc_4 + _loc_5.duration;
                _loc_7 = activatedTime + _loc_4;
                if (_loc_3 < _loc_7)
                {
                    return _loc_5;
                }
            }
            return null;
        }//end

        public String  getCurrentTier (double param1 =0)
        {
            _loc_2 = this.getCurrentTierObject(param1 );
            if (_loc_2)
            {
                return _loc_2.name;
            }
            return null;
        }//end

        protected Object  getPopupTimeData ()
        {
            GameQuestTier _loc_3 =null ;
            Object _loc_4 =null ;
            Object _loc_1 =new Object ();
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_tiers.size(); i0++)
            {
            		_loc_3 = this.m_tiers.get(i0);

                _loc_4 = new Object();
                _loc_4.data = _loc_3;
                _loc_2.push(_loc_4);
            }
            _loc_1.activatable = isActivatable();
            _loc_1.activated = isActivated();
            _loc_1.tiers = _loc_2;
            return _loc_1;
        }//end

        public void  updateQuestIconTimer ()
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 = this.getCurrentTierObject ();
            String _loc_2 =null ;
            if (_loc_1)
            {
                _loc_3 = _loc_1.name;
                _loc_4 = _loc_3;
                if (this.isLastChance())
                {
                    _loc_3 = QuestTierConfig.TIER_LAST_CHANCE;
                }
                else if (!isActivated() && isActivatable())
                {
                    _loc_3 = QuestTierConfig.TIER_START;
                    _loc_4 = QuestTierConfig.TIER_START;
                }
                this.m_icon.overlay = _loc_3;
                _loc_2 = this.getQuestIconTextForTier(_loc_4);
            }
            else if (this.m_sunset)
            {
                this.m_icon.overlay = QuestTierConfig.TIER_SUNSET;
                _loc_2 = Global.sunsetManager.getQuestIconTextForExpiringQuest(this);
            }
            this.m_icon.text = _loc_2;
            return;
        }//end

        protected String  getQuestIconTextForTier (String param1 )
        {
            QuestTierConfig _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            String _loc_2 =null ;
            if (QuestTierConfig.TIER_START == param1)
            {
                _loc_3 = Global.gameSettings().getQuestTierConfig(param1);
                if (_loc_3 && _loc_3.overlay)
                {
                    if (_loc_3.overlay.text.locPackage && _loc_3.overlay.text.locKey)
                    {
                        _loc_2 = ZLoc.t(_loc_3.overlay.text.locPackage, _loc_3.overlay.text.locKey);
                    }
                }
            }
            else
            {
                _loc_4 = this.getRemainingTimeForTier(param1);
                if (_loc_4 > 0)
                {
                    _loc_2 = DateUtil.getFormattedDayCounter(_loc_4);
                    _loc_5 = int(_loc_2);
                    if (_loc_5 > 0)
                    {
                        _loc_2 = ZLoc.t("Dialogs", "TimedQuests_days", {count:_loc_5});
                    }
                }
            }
            return _loc_2;
        }//end

         public String  icon ()
        {
            return this.m_iconUrl;
        }//end

        public boolean  canShowTimerUI ()
        {
            return this.m_showTimerUI;
        }//end

        public String  timeMessage ()
        {
            return this.m_showExtraTimeMessage;
        }//end

        public boolean  hasIntro ()
        {
            return this.m_hasIntro;
        }//end

        public boolean  popNews ()
        {
            return this.m_popNews;
        }//end

        public String  newsImageUrl ()
        {
            return this.m_newsImageUrl;
        }//end

        public double  completeWaitTime ()
        {
            return this.m_completeWaitTime;
        }//end

        public String  questUpdatedTime ()
        {
            return this.m_questUpdatedTime;
        }//end

        public boolean  hideIcon ()
        {
            return this.m_hideIcon;
        }//end

        public void  hideIcon (boolean param1 )
        {
            this.m_hideIcon = param1;
            return;
        }//end

        public void  hasCompleted (boolean param1 )
        {
            this.m_hasCompleted = param1;
            return;
        }//end

        public boolean  hasCompleted ()
        {
            return this.m_hasCompleted;
        }//end

        public boolean  hasIconAnimation ()
        {
            return this.m_animateIcon;
        }//end

        public Object  getIconAnimation ()
        {
            return this.m_animationObject;
        }//end

        public Function  introCloseCallback ()
        {
            return this.m_introCloseCallback;
        }//end

        public Function  completionCallback ()
        {
            return this.m_completionCallback;
        }//end

        public boolean  getHidden ()
        {
            return this.m_hidden;
        }//end

        public void  setHidden (boolean param1 )
        {
            this.m_hidden = param1;
            return;
        }//end

        public boolean  hideable ()
        {
            return this.m_hideable;
        }//end

        public boolean  shouldShowHideIcon ()
        {
            return this.m_hideable && !this.m_hidden;
        }//end

        public boolean  tierExpiration ()
        {
            return this.m_tierExpiration;
        }//end

        public String  expireFlag ()
        {
            return this.m_expireFlagName;
        }//end

    }



