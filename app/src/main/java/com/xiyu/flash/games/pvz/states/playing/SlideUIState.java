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
import com.xiyu.flash.games.pvz.logic.LawnMower;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.logic.Board;

    public class SlideUIState implements IState {

        public static final int TimeSeedBankOnStart =0;
        public static final int TimeSeedBankOnEnd =500;
        public static final int TimeLawnMowerDuration =250;

        public static int[] parami = {(TimeSeedBankOnEnd +200),(TimeSeedBankOnEnd +150),(TimeSeedBankOnEnd +100),(TimeSeedBankOnEnd +50),TimeSeedBankOnEnd };
        public static  Array TimeLawnMowerStart =new Array (parami);

        public void  onEnter (){
            this.app.mCutsceneTime = 0;
            if (!this.app.mBoard.ChooseSeedsOnCurrentLevel())
            {
                this.app.mBoard.mSeedBank.Move(0, -78);
                this.app.mBoard.mSeedBank.mVisible = true;
            }
            else
            {
                this.app.mCutsceneTime = TimeSeedBankOnStart;
            };
            if (((this.app.IsScaryPotterLevel()) || ((this.app.mBoard.mChallenge.mSurvivalStage > 0))))
            {
                this.app.stateManager().changeState(PVZApp.STATE_START_LEVEL);
            };
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
        }
        public void  update (){
            int y =0;
            int aTimeMowerStart =0;
            int aTimeMowerEnd =0;
            LawnMower aLawnMower ;
            int x =0;
            this.app.widgetManager().updateFrame();
            this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            if ((((this.app.mCutsceneTime > TimeSeedBankOnStart)) && ((this.app.mCutsceneTime <= TimeSeedBankOnEnd))))
            {
                if (!this.app.mBoard.ChooseSeedsOnCurrentLevel())
                {
                    y = TodCommon.TodAnimateCurve(TimeSeedBankOnStart, TimeSeedBankOnEnd, this.app.mCutsceneTime, -78, 0, TodCommon.CURVE_EASE_IN_OUT);
                    this.app.mBoard.mSeedBank.Move(0, y);
                };
            };
            int i =0;
            while (i < Board.GRIDSIZEY)
            {
            	aTimeMowerStart=((Integer)TimeLawnMowerStart.elementAt(i)).intValue();
                aTimeMowerEnd = (aTimeMowerStart + TimeLawnMowerDuration);
                if (this.app.mCutsceneTime > aTimeMowerStart)
                {
                    aLawnMower = this.app.mBoard.FindLawnMowerInRow(i);
                    if (aLawnMower == null)
                    {
                    }
                    else
                    {
                        aLawnMower.mVisible = true;
                        x = TodCommon.TodAnimateCurve(aTimeMowerStart, aTimeMowerEnd, this.app.mCutsceneTime, -54, -25, TodCommon.CURVE_EASE_IN_OUT);
                        aLawnMower.mPosX = x;
                    };
                };
                i++;
            };
            int endTime=((TimeSeedBankOnEnd+((Integer)TimeLawnMowerStart.elementAt(1)).intValue())+TimeLawnMowerDuration);
            if (this.app.mCutsceneTime >= endTime)
            {
                if (((((this.app.IsSurvivalMode()) || ((this.app.mLevel == 1)))) || ((this.app.mLevel == 5))))
                {
                    this.app.stateManager().changeState(PVZApp.STATE_START_LEVEL);
                }
                else
                {
                    this.app.stateManager().changeState(PVZApp.STATE_READY_SET_START);
                };
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  SlideUIState (PVZApp app ){
            this.app = app;
        }
    }


