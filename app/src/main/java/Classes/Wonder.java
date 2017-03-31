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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Helpers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import com.greensock.*;
//import flash.events.*;

    public class Wonder extends MechanicMapResource implements IMultiPickOwner, ITimedHarvestable
    {
        private Helicopter m_helicopterA =null ;
        private Helicopter m_helicopterB =null ;
        private Array m_peepList ;
        private double m_buildTime ;
        private double m_celebrationTime ;
        private double m_celebrationProgress ;
        private boolean m_celebrationRunning ;
        public static  String HOVER_HINT_TOASTER ="hoverhint";
        public static  double POPUP_DELAY =2000;
        public static Object wonderTextColor =16776960;

        public  Wonder (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.WONDER;
            this.m_peepList = new Array();
            this.m_buildTime = m_item.xml.buildTime;
            this.m_celebrationTime = this.m_buildTime - POPUP_DELAY;
            this.m_celebrationProgress = 0;
            this.m_celebrationRunning = false;
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            if (Global.player.hasCoupon(getItemName()))
            {
                this.actionQueue.addActions(new ActionFunctionProgressBar(this, this.playUnveilingEffects, this.displayDialog, null, ZLoc.t("Main", "Unveiling"), this.m_buildTime / 1000));
            }
            else
            {
                this.actionQueue.addActions(new ActionFunctionProgressBar(this, null, null, null, ZLoc.t("Main", "Unveiling"), this.m_buildTime / 1000));
            }
            return;
        }//end

        protected void  displayDialog ()
        {
            _loc_4 = m_clearLock-1;
            m_clearLock = _loc_4;
            _loc_1 = ZLoc.t("Dialogs","WonderOpening",{wonderNameZLoc.t("Items",getItem().name+"_friendlyName")});
            CharacterResponseDialog _loc_2 =new CharacterResponseDialog(_loc_1 ,"WonderDialog",GenericDialogView.TYPE_SHARE ,this.onWonderOpeningDialogComplete ,"WonderOpening","assets/dialogs/citysam_construction_cheer_bust02.png",true ,0,"",null ,"",true );
            UI.displayPopup(_loc_2);
            return;
        }//end

         public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            _loc_1 = GlobalEngine.getTimer ()/1000;
            MechanicManager.getInstance().handleAction((IMechanicUser)this, "onAddToWorld");
            return;
        }//end

        protected void  onWonderOpeningDialogComplete (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendWonderOpeningFeed(ZLoc.t("Items", getItem().name + "_friendlyName"), getItemName());
            }
            return;
        }//end

        public Object harvestState ()
        {
            return mechanicData.get("harvestState");
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
            if (mechanicData != null && mechanicData.hasOwnProperty("harvestState") && mechanicData.get("harvestState") != null && mechanicData.get("harvestState").hasOwnProperty("closeTS"))
            {
                return mechanicData.get("harvestState").get("closeTS");
            }
            return -1;
        }//end

        public Object sleepState ()
        {
            return mechanicData.get("sleepState");
        }//end

        public double  sleepTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("sleepTS"))
            {
                return mechanicData.get("sleepState").get("sleepTS");
            }
            return -1;
        }//end

        public double  wakeTS ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("wakeTS"))
            {
                return mechanicData.get("sleepState").get("wakeTS");
            }
            return -1;
        }//end

        public int  consumables ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("consumables"))
            {
                return mechanicData.get("sleepState").get("consumables");
            }
            return 0;
        }//end

        public int  maintenance ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("sleepState") && mechanicData.get("sleepState") != null && mechanicData.get("sleepState").hasOwnProperty("maintenance"))
            {
                return mechanicData.get("sleepState").get("maintenance");
            }
            return 0;
        }//end

        public Object  openingState ()
        {
            if (mechanicData != null && mechanicData.hasOwnProperty("openingState"))
            {
                return mechanicData.get("openingState");
            }
            return null;
        }//end

        public void  openingState (Object param1 )
        {
            mechanicData.put("openingState",  param1);
            return;
        }//end

        public double  sleepTimeLeft ()
        {
            if (this.sleepTS < 0 || this.wakeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.wakeTS;
            _loc_2 = GlobalEngine.getTimer()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

        public boolean  isSleeping ()
        {
            _loc_1 = GlobalEngine.getTimer ()/1000;
            return this.sleepTS > 0 && this.sleepTS <= _loc_1 && this.sleepTimeLeft > 0;
        }//end

        public double  openTimeLeft ()
        {
            if (this.openTS < 0 || this.closeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.closeTS ;
            _loc_2 = GlobalEngine.getTimer ()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

         public boolean  isOpen ()
        {
            return this.openTS > 0 && this.openTimeLeft > 0;
        }//end

        public void  harvestLoop (Object param1 )
        {
            double _loc_4 =0;
            double _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            _loc_2 =(double)(m_item.harvestLoopConfig.get( "timerDuration") );
            _loc_3 =(double)(m_item.harvestLoopConfig.get( "sleepDuration") );
            if (param1 !=null)
            {
                _loc_4 = param1.get("startTS");
                _loc_5 = param1.get("startTS") + _loc_2;
                _loc_6 = _loc_5;
                _loc_7 = _loc_5 + _loc_3;
                if (mechanicData.get("harvestState") == null)
                {
                    mechanicData.put("harvestState",  new Object());
                }
                if (mechanicData.get("sleepState") == null)
                {
                    mechanicData.put("sleepState",  new Object());
                }
                mechanicData.get("harvestState").put("openTS",  _loc_4);
                mechanicData.get("harvestState").put("closeTS",  _loc_5);
                mechanicData.get("sleepState").put("sleepTS",  _loc_6);
                mechanicData.get("sleepState").put("wakeTS",  _loc_7);
                mechanicData.get("sleepState").put("maintenance",  param1.get("maintenance"));
                mechanicData.get("sleepState").put("consumables",  param1.get("consumables"));
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
            _loc_1.consumables = this.consumables;
            _loc_1.maintenance = this.maintenance;
            return _loc_1;
        }//end

        public void  maintenanceHarvestLoop (Object param1 )
        {
            this.harvestLoop = param1;
            return;
        }//end

        public Object  maintenanceHarvestLoop ()
        {
            return this.harvestLoop;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            _loc_2 = getItem();
            _loc_3 = _loc_2? (_loc_2.harvestEnergyCost) : (0);
            if (this.isSleeping())
            {
                _loc_1 = ZLoc.t("Main", "MaintenanceMode", {time:GameUtil.formatMinutesSeconds(this.sleepTimeLeft)});
            }
            else if (this.isOpen())
            {
                _loc_1 = ZLoc.t("Main", "OpenBuilding", {time:GameUtil.formatMinutesSeconds(this.openTimeLeft)});
            }
            else
            {
                _loc_1 = ZLoc.t("Main", "ReadyNow") + "\n" + ZLoc.t("Main", "RequiresEnergy", {count:_loc_3});
            }
            return _loc_1;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            this.cleanupUnveilingEffects();
            return;
        }//end

         public double  getRadius (ItemBonus param1 )
        {
            if (this.isOpen())
            {
                return param1.radiusMax;
            }
            return param1.radiusMin;
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

        protected boolean  allowHoverToaster ()
        {
            return !Global.isVisiting() && !Global.world.isEditMode && !isLocked() && this.isSleeping();
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            if (this.allowHoverToaster())
            {
                showToaster(HOVER_HINT_TOASTER, true);
            }
            return;
        }//end

         public void  onMouseOut ()
        {
            super.onMouseOut();
            if (this.allowHoverToaster())
            {
                Global.ui.toaster.hide();
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            if (this.m_celebrationRunning)
            {
                this.m_celebrationProgress = this.m_celebrationProgress + param1 * 1000;
                if (this.m_celebrationProgress > this.m_celebrationTime)
                {
                    this.onCelebrationEnded();
                }
            }
            return;
        }//end

        public void  onCelebrationEnded ()
        {
            this.fadeUnveilingEffects();
            TimerUtil.callLater(this.cleanupUnveilingEffects, 1000);
            this.m_celebrationRunning = false;
            return;
        }//end

        private boolean  isOnWater ()
        {
            _loc_1 = getPositionNoClone();
            return Global.world.citySim.waterManager.testWater(_loc_1.x, _loc_1.y);
        }//end

        public void  updateUnveilingEffects (double param1 ,double param2 )
        {
            NPC peep ;
            double i ;
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
            if (!this.isOnWater())
            {
                i = 0;
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
            if (!this.m_helicopterA)
            {
                this.m_helicopterA = new Helicopter("policehelicopter", true);
                this.m_helicopterA.setPosition(this.getPosition().x + 6, this.getPosition().y + 6, 8);
                circleA = new ActionCircle(this.m_helicopterA, this.getPosition(), this.m_helicopterA.getPosition(), 8, 6);
                circleA.setVelocityScale(1);
                this.m_helicopterA.getStateMachine().addActions(circleA);
                Global.world.citySim.npcManager.addVehicle((Vehicle)this.m_helicopterA);
            }
            if (!this.m_helicopterB)
            {
                this.m_helicopterB = new Helicopter("policehelicopter", true);
                this.m_helicopterB.setPosition(this.getPosition().x + 6, this.getPosition().y + 6, 5);
                circleB = new ActionCircle(this.m_helicopterB, this.getPosition(), this.m_helicopterB.getPosition(), 5, 6, true);
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

        public boolean  clearDirtyOnUpdate ()
        {
            return !isLocked();
        }//end

        public static String  itemBonusFlyout (MapResource param1 )
        {
            MapResource _loc_4 =null ;
            Item _loc_5 =null ;
            Array _loc_6 =null ;
            ItemBonus _loc_7 =null ;
            _loc_2 = Global.world.getObjectsByKeywords("wonder");
            double _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                if (_loc_4.isOpen())
                {
                    _loc_5 = _loc_4.getItem();
                    _loc_6 = _loc_5.bonuses;
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    	_loc_7 = _loc_6.get(i0);

                        if (_loc_7.bonusAppliesToObject(param1))
                        {
                            _loc_3 = _loc_3 + _loc_7.coinModifier;
                        }
                    }
                }
            }
            if (_loc_3 > 0)
            {
                return ZLoc.t("Main", "WonderBonus", {amount:_loc_3});
            }
            return null;
        }//end

        public static Wonder  getFirstActiveWonder ()
        {
            Wonder _loc_2 =null ;
            _loc_1 = Global.world.getObjectsByType(WorldObjectTypes.WONDER);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2.isOpen())
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

    }




