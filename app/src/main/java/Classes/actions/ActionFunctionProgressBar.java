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
import GameMode.*;
//import flash.geom.*;

    public class ActionFunctionProgressBar extends BaseAction
    {
        protected boolean m_actionStarted =false ;
        protected double m_actionTime =0;
        protected String m_text ;
        protected double m_targetTime ;
        protected Function m_progressStartFunction ;
        protected Function m_progressEndFunction ;
        protected Function m_progressCancelFunction ;
public static  double PRECISION =0.1;

        public  ActionFunctionProgressBar (MapResource param1 ,Function param2 ,Function param3 ,Function param4 ,String param5 =null ,double param6 =0)
        {
            super(param1);
            GMEdit.addLock();
            this.m_text = param5;
            this.m_targetTime = param6;
            if (this.m_text == null)
            {
                this.m_text = m_mapResource.getActionText();
            }
            if (this.m_targetTime <= 0)
            {
                this.m_targetTime = Global.gameSettings().getNumber("actionBarAny", 2);
            }
            this.m_progressStartFunction = param2;
            this.m_progressEndFunction = param3;
            this.m_progressCancelFunction = param4;
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            if (this.m_progressStartFunction != null)
            {
                if (!this.m_progressStartFunction())
                {
                    m_mapResource.actionQueue.removeState(this);
                    return;
                }
            }
            m_mapResource.lock();
            this.m_actionStarted = true;
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            if (this.m_progressStartFunction != null)
            {
                if (!this.m_progressStartFunction())
                {
                    m_mapResource.actionQueue.removeState(this);
                    return;
                }
            }
            m_mapResource.lock();
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
            m_mapResource.setActionProgress(false);
            m_mapResource.setActionBarOffset(0, 0);
            m_mapResource.unlock();
            return;
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
                if (_loc_2 > 1)
                {
                    m_mapResource.actionQueue.removeState(this);
                }
                else
                {
                    _loc_4 = m_mapResource.getProgressBarOffset();
                    m_mapResource.setActionBarOffset(Math.floor(_loc_4.x), Math.floor(_loc_4.y));
                    m_mapResource.setActionProgress(true, this.m_text, _loc_2, false);
                }
            }
            return;
        }//end

    }



