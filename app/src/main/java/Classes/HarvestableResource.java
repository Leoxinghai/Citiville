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

import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import GameMode.*;

import com.zynga.skelly.util.*;

import com.xinghai.Debug;

    public class HarvestableResource extends MechanicMapResource implements IPeepTarget, IPlantable
    {
        protected double m_buildTime ;
        protected double m_plantTime ;
        protected double m_timeSinceGrowUpdate =0;
        protected double m_growPercentage =0;
        protected double m_witherPercentage =0;
        protected int m_buildPowerups =0;
        protected int m_harvestCounter =0;
        protected double m_heldEnergy =0;
        protected int m_growTimeLeft ;
        protected Item m_contract ;
        protected double m_contractGrowTime ;
        protected boolean m_witherOn ;
        protected boolean m_finishing =false ;
        protected boolean m_isAccelerating =false ;
        protected boolean m_isHarvesting =false ;
        protected double m_accelerateStartTime =0;
        protected double m_growTimeLeftAtAccelStart =0;
        protected String m_lastVisitorHelpType ="";
        protected boolean m_firstHarvestAttempt =true ;
        protected Array m_commodities ;
        public static  String STATE_PLOWED ="plowed";
        public static  String STATE_PLANTED ="planted";
        public static  String STATE_GROWN ="grown";
        public static  String STATE_FALLOW ="fallow";
        public static  String STATE_WITHERED ="withered";
        public static  String IS_PLANTING ="planting";
        public static  String IS_HARVESTING ="harvesting";
        public static  String IS_PLOWING ="plowing";
        public static  String IS_RESTING ="";
public static  double GROW_UPDATE_RATE =1;
        public static  String WORKER_BUCKET_PREFIX ="w";

        public  HarvestableResource (String param1 )
        {
            super(param1);
            setState(STATE_PLOWED);
            this.plantTime = GlobalEngine.getTimer();
            this.m_buildTime = GlobalEngine.getTimer();
            return;
        }//end

         public Object  getSaveObject ()
        {
            _loc_1 = super.getSaveObject();
            _loc_1.plantTime = this.plantTime;
            _loc_1.buildTime = this.m_buildTime;
            return _loc_1;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.plantTime = param1.plantTime;
            this.m_buildTime = param1.buildTime;
            this.harvestCounter = param1.harvestCounter;
            this.m_buildPowerups = param1.buildPowerups;
            this.m_witherOn = param1.witherOn;
            m_dbgServerConnectsToRoad = param1.isConnectedToRoad == true;
            this.updatePercentages();
            return;
        }//end

        public boolean  isWitherOn ()
        {
            return this.m_witherOn;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         protected Vector3 Vector  computeDefaultHotspots ().<>
        {
            Vector _loc_1.<Vector3 >=new Vector<Vector3 >(1);
            _loc_1.put(0,  new Vector3(0, 0));
            return _loc_1;
        }//end

        public double  getGrowTimeDelta ()
        {
            _loc_1 = Global.gameSettings().inGameDaySeconds*1000;
            _loc_2 = harvestingDefinition.growTime;
            return _loc_2 * _loc_1 * Global.gameSettings().growMultiplier;
        }//end

        protected double  getWitherTimeDelta ()
        {
            _loc_1 = Global.gameSettings().inGameDaySeconds*1000;
            _loc_2 = harvestingDefinition.growTime;
            return _loc_2 * _loc_1 * Global.gameSettings().witherMultiplier;
        }//end

        public int  getGrowTimeLeft ()
        {
            return this.m_growTimeLeft;
        }//end

        public boolean  isHarvestable ()
        {
            return m_state == STATE_GROWN;
        }//end

        public boolean  isPlantable (String param1 )
        {
            return m_state == STATE_PLOWED && hasValidId();
        }//end

        public boolean  isPlanted ()
        {
            return m_state == STATE_PLANTED;
        }//end

        public boolean  isPlowable ()
        {
            return m_state == STATE_FALLOW || m_state == STATE_WITHERED;
        }//end

        public boolean  isWithered ()
        {
            return m_state == STATE_WITHERED;
        }//end

        public boolean  isGrown ()
        {
            return m_state == STATE_GROWN;
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

        public Item  contract ()
        {
            return this.m_contract;
        }//end

        public void  boostGrowth (double param1 =2)
        {
            _loc_2 = param1;
            this.m_plantTime = this.m_plantTime - _loc_2;
            return;
        }//end

        public void  setFullGrown ()
        {
            this.m_plantTime = GlobalEngine.getTimer() - this.getGrowTimeDelta();
            this.m_growPercentage = 100;
            this.m_witherPercentage = 0;
            setState(STATE_GROWN);
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            boolean _loc_4 =false ;
            if (param1 !=null)
            {
                this.m_timeSinceGrowUpdate = this.m_timeSinceGrowUpdate + param1;
            }
            _loc_2 = this.m_timeSinceGrowUpdate >=HarvestableResource.GROW_UPDATE_RATE ;
            if (_loc_2 || this.m_isAccelerating)
            {
                this.updatePercentages();
                this.m_timeSinceGrowUpdate = 0;
            }
            _loc_3 = this.updateState(param1);
            if (!_loc_3 && _loc_2)
            {
                _loc_4 = this.updateStatePhase();
                if (_loc_4)
                {
                    reloadImage();
                }
            }
            super.onUpdate(param1);
            return;
        }//end

        protected boolean  updateState (double param1 )
        {
            boolean _loc_2 =false ;
            if (m_state == STATE_PLANTED && this.getGrowPercentage() == 100)
            {
                setState(STATE_GROWN);
            }
            else if (m_state == STATE_GROWN && this.getWitherPercentage() == 100)
            {
                setState(STATE_WITHERED);
            }
            else
            {
                _loc_2 = false;
            }
            return _loc_2;
        }//end

        public void  forceGrowthStateUpdate ()
        {
            this.updatePercentages();
            this.updateState(0);
            setState(getState());
            return;
        }//end

         protected void  onStateChanged (String param1 ,String param2 )
        {
            super.onStateChanged(param1, param2);
            if (param1 != param2)
            {
                this.updateStatePhase();
            }
            return;
        }//end

        protected boolean  updateStatePhase ()
        {
            _loc_1 = this.computeStatePhase();
            if (_loc_1 != m_statePhase)
            {
                m_statePhase = _loc_1;
                return true;
            }
            return false;
        }//end

        protected String  computeStatePhase ()
        {
            return Item.DEFAULT_PHASE;
        }//end

         public void  onPlayAction ()
        {
            if (m_visitReplayLock > 0)
            {
                return;
            }
            super.onPlayAction();
            return;
        }//end

        public boolean  plow ()
        {
            return false;
        }//end

        public boolean  plant (String param1 )
        {
            return false;
        }//end

        public boolean  harvest ()
        {
            return false;
        }//end

        protected void  onVisitorHelp (String param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            if (Global.isVisiting())
            {
                _loc_2 = friendVisitRepGain();
                _loc_3 = Global.gameSettings().getInt("friendHelpDefaultGoodsReward", 1);
                _loc_4 = Global.gameSettings().getInt("friendHelpDefaultCoinReward", 1);
                switch(param1)
                {
                    case STATE_GROWN:
                    {
                        finalizeAndAwardVisitorHelp(this.m_lastVisitorHelpType, _loc_2, 0, _loc_3);
                        break;
                    }
                    default:
                    {
                        finalizeAndAwardVisitorHelp(this.m_lastVisitorHelpType, _loc_2, _loc_4, 0);
                        break;
                        break;
                    }
                }
            }
            return;
        }//end

        public boolean  visitBoost ()
        {
            _loc_1 = Global.isVisiting();
            this.setVisitorInteractedHighlighted(_loc_1);
            _loc_2 = this.getVisitBoost();
            _loc_3 = this.getInstantLimit();
            _loc_4 = this.getGrowTimeDelta();
            if (this.m_growTimeLeft * 1000 < _loc_2 || _loc_4 <= _loc_3)
            {
                this.setFullGrown();
            }
            else
            {
                this.boostGrowth(_loc_2);
            }
            this.onVisitorHelp(STATE_PLANTED);
            return true;
        }//end

        protected double  getInstantLimit ()
        {
            _loc_1 = Global.gameSettings().dailyCollectCycleTimeSeconds*1000;
            _loc_2 = Global.gameSettings().boostGrowInstantHourLimit;
            if (_loc_2 != 0)
            {
                _loc_2 = _loc_2 / 23;
            }
            return _loc_2 * _loc_1 * Global.gameSettings().growMultiplier;
        }//end

        protected double  getVisitBoost ()
        {
            _loc_1 = Global.gameSettings().inGameDaySeconds*1000;
            _loc_2 = harvestingDefinition.growTime;
            return _loc_2 * _loc_1 * Global.gameSettings().growMultiplier * Global.gameSettings().boostGrowMultiplier;
        }//end

        public boolean  revive ()
        {
            this.setFullGrown();
            this.onVisitorHelp(STATE_WITHERED);
            return true;
        }//end

        public double  getHarvestTime ()
        {
            return Global.gameSettings().getNumber("actionBarHarvest");
        }//end

        public double  plantTime ()
        {
            return this.m_plantTime;
        }//end

        public void  plantTime (double param1 )
        {
            this.m_plantTime = param1;
            this.m_growPercentage = 0;
            this.m_witherPercentage = 0;
            return;
        }//end

        public int  harvestCounter ()
        {
            return this.m_harvestCounter;
        }//end

        public void  harvestCounter (int param1 )
        {
            this.m_harvestCounter = param1;
            return;
        }//end

        public Object  doHarvestDropOff (boolean param1 =true )
        {
            Sounds.playFromSet(Sounds.SET_MONEY);
            return null;
        }//end

         public String  getActionText ()
        {
            switch(m_state)
            {
                case STATE_WITHERED:
                {
                    if (Global.isVisiting())
                    {
                        return this.getRevivingText();
                    }
                    return this.getClearingText();
                }
                case STATE_PLANTED:
                {
                    if (Global.isVisiting())
                    {
                        return this.getWateringText();
                    }
                    return super.getActionText();
                }
                case STATE_GROWN:
                {
                    return this.getHarvestingText();
                }
                default:
                {
                    return super.getActionText();
                    break;
                }
            }
        }//end

        public String  getFinishingText ()
        {
            return ZLoc.t("Main", "Finishing");
        }//end

        public String  getHarvestingText ()
        {
            return ZLoc.t("Main", "Harvesting");
        }//end

        public String  getWateringText ()
        {
            return ZLoc.t("Main", "Watering");
        }//end

        public String  getRevivingText ()
        {
            return ZLoc.t("Main", "Reviving");
        }//end

        public String  getClearingText ()
        {
            return ZLoc.t("Main", "Clearing");
        }//end

        protected void  updatePercentages ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            double _loc_6 =0;
            this.m_growPercentage = 0;
            this.m_witherPercentage = 0;
            if (this.plantTime > 0)
            {
                _loc_1 = GlobalEngine.getTimer();
                _loc_2 = _loc_1 - this.plantTime;
                _loc_3 = this.getGrowTimeDelta();
                if (_loc_2 < _loc_3)
                {
                    _loc_4 = _loc_2 / _loc_3;
                    this.m_growPercentage = Math.floor(MathUtil.clamp(_loc_4 * 100, 0, 100));
                    if (this.m_isAccelerating && this.m_accelerateStartTime > 0 && this.m_growTimeLeftAtAccelStart > 0)
                    {
                        this.m_growTimeLeft = this.calculateAcceleratedTimeLeft(_loc_1);
                    }
                    else
                    {
                        this.m_growTimeLeft = (_loc_3 - _loc_2) / 1000;
                    }
                }
                else
                {
                    this.m_growPercentage = 100;
                    if (this.isWitherOn)
                    {
                        _loc_5 = this.getWitherTimeDelta();
                        if (_loc_2 < _loc_5)
                        {
                            _loc_6 = _loc_2 - _loc_3;
                            _loc_4 = _loc_6 / (_loc_5 - _loc_3);
                            this.m_witherPercentage = Math.floor(MathUtil.clamp(_loc_4 * 100, 0, 100));
                        }
                        else
                        {
                            this.m_witherPercentage = 100;
                        }
                    }
                }
            }
            return;
        }//end

        protected int  calculateAcceleratedTimeLeft (double param1 )
        {
            _loc_2 = this.m_accelerateStartTime+this.getHarvestTime()*1000;
            _loc_3 = _loc_2(-param1)/(_loc_2-this.m_accelerateStartTime)*this.m_growTimeLeftAtAccelStart/1000;
            if (_loc_3 < 0)
            {
                _loc_3 = 0;
            }
            return _loc_3;
        }//end

        public double  getGrowPercentage ()
        {
            return this.m_growPercentage;
        }//end

        public double  getWitherPercentage ()
        {
            return this.m_witherPercentage;
        }//end

        public double  getBuildingSpeedUpCost ()
        {
            return Math.max(1, getItem().buildEnergyCost * Global.gameSettings().buildingSpeedupCostMultiplier);
        }//end

        public void  onHarvestComplete (Object param1 )
        {
            this.parseAndCheckDooberResults(param1);
            return;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.sellPrice;
        }//end

        protected NPC  getNextActor (MapResource param1 )
        {
            NPC _loc_5 =null ;
            double _loc_7 =0;
            _loc_2 = Global.world.citySim.npcManager.getFreeAgentWalkers(this);
            NPC _loc_3 =null ;
            _loc_4 = double.POSITIVE_INFINITY;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_5 = _loc_2.get(i0);

                if (_loc_5.isFreeAgent)
                {
                    _loc_7 = _loc_5.getPosition().subtract(this.getPosition()).length();
                    if (_loc_7 < _loc_4)
                    {
                        _loc_4 = _loc_7;
                        _loc_3 = _loc_5;
                    }
                }
            }
            if (_loc_3 != null)
            {
                return _loc_3;
            }
            _loc_6 = Global.world.citySim.npcManager.createWalker(param1,false);
            return Global.world.citySim.npcManager.createWalker(param1, false);
        }//end

         public Class  getCursor ()
        {
            Debug.debug6("HarvestableResource.getCursor");
            _loc_1 = super.getCursor();
            switch(m_state)
            {
                case STATE_GROWN:
                {
                    _loc_1 = this.getGrownCursor();
                    break;
                }
                case STATE_PLANTED:
                {
                    _loc_1 = this.getPlantedCursor();
                    break;
                }
                case STATE_PLOWED:
                {
                    _loc_1 = this.getPlowedCursor();
                    break;
                }
                case STATE_WITHERED:
                {
                    _loc_1 = this.getWitheredCursor();
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

        protected Class  getGrownCursor ()
        {
            return null;
        }//end

        protected Class  getPlantedCursor ()
        {
            return null;
        }//end

        protected Class  getPlowedCursor ()
        {
            return null;
        }//end

        protected Class  getWitheredCursor ()
        {
            return null;
        }//end

        protected String  getFallowToolTipStatus ()
        {
            return ZLoc.t("Main", "FallowPlot");
        }//end

        protected String  getPlowedToolTipStatus ()
        {
            return ZLoc.t("Main", "PlowedPlot");
        }//end

        protected String  getPlantedToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_1 =null ;
            if (this.m_isAccelerating && this.m_accelerateStartTime > 0 && this.m_growTimeLeftAtAccelStart > 0)
            {
                _loc_1 = ZLoc.t("Main", "BuildingAccelerating");
            }
            else
            {
                _loc_2 = GameUtil.formatMinutesSeconds(this.getGrowTimeLeft());
                _loc_1 = this.getTimeLeftString(_loc_2);
            }
            return _loc_1;
        }//end

        protected String  getTimeLeftString (String param1 )
        {
            return ZLoc.t("Main", "PlantedPlot", {time:param1});
        }//end

        protected String  getGrownToolTipStatus ()
        {
            return ZLoc.t("Main", "GrownPlot");
        }//end

        protected String  getWitheredToolTipStatus ()
        {
            String _loc_1 =null ;
            _loc_2 = Global.gameSettings().getInt("plowCost");
            if (Global.player.canBuy(_loc_2, false))
            {
                _loc_1 = ZLoc.t("Main", "WitheredPlot");
            }
            else
            {
                _loc_1 = ZLoc.t("Main", "WitheredPlot_NoMoney");
            }
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            if (!Global.isVisiting() && !Global.world.isEditMode && !(Global.world.getTopGameMode() instanceof GMRemodel))
            {
                switch(m_state)
                {
                    case STATE_FALLOW:
                    {
                        _loc_1 = this.getFallowToolTipStatus();
                        break;
                    }
                    case STATE_PLOWED:
                    {
                        _loc_1 = this.getPlowedToolTipStatus();
                        break;
                    }
                    case STATE_PLANTED:
                    {
                        _loc_1 = this.getPlantedToolTipStatus();
                        break;
                    }
                    case STATE_GROWN:
                    {
                        _loc_1 = this.getGrownToolTipStatus();
                        break;
                    }
                    case STATE_WITHERED:
                    {
                        _loc_1 = this.getWitheredToolTipStatus();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            int _loc_2 =0;
            _loc_1 = super.getToolTipAction();
            if (!_loc_1 && !Global.isVisiting() && !Global.world.isEditMode && !(Global.world.getTopGameMode() instanceof GMRemodel))
            {
                switch(m_state)
                {
                    case STATE_FALLOW:
                    {
                        _loc_1 = ZLoc.t("Main", "FallowPlotAction");
                        break;
                    }
                    case STATE_PLOWED:
                    {
                        _loc_1 = ZLoc.t("Main", "PlowedPlotAction");
                        break;
                    }
                    case STATE_GROWN:
                    {
                        this.getGrownToolTipAction();
                        break;
                    }
                    case STATE_WITHERED:
                    {
                        _loc_2 = Global.gameSettings().getInt("plowCost");
                        if (Global.player.canBuy(_loc_2, false))
                        {
                            _loc_1 = ZLoc.t("Main", "WitheredPlotAction");
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "WitheredPlot_NoMoney_Action");
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

        protected String  getGrownToolTipAction ()
        {
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                return null;
            }
            return ZLoc.t("Main", "GrownAction");
        }//end

        protected String  getContractFriendlyName ()
        {
            if (!this.m_contract)
            {
                return null;
            }
            return ZLoc.t("Items", this.m_contract.name + "_friendlyName");
        }//end

        public Item  getHarvestingDefinition ()
        {
            if (!this.m_contract)
            {
                return m_item;
            }
            return this.m_contract;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            switch(m_state)
            {
                case STATE_PLANTED:
                {
                }
                case STATE_WITHERED:
                {
                }
                default:
                {
                    break;
                }
            }
            switch(m_state)
            {
                case STATE_PLANTED:
                {
                    if (m_actionMode == VISIT_PLAY_ACTION || m_actionMode == VISIT_REPLAY_ACTION)
                    {
                    }
                }
                case STATE_WITHERED:
                {
                    if (m_actionMode == VISIT_PLAY_ACTION || m_actionMode == VISIT_REPLAY_ACTION)
                    {
                    }
                }
                default:
                {
                    break;
                }
            }
            return Curry .curry (void  (HarvestableResource param1 )
            {
                if (param1.harvest())
                {
                    param1.doHarvestDropOff(!m_isBeingAutoHarvested);
                }
                return;
            }//end
            , this);
        }//end

         public String  getActionTargetName ()
        {
            String _loc_1 ="";
            if (harvestingDefinition)
            {
                _loc_1 = harvestingDefinition.name;
            }
            return _loc_1;
        }//end

         public boolean  addSupply (String param1 ,int param2 )
        {
            String _loc_5 =null ;
            boolean _loc_6 =false ;
            boolean _loc_7 =false ;
            boolean _loc_3 =false ;
            if (!this.acceptsAsSupplies(param1))
            {
                param1 = this.getFallbackCommodity(param1, harvestingDefinition.commodityReq);
            }
            if (!param1)
            {
                return false;
            }
            _loc_4 = harvestingDefinition.commodityReq-Global.player.commodities.getCount(param1);
            if (harvestingDefinition.commodityReq - Global.player.commodities.getCount(param1) > 0)
            {
                _loc_5 = this.getFallbackCommodity(param1, _loc_4);
                if (_loc_5)
                {
                    _loc_6 = true;
                    _loc_7 = true;
                    if (Global.player.commodities.getCount(param1) > 0)
                    {
                        _loc_6 = this.addSupplyHelper(param1, Global.player.commodities.getCount(param1));
                    }
                    if (_loc_4 > 0)
                    {
                        _loc_7 = this.addSupplyHelper(_loc_5, _loc_4);
                    }
                    _loc_3 = _loc_6 && _loc_7;
                    if (!_loc_3)
                    {
                        clearSupply();
                    }
                }
            }
            else
            {
                _loc_3 = this.addSupplyHelper(param1, param2);
            }
            return _loc_3;
        }//end

        protected boolean  addSupplyHelper (String param1 ,int param2 )
        {
            doResourceChanges(0, 0, 0, -param2, param1, true);
            _loc_3 = Global.player.commodities.remove(param1,param2);
            doSupplyDoobers(param1, param2);
            if (_loc_3)
            {
                _loc_3 = super.addSupply(param1, param2);
            }
            return _loc_3;
        }//end

        public String  getWorkerBucket ()
        {
            return WORKER_BUCKET_PREFIX + this.getId();
        }//end

        public Array  commodities ()
        {
            Object _loc_1 =null ;
            if (!this.m_commodities)
            {
                this.m_commodities = new Array();
                for(int i0 = 0; i0 < m_item.commodities.size(); i0++)
                {
                		_loc_1 = m_item.commodities.get(i0);

                    this.m_commodities.push(_loc_1.name);
                }
            }
            return this.m_commodities;
        }//end

         protected Object  getSupplyCapacities ()
        {
            String _loc_2 =null ;
            Object _loc_1 =new Object ();
            for(int i0 = 0; i0 < this.commodities.size(); i0++)
            {
            	_loc_2 = this.commodities.get(i0);

                _loc_1.put(_loc_2,  harvestingDefinition.commodityReq);
            }
            return _loc_1;
        }//end

        public boolean  acceptsAsSupplies (String param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.commodities.size(); i0++)
            {
            	_loc_2 = this.commodities.get(i0);

                if (param1 == _loc_2)
                {
                    return true;
                }
            }
            return false;
        }//end

        protected String  getFallbackCommodity (String param1 ,int param2 )
        {
            String _loc_4 =null ;
            _loc_3 = Global.gameSettings().getCommodityXMLDefault().@name;
            if (this.acceptsAsSupplies(_loc_3) && Global.player.commodities.getCount(_loc_3) >= param2)
            {
                return _loc_3;
            }
            for(int i0 = 0; i0 < this.commodities.size(); i0++)
            {
            	_loc_4 = this.commodities.get(i0);

                if (_loc_4 != _loc_3 && _loc_4 != param1 && Global.player.commodities.getCount(_loc_4) >= param2)
                {
                    return _loc_4;
                }
            }
            return null;
        }//end

        public double  getSupplyBonus ()
        {
            String _loc_3 =null ;
            double _loc_4 =0;
            int _loc_1 =0;
            double _loc_2 =0;
            for(int i0 = 0; i0 < this.commodities.size(); i0++)
            {
            	_loc_3 = this.commodities.get(i0);

                _loc_4 = 1 + Global.gameSettings().getCommodityBonus(_loc_3) / 100;
                _loc_1 = _loc_1 + getSupply(_loc_3);
                _loc_2 = _loc_2 + getSupply(_loc_3) * _loc_4;
            }
            if (_loc_1 > 0)
            {
                _loc_2 = _loc_2 / _loc_1;
            }
            else
            {
                _loc_2 = 1;
            }
            return _loc_2;
        }//end

        public boolean  isRouteable ()
        {
            boolean _loc_1 =false ;
            if (getPopularity() > 0)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public MapResource  getMapResource ()
        {
            return (MapResource)this;
        }//end

        public boolean  hasHotspot ()
        {
            boolean _loc_1 =false ;
            if (getHotspot() != null)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public void  makePeepEnterTarget (Peep param1 )
        {
            return;
        }//end

    }



