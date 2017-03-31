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
import Engine.*;
import Engine.Helpers.*;

    public class ActionBusinessPacing extends NPCAction
    {
        protected MapResource m_business ;
        protected double m_timeStarted ;
        protected double m_pacingTimeMs ;
        protected boolean m_waving ;
        protected boolean m_checkBusiness =false ;
        public static double AVG_IDLE_DELAY_SECONDS =3;

        public  ActionBusinessPacing (NPC param1 ,MapResource param2 ,boolean param3 =true ,double param4 =2.14748e +009,double param5 =0)
        {
            super(param1);
            this.m_business = param2;
            this.m_waving = param3;
            this.m_pacingTimeMs = param4 != int.MAX_VALUE ? (param4 * 1000) : (int.MAX_VALUE);
            this.m_timeStarted = param5;
            return;
        }//end

        public ActionBusinessPacing  setCheckBusiness (boolean param1 )
        {
            this.m_checkBusiness = param1;
            return this;
        }//end

         public void  update (double param1 )
        {
            if (this.m_timeStarted == 0)
            {
                this.m_timeStarted = GlobalEngine.getTimer();
            }
            _loc_2 = this.m_business.findValidCrowdPosition ();
            _loc_3 = MathUtil.randomWobble(AVG_IDLE_DELAY_SECONDS,0.5);
            _loc_4 = GlobalEngine.getTimer ()-this.m_timeStarted ;
            if (GlobalEngine.getTimer() - this.m_timeStarted < this.m_pacingTimeMs)
            {
                if (!this.m_checkBusiness || this.m_checkBusiness && Global.world.getObjectById(this.m_business.getId()))
                {
                    m_npc.getStateMachine().addActions(new ActionNavigateBeeline(m_npc, _loc_2), new ActionPlayAnimation(m_npc, this.m_waving ? ("wave") : ("idle"), _loc_3), new ActionBusinessPacing(m_npc, this.m_business, this.m_waving, this.m_pacingTimeMs / 1000, this.m_timeStarted).setCheckBusiness(this.m_checkBusiness));
                }
            }
            m_npc.getStateMachine().popState();
            return;
        }//end

    }



