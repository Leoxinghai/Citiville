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

import Classes.Managers.*;
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Classes.zbar.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.NeighborUI.*;
import Display.hud.*;
import Display.hud.components.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.GlobalTable.*;
import Modules.franchise.data.*;
import Modules.quest.Display.QuestManager.*;
import Modules.quest.Managers.*;
import Modules.socialinventory.*;
import Modules.stats.data.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Modules.storage.*;
import Transactions.*;

import com.adobe.utils.*;
//import flash.net.*;
//import flash.utils.*;
import validation.*;
import Modules.stats.*;

import com.xinghai.Debug;

    public class Player extends SavedObject implements IStatsTarget
    {
        public  String SEEN_EXTENDED_PERMISSIONS_ANY ="any";
        public  double OUT_OF_ENERGY_POPUP_DELAY =5000;
        public  double ENERGY_OVERFLOW_POPUP_DELAY =1000;
        public  double SUPER_FEED_DELAY =86400;
        public String name ;
        public SocialNetworkUser snUser ;
        public MutualFriendInviteDialog mutualFriendsDialog ;
        protected Array m_friends ;
        protected Array m_appFriends ;
        protected Array m_nonAppFriends ;
        protected boolean m_hasFriendsSNData =false ;
        protected int m_lastLogin ;
        protected int m_currLogin ;
        protected double m_level =1;
        protected double m_xp =0;
        protected double m_gold ;
        protected double m_cash ;
        protected double m_energy ;
        protected double m_paidEnergy =0;
        protected Object m_regenerableResources ;
        protected Object m_regenerableLastUpdates ;
        protected double m_heldEnergy =0;
        protected double m_socialLevel =1;
        protected double m_socialXp =0;
        protected double m_visitorEnergy ;
        protected double m_energyMax ;
        protected double m_expansionsPurchased ;
        protected double m_expansionCostLevel ;
        protected double m_lastEnergyCheck ;
        public String lastVisitedMap =null ;
        protected Dictionary m_licenses ;
        protected Dictionary m_sublicenses ;
        public Dictionary m_energyModifiers ;
        protected Dictionary m_limitedItems ;
        protected Array m_unparsedNeighborsData ;
        protected Array m_neighbors ;
        protected int m_maxNeighbors =0;
        protected int m_franchiseUnlocksPurchased =0;
        protected int m_lastPurchaseDate =-1;
        protected boolean m_hasMadePurchase =false ;
        protected int m_franchiseMoneyReturned =0;
        protected Array m_topFriends ;
        protected Array m_missionItemInventory ;
        protected Object m_gifts ;
        protected Object m_activeRequests ;
        protected Object m_sentGifts ;
        protected Object m_promos ;
        private Inventory m_inventory ;
        private RequestInventoryComponent m_requestInventory ;
        private StorageComponent m_storageComponent ;
        private Object m_collections ;
        private Object m_completedCollections ;
        private Object m_collectionTradeIns ;
        public Array wishlist ;
        private Commodities m_commodities ;
        protected Object m_options ;
        private boolean m_isNew =false ;
        private int m_creationTimestamp =0;
        private boolean m_isFirstDay =false ;
        private double m_hasVisitFriend =0;
        private boolean m_allowQuests =false ;
        public Array m_flagContainer ;
        private Object m_seenFlags ;
        private Object m_seenSessionFlags ;
        private Object m_xPromoData ;
        private Object m_geoData ;
        private Array m_news ;
        protected int m_homeIslandSize ;
        private int m_randomRollCounter ;
        private Dictionary m_randomRollCounterMap ;
        private Array m_stateObservers ;
        protected int m_lastWarpTime =0;
        private int m_populationObservedOnLastExpansion =0;
        private double m_lastXGameGiftSentTime =0;
        private Object m_receivedXGameGifts ;
        private Object m_xGameLevels ;
        protected int m_lastActionTime ;
        protected int m_lastLapsedOfflineTime ;
        protected String m_cityName ;
        protected String m_pendingCityName ;
        protected int m_socialNetworkId ;
        protected DailyBonusManager m_dailyBonusManager ;
        protected int m_outOfEnergyCount =0;
        protected int OOE_Coin =-1;
        protected boolean m_handlingOutOfEnergy =false ;
        protected boolean m_doOutOfEnergyFeed =false ;
        protected boolean m_doOutOfEnergyRequest =false ;
        protected int m_dailyVisits ;
        public int additionalWareHouseSlots =0;
        protected Object m_playerTimestamps ;
        protected String m_currentTimestamp_EventID ;
        protected String m_previousTimestamp_EventID ;
        private  int COINFLIP_FEED =0;
        private  int COINFLIP_REQUEST =1;
        protected int m_compensationFlag =0;
        private int m_randomModifierOverride =-1;
        private int m_randomModifierGroupOverride =-1;
        protected int m_numQuestsLoading =0;
        private Array m_savedQuestSequence ;
        private Array m_questManagerQuests ;
        private Array m_hiddenQuests ;
        private int m_neighbor_gate_quest_2_offset =0;
        private Dictionary m_showMFI ;
        public Array inactiveNeighbors ;
        private boolean m_npc_cloud_visible =true ;
        public double debugTime =0;
        protected Object m_dailyRewards =null ;
        protected Array m_completedQuests ;
        public boolean didVisitAction =false ;
        protected Object m_friendRewards ;
        public boolean hasSeenWitherPopup_plot =false ;
        public boolean hasSeenWitherPopup_ship =false ;
        public boolean hasSeenInstantPopup_plot =false ;
        public boolean hasSeenInstantPopup_ship =false ;
        public boolean hasSeenInstantPopup_residence =false ;
        public boolean hasSaleTransactionCompleted =false ;
        public String countryCode ="";
        protected boolean m_hasMadeRealPurchase =false ;
        protected Array m_coupons ;
        private double m_lastSuperFeedPostError ;
        private double m_visitedCitySamVersion =-1;
        private boolean m_hasVisitedCitySam =false ;
        protected boolean m_hasExtendedPermissions =true ;
        public Array purchasedSpecials ;
        public Object featureData ;
        public boolean hasMadeEoQPurchase =false ;
        public boolean cacheButtonShown =false ;
        public static  int FAKE_USER_ID =-1;
        public static  String FAKE_USER_ID_STRING =FAKE_USER_ID.toString ();
        public static  String FREE_ENERGY_CAP ="energyCap";
        public static  String PAID_ENERGY_CAP ="paidEnergyCap";
        public static  String GLOBAL_MODIFIERS_ENERGY_ACTION ="defaultenergyactionpack";
        public static  int NOT_COMPENSATED =0;
        public static  int COMPENSATED_NON_GIFT_CARD =-1;
        public static  int COMPENSATED_GIFT_CARD =1;


        protected double m_kdbomb ;
        protected double m_lrbomb ;

        protected double m_pregold ;


        public  Player (SocialNetworkUser param1 )
        {
            this.m_licenses = new Dictionary();
            this.m_sublicenses = new Dictionary();
            this.m_energyModifiers = new Dictionary();
            this.m_limitedItems = new Dictionary();
            this.m_neighbors = new Array();
            this.m_topFriends = new Array();
            this.m_missionItemInventory = new Array();
            this.m_gifts = new Object();
            this.m_activeRequests = new Object();
            this.m_sentGifts = new Object();
            this.m_promos = new Object();
            this.m_collections = {};
            this.m_completedCollections = {};
            this.m_collectionTradeIns = {};
            this.wishlist = new Array();
            this.m_options = {};
            this.m_flagContainer = new Array();
            this.m_seenFlags = {};
            this.m_seenSessionFlags = {};
            this.m_xPromoData = {};
            this.m_geoData = {};
            this.m_playerTimestamps = {};
            this.m_savedQuestSequence = new Array();
            this.m_questManagerQuests = new Array();
            this.m_hiddenQuests = new Array();
            this.inactiveNeighbors = new Array();
            this.m_coupons = new Array();
            if (param1 !=null)
            {
                this.name = param1.name;
                this.snUser = param1;
            }
            this.mutualFriendsDialog = null;
            this.m_friends = new Array();
            this.m_stateObservers = new Array();
            this.m_storageComponent = new StorageComponent();
            this.m_requestInventory = new RequestInventoryComponent();
            return;
        }//end

        public boolean  hasVisitedCitySam ()
        {
            return this.m_hasVisitedCitySam;
        }//end

        public void  hasVisitedCitySam (boolean param1 )
        {
            this.m_hasVisitedCitySam = param1;
            return;
        }//end

        public void  visitedCitySamVersion (double param1 )
        {
            this.m_visitedCitySamVersion = param1;
            return;
        }//end

        public DailyBonusManager  dailyBonusManager ()
        {
            return this.m_dailyBonusManager;
        }//end

         public Object  getSaveObject ()
        {
            _loc_1 = super.getSaveObject();
            _loc_1.level = this.level;
            _loc_1.gold = this.gold;
            _loc_1.cash = this.cash;
            _loc_1.xp = this.xp;
            _loc_1.neighbors = this.neighbors;
            _loc_1.options = this.options;
            _loc_1.rollCounter = this.rollCounter;
            return _loc_1;
        }//end

        public StatsCountData Vector  getStatsCounterObject ().<>
        {
            String _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            Vector<StatsCountData> _loc_1 =new Vector<StatsCountData>();
            _loc_1.push(new StatsCountData(new StatsOntology("user_level"), this.level));
            _loc_1.push(new StatsCountData(new StatsOntology("num_coins"), this.m_gold));
            _loc_1.push(new StatsCountData(new StatsOntology("num_cash"), this.m_cash));
            _loc_2 = this.m_inventory.getItemCountByName("permits");
            _loc_3 = this.m_inventory.getItemCountByName("energy_1");
            _loc_4 = this.m_inventory.getItemCountByName("energy_2");
            _loc_5 = this.m_inventory.getItemCountByName("energy_3");
            _loc_1.push(new StatsCountData(new StatsOntology("inv_permits"), _loc_2));
            _loc_1.push(new StatsCountData(new StatsOntology("inv_energy_1"), _loc_3));
            _loc_1.push(new StatsCountData(new StatsOntology("inv_energy_2"), _loc_4));
            _loc_1.push(new StatsCountData(new StatsOntology("inv_energy_3"), _loc_5));
            _loc_6 = this.m_commodities.getCommodityNames();
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);

                _loc_8 = this.m_commodities.getCount(_loc_7);
                _loc_9 = this.m_commodities.getCapacity(_loc_7);
                _loc_1.push(new StatsCountData(new StatsOntology("num_" + _loc_7), _loc_8));
                _loc_1.push(new StatsCountData(new StatsOntology(_loc_7 + "_capacity"), _loc_9));
            }
            _loc_1.push(new StatsCountData(new StatsOntology("num_energy"), this.m_energy));
            _loc_1.push(new StatsCountData(new StatsOntology("max_num_energy"), this.energyMax));
            return _loc_1;
        }//end

        public void  setCompletedQuests (Array param1 )
        {
            this.m_completedQuests = param1;
            return;
        }//end

        public void  addCompletedQuest (String param1 )
        {
            if (!this.isQuestCompleted(param1))
            {
                this.m_completedQuests.push(param1);
            }
            return;
        }//end

        public boolean  isQuestCompleted (String param1 )
        {
            return this.m_completedQuests.indexOf(param1) != -1;
        }//end

        public boolean  hasMadeRealPurchase ()
        {
            return this.m_hasMadeRealPurchase;
        }//end

        public void  hasMadeRealPurchase (boolean param1 )
        {
            this.m_hasMadeRealPurchase = param1;
            return;
        }//end

        public int  socialNetworkId ()
        {
            return this.m_socialNetworkId;
        }//end

        public void  socialNetworkId (int param1 )
        {
            this.m_socialNetworkId = param1;
            return;
        }//end

        public double  lastSuperFeedPostError ()
        {
            return this.m_lastSuperFeedPostError;
        }//end

        public void  lastSuperFeedPostError (double param1 )
        {
            this.m_lastSuperFeedPostError = param1;
            return;
        }//end

        public Object  dailyRewards ()
        {
            return this.m_dailyRewards;
        }//end

        public String  gender ()
        {
            _loc_1 = this.snUser? (this.snUser.gender) : ("masc");
            _loc_1 = _loc_1 == "masc" || _loc_1 == "fem" ? (_loc_1) : ("masc");
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            Object _loc_3 =null ;
            int _loc_4 =0;
            Object _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            XML _loc_12 =null ;
            int _loc_13 =0;
            if (param1.neighbor_gate_quest_2_offset)
            {
                this.m_neighbor_gate_quest_2_offset = param1.neighbor_gate_quest_2_offset;
            }
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STARTER_PACK2)>0;
            if (_loc_2 && param1.hasMadeRealPurchase === true)
            {
                this.m_hasMadeRealPurchase = true;
            }
            if (param1.lastPurchaseDate)
            {
                this.m_lastPurchaseDate = param1.lastPurchaseDate;
            }
            if (param1.visitedCitySamVersion)
            {
                this.m_visitedCitySamVersion = param1.visitedCitySamVersion;
            }
            this.m_hasMadeRealPurchase = param1.hasMadeRealPurchase;
            this.m_seenFlags = param1.seenFlags;
            if (param1.playerNews != null)
            {
                this.m_news = PlayerNews.loadFromObjects(param1.playerNews);
            }
            for(int i0 = 0; i0 < param1.flagContainer.size(); i0++)
            {
            	_loc_3 = param1.flagContainer.get(i0);

                this.m_flagContainer.put(_loc_3.name,  Flag.buildFromServerObject(_loc_3));
            }
            this.m_lastLogin = param1.oldLoginTimeStamp;
            this.m_currLogin = param1.lastLoginTimestamp;
            this.m_inventory = new Inventory(this, param1.inventory);
            this.m_commodities = new Commodities(param1.commodities);
            _loc_4 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MARKET_2);
            if (_loc_4 == ExperimentDefinitions.MARKET_2_FEATURE)
            {
            }
            this.m_requestInventory.loadObject(param1.requestInventory);
            this.m_storageComponent.loadObject(param1.storageComponent);
            this.m_dailyRewards = param1.dailyRewards;
            if (param1.m_energyModifiers)
            {
                this.m_energyModifiers = new Dictionary();
                for(int i0 = 0; i0 < param1.m_energyModifiers.size(); i0++)
                {
                		_loc_8 = param1.m_energyModifiers.get(i0);

                    this.m_energyModifiers.put(_loc_8,  param1.m_energyModifiers.get(_loc_8));
                }
            }
            this.gold = param1.gold;
            this.cash = param1.cash;
            this.level = param1.level;
            this.kdbomb = param1.kdbomb;
            this.lrbomb = param1.lrbomb;

            this.additionalWareHouseSlots = param1.additionalWareHouseSlots;
            this.xp = param1.xp;
            this.m_energy = param1.energy;
            this.m_paidEnergy = param1.paidEnergy;
            this.featureData = param1.featureData;
            this.m_regenerableResources = param1.get("regenerableResources");
            this.m_regenerableLastUpdates = param1.get("regenerableLastUpdates");
            this.m_energyMax = param1.energyMax;
            this.m_lastEnergyCheck = param1.lastEnergyCheck;
            this.m_npc_cloud_visible = param1.npc_cloud_visible;
            if (param1.expansionsPurchased != null)
            {
                this.m_expansionsPurchased = param1.expansionsPurchased;
            }
            else
            {
                this.m_expansionsPurchased = 0;
            }
            if (param1.expansionCostLevel != null)
            {
                this.m_expansionCostLevel = param1.expansionCostLevel;
            }
            else
            {
                this.m_expansionCostLevel = 1;
            }
            this.m_populationObservedOnLastExpansion = param1.get("populationObservedOnLastExpansion") ? (param1.get("populationObservedOnLastExpansion")) : (0);
            this.m_lastXGameGiftSentTime = param1.get("lastXGameGiftSentTime");
            this.m_receivedXGameGifts = param1.get("receivedXGameGifts");
            this.m_xGameLevels = param1.get("xGameLevels");
            if (param1.maxNeighbors != null)
            {
                this.maxNeighbors = param1.maxNeighbors;
            }
            else
            {
                this.maxNeighbors = 0;
            }
            if (param1.franchiseUnlocksPurchased != null)
            {
                this.franchiseUnlocksPurchased = param1.franchiseUnlocksPurchased;
            }
            else
            {
                this.franchiseUnlocksPurchased = 0;
            }
            if (param1.franchiseMoneyReturned != null)
            {
                this.franchiseMoneyReturned = param1.franchiseMoneyReturned;
            }
            else
            {
                this.franchiseMoneyReturned = 0;
            }
            this.m_randomModifierGroupOverride = param1.hasOwnProperty("randomModifierGroupOverride") ? (param1.randomModifierGroupOverride) : (-1);
            this.m_randomModifierOverride = param1.hasOwnProperty("randomModifierOverride") ? (param1.randomModifierOverride) : (-1);
            this.options = param1.options;
            this.hasVisitFriend = param1.hasVisitFriend;
            this.m_homeIslandSize = param1.homeIslandSize;
            this.m_gifts = param1.gifts;
            this.m_sentGifts = param1.sentGifts;
            this.m_activeRequests = param1.activeRequests;
            this.m_randomRollCounter = param1.rollCounter;
            if (param1.rollCounterMap)
            {
                this.m_randomRollCounterMap = new Dictionary();
                for(int i0 = 0; i0 < param1.rollCounterMap.size(); i0++)
                {
                		_loc_9 = param1.rollCounterMap.get(i0);

                    this.setRollCounterValue(_loc_9, param1.rollCounterMap.get(_loc_9));
                }
            }
            this.m_lastWarpTime = param1.lastWarpTime;
            this.m_lastActionTime = param1.lastActionTime;
            this.m_lastLapsedOfflineTime = param1.lastLapsedOfflineTime;
            Array _loc_5 =new Array();
            if (param1.neighbors instanceof Array)
            {
                _loc_5 = param1.neighbors;
            }
            else
            {
                for(int i0 = 0; i0 < param1.neighbors.size(); i0++)
                {
                		_loc_10 = param1.neighbors.get(i0);

                    _loc_5.push(_loc_10);
                }
            }
            if (param1.nonSNNeighbors instanceof Array)
            {
                _loc_5 = _loc_5.concat(param1.nonSNNeighbors);
            }
            else
            {
                for(int i0 = 0; i0 < param1.nonSNNeighbors.size(); i0++)
                {
                		_loc_11 = param1.nonSNNeighbors.get(i0);

                    _loc_5.push(_loc_11);
                }
            }
            this.neighbors = _loc_5;
            if (param1.dailyVisits)
            {
                this.m_dailyVisits = param1.daily_visits.count;
            }
            if (param1.compensationFlag)
            {
                this.m_compensationFlag = param1.compensationFlag;
            }
            this.m_socialLevel = param1.socialLevel;
            this.m_socialXp = param1.socialXp;
            if (SocialInventoryManager.isFeatureAvailable())
            {
                SocialInventoryManager.instance.setCapacitiesFromXml(this.m_socialLevel);
            }
            this.m_dailyBonusManager = new DailyBonusManager(param1);
            Global.hud.lockVisualEffectsForNextUpdate();
            this.conditionallyUpdateHUD();
            this.wishlist = param1.wishlist;
            if (param1.collections)
            {
                this.m_collections = param1.collections;
            }
            if (param1.completedCollections)
            {
                this.m_completedCollections = param1.completedCollections;
            }
            if (param1.collectionTradeIns)
            {
                this.m_collectionTradeIns = param1.collectionTradeIns;
            }
            _loc_6 = Global.gameSettings().getCollections();
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                _loc_12 = _loc_6.get(_loc_7);
                Collection.collectInfo(_loc_12);
            }
            if (param1.topFriends)
            {
                this.m_topFriends = param1.topFriends;
            }
            Global.world.citySim.neighborNavigationManager.onPlayerLoaded();
            if (param1.playerTimestamps)
            {
                this.m_playerTimestamps = param1.playerTimestamps;
            }
            ZBarNotifier.playerInitialized = true;
            this.m_friendRewards = param1.friendRewards ? (param1.friendRewards) : ({});
            if (param1.savedQuestSequence && param1.questManagerQuests)
            {
                _loc_13 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
                if (_loc_13 == ExperimentDefinitions.USE_QUEST_MANAGER)
                {
                    this.m_savedQuestSequence = param1.savedQuestSequence;
                    this.m_questManagerQuests = param1.questManagerQuests;
                    this.m_hiddenQuests = param1.hiddenQuests ? (param1.hiddenQuests) : ([]);
                }
            }
            if (param1.coupons)
            {
                this.m_coupons = param1.coupons;
            }
            if (param1.xpromos)
            {
                this.m_xPromoData = param1.xpromos;
            }
            if (param1.geoData)
            {
                this.m_geoData = param1.geoData;
                if (param1.hasOwnProperty("useOverrideCountry") && param1.useOverrideCountry && param1.hasOwnProperty("override_country"))
                {
                    this.m_geoData.put("country_code",  param1.override_country);
                }
            }
            this.m_lastSuperFeedPostError = param1.lastSuperFeedPostError;
            if (param1.purchasedSpecials)
            {
                this.purchasedSpecials = param1.purchasedSpecials;
            }
            if (param1.hasMadeEoQPurchase)
            {
                this.hasMadeEoQPurchase = param1.hasMadeEoQPurchase;
            }
            return;
        }//end

        public int  lastWarpTime ()
        {
            return this.m_lastWarpTime;
        }//end

        public int  lastActionTime ()
        {
            return this.m_lastActionTime;
        }//end

        public int  lastLapsedOfflineTime ()
        {
            return this.m_lastLapsedOfflineTime;
        }//end

        public String  currentTimestampEventID ()
        {
            return this.m_currentTimestamp_EventID;
        }//end

        public void  currentTimestampEventID (String param1 )
        {
            this.m_currentTimestamp_EventID = param1;
            return;
        }//end

        public String  previousTimestampEventID ()
        {
            return this.m_previousTimestamp_EventID;
        }//end

        public void  previousTimestampEventID (String param1 )
        {
            this.m_previousTimestamp_EventID = param1;
            return;
        }//end

        public void  addObserver (IPlayerStateObserver param1 )
        {
            if (!ArrayUtil.arrayContainsValue(this.m_stateObservers, param1))
            {
                this.m_stateObservers.push(param1);
            }
            return;
        }//end

        public void  removeObserver (IPlayerStateObserver param1 )
        {
            ArrayUtil.removeValueFromArray(this.m_stateObservers, param1);
            return;
        }//end

        public Object  getPlayerClass ()
        {
            classes = Global.gameSettings().getPlayerClasses();
            int _loc_3 =0;
            _loc_4 = classes;
            XMLList _loc_2 =new XMLList("");
            Object _loc_5;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);


                with (_loc_5)
                {
                    if (@type == this.playerClassType)
                    {
                        _loc_2.put(_loc_3++,  _loc_5);
                    }
                }
            }
            return _loc_2.get(0);
        }//end

        public Array  getPlayerNews ()
        {
            return ArrayUtil.copyArray(this.m_news);
        }//end

        public void  addSentGift (String param1 )
        {
            if (!this.m_sentGifts)
            {
                this.m_sentGifts = {};
            }
            this.m_sentGifts.put("i" + param1,  uint(GlobalEngine.getTimer() / 1000));
            return;
        }//end

        public boolean  isEligibleGiftRecipient (String param1 )
        {
            int _loc_4 =0;
            boolean _loc_2 =true ;
            _loc_3 = (Int)(GlobalEngine.getTimer ()/1000);
            if (this.m_sentGifts && this.m_sentGifts.get("i" + param1))
            {
                _loc_4 = _loc_3 - this.m_sentGifts.get("i" + param1);
                if (_loc_4 < Global.gameSettings().getInt("giftingPerUserTimeLimit"))
                {
                    _loc_2 = false;
                }
            }
            return _loc_2;
        }//end

        public Array  getActiveRequests (RequestType param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =new Array();
            this.purgeActiveRequests();
            if (this.m_activeRequests && this.m_activeRequests.get(param1.limitName) && this.m_activeRequests.get(param1.limitName).get("zids"))
            {
                for(int i0 = 0; i0 < this.m_activeRequests.get(param1.limitName).get("zids").size(); i0++)
                {
                		_loc_3 = this.m_activeRequests.get(param1.limitName).get("zids").get(i0);

                    _loc_2.push(_loc_3.substring(1));
                }
            }
            return _loc_2;
        }//end

        public void  addActiveRequests (RequestType param1 ,Array param2 )
        {
            String _loc_5 =null ;
            _loc_3 = int(GlobalEngine.getTimer()/1000);
            if (!this.m_activeRequests.get(param1.limitName))
            {
                this.m_activeRequests[param1.limitName] = {ts:_loc_3, zids:[]};
            }
            _loc_4 = Global.gameSettings().getInt("request_excludeList_size",150);
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_5 = param2.get(i0);

                if (this.m_activeRequests.get(param1.limitName).get("zids").length >= _loc_4)
                {
                    break;
                }
                this.m_activeRequests.get(param1.limitName).get("zids").push("i" + _loc_5);
            }
            return;
        }//end

        public void  purgeActiveRequests ()
        {
            int _loc_1 =0;
            Array _loc_2 =null ;
            String _loc_3 =null ;
            int _loc_4 =0;
            Object _loc_5 =null ;
            int _loc_6 =0;
            if (this.m_activeRequests)
            {
                _loc_1 = uint(GlobalEngine.getTimer() / 1000);
                _loc_2 = new Array();
                for(int i0 = 0; i0 < this.m_activeRequests.size(); i0++)
                {
                		_loc_3 = this.m_activeRequests.get(i0);

                    _loc_2.push(_loc_3);
                }
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_4 = Global.gameSettings().getInt(_loc_3 + "_resetTime") * 60 * 60;
                    _loc_5 = this.m_activeRequests.get(_loc_3);
                    _loc_6 = _loc_5.ts;
                    if (_loc_1 - _loc_6 >= _loc_4)
                    {
                        delete this.m_activeRequests.get(_loc_3);
                    }
                }
            }
            return;
        }//end

        public boolean  canSendGift (Item param1 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            boolean _loc_2 =false ;
            if (param1 && (param1.giftable || param1.specialGiftSendPermissions))
            {
                _loc_3 = this.level >= param1.requiredLevel;
                _loc_4 = this.maxNeighbors >= param1.requiredNeighbors;
                _loc_2 = _loc_3 && _loc_4;
            }
            return _loc_2;
        }//end

        public Array  getGifts ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            Array _loc_4 =null ;
            Item _loc_5 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_gifts.size(); i0++)
            {
            		_loc_2 = this.m_gifts.get(i0);

                _loc_3 = this.m_gifts.get(_loc_2);
                _loc_4 = _loc_3.split(",");
                if (_loc_4.get(0) && int(_loc_4.get(0)) > 0)
                {
                    _loc_5 = Global.gameSettings().getItemByCode(_loc_2);
                    _loc_1.push({item:_loc_5, num:_loc_4.get(0), senderArray:_loc_4.slice(1, _loc_4.length())});
                }
            }
            return _loc_1;
        }//end

        public double  lastXGameGiftSentTime ()
        {
            return this.m_lastXGameGiftSentTime;
        }//end

        public void  lastXGameGiftSentTime (double param1 )
        {
            this.m_lastXGameGiftSentTime = param1;
            return;
        }//end

        public int  getXGameLevelByGameId (int param1 )
        {
            int _loc_2 =0;
            if (this.m_xGameLevels.hasOwnProperty("game_" + param1))
            {
                _loc_2 = parseInt(this.m_xGameLevels.get("game_" + param1));
            }
            return _loc_2;
        }//end

        public Object  getReceivedXGameGiftsByGameId (int param1 )
        {
            Object _loc_2 ={};
            if (this.m_receivedXGameGifts && this.m_receivedXGameGifts.hasOwnProperty("game_" + param1))
            {
                _loc_2 = this.m_receivedXGameGifts.get("game_" + param1);
            }
            return _loc_2;
        }//end

        public Array  getAllPossibleReceivedXGameGiftsByGameId (int param1 )
        {
            Object _loc_4 =null ;
            _loc_2 = Global.gameSettings().getAllReceivableXGameGiftItemsByGameId(param1);
            Object _loc_3 ={};
            if (this.m_receivedXGameGifts && this.m_receivedXGameGifts.hasOwnProperty("game_" + param1))
            {
                _loc_3 = this.m_receivedXGameGifts.get("game_" + param1);
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                if (_loc_3.hasOwnProperty(_loc_4.get("name")))
                {
                    _loc_4.put("quantity",  _loc_3.get(_loc_4.get("name")));
                    continue;
                }
                _loc_4.put("quantity",  0);
            }
            return _loc_2;
        }//end

        public void  deleteReceivedXGameGiftsByGameId (int param1 )
        {
            if (this.m_receivedXGameGifts.hasOwnProperty("game_" + param1))
            {
                delete this.m_receivedXGameGifts.get("game_" + param1);
            }
            return;
        }//end

        public int  getGiftCount ()
        {
            _loc_1 = this.getGifts();
            _loc_2 = _loc_1.length;
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < _loc_2)
            {

                _loc_3 = _loc_3 + Number(_loc_1.get(_loc_4).num);
                _loc_4++;
            }
            return _loc_3;
        }//end

        public void  populatePromos (Object param1 )
        {
            Object _loc_2 =new Object ();
            if (param1 != null)
            {
                _loc_2 = param1;
            }
            this.m_promos = _loc_2;
            return;
        }//end

        public void  hasVisitFriend (double param1 )
        {
            this.m_hasVisitFriend = param1;
            return;
        }//end

        public double  hasVisitFriend ()
        {
            return this.m_hasVisitFriend;
        }//end

        public String  firstName ()
        {
            return this.snUser.firstName;
        }//end

        public void  level (int param1 )
        {
            this.conditionallyNotifyObservers(param1, this.m_xp, this.energy, this.m_gold, this.m_cash);
            this.m_level = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        private void  conditionallyNotifyObservers (int param1 ,int param2 ,double param3 ,int param4 ,int param5 )
        {
            IPlayerStateObserver _loc_11 =null ;
            _loc_6 = param1!= this.m_level;
            _loc_7 = param2!= this.m_xp;
            _loc_8 = param3!= this.energy;
            _loc_9 = param4!= this.m_gold;
            _loc_10 = param5!= this.m_cash;
            this.m_level = param1;
            for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
            {
            	_loc_11 = this.m_stateObservers.get(i0);

                if (_loc_6)
                {
                    _loc_11.levelChanged(param1);
                }
                if (_loc_7)
                {
                    _loc_11.xpChanged(param2);
                }
                if (_loc_8)
                {
                    _loc_11.energyChanged(param3);
                }
                if (_loc_9)
                {
                    _loc_11.goldChanged(param4);
                }
                if (_loc_10)
                {
                    _loc_11.cashChanged(param5);
                }
            }
            return;
        }//end

        public void  commodityNotifyObservers (String param1 )
        {
            IPlayerStateObserver _loc_2 =null ;
            if (Global.player.commodities)
            {
                for(int i0 = 0; i0 < this.m_stateObservers.size(); i0++)
                {
                	_loc_2 = this.m_stateObservers.get(i0);

                    _loc_2.commodityChanged(Global.player.commodities.getCount(param1), param1);
                }
            }
            return;
        }//end

        public int  neighbor_gate_quest_2_offset ()
        {
            return this.m_neighbor_gate_quest_2_offset;
        }//end

        public int  level ()
        {
            return this.m_level;
        }//end

        public boolean  canUsePaidEnergy ()
        {
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MEGA_BATTERY) == ExperimentDefinitions.MEGA_BATTERY_ENABLED)
            {
                return true;
            }
            return false;
        }//end

        public boolean  canDeductEnergy ()
        {
            boolean _loc_1 =true ;
            if (this.featureData != null && this.featureData.hasOwnProperty("energyEnableTimestamp"))
            {
                if (Number(this.featureData.get("energyEnableTimestamp")) > GlobalEngine.getTimer() / 1000)
                {
                    _loc_1 = false;
                }
                else
                {
                    delete this.featureData.get("energyEnableTimestamp");
                    this.conditionallyUpdateHUD();
                }
            }
            return _loc_1;
        }//end

        public double  getFreeEnergyTimeRemaining ()
        {
            double _loc_1 =0;
            if (this.featureData.get("energyEnableTimestamp"))
            {
                _loc_1 = Number(this.featureData.get("energyEnableTimestamp")) - GlobalEngine.getTimer() / 1000;
            }
            return _loc_1;
        }//end

        public void  setEnergyEnableTime (double param1 )
        {
            this.featureData.put("energyEnableTimestamp",  param1);
            this.conditionallyUpdateHUD();
            return;
        }//end

        public double  energy ()
        {
            _loc_1 = this.m_energy;
            if (this.canUsePaidEnergy())
            {
                _loc_1 = _loc_1 + this.m_paidEnergy;
            }
            return _loc_1;
        }//end

        public void  removeEnergyModifierByName (String param1 )
        {
            if (this.m_energyModifiers.get(param1) != undefined)
            {
                delete this.m_energyModifiers.get(param1);
            }
            return;
        }//end

        public double  getEnergyModifierValueByName (String param1 )
        {
            if (this.m_energyModifiers.get(param1) != undefined)
            {
                return this.m_energyModifiers.get(param1);
            }
            return 0;
        }//end

        public void  setEnergyModifierByName (String param1 ,double param2 )
        {
            this.m_energyModifiers.put(param1,  param2);
            return;
        }//end

        public double  getEnergyModifierTotal ()
        {
            Object _loc_3 =null ;
            double _loc_1 =0;
            int _loc_2 =0;
            if (Global.player == this)
            {
                _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_POWERSTATIONS);
            }
            if (_loc_2 == 0)
            {
                return 0;
            }
            for(int i0 = 0; i0 < this.m_energyModifiers.size(); i0++)
            {
            		_loc_3 = this.m_energyModifiers.get(i0);

                _loc_1 = _loc_1 + this.m_energyModifiers.get(_loc_3);
            }
            if (_loc_1 < 0)
            {
                _loc_1 = 0;
            }
            return _loc_1;
        }//end

        public double  energyMax ()
        {
            _loc_1 = this.getEnergyModifierTotal();
            return this.m_energyMax + _loc_1 + this.experimentalEnergyCapModifier;
        }//end

        public void  energyMax (double param1 )
        {
            this.m_energyMax = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public double  energyMaxBase ()
        {
            return this.m_energyMax;
        }//end

        public int  experimentalEnergyCapModifier ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ENERGY_CAP);
            switch(_loc_2)
            {
                case ExperimentDefinitions.ENERGY_CAP_HIGHER_1:
                case ExperimentDefinitions.ENERGY_CAP_HIGHER_2:
                {
                    _loc_1 = Global.gameSettings().getInt("energyCapModifier1");
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return _loc_1;
        }//end

        public double  expansionsPurchased ()
        {
            return this.m_expansionsPurchased;
        }//end

        public void  expansionsPurchased (double param1 )
        {
            this.m_expansionsPurchased = param1;
            return;
        }//end

        public double  expansionCostLevel ()
        {
            return this.m_expansionCostLevel;
        }//end

        public void  expansionCostLevel (double param1 )
        {
            this.m_expansionCostLevel = param1;
            return;
        }//end

        public int  maxNeighbors ()
        {
            return this.m_maxNeighbors;
        }//end

        public void  maxNeighbors (int param1 )
        {
            this.m_maxNeighbors = param1;
            return;
        }//end

        public int  franchiseUnlocksPurchased ()
        {
            return this.m_franchiseUnlocksPurchased;
        }//end

        public void  franchiseUnlocksPurchased (int param1 )
        {
            this.m_franchiseUnlocksPurchased = param1;
            return;
        }//end

        public int  franchiseMoneyReturned ()
        {
            return this.m_franchiseMoneyReturned;
        }//end

        public void  franchiseMoneyReturned (int param1 )
        {
            this.m_franchiseMoneyReturned = param1;
            return;
        }//end

        public int  populationObservedOnLastExpansion ()
        {
            return this.m_populationObservedOnLastExpansion;
        }//end

        public void  populationObservedOnLastExpansion (int param1 )
        {
            this.m_populationObservedOnLastExpansion = param1;
            return;
        }//end

        public int  getRegenerableResource (String param1 )
        {
            int _loc_2 =0;
            if (this.m_regenerableResources.hasOwnProperty(param1))
            {
                _loc_2 = parseInt(this.m_regenerableResources.get(param1));
            }
            return _loc_2;
        }//end

        public void  setRegenerableResource (String param1 ,int param2 )
        {
            this.m_regenerableResources.put(param1,  Math.max(0, param2));
            return;
        }//end

        public double  getLastRegenerableResourceCheck (String param1 )
        {
            if (this.m_regenerableLastUpdates.hasOwnProperty(param1))
            {
                return this.m_regenerableLastUpdates.get(param1);
            }
            return 0;
        }//end

        public void  setLastRegenerableResourceCheck (String param1 ,double param2 )
        {
            this.m_regenerableLastUpdates.put(param1,  param2);
            return;
        }//end

        public boolean  checkRegenerableResource (String param1 ,int param2 ,boolean param3 =true )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            GenericDialog _loc_8 =null ;
            _loc_4 = this.getRegenerableResource(param1)+param2;
            if (param3)
            {
                if (_loc_4 <= 0)
                {
                    _loc_5 = "OutOfGas";
                    _loc_6 = ZLoc.t("Dialogs", "OutOfGasFeedMessage");
                    _loc_7 = "assets/consumables/gas1.png";
                    _loc_8 = new GenericDialog(_loc_6, _loc_5, GenericDialogView.TYPE_OK, null, _loc_5, _loc_7);
                    UI.displayPopup(_loc_8, true, "OutOfGasDialog", true);
                }
            }
            return _loc_4 >= 0;
        }//end

        public boolean  isResourceAtSoftCap (String param1 )
        {
            _loc_2 = Global.gameSettings().getRegenerableResourceByName(param1);
            _loc_3 = this.getRegenerableResource(param1);
            if (_loc_3 >= _loc_2.softCap)
            {
                return true;
            }
            return false;
        }//end

        public boolean  updateRegenerableResource (String param1 ,int param2 )
        {
            RegenerableResource _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            if (this.checkRegenerableResource(param1, param2))
            {
                _loc_3 = Global.gameSettings().getRegenerableResourceByName(param1);
                _loc_4 = this.getRegenerableResource(param1);
                if (_loc_4 >= _loc_3.regenCap && param2 < 0)
                {
                    this.setLastRegenerableResourceCheck(param1, int(GlobalEngine.getTimer() / 1000));
                }
                this.setRegenerableResource(param1, Math.max(_loc_4 + param2, 0));
                this.conditionallyUpdateHUD();
                _loc_5 = this.getRegenerableResource(param1);
                if (_loc_5 <= 1 && _loc_4 > _loc_5)
                {
                    GameTransactionManager.addTransaction(new TUpdateRegenerableResource(param1), true);
                }
                return true;
            }
            return false;
        }//end

        public void  setRegenerableResourceFromServer (String param1 ,double param2 ,double param3 )
        {
            _loc_4 = this.getRegenerableResource(param1);
            this.updateRegenerableResource(param1, param2 - _loc_4);
            this.setLastRegenerableResourceCheck(param1, param3);
            this.conditionallyUpdateHUD();
            return;
        }//end

        public void  regenerateRegenerableResource (String param1 )
        {
            _loc_2 = Global.gameSettings().getRegenerableResourceByName(param1);
            _loc_3 = this.getRegenerableResource(param1);
            if (_loc_3 < _loc_2.regenCap)
            {
                this.updateRegenerableResource(param1, _loc_2.regenAmount);
                this.setLastRegenerableResourceCheck(param1, this.getLastRegenerableResourceCheck(param1) + _loc_2.regenInterval);
            }
            return;
        }//end

        public double  lastEnergyCheck ()
        {
            return this.m_lastEnergyCheck;
        }//end

        public void  lastEnergyCheck (double param1 )
        {
            this.m_lastEnergyCheck = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public void  setEnergyFromServer (double param1 ,double param2 ,double param3 )
        {
            this.conditionallyNotifyObservers(this.m_level, this.m_xp, param1, this.m_gold, this.m_cash);
            this.updateEnergy(param1 - this.energy, new Array("energy", "set_from_server", "", ""));
            this.m_energyMax = param2;
            this.m_lastEnergyCheck = param3;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public void  regenerateEnergy ()
        {
            if (this.energy < this.energyMax)
            {
                this.updateEnergy(Global.gameSettings().getNumber("energyRegenerationAmount"), new Array("energy", "earnings", "time_energy", ""));
                this.lastEnergyCheck = this.lastEnergyCheck + Global.gameSettings().getNumber("energyRegenerationSeconds");
            }
            return;
        }//end

        public double  visitorEnergy ()
        {
            return this.m_visitorEnergy;
        }//end

        public void  visitorEnergy (double param1 )
        {
            this.didVisitAction = false;
            this.m_visitorEnergy = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public double  socialXp ()
        {
            return this.m_socialXp;
        }//end

        public double  socialLevel ()
        {
            return this.m_socialLevel;
        }//end

        public void  socialLevel (double param1 )
        {
            this.m_socialLevel = param1;
            return;
        }//end

        public int  dailyVisits ()
        {
            return this.m_dailyVisits;
        }//end

        public void  dailyVisits (int param1 )
        {
            this.m_dailyVisits = param1;
            return;
        }//end

        public void  xp (int param1 )
        {
            XMLList _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_5 =false ;
            int _loc_6 =0;
            XML _loc_7 =null ;
            XML _loc_8 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            int _loc_11 =0;
            int _loc_12 =0;
            if (param1 > this.m_xp && this.m_xp > 0)
            {
                _loc_3 = Global.gameSettings().getLevelsXML().level;
                _loc_4 = this.level;
                if (param1 > 0)
                {
                    _loc_5 = false;
                    _loc_6 = 0;
                    while (_loc_6 < _loc_3.length())
                    {

                        _loc_7 = _loc_3.get(_loc_6);
                        if (param1 >= _loc_7.@requiredXP)
                        {
                            _loc_4 = _loc_6 + 1;
                            _loc_5 = true;
                        }
                        else
                        {
                            break;
                        }
                        _loc_6++;
                    }
                    if (_loc_5 && this.level < _loc_4 && _loc_4 > 1)
                    {
                        TransactionManager.sendAllTransactions(true);
                        UI.displayLevelUpDialog(_loc_4);
                        _loc_8 = Global.gameSettings().getLevelXML(_loc_4);
                        _loc_9 = _loc_8 != null ? (int(_loc_8.@energyMax)) : (0);
                        _loc_10 = Math.max(Global.player.energyMaxBase, _loc_9);
                        Global.player.energyMax = _loc_10;
                        if (Global.player.energy < Global.player.energyMax)
                        {
                            _loc_11 = Global.player.energy + Math.max(Global.player.energyMax - Global.player.energy - Global.player.heldEnergy, 0);
                            _loc_12 = _loc_11 - Global.player.energy;
                            Global.player.updateEnergy(_loc_12, new Array("energy", "earnings", "level_up", _loc_4));
                        }
                        Global.player.cash = Global.player.cash + Global.gameSettings().getInt("cashGainedPerLevel") * (_loc_4 - this.level);
                        this.level = _loc_4;
                    }
                }
            }
            this.conditionallyNotifyObservers(this.m_level, param1, this.energy, this.m_gold, this.m_cash);
            _loc_2 = Global.experimentManager.getVariant("cv_display_zbar");
            if (_loc_2 == ExperimentDefinitions.DISPLAY_ZBAR)
            {
                ZBarNotifier.awardXP(param1 - this.m_xp);
            }
            this.m_xp = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public String  levelUpNickName (int param1 )
        {
            _loc_2 = Global.gameSettings().getLevelsXML().level;
            _loc_3 =_loc_2.get((this.level -1)) ;
            _loc_4 = ZLoc.t("Levels","Level"+this.level);
            return ZLoc.t("Levels", "Level" + this.level);
        }//end

        public void  socialXp (double param1 )
        {
            XML _loc_2 =null ;
            XMLList _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            boolean _loc_7 =false ;
            int _loc_8 =0;
            XML _loc_9 =null ;
            if (param1 > this.m_socialXp && this.m_socialXp > 0)
            {
                _loc_2 = Global.gameSettings().getXML();
                _loc_3 = _loc_2.reputation.level;
                _loc_4 = this.socialLevel;
                _loc_5 = this.socialLevel;
                _loc_6 = 0;
                if (param1 > 0)
                {
                    _loc_7 = false;
                    _loc_8 = 0;
                    while (_loc_8 < _loc_3.length())
                    {

                        _loc_9 = _loc_3.get(_loc_8);
                        if (param1 >= _loc_9.@requiredXP)
                        {
                            _loc_4 = _loc_8 + 1;
                            _loc_7 = true;
                        }
                        else
                        {
                            break;
                        }
                        _loc_8++;
                    }
                    if (_loc_7 && this.socialLevel < _loc_4 && _loc_4 > 1)
                    {
                        TransactionManager.sendAllTransactions(true);
                        this.socialLevel = _loc_4;
                        if (SocialInventoryManager.isFeatureAvailable())
                        {
                            SocialInventoryManager.instance.setCapacitiesFromXml(_loc_4);
                            _loc_6 = _loc_3.get((_loc_4 - 1)).@heartCapacity - _loc_3.get((_loc_5 - 1)).@heartCapacity;
                        }
                        UI.displaySocialLevelUpDialog(this.m_socialLevel, _loc_6);
                    }
                }
            }
            this.m_socialXp = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public int  homeIslandSize ()
        {
            return this.m_homeIslandSize;
        }//end

        public void  homeIslandSize (int param1 )
        {
            this.m_homeIslandSize = param1;
            return;
        }//end

        public int  xp ()
        {
            return this.m_xp;
        }//end

        public void  gold (int param1 )
        {
            this.conditionallyNotifyObservers(this.m_level, this.m_xp, this.energy, param1, this.m_cash);
            this.m_gold = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public int  gold ()
        {
            return this.m_gold;
        }//end

        public void  cash (int param1 )
        {
            this.conditionallyNotifyObservers(this.m_level, this.m_xp, this.energy, this.m_gold, param1);
            this.m_cash = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public int  cash ()
        {
            return this.m_cash;
        }//end


        public void  kdbomb (int param1 )
        {
            //this.conditionallyNotifyObservers(this.m_level, this.m_xp, this.energy, this.m_gold, param1);
            this.m_kdbomb = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public int  kdbomb ()
        {
            return this.m_kdbomb;
        }//end

        public void  lrbomb (int param1 )
        {
            //this.conditionallyNotifyObservers(this.m_level, this.m_xp, this.energy, this.m_gold, param1);
            this.m_lrbomb = param1;
            this.conditionallyUpdateHUD();
            return;
        }//end

        public int  lrbomb ()
        {
            return this.m_lrbomb;
        }//end

        public void  pregold (int param1 )
        {
            this.m_pregold = param1;
            return;
        }//end

        public int  pregold ()
        {
            return this.m_pregold;
        }//end


        public Object  options ()
        {
            return this.m_options;
        }//end

        public void  options (Object param1 )
        {
            this.m_options = param1;
            this.saveOptionsCookie();
            Sounds.init();
            return;
        }//end

        public void  saveOptionsCookie ()
        {
            SharedObject options ;
            String name ;
            try
            {
                options = SharedObject.getLocal("options", "/");


                for(int i0 = 0; i0 < this.m_options.size(); i0++)
                {
                		name = this.m_options.get(i0);


                    options.setProperty(name, this.m_options.get(name));
                }
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        public void  addLicense (String param1 ,double param2 )
        {
            this.m_licenses.put(param1,  param2);
            return;
        }//end

        public void  deleteLicense (String param1 )
        {
            delete this.m_licenses.get(param1);
            return;
        }//end

        public Dictionary  licenses ()
        {
            return this.m_licenses;
        }//end

        public void  loadLicenses (Object param1 )
        {
            _loc_2 = undefined;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.addLicense(_loc_2, param1.get(_loc_2));
            }
            return;
        }//end

        protected String  getLicenseName (String param1 )
        {
            _loc_2 = param1;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 && _loc_3.licenseName)
            {
                _loc_2 = _loc_3.licenseName;
            }
            return _loc_2;
        }//end

        public void  acquireLicense (String param1 )
        {
            this.addLicense(this.getLicenseName(param1), 1);
            return;
        }//end

        public void  expireLicense (String param1 )
        {
            _loc_2 = this.getLicenseName(param1);
            if (this.checkLicense(_loc_2))
            {
                this.deleteLicense(_loc_2);
            }
            return;
        }//end

        public boolean  checkLicense (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.getLicenseName(param1);
            if (this.licenses && _loc_3)
            {
                _loc_2 = this.licenses.get(_loc_3) == 1;
            }
            return _loc_2;
        }//end

        public void  addSubLicense (String param1 ,String param2 )
        {
            _loc_3 = this.getSubLicenseKey(param1);
            this.m_sublicenses.put(param2,  this.m_sublicenses.get(param2) | 1 << _loc_3);
            return;
        }//end

        public int  getSubLicenseKey (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (_loc_3 && _loc_3.subLicenseID)
            {
                _loc_2 = _loc_3.subLicenseID;
            }
            return _loc_2;
        }//end

        public void  loadSubLicenses (Object param1 )
        {
            _loc_2 = undefined;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.m_sublicenses.put(_loc_2,  parseInt(param1.get(_loc_2), 16));
            }
            return;
        }//end

        public boolean  checkSubLicense (String param1 ,String param2 )
        {
            _loc_3 = this.getSubLicenseKey(param1);
            _loc_4 = this.m_sublicenses.get(param2);
            return (this.m_sublicenses.get(param2) & 1 << _loc_3) != 0;
        }//end

        public void  updateCitySamVisit ()
        {
            GameTransactionManager.addTransaction(new TUpdateCitySamVisit());
            return;
        }//end

        public boolean  getSeenFlag (String param1 )
        {
            return this.m_seenFlags.get(param1);
        }//end

        public void  setSeenFlag (String param1 ,boolean param2 =true )
        {
            if (this.m_seenFlags.get(param1) != true)
            {
                if (param2 == true)
                {
                    GameTransactionManager.addTransaction(new TSetSeenFlag(param1));
                }
                this.m_seenFlags.put(param1,  true);
            }
            return;
        }//end

        public int  countFlags ()
        {
            String _loc_1 =null ;
            int _loc_2 =0;
            for(int i0 = 0; i0 < this.m_seenFlags.size(); i0++)
            {
            		_loc_1 = this.m_seenFlags.get(i0);

                _loc_2++;
            }
            return _loc_2;
        }//end

        public int  countSeenFlags (String param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            for(int i0 = 0; i0 < this.m_seenFlags.size(); i0++)
            {
            		_loc_2 = this.m_seenFlags.get(i0);

                if (_loc_2.search(param1) >= 0)
                {
                    _loc_3++;
                }
            }
            for(int i0 = 0; i0 < this.m_seenSessionFlags.size(); i0++)
            {
            		_loc_2 = this.m_seenSessionFlags.get(i0);

                if (_loc_2.search(param1) >= 0)
                {
                    _loc_3++;
                }
            }
            return _loc_3;
        }//end

        public boolean  getSeenSessionFlag (String param1 )
        {
            return this.m_seenSessionFlags.get(param1);
        }//end

        public void  setSeenSessionFlag (String param1 )
        {
            this.m_seenSessionFlags.put(param1,  true);
            return;
        }//end

        public Array  getXPromoData (String param1 )
        {
            Array _loc_2 =new Array ();
            if (this.m_xPromoData.hasOwnProperty(param1))
            {
                _loc_2 = this.m_xPromoData.xPromoName;
            }
            return _loc_2;
        }//end

        public boolean  hasXpromoRewardPending (String param1 )
        {
            if (this.m_xPromoData.hasOwnProperty(param1))
            {
                return this.m_xPromoData.get(param1).reward_pending;
            }
            return false;
        }//end

        public void  setXPromoCompleted (String param1 )
        {
            this.m_xPromoData.get(param1).xpromo_completed = true;
            return;
        }//end

        public String  getGeoData (String param1 )
        {
            return this.m_geoData.get(param1);
        }//end

        public boolean  checkSeenExtendedPermissions (String param1 ,boolean param2 =true )
        {
            boolean _loc_3 =true ;
            String _loc_4 ="perm_"+param1 ;
            if (!this.getSeenFlag(_loc_4))
            {
                _loc_3 = false;
                if (param2)
                {
                    this.setSeenFlag(_loc_4);
                }
            }
            return _loc_3;
        }//end

        public int  numLimitedItems (String param1 )
        {
            return this.m_limitedItems.get(param1);
        }//end

        public boolean  hasLimitedItem (String param1 )
        {
            return this.m_limitedItems.get(param1) != undefined && this.m_limitedItems.get(param1) > 0;
        }//end

        public void  addLimitedItem (String param1 ,boolean param2 =true )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            if (this.m_limitedItems.get(param1) > 0)
            {
                (this.m_limitedItems.get(param1) + 1);
            }
            else
            {
                this.m_limitedItems.put(param1,  1);
            }
            if (param2)
            {
                _loc_3 = this.getItemsFromSameLimitGroup(param1);
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length())
                {

                    _loc_5 =(String) _loc_3.get(_loc_4);
                    this.addLimitedItem(_loc_5, false);
                    _loc_4++;
                }
            }
            return;
        }//end

        public void  removeLimitedItem (String param1 ,boolean param2 =true )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            if (this.m_limitedItems.get(param1) && this.m_limitedItems.get(param1) > 0)
            {
                (this.m_limitedItems.get(param1) - 1);
                if (param2)
                {
                    _loc_3 = this.getItemsFromSameLimitGroup(param1);
                    _loc_4 = 0;
                    while (_loc_4 < _loc_3.length())
                    {

                        _loc_5 =(String) _loc_3.get(_loc_4);
                        this.removeLimitedItem(_loc_5, false);
                        _loc_4++;
                    }
                }
            }
            return;
        }//end

        protected Array  getItemsFromSameLimitGroup (String param1 )
        {
            XMLList _loc_6 =null ;
            Array _loc_7 =null ;
            boolean _loc_8 =false ;
            int _loc_9 =0;
            XML _loc_10 =null ;
            String _loc_11 =null ;
            Array _loc_2 =new Array ();
            _loc_3 = Global.gameSettings().getXML();
            _loc_4 = _loc_3.limitItemOwnership.itemLimit;
            int _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                _loc_6 = _loc_4.get(_loc_5).item;
                _loc_7 = new Array();
                _loc_8 = false;
                _loc_9 = 0;
                while (_loc_9 < _loc_6.length())
                {

                    _loc_10 = _loc_6.get(_loc_9);
                    _loc_11 = _loc_10.@name;
                    if (_loc_11 == param1)
                    {
                        _loc_8 = true;
                    }
                    else
                    {
                        _loc_7.push(_loc_11);
                    }
                    _loc_9++;
                }
                if (_loc_8)
                {
                    _loc_2 = _loc_7;
                    break;
                }
                _loc_5++;
            }
            return _loc_2;
        }//end

        public void  checkGiftData (Object param1 ,String param2 )
        {
            _loc_3 =Global.gameSettings().getItemByCode(param2 );
            if (_loc_3 != null)
            {
                Global.player.gifts.put(param2,  String(param1));
            }
            return;
        }//end

        public void  unparsedNeighborsData (Array param1 )
        {
            this.m_unparsedNeighborsData = param1;
            return;
        }//end

        public Array  unparsedNeighborsData ()
        {
            return this.m_unparsedNeighborsData;
        }//end

        public void  neighbors (Array param1 )
        {
            this.m_neighbors = this.m_neighbors.concat(param1);
            Global.world.updateNeighborText();
            if (this.m_neighbors.length > this.m_maxNeighbors)
            {
                this.m_maxNeighbors = this.m_neighbors.length;
            }
            return;
        }//end

        public Array  neighbors ()
        {
            _loc_1 = Global.gameSettings().getInt("maxNeighbors");
            _loc_2 = this.m_neighbors.slice(0,_loc_1);
            return _loc_2;
        }//end

        public void  topFriends (Array param1 )
        {
            this.m_topFriends = param1;
            return;
        }//end

        public Array  topFriends ()
        {
            return this.m_topFriends;
        }//end

        public Object  getPromo (String param1 )
        {
            Object _loc_2 =null ;
            if (param1 != "" && this.m_promos && this.m_promos.get(param1))
            {
                _loc_2 = this.m_promos.get(param1);
            }
            return _loc_2;
        }//end

        public Object  getAllPromos ()
        {
            return this.m_promos;
        }//end

        public void  gifts (Object param1 )
        {
            this.m_gifts = param1;
            return;
        }//end

        public Object  gifts ()
        {
            return this.m_gifts;
        }//end

        public Inventory  inventory ()
        {
            return this.m_inventory;
        }//end

        public RequestInventoryComponent  requestInventory ()
        {
            return this.m_requestInventory;
        }//end

        public Commodities  commodities ()
        {
            return this.m_commodities;
        }//end

        public StorageComponent  storageComponent ()
        {
            return this.m_storageComponent;
        }//end

        public void  addGift (String param1 ,int param2 =0,boolean param3 =false )
        {
            _loc_4 = Global.gameSettings().getItemByName(param1);
            if (Global.gameSettings().getItemByName(param1) == null || this.checkItemLimit(param1) && !param3)
            {
                return;
            }
            this.inventory.addItems(param1, 1, param3);
            return;
        }//end

        public void  removeGift (MapResource param1 ,String param2)
        {
            throw new Error("Missing removeGift");
        }//end

        public boolean  hasGift (String param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(param1);
            if (this.m_gifts.get(_loc_2.code) != undefined)
            {
                return int(this.m_gifts.get(_loc_2.code)) > 0;
            }
            return false;
        }//end

        public void  removeAllGifts ()
        {
            this.m_gifts = new Dictionary();
            Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            return;
        }//end

        public void  conditionallyUpdateHUD ()
        {
            if (Global.hud)
            {
                Global.hud.gold = this.gold;
                Global.hud.cash = this.cash;
                Global.hud.xp = this.xp;
                Global.hud.energy = this.energy;
                Global.hud.updateCommodities();
                Global.hud.reputation = this.socialXp;
                Global.hud.visitEnergy = this.visitorEnergy;
                Global.hud.updateDebugText();

                Global.hud.kdbomb = this.kdbomb;
                Global.hud.lrbomb = this.lrbomb;
				Global.hud.level = this.level;

            }
            if (UI.m_catalog)
            {
                UI.m_catalog.updateChangedCells();
                UI.m_catalog.updateCoinsCash(this.gold, this.cash);
            }
            return;
        }//end

        public boolean  hasFriendsSNData ()
        {
            return this.m_hasFriendsSNData;
        }//end

        public void  setFriends (Array param1 )
        {
            int _loc_2 =0;
            SocialNetworkUser _loc_3 =null ;
            if (param1 != null)
            {
                this.m_friends = new Array();
                this.m_showMFI = new Dictionary();
                _loc_2 = 0;
                while (_loc_2 < param1.length())
                {

                    _loc_3 =(SocialNetworkUser) param1.get(_loc_2);
                    this.m_friends.push(new Player(_loc_3));
                    this.m_showMFI.put(_loc_3.uid,  true);
                    _loc_2++;
                }
                this.m_hasFriendsSNData = true;
            }
            return;
        }//end

        public boolean  hasMadePurchase ()
        {
            return this.m_hasMadePurchase;
        }//end

        public double  lastPurchaseDate ()
        {
            return this.m_lastPurchaseDate;
        }//end

        public void  setFakeFriend ()
        {
            this.m_friends.push(this.getFakeFriend());
            return;
        }//end

        public boolean  fake ()
        {
            return false;
            //return this.snUser.uid == FAKE_USER_ID;
        }//end

        public Player  getFakeFriend ()
        {
            Player _loc_1 =null ;
            SocialNetworkUser _loc_2 =null ;
            String _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_friends.size(); i0++)
            {
            	_loc_1 = this.m_friends.get(i0);

                if (_loc_1.fake)
                {
                    return _loc_1;
                }
            }
            _loc_2 = new SocialNetworkUser(FAKE_USER_ID);
            _loc_2.firstName = ZLoc.t("Main", "FakeFriendName");
            _loc_2.name = ZLoc.t("Main", "FakeFriendName");
            _loc_2.picture = Global.getAssetURL("assets/dialogs/citysam_neighbor_card.jpg");
            if (!Global.citySamNeighborCard)
            {
                _loc_3 = Global.gameSettings().getCitySamNeighborCard();
                if (_loc_3)
                {
                    Global.citySamNeighborCard = _loc_3;
                }
            }
            _loc_1 = new Player(_loc_2);
            _loc_1.cityName = ZLoc.t("Main", "FakeFriendCityName");
            return _loc_1;
        }//end

        public void  setFriendsNoSNData (Array param1 )
        {
            double _loc_2 =0;
            SocialNetworkUser _loc_3 =null ;
            this.m_friends = new Array();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = new SocialNetworkUser(_loc_2, Constants.NO_NETWORK);
                this.m_friends.push(new Player(_loc_3));
            }
            return;
        }//end

        public Array  friends ()
        {
            return this.m_friends;
        }//end

        public void  appFriends (Array param1 )
        {
            this.m_appFriends = param1;
            return;
        }//end

        public Array  appFriends ()
        {
            return this.m_appFriends;
        }//end

        public void  nonAppFriends (Array param1 )
        {
            this.m_nonAppFriends = param1;
            return;
        }//end

        public Array  nonAppFriends ()
        {
            return this.m_nonAppFriends;
        }//end

        public void  newPlayer (boolean param1 )
        {
            this.m_isNew = param1;
            return;
        }//end

        public int  creationTimestamp ()
        {
            return this.m_creationTimestamp;
        }//end

        public void  creationTimestamp (int param1 )
        {
            this.m_creationTimestamp = param1;
            return;
        }//end

        public boolean  isNewPlayer ()
        {
            return this.m_isNew;
        }//end

        public void  firstDay (boolean param1 )
        {
            this.m_isFirstDay = param1;
            return;
        }//end

        public boolean  isFirstDay ()
        {
            return this.m_isFirstDay;
        }//end

        public String  cityName ()
        {
            String _loc_1 =null ;
            if (!this.m_cityName)
            {
                _loc_1 = ZLoc.t("Main", "NameSuffix");
            }
            else
            {
                _loc_1 = this.m_cityName;
            }
            return _loc_1;
        }//end

        public void  cityName (String param1 )
        {
            this.m_cityName = param1;
            return;
        }//end

        public boolean  hasSetCityName ()
        {
            return this.m_cityName != null;
        }//end

        public String  pendingCityName ()
        {
            return this.m_pendingCityName;
        }//end

        public void  pendingCityName (String param1 )
        {
            this.m_pendingCityName = param1;
            return;
        }//end

        public Player  findFriendById (String param1 )
        {
            Player _loc_4 =null ;
            Player _loc_2 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.friends.length())
            {

                _loc_4 =(Player) this.friends.get(_loc_3);
                if (_loc_4.uid == param1)
                {
                    _loc_2 = _loc_4;
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        public boolean  isFriendIDInList (String param1 )
        {
            Player _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.friends.length())
            {

                _loc_3 =(Player) this.friends.get(_loc_2);
                if (_loc_3.uid == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public boolean  isFriendANeighbor (String param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.neighbors.size(); i0++)
            {
            	_loc_2 = this.neighbors.get(i0);

                if (_loc_2 == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

        public String  getFriendName (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.findFriendById(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.name;
            }
            return _loc_2;
        }//end

        public String  getFriendFirstName (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.findFriendById(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.firstName;
            }
            return _loc_2;
        }//end

        public String  getFriendGender (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.findFriendById(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.gender;
            }
            return _loc_2;
        }//end

        public void  setFriendCityName (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            _loc_4 = this.findFriendById(param1);
            if (this.findFriendById(param1))
            {
                _loc_4.cityName = param2;
            }
            return;
        }//end

        public String  getFriendCityName (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.findFriendById(param1);
            if (_loc_3)
            {
                _loc_2 = _loc_3.cityName;
            }
            boolean _loc_4 =false ;
            if (_loc_2 == null)
            {
                _loc_4 = true;
            }
            else if (_loc_2.length == 0)
            {
                _loc_4 = true;
            }
            if (_loc_4)
            {
                if (_loc_3)
                {
                    _loc_2 = ZLoc.t("Main", "FriendNameSuffix", {name:ZLoc.tn(_loc_3.firstName)});
                }
                else
                {
                    _loc_5 = ZLoc.t("Main","NameSuffix");
                    _loc_2 = ZLoc.t("Main", "NameSuffix");
                    _loc_2 = _loc_5;
                }
            }
            return _loc_2;
        }//end

        public boolean  canBuy (int param1 ,boolean param2 =true )
        {
            _loc_3 = this.gold>=param1;
            if (!_loc_3 && param2)
            {
                UI.flushDialogs();
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_COINS);
            }
            return _loc_3;
        }//end

        public boolean  canBuyCash (int param1 ,boolean param2 =true ,boolean param3 =true )
        {
            _loc_4 = this.cash-param1>=0;
            if (this.cash - param1 < 0 && param2)
            {
                if (param3)
                {
                    UI.flushDialogs();
                }
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return _loc_4;
        }//end

        public boolean  canBuyCommoditiy (int param1 ,String param2 ,boolean param3 =true )
        {
            _loc_4 = Global.player.commodities.getCount(param2)>=param1;
            if (Global.player.commodities.getCount(param2) < param1 && param3)
            {
                UI.flushDialogs();
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_COMMODITY);
            }
            return _loc_4;
        }//end

        public boolean  tryBuy (MapResource param1 ,boolean param2 =false ,Item param3 =null )
        {
            Item _loc_5 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            boolean _loc_4 =false ;
            _loc_5 = param3;
            if (_loc_5 == null)
            {
                _loc_5 = param1.getItem();
            }
            switch(_loc_5.market)
            {
                case Market.COST_CASH:
                {
                    _loc_9 = _loc_5.cash;
                    _loc_4 = Global.player.canBuyCash(_loc_9, param2 == false);
                    break;
                }
                case Market.COST_COINS:
                {
                }
                default:
                {
                    _loc_10 = _loc_5.cost;
                    _loc_4 = Global.player.canBuy(_loc_10, param2 == false);
                    break;
                    break;
                }
            }
            _loc_6 = _loc_5.cost;
            _loc_7 = Math.floor(_loc_6*Global.gameSettings().getNumber("buyXpGainRatio"));
            _loc_8 = Math.max(_loc_7,Global.gameSettings().getNumber("buyXpGainMin"));
            Global.player.xp = Global.player.xp + _loc_8;
            if (_loc_9)
            {
                Global.player.cash = Global.player.cash - _loc_9;
                if (param1 != null)
                {
                    param1.displayStatus(ZLoc.t("Main", "Status_BuyCash", {cash:_loc_9, experience:_loc_8}));
                }
            }
            else if (_loc_10)
            {
                Global.player.gold = Global.player.gold - _loc_10;
                if (param1 != null)
                {
                    param1.displayStatus(ZLoc.t("Main", "Status_BuyObject", {coins:_loc_10, experience:_loc_8}));
                }
            }
            return _loc_4;
        }//end

        public boolean  checkLevel (int param1 )
        {
            return this.level >= param1;
        }//end

        public boolean  checkNeighbors (int param1 )
        {
            return this.maxNeighbors >= param1;
        }//end

        public boolean  checkPopulation (int param1 ,String param2 )
        {
            return Global.world.citySim.getPopulation(param2) >= param1;
        }//end

        public boolean  checkItemLimit (String param1 )
        {
            return this.inventory.spareCapacity <= 0;
        }//end

        public boolean  checkGate (String param1 ,String param2 )
        {
            String _loc_6 =null ;
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            boolean _loc_10 =false ;
            boolean _loc_11 =false ;
            boolean _loc_12 =false ;
            boolean _loc_13 =false ;
            boolean _loc_3 =true ;
            _loc_4 = Global.gameSettings().getItemByName(param2);
            _loc_5 = Global.gameSettings().getItemByName(param2)!= null ? (_loc_4.xml) : (null);
            if (Global.gameSettings().getItemByName(param2) != null ? (_loc_4.xml) : (null))
            {
                _loc_6 = _loc_4.populationType;
                _loc_7 = this.checkLevel(_loc_5.requiredLevel);
                _loc_8 = this.checkNeighbors(_loc_5.minNeighbors);
                _loc_9 = ExpansionManager.instance.hasPassedExpansionGate();
                _loc_10 = Global.questManager.isFlagReached(_loc_4.requiredQuestFlag);

                //add by xinghai
                _loc_10 = true;

                _loc_11 = this.checkPopulation(_loc_4.requiredPopulation, _loc_6);
                _loc_12 = Global.world.getObjectsByNames(_loc_4.requiredObjects).length >= _loc_4.requiredObjectsCount;
                _loc_13 = Global.world.citySim.getPopulation(_loc_6) + _loc_4.requiredCanAddPopulation <= Global.world.citySim.getPopulationCap(_loc_6);
                switch(param1)
                {
                    case Item.UNLOCK_LOCKED:
                    {
                        _loc_3 = false;
                        break;
                    }
                    case Item.UNLOCK_LEVEL:
                    {
                        _loc_3 = _loc_7;
                        break;
                    }
                    case Item.UNLOCK_NEIGHBOR:
                    {
                        _loc_3 = _loc_8;
                        break;
                    }
                    case Item.UNLOCK_PERMITS:
                    {
                        if (_loc_4.requiredQuestFlag.length > 0)
                        {
                            _loc_3 = _loc_9 && _loc_10;
                        }
                        else
                        {
                            _loc_3 = _loc_9;
                        }
                        break;
                    }
                    case Item.UNLOCK_QUEST_FLAG:
                    case Item.UNLOCK_OLD_QUEST_OR_PURCHASE:
                    {
                        _loc_3 = _loc_10;
                        break;
                    }
                    case Item.UNLOCK_POPULATION:
                    {
                        _loc_3 = _loc_11;
                        break;
                    }
                    case Item.UNLOCK_QUEST_AND_LEVEL:
                    {
                        _loc_3 = _loc_10 && _loc_7;
                        break;
                    }
                    case Item.UNLOCK_QUEST_AND_NEIGHBOR:
                    {
                        _loc_3 = _loc_10 && _loc_8;
                        break;
                    }
                    case Item.UNLOCK_OBJECT_COUNT:
                    {
                        _loc_3 = _loc_12;
                        break;
                    }
                    case Item.UNLOCK_CAN_ADD_POPULATION:
                    {
                        _loc_3 = _loc_13;
                    }
                    default:
                    {
                        _loc_3 = _loc_7;
                        break;
                        break;
                    }
                }
            }
            if (this.checkLicense(param2))
            {
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        private void  updateEnergyValues (int param1 ,boolean param2 =false )
        {
            if (!this.canUsePaidEnergy())
            {
                this.m_energy = Math.max(this.m_energy + param1, 0);
            }
            else if (param1 > 0)
            {
                if (param2)
                {
                    this.m_paidEnergy = this.m_paidEnergy + param1;
                }
                else
                {
                    this.m_energy = this.m_energy + param1;
                }
            }
            else if (this.canDeductEnergy())
            {
                this.m_paidEnergy = this.m_paidEnergy + param1;
                if (this.m_paidEnergy < 0)
                {
                    this.m_energy = Math.max(this.m_energy + this.m_paidEnergy, 0);
                    this.m_paidEnergy = 0;
                }
            }
            return;
        }//end

        public boolean  updateEnergy (int param1 ,Array param2 ,boolean param3 =false )
        {
            int _loc_4 =0;
            int _loc_5 =0;
            String _loc_6 =null ;
            Array _loc_7 =null ;

            Debug.debug7("Player.updateEnergy");

            if (this.checkEnergy(param1))
            {
                _loc_4 = this.energy;
                _loc_5 = this.m_paidEnergy;
                this.conditionallyNotifyObservers(this.m_level, this.m_xp, this.energy + param1, this.m_gold, this.m_cash);
                if (this.canUsePaidEnergy())
                {
                    if (param3 == false && this.m_paidEnergy <= 0 && this.m_energy >= this.energyMax && param1 < 0)
                    {
                        this.m_lastEnergyCheck = int(GlobalEngine.getTimer() / 1000);
                    }
                }
                else if (this.m_energy >= this.energyMax && param1 < 0)
                {
                    this.m_lastEnergyCheck = int(GlobalEngine.getTimer() / 1000);
                }
                this.updateEnergyValues(param1, param3);
                this.conditionallyUpdateHUD();
                if (this.energy <= 1 && _loc_4 > this.energy || this.canUsePaidEnergy() && this.m_paidEnergy <= 1 && _loc_5 > this.m_paidEnergy)
                {
                    GameTransactionManager.addTransaction(new TUpdateEnergy(), true);
                }
                _loc_6 = GlobalEngine.socialNetwork.getLoggedInUserId();
                _loc_7 = RuntimeVariableManager.getString("ENERGY_TRACKING_WHITELIST", "").split(",");
                if (_loc_7 != null && _loc_7.indexOf(_loc_6) >= 0)
                {
                    if (param2 == null)
                    {
                        param2 = new Array("energy", "no_tracking_info", "", "");
                    }
                    StatsManager.count(param2.get(0) + "_client", param2.get(1), param2.get(2), param2.get(3), this.energy.toString(), param1.toString());
                }
                return true;
            }
            return false;
        }//end

        public boolean  checkEnergy (int param1 ,boolean param2 =true )
        {
            if (!this.canDeductEnergy())
            {
                return true;
            }
            _loc_3 = this.energy+param1;
            if (param2)
            {
                this.energyChangeHandler(_loc_3, this.m_energy);
            }
            if (_loc_3 >= 0)
            {
                return true;
            }
            return false;
        }//end

        private void  energyChangeHandler (int param1 ,int param2 )
        {
            if (param1 <= 0)
            {
                this.handleOutOfEnergy(param1, param2);
            }
            return;
        }//end

        public boolean  isNewEnergyAtMaxCapacity (int param1 )
        {
            _loc_2 = Global.gameSettings().getInt("energyCap");
            if (this.m_energy < _loc_2 + this.energyMax)
            {
                return false;
            }
            return true;
        }//end

        public int  returnNewEnergyInCaseOfOverflow (int param1 )
        {
            _loc_2 = Global.gameSettings().getInt("energyCap");
            if (this.m_energy < _loc_2 + this.energyMax)
            {
                return _loc_2 + this.energyMax;
            }
            return -1;
        }//end

        public boolean  checkForEnergyOverflow (int param1 ,boolean param2 =false )
        {
            int _loc_3 =0;
            if (this.canUsePaidEnergy() && param2)
            {
                _loc_3 = Global.gameSettings().getInt(PAID_ENERGY_CAP);
            }
            else
            {
                _loc_3 = this.energyMax + Global.gameSettings().getInt(FREE_ENERGY_CAP);
            }
            if (param1 > _loc_3)
            {
                return true;
            }
            return false;
        }//end

        private void  energyOverflowHandler (int param1 ,int param2 )
        {
            if (!this.checkForEnergyOverflow(param1))
            {
                return;
            }
            if (Global.player.returnNewEnergyInCaseOfOverflow(param1) == -1 && !this.m_handlingOutOfEnergy && param1 > param2)
            {
                this.showEnergyCanNotBeAddedDialog();
                return;
            }
            if (!this.m_handlingOutOfEnergy && param1 > param2)
            {
                this.showEnergyIsNowFull();
            }
            return;
        }//end

        public void  showEnergyCanNotBeAddedDialog ()
        {
            String _loc_2 =null ;
            GenericDialog _loc_3 =null ;
            String _loc_1 ="EnergyOverflow";
            _loc_2 = ZLoc.t("Dialogs", "EnergyOverflowWasFull");
            _loc_3 = new EnergyDialog(_loc_2, _loc_1, GenericDialogView.TYPE_OK, null, _loc_1, null, true, 0, "Okay");
            Global.ui.delayPopup(this.ENERGY_OVERFLOW_POPUP_DELAY, _loc_3, true, "energyOverflow", true);
            return;
        }//end

        public void  showEnergyIsNowFull ()
        {
            String _loc_2 =null ;
            GenericDialog _loc_3 =null ;
            String _loc_1 ="EnergyOverflow";
            _loc_2 = ZLoc.t("Dialogs", "EnergyOverflowNowFull");
            _loc_3 = new EnergyDialog(_loc_2, _loc_1, GenericDialogView.TYPE_OK, null, _loc_1, null, true, 0, "Okay");
            Global.ui.delayPopup(this.ENERGY_OVERFLOW_POPUP_DELAY, _loc_3, true, "energyOverflow", true);
            return;
        }//end

        private void  showNewUserOutOfEnergyPrompt ()
        {
            String _loc_2 =null ;
            GenericDialog _loc_3 =null ;
            String _loc_1 ="NewUserOutOfEnergy";
            _loc_2 = ZLoc.t("Dialogs", "NewUserOutOfEnergyMessage");
            _loc_3 = new EnergyDialog(_loc_2, _loc_1, GenericDialogView.TYPE_OK, this.outOfEnergyShowMarket, _loc_1, null, true, 0, "GetEnergy");
            Global.ui.delayPopup(this.OUT_OF_ENERGY_POPUP_DELAY, _loc_3, true, "newUserOutOfEnergy", true);
            return;
        }//end

        protected void  handleOutOfEnergy (int param1 ,int param2 )
        {
            GenericDialog _loc_3 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            GenericDialog _loc_8 =null ;
            if (param1 < 0)
            {
                this.outOfEnergyForceShowMarket(null);
                return;
            }
            if (param1 != 0)
            {
                return;
            }
            this.m_handlingOutOfEnergy = true;
            if (this.m_isNew)
            {
                this.showNewUserOutOfEnergyPrompt();
                return;
            }
            if (this.OOE_Coin == -1)
            {
                this.OOE_Coin = Math.floor(Math.random() * 2);
                if (this.OOE_Coin == this.COINFLIP_FEED)
                {
                    this.m_doOutOfEnergyFeed = false;
                    this.m_doOutOfEnergyRequest = true;
                }
                else
                {
                    this.m_doOutOfEnergyFeed = true;
                    this.m_doOutOfEnergyRequest = false;
                }
            }
            _loc_4 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OUTOFENERGY_COMBO);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OUTOFENERGY_COMBO) == 1)
            {
                if (this.m_doOutOfEnergyFeed)
                {
                    _loc_3 = new ComboEnergyDialog(this.outOfEnergyForceShowMarket, this.postOutOfEnergyFeed);
                }
                else
                {
                    _loc_3 = new ComboEnergyDialog(this.outOfEnergyForceShowMarket, this.sendOutOfEnergyRequest);
                }
            }
            else if (_loc_4 == 0)
            {
                this.m_outOfEnergyCount++;
                if (this.m_outOfEnergyCount == 2)
                {
                    if (this.m_dailyVisits < this.m_socialLevel)
                    {
                        _loc_5 = "OutOfEnergy";
                        _loc_6 = ZLoc.t("Dialogs", "OutOfEnergyVisitNeighborsMessage");
                        _loc_8 = new GenericDialog(_loc_6, _loc_5, GenericDialogView.TYPE_OK, this.outOfEnergyNeighborVisit, _loc_5, "assets/rewards/reward_energy.png");
                        Global.ui.delayPopup(this.OUT_OF_ENERGY_POPUP_DELAY, _loc_8, true, "visitPrompt", true);
                        return;
                    }
                }
                _loc_5 = "OutOfEnergy";
                _loc_7 = "assets/rewards/reward_energy.png";
                if (this.m_doOutOfEnergyFeed)
                {
                    StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "feed_prompted");
                    _loc_6 = ZLoc.t("Dialogs", "OutOfEnergyFeedMessage");
                    _loc_3 = new GenericDialog(_loc_6, _loc_5, GenericDialogView.TYPE_ASKFRIEND, this.postOutOfEnergyFeed, _loc_5, _loc_7, true);
                }
                else
                {
                    StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "request_prompted");
                    _loc_6 = ZLoc.t("Dialogs", "OutOfEnergyRequestMessage");
                    _loc_3 = new GenericDialog(_loc_6, _loc_5, GenericDialogView.TYPE_ASKFRIEND, this.sendOutOfEnergyRequest, _loc_5, _loc_7, true);
                }
            }
            this.m_doOutOfEnergyFeed = !this.m_doOutOfEnergyFeed;
            this.m_doOutOfEnergyRequest = !this.m_doOutOfEnergyRequest;
            Global.ui.delayPopup(this.OUT_OF_ENERGY_POPUP_DELAY, _loc_3, true, "outOfEnergy", true);
            return;
        }//end

        private void  outOfEnergyShowMarket (GenericPopupEvent event )
        {
            StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "buy_prompted");
            if (event && event.button == GenericDialogView.YES)
            {
                StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "buy_accepted");
                if (Global.world.getTopGameMode() instanceof GMRemodel)
                {
                    Global.world.addGameMode(new GMPlay(), true);
                }
                UI.displayCatalog(new CatalogParams("extras").setItemName("energy_2"), this.m_isNew, true);
            }
            this.m_handlingOutOfEnergy = false;
            return;
        }//end

        private void  outOfEnergyForceShowMarket (GenericPopupEvent event )
        {
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                Global.world.addGameMode(new GMPlay(), true);
            }
            UI.displayCatalog(new CatalogParams("energy").setItemName("energy_2").setExclusiveCategory(true).setOverrideTitle("extras"), this.m_isNew, true);
            this.m_handlingOutOfEnergy = false;
            return;
        }//end

        private void  postOutOfEnergyFeed (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES || event.button == GenericDialogView.YES_NOMARKET)
            {
                if (event.button == GenericDialogView.YES)
                {
                    StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "feed_posted");
                }
                Global.world.viralMgr.sendEnergyFeed(Global.player);
                this.m_handlingOutOfEnergy = false;
            }
            if (event.button != GenericDialogView.YES_NOMARKET)
            {
                this.buyEnergyInMarket();
            }
            return;
        }//end

        private void  sendOutOfEnergyRequest (GenericPopupEvent event )
        {
            if (event && event.button == GenericDialogView.YES || event.button == GenericDialogView.YES_NOMARKET)
            {
                if (event.button == GenericDialogView.YES)
                {
                    StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_DISABLED, "request_posted");
                }
                Global.world.viralMgr.sendEnergyRequest();
                this.m_handlingOutOfEnergy = false;
            }
            if (event.button != GenericDialogView.YES_NOMARKET)
            {
                this.buyEnergyInMarket();
            }
            return;
        }//end

        private void  outOfEnergyNeighborVisit (GenericPopupEvent event )
        {
            if (event && event.button != GenericDialogView.YES)
            {
                this.buyEnergyInMarket();
            }
            this.m_handlingOutOfEnergy = false;
            return;
        }//end

        public void  buyEnergyInMarket ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            EnergyDialog _loc_3 =null ;
            if (this.energy == 0)
            {
                _loc_1 = "OutOfEnergyVisitMarket";
                _loc_2 = ZLoc.t("Dialogs", "OutOfEnergyVisitMarketMessage");
                _loc_3 = new EnergyDialog(_loc_2, _loc_1, GenericDialogView.TYPE_OK, this.outOfEnergyShowMarket, _loc_1, null, true, 0, "GetEnergy");
                UI.displayPopup(_loc_3, true, "visitMarketPrompt", true);
            }
            else
            {
                this.m_handlingOutOfEnergy = false;
            }
            return;
        }//end

        public boolean  useVisitorEnergy (int param1 ,String param2 )
        {
            if (this.checkVisitorEnergy(param1))
            {
                this.m_visitorEnergy = this.m_visitorEnergy - param1;
                this.conditionallyUpdateHUD();
                this.checkIfNeedToTriggerVisitFeeds();
                this.didVisitAction = true;
                Global.ui.updateNeighborBar(Global.getVisiting());
                if (this.canEnterGMVisitBuy() && param2 != null)
                {
                    Global.world.addGameMode(new GMVisitBuy(param2));
                }
                return true;
            }
            return false;
        }//end

        public boolean  isVisitorEnergyZero ()
        {
            return this.m_visitorEnergy == 0;
        }//end

        public void  checkIfNeedToTriggerVisitFeeds ()
        {
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MFI);
            if (this.m_visitorEnergy == 4 && this.mutualFriendsDialog == null)
            {
                if (_loc_1 == 1)
                {
                    GameTransactionManager.addTransaction(new TRequestMutualFriends(Global.world.ownerId, false), true);
                }
            }
            else if (this.isVisitorEnergyZero())
            {
                if (_loc_1 == 1)
                {
                    if (this.mutualFriendsDialog == null)
                    {
                        GameTransactionManager.addTransaction(new TRequestMutualFriends(Global.world.ownerId, true), true);
                        this.mutualFriendsDialog = null;
                    }
                    else
                    {
                        UI.displayPopup(this.mutualFriendsDialog);
                        this.mutualFriendsDialog = null;
                    }
                    StatsManager.sample(10, StatsCounterType.PROMPTS, StatsKingdomType.MFI);
                }
                else
                {
                    NeighborVisitManager.triggerNeighborVisitFeeds();
                }
            }
            return;
        }//end

        public boolean  checkVisitorEnergy (int param1 )
        {
            return this.m_visitorEnergy >= param1;
        }//end

        public String  uid ()
        {
            return this.snUser.uid;
        }//end

        public void  giveRewards (Object param1 ,boolean param2 =false )
        {
            String _loc_3 =null ;
            Vector _loc_4.<FranchiseExpansionData >=null ;
            FranchiseExpansionData _loc_5 =null ;
            String _loc_6 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                switch(_loc_3)
                {
                    case "gold":
                    case "coins":
                    case "coin":
                    {
                        this.gold = this.gold + int(param1.get(_loc_3));
                        break;
                    }
                    case "cash":
                    {
                        this.cash = this.cash + int(param1.get(_loc_3));
                        break;
                    }
                    case "xp":
                    {
                        this.xp = this.xp + int(param1.get(_loc_3));
                        break;
                    }
                    case RegenerableResource.GAS:
                    {
                        this.updateRegenerableResource(RegenerableResource.GAS, int(param1.get(_loc_3)));
                        break;
                    }
                    case "energy":
                    {
                        this.updateEnergy(int(param1.get(_loc_3)), new Array("energy", "earnings", "quest_drop", ""));
                        break;
                    }
                    case "goods":
                    {
                        this.commodities.add("goods", int(param1.get(_loc_3)));
                        break;
                    }
                    case "premium_goods":
                    {
                        this.commodities.add("premium_goods", int(param1.get(_loc_3)));
                        break;
                    }
                    case "itemName":
                    case "item":
                    {
                        this.addGift(param1.get(_loc_3), 0, param2);
                        break;
                    }
                    case "collectable":
                    {
                        Collection.addCollectionToPlayer(param1.get(_loc_3));
                        break;
                    }
                    case "rep":
                    {
                        this.socialXp = this.socialXp + int(param1.get(_loc_3));
                        break;
                    }
                    case "itemUnlock":
                    {
                        this.setSeenSessionFlag(String(param1.get(_loc_3)));
                        if (UI.m_catalog)
                        {
                            UI.m_catalog.updateChangedCells();
                        }
                        break;
                    }
                    case "grantHQ":
                    {
                        break;
                    }
                    case "coupon":
                    {
                        this.giveCoupon(param1.get(_loc_3));
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

        public void  update ()
        {
            Global.hud.conditionallyRefreshHUD();
            return;
        }//end

        public double  GetDooberMinimums (Item param1 ,String param2 ,String param3 =null )
        {
            return param1.getDooberMinimums(param2, param3);
        }//end

        public Array  processRandomModifiers (Item param1 ,MapResource param2 ,boolean param3 ,Array param4 ,String param5 ="default",String param6 =null ,MapResource param7 =null )
        {
            _loc_8 = Player.GLOBAL_MODIFIERS_ENERGY_ACTION;
            Array _loc_9 =new Array ();
            _loc_9 = this.processRandomModifiersWithTable(param1, param2, param3, param4, param5, param6, param7, _loc_8);
            return _loc_9;
        }//end

        public Array  processRandomModifiersWithTable (Item param1 ,MapResource param2 ,boolean param3 ,Array param4 ,String param5 ="default",String param6 =null ,MapResource param7 =null ,String param8 =null )
        {
            double _loc_13 =0;
            double _loc_14 =0;
            double _loc_15 =0;
            double _loc_16 =0;
            XML _loc_17 =null ;
            XMLList _loc_18 =null ;
            XML _loc_19 =null ;
            Array _loc_20 =null ;
            String _loc_21 =null ;
            Array _loc_22 =null ;
            String _loc_23 =null ;
            XML _loc_24 =null ;
            XML _loc_25 =null ;
            Array _loc_9 =null ;
            Array _loc_10 =null ;
            if (param3 && param2 != null)
            {
                _loc_13 = ItemBonus.getBonusModifier(param2, ItemBonus.XP_YIELD);
                _loc_14 = ItemBonus.getBonusModifier(param2, ItemBonus.COIN_YIELD);
                _loc_15 = ItemBonus.getBonusModifier(param2, ItemBonus.GOODS_YIELD);
                _loc_16 = ItemBonus.getBonusModifier(param2, ItemBonus.PREMIUM_GOODS_YIELD);
                _loc_9 = .get(Doober.DOOBER_COIN, Doober.DOOBER_XP, Doober.DOOBER_GOODS, Doober.DOOBER_PREMIUM_GOODS);
                _loc_10 = .get(_loc_14, _loc_13, _loc_15, _loc_16);
            }
            _loc_11 = this.selectLocalRandomModifiers(param4,param1,param5,param6);
            if (param8)
            {
                _loc_17 = Global.gameSettings().getRandomModifierPackModifiers(param8);
                if (_loc_17 && _loc_17.length())
                {
                    if (!_loc_11)
                    {
                        _loc_11 = _loc_17;
                    }
                    else
                    {
                        _loc_18 = _loc_17.children();
                        for(int i0 = 0; i0 < _loc_18.size(); i0++)
                        {
                        		_loc_19 = _loc_18.get(i0);

                            _loc_11.appendChild(_loc_19);
                        }
                    }
                }
            }
            if (!(param2 instanceof ConstructionSite) && _loc_11)
            {
                _loc_11 = _loc_11.copy();
                _loc_20 = param1.getItemKeywords();
                for(int i0 = 0; i0 < _loc_20.size(); i0++)
                {
                	_loc_21 = _loc_20.get(i0);

                    _loc_22 = GlobalTableOverrideManager.instance.getGlobalTables(_loc_21);
                    for(int i0 = 0; i0 < _loc_22.size(); i0++)
                    {
                    	_loc_23 = _loc_22.get(i0);

                        _loc_24 = Global.gameSettings().getSpecificRandomModifierTable(_loc_23);
                        _loc_25 = new XML("<modifier type=\"toBeOverRidden\" tableName=\"toBeOverRidden\"/>");
                                       //("<modifier type="toBeOverRidden" tableName="toBeOverRidden"/>";
                        _loc_25.@tableName = _loc_24.@name;
                        _loc_25.@type = _loc_24.@type;
                        _loc_11.appendChild(_loc_25);
                    }
                }
            }
            Array _loc_12 =new Array ();
            if (_loc_11)
            {
                _loc_12 = this.processRandomModifiersFromConfig(_loc_11, param1, param4, _loc_9, _loc_10, param2, param7);
            }
            return _loc_12;
        }//end

        private XML  selectLocalRandomModifiers (Array param1 ,Item param2 ,String param3 ,String param4 )
        {
            XML _loc_5 =null ;
            if (param4)
            {
                _loc_5 = param2.getRandomModifiersXmlByName(param4);
            }
            else
            {
                if (param3 && param3 != "")
                {
                    if (param2.randomModifierGroupsXml != null)
                    {
                        if (param2.randomModifierGroupsXml.length() > 0)
                        {
                            _loc_5 = this.chooseRandomModifiersXml(param2, param2.randomModifierGroupsXml, param3, param1);
                        }
                    }
                }
                if (_loc_5 == null && param2.randomModifiersXml != null)
                {
                    _loc_5 = param2.randomModifiersXml;
                }
            }
            return _loc_5;
        }//end

        private XML  chooseRandomModifiersXml (Item param1 ,XMLList param2 ,String param3 ,Array param4 )
        {
            int rollRange ;
            String debugName ;
            int secureRand ;
            double runningTotalPercent ;
            String modifiersName ;
            XML modifiers ;
            XMLList randMods ;
            double rollPercent ;
            item = param1;
            XMLList randomModifierGroups =param2 ;
            String randomModifierGroupName =param3 ;
            secureRandsOut = param4;
            int _loc_7 =0;
            XMLList _loc_8 =randomModifierGroups ;
            XMLList _loc_6 =new XMLList("");
            Object _loc_9;
            for(int i0 = 0; i0 < _loc_8.size(); i0++)
            {
            	_loc_9 = _loc_8.get(i0);


                with (_loc_9)
                {
                    if (@name == randomModifierGroupName)
                    {
                        _loc_6.put(_loc_7++,  _loc_9);
                    }
                }
            }
            randomConfigGroup = ];
            rollRange;
            if (!randomConfigGroup)
            {
                return null;
            }
            debugName = item.name + (Global.isVisiting() ? ("/visit") : (""));
            secureRand = SecureRand.rand(0, rollRange, debugName);
            secureRandsOut.push(secureRand);
            if (this.m_randomModifierGroupOverride > -1)
            {
                secureRand = this.m_randomModifierGroupOverride;
            }
            runningTotalPercent = 0;
            modifiersName = "";
            int _loc6 =0;
            _loc7 = randomConfigGroup.modifiers;
            for(int i0 = 0; i0 < randomConfigGroup.modifiers.size(); i0++)
            {
            	modifiers = randomConfigGroup.modifiers.get(i0);


                rollPercent = parseFloat(modifiers.@percent);
                runningTotalPercent = runningTotalPercent + rollPercent;
                if (secureRand < runningTotalPercent)
                {
                    modifiersName = modifiers.@name;
                    break;
                }
            }
            randMods = item.randomModifiersXmlList;
            if (randMods.length() > 0 && randMods.hasOwnProperty("@name"))
            {
                _loc_7 = 0;
                _loc_8 = randMods;
                _loc_6 = new XMLList("");
                for(int i0 = 0; i0 < _loc_8.size(); i0++)
                {
                	_loc_9 = _loc_8.get(i0);


                    with (_loc_9)
                    {
                        if (@name == modifiersName)
                        {
                            _loc_6.put(_loc_7++,  _loc_9);
                        }
                    }
                }
                return _loc_6.get(0);
            }
            return null;
        }//end

        public Array  processRandomModifiersFromConfig (XML param1 ,Item param2 ,Array param3 ,Array param4 ,Array param5 ,MapResource param6 ,MapResource param7 =null )
        {
            XML _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            int _loc_13 =0;
            double _loc_14 =0;
            XML _loc_15 =null ;
            boolean _loc_16 =false ;
            double _loc_17 =0;
            double _loc_18 =0;
            int _loc_19 =0;
            XML _loc_20 =null ;
            GenericValidationScript _loc_21 =null ;
            String _loc_22 =null ;
            String _loc_23 =null ;
            Array _loc_24 =null ;
            String _loc_25 =null ;
            double _loc_26 =0;
            String _loc_27 =null ;
            String _loc_28 =null ;
            int _loc_29 =0;
            Class _loc_30 =null ;
            Function _loc_31 =null ;
            XML _loc_32 =null ;
            String _loc_33 =null ;
            double _loc_34 =0;
            String _loc_35 =null ;
            String _loc_36 =null ;
            _loc_37 = undefined;
            Class _loc_38 =null ;
            Function _loc_39 =null ;
            int _loc_40 =0;
            double _loc_41 =0;
            boolean _loc_42 =false ;
            int _loc_43 =0;
            String _loc_44 =null ;
            int _loc_45 =0;
            int _loc_46 =0;
            int _loc_47 =0;
            int _loc_48 =0;
            XML _loc_49 =null ;
            String _loc_50 =null ;
            Item _loc_51 =null ;
            XML _loc_52 =null ;
            int _loc_8 =99;
            Array _loc_9 =new Array ();
            if (!param7)
            {
                param7 = param6;
            }
            for(int i0 = 0; i0 < param1.modifier.size(); i0++)
            {
            	_loc_10 = param1.modifier.get(i0);

                _loc_11 = _loc_10.attribute("validate");
                if (_loc_11 && _loc_11.length > 0)
                {
                    _loc_21 = param2.getValidation(_loc_11);
                    if (_loc_21 && !_loc_21.validate(param2))
                    {
                        continue;
                    }
                }
                if (_loc_10.attribute("experimentName").length() > 0)
                {
                    _loc_22 = _loc_10.attribute("experimentName").toString();
                    _loc_23 = _loc_10.attribute("variants").toString();
                    _loc_24 = _loc_23.split(",");
                    _loc_25 = String(Global.experimentManager.getVariant(_loc_22));
                    if (_loc_24.indexOf(_loc_25) < 0)
                    {
                        continue;
                    }
                }
                _loc_12 = param2.name + (Global.isVisiting() ? ("/visit") : (""));
                _loc_13 = SecureRand.rand(0, _loc_8, _loc_12);
                param3.push(_loc_13);
                _loc_14 = 1;
                if (_loc_10.hasOwnProperty("@multiplier"))
                {
                    _loc_14 = Number(_loc_10.@multiplier);
                }
                if (this.m_randomModifierOverride > -1)
                {
                    _loc_13 = this.m_randomModifierOverride;
                }
                _loc_15 = Global.gameSettings().getSpecificRandomModifierTable(String(_loc_10.@tableName));
                if (!_loc_15)
                {
                    continue;
                }
                _loc_16 = false;
                _loc_17 = 0;
                _loc_18 = 0;
                _loc_19 = -1;
                for(int i0 = 0; i0 < _loc_15.roll.size(); i0++)
                {
                	_loc_20 = _loc_15.roll.get(i0);

                    _loc_19++;
                    if (_loc_20.@percent)
                    {
                        if (_loc_20.attribute("curve").length() > 0 && _loc_20.attribute("source").length() > 0 && param6 != null && !(param6 instanceof ConstructionSite))
                        {
                            _loc_27 = _loc_20.attribute("curve").toString();
                            _loc_28 = _loc_20.attribute("source").toString();
                            _loc_57 = param6;
                            _loc_29 = _loc_57.param6.get(_loc_28)();
                            _loc_30 =(Class) AlgebraicCurveManager;
                            _loc_31 =(Function) _loc_30.get(_loc_27);
                            if (_loc_31 != null)
                            {
                                _loc_18 = _loc_31(_loc_29) + _loc_17;
                            }
                        }
                        else
                        {
                            _loc_18 = parseFloat(_loc_20.@percent) + _loc_17;
                        }
                        _loc_26 = String(_loc_20.@divisor).length > 0 ? (Number(_loc_20.@divisor)) : (1);
                        _loc_17 = _loc_18;
                        if (_loc_13 < _loc_18 && !_loc_16)
                        {
                            for(int i0 = 0; i0 < _loc_20.children().size(); i0++)
                            {
                            		_loc_32 = _loc_20.children().get(i0);

                                _loc_33 = _loc_32.localName();
                                _loc_34 = Number(parseFloat(_loc_32.@amount)) / _loc_26;
                                if (_loc_32.attribute("curve").length() > 0 && _loc_32.attribute("source").length() > 0 && param6 != null && !(param6 instanceof ConstructionSite))
                                {
                                    _loc_35 = _loc_32.attribute("curve").toString();
                                    _loc_36 = _loc_32.attribute("source").toString();
                                    _loc_59 = param6;
                                    _loc_37 = _loc_59.param6.get(_loc_36)();
                                    _loc_38 =(Class) AlgebraicCurveManager;
                                    _loc_39 =(Function) _loc_38.get(_loc_35);
                                    if (_loc_39 != null)
                                    {
                                        _loc_34 = _loc_39(_loc_37) / _loc_26;
                                    }
                                }
                                if (param4 != null)
                                {
                                    _loc_40 = param4.indexOf(_loc_33);
                                    _loc_41 = 1;
                                    if (_loc_40 >= 0 && _loc_40 < param5.length())
                                    {
                                        _loc_41 = param5.get(_loc_40);
                                        _loc_34 = _loc_41 * _loc_34;
                                    }
                                }
                                if (_loc_33 != Doober.DOOBER_COLLECTABLE || _loc_33 == Doober.DOOBER_COLLECTABLE && !Global.guide.isActive())
                                {
                                    _loc_42 = false;
                                    switch(_loc_33)
                                    {
                                        case Doober.DOOBER_COLLECTABLE:
                                        {
                                            _loc_33 = _loc_32.@name;
                                            _loc_34 = 1;
                                            _loc_42 = true;
                                            break;
                                        }
                                        case Doober.DOOBER_POPULATION:
                                        {
                                            _loc_43 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BABY_DOOBERS_DROP);
                                            if (_loc_43 != _loc_34)
                                            {
                                                _loc_42 = false;
                                                break;
                                            }
                                            _loc_44 = param2.populationType;
                                            if (Global.world.citySim.getHappinessState(_loc_44) == CitySim.HAPPINESS_BAD)
                                            {
                                                _loc_42 = false;
                                                break;
                                            }
                                            _loc_45 = param2.populationMax;
                                            _loc_46 = Global.world.citySim.getPopulationCap(_loc_44) - Global.world.citySim.getPopulation(_loc_44);
                                            if (_loc_45 - param7.getPopulationYield() < _loc_46)
                                            {
                                                _loc_46 = _loc_45 - param7.getPopulationYield();
                                            }
                                            if (_loc_46 < 1)
                                            {
                                                _loc_42 = false;
                                                break;
                                            }
                                            _loc_47 = param2.populationBase;
                                            _loc_34 = 0;
                                            _loc_48 = 0;
                                            _loc_49 = Global.gameSettings().getXML().populationDropTable.get(0);
                                            for(int i0 = 0; i0 < _loc_49.children().size(); i0++)
                                            {
                                            	_loc_52 = _loc_49.children().get(i0);

                                                if (_loc_47 > parseInt(_loc_52.@populationBase))
                                                {
                                                    _loc_48 = _loc_52.@drop;
                                                    if (_loc_48 > _loc_46)
                                                    {
                                                        _loc_48 = _loc_46;
                                                        break;
                                                    }
                                                    _loc_34 = _loc_34 + 1;
                                                    continue;
                                                }
                                                break;
                                            }
                                            _loc_50 = "variant=" + _loc_43;
                                            StatsManager.sample(10, "doobers", "doober_creation", "client", Doober.DOOBER_POPULATION, _loc_48.toString(), _loc_50);
                                            param7.addPopulation(_loc_48);
                                            Global.world.citySim.recomputePopulation(Global.world, false);
                                            _loc_33 = Global.gameSettings().getDooberFromType(_loc_33, _loc_34);
                                            _loc_34 = _loc_48;
                                            _loc_42 = true;
                                            break;
                                        }
                                        case Doober.DOOBER_ITEM:
                                        {
                                            _loc_33 = _loc_32.@name;
                                            _loc_34 = 1;
                                            _loc_51 = Global.gameSettings().getItemByName(_loc_33);
                                            if (_loc_51 && _loc_51.getImageByName("initial"))
                                            {
                                                _loc_42 = true;
                                                this.inventory.addItems(_loc_33, 1);
                                            }
                                            else
                                            {
                                                this.inventory.addItems(_loc_33, 1);
                                                StatsManager.sample(100, StatsKingdomType.GAME_ACTIONS, "event", "reward", _loc_33);
                                                _loc_9.push(new Array(null, null, null, _loc_20, _loc_19));
                                            }
                                            break;
                                        }
                                        default:
                                        {
                                            _loc_34 = _loc_34 * _loc_14;
                                            _loc_33 = Global.gameSettings().getDooberFromType(_loc_33, Math.ceil(_loc_34));
                                            _loc_42 = true;
                                            break;
                                        }
                                    }
                                    if (_loc_42)
                                    {
                                        _loc_9.push(new Array(_loc_33, _loc_34, param6, _loc_20, _loc_19));
                                    }
                                }
                                _loc_16 = true;
                            }
                        }
                    }
                }
            }
            return _loc_9;
        }//end

        public void  forceServerStats ()
        {
            _loc_1 = TFarmTransaction.gamedata;
            if (_loc_1 == null)
            {
                return;
            }
            Global.player.gold = int(_loc_1.coins);
            Global.player.xp = int(_loc_1.xp);
            Global.player.rollCounter = int(_loc_1.roll);
            Global.player.updateEnergy(int(_loc_1.energy) - Global.player.energy, new Array("energy", "set_from_server", "", ""));
            return;
        }//end

        public int  rollCounter ()
        {
            return this.m_randomRollCounter;
        }//end

        public void  rollCounter (int param1 )
        {
            this.m_randomRollCounter = param1;
            return;
        }//end

        public Dictionary  rollCounterMap ()
        {
            return this.m_randomRollCounterMap;
        }//end

        public void  setRollCounterValue (String param1 ,int param2 )
        {
            this.m_randomRollCounterMap.put(param1,  param2);
            return;
        }//end

        public boolean  doesFlagExist (String param1 )
        {
            return this.m_flagContainer && this.m_flagContainer.hasOwnProperty(param1);
        }//end

        public Flag  getFlag (String param1 )
        {
            _loc_2 = this.m_flagContainer.get(param1);
            if (!_loc_2)
            {
                _loc_2 = new Flag(param1);
                this.m_flagContainer.put(param1,  _loc_2);
            }
            return _loc_2;
        }//end

        public void  setFlag (String param1 ,double param2 )
        {
            _loc_3 = this.m_flagContainer.get(param1);
            if (!_loc_3)
            {
                _loc_3 = new Flag(param1);
                this.m_flagContainer.put(param1,  _loc_3);
            }
            _loc_3.value = param2;
            return;
        }//end

        public boolean  allowQuests ()
        {
            return this.m_allowQuests;
        }//end

        public void  setAllowQuests (boolean param1 )
        {
            this.m_allowQuests = param1;
            return;
        }//end

        public Object  collections ()
        {
            return this.m_collections;
        }//end

        public int  getNumCollectablesOwned (String param1 )
        {
            _loc_2 = Global.gameSettings().getCollectionByCollectableName(param1).name;
            if (this.m_collections.hasOwnProperty(_loc_2) && this.m_collections.get(_loc_2).hasOwnProperty(param1))
            {
                return this.m_collections.get(_loc_2).get(param1);
            }
            return 0;
        }//end

        public void  removeCollectable (String param1 )
        {
            _loc_2 = Global.gameSettings().getCollectionByCollectableName(param1).name;
            if (this.m_collections.hasOwnProperty(_loc_2) && this.m_collections.get(_loc_2).hasOwnProperty(param1))
            {
                (this.m_collections.get(_loc_2).get(param1) - 1);
                if (this.m_collections.get(_loc_2).get(param1) <= 0)
                {
                    delete this.m_collections.get(_loc_2).get(param1);
                }
            }
            return;
        }//end

        public void  addCollectable (String param1 )
        {
            _loc_2 = Global.gameSettings().getCollectionByCollectableName(param1).name;
            if (!this.m_collections.hasOwnProperty(_loc_2))
            {
                this.m_collections.put(_loc_2,  new Object());
            }
            if (this.m_collections.get(_loc_2).get(param1))
            {
                (this.m_collections.get(_loc_2).get(param1) + 1);
            }
            else
            {
                this.m_collections.get(_loc_2).put(param1,  1);
            }
            return;
        }//end

        public void  trackTradeIns (String param1 )
        {
            if (!this.m_collectionTradeIns.hasOwnProperty(param1))
            {
                this.m_collectionTradeIns.put(param1,  0);
            }
            _loc_2 = this.m_collectionTradeIns;
            _loc_3 = param1;
            _loc_4 = this.m_collectionTradeIns.get(param1)+1;
            _loc_2.put(_loc_3,  _loc_4);
            return;
        }//end

        public int  numTradeIns (String param1 )
        {
            return this.m_collectionTradeIns.get(param1) ? (this.m_collectionTradeIns.get(param1)) : (0);
        }//end

        public int  getMasteryLevel (String param1 )
        {
            int _loc_2 =0;
            if (this.m_completedCollections.get(param1) != null)
            {
                _loc_2 = this.m_completedCollections.get(param1);
            }
            return _loc_2;
        }//end

        public void  completeCollection (String param1 )
        {
            if (this.m_completedCollections.get(param1) == null)
            {
                this.m_completedCollections.put(param1,  1);
            }
            else
            {
                _loc_2 = this.m_completedCollections;
                _loc_3 = param1;
                _loc_4 = this.m_completedCollections.get(param1)+1;
                _loc_2.put(_loc_3,  _loc_4);
            }
            return;
        }//end

        public boolean  hasCompletedCollection (String param1 )
        {
            return this.m_completedCollections.get(param1) > 0;
        }//end

        public void  addToWishlist (String param1 )
        {
            if (this.isItemOnWishlist(param1) == false)
            {
                this.wishlist.push(param1);
            }
            return;
        }//end

        public void  removeFromWishlist (String param1 )
        {
            _loc_2 = this.wishlist.indexOf(param1);
            if (_loc_2 >= 0)
            {
                this.wishlist.splice(_loc_2, 1);
            }
            return;
        }//end

        public boolean  canAddToWishlist (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = Global.gameSettings().getItemByName(param1);
            if (!_loc_3.playerLocked)
            {
                _loc_2 = !this.isItemOnWishlist(param1) && !this.isWishlistFull();
            }
            return _loc_2;
        }//end

        public boolean  isItemOnWishlist (String param1 )
        {
            return this.wishlist.indexOf(param1) >= 0;
        }//end

        public boolean  isWishlistFull ()
        {
            return this.wishlist.length >= Global.gameSettings().getInt("wishlistSize", 5);
        }//end

        public boolean  isMusicDisabled ()
        {
            return false;

			if (!this.m_options.hasOwnProperty("musicDisabled"))
            {
                return true;
            }
            return this.m_options.musicDisabled;
        }//end

        public Array  getTradeableCollections ()
        {
            XML _loc_2 =null ;
            Collection _loc_3 =null ;
            Array _loc_1 =new Array();
            for(int i0 = 0; i0 < Global.gameSettings().getCollections().size(); i0++)
            {
            		_loc_2 = Global.gameSettings().getCollections().get(i0);

                _loc_3 = new Collection(_loc_2);
                if (_loc_3.getCurrentUniqueCollectablesCount() == _loc_3.getTotalCollectablesCount())
                {
                    _loc_1.push(_loc_3);
                }
            }
            return _loc_1;
        }//end

        public boolean  isEligibleForBusinessUpgrades ()
        {
            return this.level >= Global.gameSettings().getInt("businessUpgradesRequiredLevel", 15) && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) > 0;
        }//end

        public boolean  isEligibleForMastery (Item param1 ,boolean param2 =true )
        {
            boolean _loc_3 =false ;
            if (param1.type == "plot_contract")
            {
                _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PLOT_MASTERY) == ExperimentDefinitions.PLOT_MASTERY_ENABLED;
            }
            if (param2)
            {
                _loc_3 = _loc_3 && this.level >= Global.gameSettings().getInt("masteryRequiredLevel", 15);
            }
            return _loc_3;
        }//end

        public int  getLastActivationTime (String param1 )
        {
            param1 = param1.slice(0, 25);
            if (this.m_playerTimestamps.get(param1))
            {
                return this.m_playerTimestamps.get(param1);
            }
            return -1;
        }//end

        public void  setLastActivationTime (String param1 ,int param2 ,boolean param3 =false ,boolean param4 =false )
        {
            param1 = param1.slice(0, 25);
            this.m_playerTimestamps.put(param1,  param2);
            if (!param3)
            {
                GameTransactionManager.addTransaction(new TUpdatePlayerTimestamp(param1), param4);
            }
            return;
        }//end

        public double  heldEnergy ()
        {
            return this.m_heldEnergy;
        }//end

        public void  heldEnergy (double param1 )
        {
            this.m_heldEnergy = param1;
            return;
        }//end

        public int  compensationFlag ()
        {
            return this.m_compensationFlag;
        }//end

        public void  compensationFlag (int param1 )
        {
            this.m_compensationFlag = param1;
            return;
        }//end

        public Array  savedQuestSequence ()
        {
            return this.m_savedQuestSequence;
        }//end

        public Array  questManagerQuests ()
        {
            return this.m_questManagerQuests;
        }//end

        public Array  hiddenQuests ()
        {
            return this.m_hiddenQuests;
        }//end

        public void  saveQuestOrderSequence ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER );
            if (_loc_1 == ExperimentDefinitions.NO_QUEST_MANAGER)
            {
                return;
            }
            this.m_savedQuestSequence = new Array();
            _loc_2 = (HUDQuestBarComponent)Global.hud.getComponent(HUD.COMP_QUESTS)
            int _loc_3 =0;
            while (_loc_3 < _loc_2.questNames.length())
            {

                this.m_savedQuestSequence.put(_loc_3,  _loc_2.questNames.get(_loc_3));
                _loc_3++;
            }
            this.m_questManagerQuests = new Array();
            _loc_3 = 0;
            while (_loc_3 < UI.questManagerView.content.activeModel.size())
            {

                this.m_questManagerQuests.put(_loc_3,  ((QuestManagerSprite)UI.questManagerView.content.activeModel.getElementAt(_loc_3)).name);
                _loc_3++;
            }
            this.m_hiddenQuests = new Array();
            _loc_3 = 0;
            while (_loc_3 < UI.questManagerView.content.hiddenModel.size())
            {

                this.m_hiddenQuests.put(_loc_3,  ((QuestManagerSprite)UI.questManagerView.content.hiddenModel.getElementAt(_loc_3)).name);
                _loc_3++;
            }
            TransactionManager.addTransaction(new TUpdateSavedQuestOrder(this.m_savedQuestSequence, this.m_questManagerQuests, this.m_hiddenQuests));
            return;
        }//end

        public int  getSavedQuestSlot (String param1 )
        {
            return this.m_savedQuestSequence.indexOf(param1);
        }//end

        public int  getQuestManagerSlot (String param1 )
        {
            return this.m_questManagerQuests.indexOf(param1);
        }//end

        public int  getHiddenQuestSlot (String param1 )
        {
            return this.m_hiddenQuests.indexOf(param1);
        }//end

        public void  numQuestsLoading (int param1 )
        {
            this.m_numQuestsLoading = param1;
            return;
        }//end

        public int  numQuestsLoading ()
        {
            return this.m_numQuestsLoading;
        }//end

        public boolean  isPremiumGoodsActive ()
        {
            if (Global.questManager == null)
            {
                return false;
            }
            if (Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_PREMIUM_GOODS_UNLOCKED))
            {
                return true;
            }
            if (Factory.isFactoryInPlayerWorld())
            {
                return true;
            }
            return false;
        }//end

        public boolean  getShowMFIByID (String param1 )
        {
            return this.m_showMFI.get(param1);
        }//end

        public void  setShowMFIByID (String param1 ,boolean param2 )
        {
            this.m_showMFI.put(param1,  param2);
            return;
        }//end

        public Dictionary  MFIDict ()
        {
            return this.m_showMFI;
        }//end

        public boolean  npc_cloud_visible ()
        {
            return this.m_npc_cloud_visible;
        }//end

        public boolean  allowRightSideBuild ()
        {
            return Global.player.getFlag("completed_bridge").value == 1;
        }//end

        public void  setAllowRightSideBuild ()
        {
            Global.player.setFlag("completed_bridge", 1);
            return;
        }//end

        public Object  friendRewards ()
        {
            return this.m_friendRewards;
        }//end

        public boolean  isQuestHidden (String param1 )
        {
            return this.m_hiddenQuests.indexOf(param1) >= 0;
        }//end

        public boolean  isQuestNew (String param1 )
        {
            return !(this.m_hiddenQuests.indexOf(param1) >= 0 || this.m_questManagerQuests.indexOf(param1) >= 0 || this.m_savedQuestSequence.indexOf(param1) >= 0);
        }//end

        public int  currLogin ()
        {
            return this.m_currLogin;
        }//end

        public int  lastLogin ()
        {
            return this.m_lastLogin;
        }//end

        public boolean  hasCoupon (String param1 )
        {
            return this.m_coupons.indexOf(param1) >= 0;
        }//end

        public void  useCoupon (String param1 )
        {
            _loc_2 = this.m_coupons.indexOf(param1);
            if (_loc_2 >= 0)
            {
                this.m_coupons.splice(_loc_2, 1);
            }
            return;
        }//end

        public void  giveCoupon (String param1 )
        {
            if (this.m_coupons.indexOf(param1) < 0)
            {
                this.m_coupons.push(param1);
            }
            return;
        }//end

        public boolean  hasExtendedPermissions ()
        {
            return this.m_hasExtendedPermissions;
        }//end

        public void  hasExtendedPermissions (boolean param1 )
        {
            this.m_hasExtendedPermissions = param1;
            return;
        }//end

        public boolean  canEnterGMVisitBuy ()
        {
            if (Global.world.isOwnerCitySam && this.isVisitorEnergyZero())
            {
                return true;
            }
            return false;
        }//end

        public double  visitedCitySamVersion ()
        {
            return this.m_visitedCitySamVersion;
        }//end

    }




