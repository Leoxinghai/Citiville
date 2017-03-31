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
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class TimedStreakMechanic implements IActionGameMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected Timer m_timer =null ;
        private  int TIMER_BUFFER =1000;

        public  TimedStreakMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public String  getPick ()
        {
            String _loc_1 ="supply";
            if (!this.needsToBeSupplied())
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

        public Array  getPicksToHide ()
        {
            _loc_1 = this.getPick ();
            if (_loc_1 == "supply")
            {
                return null;
            }
            return new Array("supply");
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Object _loc_7 =null ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            String _loc_5 ="none";
            if (param1 == "load")
            {
                this.relaunchTimers();
                _loc_5 = "load";
                _loc_3 = true;
            }
            else if (param1 == "sell")
            {
                _loc_7 = this.m_owner.getDataForMechanic(this.m_config.type);
                this.clearTimer(_loc_7);
                this.m_owner.setDataForMechanic(this.m_config.type, _loc_7, param1);
            }
            else if (param1 == "timerExpired")
            {
                this.updateInactive(param1);
                _loc_5 = "timerExpired";
                _loc_3 = true;
            }
            else if (this.needsToBeSupplied())
            {
                _loc_3 = this.tryToSupply(param1);
                _loc_5 = "supply";
            }
            else
            {
                this.displayInfographic();
            }
            Object _loc_6 ={operation action "streakData",};
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_6);
        }//end

        public void  displayInfographic ()
        {
            Class _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            _loc_1 = this.m_config.params.hasOwnProperty("infographicName")? (String(this.m_config.params.get("infographicName"))) : (null);
            if (_loc_1 != null)
            {
                _loc_2 =(Class) getDefinitionByName(_loc_1);
                _loc_3 = new _loc_2;
                UI.displayPopup(_loc_3, false, this.m_config.type, true);
            }
            return;
        }//end

        protected Object  getDefaultStreakData ()
        {
            Object _loc_1 ={activationTime inactiveTime -1,-streakLength 1,0,timer };
            return _loc_1;
        }//end

        protected void  processNegativeStreak (Object param1 )
        {
            _loc_2 = this.m_config.params.hasOwnProperty("maxStreakLength")? (-1 * Number(this.m_config.params.get("maxStreakLength"))) : (-1);
            if (param1.get("streakLength") > 0)
            {
                param1.put("streakLength",  0);
            }
            if (_loc_2 == -1 || param1.get("streakLength") > _loc_2)
            {
                (param1.get("streakLength") - 1);
            }
            if (this.m_owner instanceof IStreakHandler)
            {
                (this.m_owner as IStreakHandler).processNegativeStreak(param1.get("streakLength") * -1);
            }
            return;
        }//end

        protected void  processPositiveStreak (Object param1 )
        {
            _loc_2 = this.m_config.params.hasOwnProperty("maxStreakLength")? (Number(this.m_config.params.get("maxStreakLength"))) : (-1);
            if (param1.get("streakLength") < 0)
            {
                param1.put("streakLength",  0);
            }
            if (_loc_2 == -1 || param1.get("streakLength") < _loc_2)
            {
                (param1.get("streakLength") + 1);
            }
            if (this.m_owner instanceof IStreakHandler)
            {
                (this.m_owner as IStreakHandler).processPositiveStreak(param1.get("streakLength"));
            }
            return;
        }//end

        protected boolean  needsToBeSupplied ()
        {
            boolean _loc_1 =false ;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_3 = GlobalEngine.getTimer ()/1000;
            _loc_4 = this.m_config.params.hasOwnProperty("activeDuration")? (Number(this.m_config.params.get("activeDuration"))) : (0);
            if (_loc_2 == null || _loc_3 - _loc_2.get("activationTime") > _loc_4)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        protected void  relaunchTimers ()
        {
            double _loc_4 =0;
            double _loc_5 =0;
            _loc_1 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_2 = GlobalEngine.getTimer ()/1000;
            double _loc_3 =0;
            if (!this.needsToBeSupplied())
            {
                if (_loc_1 != null)
                {
                    _loc_4 = this.m_config.params.hasOwnProperty("activeDuration") ? (Number(this.m_config.params.get("activeDuration"))) : (0);
                    _loc_3 = _loc_4 - (_loc_2 - _loc_1.get("activationTime"));
                    if (_loc_3 > 0)
                    {
                        this.startTimer(_loc_3, _loc_1);
                        _loc_1.put("inactiveTime",  -1);
                    }
                    else
                    {
                        _loc_1.put("inactiveTime",  _loc_1.get("activationTime") + _loc_4);
                        _loc_1.put("activationTime",  -1);
                    }
                }
            }
            if (_loc_1 == null)
            {
                _loc_1 = this.getDefaultStreakData();
            }
            if (_loc_1.get("inactiveTime") >= 0)
            {
                _loc_5 = this.m_config.params.hasOwnProperty("inactiveDuration") ? (Number(this.m_config.params.get("inactiveDuration"))) : (0);
                _loc_3 = _loc_5 - (_loc_2 - _loc_1.get("inactiveTime"));
                while (_loc_3 < 0)
                {

                    this.processNegativeStreak(_loc_1);
                    _loc_3 = _loc_3 + _loc_5;
                }
                this.startTimer(_loc_3, _loc_1);
                _loc_1.put("inactiveTime",  _loc_2 - (_loc_5 - _loc_3));
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_1, "all");
            return;
        }//end

        protected void  updateInactive (String param1 )
        {
            double _loc_4 =0;
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_3 = GlobalEngine.getTimer ()/1000;
            if (_loc_2 == null)
            {
                _loc_2 = this.getDefaultStreakData();
            }
            if (_loc_2.get("inactiveTime") >= 0)
            {
                this.processNegativeStreak(_loc_2);
                _loc_2.put("inactiveTime",  -1);
            }
            if (_loc_2.get("inactiveTime") == -1)
            {
                _loc_2.put("inactiveTime",  _loc_3);
                _loc_2.put("activationTime",  -1);
                _loc_4 = this.m_config.params.hasOwnProperty("inactiveDuration") ? (Number(this.m_config.params.get("inactiveDuration"))) : (0);
                this.startTimer(_loc_4, _loc_2);
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_2, param1);
            return;
        }//end

        protected boolean  tryToSupply (String param1 )
        {
            String _loc_5 =null ;
            double _loc_6 =0;
            Object _loc_7 =null ;
            double _loc_8 =0;
            boolean _loc_2 =false ;
            _loc_3 = this.m_config.params.hasOwnProperty("consumableType")? (String(this.m_config.params.get("consumableType"))) : (null);
            _loc_4 = this.m_config.params.hasOwnProperty("consumableQuantity")? (int(this.m_config.params.get("consumableQuantity"))) : (0);
            if (_loc_3 == null || _loc_4 <= 0)
            {
                _loc_2 = true;
            }
            else if (Global.player.commodities.getCount(_loc_3) >= _loc_4)
            {
                _loc_2 = true;
                this.onSupplyConfirm(new GenericPopupEvent(GenericPopupEvent.SELECTED, GenericPopup.ACCEPT, false));
            }
            else
            {
                _loc_5 = null;
                _loc_5 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_4});
                this.m_owner.displayStatus(_loc_5, null, 16711680);
            }
            if (_loc_2)
            {
                _loc_6 = GlobalEngine.getTimer() / 1000;
                _loc_7 = this.m_owner.getDataForMechanic(this.m_config.type);
                if (_loc_7 == null)
                {
                    _loc_7 = this.getDefaultStreakData();
                }
                _loc_7.put("activationTime",  _loc_6);
                _loc_7.put("inactiveTime",  -1);
                _loc_8 = this.m_config.params.hasOwnProperty("activeDuration") ? (Number(this.m_config.params.get("activeDuration"))) : (0);
                this.clearTimer(_loc_7);
                this.startTimer(_loc_8, _loc_7);
                this.m_owner.setDataForMechanic(this.m_config.type, _loc_7, param1);
                this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.m_config.type, true, param1));
                this.processPositiveStreak(_loc_7);
            }
            return _loc_2;
        }//end

        protected void  startTimer (double param1 ,Object param2 )
        {
            timerLength = param1;
            streakData = param2;
            if (timerLength >= 0)
            {
                this.m_timer = new Timer(timerLength * 1000 + this.TIMER_BUFFER);
                this .m_timer .addEventListener (TimerEvent .TIMER ,void  (TimerEvent event )
            {
                m_timer.removeEventListener(TimerEvent.TIMER, arguments.callee);
                m_timer.stop();
                m_timer = null;
                onTimerFinish();
                return;
            }//end
            );
                this.m_timer.start();
                streakData.put("timer",  this.m_timer);
            }
            return;
        }//end

        protected void  clearTimer (Object param1 )
        {
            this.m_timer = param1.get("timer");
            param1.put("timer",  null);
            if (this.m_timer != null)
            {
                this.m_timer.removeEventListener(TimerEvent.TIMER, arguments.callee);
                this.m_timer.stop();
                this.m_timer = null;
            }
            return;
        }//end

        protected void  onTimerFinish ()
        {
            this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.m_config.type, true, "all"));
            MechanicManager.getInstance().handleAction(this.m_owner, "timerExpired", null);
            return;
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
                if (this.m_owner instanceof IStreakHandler)
                {
                    (this.m_owner as IStreakHandler).onPlayerStreakEvent(event);
                }
            }
            return;
        }//end

        protected void  processSupply ()
        {
            _loc_1 = this.m_config.params.hasOwnProperty("consumableType")? (String(this.m_config.params.get("consumableType"))) : (null);
            _loc_2 = this.m_config.params.hasOwnProperty("consumableQuantity")? (int(this.m_config.params.get("consumableQuantity"))) : (0);
            _loc_3 = _loc_2;
            String _loc_4 =null ;
            if (Global.player.commodities.remove(_loc_1, _loc_3))
            {
                (this.m_owner as MapResource).doSupplyDoobers(_loc_1, _loc_3);
                _loc_4 = ZLoc.t("Main", "StartingDelivery", {amount:_loc_3});
                this.m_owner.displayStatus(_loc_4);
                Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.STORED_ITEM_SUPPLY, null));
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, this.m_owner.getItem().type, "supply", "", "", "");
            }
            return;
        }//end

        public String  getToolTipStatus ()
        {
            return "";
        }//end

        public String  getToolTipAction ()
        {
            Item _loc_2 =null ;
            Object _loc_3 =null ;
            String _loc_1 =null ;
            if (this.m_owner && this.m_config && Global.player && Global.player.commodities)
            {
                _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type);
                _loc_2 = this.m_owner.getItem();
                if (_loc_2 && _loc_3 && _loc_3.get("inactiveTime") != -1)
                {
                    if (Global.player.commodities.getAddedCount(.get(this.m_config.get("params").get("consumableType"))) >= this.m_config.get("params").get("consumableQuantity"))
                    {
                        _loc_1 = ZLoc.t("Dialogs", "ClickToSupply") + "\n";
                    }
                    else
                    {
                        _loc_1 = "";
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

    }



