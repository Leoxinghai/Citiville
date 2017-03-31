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
//import flash.display.*;

    public class DelayedAnimationEffect extends AnimationEffect
    {
        protected boolean m_delayed =false ;
        protected double m_delayFrames ;
        protected double m_currentDelayFrame =0;
        protected boolean m_randomDelay =true ;
        protected double m_delayMin ;
        protected double m_delayMax ;

        public  DelayedAnimationEffect (MapResource param1 ,String param2 )
        {
            this.m_delayMin = 0;
            this.m_delayMax = 0;
            super(param1, param2, false, true, true);
            return;
        }//end  

         public boolean  animate (int param1 )
        {
            AnimatedBitmap _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            boolean _loc_4 =false ;
            double _loc_5 =0;
            if (!m_isActive)
            {
                return m_isActive;
            }
            if (!m_itemImage)
            {
                loadAnimationEffect();
            }
            switch(Global.world.defCon)
            {
                case 1:
                {
                    this.m_delayMin = 0;
                    this.m_delayMax = 0;
                    break;
                }
                case 2:
                {
                    this.m_delayMin = 20;
                    this.m_delayMax = 20;
                    break;
                }
                case 3:
                {
                    this.m_delayMin = 40;
                    this.m_delayMax = 40;
                    break;
                }
                case 4:
                {
                    this.m_delayMin = 60;
                    this.m_delayMax = 60;
                    break;
                }
                case 5:
                {
                    this.m_delayMin = 80;
                    this.m_delayMax = 80;
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (m_effectImage)
            {
                _loc_2 =(AnimatedBitmap) m_effectImage;
                _loc_3 =(DisplayObject) m_mapResource.content;
                if (_loc_2)
                {
                    if (!this.m_delayed)
                    {
                        _loc_5 = 1 / m_originalFPS;
                        updateAnimatedBitmap(_loc_5);
                    }
                    if (!_loc_2.isPlaying)
                    {
                        _loc_2.reset();
                    }
                    _loc_4 = _loc_2.currentFrame < m_lastFrame || _loc_2.currentFrame == (_loc_2.numFrames - 1);
                    if (_loc_2.isPlaying && _loc_4)
                    {
                        if (!this.m_delayed && this.m_delayMax > 0)
                        {
                            this.m_delayed = true;
                            _loc_2.visible = false;
                            this.m_delayFrames = int(Math.random() * this.m_delayMin) + this.m_delayMax;
                        }
                        else
                        {
                            this.m_currentDelayFrame = this.m_currentDelayFrame + param1;
                            if (this.m_currentDelayFrame >= this.m_delayFrames)
                            {
                                _loc_2.reset();
                                m_lastFrame = -1;
                                this.m_delayed = false;
                                this.m_currentDelayFrame = 0;
                                _loc_2.visible = true;
                                updateAnimatedBitmap(1 / m_originalFPS);
                            }
                        }
                    }
                    else
                    {
                        m_lastFrame = _loc_2.currentFrame;
                    }
                }
                else
                {
                    m_isActive = false;
                }
            }
            return m_isActive;
        }//end  

         public void  reattach ()
        {
            if (!m_itemImage || !m_itemImage.getInstance())
            {
                return;
            }
            if (!m_effectImage)
            {
                m_effectImage =(DisplayObject) m_itemImage.getInstance().image;
            }
            m_effectImage.x = m_mapResource.content.x + m_itemImage.offsetX + (m_mapResource.content.width >> 1) - (m_effectImage.width >> 1);
            m_effectImage.y = m_mapResource.content.y + m_itemImage.offsetY + (m_mapResource.content.height >> 1) - (m_effectImage.height >> 1);
            _loc_1 = getAttachParent();
            if (_loc_1)
            {
                _loc_1.addChild(m_effectImage);
            }
            _loc_2 = (AnimatedBitmap)m_effectImage
            if (!_loc_2)
            {
                m_isActive = false;
            }
            return;
        }//end  

    }



