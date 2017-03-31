package root;

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

import Cache.Init.*;
import Cache.Managers.*;
import Classes.*;
import Classes.Managers.*;
import Classes.MiniQuest.*;
import Classes.actions.*;
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Classes.zbar.*;
import Display.*;
import Display.DialogUI.*;
import Display.FriendRewardsUI.*;
import Display.MatchmakingUI.*;
import Display.SagaUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Init.*;
import Engine.Managers.*;
import Engine.Transactions.*;
import GameMode.*;
import Init.*;
import Init.PostInit.PostInitActions.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.GlobalTable.*;
import Modules.bandits.*;
import Modules.dataservices.*;
import Modules.guide.*;
import Modules.matchmaking.*;
import Modules.quest.Display.*;
import Modules.quest.Managers.*;
import Modules.realtime.*;
import Modules.saga.*;
import Modules.stats.experiments.*;
import Modules.stats.trackers.*;
import Modules.stats.types.*;
import Modules.zoo.*;
import Modules.zoo.ui.*;
import Transactions.*;

import blit.*;
import com.greensock.plugins.*;
import com.xiyu.util.Event;
import com.xiyu.util.ExternalInterface;
import com.zynga.skelly.render.*;
//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.system.*;
//import flash.ui.*;
//import flash.utils.*;
import org.aswing.*;
import plugin.*;
import tool.*;
import com.xinghai.Debug;
import Engine.Transactions.*;

import java.security.Security;
import java.util.Vector;

    public class Game extends BaseGame
    {
        private  int FRAME_RATE =30;
        protected boolean m_initializing ;
        protected double m_feedOpenTime ;
        protected GenericDialog m_warningDialog ;
        private int debug_defcon =0;
        private boolean m_zmcFreeze =false ;
        private int m_zmcFreezeCount =0;
        private boolean m_zmcOpen =false ;
        private Dictionary m_pendingTransactionData ;
        private Vector<TransactionTrackingData> m_completedTransactionData;
        private Vector<BatchTrackingData> m_pendingBatchData;
        private Vector<BatchTrackingData> m_completedBatchData;
        private boolean m_preloaderDone =false ;
        private boolean m_initComplete =false ;
        protected CityRunSpace m_runspace ;
        private int m_currentOverlayTintConfig =0;
        public static  boolean DEBUG_PRODUCTION_OVERRIDE =false ;
        public static boolean m_blitting =false ;
        private static  Array ALLOWED_DOMAINS;

        public Game (Object param1)
        {
            //ExternalInterface.call("zaspActivity","Game"+1);
            String values[] = {"zcache.zgncdn.com","zynga1-a.akamaihd.net","zynga2-a.akamaihd.net","zynga3-a.akamaihd.net","zynga4-a.akamaihd.net","zyngacv.hs.llnwd.net","cityvillefb0.static.zgncdn.com","cityvillefb1.static.zgncdn.com","cityvillefb2.static.zgncdn.com","cityvillefb3.static.zgncdn.com","cityvillefb.static.zgncdn.com"};
            ALLOWED_DOMAINS = new Array(values);
            String _loc_2 =null ;
            ZEngineOptions _loc_3 =null ;
            this.m_pendingTransactionData = new Dictionary();
            this.m_completedTransactionData = new Vector<TransactionTrackingData>();
            this.m_pendingBatchData = new Vector<BatchTrackingData>();
            this.m_completedBatchData = new Vector<BatchTrackingData>();
            for(int i0 = 0; i0 < ALLOWED_DOMAINS.size(); i0++)
            {
            		_loc_2 = ALLOWED_DOMAINS.get(i0);

                Security.allowDomain(_loc_2);
            }
            ZProfiler.stopProfile();
            ZProfiler.discardProfile();
            LoadingManager.maxLoadsPerDomain = 16;
            LoadingManager.maxLoads = LoadingManager.maxLoadsPerDomain * 16;
            _loc_3 = new ZEngineOptions();
            if (m_blitting)
            {
                _loc_3.viewportClass = BlitViewport.class;
            }
            else
            {
                _loc_3.viewportClass = GameViewport.class;
            }
            _loc_3.zaspManagerClass = GameZaspManager.class;
            _loc_3.loadingManagerClass = ZCacheLoadingManager.class;
            _loc_3.addComponent(new ZCacheComponent("city-2"));
            _loc_3.tileWidth = 10;
            _loc_3.tileHeight = 5;
            QuestComponentOptions _loc_4 =new QuestComponentOptions ();
            _loc_4.questClass = GameQuest.class;
            _loc_4.questUtility = GameQuestUtility.class;
            _loc_3.addComponent(new QuestComponent(_loc_4));
            ExternalInterface.call("zaspActivity","Game"+2);
            super(param1, _loc_3);
            return;
        }//end

         protected void  init ()
        {
            if (Config.DEBUG_MODE)
            {
                Config.verboseLogging = true;
                GlobalEngine.addTraceLevel("StateMachine", GlobalEngine.LEVEL_WARNING);
                GlobalEngine.addTraceLevel("QuestManager", GlobalEngine.LEVEL_ALL);
                GlobalEngine.addTraceLevel("Guide", GlobalEngine.LEVEL_ALL);
                GlobalEngine.addTraceLevel("Doobers", GlobalEngine.LEVEL_ALL);
                GlobalEngine.addTraceLevel("AMFConnection", GlobalEngine.LEVEL_ALL);
            }
            //ExternalInterface.call("zaspActivity","Game"+"init"+1);

            StartupSessionTracker.perf(StatsKingdomType.LOADAPP);
            GlobalEngine.addTraceLevel("Init", GlobalEngine.LEVEL_ALL);
            Global.stage = this.stage;
            stage.align = StageAlign.TOP;
            stage.scaleMode = StageScaleMode.NO_SCALE;
            TweenPlugin.activate(.get(GlowFilterPlugin));
            if (m_blitting)
            {
                Global.stage.quality = StageQuality.LOW;
            }
            ExternalInterface.call("zaspActivity","Game"+"init"+2);
            RenderManager.init((IRenderer)GlobalEngine.viewport, stage, this.FRAME_RATE, false);
            Global.stage.stageFocusRect = false;
            String _loc_1 =Config.BASE_PATH +"bootstrap.xml";
            String _loc_2 =Config.BASE_PATH +"gameSettings.xml";
            String _loc_3 =GlobalEngine.getFlashVar("locale")? (GlobalEngine.getFlashVar("locale").toString()) : ("en_US");
            String _loc_4 =Config.BASE_PATH +_loc_3 +".xml";
            String _loc_5 =Config.BASE_PATH +"questSettings.xml";
            String _loc_6 =Config.BASE_PATH +"effectsConfig.xml";
            if (parameters.game_config_url)
            {
                _loc_2 = parameters.game_config_url;
            }
            if (parameters.bootstrap_config_url)
            {
                _loc_1 = parameters.bootstrap_config_url;
            }
            if (parameters.localization_url)
            {
                _loc_4 = parameters.localization_url;
            }
            if (parameters.quest_config_url)
            {
                _loc_5 = parameters.quest_config_url;
            }
            if (parameters.effects_config_url)
            {
                _loc_6 = parameters.effects_config_url;
            }
            if (parameters.visitId)
            {
                Global.setVisiting(parameters.visitId);
            }
            if (parameters.useHttps)
            {
                Config.SERVICES_GATEWAY_PATH = Config.SERVICES_GATEWAY_PATH + "?useHttps=1";
            }
            ExternalInterface.call("zaspActivity","Game"+"init"+3);
            GlobalEngine.log("Init", "Game Config URL: " + _loc_2);
            LoadingManager.useAssetPacks = GlobalEngine.getFlashVar("useAssetPacks") == "true";
            this.m_initializing = true;
            TransactionManager.getInstance().addEventListener(TransactionFaultEvent.FAULT, this.onInitTransactionFault);
            boolean _loc_7 =parameters.skipFacebook ==true ;
            InitializationManager _loc_8 =GlobalEngine.initializationManager ;
            ExternalInterface.call("zaspActivity","Game"+"init"+4);
            GlobalEngine.initializationManager.add(new ExperimentsInit());
            if (Config.DEBUG_MODE)
            {
                _loc_8.add(new BootstrapInit(_loc_1));
            }
            _loc_8.add(new RuntimeVariableInit());
            _loc_8.add(new LoadingInit());
            _loc_8.add(new PreloadAssetsInit((String)GlobalEngine.getFlashVar("preloaded_asset_urls")));
            GameSettingsDownloadInit _loc_9 =new GameSettingsDownloadInit(_loc_2 ,GlobalEngine.getFlashVar("GameSettings")as ByteArray );
            _loc_8.add(_loc_9);
            _loc_8.add(new GameSettingsInit(_loc_9));
            _loc_8.add(new LocalizationInit(_loc_4, GlobalEngine.getFlashVar("LocaleSettings") as ByteArray));
            GlobalEngine.setFlashVar("GameSettings", null);
            GlobalEngine.setFlashVar("LocaleSettings", null);
            ExternalInterface.call("zaspActivity","Game"+"init"+5);
            if (GlobalEngine.getFlashVar("snapiEnable") != null && parseInt(GlobalEngine.getFlashVar("snapiEnable").toString()) == 1)
            {
                _loc_8.add(new SNAPIInit(_loc_7));
            }
            else
            {
                _loc_8.add(new GameFacebookInit(_loc_7));
            }
            _loc_8.add(new TransactionsInit());
            _loc_8.add(new GlobalsInit());

            Global.uiinit = new UIInit()
            _loc_8.add(Global.uiinit);


            _loc_8.add(new AssetLoadingInit());
            _loc_8.add(new FontMapperInit());
            _loc_8.add(new GuideInit());
            _loc_8.add(new EffectsInit(_loc_6));
            _loc_8.add(new StatsInit(_loc_8));
            if (Config.DEBUG_MODE)
            {
                _loc_8.add(new ConsoleInit());
            }
            _loc_8.addEventListener(ProgressEvent.PROGRESS, this.onInitProgress);
            _loc_8.addEventListener(Event.COMPLETE, this.onAllInitComplete);
            ExternalInterface.call("zaspActivity","Game"+"init"+6);
            _loc_8.execute();
            ExternalInterface.call("zaspActivity","Game"+"init"+7);
            if (Config.DEBUG_MODE)
            {
                ErrorManager.getInstance().addEventListener(ErrorEvent.ERROR, this.onError);
            }
            BracketFinder.test_getBracketedSubstrings();
            BracketFinder.test_substituteBracketedSubstrings();

            ExternalInterface.call("zaspActivity","Game"+"init"+9);

            Global.game = this;

            init2();
            return;
        }//end

	public void  init2 ()
	{
        /*
		Global.circle = new Sprite();
		Global.circle1 = new Sprite();
		Global.circle2 = new Sprite();

        try {
			Global.circle1.x = 100;

			Global.circle2.x = 300;
			Global.circle2.y = 300;
			//this.stage.addChild(Global.circle);
			this.parent.addChild(Global.circle);
			this.parent.addChild(Global.circle1);
			this.parent.addChild(Global.circle2);

		} catch(error:Error) {
		      Debug.debug4("ResourceInit. "+error);
		}
*/
       }

        private void  onInitProgress (ProgressEvent event )
        {
	    if(Global.circle2 != null) {
		    Global.circle2.graphics.beginFill(0x00794B);
		    Global.circle2.graphics.drawRect(0,0,100,20);
		    Global.circle2.graphics.endFill();

		    Global.circle2.graphics.beginFill(0xAA0099);
		    Global.circle2.graphics.drawRect(0,0,100*(event.bytesLoaded/event.bytesTotal), 20);
		    Global.circle2.graphics.endFill();
		    if(event.bytesLoaded == event.bytesTotal) {
			    this.parent.removeChild(Global.circle2);
		    }
            }

            dispatchEvent(event);
            return;
        }//end

        private void  displayUI ()
        {
            GlobalEngine.stage.align = StageAlign.TOP_LEFT;
            addChild(GlobalEngine.viewport);
            Global.world.centerCityView(0);
            addChild(Global.ui);
            UI.pumpPopupQueue();
            return;
        }//end

        public void  preloaderDone (boolean param1 ,boolean param2 ,boolean param3 )
        {
            this.m_preloaderDone = true;
            if (this.m_initComplete)
            {
                this.displayUI();
            }
            if (param1 || param3)
            {
                StatsManager.count("Zcache_Stats", param1.toString(), param2.toString(), param3.toString());
            }
            return;
        }//end

        private void  onAllInitComplete (Event event )
        {
            FrameRateGraph fps ;
            Event ev =event ;
            this.m_initializing = false;
            this.m_initComplete = true;
            Array flash_info =GameUtil.logMachineInfo ();
            StatsManager.count("Flash_Info", flash_info.get(0), flash_info.get(1), flash_info.get(2), flash_info.get(3), flash_info.get(4));
            TransactionManager.getInstance().removeEventListener(TransactionFaultEvent.FAULT, this.onInitTransactionFault);
            ZyParamsUpdateTracker.initialize();
            if (!m_blitting)
            {
                GlobalEngine.stage.addEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            }
            GlobalEngine.stage.addEventListener(Event.RESIZE, this.onResize);
            GlobalEngine.stage.addEventListener(Event.MOUSE_LEAVE, this.onMouseLeaveFrame);
            GlobalEngine.stage.addEventListener(FullScreenEvent.FULL_SCREEN, onFullScreenChanged);
            GlobalEngine.viewport.setZoom(Global.gameSettings().getNumber("defaultZoom"));
            GlobalEngine.viewport.width = Global.ui.screenWidth;
            GlobalEngine.viewport.height = Global.ui.screenHeight;
            GlobalEngine.viewport.scaleX = 1 / Global.ui.screenScale;
            GlobalEngine.viewport.scaleY = 1 / Global.ui.screenScale;
            int _loc_3 =Global.ui.screenScale ;
            this.scaleY = _loc_3;
            this.scaleX = _loc_3;
            if (this.m_preloaderDone || this.parent == Global.stage)
            {
                this.displayUI();
            }
            if (m_blitting)
            {
                fps = new FrameRateGraph();
                fps.y = 190;
                fps.x = 20;
                addChild(fps);
                fps.activate();
            }
            int dailyBonusVariant =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DAILY_BONUS_AUTOPOP );
            if (dailyBonusVariant == ExperimentDefinitions.DAILY_BONUS_AUTOPOP_ON)
            {
                if (Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_VISITS_UNLOCKED) && Global.player.dailyBonusManager.isBonusAvailable && !Global.player.isNewPlayer)
                {
                    DailyBonusMQ.doDailyBonus();
                }
            }

            Sounds.unpauseMusic();

            Global.world.resetGenericAdTime();
            GlobalEngine.stage.addEventListener(MouseEvent.MOUSE_MOVE, this.onStageMouseMove, false, 3);
            GlobalEngine.stage.addEventListener(MouseEvent.CLICK, this.onStageMouseClick, false, 3);
            GlobalEngine.stage.addEventListener(KeyboardEvent.KEY_DOWN, this.onStageKeyDown, false, 3);
            dispatchEvent(new Event(Event.COMPLETE));
            TransactionManager.getInstance().addEventListener(TransactionFaultEvent.FAULT, this.onTransactionFault);
            TransactionManager.getInstance().addEventListener(TransactionEvent.QUEUE_LIMIT_EXCEEDED, this.onTransactionQueueLimitExceeded);
            TransactionManager.getInstance().addEventListener(TransactionEvent.QUEUE_LIMIT_NORMAL, this.onTransactionQueueLimitNormal);
            TransactionManager.getInstance().addEventListener(TransactionEvent.VERSION_MISMATCH, this.onTransactionVersionMismatch);
            TransactionManager.getInstance().addEventListener(TransactionEvent.RETRY_SUCCESS, this.onTransactionRetrySuccess);
            TransactionManager.getInstance().addEventListener(TransactionFaultEvent.RETRY, this.onTransactionRetry);
            TransactionManager.getInstance().addEventListener(TransactionEvent.ADDED, this.onTransactionAdded);
            TransactionManager.getInstance().addEventListener(TransactionEvent.DISPATCHED, this.onTransactionDispatched);
            TransactionManager.getInstance().addEventListener(TransactionEvent.COMPLETED, this.onTransactionCompleted);
            TransactionManager.getInstance().addEventListener(TransactionBatchEvent.BATCH_DISPATCHED, this.onTransactionBatchDispatched);
            TransactionManager.getInstance().addEventListener(TransactionBatchEvent.BATCH_COMPLETE, this.onTransactionBatchCompleted);
            if (parameters.missionType && parameters.missionHostId)
            {
                GameTransactionManager.addTransaction(new TGetVisitMission(parameters.missionHostId, parameters.missionType), true);
            }
            try
            {
                /*
                if (ExternalInterface.available)
                {
                    ExternalInterface.addCallback("enableAllInput", this.enableAllInput);
                    ExternalInterface.addCallback("disableAllInput", this.disableAllInput);
                    ExternalInterface.addCallback("logShowFeedDialog", this.logShowFeedDialog);
                    ExternalInterface.addCallback("logCloseFeedDialog", this.logCloseFeedDialog);
                    ExternalInterface.addCallback("permissionDialogClosed", this.permissionDialogClosed);
                    ExternalInterface.addCallback("extendedPermissionsDialogClosed", this.extendedPermissionsDialogClosed);
                    ExternalInterface.addCallback("onFocus", this.onFocus);
                    ExternalInterface.addCallback("frameLoadComplete", FrameManager.frameLoadComplete);
                    ExternalInterface.addCallback("onDynamicTrayClosed", this.onDynamicTrayClosed);
                    ExternalInterface.addCallback("freezeGame", this.freezeGame);
                    ExternalInterface.addCallback("ZMCFreezeGame", this.ZMCFreezeGame);
                    ExternalInterface.addCallback("thawGame", this.thawGame);
                    ExternalInterface.addCallback("onZMCEvent", this.onZMCEvent);
                    ExternalInterface.addCallback("onZMCOpen", this.onZMCOpen);
                    ExternalInterface.addCallback("onZMCClose", this.onZMCClose);
                    ExternalInterface.addCallback("setZBarQueue", this.setZBarQueue);
                    ExternalInterface.addCallback("onZMCLoad", this.onZMCLoad);
                    ExternalInterface.addCallback("onRTStatusChange", RealtimeManager.onRTStatusChange);
                    ExternalInterface.addCallback("onRTChat", RealtimeManager.onRTChat);
                    ExternalInterface.addCallback("getScreenshot", Classes.Managers.FreezeManager.exportScreenshot);
                    ExternalInterface.addCallback("onRequestSent", this.onRequestSent);
                }
                else
                {
                    StatsManager.count("errors", "external_interface_not_supported");
                }
                */
            }
            catch (error:Error)
            {
                StatsManager.count("errors", "external_interface_threw_error");
            }
            if (Global.player.isNewPlayer)
            {
                Global.guide.loadLaterStepAssets();
            }
            Global.delayedAssets.addEventListener(Event.COMPLETE, this.onDelayedAssetsLoadComplete);
            Global.delayedAssets.startPostloading();
            String creativesUrl =Config.BASE_PATH +"creatives.xml";
            if (parameters.creatives_url)
            {
                creativesUrl = parameters.creatives_url;
            }
            Global.creatives = new Creatives(creativesUrl);
            if (!(Global.player.options.get("sfxDisabled") == true && Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_SFX_LOADING)))
            {
                Sounds.startPostloading();
            }
            Global.world.citySim.waterManager.init();
            FrameManager.doInitPreloads();
            //Global.achievementsManager.startFullUpdate();
            if (Global.ui)
            {
                UI.populateFriendBarData([]);
                Global.ui.m_friendBar.preload = true;
                Global.ui.m_friendBar.populateNeighbors(Global.friendbar);
                Global.ui.setFriendBarPos(0);
                Global.ui.m_friendBar.preload = false;
            }
            GameTransactionManager.addTransaction(new TInitNeighbors());
            GameTransactionManager.addTransaction(new TCheckForNewNeighbors());
            GameTransactionManager.addTransaction(new TGetMFSData());
            StartupSessionTracker.perf(StatsKingdomType.INITCOMPLETE);
            int zmc_popup_early_variant =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_POPUP_ZMC_EARLY );
            if (zmc_popup_early_variant == ExperimentDefinitions.POPUP_ZMC_AT_EARLY_STAGE)
            {
                StartupSessionTracker.interactive();
            }
            Global.world.startPerformanceTracking();
            variant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INCREASE_NUM_OF_QUEUED_TRANSACTIONS);
            if (variant)
            {
                StatsManager.count("transaction_max_queued_increased");
                TransactionManager.maxQueued = TransactionManager.DEFAULT_MAX_QUEUED * 2;
            }
            RealtimeManager.m_realtimeObserver = new RealtimeObserver();
            useTimerVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TRIGGER_REPAINT);
            if (useTimerVariant == ExperimentDefinitions.USE_EVENT_TO_TRIGGER_REPAINT)
            {
                RepaintManager.getInstance().setAlwaysUseTimer(false);
            }
            else
            {
                RepaintManager.getInstance().setAlwaysUseTimer(true, this.FRAME_RATE);
            }
            AsWingManager.setPreventNullFocus(false);
            return;
        }//end

        private void  onDelayedAssetsLoadComplete (Event event )
        {
            Global.delayedAssets.removeEventListener(Event.COMPLETE, this.onDelayedAssetsLoadComplete);
            ZPreloaderManager.init();
            return;
        }//end

        private void  onInitTransactionFault (TransactionFaultEvent event )
        {
            String _loc_2 =event.errorType.toString ();
            String _loc_3 =event.errorData ? (event.errorData.toString()) : ("");
            String _loc_4 ="Error #"+_loc_2 +": "+_loc_3 ;
            dispatchEvent(new ErrorEvent(ErrorEvent.ERROR, false, false, _loc_4));
            this.logErrorStats(event, true);
            return;
        }//end

        private void  onTransactionFault (TransactionFaultEvent event )
        {
            if (Config.DEBUG_MODE)
            {
                UI.displayMessage("errorData: " + event.errorData, GenericPopup.TYPE_OK, GameUtil.redirectHome, "", true);
                this.logErrorStats(event);
            }
            else if (event.errorType == Transaction.OUTDATED_GAME_VERSION)
            {
                this.onTransactionVersionMismatch();
            }
            else
            {
                this.onTransactionGeneralError(event);
            }
            return;
        }//end

        private void  onTransactionRetry (TransactionFaultEvent event )
        {
            _loc_2 =Global.gameSettings().getInt("gameConnectionMaxTimeout",30)*1000;
            ConnectionStatus.startTimeout(ConnectionStatus.TIMEOUT_CONNECTION_LOST, _loc_2, true, this.onConnectionTimeout);
            return;
        }//end

        private void  onTransactionAdded (TransactionEvent event )
        {
            this.m_pendingTransactionData.put(event.transaction.getId(),  new TransactionTrackingData(event.transaction));
            return;
        }//end

        private void  onTransactionDispatched (TransactionEvent event )
        {
            _loc_2 = this.m_pendingTransactionData.get(event.transaction.getId ()) ;
            if (_loc_2 != null)
            {
                _loc_2.dispatchTime = GlobalEngine.currentTime;
            }
            return;
        }//end

        private void  onTransactionCompleted (TransactionEvent event )
        {
            _loc_2 = this.m_pendingTransactionData.get(event.transaction.getId ()) ;
            if (_loc_2 != null)
            {
                _loc_2.responseTime = GlobalEngine.currentTime;
                _loc_2.serverDuration = this.fixupSeverTime(_loc_2.transaction.rawResult.get("transactionTimeElapsed"));
                while (this.m_completedTransactionData.length >= TransactionTrackingData.MAX_TRACKED)
                {

                    this.m_completedTransactionData.shift();
                }
                this.m_completedTransactionData.push(_loc_2);
                delete this.m_pendingTransactionData.get(event.transaction.getId());
            }
            return;
        }//end

        private void  onTransactionBatchDispatched (TransactionBatchEvent event )
        {
            BatchTrackingData _loc_2 =new BatchTrackingData ();
            _loc_2.dispatchTime = GlobalEngine.currentTime;
            _loc_2.numTransactions = event.dispatchedBatchData.length;
            this.m_pendingBatchData.unshift(_loc_2);
            return;
        }//end

        private void  onTransactionBatchCompleted (TransactionBatchEvent event )
        {
            BatchTrackingData _loc_2 =null ;
            Array _loc_3 =null ;
            Object _loc_4 =null ;
            if (this.m_pendingBatchData.length > 0)
            {
                _loc_2 = this.m_pendingBatchData.shift();
                _loc_2.responseTime = GlobalEngine.currentTime;
                _loc_2.batchServerTimeElapsed = 0;
                if (event.responseBatchData && event.responseBatchData.hasOwnProperty("data") && event.responseBatchData.get("data") is Array)
                {
                    _loc_3 = event.responseBatchData.get("data");
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    		_loc_4 = _loc_3.get(i0);

                        if (_loc_4.hasOwnProperty("transactionTimeElapsed"))
                        {
                            _loc_2.batchServerTimeElapsed = _loc_2.batchServerTimeElapsed + this.fixupSeverTime(_loc_4.get("transactionTimeElapsed"));
                        }
                    }
                }
                while (this.m_completedBatchData.length >= BatchTrackingData.MAX_TRACKED)
                {

                    this.m_completedBatchData.shift();
                }
                this.m_completedBatchData.push(_loc_2);
            }
            return;
        }//end

        private double  fixupSeverTime (double param1 )
        {
            return param1 * 1000 * 1000;
        }//end

        private void  logTransactionData (String param1 )
        {
            TransactionTrackingData _loc_5 =null ;
            TransactionTrackingData _loc_6 =null ;
            BatchTrackingData _loc_7 =null ;
            BatchTrackingData _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_13 =null ;
            String _loc_2 ="transaction_"+param1 ;
            String _loc_3 ="batch_"+param1 ;
            _loc_4 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TRACK_TRANSACTION_MAX_QUEUE )==ExperimentDefinitions.TRACK_TRANSACTION_MAX_QUEUE_ENABLED ;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TRACK_TRANSACTION_MAX_QUEUE) == ExperimentDefinitions.TRACK_TRANSACTION_MAX_QUEUE_ENABLED)
            {
                for(int i0 = 0; i0 < this.m_pendingTransactionData.size(); i0++)
                {
                		_loc_5 = this.m_pendingTransactionData.get(i0);

                    _loc_9 = getQualifiedClassName(_loc_5.transaction);
                    _loc_10 = _loc_5.addTime.toString();
                    _loc_11 = _loc_5.isDispatched ? (_loc_5.dispatchTime.toString()) : ("--");
                    _loc_12 = "--";
                    _loc_13 = "--";
                    StatsManager.count(_loc_2, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13);
                }
                for(int i0 = 0; i0 < this.m_completedTransactionData.size(); i0++)
                {
                		_loc_6 = this.m_completedTransactionData.get(i0);

                    _loc_9 = getQualifiedClassName(_loc_6.transaction);
                    _loc_10 = _loc_6.addTime.toString();
                    _loc_11 = _loc_6.dispatchTime.toString();
                    _loc_12 = _loc_6.responseTime.toString();
                    _loc_13 = _loc_6.serverDuration.toString();
                    StatsManager.count(_loc_2, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13);
                }
                for(int i0 = 0; i0 < this.m_pendingBatchData.size(); i0++)
                {
                		_loc_7 = this.m_pendingBatchData.get(i0);

                    StatsManager.count(_loc_3, _loc_7.numTransactions.toString(), _loc_7.dispatchTime.toString(), "--", "--");
                }
                for(int i0 = 0; i0 < this.m_completedBatchData.size(); i0++)
                {
                		_loc_8 = this.m_completedBatchData.get(i0);

                    StatsManager.count(_loc_3, _loc_8.numTransactions.toString(), _loc_8.dispatchTime.toString(), _loc_8.responseTime.toString(), _loc_8.batchServerTimeElapsed.toString());
                }
            }
            this.m_completedTransactionData = new Vector<TransactionTrackingData>();
            this.m_completedBatchData = new Vector<BatchTrackingData>();
            return;
        }//end

        private void  onTransactionRetrySuccess (TransactionEvent event )
        {
            ConnectionStatus.stopTimeout();
            return;
        }//end

        private void  onTransactionVersionMismatch (TransactionEvent event =null )
        {
            UI.displayMessage(ZLoc.t("Main", "OutdatedGameVersion"), GenericPopup.TYPE_OK, GameUtil.redirectHomeVersionMismatch, "", true);
            return;
        }//end

        private void  onTransactionGeneralError (TransactionFaultEvent event )
        {
            UI.displayMessage(ZLoc.t("Main", "RefreshGame", {error:event.errorType, errorMsg:event.errorData}), GenericPopup.TYPE_OK, GameUtil.redirectHome, "", true);
            this.logErrorStats(event);
            return;
        }//end

        private void  onTransactionQueueLimitExceeded (TransactionEvent event )
        {
            _loc_2 =Global.gameSettings().getInt("gameSaveMaxTimeout",25)*1000;
            ConnectionStatus.startTimeout(ConnectionStatus.TIMEOUT_SAVE_GAME, _loc_2, true, this.onConnectionTimeout);
            return;
        }//end

        private void  onTransactionQueueLimitNormal (TransactionEvent event )
        {
            this.logTransactionData("queue_recover");
            ConnectionStatus.stopTimeout();
            return;
        }//end

        private void  onConnectionTimeout (String param1 )
        {
            String _loc_2 ="unknown";
            switch(param1)
            {
                case ConnectionStatus.TIMEOUT_SAVE_GAME:
                {
                    _loc_2 = "queue_backup";
                    break;
                }
                case ConnectionStatus.TIMEOUT_CONNECTION_LOST:
                {
                    _loc_2 = "retry_fail";
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.logTransactionData(_loc_2);
            return;
        }//end

        private void  logErrorStats (TransactionFaultEvent event ,boolean param2 =false )
        {
            _loc_3 = TransactionManager.lastFunc;
            String _loc_4 ="errorCode: "+event.errorType.toString ();
            _loc_5 = event.errorData? (event.errorData.toString()) : ("");
            String _loc_6 ="test_app_mode: "+Config.DEBUG_MODE.toString ();
            _loc_7 = event.transaction? (event.transaction.getId()) : (null);
            StatsManager.count(StatsCounterType.ERRORS, param2 ? (StatsKingdomType.TRANSACTIONS_USERINIT) : (StatsKingdomType.TRANSACTIONS), _loc_3, _loc_4, _loc_6, _loc_5);
            return;
        }//end

         protected void  onStageMouseMove (MouseEvent event )
        {
            StatsManager.notIdle();
            Global.appActive = true;
            UI.onStageMouseMove(event);
            return;
        }//end

         protected void  onStageMouseClick (MouseEvent event )
        {
            if (Global.world != null)
            {
                return;
            }
            Global.lastInputTick = getTimer();
            Global.appActive = true;
            Global.world.resetIdleMissionTime();
            Global.world.resetGiftMissionTime();
            Global.world.resetGenericAdTime();
            if (this.m_zmcFreeze && this.m_zmcFreezeCount < 1)
            {
                this.thawGame();
            }
            return;
        }//end

        protected boolean  predicateBusinessIsOpen (Object param1 )
        {
            return param1 instanceof Business && (param1 as Business).isOpen() && !(param1 as Business).isNeedingRoad;
        }//end

         protected void  onStageKeyDown (KeyboardEvent event )
        {
            Array targets ;
            MapResource target ;
            MunicipalCenter cityCenter ;
            MunicipalCenter cityCenter2 ;
            RecentlyPlayedMFSManager mngr2 ;
            GenericDialog mystupiddialog ;
            Array bizes ;
            String quest_name ;
            Quest quest ;
            Vector<Object> tasks;
            int len ;
            int inx ;
            QuestPopup questPopup ;
            QuestPopupView questView ;
            String obj ;
            Object data ;
            SagaRewardDialog sagaRewardDialog ;
            String url ;
            ZooDialog zoo ;
            StartupDialogBase d ;
            Array businesses ;
            Array zoosk ;
            TieredDooberMechanic mechmech ;
            Array zoose ;
            Array zoosg ;
            Business biz ;
            Business business ;
            ZooEnclosure enclosure ;
            IStorageMechanic dmechanic ;
            boolean gr ;
            IDictionaryDataMechanic mech ;
            PluginLoader loader ;
            PluginLoader mLoader ;
            e = event;
            Global.lastInputTick = getTimer();
            Global.appActive = true;
            GlobalEngine.log("Keyboard", "Key down! keyCode: " + e.keyCode + " ctrlKey: " + e.ctrlKey + " altKey: " + e.altKey + " shiftKey: " + e.shiftKey);
            fpsOverride = DEBUG_PRODUCTION_OVERRIDE&& e.ctrlKey && e.altKey && e.shiftKey && String.fromCharCode(e.charCode) == "p";
            isTestEnvironment = RuntimeVariableManager.getBoolean("IS_TEST_ENVIRONMENT",false);
            if (Config.DEBUG_MODE || fpsOverride || isTestEnvironment)
            {
                if (e.keyCode == Keyboard.F2)
                {
                    ZProfiler.startProfile();
                }
                if (e.keyCode == Keyboard.F4)
                {
                    ZProfiler.stopProfile();
                }
            }
            if (!e.ctrlKey)
            {
                return;
            }
            char char0 = String.fromCharCode(e.charCode);
            boolean handled ;
            if (isTestEnvironment)
            {
                switch(char0)
                {
                    case 'l':
                    {
                        IdleDialogManager.toggleHotKeyForIdlePopUp();
                        break;
                    }
                    case '5':
                    {
                        GameTransactionManager.addTransaction(new TRefreshUser(GlobalEngine.socialNetwork.getLoggedInUser().name, false), true, true);
                        break;
                    }
                    default:
                    {
                        handled = null;
                        break;
                    }
                }
            }
            if (Config.DEBUG_MODE || fpsOverride)
            {
                GlobalEngine.log("Keyboard", "Key down! char: " + String.fromCharCode(e.charCode) + " keyCode: " + e.keyCode + " ctrlKey: " + e.ctrlKey + " altKey: " + e.altKey + " shiftKey: " + e.shiftKey);
                Global.player.options.put("sfxDisabled",  true);
                Global.player.options.put("musicDisabled",  true);
                switch(char0)
                {
                    case '0':
                    {
                        GameTransactionManager.addTransaction(new TBuyItem("car_darkgreenmini", 1));
                        break;
                    }
                    case '1':
                    {
                        cityCenter = Global.world.getObjectsByClass(MunicipalCenter).get(0);
                        if (cityCenter)
                        {
                            cityCenter.setDataForMechanic("mechanicPackName", "neighborhoodMechanics", "TEST GAME EVENT");
                        }
                        break;
                    }
                    case '2':
                    {
                        cityCenter2 = Global.world.getObjectsByClass(MunicipalCenter).get(0);
                        if (cityCenter2)
                        {
                            cityCenter2.setDataForMechanic("mechanicPackName", "wonderMechanics", "TEST GAME EVENT");
                        }
                        break;
                    }
                    case '3':
                    {
                        mngr2 =(RecentlyPlayedMFSManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS);
                        if (mngr2)
                        {
                            Global.dataServicesManager.query(DataServicesQueryType.GET_PLAYED_IN_LAST_N_DAYS, [RecentlyPlayedMFSManager.NUM_DAYS], mngr2.onQueryComplete);
                        }
                        break;
                    }
                    case '0':
                    {
                        mystupiddialog = new BuildingRequestDialog(.get({imageUrl:Global.gameSettings().getItemByName("mun_townhall").icon}, {}, {}, {}, {}));
                        UI.displayPopup(mystupiddialog);
                        break;
                    }
                    case '`':
                    {
                        bizes = Global.world.getObjectsByClass(Business);


                        for(int i0 = 0; i0 < bizes.size(); i0++)
                        {
                        		biz = bizes.get(i0);


                            if (biz.isHarvestable() || biz.getState() == Business.STATE_CLOSED)
                            {
                                biz.actionQueue.addState(new ActionProgressBar(null, biz, "HARVEST!!!!!", 1));
                            }
                        }
                        break;
                    }
                    case '~':
                    {
                        Global.world.forceDefcon(GameWorld.DEFCON_LOWEST_LEVEL);
                        Global.world.printPerformanceData = true;
                        break;
                    }
                    case '4':
                    {
                        Global .world .viralMgr .sendRequest (RequestType .ITEM_REQUEST ,.get( "31182300127") ,{"crew_bonus"item ,"request_crew_bonus"viralType ,"BuildingBuddiesCrew"message },void  (boolean param1 ,Array param2 )
            {
                UI.displayMessage("I\'m SO COOOOOOL! success = " + param1 + " zids = " + param2.join(","));
                return;
            }//end
            );
                        break;
                    }
                    case '6':
                    {
                        Global .world .viralMgr .sendRequest (RequestType .GIFT_REQUEST ,.get( "31182300127") ,{"mysterygift_v1"item ,"VisitAcceptAndThank"message },void  (boolean param1 ,Array param2 )
            {
                UI.displayMessage("I\'m SO COOOOOOL GIFT GIFT! success = " + param1 + " zids = " + param2.join(","));
                return;
            }//end
            );
                        break;
                    }
                    case "7":
                    {
                        quest_name = UI.m_questPopoutname;
                        quest = Global.questManager.getQuestByName(quest_name);
                        if (quest == null || !(quest instanceof Quest))
                        {
                            return;
                        }
                        tasks = quest.tasks;
                        len = tasks.length;
                        inx;
                        inx;
                        while (inx < len)
                        {

                            Global.questManager.setQuestTaskProgress(quest_name, inx, quest.tasks.get(inx).total);
                            GameTransactionManager.addTransaction(new TProgressQuestTask(quest_name, inx));
                            inx = (inx + 1);
                        }
                        questPopup =(QuestPopup) UI.currentPopup;
                        questView =(QuestPopupView) questPopup.view;
                        questView.refreshCells();
                        break;
                    }
                    case "X":
                    {
                        Global.player.setFlag("TEST", (Global.player.getFlag("TEST").value + 1) % 2);
                        break;
                    }
                    case 'Q':
                    {
                        Global.guide.notify("GardenIntroGuide");
                        break;
                    }
                    case 'x':
                    {
                        target = Global.world.citySim.roadManager.findRandomRoad();
                        if (target)
                        {
                            PreyManager.createCaptureScene(PreyManager.getPreyData("animalRescue", {id:2}), target.getHotspot(), null, true);
                        }
                        break;
                    }
                    case 'z':
                    {
                        obj = GlobalTableOverrideManager.instance.getTables();
                        UI.displayMessage(obj);
                        break;
                    }
                    case 'Z':
                    {
                        GameTransactionManager.addTransaction(new TCheckInvalidQuests(Global.questManager.getActiveQuests()));
                        break;
                    }
                    case 'P':
                    case 'p':
                    {
                        if (Global.ui.performanceTracker)
                        {
                            Global.ui.performanceTracker.visible = !Global.ui.performanceTracker.visible;
                        }
                        break;
                    }
                    case 'C':
                    {
                        IdleDialogManager.triggerIdleDialog();
                        break;
                    }
                    case 'Y':
                    case 'y'
                    {
                        GameTransactionManager.addTransaction(new TStreamPublish("request_signature", {businessFriendlyName:"Bakery", newBusinessName:"Mike\'s Bakery"}, "31184572851", {invKey:"bus_bakery"}), true, true);
                        break;
                    }
                    case 'c':
                    {

                        break;
                    }
                    case 'a':
                    {
                        RenderManager.dontRunScheduledAnimations = !RenderManager.dontRunScheduledAnimations;
                        break;
                    }
                    case 'j':
                    {
                        OffsetEditor.active = !OffsetEditor.active;
                        break;
                    }
                    case 'U':
                    case 'u':
                    {
                        Global.ui.visible = !Global.ui.visible;
                        break;
                    }
                    case 'F':
                    case 'f':
                    {
                        Utilities.toggleFullScreen();
                        break;
                    }
                    case 'e':
                    case 'E':
                    {
                        Global.world.citySim.toggleDebugRoadOverlay();
                        Global.world.citySim.waterManager.showDbgOverlay(!Global.world.citySim.waterManager.showingDbgOverlay);
                        break;
                    }
                    case 'i':
                    {
                        break;
                    }
                    case 'n':
                    {
                        Global.world.citySim.npcManager.cheatToggleNpcVisibility();
                        break;
                    }
                    case 'N':
                    {
                        TransactionManager.addTransaction(new TUpdateRegenerableResource("gas"), true);
                        break;
                    }
                    case 'm':
                    {
                        data = SagaManager.instance.getActCompleteDialogData("GovernorSaga", "act_governor_1");
                        sagaRewardDialog = new SagaRewardDialog(data);
                        UI.displayPopup(sagaRewardDialog);
                        break;
                    }
                    case 'M':
                    {
                        SurveyManager.instance.displaySurveyOverlay(true);
                        break;
                    }
                    case 'b':
                    {
                        Global.world.citySim.npcManager.cheatNpcRenderTest(true);
                        break;
                    }
                    case 's':
                    {
                        GameObjectLayer.toggleCulling();
                        break;
                    }
                    case 'g':
                    {
                        if (UI.isScreenFrozen())
                        {
                            UI.thawScreen();
                        }
                        else
                        {
                            UI.freezeScreen();
                        }
                        break;
                    }
                    case ';':
                    {
                        url = null;
                        ExternalInterface.call("theFarmOverlay.showFBLikeOverlay", url);
                        break;
                    }
                    case '1':
                    {
                        zoo = new ZooDialog(null);
                        UI.displayPopup(zoo);
                        break;
                    }
                    case '6':
                    {
                        ZCrossPromoManager.setData({i313:{status:"NEW", priority:1, startDate:1318441617, endDate:1319698800, redirect_url:"play/url.php", title:"popup_title_token", text:"popup_title_text", image_url:"popup/image/url", btn_text:"popup_button_text", offer_text:"offer_title_text", offer_image:"offer/image/url"}});
                        d = ZCrossPromoManager.getCrossPromo();
                        if (d)
                        {
                            d.show();
                        }
                        break;
                    }
                    case '7':
                    case '8':
                    {
                        businesses = Global.world.getObjectsByPredicate(this.predicateBusinessIsOpen);
                        int _loc_31 =0;
                        _loc_41 = businesses;
                        for(int i0 = 0; i0 <  i0 = 0; i0 < ss in businesses.size(); i0++.size(); i0++)
                        {
                        		ss =  i0 = 0; i0 < ss in businesses.size(); i0++.get(i0);
                        		business = ss in businesses.get(i0);


                            Global.world.citySim.roadManager.roadAlgorithm(business);
                            Global.world.citySim.roadManager.updateAllRoadTiles();
                        }
                        break;
                    }
                    case '9':
                    {
                        Global.world.addGameMode(new GMDebugPathing(), false);
                        break;
                    }
                    case '0':
                    {
                        Road.showOverlay(!Road.overlay);
                        break;
                    }
                    case 'd':
                    {
                        Global.world.dooberManager.toogleEnableDoobers();
                        break;
                    }
                    case 'B':
                    {
                        targets = Global.world.getObjectsByClass(Business);
                        if (targets.length())
                        {
                            target = targets.get(int(Math.random() * targets.length()));
                            PreyManager.addPreyWalker(PreyManager.getPreyData("copsNBandits", {id:6}), target);
                        }
                        break;
                    }
                    case 'G':
                    {
                        GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
                        break;
                    }
                    case 'A':
                    {
                        zoosk = Global.world.getObjectsByClass(ZooEnclosure);
                        if (zoosk.length > 0)
                        {
                            enclosure = zoosk.get(0);
                            UI.displayPopup(new FriendRewardsDialog(zoosk.get(0), enclosure.getDataForMechanic("giftSenders")));
                        }
                        break;
                    }
                    case 't':
                    case 'T':
                    {
                        Global.world.viralMgr.sendRollCallCollectReminder("31184494730", Global.rollCallManager.getActiveObject("mun_townhall"));
                        break;
                    }
                    case 'r':
                    {
                        mechmech =(TieredDooberMechanic) MechanicManager.getInstance().getMechanicInstance(Global.rollCallManager.getActiveObject("mun_townhall"), "rollCallTieredDooberValue", MechanicManager.ALL);
                        mechmech.executeOverrideForGameEvent(MechanicManager.ALL);
                        break;
                    }
                    case '9':
                    {
                        break;
                    }
                    case '0':
                    {
                        break;
                    }
                    case 'd':
                    {
                        Global.world.dooberManager.toogleEnableDoobers();
                        break;
                    }
                    case 'B':
                    {
                        targets = Global.world.getObjectsByClass(Business);
                        if (targets.length())
                        {
                            target = targets.get(int(Math.random() * targets.length()));
                            PreyManager.addPreyWalker(PreyManager.getPreyData("copsNBandits", {id:6}), target);
                        }
                        break;
                    }
                    case 'G':
                    {
                        GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
                        break;
                    }
                    case 'A':
                    {
                        Global.gameSettings().testAllItemAssets();
                        break;
                    }
                    case 't':
                    case 'T':
                    {
                        Global.world.viralMgr.sendZooFeed("enclosure_savannah", Global.player.cityName);
                        break;
                    }
                    case '#':
                    {
                        zoose = Global.world.getObjectsByClass(ZooEnclosure);
                        if (zoose.length > 0)
                        {
                            dmechanic =(IStorageMechanic) MechanicManager.getInstance().getMechanicInstance(zoose.get(0), ZooManager.MECHANIC_STORAGE, MechanicManager.ALL);
                            if (dmechanic)
                            {
                                gr = dmechanic.purchase("animal_alligator", 1, "zoo|adopted_animal|" + "animal_alligator");
                            }
                        }
                        break;
                    }
                    case '$':
                    {
                        zoosg = Global.world.getObjectsByClass(ZooEnclosure);
                        if (zoosg.length > 0)
                        {
                            mech =(IDictionaryDataMechanic) MechanicManager.getInstance().getMechanicInstance(zoosg.get(0), "giftSenders", MechanicManager.ALL);
                            if (mech)
                            {
                                mech.add("i31231922883", "animal_alligator");
                                mech.add("i31231922884", "welcomewagon");
                                mech.add("i31231922885", "animal_giraffe");
                            }
                        }
                        break;
                    }
                    case '/':
                    {
                        Global.world.citySim.recomputePopulation(Global.world);
                        Global.world.citySim.recomputePopulationCap(Global.world);
                        break;
                    }
                    case '`':
                    {
                        if (ConsoleStub.active == false)
                        {
                            loader = new PluginLoader("plugins/console.swf");
                            loader.load (void  (Event event)
                                {
                                    if (event && event.target.content)
                                    {
                                        ConsoleStub.install(event.target.content);
                                        ConsoleHelper.activate();
                                    }
                                    return;
                                }//end
                            );
                        }
                        break;
                    }
                    case '=':
                    {
                        if (MonsterStub.active == false)
                        {
                            mLoader = new PluginLoader("plugins/monster.swf");
                            mLoader .load (void  (Event event )
                            {
                                if (event && event.target.content)
                                {
                                    MonsterStub.install(event.target.content);
                                }
                                return;
                            }//end
                            );
                        }
                        break;
                    }
                    default:
                    {
                        handled;
                        break;
                    }
                }
            }
            else
            {
                switch(char0)
                {
                    case 'f':
                    case 'F':
                    {
                        Utilities.toggleFullScreen();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            if (handled)
            {
                e.stopImmediatePropagation();
                e.stopPropagation();
            }
            return;
        }//end

         protected void  onError (ErrorEvent event )
        {
            if (Config.DEBUG_MODE)
            {
                UI.displayMessage(event.text, 0, null, "", true);
                if (Global.ui && Global.ui.parent == null)
                {
                    if (this.m_preloaderDone)
                    {
                        UI.pumpPopupQueue();
                    }
                }
            }
            return;
        }//end

        protected void  onFocus ()
        {
            if (!(this.m_zmcFreeze && this.m_zmcOpen))
            {
                UI.thawScreen();
            }
            return;
        }//end

        protected void  onZMCEvent (Array param1 )
        {
            boolean reverseGift ;
            String lootName ;
            int lootAmount ;
            boolean reverseGift2 ;
            String strDialogMessage ;
            String strTitleMessage ;
            int typeDialog ;
            WorldObject hotel ;
            Array params = param1;
            String action ;
            if (params != null && params.length() > 0)
            {
                action = (String)params.get(0);
            }
            vDayVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_VDAY_2011);
            try
            {
                switch(action)
                {
                    case "acceptInvite":
                    {
                        if (params.length() > 1 && params.get(1) != null)
                        {
                            this.acceptInvite(params.get(1));
                        }
                        break;
                    }
                    case "acceptBuildingBuddy":
                    {
                        if (params.length() > 1 && params.get(1) != null)
                        {
                            this.acceptBuildingBuddy(params.get(1));
                        }
                        break;
                    }
                    case "acceptItem":
                    {
                        if (params.length() > 2 && params.get(1) != null)
                        {
                            reverseGift = Number(params.get(2)) > 0;
                            this.acceptItem(params.get(1), reverseGift);
                        }
                        break;
                    }
                    case "acceptItemOnNotif":
                    {
                        if (params.length() > 2 && params.get(1) != null)
                        {
                            if (Global.player.inventory.getItemCountByName(params.get(1)) < params.get(2))
                            {
                                this.acceptItem(params.get(1), false);
                            }
                        }
                        break;
                    }
                    case "acceptLoot":
                    {
                        if (params.length() >= 2 && params.get(1) && params.get(2) != null)
                        {
                            lootName = (String)params.get(1);
                            lootAmount = Integer.parseInt((String)params.get(2));
                            this.handleLoot(lootName, lootAmount);
                        }
                        break;
                    }
                    case "acceptTrain":
                    {
                        this.acceptTrain();
                        break;
                    }
                    case "acceptHolidayItem":
                    {
                        if (params.length() > 2 && params.get(1) != null)
                        {
                            reverseGift2 = (Integer.parseInt((String)params.get(2)) > 0);
                            this.acceptHolidayItem((String)params.get(1), reverseGift2);
                        }
                        break;
                    }
                    case "acceptValentineCard":
                    {
                        if (vDayVariant == 1)
                        {
                            if (!ValentineManager.m_shownIneligible && !ValentineManager.checkEligibility())
                            {
                                ValentineManager.m_shownIneligible = true;
                                strDialogMessage = ZLoc.t("Dialogs", "ValUI_StartupIneligible");
                                strTitleMessage;
                                typeDialog = GenericDialogView.TYPE_OK;
                                UI.displayMessage(strDialogMessage, typeDialog, null, strTitleMessage, true, "", strTitleMessage);
                            }
                        }
                        break;
                    }
                    case "ignoreValentineCard":
                    {
                        if (vDayVariant == 1)
                        {
                            ValentineManager.removeValentineCard(params.get(1), params.get(2));
                            Global.hud.updateVdayCount();
                        }
                        break;
                    }
                    case "acceptWorker":
                    {
                        if (params.get(1) != null)
                        {
                            Global.player.commodities.add("goods", params.get(1));
                            Global.hud.updateCommodities();
                        }
                        break;
                    }
                    case "acceptHotelVIPRequest":
                    {
                        if (params.get(1) != null)
                        {
                            hotel = Global.world.getObjectById(params.get(1));
                            if (hotel != null)
                            {
                                Global.world.centerOnObject(hotel);
                            }
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            catch (pError:Error)
            {
                ErrorManager.addError("Error receiving ZMC call from JS:\n" + pError.message + "\n" + pError.getStackTrace() + "\n" + pError.toString());
            }
            return;
        }//end

        protected void  acceptInvite (Object param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =Global.player.neighbors ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = (String)_loc_2.get(i0);

                if (_loc_3 == param1.uid)
                {
                    return;
                }
            }
            _loc_2.push(param1.uid);
            Global.player.neighbors = _loc_2;
            UI.addToFriendBarData(param1);
            if (Global.ui)
            {
                Global.ui.m_friendBar.updateNeighbors(Global.friendbar);
            }
            if (UI.m_catalog)
            {
                UI.m_catalog.updateChangedCells();
            }
            Global.world.citySim.trainManager.onNeighborAdded();
            return;
        }//end

        protected void  acceptBuildingBuddy (Object param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =Global.player.nonAppFriends ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = (String)_loc_2.get(i0);

                if (_loc_3 == param1.uid)
                {
                    return;
                }
            }
            _loc_2.push(param1.uid);
            Global.player.nonAppFriends = _loc_2;
            MatchmakingManager.instance.addBuildingBuddy(param1.uid);
            UI.addToFriendBarData(param1);
            if (Global.ui)
            {
                Global.ui.m_friendBar.updateNeighbors(Global.friendbar);
            }
            if (UI.m_catalog)
            {
                UI.m_catalog.updateChangedCells();
            }
            Global.world.citySim.trainManager.onNeighborAdded();
            return;
        }//end

        protected void  acceptItem (String param1 ,boolean param2 )
        {
            if (!param2)
            {
                Global.player.inventory.addItems(param1, 1);
            }
            return;
        }//end

        protected void  handleLoot (String param1 ,int param2 )
        {
            Item _loc_3 =null ;
            switch(param1)
            {
                case "coins":
                {
                    Global.player.gold = Global.player.gold + param2;
                    break;
                }
                case "xp":
                {
                    Global.player.xp = Global.player.xp + param2;
                    break;
                }
                case "goods":
                {
                    Global.player.commodities.add(param1, param2);
                    break;
                }
                default:
                {
                    _loc_3 = Global.gameSettings().getItemByName(param1);
                    if (Global.gameSettings().getCollectionByCollectableName(param1) != null)
                    {
                        Global.player.addCollectable(param1);
                    }
                    else
                    {
                        Global.player.inventory.addItems(param1, param2);
                    }
                    break;
                    break;
                }
            }
            return;
        }//end

        protected void  acceptHolidayItem (String param1 ,boolean param2 )
        {
            if (!param2)
            {
                Global.player.inventory.addItems(param1, 1);
                if (HolidayTree.instance != null)
                {
                    HolidayTree.instance.reloadImage();
                    HolidayTree.doPreHolidayStartupChecks(false);
                }
            }
            return;
        }//end

        protected void  acceptTrain ()
        {
            Global.player.gold(Global.player.gold() + Global.gameSettings().getInt("trainOrderRequestCoinReward", 10));
            return;
        }//end

        private void  onEnterFrame (Event event )
        {
            GlobalEngine.zaspManager.preUpdate();
            if (Global.world != null)
            {
                Global.world.updateWorld();
            }
            if (this.m_zmcFreezeCount > 0)
            {
                this.m_zmcFreezeCount--;
            }
            GlobalEngine.zaspManager.postUpdate();
            return;
        }//end

        private void  onResize (Event event )
        {
            GlobalEngine.viewport.scaleX = 1 / Global.ui.screenScale;
            GlobalEngine.viewport.scaleY = 1 / Global.ui.screenScale;
            this.scaleY = Global.ui.screenScale;
            this.scaleX = Global.ui.screenScale ;
            return;
        }//end

        private void  onMouseLeaveFrame (Event event )
        {
            Global.appActive = false;
            return;
        }//end

         public void  disableAllInput ()
        {
            super.disableAllInput();
            UI.freezeScreen();
            return;
        }//end

         public void  enableAllInput ()
        {
            super.enableAllInput();
            UI.thawScreen();
            return;
        }//end

        public void  setZBarQueue (boolean param1 )
        {
            ZBarNotifier.queueNotifications = param1;
            return;
        }//end

        public void  logShowFeedDialog ()
        {
            this.m_feedOpenTime = getTimer();
            StatsManager.count("farm_world_action", "Feed_Open");
            return;
        }//end

        public void  permissionDialogClosed (boolean param1 )
        {
            GlobalEngine.socialNetwork.onPermissionDialogClosed(param1);
            return;
        }//end

        public void  extendedPermissionsDialogClosed ()
        {
            Global.world.viralMgr.extendedPermissionsDialogClosed();
            return;
        }//end

        public void  onRequestSent (String param1 ,String param2 ,Object param3 ,Array param4 )
        {
            if (Global.world && Global.world.viralMgr)
            {
                Global.world.viralMgr.onRequestSent(param1, param2, param3, param4);
            }
            return;
        }//end

        public void  logCloseFeedDialog (Object param1)
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            ViralManager.doUIThaw();
            _loc_2 = getTimer();
            _loc_3 = _loc_2-this.m_feedOpenTime ;
            _loc_3 = int(_loc_3 / 10000);
            if (_loc_3 > 6)
            {
                _loc_4 = ">1 minute";
            }
            else
            {
                _loc_4 = Number((_loc_3 + 1)).toString() + "0 seconds";
            }
            switch(_loc_5)
            {
                case "posted":
                {
                    break;
                }
                case "closed":
                {
                    break;
                }
                case "skipped":
                {
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            GlobalEngine.socialNetwork.onFeedClosed();
            return;
        }//end

        public void  freezeGame (Object param1)
        {
            UI.freezeScreen(false, true);
            return;
        }//end

        public void  ZMCFreezeGame (Object param1)
        {
            if (!this.m_zmcOpen)
            {
                return;
            }
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ZMC_FREEZE_GAME );
            if (_loc_2 != ExperimentDefinitions.ZMC_FREEZE)
            {
                return;
            }
            this.m_zmcFreeze = true;
            this.m_zmcFreezeCount = this.FRAME_RATE * 5;
            UI.freezeScreen(false, true);
            return;
        }//end

        public void  thawGame (Object param1)
        {
            this.m_zmcFreeze = false;
            this.m_zmcFreezeCount = 0;
            UI.thawScreen();
            return;
        }//end

        public void  onDynamicTrayClosed ()
        {
            this.thawGame();
            FrameManager.onDynamicTrayClosed();
            return;
        }//end

        public void  onZMCOpen ()
        {
            this.m_zmcOpen = true;
            StatsManager.count(StatsKingdomType.ZMC_EVENT, "open");
            return;
        }//end

        public void  onZMCClose ()
        {
            this.m_zmcOpen = false;
            StatsManager.count(StatsKingdomType.ZMC_EVENT, "close");
            this.thawGame();
            return;
        }//end

        public void  onZMCLoad ()
        {
            if (Global.zmcOpenedOnInit)
            {
                Global.zmcOpenedOnInit = false;
                StartupSessionTracker.interactive();
            }
            return;
        }//end

    }



class TransactionTrackingData
    public Transaction transaction ;
    public double addTime =-1;
    public double dispatchTime =-1;
    public double responseTime =-1;
    public double serverDuration =-1;
    public static  int MAX_TRACKED =40;

    void  TransactionTrackingData (Transaction param1 )
    {
        this.transaction = param1;
        this.addTime = GlobalEngine.currentTime;
        return;
    }//end

    public boolean  isComplete ()
    {
        return this.dispatchTime != -1 && this.responseTime != -1;
    }//end

    public boolean  isDispatched ()
    {
        return this.dispatchTime != -1;
    }//end

    public double  transactionTime ()
    {
        return this.responseTime - this.dispatchTime;
    }//end
}


class BatchTrackingData {
    public double dispatchTime = -1;
    public double responseTime = -1;
    public int numTransactions = 0;
    public double batchServerTimeElapsed = -1;
    public static int MAX_TRACKED = 15;

    BatchTrackingData() {
        return;
    }//end

    public boolean isComplete() {
        return this.dispatchTime != -1 && this.responseTime != -1;
    }//end

    public double batchTimeElapsed() {
        return this.responseTime - this.dispatchTime;
    }//end
}




