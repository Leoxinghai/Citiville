package Classes.effects.Particle;

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
import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class ParticleEffect extends MapResourceEffect
    {
        protected String m_effectName ;
        protected ItemImage m_itemImage ;
        protected double m_originalFPS ;
        protected Sprite m_particleContainer ;
        protected boolean m_itemImageLoaded ;
        protected Array m_activeParticles ;
        protected Array m_doneParticles ;
        protected boolean m_isActive ;
        protected boolean m_isRunning ;
        protected double m_lastTime ;
        protected double m_lastSpawnTime ;
        public double spawnPeriod ;
        public double minVel ;
        public double maxVel ;
        public double minAngle ;
        public double maxAngle ;
        public double minDuration ;
        public double maxDuration ;
        public double minRotationVel ;
        public double maxRotationVel ;
        public double startAlpha ;
        public double endAlpha ;
        public double minStartScale ;
        public double maxStartScale ;
        public double endScale ;
        public double initRotRandomness ;
        public boolean loopParticleAnimation ;
        private static  double MAX_SCALE =300;

        public  ParticleEffect (MapResource param1 ,String param2 )
        {
            super(param1, 30);
            this.m_activeParticles = new Array();
            this.m_doneParticles = new Array();
            this.m_isActive = true;
            this.m_lastTime = getTimer();
            this.m_lastSpawnTime = getTimer();
            this.m_itemImageLoaded = false;
            this.m_isRunning = true;
            this.m_particleContainer = new Sprite();
            _loc_3 = this.getAttachParent ();
            if (_loc_3)
            {
                _loc_3.addChild(this.m_particleContainer);
            }
            this.m_effectName = param2;
            this.spawnPeriod = 0.5;
            this.minVel = 0;
            this.maxVel = 100;
            this.minAngle = 0;
            this.maxAngle = 360;
            this.minDuration = 0.5;
            this.maxDuration = 1;
            this.minRotationVel = -500;
            this.maxRotationVel = 500;
            this.startAlpha = 1;
            this.endAlpha = 0;
            this.minStartScale = 1;
            this.maxStartScale = 1;
            this.endScale = 1;
            this.initRotRandomness = 0;
            this.loopParticleAnimation = true;
            return;
        }//end

         public boolean  animate (int param1 )
        {

            if (!this.m_isActive)
            {
                return this.m_isActive;
            }
            if (!this.m_itemImage)
            {
                this.loadAnimationEffect();
            }
            if (!this.m_itemImageLoaded)
            {
                return this.m_isActive;
            }
            _loc_2 = getTimer();
            _loc_3 = _loc_2(-this.m_lastTime )/1000;
            this.m_lastTime = _loc_2;
            this.updateExistingParticles(_loc_3);
            this.cleanupDeadParticles();
            this.spawnNewParticles(_loc_3);
            return this.m_isActive;
        }//end

        protected void  updateExistingParticles (double param1 )
        {
            Particle _loc_2 =null ;
            double _loc_3 =0;
            double _loc_4 =0;
            int _loc_7 ;
            for(int i0 = 0; i0 < this.m_activeParticles.size(); i0++)
            {
            		_loc_2 = this.m_activeParticles.get(i0);


                _loc_2.activeTime = _loc_2.activeTime + param1;
                _loc_3 = _loc_7 / _loc_2.duration;
                if (_loc_3 > 1)
                {
                    _loc_2.displayObject.visible = false;
                    _loc_2.done = true;
                    continue;
                }
                this.updateParticleAnimation(_loc_2, param1);
                _loc_4 = 1 - _loc_3;
                _loc_2.displayObject.x = _loc_2.displayObject.x + _loc_2.vel.x * param1 * _loc_4;
                _loc_2.displayObject.y = _loc_2.displayObject.y + _loc_2.vel.y * param1 * _loc_4;
                _loc_2.displayObject.rotation = _loc_2.displayObject.rotation + _loc_2.deltaRotation * param1;
                _loc_2.displayObject.alpha = (this.endAlpha - this.startAlpha) * _loc_3 + this.startAlpha;
                _loc_7 = (_loc_2.endScale - _loc_2.startScale) * _loc_3 + _loc_2.startScale;
                _loc_2.displayObject.scaleY = _loc_7;
                _loc_2.displayObject.scaleX = _loc_7;
            }
            return;
        }//end

        protected void  cleanupDeadParticles ()
        {
            Particle _loc_2 =null ;
            _loc_1 = this.m_activeParticles.length -1;
            while (_loc_1 > -1)
            {

                if (this.m_activeParticles.get(_loc_1).done)
                {
                    this.m_doneParticles.push(this.m_activeParticles.splice(_loc_1, 1).get(0));
                }
                _loc_1 = _loc_1 - 1;
            }
            if (!this.m_isRunning && this.m_activeParticles.length < 1)
            {
                for(int i0 = 0; i0 < this.m_doneParticles.size(); i0++)
                {
                		_loc_2 = this.m_doneParticles.get(i0);

                    if (_loc_2.displayObject && _loc_2.displayObject.parent)
                    {
                        _loc_2.displayObject.parent.removeChild(_loc_2.displayObject);
                        _loc_2.displayObject = null;
                    }
                }
                this.m_doneParticles.splice(0, this.m_doneParticles.length());
            }
            return;
        }//end

        protected void  spawnNewParticles (double param1 )
        {
            Vector2 _loc_2 =null ;
            double _loc_3 =0;
            int _loc_4 =0;
            Particle _loc_5 =null ;
            if (this.m_isActive && this.m_isRunning)
            {
                _loc_2 = this.getTargetPos();
                _loc_3 = this.getNumNewParticles(param1);
                if (_loc_3 > 0)
                {
                    this.m_lastSpawnTime = getTimer();
                    _loc_4 = 0;
                    while (_loc_4 < _loc_3)
                    {

                        if (this.m_doneParticles.length > 0)
                        {
                            _loc_5 = this.m_doneParticles.splice(0, 1).get(0);
                        }
                        else
                        {
                            _loc_5 = new Particle();
                            this.attachNewParticle(_loc_5);
                        }
                        this.initParticle(_loc_5, _loc_2);
                        this.m_activeParticles.push(_loc_5);
                        _loc_4++;
                    }
                }
            }
            else
            {
                this.m_lastSpawnTime = getTimer();
            }
            return;
        }//end

        protected void  initParticle (Particle param1 ,Vector2 param2 )
        {
            _loc_3 = m_mapResource.displayObject;
            _loc_4 = m_mapResource.content;
            param1.lastFrame = -1;
            _loc_5 = this.maxVel -this.minVel ;
            _loc_6 = this.maxAngle -this.minAngle ;
            _loc_7 = this.maxDuration -this.minDuration ;
            _loc_8 = this.maxRotationVel -this.minRotationVel ;
            _loc_9 = this.maxStartScale -this.minStartScale ;
            _loc_10 = _loc_6(*Math.random()+this.minAngle)*Math.PI/180;
            Vector2 _loc_11 =new Vector2(Math.cos(_loc_10 ),Math.sin(_loc_10 ));
            _loc_12 = _loc_5*Math.random()+this.minVel;
            param1.vel = new Vector2(_loc_11.x * _loc_12, _loc_11.y * _loc_12);
            param1.startScale = Math.min(MAX_SCALE, (_loc_9 * Math.random() + this.minStartScale) * _loc_3.scaleX);
            param1.endScale = Math.min(MAX_SCALE, this.endScale * _loc_3.scaleX);
            param1.deltaRotation = _loc_8 * Math.random() + this.minRotationVel;
            param1.duration = this.minDuration + _loc_7 * Math.random();
            param1.activeTime = 0;
            param1.done = false;
            param1.displayObject.x = param2.x;
            param1.displayObject.y = param2.y;
            param1.displayObject.rotation = MathUtil.randomWobble(0, Math.min(this.initRotRandomness, 360));
            param1.displayObject.alpha = this.startAlpha;
            param1.displayObject.visible = false;
            _loc_13 = param1.startScale ;
            param1.displayObject.scaleY = param1.startScale;
            param1.displayObject.scaleX = _loc_13;
            return;
        }//end

        public void  attachNewParticle (Particle param1 )
        {
            if (!this.m_itemImage || !this.m_itemImage.getInstance())
            {
                return;
            }
            this.m_itemImageLoaded = true;
            _loc_2 =(DisplayObject) this.m_itemImage.getInstance ().image;
            _loc_2.x = _loc_2.x - _loc_2.width / 2;
            _loc_2.y = _loc_2.y - _loc_2.height / 2;
            param1.displayObject = new Sprite();
            ((Sprite)param1.displayObject).addChild(_loc_2);
            ((Sprite)param1.displayObject).mouseEnabled = false;
            ((Sprite)param1.displayObject).mouseChildren = false;
            if (_loc_2 instanceof AnimatedBitmap)
            {
                param1.animation =(AnimatedBitmap) _loc_2;
            }
            if (this.m_particleContainer)
            {
                this.m_particleContainer.addChild(param1.displayObject);
            }
            return;
        }//end

        protected void  updateParticleAnimation (Particle param1 ,double param2 )
        {
            AnimatedBitmap _loc_3 =null ;
            double _loc_4 =0;
            int _loc_5 =0;
            boolean _loc_6 =false ;
            if (param1.animation)
            {
                _loc_3 = param1.animation;
                if (!_loc_3.isPlaying && param1.lastFrame < 0)
                {
                    _loc_3.reset();
                }
                if (!this.loopParticleAnimation)
                {
                    _loc_4 = 1000 / this.m_originalFPS;
                    _loc_5 = Math.ceil(param2 * 1000 / _loc_4);
                    _loc_6 = _loc_3.currentFrame + _loc_5 > (_loc_3.numFrames - 1);
                    _loc_6 = _loc_6 || (_loc_3.currentFrame < param1.lastFrame || _loc_3.currentFrame == (_loc_3.numFrames - 1));
                    if (_loc_3.isPlaying && _loc_6)
                    {
                        _loc_3.stop();
                    }
                    else
                    {
                        param1.lastFrame = _loc_3.currentFrame;
                    }
                }
                _loc_3.onUpdate(param2);
            }
            param1.displayObject.visible = true;
            return;
        }//end

        protected Vector2  getTargetPos ()
        {
            _loc_1 = m_mapResource.displayObject;
            _loc_2 = m_mapResource.content;
            _loc_3 = this.getTargetOffset ();
            _loc_4 = _loc_2.x +_loc_3.x ;
            _loc_5 = _loc_2.y +_loc_3.y ;
            _loc_6 = _loc_2.parent.localToGlobal(new Point(_loc_4 ,_loc_5 ));
            _loc_7 = this.m_particleContainer.globalToLocal(_loc_6 );
            _loc_4 = this.m_particleContainer.globalToLocal(_loc_6).x;
            _loc_5 = _loc_7.y;
            return new Vector2(_loc_4, _loc_5);
        }//end

        protected int  getNumNewParticles (double param1 )
        {
            _loc_2 = getTimer(()-this.m_lastSpawnTime)/1000;
            return Math.floor(_loc_2 / this.spawnPeriod);
        }//end

        protected Vector2  getTargetOffset ()
        {
            _loc_1 = m_mapResource.content;
            return new Vector2(_loc_1.width / 2, _loc_1.height / 2);
        }//end

        public void  setIsRunning (boolean param1 )
        {
            this.m_isRunning = param1;
            return;
        }//end

        public boolean  getIsRunning ()
        {
            return this.m_isRunning;
        }//end

        protected Sprite  getAttachParent ()
        {
            if (m_mapResource.getDisplayObject().parent)
            {
                return (Sprite)m_mapResource.getDisplayObject().parent;
            }
            return null;
        }//end

         public void  cleanUp ()
        {
            Particle _loc_1 =null ;
            Particle _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_activeParticles.size(); i0++)
            {
            		_loc_1 = this.m_activeParticles.get(i0);

                if (_loc_1.displayObject && _loc_1.displayObject.parent)
                {
                    _loc_1.displayObject.parent.removeChild(_loc_1.displayObject);
                    _loc_1.displayObject = null;
                }
            }
            for(int i0 = 0; i0 < this.m_doneParticles.size(); i0++)
            {
            		_loc_2 = this.m_doneParticles.get(i0);

                if (_loc_2.displayObject && _loc_2.displayObject.parent)
                {
                    _loc_2.displayObject.parent.removeChild(_loc_2.displayObject);
                    _loc_2.displayObject = null;
                }
            }
            this.m_activeParticles = null;
            this.m_doneParticles = null;
            if (this.m_particleContainer && this.m_particleContainer.parent)
            {
                this.m_particleContainer.parent.removeChild(this.m_particleContainer);
            }
            if (this.m_itemImage)
            {
                this.m_itemImage.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                this.m_itemImage = null;
            }
            this.m_isActive = false;
            return;
        }//end

        protected void  loadAnimationEffect ()
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            _loc_1 = m_mapResource.content&& m_mapResource.getItemImage() && !m_mapResource.isCurrentImageLoading();
            if (_loc_1 && !this.m_itemImage)
            {
                _loc_2 = Global.gameSettings().getEffectByName(this.m_effectName);
                _loc_3 = _loc_2.image.get(0);
                this.m_originalFPS = _loc_3.@fps || 1;
                this.m_itemImage = new ItemImage(_loc_3);
                this.m_itemImage.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                this.m_itemImage.load();
            }
            return;
        }//end

        protected void  onItemImageLoaded (LoaderEvent event )
        {
            if (event.target == this.m_itemImage)
            {
                this.m_itemImage.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                this.m_itemImageLoaded = true;
            }
            return;
        }//end

        public boolean  isComplete ()
        {
            return !this.m_isActive;
        }//end

    }



