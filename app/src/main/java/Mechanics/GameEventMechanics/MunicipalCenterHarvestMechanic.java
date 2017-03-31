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

    public class MunicipalCenterHarvestMechanic extends TimerStorageStateHarvestMechanic
    {

        public  MunicipalCenterHarvestMechanic ()
        {
            return;
        }//end

         protected void  postHarvest ()
        {
            Array _loc_2 =null ;
            Municipal _loc_3 =null ;
            double _loc_4 =0;
            super.postHarvest();
            _loc_1 = m_owner.getDataForMechanic("slots");
            if (_loc_1 == null)
            {
                return;
            }
            _loc_2 = new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            		_loc_3 = _loc_1.get(i0);

                _loc_3.setState(HarvestableResource.STATE_PLANTED);
                _loc_3.plantTime = GlobalEngine.getTimer();
                _loc_2.push(_loc_3.getItemName());
            }
            _loc_4 = MunicipalCenter.calculateEndTime(m_owner, _loc_1);
            m_owner.setDataForMechanic("endTS", _loc_4, m_gameEvent);
            m_owner.setDataForMechanic("resources", _loc_2, m_gameEvent);
            return;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Main","RipeEntertainment");
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            double _loc_3 =0;
            Municipal _loc_4 =null ;
            double _loc_5 =0;
            _loc_1 = (MunicipalCenter)m_owner
            String _loc_2 ="";
            if (_loc_1 != null)
            {
                _loc_3 = 0;
                for(int i0 = 0; i0 < _loc_1.slots.size(); i0++) 
                {
                		_loc_4 = _loc_1.slots.get(i0);

                    _loc_3 = _loc_3 + PeepHomeUtil.getRentPayout(_loc_4.getItemName());
                }
                _loc_5 = m_owner.getItem().getDooberMinimums(Doober.DOOBER_COIN, "default");
                _loc_3 = _loc_3 + _loc_5;
                _loc_2 = _loc_2 + ZLoc.t("Main", "RentValue", {value:_loc_3});
            }
            return _loc_2;
        }//end

    }



