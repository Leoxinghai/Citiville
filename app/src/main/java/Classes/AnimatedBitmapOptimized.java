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

import Engine.Classes.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class AnimatedBitmapOptimized extends AnimatedBitmapRoundRobbin
    {
        private boolean bDisable =false ;
        protected Array m_bitmaps =null ;
        protected Array m_halfBitmaps =null ;
        protected int m_frameSkipCount =0;
        protected boolean m_useAllFrames =false ;
        protected boolean m_playOnce =false ;
        private int m_lastFrame =0;
        public static Dictionary s_cachedBitmaps =new Dictionary ();
        public static Dictionary s_cachedHalfBitmaps =new Dictionary ();

        public  AnimatedBitmapOptimized (BitmapData param1 ,int param2 ,int param3 ,int param4 ,double param5 ,boolean param6 )
        {
            super(param1, param2, param3, param4, param5);
            this.cacheBitmaps(param5, param6);
            return;
        }//end

         public AnimatedBitmap  clone ()
        {
            AnimatedBitmapOptimized _loc_1 =new AnimatedBitmapOptimized(spriteSheet ,this.numFrames ,m_frameWidth ,m_frameHeight ,this.fps ,false );
            _loc_1.roundRobbin = m_enableRoundRobbin;
            _loc_1.bDisable = this.bDisable;
            _loc_1.playOnce = this.m_playOnce;
            return _loc_1;
        }//end

        public void  playOnce (boolean param1 )
        {
            this.m_playOnce = param1;
            return;
        }//end

        public boolean  playOnce ()
        {
            return this.m_playOnce;
        }//end

        public void  enable (boolean param1 )
        {
            this.bDisable = !param1;
            return;
        }//end

        protected void  cacheBitmaps (double param1 ,boolean param2 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            Bitmap _loc_5 =null ;
            ColorTransform _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            Matrix _loc_9 =null ;
            BitmapData _loc_10 =null ;
            this.m_bitmaps = s_cachedBitmaps.get(spriteSheet);
            this.m_halfBitmaps = s_cachedHalfBitmaps.get(spriteSheet);
            if (this.m_bitmaps != null && this.m_bitmaps.length != numFrames)
            {
                this.m_bitmaps = null;
            }
            if (this.m_bitmaps == null)
            {
                if (this.numFrames < 1)
                {
                    this.m_bitmaps = new Array();
                    this.m_halfBitmaps = new Array();
                    return;
                }
                this.m_bitmaps = new Array();
                this.m_halfBitmaps = new Array();
                if(spriteSheet != null) {
                Debug.debug6("AnimatedBitmapOptimized."+spriteSheet);
                _loc_3 = Math.floor(spriteSheet.width / m_frameWidth);
                } else {
                _loc_3 = 1;
                }

                _loc_4 = 0;
                while (_loc_4 < numFrames)
                {

                    m_frameRect.x = _loc_4 % _loc_3 * m_frameWidth;
                    m_frameRect.y = Math.floor(_loc_4 / _loc_3) * m_frameHeight;
                    if (_loc_4 == 0)
                    {
                        _loc_10 = m_targetBitmapData;
                    }
                    else
                    {
                        _loc_10 = new BitmapData(m_frameWidth, m_frameHeight, true, 0);
                    }
                    _loc_10.copyPixels(spriteSheet, m_frameRect, m_destPoint);
                    this.m_bitmaps.push(_loc_10);
                    _loc_4++;
                }
                if (param2)
                {
                    spriteSheet.dispose();
                }
                _loc_5 = new Bitmap();
                _loc_6 = null;
                _loc_7 = Math.floor(m_frameWidth / 2);
                _loc_8 = Math.floor(m_frameHeight / 2);
                _loc_9 = new Matrix();
                _loc_9.scale(_loc_7 / m_frameWidth, _loc_8 / m_frameHeight);
                if (param1 < 10 && numFrames < 4)
                {
                    this.m_useAllFrames = true;
                }
                _loc_4 = 0;
                while (_loc_4 < numFrames)
                {

                    _loc_10 = new BitmapData(m_frameWidth / 2, m_frameHeight / 2, true, 0);
                    _loc_5.bitmapData = this.m_bitmaps.get(_loc_4);
                    _loc_10.draw(_loc_5, _loc_9, _loc_6, null, null, true);
                    this.m_halfBitmaps.push(_loc_10);
                    if (!this.m_useAllFrames)
                    {
                        _loc_4++;
                    }
                    _loc_4++;
                }
                s_cachedBitmaps.put(spriteSheet,  this.m_bitmaps);
                s_cachedHalfBitmaps.put(spriteSheet,  this.m_halfBitmaps);
            }
            if (this.m_bitmaps)
            {
                _loc_10 = this.m_bitmaps.get(0);
                if (_loc_10)
                {
                    m_targetBitmapData = _loc_10;
                    this.bitmapData = _loc_10;
                    this.scaleX = 1;
                    this.scaleY = 1;
                }
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            this.m_lastFrame = this.currentFrame;
            super.onUpdate(param1);
            return;
        }//end

         protected void  draw ()
        {
            BitmapData _loc_1 =null ;
            double _loc_2 =0;
            boolean _loc_3 =false ;
            int _loc_4 =0;
            int _loc_5 =0;
            Debug.debug4("AnimatedBitmapOptimized.draw");
            if (this.m_bitmaps == null)
            {
                return;
            }
            if (this.playOnce && this.m_lastFrame > this.currentFrame)
            {
                this.currentFrame = this.numFrames - 1;
                this.enable = false;
                this.stop();
            }
            if (!this.bDisable)
            {
                this.m_frameSkipCount--;
                if (this.m_frameSkipCount > 0)
                {
                    return;
                }
                this.m_frameSkipCount = 0;
                if (GameWorld.enableUpdateShardingOrCulling)
                {
                    if (!Global.playAnimations && Global.gameSettings().minZoom == GlobalEngine.viewport.getZoom())
                    {
                        return;
                    }
                }
                else
                {
                    _loc_2 = Global.gameSettings().getNumber("maxZoom", 4);
                    _loc_3 = GlobalEngine.viewport.getZoom() < _loc_2;
                    _loc_4 = Global.world.defCon;
                    if (_loc_4 >= GameWorld.DEFCON_LEVEL2)
                    {
                        if (_loc_4 >= GameWorld.DEFCON_LEVEL3)
                        {
                            this.m_frameSkipCount = 4;
                        }
                        else
                        {
                            this.m_frameSkipCount = 2;
                        }
                    }
                    if (this.m_useAllFrames)
                    {
                        this.m_frameSkipCount = 0;
                    }
                    if (_loc_3)
                    {
                        _loc_5 = currentFrame;
                        if (!this.m_useAllFrames)
                        {
                            _loc_5 = _loc_5 / 2;
                        }
                        _loc_1 = this.m_halfBitmaps.get(_loc_5);
                        if (_loc_1)
                        {
                            m_targetBitmapData = _loc_1;
                            this.bitmapData = _loc_1;
                            this.scaleX = 2;
                            this.scaleY = 2;
                        }
                        return;
                    }
                }
            }
            _loc_1 = this.m_bitmaps.get(currentFrame);
            if (_loc_1)
            {
                m_targetBitmapData = _loc_1;
                this.bitmapData = _loc_1;
                this.scaleX = 1;
                this.scaleY = 1;
            }
            return;
        }//end

    }




