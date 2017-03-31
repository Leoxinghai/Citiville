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
import Classes.effects.*;
import Classes.sim.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.utils.*;

    public class MerchantCustomerMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected boolean m_displayMilestoneText =false ;
        private  Array MILESTONES =.get(10 ,20,30,40,50) ;
public static Timer m_transactionTimer ;
public static Dictionary m_transactionList =new Dictionary ();

        public  MerchantCustomerMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return param1 == PeepActionSelection.GAME_EVENT && !Global.isVisiting() && !Global.isTransitioningWorld;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            boolean _loc_10 =false ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            _loc_5 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_6 =(Peep) param2.get(0);
            if ((this.m_owner as IMerchant).isRouteable() && _loc_5 && _loc_5.customersReq && _loc_5.customers < _loc_5.customersReq)
            {
                _loc_7 = NPCUtil.getNPCValue(_loc_6.spawnSource, this.m_owner.getItem().type, _loc_6);
                _loc_8 = _loc_5.customers;
                _loc_5.customers = _loc_5.customers + _loc_7;
                _loc_5.customers = Math.min(_loc_5.customers, _loc_5.customersReq);
                if (this.m_displayMilestoneText)
                {
                    this.handleDisplayMilestoneText(_loc_8, _loc_5.customers);
                }
                if (this.m_config.params.hasOwnProperty("trackCustomerSources") && this.m_config.params.get("trackCustomerSources") == "true")
                {
                    if (!_loc_5.hasOwnProperty("customerSources"))
                    {
                        _loc_5.customerSources = new Dictionary();
                    }
                    if (!_loc_5.customerSources.hasOwnProperty(_loc_6.spawnSource))
                    {
                        _loc_5.customerSources.put(_loc_6.spawnSource,  0);
                    }
                    _loc_5.customerSources.put(_loc_6.spawnSource,  _loc_5.customerSources.get(_loc_6.spawnSource) + _loc_7);
                }
                _loc_9 = 0;
                while (_loc_9 < _loc_7)
                {

                    this.m_owner.addAnimatedEffect(EffectType.COIN);
                    _loc_9++;
                }
                _loc_3 = true;
                _loc_4 = true;
                _loc_10 = _loc_5.customers >= _loc_5.customersReq;
                Global.world.citySim.businessVisitBatchManager.addMechanicAction(this.m_owner, this.m_config.type, param1, _loc_6.source, 1, _loc_6, _loc_10);
                if (this.m_owner instanceof IMerchant)
                {
                    (this.m_owner as IMerchant).crowdManager.makeNpcEnterMerchant(_loc_6);
                }
                else
                {
                    _loc_6.actionQueue.addActions(new ActionNavigateHotspots(_loc_6, this.m_owner), new ActionDie(_loc_6));
                }
            }
            else
            {
                if (_loc_5 && _loc_5.customersReq && _loc_5.customers >= _loc_5.customersReq)
                {
                    _loc_5.customers = Math.min(_loc_5.customers, _loc_5.customersReq);
                    Global.world.citySim.businessVisitBatchManager.onQueueForceAction();
                }
                if ((this.m_owner as IMerchant).isRouteable() == false)
                {
                    if (this.m_owner instanceof IMerchant)
                    {
                        (this.m_owner as IMerchant).crowdManager.makeNpcFailEnter(_loc_6);
                    }
                    else
                    {
                        _loc_6.getStateMachine().removeAllStates();
                        _loc_6.getStateMachine().addActions(new ActionEnableFreedom(_loc_6, false));
                    }
                }
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_5, param1);
            return new MechanicActionResult(_loc_3, _loc_4, false, {operation:"process", count:_loc_7});
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            this.m_displayMilestoneText = this.m_config.params.hasOwnProperty("displayMilestoneText") && this.m_config.params.get("displayMilestoneText") == "true";
            return;
        }//end

        protected void  handleDisplayMilestoneText (int param1 ,int param2 )
        {
            _loc_3 = param2;
            while (_loc_3 > param1)
            {

                if (this.MILESTONES.indexOf(_loc_3) != -1 || _loc_3 % 50 == 0)
                {
                    this.m_owner.displayStatus(ZLoc.t("Main", "CustomersNum", {num:_loc_3}), new EmbeddedArt.smallHappyIcon());
                }
                _loc_3 = _loc_3 - 1;
            }
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



