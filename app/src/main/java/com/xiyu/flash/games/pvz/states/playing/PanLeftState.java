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

import com.pgh.mahjong.MJCards;
import com.xiyu.util.*;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.TodCommon;

    public class PanLeftState implements IState {

        public static int TimePanLeftStart =500;
        public static int TimePanLeftEnd =(TimePanLeftStart +1500);

        public void  onEnter (){
            this.app.mBoard.mMenuButton.disabled = true;
            MJCards.getInstance().init();
            
            this.app.mCutsceneTime = 0;
            if (this.app.mBoard.ChooseSeedsOnCurrentLevel())
            {
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
            int aTimePanLeftStart =TimePanLeftStart ;
            int aTimePanLeftEnd =TimePanLeftEnd ;
            if (this.app.mCutsceneTime > aTimePanLeftStart)
            {
                posStart = ((PVZApp.BACKGROUND_IMAGE_WIDTH - this.app.appWidth()) - PVZApp.BOARD_OFFSET);
                posEnd = 0;
                x = TodCommon.TodAnimateCurve(aTimePanLeftStart, aTimePanLeftEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                this.app.mBoard.move(-(x), 0);
            };
            if (this.app.mBoard.x == 0)
            {
                this.app.stateManager().changeState(PVZApp.STATE_SODROLL);
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  PanLeftState (PVZApp app ){
            this.app = app;
        }
    }


