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
import Classes.sim.*;
import Classes.util.*;
import Display.Toaster.*;
import Display.hud.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import Mechanics.GameMechanicInterfaces.*;

    public class PowerStation extends MechanicMapResource implements IStreakHandler, IPeepCapacity
    {
        private  String POWERSTATION ="powerstation";
        protected boolean m_wasSupplied =false ;
        protected boolean m_hasDecreased =false ;
        protected Timer m_negativeTimer =null ;
        public static  int STREAK_DECREASE_MSG_DELAY =30;
        public static  int PROGRESS_BAR_DELAY =4;

        public  PowerStation (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.MUNICIPAL;
            m_typeName = this.POWERSTATION;
            return;
        }//end

        public PopulationCap  getPopulationCap ()
        {
            _loc_1 = m_item.populationCapYield;
            _loc_2 = m_item.populationType;
            PopulationCap _loc_3 =new PopulationCap(_loc_1 ,_loc_2 );
            return _loc_3;
        }//end

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            return;
        }//end

         public String  getToolTipStatus ()
        {
            MechanicConfigData _loc_2 =null ;
            double _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            String _loc_10 =null ;
            _loc_1 = getDataForMechanic("streakData");
            if (_loc_1 != null)
            {
                _loc_2 = getMechanicConfig("streakData");
                _loc_3 = GlobalEngine.getTimer() / 1000;
                if (_loc_1.get("activationTime") != -1)
                {
                    _loc_4 = _loc_2.params.get("activeDuration");
                    _loc_5 = _loc_4 - (_loc_3 - _loc_1.get("activationTime"));
                    if (_loc_5 > 0)
                    {
                        return ZLoc.t("Main", "PowerStationPowered", {time:GameUtil.formatMinutesSeconds(_loc_5)});
                    }
                }
                else if (_loc_1.get("inactiveTime") != -1)
                {
                    _loc_6 = Global.player.getEnergyModifierValueByName(String(this.getId()));
                    if (_loc_6 > 0)
                    {
                        _loc_7 = _loc_3 - _loc_1.get("inactiveTime");
                        _loc_8 = _loc_2.params.get("inactiveDuration");
                        _loc_9 = _loc_8 - _loc_7;
                        _loc_10 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_2.get("params").get("consumableQuantity")});
                        if (_loc_9 > 0)
                        {
                            return _loc_10 + "\n" + ZLoc.t("Main", "PowerStationInactive", {time:GameUtil.formatMinutesSeconds(_loc_9)});
                        }
                        return _loc_10;
                    }
                }
            }
            return super.getToolTipStatus();
        }//end

         public int  getToolTipFloatOffset ()
        {
            int _loc_1 =0;
            if (m_stagePickEffect)
            {
                _loc_1 = m_stagePickEffect.getAnimationHeight();
            }
            _loc_1 = _loc_1 - 40;
            return super.getToolTipFloatOffset() + _loc_1;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage("static",this,m_direction,m_statePhase);
            return _loc_1;
        }//end

         public boolean  isCurrentImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading("static", m_direction, m_statePhase);
        }//end

         public boolean  isDrawImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading("static", m_direction, m_statePhase);
        }//end

        private boolean  notifyOnce (String param1 ,String param2 )
        {
            if (!Global.player.getSeenFlag(param1))
            {
                Global.guide.notify(param2);
                Global.player.setSeenFlag(param1);
                return true;
            }
            return false;
        }//end

        protected void  notifyDecrease ()
        {
            String _loc_1 ="";
            _loc_2 = ZLoc.t("Dialogs","PowerStationCapDecrease_ToasterText",{powerstationthis.getItemFriendlyName(),max.player.energyMaxBase});
            _loc_3 = Global.getAssetURL("assets/dialogs/cityjack_toaster.png");
            ItemToaster _loc_4 =new ItemToaster(_loc_1 ,_loc_2 ,_loc_3 );
            _loc_4.duration = 5000;
            Global.ui.toaster.show(_loc_4);
            this.m_negativeTimer = null;
            return;
        }//end

        protected void  notifyIncrease (int param1 =0)
        {
            String _loc_2 ="";
            _loc_3 = ZLoc.t("Dialogs","PowerStationCapIncrease_ToasterText",{amountparam1,powerstation.getItemFriendlyName()});
            _loc_4 = Global.getAssetURL("assets/dialogs/cityjack_toaster.png");
            ItemToaster _loc_5 =new ItemToaster(_loc_2 ,_loc_3 ,_loc_4 );
            _loc_5.duration = 5000;
            Global.ui.toaster.show(_loc_5);
            return;
        }//end

        protected void  notifyMaintained ()
        {
            String _loc_1 ="";
            _loc_2 = ZLoc.t("Dialogs","PowerStationCapMaintained_ToasterText");
            _loc_3 = Global.getAssetURL("assets/dialogs/cityjack_toaster.png");
            ItemToaster _loc_4 =new ItemToaster(_loc_1 ,_loc_2 ,_loc_3 );
            _loc_4.duration = 5000;
            Global.ui.toaster.show(_loc_4);
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.world.citySim.recomputePopulationCap(Global.world);
            Global.player.removeEnergyModifierByName(String(this.getId()));
            return;
        }//end

        public void  onPlayerStreakEvent (Event event )
        {
            if (this.m_hasDecreased && !this.m_wasSupplied)
            {
                this.notifyOnce("NPCDamEnergyDecayGuide", "NPCDamEnergyDecayGuide");
            }
            this.m_wasSupplied = true;
            return;
        }//end

        public double  getCurrentStreakEffect ()
        {
            return Global.player.getEnergyModifierValueByName(String(this.getId()));
        }//end

        public void  processPositiveStreak (double param1 )
        {
            String _loc_5 =null ;
            _loc_6 = undefined;
            _loc_2 = Global.player.getEnergyModifierValueByName(String(this.getId()));
            _loc_3 = _loc_2;
            double _loc_4 =0;
            _loc_2 = _loc_2 + m_item.getPostiveStreakReward((param1 - 1));
            if (_loc_2 > m_item.getPositiveStreakMaxEffect())
            {
                _loc_2 = m_item.getPositiveStreakMaxEffect();
            }
            else
            {
                m_doobersArray = Global.player.processRandomModifiers(harvestingDefinition, this, true, m_secureRands, "default", "supply");
                spawnDoobers();
            }
            _loc_4 = _loc_2 - _loc_3;
            Global.player.setEnergyModifierByName(String(this.getId()), _loc_2);
            StatsManager.sample(100, "max_energy", "increase", "power_station", "dam", "", "", _loc_2);
            if (_loc_4 == 0)
            {
                this.notifyMaintained();
            }
            else if (!this.notifyOnce("NPCDamEnergyIncrementGuide", "NPCDamIncrementGuide"))
            {
                if (Global.hud)
                {
                    Global.hud.conditionallyRefreshHUD(true);
                }
                _loc_5 = ZLoc.t("Main", "GainEnergyCap", {energy:_loc_4});
                _loc_6 = new EmbeddedArt.smallEnergyIcon();
                displayStatus(_loc_5, _loc_6, 43520);
                this.notifyIncrease(_loc_4);
            }
            return;
        }//end

        public void  processNegativeStreak (double param1 )
        {
            boolean _loc_5 =false ;
            double _loc_6 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            _loc_7 = Global.player.getEnergyModifierValueByName(String(this.getId()));
            _loc_3 = Global.player.getEnergyModifierValueByName(String(this.getId()));
            _loc_2 = _loc_7;
            _loc_2 = _loc_2 - m_item.getNegativeStreakReward((param1 - 1));
            if (_loc_2 < 0)
            {
                _loc_2 = 0;
            }
            _loc_4 = _loc_2 - _loc_3;
            Global.player.setEnergyModifierByName(String(this.getId()), _loc_2);
            if (_loc_4 < 0)
            {
                if (Global.hud)
                {
                    Global.hud.conditionallyRefreshHUD(true);
                }
                _loc_5 = false;
                if (this.m_wasSupplied)
                {
                    _loc_5 = this.notifyOnce("NPCDamEnergyDecayGuide", "NPCDamEnergyDecayGuide");
                }
                if (!_loc_5 && !this.m_negativeTimer)
                {
                    _loc_6 = STREAK_DECREASE_MSG_DELAY * 1000;
                    if (this.m_wasSupplied)
                    {
                        _loc_6 = 500;
                    }
                    this.m_negativeTimer = TimerUtil.callLater(this.notifyDecrease, _loc_6);
                }
                this.m_hasDecreased = true;
            }
            return;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PreServerUpdate();
            Global.world.citySim.recomputePopulationCap(Global.world);
            this.celebrateCompletion();
            this.activateEnergyModifierBar();
            Global.player.setEnergyModifierByName(String(this.getId()), 0);
            return;
        }//end

        public double  getCurrentStreak ()
        {
            _loc_1 = getDataForMechanic("streakData");
            return _loc_1.get("streakLength");
        }//end

        protected void  activateEnergyModifierBar ()
        {
            if (Global.player)
            {
                Global.player.setFlag(HUD.SHOW_ENERGY_MODIFIER_FLAG, 1);
            }
            return;
        }//end

        private void  celebrateCompletion ()
        {
            fireZeMissiles();
            this.m_actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Celebrating"), PROGRESS_BAR_DELAY));
            return;
        }//end

         public Point  getProgressBarOffset ()
        {
            return new Point(0, 0);
        }//end

         public Function  getProgressBarStartFunction ()
        {
            return boolean  ()
            {
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return void  ()
            {
                notifyOnce("NPCDamCompletionGuide", "NPCDamCompletionGuide");
                return;
            }//end
            ;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                return;
            }//end
            ;
        }//end

         public boolean  canBeDragged ()
        {
            _loc_1 = this.getItem();
            if (_loc_1.placeableOnly != null && _loc_1.placeableOnly != "")
            {
                return false;
            }
            return super.canBeDragged();
        }//end

         public boolean  canBeRotated ()
        {
            _loc_1 = this.getItem();
            if (_loc_1.placeableOnly != null && _loc_1.placeableOnly != "")
            {
                return false;
            }
            return super.canBeRotated();
        }//end

    }





