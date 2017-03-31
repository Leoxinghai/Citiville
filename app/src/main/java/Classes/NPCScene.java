package Classes;

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

import Classes.actions.*;
//import flash.events.*;
//import flash.utils.*;

    public class NPCScene extends Prop
    {
        protected boolean m_alreadyStarted =false ;
        protected boolean m_alreadyFinished =false ;
        protected boolean m_useProgressBar =true ;
public static  double DEFAULT_SCENE_LENGTH =1;

        public  NPCScene (String param1)
        {
            super(param1);
            return;
        }//end

        public void  init ()
        {
            this.createNPCs();
            return;
        }//end

        protected void  createNPCs ()
        {
            this.startScene();
            return;
        }//end

        protected void  killNPCs ()
        {
            return;
        }//end

        protected void  startScene ()
        {
            if (this.m_alreadyStarted)
            {
                return;
            }
            this.m_alreadyStarted = true;
            if (!this.startProgress())
            {
                throw new Error("Cannot start NPC scene");
            }
            return;
        }//end

        protected void  finishScene ()
        {
            if (this.m_alreadyFinished)
            {
                return;
            }
            this.m_alreadyFinished = true;
            return;
        }//end

        protected Object startProgress (String param1 ="",double param2 =1)
        {
            ActionProgressBar _loc_3 =null ;
            Timer _loc_4 =null ;
            if (this.m_useProgressBar)
            {
                _loc_3 = new ActionProgressBar(null, this, param1, param2);
                this.actionQueue.addActions(_loc_3);
                return _loc_3;
            }
            if (this.canSceneProgressStart())
            {
                _loc_4 = new Timer(param2);
                _loc_4.addEventListener(TimerEvent.TIMER, this.finishSceneTimer, false, 0, true);
                _loc_4.start();
                return _loc_4;
            }
            this.cancelSceneProgress();
            return true;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            return this.canSceneProgressStart;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return this.finishSceneProgress;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return this.cancelSceneProgress;
        }//end

        protected boolean  canSceneProgressStart ()
        {
            return true;
        }//end

        protected void  cancelSceneProgress ()
        {
            return;
        }//end

        protected void  finishSceneProgress ()
        {
            this.finishScene();
            return;
        }//end

        protected void  finishSceneTimer (TimerEvent event )
        {
            this.finishScene();
            if (event.target && event.target instanceof Timer)
            {
                ((Timer)event.target).stop();
            }
            return;
        }//end

    }


