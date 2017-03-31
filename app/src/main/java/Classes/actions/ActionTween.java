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
import com.greensock.*;

    public class ActionTween extends NPCAction
    {
        protected int m_tweenType ;
        protected double m_duration ;
        protected Object m_vars ;
        protected Function m_onComplete ;
        public static  int TO =0;
        public static  int FROM =1;

        public  ActionTween (NPC param1 ,int param2 ,double param3 ,Object param4 )
        {
            super(param1);
            this.m_tweenType = param2;
            this.m_duration = param3;
            this.m_vars = param4;
            if (this.m_vars.get("onComplete"))
            {
                this.m_onComplete = this.m_vars.onComplete;
            }
            else
            {
                this.m_onComplete = null;
            }
            this.m_vars.onComplete = this.onTweenComplete;
            return;
        }//end  

         public void  enter ()
        {
            super.enter();
            this.startTween();
            return;
        }//end  

         public void  reenter ()
        {
            super.reenter();
            this.startTween();
            return;
        }//end  

        protected void  startTween ()
        {
            switch(this.m_tweenType)
            {
                case TO:
                {
                    TweenLite.to(m_npc, this.m_duration, this.m_vars);
                    break;
                }
                case FROM:
                {
                    TweenLite.from(m_npc, this.m_duration, this.m_vars);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end  

        protected void  onTweenComplete ()
        {
            if (this.m_onComplete != null)
            {
                this.m_onComplete.apply();
            }
            m_npc.getStateMachine().removeState(this);
            return;
        }//end  

         public int  getInterrupt ()
        {
            return NO_INTERRUPT;
        }//end  

    }



