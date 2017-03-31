package Modules.bandits;

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
import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Modules.bandits.transactions.*;
//import flash.utils.*;

    public class MunicipalComponentHub extends MunicipalComponentBase
    {
        protected Function m_endFunc =null ;
public static Dictionary m_hubGroupsAlreadyUpgrading ;

        public  MunicipalComponentHub (Municipal param1 )
        {
            super(param1);
            param1.defaultUpgradeFinishCallback = this.upgradeAnimationFinish;
            return;
        }//end

        public String  groupID ()
        {
            _loc_1 =Global.gameSettings().getHubGroupIdForItemName(this.m_municipal.getItemName ());
            if (!_loc_1)
            {
                throw new Error("This municipal isn\'t associated with a hub group");
            }
            return _loc_1;
        }//end

         public void  handlePlayAction ()
        {
            if (isHubGroupUpgrading(this.groupID))
            {
                return;
            }
            if (Global.isVisiting() || isHarvestable() && !Global.guide.isActive())
            {
                this.m_endFunc = null;
                super.handlePlayAction();
                this.m_municipal.removeStagePickEffect();
                if (!Global.isVisiting())
                {
                    PreyUtil.logGameActionStats("municipal", "collect", m_municipal.getItem().name);
                }
                return;
            }
            if (this.isHubGated())
            {
                Global.world.citySim.preyManager.showHubGate(this.groupID, this.m_municipal);
                return;
            }
            if (this.isUpgradePossible())
            {
                this.dealWithUpgrades();
            }
            else if (this.isMinLevelReached())
            {
                UI.displayHubDialog(this.groupID);
            }
            return;
        }//end

        protected void  dealWithUpgrades ()
        {
            int newLevel ;
            Array newStations ;
            Municipal station ;
            double delay ;
            int testLevel ;
            int max ;
            int level ;
            this .m_endFunc =void  ()
            {
                return;
            }//end
            ;
            if (PreyManager.canUpgradeAllHubsOfGroup(this.groupID) && Global.world.citySim.preyManager.getHubUnlockLevel(this.groupID) == PreyUtil.getHubLevel(this.groupID))
            {
                setHubGroupUpgrading(this.groupID, true);
                PreyManager.upgradeAllHubs(this.groupID);
                newLevel = PreyUtil.getHubLevel(this.groupID);
                newStations = PreyUtil.getHubsByGroup(this.groupID);
                int _loc_2 =0;
                _loc_3 = newStations;
                for(int i0 = 0; i0 < newStations.size(); i0++)
                {
                		station = newStations.get(i0);


                    testLevel = PreyUtil.getHubInstanceLevel(this.groupID, station);
                    if (testLevel == newLevel)
                    {
                        PreyUtil.logGameActionStats("municipal", "upgrade_to", station.getItem().name);
                        break;
                    }
                }
                delay = Global.gameSettings().getNumber("actionBarAny", 2);
                TimerUtil .callLater (void  ()
            {
                if (isHubGated())
                {
                    Global.world.citySim.preyManager.showHubGate(groupID, m_municipal);
                }
                else
                {
                    Global.world.citySim.preyManager.onUpgradeGateSuccess(groupID, m_municipal);
                }
                setHubGroupUpgrading(groupID, false);
                return;
            }//end
            , delay * 1000 + 200);
            }
            else if (this.isCatchUpUpgradePossible())
            {
                PreyManager.upgradeCatchUp(this.groupID, m_municipal);
                m_municipal.doUpgradeAnimations(m_municipal.defaultUpgradeFinishCallback);
            }
            else
            {
                max = PreyUtil.getHubLevel(this.groupID);
                level = PreyUtil.getHubInstanceLevel(this.groupID, m_municipal);
                if (level > 0 && level < max)
                {
                    m_municipal.upgradeBuildingIfPossible();
                }
            }
            GameTransactionManager.addTransaction(new TProcessHubWorkers(this.groupID));
            return;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return this.m_endFunc;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = m_municipal.getGameModeToolTipAction();
            if (!_loc_1 && !Global.isVisiting())
            {
                if (isHarvestable() && !Global.guide.isActive())
                {
                    _loc_1 = ZLoc.t("Main", "RipeEntertainment");
                }
                else if (this.isUpgradePossible())
                {
                    _loc_1 = ZLoc.t("Dialogs", "UpgradeToolTip");
                }
                else if (this.isResourceNeeded())
                {
                    _loc_1 = PreyManager.getHubResourceToolTipAction(this.groupID);
                }
            }
            return _loc_1;
        }//end

         protected boolean  isUpgradePossible ()
        {
            return !Global.isVisiting() && (m_municipal.isUpgradePossible() || this.isCatchUpUpgradePossible());
        }//end

        protected boolean  isCatchUpUpgradePossible ()
        {
            return !Global.isVisiting() && (PreyUtil.getHubInstanceLevel(this.groupID, m_municipal) < PreyUtil.getHubLevel(this.groupID) || PreyUtil.getHubInstanceLevel(this.groupID, m_municipal) < Global.world.citySim.preyManager.getHubUnlockLevel(this.groupID));
        }//end

        protected boolean  isHubGated ()
        {
            if (!Global.isVisiting() && Global.world.citySim.preyManager.areHubsGated(this.groupID))
            {
                return true;
            }
            m_municipal.removeAnimatedEffectByClass(ScaffoldEffect);
            return false;
        }//end

        protected boolean  isMinLevelReached ()
        {
            return !Global.world.citySim.preyManager.areHubsLocked(this.groupID);
        }//end

        protected boolean  isResourceNeeded ()
        {
            return Global.world.citySim.preyManager.doesHubNeedResource(this.groupID);
        }//end

         public boolean  enableUpdateArrow ()
        {
            return !Global.isVisiting() && (this.isUpgradePossible() || this.isHubGated() || this.isResourceNeeded()) || super.enableUpdateArrow();
        }//end

         public void  createStagePickEffect ()
        {
            ScaffoldEffect _loc_1 =null ;
            if (!Global.isVisiting() && isHarvestable() && !Global.guide.isActive())
            {
                super.createStagePickEffect();
            }
            else if (this.isUpgradePossible())
            {
                createStagePickEffectHelper(StagePickEffect.PICK_UPGRADE);
            }
            else if (this.isResourceNeeded() && !this.isHubGated())
            {
                createStagePickEffectHelper(PreyManager.getHubResourceStagePick(this.groupID));
            }
            if (this.isHubGated())
            {
                if (!isHarvestable())
                {
                    createStagePickEffectHelper(StagePickEffect.PICK_UPGRADE);
                }
                _loc_1 =(ScaffoldEffect) m_municipal.getAnimatedEffectByClass(ScaffoldEffect);
                if (_loc_1 == null)
                {
                    _loc_1 = new ScaffoldEffect(m_municipal);
                    m_municipal.addAnimatedEffectFromInstance(_loc_1);
                }
                else
                {
                    _loc_1.reattach();
                }
            }
            else
            {
                m_municipal.removeAnimatedEffectByClass(ScaffoldEffect);
            }
            return;
        }//end

         public void  onBuildingConstructionCompleted ()
        {
            Global.world.citySim.preyManager.showHubUnlockedFTUEIfNecessary(this.groupID);
            GameTransactionManager.addTransaction(new TProcessHubWorkers(this.groupID));
            return;
        }//end

         public void  doUpgradeAnimations ()
        {
            m_municipal.doUpgradeAnimations(this.upgradeAnimationFinish);
            return;
        }//end

        protected void  upgradeAnimationFinish ()
        {
            PreyManager.onSpecialNPCChange(this.groupID, true);
            PreyManager.celebrateHubUpgrade(this.groupID, this.m_municipal);
            return;
        }//end

        public static boolean  isHubGroupUpgrading (String param1 )
        {
            if (!m_hubGroupsAlreadyUpgrading)
            {
                m_hubGroupsAlreadyUpgrading = new Dictionary();
            }
            if (param1 !=null)
            {
                return m_hubGroupsAlreadyUpgrading.get(param1) === true;
            }
            return false;
        }//end

        public static void  setHubGroupUpgrading (String param1 ,boolean param2 )
        {
            if (!m_hubGroupsAlreadyUpgrading)
            {
                m_hubGroupsAlreadyUpgrading = new Dictionary();
            }
            if (param1 !=null)
            {
                m_hubGroupsAlreadyUpgrading.put(param1,  param2);
            }
            return;
        }//end

    }



