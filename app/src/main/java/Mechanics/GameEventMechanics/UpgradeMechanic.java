package Mechanics.GameEventMechanics;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.stats.types.*;

    public class UpgradeMechanic implements IUpgradeMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gateName ;
        protected IGate m_gate ;
        protected GateFactory m_gateFactory ;
        protected String m_gateTitlePackage ;
        protected String m_gateTitleKey ;
        protected String m_gateTitleItemToken ;
        protected boolean m_repopGate ;

        public  UpgradeMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            String _loc_4 =null ;
            boolean _loc_2 =false ;
            boolean _loc_3 =false ;
            if (this.m_owner.isUpgradePossible())
            {
                if (this.m_config.params.get("conditionalDisplay") != "true")
                {
                    _loc_3 = true;
                }
                if (this.m_gate && this.m_gate.checkForKeys())
                {
                    _loc_3 = true;
                    _loc_2 = true;
                }
            }
            if (_loc_3)
            {
                _loc_4 = Item.UPGRADE_STATE_GATED;
                if (_loc_2)
                {
                    _loc_4 = Item.UPGRADE_STATE_READY;
                }
                this.m_owner.upgradeState = _loc_4;
            }
            return _loc_3;
        }//end

        protected void  preUpgradeForGameAction (String param1 ,Array param2 )
        {
            return;
        }//end

        protected void  postUpgradeForGameAction (String param1 ,Array param2 )
        {
            return;
        }//end

        public void  performUpgrade ()
        {
            this.onUpgradeSuccess();
            return;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (this.m_owner.isUpgradePossible())
            {
                if (this.m_config.params.get("blockOthers") == "true")
                {
                    _loc_4 = true;
                }
                this.preUpgradeForGameAction(param1, param2);
                if (!this.doDisplayGate())
                {
                    this.performUpgrade();
                }
                _loc_3 = true;
                this.postUpgradeForGameAction(param1, param2);
            }
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_5);
        }//end

        public boolean  doDisplayGate ()
        {
            String _loc_2 =null ;
            Object _loc_3 =null ;
            boolean _loc_1 =false ;
            if (this.m_owner.isUpgradePossible())
            {
                if (this.m_gate)
                {
                    _loc_2 = null;
                    if (this.m_gateTitlePackage && this.m_gateTitleKey && this.m_gateTitleItemToken)
                    {
                        _loc_3 = {};
                        _loc_3.put(this.m_gateTitleItemToken,  this.m_owner.getItem().localizedName);
                        _loc_2 = ZLoc.t(this.m_gateTitlePackage, this.m_gateTitleKey, _loc_3);
                    }
                    this.m_gate.displayGate("upgrade_", _loc_2, this.getExtraDialogParams());
                    _loc_1 = true;
                }
            }
            return _loc_1;
        }//end

        public Object  getExtraDialogParams ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put("dialogClass",  this.m_config.params.get("dialogClass"));
            _loc_1.put("dialogClassStringKey",  this.m_config.params.get("dialogClassStringKey"));
            _loc_1.put("dialogClassQuestTimerSource",  this.m_config.params.get("dialogClassQuestTimerSource"));
            _loc_1.put("checkDuplicates",  this.m_config.params.get("checkDuplicates"));
            return _loc_1;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_gateFactory = new GateFactory();
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            this.m_gateName = this.m_config.params.get("gateName");
            this.m_gateTitlePackage = this.m_config.params.get("gateTitlePackage");
            this.m_gateTitleKey = this.m_config.params.get("gateTitleKey");
            this.m_gateTitleItemToken = this.m_config.params.get("gateTitleItemToken");
            this.m_gate = this.m_gateFactory.loadGateFromXML(this.m_owner.getItem(), this.m_owner, this.m_gateName, this.onUpgradeSuccess);
            this.m_repopGate = String(this.m_config.params.get("repopGate")) == "true";
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        private void  onUpgradeSuccess ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            CharacterDialog _loc_3 =null ;
            if (this.m_owner.isUpgradePossible())
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, this.m_owner.getItemName(), "upgrade", GameUtil.trimBadStatsCharacters(this.m_owner.getItemName()));
                if (this.m_config.params.get("upgradeFinishText"))
                {
                    _loc_1 = ZLoc.t("Dialogs", this.m_config.params.get("upgradeFinishText"), {level:(this.m_owner.getItem().level + 1)});
                    _loc_2 = this.m_config.params.get("upgradeFinishIcon") ? (this.m_config.params.get("upgradeFinishIcon")) : (null);
                    _loc_3 = new CharacterDialog(_loc_1, "", 0, this.finalizeUpgrade, this.finalizeUpgrade, true, _loc_2);
                    UI.displayPopup(_loc_3);
                }
                else
                {
                    this.finalizeUpgrade();
                }
            }
            return;
        }//end

        protected void  finalizeUpgrade (GenericPopupEvent event =null )
        {
            this.m_owner.upgradeBuildingIfPossible();
            _loc_2 = this.m_config.params.get( "effect") ;
            if (_loc_2 && _loc_2 != "")
            {
                this.doUpgradeEffect(_loc_2);
            }
            if (this.m_repopGate)
            {
                this.initialize(this.m_owner, this.m_config);
                this.doDisplayGate();
            }
            return;
        }//end

        private void  checkForKeysCallback (boolean param1 )
        {
            return;
        }//end

        public void  doUpgradeEffect (String param1 ="")
        {
            _loc_2 = (int)(this.m_config.params.get( "numEffects") )? (int(this.m_config.params.get("numEffects"))) : (0);
            _loc_3 = (Int)(this.m_config.params.get( "effectInterval") )? (int(this.m_config.params.get("effectInterval"))) : (100);
            MapResourceEffectFactory.createEffect(this.m_owner, EffectType.FIREWORK_BALLOONS);
            Sounds.play("cruise_fireworks");
            int _loc_4 =0;
            while (_loc_4 <= _loc_2)
            {

                this.makeFireworksHelper(_loc_4, _loc_2, _loc_3);
                _loc_4++;
            }
            return;
        }//end

        private void  makeFireworksHelper (int param1 ,int param2 ,int param3 )
        {
            effectsInstantiated = param1;
            numEffect = param2;
            interval = param3;
            TimerUtil .callLater (void  ()
            {
                if (m_owner.upgradeEffectsFinishCallback != null && effectsInstantiated >= numEffect)
                {
                    MapResourceEffectFactory.createEffect(m_owner, EffectType.FIREWORK_BALLOONS, m_owner.upgradeEffectsFinishCallback);
                }
                else
                {
                    MapResourceEffectFactory.createEffect(m_owner, EffectType.FIREWORK_BALLOONS);
                }
                return;
            }//end
            , effectsInstantiated * interval);
            return;
        }//end

        public String  getPick ()
        {
            _loc_1 = this.m_config.params.get( "pick") ;
            if (this.m_config.params.get("pick2"))
            {
                if (this.m_gate && this.m_gate.checkForKeys())
                {
                    _loc_1 = this.m_config.params.get("pick2");
                }
            }
            return _loc_1;
        }//end

        public Array  getPicksToHide ()
        {
            Array _loc_1 =null ;
            if (this.m_config.params.get("pick2"))
            {
                if (this.m_gate && this.m_gate.checkForKeys())
                {
                    _loc_1 = .get(this.m_config.params.get("pick"));
                }
                else
                {
                    _loc_1 = .get(this.m_config.params.get("pick2"));
                }
            }
            return _loc_1;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            if (this.m_gate && this.m_gate.checkForKeys())
            {
                return ZLoc.t("Dialogs", "FinishUpgradeToolTip");
            }
            return ZLoc.t("Dialogs", "UpgradeToolTip");
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



