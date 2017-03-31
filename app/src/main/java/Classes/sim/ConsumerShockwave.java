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
import Classes.effects.*;
import Classes.util.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class ConsumerShockwave
    {
        protected MapResource m_origin ;
        protected Vector3 m_center ;
        protected double m_velocity ;
        protected double m_maxRadius ;
        protected Function m_npcConsumer ;
        protected Function m_endCallback ;
        protected int m_startTime ;
        protected Dictionary m_seenNpcs ;
        protected DisplayObject m_animation ;
        protected int m_shockwaveColor ;
        protected double m_tileRadius ;
        protected Timer m_killTimer ;
        protected boolean m_fadeHalfway ;
        public static  double ORTHO_FACTOR =1.41421;
public static  double NPC_SHOCKWAVE_RADIUS =1;
public static  int NPC_SHOCKWAVE_COLOR =6946560;
public static  double NPC_SHOCKWAVE_VELOCITY_FACTOR =10;

        public  ConsumerShockwave (MapResource param1 ,double param2 ,double param3 ,Function param4 =null ,Function param5 =null ,boolean param6 =false ,int param7 =16755200)
        {
            this.m_seenNpcs = new Dictionary(true);
            this.m_origin = param1;
            this.m_velocity = param2;
            this.m_maxRadius = param3;
            this.m_npcConsumer = param4;
            this.m_endCallback = param5;
            this.m_shockwaveColor = param7;
            this.m_fadeHalfway = param6;
            _loc_8 = this.m_origin.getPositionNoClone ();
            _loc_9 = this.m_origin.getSizeNoClone ();
            this.m_center = new Vector3(_loc_8.x + _loc_9.x / 2, _loc_8.y + _loc_9.y / 2);
            return;
        }//end

        protected void  createWave ()
        {
            _loc_1 = IsoMath.tilePosToPixelPos(this.m_center.x ,this.m_center.y );
            _loc_6 = new EmbeddedArt.businessShockwave ();
            this.m_animation = new EmbeddedArt.businessShockwave();
            _loc_2 = _loc_6;
            _loc_3 = _loc_2.transform.colorTransform ;
            _loc_3.color = this.m_shockwaveColor;
            _loc_2.transform.colorTransform = _loc_3;
            _loc_4 = this.m_origin instanceof HarvestableResource ? ("road") : ("default");
            _loc_5 =Global.world.getObjectLayerByName(_loc_4 ).getDisplayObject ()as Sprite ;
            (Global.world.getObjectLayerByName(_loc_4).getDisplayObject() as Sprite).addChild(this.m_animation);
            this.m_animation.x = _loc_1.x;
            this.m_animation.y = _loc_1.y;
            return;
        }//end

        public void  start ()
        {
            this.m_origin.lock();
            Global.stage.addEventListener(Event.ENTER_FRAME, this.onEnterFrame, false, 0, false);
            this.m_startTime = getTimer();
            this.createWave();
            this.updateVisuals();
            return;
        }//end

        public void  freeze ()
        {
            Global.stage.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            return;
        }//end

        public void  stop ()
        {
            Global.stage.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            remover = Curry.curry(function(param1DisplayObject)
            {
                if (param1.parent != null)
                {
                    param1.parent.removeChild(param1);
                }
                return;
            }//end
            , this.m_animation);
            fadeout = Global.gameSettings().getNumber("bizWaveFade",1);
            TweenLite.to(this.m_animation, fadeout, {alpha:0, onComplete:remover});
            if (this.m_origin)
            {
                Global.world.citySim.poiManager.clearAllNPCs(this.m_origin);
            }
            this.m_origin.unlock();
            this.m_animation = null;
            this.m_seenNpcs = null;
            this.m_origin = null;
            this.m_npcConsumer = null;
            this.m_endCallback = null;
            this.m_seenNpcs = null;
            return;
        }//end

        protected void  onEnterFrame (Event event )
        {
            NPC candidate ;
            ConsumerShockwave shockWave ;
            double fadeout ;
            event = event;
            if (this.m_origin == null)
            {
                this.stop();
                return;
            }
            this.updateVisuals();
            checker = function(param1WorldObject)
            {
                if (!(param1 instanceof NPC) || (IMerchant)m_origin == null)
                {
                    return false;
                }
                _loc_2 = param1as NPC ;
                return _loc_2 != null && _loc_2.isFreeAgent && m_seenNpcs != null && m_seenNpcs.get(_loc_2) == null && _loc_2.getPositionNoClone().subtract(m_center).length() <= m_tileRadius;
            }//end
            ;
            freshmeat = Global.world.getObjectsByPredicate(checker);
            if (freshmeat.length > 0 && this.m_npcConsumer != null)
            {
                int _loc_3 =0;
                _loc_4 = freshmeat;
                for(int i0 = 0; i0 < freshmeat.size(); i0++)
                {
                		candidate = freshmeat.get(i0);


                    this.m_seenNpcs.put(candidate,  true);
                    shockWave = new ConsumerShockwave(candidate, this.m_velocity / NPC_SHOCKWAVE_VELOCITY_FACTOR, NPC_SHOCKWAVE_RADIUS, null, null, true);
                    candidate.addAnimatedEffect(EffectType.SHORT_GLOW);
                    TimerUtil .callLater (void  ()
            {
                candidate.addAnimatedEffect(EffectType.COIN);
                return;
            }//end
            , 800);
                    shockWave.start();
                }
                this.m_npcConsumer(freshmeat);
            }
            if (this.m_fadeHalfway && this.m_tileRadius >= this.m_maxRadius / 2)
            {
                fadeout;
                TweenLite.to(this.m_animation, fadeout, {alpha:0});
                this.m_fadeHalfway = false;
            }
            if (this.m_tileRadius >= this.m_maxRadius)
            {
                if (this.m_endCallback != null)
                {
                    this.m_endCallback();
                }
                else
                {
                    this.stop();
                }
            }
            return;
        }//end

        public void  setColor (int param1 )
        {
            this.m_shockwaveColor = param1;
            return;
        }//end

        protected void  updateVisuals ()
        {
            Point _loc_5 =null ;
            _loc_1 = Constants.TILE_WIDTH*ORTHO_FACTOR/200;
            _loc_2 = this.m_origin.getItem ();
            if (_loc_2 == null)
            {
                this.stop();
                return;
            }
            _loc_3 = Math.min(_loc_2.sizeX /2,_loc_2.sizeY /2);
            _loc_4 = getTimer(()-this.m_startTime)/1000;
            this.m_tileRadius = _loc_3 + _loc_4 * this.m_velocity;
            _loc_6 = _loc_1*this.m_tileRadius;
            this.m_animation.scaleY = _loc_1 * this.m_tileRadius;
            this.m_animation.scaleX = _loc_6;
            this.m_center = new Vector3(this.m_origin.positionX + this.m_origin.sizeX / 2, this.m_origin.positionY + this.m_origin.sizeY / 2);
            _loc_5 = IsoMath.tilePosToPixelPos(this.m_center.x, this.m_center.y);
            this.m_animation.x = _loc_5.x;
            this.m_animation.y = _loc_5.y;
            return;
        }//end

    }




