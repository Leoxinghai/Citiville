package Classes.sim;

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
import Classes.MiniQuest.*;
import Engine.Helpers.*;
import Modules.quest.Managers.*;
import Modules.stats.experiments.*;
//import flash.utils.*;

    public class MiniQuestManager implements IGameWorldUpdateObserver
    {
        private GameWorld m_world ;
        private Dictionary m_miniQuests ;
        private double m_lastQuestLog ;
        private double m_lastFranchiseLog ;
        private double m_lastHeadquarterInventoryLog ;
        private double m_lastDailyBonusLog ;
        private double m_lastArcadeLog ;
        private double m_lastPendingFranchiseLog ;
        private boolean m_optimizeUpdate =false ;
        private double m_timeUntilUpdate =0;
        private static  double UPDATE_TIME =1;
public static boolean m_giftsSkipped =false ;

        public  MiniQuestManager (GameWorld param1 )
        {
            this.m_optimizeUpdate = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_MINIQUEST_UPDATE) && Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRANCHISES);
            this.m_world = param1;
            param1.addObserver(this);
            this.m_miniQuests = new Dictionary();
            return;
        }//end

        public void  initialize ()
        {
            this.m_timeUntilUpdate = 0;
            this.m_lastQuestLog = 0;
            this.m_lastFranchiseLog = 0;
            this.m_lastHeadquarterInventoryLog = 60;
            this.m_lastDailyBonusLog = 0;
            this.m_lastPendingFranchiseLog = 0;
            this.m_lastArcadeLog = 0;
            return;
        }//end

        public void  resetPendingFranchiseLog ()
        {
            this.m_lastPendingFranchiseLog = 0;
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            this.m_miniQuests.put(FranchiseSaleMQ.QUEST_NAME,  new FranchiseSaleMQ());
            this.m_miniQuests.put(HeadquarterInventoryMQ.QUEST_NAME,  new HeadquarterInventoryMQ());
            this.m_miniQuests.put(DailyBonusMQ.QUEST_NAME,  new DailyBonusMQ());
            this.m_miniQuests.put(GiftMQ.QUEST_NAME,  new GiftMQ());
            this.m_miniQuests.put(RequestItemMQ.QUEST_NAME,  new RequestItemMQ());
            this.m_miniQuests.put(PendingFranchiseMQ.QUEST_NAME,  new PendingFranchiseMQ());
            this.m_miniQuests.put(ZooDonationMQ.QUEST_NAME,  new ZooDonationMQ());
            this.m_miniQuests.put(TicketMQ.QUEST_NAME,  new TicketMQ());
            this.m_miniQuests.put(GardenDonationMQ.QUEST_NAME,  new GardenDonationMQ());
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  update (double param1 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            if (this.m_optimizeUpdate)
            {
                this.m_timeUntilUpdate = this.m_timeUntilUpdate - param1;
                if (this.m_timeUntilUpdate <= 0)
                {
                    this.m_timeUntilUpdate = UPDATE_TIME;
                }
                else
                {
                    return;
                }
            }
            if (param1 !=null)
            {
                this.m_lastQuestLog = this.m_lastQuestLog + param1;
                this.m_lastFranchiseLog = this.m_lastFranchiseLog + param1;
                this.m_lastHeadquarterInventoryLog = this.m_lastHeadquarterInventoryLog + param1;
                this.m_lastDailyBonusLog = this.m_lastDailyBonusLog + param1;
                this.m_lastPendingFranchiseLog = this.m_lastPendingFranchiseLog + param1;
                this.m_lastArcadeLog = this.m_lastArcadeLog + param1;
            }
            if (!Global.player.allowQuests())
            {
                return;
            }
            if (this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME) && this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME).recurrenceTime && Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_SUPPLY_MQ_UNLOCKED))
            {
                if (this.m_lastFranchiseLog > this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME).recurrenceTime)
                {
                    this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME).checkFranchiseSales();
                    if (this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME).needSaleCount)
                    {
                        this.m_miniQuests.get(FranchiseSaleMQ.QUEST_NAME).activate();
                    }
                    this.m_lastFranchiseLog = 0;
                }
            }
            if (this.m_miniQuests.get(HeadquarterInventoryMQ.QUEST_NAME) && this.m_miniQuests.get(HeadquarterInventoryMQ.QUEST_NAME).recurrenceTime && Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_HQ_MQ_UNLOCKED))
            {
                if (this.m_lastHeadquarterInventoryLog > this.m_miniQuests.get(HeadquarterInventoryMQ.QUEST_NAME).recurrenceTime)
                {
                    if (this.m_miniQuests.get(HeadquarterInventoryMQ.QUEST_NAME).isQuestNeeded)
                    {
                        this.m_miniQuests.get(HeadquarterInventoryMQ.QUEST_NAME).activate();
                    }
                    this.m_lastHeadquarterInventoryLog = 0;
                }
            }
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DAILY_BONUS_AUTOPOP);
            if (_loc_2 < ExperimentDefinitions.DAILY_BONUS_AUTOPOP_ON)
            {
                if (this.m_miniQuests.get(DailyBonusMQ.QUEST_NAME) && this.m_miniQuests.get(DailyBonusMQ.QUEST_NAME).recurrenceTime && Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_VISITS_UNLOCKED))
                {
                    if (this.m_lastDailyBonusLog > this.m_miniQuests.get(DailyBonusMQ.QUEST_NAME).recurrenceTime)
                    {
                        if (Global.player.dailyBonusManager.isBonusAvailable && !Global.player.isNewPlayer)
                        {
                            this.m_miniQuests.get(DailyBonusMQ.QUEST_NAME).activate();
                        }
                        this.m_lastDailyBonusLog = 0;
                    }
                }
            }
            if (this.m_miniQuests.get(GiftMQ.QUEST_NAME) && !Global.isVisiting() && !Global.guide.isActive() && Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_VISITS_UNLOCKED) && !Global.player.isFirstDay && !MiniQuestManager.m_giftsSkipped)
            {
                _loc_3 = GlobalEngine.getTimer() / 1000;
                _loc_4 = Global.player.getLastActivationTime(this.m_miniQuests.get(GiftMQ.QUEST_NAME).miniQuestName);
                if (Math.abs(_loc_3 - _loc_4) >= Global.gameSettings().inGameDaySeconds || _loc_4 == -1)
                {
                    this.m_miniQuests.get(GiftMQ.QUEST_NAME).activate();
                }
            }
            if (this.m_miniQuests.get(RequestItemMQ.QUEST_NAME))
            {
                if (this.m_miniQuests.get(RequestItemMQ.QUEST_NAME).isQuestNeeded)
                {
                    this.m_miniQuests.get(RequestItemMQ.QUEST_NAME).activate();
                }
            }
            if (this.m_miniQuests.get(PendingFranchiseMQ.QUEST_NAME))
            {
                if (this.m_lastPendingFranchiseLog > this.m_miniQuests.get(PendingFranchiseMQ.QUEST_NAME).recurrenceTime && this.m_miniQuests.get(PendingFranchiseMQ.QUEST_NAME).recurrenceTime > 0)
                {
                    if (this.m_miniQuests.get(PendingFranchiseMQ.QUEST_NAME).isQuestNeeded)
                    {
                        this.m_miniQuests.get(PendingFranchiseMQ.QUEST_NAME).activate();
                    }
                }
            }
            if (this.m_miniQuests.get(ZooDonationMQ.QUEST_NAME) && this.m_miniQuests.get(ZooDonationMQ.QUEST_NAME).isQuestNeeded && this.m_miniQuests.get(ZooDonationMQ.QUEST_NAME).recurrenceTime)
            {
                this.m_miniQuests.get(ZooDonationMQ.QUEST_NAME).activate();
            }
            if (this.m_miniQuests.get(GardenDonationMQ.QUEST_NAME) && this.m_miniQuests.get(GardenDonationMQ.QUEST_NAME).isQuestNeeded && this.m_miniQuests.get(GardenDonationMQ.QUEST_NAME).recurrenceTime)
            {
                this.m_miniQuests.get(GardenDonationMQ.QUEST_NAME).activate();
            }
            return;
        }//end

        public void  endMiniQuest (String param1 )
        {
            if (this.m_miniQuests.hasOwnProperty(param1))
            {
                this.m_miniQuests.get(param1).deactivate();
            }
            return;
        }//end

        public void  showTicketMQIcon (String param1 )
        {
            Object _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_5 =false ;
            _loc_2 = this.m_miniQuests.get(TicketMQ.QUEST_NAME);
            if (_loc_2)
            {
                _loc_3 = Global.gameSettings().getTicketMQConfigForTheme(param1);
                if (_loc_3)
                {
                    if (_loc_3.get("level"))
                    {
                        _loc_4 = int(_loc_3.get("level"));
                        _loc_5 = Global.player.level < _loc_4;
                        if (!_loc_5)
                        {
                            _loc_2.showIconForTheme(param1);
                        }
                    }
                    else
                    {
                        _loc_2.showIconForTheme(param1);
                    }
                }
            }
            return;
        }//end

        public static void  giftsSkipped (boolean param1 )
        {
            m_giftsSkipped = param1;
            return;
        }//end

    }




