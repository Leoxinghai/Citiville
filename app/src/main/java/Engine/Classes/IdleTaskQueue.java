package Engine.Classes;

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

import Engine.Managers.*;
//import flash.utils.*;

    public class IdleTaskQueue
    {
        private Array m_Queue ;
        private double m_LastTaskExecuted =0;
        private boolean m_PopupQueueEmpty =false ;
        private double m_PopupQueueEmptySince =0;
        private boolean m_ActionQueueEmpty =false ;
        private double m_ActionQueueEmptySince =0;
        private double m_nextUpdateTime =0;
        private boolean m_queuePaused =false ;
public static IdleTaskQueue m_instance ;
        private static  double UPDATE_INTERVAL =1000;

        public  IdleTaskQueue ()
        {
            this.m_Queue = new Array();
            return;
        }//end

        public void  addTask (BaseIdleTask param1 )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            BaseIdleTask _loc_5 =null ;
            _loc_2 = param1.shouldAdd ;
            if (_loc_2 && param1.singleton)
            {
                _loc_3 = getQualifiedClassName(param1);
                _loc_4 = 0;
                while (_loc_4 < this.m_Queue.length())
                {

                    _loc_5 = this.m_Queue.get(_loc_4);
                    if (getQualifiedClassName(_loc_5) == _loc_3)
                    {
                        if (param1.singletonClobber)
                        {
                            this.m_Queue.splice(_loc_4, 1);
                            _loc_2 = true;
                        }
                        else
                        {
                            _loc_2 = false;
                        }
                        break;
                    }
                    _loc_4++;
                }
            }
            if (_loc_2)
            {
                this.m_Queue.push(param1);
            }
            return;
        }//end

        public void  checkTasks ()
        {
            BaseIdleTask _loc_1 =null ;
            int _loc_2 =0;
            BaseIdleTask _loc_3 =null ;
            if (GlobalEngine.currentTime > this.m_nextUpdateTime && !this.m_queuePaused)
            {
                this.m_nextUpdateTime = GlobalEngine.currentTime + IdleTaskQueue.UPDATE_INTERVAL;
                this.checkTimeStamps();
                if (this.m_Queue.length > 0)
                {
                    _loc_2 = 0;
                    while (_loc_2 < this.m_Queue.length())
                    {

                        _loc_3 = this.m_Queue.get(_loc_2);
                        if (this.checkIfTaskCanRun(_loc_3))
                        {
                            _loc_1 = _loc_3;
                            this.m_Queue.splice(_loc_2, 1);
                            break;
                        }
                        _loc_2++;
                    }
                    if (_loc_1 != null)
                    {
                        this.m_LastTaskExecuted = GlobalEngine.currentTime;
                        _loc_1.fire();
                    }
                }
            }
            return;
        }//end

        private boolean  checkIfTaskCanRun (BaseIdleTask param1 )
        {
            _loc_2 = GlobalEngine.currentTime ;
            boolean _loc_3 =true ;
            if (param1.requireActionQueueIdle)
            {
                if (!this.m_ActionQueueEmpty || _loc_2 - this.m_ActionQueueEmptySince < param1.minActionIdleMilliseconds)
                {
                    _loc_3 = false;
                }
            }
            if (_loc_3 && param1.requireMouseIdle)
            {
                if (_loc_2 - GlobalEngine.lastInputTick < param1.minMouseIdleMilliseconds)
                {
                    _loc_3 = false;
                }
            }
            if (_loc_3 && param1.requirePopupQueueEmpty)
            {
                if (!this.m_PopupQueueEmpty || _loc_2 - this.m_PopupQueueEmptySince < param1.minPopupIdleMilliseconds)
                {
                    _loc_3 = false;
                }
            }
            if (_loc_3 && _loc_2 - this.m_LastTaskExecuted < param1.queueCoolDownMilliseconds)
            {
                _loc_3 = false;
            }
            if (_loc_3 && !param1.canFire)
            {
                _loc_3 = false;
            }
            return _loc_3;
        }//end

        private void  checkTimeStamps ()
        {
            if (PopupQueueManager.getInstance().hasActiveDialog() == false)
            {
                if (!this.m_PopupQueueEmpty)
                {
                    this.m_PopupQueueEmptySince = GlobalEngine.currentTime;
                }
                this.m_PopupQueueEmpty = true;
            }
            else
            {
                this.m_PopupQueueEmpty = false;
            }
            if (this.isQueuedActionsEmpty())
            {
                if (!this.m_ActionQueueEmpty)
                {
                    this.m_ActionQueueEmptySince = GlobalEngine.currentTime;
                }
                this.m_ActionQueueEmpty = true;
            }
            else
            {
                this.m_ActionQueueEmpty = false;
            }
            return;
        }//end

        protected boolean  isQueuedActionsEmpty ()
        {
            return true;
        }//end

        public void  togglePause (boolean param1 )
        {
            this.m_queuePaused = param1;
            return;
        }//end

        public void  clearQueue ()
        {
            this.m_Queue = new Array();
            return;
        }//end

        public static IdleTaskQueue  getInstance ()
        {
            Class _loc_1 =null ;
            if (m_instance == null)
            {
                _loc_1 = GlobalEngine.engineOptions.idleTaskQueue;
                m_instance = new _loc_1;
            }
            return m_instance;
        }//end

    }



