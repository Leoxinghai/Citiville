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
import Classes.sim.*;
import Engine.Helpers.*;

    public class ActionNavigateBeeline extends NPCAction
    {
        protected Array m_path ;
        protected Vector3 m_source ;
        protected Vector3 m_target ;
        protected double m_velocityScale =1;
        public static  String EASE_NONE ="none";
        public static  String EASE_QUAD_IN ="quadIn";
        public static  String EASE_QUAD_OUT ="quadOut";
        public static Array m_turnStateToDirection =.get(2 ,11,4,10,3,9,7,8,0,15,6,14,1,13,5,12) ;

        public  ActionNavigateBeeline (NPC param1 ,Vector3 param2 ,Vector3 param3 =null )
        {
            super(param1);
            this.m_source = param3;
            this.m_target = param2;
            return;
        }//end

        public ActionNavigateBeeline  setVelocityScale (double param1 )
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
            PathElement _loc_8 =null ;
            Vector3 _loc_9 =null ;
            if (param1 > 0.5)
            {
                param1 = 0.5;
            }
            if (!this.m_path || this.m_path.length == 0)
            {
                m_npc.getStateMachine().popState();
                return;
            }
            if (Global.world.citySim.roadManager.isShowingOverlay)
            {
                m_npc.m_navDbgPath = this.m_path;
            }
            else
            {
                m_npc.m_navDbgPath = null;
            }
            _loc_2 = param1*this.navigationVelocity ;
            boolean _loc_3 =false ;
            Vector3 _loc_4 =null ;
            do
            {

                Vector3.free(_loc_4);
                _loc_8 = this.m_path.get(0);
                _loc_9 = _loc_8.offsetPosition;
                _loc_4 = _loc_9.subtract(m_npc.getPositionNoClone());
                _loc_3 = _loc_4.length() > _loc_2;
                if (!_loc_3)
                {
                    m_npc.setPosition(_loc_9.x, _loc_9.y, _loc_9.z);
                    _loc_2 = _loc_2 - _loc_4.length();
                    this.m_path.shift();
                }
                if (this.m_path.length == 0)
                {
                    _loc_3 = true;
                }
            }while (!_loc_3)
            _loc_5 = _loc_4.length ()>0? (_loc_4.normalize(_loc_2)) : (new Vector3(0, 0, 0));
            _loc_6 = m_npc.getPositionNoClone().add(_loc_5);
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
            Vector3.free(_loc_4);
            Vector3.free(_loc_5);
            Vector3.free(_loc_6);
            return;
        }//end

        protected void  updatePath ()
        {
            if (this.m_source == null)
            {
                this.m_source = m_npc.getPosition();
            }
            m_npc.setPosition(this.m_source.x, this.m_source.y, this.m_source.z);
            this.m_path = .get(new PathElement(this.m_target, this.m_target));
            this.debugPrintPath();
            return;
        }//end

        protected void  debugPrintPath ()
        {
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



