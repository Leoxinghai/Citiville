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
import Mechanics.GameMechanicInterfaces.*;
import Modules.crew.*;

    public class GatedStorageMechanic extends BaseStorageMechanic
    {

        public  GatedStorageMechanic ()
        {
            return;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            m_owner =(MechanicMapResource) param1;
            m_config = param2;
            _loc_3 = m_owner.getItem().storageInitCapacity;
            _loc_4 = m_owner.getItem().storageMaxCapacity;
            _loc_5 = _loc_3;
            _loc_6 =Global.crews.getCrewById(m_owner.getId ());
            if (Global.crews.getCrewById(m_owner.getId()))
            {
                _loc_5 = _loc_5 + _loc_6.count;
            }
            _loc_7 = Math.min(_loc_5 ,_loc_4 );
            m_maxCapacity = _loc_4;
            m_capacity = _loc_7;
            return;
        }//end

    }



