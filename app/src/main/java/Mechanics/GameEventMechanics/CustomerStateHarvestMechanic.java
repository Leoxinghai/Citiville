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
import Mechanics.GameMechanicInterfaces.*;

    public class CustomerStateHarvestMechanic extends BaseStateHarvestMechanic implements IMultiPickSupporter, IToolTipModifier
    {

        public  CustomerStateHarvestMechanic ()
        {
            return;
        }//end

         public boolean  isHarvestReady ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            if (_loc_1 && _loc_1.hasOwnProperty("customers") && _loc_1.hasOwnProperty("customersReq"))
            {
                if (_loc_1.get("customersReq") <= _loc_1.get("customers"))
                {
                    return true;
                }
            }
            return false;
        }//end

         protected Array  applyPayoutMultipliers (Item param1 ,Array param2 )
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            _loc_3 = param2;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                _loc_4 = Global.gameSettings().getDooberTypeFromItemName(_loc_5.get(0));
                switch(_loc_4)
                {
                    case Doober.DOOBER_COIN:
                    {
                        _loc_5.put(1,  _loc_5.get(1) * (param1.customerCapacity > 0 ? (param1.customerCapacity) : (param1.commodityReq)));
                        _loc_5.put(0,  Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_5.get(1)));
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
            }
            return _loc_3;
        }//end

        public String  getPick ()
        {
            return m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipStatus ()
        {
            int _loc_3 =0;
            int _loc_4 =0;
            String _loc_1 =null ;
            _loc_2 = m_owner.getDataForMechanic(m_config.type);
            if (_loc_2)
            {
                if (_loc_2.hasOwnProperty("customers") && _loc_2.hasOwnProperty("customersReq"))
                {
                    _loc_3 = _loc_2.get("customers");
                    _loc_4 = _loc_2.get("customersReq");
                    _loc_1 = ZLoc.t("Main", "BusinessCustomers", {served:_loc_3, max:_loc_4});
                }
            }
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            _loc_2 = getRequiredEnergy();
            if (_loc_2 > 0)
            {
                if (Global.player.checkEnergy(-1 * _loc_2, false))
                {
                    _loc_1 = ZLoc.t("Main", "BusinessIsHarvestableNoAmount");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:_loc_2});
                }
            }
            return _loc_1;
        }//end

    }



