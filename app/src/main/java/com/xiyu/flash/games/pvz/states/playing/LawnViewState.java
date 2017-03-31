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
import com.xiyu.flash.framework.widgets.ui.IButtonListener;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.logic.MessageWidget;
import com.xiyu.flash.games.pvz.logic.Board;

    public class LawnViewState implements IState, IButtonListener {

        public static int TimePanLeftStart =500;
        public static int TimePanRightStart =100;
        public static int TimePanLeftEnd =(TimePanLeftStart +1500);

        public static final int PANLEFT =0;

        public static int TimePanRightEnd =(TimePanRightStart +1000);

        public static final int PANRIGHT =1;

        private CButtonWidget theButton ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  buttonMouseEnter (int id ){
        }
        public void  onEnter (){
            this.theState = PANLEFT;
            this.drawMessage = true;
            this.app.mBoard.mMenuButton.setVisible(false);
            this.app.mCutsceneTime = 0;
            this.theButton = new CButtonWidget(0, this);
            this.theButton.label("");
            this.theButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB(1, 1, 1));
            FontInst font =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            this.theButton.font(font);
            this.theButton.setDisabled(true);
            this.theButton.visible = true;
            this.theButton.resize(0, 0, 540, 405);
            this.app.widgetManager().addWidget(this.theButton);
            this.app.widgetManager().setFocus(this.theButton);
        }
        public void  update (){
            int posStart ;
            int posEnd ;
            int x =0;
            int aBoardLeftPosition ;
            this.app.widgetManager().updateFrame();
            this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            int aTimePanLeftStart =TimePanLeftStart ;
            int aTimePanLeftEnd =TimePanLeftEnd ;
            int aTimePanRightStart =TimePanRightStart ;
            int aTimePanRightEnd =TimePanRightEnd ;
            if (this.theState == PANLEFT)
            {
                if (this.app.mCutsceneTime > aTimePanLeftStart)
                {
                    posStart = ((PVZApp.BACKGROUND_IMAGE_WIDTH - this.app.appWidth() ) - PVZApp.BOARD_OFFSET);
                    posEnd = 0;
                    x = TodCommon.TodAnimateCurve(aTimePanLeftStart, aTimePanLeftEnd, this.app.mCutsceneTime, posStart, posEnd, TodCommon.CURVE_EASE_IN_OUT);
                    this.app.mBoard.move(-(x), 0);
                };
                if ((((this.app.mBoard.x == 0)) && (this.drawMessage)))
                {
                    this.drawMessage = false;
                    this.theButton.setDisabled(false);
                    this.app.mBoard.DisplayAdvice("[CLICK_TO_CONTINUE]", MessageWidget.MESSAGE_STYLE_HINT_STAY, Board.ADVICE_NONE);
                };
            }
            else
            {
                if (this.theState == PANRIGHT)
                {
                    aBoardLeftPosition = 0;
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
                        this.app.stateManager().changeState(PVZApp.STATE_SEEDCHOOSER);
                    };
                };
            };
        }
        public void  buttonDownTick (int id ){
        }
        public void  onPush (){
        }
        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        private int theState ;
        private PVZApp app ;
        private boolean drawMessage =true ;

        public void  onExit (){
            this.app.widgetManager().removeWidget(this.theButton);
            this.app.widgetManager().setFocus(this.app.mSeedChooserScreen);
            this.app.mBoard.ClearAdvice(Board.ADVICE_NONE);
            this.app.mCutsceneTime = 0;
        }
        public void  buttonMouseLeave (int id ){
        }
        public void  buttonPress (int id ){
        }
        public void  onPop (){
        }
        public void  buttonRelease (int id ){
            this.theButton.setDisabled(true);
            this.app.mCutsceneTime = 0;
            this.theState = PANRIGHT;
            this.app.mBoard.ClearAdvice(Board.ADVICE_NONE);
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  LawnViewState (PVZApp app ){
            this.app = app;
        }
    }


