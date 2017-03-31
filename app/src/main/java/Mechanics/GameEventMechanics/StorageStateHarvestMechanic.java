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
import Engine.Managers.*;
import Mechanics.Transactions.*;
import Modules.stats.types.*;
import Mechanics.GameMechanicInterfaces.*;

    public class StorageStateHarvestMechanic extends BaseStateHarvestMechanic implements IMultiPickSupporter, IToolTipModifier
    {

        public  StorageStateHarvestMechanic ()
        {
            return;
        }//end

         public boolean  isHarvestReady ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            return _loc_1 && _loc_1.hasOwnProperty("resources") && _loc_1.get("resources") instanceof Array && _loc_1.get("resources").length > 0;
        }//end

         protected void  processHarvest ()
        {
            Item _loc_2 =null ;
            String _loc_3 =null ;
            MapResource _loc_4 =null ;
            Object _loc_5 =null ;
            Array _loc_6 =null ;
            Array _loc_7 =null ;
            int _loc_8 =0;
            Array _loc_9 =null ;
            Array _loc_10 =null ;
            Array _loc_11 =null ;
            Array _loc_12 =null ;
            _loc_1 = this.getRequiredEnergy ();
            if (Global.player.updateEnergy(-1 * _loc_1, getTrackingArray()))
            {
                preHarvest();
                _loc_2 = null;
                _loc_3 = null;
                _loc_4 = null;
                _loc_5 = m_owner.getDataForMechanic(m_config.type);
                _loc_6 =(Array) _loc_5.get("resources");
                _loc_7 = null;
                if (m_config.params.get("dataSourceType"))
                {
                    _loc_7 = m_owner.getDataForMechanic(m_config.params.get("dataSourceType"));
                }
                _loc_8 = 0;
                while (_loc_8 < _loc_6.length())
                {

                    _loc_3 = _loc_6.get(_loc_8);
                    _loc_4 = null;
                    if (_loc_7 && _loc_8 < _loc_7.length())
                    {
                        _loc_4 = _loc_7.get(_loc_8);
                    }
                    _loc_2 = Global.gameSettings().getItemByName(_loc_3);
                    _loc_9 = Global.player.processRandomModifiers(_loc_2, m_owner, true, [], "default", null, _loc_4);
                    _loc_10 = this.applyPayoutMultipliers(_loc_2, _loc_9);
                    m_doobers = m_doobers.concat(_loc_10);
                    _loc_8++;
                }
                if (m_config.params.get("countSelf") == "true")
                {
                    _loc_2 = m_owner.getItem();
                    _loc_4 = m_owner;
                    _loc_11 = Global.player.processRandomModifiers(_loc_2, m_owner, true, [], "default", null, _loc_4);
                    _loc_12 = this.applyPayoutMultipliers(_loc_2, _loc_11);
                    m_doobers = m_doobers.concat(_loc_12);
                }
                Global.world.dooberManager.createBatchDoobers(m_doobers, m_owner.getItem(), m_owner.positionX, m_owner.positionY, false, onDoobersCollected);
                m_owner.setDataForMechanic(m_config.type, null, m_gameEvent);
                TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, m_gameEvent, {operation:"harvest"}));
                m_owner.trackAction(TrackedActionType.HARVEST);
                postHarvest();
            }
            return;
        }//end

         protected Array  applyPayoutMultipliers (Item param1 ,Array param2 )
        {
            _loc_3 = param2;
            return _loc_3;
        }//end

         protected int  getRequiredEnergy ()
        {
            Item _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_1 =0;
            _loc_2 = m_owner.getDataForMechanic(m_config.type);
            if (_loc_2 && _loc_2.get("resources") && ((Array)_loc_2.get("resources")).length())
            {
                _loc_3 = null;
                _loc_4 =(Array) _loc_2.get("resources");
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_3 = Global.gameSettings().getItemByName(_loc_5);
                    _loc_1 = _loc_1 + _loc_3.harvestEnergyCost;
                }
            }
            return _loc_1;
        }//end

        public String  getPick ()
        {
            return m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 ="";
            if (m_config && m_config.params && m_config.params.hasOwnProperty("tooltipAction"))
            {
                _loc_1 = ZLoc.t("Main", m_config.params.get("tooltipAction"));
            }
            return _loc_1;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            if (m_config && m_config.params && m_config.params.hasOwnProperty("tooltipStatus"))
            {
                _loc_1 = ZLoc.t("Main", m_config.params.get("tooltipStatus"));
            }
            return _loc_1;
        }//end

    }



