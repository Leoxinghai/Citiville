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
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.automation.ui.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class ActionAutomationManager
    {
        private String m_mode ;
        private int m_actionDiameter ;
        private Vector3 m_clickedLocation ;
        private Timer m_statusTimer ;
        private Array m_statusQueue ;
        public static  String TYPE_RENT_COLLECTOR ="rentCollector";
        public static  String TYPE_ARMORED_TRUCK ="armoredTruck";
        public static  String TYPE_SUPPLY_TRUCK ="supplyTruck";
        private static ActionAutomationManager m_instance ;

        public  ActionAutomationManager ()
        {
            this.m_actionDiameter = Global.gameSettings().getInt("automationAOEDiameter", 12);
            this.m_statusQueue = new Array();
            this.m_statusTimer = new Timer(500);
            this.m_statusTimer.addEventListener(TimerEvent.TIMER, this.onStatusTimerTick, false, 0, true);
            return;
        }//end

        public String  mode ()
        {
            return this.m_mode;
        }//end

        public void  mode (String param1 )
        {
            this.m_mode = param1;
            return;
        }//end

        public int  actionDiameter ()
        {
            return this.m_actionDiameter;
        }//end

        public boolean  isEligibleForFeature ()
        {
            if (Global.player.level < Global.gameSettings().getInt("automationRequiredLevel", 35))
            {
                return false;
            }
            Array _loc_1 =.get(ExperimentDefinitions.AUTOMATION_PRICE_A ,ExperimentDefinitions.AUTOMATION_PRICE_B ,ExperimentDefinitions.AUTOMATION_PRICE_C) ;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_AUTOMATION);
            if (_loc_1.indexOf(_loc_2) < 0)
            {
                return false;
            }
            return true;
        }//end

        public boolean  isEligibleForAtLeastOneAutomator ()
        {
            String _loc_2 =null ;
            _loc_1 = Global.gameSettings().getAutomationMenuItems().attribute("type");
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (this.isEligibleForAutomator(_loc_2))
                {
                    return true;
                }
            }
            return false;
        }//end

        public boolean  isEligibleForAutomator (String param1 )
        {
            XMLList automatorDefs ;
            String buildingName ;
            Array buildingsOnMap ;
            MapResource currBuilding ;
            automatorName = param1;
            if (this.isEligibleForFeature)
            {
                int _loc_4 =0;
                _loc_5 = Global.gameSettings().getAutomationMenuItems();
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (attribute("type") == automatorName)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                automatorDefs = _loc_3;
                if (automatorDefs.length() > 0)
                {
                    buildingName = automatorDefs.get(0).attribute("requireOnMap").toString();
                    buildingsOnMap = Global.world.getObjectsByNames(.get(buildingName));
                    if (buildingsOnMap.length > 0)
                    {
                        int _loc3 =0;
                        _loc4 = buildingsOnMap;
                        for(int i0 = 0; i0 <  i0 = 0; i0 <  in buildingsOnMap.size(); i0++.size(); i0++)
                        {
                        		< =  i0 = 0; i0 <  in buildingsOnMap.size(); i0++.get(i0);
                        	currBuilding =  in buildingsOnMap.get(i0);


                            if (!currBuilding.isNeedingRoad)
                            {
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return false;
        }//end

        public boolean  isGameObjectEligibleForAction (GameObject param1 )
        {
            Residence _loc_2 =null ;
            Business _loc_3 =null ;
            Business _loc_4 =null ;
            if (param1.isLocked() || param1.replayLock > 0)
            {
                return false;
            }
            switch(this.m_mode)
            {
                case TYPE_RENT_COLLECTOR:
                {
                    _loc_2 =(Residence) param1;
                    if (_loc_2 && !_loc_2.isNeedingRoad && _loc_2.isHarvestable())
                    {
                        return true;
                    }
                    break;
                }
                case TYPE_ARMORED_TRUCK:
                {
                    _loc_3 =(Business) param1;
                    if (_loc_3 && !_loc_3.isNeedingRoad && _loc_3.isHarvestable())
                    {
                        return true;
                    }
                    break;
                }
                case TYPE_SUPPLY_TRUCK:
                {
                    _loc_4 =(Business) param1;
                    if (_loc_4 && !_loc_4.isNeedingRoad && _loc_4.needsGoods() && _loc_4.playerHasEnoughGoods())
                    {
                        return true;
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        public boolean  openAutomationUI ()
        {
            XMLList _loc_1 =null ;
            Array _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            Function _loc_6 =null ;
            String _loc_7 =null ;
            int _loc_8 =0;
            boolean _loc_9 =false ;
            Array _loc_10 =null ;
            MapResource _loc_11 =null ;
            boolean _loc_12 =false ;
            boolean _loc_13 =false ;
            Array _loc_14 =null ;
            ConstructionSite _loc_15 =null ;
            boolean _loc_16 =false ;
            String _loc_17 =null ;
            XMLList _loc_18 =null ;
            Array _loc_19 =null ;
            String _loc_20 =null ;
            if (this.isEligibleForFeature)
            {
                _loc_1 = Global.gameSettings().getAutomationMenuItems().attribute("requireOnMap");
                _loc_2 = new Array();
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_3 = _loc_1.get(i0);

                    _loc_2.push(_loc_3);
                }
                _loc_7 = "AutomationNeedsDepots";
                _loc_8 = GenericDialogView.TYPE_OK;
                _loc_9 = false;
                _loc_10 = Global.world.getObjectsByNames(_loc_2);
                for(int i0 = 0; i0 < _loc_10.size(); i0++)
                {
                	_loc_11 = _loc_10.get(i0);

                    if (!_loc_11.isNeedingRoad)
                    {
                        _loc_9 = true;
                        break;
                    }
                }
                if (!_loc_9 && _loc_10.length > 0)
                {
                    _loc_4 = ZLoc.t("Dialogs", "AutomationRoadsDepots_body", {cityName:Global.player.cityName});
                    _loc_5 = ZLoc.t("Dialogs", "AutomationRoadsDepots_button");
                    _loc_6 = this.onShowDepotCallback;
                    UI.displayPopup(new GenericDialog(_loc_4, _loc_7, _loc_8, _loc_6, _loc_7, null, true, 0, "", null, _loc_5));
                    return false;
                }
                if (!_loc_9)
                {
                    _loc_13 = false;
                    _loc_14 = Global.world.getObjectsByClass(ConstructionSite);
                    for(int i0 = 0; i0 < _loc_14.size(); i0++)
                    {
                    	_loc_15 = _loc_14.get(i0);

                        if (_loc_2.indexOf(_loc_15.targetName) >= 0)
                        {
                            _loc_13 = true;
                            break;
                        }
                    }
                    if (_loc_13)
                    {
                        _loc_4 = ZLoc.t("Dialogs", "AutomationRoadsDepots_body", {cityName:Global.player.cityName});
                        _loc_5 = ZLoc.t("Dialogs", "AutomationRoadsDepots_button");
                        _loc_6 = this.onShowDepotCallback;
                        UI.displayPopup(new GenericDialog(_loc_4, _loc_7, _loc_8, _loc_6, _loc_7, null, true, 0, "", null, _loc_5));
                        return false;
                    }
                }
                _loc_12 = false;
                if (!_loc_9)
                {
                    _loc_16 = false;
                    for(int i0 = 0; i0 < _loc_2.size(); i0++)
                    {
                    	_loc_17 = _loc_2.get(i0);

                        if (Global.player.inventory.getItemCountByName(_loc_17) > 0)
                        {
                            _loc_16 = true;
                            break;
                        }
                    }
                    if (_loc_16)
                    {
                        _loc_4 = ZLoc.t("Dialogs", "AutomationNeedsDepots_body", {cityName:Global.player.cityName});
                        _loc_5 = ZLoc.t("Dialogs", "AutomationNeedsDepots_button");
                        _loc_6 = this.onNeedsDepotCallback;
                        UI.displayPopup(new GenericDialog(_loc_4, _loc_7, _loc_8, _loc_6, _loc_7, null, true, 0, "", null, _loc_5));
                        return false;
                    }
                }
                if (_loc_9)
                {
                    _loc_18 = Global.gameSettings().getAutomationMenuItems().attribute("type");
                    _loc_19 = new Array();
                    for(int i0 = 0; i0 < _loc_18.size(); i0++)
                    {
                    	_loc_20 = _loc_18.get(i0);

                        _loc_19.push(_loc_20);
                    }
                    UI.displayTabbedCatalog(new AutomationUI(), _loc_19, new CatalogParams("rentCollector"));
                    return true;
                }
            }
            return false;
        }//end

        public boolean  automate (Vector3 param1 )
        {
            Debug.debug4("ActionAutomationManager"+"automate");
            int _loc_4 =0;
            MapResource _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            MapResource _loc_8 =null ;
            String _loc_9 =null ;
            Function _loc_10 =null ;
            Function _loc_11 =null ;
            boolean _loc_12 =false ;
            _loc_2 = this.getActionablesInRadius(param1);
            String _loc_3 ="";
            if (_loc_2.length > 0)
            {
                _loc_4 = 0;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_5 = _loc_2.get(i0);

                    _loc_4 = _loc_4 + _loc_5.getItem().harvestEnergyCost;
                }
                _loc_6 = 0;
                if (_loc_2.length >= Global.gameSettings().getInt("automationNumEntitiesBeforeSavingsKickIn", 5))
                {
                    _loc_6 = Math.round(_loc_4 * Global.gameSettings().getNumber("automationCostSavingsRatio", 0.2));
                }
                _loc_4 = _loc_4 - _loc_6;
                if (!Global.player.checkEnergy(-_loc_4))
                {
                    UI.displayStatus(ZLoc.t("Main", "NeedEnergy", {amount:_loc_4}), Global.stage.mouseX, Global.stage.mouseY);
                    return false;
                }
                _loc_7 = _loc_2.length;
                if (!Global.player.checkRegenerableResource(RegenerableResource.GAS, -_loc_7))
                {
                    return false;
                }
                Global.player.updateEnergy(-_loc_4, ["energy", "expenditures", "autocollect_house"]);
                Global.player.updateRegenerableResource(RegenerableResource.GAS, -_loc_7);
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_8 = _loc_2.get(i0);

                    if (_loc_8)
                    {
                        _loc_10 = _loc_8.getProgressBarStartFunction();
                        _loc_11 = _loc_8.getProgressBarEndFunction();
                        if (_loc_11 != null && _loc_10 != null)
                        {
                            _loc_8.setBeingAutoHarvested(true);
                            _loc_12 = _loc_10();
                            if (_loc_12)
                            {
                                _loc_11();
                                _loc_8.setHighlighted(false);
                            }
                            _loc_8.setBeingAutoHarvested(false);
                        }
                    }
                }
                _loc_9 = "";
                if (_loc_6 > 0)
                {
                    _loc_9 = ZLoc.t("Main", "SpendEnergySaved", {energyUsed:-_loc_4, energySaved:_loc_6});
                }
                else
                {
                    _loc_9 = ZLoc.t("Main", "SpendEnergy", {energy:-_loc_4});
                }
                this.addToStatusQueue(_loc_9, new EmbeddedArt.smallEnergyIcon());
                this.addToStatusQueue(ZLoc.t("Main", "SpendGas", {gas:-_loc_7}));
                StatsManager.sample(100, StatsKingdomType.GAME_ACTIONS, "automation", this.m_mode.toLowerCase(), _loc_2.length.toString());
                TransactionManager.addTransaction(new TAutoCollectRent(_loc_2), true);
                return true;
            }
            return false;
        }//end

        public Array  getActionablesInRadius (Vector3 param1 )
        {
            this.m_clickedLocation = param1;
            _loc_2 = Global.world.getObjectsByPredicate(this.predicateActionableMapResource);
            return _loc_2;
        }//end

        private boolean  predicateActionableMapResource (GameObject param1 )
        {
            _loc_2 = param1.getPositionNoClone();
            _loc_3 = param1.getSizeNoClone();
            _loc_4 = _loc_2.x+_loc_3.x*0.5-this.m_clickedLocation.x;
            _loc_5 = _loc_2.y+_loc_3.y*0.5-this.m_clickedLocation.y;
            if (Math.abs(_loc_4) > this.m_actionDiameter * 0.5 || Math.abs(_loc_5) > this.m_actionDiameter * 0.5)
            {
                return false;
            }
            return this.isGameObjectEligibleForAction(param1);
        }//end

        private void  addToStatusQueue (String param1 ,DisplayObject param2 )
        {
            this.m_statusQueue.push({message:param1, icon:param2, x:Global.stage.mouseX, y:Global.stage.mouseY});
            if (!this.m_statusTimer.running)
            {
                this.m_statusTimer.start();
            }
            return;
        }//end

        private void  onNeedsDepotCallback (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                UI.displayInventory("mun_rentcollectordepot");
            }
            return;
        }//end

        private void  onShowDepotCallback (GenericPopupEvent event )
        {
            MapResource targetBuilding ;
            String buildingName ;
            Array buildings ;
            Array conSites ;
            ConstructionSite conSite ;
            e = event;
            buildingNamesXML = Global.gameSettings().getAutomationMenuItems().attribute("requireOnMap");
            Array buildingNames ;
            int _loc_3 =0;
            _loc_4 = buildingNamesXML;
            for(int i0 = 0; i0 <  i0 = 0; i0 < Name in buildingNamesXML.size(); i0++.size(); i0++)
            {
            		Name =  i0 = 0; i0 < Name in buildingNamesXML.size(); i0++.get(i0);
            	buildingName = Name in buildingNamesXML.get(i0);


                buildingNames.push(buildingName);
            }
            buildings = Global.world.getObjectsByNames(buildingNames);
            if (buildings.length > 0)
            {
                targetBuilding = buildings.get(0);
            }
            else
            {
                conSites = Global.world.getObjectsByClass(ConstructionSite);
                _loc_3 = 0;
                _loc_4 = conSites;
                for(int i0 = 0; i0 < conSites.size(); i0++)
                {
                	conSite = conSites.get(i0);


                    if (buildingNames.indexOf(conSite.targetName) >= 0)
                    {
                        targetBuilding = conSite;
                        break;
                    }
                }
            }
            if (targetBuilding)
            {
                targetBuilding.lock();
                Global .world .centerOnObjectWithCallback (targetBuilding ,1,void  ()
            {
                targetBuilding.unlock();
                if (targetBuilding.isNeedingRoad)
                {
                    targetBuilding.displayStatus(ZLoc.t("Dialogs", "NotConnectedToRoad"));
                }
                return;
            }//end
            );
            }
            return;
        }//end

        private void  onStatusTimerTick (TimerEvent event )
        {
            Object _loc_2 =null ;
            if (this.m_statusQueue.length > 0)
            {
                _loc_2 = this.m_statusQueue.shift();
                UI.displayStatus(_loc_2.get("message"), _loc_2.get("x"), _loc_2.get("y"), _loc_2.get("icon"));
            }
            if (this.m_statusQueue.length == 0)
            {
                this.m_statusTimer.stop();
            }
            return;
        }//end

        public static ActionAutomationManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new ActionAutomationManager;
            }
            return m_instance;
        }//end

    }




