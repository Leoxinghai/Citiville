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
import com.xiyu.flash.games.pvz.logic.UI.OptionsDialog;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class OptionsMenuState implements IState {

        public void  onEnter (){
            this.app.adAPI.pauseBroadcast();
            this.app.mBoard.mMenuButton.disabled = true;
            if (this.app.mOptionsMenu == null)
            {
                this.app.mOptionsMenu = new OptionsDialog(this.app, this.app.mBoard);
            };
            this.app.widgetManager().addWidget(this.app.mOptionsMenu);
            this.app.widgetManager().setFocus(this.app.mOptionsMenu);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mRestartButton);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mUpsellButton);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mBackToGameButton);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mBackToMainButton);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mSoundCheckBox);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mMusicCheckBox);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mDialogBox);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mDialogBox.mOkButton);
            this.app.widgetManager().addWidget(this.app.mOptionsMenu.mDialogBox.mCancelButton);
            this.app.soundManager().pauseAll();
            this.app.musicManager().pauseMusic();
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
            if (this.app.mOptionsMenu.mIsSound)
            {
                this.app.soundManager().unmute();
            }
            else
            {
                this.app.soundManager().mute();
            };
            if (this.app.mOptionsMenu.mIsMusic)
            {
                this.app.musicManager().unmute();
            }
            else
            {
                this.app.musicManager().mute();
            };
            this.app.setSaveData(this.app.mSaveObject);
            this.app.soundManager().resumeAll();
            this.app.musicManager().resumeMusic();
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mRestartButton);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mUpsellButton);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mBackToGameButton);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mBackToMainButton);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mSoundCheckBox);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mMusicCheckBox);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mDialogBox);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mDialogBox.mOkButton);
            this.app.widgetManager().removeWidget(this.app.mOptionsMenu.mDialogBox.mCancelButton);
            this.app.mBoard.mMenuButton.disabled = false;
            this.app.widgetManager().setFocus(this.app.mBoard);
            this.app.mOptionsMenu = null;
            this.app.adAPI.resumeBroadcast();
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
        }
        public void  onPop (){
            this.app.soundManager().pauseAll();
            this.app.musicManager().pauseMusic();
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  OptionsMenuState (PVZApp app ){
            this.app = app;
        }
    }


