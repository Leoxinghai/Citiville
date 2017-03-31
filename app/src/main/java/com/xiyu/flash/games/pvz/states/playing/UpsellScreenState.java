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
import com.xiyu.flash.games.pvz.logic.UI.UpsellScreen;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class UpsellScreenState implements IState {

        public void  onEnter (){
            if (this.app.IsAdventureMode())
            {
                this.app.mUpsellScreen = new UpsellScreen(this.app, UpsellScreen.ADVENTURE);
            }
            else
            {
                if (this.app.IsSurvivalMode())
                {
                    this.app.mUpsellScreen = new UpsellScreen(this.app, UpsellScreen.SURVIVAL);
                }
                else
                {
                    if (this.app.IsScaryPotterLevel())
                    {
                        this.app.mUpsellScreen = new UpsellScreen(this.app, UpsellScreen.PUZZLE);
                    }
                    else
                    {
                        this.app.mUpsellScreen = new UpsellScreen(this.app, UpsellScreen.MINIGAME);
                    };
                };
            };
            this.app.widgetManager().addWidget(this.app.mUpsellScreen);
            this.app.widgetManager().setFocus(this.app.mUpsellScreen);
            this.app.widgetManager().addWidget(this.app.mUpsellScreen.mBackToGameButton);
            this.app.widgetManager().addWidget(this.app.mUpsellScreen.mUpsellButton);
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
            this.app.widgetManager().removeWidget(this.app.mUpsellScreen);
            this.app.widgetManager().removeWidget(this.app.mUpsellScreen.mBackToGameButton);
            this.app.widgetManager().removeWidget(this.app.mUpsellScreen.mUpsellButton);
            this.app.mUpsellScreen = null;
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  UpsellScreenState (PVZApp app ){
            this.app = app;
        }
    }


