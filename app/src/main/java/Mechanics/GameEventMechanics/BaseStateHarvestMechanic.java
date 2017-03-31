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
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class BaseStateHarvestMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gameEvent ="all";
        protected Array m_doobers ;

        public  BaseStateHarvestMechanic ()
        {
            this.m_doobers = new Array();
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
            this.m_gameEvent = param1;
            _loc_2 = this.m_owner.isNeedingRoad ;
            _loc_3 = this.isHarvestReady ();
            _loc_4 = this.m_owner.isLocked ();
            return !_loc_2 && _loc_3 && !_loc_4;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (this.m_config.params.get("blockOthers") == "true")
            {
                _loc_4 = true;
            }
            if (Global.player.checkEnergy(-1 * this.getRequiredEnergy()))
            {
                _loc_3 = true;
                if (this.m_config.params.hasOwnProperty("showConfirm") && this.m_config.params.get("showConfirm") == "true")
                {
                    _loc_6 = this.m_config.params.hasOwnProperty("showConfirmText") ? (this.m_config.params.get("showConfirmText")) : ("HarvestConfirmMechanic");
                    _loc_5 = ZLoc.t("Dialogs", _loc_6, {building:this.m_owner.getItemFriendlyName(), cost:this.getRequiredEnergy()});
                    UI.displayMessage(_loc_5, GenericPopup.TYPE_YESNO, this.onHarvestConfirm, "", true);
                }
                else
                {
                    this.onHarvestConfirm(new GenericPopupEvent(GenericPopupEvent.SELECTED, GenericPopup.ACCEPT, false));
                }
            }
            else
            {
                _loc_5 = ZLoc.t("Main", "NotEnoughEnergyRed");
                this.m_owner.displayStatus(_loc_5, null, 16711680);
            }
            return new MechanicActionResult(_loc_3, !_loc_4, false, null);
        }//end

        protected void  onHarvestConfirm (GenericPopupEvent event )
        {
            String _loc_2 =null ;
            if (event.button == GenericPopup.ACCEPT)
            {
                this.preProgressHarvest();
                if (this.m_config.params.hasOwnProperty("showProgressBar") && this.m_config.params.get("showProgressBar") == "true")
                {
                    _loc_2 = this.m_config.params.hasOwnProperty("progressBarText") ? (ZLoc.t("Main", this.m_config.params.get("progressBarText"))) : (ZLoc.t("Main", "Harvesting"));
                    this.m_owner.actionQueue.addActions(new ActionFunctionProgressBar(this.m_owner, null, this.processHarvest, null, _loc_2));
                }
                else
                {
                    this.processHarvest();
                }
            }
            return;
        }//end

        protected void  preProgressHarvest ()
        {
            return;
        }//end

        protected void  processHarvest ()
        {
            Item _loc_2 =null ;
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            _loc_1 = this.getRequiredEnergy ();
            if (Global.player.updateEnergy(-1 * _loc_1, this.getTrackingArray()))
            {
                this.preHarvest();
                _loc_2 = this.m_owner.getItem();
                _loc_3 = Global.player.processRandomModifiers(_loc_2, this.m_owner, true, []);
                _loc_4 = this.applyPayoutMultipliers(_loc_2, _loc_3);
                this.m_doobers = this.m_doobers.concat(_loc_4);
                Global.world.dooberManager.createBatchDoobers(this.m_doobers, this.m_owner.getItem(), this.m_owner.positionX, this.m_owner.positionY, false, this.onDoobersCollected);
                this.m_owner.setDataForMechanic(this.m_config.type, null, this.m_gameEvent);
                TransactionManager.addTransaction(new TMechanicAction(this.m_owner, this.m_config.type, this.m_gameEvent, {operation:"harvest"}));
                this.postHarvest();
                _loc_5 = Wonder.itemBonusFlyout(this.m_owner);
                if (_loc_5 != null)
                {
                    this.m_owner.displayStatus(_loc_5, null, Wonder.wonderTextColor);
                }
            }
            else
            {
                _loc_6 = ZLoc.t("Main", "NotEnoughEnergyRed");
                this.m_owner.displayStatus(_loc_6, null, 16711680);
            }
            return;
        }//end

        protected Array  applyPayoutMultipliers (Item param1 ,Array param2 )
        {
            throw new Error("Must override getPayoutMultipliers in children! Do not attempt to instantiate BaseStateHarvestMechanic!");
        }//end

        protected void  preHarvest ()
        {
            this.m_doobers = new Array();
            return;
        }//end

        protected void  postHarvest ()
        {
            if (this.m_config.params.get("upgradeActionMechanic") && this.m_config.params.get("upgradeActionMechanic") == "true")
            {
                this.m_owner.incrementUpgradeActionCount(false);
            }
            MechanicManager.getInstance().handleAction(this.m_owner, "harvest", null);
            return;
        }//end

        protected void  onDoobersCollected ()
        {
            this.m_doobers = new Array();
            return;
        }//end

        protected Array  getTrackingArray ()
        {
            Array _loc_1 =new Array();
            return _loc_1;
        }//end

        public boolean  isHarvestReady ()
        {
            throw new Error("Must override isHarvestReady in children! Do not attempt to instantiate BaseStateHarvestMechanic!");
        }//end

        protected int  getRequiredEnergy ()
        {
            _loc_1 = this.m_owner.getItem ();
            return _loc_1.harvestEnergyCost;
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



