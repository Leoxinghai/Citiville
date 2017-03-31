package Mechanics.ClientDisplayMechanics;

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
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.utils.*;

    public class TimerMechanic implements IClientGameMechanic
    {
        protected MechanicMapResource m_owner =null ;
        protected MechanicConfigData m_config =null ;
        protected Timer m_timer =null ;
        protected boolean m_displayMilestoneText =false ;
        protected double m_endTS =0;
        protected Function m_timerFunc ;
        protected Timer m_milestoneTimer =null ;
        protected double m_prevTimeDisplayed =-1;
        protected String m_dataSourceType =null ;
        private  int TIMER_BUFFER =1000;
        private  Array MILESTONES =.get(45 ,30,15,10,5,4,3,2,1) ;

        public  TimerMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            this.m_displayMilestoneText = this.m_config.params.hasOwnProperty("displayMilestoneText") && this.m_config.params.get("displayMilestoneText") == "true";
            this.m_dataSourceType = this.m_config.params.hasOwnProperty("dataSourceType") ? (String(this.m_config.params.get("dataSourceType"))) : (null);
            this.initTimer();
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.subType != "NPCEnterAction" && (event.obj == this.m_dataSourceType || event.obj == "load"))
            {
                this.initTimer();
            }
            _loc_2 = this.m_owner.getDataForMechanic(this.m_dataSourceType );
            if (_loc_2 != this.m_endTS)
            {
                if (this.m_timer)
                {
                    this.m_timer.removeEventListener(TimerEvent.TIMER, this.m_timerFunc);
                    this.m_timer.stop();
                    this.m_timer = null;
                }
                this.initTimer();
            }
            return;
        }//end

        protected void  initTimer ()
        {
            if (this.m_timer != null)
            {
                return;
            }
            if (this.m_dataSourceType == null)
            {
                return;
            }
            if (this.m_owner.getDataForMechanic(this.m_dataSourceType) <= 0)
            {
                return;
            }
            endTS = this.m_owner.getDataForMechanic(this.m_dataSourceType);
            this.m_endTS = endTS;
            currTime = GlobalEngine.getTimer()/1000;
            timeRemaining = endTS-currTime;
            this .m_timerFunc =void  (TimerEvent event )
            {
                m_timer.removeEventListener(TimerEvent.TIMER, arguments.callee);
                m_timer.stop();
                m_timer = null;
                onTimerFinish();
                return;
            }//end
            ;
            if (timeRemaining >= 0)
            {
                this.m_timer = new Timer(timeRemaining * 1000 + this.TIMER_BUFFER);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.m_timerFunc);
                this.m_timer.start();
            }
            return;
        }//end

        protected void  onTimerFinish ()
        {
            this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.m_config.type, true, "all"));
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            if (this.m_displayMilestoneText)
            {
                this.handleDisplayMilestoneText();
            }
            return;
        }//end

        public void  detachDisplayObject ()
        {
            return;
        }//end

        protected void  handleDisplayMilestoneText ()
        {
            int _loc_4 =0;
            String _loc_5 =null ;
            _loc_1 = this.m_owner.getDataForMechanic(this.m_dataSourceType );
            _loc_2 = GlobalEngine.getTimer ()/1000;
            _loc_3 = Math.floor(_loc_1 -_loc_2 );
            if (_loc_3 != this.m_prevTimeDisplayed && _loc_3 >= 0 && (_loc_3 % 60 == 0 || this.MILESTONES.indexOf(_loc_3) != -1))
            {
                this.m_prevTimeDisplayed = _loc_3;
                _loc_4 = EmbeddedArt.lightishBlueTextColor;
                if (_loc_3 <= 30)
                {
                    _loc_4 = EmbeddedArt.fireBrickRedColor;
                }
                else if (_loc_3 <= 60)
                {
                    _loc_4 = EmbeddedArt.lightOrangeTextColor;
                }
                else
                {
                    _loc_4 = EmbeddedArt.lightishBlueTextColor;
                }
                _loc_5 = GameUtil.formatMinutesSeconds(_loc_3);
                this.m_owner.displayStatus(ZLoc.t("Main", "Timeleft", {timeNum:_loc_5}), new EmbeddedArt.smallClockIcon(), _loc_4);
            }
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

    }



