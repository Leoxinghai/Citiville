package Classes;

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

import Classes.actions.*;
import Classes.doobers.*;
import Classes.effects.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.FactoryUI.*;
import Display.PopulationUI.*;
import Display.Toaster.*;
import Display.UpgradesUI.*;
import Display.aswingui.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.bandits.*;
import Modules.franchise.data.*;
import Modules.franchise.display.*;
import Modules.franchise.transactions.*;
import Modules.guide.ui.*;
import Modules.quest.Helpers.*;
import Modules.quest.Managers.*;
import Modules.remodel.*;
import Modules.socialinventory.GameMode.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;

import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class Business extends HarvestableResource implements ICustomToolTipTarget, IPlayerStateObserver, IMerchant
    {
        private  String BUSINESS ="business";
        private  String DESIRED_COMMODITY ="premium_goods";
        protected MerchantCrowdManager m_crowdManager ;
        protected int m_visits ;
        protected int m_peepsInFlight ;
        protected SlidePick m_pick ;
        protected boolean m_shockwaveActive ;
        private boolean m_shockwaveComplete =false ;
        protected TourBus m_tourBus ;
        protected GeekSquadVan m_geekSquad ;
        protected String m_itemName ;
        protected String m_ugcName ;
        protected int m_requiredCustomers =0;
        protected String m_downgradeFromItemName ;
        protected int m_downgradeRewards ;
        private boolean needRibbons =false ;
        private boolean gotRibbons =false ;
        private String m_headquartersName ;
        protected boolean m_isDelivering =false ;
        protected int m_cooldownStart ;
        protected boolean m_neverOpened =true ;
        private boolean m_noShockwaveHits =false ;
        protected FriendFranchiseData m_friendFranchiseData ;
        protected String m_friendFranchiseName ;
        private boolean m_grandOpeningActive =false ;
        private JPanel m_customToolTipHeader ;
        private JPanel m_customToolTipStatus ;
        private JLabel m_customToolTipStatusLabel ;
        private AssetPane m_customToolTipStatusIcon ;
        private StarRatingComponent m_starRatingComponent ;
        private FranchisePhotoComponent m_photoComponent ;
        protected BusinessUpgradesDialog m_upgradeDialog ;
        protected SimpleProgressBar m_collectionProgressBar ;
        private CoinPickEffect m_coinPickEffect ;
        public static  String STATE_CLOSED ="closed";
        public static  String STATE_CLOSED_HARVESTABLE ="closedHarvestable";
        public static  String STATE_OPEN ="open";
        public static  String STATE_OPEN_HARVESTABLE ="openHarvestable";
        public static  GlowFilter UPGRADE_FLOATER_GLOW =new GlowFilter(2105376,0.8,1.5,1.5,50,BitmapFilterQuality.HIGH );
        public static  String DEFAULT_BUSINESS_IMAGE ="static";
        public static  String DELIVERY_VEHICLE ="van_delivery";
        public static  String TOUR_BUS ="tourismbus";
        public static  String GEEK_SQUAD_VAN ="car_geeksquad";
        public static  double HAMMER_PICK_FADE_MULTIPLIER =5;
        public static  double UPGRADE_PICK_FADE_MULTIPLIER =5;
        public static  String UPGRADES_FLAG_INTRO ="biz_ups_intro";
        public static  String UPGRADES_FLAG_HALFWAY ="biz_ups_halfway";
        public static  String UPGRADE_QUEST_NAME ="qm_upgrade_business";
        public static  String DOWNGRADE_DIALOG ="biz_downgrade_to_";
        public static  String MODIFIER_GROUP_DEFAULT ="default";
        public static  String MODIFIER_GROUP_FRANCHISE ="franchise";
        private static int intTourBusNPCCount =0;
        private static int intGeekSquadVanNPCCount =0;

        public  Business (String param1)
        {
            this.m_itemName = param1;
            super(this.m_itemName);
            this.m_crowdManager = new MerchantCrowdManager(this);
            m_objectType = WorldObjectTypes.BUSINESS;
            m_typeName = this.BUSINESS;
            this.m_headquartersName = this.getHeadquartersName();
            this.m_shockwaveActive = false;
            m_isRoadVerifiable = true;
            this.m_neverOpened = true;
            setState(STATE_CLOSED);
            Global.player.addObserver(this);
            return;
        }//end

         public void  cleanUp ()
        {
            this.cleanupCrowd();
            this.cleanupPick();
            if (this.m_coinPickEffect)
            {
                this.m_coinPickEffect.cleanUp();
            }
            if (m_stagePickEffect)
            {
                m_stagePickEffect.cleanUp();
            }
            super.cleanUp();
            return;
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            if (this.isFranchise())
            {
                m_statsName = "franchise";
            }
            registerStatsActionName(TrackedActionType.FIRST_PLANT, "grandopening");
            registerStatsActionName(TrackedActionType.HARVEST, "collect");
            registerStatsActionName(TrackedActionType.PLANT, "open");
            return;
        }//end

         public void  trackDetailedAction (String param1 ,String param2 ,String param3 ,int param4 =1)
        {
            if (this.isFranchise())
            {
                StatsManager.social(StatsKingdomType.GAME_ACTIONS, this.getBusinessOwner(), m_statsName, getStatsActionName(param1), m_statsItemName, param2, param4);
            }
            else
            {
                super.trackDetailedAction(param1, param2, param3, param4);
            }
            return;
        }//end

        public String  headquartersName ()
        {
            return this.m_headquartersName;
        }//end

        private String  getHeadquartersName ()
        {
            return getItem().xml.headquarters;
        }//end

        public void  cleanupCrowd ()
        {
            if (this.m_crowdManager)
            {
                this.m_crowdManager.cleanup();
                this.m_crowdManager = null;
            }
            return;
        }//end

        public void  cleanupPick ()
        {
            if (this.m_pick)
            {
                if (this.m_pick.parent != null)
                {
                    this.m_pick.parent.removeChild(this.m_pick);
                }
                this.m_pick.cleanUp();
                this.m_pick = null;
            }
            return;
        }//end

         public void  makePeepEnterTarget (Peep param1 )
        {
            this.m_crowdManager.makeNpcEnterMerchant(param1);
            return;
        }//end

        public MerchantCrowdManager  crowdManager ()
        {
            return this.m_crowdManager;
        }//end

        public int  commodityQty ()
        {
            return Global.gameSettings().getInt("npcCommodityConsumption", 1);
        }//end

        public void  planVisit (Peep param1 )
        {
            double recoveryDelay ;
            double recoveryDelayMs ;
            peep = param1;
            this.m_peepsInFlight++;
            if (!this.isAcceptingVisits())
            {
                this.updatePeepSpawning();
                recoveryDelay = Global.gameSettings().getNumber("merchantPeepsInFlightWaitTimeMin", 30);
                recoveryDelay = Math.max(this.maxVisits, recoveryDelay);
                recoveryDelayMs = 1000 * recoveryDelay;
                recoveryDelayMs = recoveryDelayMs * Global.gameSettings().getNumber("merchantPeepsInFlightWaitTimeOut", 15);
                TimerUtil .callLater (void  ()
            {
                if (isOpen())
                {
                    m_peepsInFlight = 0;
                    updatePeepSpawning();
                }
                return;
            }//end
            , recoveryDelayMs);
            }
            return;
        }//end

        public void  performVisit (Peep param1 )
        {
            return;
        }//end

        public int  maxVisits ()
        {
            return harvestingDefinition.commodityReq;
        }//end

         public boolean  isRouteable ()
        {
            boolean _loc_1 =false ;
            if (this.isOpen() && !isNeedingRoad && getPopularity())
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public boolean  isAcceptingVisits ()
        {
            Object _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            _loc_1 = this.isRouteable();
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_PEEP_SPAWNING);
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3);
            _loc_4 = ExperimentDefinitions.PEEP_SPAWN_PLANNED==_loc_2;
            _loc_5 = ExperimentDefinitions.OPTIMIZE_FRAMERATE_Q3_ENABLED==_loc_3;
            if (_loc_4 && _loc_5)
            {
                _loc_6 = this.visits;
                if (_loc_1 && _loc_6)
                {
                    _loc_7 = _loc_6.customers + this.m_peepsInFlight;
                    _loc_8 = _loc_6.customersReq + Global.gameSettings().getNumber("merchantPeepsInFlightExceedMax", 0);
                    _loc_1 = _loc_7 < _loc_8;
                }
            }
            return _loc_1;
        }//end

        public void  visits (Object param1 )
        {
            _loc_2 = param1.customers-this.m_visits;
            if (_loc_2 > 0)
            {
                this.m_peepsInFlight = this.m_peepsInFlight - _loc_2;
                if (this.m_peepsInFlight < 0)
                {
                    this.m_peepsInFlight = 0;
                }
            }
            this.m_visits = param1.customers;
            if (this.m_visits >= this.maxVisits)
            {
                this.closeBusiness();
                this.makeHarvestable();
            }
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage(DEFAULT_BUSINESS_IMAGE,this,m_direction,m_statePhase);
            return _loc_1;
        }//end

         public boolean  isCurrentImageLoading ()
        {
            return m_item.isCachedImageLoading(DEFAULT_BUSINESS_IMAGE, m_direction, m_statePhase);
        }//end

         public boolean  isDrawImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading(DEFAULT_BUSINESS_IMAGE, m_direction, m_statePhase);
        }//end

         protected Vector3 Vector  computeDefaultHotspots ().<>
        {
            _loc_1 = m_item.getCachedImageHotspots(DEFAULT_BUSINESS_IMAGE,m_direction);
            return _loc_1 != null ? (_loc_1) : (super.computeDefaultHotspots());
        }//end

        public String  franchiseType ()
        {
            _loc_1 = Item.findUpgradeRoot(this.getItem());
            return _loc_1 != null ? (_loc_1) : (this.m_itemName);
        }//end

        private boolean  isFranchiseSupplied ()
        {
            return this.m_friendFranchiseData && this.m_friendFranchiseData.commodityLeft > 0;
        }//end

        public String  getFranchiseName ()
        {
            return "";

			_loc_1 =Global.franchiseManager.model.getDefaultFranchiseName(this.franchiseType ,this.getBusinessOwner ());
            _loc_2 = Global.franchiseManager.getDataModel(this.getBusinessOwner());
            if (_loc_2)
            {
                _loc_1 = _loc_2.getFranchiseName(this.franchiseType);
            }
            else if (this.m_friendFranchiseData != null)
            {
                _loc_1 = this.m_friendFranchiseData.franchiseName || _loc_1;
            }
            return _loc_1;
        }//end

        public double  getStarRating ()
        {
            FranchiseExpansionData _loc_3 =null ;
            double _loc_1 =0;
            _loc_2 = Global.franchiseManager.getDataModel(this.getBusinessOwner());
            if (_loc_2)
            {
                _loc_3 = _loc_2.getFranchise(this.franchiseType, Global.world.ownerId);
                if (_loc_3)
                {
                    _loc_1 = _loc_3.starRating;
                }
            }
            else
            {
                _loc_1 = this.m_friendFranchiseData.starRating;
            }
            return _loc_1;
        }//end

         protected int  getSellPrice ()
        {
            if (!this.isFranchise())
            {
                return m_item.sellPrice;
            }
            return 0;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_visits = int(param1.visits);
            this.m_peepsInFlight = 0;
            this.m_neverOpened = Boolean(param1.neverOpened);
            this.m_downgradeFromItemName = String(param1.downgradeFromItemName == undefined ? ("") : (param1.downgradeFromItemName));
            this.m_downgradeRewards = (param1.downgradeRewards == undefined ? (0) : (param1.downgradeRewards));
            if (param1 && param1.get("franchise_info"))
            {
                this.m_friendFranchiseData = FriendFranchiseData.loadObject(param1.get("franchise_info"));
            }
            this.updateSlidePickText();
            setState(getState());
            return;
        }//end

         public void  initFriendData (String param1 )
        {
            this.m_friendFranchiseData = new FriendFranchiseData(0, param1, 1);
            setState(STATE_CLOSED);
            this.m_isDragged = false;
            this.calculateDepthIndex();
            this.conditionallyReattach(true);
            return;
        }//end

         public void  drawDisplayObject ()
        {
            super.drawDisplayObject();
            this.gotRibbons = false;
            this.updateRibbons();
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            this.reloadImage();
            super.onObjectDrop(param1);
            this.verifySpawning(m_state, m_state);
            this.createCoinPickEffect();
            this.createStagePickEffect();
            if (!isNeedingRoad)
            {
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            return;
        }//end

         public void  onObjectDropPreTansaction (Vector3 param1 )
        {
            Global.world.citySim.resortManager.updateBusiness(this);
            return;
        }//end

         public void  reloadImage ()
        {
            super.reloadImage();
            return;
        }//end

         public void  replaceContent (DisplayObject param1 )
        {
            super.replaceContent(param1);
            this.updateEffects();
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            this.verifySpawning(m_state, m_state);
            this.updateEffects();
            return;
        }//end

        private void  updateEffects ()
        {
            if (!isNeedingRoad && !this.isUxLockedByQuests)
            {
                m_arrow = this.createSigns();
                this.updateRibbons();
                this.createCoinPickEffect();
                this.createStagePickEffect();
            }
            else
            {
                removeAnimatedEffects();
                if (m_arrow)
                {
                    m_arrow = null;
                }
                if (this.m_coinPickEffect)
                {
                    this.m_coinPickEffect.cleanUp();
                    this.m_coinPickEffect = null;
                }
                removeStagePickEffect();
            }
            return;
        }//end

         protected boolean  updateState (double param1 )
        {
            _loc_2 = getState();
            return _loc_2 != m_state;
        }//end

        public boolean  isBranded ()
        {
            return m_item.isBrandedBusiness;
        }//end

        public boolean  isFranchise ()
        {
            return itemOwner && itemOwner != Global.world.ownerId;
        }//end

        public boolean  isNeverOpened ()
        {
            return this.m_neverOpened;
        }//end

        public boolean  isNewFranchise ()
        {
            return this.isFranchise() && this.isNeverOpened();
        }//end

        public boolean  isNameable ()
        {
            _loc_1 = Global.gameSettings().getItemByName(this.m_itemName);
            return _loc_1.isRenameable;
        }//end

        public void  updatePeepSpawning ()
        {
            this.verifySpawning(m_state, m_state);
            return;
        }//end

        public void  verifySpawning (String param1 ,String param2 )
        {
            String _loc_7 =null ;
            _loc_3 = param1==STATE_OPEN || param1 == STATE_OPEN_HARVESTABLE;
            _loc_4 = param1==STATE_CLOSED || param1 == STATE_CLOSED_HARVESTABLE;
            _loc_5 =             !this.isAcceptingVisits();
            _loc_6 = this.isAcceptingVisits();
            if (this.isAcceptingVisits() && !isNeedingRoad)
            {
                this.activatePeepSpawning();
            }
            if (param2 == STATE_OPEN && _loc_4 && !isNeedingRoad)
            {
                this.m_isDelivering = false;
                _loc_7 = this.DESIRED_COMMODITY;
                addSupply(_loc_7, harvestingDefinition.commodityReq);
                GameTransactionManager.addTransaction(new TOpenBusiness(this, _loc_7));
                if (this.canCountUpgradeActions())
                {
                    if (!Global.player.getSeenFlag(UPGRADES_FLAG_INTRO))
                    {
                        Global.player.setSeenFlag(UPGRADES_FLAG_INTRO);
                        Business.refreshAll();
                        if (!Global.questManager.hasSeenQuestIntro(UPGRADE_QUEST_NAME))
                        {
                            Global.questManager.pumpActivePopup(UPGRADE_QUEST_NAME);
                        }
                    }
                    this.incrementUpgradeActionCount();
                    setShowUpgradeArrow(true);
                }
                if (this.isNeverOpened())
                {
                    this.doGrandOpeningAnimation();
                    trackAction(TrackedActionType.FIRST_PLANT);
                }
            }
            if (_loc_5 || isNeedingRoad)
            {
                if (this.crowdManager)
                {
                    this.crowdManager.stopCollecting();
                }
            }
            if (_loc_5 && _loc_3 || isNeedingRoad)
            {
                Global.world.citySim.npcManager.onBusinessClosed();
                this.m_requiredCustomers = 0;
            }
            return;
        }//end

         protected void  onStateChanged (String param1 ,String param2 )
        {
            super.onStateChanged(param1, param2);
            if (isAttached())
            {
                this.showGrandOpening();
            }
            this.verifySpawning(param1, param2);
            return;
        }//end

         public void  attach ()
        {
            super.attach();
            this.showGrandOpening();
            return;
        }//end

        public void  activatePeepSpawning ()
        {
            int _loc_1 =0;
            if (this.m_requiredCustomers == 0)
            {
                this.m_requiredCustomers = harvestingDefinition.commodityReq - this.m_visits;
                _loc_1 = this.m_requiredCustomers;
            }
            Global.world.citySim.npcManager.startSpawningBusinessPeeps(_loc_1);
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            String _loc_2 =null ;
            OwnedFranchiseData _loc_3 =null ;
            _loc_1 = Global.franchiseManager.getFranchise(m_item.name);
            if (_loc_1)
            {
                _loc_2 = _loc_1.franchiseName;
            }
            if (this.getBusinessCount() == 1 && !Global.isVisiting() && !this.isFranchise() && _loc_2 == null)
            {
                if (this.isNameable())
                {
                    this.createNameDialog();
                }
                else
                {
                    this.createBusinessOpeningDialog();
                }
                _loc_3 = new OwnedFranchiseData(m_item.name, null, null, new Dictionary(), 0);
                Global.franchiseManager.model.addOwnedFranchise(_loc_3);
            }
            else
            {
                this.showGrandOpening();
            }
            Global.world.citySim.resortManager.updateBusiness(this);
            return;
        }//end

        private int  getBusinessCount ()
        {
            Business _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = Global.world.getObjectsByNames(.get(this.franchiseType));
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (!_loc_3.isFranchise())
                {
                    _loc_1++;
                }
            }
            return _loc_1;
        }//end

        protected void  createCelebrationCrowd ()
        {
            if (!Global.isVisiting())
            {
                if (!Global.world.citySim.roadManager.getRoadSize())
                {
                    Global.world.citySim.roadManager.onGameLoaded(null);
                }
                if (this.isFranchiseSupplied() && Global.world.citySim.roadManager.getRoadSize() && !this.m_grandOpeningActive)
                {
                    Global.world.citySim.npcManager.createFranchiseFreebiePeeps(this, 4);
                    this.m_grandOpeningActive = true;
                }
            }
            return;
        }//end

        protected void  createNameDialog ()
        {
            _loc_1 = ZLoc.t("Dialogs","rename_business_message",{itemZLoc.t("Items",this.m_itemName+"_friendlyName")});
            _loc_2 = Global.gameSettings().getInt("maxBusinessNameLength",15);
            _loc_3 = ZLoc.t("Dialogs","rename_business_inputLabel",{itemZLoc.t("Items",this.m_itemName+"_friendlyName")});
            _loc_4 = Global.franchiseManager.model.getFranchiseName(this.franchiseType).substr(0,_loc_2);
            this.m_ugcName = _loc_4;
            InputTextDialog _loc_5 =new InputTextDialog(_loc_1 ,"rename_business",_loc_3 ,_loc_4 ,_loc_2 ,GenericDialogView.TYPE_SAVESHARECOINS ,this.onNameDialogComplete ,true ,this.onNameDialogSkip );
            _loc_5.textField.addEventListener(Event.CHANGE, this.onNameDialogChange);
            UI.displayPopup(_loc_5);
            return;
        }//end

        public String  getBusinessOwner ()
        {
            _loc_1 = m_itemOwner;
            if (!_loc_1)
            {
                _loc_1 = Global.world.ownerId;
            }
            return _loc_1;
        }//end

        protected void  onNameDialogChange (Event event )
        {
            _loc_2 = (TextField)event.target
            if (_loc_2)
            {
                this.m_ugcName = _loc_2.text;
            }
            return;
        }//end

        protected void  onNameDialogComplete (GenericPopupEvent event )
        {
            Global.franchiseManager.model.setFranchiseName(this.m_ugcName, this.franchiseType);
            GameTransactionManager.addTransaction(new TUpdateFranchiseName(this.franchiseType, this.m_ugcName));
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendUGCViralFeed(Global.player, Global.player.cityName, ZLoc.t("Items", this.franchiseType + "_friendlyName"), this.m_ugcName);
            }
            this.showGrandOpening();
            return;
        }//end

        protected void  onNameDialogSkip (GenericPopupEvent event )
        {
            Global.franchiseManager.model.setFranchiseName(this.m_ugcName, this.franchiseType);
            GameTransactionManager.addTransaction(new TUpdateFranchiseName(this.franchiseType, this.m_ugcName));
            this.showGrandOpening();
            return;
        }//end

        protected void  createBusinessOpeningDialog ()
        {
            _loc_1 = ZLoc.t("Dialogs","BusinessOpening",{businessNamethis.getFranchiseName()});
            _loc_2 = ZLoc.exists("Items",this.m_itemName+"_brandMessage")? (ZLoc.t("Items", this.m_itemName + "_brandMessage")) : ("");
            _loc_1 = _loc_1 + "\n\n" + _loc_2;
            GenericDialog _loc_3 =null ;
            if (m_item.isBrandedBusiness && m_item.fbLikeUrl != null)
            {
                _loc_3 = new FbLikeDialog(_loc_1, "RollCall_callFail", GenericDialogView.TYPE_SHARECOINS, this.onBusinessOpeningDialogComplete, "BusinessOpening", "assets/dialogs/citysam_construction_cheer_bust02.png", true, 0, "", null, "", true, m_item.fbLikeUrl, m_item.fbLikeRef, ZLoc.t("Items", this.m_itemName + "_friendlyName"));
            }
            else
            {
                _loc_3 = new CharacterResponseDialog(_loc_1, "RollCall_callFail", GenericDialogView.TYPE_SHARECOINS, this.onBusinessOpeningDialogComplete, "BusinessOpening", "assets/dialogs/citysam_construction_cheer_bust02.png", true, 0, "", null, "", true);
            }
            UI.displayPopup(_loc_3);
            return;
        }//end

        protected void  onBusinessOpeningDialogComplete (GenericPopupEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = this.franchiseType;
                _loc_3 = ZLoc.t("Items", this.franchiseType + "_friendlyName");
                Global.world.viralMgr.sendBusinessOpeningFeed(Global.player, Global.player.cityName, this.franchiseType, _loc_3);
            }
            this.showGrandOpening();
            return;
        }//end

        protected void  showGrandOpening ()
        {
            this.createCelebrationCrowd();
            this.updateRibbons();
            this.updateArrow();
            return;
        }//end

        protected void  doGrandOpeningAnimation ()
        {
            removeAnimatedEffects();
            addAnimatedEffect(EffectType.CELEBRATION_BALLOONS);
            Sounds.playFromSet(Sounds.SET_FANFARE);
            this.needRibbons = false;
            this.m_neverOpened = false;
            return;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            if (!Global.isVisiting() && !Global.world.isEditMode && !this.isInCooldown && this.isFranchiseSupplied() && !this.m_shockwaveActive)
            {
                this.m_crowdManager.startWavePreview();
            }
            return;
        }//end

         public boolean  isHighlightable ()
        {
            return super.isHighlightable && (m_state != STATE_OPEN || Global.world.isEditMode || m_state == STATE_OPEN && ((this.isUpgradePossible() || this.canCountUpgradeActions()) && this.canShowUpgradeToolTips()));
        }//end

        private boolean  checkIfCanSupplyPremiumGoods ()
        {
            if (!acceptsAsSupplies(Commodities.PREMIUM_GOODS_COMMODITY))
            {
                return false;
            }
            if (Global.isVisiting())
            {
                return false;
            }
            if (Global.player.commodities.getCount(Commodities.PREMIUM_GOODS_COMMODITY) > 0)
            {
                return true;
            }
            return false;
        }//end

         public Class  getCursor ()
        {
            _loc_1 = super.getCursor();
            if (isNeedingRoad)
            {
                _loc_1 = EmbeddedArt.hud_act_move;
            }
            else if (m_state == STATE_CLOSED)
            {
                if (this.checkIfCanSupplyPremiumGoods())
                {
                    _loc_1 = EmbeddedArt.hud_biz_premiumsupply;
                }
                else
                {
                    _loc_1 = EmbeddedArt.hud_biz_supply;
                }
            }
            return _loc_1;
        }//end

         public Object  getCustomCursor ()
        {
            ASFont _loc_2 =null ;
            GlowFilter _loc_3 =null ;
            MarginBackground _loc_4 =null ;
            if (this.m_isDelivering)
            {
                return null;
            }
            if (!m_customCursor)
            {
                m_customCursor = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                m_customCursorLabel = new JLabel("");
                m_customCursorHolder = new Sprite();
                Global.ui.addChild(m_customCursorHolder);
                m_customCursorWindow = new JWindow(m_customCursorHolder);
                _loc_2 = ASwingHelper.getBoldFont(16);
                _loc_3 = new GlowFilter(0, 1, 1.2, 1.2, 20, BitmapFilterQuality.HIGH);
                m_customCursorLabel.setForeground(new ASColor(16777215));
                m_customCursorLabel.setFont(_loc_2);
                m_customCursorLabel.setTextFilters(.get(_loc_3));
            }
            DisplayObject _loc_1 =null ;
            if (isNeedingRoad)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_move();
                m_customCursorLabel.setText("");
            }
            else if (this.isFranchiseSupplied())
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_sale_cursor();
                m_customCursorLabel.setText("");
            }
            else if (m_state == STATE_CLOSED)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_biz_supply();
                m_customCursorLabel.setText("");
            }
            else
            {
                return null;
            }
            if (_loc_1)
            {
                _loc_4 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                m_customCursor.setBackgroundDecorator(_loc_4);
                m_customCursor.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            }
            m_customCursor.append(m_customCursorLabel);
            m_customCursorWindow.setContentPane(m_customCursor);
            ASwingHelper.prepare(m_customCursorWindow);
            m_customCursorWindow.show();
            return m_customCursorWindow;
        }//end

         protected Class  getGrownCursor ()
        {
            return EmbeddedArt.hud_act_openBusiness;
        }//end

         protected Class  getWitheredCursor ()
        {
            return EmbeddedArt.hud_act_clean;
        }//end

         public void  onMouseOut ()
        {
            super.onMouseOut();
            if (this.m_crowdManager)
            {
                this.m_crowdManager.endWavePreview();
            }
            return;
        }//end

         protected boolean  isUxLockedByQuests ()
        {
            return Global.questManager != null && !Global.questManager.isUXUnlocked(GameQuestManager.QUEST_UX_BIZ_UNLOCKED);
        }//end

         public int  getHighlightColor ()
        {
            _loc_1 = this.isHarvestable()&& Global.world.getTopGameMode() instanceof GMPlay;
            _loc_2 = this(.isUpgradePossible()|| this.canCountUpgradeActions()) && this.canShowUpgradeToolTips() && (!m_item.upgrade || m_item.upgrade && m_item.upgrade.meetsExperimentRequirement) && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) > 0 && this.isOpen() && Global.world.getTopGameMode() instanceof GMPlay;
            return _loc_2 ? (EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR) : (_loc_1 ? (EmbeddedArt.READY_HIGHLIGHT_COLOR) : (super.getHighlightColor()));
        }//end

         protected void  updateArrow ()
        {
            super.updateArrow();
            if (isNeedingRoad || this.isUxLockedByQuests)
            {
                m_arrow = null;
                if (this.m_coinPickEffect)
                {
                    this.m_coinPickEffect.cleanUp();
                    this.m_coinPickEffect = null;
                }
                removeStagePickEffect();
                return;
            }
            if (!Global.isVisiting() && hasValidId())
            {
                this.createCoinPickEffect();
                this.createStagePickEffect();
                m_arrow = this.createSigns();
            }
            this.reloadImage();
            if (Global.isVisiting())
            {
                return;
            }
            return;
        }//end

        protected Sprite  createSigns ()
        {
            Sprite _loc_1 =new Sprite ();
            DisplayObject _loc_2 =new EmbeddedArt.sale_sign ()as DisplayObject ;
            _loc_2.visible = false;
            if (this.isFranchiseSupplied())
            {
                _loc_2.visible = true;
                removeStagePickEffect();
            }
            _loc_1.addChild(_loc_2);
            _loc_3 = IsoRect.getIsoRectFromSize(this.getSize());
            _loc_2.x = _loc_3.bottom.x - _loc_2.width / 2;
            _loc_2.y = _loc_3.top.y + _loc_2.height / 2;
            return _loc_1;
        }//end

        protected void  createCoinPickEffect ()
        {
            if (!this.m_coinPickEffect)
            {
                this.m_coinPickEffect =(CoinPickEffect) MapResourceEffectFactory.createEffect(this, EffectType.COIN_PICK);
                this.m_coinPickEffect.setPickType(CoinPickEffect.PICK_1);
            }
            else
            {
                this.m_coinPickEffect.setPickType(CoinPickEffect.PICK_1);
                this.m_coinPickEffect.reattach();
            }
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            _loc_1 = StagePickEffect.PICK_4;
            String _loc_2 =null ;
            if (!_loc_1 || isNeedingRoad || this.isFranchiseSupplied() || this.isUxLockedByQuests)
            {
                return;
            }
            if (Global.isVisiting())
            {
                _loc_1 = StagePickEffect.PICK_TOURBUS;
            }
            else if (m_state == STATE_CLOSED)
            {
                _loc_1 = StagePickEffect.PICK_GOODS;
            }
            else if ((m_state == STATE_OPEN || m_state == STATE_OPEN_HARVESTABLE) && getShowUpgradeArrow() && this.canShowUpgradeToolTips())
            {
                if (this.isUpgradePossible())
                {
                    _loc_1 = StagePickEffect.PICK_UPGRADE_HAMMER;
                }
                else if (this.canCountUpgradeActions())
                {
                    _loc_1 = StagePickEffect.PICK_UPGRADE;
                }
            }
            boolean _loc_3 =false ;
            if (_loc_1 == StagePickEffect.PICK_TOURBUS || _loc_1 == StagePickEffect.PICK_GOODS || _loc_1 == StagePickEffect.PICK_UPGRADE_HAMMER || _loc_1 == StagePickEffect.PICK_UPGRADE || m_state == STATE_CLOSED_HARVESTABLE)
            {
                _loc_3 = true;
            }
            if (_loc_3)
            {
                if (!m_stagePickEffect)
                {
                    m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.MASTERY_STAGE_PICK);
                    m_stagePickEffect.setPickType(_loc_1, _loc_2);
                    m_stagePickEffect.queuedFloat();
                }
                else
                {
                    m_stagePickEffect.setPickType(_loc_1, _loc_2);
                    m_stagePickEffect.reattach();
                    m_stagePickEffect.queuedFloat();
                }
                if (_loc_1 == StagePickEffect.PICK_UPGRADE_HAMMER)
                {
                    m_stagePickEffect.setFadeTime(HAMMER_PICK_FADE_MULTIPLIER * Global.gameSettings().getInt("businessUpgradesExclamationFadeDelay", 1));
                }
                else if (_loc_1 == StagePickEffect.PICK_UPGRADE)
                {
                    m_stagePickEffect.setFadeTime(UPGRADE_PICK_FADE_MULTIPLIER * Global.gameSettings().getInt("businessUpgradesExclamationFadeDelay", 1));
                }
            }
            return;
        }//end

        public void  performVisitAnimation (Peep param1 )
        {
            if (!isNeedingRoad)
            {
                this.createStagePickEffect();
                if (m_stagePickEffect)
                {
                    m_stagePickEffect.shine();
                }
            }
            return;
        }//end

         protected void  setArrowPosition ()
        {
            return;
        }//end

        public void  updateSlidePickText ()
        {
            if (this.m_pick == null)
            {
                return;
            }
            return;
        }//end

        public void  addFactoryBoost ()
        {
            return;
        }//end

         public boolean  isHarvestable ()
        {
            if (this.isFranchiseSupplied())
            {
                return true;
            }
            return m_state == STATE_OPEN_HARVESTABLE || m_state == STATE_CLOSED_HARVESTABLE;
        }//end

        public boolean  isInCooldown ()
        {
            return this.m_cooldownStart != 0;
        }//end

         public boolean  isOpen ()
        {
            return m_state == STATE_OPEN || m_state == STATE_OPEN_HARVESTABLE;
        }//end

         public Array  getToolTipComponentList ()
        {
            Component _loc_1 =null ;
            Component _loc_2 =null ;
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                return new Array();
            }
            _loc_1 = ToolTipDialog.buildToolTipComponent(ToolTipDialog.ACTION, m_toolTipComponents, this.getToolTipAction() ? (TextFieldUtil.formatSmallCapsString(this.getToolTipAction())) : (null), ToolTipSchema.getSchemaForObject(this));
            _loc_2 = null;
            _loc_2 = this.getCustomToolTipLevelBar();
            if (_loc_2 == null)
            {
                _loc_2 = this.getCollectionProgressBar();
            }
            return .get(_loc_1, _loc_1 ? (ASwingHelper.verticalStrut(-4)) : (null), this.getCustomToolTipStatus(), this.getCustomToolTipStarComponent(), _loc_2);
        }//end

        public Component  getCollectionProgressBar ()
        {
            double _loc_3 =0;
            double _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            Component _loc_1 =null ;
            _loc_2 = Global.gameSettings().getItemByName(this.m_itemName);
            if (_loc_2.downgradeToItemName != "" && !isNaN(_loc_2.endDate))
            {
                _loc_3 = _loc_2.harvestBasedMasteryTarget;
                _loc_4 = harvestCounter;
                _loc_5 = Global.world.getAllCountByName(_loc_2.harvestBasedMasteryRewardItemName);
                _loc_6 = _loc_2.buildingCap;
                if (_loc_4 >= _loc_3 || _loc_5 >= _loc_6)
                {
                    _loc_1 = null;
                }
                else
                {
                    if (!this.m_collectionProgressBar)
                    {
                        this.m_collectionProgressBar = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 100, 10, 2, -5);
                        ((SimpleProgressBar)this.m_collectionProgressBar).setBgAlpha(1);
                        ((SimpleProgressBar)this.m_collectionProgressBar).setBgColor(2450817);
                        ((SimpleProgressBar)this.m_collectionProgressBar).setTitle(ZLoc.t("Main", "ProgressToGetReward"));
                    }
                    ((SimpleProgressBar)this.m_collectionProgressBar).setProgressRatio(_loc_4, _loc_3);
                    _loc_1 = this.m_collectionProgressBar;
                }
            }
            return _loc_1;
        }//end

         public Component  getCustomToolTipStatus ()
        {
            if (this.m_clearLock || this.isFranchiseSupplied())
            {
                return null;
            }
            return ToolTipDialog.buildToolTipComponent(ToolTipDialog.STATUS, m_toolTipComponents, this.getToolTipStatus(), ToolTipSchema.getSchemaForObject(this));
        }//end

         public Component  getCustomToolTipImage ()
        {
            if (this.isFranchise())
            {
                if (!this.m_photoComponent)
                {
                    this.m_photoComponent = new FranchisePhotoComponent(this.getBusinessOwner());
                }
                return this.m_photoComponent;
            }
            else
            {
                return null;
            }
        }//end

         public Component  getCustomToolTipLevelBar ()
        {
            Component _loc_1 =null ;
            if (!m_isHarvesting && !this.m_isDelivering && m_clearLock == 0 && this.canShowUpgradeToolTips() && !this.isUpgradePossible())
            {
                _loc_1 = super.getCustomToolTipLevelBar();
            }
            if (Global.isVisiting() && _loc_1 instanceof SimpleProgressBar)
            {
                ((SimpleProgressBar)_loc_1).setProgressRatioText(" ");
            }
            return _loc_1;
        }//end

        public Component  getCustomToolTipStarComponent ()
        {
            if (!this.isFranchise() || !this.m_friendFranchiseData)
            {
                return null;
            }
            _loc_1 = Global.franchiseManager.worldOwnerModel.getFranchise(this.franchiseType,this.getBusinessOwner());
            if (!_loc_1 && Global.world.isOwnerCitySam)
            {
                return null;
            }
            _loc_2 = this.m_friendFranchiseData? (this.m_friendFranchiseData.starRating) : (0);
            if (!this.m_starRatingComponent)
            {
                this.m_starRatingComponent = new StarRatingComponent(_loc_2);
            }
            this.m_starRatingComponent.setStarRating(_loc_2);
            return this.m_starRatingComponent;
        }//end

         public String  getToolTipHeader ()
        {
            return this.getFranchiseName();
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_1 =null ;
            if (isNeedingRoad)
            {
                _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
                return _loc_1;
            }
            if (Global.isVisiting())
            {
                if (this.m_clearLock || Global.world.getTopGameMode() instanceof GMVisitBuy)
                {
                    return _loc_1;
                }
                if (Global.player.checkVisitorEnergy(1))
                {
                    _loc_1 = ZLoc.t("Main", "ClickToSendTour");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:1});
                }
            }
            else if (Global.world.getTopGameMode() instanceof GMEditSocialInventory)
            {
                _loc_1 = " ";
            }
            else if (this.isFranchise() && this.isFranchiseSupplied())
            {
                _loc_2 = Global.player.getFriendFirstName(m_itemOwner);
                if (!_loc_2)
                {
                    _loc_2 = ZLoc.t("Dialogs", "DefaultFriendName");
                }
                _loc_1 = ZLoc.t("Main", "BusinessSaleStatus", {name:ZLoc.tn(_loc_2)});
            }
            else if (this.m_visits > 0)
            {
                if (m_state == STATE_OPEN || m_state == STATE_CLOSED_HARVESTABLE)
                {
                    _loc_1 = ZLoc.t("Main", "BusinessCustomers", {served:this.m_visits, max:this.maxVisits});
                }
            }
            else if (this.m_visits == 0)
            {
                if (m_state == STATE_OPEN)
                {
                    _loc_1 = ZLoc.t("Main", "BusinessCustomers", {served:this.m_visits, max:this.maxVisits});
                }
                else if (this.m_isDelivering)
                {
                    _loc_1 = null;
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:harvestingDefinition.commodityReq});
                }
            }
            return _loc_1;
        }//end

        private int  getDaysToEnd (Date param1 ,Date param2 ,int param3 )
        {
            _loc_4 =             24*60*60*1000;
            _loc_5 = param1.getTime();
            _loc_6 = param2.getTime()+param3*_loc_4;
            _loc_7 = param2(.getTime()+param3*_loc_4-_loc_5)/_loc_4;
            _loc_7 = (param2.getTime() + param3 * _loc_4 - _loc_5) / _loc_4 < 0 ? (0) : (_loc_7);
            return _loc_7;
        }//end

        private String  getDowngradeTooltip ()
        {
            Date _loc_5 =null ;
            Date _loc_6 =null ;
            double _loc_7 =0;
            String _loc_1 ="";
            _loc_2 = Global.gameSettings().getItemByName(this.m_itemName);
            _loc_3 = Global.world.getAllCountByName(_loc_2.harvestBasedMasteryRewardItemName);
            _loc_4 = _loc_2.buildingCap;
            if (_loc_2.downgradeToItemName != "" && !isNaN(_loc_2.endDate) && _loc_3 < _loc_4)
            {
                if (harvestCounter < _loc_2.harvestBasedMasteryTarget)
                {
                    _loc_5 = new Date();
                    _loc_6 = new Date(_loc_2.endDate);
                    _loc_7 = this.getDaysToEnd(_loc_5, _loc_6, _loc_2.downgradeGracePeriodDays);
                    if (_loc_7 > 1)
                    {
                        _loc_1 = ZLoc.t("Main", "DaysLeftForCampaignEnd", {days:_loc_7});
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Main", "CampaignEndsToday");
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  needsGoods ()
        {
            if (this.isFranchise() && this.isFranchiseSupplied())
            {
                return false;
            }
            if (this.m_visits > 0)
            {
                return false;
            }
            if (m_state == STATE_OPEN || this.m_isDelivering)
            {
                return false;
            }
            return true;
        }//end

        public boolean  playerHasEnoughGoods ()
        {
            if (Global.player.commodities.getAddedCount(commodities) >= harvestingDefinition.commodityReq)
            {
                return true;
            }
            return false;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = getGameModeToolTipAction();
            if (!_loc_1)
            {
                if (isNeedingRoad)
                {
                    return _loc_1;
                }
                if (!Global.isVisiting())
                {
                    if (this.isFranchise() && this.isFranchiseSupplied())
                    {
                        _loc_1 = ZLoc.t("Main", "BusinessSaleCTA");
                    }
                    else if (!isLocked() && !Global.world.isEditMode && !this.m_shockwaveActive)
                    {
                        switch(m_state)
                        {
                            case STATE_CLOSED:
                            {
                                if (Global.player.commodities.getAddedCount(commodities) >= harvestingDefinition.commodityReq)
                                {
                                    _loc_1 = ZLoc.t("Main", "ClickToSupply");
                                }
                                else
                                {
                                    _loc_1 = Commodities.getNoCommoditiesTooltip();
                                }
                                break;
                            }
                            case STATE_OPEN:
                            {
                                if (this.isUpgradePossible())
                                {
                                    _loc_1 = ZLoc.t("Dialogs", "UpgradeToolTip");
                                }
                                else if (this.canCountUpgradeActions() && this.canShowUpgradeToolTips())
                                {
                                    _loc_1 = ZLoc.t("Main", "UpgradeCallToAction");
                                }
                            }
                            case STATE_OPEN_HARVESTABLE:
                            {
                                break;
                            }
                            case STATE_CLOSED_HARVESTABLE:
                            {
                                if (Global.player.checkEnergy(-harvestingDefinition.harvestEnergyCost, false))
                                {
                                    _loc_1 = ZLoc.t("Main", "BusinessIsHarvestableNoAmount");
                                }
                                else
                                {
                                    _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:harvestingDefinition.harvestEnergyCost});
                                }
                                break;
                            }
                            default:
                            {
                                break;
                            }
                        }
                    }
                }
            }
            _loc_2 = this.getDowngradeTooltip();
            if (_loc_2 != "" && _loc_1 != null)
            {
                _loc_2 = _loc_2 + "\n";
            }
            _loc_1 = _loc_1 == null ? ("") : (_loc_1);
            _loc_1 = _loc_2 + _loc_1;
            return _loc_1;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            if (this.m_crowdManager)
            {
                this.m_crowdManager.onUpdate(param1);
            }
            return;
        }//end

         public void  onPlayAction ()
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            GenericDialog _loc_5 =null ;
            Array _loc_6 =null ;
            Item _loc_7 =null ;
            ReminderToaster _loc_8 =null ;
            return;

            Global.world.citySim.businessVisitBatchManager.onQueueForceAction();
            if (this.isUxLockedByQuests)
            {
                displayStatus(ZLoc.t("Main", "QuestUXLocked"));
                return;
            }
            if (m_visitReplayLock > 0 || isPendingOrder || Global.isVisiting())
            {
                return;
            }
            if (isNeedingRoad)
            {
                super.enterMoveMode();
                return;
            }
            m_actionMode = PLAY_ACTION;
            super.onPlayAction();
            if (!hasValidId() || isLocked())
            {
                showObjectBusy();
                return;
            }
            _loc_1 = getState();
            boolean _loc_2 =false ;
            if (this.isFranchiseSupplied())
            {
                this.m_shockwaveComplete = false;
                removeStagePickEffect();
                Global.world.citySim.pickupManager.enqueue("NPC_businessPickup", this);
                StatsManager.social(StatsCounterType.FRANCHISES, this.getBusinessOwner(), "accept_sale", m_id.toString());
                return;
            }
            switch(m_state)
            {
                case STATE_CLOSED:
                {
                    if (Global.player.commodities.getAddedCount(this.commodities) >= harvestingDefinition.commodityReq)
                    {
                        this.m_isDelivering = true;
                        removeStagePickEffect();
                        Global.world.citySim.pickupManager.enqueue("NPC_businessPickup", this);
                    }
                    else
                    {
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS, true);
                    }
                    break;
                }
                case STATE_CLOSED_HARVESTABLE:
                {
                    if (this.checkLootable())
                    {
                        _loc_2 = true;
                    }
                    break;
                }
                case STATE_OPEN:
                {
                    if (this.canCountUpgradeActions() && this.canShowUpgradeToolTips())
                    {
                        this.m_upgradeDialog = UI.displayBusinessUpgradesDialog(this, this.isUpgradePossible, this.finishUpgradeCallback, this.buyUpgradeCallback);
                        this.m_upgradeDialog.addEventListener(Event.CLOSE, this.removeBusinessUpgradesDialog, false, 0, true);
                    }
                }
                case STATE_OPEN_HARVESTABLE:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (this.m_downgradeFromItemName != null && this.m_downgradeRewards != 0)
            {
                _loc_3 = DOWNGRADE_DIALOG + this.m_downgradeFromItemName;
                if (Global.player && !Global.player.getSeenFlag(_loc_3))
                {
                    _loc_4 = ZLoc.t("Dialogs", "BusinessDowngradeAward", {business_name:ZLoc.t("Items", this.m_downgradeFromItemName + "_friendlyName"), amount:this.m_downgradeRewards, downgradeName:ZLoc.t("Items", this.m_itemName + "_friendlyName")});
                    _loc_5 = new CharacterResponseDialog(_loc_4, "RollCall_callFail", 0, this.onDowngradeDialogClosed, "BusinessDowngrade", "assets/dialogs/citysam_bust02.png", true, 0, "", null, "", true);
                    UI.displayPopup(_loc_5);
                    Global.player.setSeenFlag(_loc_3);
                    StatsManager.count("Zyngage", "revocation_message", "click", this.m_downgradeFromItemName + "_zyngage");
                }
            }
            if (_loc_2)
            {
                if (this.m_shockwaveActive)
                {
                    return;
                }
                if (RemodelManager.isRemodelPossible(this))
                {
                    _loc_6 = RemodelManager.getConstructionCompanyObjects();
                    _loc_7 = _loc_6.length > 0 ? (_loc_6.get(0)) : (null);
                    _loc_8 = new ReminderToaster(ZLoc.t("Dialogs", "remodelReminder_title"), ZLoc.t("Dialogs", "remodelReminder_body"), this, Global.getAssetURL(RemodelManager.TOASTER_ICON), "remodel_reminder", ZLoc.t("Main", "GetStarted"), popRemodelMarket);
                    if (_loc_8.canShow)
                    {
                        Global.ui.toaster.show(_loc_8);
                    }
                }
                if (Global.player.checkEnergy(-harvestingDefinition.harvestEnergyCost))
                {
                    removeStagePickEffect();
                    Global.world.citySim.pickupManager.enqueue("NPC_businessPickup", this);
                }
                else
                {
                    displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
            }
            return;
        }//end

         public void  onVisitPlayAction ()
        {
            m_actionMode = VISIT_PLAY_ACTION;
            _loc_2 = m_numVisitorInteractions+1;
            m_numVisitorInteractions = _loc_2;
            this.doTourBus();
            trackVisitAction(TrackedActionType.SENT_BUS);
            NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_TOURBUS);
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            _loc_2 = super.onVisitReplayAction(null);
            GameTransactionManager.addTransaction(param1);
            m_doobersArray = Global.player.processRandomModifiers(m_item, this, true, m_secureRands);
            Global.world.dooberManager.createBatchDoobers(m_doobersArray, m_item, m_position.x, m_position.y);
            this.doTourBus();
            if (this.canCountUpgradeActions())
            {
                this.incrementUpgradeActionCount();
            }
            return _loc_2;
        }//end

        protected void  unloadTourists ()
        {
            Global.world.citySim.npcManager.createBusinessPeepsWaiting(this, 3);
            return;
        }//end

        protected void  doTourBus ()
        {
            SoundObject loopsound ;
            int rep ;
            int coins ;
            lockForReplay();
            Sounds.play("tourbus_start");
            loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
            _loc_3 = intTourBusNPCCount+1;
            intTourBusNPCCount = _loc_3;
            removeStagePickEffect();
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(getPosition());
            if (!nearRoad)
            {
                if (Global.isVisiting())
                {
                    rep = Global.gameSettings().getInt("friendVisitBusinessRepGain", 1);
                    coins = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                    finalizeAndAwardVisitorHelp(VisitorHelpType.BUSINESS_SEND_TOUR, rep, coins);
                }
                return;
            }
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            this.m_tourBus = new TourBus(TOUR_BUS, false);
            this.m_tourBus.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
            Global.world.citySim.npcManager.addVehicle(this.m_tourBus);
            actions = this.m_tourBus.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (this .m_tourBus ,EffectType .VEHICLE_POOF ),new ActionNavigate (this .m_tourBus ,this ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (this .m_tourBus ,Curry .curry (void  (Business param1 )
            {
                param1.m_tourBus.full = false;
                param1.unloadTourists();
                return;
            }//end
            ,this )),new ActionFn (this .m_tourBus ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_honk");
                Sounds.play("peopleMoveIn");
                return;
            }//end
            ),new ActionProgressBar (this .m_tourBus ,this ,ZLoc .t ("Main","UnloadingTourists")),new ActionFn (this .m_tourBus ,void  ()
            {
                _loc_1 = undefined;
                _loc_2 = undefined;
                if (Global.isVisiting())
                {
                    _loc_1 = Global.gameSettings().getInt("friendVisitBusinessRepGain", 1);
                    _loc_2 = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                    finalizeAndAwardVisitorHelp(VisitorHelpType.BUSINESS_SEND_TOUR, _loc_1, _loc_2);
                    if (canCountUpgradeActions() && canShowUpgradeToolTips())
                    {
                        displayStatus(ZLoc.t("Main", "UpgradeHelp"), "", EmbeddedArt.lightishBlueTextColor);
                    }
                }
                Sounds.play("tourbus_start");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                unlockForReplay();
                return;
            }//end
            ),new ActionNavigate (this .m_tourBus ,startRoad ,this ),new ActionFn (this .m_tourBus ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(this.m_tourBus));
            displayStatus(ZLoc.t("Main", "SendingTourists"));
            return;
        }//end

        protected void  doGeekSquadVan ()
        {
            SoundObject loopsound ;
            if (this.franchiseType != "bus_electronicsstore")
            {
                return;
            }
            lockForReplay();
            Sounds.play("tourbus_start");
            loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
            _loc_3 = intGeekSquadVanNPCCount+1;
            intGeekSquadVanNPCCount = _loc_3;
            removeStagePickEffect();
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(getPosition());
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            this.m_geekSquad = new GeekSquadVan(GEEK_SQUAD_VAN, false);
            this.m_geekSquad.setPosition(this.getHotspot().x, this.getHotspot().y);
            Global.world.citySim.npcManager.addVehicle(this.m_geekSquad);
            unlockForReplay();
            actions = this.m_geekSquad.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (this .m_geekSquad ,EffectType .VEHICLE_POOF ),new ActionNavigate (this .m_geekSquad ,startRoad ,this ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (this .m_geekSquad ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(this.m_geekSquad));
            return;
        }//end

         public boolean  isVisitorInteractable ()
        {
            if (isNeedingRoad || isPendingOrder)
            {
                return false;
            }
            return true;
        }//end

        public void  startCollecting ()
        {
            if (this.m_crowdManager.findNpcsInRange().length <= 0)
            {
                displayStatus(ZLoc.t("Main", "BusinessNoCustomers"));
                this.m_noShockwaveHits = true;
                return;
            }
            if (this.isFranchiseSupplied())
            {
                this.m_shockwaveActive = true;
                UI.popupLock();
                this.m_crowdManager.performFranchiseShockwave();
                this.m_noShockwaveHits = false;
            }
            return;
        }//end

        public void  onWaveFinished ()
        {
            this.m_shockwaveActive = false;
            this.m_shockwaveComplete = true;
            UI.popupUnlock();
            if (this.harvest())
            {
                this.doHarvestDropOff();
            }
            return;
        }//end

         public boolean  harvest ()
        {
            double _loc_5 =0;
            double _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            String _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_13 =null ;
            String _loc_14 =null ;
            GenericDialog _loc_15 =null ;
            m_isHarvesting = false;
            if (!this.m_shockwaveActive && !this.m_shockwaveComplete && this.isFranchiseSupplied())
            {
                this.startCollecting();
                return false;
            }
            if (this.isNeverOpened())
            {
                this.doGrandOpeningAnimation();
                trackAction(TrackedActionType.FIRST_PLANT);
            }
            int _loc_1 =0;
            if (this.m_crowdManager.npcsCaptured)
            {
                _loc_1 = Math.min(this.m_crowdManager.npcsCaptured, Global.gameSettings().getInt("maxShockwaveHits", 10));
            }
            else
            {
                if (this.m_noShockwaveHits)
                {
                    return false;
                }
                _loc_1 = this.m_visits;
            }
            _loc_17 = harvestCounter+1;
            harvestCounter = _loc_17;
            int _loc_2 =0;
            if (!this.isFranchiseSupplied())
            {
                _loc_5 = _loc_1 * getSupplyBonus();
                m_doobersArray = this.makeDoobers(_loc_5);
                clearSupply();
                this.m_visits = 0;
                this.makeNonHarvestable();
                this.updateArrow();
                spawnDoobers();
            }
            else
            {
                m_doobersArray = this.makeDoobers(_loc_1);
                _loc_2 = this.m_friendFranchiseData.commodityLeft;
                this.m_friendFranchiseData.commodityLeft = 0;
                this.updateArrow();
                spawnDoobers();
                _loc_6 = this.m_friendFranchiseData.starRating ? (this.m_friendFranchiseData.starRating) : (1);
                this.displayStatus(ZLoc.t("Main", "HarvestStars", {count:_loc_6, star:ZLoc.tk("Items", "bus_star", "", _loc_6)}), null);
                this.countUpgradeActionIfPossible();
            }
            MechanicManager.getInstance().handleAction(this, "harvest", null);
            GameTransactionManager.addTransaction(new THarvestBusiness(this, _loc_1, _loc_2));
            trackAction(TrackedActionType.HARVEST);
            Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
            this.m_cooldownStart = getTimer();
            if (m_highlighted)
            {
                setHighlighted(true);
            }
            _loc_3 = Global.gameSettings().getItemByName(this.m_itemName);
            if (_loc_3.harvestBasedMasteryTarget > 0 && _loc_3.harvestBasedMasteryTarget == harvestCounter)
            {
                _loc_7 = Global.world.getAllCountByName(_loc_3.harvestBasedMasteryRewardItemName);
                _loc_8 = _loc_3.buildingCap;
                if (_loc_7 < _loc_8)
                {
                    _loc_9 = Global.gameSettings().getItemByName(this.m_itemName).harvestBasedMasteryRewardItemName;
                    _loc_10 = ZLoc.t("Items", _loc_9 + "_friendlyName");
                    _loc_11 = "HarvestBasedMastery";
                    _loc_12 = ZLoc.exists("Items", this.m_itemName + "_masteryMessage") ? (ZLoc.t("Items", this.m_itemName + "_masteryMessage")) : ("");
                    _loc_12 = "\n\n" + _loc_12;
                    _loc_13 = ZLoc.t("Dialogs", "HarvestBasedMastery_desc", {masteryName:_loc_10, brandMessage:_loc_12});
                    _loc_14 = "defaultFeedAccept";
                    if (_loc_9.length > 0)
                    {
                        GameTransactionManager.addTransaction(new TGrantHarvestBasedMasteryReward(_loc_9));
                    }
                    _loc_15 = null;
                    if (m_item.isBrandedBusiness && m_item.fbLikeUrl != null)
                    {
                        _loc_15 = new FbLikeDialog(_loc_13, "RollCall_callFail", GenericDialogView.TYPE_SHARE, this.onMasteryDialogClosed, _loc_11, "assets/dialogs/citysam_construction_cheer_bust02.png", true, 0, "", null, "", true, m_item.fbLikeUrl, m_item.fbLikeRef, ZLoc.t("Items", this.m_itemName + "_friendlyName"));
                    }
                    else
                    {
                        _loc_15 = new CharacterResponseDialog(_loc_13, "RollCall_callFail", GenericDialogView.TYPE_SHARE, this.onMasteryDialogClosed, _loc_11, "assets/dialogs/citysam_construction_cheer_bust02.png", true, 0, "", null, "", true);
                    }
                    UI.displayPopup(_loc_15);
                }
            }
            PreyManager.processHarvest(this);
            _loc_4 = Wonder.itemBonusFlyout(this);
            if (Wonder.itemBonusFlyout(this) != null)
            {
                this.displayStatus(_loc_4, null, Wonder.wonderTextColor);
            }
            this.updateEffects();
            return true;
        }//end

        protected void  onMasteryDialogClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, this.onMasteryDialogClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            _loc_2 = this.franchiseType;
            _loc_3 = ZLoc.t("Items",this.franchiseType+"_friendlyName");
            _loc_4 = Global.gameSettings().getItemByName(this.m_itemName).harvestBasedMasteryRewardItemName;
            _loc_5 = Global.gameSettings().getItemByName(_loc_4).localizedName;
            Global.world.viralMgr.sendHarvestBasedBusinessMasteryFeed(Global.player, Global.player.cityName, _loc_2, _loc_3, _loc_5, _loc_4);
            return;
        }//end

        protected void  onDowngradeDialogClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, this.onDowngradeDialogClosed);
            Array _loc_2 =new Array ();
            _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, this.m_downgradeRewards), this.m_downgradeRewards, this));
            m_doobersArray = _loc_2;
            spawnDoobers();
            return;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            int _loc_6 =0;
            double _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            _loc_2 = this.isFranchiseSupplied();
            _loc_3 = MODIFIER_GROUP_DEFAULT;
            if (_loc_2)
            {
                _loc_3 = MODIFIER_GROUP_FRANCHISE;
            }
            _loc_4 = processBaseDoobers(harvestingDefinition,m_secureRands,_loc_3);
            Array _loc_5 =new Array ();
            if (!_loc_2)
            {
                if (_loc_4.get("coin"))
                {
                    _loc_6 = Math.ceil(_loc_4.get("coin") * param1);
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("xp"))
                {
                    _loc_6 = Math.ceil(_loc_4.get("xp"));
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("energy"))
                {
                    _loc_6 = Math.ceil(_loc_4.get("energy"));
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("goods"))
                {
                    _loc_6 = Math.ceil(_loc_4.get("goods"));
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_GOODS, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("collectable"))
                {
                    _loc_5.push(new Array(_loc_4.get("collectable"), 1, this));
                }
                if (_loc_4.get("item"))
                {
                    _loc_5.push(new Array(_loc_4.get("item"), 1, this));
                }
            }
            else
            {
                if (_loc_4.get("coin"))
                {
                    _loc_7 = this.getRewardScale(this.m_friendFranchiseData.starRating);
                    _loc_8 = param1;
                    _loc_6 = Math.ceil(_loc_4.get("coin") * _loc_8 * _loc_7);
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("energy"))
                {
                    _loc_6 = Math.ceil(_loc_4.get("energy"));
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("goods"))
                {
                    _loc_9 = harvestingDefinition.commodityReq;
                    _loc_6 = Math.ceil(_loc_4.get("goods") * _loc_9);
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Commodities.GOODS_COMMODITY, _loc_6), _loc_6, this));
                }
                if (_loc_4.get("item"))
                {
                    _loc_5.push(new Array(_loc_4.get("item"), 1, this));
                }
            }
            return _loc_5;
        }//end

        private double  getRewardScale (int param1 )
        {
            if (param1 <= 0)
            {
                return 1;
            }
            Array _loc_2 =new Array ();
            String _loc_3 ="ratingScale";
            int _loc_4 =0;
            while (_loc_4 < 5)
            {

                _loc_3 = _loc_3 + ((_loc_4 + 1)).toString();
                _loc_3 = _loc_3 + "Star";
                _loc_2.push(Global.gameSettings().getNumber(_loc_3, 1));
                _loc_3 = "ratingScale";
                _loc_4++;
            }
            return _loc_2.get((param1 - 1));
        }//end

         public Object  doHarvestDropOff (boolean param1 =true )
        {
            if (param1 !=null)
            {
                displayDelayedResourceChanges();
            }
            return super.doHarvestDropOff(param1);
        }//end

        public void  updateRibbons ()
        {
            if ((m_content || m_maskBitmap || m_itemImage) && !this.gotRibbons && this.m_neverOpened || this.needRibbons)
            {
                if (!this.gotRibbons && hasValidId() && !isNeedingRoad)
                {
                    removeAnimatedEffects();
                    addAnimatedEffect(EffectType.RIBBONS);
                    addAnimatedEffect(EffectType.BALLOONS);
                    this.gotRibbons = true;
                }
                this.needRibbons = true;
            }
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            if (param1 < 1 && Global.player.commodities.getTotalCount() < 1)
            {
                ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS);
            }
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

         public Class  getPickupAction ()
        {
            if (this.isFranchiseSupplied())
            {
                return ActionHarvest;
            }
            switch(m_state)
            {
                case STATE_OPEN_HARVESTABLE:
                case STATE_OPEN:
                {
                    return ActionHarvest;
                }
                case STATE_CLOSED_HARVESTABLE:
                {
                    return ActionHarvest;
                }
                case STATE_CLOSED:
                {
                    return ActionBusinessOpen;
                }
                default:
                {
                    break;
                }
            }
            return null;
        }//end

         public double  getHarvestTime ()
        {
            switch(m_state)
            {
                case STATE_CLOSED:
                {
                    return Global.gameSettings().getNumber("actionBarBizOpen");
                }
                case STATE_OPEN_HARVESTABLE:
                case STATE_CLOSED_HARVESTABLE:
                {
                }
                default:
                {
                    return Global.gameSettings().getNumber("actionBarBizHarvest");
                    break;
                }
            }
        }//end

        public double  getOpenTime ()
        {
            return Global.gameSettings().getNumber("actionBarBizOpen");
        }//end

         public String  getActionText ()
        {
            switch(m_state)
            {
                case STATE_CLOSED:
                {
                    return ZLoc.t("Main", "BusinessOpening");
                }
                case STATE_OPEN_HARVESTABLE:
                case STATE_CLOSED_HARVESTABLE:
                {
                }
                default:
                {
                    return ZLoc.t("Main", "BusinessHarvesting");
                    break;
                }
            }
        }//end

        public String  getOpeningText ()
        {
            return ZLoc.t("Main", "BusinessOpening");
        }//end

        public void  openBusiness ()
        {
            switch(m_state)
            {
                case STATE_CLOSED:
                {
                    setState(STATE_OPEN);
                    break;
                }
                case STATE_CLOSED_HARVESTABLE:
                {
                    setState(STATE_OPEN_HARVESTABLE);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  closeBusiness ()
        {
            switch(m_state)
            {
                case STATE_OPEN_HARVESTABLE:
                {
                    setState(STATE_CLOSED_HARVESTABLE);
                    break;
                }
                case STATE_OPEN:
                {
                    setState(STATE_CLOSED);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            if (this.isFranchise())
            {
                FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_REMOVEDBUILDING, this.m_itemName, itemOwner, null, 0, this);
            }
            Global.world.citySim.resortManager.removeBusiness(this);
            return;
        }//end

        public void  makeHarvestable ()
        {
            switch(m_state)
            {
                case STATE_OPEN:
                {
                    setState(STATE_OPEN_HARVESTABLE);
                    break;
                }
                case STATE_CLOSED:
                {
                    setState(STATE_CLOSED_HARVESTABLE);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  makeNonHarvestable ()
        {
            switch(m_state)
            {
                case STATE_OPEN_HARVESTABLE:
                {
                    setState(STATE_OPEN);
                    break;
                }
                case STATE_CLOSED_HARVESTABLE:
                {
                    setState(STATE_CLOSED);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public Object  visits ()
        {
            _loc_1 = (int)getItem().commodityReq
            Object _loc_2 ={customers this.m_visits ,customersReq };
            return _loc_2;
        }//end

        public boolean  checkLootable ()
        {
            if (this.m_visits >= this.maxVisits && Global.player.checkEnergy(-harvestingDefinition.harvestEnergyCost, true))
            {
                return true;
            }
            if (this.m_visits >= this.maxVisits)
            {
                if (!Global.player.checkEnergy(-harvestingDefinition.harvestEnergyCost))
                {
                    displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
            }
            return false;
        }//end

         public boolean  deferProgressBarToNPC ()
        {
            return false;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            HarvestableResource harvestResource ;
            harvestResource;
            return boolean  ()
            {
                _loc_1 = undefined;
                if (!Global.isVisiting())
                {
                    if (m_actionMode != VISIT_REPLAY_ACTION)
                    {
                        if (isFranchiseSupplied())
                        {
                        }
                        else
                        {
                            switch(m_state)
                            {
                                case STATE_OPEN_HARVESTABLE:
                                case STATE_CLOSED_HARVESTABLE:
                                {
                                    if (!checkLootable())
                                    {
                                        return false;
                                    }
                                    Sounds.playFromSet(Sounds.SET_HARVEST, harvestResource);
                                    _loc_1 = harvestingDefinition.harvestEnergyCost;
                                    doEnergyChanges(-_loc_1, new Array("energy", "expenditures", "collect_business", harvestingDefinition.name), false);
                                    Global.player.heldEnergy = Global.player.heldEnergy + _loc_1;
                                    m_heldEnergy = _loc_1;
                                    m_isHarvesting = true;
                                    break;
                                }
                                case STATE_CLOSED:
                                {
                                    if (Global.player.commodities.getAddedCount(commodities) < harvestingDefinition.commodityReq)
                                    {
                                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS, true);
                                        return false;
                                    }
                                    break;
                                }
                                default:
                                {
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        Sounds.playFromSet(Sounds.SET_HARVEST, harvestResource);
                    }
                }
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            if (!Global.isVisiting())
            {
                if (this.isFranchiseSupplied())
                {
                    return Curry .curry (void  (HarvestableResource param1 )
            {
                if (param1.harvest())
                {
                    param1.doHarvestDropOff();
                }
                return;
            }//end
            , this);
                }
                if (m_actionMode != VISIT_REPLAY_ACTION)
                {
                    switch(m_state)
                    {
                        case STATE_CLOSED:
                        {
                            return this.openBusiness;
                        }
                        case STATE_OPEN:
                        case STATE_OPEN_HARVESTABLE:
                        {
                            return null;
                        }
                        case STATE_CLOSED_HARVESTABLE:
                        {
                            return Curry .curry (void  (HarvestableResource param1 )
            {
                if (param1.harvest())
                {
                    param1.doHarvestDropOff();
                }
                return;
            }//end
            , this);
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
            return null;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                m_isHarvesting = false;
                updateArrow();
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                return;
            }//end
            ;
        }//end

         public Point  getProgressBarOffset ()
        {
            if (this.content)
            {
                return new Point(0, this.content.height >> 1);
            }
            return new Point(0, 0);
        }//end

         protected Vector3 Vector  getItemImageHotspots (Item param1 ,String param2 ,int param3 ,String param4 ="").<>
        {
            return param1.getCachedImageHotspots(DEFAULT_BUSINESS_IMAGE, m_direction, param4);
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            if (this.isOpen() && this.canCountUpgradeActions() || this.isHarvestable())
            {
                decrementUpgradeActionCount();
            }
            this.closeBusiness();
            if (m_state == STATE_CLOSED_HARVESTABLE)
            {
                setState(STATE_CLOSED);
            }
            this.m_visits = 0;
            this.m_peepsInFlight = 0;
            super.prepareForStorage(param1);
            Global.world.citySim.resortManager.removeBusiness(this);
            return;
        }//end

         public boolean  warnForStorage ()
        {
            if (m_state == STATE_CLOSED)
            {
                return false;
            }
            return true;
        }//end

         public boolean  canCountUpgradeActions ()
        {
            return super.canCountUpgradeActions() && Global.player.level >= Global.gameSettings().getInt("businessUpgradesRequiredLevel", 15) && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) > 0 && (!m_item.upgrade || m_item.upgrade && m_item.upgrade.meetsExperimentRequirement);
        }//end

         public boolean  canShowUpgradeToolTips ()
        {
            return Global.player && Global.player.getSeenFlag(UPGRADES_FLAG_INTRO) || Global.questManager && Global.questManager.hasSeenQuestIntro(UPGRADE_QUEST_NAME);
        }//end

         public boolean  canShowAlternativeUpgradeToolTip ()
        {
            return super.canShowAlternativeUpgradeToolTip() && Global.player.level >= Global.gameSettings().getInt("businessUpgradesRequiredLevel", 15) && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) > 0 && (!m_item.upgrade || m_item.upgrade && m_item.upgrade.meetsExperimentRequirement);
        }//end

        public void  countUpgradeActionIfPossible ()
        {
            if (this.canCountUpgradeActions())
            {
                this.incrementUpgradeActionCount();
            }
            return;
        }//end

         public void  incrementUpgradeActionCount (boolean param1 =true )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            WelcomeDialog _loc_7 =null ;
            super.incrementUpgradeActionCount();
            if (!this.m_upgradeDialog && this.canShowUpgradeToolTips())
            {
                this.showUpgradeProgressFloaterText(EmbeddedArt.lightishBlueTextColor);
            }
            _loc_2 =(double)(getItem ().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS ));
            double _loc_3 =0.51;
            _loc_4 = _loc_2(-2)/_loc_2;
            if (param1 && !Global.player.getSeenFlag(UPGRADES_FLAG_HALFWAY) && getItem().upgrade.getUpgradeActionsProgress(this) >= _loc_3 && getItem().upgrade.getUpgradeActionsProgress(this) <= _loc_4)
            {
                Global.player.setSeenFlag(UPGRADES_FLAG_HALFWAY);
                _loc_5 = ZLoc.t("Dialogs", "UpgradesHalfWay");
                _loc_6 = ZLoc.t("Dialogs", "UpgradesHalfWay_title");
                _loc_7 = new WelcomeDialog(_loc_5, _loc_6, 0, WelcomeDialog.POS_CENTER, null, true, "assets/missions/architect_167.png");
                UI.displayPopup(_loc_7, true, _loc_6);
                StatsManager.sample(100, StatsCounterType.DIALOG, "businessupgradesui", "halfway_message", getItemName());
            }
            if (this.isThisFirstBusinessUpgrade() && getItem().level == 1 && getItem().upgrade.getUpgradeActionsRemaining(this) == 1)
            {
                Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBusinessUpgradesAlmost"), true);
            }
            return;
        }//end

         public void  showUpgradeProgressFloaterText (int param1 =16777215)
        {
            double total ;
            double amount ;
            SimpleProgressBar tempDisplay ;
            double progress ;
            Sprite tempSpriteHolder ;
            JWindow tempSpriteWindow ;
            color = param1;
            if (this.canCountUpgradeActions())
            {
                total = Number(this.getItem().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS));
                amount = Math.min(this.upgradeActions.getTotal(), total);
                if (amount < total)
                {
                    tempDisplay = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 100, 10, 2, -3);
                    progress = this.getItem().upgrade.getUpgradeActionsProgress(this);
                    tempSpriteHolder = new Sprite();
                    tempSpriteWindow = new JWindow(tempSpriteHolder);
                    tempDisplay.setTitleColor(12443872);
                    tempDisplay.setOutlineColor(0);
                    tempDisplay.setBgAlpha(1);
                    tempDisplay.setOutlineAlpa(0.8);
                    tempDisplay.setFilters(.get(Business.UPGRADE_FLOATER_GLOW));
                    tempDisplay.setPreferredHeight(50);
                    tempSpriteWindow.setContentPane(tempDisplay);
                    ASwingHelper.prepare(tempDisplay);
                    ASwingHelper.prepare(tempSpriteWindow);
                    tempSpriteWindow.show();
                    tempDisplay.setProgress(progress);
                    tempDisplay.setTitle(String("" + amount + "/" + total));
                    MovieClipUtil .runWhenOnStage (tempDisplay ,Curry .curry (void  (SimpleProgressBar param11 )
            {
                param11.setProgressBarFlash();
                return;
            }//end
            , tempDisplay));
                    displayStatusComponent(tempSpriteHolder);
                }
                else
                {
                    this.displayStatus(ZLoc.t("Main", "UpgradeReady"), "", color);
                }
            }
            return;
        }//end

        private boolean  isThisFirstBusinessUpgrade ()
        {
            Business _loc_2 =null ;
            _loc_1 = Global.world.getObjectsByClass(Business);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2.getItem().level > this.getItem().level)
                {
                    return false;
                }
                if (_loc_2 != this && _loc_2.getItem().level == this.getItem().level && _loc_2.upgradeActions.getTotal() >= this.upgradeActions.getTotal())
                {
                    return false;
                }
            }
            return true;
        }//end

         public boolean  isUpgradePossible ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            ItemImage _loc_4 =null ;
            _loc_1 = super.isUpgradePossible();
            if (_loc_1 == true)
            {
                _loc_2 = Global.gameSettings().getEffectByName("fireworks");
                _loc_3 = _loc_2.image.get(0);
                _loc_4 = new ItemImage(_loc_3);
                _loc_4.load();
                Sounds.loadSoundByName("cruise_fireworks");
            }
            return _loc_1;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            Array upgradeDoobers ;
            Business me ;
            Array ids ;
            oldItem = param1;
            newItem = param2;
            a_sendMechanicTxn = param3;
            upgradeDoobers = grantUpgradeRewards();
            super.onUpgrade(oldItem, newItem);
            this.m_crowdManager.loadShockWaveSettings(newItem.name);
            this.m_itemName = newItem.name;
            this.resetUpgradeActionCount();
            me = new Business();
            ids = me.m_upgradeActions.getFriendHelperIds();
            m_defaultUpgradeFinish =void  ()
            {
                me.m_visits = me.maxVisits;
                me.m_peepsInFlight = 0;
                closeBusiness();
                makeHarvestable();
                setState(getState());
                if (upgradeDoobers != null && upgradeDoobers.length > 0)
                {
                    Global.world.dooberManager.createBatchDoobers(upgradeDoobers, m_item, m_position.x, m_position.y, true);
                }
                fireZeMissiles();
                if (ids != null && ids.length > 0)
                {
                    TimerUtil .callLater (void  ()
                {
                    GenericDialog d =new GenericDialog(ZLoc.t("Dialogs","UpgradesThankFriends"),"",GenericDialogView.TYPE_OK ,function(event GenericPopupEvent )
                    {
                        _loc_2 = undefined;
                        if (event.button == GenericDialogView.YES)
                        {
                            _loc_2 = Global.gameSettings().getString("businessUpgradeFriendReward", "mysterygift_v1");
                            if (Global.gameSettings().getItemByName(_loc_2) == null || !Global.gameSettings().getItemByName(_loc_2).giftable)
                            {
                                ErrorManager.addError("Invalid friend gift name: " + _loc_2);
                                _loc_2 = "mysterygift_v1";
                            }
                            StatsManager.sample(100, StatsCounterType.DIALOG, "businessupgradesui", "send_gift", getItemName());
                            UpgradesUtil.redirectToGiftPage(_loc_2, ids);
                        }
                        return;
                    }//end
                    );
                    UI.displayPopup(d, true);
                    return;
                }//end
                , 5000);
                }
                return;
            }//end
            ;
            dataMechanic = (IDictionaryDataMechanic)MechanicManager.getInstance().getMechanicInstance(this,UpgradeActions.MECHANIC_NAME,MechanicManager.ALL)
            if (dataMechanic)
            {
                dataMechanic.clear(a_sendMechanicTxn);
            }
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "business", "upgrade_from", oldItem.name);
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "business", "upgrade_to", newItem.name);
            return;
        }//end

        protected void  finishUpgradeCallback (Event event )
        {
            String _loc_2 =null ;
            if (this.isUpgradePossible())
            {
                this.m_upgradeDialog.close();
                upgradeBuildingIfPossible();
            }
            else
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, "businessupgradesui", "get_help", getItemName());
                _loc_2 = "upgradeHelp.php?wId=" + m_id;
                FrameManager.navigateTo(_loc_2);
            }
            return;
        }//end

        protected boolean  buyUpgradeCallback ()
        {
            return false;
        }//end

        protected void  removeBusinessUpgradesDialog (Event event )
        {
            this.m_upgradeDialog = null;
            return;
        }//end

        protected void  onUpgradeGateSuccess ()
        {
            this.upgradeBuildingIfPossible();
            return;
        }//end

         public void  fireZeMissiles ()
        {
            Business m_biz ;
            Sounds.play("cruise_fireworks");
            m_biz;
            MapResourceEffectFactory.createEffect(m_biz, EffectType.FIREWORK_BALLOONS);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_biz, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_biz, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_biz, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 700);
            return;
        }//end

        public static void  decrementBusCounter ()
        {
            if (intTourBusNPCCount > 0)
            {
                _loc_2 = intTourBusNPCCount-1;
                intTourBusNPCCount = _loc_2;
            }
            if (intTourBusNPCCount == 0)
            {
                Sounds.stop("tourbus_engine_loop");
            }
            return;
        }//end

        public static void  decrementGeekSquadVanCounter ()
        {
            if (intGeekSquadVanNPCCount > 0)
            {
                _loc_2 = intGeekSquadVanNPCCount-1;
                intGeekSquadVanNPCCount = _loc_2;
            }
            if (intGeekSquadVanNPCCount == 0)
            {
                Sounds.stop("tourbus_engine_loop");
            }
            return;
        }//end

        public static void  refreshAll (Array param1)
        {
            Array _loc_2 =null ;
            WorldObject _loc_3 =null ;
            MapResource _loc_4 =null ;
            if (param1 !=null)
            {
                _loc_2 = Global.world.getObjectsByNames(param1);
            }
            else
            {
                _loc_2 = Global.world.getObjectsByClass(Business);
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 =(MapResource) _loc_3;
                if (_loc_4 != null)
                {
                    _loc_4.setState(_loc_4.getState());
                    _loc_4.refreshArrow();
                }
            }
            return;
        }//end

    }





