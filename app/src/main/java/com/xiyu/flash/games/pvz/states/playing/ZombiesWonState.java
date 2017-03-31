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
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.PVZMusic;
//import flash.geom.Rectangle;
import com.xiyu.flash.framework.utils.Utils;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.framework.resources.images.ImageInst;

    public class ZombiesWonState implements IState {

        private static int TIME_PAN_RIGHT_START =1500;
        private static int TIME_PAN_RIGHT_END =(TIME_PAN_RIGHT_START +2000);
        private static int TIME_GRAPHIC_START =(TIME_PAN_RIGHT_START +2500);
        private static int TIME_GRAPHIC_SHAKE =(TIME_GRAPHIC_START +1000);
        private static int TIME_GRAPHIC_SHAKE_END =(TIME_GRAPHIC_SHAKE +1000);
        private static int TIME_GRAPHIC_END =(TIME_GRAPHIC_SHAKE_END +3000);
        private static int TIME_END =TIME_GRAPHIC_END;

        public void  onEnter (){
            if (this.mZombiesWon == null)
            {
                this.mZombiesWon = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_ZOMBIESWON);
            };
            this.mApp.musicManager().playMusic(PVZMusic.LOSEMUSIC, false,10);
            this.mApp.mBoard.mMenuButton.setVisible(false);
            if (this.mApp.IsScaryPotterLevel())
            {
                this.mApp.mCutsceneTime = (TIME_END - 10);
            }
            else
            {
                this.mApp.mCutsceneTime = 0;
            };
        }

        private Rectangle mImageBounds ;

        public void  draw (Graphics2D g ){
            this.mApp.widgetManager().drawScreen(g);
            if (this.mApp.IsScaryPotterLevel())
            {
                return;
            };
            if ((((this.mApp.mCutsceneTime >= TIME_GRAPHIC_START)) && ((this.mApp.mCutsceneTime < TIME_GRAPHIC_END))))
            {
                this.mImageBounds.x = 0;
                this.mImageBounds.y = 0;
                this.mImageBounds.width = (this.mZombiesWon.width() * this.mImageScale);
                this.mImageBounds.height = (this.mZombiesWon.height() * this.mImageScale);
                Utils.align(this.mImageBounds, this.mScreenBounds, Utils.ALIGN_CENTER, Utils.ALIGN_CENTER);
                g.pushState();
                g.scale(this.mImageScale, this.mImageScale);
                g.drawImage(this.mZombiesWon, (this.mImageBounds.x + this.mImageOffsetX), (this.mImageBounds.y + this.mImageOffsetY));
                g.popState();
            };
        }

        private Rectangle mScreenBounds ;

        public void  update (){
            int x =0;
            this.mApp.widgetManager().updateFrame();
            this.mApp.mCutsceneTime = (this.mApp.mCutsceneTime + 10);
            int time =this.mApp.mCutsceneTime ;
            if (time == TIME_END)
            {
                this.mApp.stateManager().changeState(PVZApp.STATE_DIALOG_BOX);
            };
            if ((((time > TIME_PAN_RIGHT_START)) && ((time <= TIME_PAN_RIGHT_END))))
            {
                x = TodCommon.TodAnimateCurve(TIME_PAN_RIGHT_START, TIME_PAN_RIGHT_END, time, 0, PVZApp.BOARD_OFFSET, TodCommon.CURVE_EASE_IN_OUT);
                this.mApp.mBoard.move(x, 0);
            };
            if ((((time == (TIME_GRAPHIC_START - 400))) || ((time == (TIME_GRAPHIC_START - 900)))))
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.CHOMP);
            };
            if (time > TIME_PAN_RIGHT_START)
            {
                this.mApp.mBoard.mShowZombieWalking = true;
            };
            if (time == TIME_GRAPHIC_START)
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.SCREAM);
            };
            if ((((time >= TIME_GRAPHIC_START)) && ((time < TIME_GRAPHIC_SHAKE))))
            {
                this.mImageScale = ((time - TIME_GRAPHIC_START) / (TIME_GRAPHIC_SHAKE - TIME_GRAPHIC_START));
            };
            this.mImageOffsetX = 0;
            this.mImageOffsetY = 0;
            if ((((time >= TIME_GRAPHIC_SHAKE)) && ((time < TIME_GRAPHIC_SHAKE_END))))
            {
                this.mImageOffsetX = (int)((Math.random() * 4) - 2);
                this.mImageOffsetY = (int)((Math.random() * 4) - 2);
            };
        }

        private ImageInst mZombiesWon ;
        private int mImageOffsetX =0;
        private int mImageOffsetY =0;

        public void  onPush (){
        }
        public void  onExit (){
        }
        public void  onPop (){
        }

        public void setView() {
			mApp.getMainView().setState(this);
        }

        private PVZApp mApp ;
        private int mImageScale =1;

        public  ZombiesWonState (PVZApp app ){
            this.mImageBounds = new Rectangle();
            this.mScreenBounds = new Rectangle(0, 0, 540, 405);
            this.mApp = app;
        }
    }


