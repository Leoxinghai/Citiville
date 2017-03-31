package Transactions;

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
import Classes.featuredata.*;
import Classes.util.*;
import Display.*;
import Engine.Events.*;
import Engine.Managers.*;
import Events.*;
import Init.PostInit.*;
import Modules.GlobalTable.*;
import Modules.achievements.*;
import Modules.bandits.*;
import Modules.garden.*;
import Modules.itemcount.*;
import Modules.matchmaking.*;
import Modules.pickthings.*;
import Modules.quest.Managers.*;
import Modules.remodel.*;
import Modules.saga.*;
import Modules.socialinventory.*;
import Modules.stats.experiments.*;
import Modules.stats.trackers.*;
import Modules.stats.types.*;
import Modules.workers.*;

//import flash.events.*;
import org.aswing.*;
//import flash.external.*;

    public class TInitUser extends TFarmTransaction
    {
        protected String m_firstName ;
        private  int NUM_SEEN_EXPAND_FARM =2;
        private  int SHOW_EXPAND_FARM_SIZE =14;
        private  int SHOW_EXPAND_MIN_LEVEL =4;
        private int m_bookmarkCash =0;

        public  TInitUser (String param1 )
        {
            this.m_firstName = param1;
            funName = "UserService.initUser";

            return;
        }//end

         public void  perform ()
        {
            String _loc_1 =null ;
            _loc_1 = String(GlobalEngine.getFlashVar("unique_session_key"));
            ExternalInterface.call("zaspActivity","TInitUser"+"perform"+1);
            signedCall("UserService.initUser", Config.userid,  _loc_1);
            return;
        }//end

         public boolean  isInitTransaction ()
        {
            return true;
        }//end

         protected void  onComplete (Object param1 )
        {
            StartupSessionTracker.initUser();
            if (param1 != null)
            {
                Global.gameSettings().parseAllItems();
                Global.gameSettings().parseAllGlobalValidators();
                Global.gameSettings().parseAllSunsets();
                if (Global.world != null && Global.player != null)
                {
                    this.loadWorld(param1);
                }
                else
                {
                    ErrorManager.addError("InitUser global state is invalid", ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
            }
            else
            {
                ErrorManager.addError("InitUser result is null", ErrorManager.ERROR_REMOTEOBJECT_FAULT);
            }
            return;
        }//end

        public void  loadWorld (Object param1 )
        {
            GameTransactionManager.addTransaction(new TWeiboInit());

            ExternalInterface.call("zaspActivity","TInitUser"+"loadWorld"+0);
            boolean _loc_3 =false ;
            double _loc_4 =0;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            Object _loc_7 =null ;
            boolean _loc_8 =false ;
            Array _loc_9 =null ;
            Object _loc_10 =null ;
            String _loc_11 =null ;
            WorkerManager _loc_12 =null ;
            boolean _loc_13 =false ;
            int _loc_14 =0;
            Item _loc_15 =null ;
            String _loc_16 =null ;
            Array _loc_17 =null ;
            String _loc_18 =null ;
            String _loc_19 =null ;
            double _loc_20 =0;
            double _loc_21 =0;
            double _loc_22 =0;
            double _loc_23 =0;
            String _loc_24 =null ;

            //add by xinghai
                Global.world.citySim.preyManager.workerManagers = new Array();
                if (param1.featureData)
                {
                    _loc_10 = Global.gameSettings().getXmlData("preyGroups");
                    for(int i0 = 0; i0 < _loc_10.size(); i0++)
                    {
                    		_loc_11 = _loc_10.get(i0);

                        _loc_12 = new WorkerManager(_loc_11);
                        //_loc_12.loadObject(param1.featureData.get(_loc_11).get("workers"));
                        Global.world.citySim.preyManager.workerManagers.push(_loc_12);
                    }
                }
                if (param1.featureData && param1.featureData.rollCall)
                {
                    Global.rollCallManager.loadObject(param1.featureData.rollCall);
                }
                if (param1.featureData && param1.featureData.goal)
                {
                    Global.goalManager.loadObject(param1.featureData.goal);
                }

            Global.player.loadObject(param1.userInfo.player);
            Global.world.loadObject(param1.userInfo.world);
            dispatchEvent(new Event(Event.COMPLETE));
            Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            RepaintManager.getInstance().forceRender();


            return;

            ExternalInterface.call("zaspActivity","TInitUser"+"loadWorld"+1);
            if (param1.userInfo && param1.userInfo.attr && param1.userInfo.attr.debugTime)
            {
                Global.player.debugTime = param1.userInfo.attr.debugTime;
            }
            ExternalInterface.call("zaspActivity","TInitUser"+"loadWorld"+2);
            if (param1.get("flashHotParams") != null)
            {
                Global.flashHotParams = param1.get("flashHotParams");
            }
            String _loc_2 =null ;
            if (param1.get("overlaySurveyURL") != null)
            {
                _loc_2 = param1.get("overlaySurveyURL");
            }
            if (Global.world != null && Global.player != null)
            {
                LoadingManager.trackLoadTiming = true;
                LoadingManager.getInstance().addEventListener(LoaderEvent.ALL_TRACKED_LOADED, this.onTrackedLoaded);
                Global.pendingPresents = param1.pendingPresents;
                _loc_3 = param1.showBookmark;
                _loc_4 = -1;
                _loc_5 = param1.firstDay;
                _loc_6 = param1.preyGroups;
                if (param1.get("inactiveNeighbors") != null)
                {
                    Global.player.inactiveNeighbors = param1.get("inactiveNeighbors");
                }
                if (param1.get("eventTimeStamp") != null)
                {
                    Global.player.currentTimestampEventID = param1.get("eventTimeStamp").data.currentTimeStampID;
                    Global.player.previousTimestampEventID = param1.get("eventTimeStamp").data.previousTimeStampID;
                }

                Global.crews.loadObject(param1.crews);
                Global.factoryWorkerManager = new WorkerManager(WorkerManager.FEATURE_FACTORIES);
                Global.trainWorkerManager = new WorkerManager(WorkerManager.FEATURE_TRAINS);
                Global.ticketManager = new TicketManager();
                Global.itemCountManager = new ItemCountManager();
                if (param1.featureData)
                {
                    if (param1.featureData.factories)
                    {
                        Global.factoryWorkerManager.loadObject(param1.featureData.factories.workers);
                    }
                    if (param1.get("featureData").trains)
                    {
                        Global.trainWorkerManager.loadObject(param1.get("featureData").get("trains").get("workers"));
                    }
                    if (param1.get("featureData").socialInventory)
                    {
                        SocialInventoryManager.setData(param1.get("featureData").socialInventory);
                    }
                    if (param1.get("featureData").tickets)
                    {
                        Global.ticketManager.loadObject(param1.get("featureData").get("tickets"));
                    }
                    if (param1.get("featureData").saga)
                    {
                        SagaManager.instance.loadObject(param1.get("featureData").get("saga"));
                    }
                    if (param1.get("featureData").globalTable)
                    {
                        GlobalTableOverrideManager.instance.loadObject(param1.get("featureData").get("globalTable"));
                    }
                    if (param1.get("featureData").matchmaking)
                    {
                        MatchmakingManager.instance.loadObject(param1.get("featureData").get("matchmaking"));
                    }
                    RemodelManager.loadFeatureData(param1.get("featureData").get("remodel"));
                    GardenManager.instance.loadFeatureData(param1.get("featureData").get("gardens"));
                    Global.itemCountManager.loadFeatureData(param1.get("featureData").get("itemCounts"));
                    PickThingsManager.instance.loadFeatureData(param1.get("featureData"));
                    FeatureDataManager.instance.loadFeatureData(param1.get("featureData"));
                }
                Global.world.citySim.preyManager.workerManagers = new Array();
                if (param1.featureData)
                {
                    _loc_10 = Global.gameSettings().getXmlData("preyGroups");
                    for(int i0 = 0; i0 < _loc_10.size(); i0++)
                    {
                    		_loc_11 = _loc_10.get(i0);

                        _loc_12 = new WorkerManager(_loc_11);
                        _loc_12.loadObject(param1.featureData.get(_loc_11).get("workers"));
                        Global.world.citySim.preyManager.workerManagers.push(_loc_12);
                    }
                }
                if (param1.featureData && param1.featureData.rollCall)
                {
                    Global.rollCallManager.loadObject(param1.featureData.rollCall);
                }
                if (param1.featureData && param1.featureData.goal)
                {
                    Global.goalManager.loadObject(param1.featureData.goal);
                }
                if (param1.permissions)
                {
                    if (param1.showTOS)
                    {
                        TOSManager.init(param1.permissions);
                        TOSManager.instance.showTOSDialog();
                    }
                    GlobalEngine.socialNetwork.onPermissionDialogClosed(param1.permissions.get("publish_stream") == "1");
                    Global.world.viralMgr.checkExtendedPermissions(param1.permissions, false);
                }
                _loc_7 = param1.featureData;
                param1 = param1.userInfo;
                Global.player.cityName = param1.worldName;
                Global.player.pendingCityName = param1.pendingWorldName;
                if (Global.ui)
                {

                }
                if (param1.player)
                {
                    Global.player.loadObject(param1.player);
                    Global.player.newPlayer = param1.is_new;
                    Global.player.firstDay = param1.firstDay;
                    Global.player.creationTimestamp = param1.get("creationTimestamp");
                    _loc_13 = ReactivationManager.isActionAllowed(ReactivateAction.DISABLE_POPUPS);
                    Global.disableGamePopups = _loc_13;
                    Global.disableLoadPopups = _loc_13;
                }
                if (_loc_7 && _loc_7.xpromo)
                {
                    ZCrossPromoManager.setData(_loc_7.xpromo);
                }
                Global.gameSettings().setupSaleAndNewItems();
                if (param1.get("returnedFranchiseMoney") != null)
                {
                    _loc_14 = int(param1.get("returnedFranchiseMoney"));
                }
                if (_loc_6)
                {
                    Global.world.citySim.preyManager.parseServerData(_loc_6);
                }
                if (Global.isVisiting())
                {
                    UI.visitNeighbor(Global.getVisiting());
                }
                else
                {
                    param1.world.ownerId = GlobalEngine.socialNetwork.getLoggedInUserId();
                    Global.world.loadObject(param1.world);
                    Global.world.mostFrequentHelpers = param1.world.mostFrequentHelpers;
                    Global.world.vistorCenterText =(String) param1.world.visitorCenterMessage;
                    if (param1.player.UGCMap)
                    {
                        Global.world.friendUGCManager.loadUGCMap(param1.player.UGCMap);
                    }
                    GlobalEngine.initializationManager.addEventListener(Event.COMPLETE, this.onInitComplete, false, 0, true);
                    Global.hud.applyConfig("tutorial");
                }
                Global.rollCallManager.init();
                if (_loc_6)
                {
                    PreyUtil.refreshAllHubAppearances();
                    PreyManager.spawnExistingHunters();
                }
                Global.townName = Global.player.snUser.firstName;
                _loc_25 = ZLoc.t("Main","CityName",{nameZLoc.tn(Global.townName)});
                Global.townName = _loc_25;
                Global.hud.townName = _loc_25;
                if (param1.player.licenses)
                {
                    Global.player.loadLicenses(param1.player.licenses);
                }
                if (param1.player.sublicenses)
                {
                    Global.player.loadSubLicenses(param1.player.sublicenses);
                }
                _loc_8 = false;
                if (GlobalEngine.getFlashVar("frMsg") != null)
                {
                    _loc_15 = null;
                    if (GlobalEngine.getFlashVar("frIconItem") != null)
                    {
                        _loc_15 = Global.gameSettings().getItemByName((String)GlobalEngine.getFlashVar("frIconItem"));
                    }
                    if (_loc_15)
                    {
                        _loc_16 = String(GlobalEngine.getFlashVar("frParams"));
                        _loc_17 = _loc_16.split(":");
                        _loc_18 = ZLoc.t((String)GlobalEngine.getFlashVar("frMsg"), null, _loc_17);
                        Global.postInitActions.add(new DoDisplayDialog(new GenericIconPopup(_loc_18, GenericPopup.TYPE_ACCEPTCANCEL, null, _loc_15.icon)));
                    }
                    else
                    {
                        Global.postInitActions.add(new DoDisplayDialog(new FriendRewardDialog()));
                    }
                }
                else if (GlobalEngine.getFlashVar("initMsg") != null)
                {
                    _loc_19 = ZLoc.t((String)GlobalEngine.getFlashVar("initMsg"), null);
                    Global.postInitActions.add(new DoDisplayDialog(new GenericPopup(_loc_19)));
                }
                else if (Global.isVisiting() == false)
                {
                    _loc_20 = GlobalEngine.getTimer();
                    _loc_21 = Global.gameSettings().getInt("hideGiftNotifyTimeout") * 1000;
                    _loc_22 = param1.player && param1.player.options && param1.player.options.noGiftNotify ? (param1.player.options.noGiftNotify) : (0);
                    _loc_23 = _loc_20 - _loc_22;
                    if (param1.player && param1.player.gifts && param1.player.gifts.length > 0 && _loc_23 >= _loc_21)
                    {
                        _loc_8 = true;
                    }
                }
                if (_loc_8)
                {
                    Global.postInitActions.add(new DoDisplayDialog(new GiftNotify(param1.player.gifts.length())));
                }
                if (param1.bookmarkReward > 0)
                {
                    this.m_bookmarkCash = param1.bookmarkReward;
                    _loc_24 = ZLoc.t("Dialogs", "BookmarkReward", {cash:this.m_bookmarkCash});
                    Global.postInitActions.add(new DoDisplayDialog(new GenericPopup(_loc_24, GenericPopup.TYPE_OK, this.onBookmarkReward)));
                }
                else if (_loc_3 && param1.is_new == false)
                {
                }
                Global.xpromoManager = new CrossPromoManager(GlobalEngine.getFlashVars());
                Global.xpromoManager.handleStartup();
                Global.achievementsManager = new AchievementManager();
                Global.achievementsManager.loadServerData(param1.player.achievements, null, false);
                if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_POPULATION_TEASER) == ExperimentDefinitions.POPULATION_TEASER_SHOW)
                {
                    Global.populationMilestoneManager = new PopulationMilestoneManager();
                }
                if (param1.player.motdVersion < Global.gameSettings().getInt("motdVersion") && param1.is_new == false)
                {
                    Global.postInitActions.add(new DoDisplayDialog(new MOTD()));
                }
                _loc_9 = Global.player.getPlayerNews();
                if (_loc_9.length > 0)
                {
                    GameTransactionManager.addTransaction(new TSawPlayerNews());
                }
                Global.questManager = new GameQuestManager();
                Global.player.setAllowQuests(false);
                Global.player.setCompletedQuests(param1.CompletedQuests);
                Global.world.viralMgr.loadActiveVirals(param1.player.activeVirals);
                Global.world.orderMgr.initializeOrders(param1.player.Orders);
                Global.world.citySim.lotManager.initLots();
                Global.world.citySim.resortManager.initialize();

                if (_loc_2)
                {
                    SurveyManager.instance.initializeSurveyOverlay(_loc_2);
                }
                Global.hud.conditionallyRefreshHUD();
                Global.paymentsSaleManager.refreshSales();
                StartUpDialogManager.StartUpDialogTrigger(param1);
                LoadingManager.trackLoadTiming = false;
                LoadingManager.worldLoaded = true;
            }
            dispatchEvent(new Event(Event.COMPLETE));
            Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            RepaintManager.getInstance().forceRender();


            return;
        }//end

        private void  onTrackedLoaded (LoaderEvent event )
        {
            Vector _loc_2.<uint >=null ;
            LoadingManager.getInstance().removeEventListener(LoaderEvent.ALL_TRACKED_LOADED, this.onTrackedLoaded);
            StartupSessionTracker.perf(StatsKingdomType.WORLDLOADED);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_LOG_RETRY_COUNTS) == 1)
            {
                _loc_2 = event.eventData.retryCounts;
                StatsManager.count("assets", "retry_count", _loc_2.get(0).toString(), _loc_2.get(1).toString(), _loc_2.get(2).toString());
            }
            return;
        }//end

        protected void  parseNeighbors (Array param1 )
        {
            UI.populateFriendBarData(param1);
            if (Global.ui)
            {
                Global.ui.m_friendBar.populateNeighbors(Global.friendbar);
                Global.ui.setFriendBarPos(Math.max(Global.friendbar.length - 15, 0));
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        protected void  onBookmarkReward (GenericPopupEvent event )
        {

            return;
        }//end

        protected void  onInitComplete (Event event )
        {
            MapResource _loc_2 =null ;
            GlobalEngine.initializationManager.removeEventListener(Event.COMPLETE, this.onInitComplete);
            for(int i0 = 0; i0 < Global.world.getObjects().size(); i0++)
            {
            		_loc_2 = Global.world.getObjects().get(i0);

                _loc_2.refreshArrow();
            }
            return;
        }//end

    }



