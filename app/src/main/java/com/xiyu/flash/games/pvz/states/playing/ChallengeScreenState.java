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

    public class ChallengeScreenState implements IState {

        public void  onEnter (){
            this.app.mChallengeScreen = new ChallengeScreen(this.app, this.app.mBoard);
            this.app.widgetManager().addWidget(this.app.mChallengeScreen);
            this.app.widgetManager().setFocus(this.app.mChallengeScreen);
            this.app.widgetManager().addWidget(this.app.mChallengeScreen.mStartButton);
            this.app.widgetManager().addWidget(this.app.mChallengeScreen.mBackToMenuButton);
        }

        private PVZApp app ;

        public void onDraw(Canvas canvas)
	      {
	    	 	Paint paint = new Paint();
//	    	    paint.setColor(Color.BLUE);

	    	    paint.setAntiAlias(true);
	    	    paint.setFakeBoldText(true);
	    	    paint.setTextSize(15);
	    	    paint.setTextAlign(Align.LEFT);
	    	    canvas.drawText("Score :"+55, 3*4/4, 98, paint);
	      }

        public void  draw (Canvas canvas ){
  			if(canvas ==null)
  				return;
  			Graphics2D g = new Graphics2D(canvas);
//  			this.app.mBoard.draw(g);
            this.app.widgetManager().drawScreen(g);
  		}

  		public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }

        public void  onPush (){
        }
        public void  onExit (){
            this.app.widgetManager().removeWidget(this.app.mChallengeScreen);
            this.app.widgetManager().removeWidget(this.app.mChallengeScreen.mStartButton);
            this.app.widgetManager().removeWidget(this.app.mChallengeScreen.mBackToMenuButton);
            this.app.mChallengeScreen = null;
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
        }
        public void  onPop (){
        }

        public void setView() {
        	app.getMainView().setState(this);
        }

        private SurfaceHolder holder;
   	    private gameloop gameLoopThread;

   	    public  ChallengeScreenState (PVZApp app ){
        	this.app = app;
//        	app.mBoard = new Board(app);
        	onEnter ();

        }
    }


