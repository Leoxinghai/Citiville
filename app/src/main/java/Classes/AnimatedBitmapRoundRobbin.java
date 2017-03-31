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

    public class AnimatedBitmapRoundRobbin extends AnimatedBitmap
    {
        protected double m_drawTime =0;
        protected double m_delayTime =0;
        protected boolean m_drawing =true ;
        protected boolean m_firstDelay =false ;
        protected boolean m_enableRoundRobbin =false ;
        protected double m_frameDelay ;
public static  double DRAW_TIME =4;

        public  AnimatedBitmapRoundRobbin (BitmapData param1 ,int param2 ,int param3 ,int param4 ,double param5 )
        {
            super(param1, param2, param3, param4, param5);
            this.m_frameDelay = param5 > 0 ? (1 / param5) : (0);
            return;
        }//end

         public AnimatedBitmap  clone ()
        {
            AnimatedBitmapRoundRobbin _loc_1 =new AnimatedBitmapRoundRobbin(spriteSheet ,this.numFrames ,m_frameWidth ,m_frameHeight ,this.fps );
            _loc_1.roundRobbin = this.m_enableRoundRobbin;
            return _loc_1;
        }//end

        public double  frameDelay ()
        {
            return this.m_frameDelay;
        }//end

        public void  roundRobbin (boolean param1 )
        {
            this.m_enableRoundRobbin = param1;
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            if (!this.m_enableRoundRobbin)
            {
                super.onUpdate(param1);
                return;
            }
            _loc_2 =Global.world.defCon ;
            if (_loc_2 >= GameWorld.DEFCON_LEVEL2)
            {
                if (_loc_2 >= GameWorld.DEFCON_LEVEL4)
                {
                    return;
                }
                if (this.m_drawing)
                {
                    this.m_drawTime = this.m_drawTime - param1;
                    if (this.m_drawTime < 0)
                    {
                        this.m_drawing = false;
                        this.m_delayTime = _loc_2 == GameWorld.DEFCON_LEVEL2 ? (DRAW_TIME) : (3 * DRAW_TIME);
                        this.m_delayTime = this.m_delayTime + Math.random();
                        if (this.m_firstDelay)
                        {
                            this.m_delayTime = this.m_delayTime * Math.random();
                            this.m_firstDelay = false;
                        }
                    }
                }
                else
                {
                    this.m_delayTime = this.m_delayTime - param1;
                    if (this.m_delayTime < 0)
                    {
                        this.m_drawing = true;
                        this.m_drawTime = DRAW_TIME + Math.random();
                    }
                }
            }
            else
            {
                this.m_drawing = true;
                this.m_firstDelay = true;
                if (this.m_drawTime == 0)
                {
                    this.m_drawTime = DRAW_TIME * Math.random();
                }
            }
            if (this.m_drawing)
            {
                super.onUpdate(param1);
            }
            return;
        }//end

         protected void  draw ()
        {
            super.draw();
            return;
        }//end

    }



