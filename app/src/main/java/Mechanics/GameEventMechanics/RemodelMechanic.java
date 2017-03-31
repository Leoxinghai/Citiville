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
import Classes.doobers.*;
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.remodel.*;
import com.zynga.skelly.util.*;

    public class RemodelMechanic implements IPurchaseMechanic, IGateMechanic, IToolTipModifier
    {
        protected IMechanicUser m_owner ;
        protected String m_type ;
        protected String m_incrementKey ;
        private static Array s_validGameEvents =.get( "GMPlay","GMRemodel","catalogPurchase","catalogConfirmation") ;
        public static  String ITEMNAME_KEY ="itemName";
        public static  String GATE_KEY ="gate";
        public static  String COMPLETE_KEY ="complete";

        public  RemodelMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return !(this.m_owner as GameObject).isLocked() && s_validGameEvents.indexOf(param1) != -1;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            _loc_3 = this.get(param1)(as Function ).apply(this ,param2 );
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.m_type = param2.type;
            this.m_incrementKey = param2.params.get("incrementKey");
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  doPurchase (UserResourceType param1 ,int param2 )
        {
            _loc_3 = UserResourceHelper.deductResource(param1,param2);
            return _loc_3;
        }//end

        public IGate  getGateInstance (String param1 ,Object param2 )
        {
            return (this.m_owner as MechanicMapResource).getMechanicGate(param1, param2);
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            IGate _loc_3 =null ;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_type );
            if (_loc_2 && _loc_2.hasOwnProperty(ITEMNAME_KEY) && _loc_2.hasOwnProperty(GATE_KEY))
            {
                _loc_3 = this.getGateInstanceForRemodelItemName(_loc_2.get(ITEMNAME_KEY));
                _loc_3.loadFromObject(_loc_2.get(GATE_KEY));
                if (_loc_3 instanceof IToolTipModifier)
                {
                    _loc_1 = ((IToolTipModifier)_loc_3).getToolTipAction();
                }
            }
            return _loc_1;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            IGate _loc_3 =null ;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_type );
            if (_loc_2 && _loc_2.hasOwnProperty(ITEMNAME_KEY) && _loc_2.hasOwnProperty(GATE_KEY))
            {
                _loc_3 = this.getGateInstanceForRemodelItemName(_loc_2.get(ITEMNAME_KEY));
                if (_loc_3 instanceof IToolTipModifier)
                {
                    _loc_1 = ((IToolTipModifier)_loc_3).getToolTipStatus();
                }
            }
            return _loc_1;
        }//end

        protected MechanicActionResult  GMPlay (...args )
        {
            RemodelDefinition _loc_7 =null ;
            Item _loc_8 =null ;
            String _loc_9 =null ;
            boolean _loc_10 =false ;
            boolean argsvalue =true ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            _loc_5 = this.m_owner.getDataForMechanic(this.m_type );
            _loc_6 =Global.gameSettings().getItemByName ((this.m_owner as ItemInstance ).getItemName ());
            if (Global.gameSettings().getItemByName((this.m_owner as ItemInstance).getItemName()) && _loc_5 && _loc_5.hasOwnProperty(ITEMNAME_KEY))
            {
                _loc_7 = _loc_6.getRemodelDefinitionByName(_loc_5.get(ITEMNAME_KEY));
                if (_loc_7)
                {
                    _loc_8 = Global.gameSettings().getItemByName(_loc_7.remodelItemName);
                    _loc_9 = _loc_7.gateName;
                    _loc_4 = true;
                    argsvalue = false;
                    if (PopulationUtil.canAddItemToWorld(_loc_8, true, (MapResource)this.m_owner))
                    {
                        _loc_10 = this.updateGate(_loc_9, true);
                        _loc_3 = _loc_10;
                    }
                }
            }
            return new MechanicActionResult(_loc_4, argsvalue, _loc_3);
        }//end

        protected MechanicActionResult  GMRemodel (...args )
        {
            return this.GMPlay();
        }//end

        protected MechanicActionResult  catalogPurchase (String param1 )
        {
            Item _loc_3 =null ;
            RemodelDefinition _loc_4 =null ;
            UserResourceType _loc_5 =null ;
            int _loc_6 =0;
            boolean _loc_2 =false ;
            if (RemodelManager.canPurchaseSkin((MapResource)this.m_owner, param1))
            {
                _loc_3 = Global.gameSettings().getItemByName((this.m_owner as ItemInstance).getItemName());
                if (_loc_3)
                {
                    _loc_4 = _loc_3.getRemodelDefinitionByName(param1);
                    if (_loc_4)
                    {
                        _loc_5 = _loc_4.getPurchaseType();
                        _loc_6 = _loc_4.getPurchaseAmount(_loc_5);
                        _loc_2 = this.doPurchase(_loc_5, _loc_6);
                        if (_loc_2)
                        {
                            this.startRemodel(_loc_4);
                        }
                    }
                }
            }
            return new MechanicActionResult(_loc_2, false, _loc_2, {itemName:param1});
        }//end

        protected MechanicActionResult  catalogConfirmation (String param1 ,Function param2 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_3 =(MapResource) this.m_owner;
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3 && _loc_4)
            {
                _loc_5 = _loc_3.getPopulationYield() - _loc_4.populationMax;
                _loc_6 = Global.world.citySim.getScaledPopulation() - Global.world.citySim.applyPopulationScale(_loc_5);
                if (_loc_5 > 0)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "RemodelPopWarning", {pop:_loc_6}), GenericDialogView.TYPE_YESNO, Curry.curry(this.onCatalogPurchaseConfirmation, param2, param1));
                }
                else
                {
                    this.onCatalogPurchaseConfirmation(param2, param1, null);
                }
            }
            return new MechanicActionResult(false, false, false);
        }//end

        protected void  onCatalogPurchaseConfirmation (Function param1 ,String param2 ,GenericPopupEvent param3 )
        {
            GenericDialog _loc_4 =null ;
            if (!param3 || param3.button == GenericDialogView.YES && param1 != null)
            {
                if (this.m_owner instanceof MapResource && ((MapResource)this.m_owner).getItemName() == param2)
                {
                    _loc_4 = new GenericDialog(ZLoc.t("Dialogs", "SkinAlreadyOwned"), "", GenericDialogView.TYPE_OK, null, "", "", true);
                    UI.displayPopup(_loc_4, false, "skin_already_owned", true);
                }
                else
                {
                    param1(this.m_owner, param2);
                }
            }
            return;
        }//end

        protected void  startRemodel (RemodelDefinition param1 )
        {
            Object _loc_2 ={};
            _loc_2.put(ITEMNAME_KEY,  param1.remodelItemName);
            _loc_3 = this.getGateInstance(param1.gateName ,{});
            _loc_2.put(GATE_KEY,  _loc_3.getData());
            _loc_2.put(COMPLETE_KEY,  false);
            this.m_owner.setDataForMechanic(this.m_type, _loc_2, MechanicManager.ALL);
            return;
        }//end

        protected boolean  updateGate (String param1 ,boolean param2 =false )
        {
            String _loc_6 =null ;
            _loc_3 = this.m_owner.getDataForMechanic(this.m_type );
            _loc_4 = this.getGateInstance(param1 );
            boolean _loc_5 =false ;
            if (_loc_3.hasOwnProperty(GATE_KEY))
            {
                _loc_4.loadFromObject(_loc_3.get(GATE_KEY));
            }
            else
            {
                _loc_4.loadFromObject(_loc_4.getData());
            }
            _loc_5 = UserResourceHelper.deductResource(UserResourceType.get(UserResourceType.ENERGY), 1);
            if (_loc_5)
            {
                if (this.m_incrementKey != null && this.m_incrementKey != "")
                {
                    _loc_4.incrementKey(this.m_incrementKey);
                    if (this.m_owner instanceof MapResource)
                    {
                        ((MapResource)this.m_owner).doResourceChanges(-1, 0, 0, 0, "", true, false);
                    }
                }
            }
            if (_loc_4.unlockGate())
            {
                _loc_6 = _loc_3.get(ITEMNAME_KEY);
                _loc_3 = new Object();
                _loc_3.put(COMPLETE_KEY,  true);
                this.m_owner.setDataForMechanic(this.m_type, _loc_3, MechanicManager.ALL);
                (this.m_owner as MechanicMapResource).clearMechanicGate();
                this.finishRemodel(_loc_6);
            }
            else
            {
                _loc_3.put(GATE_KEY,  _loc_4.getData());
                this.m_owner.setDataForMechanic(this.m_type, _loc_3, MechanicManager.ALL);
            }
            return _loc_5;
        }//end

        protected void  finishRemodel (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            ((MapResource)this.m_owner).switchItem(_loc_2);
            this.spawnFinishDoobers(_loc_2);
            ((MapResource)this.m_owner).recomputePopulation();
            Global.world.citySim.recomputePopulationCap(Global.world);
            Global.world.citySim.recomputePopulation(Global.world);
            Global.world.citySim.recomputePotentialPopulation(Global.world);
            return;
        }//end

        protected void  spawnFinishDoobers (Item param1 )
        {
            int _loc_2 =0;
            Array _loc_3 =null ;
            if (param1 && param1.remodelXp > 0)
            {
                _loc_2 = param1.remodelXp;
                if (this.m_owner instanceof MapResource)
                {
                    _loc_3 = new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_2), _loc_2);
                    ((MapResource)this.m_owner).doobersArray = new Array(_loc_3);
                    ((MapResource)this.m_owner).spawnDoobers();
                }
            }
            return;
        }//end

        private IGate  getGateInstanceForRemodelItemName (String param1 )
        {
            _loc_2 = this(.m_owner as ItemInstance ).getItem ().getRemodelDefinitionByName(param1 );
            if (_loc_2)
            {
                return this.getGateInstance(_loc_2.gateName);
            }
            return null;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



