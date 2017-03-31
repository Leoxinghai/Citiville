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

import Classes.util.*;
import Mechanics.*;
//import flash.utils.*;

    public class TimerCouponMechanic extends TimerActionMechanic
    {

        public  TimerCouponMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            switch(param1)
            {
                case "onAddToWorld":
                {
                    return true;
                }
                case MechanicManager.PLAY:
                {
                    return super.hasOverrideForGameAction(param1);
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            switch(param1)
            {
                case "onAddToWorld":
                {
                    return this.onAddToWorld();
                }
                default:
                {
                    return super.executeOverrideForGameEvent(param1, param2);
                    break;
                }
            }
        }//end

         public boolean  isTimeReady ()
        {
            _loc_1 = m_owner.getItem().harvestLoopConfig;
            _loc_2 = (String)(_loc_1.get( "requiredCoupon") );
            if (_loc_2)
            {
                return Global.player.hasCoupon(_loc_2);
            }
            return super.isTimeReady();
        }//end

         protected void  processHarvest ()
        {
            _loc_1 = m_owner.getItem().harvestLoopConfig;
            _loc_2 = (String)(_loc_1.get( "requiredCoupon") );
            if (_loc_2)
            {
                if (Global.player.hasCoupon(_loc_2))
                {
                    Global.player.useCoupon(_loc_2);
                }
            }
            super.processHarvest();
            return;
        }//end

        protected MechanicActionResult  onAddToWorld ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            if (Global.player.hasCoupon(m_owner.getItemName()))
            {
                _loc_1.put("startTS",  DateUtil.getUnixTime() - getOpenDuration() - getSleepDuration() - 1);
                Global.player.useCoupon(m_owner.getItemName());
            }
            else
            {
                _loc_1.put("startTS",  DateUtil.getUnixTime() - getOpenDuration());
            }
            m_owner.setDataForMechanic(m_config.type, _loc_1, m_gameEvent);
            return new MechanicActionResult(true, true, false, null);
        }//end

    }



