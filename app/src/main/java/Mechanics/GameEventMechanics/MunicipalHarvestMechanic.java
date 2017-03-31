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
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Transactions.*;

    public class MunicipalHarvestMechanic implements IActionGameMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected Municipal m_owner ;
        protected MechanicConfigData m_config ;

        public  MunicipalHarvestMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_owner)
            {
                _loc_2 = !Global.isVisiting() && this.m_owner.getState() == HarvestableResource.STATE_GROWN;
            }
            return _loc_2;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Flag _loc_7 =null ;
            int _loc_8 =0;
            double _loc_9 =0;
            boolean _loc_3 =false ;
            _loc_4 = this.hasOverrideForGameAction(param1 );
            boolean _loc_5 =false ;
            _loc_6 = this.m_config.params.get( "countflag") ;
            if (this.m_config.params.get("countflag") && _loc_6 != "")
            {
                _loc_7 = Global.player.getFlag(_loc_6);
                _loc_8 = 0;
                if (_loc_7)
                {
                    _loc_8 = _loc_7.m_value;
                }
                _loc_8++;
                GameTransactionManager.addTransaction(new TFlag(_loc_6, _loc_8));
            }
            if (_loc_4)
            {
                if (this.m_config.params.get("blockOthers") == "true")
                {
                    _loc_5 = true;
                }
                _loc_9 = this.m_owner.getItem().harvestEnergyCost;
                if (!Global.player.checkEnergy(-_loc_9))
                {
                    this.m_owner.displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
                else
                {
                    this.m_owner.doEnergyChanges(-_loc_9, new Array("energy", "expenditures", "collect_municipal", this.m_owner.getItem().name));
                    Global.player.heldEnergy = Global.player.heldEnergy + _loc_9;
                    this.m_owner.heldEnergy = _loc_9;
                    Global.world.citySim.pickupManager.enqueue("NPC_mailman", this.m_owner);
                }
            }
            return new MechanicActionResult(_loc_3, !_loc_5, false, null);
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(Municipal) param1;
            this.m_config = param2;
            return;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Main","BusinessIsHarvestableNoAmount");
            return _loc_1;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



