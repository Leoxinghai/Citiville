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
import Classes.actions.*;
import Display.*;
import Display.PopulationUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;

    public class BaseSupplyMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gameEvent ="";

        public  BaseSupplyMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return !this.m_owner.isNeedingRoad && !this.hasHarvestState() && !this.m_owner.isLocked();
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            Object _loc_4 ={operation "supply"};
            this.m_gameEvent = param1;
            boolean _loc_5 =false ;
            if (this.m_config.params.get("blockOthers") == "true")
            {
                _loc_5 = true;
            }
            _loc_6 = this.m_owner.getDataForMechanic(this.m_config.params.get( "harvestStateName") );
            _loc_7 = this.getRequiredCommodity ();
            String _loc_8 =null ;
            if (Global.player.commodities.getCount("goods") >= _loc_7)
            {
                _loc_3 = true;
                if (this.m_config.params.hasOwnProperty("showConfirm") && this.m_config.params.get("showConfirm") == "true")
                {
                    _loc_8 = ZLoc.t("Dialogs", "SupplyConfirmMechanic", {building:this.m_owner.getItemFriendlyName(), cost:_loc_7});
                    UI.displayMessage(_loc_8, GenericPopup.TYPE_YESNO, this.onSupplyConfirm, "", true);
                }
                else
                {
                    this.onSupplyConfirm(new GenericPopupEvent(GenericPopupEvent.SELECTED, GenericPopup.ACCEPT, false));
                }
            }
            else if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SUPPLY_POPUP) > 0)
            {
                ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_GOODS, true);
            }
            else
            {
                _loc_8 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_7});
                this.m_owner.setDataForMechanic(this.m_config.params.get("supplyStateName"), _loc_6, param1);
                this.m_owner.displayStatus(_loc_8, null, 16711680);
            }
            return new MechanicActionResult(_loc_3, !_loc_5, false, _loc_4);
        }//end

        protected void  onSupplyConfirm (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.ACCEPT)
            {
                if (this.m_config.params.hasOwnProperty("showProgressBar") && this.m_config.params.get("showProgressBar") == "true")
                {
                    this.m_owner.actionQueue.addActions(new ActionFunctionProgressBar(this.m_owner, null, this.processSupply, null, ZLoc.t("Main", "Supplying")));
                }
                else
                {
                    this.processSupply();
                }
            }
            return;
        }//end

        protected void  processSupply ()
        {
            _loc_1 = this.m_owner.getDataForMechanic(this.m_config.params.get( "harvestStateName") );
            _loc_2 = this.getRequiredCommodity ();
            _loc_3 = this.getCustomerCapacity ();
            String _loc_4 =null ;
            if (Global.player.commodities.remove("goods", _loc_2))
            {
                (this.m_owner as MapResource).doSupplyDoobers("goods", _loc_2);
                _loc_1 = this.generateHarvestState(_loc_2, _loc_3);
                _loc_4 = ZLoc.t("Main", "StartingDelivery", {amount:_loc_2});
                this.m_owner.displayStatus(_loc_4);
                this.m_owner.setDataForMechanic(this.m_config.params.get("harvestStateName"), _loc_1, this.m_gameEvent);
                TransactionManager.addTransaction(new TMechanicAction(this.m_owner, this.m_config.type, this.m_gameEvent, {operation:"supply"}));
                this.m_owner.trackAction(TrackedActionType.SUPPLY);
                if (this.m_owner instanceof IMerchant)
                {
                    (this.m_owner as IMerchant).updatePeepSpawning();
                }
                Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.STORED_ITEM_SUPPLY, null));
                this.awardUpgradeActions();
                MechanicManager.getInstance().handleAction(this.m_owner, "supply", null);
            }
            else
            {
                _loc_4 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_2});
                this.m_owner.setDataForMechanic(this.m_config.params.get("supplyStateName"), _loc_1, this.m_gameEvent);
                this.m_owner.displayStatus(_loc_4, null, 16711680);
            }
            return;
        }//end

        protected boolean  hasHarvestState ()
        {
            _loc_1 = this.m_owner.getDataForMechanic(this.m_config.params.get( "harvestStateName") );
            if (_loc_1)
            {
                return true;
            }
            return false;
        }//end

        protected void  awardUpgradeActions ()
        {
            if (this.m_owner.canCountUpgradeActions())
            {
                this.m_owner.incrementUpgradeActionCount(false);
            }
            return;
        }//end

        protected Object  generateHarvestState (int param1 ,int param2 )
        {
            throw new Error("Must override generateHarvestState in children! Do not attempt to instantiate BaseSupplyMechanic!");
        }//end

        protected int  getRequiredCommodity ()
        {
            _loc_1 = this.m_owner.getItem ();
            return _loc_1.commodityReq;
        }//end

        protected int  getCustomerCapacity ()
        {
            _loc_1 = this.m_owner.getItem ();
            return _loc_1.customerCapacity > 0 ? (_loc_1.customerCapacity) : (this.getRequiredCommodity());
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



