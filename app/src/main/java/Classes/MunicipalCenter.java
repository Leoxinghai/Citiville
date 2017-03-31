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
import Classes.bonus.*;
import Classes.effects.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Engine.Helpers.*;
import Engine.Transactions.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

import com.greensock.*;
//import flash.events.*;
//import flash.utils.*;

    public class MunicipalCenter extends MechanicMapResource implements ISlottedContainer, IPopulationStateObserver, IPeepCapacity, ITimedHarvestable
    {
        protected Helicopter m_helicopterA =null ;
        protected Helicopter m_helicopterB =null ;
        protected Array m_peepList ;
        protected double m_buildTime ;
        protected double m_celebrationTime ;
        protected double m_celebrationProgress ;
        protected boolean m_celebrationRunning ;

        public  MunicipalCenter (String param1 )
        {
            super(param1);
            m_isRoadVerifiable = false;
            m_statePhase = "upgrade";
            this.m_peepList = new Array();
            this.m_buildTime = m_item.xml.buildTime;
            this.m_celebrationTime = 10000 - Wonder.POPUP_DELAY;
            this.m_celebrationProgress = 0;
            this.m_celebrationRunning = false;
            m_upgradeEffectsFinish = this.onUpgradeEffectsComplete;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            ItemInstance _loc_4 =null ;
            super.loadObject(param1);
            mechanicData.put("slots",  new Array());
            if (param1.mechanicData.slots && param1.mechanicData.slots != NOT_INITIALIZED_MECHANIC)
            {
                while (_loc_2 < param1.mechanicData.slots.length())
                {

                    _loc_3 = param1.mechanicData.slots.get(_loc_2) ? (param1.mechanicData.slots.get(_loc_2)) : (null);
                    if (_loc_3)
                    {
                        _loc_4 =(ItemInstance) Global.world.loadObjectInstance(_loc_3);
                        _loc_4.loadObject(_loc_3);
                        ((Array)mechanicData.get("slots")).push(_loc_4);
                    }
                    _loc_2++;
                }
            }
            if (param1.mechanicData.harvestState)
            {
                mechanicData.put("harvestState",  param1.mechanicData.harvestState);
            }
            this.refreshImagePhase();
            return;
        }//end

        public void  refreshImagePhase ()
        {
            _loc_1 = this.getDataForMechanic("mechanicPackName");
            if (_loc_1 == "DownTownCenter_neighborhoodMechanics")
            {
                m_statePhase = "neighborhood";
            }
            else if (_loc_1 == "DownTownCenter_wonderMechanics")
            {
                m_statePhase = "wonder";
            }
            else
            {
                m_statePhase = "upgrade";
            }
            if (m_imageClass != null)
            {
                m_imageClass = getCurrentImage();
            }
            return;
        }//end

        public void  harvestLoop (Object param1 )
        {
            double _loc_2 =0;
            if (param1 !=null)
            {
                _loc_2 = param1.get("startTS");
                if (mechanicData.get("harvestState") == null)
                {
                    mechanicData.put("harvestState",  new Object());
                }
                mechanicData.get("harvestState").put("openTS",  _loc_2);
            }
            else
            {
                mechanicData.put("harvestState",  null);
            }
            return;
        }//end

        public Object  harvestLoop ()
        {
            Object _loc_1 ={};
            _loc_1.startTS = this.openTS;
            return _loc_1;
        }//end

        public Object harvestState ()
        {
            if (!mechanicData.hasOwnProperty("harvestState") || mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            return mechanicData.get("harvestState");
        }//end

        public void  harvestState (Object param1)
        {
            if (!mechanicData.hasOwnProperty("harvestState") || mechanicData.get("harvestState") == null)
            {
                mechanicData.put("harvestState",  new Object());
            }
            mechanicData.put("harvestState",  param1);
            return;
        }//end

        public Object resources ()
        {
            if (!this.harvestState.hasOwnProperty("resources"))
            {
                this.harvestState.put("resources",  new Array());
            }
            return this.harvestState.get("resources");
        }//end

        public void  resources (Object param1)
        {
            if (!this.harvestState.hasOwnProperty("resources"))
            {
                this.harvestState.put("resources",  new Array());
            }
            this.harvestState.put("resources",  param1);
            return;
        }//end

        public  endTS ()*
        {
            if (!this.harvestState.hasOwnProperty("endTS"))
            {
                harvestState.put("endTS", -1);
            }
            return this.harvestState.get("endTS");
        }//end

        public void  endTS (Object param1)
        {
            harvestState.put("endTS", param1);
            return;
        }//end

        public double  openTimeLeft ()
        {
            if (this.openTS < 0 || this.closeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.closeTS;
            _loc_2 = GlobalEngine.getTimer()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

         public boolean  isOpen ()
        {
            return this.openTS > 0 && this.openTimeLeft > 0;
        }//end

        public double  openTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("harvestState") && mechanicData.get("harvestState") != null && mechanicData.get("harvestState").hasOwnProperty("openTS"))
            {
                return mechanicData.get("harvestState").get("openTS");
            }
            return -1;
        }//end

        public double  closeTS ()
        {
            if (this.openTS < 0)
            {
                return -1;
            }
            _loc_1 = getItem().harvestLoopConfig;
            _loc_2 = double(_loc_1.get("timerDuration"));
            return this.openTS + _loc_2;
        }//end

        public MechanicMapResource  mechanicMapResource ()
        {
            return this;
        }//end

        public Array  slots ()
        {
            _loc_1 = getMechanicData();
            if (_loc_1 == null || _loc_1.get("slots") == null)
            {
                _loc_1.put("slots",  new Array());
            }
            return _loc_1.get("slots");
        }//end

        public void  slots (Array param1 )
        {
            _loc_2 = getMechanicData();
            _loc_2.put("slots",  param1);
            return;
        }//end

        public boolean  hasMysteryItemInInventory (int param1 )
        {
            return false;
        }//end

        public String  getMysteryItemName (int param1 )
        {
            return null;
        }//end

        public int  getInitialHarvestBonus (int param1 )
        {
            HarvestBonus _loc_7 =null ;
            _loc_2 = Item.findUpgradeRoot(getItem());
            _loc_3 = UpgradeDefinition.getFullUpgradeChain(_loc_2);
            if (_loc_3 == null || param1 >= _loc_3.length())
            {
                return 0;
            }
            _loc_4 = _loc_3.get(param1);
            _loc_5 = _loc_3.get(param1).harvestBonuses ;
            int _loc_6 =0;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_7 = _loc_5.get(i0);

                _loc_6 = _loc_6 + _loc_7.initialPercentModifier;
            }
            return _loc_6;
        }//end

        public void  onStoreSlotObject (MapResource param1 )
        {
            param1.pathProvider = this;
            return;
        }//end

        public void  onRemoveSlotObject (MapResource param1 )
        {
            param1.pathProvider = null;
            return;
        }//end

        public boolean  isHarvestable ()
        {
            return this.timeLeft <= 0;
        }//end

        public double  timeLeft ()
        {
            _loc_1 = GlobalEngine.getTimer()/1000;
            _loc_2 = this.endTS-_loc_1;
            return _loc_2 >= 0 ? (_loc_2) : (0);
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
            if (this.slots == null || this.slots.length <= 0)
            {
                return super.getToolTipStatus();
            }
            if (!this.isHarvestable() && !Global.isVisiting())
            {
                _loc_2 = ZLoc.t("Main", "BareResidence", {time:GameUtil.formatMinutesSeconds(this.timeLeft)});
                _loc_1 = _loc_2;
                return _loc_1;
            }
            return super.getToolTipStatus();
        }//end

         public double  getRadius (ItemBonus param1 )
        {
            if (this.isOpen())
            {
                return param1.radiusMax;
            }
            return param1.radiusMin;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            Global.world.citySim.recomputePopulationCap(Global.world);
            super.onBuildingConstructionCompleted_PreServerUpdate();
            MechanicManager.getInstance().handleAction((IMechanicUser)this, MechanicManager.ON_ADD_TO_WORLD);
            MechanicManager.getInstance().handleAction(this, MechanicManager.PLAY);
            return;
        }//end

         public void  sell ()
        {
            if (!this.slots || this.slots.length <= 0)
            {
                super.sell();
            }
            else
            {
                UI.displayMessage(ZLoc.t("Main", "CannotSellStorageMunicipalCenter", {item:getItemFriendlyName()}));
            }
            return;
        }//end

         public void  doUpgradeAnimations (Function param1 ,boolean param2 =true )
        {
            UI.popupLock();
            if (m_item.isAtMaxUpgradeLevel())
            {
                UI.popupLock();
                this.playUnveilingEffects();
                TimerUtil.callLater(this.onCelebrationEnded, this.m_celebrationTime);
            }
            super.doUpgradeAnimations(param1, param2);
            return;
        }//end

        public void  onCelebrationEnded (TimerEvent event =null )
        {
            _loc_3 = m_clearLock-1;
            m_clearLock = _loc_3;
            updateAlpha();
            this.fadeUnveilingEffects();
            TimerUtil.callLater(this.cleanupUnveilingEffects, 1000);
            this.m_celebrationRunning = false;
            return;
        }//end

        public void  fadeUnveilingEffects ()
        {
            NPC _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_peepList.size(); i0++)
            {
            		_loc_1 = this.m_peepList.get(i0);

                TweenLite.to(_loc_1, 1, {alpha:0});
            }
            if (this.m_helicopterA)
            {
                TweenLite.to(this.m_helicopterA, 1, {alpha:0});
            }
            if (this.m_helicopterB)
            {
                TweenLite.to(this.m_helicopterB, 1, {alpha:0});
            }
            return;
        }//end

        public void  cleanupUnveilingEffects ()
        {
            NPC _loc_1 =null ;
            this.m_celebrationRunning = false;
            if (this.m_helicopterA)
            {
                Global.world.citySim.npcManager.removeVehicle(this.m_helicopterA);
                this.m_helicopterA = null;
            }
            if (this.m_helicopterB)
            {
                Global.world.citySim.npcManager.removeVehicle(this.m_helicopterB);
                this.m_helicopterB = null;
            }
            if (this.m_peepList)
            {
                for(int i0 = 0; i0 < this.m_peepList.size(); i0++)
                {
                		_loc_1 = this.m_peepList.get(i0);

                    Global.world.citySim.npcManager.removeWalker(_loc_1);
                }
                this.m_peepList = new Array();
            }
            UI.popupUnlock();
            MechanicManager.setForceDisplayRefresh("wonderPick", this.getId(), true);
            return;
        }//end

        public void  updateUnveilingEffects (double param1 ,double param2 )
        {
            NPC peep ;
            double peepRad ;
            double peepAngle ;
            double peepX ;
            double peepY ;
            Vector3 peepPos ;
            time = param1;
            decay = param2;
            if (!this.m_celebrationRunning || this.m_celebrationProgress > this.m_celebrationTime)
            {
                return;
            }
            double i ;
            while (i < 2)
            {

                peepRad = Math.random() * 2 + 3;
                peepAngle = Math.random() * Math.PI * 2;
                peepX = peepRad * (Math.cos(peepAngle) - Math.sin(peepAngle));
                peepY = peepRad * (Math.sin(peepAngle) + Math.cos(peepAngle));
                peepPos = new Vector3(this.getPosition().x + peepX, this.getPosition().y + peepY, 0);
                peep = Global.world.citySim.npcManager.createWalkerAtPosition(peepPos, false);
                peep.getStateMachine().addActions(new ActionPlayAnimation(peep, "cheer", 10));
                this.m_peepList.push(peep);
                i = (i + 1);
            }
            if (time < this.m_buildTime * 0.85)
            {
                MapResourceEffectFactory.createEffect(this, EffectType.FIREWORK_BALLOONS);
            }
            TimerUtil .callLater (void  ()
            {
                updateUnveilingEffects(time + decay, Math.max(300, decay * 0.9));
                return;
            }//end
            , decay);
            return;
        }//end

        public boolean  playUnveilingEffects ()
        {
            ActionCircle circleA ;
            ActionCircle circleB ;
            _loc_3 = m_clearLock+1;
            m_clearLock = _loc_3;
            rotationPos = this.getPosition();
            rotationPos.x = rotationPos.x + 4;
            rotationPos.y = rotationPos.y + 4;
            if (!this.m_helicopterA)
            {
                this.m_helicopterA = new Helicopter("policehelicopter", true);
                this.m_helicopterA.setPosition(this.getPosition().x + 9, this.getPosition().y + 9, 8);
                circleA = new ActionCircle(this.m_helicopterA, rotationPos, this.m_helicopterA.getPosition(), 8, 8);
                circleA.setVelocityScale(1);
                this.m_helicopterA.getStateMachine().addActions(circleA);
                Global.world.citySim.npcManager.addVehicle((Vehicle)this.m_helicopterA);
            }
            if (!this.m_helicopterB)
            {
                this.m_helicopterB = new Helicopter("policehelicopter", true);
                this.m_helicopterB.setPosition(this.getPosition().x + 9, this.getPosition().y + 9, 5);
                circleB = new ActionCircle(this.m_helicopterB, rotationPos, this.m_helicopterB.getPosition(), 5, 8, true);
                circleB.setVelocityScale(1);
                this.m_helicopterB.getStateMachine().addActions(circleB);
                Global.world.citySim.npcManager.addVehicle((Vehicle)this.m_helicopterB);
            }
            Sounds.play("cruise_fireworks");
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , this.m_buildTime * 0.25);
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , this.m_buildTime * 0.5);
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , this.m_buildTime * 0.75);
            this.m_celebrationRunning = true;
            this.m_celebrationProgress = 0;
            this.updateUnveilingEffects(0, 1000);
            return true;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.world.citySim.recomputePopulationCap(Global.world);
            return;
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            m_doobersArray = Global.player.processRandomModifiers(harvestingDefinition, this, true, m_secureRands, "default", "upgradeLoot");
            spawnDoobers();
            super.onUpgrade(param1, param2, param3);
            return;
        }//end

        public PopulationCap  getPopulationCap ()
        {
            Municipal _loc_3 =null ;
            _loc_1 = this.slots;
            _loc_2 = m_item.populationCapYield;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                _loc_2 = _loc_2 + _loc_3.getPopulationCap().getCapYield(m_item.populationType);
            }
            return new PopulationCap(_loc_2, m_item.populationType);
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            throw new Error("Not implemented.");
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            throw new Error("Not implemented.");
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            return;
        }//end

         public void  upgradeBuildingIfPossible (boolean param1 =true ,Transaction param2 ,boolean param3 =true )
        {
            super.upgradeBuildingIfPossible(param1, param2, param3);
            Global.world.citySim.recomputePopulationCap(Global.world);
            this.refreshImagePhase();
            return;
        }//end

         public void  onMechanicPackSwap ()
        {
            this.refreshImagePhase();
            return;
        }//end

        public void  onUpgradeEffectsComplete ()
        {
            UI.popupUnlock();
            return;
        }//end

         public boolean  isSellable ()
        {
            if (this.isOpen())
            {
                return false;
            }
            return super.isSellable();
        }//end

        public static double  calculateEndTime (MechanicMapResource param1 ,Array param2 )
        {
            Municipal _loc_6 =null ;
            Item _loc_7 =null ;
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            if (param1 == null || param2 == null)
            {
                return -1;
            }
            _loc_3 = GlobalEngine.getTimer()/1000;
            double _loc_4 =-1;
            double _loc_5 =-1;
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_6 = param2.get(i0);

                _loc_7 = _loc_6.getItem();
                _loc_8 = Math.round(_loc_7.growTime * 23 * 60 * 60);
                _loc_9 = _loc_6.plantTime / 1000;
                _loc_10 = _loc_9 + _loc_8;
                _loc_11 = Math.max(_loc_10 - _loc_3, 0);
                if (_loc_11 > _loc_5)
                {
                    _loc_4 = _loc_10;
                    _loc_5 = _loc_11;
                }
            }
            return _loc_4;
        }//end

        public static Municipal  getStoredMunicipalById (double param1 ,double param2 =-1)
        {
            Array _loc_3 =null ;
            Municipal _loc_4 =null ;
            MunicipalCenter _loc_5 =null ;
            Array _loc_6 =null ;
            Municipal _loc_7 =null ;
            if (param2 > 0)
            {
                _loc_3 = new Array();
                _loc_3.push(Global.world.getObjectById(param2));
            }
            else
            {
                _loc_3 = Global.world.getObjectsByClass(MunicipalCenter);
            }
            if (_loc_3 && _loc_3.length > 0)
            {
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_5 = _loc_3.get(i0);

                    _loc_6 = _loc_5.getDataForMechanic("slots");
                    if (_loc_6)
                    {
                        for(int i0 = 0; i0 < _loc_6.size(); i0++)
                        {
                        	_loc_7 = _loc_6.get(i0);

                            if (_loc_7.getId() == param1)
                            {
                                return _loc_7;
                            }
                        }
                    }
                }
            }
            return _loc_4;
        }//end

    }




