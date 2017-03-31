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

import Classes.sim.*;
import Engine.*;
//import flash.display.*;

    public class Vehicle extends DesirePeep
    {
        private  double TURN_SPEED =20;
        protected int m_goalTurnState ;
        protected double m_turnStateProgress ;
        private static Array m_turnStateToDirection =.get(0 ,8,7,9,3,10,4,11,2,12,5,13,1,14,6,15) ;
        private static Array m_directionToTurnState =.get(0 ,12,8,4,6,10,14,2,1,3,5,7,9,11,13,15) ;

        public  Vehicle (String param1 ,boolean param2 ,double param3 =-1)
        {
            super(param1, param2);
            this.m_goalTurnState = m_directionToTurnState.get(m_direction);
            this.m_turnStateProgress = this.m_goalTurnState;
            return;
        }//end

         public boolean  isVehicle ()
        {
            return true;
        }//end

        public void  preloadImages (double param1 )
        {
            double _loc_2 =0;
            while (_loc_2 < param1)
            {

                m_item.getCachedImage("static", this, _loc_2);
                _loc_2 = _loc_2 + 1;
            }
            return;
        }//end

         protected Sprite  createFeedbackBubble (DisplayObject param1 )
        {
            Sprite _loc_3 =null ;
            _loc_2 = new EmbeddedArt.factoryTruckBubble ();
            _loc_3 = new Sprite();
            _loc_3.addChild(_loc_2);
            _loc_3.addChild(param1);
            param1.x = (_loc_3.width - param1.width) / 2;
            param1.y = (_loc_3.height - param1.height) / 2;
            return _loc_3;
        }//end

         public void  showDeclineEntranceBubble ()
        {
            return;
        }//end

         public void  showSmileFeedbackBubble ()
        {
            return;
        }//end

         public void  showGoingToHotelFeedbackBubble ()
        {
            return;
        }//end

         public void  showCannotEnterHotelFeedbackBubble ()
        {
            return;
        }//end

         public int  getPathTypeToBusiness ()
        {
            return RoadManager.PATH_ROAD_ONLY;
        }//end

         public int  getPathTypeToResidence ()
        {
            return RoadManager.PATH_ROAD_ONLY;
        }//end

         public boolean  canWander ()
        {
            return false;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.updateTurnAnimation(param1);
            return;
        }//end

        public void  startTurnAnimation (int param1 )
        {
            if (m_directionToTurnState.get(param1) != this.m_goalTurnState)
            {
                this.m_goalTurnState = m_directionToTurnState.get(param1);
                this.m_turnStateProgress = m_directionToTurnState.get(m_direction);
            }
            return;
        }//end

        protected void  updateTurnAnimation (double param1 )
        {
            if (param1 > 1 / this.TURN_SPEED)
            {
                param1 = 1 / this.TURN_SPEED;
            }
            if (Math.floor(this.m_turnStateProgress) == this.m_goalTurnState)
            {
                this.m_turnStateProgress = this.m_goalTurnState;
                return;
            }
            this.m_turnStateProgress = this.m_turnStateProgress + param1 * this.TURN_SPEED * this.getQuickestDirection();
            this.m_turnStateProgress = MathUtil.wrap(this.m_turnStateProgress, Item.DIRECTION_16_MAX, 0);
            setDirection(m_turnStateToDirection.get(Math.floor(this.m_turnStateProgress)));
            setState(getState());
            return;
        }//end

        protected int  getQuickestDirection ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            _loc_3 = this.m_goalTurnState >this.m_turnStateProgress ? (-1) : (1);
            if (_loc_3 == -1)
            {
                _loc_1 = this.m_goalTurnState - this.m_turnStateProgress;
                _loc_2 = this.m_turnStateProgress + (Item.DIRECTION_16_MAX - this.m_goalTurnState);
            }
            else
            {
                _loc_1 = this.m_turnStateProgress - this.m_goalTurnState;
                _loc_2 = Item.DIRECTION_16_MAX - this.m_turnStateProgress + this.m_goalTurnState;
            }
            return _loc_2 <= _loc_1 ? (_loc_3) : (_loc_3 * -1);
        }//end

    }


