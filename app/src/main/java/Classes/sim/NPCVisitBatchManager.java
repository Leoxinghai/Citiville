package Classes.sim;

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
import Classes.ClientDataObjects.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Transactions.*;
//import flash.events.*;
//import flash.utils.*;

    public class NPCVisitBatchManager implements IGameWorldStateObserver
    {
        protected Dictionary m_transactionList =null ;
        protected int m_updates =0;
        protected Timer m_timer =null ;
        public static  double MAX_BATCH_DELAY_SECONDS =20;
        public static  int MAX_MECHANICS_AFFECTED =50;
        public static  int MAX_NUMBER_OF_UPDATES =100;

        public  NPCVisitBatchManager (GameWorld param1 )
        {
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            this.flushVisits();
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        protected void  startTimer ()
        {
            if (this.m_timer == null)
            {
                this.m_timer = new Timer(MAX_BATCH_DELAY_SECONDS * 1000);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.onTimerComplete);
                this.m_timer.start();
            }
            return;
        }//end

        public void  addMechanicAction (MechanicMapResource param1 ,String param2 ,String param3 ,String param4 ,int param5 =1,NPC param6 =null ,boolean param7 =false )
        {
            NPCVisitBatchObject _loc_9 =null ;
            _loc_8 = param1.getId ();
            if (this.m_transactionList == null)
            {
                this.m_transactionList = new Dictionary();
            }
            if (this.m_transactionList.get(_loc_8) && this.m_transactionList.get(_loc_8).get(param3))
            {
                _loc_9 =(NPCVisitBatchObject) this.m_transactionList.get(_loc_8).get(param3);
                _loc_9.addNPCVisit(param4, param5, param6);
                if (_loc_9.count >= MAX_NUMBER_OF_UPDATES)
                {
                    param7 = true;
                }
            }
            else
            {
                this.m_updates++;
                if (!this.m_transactionList.get(_loc_8))
                {
                    this.m_transactionList.put(_loc_8,  new Dictionary());
                }
                this.m_transactionList.get(_loc_8).put(param3,  new NPCVisitBatchObject(param2, param4, param1.getItem().type, param5, param6));
                if (this.m_updates >= MAX_MECHANICS_AFFECTED)
                {
                    param7 = true;
                }
            }
            if (param7)
            {
                this.flushVisits();
            }
            else
            {
                this.startTimer();
            }
            return;
        }//end

        public void  onQueueForceAction ()
        {
            this.flushVisits();
            return;
        }//end

        protected void  onTimerComplete (TimerEvent event )
        {
            this.flushVisits();
            return;
        }//end

        protected void  flushVisits ()
        {
            if (this.m_timer != null)
            {
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.onTimerComplete);
                this.m_timer.stop();
                this.m_timer = null;
            }
            if (this.m_transactionList)
            {
                TransactionManager.addTransaction(new TNPCVisitBatch(this.m_transactionList));
            }
            this.m_updates = 0;
            this.m_transactionList = null;
            return;
        }//end

    }



