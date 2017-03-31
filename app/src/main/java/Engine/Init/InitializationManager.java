package Engine.Init;

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

import Engine.*;
import Engine.Managers.*;
import root.GlobalEngine;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.Debug;
import com.xiyu.util.Event;

public class InitializationManager extends EventDispatcher
    {
        protected Array m_actions ;
        protected Array m_runningActions ;
        protected Array m_completedActions ;
        protected Timer m_loadTimer ;
        protected boolean m_CompleteTimeRecorded =false ;
        private double _m_startTime ;
        private int m_retryDoActionsHandle =0;
        private boolean m_retryDoActionsActive =false ;
        public static InitializationManager m_instance ;
        public static boolean m_doActionsLock ;
        public static boolean m_statsReporting =false ;

        private static  int REPORT_DELAY =10000;
        private static double _m_gameSettingsLoadTime ;

        public  InitializationManager ()
        {
            super(null);
            if (InitializationManager.m_instance == null)
            {
                InitializationManager.m_instance = this;
            }
            this.m_actions = new Array();
            this.m_runningActions = new Array();
            this.m_completedActions = new Array();
            m_doActionsLock = false;
            if (Math.floor(Utilities.randBetween(1, 100)) == 50)
            {
                m_statsReporting = true;
            }
            this.m_startTime = GlobalEngine.getTimer();
            if (m_statsReporting)
            {
                StatsManager.count("Initialization", "Begin");
                this.m_loadTimer = new Timer(REPORT_DELAY);
                this.m_loadTimer.addEventListener(TimerEvent.TIMER, this.onTimerCallback);
                this.m_loadTimer.start();
            }
            return;
        }//end

        public double  m_startTime ()
        {
            return this._m_startTime;
        }//end

        public void  m_startTime (double param1 )
        {
            this._m_startTime = param1;
            return;
        }//end

        public double  m_gameSettingsLoadTime ()
        {
            return _m_gameSettingsLoadTime;
        }//end

        public void  m_gameSettingsLoadTime (double param1 )
        {
            _m_gameSettingsLoadTime = param1;
            return;
        }//end

        public void  add (InitializationAction param1 )
        {
            this.m_actions.push(param1);
            return;
        }//end

        public void  execute ()
        {
            this._doActions(null);
            return;
        }//end

        public boolean  hasActionCompleted (String param1 )
        {
            return this.m_completedActions.indexOf(param1) >= 0;
        }//end

        public Array  getDependentActions (String param1 )
        {
            InitializationAction _loc_4 =null ;
            Array _loc_2 =new Array();
            int _loc_3 =0;
            while (_loc_3 < this.m_actions.length())
            {

                  _loc_4 = (InitializationAction)this.m_actions.get(_loc_3);
                if (_loc_4.getDependencies().indexOf(param1) >= 0)
                {
                    _loc_2.push(_loc_4);
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        private Array  _getIncompleteActions ()
        {
            InitializationAction _loc_3 =null ;
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            while (_loc_2 < this.m_actions.length())
            {

                _loc_3 = this.m_actions.get(_loc_2);
                if (!_loc_3.hasCompleted())
                {
                    _loc_1.push(_loc_3);
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        private boolean  _isActionRunning (String param1 )
        {
            return this.m_runningActions.indexOf(param1) >= 0;
        }//end

        private void  _doActions (Event event  )
        {
            InitializationAction _loc_4 =null ;
            if (m_doActionsLock)
            {
                if (!this.m_retryDoActionsActive)
                {
                    this.m_retryDoActionsHandle = setTimeout(this.retryDoActions, 0);
                    this.m_retryDoActionsActive = true;
                }
                return;
            }
            m_doActionsLock = true;
            Array _loc_2 =this._getIncompleteActions ();

            Debug.debug4("InitializationManager._getIncompleteActions"+ _loc_2.length() );

            if (this.m_runningActions.length() <= 0 && _loc_2.length() <= 0)
            {
                if (this.m_retryDoActionsActive)
                {
                    clearTimeout(this.m_retryDoActionsHandle);
                    this.m_retryDoActionsActive = false;
                }
                dispatchEvent(new Event(Event.COMPLETE));
                this.recordFinishTime();
            }
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = _loc_2.get(_loc_3);
                if (!this._isActionRunning(_loc_4.getActionId()) && _loc_4.isReady())
                {
                    GlobalEngine.msg("Init", _loc_4.getActionId() + " started");
                    _loc_4.startTime = getTimer();
                    _loc_4.addEventListener(Event.COMPLETE, this._onActionComplete);

	                Debug.debug4("InitializationManager.m_runningActions"+ _loc_4.getActionId() );

                    this.m_runningActions.push(_loc_4.getActionId());
                    _loc_4.execute();
                }
                _loc_3++;
            }
            m_doActionsLock = false;
            return;
        }//end

        private void  retryDoActions ()
        {
            this.m_retryDoActionsActive = false;
            this.m_retryDoActionsHandle = 0;
            this._doActions(null);
            return;
        }//end

        public boolean  haveAllCompleted ()
        {

            Debug.debug7("InitializatinManager.haveAllCompleted." + this.m_runningActions.length() + ";" + this._getIncompleteActions().length );
            return this.m_runningActions.length() <= 0 && this._getIncompleteActions().length() <= 0;
        }//end

        private void  _onActionComplete (Event event )
        {
            String _loc_8 =null ;
            InitializationAction _loc_2 =(InitializationAction) event.target;
            int _loc_3 = getTimer()-_loc_2.startTime;
            int _loc_4 = _loc_3/1000;
            GlobalEngine.log("Init", _loc_2.getActionId() + " completed (" + _loc_4 + " sec)");
            if (_loc_2.getActionId() == "GameSettingsDownloadInit")
            {
                this.m_gameSettingsLoadTime = _loc_3;
            }
            this.m_completedActions.push(_loc_2.getActionId());
            ProgressEvent _loc_5 =new ProgressEvent(ProgressEvent.PROGRESS );
            _loc_5.bytesLoaded = this.m_completedActions.length;
            _loc_5.bytesTotal = this.m_actions.length;
            dispatchEvent(_loc_5);
            Array _loc_6 =new Array ();
            int _loc_7 =0;
            while (_loc_7 < this.m_runningActions.length())
            {

                _loc_8 = this.m_runningActions.get(_loc_7);
                if (_loc_8 != _loc_2.getActionId())
                {
                    _loc_6.push(_loc_8);
                }
                _loc_7++;
            }
            this.m_runningActions = _loc_6;
            this._doActions(null);
            return;
        }//end

        private InitializationAction  _actionById (String param1 )
        {
            InitializationAction _loc_4 =null ;
            InitializationAction _loc_2 =null ;
            int _loc_3 =0;
            while (_loc_2 == null && _loc_3 < this.m_actions.length())
            {

                _loc_4 =(InitializationAction) this.m_actions.get(_loc_3);
                if (_loc_4.getActionId() == param1)
                {
                    _loc_2 = _loc_4;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        private void  onTimerCallback (TimerEvent event )
        {
            if (this.haveAllCompleted())
            {
                StatsManager.count("Initialization", "Finished", this.m_loadTimer.currentCount * REPORT_DELAY / 1000 + " seconds");
                this.recordFinishTime();
                this.m_loadTimer.stop();
                this.m_loadTimer = null;
            }
            else
            {
                StatsManager.count("Initialization", "Not Finished", this.m_loadTimer.currentCount * REPORT_DELAY / 1000 + " seconds", "Actions running: " + this.m_runningActions.toString());
            }
            return;
        }//end

        private void  recordFinishTime ()
        {
            double _loc_1 =0;
            if (!this.m_CompleteTimeRecorded)
            {
                this.m_CompleteTimeRecorded = true;
                if (m_statsReporting)
                {
                    _loc_1 = GlobalEngine.getTimer() - this.m_startTime;
                    StatsManager.count("Initialization", "Elapsed Time", "", "", "", "", Math.floor(_loc_1 / 1000));
                }
            }
            return;
        }//end

        public String  getRunningActionsString ()
        {
            String _loc_1 ="initNoRunningActions";
            if (this.m_runningActions.length() > 0)
            {
                _loc_1 = "init:" + this.m_runningActions.join(",");
            }
            return _loc_1;
        }//end

        public static InitializationManager  getInstance ()
        {
            return m_instance;
        }//end

    }



