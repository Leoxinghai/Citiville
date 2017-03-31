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
import com.xiyu.flash.games.pvz.logic.GridItem;
import com.xiyu.flash.games.pvz.logic.LawnMower;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class StartLevelState implements IState {

        public void  onEnter (){
            GridItem aGridItem ;
            LawnMower aLawnMower ;
            if (this.app.mSeedChooserScreen != null)
            {
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen);
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen.mStartButton);
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen.mLawnViewButton);
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen.mDialogBox);
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen.mDialogBox.mOkButton);
                this.app.widgetManager().removeWidget(this.app.mSeedChooserScreen.mDialogBox.mCancelButton);
                this.app.mSeedChooserScreen = null;
            };
            if (this.app.IsScaryPotterLevel())
            {
                this.app.musicManager().playMusic(PVZMusic.CEREBRAWL, true, 50);
            }
            else
            {
                if (this.app.mBoard.IsWallnutBowlingLevel())
                {
                    this.app.musicManager().playMusic(PVZMusic.LOON_BOON, true, 50);
                }
                else
                {
                    if ((((this.app.mBoard.mLevel > 10)) || (this.app.IsSurvivalMode())))
                    {
                        this.app.musicManager().playMusic(PVZMusic.MOONGRAINS, true, 50);
                    }
                    else
                    {
                        if (this.app.mBoard.mLevel == 10)
                        {
                            this.app.musicManager().playMusic(PVZMusic.CONVEYOR, true, 50);
                        }
                        else
                        {
                            this.app.musicManager().playMusic(PVZMusic.GRASS_WALK, true, 50);
                        };
                    };
                };
            };
            if (this.app.mBoard == null)
            {
                this.app.mBoard = new Board(this.app);
            };
            this.app.mBoard.RemoveCutsceneZombies();
            this.app.mBoard.mGameScene = PVZApp.SCENE_PLAYING;
            this.app.widgetManager().setFocus(this.app.mBoard);
            if (!this.app.IsScaryPotterLevel())
            {
                this.app.mBoard.mSeedBank.mVisible = true;
            };
            this.app.mBoard.mToolTip.mVisible = true;
            this.app.mBoard.mMenuButton.setVisible(true);
            this.app.mBoard.mEnableGraveStones = true;
			for(int i =0; i<this.app.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.app.mBoard.mGridItems.elementAt(i);
                if ((((aGridItem.mGridItemType == GridItem.GRIDITEM_GRAVESTONE)) && ((this.app.mBoard.mChallenge.mSurvivalStage == 0))))
                {
                    aGridItem.AddGraveStoneParticles();
                };
            };
            if ((((((this.app.mLevel > 4)) || (this.app.IsSurvivalMode()))) || (this.app.IsScaryPotterLevel())))
            {
                this.app.mBoard.mShowShovel = true;
            };
			for(int i =0; i<this.app.mBoard.mLawnMowers.length();i++)
			{
				aLawnMower = (LawnMower)this.app.mBoard.mLawnMowers.elementAt(i);
                aLawnMower.mVisible = true;
            };
            this.app.mBoard.StartLevel();
            this.app.stateManager().changeState(PVZApp.STATE_PLAY_LEVEL);
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
            this.app.widgetManager().updateFrame();
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  StartLevelState (PVZApp app ){
            this.app = app;
        }
    }


