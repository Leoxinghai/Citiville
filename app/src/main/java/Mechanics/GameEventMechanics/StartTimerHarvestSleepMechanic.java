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

import Mechanics.*;
import Modules.stats.types.*;

    public class StartTimerHarvestSleepMechanic extends StartTimerHarvestMechanic
    {

        public  StartTimerHarvestSleepMechanic ()
        {
            return;
        }//end

         protected boolean  canStartTimer ()
        {
            return super.canStartTimer() && !this.isSleeping();
        }//end

        public double  sleepTS ()
        {
            return m_owner.getDataForMechanic("sleepTS");
        }//end

        public double  wakeTS ()
        {
            return m_owner.getDataForMechanic("wakeTS");
        }//end

        public double  sleepTimeLeft ()
        {
            if (this.sleepTS < 0 || this.wakeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.wakeTS ;
            _loc_2 = GlobalEngine.getTimer ()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

        public boolean  isSleeping ()
        {
            return this.sleepTS > 0 && this.sleepTimeLeft > 0;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            _loc_3 = m_owner.getDataForMechanic("sleepState");
            _loc_4 = _loc_3!= null && _loc_3.hasOwnProperty("maintenance") && _loc_3.maintenance == 1;
            initializeHarvestTimer();
            this.initializeSleepState();
            m_owner.trackDetailedAction(TrackedActionType.START, _loc_4 ? ("with_consumables") : ("without_consumables"), "");
            boolean _loc_5 =true ;
            _loc_6 = m_config.params.get( "blockOthers") =="true"? (false) : (true);
            boolean _loc_7 =true ;
            Object _loc_8 ={operation "startTimer"};
            return new MechanicActionResult(_loc_5, _loc_6, _loc_7, _loc_8);
        }//end

        protected void  initializeSleepState ()
        {
            _loc_1 = m_config.params.get( "sleepStateName") ;
            _loc_2 = m_owner.getDataForMechanic(_loc_1);
            if (_loc_2 == null)
            {
                _loc_2 = new Object();
            }
            _loc_2.put("sleepTS",  0);
            _loc_2.put("wakeTS",  0);
            if (!_loc_2.hasOwnProperty("consumables"))
            {
                _loc_2.put("consumables",  0);
            }
            if (!_loc_2.hasOwnProperty("lastViralTime"))
            {
                _loc_2.put("lastViralTime",  0);
            }
            if (!_loc_2.hasOwnProperty("maintenance"))
            {
                _loc_2.put("maintenance",  0);
            }
            m_owner.setDataForMechanic(_loc_1, _loc_2, m_gameEvent);
            return;
        }//end

    }



