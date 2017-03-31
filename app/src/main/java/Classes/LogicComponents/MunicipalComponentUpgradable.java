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
import Classes.gates.*;
import Classes.util.*;
import Display.Toaster.*;
import Engine.Managers.*;
import Modules.stats.types.*;

    public class MunicipalComponentUpgradable extends MunicipalComponentBase
    {

        public  MunicipalComponentUpgradable (Municipal param1 )
        {
            super(param1);
            m_gateFactory.register(GateType.INVENTORY, UpgradeInventoryGate);
            m_gateFactory.register(GateType.CREW, UpgradeCrewGate);
            this.m_gate = m_gateFactory.loadGateFromXML(m_municipal.getItem(), m_municipal, "pre_upgrade", this.onUpgradeSuccess);
            m_inExperiment = true;
            if (m_municipal.getItem().upgrade && m_municipal.getItem().upgrade.experiment)
            {
                m_inExperiment = Global.experimentManager.getVariant(m_municipal.getItem().upgrade.experiment) == 1;
            }
            return;
        }//end

         public boolean  passesExperimentGate ()
        {
            return super.passesExperimentGate() || m_municipal.getItem().level > 1;
        }//end

         protected void  onUpgradeSuccess ()
        {
            Array _loc_1 =null ;
            String _loc_2 =null ;
            ItemToaster _loc_3 =null ;
            String _loc_4 =null ;
            super.onUpgradeSuccess();
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "unlock_" + m_municipal.getItem().name, m_municipal.getItem().upgrade.upgradeTrackingClass);
            this.m_municipal.setFullGrown();
            if (this.m_municipal.harvest())
            {
                this.m_municipal.doHarvestDropOff(true);
            }
            if (m_municipal.getItem().level >= 2)
            {
                _loc_1 = Global.world.getObjectsByPredicate(isEqualOrHigherUpgradedMunicipal);
                _loc_2 = null;
                _loc_3 = null;
                if (_loc_1.length == 1 && m_municipal.getItem().level == 2)
                {
                    _loc_2 = ZLoc.t("Main", m_municipal.getItem().upgrade.firstTimeToasterText);
                }
                else if (_loc_1.length == 1 && m_municipal.getItem().level > 2)
                {
                    _loc_2 = ZLoc.t("Main", "UnlockToasterText", {itemName:this.m_municipal.getItem().localizedName});
                }
                _loc_4 = TOASTER_SAM;
                if (m_municipal.getItem().upgrade.toasterIcon)
                {
                    _loc_4 = m_municipal.getItem().upgrade.toasterIcon;
                }
                if (_loc_2 != null && _loc_2.length > 0)
                {
                    _loc_3 = new ItemToaster("", _loc_2, Global.getAssetURL(_loc_4));
                    Global.ui.toaster.show(_loc_3, false);
                }
            }
            return;
        }//end

         public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            m_lastPopulation = param1;
            return;
        }//end

         public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            _loc_5 = param1-m_lastPopulation ;
            if (param1 - m_lastPopulation > 0)
            {
                this.attemptUpgradeIntro();
            }
            m_lastPopulation = param1;
            return;
        }//end

         public void  onLevelChanged (int param1 )
        {
            this.attemptUpgradeIntro();
            return;
        }//end

         public void  onBuildingConstructionCompleted ()
        {
            this.attemptUpgradeIntro();
            return;
        }//end

        protected void  attemptUpgradeIntro ()
        {
            _loc_1 = m_municipal.getItem().upgrade.introSeenFlag;
            if (_loc_1 != "" && !Global.player.getSeenFlag(_loc_1) && m_inExperiment)
            {
                if (this.m_municipal.isUpgradePossible())
                {
                    Global.player.setSeenFlag(m_municipal.getItem().upgrade.introSeenFlag);
                    this.showUpgradeIntro();
                }
            }
            return;
        }//end

        protected void  showUpgradeIntro ()
        {
            if (Global.world.dooberManager.getDooberCount() > 0)
            {
                TimerUtil.callLater(this.showUpgradeIntro, UPGRADE_INTRO_DELAY);
            }
            else if (!m_cancelTutorial)
            {
                Global.guide.notify(m_municipal.getItem().upgrade.introGuideNotify);
            }
            return;
        }//end

    }


