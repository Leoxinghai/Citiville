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
import com.xiyu.flash.games.pvz.logic.SeedChooserScreen;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.TodCommon;

    public class ChooseSeedsState implements IState {

        public static final int SEED_CHOOSER_OFFSETSCREEN_OFFSET =348*2;
        public static final int TimeSeedChoserSlideOnStart =500*2;
        public static final int SEED_CHOOSER_STARTBUTTON_OFFSET =713;
        public static final int TimeSeedChoserSlideOnEnd =(TimeSeedChoserSlideOnStart +250);//750
        public static final int TimeSeedChoserSlideOffStart =(TimeSeedChoserSlideOnEnd +250);//1000
        public static final int TimeSeedChoserSlideOffEnd =(TimeSeedChoserSlideOffStart +250);//1250

        public void  onEnter (){
            if (this.app.mSeedChooserScreen == null)
            {
                this.app.mSeedChooserScreen = new SeedChooserScreen(this.app, this.app.mBoard);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen.mStartButton);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen.mLawnViewButton);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen.mDialogBox);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen.mDialogBox.mOkButton);
                this.app.widgetManager().addWidget(this.app.mSeedChooserScreen.mDialogBox.mCancelButton);
            };
            this.app.mBoard.mMenuButton.setVisible(false);
            this.app.mBoard.mSeedBank.mVisible = true;
            this.app.mBoard.mToolTip.mVisible = false;
            this.app.mSeedChooserScreen.mToolTip.mVisible = false;
            this.app.mCutsceneTime = 0;
            this.app.widgetManager().setFocus(this.app.mSeedChooserScreen);
            this.app.mSeedChooserScreen.visible = true;
            if (((this.app.IsAdventureMode()) || ((this.app.mBoard.mChallenge.mSurvivalStage == 0))))
            {
                this.app.mSeedChooserScreen.mLawnViewButton.visible = false;
            }
            else
            {
                this.app.mSeedChooserScreen.mLawnViewButton.visible = true;
            };
            this.app.mSeedChooserScreen.mStartButton.visible = true;
            this.app.mSeedChooserScreen.mStartButton.move(115, SEED_CHOOSER_STARTBUTTON_OFFSET);
            this.app.mSeedChooserScreen.mLawnViewButton.move(20, (SEED_CHOOSER_STARTBUTTON_OFFSET + 5));
            this.app.mSeedChooserScreen.move(800, SEED_CHOOSER_OFFSETSCREEN_OFFSET);
            this.app.mSeedChooserScreen.mChooseState = SeedChooserScreen.CHOOSE_ENTER;
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }

        public void  onPush (){
        }
        public void  onExit (){
            this.app.mBoard.mToolTip.mVisible = false;
        }
        public void  update (){
            int y =0;
            int posStart ;
            int posEnd ;
            int aButtonY ;
            this.app.widgetManager().updateFrame();
            if ((((((this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_ENTER)) || ((this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_LEAVE)))) || ((this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_VIEW_LAWN))))
            {
                this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            };
            int aTimeSeedChoserSlideOnStart =TimeSeedChoserSlideOnStart ;
            int aTimeSeedChoserSlideOnEnd =TimeSeedChoserSlideOnEnd ;
            if (this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_ENTER)
            {
                if (this.app.mCutsceneTime > aTimeSeedChoserSlideOnEnd)
                {
                    this.app.mSeedChooserScreen.mToolTip.mVisible = true;
                    this.app.mSeedChooserScreen.mChooseState = SeedChooserScreen.CHOOSE_NORMAL;
                };
                if ((((this.app.mCutsceneTime > aTimeSeedChoserSlideOnStart)) && ((this.app.mCutsceneTime <= aTimeSeedChoserSlideOnEnd))))
                {
                    posStart = SEED_CHOOSER_OFFSETSCREEN_OFFSET;
                    posEnd = 0;
                    y = TodCommon.TodAnimateCurve(aTimeSeedChoserSlideOnStart, aTimeSeedChoserSlideOnEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                    this.app.mSeedChooserScreen.move(800, y);
                    posStart = SEED_CHOOSER_STARTBUTTON_OFFSET;
                    posEnd = 370;
                    aButtonY = TodCommon.TodAnimateCurve(aTimeSeedChoserSlideOnStart, aTimeSeedChoserSlideOnEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                    this.app.mSeedChooserScreen.mStartButton.move(115, aButtonY);
                    this.app.mSeedChooserScreen.mLawnViewButton.move(20, (aButtonY + 5));
                    if (y <= 0)
                    {
                    };
                };
            };
            int aTimeSeedChoserSlideOffStart =TimeSeedChoserSlideOffStart ;
            int aTimeSeedChoserSlideOffEnd =TimeSeedChoserSlideOffEnd ;
            if ((((this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_LEAVE)) || ((this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_VIEW_LAWN))))
            {
                if (this.app.mCutsceneTime > aTimeSeedChoserSlideOffEnd)
                {
                    if (this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_VIEW_LAWN)
                    {
                        this.app.stateManager().changeState(PVZApp.STATE_LAWN_VIEW);
                    }
                    else
                    {
                        this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                    };
                };
                if ((((this.app.mCutsceneTime > aTimeSeedChoserSlideOffStart)) && ((this.app.mCutsceneTime <= aTimeSeedChoserSlideOffEnd))))
                {
                    posStart = 0;
                    posEnd = SEED_CHOOSER_OFFSETSCREEN_OFFSET;
                    y = TodCommon.TodAnimateCurve(aTimeSeedChoserSlideOffStart, aTimeSeedChoserSlideOffEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                    this.app.mSeedChooserScreen.move(800, y);
                    this.app.mSeedChooserScreen.mStartButton.setVisible(false);
                    this.app.mSeedChooserScreen.mLawnViewButton.setVisible(false);
                    if (y >= SEED_CHOOSER_OFFSETSCREEN_OFFSET)
                    {
                        this.app.mSeedChooserScreen.visible = false;
                        if (this.app.mSeedChooserScreen.mChooseState == SeedChooserScreen.CHOOSE_VIEW_LAWN)
                        {
                            this.app.stateManager().changeState(PVZApp.STATE_LAWN_VIEW);
                        }
                        else
                        {
                            this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                        };
                    };
                };
            };

        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  ChooseSeedsState (PVZApp app ){
            this.app = app;
        }
    }


