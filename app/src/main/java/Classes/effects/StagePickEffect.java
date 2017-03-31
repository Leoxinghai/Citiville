package Classes.effects;

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
import Classes.util.*;
import Engine.Classes.*;
import Events.*;
import com.greensock.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.geom.*;

    public class StagePickEffect extends MapResourceEffect
    {
        protected  boolean DISABLE_ANIMATION =true ;
        protected boolean m_isComplete =false ;
        private String m_currentPickName ="";
        private String m_currentModifierName ="";
        private Array m_itemImageInstances ;
        protected DisplayObject m_currentPickImage ;
        protected DisplayObject m_currentModifierImage ;
        protected DisplayObject m_guitarPickImage ;
        protected AnimatedBitmap m_guitarPickAnimation ;
        protected DisplayObject m_ministarImage ;
        protected boolean m_showMinistar =false ;
        protected boolean m_drawBackground =true ;
        protected TimelineLite m_animTimeline ;
        protected Array m_fadeTweenQueue ;
        protected double m_effectX ;
        protected double m_effectY ;
        protected boolean m_floatActive =false ;
        protected boolean m_queuedActive =false ;
        protected int m_queuedCount =0;
        protected int m_startTime =-1;
        protected int m_fadeTime =-1;
        private GlowFilter m_glow =null ;
        private int m_glowColor =65535;
        private double m_pickOffset =0;
        private boolean m_multipickToggle =true ;
        private boolean m_managerToggle =true ;
        public static  String PICK_1 ="coin1";
        public static  String PICK_1_SMALL ="coin1small";
        public static  String PICK_1_SUPERSMALL ="coin1supersmall";
        public static  String PICK_2 ="coin2";
        public static  String PICK_3 ="coin3";
        public static  String PICK_4 ="moneybag1";
        public static  String PICK_4_SMALL ="moneybag1small";
        public static  String PICK_5 ="moneybag2";
        public static  String PICK_6 ="moneybag3";
        public static  String PICK_7 ="gold1";
        public static  String PICK_8 ="gold2";
        public static  String PICK_9 ="gold3";
        public static  String PICK_10 ="diamond1";
        public static  String PICK_11 ="diamond2";
        public static  String PICK_12 ="diamond3";
        public static  String PICK_BG ="guitarpic";
        public static  String PICK_SHINE ="guitarpicanim";
        public static  String PICK_SHINE_TALL ="guitarpicanim_tall";
        public static  String MINI_STAR ="ministar";
        public static  String PICK_GOODS ="goods";
        public static  String PICK_TOURBUS ="tourBus";
        public static  String PICK_LOCK ="lock";
        public static  String PICK_POLICE ="police";
        public static  String PICK_UPGRADE ="upgrade";
        public static  String PICK_UPGRADE_HAMMER ="upgrade_hammer";
        public static  String PICK_ATTENTION ="attention";
        public static  String PICK_DONUT ="donut";
        public static  String PICK_FORKLIFT ="forklift";
        public static  String PICK_PREMIUM_GOODS ="premium_goods";
        public static  String PICK_EXCLAMATION ="exclamation";
        public static  String PICK_IN_TRANSIT ="in_transit";
        public static  String PICK_TRAIN_AWAITING_CLEARANCE ="train_awaiting_clearance";
        public static  String PICK_BINOCULARS ="binoculars";
        public static  String PICK_HARDHAT ="hardhat";
        public static  String PICK_OPENDOOR ="opendoor";
        public static  String PICK_HOTEL ="hotel";
        public static  String PICK_HEART ="heart";
        public static  String PICK_TICKET_CARNIVAL ="ticket_carnival";
        public static  String PICK_REMODEL ="remodel";
        public static  String PICK_ENERGY ="energy";
        public static  String PICK_PUMPKIN ="pumpkin";
        public static  String PICK_DOGGIE_TREAT ="doggie_treat";
        public static  String PICK_MONSTERS ="monsters";
        public static  String PICK_BLUECANDY ="bluecandy";
        public static  String PICK_REDCANDY ="redcandy";
        public static  String PICK_GREENCANDY ="greencandy";
        public static  String LOADED ="stage_pick_effect_loaded";
public static  String EFFECT_NAME ="stagePick";
public static  int PICK_FLOAT_DISTANCE =5;
public static  int PICK_Y_OFFSET =10;
public static  int PICK_Y_OFFSET_TALL =-10;
public static  int ICON_OFFSET =10;
public static  double PICK_TWEEN_IN_TIME =0.75;
public static  double PICK_ANIMATE_DELAY =1000;
        public static  String PICK_TIMER_GREEN ="timer_green";
        public static  String PICK_TIMER_YELLOW ="timer_yellow";
        public static  String PICK_TIMER_RED ="timer_red";
        private static  int MAX_QUEUED_ACTIVE =15;
        private static  int QUEUED_ANIM_CYCLES =3;
        private static Array m_queuedFloatWait =new Array ();
        private static Array m_queuedFloatActive =new Array ();

        public  StagePickEffect (MapResource param1 )
        {
            this.m_fadeTweenQueue = new Array();
            super(param1);
            this.m_glow = new GlowFilter(this.m_glowColor, 1, 1.5, 1.5, 30, 10);
            this.animate(1);
            Global.stagePickManager.addEventListener(StagePickEvent.HIDE, this.onHidePick);
            Global.stagePickManager.addEventListener(StagePickEvent.SHOW, this.onShowPick);
            return;
        }//end

        public boolean  multipickToggle ()
        {
            return this.m_multipickToggle;
        }//end

        public void  multipickToggle (boolean param1 )
        {
            this.m_multipickToggle = param1;
            this.updateVisibility();
            return;
        }//end

        public boolean  managerToggle ()
        {
            return this.m_managerToggle;
        }//end

        public void  managerToggle (boolean param1 )
        {
            this.m_managerToggle = param1;
            this.updateVisibility();
            return;
        }//end

        public void  updateVisibility ()
        {
            this.visible = this.multipickToggle && this.managerToggle;
            return;
        }//end

        protected void  onHidePick (StagePickEvent event )
        {
            if (event.allPicks || event.pickName == this.m_currentPickName)
            {
                this.managerToggle = false;
            }
            return;
        }//end

        protected void  onShowPick (StagePickEvent event )
        {
            if (event.allPicks || event.pickName == this.m_currentPickName)
            {
                this.managerToggle = true;
            }
            return;
        }//end

        public void  drawBackground (boolean param1 )
        {
            this.m_drawBackground = param1;
            return;
        }//end

        public void  setFadeTime (int param1 )
        {
            seconds = param1;
            if (int(this.m_fadeTime) == -1 && int(seconds) > 0)
            {
                this.m_fadeTime = seconds;
                TimerUtil .callLater (void  ()
            {
                if (m_animTimeline)
                {
                    m_animTimeline.kill();
                }
                m_animTimeline = new TimelineLite();
                m_animTimeline.appendMultiple(.get(new TweenLite(this, 1, {alpha:0}), new TweenLite(this, 0.1, {onStart:fadeCallback})), 0, TweenAlign.SEQUENCE);
                return;
            }//end
            , this.m_fadeTime * 1000);
            }
            return;
        }//end

        protected void  queueFade (int param1 )
        {
            this.m_fadeTweenQueue.push(param1);
            return;
        }//end

        protected void  fadeCallback ()
        {
            m_mapResource.removeStagePickEffect();
            this.m_fadeTime = -1;
            return;
        }//end

         public boolean  animate (int param1 )
        {
            return true;
        }//end

         public void  cleanUp ()
        {
            this.removeFromFloatQueues();
            if (this.m_currentPickImage)
            {
                this.removeFromParent(this.m_currentPickImage);
                this.m_currentPickImage = null;
            }
            if (this.m_currentModifierImage)
            {
                this.removeFromParent(this.m_currentModifierImage);
                this.m_currentModifierImage = null;
            }
            if (this.m_guitarPickAnimation)
            {
                this.removeFromParent(this.m_guitarPickAnimation);
                this.m_guitarPickAnimation = null;
            }
            if (this.m_ministarImage)
            {
                this.removeFromParent(this.m_ministarImage);
                this.m_ministarImage = null;
            }
            TweenLite.killTweensOf(this);
            Global.stagePickManager.removeEventListener(StagePickEvent.HIDE, this.onHidePick);
            Global.stagePickManager.removeEventListener(StagePickEvent.SHOW, this.onShowPick);
            this.m_isComplete = true;
            return;
        }//end

        public void  setPickType (String param1 ,String param2 )
        {
            if (!this.m_currentPickName)
            {
                this.m_currentPickName = param1;
                this.m_currentModifierName = param2;
            }
            else if (this.m_currentPickName != param1)
            {
                this.m_currentPickName = param1;
                this.m_currentModifierName = param2;
                this.reattach();
            }
            if (!this.m_currentPickImage)
            {
                this.m_currentPickName = param1;
                this.m_currentModifierName = param2;
                this.reattach();
            }
            if (Global.stagePickManager)
            {
                this.managerToggle = Global.stagePickManager.isPickTypeVisible(this.m_currentPickName);
            }
            return;
        }//end

        public String  getPickType ()
        {
            return this.m_currentPickName;
        }//end

        public void  shine ()
        {
            if (!this.m_guitarPickAnimation)
            {
                this.reattach();
            }
            if (this.m_guitarPickAnimation)
            {
                this.m_guitarPickAnimation.play();
            }
            return;
        }//end

        public void  float ()
        {
            double _loc_1 =0;
            if (this.DISABLE_ANIMATION)
            {
                return;
            }
            if (this.m_queuedActive)
            {
                this.stopFloat();
            }
            this.m_floatActive = true;
            if (this.m_guitarPickAnimation && this.m_currentPickImage)
            {
                if (this.m_animTimeline)
                {
                    this.m_animTimeline.gotoAndPlay(0);
                    return;
                }
                _loc_1 = 1.1 + Math.random() / (1 / (0.9 - 1.1) + 1);
                ((Bitmap)this.m_currentPickImage).pixelSnapping = PixelSnapping.NEVER;
                this.m_guitarPickAnimation.pixelSnapping = PixelSnapping.NEVER;
                this.m_animTimeline = new TimelineLite({onComplete:this.floatCompleteHandler});
                this.m_animTimeline.appendMultiple(.get(new TweenLite(this, _loc_1, {y:this.y + PICK_FLOAT_DISTANCE}), new TweenLite(this, _loc_1, {y:this.y})), 0, TweenAlign.SEQUENCE);
            }
            return;
        }//end

        public void  queuedFloat ()
        {
            if (this.DISABLE_ANIMATION)
            {
                return;
            }
            if (this.m_floatActive)
            {
                this.stopFloat();
            }
            this.m_queuedActive = true;
            if (m_queuedFloatActive.indexOf(this) >= 0 || m_queuedFloatWait.indexOf(this) >= 0)
            {
                return;
            }
            m_queuedFloatWait.splice(0, 0, this);
            activateQueuedPicks();
            return;
        }//end

        private void  queuedActivate ()
        {
            _loc_1 = Math1.1+.random ()/(1/(0.9-1.1)+1);
            _loc_2 = m_queuedFloatActive(1-.length/MAX_QUEUED_ACTIVE)*2*_loc_1;
            ((Bitmap)this.m_currentPickImage).pixelSnapping = PixelSnapping.NEVER;
            this.m_guitarPickAnimation.pixelSnapping = PixelSnapping.NEVER;
            this.cacheAsBitmap = m_queuedFloatWait.length > 1;
            if (this.m_animTimeline == null)
            {
                this.m_animTimeline = new TimelineLite({onComplete:this.queuedFloatCompleteHandler});
                this.m_animTimeline.appendMultiple(.get(new TweenLite(this, _loc_1, {y:this.y + PICK_FLOAT_DISTANCE}), new TweenLite(this, _loc_1, {y:this.y})), 0, TweenAlign.SEQUENCE);
            }
            else
            {
                this.m_animTimeline.gotoAndPlay(0);
            }
            this.m_queuedCount = QUEUED_ANIM_CYCLES;
            m_queuedFloatActive.push(this);
            return;
        }//end

        protected void  removeFromFloatQueues ()
        {
            _loc_1 = m_queuedFloatActive.indexOf(this);
            if (_loc_1 >= 0)
            {
                m_queuedFloatActive.splice(_loc_1, 1);
            }
            _loc_1 = m_queuedFloatWait.indexOf(this);
            if (_loc_1 >= 0)
            {
                m_queuedFloatWait.splice(_loc_1, 1);
            }
            return;
        }//end

        private void  queuedFloatCompleteHandler ()
        {
            if (this.m_queuedActive && this.m_animTimeline && this.m_queuedCount > 1)
            {
                this.m_queuedCount--;
                this.m_animTimeline.gotoAndPlay(0);
            }
            else
            {
                this.removeFromFloatQueues();
                if (this.m_animTimeline != null && this.m_queuedActive)
                {
                    m_queuedFloatWait.push(this);
                }
            }
            activateQueuedPicks();
            return;
        }//end

        public void  stopFloat ()
        {
            this.removeFromFloatQueues();
            this.m_floatActive = false;
            this.m_queuedActive = false;
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
            }
            this.m_animTimeline = null;
            return;
        }//end

        private void  floatCompleteHandler ()
        {
            this.float();
            return;
        }//end

        public void  showBonusXp ()
        {
            if (!this.m_currentPickImage)
            {
                this.reattach();
            }
            else if (this.m_ministarImage)
            {
                this.m_ministarImage.visible = true;
                this.m_showMinistar = true;
            }
            return;
        }//end

        public void  hideBonusXp ()
        {
            this.m_showMinistar = false;
            if (this.m_ministarImage)
            {
                this.m_ministarImage.visible = false;
            }
            return;
        }//end

         public void  reattach ()
        {
            this.alpha = 0;
            _loc_1 = this.getAttachParent ();
            if (!this.parent || this.parent != _loc_1)
            {
                _loc_1.addChild(this);
            }
            this.setPickImage(this.m_currentPickName, this.m_currentModifierName);
            TweenLite.to(this, PICK_TWEEN_IN_TIME, {alpha:1});
            if (this.m_fadeTweenQueue)
            {
                this.setFadeTime(this.m_fadeTweenQueue.shift());
            }
            return;
        }//end

        protected void  removeFromParent (DisplayObject param1 )
        {
            if (param1 && param1.parent)
            {
                param1.parent.removeChild(param1);
            }
            return;
        }//end

        protected Object  getImage (String param1 )
        {
            return null;
        }//end

        private Sprite  getAttachParent ()
        {
            _loc_1 = (Sprite)m_mapResource.getDisplayObject()
            return _loc_1;
        }//end

        protected void  setPickImage (String param1 ,String param2 )
        {
            String _loc_4 =null ;
            IsoRect _loc_5 =null ;
            Point _loc_6 =null ;
            double _loc_7 =0;
            this.removeFromParent(this.m_currentPickImage);
            this.removeFromParent(this.m_currentModifierImage);
            this.removeFromParent(this.m_guitarPickAnimation);
            this.removeFromParent(this.m_ministarImage);
            _loc_3 = param2!= null;
            if (param1 !=null)
            {
                _loc_4 = _loc_3 ? (PICK_SHINE_TALL) : (PICK_SHINE);
                this.m_guitarPickAnimation =(AnimatedBitmap) this.getImage(_loc_4);
                this.m_guitarPickAnimation.x = (-this.m_guitarPickAnimation.width >> 1) + 1;
                if (param1 == PICK_4)
                {
                    this.m_guitarPickAnimation.x = (-this.m_guitarPickAnimation.width >> 1) + 1.5;
                }
                this.m_guitarPickAnimation.y = (-this.m_guitarPickAnimation.height >> 1) + (_loc_3 ? (-12) : (3));
                this.m_currentPickImage =(DisplayObject) this.getImage(param1);
                this.m_currentPickImage.x = (-this.m_currentPickImage.width) * 0.5;
                this.m_currentPickImage.y = (-this.m_currentPickImage.height) * 0.5;
                this.m_currentModifierImage = param2 != null ? ((DisplayObject)this.getImage(param2)) : (new Sprite());
                this.m_currentModifierImage.x = (-this.m_currentModifierImage.width) * 0.5;
                this.m_currentModifierImage.y = (-this.m_currentModifierImage.height) * 1.5;
                this.m_ministarImage =(DisplayObject) this.getImage(MINI_STAR);
                this.m_ministarImage.x = this.m_currentPickImage.x + this.m_currentPickImage.width * 0.6;
                this.m_ministarImage.y = this.m_currentPickImage.y + this.m_currentPickImage.height * 0.1;
                if (!this.m_showMinistar)
                {
                    this.m_ministarImage.visible = false;
                }
                _loc_5 = IsoRect.getIsoRectFromSize(m_mapResource.getReference().getSize());
                this.m_effectX = _loc_5.bottom.x;
                this.m_effectY = _loc_5.top.y + this.m_guitarPickAnimation.height / 2;
                this.m_guitarPickAnimation.stop();
                if (this.m_drawBackground)
                {
                    this.addChild(this.m_guitarPickAnimation);
                }
                this.addChild(this.m_currentPickImage);
                this.addChild(this.m_currentModifierImage);
                this.addChild(this.m_ministarImage);
                if (m_mapResource && m_mapResource.getItem() && m_mapResource.getItem().pickOffset)
                {
                    _loc_6 = m_mapResource.getItem().pickOffset;
                }
                else
                {
                    _loc_6 = new Point(0, 0);
                }
                _loc_7 = _loc_3 ? (PICK_Y_OFFSET_TALL) : (PICK_Y_OFFSET);
                this.y = this.m_effectY + _loc_7 - PICK_FLOAT_DISTANCE + _loc_6.y - this.m_pickOffset;
                this.x = this.m_effectX + _loc_6.x;
            }
            if (this.m_floatActive)
            {
                this.float();
            }
            else if (this.m_queuedActive)
            {
                this.queuedFloat();
            }
            return;
        }//end

        public int  getAnimationHeight ()
        {
            IsoRect _loc_1 =null ;
            if (m_mapResource)
            {
                _loc_1 = IsoRect.getIsoRectFromSize(m_mapResource.getReference().getSize());
                return -(_loc_1.top.y + PICK_FLOAT_DISTANCE);
            }
            return 0;
        }//end

        public void  showHighlight ()
        {
            this.filters = .get(this.m_glow);
            return;
        }//end

        public void  hideHighlight ()
        {
            this.filters = new Array();
            return;
        }//end

        protected int  glow ()
        {
            return this.m_glowColor;
        }//end

        protected void  glow (int param1 )
        {
            this.m_glowColor = param1;
            this.m_glow = new GlowFilter(param1, 1, 1.5, 1.5, 100, 100);
            return;
        }//end

        public void  pickOffset (double param1 )
        {
            this.m_pickOffset = param1;
            return;
        }//end

        private static void  activateQueuedPicks ()
        {
            StagePickEffect _loc_2 =null ;
            boolean _loc_3 =false ;
            StagePickEffect _loc_4 =null ;
            int _loc_1 =0;
            while (_loc_1 < m_queuedFloatActive.length())
            {

                _loc_2 = m_queuedFloatActive.get(_loc_1);
                _loc_3 = false;
                if (!_loc_2.m_queuedActive)
                {
                    _loc_3 = true;
                }
                if (!_loc_2.m_animTimeline)
                {
                    _loc_3 = true;
                }
                if (_loc_3)
                {
                    m_queuedFloatActive.splice(_loc_1, 1);
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1++;
            }
            while (m_queuedFloatWait.length > 0 && m_queuedFloatActive.length < MAX_QUEUED_ACTIVE)
            {

                _loc_4 = m_queuedFloatWait.get(0);
                if (_loc_4.m_guitarPickAnimation && _loc_4.m_currentPickImage)
                {
                    m_queuedFloatWait.splice(0, 1);
                    _loc_4.queuedActivate();
                    continue;
                }
                m_queuedFloatWait.splice(0, 1);
                m_queuedFloatWait.push(_loc_4);
                break;
            }
            return;
        }//end

    }


