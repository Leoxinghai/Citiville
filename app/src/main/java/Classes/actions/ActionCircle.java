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

    public class ActionCircle extends NPCAction
    {
        protected double m_height ;
        protected double m_radius ;
        protected Vector3 m_source ;
        protected Vector3 m_target ;
        protected double m_velocityScale =1;
        private double m_direction =1;
        public static  String EASE_NONE ="none";
        public static  String EASE_QUAD_IN ="quadIn";
        public static  String EASE_QUAD_OUT ="quadOut";
public static Array m_turnStateToDirection =.get(2 ,11,4,10,3,9,7,8,0,15,6,14,1,13,5,12) ;

        public  ActionCircle (NPC param1 ,Vector3 param2 ,Vector3 param3 ,double param4 ,double param5 ,boolean param6 =false )
        {
            super(param1);
            this.m_source = param3;
            this.m_target = param2;
            this.m_height = param4;
            this.m_radius = param5;
            if (param6)
            {
                this.m_direction = -1;
            }
            return;
        }//end

        public ActionCircle  setVelocityScale (double param1 )
        {
            this.m_velocityScale = param1;
            return this;
        }//end

        protected double  navigationVelocity ()
        {
            return m_npc.velocityWalk * this.m_velocityScale;
        }//end

         public void  update (double param1 )
        {
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            if (param1 > 0.5)
            {
                param1 = 0.5;
            }
            _loc_2 = param1*this.navigationVelocity ;
            double _loc_3 =0;
            Vector3 _loc_4 =new Vector3(0,0,0);
            _loc_5 = this.m_target.subtract(m_npc.getPosition ());
            this.m_target.subtract(m_npc.getPosition()).z = 0;
            _loc_3 = _loc_5.length() - this.m_radius;
            if (_loc_3 <= -0.2)
            {
                _loc_4 = _loc_5.normalize().scale(Math.max(-_loc_2, _loc_3));
            }
            else if (_loc_3 >= 0.2)
            {
                _loc_4 = _loc_5.normalize().scale(Math.min(_loc_2, _loc_3));
            }
            else
            {
                _loc_8 = _loc_2 / _loc_5.length() * this.m_direction;
                _loc_9 = Math.cos(_loc_8);
                _loc_10 = Math.sin(_loc_8);
                _loc_11 = this.m_target.x - _loc_5.x * _loc_9 + _loc_5.y * _loc_10;
                _loc_12 = this.m_target.y - _loc_5.x * _loc_10 - _loc_5.y * _loc_9;
                _loc_4.x = _loc_11 - m_npc.getPosition().x;
                _loc_4.y = _loc_12 - m_npc.getPosition().y;
            }
            _loc_6 = m_npc.getPosition().add(_loc_4);
            m_npc.setPosition(_loc_6.x, _loc_6.y, _loc_6.z);
            _loc_7 = ActionNavigate.getMovementDirection(_loc_4);
            if (m_npc instanceof Helicopter)
            {
                _loc_7 = getAbsoluteMovementDirection(_loc_4);
            }
            if (_loc_7 != m_npc.getDirection())
            {
                if (m_npc instanceof Vehicle)
                {
                    ((Vehicle)m_npc).startTurnAnimation(_loc_7);
                }
                else
                {
                    m_npc.setDirection(_loc_7);
                    m_npc.setState(m_npc.getState());
                }
            }
            m_npc.conditionallyReattach();
            return;
        }//end

        protected void  updatePath ()
        {
            if (this.m_source == null)
            {
                this.m_source = m_npc.getPosition();
            }
            m_npc.setPosition(this.m_source.x, this.m_source.y, this.m_source.z);
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

        public static int  getAbsoluteMovementDirection (Vector3 param1 )
        {
            _loc_2 = Math(.atan2(param1.y ,param1.x )*(180/Math.PI )+360)% 360;
            _loc_3 = m_turnStateToDirection360/.length;
            _loc_4 = int(_loc_2/_loc_3);
            return m_turnStateToDirection.get(_loc_4);
        }//end

        public static double  easeInQuad (double param1 ,double param2 ,double param3 ,double param4 )
        {
            param1 = param1 / param2;
            return param4 * param1 * param1 + param3;
        }//end

        public static double  easeOutQuad (double param1 ,double param2 ,double param3 ,double param4 )
        {
            param1 = param1 / param2;
            return (-param4) * param1 * (param1 - 2) + param3;
        }//end

    }



