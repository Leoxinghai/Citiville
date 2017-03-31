package Modules.ships.cruise;

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
import Classes.LogicComponents.*;
import Classes.doobers.*;
import Classes.effects.*;
import Classes.inventory.*;
import Classes.util.*;
import Display.PopulationUI.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;

    public class CruiseShipLogicComponent extends HarvestableShipComponentBase
    {
        protected Array m_doobersToGiveAway =null ;
        private  String DESIRED_COMMODITY ="premium_goods";
        protected boolean m_isDelivering =false ;
        protected boolean m_isHarvesting =false ;

        public  CruiseShipLogicComponent (HarvestableShip param1 )
        {
            super(param1);
            _loc_2 =Global.gameSettings().getEffectByName("fireworks");
            _loc_3 = _loc_2.image.get(0) ;
            ItemImage _loc_4 =new ItemImage(_loc_3 );
            _loc_4.load();
            Sounds.loadSoundByName("cruise_arrive_nocamera");
            Sounds.loadSoundByName("ship_approaches");
            Sounds.loadSoundByName("cruise_fireworks");
            return;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            return boolean  ()
            {
                if (Global.isVisiting())
                {
                    return true;
                }
                switch(m_ship.getState())
                {
                    case HarvestableResource.STATE_GROWN:
                    {
                        if (m_ship.isHarvestable())
                        {
                            Sounds.playFromSet(Sounds.SET_HARVEST, m_ship);
                            spawnTourists();
                            return true;
                        }
                        return false;
                    }
                    case HarvestableResource.STATE_PLOWED:
                    {
                        if (Global.player.commodities.getAddedCount(m_ship.commodities) >= m_ship.harvestingDefinition.commodityReq)
                        {
                            return true;
                        }
                        return false;
                    }
                    default:
                    {
                        return true;
                        break;
                    }
                }
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return void  ()
            {
                _loc_1 = null;
                if (Global.isVisiting())
                {
                    return;
                }
                switch(m_ship.getState())
                {
                    case HarvestableResource.STATE_GROWN:
                    {
                        if (m_ship.harvest())
                        {
                            m_ship.doHarvestDropOff();
                        }
                        m_ship.setState(HarvestableResource.STATE_PLOWED);
                        m_ship.clearSupply();
                        m_isHarvesting = false;
                        break;
                    }
                    case HarvestableResource.STATE_PLOWED:
                    {
                        Sounds.play("ship_approaches");
                        _loc_1 = DESIRED_COMMODITY;
                        m_ship.addSupply(_loc_1, m_ship.harvestingDefinition.commodityReq);
                        GameTransactionManager.addTransaction(new TSupplyShip(m_ship, _loc_1));
                        m_ship.setState(HarvestableResource.STATE_PLANTED);
                        m_ship.plantTime = GlobalEngine.getTimer();
                        startShipLeave();
                        m_isDelivering = false;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                return;
            }//end
            ;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                m_isHarvesting = false;
                m_isDelivering = false;
                return;
            }//end
            ;
        }//end

         public boolean  harvest ()
        {
            boolean _loc_1 =false ;
            if (m_ship.isHarvestable())
            {
                if (!Global.isVisiting())
                {
                    m_ship.doDoobers();
                    if (m_ship.actionMode != m_ship.VISIT_REPLAY_ACTION)
                    {
                        GameTransactionManager.addTransaction(new THarvest(m_ship));
                        m_ship.trackAction(TrackedActionType.HARVEST);
                    }
                }
                _loc_1 = true;
            }
            _loc_2 = m_ship;
            _loc_3 = m_ship.harvestCounter+1;
            _loc_2.harvestCounter = _loc_3;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.SWEEPSTAKES_CRUISE) == 1)
            {
                StatsManager.count(StatsCounterType.SWEEPSTAKES, StatsKingdomType.CRUISESHIPS, "", "", "", "", m_ship.harvestCounter);
            }
            m_ship.updateObjectIndicator();
            return _loc_1;
        }//end

         public Array  makeDoobers (Item param1 ,Array param2 ,double param3 =1)
        {
            Array _loc_5 =null ;
            String _loc_6 =null ;
            int _loc_7 =0;
            this.m_doobersToGiveAway = super.makeDoobers(param1, param2, param3);
            _loc_4 = m_ship.getSupplyBonus();
            for(int i0 = 0; i0 < this.m_doobersToGiveAway.size(); i0++)
            {
            		_loc_5 = this.m_doobersToGiveAway.get(i0);

                _loc_6 = _loc_5.get(0);
                _loc_7 = _loc_5.get(1);
                if (_loc_6 == Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_7))
                {
                    _loc_5.put(1,  _loc_7 * _loc_4);
                }
            }
            return this.m_doobersToGiveAway;
        }//end

         public void  handlePlayAction ()
        {
            if (Global.isVisiting())
            {
                return;
            }
            switch(m_ship.getState())
            {
                case HarvestableResource.STATE_GROWN:
                {
                    if (m_ship.isHarvestable() && !Global.guide.isActive() && !this.m_isHarvesting)
                    {
                        this.m_isHarvesting = true;
                        if (this.isUxLockedByQuest())
                        {
                            Global.ui.showTickerMessage(ZLoc.t("Main", "TickerCruiseShips"));
                        }
                        Sounds.play("cruise_arrive_nocamera");
                        if (m_ship.harvestCounter == 0)
                        {
                            TimerUtil .callLater (void  ()
            {
                fireZeMissiles(null);
                return;
            }//end
            , 1000);
                        }
                        super.handlePlayAction();
                    }
                    break;
                }
                case HarvestableResource.STATE_PLOWED:
                {
                    if (!this.m_isHarvesting && Global.player.commodities.getAddedCount(m_ship.commodities) >= m_ship.harvestingDefinition.commodityReq)
                    {
                        this.m_isDelivering = true;
                        m_ship.removeStagePickEffect();
                        Global.world.citySim.pickupManager.enqueue("NPC_shipPickup", m_ship);
                    }
                    else
                    {
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS, true);
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        private void  fireZeMissiles (Event event =null )
        {
            e = event;
            Sounds.play("cruise_fireworks");
            MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 700);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1200);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(m_ship, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1300);
            return;
        }//end

        protected void  spawnTourists ()
        {
            _loc_1 = int(m_ship.harvestingDefinition.xml.cruiseTourists);
            Global.world.citySim.cruiseShipManager.releasePeeps(_loc_1, m_ship);
            Global.world.citySim.cruiseShipManager.removeCheeringScene();
            return;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = super.getToolTipAction();
            if (Global.isVisiting())
            {
                return _loc_1;
            }
            switch(m_ship.getState())
            {
                case HarvestableResource.STATE_GROWN:
                {
                    if (this.m_isHarvesting)
                    {
                        break;
                    }
                    if (!Global.player.checkEnergy(-m_ship.harvestingDefinition.harvestEnergyCost, false))
                    {
                        _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:m_ship.harvestingDefinition.harvestEnergyCost});
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Main", "LoadedAction");
                    }
                    break;
                }
                case HarvestableResource.STATE_PLOWED:
                {
                    if (this.m_isDelivering)
                    {
                        break;
                    }
                    if (Global.player.commodities.getAddedCount(m_ship.commodities) >= m_ship.harvestingDefinition.commodityReq)
                    {
                        _loc_1 = ZLoc.t("Main", "ClickToSupply");
                    }
                    else
                    {
                        _loc_1 = Commodities.getNoCommoditiesTooltip();
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_1 ="";
            if (Global.isVisiting())
            {
                return _loc_1;
            }
            switch(m_ship.getState())
            {
                case HarvestableResource.STATE_PLANTED:
                {
                    _loc_2 = GameUtil.formatMinutesSeconds(m_ship.getGrowTimeLeft());
                    _loc_1 = ZLoc.t("Main", "AwayCruise", {time:_loc_2});
                    break;
                }
                case HarvestableResource.STATE_PLOWED:
                {
                    if (this.m_isDelivering)
                    {
                        break;
                    }
                    _loc_1 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:m_ship.harvestingDefinition.commodityReq});
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

         public void  onStateChanged (String param1 ,String param2 )
        {
            super.onStateChanged(param1, param2);
            this.checkCheeringScene();
            if (param1 != param2 && param2 == HarvestableResource.STATE_GROWN)
            {
                Sounds.play("ship_approaches");
            }
            return;
        }//end

        public void  checkCheeringScene ()
        {
            if (m_ship.harvestCounter == 0 && m_ship.getState() == HarvestableResource.STATE_GROWN && !Global.world.citySim.cruiseShipManager.isCheeringSceneActive())
            {
                Global.world.citySim.cruiseShipManager.createCheeringScene();
            }
            return;
        }//end

        protected boolean  isUxLockedByQuest ()
        {
            return !Global.questManager.isFlagReached(GameQuestManager.QUEST_UX_CRUISESHIP);
        }//end

         public double  getHarvestTime ()
        {
            switch(m_ship.getState())
            {
                case HarvestableResource.STATE_GROWN:
                {
                    return Global.gameSettings().getNumber("actionBarCruiseUnload");
                }
                case HarvestableResource.STATE_PLOWED:
                {
                }
                default:
                {
                    return Global.gameSettings().getNumber("actionBarBizOpen");
                    break;
                }
            }
        }//end

         public String  getActionText ()
        {
            switch(m_ship.getState())
            {
                case HarvestableResource.STATE_GROWN:
                {
                    return ZLoc.t("Main", "Unloading");
                }
                case HarvestableResource.STATE_PLOWED:
                {
                }
                default:
                {
                    return ZLoc.t("Main", "BusinessOpening");
                    break;
                }
            }
        }//end

         public boolean  enableUpdateArrow ()
        {
            return !Global.isVisiting() && (m_ship.getState() == HarvestableResource.STATE_GROWN || m_ship.getState() == HarvestableResource.STATE_PLOWED);
        }//end

         public void  createStagePickEffect ()
        {
            if (m_ship.getState() == HarvestableResource.STATE_GROWN)
            {
                createStagePickEffectHelper(StagePickEffect.PICK_1);
            }
            else if (m_ship.getState() == HarvestableResource.STATE_PLOWED)
            {
                createStagePickEffectHelper(StagePickEffect.PICK_GOODS);
            }
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

    }



