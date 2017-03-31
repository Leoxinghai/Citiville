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
import Classes.sim.*;
import Modules.bandits.*;

    public class NeighborhoodHarvestMechanic extends TimerStorageStateHarvestMechanic
    {

        public  NeighborhoodHarvestMechanic ()
        {
            return;
        }//end

         protected void  postHarvest ()
        {
            Array _loc_2 =null ;
            Residence _loc_5 =null ;
            super.postHarvest();
            _loc_1 = m_owner.getDataForMechanic("slots");
            if (_loc_1 == null)
            {
                return;
            }
            _loc_2 = new Array();
            int _loc_3 =0;
            while (_loc_3 < _loc_1.length())
            {

                _loc_5 = _loc_1.get(_loc_3);
                _loc_5.setState(HarvestableResource.STATE_PLANTED);
                _loc_5.plantTime = GlobalEngine.getTimer();
                _loc_2.push(_loc_5.getItemName());
                _loc_3++;
            }
            _loc_4 = Neighborhood.calculateEndTime(m_owner,_loc_1);
            m_owner.setDataForMechanic("endTS", _loc_4, m_gameEvent);
            m_owner.setDataForMechanic("resources", _loc_2, m_gameEvent);
            PreyManager.processHarvest(m_owner);
            return;
        }//end

         public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Main","RipeResidence");
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            double _loc_3 =0;
            Residence _loc_4 =null ;
            double _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            Object _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            _loc_1 = (Neighborhood)m_owner
            String _loc_2 ="";
            if (_loc_1 != null)
            {
                _loc_3 = 0;
                for(int i0 = 0; i0 < _loc_1.slots.size(); i0++)
                {
                		_loc_4 = _loc_1.slots.get(i0);

                    _loc_3 = _loc_3 + PeepHomeUtil.getRentPayout(_loc_4.getItemName());
                }
                _loc_2 = _loc_2 + ZLoc.t("Main", "RentValue", {value:_loc_3});
                _loc_2 = _loc_2 + "\n";
                _loc_5 = Global.gameSettings().getNumber("populationScale", 1);
                _loc_6 = _loc_1.getPopulationYield() * _loc_5;
                _loc_7 = _loc_1.getPopulationMaxYield() * _loc_5;
                _loc_8 = {curPop:_loc_6, maxPop:_loc_7};
                _loc_9 = "TT_PopulationCurOfMax";
                if (Global.gameSettings().hasMultiplePopulations())
                {
                    _loc_10 = _loc_1.getPopulationType();
                    _loc_11 = PopulationHelper.getPopulationZlocType(_loc_10, true);
                    _loc_8.popType = _loc_11;
                    _loc_9 = "TT_PopulationTypeCurOfMax";
                }
                _loc_2 = _loc_2 + ZLoc.t("Dialogs", _loc_9, _loc_8);
            }
            return _loc_2;
        }//end

    }



