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
import Classes.gates.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Modules.crew.*;
import Modules.guide.actions.*;
import Transactions.*;

    public class MunicipalComponentBase
    {
        protected Municipal m_municipal ;
        protected IGate m_gate ;
        protected int m_lastPopulation ;
        protected boolean m_inExperiment =true ;
        protected boolean m_cancelTutorial =false ;
public static GateFactory m_gateFactory ;
public static  int UPGRADE_INTRO_DELAY =2000;
public static  String TOASTER_SAM ="assets/dialogs/toasterSam.png";

        public  MunicipalComponentBase (Municipal param1 )
        {
            this.m_municipal = param1;
            if (m_gateFactory == null)
            {
                m_gateFactory = new GateFactory();
                m_gateFactory.register(GateType.INVENTORY, InventoryGate);
                m_gateFactory.register(GateType.CREW, CrewGate);
            }
            this.m_gate = m_gateFactory.loadGateFromXML(this.m_municipal.getItem(), this.m_municipal, "pre_upgrade", this.onUpgradeSuccess);
            return;
        }//end

        public boolean  passesExperimentGate ()
        {
            return this.m_inExperiment;
        }//end

        public void  onLoadObject (Object param1 )
        {
            if (this.m_gate != null)
            {
                this.m_gate.targetObjectId = this.m_municipal.getId();
            }
            return;
        }//end

        public void  onSetId (double param1 )
        {
            if (this.m_gate != null)
            {
                this.m_gate.targetObjectId = param1;
            }
            return;
        }//end

        public Function  getProgressBarEndFunction ()
        {
            return null;
        }//end

        public void  handlePlayAction ()
        {
            double _loc_1 =0;
            if (Global.guide.isActive())
            {
                RollCallManager.debugSample("MunicipalComponentBase", "guideActiveClick");
            }
            if (!Global.guide.isActive())
            {
                if (!Global.isVisiting() && this.m_municipal.getState() == HarvestableResource.STATE_GROWN)
                {
                    _loc_1 = this.m_municipal.getItem().harvestEnergyCost;
                    if (!Global.player.checkEnergy(-_loc_1))
                    {
                        this.m_municipal.displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                    }
                    else
                    {
                        this.m_municipal.doEnergyChanges(-_loc_1, new Array("energy", "expenditures", "collect_municipal", this.m_municipal.getItem().name));
                        Global.player.heldEnergy = Global.player.heldEnergy + _loc_1;
                        this.m_municipal.heldEnergy = _loc_1;
                        Global.world.citySim.pickupManager.enqueue("NPC_mailman", this.m_municipal);
                    }
                }
                else if (!Global.isVisiting() && this.m_municipal.isUpgradePossible())
                {
                    this.m_municipal.upgradeBuildingIfPossible();
                }
                else if (this.m_municipal.getItem().special_onclick == "visitor_center")
                {
                    if (Global.isVisiting())
                    {
                        StatsManager.sample(1000, "dialog", "visitorcenter", "view", "visitorboard");
                    }
                    else
                    {
                        StatsManager.sample(1000, "dialog", "visitorcenter", "view", "ownboard");
                    }
                    UI.displayVisitorCenterDialog();
                }
            }
            else if (this.isUpgradePossible() && Global.guide.getActiveSequence().currentAction() instanceof GAWaitForClickAnywhere)
            {
                if (this.m_gate != null)
                {
                    if (this.passesExperimentGate())
                    {
                        this.m_gate.displayGate();
                    }
                }
            }
            return;
        }//end

        public void  doHarvestDropOff (boolean param1 )
        {
            if (!this.passesExperimentGate())
            {
                return;
            }
            if (!this.m_municipal.getItem().upgrade || !this.m_municipal.getItem().upgrade.isValid())
            {
                return;
            }
            if (this.m_municipal.getItem().upgrade.requirements.isRequirementTypeDefined(Requirements.REQUIREMENT_UPGRADE_ACTIONS))
            {
                this.m_municipal.incrementUpgradeActionCount();
            }
            return;
        }//end

        public String  getToolTipStatus ()
        {
            if (!Global.isVisiting() && this.m_municipal.getState() == HarvestableResource.STATE_PLANTED)
            {
                return ZLoc.t("Main", "BareResidence", {time:GameUtil.formatMinutesSeconds(this.m_municipal.getGrowTimeLeft())});
            }
            return "";
        }//end

        public String  getToolTipAction ()
        {
            _loc_1 = this.m_municipal.getGameModeToolTipAction();
            if (!_loc_1)
            {
                if (this.isHarvestable() && !Global.guide.isActive())
                {
                    _loc_1 = ZLoc.t("Main", "RipeEntertainment");
                }
                else if (this.isUpgradePossible() && this.passesExperimentGate())
                {
                    _loc_1 = ZLoc.t("Dialogs", "UpgradeToolTip");
                }
            }
            return _loc_1;
        }//end

        protected boolean  isUpgradePossible ()
        {
            return !Global.isVisiting() && this.m_municipal.isUpgradePossible();
        }//end

        public boolean  enableUpdateArrow ()
        {
            return !Global.isVisiting() && (this.m_municipal.getState() == HarvestableResource.STATE_GROWN || this.m_municipal.isUpgradePossible() && !this.m_municipal.isLocked());
        }//end

        public void  createStagePickEffect ()
        {
            if (Global.guide.isActive())
            {
                return;
            }
            if (this.isHarvestable())
            {
                this.createStagePickEffectHelper(this.getHarvestPickIconKey());
            }
            else if (this.passesExperimentGate() && this.isUpgradePossible() && !this.m_municipal.isLocked())
            {
                if (this.m_gate != null && this.m_gate.checkForKeys())
                {
                    this.createStagePickEffectHelper(StagePickEffect.PICK_UPGRADE_HAMMER);
                }
                else if (this.m_municipal.getShowUpgradeArrow())
                {
                    this.createStagePickEffectHelper(StagePickEffect.PICK_UPGRADE);
                }
            }
            return;
        }//end

        protected String  getHarvestPickIconKey ()
        {
            if (this.m_municipal.getItem().harvestIcon != null)
            {
                return this.m_municipal.getItem().harvestIcon;
            }
            return StagePickEffect.PICK_1;
        }//end

        public boolean  upgradeGateKeysMet ()
        {
            if (this.passesExperimentGate() && this.isUpgradePossible())
            {
                return this.m_gate != null && this.m_gate.checkForKeys();
            }
            return false;
        }//end

        protected void  createStagePickEffectHelper (String param1 )
        {
            _loc_2 = this.m_municipal.stagePickEffect;
            if (!_loc_2)
            {
                _loc_2 =(StagePickEffect) MapResourceEffectFactory.createEffect(this.m_municipal, EffectType.STAGE_PICK);
                _loc_2.setPickType(param1);
                _loc_2.float();
            }
            else
            {
                _loc_2.setPickType(param1);
                _loc_2.reattach();
                _loc_2.float();
            }
            this.m_municipal.stagePickEffect = _loc_2;
            return;
        }//end

        public void  upgradeBuildingIfPossible ()
        {
            if (!this.m_municipal.isUpgradePossible())
            {
                return;
            }
            if (this.m_gate != null)
            {
                if (this.passesExperimentGate())
                {
                    this.m_cancelTutorial = true;
                    this.m_gate.displayGate();
                }
            }
            else
            {
                this.onUpgradeSuccess();
            }
            return;
        }//end

        protected void  onUpgradeSuccess ()
        {
            _loc_1 = Global.gameSettings().getItemByName(this.m_municipal.getItem().upgrade.newItemName);
            GameTransactionManager.addTransaction(new TUpgradeBuilding(this.m_municipal));
            this.m_municipal.onUpgrade(this.m_municipal.getItem(), _loc_1);
            this.doUpgradeAnimations();
            this.m_gate = m_gateFactory.loadGateFromXML(this.m_municipal.getItem(), this.m_municipal, "pre_upgrade", this.onUpgradeSuccess);
            Global.world.citySim.recomputePopulationCap(Global.world);
            this.m_municipal.resetUpgradeActionCount();
            return;
        }//end

        protected boolean  isHarvestable ()
        {
            return !Global.isVisiting() && this.m_municipal.getState() == HarvestableResource.STATE_GROWN;
        }//end

        public void  doUpgradeAnimations ()
        {
            this.m_municipal.doUpgradeAnimations(this.m_municipal.defaultUpgradeFinishCallback);
            return;
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            return;
        }//end

        public void  onLevelChanged (int param1 )
        {
            return;
        }//end

        public void  onBuildingConstructionCompleted ()
        {
            return;
        }//end

        protected boolean  isEqualOrHigherUpgradedMunicipal (WorldObject param1 )
        {
            Item _loc_2 =null ;
            if (param1 instanceof MapResource)
            {
                _loc_2 = ((MapResource)param1).getItem();
                return _loc_2 != null && _loc_2.type == "municipal" && _loc_2.behavior == this.m_municipal.getItem().behavior && _loc_2.level >= this.m_municipal.getItem().level;
            }
            return false;
        }//end

    }



