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
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.Board;

    public class SurvivalRepickState implements IState {

        public static int TimePanRightStart =1000;
        public static int TimePanRightEnd =(TimePanRightStart +1500);

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  GameContinue (){
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().resumeMusic();
                this.app.soundManager().resumeAll();
            };
            this.mPaused = false;
        }
        public void  onPush (){
        }
        public void  update (){
            int posStart ;
            int posEnd ;
            int x =0;
            this.app.widgetManager().updateFrame();
            if (this.mPaused)
            {
                return;
            };
            this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            int aTimePanRightStart =TimePanRightStart ;
            int aTimePanRightEnd =TimePanRightEnd ;
            int aBoardLeftPosition =0;
            if (this.app.mCutsceneTime <= aTimePanRightStart)
            {
                this.app.mBoard.move(aBoardLeftPosition, 0);
            };
            if ((((this.app.mCutsceneTime > aTimePanRightStart)) && ((this.app.mCutsceneTime <= aTimePanRightEnd))))
            {
                posStart = -(aBoardLeftPosition);
                posEnd = ((-(PVZApp.BOARD_OFFSET) + PVZApp.BACKGROUND_IMAGE_WIDTH) - this.app.appWidth());
                x = TodCommon.TodAnimateCurve(aTimePanRightStart, aTimePanRightEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                this.app.mBoard.move(-(x), 0);
            };
            if (this.app.mBoard.x <= -257)
            {
                if (this.app.mBoard.ChooseSeedsOnCurrentLevel())
                {
                    this.app.stateManager().changeState(PVZApp.STATE_SEEDCHOOSER);
                }
                else
                {
                    this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                };
            };
        }
        public void  onPop (){
        }
        public void  onEnter (){
            String aString ;
            this.mPaused = true;
            this.app.musicManager().playMusic(PVZMusic.CHOOSE_YOUR_SEEDS, true, 50);
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().pauseMusic();
                this.app.soundManager().pauseAll();
            };
//            this.app.adAPI.GameBreak(this.app.mBoard.mChallenge.mSurvivalStage, this.GameContinue());
            this.app.mBoard.mGameScene = PVZApp.SCENE_LEVEL_INTRO;
            this.app.mBoard.mShowShovel = false;
            if (this.app.mSeedChooserScreen == null)
            {
                this.app.mBoard.mSeedBank.mVisible = false;
                this.app.mBoard.mShowShovel = false;
                this.app.mPlacedZombies = false;
                aString = "[SURVIVAL_POOL_ENDLESS]";
                this.app.mBoard.mAdvice.SetLabel(aString, Board.MESSAGE_STYLE_HOUSE_NAME);
            };
        }

        private boolean mPaused ;

        public void  onExit (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  SurvivalRepickState (PVZApp app ){
            this.app = app;
        }
    }


