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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class StartTimerHarvestMechanic implements IActionGameMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_gameEvent ="GMPlay";

        public  StartTimerHarvestMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return !this.m_owner.isNeedingRoad && this.canStartTimer();
        }//end

        protected boolean  canStartTimer ()
        {
            _loc_1 = this.m_owner.getDataForMechanic("harvestState");
            return (_loc_1 == null || GameUtil.countObjectLength(_loc_1) == 0) && !this.isOpen();
        }//end

        public double  openTS ()
        {
            return this.m_owner.getDataForMechanic("openTS");
        }//end

        public double  closeTS ()
        {
            return this.m_owner.getDataForMechanic("closeTS");
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

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            this.initializeHarvestTimer();
            boolean _loc_3 =true ;
            _loc_4 = this.m_config.params.get( "blockOthers") =="true"? (false) : (true);
            boolean _loc_5 =true ;
            Object _loc_6 ={operation "startTimer"};
            return new MechanicActionResult(_loc_3, _loc_4, _loc_5, _loc_6);
        }//end

        protected void  initializeHarvestTimer ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put("customers",  0);
            _loc_1.put("customersReq",  this.getCustomerCapacity());
            _loc_2 = this.m_config.params.get( "harvestStateName") ;
            this.m_owner.setDataForMechanic(_loc_2, _loc_1, this.m_gameEvent);
            _loc_3 = GlobalEngine.getTimer ()/1000;
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, this.m_gameEvent);
            _loc_4 = _loc_3+this.getDuration ();
            this.m_owner.setDataForMechanic("closeTS", _loc_4, this.m_gameEvent);
            return;
        }//end

        protected int  getCustomerCapacity ()
        {
            _loc_1 = this.m_owner.getItem ();
            return _loc_1.customerCapacity > 0 ? (_loc_1.customerCapacity) : (int.MAX_VALUE);
        }//end

        protected double  getDuration ()
        {
            return this.m_config.params.hasOwnProperty("totalTime") ? (this.m_config.params.get("totalTime")) : (0);
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Main","ClickToOpen");
            return _loc_1;
        }//end

        public String  getToolTipStatus ()
        {
            _loc_1 = ZLoc.t("Main","ReadyForOpening");
            return _loc_1;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



