package Classes.LogicComponents;

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
import Classes.effects.*;
import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.geom.*;

    public class HarvestableShipComponentBase
    {
        protected HarvestableShip m_ship ;
        protected SeaVehicle m_shipNPC ;
        protected double m_returnTime =-1;
        protected boolean m_shipReturnTrigger =false ;

        public  HarvestableShipComponentBase (HarvestableShip param1 )
        {
            this.m_ship = param1;
            return;
        }//end

        public Function  getProgressBarEndFunction ()
        {
            return void  ()
            {
                if (Global.isVisiting())
                {
                    return;
                }
                if (m_ship.harvest())
                {
                    m_ship.doHarvestDropOff();
                    startShipLeave();
                }
                return;
            }//end
            ;
        }//end

        public Function  getProgressBarStartFunction ()
        {
            return null;
        }//end

        public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                return;
            }//end
            ;
        }//end

        public boolean  harvest ()
        {
            boolean _loc_1 =false ;
            if (this.m_ship.isHarvestable())
            {
                if (!Global.isVisiting())
                {
                    this.m_ship.doDoobers();
                    if (this.m_ship.actionMode != this.m_ship.VISIT_REPLAY_ACTION)
                    {
                        GameTransactionManager.addTransaction(new THarvest(this.m_ship));
                        this.m_ship.trackAction(TrackedActionType.HARVEST);
                    }
                }
                this.m_ship.setState(HarvestableResource.STATE_PLANTED);
                this.m_ship.plantTime = GlobalEngine.getTimer();
                _loc_1 = true;
            }
            _loc_2 = this.m_ship;
            _loc_3 = this.m_ship.harvestCounter+1;
            _loc_2.harvestCounter = _loc_3;
            this.m_ship.updateObjectIndicator();
            return _loc_1;
        }//end

        public void  handlePlayAction ()
        {
            double _loc_1 =0;
            if (!Global.isVisiting() && this.m_ship.getState() == HarvestableResource.STATE_GROWN)
            {
                _loc_1 = this.m_ship.harvestingDefinition.harvestEnergyCost;
                if (!Global.player.checkEnergy(-_loc_1))
                {
                    this.m_ship.displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
                else
                {
                    this.m_ship.doEnergyChanges(-_loc_1, new Array("energy", "expenditures", "harvest_ship", this.m_ship.harvestingDefinition.name));
                    this.m_ship.removeStagePickEffect();
                    Global.world.citySim.pickupManager.enqueue("NPC_shipPickup", this.m_ship);
                }
            }
            return;
        }//end

        public void  onUpdate (double param1 )
        {
            if (this.m_ship.getState() == HarvestableResource.STATE_PLANTED)
            {
                this.calculateShipReturnTime();
                this.checkAndDoShipReturns();
            }
            else if (this.m_ship.getState() == HarvestableResource.STATE_GROWN && this.m_shipReturnTrigger)
            {
                this.m_shipReturnTrigger = false;
            }
            return;
        }//end

        public boolean  updateState (double param1 )
        {
            if (this.m_ship.isHarvestable() && this.m_ship.getState() == HarvestableResource.STATE_PLANTED)
            {
                this.m_ship.setState(HarvestableResource.STATE_GROWN);
                return true;
            }
            return false;
        }//end

        protected void  startShipLeave ()
        {
            _loc_1 = Global.gameSettings().getInt("shipExitX",-80);
            Array _loc_2 =new Array ();
            _loc_2.push(new Vector3(_loc_1, this.m_ship.positionY, 0));
            _loc_3 = Global.gameSettings().getItemByName(this.m_ship.harvestingDefinition.emptyNPCType);
            _loc_4 = _loc_3.navigateXml.walkSpeed;
            this.m_shipNPC = Global.world.citySim.npcManager.createShipByNameAtPosition(this.m_ship.harvestingDefinition.emptyNPCType, this.m_ship.getPosition(), _loc_2, false, _loc_4);
            return;
        }//end

        protected void  startShipArrive ()
        {
            this.m_shipReturnTrigger = true;
            _loc_1 = Global.gameSettings().getInt("shipExitX",-80);
            Vector3 _loc_2 =new Vector3(_loc_1 ,this.m_ship.positionY ,0);
            Array _loc_3 =new Array ();
            _loc_3.push(new Vector3(this.m_ship.positionX, this.m_ship.positionY, 0));
            _loc_4 = Global.gameSettings().getItemByName(this.m_ship.harvestingDefinition.loadedNPCType);
            _loc_5 = Global.gameSettings().getItemByName(this.m_ship.harvestingDefinition.loadedNPCType).navigateXml.walkSpeed;
            this.m_shipNPC = Global.world.citySim.npcManager.createShipByNameAtPosition(this.m_ship.harvestingDefinition.loadedNPCType, _loc_2, _loc_3, false, _loc_5);
            return;
        }//end

        protected double  calculateShipReturnTime ()
        {
            if (this.m_returnTime != -1)
            {
                return this.m_returnTime;
            }
            _loc_1 = Global.gameSettings().getInt("shipExitX",-80);
            Vector3 _loc_2 =new Vector3(_loc_1 ,this.m_ship.positionY ,0);
            _loc_3 = MathUtil.distance(newPoint(this.m_ship.positionX,this.m_ship.positionY),newPoint(_loc_2.x,_loc_2.y));
            _loc_4 = Global.gameSettings().getItemByName(this.m_ship.harvestingDefinition.loadedNPCType);
            _loc_5 = Global.gameSettings().getItemByName(this.m_ship.harvestingDefinition.loadedNPCType).navigateXml.walkSpeed;
            this.m_returnTime = _loc_3 / _loc_5;
            return this.m_returnTime;
        }//end

        protected void  checkAndDoShipReturns ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            if (this.m_ship.plantTime > 0)
            {
                _loc_1 = GlobalEngine.getTimer() - this.m_ship.plantTime;
                _loc_2 = this.m_ship.getGrowTimeDelta();
                if (_loc_1 < _loc_2)
                {
                    _loc_3 = (_loc_2 - _loc_1) / 1000;
                    if (_loc_3 < this.m_returnTime && !this.m_shipReturnTrigger)
                    {
                        this.startShipArrive();
                    }
                }
            }
            return;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            if (this.m_ship.getState() == HarvestableResource.STATE_PLANTED)
            {
                _loc_1 = GameUtil.formatMinutesSeconds(this.m_ship.getGrowTimeLeft());
                return ZLoc.t("Main", "AwayShip", {time:_loc_1});
            }
            return "";
        }//end

        public String  getToolTipAction ()
        {
            return this.m_ship.getGameModeToolTipAction();
        }//end

        public String  getActionText ()
        {
            return ZLoc.t("Main", "Unloading");
        }//end

        public double  getHarvestTime ()
        {
            return Global.gameSettings().getNumber("actionBarHarvest");
        }//end

        public void  createStagePickEffect ()
        {
            if (this.m_ship.getState() == HarvestableResource.STATE_GROWN)
            {
                this.createStagePickEffectHelper(StagePickEffect.PICK_1);
            }
            return;
        }//end

        protected void  createStagePickEffectHelper (String param1 )
        {
            _loc_2 = this.m_ship.stagePickEffect;
            if (!_loc_2)
            {
                _loc_2 =(StagePickEffect) MapResourceEffectFactory.createEffect(this.m_ship, EffectType.STAGE_PICK);
                _loc_2.setPickType(param1);
                _loc_2.float();
            }
            else
            {
                _loc_2.setPickType(param1);
                _loc_2.reattach();
                _loc_2.float();
            }
            this.m_ship.stagePickEffect = _loc_2;
            return;
        }//end

        public boolean  enableUpdateArrow ()
        {
            return !Global.isVisiting() && this.m_ship.getState() == HarvestableResource.STATE_GROWN;
        }//end

        public boolean  checkPlacementRequirements (int param1 ,int param2 )
        {
            Pier _loc_5 =null ;
            Array _loc_6 =null ;
            _loc_3 = Global.world.getObjectsByClass(Pier);
            _loc_4 = this.m_ship.getSizeNoClone();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                if (_loc_5.shipCollidedWithDock(new Rectangle(param1, param2, _loc_4.x, _loc_4.y)))
                {
                    return false;
                }
            }
            _loc_6 = Global.world.findConnectingMapResources(param1, param2, _loc_4.x, _loc_4.y, null, false, Dock);
            if (_loc_6.length > 0)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isHarvestable ()
        {
            return this.m_ship.getGrowPercentage() == 100 && this.m_ship.getState() != HarvestableResource.STATE_FALLOW || this.m_ship.getState() == HarvestableResource.STATE_GROWN;
        }//end

        public Array  makeDoobers (Item param1 ,Array param2 ,double param3 =1)
        {
            return Global.player.processRandomModifiers(param1, this.m_ship, true, param2);
        }//end

        public void  onStateChanged (String param1 ,String param2 )
        {
            return;
        }//end

        public void  setPlantedWhenShipArrives ()
        {
            _loc_1 = GlobalEngine.getTimer();
            _loc_2 = this.m_ship.getGrowTimeDelta();
            this.m_ship.plantTime = _loc_1 - _loc_2 + this.calculateShipReturnTime() * 1000;
            return;
        }//end

        public boolean  isSellable ()
        {
            return true;
        }//end

    }



