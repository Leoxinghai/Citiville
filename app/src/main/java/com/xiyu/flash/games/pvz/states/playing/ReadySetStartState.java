package com.xiyu.flash.games.pvz.states.playing;
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

import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.utils.Utils;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.PVZFoleyType;

    public class ReadySetStartState implements IState {

        private static  int SET_START =60;
        private static  int READY_START =0;
        private static  int PLANT_DURATION =80;
        private static  double SET_SCALE =0.1;
        private static  double READY_SCALE =0.1;
        private static  int READY_DURATION =60;
        private static  int SET_DURATION =60;
        private static  int PLANT_START =120;
        private static  double PLANT_SCALE =0.3;

        public void  onPop (){
        }

        private ImageInst mSetText ;
        private ImageInst mReadyText ;
        private ImageInst mPlantText ;
        private Rectangle mScreenBounds ;
        private Rectangle mTextBounds ;

        public void  update (){
            this.mApp.widgetManager().updateFrame();
            this.mApp.mCutsceneTime = (this.mApp.mCutsceneTime + 10);
            this.mTimer = (this.mTimer + 1);
            int time =0;
            if (this.mTimer > (PLANT_START + PLANT_DURATION))
            {
                this.mApp.stateManager().changeState(PVZApp.STATE_START_LEVEL);
            }
            else
            {
                if (this.mTimer >= (SET_START + SET_DURATION))
                {
                    this.mCurrentText = this.mPlantText;
                    this.mTextScale = (1 + PLANT_SCALE);
                }
                else
                {
                    if (this.mTimer >= (READY_START + READY_DURATION))
                    {
                        time = ((this.mTimer - SET_START) / SET_DURATION);
                        this.mCurrentText = this.mSetText;
                        this.mTextScale = (1 + (SET_SCALE * time));
                    }
                    else
                    {
                        time = ((this.mTimer - READY_START) / READY_DURATION);
                        this.mCurrentText = this.mReadyText;
                        this.mTextScale = (1 + (READY_SCALE * time));
                    };
                };
            };
        }
        public void  draw (Graphics2D g ){
            this.mApp.widgetManager().drawScreen(g);
            if (!this.mInited)
            {
                return;
            };
            this.mTextBounds.x = this.mCurrentText.x();
            this.mTextBounds.y = this.mCurrentText.y();
            this.mTextBounds.width = (int)(this.mCurrentText.width() * this.mTextScale);
            this.mTextBounds.height = (int)(this.mCurrentText.height() * this.mTextScale);
            Utils.align(this.mTextBounds, this.mScreenBounds, Utils.ALIGN_CENTER, Utils.ALIGN_CENTER);
            g.pushState();
            g.scale(this.mTextScale, this.mTextScale);
            g.drawImage(this.mCurrentText, this.mTextBounds.x, this.mTextBounds.y);
            g.popState();
        }
        private void  init (){
            if (this.mInited == true)
            {
                return;
            };
            this.mFont = this.mApp.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR28);
            this.mScreenBounds = new Rectangle(0, 0, 540, 405);
            this.mTextBounds = new Rectangle();
            this.mReadyText = Utils.createStringImage(this.mApp.stringManager().translateString("[LEVEL_INTRO_READY]"), this.mFont, this.mScreenBounds, Utils.JUSTIFY_CENTER);
            this.mReadyText.setColor(1, (246 / 250), (9 / 250), (9 / 250));
            this.mReadyText.useColor = true;
            this.mSetText = Utils.createStringImage(this.mApp.stringManager().translateString("[LEVEL_INTRO_SET]"), this.mFont, this.mScreenBounds, Utils.JUSTIFY_CENTER);
            this.mSetText.setColor(1, (246 / 250), (9 / 250), (9 / 250));
            this.mSetText.useColor = true;
            this.mPlantText = Utils.createStringImage(this.mApp.stringManager().translateString("[LEVEL_INTRO_PLANT]"), this.mFont, this.mScreenBounds, Utils.JUSTIFY_CENTER);
            this.mPlantText.setColor(1, (246 / 250), (9 / 250), (9 / 250));
            this.mPlantText.useColor = true;
            this.mInited = true;
        }

        private int mTimer =0;
        private ImageInst mCurrentText ;

        public void  onExit (){
        }

        private FontInst mFont ;

        public void  onPush (){
        }

        private PVZApp mApp ;
        private double mTextScale =0;

        public void  onEnter (){
            this.init();
            this.mTextScale = 1;
            this.mCurrentText = this.mReadyText;
            this.mTimer = 0;
            this.mApp.foleyManager().playFoley(PVZFoleyType.READYSETPLANT);
        }

        private boolean mInited =false ;

        public void setView() {
			mApp.getMainView().setState(this);
        }

        public  ReadySetStartState (PVZApp app ){
            this.mApp = app;
        }
    }


