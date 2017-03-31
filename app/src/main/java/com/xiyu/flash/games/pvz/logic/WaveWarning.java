package com.xiyu.flash.games.pvz.logic;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

import com.xiyu.flash.framework.utils.Utils;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.PVZFoleyType;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;

    public class WaveWarning {

        private static  int FINAL_WAVE_GREEN =(50/0xFF );//0
        private static  int FINAL_FADE_OUT_TIME =12;
        private static  int HUGE_WAVE_BLUE =(9/0xFF );//0
        private static  int HUGE_FADE_OUT_TIME =12;
        private static  int FINAL_WAVE_BLUE =(50/0xFF );//0
        private static  int HUGE_WAVE_GREEN =(9/0xFF );//0
        private static  int FINAL_FADE_IN_TIME =50;
        private static  int HUGE_FADE_IN_TIME =18;
        private static  int FINAL_WAVE_RED =1;
        private static  int HUGE_WAVE_RED =(246/0xFF );//0

        private int mFinalWaveTimer =0;

        public void draw (Graphics2D g ){
            if (this.mHugeWaveTimer > 0)
            {
                this.mHugeWaveBounds.x = this.mHugeWaveText.x();
                this.mHugeWaveBounds.y = this.mHugeWaveText.y();
                this.mHugeWaveBounds.width = (int)(this.mHugeWaveText.width() * this.mHugeWaveScale);
                this.mHugeWaveBounds.height = (int)(this.mHugeWaveText.height() * this.mHugeWaveScale);
                this.mHugeWaveText.setColor(this.mHugeWaveAlpha, HUGE_WAVE_RED, HUGE_WAVE_GREEN, HUGE_WAVE_BLUE);
                Utils.align(this.mHugeWaveBounds, this.mScreenBounds, Utils.ALIGN_CENTER, Utils.ALIGN_CENTER);
                g.pushState();
                g.scale(this.mHugeWaveScale, this.mHugeWaveScale);
                g.drawImage(this.mHugeWaveText, this.mHugeWaveBounds.x, this.mHugeWaveBounds.y);
                g.popState();
            };
            if (this.mFinalWaveTimer > 0)
            {
                this.mFinalWaveBounds.x = this.mFinalWaveText.x();
                this.mFinalWaveBounds.y = this.mFinalWaveText.y();
                this.mFinalWaveBounds.width = (int)(this.mFinalWaveText.width() * this.mFinalWaveScale);
                this.mFinalWaveBounds.height = (int)(this.mFinalWaveText.height() * this.mFinalWaveScale);
                Utils.align(this.mFinalWaveBounds, this.mScreenBounds, Utils.ALIGN_CENTER, Utils.ALIGN_CENTER);
                this.mFinalWaveText.setColor(this.mFinalWaveAlpha, FINAL_WAVE_RED, FINAL_WAVE_GREEN, FINAL_WAVE_BLUE);
                g.pushState();
                g.scale(this.mFinalWaveScale, this.mFinalWaveScale);
                g.drawImage(this.mFinalWaveText, this.mFinalWaveBounds.x, this.mFinalWaveBounds.y);
                g.popState();
            };
        }

        private int mHugeWaveDuration =0;
        private double mFinalWaveScale =1;

        public void  update (){
            int range =0;
            int lower =0;
            int diff =0;
            if (this.mHugeWaveTimer == (this.mHugeWaveDuration - HUGE_FADE_IN_TIME))
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.HUGEWAVE);
            };
            if (this.mHugeWaveTimer > (this.mHugeWaveDuration - HUGE_FADE_IN_TIME))
            {
                lower = (this.mHugeWaveDuration - HUGE_FADE_IN_TIME);
                range = (this.mHugeWaveDuration - lower);
                diff = (this.mHugeWaveTimer - lower);
                this.mHugeWaveAlpha = (1 - (diff / range));
                this.mHugeWaveScale = ((2.9 * (diff / range)) + 1);
            }
            else
            {
                if (this.mHugeWaveTimer > (this.mHugeWaveDuration - HUGE_FADE_OUT_TIME))
                {
                    this.mHugeWaveAlpha = 1;
                    this.mHugeWaveScale = 1;
                }
                else
                {
                    lower = 0;
                    range = HUGE_FADE_OUT_TIME;
                    diff = this.mHugeWaveTimer;
                    this.mHugeWaveAlpha = (diff / range);
                    this.mHugeWaveScale = 1;
                };
            };
            if (this.mHugeWaveTimer > 0)
            {
                this.mHugeWaveTimer = (this.mHugeWaveTimer - 1);
            };
            if (this.mFinalWaveTimer == (this.mFinalWaveDuration - FINAL_FADE_IN_TIME))
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.FINALWAVE);
            };
            if (this.mFinalWaveTimer > (this.mFinalWaveDuration - FINAL_FADE_IN_TIME))
            {
                lower = (this.mFinalWaveDuration - FINAL_FADE_IN_TIME);
                range = (this.mFinalWaveDuration - lower);
                diff = (this.mFinalWaveTimer - lower);
                this.mFinalWaveAlpha = (1 - (diff / range));
                this.mFinalWaveScale = ((2.9 * (diff / range)) + 1);
            }
            else
            {
                if (this.mFinalWaveTimer > (this.mFinalWaveDuration - FINAL_FADE_OUT_TIME))
                {
                    this.mFinalWaveAlpha = 1;
                    this.mFinalWaveScale = 1;
                }
                else
                {
                    lower = 0;
                    range = FINAL_FADE_OUT_TIME;
                    diff = this.mFinalWaveTimer;
                    this.mFinalWaveAlpha = (diff / range);
                    this.mFinalWaveScale = 1;
                };
            };
            if (this.mFinalWaveTimer > 0)
            {
                this.mFinalWaveTimer = (this.mFinalWaveTimer - 1);
            };
        }

        private int mHugeWaveAlpha =1;
        private Rectangle mScreenBounds ;

        public void  showFinalWave (int duration ){
            this.mFinalWaveDuration = duration;
            this.mFinalWaveTimer = duration;
        }

        private int mFinalWaveDuration =0;
        private int mFinalWaveAlpha =1;

        public void  showHugeWave (int duration ){
            this.mHugeWaveDuration = duration;
            this.mHugeWaveTimer = duration;
        }

        private ImageInst mHugeWaveText ;
        private Rectangle mHugeWaveBounds ;
        private ImageInst mFinalWaveText ;
        private int mHugeWaveTimer =0;
        private double mHugeWaveScale =1;
        private PVZApp mApp ;
        private Rectangle mFinalWaveBounds ;

        public  WaveWarning (PVZApp app ){
            super();
            this.mHugeWaveBounds = new Rectangle();
            this.mFinalWaveBounds = new Rectangle();
            this.mScreenBounds = new Rectangle(0, 0, 540, 405);
            this.mApp = app;
            String hugeWaveString =this.mApp.stringManager().translateString("[ADVICE_HUGE_WAVE]");
            String finalWaveString =this.mApp.stringManager().translateString("[ADVICE_FINAL_WAVE]");
            FontInst waveFont =this.mApp.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR28 );
            this.mHugeWaveText = Utils.createStringImage(hugeWaveString, waveFont, this.mScreenBounds, Utils.JUSTIFY_CENTER);
            this.mHugeWaveText.useColor = true;
            this.mFinalWaveText = Utils.createStringImage(finalWaveString, waveFont, this.mScreenBounds, Utils.JUSTIFY_CENTER);
            this.mFinalWaveText.useColor = true;
        }
    }


