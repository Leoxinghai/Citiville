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
import Classes.util.*;
import Mechanics.GameMechanicInterfaces.*;

    public class TimerHarvestMechanic extends BaseStateHarvestMechanic implements IMultiPickSupporter, IToolTipModifier
    {

        public  TimerHarvestMechanic ()
        {
            return;
        }//end

         public boolean  isHarvestReady ()
        {
            _loc_1 = m_owner.getDataForMechanic("harvestState");
            return _loc_1 && GameUtil.countObjectLength(_loc_1) > 0 && !this.isOpen();
        }//end

         protected Array  applyPayoutMultipliers (Item param1 ,Array param2 )
        {
            return param2;
        }//end

        public double  openTS ()
        {
            return m_owner.getDataForMechanic("openTS");
        }//end

        public double  closeTS ()
        {
            _loc_1 = this.getOpenDuration ();
            return this.openTS + _loc_1;
        }//end

        protected double  getOpenDuration ()
        {
            return m_config.params.get("duration");
        }//end

        public double  openTimeLeft ()
        {
            if (this.openTS < 0 || this.closeTS < 0)
            {
                return 0;
            }
            _loc_1 = this.closeTS ;
            _loc_2 = GlobalEngine.getTimer ()/1000;
            return _loc_1 - _loc_2 >= 0 ? (_loc_1 - _loc_2) : (0);
        }//end

        public boolean  isOpen ()
        {
            return this.openTS > 0 && this.openTimeLeft > 0;
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

         protected void  postHarvest ()
        {
            Object _loc_1 =null ;
            super.postHarvest();
            if (m_owner && m_config && (m_config.params.get("autoStart") == "true" || m_config.params.get("autoStart") == "1"))
            {
                _loc_1 = new Object();
                _loc_1.put("openTS",  GlobalEngine.getTimer() / 1000);
                m_owner.setDataForMechanic("harvestState", _loc_1, "GMPlay");
            }
            return;
        }//end

    }



