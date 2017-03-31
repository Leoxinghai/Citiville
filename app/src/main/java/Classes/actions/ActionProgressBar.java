package Classes.actions;

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
import Engine.Classes.*;
import GameMode.*;
//import flash.geom.*;
//import flash.utils.*;

import com.xinghai.Debug;


    public class ActionProgressBar extends NPCAction
    {
        protected MapResource m_resource ;
        protected boolean m_actionStarted =false ;
        protected double m_actionTime =0;
        protected double m_targetTime ;
        protected MapResource m_progressResource ;
        protected MapResource m_actionResource ;
        protected String m_text ;
        protected Function m_progressStartFunction ;
        protected Function m_progressEndFunction ;
        protected Function m_progressCancelFunction ;
        protected Dictionary m_timeMap ;
        protected double m_lastPreciseTime =0;
        public static  double PRECISION =0.1;

        public  ActionProgressBar (NPC param1 ,MapResource param2 ,String param3 =null ,double param4 =-1,Function param5 =null ,Function param6 =null ,Function param7 =null )
        {
            this.m_timeMap = new Dictionary(true);
            super(param1);
            GMEdit.addLock();
            this.m_resource = param2;
            this.m_text = param3;
            this.m_targetTime = param4;
            if (this.m_text == null && this.m_resource instanceof HarvestableResource)
            {
                this.m_text = ((HarvestableResource)this.m_resource).getActionText();
            }
            if (this.m_targetTime < 0)
            {
                if (this.m_resource instanceof HarvestableResource)
                {
                    this.m_targetTime = ((HarvestableResource)this.m_resource).getHarvestTime();
                }
                else
                {
                    this.m_targetTime = Global.gameSettings().getNumber("actionBarAny", 2);
                }
            }
            this.m_progressStartFunction = param5 != null ? (param5) : (this.m_resource.getProgressBarStartFunction());
            this.m_progressEndFunction = param6 != null ? (param6) : (this.m_resource.getProgressBarEndFunction());
            this.m_progressCancelFunction = param7 != null ? (param7) : (this.m_resource.getProgressBarCancelFunction());
            if (param1 !=null)
            {
                this.m_actionResource = param1;
            }
            else
            {
                this.m_actionResource = param2;
            }
            if (param2.deferProgressBarToNPC() && param1)
            {
                this.m_progressResource = param1;
            }
            else
            {
                this.m_progressResource = param2;
            }
            return;
        }//end

        public ActionProgressBar  setTimedCallback (double param1 ,Function param2 )
        {
            if (param1 > 0 && param2 != null)
            {
                this.m_timeMap.put(param1,  param2);
            }
            return this;
        }//end

         public void  enter ()
        {
            super.enter();
            Debug.debug5("ActionProgressBar."+"enter");
            if (this.m_progressStartFunction != null)
            {
                if (!this.m_progressStartFunction())
                {
                    this.m_actionResource.actionQueue.removeState(this);
                    if (m_npc)
                    {
                        Global.world.citySim.pickupManager.clearQueue(m_npc.getItemName());
                    }
                    return;
                }
            }
            this.m_resource.lock();
            this.m_actionStarted = true;
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            Debug.debug5("ActionProgressBar."+"reenter");
            if (this.m_progressStartFunction != null)
            {
                if (!this.m_progressStartFunction())
                {
                    this.m_actionResource.actionQueue.removeState(this);
                    if (m_npc)
                    {
                        Global.world.citySim.pickupManager.clearQueue(m_npc.getItemName());
                    }
                    return;
                }
            }
            this.m_resource.lock();
            this.m_actionStarted = true;
            return;
        }//end

         public void  removed ()
        {
            super.removed();
            GMEdit.removeLock();
            if (!Global.isTransitioningWorld)
            {
                if (!this.m_actionStarted)
                {
                    if (this.m_progressCancelFunction != null)
                    {
                        this.m_progressCancelFunction();
                    }
                    return;
                }
                if (this.m_progressEndFunction != null)
                {
                    this.m_progressEndFunction();
                }
            }
            this.m_actionStarted = false;
            this.m_progressResource.setActionProgress(false);
            this.m_progressResource.setActionBarOffset(0, 0);
            this.m_resource.unlock();
            return;
        }//end

         public int  getInterrupt ()
        {
            return this.m_actionStarted ? (State.NO_INTERRUPT) : (State.NORMAL_INTERRUPT);
        }//end

         public void  update (double param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            Point _loc_4 =null ;
            if (this.m_actionStarted && !Global.isTransitioningWorld)
            {
                this.m_actionTime = this.m_actionTime + param1;
                _loc_2 = this.m_actionTime / this.m_targetTime;
                _loc_3 = Number(int(this.m_actionTime / PRECISION)) * PRECISION;
                if (this.m_timeMap.get(_loc_3) instanceof Function && _loc_3 > this.m_lastPreciseTime)
                {
                    this.m_lastPreciseTime = _loc_3;
                    Function loc_3 =(Function)this.m_timeMap.get(_loc_3);
                    loc_3();
                }
                if (_loc_2 > 1)
                {
                    this.m_actionResource.actionQueue.removeState(this);
                }
                else
                {
                    _loc_4 = this.m_progressResource.getProgressBarOffset();
                    this.m_progressResource.setActionBarOffset(Math.floor(_loc_4.x), Math.floor(_loc_4.y));
                    this.m_progressResource.setActionProgress(true, this.m_text, _loc_2, false);
                }
            }
            return;
        }//end

    }



