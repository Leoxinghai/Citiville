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
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.TodCommon;

    public class PanRightState implements IState {

        public static int TimePanRightStart =100;//1500;
        public static int TimePanRightEnd =(TimePanRightStart +2000);

        public void  onEnter (){
            String aString ="";
            this.app.mCutsceneTime = 0;
            this.app.mBoard.move(PVZApp.BOARD_OFFSET, 0);
            if (this.app.IsAdventureMode())
            {
                aString = "[PLAYERS_HOUSE]";
            }
            else
            {
                if (this.app.IsSurvivalMode())
                {
                    aString = "[SURVIVAL_POOL_ENDLESS]";
                };
            };
            if (!((this.app.IsAdventureMode()) && ((this.app.mLevel == 11))))
            {
                this.app.mBoard.mAdvice.SetLabel(aString, Board.MESSAGE_STYLE_HOUSE_NAME);
            };
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
            int posStart ;
            int posEnd ;
            int x =0;
            this.app.widgetManager().updateFrame();
            this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            int aTimePanRightStart =TimePanRightStart ;
            int aTimePanRightEnd =TimePanRightEnd ;
            int aBoardLeftPosition =PVZApp.BOARD_OFFSET ;
            if (this.app.mCutsceneTime <= aTimePanRightStart)
            {
                this.app.mBoard.move(aBoardLeftPosition, 0);
            };
            if ((((this.app.mCutsceneTime > aTimePanRightStart)) && ((this.app.mCutsceneTime <= aTimePanRightEnd))))
            {
                posStart = -(aBoardLeftPosition);
                posEnd = ((-(PVZApp.BOARD_OFFSET) + PVZApp.BACKGROUND_IMAGE_WIDTH) - this.app.appWidth());
                x = TodCommon.TodAnimateCurve(aTimePanRightStart, aTimePanRightEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                this.app.mBoard.move(-(x), 0);
            };
            if (this.app.mBoard.x <= -257)
            {
                if (this.app.mBoard.ChooseSeedsOnCurrentLevel())
                {
                	//this.app.stateManager().changeState(PVZApp.STATE_SEEDCHOOSER);
                	this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                }
                else
                {
                    this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                };
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  PanRightState (PVZApp app ){
            this.app = app;
        }
    }


