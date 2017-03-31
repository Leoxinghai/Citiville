package com.zynga.skelly.render;

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
//import flash.system.*;
//import flash.text.*;
//import flash.utils.*;

    public class FrameRateGraph extends Sprite implements IRenderer
    {
        private  int BARS =60;
        private  int MOVING_AVG_FRAMES =30;
        public  int BAR_WIDTH =1;
        private  int YELLOW_CUTOFF =50;
        private  int RED_CUTOFF =100;
        private TextField m_tfFps ;
        private TextField m_tfMovingAvgFps ;
        private int m_lastTime ;
        private Array m_frameTimes ;
        public Array m_frameBars ;
        private boolean m_bNoChart =false ;
        private String m_mem ;
        private int m_secondBucket =0;
        private static int nextColor =0;
        private static FrameRateGraph _instance ;
        private static Bitmap debugBitmapDisplay =new Bitmap ();
        private static Sprite m_sprite =new Sprite ();

        public  FrameRateGraph ()
        {
            int _loc_3 =0;
            Shape _loc_4 =null ;
            Shape _loc_5 =null ;
            Shape _loc_6 =null ;
            this.m_frameTimes = new Array();
            this.m_frameBars = new Array();
            _instance = this;
            m_sprite.addChild(debugBitmapDisplay);
            addChild(m_sprite);
            m_sprite.y = 10;
            Shape _loc_1 =new Shape ();
            _loc_1.graphics.lineStyle(2, 16777215);
            _loc_1.graphics.beginFill(0);
            _loc_1.graphics.drawRoundRect(-5, -this.RED_CUTOFF - 5, this.m_bNoChart ? (30) : (this.BARS * this.BAR_WIDTH + 10), this.m_bNoChart ? (45) : (this.RED_CUTOFF + 10), 5, 5);
            _loc_1.graphics.endFill();
            addChild(_loc_1);
            int _loc_2 =0;
            while (_loc_2 < this.BARS)
            {

                this.m_frameTimes.push(10);
                _loc_2++;
            }
            if (!this.m_bNoChart)
            {
                _loc_3 = 0;
                while (_loc_3 < this.BARS)
                {

                    _loc_6 = new Shape();
                    this.m_frameBars.push(_loc_6);
                    addChild(_loc_6);
                    _loc_3++;
                }
                _loc_4 = new Shape();
                _loc_4.graphics.lineStyle(1, 16776960);
                _loc_4.graphics.moveTo(0, -this.YELLOW_CUTOFF);
                _loc_4.graphics.lineTo(this.BARS * this.BAR_WIDTH, -this.YELLOW_CUTOFF);
                addChild(_loc_4);
                _loc_5 = new Shape();
                _loc_5.graphics.lineStyle(0, 16711680);
                _loc_5.graphics.moveTo(0, -this.RED_CUTOFF);
                _loc_5.graphics.lineTo(this.BARS * this.BAR_WIDTH, -this.RED_CUTOFF);
                addChild(_loc_5);
            }
            this.m_tfFps = new TextField();
            this.m_tfFps.text = "9999999";
            this.m_tfFps.width = this.m_tfFps.textWidth;
            this.m_tfFps.textColor = 16777215;
            this.m_tfFps.y = -100;
            this.m_tfFps.selectable = false;
            addChild(this.m_tfFps);
            this.m_tfMovingAvgFps = new TextField();
            this.m_tfMovingAvgFps.text = "9999999999";
            this.m_tfMovingAvgFps.width = this.m_tfMovingAvgFps.textWidth;
            this.m_tfMovingAvgFps.textColor = 16777215;
            this.m_tfMovingAvgFps.y = -80;
            this.m_tfMovingAvgFps.selectable = false;
            addChild(this.m_tfMovingAvgFps);
            return;
        }//end

        public void  activate ()
        {
            RenderManager.addFrameBeginUpdater(this);
            return;
        }//end

        public void  deactivate ()
        {
            RenderManager.removeFrameBeginUpdater(this);
            return;
        }//end

        public boolean  onRender (int param1 ,int param2 )
        {
            Shape _loc_7 =null ;
            int _loc_8 =0;
            _loc_3 = getTimer();
            _loc_4 = _loc_3-this.m_lastTime ;
            this.m_lastTime = _loc_3;
            this.m_secondBucket = this.m_secondBucket + _loc_4;
            this.m_mem = Number(System.totalMemory / 1024 / 1024).toFixed(1) + "mb";
            if (this.m_secondBucket > 1000)
            {
                this.m_secondBucket = 0;
            }
            this.m_frameTimes.push(_loc_4);
            this.m_frameTimes.shift();
            int _loc_5 =0;
            _loc_6 = this.BARS -this.MOVING_AVG_FRAMES ;
            while (_loc_6 < this.BARS)
            {

                _loc_5 = _loc_5 + this.m_frameTimes.get(_loc_6);
                _loc_6++;
            }
            _loc_5 = _loc_5 / this.MOVING_AVG_FRAMES;
            if (!this.m_bNoChart)
            {
                _loc_7 = this.m_frameBars.shift();
                _loc_7.graphics.clear();
                if (nextColor)
                {
                    _loc_8 = nextColor;
                    nextColor = 0;
                }
                else if (_loc_4 >= this.RED_CUTOFF)
                {
                    _loc_8 = 16711680;
                }
                else if (_loc_4 >= this.YELLOW_CUTOFF)
                {
                    _loc_8 = 16776960;
                }
                else
                {
                    _loc_8 = 52224;
                }
                _loc_7.graphics.lineStyle(this.BAR_WIDTH, _loc_8);
                _loc_7.graphics.moveTo(0, -_loc_4);
                _loc_7.graphics.lineTo(0, 0);
                _loc_7.graphics.lineStyle(0, 16777215);
                _loc_7.graphics.moveTo(0, -_loc_5);
                _loc_7.graphics.lineTo(this.BAR_WIDTH, -_loc_5);
                this.m_frameBars.push(_loc_7);
                _loc_6 = 0;
                while (_loc_6 < this.BARS)
                {

                    _loc_7 =(Shape) this.m_frameBars.get(_loc_6);
                    _loc_7.x = _loc_6 * this.BAR_WIDTH;
                    _loc_6++;
                }
            }
            this.m_tfFps.text = _loc_4.toString() + "ms/f";
            this.m_tfMovingAvgFps.text = Math.floor(1000 / _loc_5).toString() + " | " + this.m_mem;
            return true;
        }//end

        public static void  annotate (int param1 )
        {
            nextColor = param1;
            return;
        }//end

        public static void  attach (BitmapData param1 )
        {
            debugBitmapDisplay.bitmapData = param1;
            return;
        }//end

        public static Sprite  debugSprite ()
        {
            return m_sprite;
        }//end

    }


