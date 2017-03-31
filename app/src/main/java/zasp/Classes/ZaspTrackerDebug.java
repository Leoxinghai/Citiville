package zasp.Classes;

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

//import flash.display.*;
//import flash.events.*;
import zasp.Display.*;
import zasp.Util.*;

    public class ZaspTrackerDebug
    {
        protected FunkySpinner spinnerF2F =null ;
        protected FunkySpinner spinnerEMA =null ;
        protected FunkySpinner spinnerOEF =null ;
        protected FullRedrawForcer fullRedraw =null ;
        protected boolean keysActive =false ;
        protected Sprite m_sprite =null ;
        protected ZaspTrackerBase baseTracker =null ;
        protected boolean useSpinnerOef =false ;

        public  ZaspTrackerDebug (ZaspTrackerBase param1 ,boolean param2 =false )
        {
            CircularBuffer.test();
            SumSampler.test();
            this.baseTracker = param1;
            this.useSpinnerOef = param2;
            this.m_sprite = new Sprite();
            this.m_sprite.addEventListener(Event.ADDED_TO_STAGE, this.added_to_stage, false, 0, true);
            return;
        }//end

        public void  sample (double param1 ,double param2 )
        {
            if (this.spinnerF2F != null)
            {
                this.spinnerF2F.sample(param1);
            }
            if (this.spinnerEMA != null)
            {
                this.spinnerEMA.sample(this.baseTracker.f2f);
            }
            if (this.spinnerOEF != null)
            {
                this.spinnerOEF.sample(param2);
            }
            return;
        }//end

        private void  added_to_stage (Event event )
        {
            this.m_sprite.removeEventListener(Event.ADDED_TO_STAGE, this.added_to_stage, false);
            this.m_sprite.addEventListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage, false, 0, true);
            this.m_sprite.stage.addEventListener(KeyboardEvent.KEY_DOWN, this.key_down, false, 0, true);
            return;
        }//end

        private void  removed_from_stage (Event event )
        {
            this.m_sprite.stage.removeEventListener(KeyboardEvent.KEY_DOWN, this.key_down, false);
            this.m_sprite.removeEventListener(Event.REMOVED_FROM_STAGE, this.removed_from_stage, false);
            this.m_sprite.addEventListener(Event.ADDED_TO_STAGE, this.added_to_stage, false, 0, true);
            return;
        }//end

        private void  key_down (KeyboardEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            if (event.keyCode == 219)
            {
                this.keyCapture = !this.keyCapture;
                if (this.keyCapture)
                {
                    trace("keyboard input for ZASP enabled");
                }
                else
                {
                    trace("keyboard input for ZASP disnabled");
                }
            }
            if (!this.keyCapture)
            {
                return;
            }
            switch(event.keyCode)
            {
                case 82:
                {
                    this.baseTracker.log_report();
                    break;
                }
                case 49:
                {
                    trace("disabling all cacheAsBitmap");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isCacheAsBitmap, this.disable_CacheAsBitmap);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 50:
                {
                    trace("enabling all cacheAsBitmap");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isAnything, this.enable_CacheAsBitmap);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 51:
                {
                    trace("toggling Shapes cacheAsBitmap");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isShape, this.toggle_CacheAsBitmap);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 52:
                {
                    trace("toggling Sprites cacheAsBitmap");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isSprite, this.toggle_CacheAsBitmap);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 54:
                {
                    if (this.areSpinnersActive)
                    {
                        trace("deactivating spinners");
                        this.deactivateSpinners();
                    }
                    else
                    {
                        trace("activating spinners");
                        this.activateSpinners();
                    }
                    break;
                }
                case 55:
                {
                    if (this.isFullRedrawActive)
                    {
                        trace("deactivating forced full redraw");
                        this.deactivateFullRedraw();
                    }
                    else
                    {
                        trace("activating forced full redraw");
                        this.activateFullRedraw();
                    }
                    break;
                }
                case 56:
                {
                    trace("bringing spinners to front");
                    if (this.m_sprite.parent != null)
                    {
                        this.m_sprite.parent.setChildIndex(this.m_sprite, (this.m_sprite.parent.numChildren - 1));
                    }
                    break;
                }
                case 48:
                {
                    trace("logging empties");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isEmpty, this.log);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 86:
                {
                    trace("pruning empties");
                    _loc_2 = this.recurse(this.m_sprite.stage, this.isAnything, this.prune_empties);
                    trace("  num: " + _loc_2);
                    break;
                }
                case 57:
                {
                    trace("counting objects cacheAsBitmap");
                    _loc_3 = this.recurse(this.m_sprite.stage, this.isAnything, this.nothing);
                    _loc_4 = this.recurse(this.m_sprite.stage, this.isCacheAsBitmap, this.nothing);
                    _loc_5 = this.recurse(this.m_sprite.stage, this.isNotCacheAsBitmap, this.nothing);
                    trace("  has: " + _loc_4);
                    trace("  not: " + _loc_5);
                    trace("  all: " + _loc_3);
                    _loc_6 = this.recurse(this.m_sprite.stage, this.isShape, this.nothing);
                    trace("num Shapes:      " + _loc_6);
                    _loc_7 = this.recurse(this.m_sprite.stage, this.isSprite, this.nothing);
                    trace("num Sprites:     " + _loc_7);
                    _loc_8 = this.recurse(this.m_sprite.stage, this.isEmpty, this.nothing);
                    trace("num empties:     " + _loc_8);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  log_report ()
        {
            trace("PERFORMANCE REPORT DEBUG");
            return;
        }//end

        private void  log (DisplayObject param1 )
        {
            trace(param1);
            trace("  name:        " + param1.name);
            trace("  x:           " + param1.x);
            trace("  y:           " + param1.y);
            trace("  width:       " + param1.width);
            trace("  height:      " + param1.height);
            trace("  scaleX:      " + param1.scaleX);
            trace("  scaleY:      " + param1.scaleY);
            trace("  rotation:    " + param1.rotation);
            trace("  cab:         " + param1.cacheAsBitmap);
            trace("  visible:     " + param1.visible);
            trace("  mouseX:      " + param1.mouseX);
            trace("  mouseY:      " + param1.mouseY);
            _loc_2 = (DisplayObjectContainer)param1
            if (_loc_2 != null)
            {
                trace("  numChildren: " + _loc_2.numChildren);
            }
            return;
        }//end

        private int  recurse (DisplayObject param1 ,Function param2 ,Function param3 )
        {
            int _loc_6 =0;
            if (param1 == null)
            {
                return 0;
            }
            int _loc_4 =0;
            if (param2(param1))
            {
                _loc_4 = _loc_4 + 1;
                param3(param1);
            }
            _loc_5 = (DisplayObjectContainer)param1
            if (_loc_5 != null)
            {
                _loc_6 = 0;
                while (_loc_6 < _loc_5.numChildren)
                {

                    _loc_4 = _loc_4 + this.recurse(_loc_5.getChildAt(_loc_6), param2, param3);
                    _loc_6++;
                }
            }
            return _loc_4;
        }//end

        private boolean  isShape (DisplayObject param1 )
        {
            return param1 instanceof Shape;
        }//end

        private boolean  isSprite (DisplayObject param1 )
        {
            return param1 instanceof Sprite;
        }//end

        private boolean  isCacheAsBitmap (DisplayObject param1 )
        {
            return param1.cacheAsBitmap;
        }//end

        private boolean  isNotCacheAsBitmap (DisplayObject param1 )
        {
            return !this.isCacheAsBitmap(param1);
        }//end

        private boolean  isFunkySpinner (DisplayObject param1 )
        {
            return param1 instanceof FunkySpinner;
        }//end

        private boolean  isAnything (DisplayObject param1 )
        {
            return param1 != this.m_sprite.stage;
        }//end

        private void  togleVisibility (DisplayObject param1 )
        {
            param1.visible = !param1.visible;
            return;
        }//end

        private void  toggle_CacheAsBitmap (DisplayObject param1 )
        {
            param1.cacheAsBitmap = !param1.cacheAsBitmap;
            return;
        }//end

        private void  enable_CacheAsBitmap (DisplayObject param1 )
        {
            param1.cacheAsBitmap = true;
            return;
        }//end

        private void  disable_CacheAsBitmap (DisplayObject param1 )
        {
            param1.cacheAsBitmap = false;
            return;
        }//end

        private void  nothing (DisplayObject param1 )
        {
            return;
        }//end

        public boolean  areSpinnersActive ()
        {
            if (this.spinnerF2F != null)
            {
                return true;
            }
            if (this.spinnerEMA != null)
            {
                return true;
            }
            if (this.spinnerOEF != null)
            {
                return true;
            }
            return false;
        }//end

        public Sprite  sprite ()
        {
            return this.m_sprite;
        }//end

        public void  activateSpinners ()
        {
            if (this.areSpinnersActive)
            {
                return;
            }
            this.deactivateSpinners();
            double r ;
            double x ;
            y = r5+;
            this.spinnerEMA = new FunkySpinner(x, y, r);
            x = x + 1.4 * r;
            this.spinnerF2F = new FunkySpinner(x, y, r);
            x = x + 1.4 * r;
            if (this.useSpinnerOef)
            {
                this.spinnerOEF = new FunkySpinner(x, y, r);
            }
            try
            {
                this.m_sprite.addChild(this.spinnerF2F);
                this.m_sprite.addChild(this.spinnerEMA);
                if (this.spinnerOEF != null)
                {
                    this.m_sprite.addChild(this.spinnerOEF);
                }
            }
            catch (err:Error)
            {
                spinnerF2F = null;
                spinnerEMA = null;
                spinnerOEF = null;
                throw err;
            }
            return;
        }//end

        public void  deactivateSpinners ()
        {
            if (this.spinnerF2F != null)
            {
                this.m_sprite.removeChild(this.spinnerF2F);
            }
            if (this.spinnerEMA != null)
            {
                this.m_sprite.removeChild(this.spinnerEMA);
            }
            if (this.spinnerOEF != null)
            {
                this.m_sprite.removeChild(this.spinnerOEF);
            }
            this.spinnerF2F = null;
            this.spinnerEMA = null;
            this.spinnerOEF = null;
            return;
        }//end

        public boolean  isFullRedrawActive ()
        {
            return this.fullRedraw != null;
        }//end

        public void  activateFullRedraw ()
        {
            if (this.isFullRedrawActive)
            {
                return;
            }
            this.deactivateFullRedraw();
            this.fullRedraw = new FullRedrawForcer();
            try
            {
                this.m_sprite.addChild(this.fullRedraw);
            }
            catch (err:Error)
            {
                fullRedraw = null;
                throw err;
            }
            return;
        }//end

        public void  deactivateFullRedraw ()
        {
            if (this.fullRedraw != null)
            {
                this.m_sprite.removeChild(this.fullRedraw);
            }
            this.fullRedraw = null;
            return;
        }//end

        public boolean  keyCapture ()
        {
            return this.keysActive;
        }//end

        public void  keyCapture (boolean param1 )
        {
            this.keysActive = param1;
            return;
        }//end

        private boolean  isEmpty (DisplayObject param1 )
        {
            if (!param1.visible)
            {
                return true;
            }
            if (param1.width != 0)
            {
                return false;
            }
            if (param1.height != 0)
            {
                return false;
            }
            _loc_2 = (DisplayObjectContainer)param1
            if (_loc_2 == null)
            {
                return true;
            }
            return _loc_2.numChildren <= 0;
        }//end

        private void  prune_empties (DisplayObject param1 )
        {
            DisplayObject _loc_4 =null ;
            _loc_2 = (DisplayObjectContainer)param1
            if (_loc_2 == null)
            {
                return;
            }
            _loc_3 = _loc_2.numChildren-1;
            while (_loc_3 >= 0)
            {

                _loc_4 = _loc_2.getChildAt(_loc_3);
                if (!this.isEmpty(_loc_4))
                {
                }
                else
                {
                    _loc_2.removeChildAt(_loc_3);
                }
                _loc_3 = _loc_3 - 1;
            }
            return;
        }//end

    }




