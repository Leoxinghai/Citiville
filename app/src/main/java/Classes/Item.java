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

import Classes.bonus.*;
import Classes.doobers.*;
import Classes.sim.*;
import Classes.util.*;
import Engine.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.goals.mastery.*;
import Modules.remodel.*;
import Modules.sale.market.*;
import ZLocalization.*;

//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import validation.*;
import com.xinghai.Debug;

    public class Item extends GameSettingsObject
    {
        protected Array m_cachedImages ;
        protected Array m_collisionData ;
        protected ItemGroup m_set ;
        protected Array m_bonuses ;
        protected Array m_positiveStreakRewards ;
        protected Array m_negativeStreakRewards ;
        protected int m_positiveStreakMaxEffect =0;
        protected Dictionary m_tickers =null ;
        protected Dictionary m_toasters =null ;
        protected Dictionary m_validations ;
        protected String m_localizedName ;
        protected String m_code ;
        protected double m_noAutoScale ;
        protected boolean m_noAutoRotate ;
        protected double m_imageScale ;
        public boolean m_buyable ;
        protected String m_minigame ;
        protected String m_description ;
        protected Dictionary m_includeExperiments ;
        protected Dictionary m_excludeExperiments ;
        public boolean m_noMarket ;
        protected boolean m_placeable ;
        protected boolean m_requestOnly ;
        protected String m_special_onclick ;
        protected boolean m_persistent ;
        protected boolean m_consumable ;
        protected double m_firstTimeBonus ;
        protected boolean m_giftable ;
        protected boolean m_specialGiftSendPermissions ;
        protected boolean m_newTabPriority ;
        protected boolean m_sellSendsToInventory ;
        protected String m_sellSendsToInventoryMessage ;
        protected IMarketSale m_sale ;
        protected int m_cost ;
        protected int m_cash ;
        protected int m_goods ;
        protected int m_fbc ;
        protected int m_level ;
        protected int m_requiredLevel ;
        protected String m_unlockRequirements ;
        protected int m_dailyCollect ;
        protected String m_type ;
        protected String m_subtype ;
        protected String m_behavior ;
        protected Array m_category ;
        protected boolean m_isNew ;
        protected String m_className ;
        protected String m_unlock ;
        protected int m_unlockCostCash ;
        protected int m_unlockCostCoin ;
        protected String m_licenseName ;
        protected String m_unlockShareFunc ;
        protected String m_unlockedCostString ;
        protected String m_unlockTipText ;
        protected boolean m_enabledWhenLocked ;
        protected int m_requiredPopulation ;
        protected int m_requiredNeighbors ;
        protected String m_requiredQuestFlag ;
        protected String m_requiredSeenFlag ;
        protected String m_requiredSeenFlagText ;
        protected String m_requiredObjects ;
        protected int m_requiredObjectsCount ;
        protected int m_requiredCanAddPopulation ;
        protected int m_count ;
        protected int m_coinYield ;
        protected int m_xpYield ;
        protected int m_remodelXp ;
        protected int m_quantity ;
        protected String m_market ;
        protected boolean m_multiplace ;
        protected boolean m_multiplant ;
        protected boolean m_useTileHighlight ;
        protected boolean m_useTilePicking ;
        protected String m_construction ;
        protected int m_truckNum ;
        protected int m_harvestEnergyCost ;
        protected int m_buildEnergyCost ;
        protected int m_cleanEnergyCost ;
        protected int m_plantEnergyCost ;
        protected int m_clearEnergyCost ;
        protected int m_unlockMovementEnergyCost ;
        protected int m_openEnergyCost ;
        protected int m_instantFinishEnergyCost ;
        protected int m_clearXpReward ;
        protected int m_clearGoldReward ;
        protected double m_growTime ;
        protected double m_buildTime ;
        protected boolean m_allowWither ;
        protected int m_sizeX ;
        protected int m_sizeY ;
        protected double m_startDate =0;
        protected double m_endDate =0;
        protected boolean m_showTimer =false ;
        protected double m_imageOffsetX ;
        protected double m_imageOffsetY ;
        protected int m_peoplePerHarvest ;
        protected int m_peoplePerPlant ;
        protected int m_actionsPerPerson ;
        protected int m_size ;
        protected boolean m_ownable ;
        protected Array m_commodities ;
        protected double m_maxPlots ;
        protected double m_bonusPercent ;
        protected double m_waterLength ;
        protected String m_landSquares ;
        protected String m_dockSquares ;
        protected String m_berthSquares ;
        protected String m_freeShipSquares ;
        protected String m_freeShipType ;
        protected String m_emptyNPCType ;
        protected String m_loadedNPCType ;
        protected String m_requiredShip ;
        protected double m_energyRewards ;
        protected Dictionary m_regenerableRewards ;
        protected double m_cashRewards ;
        protected double m_coinRewards ;
        protected double m_goodsReward ;
        protected Array m_itemRewards ;
        protected Array m_randomRewards ;
        protected int m_foodConsumption ;
        protected int m_goodsConsumption ;
        protected String m_populationType =null ;
        protected String m_populationTypeTuned =null ;
        protected String m_moveInVehicle ;
        protected double m_populationBase ;
        protected double m_populationMax ;
        protected double m_populationCapYield ;
        protected String m_headquartersName ;
        protected double m_heightLimit ;
        protected boolean m_canFranchise ;
        protected String m_clickSound ;
        protected double m_commodityReq ;
        protected int m_trainTripTime ;
        protected int m_trainStorage ;
        protected int m_trainMinStops ;
        protected int m_trainSpeedUpCost ;
        protected double m_trainBuyPrice ;
        protected double m_trainSellPrice ;
        protected String m_trainDestination ;
        protected String m_trainPayout ;
        protected String m_validGiftTimeRangeStart ;
        protected Dictionary m_themeListeners ;
        protected String m_validGiftTimeRangeEnd ;
        protected Array m_themeCollection ;
        protected boolean m_toggling ;
        protected Function m_isToggled ;
        protected Object toggleFuncs ;
        protected int m_InventoryLimit =-1;
        protected String m_UnwrapIcon ;
        protected boolean m_storable ;
        protected String m_storageType =null ;
        protected String m_storageKey =null ;
        protected int m_storageInitCapacity =-1;
        protected int m_storageMaxCapacity =-1;
        protected WorkersDefinition m_workers ;
        protected Vector<HarvestBonus> m_harvestBonuses;
        protected Vector<MasteryDefinition> m_masteryLevels;
        protected UpgradeDefinition m_upgrade ;
        protected String m_upgradedFromItemName ="## unknown item name ##";
        protected String m_upgradeRootName ="## unknown item name ##";
        protected String m_feed ;
        protected int m_feedImageIndex ;
        protected Array m_spawnNpcs ;
        protected Array m_spawnNpcWeights ;
        protected int m_maxSpawnNpcWeight ;
        protected String m_placeableOn ;
        protected String m_rarity ;
        protected String m_unlocksItem ;
        protected DisplayLocationsDefinition m_displayLocations ;
        protected int m_isPixelInsideAlphaThresholdOveride =128;
        protected int m_showGirders ;
        protected ItemDefinitionBridge m_item_bridgeDefn ;
        protected String m_mysteryItem ;
        protected InventoryChecksConfig m_inventoryChecksConfig ;
        protected String m_harvestIcon ;
        protected Point m_pickOffset ;
        protected ItemDefinitionHotel m_item_hotelDefn ;
        protected Array m_keywords ;
        protected Array m_marketKeywords ;
        public Array rawMarketKeywords ;
        protected String m_derivedItem ;
        protected String m_experiment ;
        protected int m_customerCapacity ;
        protected Object m_experimentTunings ;
        protected int m_harvestBasedMasteryTarget ;
        protected String m_harvestBasedMasteryRewardItemName ;
        protected int m_buildingCap ;
        protected String m_downgradeToItemName ;
        protected boolean m_isRenameable =true ;
        protected int m_downgradeGracePeriodDays ;
        protected String m_placedGuide ;
        protected int m_landNeeded ;
        protected int m_waterNeeded ;
        protected String m_grantedExpansionsOnPlace ;
        protected String m_grantedExpansionType ;
        protected String m_grantedExpansionsOnFinish ;
        protected boolean m_requestable ;
        protected boolean m_lootable ;
        protected boolean m_isBrandedBusiness =false ;
        protected String m_fbLikeUrl =null ;
        protected String m_fbLikeRef =null ;
        protected String m_mustOwnToPlace ;
        private MemoizationCache m_dooberMinimumsCache ;
        protected String m_wonderName ;
        protected Array m_requiredLandmarks ;
        protected String m_prerequisiteSaga ;
        protected String m_UI_skin ;
        protected Array m_UI_skin_params ;
        protected String m_placeableOnly ;
        protected Dictionary m_placementBoundaries ;
        protected RequirementsIndex m_requirementsIndex ;
        protected Dictionary m_marketQuest ;
        protected Dictionary m_remodelDefs ;
        protected String m_extraText ;
        protected boolean m_marketBuyable =true ;
        protected Array m_blockingExperiments ;
        protected Array m_storables ;
        protected Dictionary m_harvestLoopConfig ;
        protected String m_gardenType ;
        protected int m_gardenCapacityBonus =0;
        protected Dictionary m_fieldsAttributes ;
        protected int m_subLicenseID =0;
        protected boolean m_firework ;
        protected Array m_mechanicPackData =null ;
        protected String m_currentMechanicPackName =null ;
        public boolean m_isFranchise =true ;
        protected int m_harvestMultiplier ;
        public static  String UNLOCK_LOCKED ="locked";
        public static  String UNLOCK_LEVEL ="level";
        public static  String UNLOCK_NEIGHBOR ="neighbor";
        public static  String UNLOCK_PERMITS ="permits";
        public static  String UNLOCK_QUEST_FLAG ="questflag";
        public static  String UNLOCK_SEEN_FLAG ="seenflag";
        public static  String UNLOCK_NOT_SEEN_FLAG ="notseenflag";
        public static  String UNLOCK_POPULATION ="population";
        public static  String UNLOCK_QUEST_AND_LEVEL ="quest_and_level";
        public static  String UNLOCK_QUEST_AND_NEIGHBOR ="quest_and_neighbor";
        public static  String UNLOCK_OLD_QUEST_OR_PURCHASE ="old_quest_or_purchase";
        public static  String UNLOCK_PERMIT_BUNDLE ="permit_bundle_lock";
        public static  String UNLOCK_REQUIREMENTS ="requirements";
        public static  String UNLOCK_OBJECT_COUNT ="object_count";
        public static  String UNLOCK_CAN_ADD_POPULATION ="can_add_population";
        public static  String UPGRADE_STATE_NONE ="none";
        public static  String UPGRADE_STATE_GATED ="gated";
        public static  String UPGRADE_STATE_READY ="ready";
        public static  String COST_COINS ="coins";
        public static  String COST_CASH ="cash";
        public static  String COST_BOTH ="both";
        public static  String PERMIT_ITEM ="permits";
        public static  String HOLIDAY2010_TREE_ITEM ="holiday_tree_2010";
        public static  String HOLIDAY2010_PRESENT_ITEM ="present_holiday_2010";
        public static  int DIRECTION_SW =0;
        public static  int DIRECTION_SE =1;
        public static  int DIRECTION_NE =2;
        public static  int DIRECTION_NW =3;
        public static  int DIRECTION_MAX =4;
        public static  int DIRECTION_N =4;
        public static  int DIRECTION_E =5;
        public static  int DIRECTION_S =6;
        public static  int DIRECTION_W =7;
        public static  int DIRECTION_8_MAX =8;
        public static  int DIRECTION_SWW =8;
        public static  int DIRECTION_NWW =9;
        public static  int DIRECTION_NNW =10;
        public static  int DIRECTION_NNE =11;
        public static  int DIRECTION_NEE =12;
        public static  int DIRECTION_SEE =13;
        public static  int DIRECTION_SSE =14;
        public static  int DIRECTION_SSW =15;
        public static  int DIRECTION_16_MAX =16;
        public static  int DEFAULT_DIRECTION =0;
        public static  String DEFAULT_PHASE =null ;

	public static  String UNKNOWN_ITEM_NAME ="## unknown item name ##";
	public static  String EXPERIMENT_TUNING_MODIFY_MULTIPLIER ="multiplier";
	public static  String EXPERIMENT_TUNING_MODIFY_ADDER ="adder";
	public static  String EXPERIMENT_TUNING_MODIFY_SETTER ="setter";
	public static  String EXPERIMENT_TUNING_OPCODE_MUL ="mul";
	public static  String EXPERIMENT_TUNING_OPCODE_ADD ="add";
	public static  String EXPERIMENT_TUNING_OPCODE_SET ="set";
	public static Array s_xmlPreprocessSteps =.get(Global.gameSettings().mergeMechanicPackXmlIntoItem ,Global.gameSettings().mergeParentXmlIntoItem) ;
	public static Array m_loadedAssets =new Array();

	public boolean isDebug =false ;

        public  Item (XML param1 ,boolean param2 =false )
        {
            isDebug = param2;

            XML imageDef ;
            int typeIdx ;
            XML harvestBonusXML ;
            XML masteryLevel ;
            XML remodelXml ;
            XML validationXml ;
            XML themeXML ;
            XML keywordXML ;
            XML regenReward ;
            String animationSetName ;
            String textureDefName ;
            XMLList animationSet ;
            XMLList textureDef ;
            Object textureAsset;
            XML img ;
            XML copy ;
            XML subimage ;
            ItemImage image ;
            int rowLength ;
            int rowIdx ;
            String str ;
            int charIdx ;
            String char ;
            XML ticker ;
            String tickerName ;
            XML toaster ;
            String toasterName ;
            String toasterAsset ;
            XML toasterImage ;
            ItemBonus bonus ;
            String bonusField ;
            String positiveString ;
            Array positiveResults ;
            int positiveStreakAmount ;
            String negativeString ;
            Array negativeResults ;
            int negativeStreakAmount ;
            String bonusName ;
            HarvestBonus harvestBonus ;
            XMLList dummyxmlList ;
            RemodelDefinition remodelDef ;
            XML harvestLoopChild ;
            GenericValidationScript script ;
            Theme theme ;
            XML skinParam ;
            String key ;
            String value ;
            String branded ;
            XML experimentDefinition ;
            Array eligibleVariants ;
            Array explodedVariants ;
            String explodedVariant ;
            boolean inExperiment ;
            int sum ;
            XML npc ;
            XML commod ;
            Object data ;
            XML landmark ;
            String itemReward ;
            int addReward ;
            XML def ;
            Item randItem ;
            Object randReward ;
            String endDateStr ;
            String showTimer ;
            String startDateStr ;
            XML unlockCostCoinXML ;
            XML unlockCostCashXML ;
            String isRenameable ;
            XMLList attrs ;
            int i ;
            XML experimentTuningXML ;
            Object experimentTuning ;
            Array variants ;
            XML variantXML ;
            String values ;
            String opcode ;
            String operand ;
            Array valuesArray ;
            int expvalue ;
            xml = param1;
            super(xml);
            if(isDebug) {
            	Debug.debug4("Item.NPC_mailman");
            }

            this.m_cachedImages = new Array();
            this.m_collisionData = new Array();
            this.m_bonuses = new Array();
            this.m_positiveStreakRewards = new Array();
            this.m_negativeStreakRewards = new Array();
            this.m_validations = new Dictionary(false);
            this.toggleFuncs = {theme_collection:Global.world.isThemeCollectionEnabled};
            this.m_item_bridgeDefn = new ItemDefinitionBridge();
            this.m_item_hotelDefn = new ItemDefinitionHotel();
            this.m_dooberMinimumsCache = new MemoizationCache(this.getDooberMinimumsImpl);
            this.m_blockingExperiments = new Array();
            if(isDebug) {
            	Debug.debug4("Item.NPC_mailman");
                xml = preprocessXML(xml);
            } else {
                xml = preprocessXML(xml);
	    }

            this.m_themeListeners = new Dictionary();
            Array imageDefinitions =new Array ();
            int _loc_3 =0;
            XMLList _loc_4 =m_xml.image ;

            if(isDebug) {
            	Debug.debug4("Item.NPC_mailman");
            }

	    for(int i0 = 0; i0 < _loc_4 .size(); i0++)
	    {
	    	imageDef = _loc_4 .get(i0);

                imageDefinitions.push(imageDef);
            }
            if (xml.hasOwnProperty("displayLocations"))
            {
                this.m_displayLocations = new DisplayLocationsDefinition();
                this.m_displayLocations.loadObject(xml.child("displayLocations").get(0));
            }
            if (xml.imageGenerator.length() > 0)
            {
                animationSetName = m_xml.imageGenerator.animationSet.@name;
                textureDefName = m_xml.imageGenerator.texture.@name;
                int _loc_41 =0;
                _loc_51 = Global.gameSettings().getXML().animationDirectory.animationSet;
                XMLList _loc_31 =new XMLList("");
                Object _loc_61;
                for(int i0 = 0; i0 < _loc_51.size(); i0++)
                {
                	_loc_61 = _loc_51.get(i0);

                    with (_loc_61)
                    {
                        if (@name == animationSetName)
                        {
                            _loc_31.put(_loc_41++,  _loc_61);
                        }
                    }
                }
                animationSet = _loc_31;
                int _loc_42 =0;
                _loc_52 = Global.gameSettings().getXML().textureDirectory.texture;
                XMLList _loc_32 =new XMLList("");
                Object _loc_62;
                for(int i0 = 0; i0 < _loc_52.size(); i0++)
                {
                		_loc_62 = _loc_52.get(i0);


                    with (_loc_62)
                    {
                        if (@name == textureDefName)
                        {
                            _loc_32.put(_loc_42++,  _loc_62);
                        }
                    }
                }
                textureDef = _loc_32;
                if (animationSet.length() == 1 && textureDef.length() == 1)
                {
                    textureAsset = textureDef.get(0).asset.get(0);


                    for(int i0 = 0; i0 < animationSet.image.size(); i0++)
                    {
                    		img = animationSet.image.get(i0);


                        copy = img.copy();
                        subimage = copy.image.get(0);
                        subimage.appendChild(textureAsset.copy());
                        imageDefinitions.push(copy);
                    }
                }
                else
                {
                    ErrorManager.addError("Failed to process imageGenerator definition for " + xml.@name);
                }
            }
            int _loc_34 =0;
            _loc_44 = imageDefinitions;
            for(int i0 = 0; i0 < _loc_44.size(); i0++)
            {
            	imageDef = _loc_44.get(i0);

		if(isDebug) {
                   image = new ItemImage(imageDef,true);
                } else {
                   image = new ItemImage(imageDef);
                }
                image.addEventListener(LoaderEvent.LOADED, this.onImageLoaded);
                this.m_cachedImages.push(image);
            }
            typeIdx = 0;
            while (typeIdx < m_xml.collision.length())
            {

                this.m_collisionData.push(new Array());
                rowLength = m_xml.collision.get(typeIdx).r.length();
                rowIdx = 0;
                while (rowIdx < m_xml.collision.get(typeIdx).r.length())
                {

                    str = m_xml.collision.get(typeIdx).r.get(rowIdx);
                    charIdx;
                    while (charIdx < str.length())
                    {

                        char = str.charAt(charIdx);
                        this.m_collisionData.get(typeIdx).push(this.getCollisionFlagsFromChar(char));
                        charIdx = (charIdx + 1);
                    }
                    rowIdx = (rowIdx + 1);
                }
                typeIdx = (typeIdx + 1);
            }
            if (this.m_collisionData.length == 0)
            {
                this.m_collisionData.push(new Array());
            }
            if (m_xml.hasOwnProperty("tickers") && m_xml.tickers.length() != 0)
            {
                this.m_tickers = new Dictionary();
                int _loc_35 =0;
                _loc_45 = m_xml.tickers.ticker;
                for(int i0 = 0; i0 < m_xml.tickers.ticker.size(); i0++)
                {
                	ticker = m_xml.tickers.ticker.get(i0);


                    tickerName = String(ticker.@name);
                    if (tickerName.length != 0)
                    {
                        this.m_tickers.put(tickerName,  {name:tickerName, text:String(ticker.@text)});
                    }
                }
            }
            if (m_xml.hasOwnProperty("toasters") && m_xml.toasters.length() != 0)
            {
                this.m_toasters = new Dictionary();
                int _loc_36 =0;
                _loc_46 = m_xml.toasters.toaster;
                for(int i0 = 0; i0 < m_xml.toasters.toaster.size(); i0++)
                {
                	toaster = m_xml.toasters.toaster.get(i0);


                    toasterName = String(toaster.@name);
                    if (toasterName.length != 0)
                    {
                        toasterAsset;
                        if (toaster.hasOwnProperty("image") && toaster.image.length() != 0)
                        {
                            toasterImage = toaster.image.get(0);
                            toasterAsset = toaster.image.get(0).@url;
                        }
                        this.m_toasters.put(toasterName,  {name:toasterName, title:String(toaster.@title), text:String(toaster.@text), duration:int(toaster.@duration), asset:toasterAsset});
                    }
                }
            }
            int bonusIdx =0;
            while (bonusIdx < m_xml.bonuses.bonus.length())
            {

                bonus = new ItemBonus(m_xml.bonuses.bonus.get(bonusIdx));
                this.m_bonuses.push(bonus);
                bonusIdx = (bonusIdx + 1);
            }
            if (Config.DEBUG_MODE && this.m_bonuses.length > 1)
            {
                bonusField = ((ItemBonus)this.m_bonuses.get(0)).field;
                int _loc_37 =0;
                _loc_47 = this.m_bonuses;
                for(int i0 = 0; i0 < this.m_bonuses.size(); i0++)
                {
                		bonus = this.m_bonuses.get(i0);


                    if (bonus.field != bonusField)
                    {
                        ErrorManager.addError("GameSettingsInit: Item bonus for item=" + xml.@name + " has multiple fields: " + bonus.field + ", " + bonus.field + ".  Only one field type instanceof allowed at this time per item.");
                    }
                }
            }
            if (m_xml.hasOwnProperty("positiveStreak"))
            {
                positiveString = m_xml.positiveStreak.length() > 0 ? (String(m_xml.positiveStreak)) : (null);
                if (positiveString != null)
                {
                    positiveResults = positiveString.split(",");
                    int _loc_38 =0;
                    _loc_48 = positiveResults;
                    for(int i0 = 0; i0 < positiveResults.size(); i0++)
                    {
                    	positiveStreakAmount = positiveResults.get(i0);


                        this.m_positiveStreakRewards.push(positiveStreakAmount);
                    }
                }
            }
            if (m_xml.hasOwnProperty("negativeStreak"))
            {
                negativeString = m_xml.negativeStreak.length() > 0 ? (String(m_xml.negativeStreak)) : (null);
                if (negativeString != null)
                {
                    negativeResults = negativeString.split(",");
                    int _loc_39 =0;
                    _loc_49 = negativeResults;
                    for(int i0 = 0; i0 < negativeResults.size(); i0++)
                    {
                    	negativeStreakAmount = negativeResults.get(i0);


                        this.m_negativeStreakRewards.push(negativeStreakAmount);
                    }
                }
            }
            if (m_xml.hasOwnProperty("positiveStreakMaxEffect"))
            {
                this.m_positiveStreakMaxEffect = int(m_xml.positiveStreakMaxEffect);
            }
            HarvestBonusFactory harvestBonusFactory =new HarvestBonusFactory ();
            harvestBonusFactory.register("workerBonus", WorkerBonus);
            harvestBonusFactory.register("masteryBonus", MasteryBonus);
            harvestBonusFactory.register("slottedContainerBonus", SlottedContainerBonus);
            harvestBonusFactory.register("customerMaintenanceBonus", CustomerMaintenanceBonus);
            harvestBonusFactory.register("shipBonus", ShipBonus);
            this.m_harvestBonuses = new Vector<HarvestBonus>();
            _loc_3 = 0;
            _loc_4 = m_xml.harvestBonuses.bonus;
            for(int i0 = 0; i0 < m_xml.harvestBonuses.bonus.size(); i0++)
            {
            		harvestBonusXML = m_xml.harvestBonuses.bonus.get(i0);


                bonusName = harvestBonusXML.@name;
                harvestBonus = harvestBonusFactory.createBonus(bonusName, harvestBonusXML);
                if (harvestBonus)
                {
                    this.m_harvestBonuses.push(harvestBonus);
                }
            }
            this.m_masteryLevels = new Vector<MasteryDefinition>();
            _loc_3 = 0;
            _loc_4 = m_xml.mastery;
            for(int i0 = 0; i0 < m_xml.mastery.size(); i0++)
            {
            	masteryLevel = m_xml.mastery.get(i0);


                this.m_masteryLevels.push(new MasteryDefinition(masteryLevel));
            }
            if (this.name == "res_cottage3_colorswap_01")
            {
                dummyxmlList = m_xml..remodel;
            }
            this.m_remodelDefs = new Dictionary();
            _loc_3 = 0;
            _loc_4 = m_xml..remodel;
            for(int i0 = 0; i0 < m_xml..remodel.size(); i0++)
            {
            	remodelXml = m_xml..remodel.get(i0);


                remodelDef = RemodelDefinition.parse(remodelXml, m_name);
                if (remodelDef)
                {
                    this.m_remodelDefs.put(remodelDef.remodelItemName,  remodelDef);
                }
            }
            this.m_harvestLoopConfig = new Dictionary();
            if (m_xml.harvestLoopConfig.length() > 0)
            {
                _loc_3 = 0;
                _loc_4 = m_xml.harvestLoopConfig.children();
                for(int i0 = 0; i0 < m_xml.harvestLoopConfig.children().size(); i0++)
                {
                		harvestLoopChild = m_xml.harvestLoopConfig.children().get(i0);


                    this.m_harvestLoopConfig.put(String(harvestLoopChild.name()),  String(harvestLoopChild));
                }
            }
            _loc_3 = 0;
            _loc_4 = m_xml.validate;
            for(int i0 = 0; i0 < m_xml.validate.size(); i0++)
            {
            		validationXml = m_xml.validate.get(i0);


                script = Global.gameSettings().parseValidateTag(validationXml);
                if (script)
                {
                    this.m_validations.put(script.name,  script);
                }
            }
            this.m_themeCollection = new Array();
            _loc_3 = 0;
            _loc_4 = m_xml.toggleThemes.theme;
            for(int i0 = 0; i0 < m_xml.toggleThemes.theme.size(); i0++)
            {
            		themeXML = m_xml.toggleThemes.theme.get(i0);

                themeXML = _loc_4.get(_loc_3);
                theme = Theme.loadObject(themeXML);
                this.m_themeCollection.push(theme);
            }
            this.m_keywords = new Array();
            _loc_3 = 0;
            _loc_4 = m_xml.keyword;
            for(int i0 = 0; i0 < m_xml.keyword.size(); i0++)
            {
            		keywordXML = m_xml.keyword.get(i0);


                this.m_keywords.push(String(keywordXML));
            }
            //m_name = xml.@name;

            this.m_localizedName = ZLoc.t(this.getFriendlyNameLocFile(), name + "_friendlyName");
            this.m_code = xml.@code;
            this.m_category = new Array();
            this.m_type = xml.@type;
            this.m_behavior = xml.@behavior;
            this.m_subtype = xml.@subtype;
            this.m_className = xml.@className.toString() != "" ? (xml.@className) : (null);
            this.m_minigame = xml.@minigame.toString() != "" ? (xml.@minigame) : (null);
            this.m_description = xml.@description.toString() != "" ? (xml.@description) : (null);
            this.m_derivedItem = xml.@derivesFrom.toString() != "" ? (xml.@derivesFrom) : (null);
            this.m_buyable = xml.@buyable != false;
            this.m_placeable = xml.@placeable == true;
            this.m_requestOnly = xml.attribute("requestOnly") == true;
            this.m_persistent = xml.@persistent == true;
            this.m_consumable = xml.@consumable == true;
            this.m_requestable = xml.@requestable == true;
            this.m_lootable = xml.@lootable == true;
            this.m_ownable = xml.@ownable != false;
            this.m_giftable = xml.@giftable != false;
            this.m_noMarket = xml.@noMarket == true;
            this.m_storable = xml.@storable != false;
            this.m_newTabPriority = xml.@newTabPriority == true;
            this.m_specialGiftSendPermissions = xml.@specialGiftSendPermissions == true;
            this.m_subLicenseID = xml.@subLicenseID;
            this.m_level = int(xml.level) > 0 ? (xml.level) : (1);
            this.m_coinYield = xml.coinYield;
            this.m_xpYield = xml.xpYield;
            this.m_remodelXp = xml.remodelXp;
            this.m_populationType = xml.populationType;
            this.m_populationBase = xml.populationBase;
            this.m_populationMax = xml.populationMax;
            this.m_populationCapYield = xml.populationCapYield;
            this.m_buildTime = xml.buildTime;
            this.m_growTime = xml.growTime;
            this.m_truckNum = xml.delivery.@count;
            this.m_canFranchise = xml.canFranchise == true;
            this.m_commodityReq = xml.commodityReq;
            this.m_customerCapacity = xml.customerCapacity;
            this.m_wonderName = String(xml.wonder);
            this.m_experiment = String(xml.giftpage_experiment);
            this.m_feed = String(xml.feed);
            this.m_feedImageIndex = int(xml.feedImageIndex);
            this.m_trainTripTime = xml.trainTripTime;
            this.m_trainStorage = xml.trainStorage;
            this.m_trainMinStops = xml.trainMinStops;
            this.m_trainSpeedUpCost = xml.trainSpeedUpCost;
            this.m_trainSellPrice = xml.trainSellPrice;
            this.m_trainBuyPrice = xml.trainBuyPrice;
            this.m_trainDestination = xml.trainDestination;
            this.m_trainPayout = String(xml.trainPayout.@table);
            this.m_firstTimeBonus = Number(xml.firstTimeBonus);
            if (xml.@marketBuyable == false)
            {
                this.m_marketBuyable = false;
            }
            this.m_UI_skin = String(xml.UI_skin) ? (String(xml.UI_skin)) : ("default");
            skinParams = xml.child("UI_skin_params");
            if (skinParams.length() > 0)
            {
                this.m_UI_skin_params = new Array();
                _loc_3 = 0;
                _loc_4 = skinParams.param;
                for(int i0 = 0; i0 < aram in skinParams.size(); i0++)
                {
                		skinParam = aram in skinParams.get(i0);


                    key = skinParam.@key;
                    value = skinParam.@value;
                    this.m_UI_skin_params.put(String(skinParam.@key),  String(skinParam.@value));
                }
            }
            else
            {
                this.m_UI_skin_params = new Array();
            }
            this.m_rarity = xml.rarity;
            this.m_unlocksItem = xml.unlocksItem;
            if (xml.branded.length() > 0)
            {
                branded = xml.branded;
                if (branded == "true")
                {
                    this.m_isBrandedBusiness = true;
                }
            }
            if (xml.fbLikeUrl.length() > 0)
            {
                this.m_fbLikeUrl = xml.fbLikeUrl;
            }
            if (xml.fbLikeRef.length() > 0)
            {
                this.m_fbLikeRef = xml.fbLikeRef;
            }
            experimentDefinitions = xml.child("experiments").child("experiment");
            if (experimentDefinitions.length() > 0)
            {
                this.m_includeExperiments = new Dictionary();
                this.m_excludeExperiments = new Dictionary();
                _loc_3 = 0;
                _loc_4 = experimentDefinitions;
                for(int i0 = 0; i0 <  i0 = 0; i0 < tion in experimentDefinitions.size(); i0++.size(); i0++)
                {
                		tion =  i0 = 0; i0 < tion in experimentDefinitions.size(); i0++.get(i0);
                	experimentDefinition = tion in experimentDefinitions.get(i0);


                    if (experimentDefinition.attribute("name").length() > 0)
                    {
                        eligibleVariants = new Array();
                        if (experimentDefinition.attribute("variants").length() > 0)
                        {
                            explodedVariants = experimentDefinition.attribute("variants").toString().split(",");
                            int _loc_5 =0;
                            _loc_6 = explodedVariants;
                            for(int i0 = 0; i0 < explodedVariants.size(); i0++)
                            {
                            		explodedVariant = explodedVariants.get(i0);


                                eligibleVariants.push(parseInt(explodedVariant));
                            }
                        }
                        else
                        {
                            eligibleVariants.push(1);
                        }
                        if (experimentDefinition.attribute("blockIfInactive").length() > 0 && experimentDefinition.attribute("blockIfInactive").toString() == "true")
                        {
                            this.m_blockingExperiments.push(experimentDefinition.attribute("name").toString());
                        }
                        if (experimentDefinition.@type == "exclude")
                        {
                            this.m_excludeExperiments.put(experimentDefinition.attribute("name").toString(),  eligibleVariants);
                            continue;
                        }
                        this.m_includeExperiments.put(experimentDefinition.attribute("name").toString(),  eligibleVariants);
                    }
                }
            }
            if (xml.hasOwnProperty("energyCost"))
            {
                this.m_buildEnergyCost = xml.energyCost.@build;
                this.m_harvestEnergyCost = xml.energyCost.@harvest;
                this.m_plantEnergyCost = xml.energyCost.@plant;
                this.m_cleanEnergyCost = xml.energyCost.@clean;
                this.m_clearEnergyCost = xml.energyCost.@clear;
                this.m_openEnergyCost = xml.energyCost.@open;
                this.m_instantFinishEnergyCost = xml.energyCost.@instantFinish;
                this.m_unlockMovementEnergyCost = xml.energyCost.@unlockMovement;
            }
            if (xml.hasOwnProperty("clearReward"))
            {
                this.m_clearXpReward = m_xml.clearReward.@xp;
                this.m_clearGoldReward = m_xml.clearReward.@gold;
            }
            if (xml.hasOwnProperty("plots"))
            {
                this.m_maxPlots = xml.plots;
            }
            if (xml.hasOwnProperty("upgrade") && ((XMLList)xml.upgrade).length() > 0)
            {
                GlobalEngine.assert(((XMLList)xml.upgrade).length() == 1, "Invalid XML upgrade definition!");
                this.m_upgrade = new UpgradeDefinition(m_name, xml.upgrade.get(0));
            }
            this.m_spawnNpcs = new Array();
            this.m_spawnNpcWeights = new Array();
            if (xml.hasOwnProperty("npcInfo"))
            {
                this.m_peoplePerHarvest = m_xml.npcInfo.@npcsPerHarvest;
                this.m_peoplePerPlant = m_xml.npcInfo.@npcsPerPlant;
                this.m_actionsPerPerson = m_xml.npcInfo.@actionsPerNpc;
                this.m_maxSpawnNpcWeight = int(m_xml.npcInfo.@maxWeight);
                if (this.m_maxSpawnNpcWeight > 0)
                {
                    inExperiment;
                    if (xml.npcInfo.@experiment.toString() != "")
                    {
                        inExperiment = Global.experimentManager.getVariant(xml.npcInfo.@experiment) == xml.npcInfo.@variant;
                    }
                    if (inExperiment)
                    {
                        sum;
                        _loc_3 = 0;
                        _loc_4 = xml.npcInfo.npc;
                        for(int i0 = 0; i0 < xml.npcInfo.npc.size(); i0++)
                        {
                        		npc = xml.npcInfo.npc.get(i0);


                            this.m_spawnNpcs.push(String(npc.@type));
                            this.m_spawnNpcWeights.push(int(npc.@probability));
                            sum = sum + int(npc.@probability);
                        }
                        if (sum >= this.m_maxSpawnNpcWeight)
                        {
                            this.m_maxSpawnNpcWeight = sum;
                        }
                        else
                        {
                            this.m_spawnNpcs.push("");
                            this.m_spawnNpcWeights.push(this.m_maxSpawnNpcWeight - sum);
                        }
                    }
                }
            }
            else
            {
                this.m_peoplePerHarvest = Global.gameSettings().getInt("npcsDefaultPerHarvest");
                this.m_peoplePerPlant = Global.gameSettings().getInt("npcsDefaultPerPlant");
            }
            if (xml.hasOwnProperty("commodity"))
            {
                this.m_commodities = new Array();
                _loc_3 = 0;
                _loc_4 = xml.commodity;
                for(int i0 = 0; i0 < xml.commodity.size(); i0++)
                {
                	commod = xml.commodity.get(i0);


                    //data;
                    this.m_commodities.push(commod);
                }
            }
            else
            {
                this.m_commodities = new Array();
            }
            this.m_bonusPercent = 0;
            if (xml.hasOwnProperty("bonuses"))
            {
                this.m_bonusPercent = xml.bonuses.bonus.@percentModifier;
            }
            if (xml.hasOwnProperty("storageUnit"))
            {
                this.m_storageType = xml.storageUnit.get("storageType");
                this.m_storageKey = xml.storageUnit.get("storageKey");
                this.m_storageInitCapacity = xml.storageUnit.get("initialCapacity");
                this.m_storageMaxCapacity = xml.storageUnit.get("maxCapacity");
            }
            if (xml.hasOwnProperty("storageParams"))
            {
                if (xml.storageParams.length() > 1)
                {
                    ErrorManager.addError("ERROR: Items can only have one storageParams defined!");
                }
            }
            if (xml.hasOwnProperty("workers"))
            {
                this.m_workers = new WorkersDefinition();
                this.m_workers.loadObject(xml.workers.get(0));
            }
            if (xml.hasOwnProperty("requiredLandmarks"))
            {
                this.m_requiredLandmarks = new Array();
                _loc_3 = 0;
                _loc_4 = xml.requiredLandmarks.children();
                for(int i0 = 0; i0 < xml.requiredLandmarks.children().size(); i0++)
                {
                		landmark = xml.requiredLandmarks.children().get(i0);


                    this.m_requiredLandmarks.push(String(landmark.@name));
                }
            }
            this.m_prerequisiteSaga = null;
            if (xml.hasOwnProperty("prerequisiteSaga"))
            {
                this.m_prerequisiteSaga = xml.prerequisiteSaga.toString();
            }
            this.m_headquartersName = xml.hasOwnProperty("headquarters") ? (xml.headquarters) : (null);
            this.m_heightLimit = xml.hasOwnProperty("heightLimit") ? (xml.heightLimit) : (-1);
            this.m_dailyCollect = xml.dailyCollect;
            this.m_regenerableRewards = new Dictionary();
            _loc_3 = 0;
            _loc_4 = xml.child("regenerableRewards").child("reward");
            for(int i0 = 0; i0 < xml.child("regenerableRewards").child("reward").size(); i0++)
            {
            	regenReward = xml.child("regenerableRewards").child("reward").get(i0);


                this.m_regenerableRewards.put(regenReward.attribute("name").toString(),  parseInt(regenReward.attribute("amount").toString()));
            }
            this.m_extraText = String(xml.extraText) != "" ? (String(xml.extraText)) : ("");
            this.m_energyRewards = xml.energyRewards;
            this.m_goodsReward = xml.goodsReward;
            this.m_cashRewards = xml.cashRewards;
            this.m_coinRewards = xml.coinRewards;
            this.m_itemRewards = new Array();
            int itemRewardQuant ;
            int itemNameIdx ;
            while (itemNameIdx < xml.itemRewards.itemName.length())
            {

                if (xml.itemRewards.itemName.get(itemNameIdx).attribute("quantity"))
                {
                    itemRewardQuant = int(xml.itemRewards.itemName.get(itemNameIdx).attribute("quantity"));
                }
                itemReward = String(xml.itemRewards.itemName.get(itemNameIdx));
                if (itemRewardQuant > 1)
                {
                    addReward;
                    while (addReward < itemRewardQuant)
                    {

                        this.m_itemRewards.push(itemReward);
                        addReward = (addReward + 1);
                    }
                    itemRewardQuant;
                }
                else
                {
                    this.m_itemRewards.push(itemReward);
                }
                itemNameIdx = (itemNameIdx + 1);
            }
            if (xml.hasOwnProperty("harvestMultiplier"))
            {
                this.m_harvestMultiplier = parseInt(xml.harvestMultiplier);
            }
            if (xml.hasOwnProperty("randomRewards"))
            {
                this.m_randomRewards = new Array();
                _loc_3 = 0;
                _loc_4 = xml.randomRewards.randItem;
                for(int i0 = 0; i0 < xml.randomRewards.randItem.size(); i0++)
                {
                	def = xml.randomRewards.randItem.get(i0);


                    randItem = Global.gameSettings().getItemByName(def.@name);
                    randReward = new Object();
                    randReward.put("item", randItem);
                    if (String(def.@rarity) != "")
                    {
                        randReward.put("rarity",  String(def.@rarity));
                    }
                    this.m_randomRewards.push(randReward);
                }
            }
            allowWither = xml.@allowWither;
            this.m_allowWither = !(allowWither.length > 0 && allowWither == "false");
            this.m_cost = xml.cost;
            this.m_cash = xml.cash;
            this.m_goods = xml.goods;
            this.m_fbc = xml.fbc;
            if (xml.sale.get(0))
            {
                this.m_sale = new MarketSaleFactory().createSale(xml.sale.get(0));
                this.m_sale.loadObject(xml.sale.get(0));
            }
            this.m_sizeX = xml.sizeX;
            this.m_sizeY = xml.sizeY;
            this.m_imageOffsetX = xml.imageOffsetX;
            this.m_imageOffsetY = xml.imageOffsetY;
            this.m_requiredLevel = xml.requiredLevel;
            this.m_unlockRequirements = String(xml.unlockRequirements);
            if (xml.endDate.length() > 0)
            {
                endDateStr = xml.endDate;
                this.m_endDate = DateFormatter.parseTimeString(endDateStr);
                if (xml.showTimer.length() > 0)
                {
                    showTimer = xml.showTimer;
                    if (showTimer == "true")
                    {
                        this.m_showTimer = true;
                    }
                }
            }
            if (xml.startDate.length() > 0)
            {
                startDateStr = xml.startDate;
                this.m_startDate = DateFormatter.parseTimeString(startDateStr);
            }
            this.m_requiredPopulation = xml.requiredPopulation;
            this.m_requiredNeighbors = xml.requiredNeighbors;
            this.m_requiredQuestFlag = xml.requiredQuestFlag;
            this.m_requiredSeenFlag = xml.requiredSeenFlag;
            this.m_requiredSeenFlagText = xml.requiredSeenFlagText;
            this.m_requiredObjects = xml.requiredObjects;
            this.m_requiredObjectsCount = xml.requiredObjectsCount;
            this.m_requiredCanAddPopulation = int(xml.m_requiredCanAddPopulation);
            this.m_unlock = String(xml.unlock) != "" ? (String(xml.unlock)) : (UNLOCK_LEVEL);
            this.m_unlockCostCash = 0;
            this.m_unlockCostCoin = 0;
            unlockCostXML = xml.unlockCost.get(0);
            if (unlockCostXML != null)
            {
                unlockCostCoinXML = unlockCostXML.coin.get(0);
                if (unlockCostCoinXML != null)
                {
                    this.m_unlockCostCoin = parseInt(unlockCostCoinXML.toString());
                }
                unlockCostCashXML = unlockCostXML.cash.get(0);
                if (unlockCostCashXML != null)
                {
                    this.m_unlockCostCash = parseInt(unlockCostCashXML.toString());
                }
                if (this.m_unlockCostCash == 0)
                {
                    this.m_unlockCostCash = parseInt(unlockCostXML.toString());
                }
            }
            this.m_unlockShareFunc = String(xml.unlockShareFunc);
            this.m_unlockedCostString = String(xml.unlockedCostString);
            this.m_unlockTipText = String(xml.unlockTipText);
            this.m_enabledWhenLocked = String(xml.enabledWhenLocked) == "true";
            this.m_licenseName = String(xml.licenseName);
            this.m_size = xml.@size;
            this.m_noAutoScale = xml.noAutoScale;
            this.m_noAutoRotate = (xml.noAutoRotate == 1 );
            this.m_imageScale = xml.imageScale;
            this.m_market = String(m_xml.@market);
            this.m_multiplace = m_xml.@multiplace == true;
            this.m_multiplant = m_xml.@multiplant != false;
            this.m_useTileHighlight = m_xml.@useTileHighlight == true;
            this.m_special_onclick = String(m_xml.@special_onclick);
            this.m_useTilePicking = (m_xml.@useTilePicking == true);

            this.m_sellSendsToInventory = m_xml.@sellSendsToInventory == true;
            this.m_sellSendsToInventoryMessage = String(m_xml.sellSendsToInventoryMessage);
            this.m_construction = m_xml.construction.length() > 0 ? (String(m_xml.construction)) : (null);
            this.m_toggling = m_xml.@toggling == "true";
            this.m_isToggled = this.m_toggling ? (this.toggleFuncs.get(this.type)) : (null);
            this.m_waterLength = xml.waterlength;
            this.m_landSquares = m_xml.docksquares.length() > 0 ? (String(m_xml.docksquares)) : (null);
            this.m_dockSquares = m_xml.docksquares.length() > 0 ? (String(m_xml.docksquares)) : (null);
            this.m_berthSquares = m_xml.berthsquares.length() > 0 ? (String(m_xml.berthsquares)) : (null);
            this.m_freeShipSquares = m_xml.freeShipSquares.length() > 0 ? (String(m_xml.freeShipSquares)) : (null);
            this.m_freeShipType = m_xml.freeShipType.length() > 0 ? (String(m_xml.freeShipType)) : (null);
            this.m_requiredShip = m_xml.requiredShip.length() > 0 ? (String(m_xml.requiredShip)) : (null);
            this.m_emptyNPCType = m_xml.emptyNPCType.length() > 0 ? (String(m_xml.emptyNPCType)) : (null);
            this.m_loadedNPCType = m_xml.loadedNPCType.length() > 0 ? (String(m_xml.loadedNPCType)) : (null);
            this.m_clickSound = m_xml.clickSound.length() > 0 ? (String(m_xml.clickSound)) : (null);
            this.m_validGiftTimeRangeStart = m_xml.validGiftTimeRangeStart.length() > 0 ? (String(m_xml.validGiftTimeRangeStart)) : (null);
            this.m_validGiftTimeRangeEnd = m_xml.validGiftTimeRangeEnd.length() > 0 ? (String(m_xml.validGiftTimeRangeEnd)) : (null);
            this.m_InventoryLimit = xml.hasOwnProperty("inventoryLimit") ? (xml.inventoryLimit) : (-1);
            this.m_UnwrapIcon = m_xml.unwrapicon.length() > 0 ? (String(m_xml.unwrapicon)) : (null);
            this.m_placeableOn = m_xml.@placeableOn;
            this.m_isPixelInsideAlphaThresholdOveride = xml.hasOwnProperty("isPixelInsideAlphaThresholdOveride") ? (xml.isPixelInsideAlphaThresholdOveride) : (128);
            this.m_showGirders = m_xml.hasOwnProperty("showGirders") ? (m_xml.showGirders) : (1);
            this.m_item_bridgeDefn.init(m_xml);
            this.m_item_hotelDefn.init(m_xml);
            this.m_mysteryItem = m_xml.mysteryItem.length() > 0 ? (String(m_xml.mysteryItem)) : (null);
            this.m_inventoryChecksConfig = m_xml.inventoryChecks.length() > 0 ? (new InventoryChecksConfig(m_xml)) : (null);
            this.m_moveInVehicle = m_xml.moveInVehicle.length() > 0 ? (String(m_xml.moveInVehicle)) : (null);
            this.m_harvestIcon = m_xml.harvestIcon.length() > 0 ? (String(m_xml.harvestIcon)) : (null);
            this.m_firework = m_xml.firework.length() > 0 ? (m_xml.firework == "true") : (false);
            this.m_pickOffset = m_xml.pickOffset.length() > 0 && m_xml.pickOffset.@x != undefined && m_xml.pickOffset.@y != undefined ? (new Point(m_xml.pickOffset.@x, m_xml.pickOffset.@y)) : (null);
            this.m_storables = this.parseStorablesXML(m_xml);
            this.m_quantity = m_xml.quantity.length() > 0 ? (int(m_xml.quantity)) : (1);
            this.m_harvestBasedMasteryTarget = m_xml.harvestBasedMasteryTarget.length() > 0 ? (int(m_xml.harvestBasedMasteryTarget)) : (0);
            this.m_harvestBasedMasteryRewardItemName = m_xml.harvestBasedMasteryRewardItemName.length() > 0 ? (m_xml.harvestBasedMasteryRewardItemName) : ("");
            this.m_buildingCap = m_xml.buildingCap.length() > 0 ? (int(m_xml.buildingCap)) : (0);
            this.m_downgradeToItemName = m_xml.downgradeToItemName.length() > 0 ? (m_xml.downgradeToItemName) : ("");
            if (xml.isRenameable.length() > 0)
            {
                isRenameable = xml.isRenameable;
                if (isRenameable == "false")
                {
                    this.m_isRenameable = false;
                }
            }
            this.m_downgradeGracePeriodDays = m_xml.downgradeGracePeriodDays.length() > 0 ? (int(m_xml.downgradeGracePeriodDays)) : (0);
            this.m_placedGuide = xml.hasOwnProperty("guideOnPlace") ? (m_xml.guideOnPlace) : (null);
            this.m_gardenType = m_xml.gardenType.length() > 0 ? (String(m_xml.gardenType)) : (null);
            this.m_gardenCapacityBonus = m_xml.gardenPoolBonus.length() > 0 ? (int(m_xml.gardenPoolBonus)) : (0);
            this.m_waterNeeded = String(xml.@waterNeeded).length > 0 ? (int(xml.@waterNeeded)) : (5);
            this.m_landNeeded = String(xml.@landNeeded).length > 0 ? (int(xml.@landNeeded)) : (1);
            if (m_xml.placementBoundary.length() > 0)
            {
                attrs = m_xml.placementBoundary.@*;
                this.m_placementBoundaries = new Dictionary();
                i = 0;
                while (i < attrs.length())
                {

                    this.m_placementBoundaries.get(String(attrs.put(i).name()),  String(attrs.get(i)));
                    i = (i + 1);
                }
            }
            this.m_requirementsIndex = null;
            if (m_xml.requirementsIndex && m_xml.requirementsIndex.length() > 0)
            {
                this.m_requirementsIndex = new RequirementsIndex(m_xml.requirementsIndex);
            }
            this.m_marketQuest = new Dictionary();
            if (m_xml.marketQuest && m_xml.marketQuest.attributes().length() > 0)
            {
                this.m_marketQuest.put("name",  m_xml.marketQuest.attribute("name"));
                this.m_marketQuest.get("begin") = m_xml.marketQuest.attribute("begin") == "true";
            }
            if (xml.hasOwnProperty("experimentTunings"))
            {
                this.m_experimentTunings = new Object();
                _loc_3 = 0;
                _loc_4 = xml.experimentTunings.experimentTuning;
                for(int i0 = 0; i0 <  i0 = 0; i0 < XML in xml.experimentTunings.experimentTuning.size(); i0++.size(); i0++)
                {
                		XML =  i0 = 0; i0 < XML in xml.experimentTunings.experimentTuning.size(); i0++.get(i0);
                	experimentTuningXML = XML in xml.experimentTunings.experimentTuning.get(i0);


                    experimentTuning = new Object();
                    experimentTuning.experimentName = experimentTuningXML.@experimentName;
                    variants = new Array();


                    for(int i0 = 0; i0 < experimentTuningXML.variant.size(); i0++)
                    {
                    	variantXML = experimentTuningXML.variant.get(i0);


                        values = variantXML.@value;
                        opcode = Item.EXPERIMENT_TUNING_OPCODE_MUL;
                        operand = "";
                        if (variantXML.hasOwnProperty("@multiplier"))
                        {
                            opcode = Item.EXPERIMENT_TUNING_OPCODE_MUL;
                            operand = String(variantXML.@multiplier);
                        }
                        else if (variantXML.hasOwnProperty("@adder"))
                        {
                            opcode = Item.EXPERIMENT_TUNING_OPCODE_ADD;
                            operand = String(variantXML.@adder);
                        }
                        else if (variantXML.hasOwnProperty("@setter"))
                        {
                            opcode = Item.EXPERIMENT_TUNING_OPCODE_SET;
                            operand = String(variantXML.@setter);
                        }
                        valuesArray = values.split(",");


                        for(int i0 = 0; i0 < valuesArray.size(); i0++)
                        {
                        	expvalue = valuesArray.get(i0);


                            variants.put(expvalue,  {opcode:opcode, operand:operand});
                        }
                    }
                    experimentTuning.variants = variants;
                    this.m_experimentTunings.put(experimentTuningXML.@field,  experimentTuning);
                }
            }
            this.m_grantedExpansionsOnPlace = m_xml.grantedExpansionsOnPlace.length() > 0 ? (String(m_xml.grantedExpansionsOnPlace)) : (null);
            this.m_grantedExpansionType = m_xml.grantedExpansionType.length() > 0 ? (String(m_xml.grantedExpansionType)) : (null);
            this.m_grantedExpansionsOnFinish = m_xml.grantedExpansionsOnFinish.length() > 0 ? (String(m_xml.grantedExpansionsOnFinish)) : (null);
            this.m_mustOwnToPlace = xml.hasOwnProperty("mustOwnToPlace") ? (xml.mustOwnToPlace) : (null);
            this.m_placeableOnly = xml.hasOwnProperty("placeableOnly") ? (m_xml.placeableOnly.attribute("targetSquare")) : (null);
            this.storeXMLFieldAttributes(xml.cost);
            this.initSet(xml);
            return;
        }//end

        protected void  storeXMLFieldAttributes (Object param1)
        {
            XMLList _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (param1 instanceof XMLList && ((XMLList)param1).length() > 0)
            {
                if (((XMLList)param1).attributes().length() > 0)
                {
                    if (!this.m_fieldsAttributes)
                    {
                        this.m_fieldsAttributes = new Dictionary();
                    }
                    _loc_2 = ((XMLList)param1).attributes();
                    _loc_3 = ((XMLList)param1).name().toString();
                    this.m_fieldsAttributes.put(_loc_3,  new Object());
                    for(int i0 = 0; i0 < _loc_2.size(); i0++)
                    {
                    		_loc_4 = _loc_2.get(i0);

                        this.m_fieldsAttributes.get(_loc_3).get(_loc_2.put(_loc_4).name().toString(),  _loc_2.get(_loc_4).toString());
                    }
                }
            }
            return;
        }//end

        protected void  initSet (XML param1 )
        {
            boolean _loc_2 =false ;
            ItemGroup _loc_3 =null ;
            if ("@set" in param1)
            {
                this.m_set = Global.gameSettings().getSetByName(param1.@set);
            }
            else
            {
                _loc_2 = false;
                for(int i0 = 0; i0 < Global.gameSettings().getSets().size(); i0++)
                {
                	_loc_3 = Global.gameSettings().getSets().get(i0);

                    if (_loc_3.isItemInGroup(m_name))
                    {
                        this.m_set = _loc_3;
                        _loc_2 = true;
                        break;
                    }
                }
                if (!_loc_2)
                {
                    this.m_set = null;
                }
            }
            return;
        }//end

        protected int  getCollisionFlagsFromChar (String param1 )
        {
            int _loc_2 =0;
            switch(param1)
            {
                case "#":
                {
                    _loc_2 = Constants.COLLISION_ALL;
                    break;
                }
                case "O":
                {
                    _loc_2 = Constants.COLLISION_OVERLAP;
                    break;
                }
                case "H":
                {
                    _loc_2 = Constants.COLLISION_ALL_EXCEPT_AVATAR;
                    break;
                }
                case ".":
                {
                }
                default:
                {
                    _loc_2 = Constants.COLLISION_NONE;
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

        public Object  getImageObjectFromCache ()
        {
            Object _loc_1 =null ;
            Object _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.m_cachedImages.length())
            {

                _loc_3 = this.m_cachedImages.get(_loc_2);
                if (_loc_3.name == "static")
                {
                    _loc_1 = _loc_3;
                    break;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        public ItemImageInstance  getCachedImage (String param1 ,MapResource param2 ,int param3 =0,String param4 =null )
        {
            _loc_5 = Global.gameSettings().getItemByNameAndThemes(m_name,Global.world.currentThemes);
            if (_loc_5 == null)
            {
                _loc_5 = this;
            }
            else if (!this.m_themeListeners.get(_loc_5) && _loc_5 != this)
            {
                this.m_themeListeners.put(_loc_5,  true);
                _loc_5.addEventListener(LoaderEvent.LOADED, this.onImageLoaded);
            }
            return _loc_5.getCanonicalCachedImage(param1, param2, param3, param4);
        }//end

        public ItemImageInstance  getCanonicalCachedImage (String param1 ,MapResource param2 ,int param3 =0,String param4 =null )
        {
            ItemImageInstance _loc_5 =null ;
            ItemImage _loc_7 =null ;
            int _loc_6 =0;
            while (_loc_6 < this.m_cachedImages.length())
            {

                _loc_7 =(ItemImage) this.m_cachedImages.get(_loc_6);
                if (_loc_7 && _loc_7.isMatching(param1, param3, param4))
                {
                    _loc_5 = _loc_7.getInstance(param2);
                    if (_loc_5 && _loc_5.image == null)
                    {
                        _loc_5 = null;
                    }
                    if(_loc_5) {
			    //add by xinghai
			    _loc_5.name = m_name;

                    }
                }
                _loc_6++;
            }

            return _loc_5;
        }//end

        public ItemImage  getCanonicalCachedImageDefinition (String param1 ,MapResource param2 ,int param3 =0,String param4 =null )
        {
            ItemImage _loc_5 =null ;
            ItemImage _loc_7 =null ;
            int _loc_6 =0;
            while (_loc_6 < this.m_cachedImages.length())
            {

                _loc_7 =(ItemImage) this.m_cachedImages.get(_loc_6);
                if (_loc_7 && _loc_7.isMatching(param1, param3, param4))
                {
                    _loc_5 = _loc_7;
                }
                _loc_6++;
            }
            return _loc_5;
        }//end

        public boolean  hasCachedImageByName (String param1 )
        {
            ItemImage _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_cachedImages.size(); i0++)
            {
            	_loc_2 = this.m_cachedImages.get(i0);

                if (_loc_2.name == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  isCachedImageLoading (String param1 ,int param2 =0,String param3 =null )
        {
            ItemImage _loc_5 =null ;
            int _loc_4 =0;
            while (_loc_4 < this.m_cachedImages.length())
            {

                _loc_5 =(ItemImage) this.m_cachedImages.get(_loc_4);
                if (_loc_5 && _loc_5.isMatching(param1, param2, param3))
                {
                    if (_loc_5.isLoading())
                    {
                        return true;
                    }
                }
                _loc_4++;
            }
            return false;
        }//end

        public Vector3 Vector  getCachedImageHotspots (String param1 ,int param2 =0,String param3 ="").<>
        {
            ItemImage _loc_5 =null ;
            int _loc_4 =0;
            while (_loc_4 < this.m_cachedImages.length())
            {

                _loc_5 =(ItemImage) this.m_cachedImages.get(_loc_4);
                if (_loc_5 && _loc_5.name == param1 && (_loc_5.direction == param2 || _loc_5.allDirections))
                {
                    return _loc_5.getHotspotsByName(param3);
                }
                _loc_4++;
            }
            return null;
        }//end

        public Vector3  getCachedImageDimensions (String param1 ,int param2)
        {
            ItemImage _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_cachedImages.length())
            {

                _loc_4 =(ItemImage) this.m_cachedImages.get(_loc_3);
                if (_loc_4 && _loc_4.name == param1 && (_loc_4.direction == param2 || _loc_4.allDirections))
                {
                    if (_loc_4.forcedWidth > 0 && _loc_4.forcedHeight > 0)
                    {
                        return new Vector3(_loc_4.forcedWidth, _loc_4.forcedHeight);
                    }
                    return null;
                }
                _loc_3++;
            }
            return null;
        }//end

        private void  onImageLoaded (Event event )
        {

            if(isDebug) {
            	Debug.debug4("Item.NPC_mailman. onImageLoaded");
            }

            event.stopImmediatePropagation();
            dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        public String  grantedExpansionsOnPlace ()
        {
            return this.m_grantedExpansionsOnPlace;
        }//end

        public String  grantedExpansionType ()
        {
            return this.m_grantedExpansionType;
        }//end

        public String  grantedExpansionsOnFinish ()
        {
            return this.m_grantedExpansionsOnFinish;
        }//end

        public Array  mustOwnToPlace ()
        {
            Array _loc_1 =null ;
            if (this.m_mustOwnToPlace)
            {
                _loc_1 = this.m_mustOwnToPlace.split("|");
                return _loc_1;
            }
            return null;
        }//end

        public String  placeableOnly ()
        {
            return this.m_placeableOnly;
        }//end

        public int  dailyCollect ()
        {
            return this.m_dailyCollect;
        }//end

        public int  level ()
        {
            return this.m_level;
        }//end

        public int  requiredLevel ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredLevel;
            }
            return this.m_requiredLevel;
        }//end

        public Requirements  unlockRequirements ()
        {
            return this.getRequirementsByName(this.m_unlockRequirements);
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public String  subtype ()
        {
            return this.m_subtype;
        }//end

        public String  freeGiftExperiment ()
        {
            return this.m_experiment;
        }//end

        public Array  bonuses ()
        {
            return this.m_bonuses;
        }//end

        public boolean  requestable ()
        {
            return this.m_requestable;
        }//end

        public boolean  lootable ()
        {
            return this.m_lootable;
        }//end

        public boolean  isBrandedBusiness ()
        {
            return this.m_isBrandedBusiness;
        }//end

        public String  fbLikeUrl ()
        {
            return this.m_fbLikeUrl;
        }//end

        public String  fbLikeRef ()
        {
            return this.m_fbLikeRef;
        }//end

        public String  placeGuide ()
        {
            return this.m_placedGuide;
        }//end

        public String  gardenType ()
        {
            return this.m_gardenType;
        }//end

        public int  gardenCapacityBonus ()
        {
            return this.m_gardenCapacityBonus;
        }//end

        public int  getPostiveStreakReward (int param1 )
        {
            return this.m_positiveStreakRewards.get(param1);
        }//end

        public int  getNegativeStreakReward (int param1 )
        {
            return this.m_negativeStreakRewards.get(param1);
        }//end

        public int  getPositiveStreakMaxEffect ()
        {
            return this.m_positiveStreakMaxEffect;
        }//end

        public Object  getTickerInfo (String param1 )
        {
            Object _loc_2 =null ;
            if (this.m_tickers && param1 && this.m_tickers.get(param1))
            {
                _loc_2 = this.m_tickers.get(param1);
            }
            return _loc_2;
        }//end

        public Object  getToasterInfo (String param1 )
        {
            Object _loc_2 =null ;
            if (this.m_toasters && param1 && this.m_toasters.get(param1))
            {
                _loc_2 = this.m_toasters.get(param1);
            }
            return _loc_2;
        }//end

        public String  extraText ()
        {
            return this.m_extraText;
        }//end

        public GenericValidationScript  getValidation (String param1 )
        {
            _loc_2 = this.m_validations.get(param1);
            if (!_loc_2)
            {
                _loc_2 = Global.validationManager.getValidator(param1);
            }
            return _loc_2;
        }//end

        private Array  parseStorablesXML (XML param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =null ;
            if (param1.storables.length() > 0 && m_xml.storables.@keywords != undefined)
            {
                _loc_3 = String(m_xml.storables.@keywords);
                _loc_2 = _loc_3.split(",");
            }
            return _loc_2;
        }//end

        public HarvestBonus Vector  harvestBonuses ().<>
        {
            return this.m_harvestBonuses;
        }//end

        public MasteryDefinition Vector  masteryLevels ().<>
        {
            return this.m_masteryLevels;
        }//end

        public String  behavior ()
        {
            return this.m_behavior;
        }//end

        public boolean  multiplace ()
        {
            return this.m_multiplace;
        }//end

        public boolean  multiplant ()
        {
            return this.m_multiplant;
        }//end

        public boolean  giftable ()
        {
            return this.m_giftable;
        }//end

        public boolean  specialGiftSendPermissions ()
        {
            return this.m_specialGiftSendPermissions;
        }//end

        public String  market ()
        {
            return this.m_market;
        }//end

        protected IUnlock  unlockDataSource ()
        {
            if (this.unlockRequirements)
            {
                return this.unlockRequirements;
            }
            return null;
        }//end

        public String  unlock ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.unlockType;
            }
            return this.m_unlock;
        }//end

        public String  rawUnlock ()
        {
            return this.m_unlock;
        }//end

        public void  unlockCostCash (int param1 )
        {
            this.m_unlockCostCash = param1;
            return;
        }//end

        public int  subLicenseID ()
        {
            return this.m_subLicenseID;
        }//end

        public String  licenseName ()
        {
            _loc_1 = name;
            if (this.m_licenseName)
            {
                _loc_1 = this.m_licenseName;
            }
            return _loc_1;
        }//end

        public String  unlockShareFunc ()
        {
            return this.m_unlockShareFunc;
        }//end

        public String  unlockedCostString ()
        {
            return this.m_unlockedCostString;
        }//end

        public String  unlockTipText ()
        {
            String _loc_1 =null ;
            if (this.unlock == UNLOCK_LOCKED || this.unlock == UNLOCK_QUEST_FLAG)
            {
                _loc_1 = this.m_unlockTipText;
            }
            return _loc_1;
        }//end

        public boolean  enabledWhenLocked ()
        {
            return this.m_enabledWhenLocked && (this.unlock == UNLOCK_LOCKED || this.unlock == UNLOCK_QUEST_FLAG);
        }//end

        public int  unlockCostCash ()
        {
            return this.m_unlockCostCash;
        }//end

        public int  unlockCostCoin ()
        {
            return this.m_unlockCostCoin;
        }//end

        public IMarketSale  sale ()
        {
            return this.m_sale;
        }//end

        public int  requiredCanAddPopulation ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredCanAddPopulation;
            }
            return this.m_requiredCanAddPopulation;
        }//end

        public Array  requiredObjects ()
        {
            _loc_1 = this.m_requiredObjects.split(",");
            if (this.unlockDataSource)
            {
                _loc_1 = this.unlockDataSource.requiredObjects;
            }
            return _loc_1;
        }//end

        public boolean  requiredObjectsNeedUpgrade ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.requiredObjectsBase;
            _loc_3 = Global.world.getObjectsByNames(.get(_loc_2));
            if (_loc_3.length > 0)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public String  requiredObjectsBase ()
        {
            Item _loc_2 =null ;
            String _loc_1 =null ;
            if (this.requiredObjects.length > 0)
            {
                _loc_2 = Global.gameSettings().getItemByName(this.requiredObjects.get(0));
                if (_loc_2)
                {
                    _loc_1 = Item.findUpgradeRoot(_loc_2);
                }
            }
            return _loc_1;
        }//end

        public int  requiredObjectsCount ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredObjectsCount;
            }
            return this.m_requiredObjectsCount;
        }//end

        public int  requiredPopulation ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredPopulation;
            }
            return this.m_requiredPopulation;
        }//end

        public int  requiredPopulationScaled ()
        {
            _loc_1 = this.m_requiredPopulation;
            if (this.unlockDataSource)
            {
                _loc_1 = this.unlockDataSource.requiredPopulation;
            }
            return _loc_1 * Global.gameSettings().getNumber("populationScale", 1);
        }//end

        public int  requiredNeighbors ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredNeighbors;
            }
            return this.m_requiredNeighbors;
        }//end

        public String  requiredQuestFlag ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredQuestFlag;
            }
            return this.m_requiredQuestFlag;
        }//end

        public String  requiredSeenFlag ()
        {
            if (this.unlockDataSource)
            {
                return this.unlockDataSource.requiredSeenFlag;
            }
            return this.m_requiredSeenFlag;
        }//end

        public String  requiredSeenFlagText ()
        {
            return this.m_requiredSeenFlagText;
        }//end

        public String  className ()
        {
            return this.m_className;
        }//end

        public String  icon ()
        {
            return getImageByName("icon");
        }//end

        public String  iconRelative ()
        {
            return getRelativeImageByName("icon");
        }//end

        public int  cost ()
        {
            _loc_1 = this.getModifiedField("cost",this.m_cost);
            _loc_2 = (String)(_loc_1 );
            _loc_3 = this.getExperimentTuningsByFieldName("cost");
            _loc_4 = this.applyExperimentTuning(_loc_2,_loc_3);
            _loc_5 =(double)(_loc_4 );
            if (Global.isVisiting() && this.type == "business" && this.m_isFranchise)
            {
                return _loc_5 * (1 - Global.gameSettings().getInt("franchiseDiscountPct") / 100);
            }
            if (this.type == "expansion")
            {
                return ExpansionManager.instance.getExpansionCost(this.m_cost);
            }
            if (this.hasOverridePrice(UserResourceType.get(UserResourceType.COIN)))
            {
                return this.getOverridePrice(UserResourceType.get(UserResourceType.COIN));
            }
            return _loc_5;
        }//end

        public int  baseCost ()
        {
            return this.m_cost;
        }//end

        public int  getRemodelCost ()
        {
            _loc_1 = this.m_cost;
            if (this.getRemodelBase() != null)
            {
                return _loc_1 + this.getRemodelBase().cost;
            }
            return _loc_1;
        }//end

        public int  cash ()
        {
            _loc_1 = String(this.m_cash);
            _loc_2 = this.getExperimentTuningsByFieldName("cash");
            _loc_3 = this.applyExperimentTuning(_loc_1,_loc_2);
            _loc_4 = double(_loc_3);
            if (Global.isVisiting() && this.type == "business" && this.m_isFranchise)
            {
                return _loc_4 * (1 - Global.gameSettings().getInt("franchiseDiscountPct") / 100);
            }
            if (this.hasOverridePrice(UserResourceType.get(UserResourceType.CASH)))
            {
                return this.getOverridePrice(UserResourceType.get(UserResourceType.CASH));
            }
            return _loc_4;
        }//end

        public  getModifiedField (String param1 , Object param2) *
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            _loc_3 = param2;
            if (this.m_fieldsAttributes)
            {
                _loc_4 = this.m_fieldsAttributes.get(param1);
                if (_loc_4)
                {
                    _loc_5 = _loc_4.get("itemFieldModifier");
                    if (_loc_5 && ItemFieldUtils.get(_loc_5) instanceof Function)
                    {
                        Function loc_1 =ItemFieldUtils.get(_loc_5) ;
                        _loc_3 = loc_1(m_name, param1, param2, _loc_4);
                    }
                }
            }
            return _loc_3;
        }//end

        public boolean  hasRemodel ()
        {
            boolean _loc_1 =false ;
            if (this.getRemodelDefinition())
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public boolean  isRemodelSkin ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.getRemodelDefinition();
            if (_loc_2 && !_loc_2.isBaseModel)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public Item  getRemodelBase ()
        {
            RemodelDefinition _loc_2 =null ;
            _loc_1 = this.getAllRemodelDefinitions();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2 && _loc_2.isBaseModel)
                {
                    return Global.gameSettings().getItemByName(_loc_2.remodelItemName);
                }
            }
            return null;
        }//end

        public RemodelDefinition  getRemodelDefinition ()
        {
            return this.getRemodelDefinitionByName(name);
        }//end

        public RemodelDefinition  getRemodelDefinitionByName (String param1 )
        {
            return this.m_remodelDefs.get(param1);
        }//end

        public RemodelDefinition Vector  getAllRemodelDefinitions ().<>
        {
            String _loc_2 =null ;
            Vector<RemodelDefinition> _loc_1 =new Vector<RemodelDefinition>();
            for(int i0 = 0; i0 < this.m_remodelDefs.size(); i0++)
            {
            		_loc_2 = this.m_remodelDefs.get(i0);

                _loc_1.push(this.m_remodelDefs.get(_loc_2));
            }
            return _loc_1;
        }//end

        public boolean  hasOverridePrice (UserResourceType param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.getRemodelDefinitionByName(name );
            if (_loc_3)
            {
                _loc_2 = _loc_3.useOverridePrice(param1);
            }
            return _loc_2;
        }//end

        public int  getOverridePrice (UserResourceType param1 )
        {
            _loc_2 = this.getRemodelDefinitionByName(name);
            if (_loc_2 && _loc_2.useOverridePrice(param1))
            {
                return _loc_2.getOverridePrice(param1);
            }
            return 0;
        }//end

        public Dictionary  harvestLoopConfig ()
        {
            return this.m_harvestLoopConfig;
        }//end

        public int  goods ()
        {
            return this.m_goods;
        }//end

        public int  fbc ()
        {
            return this.m_fbc;
        }//end

        public int  GetItemCost ()
        {
            if (this.goods > 0)
            {
                return this.goods;
            }
            if (this.cash > 0)
            {
                return this.cash;
            }
            if (this.fbc > 0)
            {
                return this.fbc;
            }
            return this.cost;
        }//end

        public int  GetItemSalePrice ()
        {
            _loc_1 = this.GetItemCost();
            double _loc_2 =0;
            if (Global.marketSaleManager.isItemOnSale(this))
            {
                _loc_2 = Global.marketSaleManager.getDiscountPercent(this) / 100;
            }
            _loc_1 = Math.ceil(_loc_1 - _loc_1 * _loc_2);
            _loc_1 = int(_loc_1.toFixed(0));
            return _loc_1;
        }//end

        public int  plantXp ()
        {
            return Global.gameSettings().getInt("buyXpGainMin");
        }//end

        public double  growTime ()
        {
            return this.m_growTime;
        }//end

        public double  buildTimeInDays ()
        {
            Item _loc_1 =null ;
            double _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            double _loc_6 =0;
            if (this.construction != null)
            {
                _loc_1 = Global.gameSettings().getItemByName(this.m_construction);
                if (_loc_1 != null)
                {
                    _loc_2 = _loc_1.xml.timePerBuild;
                    _loc_3 = _loc_1.xml.buildsPerStage;
                    _loc_4 = _loc_1.xml.numberOfStages;
                    _loc_5 = _loc_3 * _loc_4;
                    _loc_6 = _loc_2 * _loc_5 / (1000 * 60 * 60 * 24);
                    return _loc_6;
                }
            }
            return this.m_buildTime;
        }//end

        public boolean  allowWither ()
        {
            return this.m_allowWither;
        }//end

        public int  coinYield ()
        {
            return this.m_coinYield;
        }//end

        public int  xpYield ()
        {
            return this.m_xpYield;
        }//end

        public int  remodelXp ()
        {
            return this.m_remodelXp;
        }//end

        public String  populationType ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            Object _loc_3 =null ;
            if (!this.m_populationTypeTuned)
            {
                _loc_1 = Population.CITIZEN;
                if (this.m_populationType)
                {
                    _loc_1 = this.m_populationType;
                }
                _loc_2 = _loc_1;
                _loc_3 = this.getExperimentTuningsByFieldName("populationType");
                if (EXPERIMENT_TUNING_OPCODE_SET == _loc_3.opcode)
                {
                    _loc_2 = this.applyExperimentTuning(_loc_1, _loc_3);
                    if (!_loc_2)
                    {
                        _loc_2 = Population.CITIZEN;
                    }
                }
                this.m_populationTypeTuned = _loc_2;
            }
            return this.m_populationTypeTuned;
        }//end

        public int  populationBase ()
        {
            return this.m_populationBase;
        }//end

        public int  populationMax ()
        {
            return this.m_populationMax;
        }//end

        public int  populationCapYield ()
        {
            return this.m_populationCapYield;
        }//end

        public int  harvestEnergyCost ()
        {
            return this.m_harvestEnergyCost;
        }//end

        public int  buildEnergyCost ()
        {
            return this.m_buildEnergyCost;
        }//end

        public int  cleanEnergyCost ()
        {
            return this.m_cleanEnergyCost;
        }//end

        public int  plantEnergyCost ()
        {
            return this.m_plantEnergyCost;
        }//end

        public int  clearEnergyCost ()
        {
            return this.m_clearEnergyCost;
        }//end

        public int  unlockMovementEnergyCost ()
        {
            return this.m_unlockMovementEnergyCost;
        }//end

        public int  openEnergyCost ()
        {
            return this.m_openEnergyCost;
        }//end

        public int  instantFinishEnergyCost ()
        {
            return this.m_instantFinishEnergyCost;
        }//end

        public int  clearXpReward ()
        {
            return this.m_clearXpReward;
        }//end

        public int  clearGoldReward ()
        {
            return this.m_clearGoldReward;
        }//end

        public Array  contractChoices ()
        {
            return String(m_xml.contracts).split(",");
        }//end

        public boolean  buyable ()
        {
            return this.m_buyable;
        }//end

        public String  minigame ()
        {
            return this.m_minigame;
        }//end

        public String  description ()
        {
            return this.m_description;
        }//end

        public Dictionary  experiments ()
        {
            return this.m_includeExperiments;
        }//end

        public Dictionary  excludeExperiments ()
        {
            return this.m_excludeExperiments;
        }//end

        public boolean  noMarket ()
        {
            return this.m_noMarket;
        }//end

        public boolean  placeable ()
        {
            return this.m_placeable;
        }//end

        public boolean  requestOnly ()
        {
            return this.m_requestOnly;
        }//end

        public boolean  persistent ()
        {
            return this.m_persistent;
        }//end

        public boolean  consumable ()
        {
            return this.m_consumable;
        }//end

        public double  npcsPerHarvest ()
        {
            return this.m_peoplePerHarvest;
        }//end

        public double  npcsPerPlant ()
        {
            return this.m_peoplePerPlant;
        }//end

        public int  actionsPerNpc ()
        {
            return this.m_actionsPerPerson;
        }//end

        public boolean  ownable ()
        {
            return this.m_ownable;
        }//end

        public boolean  storable ()
        {
            return this.m_storable;
        }//end

        public WorkersDefinition  workers ()
        {
            return this.m_workers;
        }//end

        public DisplayLocationsDefinition  displayLocations ()
        {
            return this.m_displayLocations;
        }//end

        public boolean  canFranchise ()
        {
            return this.m_canFranchise;
        }//end

        public double  commodityReq ()
        {
            _loc_1 = String(this.m_commodityReq);
            _loc_2 = this.getExperimentTuningsByFieldName("commodityReq");
            _loc_3 = this.applyExperimentTuning(_loc_1,_loc_2);
            _loc_4 =(double)(_loc_3 );
            return Number(_loc_3);
        }//end

        public int  customerCapacity ()
        {
            return this.m_customerCapacity;
        }//end

        public double  harvestBasedMasteryTarget ()
        {
            return this.m_harvestBasedMasteryTarget;
        }//end

        public String  harvestBasedMasteryRewardItemName ()
        {
            return this.m_harvestBasedMasteryRewardItemName;
        }//end

        public int  buildingCap ()
        {
            return this.m_buildingCap;
        }//end

        public String  downgradeToItemName ()
        {
            return this.m_downgradeToItemName;
        }//end

        public boolean  isRenameable ()
        {
            return this.m_isRenameable;
        }//end

        public int  downgradeGracePeriodDays ()
        {
            return this.m_downgradeGracePeriodDays;
        }//end

        public boolean  useTileHighlight ()
        {
            return this.m_useTileHighlight;
        }//end

        public boolean  useTilePicking ()
        {
            return true;
            //return this.m_useTilePicking;
        }//end

        public boolean  sellSendsToInventory ()
        {
            return this.m_sellSendsToInventory;
        }//end

        public String  sellSendsToInventoryMessage ()
        {
            if (this.m_sellSendsToInventoryMessage)
            {
                return this.m_sellSendsToInventoryMessage;
            }
            return "SendToInventorySpecific";
        }//end

        public int  sellPrice ()
        {
            _loc_1 = Global.gameSettings().getNumber("sellBackRatio");
            _loc_2 = Global.gameSettings().getNumber("goodsToCoinRatio");
            if (this.goods > 0)
            {
                return Math.ceil(this.goods * (1 / _loc_2) * _loc_1);
            }
            return Math.ceil(this.cost * _loc_1);
        }//end

        public String  code ()
        {
            return this.m_code;
        }//end

         public String  localizedName ()
        {
            if (this.m_toggling && this.m_isToggled && this.m_isToggled(this))
            {
                return ZLoc.t(this.getFriendlyNameLocFile(), name + "_friendlyName_disable");
            }
            return this.m_localizedName;
        }//end

        public LocalizationObjectToken  getLocalizedObjectToken (int param1 =1)
        {
            return ZLoc.tk(this.getObjectTokenLocFile(), "Item_" + name, "", param1);
        }//end

        public String  iconImageName ()
        {
            if (this.m_toggling && this.m_isToggled && this.m_isToggled(this))
            {
                return "icon_disable";
            }
            return "icon";
        }//end

        public boolean  toggling ()
        {
            return this.m_toggling;
        }//end

        public void  localizedName (String param1 )
        {
            this.m_localizedName = param1;
            return;
        }//end

        public double  sizeX ()
        {
            return Math.max(this.m_sizeX, 0.01);
        }//end

        public double  sizeY ()
        {
            return Math.max(this.m_sizeY, 0.01);
        }//end

        public double  noAutoScale ()
        {
            return this.m_noAutoScale;
        }//end

        public boolean  noAutoRotate ()
        {
            return this.m_noAutoRotate;
        }//end

        public double  imageScale ()
        {
            return this.m_imageScale;
        }//end

        public double  imageOffsetX ()
        {
            return this.m_imageOffsetX;
        }//end

        public double  imageOffsetY ()
        {
            return this.m_imageOffsetY;
        }//end

        public Array  getCollisionData (int param1 =0)
        {
            return this.m_collisionData.get(param1);
        }//end

        public Array  itemRewards ()
        {
            return this.m_itemRewards;
        }//end

        public Array  randomRewards ()
        {
            return this.m_randomRewards;
        }//end

        public double  energyRewards ()
        {
            return this.m_energyRewards;
        }//end

        public Dictionary  regenerableRewards ()
        {
            return this.m_regenerableRewards;
        }//end

        public int  getRegenerableRewardAmountByName (String param1 )
        {
            return this.m_regenerableRewards.get(param1) ? (this.m_regenerableRewards.get(param1)) : (0);
        }//end

        public double  cashRewards ()
        {
            return this.m_cashRewards;
        }//end

        public double  coinRewards ()
        {
            return this.m_coinRewards;
        }//end

        public double  goodsReward ()
        {
            return this.m_goodsReward;
        }//end

        public double  firstTimeBonus ()
        {
            return this.m_firstTimeBonus;
        }//end

        public int  getCollisionDataIndexByName (String param1 )
        {
            int _loc_2 =-1;
            int _loc_3 =0;
            while (_loc_3 < m_xml.collision.length())
            {

                if (m_xml.collision.get(_loc_3).@name == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        public ItemGroup  getSet ()
        {
            return this.m_set;
        }//end

        public boolean  showTimer ()
        {
            return this.m_showTimer;
        }//end

        public double  startDate ()
        {
            return this.m_startDate;
        }//end

        public double  endDate ()
        {
            return this.m_endDate;
        }//end

        public String  construction ()
        {
            return this.m_construction;
        }//end

        public XML  constructionXml ()
        {
            if (m_xml.construction.length() > 0)
            {
                return m_xml.construction.get(0);
            }
            return null;
        }//end

        public XMLList  shockwaveXml ()
        {
            return m_xml.shockwave;
        }//end

        public double  expansionWidth ()
        {
            return m_xml.@width;
        }//end

        public double  expansionHeight ()
        {
            return m_xml.@height;
        }//end

        public XMLList  gatesXml ()
        {
            return m_xml.get("gates");
        }//end

        public String  placementSeenFlag ()
        {
            _loc_1 = (String)(m_xml.placementSeenFlag );
            return _loc_1 == "" ? (null) : (_loc_1);
        }//end

        public Requirements  getRequirementsByName (String param1 )
        {
            Requirements _loc_2 =null ;
            if (this.m_requirementsIndex)
            {
                _loc_2 = this.m_requirementsIndex.getRequirementsByName(param1);
            }
            return _loc_2;
        }//end

        public UserResourceType  getPurchaseType ()
        {
            _loc_1 = this.cash>0? (UserResourceType.get(UserResourceType.CASH)) : (UserResourceType.get(UserResourceType.COIN));
            return _loc_1;
        }//end

        public int  getPurchaseAmount (UserResourceType param1 )
        {
            int _loc_2 =-1;
            switch(param1)
            {
                case UserResourceType.get(UserResourceType.CASH):
                {
                    _loc_2 = this.cash;
                    break;
                }
                case UserResourceType.get(UserResourceType.COIN):
                {
                    _loc_2 = this.cost;
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_2;
        }//end

        public XMLList  getCurrentCrewGateXML (MapResource param1 )
        {
            Item crewDefItem ;
            owner = param1;
            Item parent ;
            XMLList gatesxml ;
            parentName = Item.findUpgradeParent(this);
            Object _loc_6;
            Object _loc_7;
	    int _loc_4 =0;
	    Object _loc_5;
	    XMLList _loc_3 =new XMLList("");

            if (parentName)
            {
                parent = Global.gameSettings().getItemByName(parentName);
            }
            RollCallDataMechanic rollCallMechanic ;
            if (owner instanceof IMechanicUser)
            {
                rollCallMechanic =(IMechanicUser, "rollCall", MechanicManager.ALL) as RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(owner;
            }
            rollCallInProgress = rollCallMechanic&& rollCallMechanic.getState() == RollCallDataMechanic.STATE_IN_PROGRESS && rollCallMechanic.getRollCallTimeLeft() > 0;
            if (rollCallInProgress)
            {
                if (parent)
                {
                    _loc_4 = 0;
                    _loc_5 = parent.gatesXml.gate;
                    _loc_3 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    	_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@name == "pre_upgrade")
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    gatesxml = _loc_3;
                }
                else
                {
                    _loc_4 = 0;
                    _loc_5 = this.gatesXml.gate;
                    _loc_3 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    	_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@name == "build")
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    gatesxml = _loc_3;
                }
            }
            else
            {
                crewDefItem = this.upgrade.newItemName ? (this) : (parent);
                _loc_4 = 0;
                _loc_5 = this.gatesXml.gate;
                _loc_3 = new XMLList("");
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == "build")
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                if (crewDefItem && (owner.isUpgradePossible() || _loc_3.length() < 1))
                {
                    _loc_4 = 0;
                    _loc_5 = crewDefItem.gatesXml.gate;
                    _loc_3 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    	_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@name == "pre_upgrade")
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    gatesxml = _loc_3;
                }
                else
                {
                    _loc_4 = 0;
                    _loc_5 = this.gatesXml.gate;
                    _loc_3 = new XMLList("");
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    	_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@name == "build")
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    gatesxml = _loc_3;
                }
            }
            return gatesxml;
        }//end

        public XML  randomModifiersXml ()
        {
            randMods = m_xml.randomModifiers;
	    Object _loc_5;

            if (randMods.length() > 0 && randMods.hasOwnProperty("@name"))
            {
                int _loc_3 =0;
                _loc_4 = randMods;
                XMLList _loc_2 =new XMLList("");
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);


                    with (_loc_5)
                    {
                        if (@name == "default")
                        {
                            _loc_2.put(_loc_3++,  _loc_5);
                        }
                    }
                }
                return _loc_2.get(0);
            }
            else if (randMods.length() > 0)
            {
                return randMods.get(0);
            }
            return null;
        }//end

        public XMLList  randomModifiersXmlList ()
        {
            return m_xml.randomModifiers;
        }//end

        public XML  getRandomModifiersXmlByName (String param1 )
        {
            modifiersName = param1;
            XML table ;
            if (this.randomModifiersXmlList.length() > 0 && this.randomModifiersXmlList.hasOwnProperty("@name"))
            {
                int _loc_4 =0;
                _loc_5 = this.randomModifiersXmlList;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == modifiersName)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                table = _loc_3.get(0);
            }
            return table;
        }//end

        public String  special_onclick ()
        {
            if (this.m_special_onclick)
            {
                return this.m_special_onclick;
            }
            return null;
        }//end

        public XMLList  randomModifierGroupsXml ()
        {
            return m_xml.randomModifierGroups.group;
        }//end

        public int  npcValue ()
        {
            if (m_xml.hasOwnProperty("npcValue"))
            {
                return m_xml.npcValue;
            }
            return 1;
        }//end

        public int  carTier ()
        {
            if (m_xml.hasOwnProperty("tier"))
            {
                return m_xml.tier;
            }
            return 1;
        }//end

        public XMLList  navigateXml ()
        {
            _loc_1 =Global.gameSettings().getItemByNameAndThemes(m_name ,Global.world.currentThemes );
            if (_loc_1 == null)
            {
                _loc_1 = this;
            }
            return _loc_1.xml.navigate;
        }//end

        public XMLList  smokeXml ()
        {
            return m_xml.smoke;
        }//end

        public XMLList  deliveryXml ()
        {
            return m_xml.delivery;
        }//end

        public XML  commodityXml ()
        {
            XML result ;
            if (m_xml.commodity.length() > 1)
            {
                int _loc_3 =0;
                _loc_4 = m_xml.commodity;
                XMLList _loc_2 =new XMLList("");
                Object _loc_5;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);


                    with (_loc_5)
                    {
                        if (@default == "1")
                        {
                            _loc_2.put(_loc_3++,  _loc_5);
                        }
                    }
                }
                //result = _loc_2;
                result = m_xml.commodity.get(0);
            }
            else if (m_xml.commodity.length() > 0)
            {
                result = m_xml.commodity.get(0);
            }
            return result;
        }//end

        private Object  getDefaultCommodity ()
        {
            Object _loc_1 =null ;
            if (this.m_commodities.length > 1)
            {
                for(int i0 = 0; i0 < this.m_commodities.size(); i0++)
                {
                	_loc_1 = this.m_commodities.get(i0);
                    if ( _loc_1 == null) {
                    	continue;
                    }

                    if (_loc_1.default)
                    {
                        return _loc_1;
                    }
                }
            }
            else if (this.m_commodities.length > 0)
            {
                return this.m_commodities.get(0);
            }
            return null;
        }//end

        public String  commodityName ()
        {
            String _loc_1 ="";
            _loc_2 = this.getDefaultCommodity();
            if (_loc_2)
            {
                _loc_1 = _loc_2.name;
            }
            return _loc_1;
        }//end

        public double  commodityCapacity ()
        {
            double _loc_1 =0;
            _loc_2 = this.getDefaultCommodity ();
            if (_loc_2)
            {
                _loc_1 = _loc_2.capacity;
            }
            return _loc_1;
        }//end

        public double  commodityProduction ()
        {
            double _loc_1 =0;
            _loc_2 = this.getDefaultCommodity();
            if (_loc_2)
            {
                _loc_1 = _loc_2.produces;
            }
            return _loc_1;
        }//end

        public Array  commodities ()
        {
            return this.m_commodities;
        }//end

        public double  maxPlots ()
        {
            return this.m_maxPlots;
        }//end

        public double  bonusPercent ()
        {
            return this.m_bonusPercent;
        }//end

        public double  waterLength ()
        {
            return this.m_waterLength;
        }//end

        public String  landSquares ()
        {
            return this.m_landSquares;
        }//end

        public String  dockSquares ()
        {
            return this.m_dockSquares;
        }//end

        public String  berthSquares ()
        {
            return this.m_berthSquares;
        }//end

        public String  freeShipSquares ()
        {
            return this.m_freeShipSquares;
        }//end

        public String  freeShipType ()
        {
            return this.m_freeShipType;
        }//end

        public String  requiredShip ()
        {
            return this.m_requiredShip;
        }//end

        public String  emptyNPCType ()
        {
            return this.m_emptyNPCType;
        }//end

        public String  loadedNPCType ()
        {
            return this.m_loadedNPCType;
        }//end

        public String  clickSound ()
        {
            return this.m_clickSound;
        }//end

        public String  validGiftTimeRangeStart ()
        {
            return this.m_validGiftTimeRangeStart;
        }//end

        public String  validGiftTimeRangeEnd ()
        {
            return this.m_validGiftTimeRangeEnd;
        }//end

        public int  inventoryLimit ()
        {
            return this.m_InventoryLimit;
        }//end

        public String  unwrapIcon ()
        {
            return this.m_UnwrapIcon;
        }//end

        public boolean  playerLocked ()
        {
            return !m_xml.@playerLocked || m_xml.@playerLocked == false;
        }//end

        public XMLList  rawImageXml ()
        {
            return m_xml.get("image");
        }//end

        public String  storageType ()
        {
            return this.m_storageType;
        }//end

        public String  storageKey ()
        {
            return this.m_storageKey;
        }//end

        public int  storageInitCapacity ()
        {
            return this.m_storageInitCapacity;
        }//end

        public int  storageMaxCapacity ()
        {
            return this.m_storageMaxCapacity;
        }//end

        public UpgradeDefinition  upgrade ()
        {
            return this.m_upgrade;
        }//end

        public String  derivedItemName ()
        {
            return this.m_derivedItem;
        }//end

        public int  waterNeeded ()
        {
            return this.m_waterNeeded;
        }//end

        public int  landNeeded ()
        {
            return this.m_landNeeded;
        }//end

        public Array  category ()
        {
            return this.m_category;
        }//end

        public boolean  isNew ()
        {
            return this.m_isNew;
        }//end

        public void  isNew (boolean param1 )
        {
            this.m_isNew = param1;
            return;
        }//end

        public Array  marketKeywords ()
        {
            return this.m_marketKeywords;
        }//end

        public void  marketKeywords (Array param1 )
        {
            this.m_marketKeywords = param1;
            return;
        }//end

        public boolean  isAtMaxUpgradeLevel ()
        {
            if (!this.upgrade)
            {
                return true;
            }
            _loc_1 = UpgradeDefinition.getFullUpgradeChain(Item.findUpgradeRoot(this));
            if (_loc_1 && _loc_1.length > 0)
            {
                if (_loc_1.get((_loc_1.length - 1)) == this)
                {
                    return true;
                }
                return false;
            }
            return true;
        }//end

        public String  itemUpgradeDescendantName ()
        {
            if (this.m_upgrade)
            {
                return this.m_upgrade.newItemName;
            }
            return null;
        }//end

        public String  headquartersName ()
        {
            return this.m_headquartersName;
        }//end

        public double  heightLimit ()
        {
            return this.m_heightLimit;
        }//end

        public int  trainTripTime ()
        {
            return this.m_trainTripTime;
        }//end

        public int  trainMinStops ()
        {
            return this.m_trainMinStops;
        }//end

        public int  trainSpeedUpCost ()
        {
            return this.m_trainSpeedUpCost;
        }//end

        public int  trainStorage ()
        {
            return this.m_trainStorage;
        }//end

        public double  trainSellPrice ()
        {
            return this.m_trainSellPrice;
        }//end

        public double  trainBuyPrice ()
        {
            return this.m_trainBuyPrice;
        }//end

        public String  trainDestination ()
        {
            return this.m_trainDestination;
        }//end

        public String  trainPayout ()
        {
            return this.m_trainPayout;
        }//end

        public String  feed ()
        {
            return this.m_feed;
        }//end

        public int  feedImageIndex ()
        {
            return this.m_feedImageIndex;
        }//end

        public String  rarity ()
        {
            return this.m_rarity;
        }//end

        public String  unlocksItem ()
        {
            return this.m_unlocksItem;
        }//end

        public Class  getClass ()
        {
            _loc_1 = ItemClassDefinitions.getClassByItem(this);
            return _loc_1;
        }//end

         protected String  getFriendlyNameLocFile ()
        {
            return "Items";
        }//end

        protected String  getObjectTokenLocFile ()
        {
            return "Rewards";
        }//end

        public void  addImageToCache (Object param1 )
        {
            this.m_cachedImages.push(param1);
            return;
        }//end

        public Array  getThemeCollection ()
        {
            return this.m_themeCollection;
        }//end

        public Array  spawnNpcs ()
        {
            return this.m_spawnNpcs;
        }//end

        public Array  spawnNpcWeights ()
        {
            return this.m_spawnNpcWeights;
        }//end

        public boolean  isEmptyLot ()
        {
            return this.className == "LotSite";
        }//end

        public String  getRandomSpawnNpc ()
        {
            int _loc_1 =0;
            String _loc_2 =null ;
            if (this.m_spawnNpcs != null && this.m_spawnNpcs.length > 0)
            {
                _loc_1 = MathUtil.randomIndexWeighed(this.m_spawnNpcWeights);
                _loc_2 = this.m_spawnNpcs.get(_loc_1);
                if (_loc_2 != "")
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public XMLList  mechanicsXml ()
        {
            XMLList _loc_2 =null ;
            _loc_1 = Global.experimentManager.filterXmlByExperiment(xml.mechanics);
            if (_loc_1.children().length() != 0)
            {
                _loc_2 = Global.experimentManager.filterXmlByExperiment(_loc_1.children());
                _loc_1.setChildren(_loc_2);
            }
            return _loc_1;
        }//end

        public MechanicConfigData  getClientDisplayMechanicConfig (String param1 )
        {
            MechanicConfigData config ;
            XML mechXML ;
            mechType = param1;
            //config;
            int _loc_4 =0;
            _loc_5 = this.mechanicsXml.child("clientDisplayMechanics").child("mechanic");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("type") == mechType)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            mechXML = _loc_3.get(0);
            if (mechXML)
            {
                config = new MechanicConfigData(mechXML);
            }
            return config;
        }//end

        public MechanicConfigData  getGameEventMechanicConfig (String param1 ,String param2 )
        {
            MechanicConfigData config ;
            XML mechXML ;
            mechGameMode = param1;
            mechType = param2;
            //config;
            int _loc_5 =0;
            int _loc_8 =0;
            _loc_9 = this.mechanicsXml.child("gameEventMechanics");
            XMLList _loc_7 =new XMLList("");
            Object _loc_10;
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            	_loc_10 = _loc_9.get(i0);


                with (_loc_10)
                {
                    if (attribute("gameMode") == mechGameMode)
                    {
                        _loc_7.put(_loc_8++,  _loc_10);
                    }
                }
            }
            _loc_6 = _loc_7.child("mechanic");
            XMLList _loc_4 =new XMLList("");
            Object _loc_77;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_77 = _loc_6.get(i0);


                with (_loc_77)
                {
                    if (attribute("type") == mechType)
                    {
                        _loc_4.put(_loc_5++,  _loc_77);
                    }
                }
            }
            mechXML = _loc_4.get(0);
            if (mechXML)
            {
                config = new MechanicConfigData(mechXML);
            }
            return config;
        }//end

        public MechanicConfigData  getMechanicConfigByEventAndName (String param1 ,String param2 )
        {
            MechanicConfigData config ;
            XML mechXML ;
            mechGameEvent = param1;
            mechName = param2;
            //config;
            int _loc_5 =0;
            int _loc_8 =0;
            _loc_9 = this.mechanicsXml.child("gameEventMechanics");
            XMLList _loc_7 =new XMLList("");
            Object _loc_10;
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            	_loc_10 = _loc_9.get(i0);


                with (_loc_10)
                {
                    if (@gameMode == mechGameEvent)
                    {
                        _loc_7.put(_loc_8++,  _loc_10);
                    }
                }
            }
            _loc_6 = _loc_7.child("mechanic");
            XMLList _loc_4 =new XMLList("");
            Object _loc_77;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_77 = _loc_6.get(i0);


                with (_loc_77)
                {
                    if (@className == mechName)
                    {
                        _loc_4.put(_loc_5++,  _loc_77);
                    }
                }
            }
            mechXML = _loc_4.get(0);
            if (mechXML)
            {
                config = new MechanicConfigData(mechXML);
            }
            return config;
        }//end

        public MechanicPackData  getAvailableMechanicPackData (Object param1 ,String param2 )
        {
            boolean _loc_3 =false ;
            MechanicPackData _loc_4 =null ;
            if (!this.m_mechanicPackData)
            {
                this.m_mechanicPackData = this.parseMechanicPackData();
            }
            if (param2)
            {
                _loc_3 = false;
                for(int i0 = 0; i0 < this.m_mechanicPackData.size(); i0++)
                {
                	_loc_4 = this.m_mechanicPackData.get(i0);

                    if (_loc_4.name == param2)
                    {
                        _loc_3 = true;
                    }
                    if (!_loc_3 && _loc_4.name != param2)
                    {
                        continue;
                    }
                    if (_loc_4.validatorName && !this.getValidation(_loc_4.validatorName).validate(param1))
                    {
                        continue;
                    }
                    return _loc_4;
                }
            }
            else
            {
                for(int i0 = 0; i0 < this.m_mechanicPackData.size(); i0++)
                {
                		_loc_4 = this.m_mechanicPackData.get(i0);

                    if (_loc_4.isDefault() && _loc_4.validatorName && this.getValidation(_loc_4.validatorName).validate(param1))
                    {
                        return _loc_4;
                    }
                    if (_loc_4.isDefault())
                    {
                        break;
                    }
                }
                for(int i0 = 0; i0 < this.m_mechanicPackData.size(); i0++)
                {
                	_loc_4 = this.m_mechanicPackData.get(i0);

                    if (_loc_4.isDefault())
                    {
                        continue;
                    }
                    if (_loc_4.validatorName && !this.getValidation(_loc_4.validatorName).validate(param1))
                    {
                        continue;
                    }
                    return _loc_4;
                }
            }
            return null;
        }//end

        protected Array  parseMechanicPackData ()
        {
            XML _loc_3 =null ;
            Array _loc_1 =new Array();
            _loc_2 = m_xml.mechanicPacks.mechanicPack;
            if (_loc_2 && _loc_2.length() > 0)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    _loc_1.push(new MechanicPackData(_loc_3));
                }
            }
            return _loc_1;
        }//end

        public boolean  canCountUpgradeActions ()
        {
            return this.upgrade && this.upgrade.requirements && this.upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS) != null;
        }//end

        public Array  getItemKeywords ()
        {
            return this.m_keywords;
        }//end

        public Array  getVisibleItemKeywords ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_keywords.size(); i0++)
            {
            	_loc_2 = this.m_keywords.get(i0);

                if (Global.gameSettings().keywordVisible(_loc_2))
                {
                    _loc_1.push(_loc_2);
                }
            }
            return _loc_1;
        }//end

        public Array  getMarketVisibleItemKeywords ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_keywords.size(); i0++)
            {
            	_loc_2 = this.m_keywords.get(i0);

                if (Global.gameSettings().keywordMarketVisible(_loc_2))
                {
                    _loc_1.push(_loc_2);
                }
            }
            return _loc_1;
        }//end

        public boolean  itemHasKeyword (String param1 )
        {
            String _loc_3 =null ;
            _loc_2 = Global.gameSettings().keywordID(param1);
            for(int i0 = 0; i0 < this.m_keywords.size(); i0++)
            {
            	_loc_3 = this.m_keywords.get(i0);

                if (_loc_2 == Global.gameSettings().keywordID(_loc_3))
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  doesKeywordMatch (String param1 )
        {
            boolean _loc_3 =false ;
            String _loc_4 =null ;
            _loc_2 = processKeywordQuery(param1);
            if (_loc_2.union)
            {
                _loc_3 = true;
                for(int i0 = 0; i0 < _loc_2.keywords.size(); i0++)
                {
                	_loc_4 = _loc_2.keywords.get(i0);

                    if (!this.itemHasKeyword(_loc_4))
                    {
                        _loc_3 = false;
                        break;
                    }
                }
                return _loc_3;
            }
            else
            {
                for(int i0 = 0; i0 < _loc_2.keywords.size(); i0++)
                {
                	_loc_4 = _loc_2.keywords.get(i0);

                    if (this.itemHasKeyword(_loc_4))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public String  placeableOn ()
        {
            return this.m_placeableOn;
        }//end

        public int  isPixelInsideAlphaThresholdOveride ()
        {
            return this.m_isPixelInsideAlphaThresholdOveride;
        }//end

        public int  showGirders ()
        {
            return this.m_showGirders;
        }//end

        public ItemDefinitionBridge  bridge ()
        {
            return this.m_item_bridgeDefn;
        }//end

        public String  mysteryItem ()
        {
            return this.m_mysteryItem;
        }//end

        public InventoryChecksConfig  inventoryChecksConfig ()
        {
            return this.m_inventoryChecksConfig;
        }//end

        public String  moveInVehicle ()
        {
            return this.m_moveInVehicle;
        }//end

        public ItemDefinitionHotel  hotel ()
        {
            return this.m_item_hotelDefn;
        }//end

        public String  marketQuestName ()
        {
            return this.m_marketQuest.get("name") ? (this.m_marketQuest.get("name")) : (null);
        }//end

        public boolean  marketQuestBegin ()
        {
            return this.m_marketQuest.get("begin") ? (this.m_marketQuest.get("begin")) : (false);
        }//end

        public String  harvestIcon ()
        {
            return this.m_harvestIcon;
        }//end

        public boolean  hasFireworks ()
        {
            return this.m_firework;
        }//end

        public Point  pickOffset ()
        {
            return this.m_pickOffset;
        }//end

        public Array  storables ()
        {
            return this.m_storables;
        }//end

        public int  quantity ()
        {
            return this.m_quantity;
        }//end

        public String  wonderName ()
        {
            return this.m_wonderName;
        }//end

        public Array  requiredLandmarks ()
        {
            return this.m_requiredLandmarks;
        }//end

        public String  prerequisiteSaga ()
        {
            return this.m_prerequisiteSaga;
        }//end

        public String  UI_skin ()
        {
            return this.m_UI_skin;
        }//end

        public String  getUI_skin_param (String param1 )
        {
            return this.m_UI_skin_params.get(param1);
        }//end

        public boolean  marketBuyable ()
        {
            return this.m_marketBuyable;
        }//end

        public Array  blockingExperiments ()
        {
            return this.m_blockingExperiments;
        }//end

        public boolean  blockedByExperiments ()
        {
            String _loc_1 =null ;
            int _loc_2 =0;
            for(int i0 = 0; i0 < this.m_blockingExperiments.size(); i0++)
            {
            		_loc_1 = this.m_blockingExperiments.get(i0);

                _loc_2 = Global.experimentManager.getVariant(_loc_1);
                if (_loc_2 == 0)
                {
                    return true;
                }
            }
            return false;
        }//end

        public Object  getExperimentTuningsByFieldName (String param1 )
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            Array _loc_6 =null ;
            Object _loc_2 ={opcode Item.EXPERIMENT_TUNING_OPCODE_MUL ,operand "1"};
            if (this.m_experimentTunings != null && this.m_experimentTunings.get(param1) != null)
            {
                _loc_3 = this.m_experimentTunings.get(param1);
                _loc_4 = _loc_3.experimentName;
                _loc_5 = Global.experimentManager.getVariant(_loc_4);
                _loc_6 = _loc_3.variants;
                if (_loc_6.get(_loc_5) != null)
                {
                    _loc_2 = _loc_6.get(_loc_5);
                }
            }
            return _loc_2;
        }//end

        protected String  applyExperimentTuning (String param1 ,Object param2 )
        {
            _loc_3 = param1;
            if (param2.hasOwnProperty("opcode") && param2.hasOwnProperty("operand"))
            {
                switch(param2.opcode)
                {
                    case Item.EXPERIMENT_TUNING_OPCODE_MUL:
                    {
                        _loc_3 = String(Number(param1) * Number(param2.operand));
                        break;
                    }
                    case Item.EXPERIMENT_TUNING_OPCODE_ADD:
                    {
                        _loc_3 = String(Number(param1) + Number(param2.operand));
                        break;
                    }
                    case Item.EXPERIMENT_TUNING_OPCODE_SET:
                    {
                        _loc_3 = param2.operand;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_3;
        }//end

        public double  getDooberMinimums (String param1 ,String param2 )
        {
            return this.m_dooberMinimumsCache.get(param1, param2);
        }//end

        private double  getDooberMinimumsImpl (String param1 ,String param2 )
        {
            XML modifier ;
            XMLList modifierTable ;
            XMLList multis ;
            double tableMultiplier ;
            XML roll ;
            String experimentName ;
            String variantsStr ;
            Array variants ;
            String userVariant ;
            double rollTotal ;
            boolean gotTotal ;
            XML dooberXML ;
            String dooberName ;
            double dooberAmount ;
            type = param1;
            modifiersName = param2;
            randomModifiersXml = this.randomModifiersXml;
            if (modifiersName)
            {
                randomModifiersXml = this.getRandomModifiersXmlByName(modifiersName);
            }
            strCheckType = type;
            if (randomModifiersXml == null)
            {
                return -1;
            }
            if (type == Doober.DOOBER_COLLECTABLE || type == Doober.DOOBER_REP)
            {
                return -1;
            }
            modifierTables = Global.gameSettings().getRandomModifierTables();
            randomModifiers = randomModifiersXml;
            double returnValue ;
            boolean found ;
            int _loc_4 =0;
            _loc_5 = randomModifiersXml.modifier;
            for(int i0 = 0; i0 < randomModifiersXml.modifier.size(); i0++)
            {
            	modifier = randomModifiersXml.modifier.get(i0);


                int _loc_7 =0;
                _loc_8 = modifierTables.randomModifierTable;
                XMLList _loc_6 =new XMLList("");
                for(int i0 = 0; i0 < _loc_8.size(); i0++)
                {
                	_loc_9 = _loc_8.get(i0);


                    with (_loc_9)
                    {
                        if (@name == modifier.@tableName)
                        {
                            _loc_6.put(_loc_7++,  _loc_9);
                        }
                    }
                }
                modifierTable = _loc_6;
                if (!modifierTable.get(0))
                {
                    continue;
                }
                if (modifier.attribute("experimentName").length() > 0)
                {
                    experimentName = modifier.attribute("experimentName").toString();
                    variantsStr = modifier.attribute("variants").toString();
                    variants = variantsStr.split(",");
                    userVariant = String(Global.experimentManager.getVariant(experimentName));
                    if (variants.indexOf(userVariant) < 0)
                    {
                        continue;
                    }
                }
                multis = (XMLList)modifier.@multiplier;
                tableMultiplier = multis != null && multis.length() > 0 ? (Number(multis)) : (1);
                int _loc_61 =0;
                _loc_71 = modifierTable.get(0).roll ;
                for(int i0 = 0; i0 < modifierTable.get(0).roll.size(); i0++)
                {
                	roll = modifierTable.get(0).roll.get(i0);


                    rollTotal = 0;
                    gotTotal = false;
                    int _loc_81 =0;
                    _loc_9 = roll.children();
                    for(int i0 = 0; i0 < roll.children().size(); i0++)
                    {
                    		dooberXML = roll.children().get(i0);


                        dooberName = dooberXML.localName();
                        dooberAmount = Number(parseFloat(dooberXML.@amount));
                        if (dooberName == Doober.DOOBER_ITEM)
                        {
                            dooberAmount;
                        }
                        if (dooberName == strCheckType)
                        {
                            rollTotal = rollTotal + dooberAmount;
                            gotTotal = true;
                        }
                    }
                    if (!gotTotal)
                    {
                        continue;
                    }
                    rollTotal = rollTotal * tableMultiplier;
                    if (!found)
                    {
                        returnValue = rollTotal;
                        found = true;
                        continue;
                    }
                    returnValue = Math.min(returnValue, rollTotal);
                }
            }
            return returnValue;
        }//end

        public Object  getPlacementBoundaryByName (String param1 )
        {
            if (!this.m_placementBoundaries.hasOwnProperty(param1))
            {
                return null;
            }
            Object _loc_2 =null ;
            if (this.m_placementBoundaries.hasOwnProperty(param1))
            {
                _loc_2 = this.parsePlacementBoundary(this.m_placementBoundaries.get(param1));
            }
            return _loc_2;
        }//end

        public Dictionary  getAllPlacementBoundaries ()
        {
            String _loc_2 =null ;
            Dictionary _loc_1 =new Dictionary ();
            for(int i0 = 0; i0 < this.m_placementBoundaries.size(); i0++)
            {
            		_loc_2 = this.m_placementBoundaries.get(i0);

                _loc_1.put(_loc_2, this.parsePlacementBoundary(_loc_2));
            }
            return _loc_1;
        }//end

        protected Object  parsePlacementBoundary (String param1 )
        {
            if (!this.m_placementBoundaries.hasOwnProperty(param1))
            {
                return null;
            }
            _loc_2 = this.m_placementBoundaries.get(param1).split(",");
            Object _loc_3 =new Object ();
            _loc_3.top = _loc_2.get(0);
            _loc_3.bottom = _loc_2.get(1);
            _loc_3.left = _loc_2.get(2);
            _loc_3.right = _loc_2.get(3);
            return _loc_3;
        }//end

        public void  updateXML (XML param1 )
        {
            m_xml = param1;
            return;
        }//end

        public void  updateMechanicPackName (String param1 )
        {
            this.m_currentMechanicPackName = param1;
            return;
        }//end

        public String  getCurrentMechanicPackName ()
        {
            return this.m_currentMechanicPackName;
        }//end

        public int  harvestMultiplier ()
        {
            return this.m_harvestMultiplier;
        }//end

        public static String  findUpgradeParent (Item param1 )
        {
            Item _loc_2 =null ;
            if (param1.m_upgradedFromItemName == UNKNOWN_ITEM_NAME)
            {
                param1.m_upgradedFromItemName = null;
                for(int i0 = 0; i0 < Global.gameSettings().getItemsByType(param1.type).size(); i0++)
                {
                	_loc_2 = Global.gameSettings().getItemsByType(param1.type).get(i0);

                    if (_loc_2 != param1 && _loc_2.upgrade != null && _loc_2.upgrade.newItemName == param1.name)
                    {
                        param1.m_upgradedFromItemName = _loc_2.name;
                        break;
                    }
                }
            }
            return param1.m_upgradedFromItemName;
        }//end

        public static String  findUpgradeRoot (Item param1 )
        {
            Item _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (param1.m_upgradeRootName == UNKNOWN_ITEM_NAME)
            {
                _loc_2 = param1;
                while (_loc_2 != null)
                {

                    _loc_3 = _loc_2.name;
                    _loc_4 = Item.findUpgradeParent(_loc_2);
                    _loc_2 = Global.gameSettings().getItemByName(_loc_4);
                }
                param1.m_upgradeRootName = _loc_3;
            }
            return param1.m_upgradeRootName;
        }//end

        public static Object  processKeywordQuery (String param1 )
        {
            Array keywordArray ;
            Array orArray ;
            query = param1;
            boolean union ;
            trimFunc = function(param1String,param2,param3)
            {
                _loc_4 = param1.replace (/^\s+|\s+$""^\s+|\s+$/g, "");
                return param1.replace(/^\s+|\s+$""^\s+|\s+$/g, "");
            }//end
            ;
            andArray = query.split("AND");
            if (andArray.length > 1)
            {
                union;
                keywordArray = andArray.map(trimFunc);
            }
            else
            {
                orArray = query.split("OR");
                keywordArray = orArray.map(trimFunc);
            }
            Object processedQuery =new Object ();
            processedQuery.union = union;
            processedQuery.keywords = keywordArray;
            return processedQuery;
        }//end

        public static XML  preprocessXML (XML param1 )
        {
            XML _loc_2 =null ;
            Function _loc_3 =null ;
            for(int i0 = 0; i0 < s_xmlPreprocessSteps.size(); i0++)
            {
            		_loc_3 = s_xmlPreprocessSteps.get(i0);

                _loc_2 = _loc_3(param1);
                if (_loc_2 != null)
                {
                    param1 = _loc_2;
                    Global.gameSettings().addOrReplaceItemXMLByName(param1);
                }
            }
            return param1;
        }//end

        private static int  parseSale (XML param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_5 =null ;
            if (param1 == null)
            {
                return 0;
            }
            _loc_2 = param1.variant;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    _loc_4 = _loc_3.@experimentName.get(0);
                    if (_loc_4 == null || _loc_4.length == 0)
                    {
                        continue;
                    }
                    _loc_5 = parseIntList(_loc_3.@experimentVariants.get(0));
                    if (_loc_5 == null)
                    {
                        continue;
                    }
                    if (_loc_5.indexOf(Global.experimentManager.getVariant(_loc_4)) >= 0)
                    {
                        return parseInt(_loc_3.toString());
                    }
                }
            }
            return parseInt(param1.toString());
        }//end

        private static Array  parseIntList (String param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_2 = param1.split(",");
            _loc_3 = _loc_2.length;
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_2.put(_loc_4,  parseInt(_loc_2.get(_loc_4)));
                _loc_4++;
            }
            return _loc_2;
        }//end

    }




