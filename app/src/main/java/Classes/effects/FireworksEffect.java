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
//import flash.filters.*;

    public class FireworksEffect extends AnimationEffect
    {
        protected Function m_effectCompleteCallback ;

        public  FireworksEffect (MapResource param1 ,String param2 ,boolean param3 =false ,boolean param4 =false ,boolean param5 =false ,Function param6 =null )
        {
            this.m_effectName = param2;
            this.m_isAutoUpdating = param3;
            this.m_isLooping = param4;
            this.m_lastFrame = -1;
            this.m_isActive = true;
            this.m_isCentering = param5;
            this.m_effectCompleteCallback = param6;
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         public void  reattach ()
        {
            AnimatedBitmap _loc_2 =null ;
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            if (!m_itemImage || !m_itemImage.getInstance())
            {
                return;
            }
            if (!m_effectImage)
            {
                m_effectImage =(DisplayObject) m_itemImage.getInstance().image;
                _loc_2 =(AnimatedBitmap) m_effectImage;
                _loc_2.height = _loc_2.height * (0.5 * Math.random());
                _loc_2.width = _loc_2.width * Math.random();
                _loc_3 = [[0.9, 0.8, 0.3], [0.7, 0.1, 0.1], [0.6, 0.1, 0.5], [0.9, 0.2, 0.9], [0.3, 0.9, 0.2], [0.5, 0.2, 0.7], [0.9, 0.2, 0.4], [0.9, 0.6, 0.8], [0.7, 0.9, 0.5], [0.7, 0.3, 0.9], [0.5, 0.9, 0.9], [0.8, 0.9, 0.2], [0.8, 0.9, 0.2], [0.9, 0.8, 0.1]];
                _loc_4 = _loc_3.get(Math.floor(Math.random() * _loc_3.length()));
                _loc_5 = new Array();
                _loc_5.put(0,  _loc_4.get(0));
                _loc_5.put(6,  _loc_4.get(1));
                _loc_5.put(12,  _loc_4.get(2));
                _loc_5.put(18,  1);
                _loc_6 = .get(new ColorMatrixFilter(_loc_5));
                _loc_2.filters = _loc_6;
            }
            m_effectImage.x = -(m_effectImage.width >> 1) + m_itemImage.offsetX + x;
            m_effectImage.y = -(m_effectImage.height >> 1) + m_itemImage.offsetY + y - 130;
            _loc_1 = getAttachParent();
            if (_loc_1)
            {
                _loc_1.addChild(m_effectImage);
            }
            if (!_loc_2)
            {
                m_isActive = false;
            }
            return;
        }//end

         public boolean  animate (int param1 )
        {
            AnimatedBitmap _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            double _loc_4 =0;
            boolean _loc_5 =false ;
            if (!m_isActive)
            {
                return m_isActive;
            }
            if (!m_itemImage)
            {
                loadAnimationEffect();
            }
            if (m_effectImage)
            {
                _loc_2 =(AnimatedBitmap) m_effectImage;
                _loc_3 =(DisplayObject) m_mapResource.content;
                if (_loc_2)
                {
                    if (m_isAutoUpdating)
                    {
                        _loc_4 = 1 / m_originalFPS;
                        updateAnimatedBitmap(_loc_4);
                    }
                    if (!_loc_2.isPlaying)
                    {
                        _loc_2.reset();
                    }
                    if (!m_isLooping)
                    {
                        _loc_5 = _loc_2.currentFrame < m_lastFrame || _loc_2.currentFrame == (_loc_2.numFrames - 1);
                        if (_loc_2.isPlaying && _loc_5)
                        {
                            _loc_2.stop();
                            this.cleanUp();
                            _loc_3.visible = true;
                            m_isActive = false;
                            if (this.m_effectCompleteCallback != null)
                            {
                                this.m_effectCompleteCallback();
                            }
                        }
                        else
                        {
                            m_lastFrame = _loc_2.currentFrame;
                        }
                    }
                }
                else
                {
                    m_isActive = false;
                }
            }
            return m_isActive;
        }//end

    }



