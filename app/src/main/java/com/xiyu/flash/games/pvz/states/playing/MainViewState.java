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

import com.thelikes.thegot2run.gameloop;
import com.xiyu.util.*;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.games.pvz.logic.ChallengeScreen;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.logic.UI.DialogBox;

public class MainViewState extends SurfaceView {

    public void  onEnter (){
    }

    private PVZApp app ;
    private IState currState;

    public void  draw (Graphics2D g ){
        this.app.widgetManager().drawScreen(g);
    }

    public void  draw (Canvas canvas){
    	if(canvas == null)
    		return;
    	Graphics2D g = new Graphics2D(canvas);
    	currState.draw(g);
    }

	public boolean onTouchEvent(MotionEvent e) {

    	if(e.getAction() == MotionEvent.ACTION_DOWN) {
    		app.widgetManager().doMouseDown((int)e.getX(), (int)e.getY());
    	} else if(e.getAction() == MotionEvent.ACTION_MOVE) {
    		app.widgetManager().doMouseMove((int)e.getX(), (int)e.getY());
    	} else if(e.getAction() == MotionEvent.ACTION_UP) {
    		app.widgetManager().doMouseUp((int)e.getX(), (int)e.getY());
    	} else if(e.getAction() == MotionEvent.AXIS_WHEEL) {
    		app.widgetManager().doMouseWheel((int)e.getAxisValue(0));
    	}
//        this._widgetManager.onMouseEnter();


		return true;
	}


    public void  onPush (){
    }
    public void  onExit (){
        this.app.widgetManager().removeAllWidgets(true);
        this.app.mBoard = null;
        this.app.mDialogBox = null;
    }
    public void  update (){
    	this.app.handleFrame();
        //this.app.widgetManager().updateFrame();
    }
    public void  onPop (){
    }
    public void  GameContinue (Object obj ){
        this.app.soundManager().resumeAll();
        this.app.musicManager().resumeMusic();
        this.app.mAwardScreen.mStartButton.setDisabled(false);
    }

    public void setState(IState newState) {
    	currState = newState;
    	currState.onEnter();
    }

	public MainViewState(PVZApp app, IState newState) {
		super(app);
        this.app = app;
        app.init();
        app.start();

        currState = newState;
//    	currState.onEnter();
	}
}

