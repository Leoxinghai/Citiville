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
import Engine.Helpers.*;

    public class ActionElevate extends NPCAction
    {
        protected double m_sourceElevation ;
        protected double m_targetElevation ;
        protected double m_velocityScale =1;
        protected int m_direction =-1;
        protected String m_ease ;
        private double m_duration =0;
        private double m_elapsed =0;
        private double m_progress =0;

        public  ActionElevate (NPC param1 ,double param2 ,double param3 =-1)
        {
            this.m_ease = ActionNavigateBeeline.EASE_NONE;
            super(param1);
            this.m_sourceElevation = param3;
            this.m_targetElevation = param2;
            return;
        }//end

        public ActionElevate  setVelocityScale (double param1 )
        {
            this.m_velocityScale = param1;
            return this;
        }//end

        public ActionElevate  setEase (String param1 )
        {
            this.m_ease = param1;
            return this;
        }//end

        public double  duration ()
        {
            return this.m_duration;
        }//end

        public double  elapsed ()
        {
            return this.m_elapsed;
        }//end

        public ActionElevate  setDirection (int param1 )
        {
            this.m_direction = param1;
            return this;
        }//end

        protected double  navigationVelocity ()
        {
            return m_npc.velocityWalk * this.m_velocityScale;
        }//end

         public void  update (double param1 )
        {
            this.m_elapsed = this.m_elapsed + param1;
            _loc_2 = this.m_progress ;
            _loc_3 = Math.abs(this.m_sourceElevation -this.m_targetElevation );
            switch(this.m_ease)
            {
                case ActionNavigateBeeline.EASE_NONE:
                {
                    this.m_progress = this.m_progress + param1 * this.navigationVelocity;
                    break;
                }
                case ActionNavigateBeeline.EASE_QUAD_IN:
                {
                    this.m_progress = ActionNavigateBeeline.easeInQuad(this.m_elapsed, this.m_duration, 0, _loc_3);
                    break;
                }
                case ActionNavigateBeeline.EASE_QUAD_OUT:
                {
                    this.m_progress = ActionNavigateBeeline.easeOutQuad(this.m_elapsed, this.m_duration, 0, _loc_3);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_4 = int((this.m_targetElevation-this.m_sourceElevation)/Math.abs(this.m_targetElevation-this.m_sourceElevation));
            _loc_5 = Math.abs(this.m_progress -_loc_2 )*_loc_4 ;
            _loc_6 = m_npc.getPosition();
            m_npc.setPosition(_loc_6.x, _loc_6.y, _loc_6.z + _loc_5);
            if (this.m_direction != m_npc.getDirection() && this.m_direction != -1)
            {
                if (m_npc instanceof Vehicle)
                {
                    ((Vehicle)m_npc).startTurnAnimation(this.m_direction);
                }
                else
                {
                    m_npc.setDirection(this.m_direction);
                    m_npc.setState(m_npc.getState());
                }
            }
            if (this.m_progress >= _loc_3 || this.m_elapsed >= this.m_duration || m_npc.positionZ < 0)
            {
                m_npc.setPosition(_loc_6.x, _loc_6.y, this.m_targetElevation);
                m_npc.getStateMachine().removeState(this);
            }
            m_npc.conditionallyReattach();
            return;
        }//end

        protected void  updatePath ()
        {
            if (this.m_sourceElevation == -1)
            {
                this.m_sourceElevation = m_npc.positionZ;
            }
            _loc_1 = Math.abs(this.m_sourceElevation -this.m_targetElevation );
            this.m_duration = _loc_1 / this.navigationVelocity;
            this.m_elapsed = 0;
            this.m_progress = 0;
            m_npc.setPosition(m_npc.positionX, m_npc.positionY, this.m_sourceElevation);
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.updatePath();
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.updatePath();
            return;
        }//end

    }



