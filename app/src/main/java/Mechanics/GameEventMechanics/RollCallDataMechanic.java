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
import Mechanics.Transactions.*;
import Modules.crew.*;

    public class RollCallDataMechanic implements IActionGameMechanic
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;
        private String m_state ;
        private  String MECHANIC_TYPE ="rollCall";
        private  String TIMESTAMP_PREFIX ="rollCall_";
        private  double SECONDS_PER_HOUR =3600;
        private  double MILLIS_PER_HOUR =3600000;
        protected String m_gameEvent ="";
        public static  String STATE_VIRGIN ="virgin";
        public static  String STATE_IN_PROGRESS ="in_progress";
        public static  String STATE_COMPLETE ="complete";
        public static  String STATE_FINISHED ="finished";

        public  RollCallDataMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            this.m_gameEvent = param1;
            return this.get(param1) instanceof Function;
        }//end

        public MechanicMapResource  owner ()
        {
            return this.m_owner;
        }//end

        public int  requiredLevel ()
        {
            return int(this.m_config.params.get("requiredLevel"));
        }//end

        public int  skipCost ()
        {
            return int(this.m_config.params.get("skipPrice"));
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            this.m_gameEvent = param1;
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true );
            if (this.get(param1) instanceof Function)
            {
                _loc_3 = this.get(param1).apply(this, param2);
            }
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public int  getStartTime ()
        {
            _loc_1 = this.getData ();
            if (_loc_1.get("state") == STATE_IN_PROGRESS)
            {
                return _loc_1.get("stateChangeTime");
            }
            return -1;
        }//end

        public boolean  setFinished ()
        {
            _loc_1 = this.getData ();
            if (_loc_1.get("state") == STATE_COMPLETE)
            {
                _loc_1.put("state",  STATE_FINISHED);
                this.sendTransaction("finishEverything");
                return true;
            }
            return false;
        }//end

        public boolean  checkIn (String param1 ,boolean param2 =false )
        {
            boolean _loc_4 =false ;
            int _loc_5 =0;
            _loc_3 = this.getData ();
            if (this.canCheckInHelper(param1, _loc_3))
            {
                if (param2 == true)
                {
                    _loc_5 = this.skipCost;
                    if (Global.player.cash < _loc_5)
                    {
                        return false;
                    }
                    Global.player.cash = Global.player.cash - _loc_5;
                }
                _loc_4 = this.addCheckinData(_loc_3, param1, param2);
                if (_loc_4 == true)
                {
                    this.sendTransaction("checkIn", param1, param2);
                }
                return _loc_4;
            }
            return false;
        }//end

        public boolean  collect (String param1 ,boolean param2 =true )
        {
            boolean _loc_4 =false ;
            _loc_3 = this.getData ();
            if (this.canCollectHelper(param1, _loc_3))
            {
                _loc_4 = this.addCollectData(_loc_3, param1);
                if (_loc_4 == true && param2)
                {
                    this.sendTransaction("collect", param1);
                }
                return _loc_4;
            }
            return false;
        }//end

        public boolean  feedSent (String param1 )
        {
            boolean _loc_3 =false ;
            _loc_2 = this.getData ();
            if (this.canSendFeed(param1, _loc_2))
            {
                _loc_3 = this.addFeedSendData(_loc_2, param1);
                if (_loc_3 == true)
                {
                    this.sendTransaction("feedSent", param1);
                }
                return _loc_3;
            }
            return false;
        }//end

        public boolean  startRollCall ()
        {
            _loc_1 = this.getData ();
            if (this.canPerformRollCall())
            {
                _loc_1 = this.initData();
                _loc_1.put("state",  STATE_IN_PROGRESS);
                _loc_1.put("stateChangeTime",  uint(GlobalEngine.getTimer() / 1000));
                this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, _loc_1, this.m_gameEvent);
                this.sendTransaction("startRollCall");
                this.setGlobalRollCallCooldownTimestamp();
                return true;
            }
            return false;
        }//end

        public boolean  canCollect (String param1 )
        {
            _loc_2 = this.getData ();
            return this.canCollectHelper(param1, _loc_2);
        }//end

        public double  getRollCallTimeLeft ()
        {
            _loc_1 = this.getData ();
            return this.getRollCallTimeLeftHelper(_loc_1);
        }//end

        public double  getTimeLeftToCollect ()
        {
            _loc_1 = this.getData ();
            return this.getTimeLeftToCollectHelper(_loc_1);
        }//end

        public boolean  isRollCallComplete ()
        {
            _loc_1 = this.getData ();
            if (_loc_1.get("state") == STATE_COMPLETE)
            {
                return true;
            }
            return _loc_1.get("state") == STATE_IN_PROGRESS && this.getRollCallTimeLeftHelper(_loc_1) <= 0;
        }//end

        public String  getState ()
        {
            _loc_1 = this.getData ();
            return _loc_1.get("state");
        }//end

        public boolean  canPerformRollCall ()
        {
            _loc_1 = this.getData ();
            _loc_2 = _loc_1.get("state") ==STATE_VIRGIN || this.isCooldownComplete(_loc_1);
            _loc_3 =Global.isVisiting ()|| Global.player.level >= this.requiredLevel;
            _loc_4 = this.isGlobalCooldownComplete ();
            return _loc_2 && _loc_3 && _loc_4;
        }//end

        public Array  getCrewList ()
        {
            String _loc_3 =null ;
            Array _loc_1 =new Array ();
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
                {
                		_loc_3 = _loc_2.list.get(i0);

                    _loc_1.push(_loc_3);
                }
            }
            return _loc_1;
        }//end

        public Array  getCrewState ()
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            Object _loc_8 =null ;
            Object _loc_1 ={};
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            _loc_3 = this.getData ();
            Array _loc_4 =new Array ();
            int _loc_5 =1;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
                {
                		_loc_6 = _loc_2.list.get(i0);

                    _loc_7 = "i" + _loc_6;
                    _loc_8 = new Object();
                    if (_loc_3.get(_loc_7))
                    {
                        _loc_8.put("position",  this.m_owner.getCrewPositionName(_loc_6));
                        _loc_8.put("zid",  _loc_6);
                        _loc_8.put("checkedIn",  _loc_3.get(_loc_7).get("checkedIn"));
                        _loc_8.put("collected",  _loc_3.get(_loc_7).get("collected"));
                        _loc_8.put("purchasedCheckIn",  _loc_3.get(_loc_7).get("purchasedCheckIn"));
                        _loc_8.put("count",  _loc_3.get(_loc_7).get("count"));
                        _loc_8.put("feedSent",  _loc_3.get(_loc_7).get("feedSent"));
                        _loc_8.put("orderId",  _loc_5);
                        _loc_5++;
                        _loc_4.push(_loc_8);
                    }
                }
            }
            return _loc_4;
        }//end

        public boolean  canCheckIn (String param1 )
        {
            _loc_2 = this.getData ();
            return this.canCheckInHelper(param1, _loc_2);
        }//end

        public boolean  hasCollected (String param1 )
        {
            _loc_2 = this.getData ();
            return this.hasCollectedHelper(param1, _loc_2);
        }//end

        private boolean  isGlobalCooldownComplete ()
        {
            _loc_1 = this.m_config.params.get( "category") ;
            if (Global.isVisiting() == true)
            {
                return true;
            }
            _loc_2 = this.m_config.params.get( "name") ;
            _loc_3 = this.getData ();
            if (!_loc_2 || !_loc_3.get("name") || _loc_3.get("name") != _loc_2)
            {
                return true;
            }
            _loc_4 =Global.player.getLastActivationTime(this.TIMESTAMP_PREFIX +_loc_1 );
            if (_loc_3.get("state") == STATE_VIRGIN || _loc_4 < 0)
            {
                return true;
            }
            _loc_5 =(double)(this.m_config.params.get( "cooldown") )*this.SECONDS_PER_HOUR ;
            _loc_6 = int(GlobalEngine.getTimer()/1000);
            return uint(GlobalEngine.getTimer() / 1000) > _loc_4 + _loc_5;
        }//end

        private void  setGlobalRollCallCooldownTimestamp ()
        {
            _loc_1 = this.m_config.params.get( "category") ;
            _loc_2 = (int)(GlobalEngine.getTimer ()/1000);
            Global.player.setLastActivationTime(this.TIMESTAMP_PREFIX + _loc_1, _loc_2);
            return;
        }//end

        private boolean  isCitySamZid (String param1 )
        {
            return param1.charAt(0) == "-";
        }//end

        private double  getRollCallTimeLeftHelper (Object param1 )
        {
            if (param1.get("state") != STATE_IN_PROGRESS)
            {
                return -1;
            }
            _loc_2 =(Number) param1.get("stateChangeTime");
            _loc_3 =(double)(this.m_config.params.get( "rollCallTimeLimit") )*this.SECONDS_PER_HOUR ;
            _loc_4 = int(GlobalEngine.getTimer()/1000);
            return _loc_2 + _loc_3 - _loc_4;
        }//end

        private double  getTimeLeftToCollectHelper (Object param1 )
        {
            if (param1.get("state") != STATE_COMPLETE)
            {
                return -1;
            }
            _loc_2 =(Number) param1.get("stateChangeTime");
            _loc_3 =(double)(this.m_config.params.get( "collectionTimeLimit") )*this.SECONDS_PER_HOUR ;
            _loc_4 = int(GlobalEngine.getTimer()/1000);
            return _loc_2 + _loc_3 - _loc_4;
        }//end

        private Object  getData ()
        {
            Object _loc_1 =null ;
            double _loc_2 =0;
            _loc_1 = this.m_owner.getDataForMechanic(this.MECHANIC_TYPE);
            if (!_loc_1 || _loc_1 == MechanicManager.DATA_NOT_INITIALIZED)
            {
                _loc_1 = this.initData();
            }
            switch(_loc_1.get("state"))
            {
                case STATE_VIRGIN:
                {
                    break;
                }
                case STATE_IN_PROGRESS:
                {
                    _loc_2 = this.getRollCallTimeLeftHelper(_loc_1);
                    if (_loc_2 <= 0 || this.areCheckinsComplete(_loc_1))
                    {
                        _loc_1.put("state",  STATE_COMPLETE);
                        _loc_1.put("stateChangeTime",  uint(GlobalEngine.getTimer() / 1000));
                        this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, _loc_1, this.m_gameEvent);
                    }
                    break;
                }
                case STATE_COMPLETE:
                {
                    break;
                }
                case STATE_FINISHED:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_1 = this.addMissingCrew(_loc_1);
            return _loc_1;
        }//end

        private Object  addMissingCrew (Object param1 )
        {
            String _loc_3 =null ;
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
                {
                		_loc_3 = _loc_2.list.get(i0);

                    _loc_3 = "i" + _loc_3;
                    if (!param1.get(_loc_3))
                    {
                        param1.put(_loc_3,  this.initCrewEntry());
                    }
                }
            }
            return param1;
        }//end

        private boolean  areCheckinsComplete (Object param1 )
        {
            String _loc_3 =null ;
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            if (_loc_2 == null)
            {
                return false;
            }
            for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
            {
            		_loc_3 = _loc_2.list.get(i0);

                _loc_3 = "i" + _loc_3;
                if (!param1[_loc_3] || !param1[_loc_3].get("checkedIn") || param1.get(_loc_3).get("checkedIn") == false)
                {
                    return false;
                }
            }
            return true;
        }//end

        private boolean  addCheckinData (Object param1 ,String param2 ,boolean param3 )
        {
            if (this.isCrewMember(param2) && param1.get("state") == STATE_IN_PROGRESS)
            {
                param2 = "i" + param2;
                if (!param1.get(param2))
                {
                    param1.put(param2,  this.initCrewEntry());
                }
                if (param1.get(param2).get("checkedIn") == false)
                {
                    param1.get(param2).put("checkedIn",  true);
                    param1.get(param2).put("purchasedCheckIn",  param3);
                    _loc_4 = param1.get(param2);
                    String _loc_5 ="count";
                    _loc_6 = param1.get(param2).get("count") +1;
                    _loc_4.put(_loc_5,  _loc_6);
                }
                this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, param1, this.m_gameEvent);
                return true;
            }
            return false;
        }//end

        private boolean  addFeedSendData (Object param1 ,String param2 )
        {
            if (this.isCrewMember(param2) && param1.get("state") == STATE_IN_PROGRESS)
            {
                param2 = "i" + param2;
                if (!param1.get(param2))
                {
                    param1.put(param2,  this.initCrewEntry());
                }
                if (param1.get(param2).get("feedSent") == false)
                {
                    param1.get(param2).put("feedSent",  true);
                }
                this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, param1, this.m_gameEvent);
                return true;
            }
            return false;
        }//end

        private boolean  addCollectData (Object param1 ,String param2 )
        {
            if ((this.isCrewMember(param2) || Global.isVisiting() == false) && param1.get("state") == STATE_COMPLETE && this.getTimeLeftToCollectHelper(param1) > 0)
            {
                param2 = "i" + param2;
                if (!param1.get(param2))
                {
                    param1.put(param2,  this.initCrewEntry());
                }
                if (param1.get(param2).get("collected") == false)
                {
                    param1.get(param2).put("collected",  true);
                }
                this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, param1, this.m_gameEvent);
                return true;
            }
            return false;
        }//end

        public boolean  hasCheckedIn (String param1 )
        {
            _loc_2 = this.getData ();
            return this.hasCheckedInHelper(param1, _loc_2);
        }//end

        private Object  initData ()
        {
            String _loc_3 =null ;
            Object _loc_1 ={};
            _loc_1.put("state",  STATE_VIRGIN);
            _loc_1.put("stateChangeTime",  0);
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
                {
                		_loc_3 = _loc_2.list.get(i0);

                    _loc_1.put("i" + _loc_3,  this.initCrewEntry());
                }
            }
            this.m_owner.setDataForMechanic(this.MECHANIC_TYPE, _loc_1, this.m_gameEvent);
            return _loc_1;
        }//end

        private boolean  isCooldownComplete (Object param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            if (param1.get("state") == STATE_COMPLETE)
            {
                _loc_2 = param1.get("stateChangeTime");
                _loc_3 = Number(this.m_config.params.get("cooldown")) * this.SECONDS_PER_HOUR;
                _loc_4 = uint(GlobalEngine.getTimer() / 1000);
                return _loc_4 > _loc_2 + _loc_3;
            }
            return false;
        }//end

        private boolean  canSendFeed (String param1 ,Object param2 )
        {
            return param2["state"] == STATE_IN_PROGRESS && this.isCrewMember(param1) && this.getRollCallTimeLeftHelper(param2) > 0 && (!param2["i" + param1] || param2["i" + param1].get("feedSent") == false);
        }//end

        private boolean  canCollectHelper (String param1 ,Object param2 )
        {
            return param2.get("state") == STATE_COMPLETE && this.getTimeLeftToCollectHelper(param2) > 0 && !this.hasCollectedHelper(param1, param2) && (Global.isVisiting() == false || this.isCrewMember(param1) && this.hasCheckedInHelper(param1, param2) && !this.hasPurchasedCheckIn(param1, param2));
        }//end

        private boolean  hasPurchasedCheckIn (String param1 ,Object param2 )
        {
            param1 = "i" + param1;
            if (param2 && param2.get(param1) && param2.get(param1).get("purchasedCheckIn"))
            {
                return param2.get(param1).get("purchasedCheckIn");
            }
            return false;
        }//end

        private boolean  canCheckInHelper (String param1 ,Object param2 )
        {
            return param2.get("state") == STATE_IN_PROGRESS && this.isCrewMember(param1) && this.getRollCallTimeLeftHelper(param2) > 0 && !param2.get("i" + param1).get("checkedIn");
        }//end

        private boolean  hasCollectedHelper (String param1 ,Object param2 )
        {
            param1 = "i" + param1;
            if (param2.get(param1) && param2.get(param1).get("collected"))
            {
                return param2.get(param1).get("collected");
            }
            return false;
        }//end

        private boolean  hasCheckedInHelper (String param1 ,Object param2 )
        {
            param1 = "i" + param1;
            if (param2.get(param1) && param2.get(param1).get("checkedIn"))
            {
                return param2.get(param1).get("checkedIn");
            }
            return false;
        }//end

        public boolean  isCrewMember (String param1 )
        {
            String _loc_3 =null ;
            _loc_2 =Global.crews.getCrewById(this.m_owner.getId ());
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.list.size(); i0++)
                {
                		_loc_3 = _loc_2.list.get(i0);

                    if (_loc_3 === param1)
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        public int  getRollCallDuration ()
        {
            if (this.m_config.params.get("rollCallTimeLimit"))
            {
                return parseInt(this.m_config.params.get("rollCallTimeLimit"));
            }
            return -1;
        }//end

        public boolean  isActiveObject ()
        {
            _loc_1 = this.m_config.params.get( "category") ;
            _loc_2 =Global.rollCallManager.getActiveObject(_loc_1 );
            return _loc_2 && _loc_2.getId() == this.m_owner.getId();
        }//end

        private Object  initCrewEntry ()
        {
            Object _loc_1 ={};
            _loc_1.put("collected",  false);
            _loc_1.put("count",  0);
            _loc_1.put("checkedIn",  false);
            _loc_1.put("purchasedCheckIn",  false);
            _loc_1.put("feedSent",  false);
            return _loc_1;
        }//end

        private void  sendTransaction (String param1 ,...args0 )
        {
            GameTransactionManager.addTransaction(new TMechanicAction(this.m_owner, MechanicManager.MECHANIC_ROLL_CALL, MechanicManager.ALL, {operation:param1, args:args0}), true);
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



