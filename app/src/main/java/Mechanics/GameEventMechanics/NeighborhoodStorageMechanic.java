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

    public class NeighborhoodStorageMechanic extends GatedStorageMechanic
    {

        public  NeighborhoodStorageMechanic ()
        {
            return;
        }//end

         protected MechanicActionResult  handleStore (Array param1 )
        {
            _loc_2 = super.handleStore(param1);
            if (!_loc_2.actionSuccess)
            {
                throw new Error("ERROR STORING RESIDENCE IN NEIGHBORHOOD");
            }
            _loc_3 = GlobalEngine.getTimer ()/1000;
            _loc_4 = m_owner.getDataForMechanic("resources");
            _loc_5 = param1.get("object") ;
            _loc_6 = param1.get("object").getItemName ();
            _loc_4.push(_loc_6);
            m_owner.setDataForMechanic("resources", _loc_4, m_gameEvent);
            _loc_7 = this.getVisualState(_loc_4 );
            if (m_owner.getState() != _loc_7)
            {
                m_owner.setState(_loc_7);
            }
            return _loc_2;
        }//end

         protected MechanicActionResult  handlePlaceFromStorage (Array param1 )
        {
            _loc_2 = super.handlePlaceFromStorage(param1);
            if (!_loc_2.actionSuccess)
            {
                throw new Error("ERROR REMOVING RESIDENCE FROM NEIGHBORHOOD");
            }
            _loc_3 = param1.get("slotId") ;
            _loc_4 = m_owner.getDataForMechanic("resources");
            m_owner.getDataForMechanic("resources").splice(_loc_3, 1);
            m_owner.setDataForMechanic("resources", _loc_4, m_gameEvent);
            _loc_5 = this.getVisualState(_loc_4 );
            if (m_owner.getState() != _loc_5)
            {
                m_owner.setState(_loc_5);
            }
            return _loc_2;
        }//end

         protected void  postStorageAction ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            _loc_2 = Neighborhood.calculateEndTime(m_owner,_loc_1);
            m_owner.setDataForMechanic("endTS", _loc_2, m_gameEvent);
            return;
        }//end

        private String  getVisualState (Array param1 )
        {
            XML _loc_5 =null ;
            _loc_2 = m_owner.getDataForMechanic(m_config.type);
            _loc_3 = _loc_2? (_loc_2.length()) : (0);
            Array _loc_4 =new Array ();
            if (m_config.rawXMLConfig.hasOwnProperty("visualStates"))
            {
                for(int i0 = 0; i0 < m_config.rawXMLConfig.visualStates.visualState.size(); i0++)
                {
                		_loc_5 = m_config.rawXMLConfig.visualStates.visualState.get(i0);

                    if (_loc_3 >= _loc_5.@min && _loc_3 <= _loc_5.@max)
                    {
                        return _loc_5.@state;
                    }
                }
            }
            return m_owner.STATE_STATIC;
        }//end

    }



