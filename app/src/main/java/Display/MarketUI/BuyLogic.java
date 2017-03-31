package Display.MarketUI;

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
import Classes.sim.*;
import Modules.franchise.data.*;
import Modules.remodel.*;
import Modules.stats.experiments.*;
import ZLocalization.*;

    public class BuyLogic
    {

        public  BuyLogic ()
        {
            return;
        }//end

        public static boolean  friendLevelCheck (Item param1 )
        {
            Friend _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            if (Global.isVisiting())
            {
                for(int i0 = 0; i0 < Global.friendbar.size(); i0++)
                {
                		_loc_2 = Global.friendbar.get(i0);

                    if (_loc_2.m_uid == Global.world.ownerId)
                    {
                        _loc_4 = _loc_2.m_level;
                        break;
                    }
                }
                _loc_3 = param1.requiredLevel;
                if (_loc_3 > _loc_4)
                {
                    return false;
                }
            }
            return true;
        }//end

        public static boolean  franchiseCheck (Item param1 )
        {
            Item _loc_3 =null ;
            MapResource _loc_4 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.CONSTRUCTION_SITE ,WorldObjectTypes.BUSINESS) );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_3 = Global.gameSettings().getItemByName(Item.findUpgradeRoot(_loc_4.getItem()));
                if (_loc_4.itemOwner == Global.player.uid)
                {
                    if (_loc_3.name == param1.name)
                    {
                        return false;
                    }
                    if (_loc_4 instanceof ConstructionSite && (_loc_4 as ConstructionSite).targetClass == Business && (_loc_4 as ConstructionSite).targetName == param1.name)
                    {
                        return false;
                    }
                }
            }
            return true;
        }//end

        public static boolean  hasBaseItemForSkinUnlock (Item param1 )
        {
            _loc_2 = param1.derivedItemName =="res_beachfrontapt_a"|| param1.derivedItemName == "res_simpsonmegabrick";
            if (isLocked(param1) && _loc_2 && !RemodelManager.hasRemodelEligibleResidence(param1))
            {
                return true;
            }
            return false;
        }//end

        public static boolean  lotSiteCheck (Item param1 )
        {
            if (param1.isEmptyLot)
            {
                return !maxReceivedFranchisesCheckDefault();
            }
            return true;
        }//end

        public static boolean  canFranchise (Item param1 )
        {
            return param1.canFranchise;
        }//end

        public static boolean  hasFranchiseCheck (Item param1 )
        {
            _loc_2 =Global.franchiseManager.model.getOwnedFranchise(param1.name );
            if (_loc_2)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  franchiseCountLevelCheck (Item param1 )
        {
            _loc_2 =Global.franchiseManager.model.getOwnsFranchise(param1.name );
            _loc_3 = _loc_2&& Global.franchiseManager.getFranchise(param1.name).getLocationCount() > 0;
            if (_loc_3)
            {
                return true;
            }
            return !Global.franchiseManager.isFranchiseLocked();
        }//end

        public static int  getFranchisesCount (String param1 )
        {
            Business _loc_4 =null ;
            Array _loc_5 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.BUSINESS) );
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                if (_loc_4.itemOwner != param1)
                {
                    _loc_3++;
                }
            }
            _loc_5 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.LOT_SITE));
            _loc_3 = _loc_3 + _loc_5.length;
            return _loc_3;
        }//end

        public static boolean  canAcceptFranchiseRequests (String param1 )
        {
            Business _loc_4 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.BUSINESS) );
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                if (_loc_4.itemOwner != param1)
                {
                    _loc_3++;
                }
            }
            if (_loc_3 < maxFranchiseCount)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  maxReceivedFranchisesCheck (Item param1 )
        {
            if (param1.className == "LotSite")
            {
                return maxReceivedFranchisesCheckDefault();
            }
            return false;
        }//end

        public static boolean  maxReceivedFranchisesCheckDefault ()
        {
            int _loc_2 =0;
            _loc_1 = getFranchisesCount(Global.player.uid);
            _loc_2 = maxAllowedFranchiseCount;
            if (_loc_1 >= _loc_2)
            {
                return true;
            }
            return false;
        }//end

        public static boolean  isAtMaxFranchiseCount ()
        {
            _loc_1 = getFranchisesCount(Global.player.uid);
            if (_loc_1 >= maxFranchiseCount)
            {
                return true;
            }
            return false;
        }//end

        public static int  maxFranchiseCount ()
        {
            return Global.gameSettings().getInt("maxReceivedFranchises");
        }//end

        public static int  maxAllowedFranchiseCount ()
        {
            return Math.min(Global.gameSettings().getInt("maxReceivedFranchises"), maxAllowedFranchiseCountFromNeighbors + Global.player.franchiseUnlocksPurchased);
        }//end

        public static int  maxAllowedFranchiseCountFromNeighbors ()
        {
            return Math.min(Global.gameSettings().getInt("maxReceivedFranchises"), (Math.floor(Global.player.maxNeighbors / Global.gameSettings().getInt("neighborsPerLotsite")) + 1));
        }//end

        public static int  getNeededNeighborCountForNextFranchise ()
        {
            int _loc_1 =0;
            _loc_1 = maxAllowedFranchiseCountFromNeighbors;
            _loc_2 = _loc_1*Global.gameSettings().getInt("neighborsPerLotsite");
            return _loc_2 - Global.player.maxNeighbors;
        }//end

        public static int  getNextFranchiseUnlockCost ()
        {
            return Global.gameSettings().getInt("franchiseUnlockCost");
        }//end

        public static boolean  isLocked (Item param1 )
        {
            int _loc_3 =0;
            Item _loc_4 =null ;
            int _loc_5 =0;
            boolean _loc_2 =true ;
            return false;

            if (param1.isRemodelSkin() && !RemodelManager.isFeatureAvailable())
            {
                return true;
            }
            if (param1.derivedItemName == "res_simpsonmegabrick" && !RemodelManager.hasRemodelEligibleResidence(param1))
            {
                return true;
            }
            if (param1.derivedItemName == "res_beachfrontapt_a" && !RemodelManager.hasRemodelEligibleResidence(param1))
            {
                return true;
            }
            if (param1.isRemodelSkin() && isLocked(Global.gameSettings().getItemByName(param1.derivedItemName)))
            {
                return true;
            }
            if (Global.player.checkLicense(param1.name))
            {
                return false;
            }
            if (param1.isEmptyLot)
            {
                return !lotSiteCheck(param1);
            }
            switch(param1.unlock)
            {
                case Item.UNLOCK_LOCKED:
                {
                    _loc_2 = true;
                    break;
                }
                case Item.UNLOCK_LEVEL:
                {
                    _loc_2 = Global.player.level < param1.requiredLevel;
                    break;
                }
                case Item.UNLOCK_NEIGHBOR:
                {
                    _loc_2 = !Global.player.checkNeighbors(param1.requiredNeighbors);
                    break;
                }
                case Item.UNLOCK_PERMITS:
                {
                    _loc_2 = !ExpansionManager.instance.hasPassedExpansionGate();
                    if (!_loc_2 && param1.requiredQuestFlag.length > 0)
                    {
                        if (!Global.questManager.isFlagReached(param1.requiredQuestFlag))
                        {
                            _loc_2 = true;
                        }
                    }
                    break;
                }
                case Item.UNLOCK_OLD_QUEST_OR_PURCHASE:
                case Item.UNLOCK_QUEST_FLAG:
                {
                    _loc_2 = !Global.questManager.isFlagReached(param1.requiredQuestFlag);
                    break;
                }
                case Item.UNLOCK_SEEN_FLAG:
                {
                    _loc_2 = !Global.player.getSeenFlag(param1.requiredSeenFlag) && !Global.player.getSeenSessionFlag(param1.requiredSeenFlag);
                    break;
                }
                case Item.UNLOCK_NOT_SEEN_FLAG:
                {
                    _loc_2 = Global.player.getSeenFlag(param1.requiredSeenFlag) || Global.player.getSeenSessionFlag(param1.requiredSeenFlag);
                    break;
                }
                case Item.UNLOCK_POPULATION:
                {
                    _loc_2 = !Global.player.checkPopulation(param1.requiredPopulation, param1.populationType);
                    break;
                }
                case Item.UNLOCK_QUEST_AND_LEVEL:
                {
                    if (param1.requiredQuestFlag.length > 0)
                    {
                        _loc_2 = Global.player.level < param1.requiredLevel || !Global.questManager.isFlagReached(param1.requiredQuestFlag);
                    }
                    else
                    {
                        _loc_2 = Global.player.level < param1.requiredLevel;
                    }
                    break;
                }
                case Item.UNLOCK_QUEST_AND_NEIGHBOR:
                {
                    if (param1.requiredQuestFlag.length > 0)
                    {
                        _loc_2 = !Global.questManager.isFlagReached(param1.requiredQuestFlag) || !Global.player.checkNeighbors(param1.requiredNeighbors);
                    }
                    else
                    {
                        _loc_2 = !Global.player.checkNeighbors(param1.requiredNeighbors);
                    }
                    break;
                }
                case Item.UNLOCK_PERMIT_BUNDLE:
                {
                    _loc_3 = param1.itemRewards.length;
                    _loc_4 = Global.gameSettings().getItemByName(param1.itemRewards.get(0));
                    _loc_5 = Global.player.inventory.getItemCountByName(_loc_4.name);
                    if (_loc_3 + _loc_5 > _loc_4.inventoryLimit)
                    {
                        _loc_2 = true;
                    }
                    else
                    {
                        _loc_2 = false;
                    }
                    if (Global.player.inventory.spareCapacity < _loc_3)
                    {
                        _loc_2 = true;
                    }
                    break;
                }
                case Item.UNLOCK_OBJECT_COUNT:
                {
                    _loc_2 = Global.world.getObjectsByNames(param1.requiredObjects).length < param1.requiredObjectsCount;
                    break;
                }
                case Item.UNLOCK_CAN_ADD_POPULATION:
                {
                    _loc_2 = Global.world.citySim.getPopulation(param1.populationType) + param1.requiredCanAddPopulation > Global.world.citySim.getPopulationCap(param1.populationType);
                    break;
                }
                case Item.UNLOCK_LOCKED:
                {
                    _loc_2 = true;
                    break;
                }
                default:
                {
                    _loc_2 = Global.player.level < param1.requiredLevel;
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

        public static boolean  shouldShowCantBuyDialog (Item param1 )
        {
            _loc_2 = param1.unlock ==Item.UNLOCK_QUEST_FLAG || param1.unlock == Item.UNLOCK_QUEST_AND_LEVEL || param1.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR || param1.unlock == Item.UNLOCK_SEEN_FLAG;
            _loc_3 = param1.unlock ==Item.UNLOCK_NOT_SEEN_FLAG ;
            _loc_4 = param1.unlock ==Item.UNLOCK_OBJECT_COUNT || param1.unlock == Item.UNLOCK_CAN_ADD_POPULATION;
            return param1.unlock == Item.UNLOCK_OBJECT_COUNT || param1.unlock == Item.UNLOCK_CAN_ADD_POPULATION || _loc_2 && hasUnlockedFlag(param1) || _loc_3 && !hasUnlockedFlag(param1);
        }//end

        public static boolean  hasUnlockedFlag (Item param1 )
        {
            return param1.unlock != Item.UNLOCK_PERMIT_BUNDLE && param1.requiredQuestFlag.length > 0 && !Global.questManager.isFlagReached(param1.requiredQuestFlag) || param1.requiredSeenFlag.length > 0 && !Global.player.getSeenFlag(param1.requiredSeenFlag) && !Global.player.getSeenSessionFlag(param1.requiredSeenFlag);
        }//end

        private static boolean  areYouAPermitBundle (String param1 )
        {
            switch(param1)
            {
                case "permit_pack2":
                case "permit_pack3":
                case "permit_pack4":
                {
                    return true;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        public static Object  getUnlockText (Item param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            LocalizationObjectToken _loc_5 =null ;
            String _loc_8 =null ;
            Object _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            _loc_4 = ZLoc.t("Dialogs","RequiresText");
            _loc_6 = areYouAPermitBundle(param1.type);
            _loc_7 = param1.className =="LotSite";
            if (_loc_6)
            {
                _loc_4 = ZLoc.t("Dialogs", "ReachedText");
                _loc_2 = ZLoc.t("Dialogs", "MaxPermitsText");
                return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
            }
            if (_loc_7)
            {
                if (isAtMaxFranchiseCount())
                {
                    _loc_4 = ZLoc.t("Dialogs", "ReachedText");
                    _loc_2 = ZLoc.t("Dialogs", "MaxFranchisesText");
                    return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
                }
                _loc_4 = ZLoc.t("Dialogs", "InviteText");
                _loc_5 = ZLoc.tk("Dialogs", "Neighbor", "", getNeededNeighborCountForNextFranchise);
                _loc_2 = ZLoc.t("Dialogs", "neighbor_text", {amount:getNeededNeighborCountForNextFranchise, neighbor:_loc_5});
                return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
            }
            if (param1.isRemodelSkin() && !RemodelManager.isFeatureAvailable())
            {
                _loc_2 = ZLoc.t("Dialogs", "quest_text1");
                return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
            }
            if (param1.derivedItemName == "res_simpsonmegabrick" && !RemodelManager.hasRemodelEligibleResidence(param1))
            {
                _loc_2 = ZLoc.t("Items", "res_simpsonmegabrick_friendlyName");
                return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
            }
            if (param1.derivedItemName == "res_beachfrontapt_a" && !RemodelManager.hasRemodelEligibleResidence(param1))
            {
                _loc_2 = ZLoc.t("Items", "res_beachfrontapt_a_friendlyName");
                return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
            }
            switch(param1.unlock)
            {
                case Item.UNLOCK_LEVEL:
                {
                    _loc_2 = ZLoc.t("Dialogs", "level_text", {amount:param1.requiredLevel});
                    break;
                }
                case Item.UNLOCK_NEIGHBOR:
                {
                    _loc_4 = ZLoc.t("Dialogs", "InviteText");
                    _loc_5 = ZLoc.tk("Dialogs", "Neighbor", "", param1.requiredNeighbors);
                    _loc_2 = ZLoc.t("Dialogs", "neighbor_text", {amount:param1.requiredNeighbors, neighbor:_loc_5});
                    break;
                }
                case Item.UNLOCK_PERMITS:
                {
                    if (param1.requiredQuestFlag.length > 0 && !Global.questManager.isFlagReached(param1.requiredQuestFlag))
                    {
                        _loc_2 = ZLoc.t("Dialogs", "quest_text1");
                    }
                    else if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
                    {
                        _loc_10 = ExpansionManager.instance.getPopulationLabel();
                        _loc_11 = ExpansionManager.instance.getPermitsLabel();
                        if (_loc_10.length > 0 && _loc_11.length > 0)
                        {
                            _loc_2 = _loc_10 + "\n" + _loc_11;
                        }
                        else
                        {
                            _loc_2 = _loc_10 + _loc_11;
                        }
                    }
                    else
                    {
                        _loc_2 = ExpansionManager.instance.getUnlockLabel();
                    }
                    break;
                }
                case Item.UNLOCK_QUEST_FLAG:
                {
                    _loc_2 = ZLoc.t("Dialogs", "quest_text1");
                    break;
                }
                case Item.UNLOCK_SEEN_FLAG:
                case Item.UNLOCK_NOT_SEEN_FLAG:
                {
                    _loc_2 = ZLoc.t("Dialogs", param1.requiredSeenFlagText);
                    break;
                }
                case Item.UNLOCK_POPULATION:
                {
                    _loc_8 = "population_text1";
                    _loc_9 = {amount2:param1.requiredPopulationScaled};
                    if (Global.gameSettings().hasMultiplePopulations())
                    {
                        _loc_8 = "population_type_text1";
                        _loc_9.popType = PopulationHelper.getPopulationZlocType(param1.populationType);
                    }
                    _loc_2 = ZLoc.t("Dialogs", _loc_8, _loc_9);
                    break;
                }
                case Item.UNLOCK_QUEST_AND_LEVEL:
                {
                    if (param1.requiredQuestFlag.length > 0 && !Global.questManager.isFlagReached(param1.requiredQuestFlag))
                    {
                        _loc_2 = ZLoc.t("Dialogs", "quest_text1");
                    }
                    else
                    {
                        _loc_2 = ZLoc.t("Dialogs", "level_text", {amount:param1.requiredLevel});
                        _loc_3 = "";
                    }
                    break;
                }
                case Item.UNLOCK_QUEST_AND_NEIGHBOR:
                {
                    if (param1.requiredQuestFlag.length > 0 && !Global.questManager.isFlagReached(param1.requiredQuestFlag))
                    {
                        _loc_2 = ZLoc.t("Dialogs", "quest_text1");
                    }
                    else
                    {
                        _loc_4 = ZLoc.t("Dialogs", "InviteText");
                        _loc_5 = ZLoc.tk("Dialogs", "Neighbor", "", param1.requiredNeighbors);
                        _loc_2 = ZLoc.t("Dialogs", "neighbor_text", {amount:param1.requiredNeighbors, neighbor:_loc_5});
                    }
                    break;
                }
                case Item.UNLOCK_OLD_QUEST_OR_PURCHASE:
                {
                    _loc_2 = "";
                    _loc_3 = "";
                    _loc_4 = "";
                    break;
                }
                case Item.UNLOCK_LOCKED:
                {
                    _loc_2 = ZLoc.t("Dialogs", "purchase_text");
                    break;
                }
                case Item.UNLOCK_OBJECT_COUNT:
                {
                    _loc_2 = "";
                    if (param1.requiredObjects.length > 0)
                    {
                        param1 = Global.gameSettings().getItemByName(param1.requiredObjects.get(0));
                        if (param1 !=null)
                        {
                            _loc_2 = param1.localizedName;
                        }
                    }
                    break;
                }
                case Item.UNLOCK_CAN_ADD_POPULATION:
                {
                    _loc_2 = ZLoc.t("Dialogs", "populationLimit_text");
                    break;
                }
                default:
                {
                    break;
                }
            }
            return {line1:_loc_2, line2:_loc_3, requires:_loc_4};
        }//end

        public static String  getUnlockEarlyText (Item param1 )
        {
            String _loc_2 =null ;
            if (param1.unlock == Item.UNLOCK_PERMITS)
            {
                if (param1.requiredQuestFlag.length > 0 && !Global.questManager.isFlagReached(param1.requiredQuestFlag))
                {
                }
                else if (ExpansionManager.instance.getNextExpansionNumPermitPurchaseToComplete() <= 0)
                {
                    _loc_2 = ZLoc.t("Dialogs", "UnlockEarly", {amount:String(ExpansionManager.instance.getNextExpansionLockOverrideCost(param1))});
                }
                else
                {
                    _loc_2 = "";
                }
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "UnlockEarly", {amount:param1.unlockCostCash});
            }
            return _loc_2;
        }//end

        public static String  getUnlockTipText (Item param1 )
        {
            String _loc_2 =null ;
            if (param1.unlockTipText)
            {
                _loc_2 = ZLoc.t("Dialogs", param1.unlockTipText);
            }
            return _loc_2;
        }//end

        public static boolean  hasResidencesForNeighborhood (String param1 )
        {
            _loc_2 = Neighborhood.getResidenceTypeForNeighborhood(param1);
            _loc_3 =Global.world.getObjectsByKeywords(_loc_2 ).length ;
            return _loc_3 > 0;
        }//end

    }



