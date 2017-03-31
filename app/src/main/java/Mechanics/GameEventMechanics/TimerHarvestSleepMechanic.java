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

import Modules.stats.experiments.*;
//import flash.utils.*;
import Mechanics.GameMechanicInterfaces.*;

    public class TimerHarvestSleepMechanic extends TimerHarvestMechanic
    {

        public  TimerHarvestSleepMechanic ()
        {
            return;
        }//end

         protected void  processHarvest ()
        {
            super.processHarvest();
            _loc_1 = GlobalEngine.getTimer ()/1000;
            m_owner.setDataForMechanic("sleepTS", _loc_1, m_gameEvent);
            _loc_2 = _loc_1+getSleepDuration(m_config.params );
            m_owner.setDataForMechanic("wakeTS", _loc_2, m_gameEvent);
            return;
        }//end

         protected void  postHarvest ()
        {
            super.postHarvest();
            m_owner.setDataForMechanic("consumables", 0, m_gameEvent);
            return;
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
            _loc_1 = GlobalEngine.getTimer ()/1000;
            return this.sleepTS > 0 && this.sleepTS <= _loc_1 && this.sleepTimeLeft > 0;
        }//end

        public static double  getSleepDuration (Dictionary param1 )
        {
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ATTRACTION_MAINTENANCE );
            String _loc_3 ="sleepDuration";
            if (_loc_2 == ExperimentDefinitions.ATTRACTION_MAINTENANCE_48HR)
            {
                _loc_3 = "sleepDurationAlt1";
            }
            else if (_loc_2 == ExperimentDefinitions.ATTRACTION_MAINTENANCE_24HR)
            {
                _loc_3 = "sleepDurationAlt2";
            }
            return param1.hasOwnProperty(_loc_3) ? (param1.get(_loc_3)) : (0);
        }//end

    }



