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
import Engine.Events.*;
import Engine.Helpers.*;
import GameMode.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class MultiPickEffect extends MapResourceEffect
    {
        protected int m_phase ;
        protected String m_stage ;
        protected Array m_picks ;
        protected Dictionary m_stageToPick =null ;
        protected double m_stageToPickLength =0;
        protected StagePickEffect m_highlighted ;
        protected int m_pickEventCount =0;
        protected int m_animateEventCount =0;
        protected Dictionary m_options =null ;
        protected Timer m_timer =null ;
        public static  String MULTIPICK_PICK_CHANGED ="MultiPickPickChanged";
        public static  String OPTION_ACTION_ON_SELECT ="OptionActionOnSelect";
public static  String EFFECT_NAME ="multiPick";
public static  double PICK_TWEEN_IN_TIME =0.75;
public static  int PICK_SLOTS =3;
public static  Array PICK_SLOT_MAP =new Array(0,-1,1);
public static  int PICK_FLOAT_DISTANCE =5;
public static  int PICK_OFFSET_Y =10;
public static  int FOLD_PICK_OFFSET_X =10;
public static  int FOLD_PICK_OFFSET_Y =10;
public static  int MOVE_PICK_OFFSET_X =5;
public static  int MOVE_PICK_OFFSET_Y =5;
public static  int PHASE_NONE =1;
public static  int PHASE_FOLDING =10;
public static  int PHASE_FOLDED =11;
public static  int PHASE_PRE_FOLDING =12;
public static  int PHASE_UNFOLDING =20;
public static  int PHASE_UNFOLDED =21;

        public  MultiPickEffect (MapResource param1 )
        {
            super(param1);
            this.m_phase = PHASE_NONE;
            this.m_stage = null;
            this.m_highlighted = null;
            this.m_pickEventCount = 0;
            this.m_animateEventCount = 0;
            this.setDefaultOptions();
            this.m_picks = new Array();
            this.m_stageToPick = new Dictionary();
            this.m_stageToPickLength = 0;
            this.mouseEnabled = true;
            this.mouseChildren = true;
            this.addEventListener(MouseEvent.MOUSE_OVER, this.animatePicksUnfold, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OUT, this.animatePicksFold, false, 0, true);
            return;
        }//end

        protected void  setDefaultOptions ()
        {
            this.m_options = new Dictionary();
            this.m_options.put(OPTION_ACTION_ON_SELECT,  false);
            return;
        }//end

        public boolean  getOption (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_options.get(param1))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public void  setOption (String param1 ,boolean param2 )
        {
            if (this.m_options.get(param1) !== null)
            {
                this.m_options.put(param1,  param2);
            }
            return;
        }//end

        public void  create (String param1 ,String param2 ,String param3 =null )
        {
            if (this.m_stageToPick.get(param1))
            {
                this.destroy(param1);
            }
            if (!this.m_pickEventCount)
            {
                this.addEventListener(LoaderEvent.LOADED, this.setPicksFolded, false, 0, true);
            }
            this.m_pickEventCount++;
            _loc_4 = (StagePickEffect)MapResourceEffectFactory.createEffect(m_mapResource,EffectType.STAGE_PICK)
            ((StagePickEffect)MapResourceEffectFactory.createEffect(m_mapResource, EffectType.STAGE_PICK)).alpha = 0;
            _loc_4.mouseEnabled = true;
            _loc_4.mouseChildren = false;
            _loc_4.addEventListener(MouseEvent.MOUSE_OVER, this.highlightPick, false, 0, true);
            _loc_4.addEventListener(MouseEvent.MOUSE_UP, this.updateActivePick, false, 0, true);
            this.addChild(_loc_4);
            _loc_4.addEventListener(LoaderEvent.LOADED, this.attachPickLoaded, false, 0, true);
            this.m_stageToPick.put(param1,  _loc_4);
            this.m_stageToPickLength++;
            this.m_picks.push(_loc_4);
            _loc_4.setPickType(param2, param3);
            return;
        }//end

        public void  destroy (String param1 )
        {
            StagePickEffect _loc_2 =null ;
            int _loc_3 =0;
            if (this.m_stageToPick.get(param1))
            {
                _loc_2 =(StagePickEffect) this.m_stageToPick.get(param1);
                if (_loc_2)
                {
                    _loc_2.removeEventListener(MouseEvent.MOUSE_OVER, this.highlightPick);
                    _loc_2.removeEventListener(MouseEvent.MOUSE_UP, this.updateActivePick);
                    _loc_2.parent.removeChild(_loc_2);
                    _loc_2.cleanUp();
                    _loc_3 = this.m_picks.indexOf(_loc_2);
                    if (_loc_3 != -1)
                    {
                        this.m_picks.splice(_loc_3, 1);
                    }
                    if (_loc_3 == 0)
                    {
                        this.m_stage = null;
                    }
                }
                delete this.m_stageToPick.get(param1);
            }
            return;
        }//end

        public boolean  exists (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_stageToPick.get(param1) instanceof StagePickEffect)
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  isViewable (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_stageToPick.get(param1) instanceof StagePickEffect)
            {
                _loc_2 = ((StagePickEffect)this.m_stageToPick.get(param1)).visible;
            }
            return _loc_2;
        }//end

        public void  hideAll ()
        {
            StagePickEffect _loc_1 =null ;
            this.m_stage = null;
            while (this.m_picks.length > 0)
            {

                _loc_1 = this.m_picks.pop();
                _loc_1.visible = false;
            }
            return;
        }//end

        public void  viewable (String param1 ,boolean param2 )
        {
            StagePickEffect _loc_3 =null ;
            boolean _loc_4 =false ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            StagePickEffect _loc_8 =null ;
            if (this.m_stageToPick.get(param1) instanceof StagePickEffect)
            {
                _loc_3 =(StagePickEffect) this.m_stageToPick.get(param1);
                _loc_4 = _loc_3.visible;
                if (_loc_4 != param2)
                {
                    _loc_5 = this.m_picks.indexOf(_loc_3);
                    if (_loc_5 != -1)
                    {
                        this.m_picks.splice(_loc_5, 1);
                    }
                    if (_loc_5 == 0)
                    {
                        this.m_stage = null;
                    }
                    _loc_3.alpha = 0;
                    _loc_3.multipickToggle = param2;
                    param2 = _loc_3.multipickToggle;
                    if (param2)
                    {
                        this.m_picks.push(_loc_3);
                        _loc_5 = this.m_picks.length - 1;
                        this.movePickToTopChild(this.m_picks.get(0));
                        _loc_3.x = 0;
                        _loc_3.y = 0;
                        _loc_6 = PICK_SLOT_MAP.get(_loc_5 % PICK_SLOTS);
                        _loc_7 = int(_loc_5 / PICK_SLOTS);
                        if (PHASE_UNFOLDING == this.m_phase || PHASE_UNFOLDED == this.m_phase)
                        {
                            _loc_8 = this.m_picks.get(0);
                            _loc_3.x = _loc_6 * (_loc_8.width - MOVE_PICK_OFFSET_X);
                            _loc_3.y = _loc_7 * (_loc_8.height - MOVE_PICK_OFFSET_Y);
                        }
                        else if (PHASE_FOLDING == this.m_phase || PHASE_FOLDED == this.m_phase || PHASE_NONE == this.m_phase)
                        {
                            _loc_3.x = _loc_6 * FOLD_PICK_OFFSET_X;
                            _loc_3.y = _loc_7 * FOLD_PICK_OFFSET_Y;
                        }
                        TweenLite.to(_loc_3, PICK_TWEEN_IN_TIME, {alpha:1});
                    }
                    else
                    {
                        if (this.m_picks.length())
                        {
                            this.movePickToTopChild(this.m_picks.get(0));
                        }
                        if (PHASE_FOLDED == this.m_phase)
                        {
                            this.setPicksFolded();
                        }
                    }
                }
            }
            return;
        }//end

        public String  active ()
        {
            String _loc_1 =null ;
            if (!this.m_stage && this.m_picks.length > 0)
            {
                for(int i0 = 0; i0 < this.m_stageToPick.size(); i0++)
                {
                		_loc_1 = this.m_stageToPick.get(i0);

                    if (this.m_stageToPick.get(_loc_1) === this.m_picks.get(0))
                    {
                        this.m_stage = _loc_1;
                    }
                }
            }
            return this.m_stage;
        }//end

        public void  active (String param1 )
        {
            StagePickEffect _loc_2 =null ;
            int _loc_3 =0;
            if (this.m_stageToPick.get(param1) instanceof StagePickEffect)
            {
                if (param1 != this.m_stage && this.m_picks.length > 0)
                {
                    _loc_2 =(StagePickEffect) this.m_stageToPick.get(param1);
                    if (_loc_2 && _loc_2 != this.m_picks.get(0))
                    {
                        _loc_3 = this.m_picks.indexOf(_loc_2);
                        if (_loc_3 != -1)
                        {
                            this.m_picks.put(_loc_3,  this.m_picks.get(0));
                            this.m_picks.put(0,  _loc_2);
                            this.m_stage = param1;
                            this.movePickToTopChild(_loc_2);
                            if (PHASE_FOLDED == this.m_phase)
                            {
                                this.setPicksFolded();
                            }
                            this.dispatchEvent(new Event(MULTIPICK_PICK_CHANGED));
                        }
                    }
                }
            }
            return;
        }//end

        public int  length ()
        {
            return this.m_picks.length;
        }//end

        public int  phase ()
        {
            return this.m_phase;
        }//end

        public void  fold ()
        {
            this.setPicksFolded();
            return;
        }//end

         public void  cleanUp ()
        {
            String _loc_1 =null ;
            StagePickEffect _loc_2 =null ;
            this.alpha = 0;
            this.removeEventListener(MouseEvent.MOUSE_OVER, this.animatePicksUnfold);
            this.removeEventListener(MouseEvent.MOUSE_OUT, this.animatePicksFold);
            for(int i0 = 0; i0 < this.m_stageToPick.size(); i0++)
            {
            		_loc_1 = this.m_stageToPick.get(i0);

                _loc_2 = this.m_stageToPick.get(_loc_1);
                _loc_2.removeEventListener(MouseEvent.MOUSE_OVER, this.highlightPick);
                _loc_2.removeEventListener(MouseEvent.MOUSE_UP, this.updateActivePick);
                if (_loc_2.parent)
                {
                    _loc_2.parent.removeChild(_loc_2);
                }
                _loc_2.stopFloat();
                _loc_2.cleanUp();
            }
            this.m_picks = new Array();
            this.m_stageToPick = new Dictionary();
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            this.m_animateEventCount = 0;
            this.m_phase = PHASE_NONE;
            this.m_stage = null;
            this.m_highlighted = null;
            return;
        }//end

        protected boolean  isReady ()
        {
            boolean _loc_1 =false ;
            if (isMapResourceLoaded)
            {
                if (PHASE_NONE != this.m_phase && this.m_picks.length > 0 && !Global.world.isEditMode)
                {
                    _loc_1 = true;
                }
            }
            return _loc_1;
        }//end

        protected void  attachPickLoaded (LoaderEvent event )
        {
            _loc_2 =(StagePickEffect) event.target;
            if (_loc_2)
            {
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.attachPickLoaded);
                if (!_loc_2.parent || _loc_2.parent != (Sprite)this)
                {
                    this.addChild(_loc_2);
                }
                this.m_pickEventCount--;
                if (!this.m_pickEventCount)
                {
                    this.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
                }
            }
            return;
        }//end

        protected void  animatePicksUnfold (MouseEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            StagePickEffect _loc_6 =null ;
            int _loc_7 =0;
            GameMode _loc_8 =null ;
            String _loc_9 =null ;
            GameMode _loc_10 =null ;
            String _loc_11 =null ;
            if (this.isReady())
            {
                if (!this.m_options.get(OPTION_ACTION_ON_SELECT))
                {
                    _loc_8 = Global.world.getTopGameMode();
                    _loc_9 = getQualifiedClassName(_loc_8);
                    _loc_10 = new GMMultiPick();
                    _loc_11 = getQualifiedClassName(_loc_10);
                    if (_loc_11 != _loc_9)
                    {
                        Global.world.addGameMode(_loc_10);
                    }
                }
                if (this.m_phase == PHASE_UNFOLDING)
                {
                    return;
                }
                if (this.m_phase == PHASE_UNFOLDED)
                {
                    return;
                }
                if (this.m_phase == PHASE_PRE_FOLDING)
                {
                    this.m_phase = PHASE_UNFOLDED;
                    return;
                }
                this.m_animateEventCount = this.m_picks.length;
                this.m_phase = PHASE_UNFOLDING;
                _loc_2 = 0;
                _loc_3 = 0;
                _loc_4 = 0;
                _loc_5 = 0;
                _loc_6 = this.m_picks.get(0);
                _loc_7 = 0;
                while (_loc_7 < this.m_picks.length())
                {

                    _loc_2 = PICK_SLOT_MAP.get(_loc_7 % PICK_SLOTS);
                    _loc_3 = int(_loc_7 / PICK_SLOTS);
                    _loc_4 = _loc_2 * (_loc_6.width - MOVE_PICK_OFFSET_X);
                    _loc_5 = 1 * _loc_3 * (_loc_6.height - MOVE_PICK_OFFSET_Y);
                    TweenLite.to(this.m_picks.get(_loc_7), 0.3, {x:_loc_4, y:_loc_5, onComplete:this.animatePicksUnfolded});
                    _loc_7++;
                }
            }
            return;
        }//end

        protected void  animatePicksUnfolded ()
        {
            if (this.isReady())
            {
                this.m_animateEventCount--;
                if (!this.m_animateEventCount)
                {
                    this.m_animateEventCount = 0;
                    this.m_phase = PHASE_UNFOLDED;
                }
            }
            return;
        }//end

        protected void  animatePicksFold (MouseEvent event =null )
        {
            this.m_phase = PHASE_PRE_FOLDING;
            this.m_timer = new Timer(100, 1);
            this.m_timer.addEventListener(TimerEvent.TIMER_COMPLETE, this.doAnimatePicksFold);
            this.m_timer.start();
            return;
        }//end

        protected void  doAnimatePicksFold (TimerEvent event )
        {
            int _loc_2 =0;
            if (this.m_phase != PHASE_PRE_FOLDING)
            {
                return;
            }
            if (this.isReady())
            {
                this.m_phase = PHASE_FOLDING;
                this.m_animateEventCount = this.m_picks.length;
                _loc_2 = 0;
                while (_loc_2 < this.m_picks.length())
                {

                    TweenLite.to(this.m_picks.get(_loc_2), 0.3, {x:0, y:0, onComplete:this.animatePicksFolded, ease:Quad.easeIn});
                    _loc_2++;
                }
            }
            return;
        }//end

        protected void  animatePicksFolded ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            int _loc_5 =0;
            if (this.isReady())
            {
                this.m_animateEventCount--;
                if (!this.m_animateEventCount)
                {
                    _loc_1 = 0;
                    _loc_2 = 0;
                    _loc_3 = 10;
                    _loc_4 = 10;
                    _loc_5 = 0;
                    while (_loc_5 < this.m_picks.length())
                    {

                        _loc_1 = PICK_SLOT_MAP.get(_loc_5 % PICK_SLOTS);
                        _loc_2 = int(_loc_5 / PICK_SLOTS);
                        TweenLite.to(this.m_picks.get(_loc_5), 0.1, {x:_loc_1 * _loc_3, y:_loc_2 * _loc_4});
                        _loc_5++;
                    }
                    this.m_animateEventCount = 0;
                    this.m_phase = PHASE_FOLDED;
                    if (this.m_highlighted)
                    {
                        this.m_highlighted.hideHighlight();
                        this.m_highlighted = null;
                    }
                    if (!this.m_options.get(OPTION_ACTION_ON_SELECT))
                    {
                        Global.world.setDefaultGameMode();
                    }
                }
            }
            return;
        }//end

        protected void  setPicksFolded (LoaderEvent event =null )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            if (event)
            {
                this.removeEventListener(LoaderEvent.LOADED, this.setPicksFolded);
            }
            if (this.m_highlighted)
            {
                this.m_highlighted.hideHighlight();
                this.m_highlighted = null;
            }
            if (this.m_stageToPickLength > 0)
            {
                this.reposition();
                for(int i0 = 0; i0 < this.m_stageToPick.size(); i0++)
                {
                		_loc_2 = this.m_stageToPick.get(i0);

                    this.m_stageToPick.get(_loc_2).x = 0;
                    this.m_stageToPick.get(_loc_2).y = 0;
                }
            }
            if (this.m_picks.length > 0)
            {
                if (this.parent)
                {
                    this.parent.removeChild(this);
                }
                _loc_3 = this.m_picks.length;
                _loc_4 = 0;
                _loc_5 = 0;
                _loc_6 = FOLD_PICK_OFFSET_X;
                _loc_7 = FOLD_PICK_OFFSET_Y;
                while (--_loc_3 > -1)
                {

                    _loc_4 = PICK_SLOT_MAP.get(_loc_3 % PICK_SLOTS);
                    _loc_5 = int(_loc_3 / PICK_SLOTS);
                    this.m_picks.get(_loc_3).x = _loc_4 * _loc_6;
                    this.m_picks.get(_loc_3).y = _loc_5 * _loc_7;
                    this.movePickToTopChild(this.m_picks.get(_loc_3));
                }
            }
            this.m_animateEventCount = 0;
            this.m_phase = PHASE_FOLDED;
            this.reattach();
            return;
        }//end

        protected void  updateActivePick (MouseEvent event )
        {
            StagePickEffect _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_2 =false ;
            if (this.isReady())
            {
                _loc_3 =(StagePickEffect) event.target;
                if (_loc_3 && _loc_3 != this.m_picks.get(0))
                {
                    _loc_4 = this.m_picks.indexOf(_loc_3);
                    if (_loc_4 != -1)
                    {
                        this.m_picks.put(_loc_4,  this.m_picks.get(0));
                        this.m_picks.put(0,  _loc_3);
                        this.m_stage = null;
                        this.movePickToTopChild(_loc_3);
                        _loc_2 = true;
                    }
                }
                this.animatePicksFold();
                if (_loc_2)
                {
                    this.dispatchEvent(new Event(MULTIPICK_PICK_CHANGED));
                }
            }
            return;
        }//end

        protected void  highlightPick (MouseEvent event )
        {
            StagePickEffect _loc_2 =null ;
            boolean _loc_3 =false ;
            int _loc_4 =0;
            if (this.isReady())
            {
                Global.ui.turnOffHighlightedObject();
                _loc_2 =(StagePickEffect) event.target;
                if (this.m_highlighted && this.m_highlighted != _loc_2)
                {
                    this.m_highlighted.hideHighlight();
                    _loc_3 = false;
                    if (_loc_2 != this.m_picks.get(0))
                    {
                        _loc_4 = this.m_picks.indexOf(_loc_2);
                        if (_loc_4 != -1)
                        {
                            this.m_picks.put(_loc_4,  this.m_picks.get(0));
                            this.m_picks.put(0,  _loc_2);
                            this.m_stage = null;
                            this.movePickToTopChild(_loc_2);
                            _loc_3 = true;
                        }
                    }
                    if (_loc_3)
                    {
                        this.dispatchEvent(new Event(MULTIPICK_PICK_CHANGED));
                    }
                }
                this.m_highlighted = _loc_2;
                this.m_highlighted.showHighlight();
            }
            return;
        }//end

         public boolean  animate (int param1 )
        {
            return false;
        }//end

        public int  getFloatOffset ()
        {
            Vector3 _loc_2 =null ;
            IsoRect _loc_3 =null ;
            Point _loc_4 =null ;
            int _loc_1 =0;
            if (m_mapResource)
            {
                _loc_2 = m_mapResource.getReference().getSize();
                _loc_3 = IsoRect.getIsoRectFromSize(_loc_2);
                _loc_4 = new Point(0, 0);
                if (m_mapResource && m_mapResource.getItem() && m_mapResource.getItem().pickOffset)
                {
                    _loc_4 = m_mapResource.getItem().pickOffset;
                }
                return -(_loc_3.top.y + PICK_FLOAT_DISTANCE + _loc_4.y);
            }
            return _loc_1;
        }//end

        public void  reposition ()
        {
            _loc_1 = m_mapResource.getReference().getSize();
            _loc_2 = IsoRect.getIsoRectFromSize(_loc_1);
            Point _loc_3 =new Point(0,0);
            if (m_mapResource && m_mapResource.getItem() && m_mapResource.getItem().pickOffset)
            {
                _loc_3 = m_mapResource.getItem().pickOffset;
            }
            this.x = _loc_2.bottom.x;
            this.y = _loc_2.top.y + this.height / 2 + PICK_OFFSET_Y - PICK_FLOAT_DISTANCE + _loc_3.y;
            return;
        }//end

         public void  reattach ()
        {
            Sprite _loc_1 =null ;
            if (isMapResourceLoaded && !m_mapResource.isBeingRotated() && !m_mapResource.isDisplayObjectDirty())
            {
                _loc_1 =(Sprite) m_mapResource.getDisplayObject();
                if ((!this.parent || this.parent != _loc_1) && this.active != null)
                {
                    this.alpha = 0;
                    this.reposition();
                    _loc_1.addChild(this);
                    _loc_1.mouseChildren = true;
                }
                if (this.alpha == 0 && this.active != null)
                {
                    TweenLite.to(this, PICK_TWEEN_IN_TIME, {alpha:1});
                }
            }
            return;
        }//end

        public boolean  hasVisiblePicks ()
        {
            return Boolean(this.m_picks.length());
        }//end

        private void  movePickToTopChild (StagePickEffect param1 )
        {
            if (this.numChildren)
            {
                this.addChildAt(param1, (this.numChildren - 1));
            }
            else
            {
                this.addChild(param1);
            }
            return;
        }//end

    }



