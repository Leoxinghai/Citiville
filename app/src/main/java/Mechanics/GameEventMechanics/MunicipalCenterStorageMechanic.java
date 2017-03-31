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

    public class MunicipalCenterStorageMechanic extends GatedStorageMechanic
    {

        public  MunicipalCenterStorageMechanic ()
        {
            return;
        }//end

         protected MechanicActionResult  handleStore (Array param1 )
        {
            _loc_2 = super.handleStore(param1);
            if (!_loc_2.actionSuccess)
            {
                throw new Error("ERROR STORING MUNICIPAL IN MUNICIPALCENTER");
            }
            _loc_3 = GlobalEngine.getTimer ()/1000;
            _loc_4 = m_owner.getDataForMechanic("resources");
            _loc_5 = param1.get("object") ;
            _loc_6 = param1.get("object").getItemName ();
            _loc_4.push(_loc_6);
            m_owner.setDataForMechanic("resources", _loc_4, m_gameEvent);
            return _loc_2;
        }//end

         protected MechanicActionResult  handlePlaceFromStorage (Array param1 )
        {
            _loc_2 = super.handlePlaceFromStorage(param1);
            if (!_loc_2.actionSuccess)
            {
                throw new Error("ERROR STORING MUNICIPAL IN MUNICIPALCENTER");
            }
            _loc_3 = param1.get("slotId") ;
            _loc_4 = m_owner.getDataForMechanic("resources");
            m_owner.getDataForMechanic("resources").splice(_loc_3, 1);
            m_owner.setDataForMechanic("resources", _loc_4, m_gameEvent);
            return _loc_2;
        }//end

         protected void  postStorageAction ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            _loc_2 = MunicipalCenter.calculateEndTime(m_owner,_loc_1);
            m_owner.setDataForMechanic("endTS", _loc_2, m_gameEvent);
            Global.world.citySim.recomputePopulationCap(Global.world);
            return;
        }//end

    }



