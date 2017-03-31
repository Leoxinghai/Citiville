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
import com.xiyu.flash.games.pvz.logic.AwardScreen;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class ShowAwardState implements IState {

        public void  onEnter (){
            this.app.mAwardScreen = new AwardScreen(this.app);
            this.app.widgetManager().addWidget(this.app.mAwardScreen);
            this.app.widgetManager().setFocus(this.app.mAwardScreen);
//            this.app.musicManager().playMusic(PVZMusic.ZENGARDEN);
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
            this.app.widgetManager().removeAllWidgets(true);
            this.app.mAwardScreen = null;
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
            if (this.app.mAwardScreen.mFadedIn)
            {
                this.app.widgetManager().addWidget(this.app.mAwardScreen.mStartButton);
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  ShowAwardState (PVZApp app ){
            this.app = app;
        }
    }


