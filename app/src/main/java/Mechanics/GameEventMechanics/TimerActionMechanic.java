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
//import flash.utils.*;

    public class TimerActionMechanic extends TimerHarvestMechanic
    {

        public  TimerActionMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            switch(param1)
            {
                case "hudTimerClicked":
                {
                    return true;
                }
                default:
                {
                    return super.hasOverrideForGameAction(param1);
                    break;
                }
            }
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            switch(param1)
            {
                case "hudTimerClicked":
                {
                    return this.hudTimerClicked();
                }
                default:
                {
                    return super.executeOverrideForGameEvent(param1, param2);
                    break;
                }
            }
        }//end

         public boolean  isHarvestReady ()
        {
            return this.isTimeReady() && !this.isHarvestLocked();
        }//end

        public boolean  isTimeReady ()
        {
            return DateUtil.getUnixTime() - this.getStartTS() > this.getOpenDuration() + this.getSleepDuration();
        }//end

        public boolean  isHarvestLocked ()
        {
            String _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            Object _loc_6 =null ;
            MechanicMapResource _loc_7 =null ;
            Object _loc_8 =null ;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            double _loc_13 =0;
            double _loc_14 =0;
            _loc_1 = m_owner.getItem().harvestLoopConfig;
            if (_loc_1 && _loc_1.get("lockOnKeyword"))
            {
                _loc_2 = _loc_1.get("lockOnKeyword");
                _loc_3 = Global.world.getObjectsByKeywords(.get(_loc_2));
                _loc_4 = _loc_3.length;
                _loc_5 = 0;
                while (_loc_5 < _loc_4)
                {

                    _loc_6 = _loc_3.get(_loc_5);
                    if (_loc_6)
                    {
                        if (_loc_6.hasOwnProperty("isOpen") && _loc_6.isOpen())
                        {
                            return true;
                        }
                        if (_loc_6 instanceof MechanicMapResource)
                        {
                            _loc_7 =(MechanicMapResource) _loc_6;
                            if (_loc_7.getDataForMechanic("openingState"))
                            {
                                return true;
                            }
                            if (_loc_7.hasMechanicAvailable("harvestLoop") || _loc_7.hasMechanicAvailable("harvestState"))
                            {
                                _loc_8 = _loc_7.getDataForMechanic("harvestState");
                                if (_loc_8.get("openTS"))
                                {
                                    _loc_9 = Number(m_owner.getItem().harvestLoopConfig.get("timerDuration"));
                                    _loc_10 = _loc_8.get("openTS");
                                    _loc_11 = _loc_8.get("openTS") + _loc_9;
                                    if (_loc_10 <= 0 || _loc_11 < 0)
                                    {
                                    }
                                    else
                                    {
                                        _loc_12 = _loc_11;
                                        _loc_13 = GlobalEngine.getTimer() / 1000;
                                        _loc_14 = _loc_12 - _loc_13 >= 0 ? (_loc_12 - _loc_13) : (0);
                                        if (_loc_14 > 0)
                                        {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _loc_5++;
                }
            }
            return false;
        }//end

         protected void  preProgressHarvest ()
        {
            m_owner.setDataForMechanic("openingState", new Object(), m_gameEvent);
            MechanicManager.getInstance().handleAction(m_owner, "manualWonderStart", null);
            return;
        }//end

         protected void  processHarvest ()
        {
            super.processHarvest();
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            _loc_1.put("startTS",  DateUtil.getUnixTime());
            m_owner.setDataForMechanic(m_config.type, _loc_1, m_gameEvent);
            m_owner.setDataForMechanic("openingState", null, m_gameEvent);
            return;
        }//end

        protected MechanicActionResult  hudTimerClicked (Array param1)
        {
            boolean _loc_2 =false ;
            _loc_3 = m_owner.getItem().harvestLoopConfig;
            _loc_4 =(int)(_loc_3.get( "cash") );
            _loc_5 =(int)(_loc_3.get( "energyGain") );
            _loc_6 =(int)(_loc_3.get( "boostSeconds") );
            if (Global.player.cash >= _loc_4 && isOpen())
            {
                Global.player.cash = Global.player.cash - _loc_4;
                _loc_2 = true;
                this.boostTime(_loc_6);
                Global.player.updateEnergy(_loc_5, null);
            }
            return new MechanicActionResult(_loc_2, true, _loc_2, {operation:"hudTimerClicked"});
        }//end

        protected void  boostTime (int param1 )
        {
            _loc_2 = m_owner.getDataForMechanic(m_config.type);
            _loc_2.put("startTS",  _loc_2.get("startTS") + param1);
            m_owner.setDataForMechanic(m_config.type, _loc_2, m_gameEvent);
            return;
        }//end

        protected double  getStartTS ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            return _loc_1.get("startTS") ? (_loc_1.get("startTS")) : (0);
        }//end

         protected double  getOpenDuration ()
        {
            _loc_1 = m_owner.getItem().harvestLoopConfig;
            _loc_2 =(double)(_loc_1.get( "timerDuration") );
            _loc_3 = _loc_2;
            return _loc_3;
        }//end

        protected double  getSleepDuration ()
        {
            _loc_1 = m_owner.getItem().harvestLoopConfig;
            return Number(_loc_1.get("sleepDuration"));
        }//end

    }



