package com.xiyu.flash.games.pvz.states.loading;
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

    public class LoadingState implements IState {

        public void  onEnter (){
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
        }

        public void  draw (Canvas _canvas){
        }

        public void  onPush (){
        }
        public void  onExit (){
        }
        public void  update (){
            this.app.stateManager().changeState(PVZApp.STATE_TITLE_SCREEN);
        }
        public void  onPop (){
        }

        public  LoadingState (PVZApp app ){
            this.app = app;
        }

        public void setView() {
			app.getMainView().setState(this);
        }
    }


