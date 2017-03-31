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
import Classes.doobers.*;
import Classes.virals.*;
import Engine.Classes.*;
import Engine.Transactions.*;
import GameMode.*;
import Init.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.bandits.transactions.*;
import Modules.cars.*;
import Modules.franchise.transactions.*;
import Modules.garden.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.minigames.transactions.*;
import Modules.quest.Helpers.*;
import Modules.remodel.*;
import Modules.socialinventory.*;
import Modules.trains.*;
import Modules.zoo.*;
import Transactions.*;
import Transactions.train.*;
//import flash.utils.*;
import validation.*;

    public class GameQuestUtility
    {

        public  GameQuestUtility ()
        {
            return;
        }//end

        public static int  countNewNeighbors (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            String _loc_10 =null ;
            boolean _loc_11 =false ;
            String _loc_12 =null ;
            int _loc_4 =0;
            if (!param3 || !param3.hasOwnProperty("neighbors") || !param3.hasOwnProperty("total") || !param3.hasOwnProperty("max"))
            {
                return 0;
            }
            int _loc_5 =0;
            for(int i0 = 0; i0 < param3.neighbors.size(); i0++)
            {
            		_loc_6 = param3.neighbors.get(i0);

                if (_loc_6 == null)
                {
                    continue;
                }
                _loc_5++;
            }
            _loc_7 = Math.max(param3.max - _loc_5, 0);
            _loc_8 = Math.min(_loc_7, param3.total);
            _loc_9 = param3.total - _loc_8;
            for(int i0 = 0; i0 < Global.player.neighbors.size(); i0++)
            {
            		_loc_10 = Global.player.neighbors.get(i0);

                _loc_11 = true;
                if (_loc_10 == Player.FAKE_USER_ID_STRING)
                {
                    continue;
                }
                for(int i0 = 0; i0 < param3.neighbors.size(); i0++)
                {
                		_loc_12 = param3.neighbors.get(i0);

                    if (_loc_12 == _loc_10)
                    {
                        _loc_11 = false;
                    }
                }
                if (_loc_11)
                {
                    _loc_9++;
                }
            }
            return Math.min(_loc_9, param3.total);
        }//end

        public static boolean  isQuestValid (String param1 )
        {
            GenericValidationScript _loc_3 =null ;
            _loc_2 = QuestSettingsInit.getQuestXMLByName(param1);
            if (_loc_2)
            {
                _loc_3 = Global.validationManager.getValidator(_loc_2.@validate);
                if (!_loc_3)
                {
                    return true;
                }
                return _loc_3.validate();
            }
            return false;
        }//end

        public static int  countTicketObjectsByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 =Global.ticketManager.getCount(param2 );
            if (Global.ticketManager.getCount(param2))
            {
                return _loc_4;
            }
            return 0;
        }//end

        public static WorldObject  getFriendVisitReplayObject (Class param1 ,Transaction param2 ,Object param3 =null )
        {
            _loc_4 = param2as TRedeemVisitorHelpAction ;
            _loc_5 = param2(as TRedeemVisitorHelpAction ).getWorldObject ();
            if (_loc_4.getEquivalentTransaction(_loc_5) != param1)
            {
                return null;
            }
            return _loc_5;
        }//end

        public static int  countAutomationActionByClassHelper (String param1 ,Transaction param2 ,String param3 )
        {
            Array _loc_5 =null ;
            double _loc_6 =0;
            WorldObject _loc_7 =null ;
            int _loc_4 =0;
            if (param2 instanceof TAutoCollectRent)
            {
                _loc_5 = ((TAutoCollectRent)param2).mapResourceIds;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 = Global.world.getObjectById(_loc_6);
                    _loc_4 = _loc_4 + countActionByClassTypeSubHelper((ItemInstance)_loc_7, param3);
                }
            }
            return _loc_4;
        }//end

        public static int  countAutomationActionByNameHelper (String param1 ,Transaction param2 ,String param3 ,boolean param4 =false )
        {
            Dictionary _loc_6 =null ;
            Array _loc_7 =null ;
            double _loc_8 =0;
            WorldObject _loc_9 =null ;
            int _loc_5 =0;
            if (param2 instanceof TAutoCollectRent)
            {
                _loc_6 = null;
                _loc_7 = ((TAutoCollectRent)param2).mapResourceIds;
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_8 = _loc_7.get(i0);

                    _loc_9 = Global.world.getObjectById(_loc_8);
                    if (param4)
                    {
                        _loc_5 = _loc_5 + countActionByNameRegExSubHelper((ItemInstance)_loc_9, param3);
                        continue;
                    }
                    if (!_loc_6)
                    {
                        _loc_6 = breakApartNamesAsMap(param3);
                    }
                    _loc_5 = _loc_5 + countActionByNameTypeMapSubHelper((ItemInstance)_loc_9, _loc_6);
                }
            }
            return _loc_5;
        }//end

        public static int  countMechanicActionByClassHelper (String param1 ,Transaction param2 ,String param3 )
        {
            if (!(param2 instanceof TMechanicAction))
            {
                return 0;
            }
            if (((TMechanicAction)param2).getParams().operation != param1)
            {
                return 0;
            }
            _loc_4 = param2(as TMechanicAction ).ownerObject ;
            _loc_5 = countActionByClassTypeSubHelper(_loc_4,param3);
            return countActionByClassTypeSubHelper(_loc_4, param3);
        }//end

        public static int  countMechanicActionByNameHelper (String param1 ,Transaction param2 ,String param3 )
        {
            if (!(param2 instanceof TMechanicAction))
            {
                return 0;
            }
            if (((TMechanicAction)param2).getParams().operation != param1)
            {
                return 0;
            }
            _loc_4 = breakApartNamesAsMap(param3);
            _loc_5 = param2(as TMechanicAction ).ownerObject ;
            _loc_6 = countActionByNameTypeMapSubHelper(_loc_5,_loc_4);
            return countActionByNameTypeMapSubHelper(_loc_5, _loc_4);
        }//end

        public static int  countActionByClassTypeSubHelper (ItemInstance param1 ,String param2 )
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            ItemInstance _loc_6 =null ;
            int _loc_3 =0;
            if (param1 !=null)
            {
                _loc_4 = "[object " + param2 + "]";
                if (String(param1) == _loc_4)
                {
                    _loc_3 = 1;
                }
                else if (param1 instanceof ISlottedContainer)
                {
                    _loc_5 = ((ISlottedContainer)param1).slots;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);

                        if (String(_loc_6) == _loc_4)
                        {
                            _loc_3++;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public static int  countActionByNameTypeMapSubHelper (ItemInstance param1 ,Dictionary param2 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            ItemInstance _loc_7 =null ;
            int _loc_3 =0;
            if (param1 !=null)
            {
                _loc_4 = param1.getItemName();
                if (param2.get(_loc_4))
                {
                    _loc_3 = 1;
                }
                else if (param1 instanceof ISlottedContainer)
                {
                    _loc_5 = null;
                    _loc_6 = ((ISlottedContainer)param1).slots;
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    		_loc_7 = _loc_6.get(i0);

                        _loc_5 = _loc_7.getItemName();
                        if (param2.get(_loc_5))
                        {
                            _loc_3++;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public static int  countActionByNameRegExSubHelper (ItemInstance param1 ,String param2 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            ItemInstance _loc_7 =null ;
            int _loc_3 =0;
            if (param1 !=null)
            {
                _loc_4 = param1.getItemName();
                if (matchRegEx(_loc_4, param2))
                {
                    _loc_3 = 1;
                }
                else if (param1 instanceof ISlottedContainer)
                {
                    _loc_5 = null;
                    _loc_6 = ((ISlottedContainer)param1).slots;
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    		_loc_7 = _loc_6.get(i0);

                        _loc_5 = _loc_7.getItemName();
                        if (matchRegEx(_loc_4, param2))
                        {
                            _loc_3++;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public static int  countTransactionByClassHelper (Class param1 ,Transaction param2 ,String param3 )
        {
            String _loc_6 =null ;
            if (!(param2 instanceof param1 || param2 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            int _loc_4 =0;
            WorldObject _loc_5 =null ;
            if (param2 instanceof TRedeemVisitorHelpAction)
            {
                _loc_5 = getFriendVisitReplayObject(param1, param2);
                if (_loc_5)
                {
                    _loc_6 = "[object " + param3 + "]";
                    if (String(_loc_5) == _loc_6)
                    {
                        _loc_4++;
                    }
                }
            }
            else
            {
                if (!(param2 instanceof TWorldState))
                {
                    throw new Error("Quest error: tested transaction " + param1 + " instanceof not a world state transaction!");
                }
                _loc_5 = ((TWorldState)param2).getWorldObject();
                _loc_4 = countActionByClassTypeSubHelper((ItemInstance)_loc_5, param3);
            }
            return _loc_4;
        }//end

        public static int  countTransactionByNameHelper (Class param1 ,Transaction param2 ,String param3 ,boolean param4 =false )
        {
            String _loc_9 =null ;
            if (!(param2 instanceof param1 || param2 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            Object _loc_5 =null ;
            WorldObject _loc_6 =null ;
            int _loc_7 =0;
            Dictionary _loc_8 =null ;
            if (param2 instanceof TRedeemVisitorHelpAction)
            {
                _loc_6 = getFriendVisitReplayObject(param1, param2);
                if (_loc_6)
                {
                    _loc_5 = _loc_6.getSaveObject();
                    if (_loc_5)
                    {
                        _loc_9 = _loc_5.itemName;
                        if (param4)
                        {
                            if (matchRegEx(_loc_9, param3))
                            {
                                _loc_7++;
                            }
                        }
                        else
                        {
                            _loc_8 = breakApartNamesAsMap(param3);
                            if (_loc_8.get(_loc_9))
                            {
                                _loc_7++;
                            }
                        }
                    }
                }
            }
            else
            {
                _loc_6 = ((TWorldState)param2).getWorldObject();
                _loc_5 = ((TWorldState)param2).getSaveObject();
                if (_loc_5 && _loc_6 instanceof ItemInstance)
                {
                    if (param4)
                    {
                        _loc_7 = countActionByNameRegExSubHelper((ItemInstance)_loc_6, param3);
                    }
                    else
                    {
                        _loc_8 = breakApartNamesAsMap(param3);
                        _loc_7 = countActionByNameTypeMapSubHelper((ItemInstance)_loc_6, _loc_8);
                    }
                }
            }
            return _loc_7;
        }//end

        public static int  countConstructionTransactionByNameHelper (Class param1 ,Transaction param2 ,String param3 )
        {
            if (!(param2 instanceof param1 || param2 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            WorldObject _loc_4 =null ;
            if (param2 instanceof TRedeemVisitorHelpAction)
            {
                _loc_4 = getFriendVisitReplayObject(param1, param2);
            }
            else
            {
                _loc_4 = ((TWorldState)param2).getWorldObject();
            }
            if (!_loc_4)
            {
                return 0;
            }
            _loc_5 = _loc_4as ConstructionSite ;
            if ((ConstructionSite)_loc_4 == null)
            {
                return 0;
            }
            _loc_6 = _loc_5.targetName;
            return findNameInNames(_loc_6, param3) ? (1) : (0);
        }//end

        public static int  countTransactionByTypeHelper (Class param1 ,Transaction param2 ,String param3 )
        {
            WorldObject _loc_8 =null ;
            if (!(param2 instanceof param1 || param2 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            Object _loc_4 =null ;
            if (param2 instanceof TRedeemVisitorHelpAction)
            {
                _loc_8 = getFriendVisitReplayObject(param1, param2);
                if (_loc_8)
                {
                    _loc_4 = _loc_8.getSaveObject();
                }
            }
            else
            {
                _loc_4 = ((TWorldState)param2).getSaveObject();
            }
            _loc_5 = _loc_4.contractName ? (_loc_4.contractName) : (_loc_4.itemName);
            _loc_6 =Global.gameSettings().getItemByName(_loc_5 );
            _loc_7 =Global.gameSettings().getItemByName(_loc_5 ).type ;
            return findNameInNames(_loc_7, param3) ? (1) : (0);
        }//end

        public static int  countTransactionByContractNameHelper (Class param1 ,Transaction param2 ,String param3 )
        {
            WorldObject _loc_6 =null ;
            if (!(param2 instanceof param1 || param2 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            Object _loc_4 =null ;
            if (param2 instanceof TRedeemVisitorHelpAction)
            {
                _loc_6 = getFriendVisitReplayObject(param1, param2);
                if (_loc_6)
                {
                    _loc_4 = _loc_6.getSaveObject();
                }
            }
            else
            {
                _loc_4 = ((TWorldState)param2).getSaveObject();
            }
            _loc_5 = _loc_4.contractName ;
            return findNameInNames(_loc_5, param3) ? (1) : (0);
        }//end

        public static boolean  findNameInNames (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            for(int i0 = 0; i0 < breakApartNames(param2).size(); i0++)
            {
            		_loc_3 = breakApartNames(param2).get(i0);

                if (param1 == _loc_3)
                {
                    return true;
                }
            }
            return false;
        }//end

        public static Dictionary  breakApartNamesAsMap (String param1 )
        {
            String _loc_4 =null ;
            _loc_2 = param1.split(",");
            _loc_3 = new Dictionary ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_3.put(_loc_4,  1);
            }
            return _loc_3;
        }//end

        public static Array  breakApartNames (String param1 )
        {
            _loc_2 = param1.split(",");
            return _loc_2;
        }//end

        private static boolean  matchRegEx (String param1 ,String param2 )
        {
            boolean _loc_3 =false ;
            _loc_4 = new RegExp(param2 );
            _loc_5 = new RegExp(param2 ).exec(param1 );
            if (new RegExp(param2).exec(param1) && _loc_5.length > 0)
            {
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        public static int  clearByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TClear, param1, param2);
        }//end

        public static int  sellByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TSell, param1, param2);
        }//end

        public static int  sellByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByNameHelper(TSell, param1, param2);
        }//end

        public static int  moveByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TMove, param1, param2);
        }//end

        public static int  moveByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByNameHelper(TMove, param1, param2);
        }//end

        public static int  upgradeItemByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByNameHelper(TUpgradeBuilding, param1, param2);
        }//end

        public static int  countUpgradeItemByRootName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            int _loc_4 =0;
            _loc_5 = new Array ();
            _loc_6 = new Array ();
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_7 = breakApartNames(param2);
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_8 = _loc_7.get(i0);

                    _loc_9 = Global.gameSettings().getOrderedUpgradeChainByRoot(_loc_8);
                    _loc_9.shift();
                    _loc_5 = _loc_5.concat(_loc_9);
                }
                _loc_6 = Global.world.getObjectsByNames(_loc_5);
                _loc_4 = _loc_6.length;
            }
            return _loc_4 + countTransactionByNameHelper(TUpgradeBuilding, param1, param2);
        }//end

        public static int  placeByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TPlaceMapResource, param1, param2);
        }//end

        public static int  placeBuildingByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByNameHelper(TPlaceMapResource, param1, param2);
        }//end

        public static int  autoHarvest (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TAutoCollectRent)
            {
                return ((TAutoCollectRent)param1).numResources;
            }
            return 0;
        }//end

        public static int  harvestByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByClassHelper(THarvest,param1,param2)+countMechanicActionByClassHelper("harvest",param1,param2)+countAutomationActionByClassHelper("harvest",param1,param2);
            return countTransactionByClassHelper(THarvest, param1, param2) + countMechanicActionByClassHelper("harvest", param1, param2) + countAutomationActionByClassHelper("harvest", param1, param2);
        }//end

        public static int  harvestBusinessByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByClassHelper(THarvestBusiness,param1,param2)+countMechanicActionByClassHelper("harvest",param1,param2);
            return countTransactionByClassHelper(THarvestBusiness, param1, param2) + countMechanicActionByClassHelper("harvest", param1, param2);
        }//end

        public static int  harvestItemByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByNameHelper(THarvest,param1,param2)+countMechanicActionByNameHelper("harvest",param1,param2);
            return countTransactionByNameHelper(THarvest, param1, param2) + countMechanicActionByNameHelper("harvest", param1, param2);
        }//end

        public static int  sawFlashMob (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TSawFlashMob)
            {
                return 1;
            }
            return 0;
        }//end

        public static int  harvestResidenceByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByNameHelper(THarvest,param1,param2)+countMechanicActionByNameHelper("harvest",param1,param2)+countAutomationActionByNameHelper("harvest",param1,param2);
            return countTransactionByNameHelper(THarvest, param1, param2) + countMechanicActionByNameHelper("harvest", param1, param2) + countAutomationActionByNameHelper("harvest", param1, param2);
        }//end

        public static int  harvestResidenceByRegEx (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByNameHelper(THarvest,param1,param2,true)+countMechanicActionByNameHelper("harvest",param1,param2)+countAutomationActionByNameHelper("harvest",param1,param2,true);
            return countTransactionByNameHelper(THarvest, param1, param2, true) + countMechanicActionByNameHelper("harvest", param1, param2) + countAutomationActionByNameHelper("harvest", param1, param2, true);
        }//end

        public static int  harvestPlotByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByTypeHelper(THarvest, param1, param2);
        }//end

        public static int  harvestPlotByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByContractNameHelper(THarvest, param1, param2);
        }//end

        public static int  harvestByKeyword (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            String _loc_6 =null ;
            if (!(param1 instanceof THarvest || param1 instanceof TRedeemVisitorHelpAction))
            {
                return 0;
            }
            HarvestableResource _loc_4 =null ;
            if (param1 instanceof TRedeemVisitorHelpAction)
            {
                _loc_4 =(HarvestableResource) getFriendVisitReplayObject(THarvest, param1);
            }
            else
            {
                _loc_4 =(TWorldState).getWorldObject() as HarvestableResource) (param1;
            }
            if (_loc_4)
            {
                _loc_5 = breakApartNames(param2);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    if (_loc_4.getItem().itemHasKeyword(_loc_6))
                    {
                        return 1;
                    }
                }
            }
            return 0;
        }//end

        public static int  harvestBusinessByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countTransactionByNameHelper(THarvestBusiness,param1,param2)+countMechanicActionByNameHelper("harvest",param1,param2);
            return countTransactionByNameHelper(THarvestBusiness, param1, param2) + countMechanicActionByNameHelper("harvest", param1, param2);
        }//end

        public static int  startTimerByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countMechanicActionByNameHelper("startTimer", param1, param2);
        }//end

        public static int  startContractByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TFactoryContractStart, param1, param2);
        }//end

        public static int  startContractByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByContractNameHelper(TFactoryContractStart, param1, param2);
        }//end

        public static int  openBusinessByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByClassHelper(TOpenBusiness, param1, param2);
        }//end

        public static int  openBusinessByCommodityType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByCommodityUsedHelper(param1, param2);
        }//end

        public static int  openBusinessByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countTransactionByNameHelper(TOpenBusiness, param1, param2);
        }//end

        public static int  finishConstructionByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countConstructionTransactionByNameHelper(TConstructionFinish, param1, param2);
        }//end

        public static int  grantVIPHotelAccess (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 instanceof TMechanicAction && (param1 as TMechanicAction).mechanicType == "hotelVisitClosed")
            {
                _loc_4 = ((TMechanicAction)param1).getParams();
                _loc_5 = _loc_4.get("operation");
                if (_loc_5 == "grantVIP")
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  upgradeHotelGuest (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 instanceof TMechanicAction && (param1 as TMechanicAction).mechanicType == "hotelVisitClosed")
            {
                _loc_4 = ((TMechanicAction)param1).getParams();
                _loc_5 = _loc_4.get("operation");
                if (_loc_5 == "acceptVIP")
                {
                    return 1;
                }
            }
            if (param1 instanceof TMechanicAction && (param1 as TMechanicAction).mechanicType == "hotelVisitClosed")
            {
                _loc_4 = ((TMechanicAction)param1).getParams();
                _loc_5 = _loc_4.get("operation");
                if (_loc_5 == "upgradeGuest")
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  acceptHotelVIPStatus (Transaction param1 ,String param2 ,Object param3 =null )
        {
            double _loc_6 =0;
            Object _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 instanceof TMechanicAction && (param1 as TMechanicAction).mechanicType == "hotelVisitClosed")
            {
                _loc_4 = ((TMechanicAction)param1).getParams();
                _loc_5 = _loc_4.get("operation");
                if (_loc_5 == "acceptVIP")
                {
                    return 1;
                }
            }
            if (param1 instanceof TMechanicAction && (param1 as TMechanicAction).mechanicType == "hotelVisitClosed")
            {
                _loc_4 = ((TMechanicAction)param1).getParams();
                _loc_5 = _loc_4.get("operation");
                _loc_6 = _loc_4.get("floorId");
                if (_loc_5 == "upgradeGuest" && _loc_6 == 0)
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  countStreamPublishByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            if (!(param1 instanceof TCityStreamPublish))
            {
                return 0;
            }
            _loc_4 = param1as TCityStreamPublish ;
            _loc_5 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                if (_loc_4.getViralType() == _loc_6)
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  firstTimeHotelSupply (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Hotel _loc_6 =null ;
            _loc_4 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.HOTEL) );
            double _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                _loc_6 = _loc_4.get(_loc_5);
                if (_loc_6.getState() == Hotel.STATE_OPEN || _loc_6.getState() == Hotel.STATE_HARVESTABLE)
                {
                    return 1;
                }
                _loc_5 = _loc_5 + 1;
            }
            return 0;
        }//end

        public static int  resortBusinessThreshold (Transaction param1 ,String param2 ,Object param3 =null )
        {
            double _loc_7 =0;
            _loc_4 =Global.world.citySim.resortManager.getNumberOfHotels ();
            double _loc_5 =0;
            double _loc_6 =0;
            while (_loc_6 < _loc_4)
            {

                _loc_7 = Global.world.citySim.resortManager.getNumberOfBusinessesForHotel(_loc_6);
                if (_loc_7 > _loc_5)
                {
                    _loc_5 = _loc_7;
                }
                _loc_6 = _loc_6 + 1;
            }
            return _loc_5;
        }//end

        public static int  supplyResortBusiness (Transaction param1 ,String param2 ,Object param3 =null )
        {
            TOpenBusiness _loc_4 =null ;
            double _loc_5 =0;
            boolean _loc_6 =false ;
            if (param1 instanceof TOpenBusiness)
            {
                _loc_4 =(TOpenBusiness) param1;
                _loc_5 = _loc_4.getWorldObject().getId();
                _loc_6 = Global.world.citySim.resortManager.businessInResortById(_loc_5);
                if (_loc_6)
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  transferFromStorageToDisplay (Transaction param1 ,String param2 ,Object param3 =null )
        {
            TMechanicDataTransfer _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            if (param1 instanceof TMechanicDataTransfer)
            {
                _loc_4 =(TMechanicDataTransfer) param1;
                if (_loc_4.params && _loc_4.params.length > 0)
                {
                    for(int i0 = 0; i0 < _loc_4.params.size(); i0++)
                    {
                    		_loc_5 = _loc_4.params.get(i0);

                        _loc_6 = _loc_4.getOwner().getItemName();
                        if (_loc_5.get("source") == ZooManager.MECHANIC_STORAGE && _loc_5.get("dest") == ZooManager.MECHANIC_SLOTS && param2 == _loc_6)
                        {
                            return 1;
                        }
                    }
                }
            }
            return 0;
        }//end

        public static int  storeItemInMechanicUser (Transaction param1 ,String param2 ,Object param3 =null )
        {
            TMechanicAction _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            if (param1 instanceof TMechanicAction)
            {
                _loc_4 =(TMechanicAction) param1;
                _loc_5 = ((TMechanicAction)param1).getParams();
                if (_loc_5)
                {
                    if (param2 == _loc_4.ownerObject.getItem().type)
                    {
                        for(int i0 = 0; i0 < _loc_5.size(); i0++)
                        {
                        		_loc_6 = _loc_5.get(i0);

                            if (_loc_5.get(_loc_6) == "storeObject" && _loc_6 == "operation")
                            {
                                return 1;
                            }
                        }
                    }
                }
            }
            return 0;
        }//end

        public static int  getTicketPurchase (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TTicket)
            {
                return ((TTicket)param1).getTheme() == param2 ? (1) : (0);
            }
            return 0;
        }//end

        public static int  finishMiniGameByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TMiniGameComplete)
            {
                return ((TMiniGameComplete)param1).getGameName() == param2 ? (1) : (0);
            }
            return 0;
        }//end

        public static int  renameCity (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TRenameCity ? (1) : (0);
        }//end

        public static int  onValidCityName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TOnValidCityName ? (1) : (0);
        }//end

        public static int  seenQuest (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TSeenQuest && (param1 as TSeenQuest).m_questName == param2 ? (1) : (0);
        }//end

        public static int  popNews (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TPopNews && (param1 as TPopNews).m_questName == param2 ? (1) : (0);
        }//end

        public static int  neighborVisit (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TInitialVisit)
            {
                if (param2 == "" || param2 == null || param2 == (param1 as TInitialVisit).recipientId)
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  uniqueNeighborVisit (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            String _loc_7 =null ;
            int _loc_8 =0;
            if (param1 instanceof TInitialVisit)
            {
                _loc_4 = ((TInitialVisit)param1).recipientId;
                _loc_5 = int(param2);
                _loc_6 = int(GlobalEngine.getTimer() / 1000);
                if (!param3 || !param3.hasOwnProperty("neighborsVisited"))
                {
                    param3.neighborsVisited = new Array();
                    param3.neighborsVisited.put(_loc_4,  _loc_6);
                    return 1;
                }
                for(int i0 = 0; i0 < param3.neighborsVisited.size(); i0++)
                {
                		_loc_7 = param3.neighborsVisited.get(i0);

                    if (_loc_7 == _loc_4)
                    {
                        _loc_8 = param3.neighborsVisited.get(_loc_7);
                        if (_loc_6 - _loc_8 < _loc_5)
                        {
                            return 0;
                        }
                    }
                }
                param3.neighborsVisited.put(_loc_4,  _loc_6);
                return 1;
            }
            return 0;
        }//end

        public static int  harvestNeighborResidenceByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countNeighborVisitActionHelper(VisitorHelpType.RESIDENCE_COLLECT_RENT, param1, param2);
        }//end

        public static int  harvestNeighborResidenceByRegEx (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countNeighborVisitActionHelper(VisitorHelpType.RESIDENCE_COLLECT_RENT, param1, param2, true);
        }//end

        public static int  sendTourNeighborBusinessByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countNeighborVisitActionHelper(VisitorHelpType.BUSINESS_SEND_TOUR, param1, param2);
        }//end

        public static int  hotelCheckIn (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countNeighborVisitActionHelper(VisitorHelpType.HOTEL_CHECKIN, param1, param2);
        }//end

        public static int  reviveNeighborPlotByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countNeighborVisitActionHelper(VisitorHelpType.PLOT_REVIVE,param1,param2)+countNeighborVisitActionHelper(VisitorHelpType.SHIP_REVIVE,param1,param2);
            return countNeighborVisitActionHelper(VisitorHelpType.PLOT_REVIVE, param1, param2) + countNeighborVisitActionHelper(VisitorHelpType.SHIP_REVIVE, param1, param2);
        }//end

        public static int  waterNeighborPlotByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countNeighborVisitActionHelper(VisitorHelpType.PLOT_WATER,param1,param2)+countNeighborVisitActionHelper(VisitorHelpType.SHIP_WATER,param1,param2);
            return countNeighborVisitActionHelper(VisitorHelpType.PLOT_WATER, param1, param2) + countNeighborVisitActionHelper(VisitorHelpType.SHIP_WATER, param1, param2);
        }//end

        public static int  publicVisitorHelpTypeGlobal (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TPerformVisitorHelp && findNameInNames((param1 as TPerformVisitorHelp).getHelpType(), param2))
            {
                return 1;
            }
            return 0;
        }//end

        public static int  harvestNeighborPlotByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = countNeighborVisitActionHelper(VisitorHelpType.PLOT_HARVEST,param1,param2)+countNeighborVisitActionHelper(VisitorHelpType.SHIP_HARVEST,param1,param2);
            return countNeighborVisitActionHelper(VisitorHelpType.PLOT_HARVEST, param1, param2) + countNeighborVisitActionHelper(VisitorHelpType.SHIP_HARVEST, param1, param2);
        }//end

        public static int  countNeighborVisitActionHelper (String param1 ,Transaction param2 ,String param3 ,boolean param4 =false )
        {
            if (!(param2 instanceof TPerformVisitorHelp))
            {
                return 0;
            }
            _loc_5 = param2(as TPerformVisitorHelp ).getHelpType ();
            if (param1 != _loc_5)
            {
                return 0;
            }
            _loc_6 = param2(as TPerformVisitorHelp ).getGameObject ();
            String _loc_7 ="";
            if (_loc_6 instanceof HarvestableResource)
            {
                _loc_7 = ((HarvestableResource)_loc_6).getHarvestingDefinition().name;
            }
            else
            {
                _loc_7 = _loc_6 instanceof ItemInstance ? ((_loc_6 as ItemInstance).getItemName()) : ("");
            }
            if (param4)
            {
                return matchRegEx(_loc_7, param3) ? (1) : (0);
            }
            else
            {
            }
            return findNameInNames(_loc_7, param3) ? (1) : (0);
        }//end

        public static int  citySamHQ (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = param2? (param2) : (Player.FAKE_USER_ID_STRING);

	    return 0;
        }//end

        public static int  welcomeTrain (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TTrainWelcomeOrder ? (1) : (0);
        }//end

        public static int  sendFranchiseSupply (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param2 == null || param2 == "")
            {
                return param1 instanceof TFranchiseSupply ? (1) : (0);
            }
            else
            {
                return param1 instanceof TFranchiseSupply && findNameInNames((param1 as TFranchiseSupply).franchiseType, param2) ? (1) : (0);
            }
        }//end

        public static int  countTransactionByCommodityUsedHelper (Transaction param1 ,String param2 ,Object param3 =null )
        {
            int _loc_4 =0;
            TOpenBusiness _loc_5 =null ;
            boolean _loc_6 =false ;
            Business _loc_7 =null ;
            int _loc_8 =0;
            if (param2 == null || param2 == "")
            {
                return param1 instanceof TOpenBusiness ? (1) : (0);
            }
            else
            {
                _loc_4 = 0;
                _loc_5 =(TOpenBusiness) param1;
                if (_loc_5)
                {
                    _loc_6 = _loc_5.desiredCommodity == param2;
                    _loc_7 =(Business) _loc_5.getWorldObject();
                    _loc_8 = _loc_7.getSupply(param2);
                    if (_loc_6 && _loc_8 > 0)
                    {
                        _loc_4 = 1;
                    }
                }
                return _loc_4;
            }
        }//end

        public static int  sendTrain (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return param1 instanceof TSendTrain ? (1) : (0);
        }//end

        public static int  visitorHelp (Transaction param1 ,String param2 ,Object param3 =null )
        {
            TPerformVisitorHelp _loc_4 =null ;
            if (param1 instanceof TPerformVisitorHelp)
            {
                _loc_4 =(TPerformVisitorHelp) param1;
                if (_loc_4.getHelpType() == param2)
                {
                    return 1;
                }
            }
            return 0;
        }//end

        public static int  consumeItems (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_7 =null ;
            Item _loc_8 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_6 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                _loc_8 = Global.gameSettings().getItemByName(_loc_7);
                if (_loc_8 != null)
                {
                    _loc_4 = Global.player.inventory.getItemCount(_loc_8);
                    if (_loc_4 > 0)
                    {
                        _loc_5 = _loc_5 + _loc_4;
                        Global.player.inventory.removeItems(_loc_7, _loc_4);
                    }
                }
            }
            return _loc_5;
        }//end

        public static int  consumeItemsAtInit (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_6 =null ;
            String _loc_7 =null ;
            Item _loc_8 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            if (param1 instanceof TPingFeedQuests || param3.get("alwaysCheck") == "true" && Global.player.inventory.consumeFlag)
            {
                _loc_6 = breakApartNames(param2);
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_7 = _loc_6.get(i0);

                    _loc_8 = Global.gameSettings().getItemByName(_loc_7);
                    if (_loc_8 != null)
                    {
                        _loc_4 = Global.player.inventory.getItemCount(_loc_8);
                        if (_loc_4 > 0)
                        {
                            _loc_5 = _loc_5 + _loc_4;
                            Global.player.inventory.removeItems(_loc_7, _loc_4);
                        }
                    }
                }
                if (param3.get("alwaysCheck") == "true")
                {
                    Global.player.inventory.resetConsumeFlag();
                }
            }
            return _loc_5;
        }//end

        public static int  checkItemsAtInit (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            Item _loc_7 =null ;
            int _loc_4 =0;
            _loc_5 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_7 = Global.gameSettings().getItemByName(_loc_6);
                if (_loc_7 != null)
                {
                    _loc_4 = _loc_4 + Global.player.inventory.getItemCount(_loc_7);
                }
            }
            return _loc_4;
        }//end

        public static int  finishConstructionByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (!(param1 instanceof TConstructionFinish))
            {
                return 0;
            }
            _loc_4 = param1(as TWorldState ).getWorldObject ();
            _loc_5 = param1(as TWorldState ).getWorldObject ()as ConstructionSite ;
            if (((TWorldState)param1).getWorldObject() as ConstructionSite == null)
            {
                return 0;
            }
            _loc_6 = new _loc_5.targetClass(_loc_5.targetName );
            _loc_7 = "[object "+param2+"]";
            _loc_8 = String(_loc_6)==_loc_7? (1) : (0);
            _loc_6.detach();
            _loc_6.cleanUp();
            return _loc_8;
        }//end

        public static int  countWorldObjectsHelper (Array param1 ,Transaction param2 )
        {
            MapResource _loc_4 =null ;
            int _loc_5 =0;
            MapResource _loc_6 =null ;
            boolean _loc_7 =false ;
            int _loc_3 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_4 = null;
                if (Global.world.getTopGameMode() instanceof GMPlaceMapResource)
                {
                    _loc_4 = (Global.world.getTopGameMode() as GMPlaceMapResource).displayedMapResource;
                }
                _loc_5 = 0;
                while (_loc_5 < param1.length())
                {

                    _loc_6 =(MapResource) param1.get(_loc_5);
                    if (_loc_6 != null)
                    {
                        _loc_7 = !(param2 instanceof TPlaceMapResource) || _loc_4.getItem().construction != null;
                        if (_loc_4 != null && _loc_4.getId() == _loc_6.getId() && _loc_7)
                        {
                        }
                        else
                        {
                            _loc_3++;
                        }
                    }
                    _loc_5++;
                }
            }
            return _loc_3;
        }//end

        public static int  countWorldObjectByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 = breakApartNames(param2);
                _loc_6 = Global.world.getObjectsByNames(_loc_5);
                _loc_4 = countWorldObjectsHelper(_loc_6, param1);
            }
            return _loc_4;
        }//end

        public static int  countSlottedContainerObjects (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            String _loc_6 =null ;
            Class _loc_7 =null ;
            Array _loc_8 =null ;
            ISlottedContainer _loc_9 =null ;
            int _loc_10 =0;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 = breakApartNames(param2);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 =(Class) getDefinitionByName("Classes." + param2);
                    _loc_8 = Global.world.getObjectsByClass(_loc_7);
                    for(int i0 = 0; i0 < _loc_8.size(); i0++)
                    {
                    		_loc_9 = _loc_8.get(i0);

                        _loc_10 = _loc_9.slots.length;
                        if (_loc_10 > _loc_4)
                        {
                            _loc_4 = _loc_10;
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public static int  countWorldObjectByRegEx (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 = Global.world.getObjectsByRegEx(param2);
                _loc_4 = _loc_4 + countWorldObjectsHelper(_loc_5, param1);
            }
            return _loc_4;
        }//end

        public static int  countWorldObjectByClass (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Class _loc_5 =null ;
            Array _loc_6 =null ;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 =(Class) getDefinitionByName("Classes." + param2);
                _loc_6 = Global.world.getObjectsByClass(_loc_5);
                _loc_4 = countWorldObjectsHelper(_loc_6, param1);
            }
            return _loc_4;
        }//end

        public static int  countStorageObjects (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return Global.player.storageComponent.size;
        }//end

        public static int  countStorageObjectsByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            int _loc_4 =0;
            if (Global.player.storageComponent)
            {
                _loc_4 = Global.player.storageComponent.getItemsByType(param2).length;
            }
            return _loc_4;
        }//end

        public static int  countCrewByItemName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            Array _loc_9 =null ;
            GameObject _loc_10 =null ;
            _loc_4 = breakApartNames(param2);
            int _loc_5 =0;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_6 = _loc_4.get(i0);

                _loc_7 = 0;
                _loc_8 = 0;
                _loc_9 = Global.world.getObjectsByNames(.get(param2));
                for(int i0 = 0; i0 < _loc_9.size(); i0++)
                {
                		_loc_10 = _loc_9.get(i0);

                    _loc_8 = Global.crews.getCrewCountByObject(_loc_10);
                    if (_loc_8 > _loc_7)
                    {
                        _loc_7 = _loc_8;
                    }
                }
                _loc_5 = _loc_5 + _loc_7;
            }
            return _loc_5;
        }//end

        public static int  countAnimalsObjects (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (!Global.world.getObjectsByNames(.get(param2)).length())
            {
                return 0;
            }
            return (Global.world.getObjectsByNames(.get(param2)).get(0) as ZooEnclosure).getAnimals();
        }//end

        public static int  countConstructionOrBuildingByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            ConstructionSite _loc_7 =null ;
            Array _loc_8 =null ;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 = Global.world.getObjectsByClass(ConstructionSite);
                _loc_6 = new Array();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_7 = _loc_5.get(i0);

                    if (findNameInNames(_loc_7.targetName, param2))
                    {
                        _loc_6.push(_loc_7);
                    }
                }
                _loc_4 = _loc_4 + countWorldObjectsHelper(_loc_6, param1);
                _loc_8 = breakApartNames(param2);
                _loc_5 = Global.world.getObjectsByNames(_loc_8);
                _loc_4 = _loc_4 + countWorldObjectsHelper(_loc_5, param1);
            }
            return _loc_4;
        }//end

        public static int  countPlayerCropMasteryByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_7 =null ;
            _loc_4 = param2.split(",");
            int _loc_5 =0;
            _loc_6 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (Global.goalManager.getGoal(GoalManager.GOAL_MASTERY) as MasteryGoal)
            {
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_7 = _loc_4.get(i0);

                    _loc_5 = Math.max(_loc_6.getLevel(_loc_7), _loc_5);
                }
            }
            return _loc_5;
        }//end

        public static int  countPlayerResourceByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            Doober _loc_9 =null ;
            _loc_4 = param2.split(",");
            param2 = param2.split(",").get(0);
            _loc_5 = _loc_4.get(1);
            switch(param2)
            {
                case "coin":
                {
                    _loc_6 = 0;
                    for(int i0 = 0; i0 < Global.world.dooberManager.doobers.size(); i0++)
                    {
                    		_loc_9 = Global.world.dooberManager.doobers.get(i0);

                        if (_loc_9.getItem().type == Doober.DOOBER_COIN)
                        {
                            _loc_6 = _loc_6 + _loc_9.amount;
                        }
                    }
                    return Global.player.gold + _loc_6;
                }
                case "xp":
                {
                    return Global.player.xp;
                }
                case "goods":
                {
                    _loc_7 = 0;
                    for(int i0 = 0; i0 < Global.world.dooberManager.doobers.size(); i0++)
                    {
                    		_loc_9 = Global.world.dooberManager.doobers.get(i0);

                        if (_loc_9.getItem().type == Doober.DOOBER_GOODS)
                        {
                            _loc_7 = _loc_7 + _loc_9.amount;
                        }
                    }
                    return Global.player.commodities.getCount("goods") + _loc_7;
                }
                case "premium_goods":
                {
                    _loc_8 = 0;
                    for(int i0 = 0; i0 < Global.world.dooberManager.doobers.size(); i0++)
                    {
                    		_loc_9 = Global.world.dooberManager.doobers.get(i0);

                        if (_loc_9.getItem().type == Doober.DOOBER_PREMIUM_GOODS)
                        {
                            _loc_8 = _loc_8 + _loc_9.amount;
                        }
                    }
                    return Global.player.commodities.getCount("premium_goods") + _loc_8;
                }
                case "population":
                {
                    if (!Global.isVisiting() && !Global.isTransitioningWorld)
                    {
                        return Global.world.citySim.getScaledPopulation(_loc_5);
                    }
                    break;
                }
                case "neighbors":
                {
                    return Global.player.neighbors.length;
                }
                default:
                {
                    break;
                }
            }
            return 0;
        }//end

        public static int  countMonsterPopulation (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return countPlayerResourceByType(param1, "population,monster", param3);
        }//end

        public static int  countPlayerMapExpansions (Transaction param1 ,String param2 ,Object param3 =null )
        {
            return Global.player.expansionsPurchased;
        }//end

        public static int  getExpansionByCoord (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_4 = param2.split(",");
                _loc_5 = int(_loc_4.get(0));
                _loc_6 = int(_loc_4.get(1));
                return int(Global.world.territory.pointInTerritory(_loc_5, _loc_6));
            }
            return 0;
        }//end

        public static int  completeCollectionByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            int _loc_4 =0;
            _loc_5 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                if (Global.gameSettings().getCollectionByName(_loc_6).isReadyToTradeIn())
                {
                    _loc_4 = 1;
                    break;
                }
            }
            return _loc_4;
        }//end

        public static int  countCollectableByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            int _loc_4 =0;
            _loc_5 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_7 = Global.gameSettings().getCollectionByCollectableName(_loc_6).name;
                if (Global.player.collections.hasOwnProperty(_loc_7))
                {
                    if (Global.player.collections.get(_loc_7).hasOwnProperty(_loc_6))
                    {
                        _loc_4 = _loc_4 + Global.player.collections.get(_loc_7).get(_loc_6);
                    }
                }
            }
            return _loc_4;
        }//end

        public static int  countFranchiseExpansionsByName (Transaction param1 ,String param2 ,Object param3 =null )
        {
            int _loc_4 =0;
            Array _loc_5 =null ;
            String _loc_6 =null ;
            if (param2 == null || param2 == "")
            {
                return 0;
            }
            _loc_4 = 0;
            _loc_5 = breakApartNames(param2);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_4 = 0;
            }
            return _loc_4;
        }//end

        public static int  countHeadquarters (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_5 =null ;
            int _loc_4 =0;
            if (!Global.isVisiting() && !Global.isTransitioningWorld)
            {
                _loc_5 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.HEADQUARTER));
                _loc_4 = countWorldObjectsHelper(_loc_5, param1);
            }
            return _loc_4;
        }//end

        public static int  incrementalPopulationCount (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (!Global.world.isHome())
            {
                return 0;
            }
            if (!param3 || !param3.hasOwnProperty("incrementalPopulation"))
            {
                param3.incrementalPopulation = Global.world.citySim.getScaledPopulation();
            }
            _loc_4 =Global.world.citySim.getScaledPopulation ();
            _loc_5 =Global.world.citySim.getScaledPopulation ()-param3.incrementalPopulation ;
            return Global.world.citySim.getScaledPopulation() - param3.incrementalPopulation;
        }//end

        public static int  incrementalExpansionCount (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (!Global.world.isHome())
            {
                return 0;
            }
            if (!param3 || !param3.hasOwnProperty("incrementalExpansion"))
            {
                param3.incrementalExpansion = Global.player.expansionsPurchased;
            }
            _loc_4 =Global.player.expansionsPurchased ;
            _loc_5 =Global.player.expansionsPurchased -param3.incrementalExpansion ;
            return Global.player.expansionsPurchased - param3.incrementalExpansion;
        }//end

        public static int  acceptTrain (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TAcceptTrain)
            {
                return 1;
            }
            return 0;
        }//end

        public static int  viralFeedCheck (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Object _loc_7 =null ;
            String _loc_8 =null ;
            double _loc_4 =0;
            if (Global.player.friendRewards && Global.player.friendRewards.get(param2))
            {
                _loc_5 = Global.player.friendRewards.get(param2);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 = _loc_5.get(_loc_6);
                    if (_loc_7.get("helpers"))
                    {
                        for(int i0 = 0; i0 < _loc_7.get("helpers").size(); i0++)
                        {
                        		_loc_8 = _loc_7.get("helpers").get(i0);

                            _loc_4 = _loc_4 + 1;
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public static int  capturePrey (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof TCapturePrey || param1 instanceof TBuyCapturePrey)
            {
                return 1;
            }
            return 0;
        }//end

        public static int  capturePreyByID (Transaction param1 ,String param2 ,Object param3 =null )
        {
            int _loc_4 =0;
            String _loc_5 =null ;
            if (param1 instanceof TCapturePrey || param1 instanceof TBuyCapturePrey)
            {
                _loc_4 = ((TCapturePrey)param1).preyID;
                if (param3.get("groupId"))
                {
                    _loc_5 = param3.get("groupId");
                }
                if (param2 == null || param2 == "" || findNameInNames(_loc_4.toString(), param2))
                {
                    if (_loc_5 == null || _loc_5 == (param1 as TCapturePrey).groupID)
                    {
                        return 1;
                    }
                }
            }
            return 0;
        }//end

        public static int  placeHeart (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            if (param1 instanceof TMechanicAction)
            {
                if (((TMechanicAction)param1).mechanicType == SocialInventoryManager.MECHANIC_DATA)
                {
                    _loc_4 = ((TMechanicAction)param1).getParams();
                    _loc_5 = _loc_4.get("operation");
                    _loc_6 = _loc_4.get("args");
                    if (_loc_5 == "transferFromSource" && _loc_6.indexOf("heart") > -1)
                    {
                        return 1;
                    }
                }
            }
            return 0;
        }//end

        public static int  helpHeart (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            if (param1 instanceof TMechanicAction)
            {
                if (((TMechanicAction)param1).mechanicType == SocialInventoryManager.MECHANIC_DATA)
                {
                    _loc_4 = ((TMechanicAction)param1).getParams();
                    _loc_5 = _loc_4.get("operation");
                    _loc_6 = _loc_4.get("args");
                    if (((TMechanicAction)param1).callingGameMode == "GMVisit" && _loc_6.indexOf("heart") > -1)
                    {
                        return 1;
                    }
                }
            }
            return 0;
        }//end

        public static int  countNumAtThisStreak (Transaction param1 ,String param2 ,Object param3 =null )
        {
            MechanicMapResource _loc_4 =null ;
            int _loc_5 =0;
            if (param1 instanceof TMechanicAction)
            {
                _loc_4 = MechanicMapResource(TMechanicAction(param1).ownerObject);
                if (_loc_4 instanceof IStreakHandler)
                {
                    if (_loc_4.getItemName() == param2)
                    {
                        _loc_5 = param3.get("streak");
                        return IStreakHandler(_loc_4).getCurrentStreak() >= _loc_5 ? (1) : (0);
                    }
                }
            }
            return 0;
        }//end

        public static int  checkStreakEffect (Transaction param1 ,String param2 ,Object param3 =null )
        {
            IStreakHandler _loc_6 =null ;
            int _loc_7 =0;
            _loc_4 =Global.world.getObjectsByNames(.get(param2) );
            double _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                _loc_6 = _loc_4.get(_loc_5);
                _loc_7 = param3.get("streak");
                return _loc_6.getCurrentStreakEffect() >= _loc_7 ? (1) : (0);
                _loc_5 = _loc_5 + 1;
            }
            return 0;
        }//end

        public static int  buildingremodeled (Transaction param1 ,String param2 ,Object param3 =null )
        {
            MechanicMapResource _loc_4 =null ;
            Object _loc_5 =null ;
            if (param1 instanceof TMechanicAction)
            {
                if (((TMechanicAction)param1).mechanicType == RemodelManager.MECHANIC_TYPE)
                {
                    _loc_4 = ((TMechanicAction)param1).ownerObject;
                    _loc_5 = _loc_4.getMechanicData();
                    if (_loc_5 != null && _loc_5.get(RemodelManager.MECHANIC_TYPE) != null && _loc_5.get(RemodelManager.MECHANIC_TYPE).get("complete") == true)
                    {
                        return 1;
                    }
                }
            }
            return 0;
        }//end

        public static int  carHarvest (Transaction param1 ,String param2 ,Object param3 =null )
        {
            if (param1 instanceof THarvestCar)
            {
                return 1;
            }
            return 0;
        }//end

        public static int  plantedGardenFlowersByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_4 =null ;
            String _loc_7 =null ;
            Garden _loc_8 =null ;
            ISlotMechanic _loc_9 =null ;
            if (param2 == "all")
            {
                _loc_4 = GardenManager.instance.getGardenTypes();
            }
            else
            {
                _loc_4 = breakApartNames(param2);
            }
            int _loc_5 =0;
            _loc_6 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.GARDEN) );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_7 = _loc_4.get(i0);

                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_8 = _loc_6.get(i0);

                    if (_loc_8.getItem().gardenType == _loc_7)
                    {
                        _loc_9 =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_8, MechanicManager.MECHANIC_SLOTS, MechanicManager.ALL);
                        if (_loc_9)
                        {
                            _loc_5 = _loc_5 + _loc_9.numFilledSlots;
                        }
                    }
                }
            }
            return _loc_5;
        }//end

        public static int  gardenFlowerCountByType (Transaction param1 ,String param2 ,Object param3 =null )
        {
            Array _loc_4 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            Garden _loc_8 =null ;
            ISlotMechanic _loc_9 =null ;
            if (param2 == "all")
            {
                _loc_4 = GardenManager.instance.getGardenTypes();
            }
            else
            {
                _loc_4 = breakApartNames(param2);
            }
            int _loc_5 =0;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		param2 = _loc_4.get(i0);

                _loc_5 = _loc_5 + GardenManager.instance.getFlowerAmount(param2);
            }
            _loc_6 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.GARDEN));
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_7 = _loc_4.get(i0);

                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_8 = _loc_6.get(i0);

                    if (_loc_8.getItem().gardenType == _loc_7)
                    {
                        _loc_9 =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_8, MechanicManager.MECHANIC_SLOTS, MechanicManager.ALL);
                        if (_loc_9)
                        {
                            _loc_5 = _loc_5 + _loc_9.numFilledSlots;
                        }
                    }
                }
            }
            return _loc_5;
        }//end

        public static int  checkTheme (Transaction param1 ,String param2 ,Object param3 =null )
        {
            _loc_4 = param2;
            return Global.world.isThemeEnabled(_loc_4) ? (1) : (0);
        }//end

    }


