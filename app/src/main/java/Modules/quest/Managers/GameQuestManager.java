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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Events.*;
import Managers.*;
import Modules.GlobalTable.*;
import Modules.quest.Display.*;
import Modules.quest.Display.QuestManager.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;

import com.adobe.utils.*;
//import flash.events.*;
//import flash.utils.*;

    public class GameQuestManager
    {
        private Timer m_startupTimer =null ;
        protected Timer m_questTimer =null ;
        protected double m_timedQuests =0;
        private Object m_loadedQuestData ;
        private boolean m_saveRequired =false ;
        private GameQuest m_currentQuest ;
        private Array m_expiredQuests ;
        public boolean m_activeDialogDisplayed =false ;
        public static  String QUEST_UX_LOCKING_STARTED ="qux_started";
        public static  String QUEST_UX_CROPS_UNLOCKED ="qux_crops";
        public static  String QUEST_UX_RENT_UNLOCKED ="qux_rent";
        public static  String QUEST_UX_BIZ_UNLOCKED ="qux_biz";
        public static  String QUEST_UX_VISITS_UNLOCKED ="qux_visits";
        public static  String QUEST_UX_SUPPLY_MQ_UNLOCKED ="qux_supply_mq";
        public static  String QUEST_UX_HQ_MQ_UNLOCKED ="qux_hq_mq";
        public static  String QUEST_UX_PREMIUM_GOODS_UNLOCKED ="qux_premiumGoodsBar";
        public static  String QUEST_UX_CRUISESHIP ="qux_cruise";
        public static  String QUEST_UX_ZOO_UNLOCK ="zooUnlock_enclosure_jungle";
        public static  String QUEST_UX_ZOO_ARCTIC ="zooUnlock_enclosure_arctic";
        public static  String QUEST_UX_HOTEL_TOASTER ="qux_hotel2";
        public static  Array ALL_QUEST_UX_FLAGS =.get(QUEST_UX_CROPS_UNLOCKED ,QUEST_UX_RENT_UNLOCKED ,QUEST_UX_BIZ_UNLOCKED ,QUEST_UX_VISITS_UNLOCKED ,QUEST_UX_SUPPLY_MQ_UNLOCKED ,QUEST_UX_HQ_MQ_UNLOCKED ,QUEST_UX_PREMIUM_GOODS_UNLOCKED ,QUEST_UX_CRUISESHIP ,QUEST_UX_ZOO_UNLOCK ,QUEST_UX_ZOO_ARCTIC ,QUEST_UX_HOTEL_TOASTER) ;
        private static  int STARTUP_PING_DELAY_MS =5000;
        private static  int STARTUP_PING_DELAY_N =15;
        public static  int QUEST_TIMER_DELAY_MS =1000;

        public  GameQuestManager ()
        {
            _loc_1 = QuestManager.getInstance();
            _loc_1.addEventListener(QuestEvent.STARTED, this.onQuestStarted);
            _loc_1.addEventListener(QuestEvent.PROGRESS, this.onQuestProgress);
            _loc_1.addEventListener(QuestEvent.COMPLETED, this.onQuestCompleted);
            _loc_1.addEventListener(QuestEvent.EXPIRED, this.onQuestExpired);
            this.m_expiredQuests = new Array();
            return;
        }//end

        public void  refreshAllQuests ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FORCE_TRANSACTION_ON_PROGRESS );
            if (_loc_1)
            {
                TransactionManager.addTransaction(new TRefreshQuestsTransaction());
            }
            return;
        }//end

        public void  loadQuestData (Object param1)
        {
            this.m_loadedQuestData = param1;
            return;
        }//end

        public void  startUp ()
        {
            _loc_1 =Global.gameSettings().getInt("questStartupPingDelay",STARTUP_PING_DELAY_MS );
            _loc_2 =Global.gameSettings().getInt("questStartupPingRepeat",STARTUP_PING_DELAY_N );
            this.m_startupTimer = new Timer(_loc_1, _loc_2);
            this.m_startupTimer.addEventListener(TimerEvent.TIMER, this.onStartupTimer);
            this.m_startupTimer.start();
            return;
        }//end

        public void  callEpilogueWithUrl (String param1 ,String param2 )
        {
            Array _loc_3 =.get(param2) ;
            this.displayEpiloguePopups(this.m_currentQuest, _loc_3);
            return;
        }//end

        protected void  onStartupTimer (TimerEvent event )
        {
            int _loc_2 =0;
            if (this.m_loadedQuestData != null && Global.questManager.canWeLoadQuests())
            {
                this.m_startupTimer.stop();
                this.m_startupTimer.removeEventListener(TimerEvent.TIMER, this.onStartupTimer);
                this.m_startupTimer = null;
                _loc_2 = Global.gameSettings().getInt("questTimerRefreshDelay", QUEST_TIMER_DELAY_MS);
                this.m_questTimer = new Timer(_loc_2, 0);
                this.m_questTimer.addEventListener(TimerEvent.TIMER, this.onQuestTimer);
                this.m_questTimer.start();
                GameTransactionManager.addTransaction(new TPingFeedQuests());
                Global.questManager.refreshActiveIconQuests();
                this.fixInventoryItemQuests();
            }
            return;
        }//end

        protected void  onQuestTimer (TimerEvent event )
        {
            if (Global.hud)
            {
                Global.hud.refreshQuestIcons();
            }
            _loc_2 = UI.questManagerView;
            if (_loc_2 && _loc_2.isShown)
            {
                _loc_2.content.refreshIcons();
            }
            return;
        }//end

        protected void  onQuestCompleted (QuestEvent event )
        {
            GameQuest quest ;
            double enqueueTimeMSEC ;
            event = event;
            quest =(GameQuest) event.quest;
            if (quest.hasCompleted)
            {
                return;
            }
            quest.hasCompleted = true;
            Global.player.addCompletedQuest(quest.name);
            enqueueTimeMSEC = event.enqueueTimeMSEC;
            this.m_currentQuest = quest;
            quest.hideIcon = true;
            TimerUtil .callLater (void  ()
            {
                String _loc_2 =null ;
                Global.hud.removeQuestSprite(quest.name);
                quest.awardQuestRewards(enqueueTimeMSEC);
                quest.doQuestCompleteUxUnlocks();
                quest.resolveDependencies();
                if (quest.hasConsumptions() && !quest.wasAnyTaskPurchased())
                {
                    quest.doQuestCompleteConsumptions();
                }
                displayQuestPopup(quest.name + "_complete", quest, true);
                displayEpiloguePopups(quest);
                StatsManager.milestone(getMilestoneName(quest.name, "complete"));
                Global.hud.refreshGoodsHUD();
                _loc_1 = quest.getSequels();
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_2 = _loc_1.get(i0);

                    StatsManager.milestone(getMilestoneName(_loc_2, "begin"));
                }
                if (quest.tasks.get(event.taskCompleted).overrideTable)
                {
                    GlobalTableOverrideManager.instance.removeQuestData(quest.tasks.get(event.taskCompleted).overrideTable, quest.name);
                }
                if (quest.completionCallback != null)
                {
                    quest.completionCallback();
                }
                GameTransactionManager.addTransaction(new TPingFeedQuests());
                return;
            }//end
            , quest.completeWaitTime * 1000);
            return;
        }//end

        protected void  onQuestExpired (QuestEvent event )
        {
            Global.hud.removeQuestSprite(event.quest.name);
            this.m_expiredQuests.push(event.quest.name);
            return;
        }//end

        protected void  onQuestProgress (QuestEvent event )
        {
            _loc_2 =(GameQuest) event.quest;
            _loc_2.updateProgressFlag();
            if (event.taskCompleted != -1)
            {
                if (!(_loc_2.hasIntro && _loc_2.tasks.get(event.taskCompleted).action == "seenQuest" || _loc_2.popNews && _loc_2.tasks.get(event.taskCompleted).action == "popNews"))
                {
                    Global.hud.showGoalsProgressOverlayOnQuestIcon(_loc_2.name, "progress");
                    StatsManager.milestone(this.getMilestoneName(_loc_2.name, "progress_" + event.taskCompleted));
                    if (_loc_2.tasks.get(event.taskCompleted).overrideTable)
                    {
                        GlobalTableOverrideManager.instance.removeQuestData(_loc_2.tasks.get(event.taskCompleted).overrideTable, _loc_2.name);
                    }
                }
            }
            return;
        }//end

        protected void  onQuestStarted (QuestEvent event )
        {
            _loc_2 =(GameQuest) event.quest;
            int _loc_3 =0;
            while (_loc_3 < _loc_2.tasks.length())
            {

                if (_loc_2.tasks.get(_loc_3).overrideTable)
                {
                    GlobalTableOverrideManager.instance.addQuestData(_loc_2.tasks.get(_loc_3).overrideTable, event.quest.name);
                }
                _loc_3++;
            }
            return;
        }//end

        protected String  getMilestoneName (String param1 ,String param2 )
        {
            return "quest_" + param1 + "_" + param2;
        }//end

        private boolean  hasRecentlyExpired (String param1 )
        {
            String _loc_3 =null ;
            boolean _loc_2 =false ;
            for(int i0 = 0; i0 < this.m_expiredQuests.size(); i0++)
            {
            		_loc_3 = this.m_expiredQuests.get(i0);

                if (param1 == _loc_3)
                {
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        public boolean  hasSeenQuestIntro (String param1 )
        {
            GameQuest _loc_3 =null ;
            Object _loc_4 =null ;
            _loc_2 =Global.questManager.getQuestProgressByName(param1 );
            if (_loc_2 && !Global.player.isQuestCompleted(param1))
            {
                _loc_3 = Global.questManager.getQuestByName(param1);
                if (_loc_3)
                {
                    _loc_4 = _loc_3.getPopupData();
                    if (_loc_3.hasIntro && _loc_3.hasDoneTask("seenQuest", _loc_4.tasks, _loc_4.progress))
                    {
                        return true;
                    }
                }
            }
            else if (Global.player.isQuestCompleted(param1))
            {
                _loc_3 = Global.questManager.getQuestByName(param1);
                if (_loc_3 && _loc_3.hasIntro)
                {
                    return true;
                }
            }
            return false;
        }//end

        private boolean  questHasNotRecentlyExpired (Quest param1 )
        {
            String _loc_3 =null ;
            boolean _loc_2 =true ;
            for(int i0 = 0; i0 < this.m_expiredQuests.size(); i0++)
            {
            		_loc_3 = this.m_expiredQuests.get(i0);

                if (param1.name == _loc_3)
                {
                    _loc_2 = false;
                }
            }
            return _loc_2;
        }//end

        public Quest Vector  getActiveQuests ().<>
        {
            Quest _loc_3 =null ;
            _loc_1 = QuestManager.getInstance().getActiveQuests();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 = _loc_1.get(_loc_2);
                if (this.questHasNotRecentlyExpired(_loc_3))
                {
                    _loc_2++;
                    continue;
                }
                _loc_1.splice(_loc_2, 1);
            }
            return _loc_1;
        }//end

        public Quest Vector  getAndRemoveInvalidQuests ().<>
        {
            Quest _loc_3 =null ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            Vector<Quest> _loc_1 =new Vector<Quest>();
            _loc_2 = QuestManager.getInstance().getActiveQuests();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof GameQuest)
                {
                    _loc_4 = GameQuest(_loc_3).isQuestValid();
                    _loc_5 = GameQuest(_loc_3).isQuestTierExpired();
                    if (!_loc_4 || _loc_5)
                    {
                        if (_loc_5)
                        {
                            _loc_6 = GameQuest(_loc_3).getExpirationConfig();
                            if (_loc_6 && _loc_6.get("popMidGame") == "true")
                            {
                                this.showTimedQuestExpired(GameQuest(_loc_3));
                            }
                        }
                        Global.hud.removeQuestSprite(_loc_3.name);
                        QuestManager.getInstance().removeActiveQuestByName(_loc_3.name);
                        GameQuest(_loc_3).resolveDependencies();
                        _loc_1.push(_loc_3);
                        if (GameQuest(_loc_3).expireFlag)
                        {
                            Global.player.setFlag(GameQuest(_loc_3).expireFlag, 1);
                        }
                        Global.world.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.QUEST_EXPIRED_EVENT, _loc_3.name, true));
                    }
                }
            }
            return _loc_1;
        }//end

        public Quest Vector  getIncompleteActiveQuests ().<>
        {
            Quest _loc_3 =null ;
            _loc_1 = QuestManager.getInstance().getIncompleteActiveQuests();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 = _loc_1.get(_loc_2);
                if (this.questHasNotRecentlyExpired(_loc_3))
                {
                    _loc_2++;
                    continue;
                }
                _loc_1.splice(_loc_2, 1);
            }
            return _loc_1;
        }//end

        public Object  getQuestProgressByName (String param1 )
        {
            if (this.hasRecentlyExpired(param1))
            {
                return null;
            }
            return QuestManager.getInstance().getQuestProgressByName(param1);
        }//end

        public int  proRatedCost (String param1 ,int param2 ,double param3 )
        {
            GameQuestTier _loc_14 =null ;
            _loc_4 = this.getQuestByName(param1 );
            if (this.getQuestByName(param1) == null)
            {
                return 0;
            }
            _loc_15 = _loc_4.getQuestData();
            _loc_5 = _loc_4.getQuestData ();
            _loc_5 = _loc_15;
            _loc_6 = _loc_5.tasks.task.get(param2) ;
            if (_loc_5.tasks.task.get(param2) == null)
            {
                return 0;
            }
            _loc_7 =Global.questManager.getQuestProgressByName(param1 ).progress ;
            int _loc_8 =0;
            if (param2 >= 0 && param2 < _loc_7.length())
            {
                _loc_8 = _loc_7.get(param2);
            }
            _loc_9 = int(_loc_6.@cashcost);
            _loc_10 = int(_loc_6.@cashcost);
            _loc_11 = int(_loc_6.@total);
            if (_loc_8 >= _loc_11)
            {
                return 0;
            }
            if (_loc_9 <= 0)
            {
                return 0;
            }
            _loc_12 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PRORATED_QUESTS );
            _loc_13 = String(_loc_5.@pro_rate);
            if (_loc_4.isActivated())
            {
                _loc_14 = _loc_4.getCurrentTierObject(param3);
                if (_loc_14 && _loc_14.pro_rate && _loc_14.pro_rate != "")
                {
                    _loc_13 = _loc_14.pro_rate;
                }
            }
            if (_loc_13 && _loc_13 != "")
            {
                if (_loc_13 == "fixed_1")
                {
                    _loc_12 = ExperimentDefinitions.QUEST_PRICE_FIXED;
                }
                else if (_loc_13 == "discount_2")
                {
                    _loc_12 = ExperimentDefinitions.QUEST_PRO_RATE;
                }
                else if (_loc_13 == "discount_50_3")
                {
                    _loc_12 = ExperimentDefinitions.QUEST_PRO_RATE_50;
                }
                else if (_loc_13 == "experiment")
                {
                }
                else
                {
                    ErrorManager.addError("Quest pro_rate - unknown value \'" + _loc_13 + "\'", ErrorManager.ERROR_UNKNOWN);
                }
            }
            if (_loc_12 == 0)
            {
                return _loc_9;
            }
            if (_loc_12 == ExperimentDefinitions.QUEST_PRICE_FIXED)
            {
                return _loc_9;
            }
            if (_loc_12 == ExperimentDefinitions.QUEST_PRO_RATE)
            {
                _loc_10 = Math.ceil(Number(_loc_9) * ((_loc_11 - _loc_8) / _loc_11));
                if (_loc_10 > _loc_9)
                {
                    _loc_10 = _loc_9;
                }
                if (_loc_10 < 1)
                {
                    _loc_10 = 1;
                }
                return _loc_10;
            }
            if (_loc_12 == ExperimentDefinitions.QUEST_PRO_RATE_50)
            {
                _loc_10 = Math.ceil(Number(_loc_9) * ((2 * _loc_11 - _loc_8) / (2 * _loc_11)));
                if (_loc_10 > _loc_9)
                {
                    _loc_10 = _loc_9;
                }
                if (_loc_10 < 1)
                {
                    _loc_10 = 1;
                }
                return _loc_10;
            }
            return _loc_10;
        }//end

        public int  proRatedDiscountPercentage (String param1 ,int param2 ,double param3 )
        {
            _loc_4 = this.getQuestByName(param1 );
            if (this.getQuestByName(param1) == null)
            {
                return 0;
            }
            _loc_10 = _loc_4.getQuestData();
            _loc_5 = _loc_4.getQuestData ();
            _loc_5 = _loc_10;
            _loc_6 = _loc_5.tasks.task.get(param2) ;
            if (_loc_5.tasks.task.get(param2) == null)
            {
                return 0;
            }
            _loc_7 = int(_loc_6.@cashcost);
            if (int(_loc_6.@cashcost) == 0)
            {
                return 0;
            }
            _loc_8 = this.proRatedCost(param1 ,param2 ,param3 );
            _loc_9 = Math.floor(100*((_loc_7 -_loc_8 )/_loc_7 ));
            return Math.floor(100 * ((_loc_7 - _loc_8) / _loc_7));
        }//end

        public GameQuest  getQuestByName (String param1 )
        {
            Quest _loc_3 =null ;
            _loc_2 = this.getActiveQuests ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof GameQuest && _loc_3.name == param1)
                {
                    return (GameQuest)_loc_3;
                }
            }
            return null;
        }//end

        public void  setQuestTaskProgress (String param1 ,int param2 ,int param3 )
        {
            if (this.hasRecentlyExpired(param1))
            {
                return;
            }
            QuestManager.getInstance().setQuestTaskProgress(param1, param2, param3);
            return;
        }//end

        public boolean  canWeLoadQuests ()
        {
            Quest _loc_2 =null ;
            _loc_1 = this.getIncompleteActiveQuests ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2 instanceof GameQuest)
                {
                    if (this.questHasNotRecentlyExpired(_loc_2))
                    {
                        if (!(_loc_2 as GameQuest).isLoaded())
                        {
                            return false;
                        }
                    }
                }
            }
            return true;
        }//end

        public void  refreshActiveIconQuests ()
        {
            Vector _loc_1.<Quest >=null ;
            Quest _loc_2 =null ;
            GameQuest _loc_3 =null ;
            if (Global.hud.hasQuestManager())
            {
                _loc_1 = this.getIncompleteActiveQuests();
                Global.hud.lockQuestManager();
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_2 = _loc_1.get(i0);

                    if (_loc_2 instanceof GameQuest && this.questHasNotRecentlyExpired(_loc_2))
                    {
                        _loc_3 =(GameQuest) _loc_2;
                        _loc_3.addToQuestBar();
                    }
                }
                Global.hud.unlockQuestManager();
            }
            return;
        }//end

        public void  pumpActivePopup (String param1 )
        {
            Quest _loc_3 =null ;
            GameQuest _loc_4 =null ;
            if (UI.questManagerView.isShown)
            {
                return;
            }
            _loc_2 = this.getActiveQuests ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof GameQuest && _loc_3.name == param1)
                {
                    _loc_4 =(GameQuest) _loc_3;
                    this.displayQuestPopup(_loc_4.name, _loc_4, false);
                }
            }
            return;
        }//end

        public void  expireSpecificQuest (String param1 )
        {
            this.m_expiredQuests.push(param1);
            Global.hud.removeQuestSprite(param1);
            return;
        }//end

        protected void  displayQuestPopup (String param1 ,GameQuest param2 ,boolean param3 )
        {
            _loc_4 = param2.getPopupData ();
            String _loc_5 ="";
            if (_loc_4 == null)
            {
                return;
            }
            _loc_6 = QuestPopup;
            _loc_7 = param3? (param2.getCustomQuestCompleteDialog()) : (param2.getCustomQuestDialog());
            if (param3 ? (param2.getCustomQuestCompleteDialog()) : (param2.getCustomQuestDialog()))
            {
                _loc_6 = _loc_7;
            }
            if (param2.popNews)
            {
                if (!param2.hasDoneTask("popNews", _loc_4.tasks, _loc_4.progress))
                {
                    this.showNews(param1, param2.newsImageUrl);
                }
            }
            else
            {
                if (param2.hasIntro && !param2.hasDoneTask("seenQuest", _loc_4.tasks, _loc_4.progress) && param2.isActivated())
                {
                    this.showQuestIntro(_loc_4, param1);
                    param2.resetQuestIcon();
                    _loc_4 = param2.getPopupData();
                }
                if (!param2.isActivated())
                {
                    _loc_5 = "_activate";
                }
                if (param2.shouldShowPopup(param3))
                {
                    UI.displayPopup(new _loc_6(_loc_4, param3), true, param1 + _loc_5, true);
                }
            }
            return;
        }//end

        protected void  displayEpiloguePopups (GameQuest param1 ,Array param2 )
        {
            int _loc_6 =0;
            Object _loc_7 =null ;
            double _loc_8 =0;
            Class _loc_9 =null ;
            _loc_3 = param1.getEpilogueDialogs ();
            _loc_4 = param1.getEpiloguePopupData ();
            if (param2 != null && param2.length > 0)
            {
                _loc_6 = 0;
                while (_loc_6 < _loc_4.length())
                {

                    if (param2.length <= _loc_6)
                    {
                        _loc_4.get(_loc_6).url = param2.get((param2.length - 1));
                    }
                    else
                    {
                        _loc_4.get(_loc_6).url = param2.get(_loc_6);
                    }
                    _loc_6++;
                }
            }
            if (!_loc_3 || _loc_3.length <= 0)
            {
                return;
            }
            if (!_loc_4 || _loc_4.length != _loc_3.length())
            {
                return;
            }
            int _loc_5 =0;
            while (_loc_5 < _loc_3.length())
            {

                _loc_7 = _loc_4.get(_loc_5);
                _loc_8 = 0;
                if (_loc_7.delay != null)
                {
                    _loc_8 = _loc_7.delay;
                }
                _loc_9 = _loc_3.get(_loc_5);
                if (_loc_9)
                {
                    this.displayEpiloguePopup(_loc_9, _loc_7, _loc_8);
                }
                _loc_5++;
            }
            return;
        }//end

        private void  displayEpiloguePopup (Class param1 ,Object param2 ,double param3 )
        {
            epilogueClass = param1;
            popupObject = param2;
            delay = param3;
            TimerUtil .callLater (void  ()
            {
                UI.displayPopup(new epilogueClass(popupObject));
                return;
            }//end
            , delay * 1000);
            return;
        }//end

        protected void  showQuestIntro (Object param1 ,String param2 )
        {
            _loc_3 = ZLoc.t("Quest",param1.intro.@text, this.getLocObjects());
            _loc_4 = ZLoc.t("Quest",param1.intro.@text + "_title", this.getLocObjects());
            QuestIntroDialog _loc_5 =new QuestIntroDialog(_loc_3 ,_loc_4 ,0,param1.introCloseCallback ,true ,param1.npcIntroUrl );
            UI.displayPopup(_loc_5);
            GameTransactionManager.addTransaction(new TSeenQuest(param2), true);
            return;
        }//end

        public void  showTimedQuestExpired (GameQuest param1 )
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            CharacterDialog _loc_5 =null ;
            if (param1 !=null)
            {
                _loc_2 = param1.getExpirationConfig();
                _loc_3 = _loc_2.get("titleKey");
                _loc_4 = ZLoc.t("Dialogs", _loc_2.get("textKey"), {questName:ZLoc.t("Quest", param1.name + "_dialog_title")});
                _loc_5 = new CharacterDialog(_loc_4, _loc_3, 0, null, null, true);
                UI.displayPopup(_loc_5, true, "questExpired" + param1.name, true);
            }
            return;
        }//end

        protected void  showNews (String param1 ,String param2 )
        {
            _loc_3 = this.getLocObjects ();
            _loc_4 = ZLoc.t("Quest",param1 +"_newsContent",_loc_3 );
            _loc_5 = ZLoc.t("Quest",param1 +"_newsSubHead",_loc_3 );
            UI.displayNewsFlash(_loc_4, 0, param2, _loc_5);
            GameTransactionManager.addTransaction(new TPopNews(param1), true);
            return;
        }//end

        public Object  getLocObjects ()
        {
            _loc_1 =Global.player.snUser.gender =="M"? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
            return {user:ZLoc.tn(Global.player.firstName, _loc_1), cityname:Global.player.cityName};
        }//end

        protected boolean  refreshUxByPredicate (WorldObject param1 )
        {
            if (param1 instanceof Residence || param1 instanceof Business)
            {
                ((MapResource)param1).setState(((MapResource)param1).getState());
            }
            return false;
        }//end

        public void  startUXLocking ()
        {
            Global.player.setSeenFlag(QUEST_UX_LOCKING_STARTED);
            _loc_1 =Global.world.getObjectsByPredicate(this.refreshUxByPredicate );
            return;
        }//end

        public void  unlockUX (String param1 )
        {
            if (!ArrayUtil.arrayContainsValue(ALL_QUEST_UX_FLAGS, param1))
            {
                throw new Error("Invalid quest ux flag: " + param1);
            }
            Global.player.setSeenFlag(param1);
            _loc_2 =Global.world.getObjectsByPredicate(this.refreshUxByPredicate );
            return;
        }//end

        public boolean  isFlagReached (String param1 )
        {
            return Global.player.getSeenFlag(param1) || Global.player.getSeenSessionFlag(param1);
        }//end

        public boolean  isUXUnlocked (String param1 )
        {
            if (!ArrayUtil.arrayContainsValue(ALL_QUEST_UX_FLAGS, param1))
            {
                throw new Error("Invalid quest ux flag: " + param1);
            }
            return !this.isFlagReached(QUEST_UX_LOCKING_STARTED) || this.isFlagReached(param1);
        }//end

        public void  manualQuestStarted_FinalSetup (String param1 ,boolean param2 )
        {
            if (UI.isScreenFrozen())
            {
                UI.thawScreen();
            }
            if (!param2)
            {
                return;
            }
            StatsManager.milestone(this.getMilestoneName(param1, "begin"));
            switch(param1)
            {
                case "holiday_tree1":
                {
                    if (!HolidayTree.doesPlayerHaveTree())
                    {
                        HolidayTree.instance.trackAction("tree_issued");
                        Global.player.inventory.addItems(Item.HOLIDAY2010_TREE_ITEM, 1, true);
                    }
                    break;
                }
                case "neighbor_gate_quest_2":
                {
                    break;
                }
                case "qm_storage_warehouse":
                {
                    Global.player.inventory.addSingletonItem("warehouse", true);
                    break;
                }
                case "qm_visitor_center":
                {
                    Global.player.inventory.addSingletonItem("mun_visitorcenter", true);
                    break;
                }
                case "qm_factory_1":
                {
                    Global.player.inventory.addSingletonItem("factory_premiumgoods", true);
                    break;
                }
                case "qm_clerk_office":
                {
                    Global.player.inventory.addSingletonItem("mun_clerkoffice", true);
                    break;
                }
                case "qm_rent_collector":
                {
                    Global.player.inventory.addSingletonItem("mun_rentcollectordepot", true);
                    break;
                }
                case "qm_hardware1":
                {
                    Global.player.inventory.addSingletonItem("bus_hardwarestore", true);
                    break;
                }
                case "qm_cruiseship_dock":
                {
                    Global.player.inventory.addSingletonItem("dock_house", true);
                    break;
                }
                case "qm_arctic_zoo":
                {
                    Global.player.inventory.addSingletonItem("enclosure_arctic", true);
                    break;
                }
                case "qm_zoo_1":
                {
                    Global.player.inventory.addSingletonItem("enclosure_jungle", true);
                    break;
                }
                case "qm_bridge_1":
                {
                    Global.player.inventory.addSingletonItem("bridge_standard", true);
                    break;
                }
                case "qm_promo_kungfupanda":
                {
                    Global.player.inventory.addSingletonItem("bus_drivein", true);
                    break;
                }
                case "qf_mall2_construction":
                {
                    break;
                }
                case "qf_mall_construction":
                {
                    Global.player.inventory.addSingletonItem("mall", true);
                    break;
                }
                case "qf_ticket_booth":
                {
                    Global.player.inventory.addSingletonItem("mun_carnivalticketbooth", true);
                    break;
                }
                case "qf_carnival_quest":
                {
                    Global.player.inventory.addSingletonItem("mun_streetcarnivalsmall", true);
                    break;
                }
                case "qm_green1":
                {
                    Global.player.inventory.addSingletonItem("deco_windfarm", true);
                    break;
                }
                case "qf_tikisocial":
                {
                    Global.player.inventory.addSingletonItem("tiki_social_business", true);
                    break;
                }
                case "qf_casinosocial":
                {
                    Global.player.inventory.addSingletonItem("casino_social_business", true);
                    break;
                }
                case "qf_hotels":
                {
                    Global.player.inventory.addSingletonItem("resort_hotel_low", true);
                    break;
                }
                case "qf_sandcastle":
                {
                    Global.player.inventory.addSingletonItem("mun_shellticketbooth", true);
                    break;
                }
                case "qf_beach_carnival_quest":
                {
                    Global.player.inventory.addSingletonItem("mun_shellcarnivalsmall", true);
                    break;
                }
                case "qm_landmark_sailboat_hotel":
                {
                    Global.player.inventory.addSingletonItem("hotel_sailboat_low", true);
                    break;
                }
                case "qf_dam_1":
                {
                    Global.player.inventory.addSingletonItem("mun_dam", true);
                    break;
                }
                case "qm_clown":
                {
                    Global.player.inventory.addSingletonItem("mun_circus_clowncollege", true);
                    break;
                }
                case "qm_remodeling":
                {
                    Global.player.inventory.addSingletonItem("mun_constructioncompany", true);
                    break;
                }
                case "qm_promo_bbuy":
                {
                    Global.player.inventory.addSingletonItem("bus_electronicsstore", true);
                    break;
                }
                case "qm_promo_2bbuy":
                {
                    Global.player.inventory.addSingletonItem("bus_electronicsstore", true);
                    Global.player.inventory.addSingletonItem("deco_bestbuygift", true);
                    break;
                }
                case "qf_gardens":
                {
                    Global.player.inventory.addItems("garden_roses_4x4", 1, true);
                    break;
                }
                case "qf_cars":
                {
                    Global.player.inventory.addItems("mun_cargarage", 1, true);
                    break;
                }
                case "q_governor_run_toy_1":
                {
                    Global.player.inventory.addSingletonItem("mun_duckfactory", true);
                    break;
                }
                case "qm_stadiums_baseball":
                {
                    Global.player.inventory.addSingletonItem("mun_baseballstadium", true);
                    break;
                }
                case "qf_enrique_1":
                {
                    Global.player.inventory.addSingletonItem("atr_concert", true);
                    break;
                }
                case "qf_mj_1":
                {
                    Global.player.inventory.addSingletonItem("atr_mj_concert", true);
                    break;
                }
                case "qm_stadiums_soccer":
                {
                    Global.player.inventory.addSingletonItem("mun_soccerstadium", true);
                    break;
                }
                case "qm_spyagency":
                {
                    Global.player.inventory.addSingletonItem("mun_spy_building", true);
                    break;
                }
                case "qm_citysymphony":
                {
                    Global.player.inventory.addSingletonItem("mun_metro_symphonyhall", true);
                    break;
                }
                case "qm_prodigystudios":
                {
                    Global.player.inventory.addSingletonItem("mun_metro_recordingstudio", true);
                    break;
                }
                case "qm_coliseum":
                {
                    Global.player.inventory.addSingletonItem("mun_coliseum", true);
                    break;
                }
                case "q_governor_run_act2_nature_1":
                {
                    Global.player.inventory.addSingletonItem("mun_conservatory", true);
                    break;
                }
                case "qf_animalrescue":
                {
                    Global.player.inventory.addItems("mun_animalrescue", 1, true);
                    break;
                }
                case "qf_universities":
                {
                    Global.player.inventory.addSingletonItem("univ_library", true);
                    break;
                }
                case "qf_candybooth":
                {
                    Global.player.inventory.addSingletonItem("mun_candyticketbooth", true);
                    break;
                }
                case "qm_farmers":
                {
                    Global.player.inventory.addSingletonItem("mun_insurancebuilding", true);
                    break;
                }
                case "q_cokezero":
                {
                    Global.player.inventory.addSingletonItem("bus_cocacolaplant", true);
                    break;
                }
                case "qf_halloween_3":
                {
                    Global.player.inventory.addSingletonItem("mun_trickortreathouse", true);
                    break;
                }
                case "qf_halloween_2_2":
                {
                    Global.player.inventory.addSingletonItem("tower_of_terror_regular", true);
                    break;
                }
                case "q_monsterdentist":
                {
                    Global.player.inventory.addSingletonItem("mun_monsterdentist", true);
                    break;
                }
                case "qt_cidermill":
                {
                    Global.player.inventory.addSingletonItem("mun_cidermill", true);
                    break;
                }
                case "qm_applefarm":
                {
                    Global.player.inventory.addSingletonItem("mun_appleorchard", true);
                    break;
                }
                case "q_halloweenneighborhood":
                {
                    Global.player.inventory.addSingletonItem("hood_halloween", true);
                    break;
                }
                case "qm_swissmuseum":
                {
                    Global.player.inventory.addSingletonItem("mun_swiss_museum", true);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        private void  fixInventoryItemQuests ()
        {
            Quest _loc_2 =null ;
            _loc_1 = this.getIncompleteActiveQuests ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2 instanceof GameQuest)
                {
                    this.fixInventoryItemQuest((GameQuest)_loc_2);
                }
            }
            return;
        }//end

        private void  fixInventoryItemQuest (GameQuest param1 )
        {
            Vector _loc_3.<Object >=null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            boolean _loc_2 =false ;
            switch(param1.name)
            {
                case "q_monsterdentist":
                {
                    _loc_2 = true;
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_2)
            {
                _loc_3 = param1.tasks;
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length())
                {

                    if (!param1.isTaskComplete(_loc_4))
                    {
                        _loc_5 = null;
                        _loc_5 = param1.getTaskInventoryItemName(_loc_4);
                        if (_loc_5)
                        {
                            Global.player.inventory.addSingletonItem(_loc_5, true);
                        }
                    }
                    _loc_4++;
                }
            }
            return;
        }//end

        public void  timedQuestActivated_FinalSetup (String param1 )
        {
            switch(param1)
            {
                case "qf_birthday_2011":
                {
                    Global.player.inventory.addSingletonItem("city_center_1", true);
                    Global.player.giveCoupon("coupon_cv_birthday_2011");
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public boolean  isQuestActive (String param1 )
        {
            Object _loc_3 =null ;
            _loc_2 = this.getIncompleteActiveQuests ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof GameQuest && _loc_3.name == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  isTaskActionVisible (String param1 )
        {
            if (param1 == "seenQuest" || param1 == "popNews")
            {
                return false;
            }
            return true;
        }//end

        public void  trackQuestHidingAction (String param1 ,String param2 )
        {
            StatsManager.count(StatsCounterType.HUD_COUNTER, StatsKingdomType.QUEST_HIDING, param1, param2);
            return;
        }//end

        public boolean  showOrStartQuest (String param1 ,boolean param2 =false )
        {
            boolean _loc_3 =false ;
            if (this.isQuestActive(param1))
            {
                this.pumpActivePopup(param1);
                _loc_3 = true;
            }
            else if (param2)
            {
                this.startQuest(param1, true);
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        public void  startQuest (String param1 ,boolean param2 =false )
        {
            GameTransactionManager.addTransaction(new TStartManualQuest(param1), true);
            if (param2)
            {
                UI.freezeScreen(true, false, "FrameLoading_Quest");
            }
            return;
        }//end

        public static boolean  inHidingExperiment ()
        {
            return Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_HIDING) > 0;
        }//end

    }



