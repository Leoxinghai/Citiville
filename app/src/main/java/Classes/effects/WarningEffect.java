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
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Interfaces.*;
//import flash.display.*;
import com.zynga.skelly.animation.*;
//import flash.geom.*;
import com.zynga.skelly.render.*;

import com.xinghai.Debug;

    public class WarningEffect extends Sprite implements IAnimated
    {
        protected String m_effectName ;
        protected ItemImage m_itemImage ;
        protected DisplayObject m_effectImage ;
        protected int m_lastFrame ;
        protected boolean m_isActive ;
        protected boolean m_isAutoUpdating ;
        protected boolean m_isLooping ;
        protected double m_originalFPS ;
        protected boolean m_isCentering ;

        protected double m_fps ;
        protected boolean m_isCompleted =false ;
        protected EffectType m_effectType ;
        protected Point m_point ;

        protected Sprite m_parent ;
        protected double m_offsetY ;


        public  WarningEffect (Point param1 ,String param2 ,boolean param3 =false ,boolean param4 =false ,boolean param5 =false )
        {
            this.m_effectName = param2;
            this.m_isAutoUpdating = param3;
            this.m_isLooping = param4;
            this.m_lastFrame = -1;
            this.m_isActive = true;
            this.m_isCentering = param5;
            m_point = param1;
            m_fps = 20;
            m_offsetY = 0;

            RenderManager.addAnimationByFPS(this.m_fps, this);
            return;
        }//end

        public boolean  isAutoUpdating ()
        {
            return this.m_isAutoUpdating;
        }//end

        public boolean  isLooping ()
        {
            return this.m_isLooping;
        }//end

        public boolean  animate (int param1 )
        {
            AnimatedBitmap _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            double _loc_4 =0;
            boolean _loc_5 =false ;


            if (!this.m_isActive)
            {
                return this.m_isActive;
            }
            if (!this.m_itemImage)
            {
                this.loadAnimationEffect();
            }
            if (this.m_effectImage)
            {
                _loc_2 =(AnimatedBitmap) this.m_effectImage;

                if (_loc_2)
                {
                    if (this.m_isAutoUpdating)
                    {
                        _loc_4 = 1 / this.m_originalFPS;
                        this.updateAnimatedBitmap(_loc_4);
                    }
                    if (!_loc_2.isPlaying)
                    {
                        _loc_2.reset();
                    }
                    if (!this.m_isLooping)
                    {
                        _loc_5 = _loc_2.currentFrame < this.m_lastFrame || _loc_2.currentFrame == (_loc_2.numFrames - 1);
                        //if (_loc_2.isPlaying && _loc_5)
                        if (m_offsetY <-10)
                        {
                            _loc_2.stop();

                            this.m_isActive = false;
			    _loc_11 = this.getAttachParent ();
			    if (_loc_11)
			    {
				_loc_11.removeChild(this.m_effectImage);
			    }
			    this.m_effectImage = null;
                        }
                        else
                        {
                            this.m_lastFrame = _loc_2.currentFrame;
                        }
                    }
                }
                else
                {
                    this.m_isActive = false;
                }
            }

            return this.m_isActive;
        }//end

        public void  updateAnimatedBitmap (double param1 )
        {
            if (this.m_effectImage)
            {
                if (this.m_effectImage instanceof IDynamicDisplayObject)
                {
                    m_offsetY -=param1;

                    this.m_effectImage.y = this.m_effectImage.y + m_offsetY;
                    ((IDynamicDisplayObject)this.m_effectImage).onUpdate(param1);
                }
                else
                {
                    this.m_isActive = false;
                }
            }
            return;
        }//end

        public boolean  allowReattachOnReplaceContent ()
        {
            return true;
        }//end

        public void  reattach ()
        {

            if (!this.m_itemImage || !this.m_itemImage.getInstance())
            {
                return;
            }
            if (!this.m_effectImage)
            {
                this.m_effectImage =(DisplayObject) this.m_itemImage.getInstance().image;
            }

            if (this.m_isCentering)
            {
                this.m_effectImage.x = 0;
                this.m_effectImage.y = 0;
            }
            else
            {
                this.m_effectImage.x = -(this.m_effectImage.width >> 1) + this.m_itemImage.offsetX + x;
                this.m_effectImage.y = -(this.m_effectImage.height >> 1) + this.m_itemImage.offsetY + y;
            }


            _loc_1 = this.getAttachParent ();
            if (_loc_1)
            {
                _loc_1.addChild(this.m_effectImage);
            }
            _loc_2 =(AnimatedBitmap) this.m_effectImage;
            if (!_loc_2)
            {
                this.m_isActive = false;
            }
            return;
        }//end

        protected Sprite  getAttachParent ()
        {

            if(m_parent == null) {
            	m_parent = new Sprite();
            	m_parent.x = m_point.x;
            	m_parent.y = m_point.y;
            	m_parent.scaleY = 0.3;
            	m_parent.scaleX = 0.3;
                _loc_1 = GlobalEngine.viewport.getLayer("object");
              	_loc_1.addChild(m_parent);
            }
            return m_parent;
        }//end

        public void  cleanUp ()
        {
            if (this.m_effectImage && this.m_effectImage.parent)
            {
                this.m_effectImage.parent.removeChild(this.m_effectImage);
                this.m_effectImage = null;
            }
            if (this.m_itemImage)
            {
                this.m_itemImage.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                this.m_itemImage = null;
            }
            this.m_isActive = false;
            m_isCompleted = true;
            return;
        }//end

        protected void  loadAnimationEffect ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
	    _loc_2 = Global.gameSettings().getEffectByName(this.m_effectName);
	    if (_loc_2)
	    {
	      _loc_3 = _loc_2.image.get(0);
	       this.m_originalFPS = _loc_3.@fps || 1;
	      this.m_itemImage = new ItemImage(_loc_3);
	    }

	    if (this.m_itemImage)
	    {
	       this.m_itemImage.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
	       this.m_itemImage.load();
	    }

            return;
        }//end

        protected void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImageInstance _loc_2 =null ;
            if (event.target == this.m_itemImage)
            {
                _loc_2 = this.m_itemImage.getInstance();
                if (_loc_2)
                {
                    this.m_itemImage.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                    this.reattach();
                }
            }
            return;
        }//end

        public boolean  isComplete ()
        {
            return !this.m_isActive;
        }//end

        public DisplayObject  effectImage ()
        {
            return this.m_effectImage;
        }//end

        public void  effectImage (DisplayObject param1 )
        {
            this.m_effectImage = param1;
            return;
        }//end

    }


