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
import Classes.effects.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.Toaster.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import GameMode.*;
import Mechanics.*;
import Modules.quest.Helpers.*;
import Modules.quest.Managers.*;
import Modules.remodel.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.geom.*;
import org.aswing.*;

import com.xinghai.Debug;

    public class Residence extends SimpleHarvestableResource implements IPeepHome
    {
        private  String RESIDENCE ="residence";
        protected int m_numResidents ;
        protected int m_numOccupants ;
        private Vehicle m_truck ;
        protected boolean m_movingIn =false ;

        public  Residence (String param1)
        {
            super(param1);
            m_objectType = WorldObjectTypes.RESIDENCE;
            m_typeName = this.RESIDENCE;
            this.m_numResidents = m_item.populationBase;
            this.m_numOccupants = this.m_numResidents;
            m_isRoadVerifiable = true;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_numResidents = this.getPopulationYield();
            this.m_numOccupants = this.m_numResidents;
            this.updateNpcNames();
            return;
        }//end

         public void  addPopulation (int param1 )
        {
            super.addPopulation(param1);
            this.m_numResidents = this.m_numResidents + param1;
            this.m_numOccupants = this.m_numOccupants + param1;
            return;
        }//end

        public Population  getPopulation ()
        {
            _loc_1 = this.getPopulationYield();
            _loc_2 = m_item.populationType;
            Population _loc_3 =new Population(_loc_1 ,_loc_2 );
            return _loc_3;
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            registerStatsActionName(TrackedActionType.HARVEST, "collect");
            return;
        }//end

         public Array  npcNames ()
        {
            _loc_1 = this.m_item.getRandomSpawnNpc();
            return _loc_1 != null ? (.get(_loc_1)) : (super.npcNames);
        }//end

        protected void  updateNpcNames ()
        {
            int _loc_4 =0;
            _loc_1 = this.m_numResidents*2;
            if (_loc_1 < 1)
            {
                _loc_1 = Global.gameSettings().getInt("npcTypesPerBuilding", 1);
            }
            Array _loc_2 =new Array();
            int _loc_3 =0;
            while (_loc_3 < _loc_1)
            {

                _loc_4 = MathUtil.random(m_npcNames.length, 0);
                _loc_2.push(m_npcNames.get(_loc_4));
                _loc_3++;
            }
            m_npcNames = _loc_2;
            return;
        }//end

        public boolean  takeResident ()
        {
            this.m_numOccupants--;
            if (this.m_numOccupants < 1)
            {
                this.m_numOccupants = 0;
                return true;
            }
            return false;
        }//end

        public void  returnResident ()
        {
            this.m_numOccupants++;
            if (this.m_numOccupants > this.m_numResidents)
            {
                this.m_numOccupants = this.m_numResidents;
            }
            return;
        }//end

        public boolean  hasOccupants ()
        {
            return this.m_numOccupants > 0;
        }//end

         public String  getToolTipStatus ()
        {
            double _loc_2 =0;
            Object _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            _loc_1 = getMechToolTipStatus();
            if (!_loc_1)
            {
                if (isNeedingRoad)
                {
                    _loc_1 = ZLoc.t("Dialogs", "NotConnectedToRoad");
                }
                else if (!isLocked() && !Global.isVisiting() && !Global.world.isEditMode && !this.m_movingIn)
                {
                    if (m_state == STATE_PLANTED)
                    {
                        _loc_1 = ZLoc.t("Main", "BareResidence", {time:GameUtil.formatMinutesSeconds(getGrowTimeLeft())});
                        _loc_2 = Global.gameSettings().getNumber("populationScale", 1);
                        _loc_3 = {curPop:getPopulationYield() * _loc_2, maxPop:getItem().populationMax * _loc_2};
                        _loc_4 = "TT_PopulationCurOfMax";
                        if (Global.gameSettings().hasMultiplePopulations())
                        {
                            _loc_5 = this.getPopulationType();
                            _loc_6 = PopulationHelper.getPopulationZlocType(_loc_5, true);
                            _loc_3.popType = _loc_6;
                            _loc_4 = "TT_PopulationTypeCurOfMax";
                        }
                        _loc_1 = _loc_1 + ("\n" + ZLoc.t("Dialogs", _loc_4, _loc_3));
                    }
                }
                else if (!isLocked() && !Global.isVisiting() && !Global.world.isEditMode && this.m_movingIn)
                {
                    _loc_1 = ZLoc.t("Main", "MovingIn");
                }
            }
            return _loc_1;
        }//end

         public Component  getCustomToolTipStatus ()
        {
            double _loc_3 =0;
            _loc_1 = super.getCustomToolTipStatus();
            boolean _loc_2 =false ;
            if (_loc_1)
            {
                if (!isLocked() && !Global.isVisiting() && !Global.world.isEditMode && !this.m_movingIn && m_state == STATE_PLANTED)
                {
                    _loc_2 = true;
                }
                if (_loc_2)
                {
                    _loc_3 = getToolTipTextWidth(ZLoc.t("Main", "BareResidence", {time:"00:00:00"})) + 5;
                    if (_loc_3 > _loc_1.getPreferredWidth())
                    {
                        _loc_1.setPreferredWidth(_loc_3);
                    }
                }
                else
                {
                    _loc_1.setPreferredSize(null);
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            int _loc_2 =0;
            String _loc_1 =null ;
            _loc_1 = super.getToolTipAction();
            if (!_loc_1)
            {
                _loc_1 = getGameModeToolTipAction();
            }
            if (!_loc_1)
            {
                if (isNeedingRoad)
                {
                    return null;
                }
                _loc_2 = m_item.npcsPerHarvest;
                if (Global.isVisiting())
                {
                    if (!areVisitorInteractionsExhausted && m_state == STATE_GROWN && this.isVisitorInteractable())
                    {
                        _loc_1 = ZLoc.t("Main", "RipeResidence", {people:_loc_2});
                    }
                }
                else if (!isLocked() && !Global.world.isEditMode && !m_isHarvesting && !this.m_movingIn)
                {
                    if (m_state == STATE_GROWN)
                    {
                        if (Global.player.checkEnergy(-m_item.harvestEnergyCost, false))
                        {
                            _loc_1 = ZLoc.t("Main", "RipeResidence", {people:_loc_2});
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:m_item.harvestEnergyCost});
                        }
                    }
                    else
                    {
                        _loc_1 = null;
                    }
                }
            }
            return _loc_1;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PreServerUpdate();
            _loc_1 = getGrowTimeDelta();
            this.m_plantTime = GlobalEngine.getTimer() - _loc_1;
            this.setState(STATE_PLANTED);
            plantTime = GlobalEngine.getTimer();
            Global.world.citySim.recomputePopulation(Global.world, false);
            this.doMoveIn();
            return;
        }//end

        private void  endPlayMoveIn ()
        {
            this.m_movingIn = false;
            this.updateArrow();
            reloadImage();
            return;
        }//end

        public void  playMoveIn ()
        {
            SoundObject loopsound ;
            this.m_movingIn = true;
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(getPosition());
            if (!nearRoad)
            {
                this.endPlayMoveIn();
                return;
            }
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            movingTruckName = this.getMovingInTruckName();
            this.m_truck = new Vehicle(movingTruckName, false);
            this.m_truck.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
            Global.world.citySim.npcManager.addVehicle(this.m_truck);
            moveInTime = Global.gameSettings().getNumber("actionBarResMoveIn");
            actions = this.m_truck.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (this .m_truck ,EffectType .VEHICLE_POOF ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.play("tourbus_honk");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (this .m_truck ,this ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (this .m_truck ,Curry .curry (void  (Residence param1 )
            {
                param1.unloadResidents();
                return;
            }//end
            ,this )),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                Sounds.play("peopleMoveIn");
                return;
            }//end
            ),new ActionProgressBar (this .m_truck ,this ,ZLoc .t ("Main","MovingIn"),moveInTime ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.play("tourbus_start");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                endPlayMoveIn();
                return;
            }//end
            ),new ActionNavigate (this .m_truck ,startRoad ,this ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(this.m_truck));
            displayStatus(ZLoc.t("Main", "MovingIn"));
            return;
        }//end

        private void  doMoveIn ()
        {
            SoundObject loopsound ;
            this.m_movingIn = true;
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(getPosition());
            if (!nearRoad)
            {
                this.finishMoveIn();
                return;
            }
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            movingTruckName = this.getMovingInTruckName();
            this.m_truck = new Vehicle(movingTruckName, false);
            if (Global.guide.isActive())
            {
                this.m_truck.setPosition(-6.5, 34.5);
            }
            else
            {
                this.m_truck.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
            }
            Global.world.citySim.npcManager.addVehicle(this.m_truck);
            moveInTime = Global.gameSettings().getNumber("actionBarResMoveIn");
            actions = this.m_truck.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (this .m_truck ,EffectType .VEHICLE_POOF ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.play("tourbus_honk");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (this .m_truck ,this ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (this .m_truck ,Curry .curry (void  (Residence param1 )
            {
                param1.unloadResidents();
                return;
            }//end
            ,this )),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                Sounds.play("peopleMoveIn");
                return;
            }//end
            ),new ActionProgressBar (this .m_truck ,this ,ZLoc .t ("Main","MovingIn"),moveInTime ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.play("tourbus_start");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                finishMoveIn();
                return;
            }//end
            ),new ActionNavigate (this .m_truck ,startRoad ,this ),new ActionFn (this .m_truck ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(this.m_truck));
            displayStatus(ZLoc.t("Main", "MovingIn"));
            return;
        }//end

        protected void  unloadResidents ()
        {
            if (Global.guide.isActive())
            {
                Global.world.citySim.npcManager.createMoverPeeps(this, 3);
            }
            else
            {
                Global.world.citySim.npcManager.createMoverPeeps(this, this.m_numResidents);
            }
            return;
        }//end

        protected void  finishMoveIn ()
        {
            this.m_movingIn = false;
            Global.world.citySim.recomputePopulation(Global.world);
            doResourceChanges(0, 0, 0, 0, "", true, false, true, 0, Global.world.citySim.applyPopulationScale(m_item.populationBase));
            Global.world.citySim.npcManager.addNewResidence(this);
            this.updateArrow();
            reloadImage();
            spawnDoobers();
            return;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            return Global.player.processRandomModifiers(harvestingDefinition, this, true, m_secureRands);
        }//end

         public void  onPlayAction ()
        {
            Array _loc_2 =null ;
            Item _loc_3 =null ;
            ReminderToaster _loc_4 =null ;
            return;

            if (this.isUxLockedByQuests)
            {
                displayStatus(ZLoc.t("Main", "QuestUXLocked"));
                return;
            }
            if (m_visitReplayLock > 0 || this.m_movingIn || Global.isVisiting())
            {
                return;
            }
            if (isNeedingRoad)
            {
                super.enterMoveMode();
                return;
            }
            if (m_state == STATE_PLANTED)
            {
                this.processPlantedState();
                return;
            }
            if (m_state != STATE_GROWN)
            {
                return;
            }
            m_actionMode = PLAY_ACTION;
            _loc_1 = m_item.harvestEnergyCost;
            if (!Global.player.checkEnergy(-_loc_1))
            {
                displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
            }
            else if (!m_isHarvesting)
            {
                if (RemodelManager.isRemodelPossible(this))
                {
                    _loc_2 = RemodelManager.getConstructionCompanyObjects();
                    _loc_3 = _loc_2.length > 0 ? (_loc_2.get(0)) : (null);
                    _loc_4 = new ReminderToaster(ZLoc.t("Dialogs", "remodelReminder_title"), ZLoc.t("Dialogs", "remodelReminder_body"), this, Global.getAssetURL(RemodelManager.TOASTER_ICON), "remodel_reminder", ZLoc.t("Main", "GetStarted"), popRemodelMarket);
                    if (_loc_4.canShow)
                    {
                        Global.ui.toaster.show(_loc_4);
                    }
                }
                this.initiateHarvestingSequence();
            }
            return;
        }//end

         public void  onVisitPlayAction ()
        {
            m_actionMode = VISIT_PLAY_ACTION;
            _loc_2 = m_numVisitorInteractions+1;
            m_numVisitorInteractions = _loc_2;
            this.initiateHarvestingSequence();
            trackVisitAction(TrackedActionType.COLLECT_RENT);
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            if (suppressVisitorActions())
            {
                return 0;
            }
            super.onVisitReplayAction(null);
            if (m_state == STATE_GROWN)
            {
                m_isHarvesting = true;
                setHighlighted(false);
                removeAnimatedEffects();
                removeStagePickEffect();
                GameTransactionManager.addTransaction(param1);
                this.harvest();
                return DEFAULT_REPLAY_TIME;
            }
            return 0;
        }//end

         public boolean  isVisitorInteractable ()
        {
            _loc_1 = suppressVisitorActions();
            return !isNeedingRoad && m_state == STATE_GROWN && !_loc_1;
        }//end

        private void  initiateHarvestingSequence ()
        {
            m_isHarvesting = true;
            setHighlighted(false);
            removeAnimatedEffects();
            removeStagePickEffect();
            Global.world.citySim.pickupManager.enqueue("NPC_mailman", this);
            return;
        }//end

         protected boolean  isUxLockedByQuests ()
        {
            return Global.questManager != null && !Global.questManager.isUXUnlocked(GameQuestManager.QUEST_UX_RENT_UNLOCKED);
        }//end

         public void  setState (String param1 )
        {
            removeAnimatedEffects();
            super.setState(param1);
            this.updateArrow();
            return;
        }//end

         public void  updateRoadState ()
        {
            super.updateRoadState();
            this.updateEffects();
            return;
        }//end

        private void  updateEffects ()
        {
            if (!isNeedingRoad && !this.isUxLockedByQuests)
            {
                this.createStagePickEffect();
            }
            else
            {
                removeAnimatedEffects();
                if (m_arrow)
                {
                    m_arrow = null;
                }
                removeStagePickEffect();
            }
            return;
        }//end

         public void  cleanUp ()
        {
            removeStagePickEffect();
            super.cleanUp();
            return;
        }//end

         protected void  updateArrow ()
        {
            removeAnimatedEffectByClass(StagePickEffect);
            if (isNeedingRoad || Global.world.isEditMode)
            {
                m_arrow = null;
                return;
            }
            switch(m_state)
            {
                case STATE_GROWN:
                {
                    m_isHarvesting = false;
                    m_isHighlightable = true;
                    m_isShowStateTransition = true;
                    this.createStagePickEffect();
                    break;
                }
                case STATE_PLANTED:
                {
                    m_isHarvesting = false;
                    m_isHighlightable = false;
                    if (m_isShowStateTransition)
                    {
                        m_isShowStateTransition = false;
                    }
                    removeStagePickEffect();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            return;

            _loc_1 =             !Global.isVisiting() || this.isVisitorInteractable();
            if (m_state == STATE_GROWN && !isNeedingRoad && !this.m_movingIn && !this.m_clearLock && !this.isUxLockedByQuests && _loc_1)
            {
                if (!m_stagePickEffect)
                {
                    m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.STAGE_PICK);
                    m_stagePickEffect.setPickType(StagePickEffect.PICK_1);
                    m_stagePickEffect.queuedFloat();
                }
                else
                {
                    m_stagePickEffect.setPickType(StagePickEffect.PICK_1);
                    m_stagePickEffect.reattach();
                    m_stagePickEffect.queuedFloat();
                }
            }
            return;
        }//end

         protected void  updateDisabled ()
        {
            return;
        }//end

         protected void  setArrowPosition ()
        {
            return;
        }//end

         public void  onEditModeSwitch ()
        {
            this.updateArrow();
            reloadImage();
            return;
        }//end

         public String  getActionText ()
        {
            return ZLoc.t("Main", "Collecting");
        }//end

         public boolean  harvest ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            Debug.debug6("Residence.harvest");
            _loc_1 = super.harvest();
            if (_loc_1)
            {
                if (Global.isVisiting())
                {
                    _loc_2 = Global.gameSettings().getInt("friendVisitResidenceRepGain", 1);
                    _loc_3 = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                    finalizeAndAwardVisitorHelp(VisitorHelpType.RESIDENCE_COLLECT_RENT, _loc_2, _loc_3);
                }
                else
                {
                    MechanicManager.getInstance().handleAction(this, "harvest", null);
                }
            }
            m_actionMode = NO_ACTION;
            return _loc_1;
        }//end

         protected Class  getGrownCursor ()
        {
            if (m_isHarvesting || areVisitorInteractionsExhausted)
            {
                return null;
            }
            return null;
        }//end

         public boolean  isHighlightable ()
        {
            boolean _loc_1 =false ;
            _loc_2 = Global.world.getTopGameMode();
            if (_loc_2 instanceof GMPlay)
            {
                if (isNeedingRoad)
                {
                    _loc_1 = false;
                }
                else
                {
                    _loc_1 = m_isHighlightable;
                }
            }
            else
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

         public double  getHarvestTime ()
        {
            return Global.gameSettings().getNumber("actionBarResHarvest");
        }//end

         public void  onSell (GameObject param1)
        {
            if (!(param1 instanceof IPeepHome))
            {
                Global.world.citySim.recomputePopulation(Global.world);
                Global.world.citySim.npcManager.removeResidence(this);
            }
            return;
        }//end

        public boolean  isMovingIn ()
        {
            return this.m_movingIn;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            Global.world.citySim.roadManager.updateResource(this);
            if (!isNeedingRoad)
            {
                this.displayStatus(ZLoc.t("Main", "ConnectedToRoad"), "", 43520);
            }
            return;
        }//end

         public Class  getCursor ()
        {
            _loc_1 = super.getCursor();
            if (isNeedingRoad)
            {
                _loc_1 = EmbeddedArt.hud_act_move;
            }
            return _loc_1;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            HarvestableResource harvestResource ;
            harvestResource;
            return boolean  ()
            {
                _loc_1 = undefined;
                if (m_movingIn)
                {
                    return true;
                }
                switch(m_state)
                {
                    case STATE_GROWN:
                    {
                        if (m_actionMode == VISIT_REPLAY_ACTION)
                        {
                        }
                        if (m_actionMode == PLAY_ACTION && !Global.player.checkEnergy(-_loc_1))
                        {
                        }
                        if (!m_isBeingAutoHarvested)
                        {
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                switch(m_state)
                {
                    case STATE_GROWN:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            Function superEndFunction ;
            superEndFunction = this.m_movingIn ? (null) : (super.getProgressBarEndFunction());
            return void  ()
            {
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                m_heldEnergy = 0;
                if (superEndFunction != null)
                {
                    superEndFunction();
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
                updateArrow();
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                m_heldEnergy = 0;
                return;
            }//end
            ;
        }//end

         public boolean  deferProgressBarToNPC ()
        {
            return false;
        }//end

         public Point  getProgressBarOffset ()
        {
            if (this.content)
            {
                return new Point(0, this.content.height >> 1);
            }
            return new Point(0, 0);
        }//end

         public void  prepareForStorage (MapResource param1)
        {
            super.prepareForStorage(param1);
            return;
        }//end

         public void  restoreFromStorage (MapResource param1)
        {
            _loc_2 = this.plantTime;
            _loc_3 = this.state;
            super.restoreFromStorage(param1);
            if (!(param1 instanceof IPeepHome))
            {
                Global.world.citySim.recomputePopulation(Global.world);
                Global.world.citySim.npcManager.addNewResidence(this);
            }
            if (param1 instanceof Neighborhood)
            {
                this.plantTime = _loc_2;
                this.m_state = _loc_3;
            }
            return;
        }//end

        protected void  processPlantedState ()
        {
            DisplayObject _loc_3 =null ;
            _loc_1 = Global.player.hasSeenInstantPopup_residence;
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_RESIDENCES);
            if (_loc_1 == false && !Global.isVisiting() && Global.player.level >= 6 && _loc_2 > 1)
            {
                _loc_3 = new InstantReadyDialog(this);
                UI.displayPopup(_loc_3, true, "instantReadyCrops", false);
            }
            return;
        }//end

        protected String  getMovingInTruckName ()
        {
            String _loc_1 ="yehaul";
            _loc_2 = getItem().moveInVehicle;
            if (_loc_2 != null)
            {
                _loc_1 = _loc_2;
            }
            return _loc_1;
        }//end

    }




