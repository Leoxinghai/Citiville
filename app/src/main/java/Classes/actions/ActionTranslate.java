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

    public class ActionTranslate extends NPCAction
    {
        protected Vector3 m_startPos ;
        protected Vector3 m_endPos ;
        protected double m_progress ;
        protected double m_duration ;

        public  ActionTranslate (NPC param1 ,double param2 ,Vector3 param3 ,Vector3 param4 =null )
        {
            super(param1);
            this.m_startPos = param4;
            this.m_endPos = param3;
            this.m_duration = param2;
            this.m_progress = 0;
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            this.resetTranslation();
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            this.resetTranslation();
            return;
        }//end

        protected void  resetTranslation ()
        {
            if (this.m_startPos == null)
            {
                this.m_startPos = m_npc.getPosition();
            }
            this.m_progress = 0;
            _loc_1 = this.m_endPos.subtract(this.m_startPos );
            _loc_2 = this.getMovementDirection(_loc_1 );
            if (_loc_2 != m_npc.getDirection())
            {
                m_npc.setDirection(_loc_2);
                m_npc.setState(m_npc.getState());
            }
            return;
        }//end

         public void  update (double param1 )
        {
            this.m_progress = this.m_progress + param1;
            _loc_2 = this.m_progress /this.m_duration ;
            if (_loc_2 >= 1)
            {
                _loc_2 = 1;
                m_npc.getStateMachine().removeState(this);
            }
            _loc_3 = Vector3.lerp(this.m_startPos,this.m_endPos,_loc_2);
            m_npc.setPosition(_loc_3.x, _loc_3.y);
            m_npc.conditionallyReattach();
            return;
        }//end

        protected int  getMovementDirection (Vector3 param1 ,double param2)
        {
            if (Math.abs(param1.x) > Math.abs(param1.y))
            {
                if (param1.x > param2)
                {
                    return Constants.DIRECTION_NE;
                }
                if (param1.x < -param2)
                {
                    return Constants.DIRECTION_SW;
                }
            }
            else
            {
                if (param1.y > param2)
                {
                    return Constants.DIRECTION_NW;
                }
                if (param1.y < -param2)
                {
                    return Constants.DIRECTION_SE;
                }
            }
            return Constants.DIRECTION_MAX;
        }//end

         public int  getInterrupt ()
        {
            return NO_INTERRUPT;
        }//end

    }



