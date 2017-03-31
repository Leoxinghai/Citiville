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

    public class PlayLevelState implements IState {

        public void  onEnter (){
            this.app.mBoard.mMenuButton.setVisible(true);
            this.app.mBoard.mSeedBank.mVisible = true;            
            this.app.mBoard.mMenuButton.disabled = false;
            if ((((this.app.mLevel > 4)) || (this.app.IsSurvivalMode())))
            {
                this.app.mBoard.mShowShovel = true;
            };
            this.app.mBoard.mGameScene = PVZApp.SCENE_PLAYING;
            this.app.widgetManager().setFocus(this.app.mBoard);
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
        }
        public void  CheckForGameEnd (){
            if (this.app.mBoard ==null || !this.app.mBoard.mLevelComplete)
            {
                return;
            };
            if (this.app.IsAdventureMode() && this.app.mBoard.mLevel == 14)
            {
                this.app.stateManager().changeState(PVZApp.STATE_CRAZY_DAVE);
            }
            else
            {
            	System.out.println("checkforGame.end.1 ");
            	if (this.app.IsAdventureMode())
                {
                    this.KillBoard();
                    this.app.stateManager().changeState(PVZApp.STATE_SHOWAWARD);
                }
                else
                {
                    if (this.app.IsSurvivalMode())
                    {
                        this.app.mBoard.mChallenge.mSurvivalStage++;
                        this.app.mBoard.InitSurvivalStage();
                        this.app.mBoard.PlaceStreetZombies();
                        this.app.mCutsceneTime = 0;
//                        this.app.stateManager().changeState(PVZApp.STATE_SURVIVAL_REPICK);
                    };
                };
            	System.out.println("checkforGame.end.2 ");
            };

        }
        public void  update (){
            this.app.widgetManager().updateFrame();
            this.CheckForGameEnd();
        }
        public void  onPop (){
        }
        public void  KillBoard (){
            if (this.app.mBoard != null)
            {
                this.app.widgetManager().removeWidget(this.app.mBoard);
                this.app.mBoard = null;
            };
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  PlayLevelState (PVZApp app ){
            this.app = app;
        }
    }


