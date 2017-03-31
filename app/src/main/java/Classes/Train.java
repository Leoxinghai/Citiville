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

import Classes.effects.*;
import Classes.effects.Particle.*;
import Display.TrainUI.*;
import Engine.*;
//import flash.events.*;
//import flash.geom.*;

import com.xinghai.Debug;

    public class Train extends Vehicle
    {
        private  String NPCTYPE ="train";
        protected TrainSlidePick m_pick =null ;
        protected TrainSmokeEffect m_trainSmokeEffect =null ;
        protected boolean m_smokeEffectOn =false ;
        protected boolean m_allowSmoke ;
        protected String m_friendUID ="";

        public  Train (String param1 ,boolean param2 )
        {
            super(param1, param2);
            setShowHighlight(false);
            m_typeName = this.NPCTYPE;
            this.m_allowSmoke = m_item.smokeXml.@show == "true";
            return;
        }//end

         protected void  onItemImageLoaded (Event event )
        {
            super.onItemImageLoaded(event);

            if (this.m_smokeEffectOn)
            {
                this.addSmokeEffect();
            }

            //add by xinghai
            //Global.circle.addChild(this.m_content);

            return;
        }//end

        protected void  addSmokeEffect ()
        {
            MapResourceEffect _loc_1 =null ;
            if (!this.m_trainSmokeEffect)
            {
                _loc_1 = addAnimatedEffect(EffectType.TRAIN_SMOKE);
                if (_loc_1 && _loc_1 instanceof TrainSmokeEffect)
                {
                    this.m_trainSmokeEffect =(TrainSmokeEffect) _loc_1;
                }
            }
            return;
        }//end

         public boolean  isHighlightable ()
        {
            return true;
        }//end

         public boolean  canShowHighlight ()
        {
            return false;
        }//end

         protected void  calculateDepthIndex ()
        {
            super.calculateDepthIndex();
            if (m_direction == Constants.DIRECTION_NE || m_direction == Constants.DIRECTION_NW)
            {
                m_depthIndex = m_depthIndex - 5 * 1000;
            }
            return;
        }//end

        public void  setPick (TrainSlidePick param1 )
        {
            this.m_pick = param1;
            return;
        }//end

        public void  setFriend (String param1 )
        {
            this.m_friendUID = param1;
            return;
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            Rectangle _loc_2 =null ;
            if (this.m_pick && m_displayObject && m_displayObject.getRect(m_displayObject.parent).containsPoint(param1))
            {
                _loc_2 = this.m_pick.getPickSprite().getRect(m_displayObject.parent);
                if (_loc_2.containsPoint(param1))
                {
                    return true;
                }
            }
            return super.isPixelInside(param1);
        }//end

         public String  getToolTipHeader ()
        {
            String _loc_1 =null ;
            String _loc_2 ="";
            _loc_1 = Global.player.getFriendFirstName(this.m_friendUID);
            if (_loc_1 != null)
            {
                _loc_2 = ZLoc.t("Dialogs", "TrainUI_Joes_Train", {name:ZLoc.tn(_loc_1)});
            }
            return _loc_2;
        }//end

        public void  setSmokeEnabled (boolean param1 )
        {
            this.m_smokeEffectOn = param1 && this.m_allowSmoke;
            if (this.m_smokeEffectOn && m_itemImage && !this.m_trainSmokeEffect)
            {
                this.addSmokeEffect();
            }
            if (this.m_trainSmokeEffect)
            {
                this.m_trainSmokeEffect.setIsRunning(this.m_smokeEffectOn);
            }
            return;
        }//end

        public void  adjustSmokeForTrainSpeed (double param1 )
        {
            if (this.m_trainSmokeEffect)
            {
                this.m_trainSmokeEffect.adjustForTrainSpeed(param1);
            }
            return;
        }//end

         public void  setItem (Item param1 )
        {
            super.setItem(param1);
            this.m_allowSmoke = m_item.smokeXml.@show == "true";
            if (!this.m_allowSmoke)
            {
                this.setSmokeEnabled(false);
            }
            return;
        }//end

    }


