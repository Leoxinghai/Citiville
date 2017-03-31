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
import com.xiyu.flash.games.pvz.logic.UI.DialogBox;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class DialogState implements IState {

        public void  onEnter (){
            String aMessage ;
            int aFlags ;
            String aFlagStr ;
            String aCompletedStr ;
            this.app.mDialogBox = new DialogBox(this.app, this.app.mBoard);
            this.app.widgetManager().addWidget(this.app.mDialogBox);
            this.app.widgetManager().addWidget(this.app.mDialogBox.mOkButton);
            this.app.widgetManager().setFocus(this.app.mDialogBox);
            if (this.app.mBoard.mGameScene == Board.SCENE_ZOMBIES_WON)
            {
                if (this.app.IsAdventureMode())
                {
                    this.app.mDialogBox.resize((0xFF - (63 * 1)), (130 - (36 * 1)), (153 + (63 * 1)), (184 + (36 * 1)));
                    this.app.mDialogBox.mDialogType = DialogBox.DIALOG_GAMEOVER;
                    this.app.mDialogBox.InitializeDialogBox(this.app.stringManager().translateString("[GAME_OVER]"), "", this.app.stringManager().translateString("[TRY_AGAIN]"), "", 1, 1);
                }
                else
                {
                    if (this.app.IsScaryPotterLevel())
                    {
                        this.app.mDialogBox.resize((0xFF - (63 * 3)), (130 - (36 * 1)), (153 + (63 * 4)), (184 + (36 * 1)));
                        this.app.mDialogBox.mDialogType = DialogBox.DIALOG_RETRY;
                        aMessage = this.app.stringManager().translateString("[ENDLESS_PUZZLE_DEATH_MESSAGE]");
                        aMessage = aMessage.replaceAll("{STREAK}", ""+this.app.mBoard.mChallenge.mSurvivalStage);
                        this.app.mDialogBox.InitializeDialogBox(this.app.stringManager().translateString("[SCARY_POTTER_ENDLESS]"), aMessage, this.app.stringManager().translateString("[TRY_AGAIN]"), "", 4, 1);
                    }
                    else
                    {
                        if (this.app.IsSurvivalMode())
                        {
                            this.app.mDialogBox.resize((0xFF - (63 * 3)), (130 - (36 * 2)), (153 + (63 * 4)), (184 + (36 * 2)));
                            this.app.mDialogBox.mDialogType = DialogBox.DIALOG_RETRY;
                            aFlags = this.app.mBoard.GetSurvivalFlagsCompleted();
                            aFlagStr = this.app.mBoard.Pluralize(aFlags, "[ONE_FLAG]", "[COUNT_FLAGS]");
                            aCompletedStr = this.app.stringManager().translateString("[SURVIVAL_DEATH_MESSAGE]").replace("{FLAGS}", aFlagStr);
                            this.app.mDialogBox.InitializeDialogBox(this.app.stringManager().translateString("[SURVIVAL_POOL_ENDLESS]"), aCompletedStr, this.app.stringManager().translateString("[TRY_AGAIN]"), "", 4, 2);
                        };
                    };
                };
            };
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
            this.app.widgetManager().removeAllWidgets(true);
            this.app.mBoard = null;
            this.app.mDialogBox = null;
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
        }
        public void  onPop (){
        }
        public void  GameContinue (Object obj ){
            this.app.soundManager().resumeAll();
            this.app.musicManager().resumeMusic();
            this.app.mAwardScreen.mStartButton.setDisabled(false);
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  DialogState (PVZApp app ){
            this.app = app;
        }
    }


