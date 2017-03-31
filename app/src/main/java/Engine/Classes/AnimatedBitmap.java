package Engine.Classes;

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
import com.xiyu.util.BitmapData;
import com.xiyu.util.Dictionary;
import com.xiyu.util.Rectangle;

import Engine.*;
//import flash.display.*;
//import flash.geom.*;
import Engine.Interfaces.*;


    public class AnimatedBitmap extends Bitmap implements IDynamicDisplayObject
    {
        protected BitmapData m_targetBitmapData ;
        private BitmapData m_spriteSheet ;
        private int m_numFrames ;
        private double m_frameDelay ;
        protected int m_frameWidth ;
        protected int m_frameHeight ;
        protected Rectangle m_frameRect ;
        private int m_currentFrame ;
        private double m_frameTimer =0;
        private boolean m_isPlaying =true ;
        private int m_direction ;
        public static  int ANIMATE_FORWARD =1;
        public static  int ANIMATE_REVERSE =-1;
        public static Point m_destPoint =new Point(0,0);

        public  AnimatedBitmap (BitmapData param1 ,int param2 ,int param3 ,int param4 ,double param5 )
        {
            this.m_targetBitmapData = new BitmapData(param3, param4, true, 0);
            this.m_spriteSheet = param1;
            this.m_frameWidth = param3;
            this.m_frameHeight = param4;
            this.m_frameDelay = 1000 / param5;
            this.m_numFrames = param2;
            this.m_frameRect = new Rectangle(0, 0, param3, param4);
            this.m_currentFrame = 0;
            this.m_direction = ANIMATE_FORWARD;
            super(this.m_targetBitmapData, PixelSnapping.NEVER);
            this.draw();
            return;
        }//end

        public double  fps ()
        {
            return 1000 / this.m_frameDelay;
        }//end

        public AnimatedBitmap  clone ()
        {
            return new AnimatedBitmap(this.m_spriteSheet, this.m_numFrames, this.m_frameWidth, this.m_frameHeight, this.fps);
        }//end

        public void  reset ()
        {
            this.m_currentFrame = 0;
            this.m_isPlaying = true;
            return;
        }//end

        public void  randomFrame ()
        {
            this.m_currentFrame = MathUtil.random((this.m_numFrames - 1));
            return;
        }//end

        public double  numFrames ()
        {
            return this.m_numFrames;
        }//end

        public double  currentFrame ()
        {
            return this.m_currentFrame;
        }//end

        public void  currentFrame (double param1 )
        {
            this.m_currentFrame = MathUtil.clamp(param1, 0, this.m_numFrames);
            return;
        }//end

        public boolean  isPlaying ()
        {
            return Boolean(this.m_isPlaying);
        }//end

        public BitmapData  targetBitmapData ()
        {
            return this.m_targetBitmapData;
        }//end

        public void  targetBitmapData (BitmapData param1 )
        {
            this.m_targetBitmapData = param1;
            return;
        }//end

        public int  direction ()
        {
            return this.m_direction;
        }//end

        public void  direction (int param1 )
        {
            this.m_direction = param1;
            return;
        }//end

        public void  onUpdate (double param1 )
        {
            double _loc_2 =0;
            int _loc_3 =0;
            if (this.m_isPlaying)
            {
                this.m_frameTimer = this.m_frameTimer + param1 * 1000;
                _loc_2 = Math.floor(this.m_frameTimer / this.m_frameDelay);
                if (_loc_2 > 0)
                {
                    _loc_3 = this.m_currentFrame;
                    this.m_currentFrame = this.m_currentFrame + this.m_direction * _loc_2;
                    if (this.m_currentFrame >= this.m_numFrames)
                    {
                        this.m_currentFrame = this.m_currentFrame % this.m_numFrames;
                    }
                    else if (this.m_currentFrame < 0)
                    {
                        this.m_currentFrame = this.m_numFrames - -1 * this.m_currentFrame % this.m_numFrames;
                    }
                    this.m_frameTimer = this.m_frameTimer - _loc_2 * this.m_frameDelay;
                    if (this.m_currentFrame != _loc_3)
                    {
                        this.draw();
                    }
                }
            }
            else
            {
                this.m_frameTimer = 0;
            }
            return;
        }//end

        public void  stop ()
        {
            this.m_isPlaying = false;
            return;
        }//end

        public void  play ()
        {
            this.m_isPlaying = true;
            return;
        }//end

        protected void  draw ()
        {
            _loc_1 = Math.floor(this.m_spriteSheet.width /this.m_frameWidth );
            this.m_frameRect.x = this.m_currentFrame % _loc_1 * this.m_frameWidth;
            this.m_frameRect.y = Math.floor(this.m_currentFrame / _loc_1) * this.m_frameHeight;
            this.m_targetBitmapData.copyPixels(this.m_spriteSheet, this.m_frameRect, m_destPoint);
            return;
        }//end

        public BitmapData  spriteSheet ()
        {
            return this.m_spriteSheet;
        }//end

        public void  spriteSheet (BitmapData param1 )
        {
            this.m_spriteSheet = param1;
            return;
        }//end

    }



