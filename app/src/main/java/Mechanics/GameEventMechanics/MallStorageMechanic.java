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
import Mechanics.*;

    public class MallStorageMechanic extends GatedStorageMechanic
    {

        public  MallStorageMechanic ()
        {
            return;
        }//end

         protected MechanicActionResult  handlePlaceFromStorage (Array param1 )
        {
            Object _loc_4 =null ;
            Array _loc_5 =null ;
            String _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            Array _loc_10 =null ;
            MapResource _loc_11 =null ;
            int _loc_12 =0;
            double _loc_13 =0;
            _loc_2 = param1.get("mapResource") ;
            _loc_3 = super.handlePlaceFromStorage(param1);
            if (_loc_3.actionSuccess)
            {
                _loc_4 = m_owner.getDataForMechanic("harvestState");
                if (_loc_4 && _loc_4.get("resources"))
                {
                    _loc_5 =(Array) _loc_4.get("resources");
                    _loc_6 = _loc_2.getItemName();
                    _loc_7 = 0;
                    _loc_8 = -1;
                    _loc_9 = 0;
                    _loc_10 = m_owner.getDataForMechanic(m_config.type);
                    for(int i0 = 0; i0 < _loc_10.size(); i0++)
                    {
                    		_loc_11 = _loc_10.get(i0);

                        if (_loc_11.getItemName() == _loc_6)
                        {
                            _loc_9++;
                        }
                    }
                    _loc_12 = 0;
                    while (_loc_12 < _loc_5.length())
                    {

                        if (_loc_6 == _loc_5.get(_loc_12))
                        {
                            _loc_7++;
                            _loc_8 = _loc_12;
                        }
                        _loc_12++;
                    }
                    if (_loc_7 > _loc_9)
                    {
                        _loc_5.splice(_loc_8, 1);
                        _loc_4.put("resources",  _loc_5);
                        _loc_13 = _loc_2.getItem().commodityReq;
                        _loc_4.put("customersReq",  Number(_loc_4.get("customersReq")) - _loc_13);
                        if (_loc_4.get("customersReq") <= 0)
                        {
                            _loc_4 = null;
                        }
                        m_owner.setDataForMechanic("harvestState", _loc_4, m_gameEvent);
                        ((IMerchant)m_owner).updatePeepSpawning();
                    }
                }
            }
            return _loc_3;
        }//end

    }



