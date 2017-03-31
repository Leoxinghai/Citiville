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
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;

    public class PauseState implements IState {

        public void  draw (Graphics2D g ){
            g.fillRect(0, 0, 540, 405, 0x99000000);
        }
        public void  update (){
        }
        public void  onPush (){
        }
        public void  onPop (){
        }

        private PVZApp mApp ;

        public void  onEnter (){
            this.mApp.widgetManager().addWidget(this.mButton);
        }
        public void  onExit (){
            this.mApp.widgetManager().removeWidget(this.mButton);
        }

        public void setView() {
			mApp.getMainView().setState(this);
        }

        private CButtonWidget mButton ;

        public  PauseState (PVZApp app ){
            this.mApp = app;
            this.mButton = new CButtonWidget(0, null);
            this.mButton.resize(0, 0, this.mApp.screenWidth(), this.mApp.screenHeight());
            this.mButton.setDisabled(true);
        }
    }


