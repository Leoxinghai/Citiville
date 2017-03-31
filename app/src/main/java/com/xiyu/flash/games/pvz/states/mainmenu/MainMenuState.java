package com.xiyu.flash.games.pvz.states.mainmenu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
//import android.graphics.Color;
import android.graphics.Point;

import com.thelikes.thegot2run.gameloop;
import com.xiyu.util.*;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.framework.widgets.ui.IButtonListener;
import com.xiyu.flash.framework.widgets.ui.ICheckboxListener;
//import flash.geom.Matrix;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.resources.images.ImageInst;
//import flash.net.URLRequest;
import com.xiyu.flash.framework.AppUtils;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Point;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.widgets.ui.CheckboxWidget;
//import flash.net.navigateToURL;
import com.xiyu.flash.games.pvz.logic.UI.DialogBox;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.framework.utils.Utils;

import java.util.Vector;





//import flash.geom.Rectangle;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;

    public class MainMenuState implements IState, IButtonListener, ICheckboxListener {

        private static  Matrix mScratchMatrix =new Matrix ();

        public boolean mShowingDialog ;

        private SurfaceHolder holder;
   	    gameloop gameLoopThread;

        public void  onPop (){
        }

        private Reanimation menuReanim ;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        private CButtonWidget mUpsellButton ;
        private final int Menu_MusicCheckBox =105;

        public void  buttonMouseEnter (int id ){
            if (id != this.GameSelector_Minigame)
            {
                this.app.foleyManager().playFoley(PVZFoleyType.BLEEP);
            };
        }

        private ImageButtonWidget mPuzzleButton ;

        public void  ButtonsOn (){
            this.mMusicCheckBox.disabled = false;
            this.mSoundCheckBox.disabled = false;
            this.mPuzzleButton.setDisabled(false);
            this.mMinigameButton.setDisabled(false);
            this.mSurvivalButton.setDisabled(false);
            this.mAdventureButton.setDisabled(false);
            this.mUpsellButton.setDisabled(false);
            this.mAdventureButton.setVisible(true);
            this.mPuzzleButton.setVisible(true);
            this.mMinigameButton.setVisible(true);
            this.mSurvivalButton.setVisible(true);
            this.mUpsellButton.setVisible(true);
            this.menuReanim.setTrackVisible("SelectorScreen_Survival_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_Challenges_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_ZenGarden_button", false);
            if (this.app.mLevel == 1)
            {
                this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_button", false);
            }
            else
            {
                this.menuReanim.setTrackVisible("SelectorScreen_Adventure_button", false);
            };
        }

        private PVZApp app ;
        private ImageInst mAdventureButtonImage ;
        private ImageButtonWidget mMinigameButton ;
        public boolean mAdventureLevel ;

        public void  buttonPress (int id ){
        }

        private ImageButtonWidget mAdventureButton ;

        public void  onEnter (){
            this.app.mTotalZombiesKilled = 0;
            this.app.adAPI.setScore(this.app.mTotalZombiesKilled);

//            this.app.mUpsellOn = AppUtils.asBoolean(this.app.getProperties().upsell.enabled, false);
//            this.app.mUpsellLink = this.app.getProperties().upsell.url;
            if (this.app.musicManager().getPlayingId() != PVZMusic.CRAZY_DAVE)
            {
                this.app.musicManager().playMusic(PVZMusic.CRAZY_DAVE,true, 1);
            };
            this.mShowingDialog = false;
            if (this.app.mBoard != null)
            {
                this.app.widgetManager().removeAllWidgets(true);
                this.app.mBoard = null;
            };
            this.initDialog();
            this.initMenuReanim();
        }

        private int GameSelector_Sound =104;

        private String xmlStr = "";

    		public boolean onTouchEvent(MotionEvent event) {


      	  	if(event.getAction()==MotionEvent.ACTION_DOWN)
      	  	{
      	  		xmlStr = "action_down";

      	  	} else if(event.getAction()==MotionEvent.ACTION_MOVE) {
      	  		xmlStr = "action_move";
      	  	} else if(event.getAction()==MotionEvent.ACTION_UP) {
      	  		xmlStr = "action_up";
      	  	}


    	  		return true;
    		}


        public void  draw (Graphics2D g ){

        }

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
            int yPos ;
  			Graphics2D g = new Graphics2D(canvas);

            if (this.menuReanim != null)
            {
//                this.menuReanim.drawLerp(g, mScratchMatrix, 1);
            };
            if (this.zombieHandReanim != null)
            {
              //  this.zombieHandReanim.drawLerp(g, mScratchMatrix, 1);
            };
            /*
            if (this.mUpsellButton.visible)
            {
                yPos = (int)TodCommon.TodAnimateCurveFloat(0, 50, this.mUpsellCounter, -50, 110, TodCommon.CURVE_EASE_OUT);
                g.drawImage(this.app.imageManager().getImageInst(PVZImages.IMAGE_SELECTORSCREEN_WOODSIGN), 17, yPos);
                this.mUpsellButton.move(17, yPos);
            };
            */
//            g.reset();
            return;
            //this.app.widgetManager().drawScreen(g);
        }

        private final int GameSelector_Adventure =100;
        private ImageInst mStageImage ;

        private void  initMenuReanim (){
            Point aPoint =new Point(1,1);
            boolean buttonsOn =true ;
			this.app.mSurvivalLocked = false;
            if (this.menuReanim == null)
            {
                this.menuReanim = this.app.reanimator().createReanimation(PVZReanims.REANIM_SELECTORSCREEN);
                this.menuReanim.x(130);
                this.menuReanim.y(-30);
                this.menuReanim.animRate(20);
                this.menuReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                this.menuReanim.currentTrack("anim_open");
                this.app.foleyManager().playFoley(PVZFoleyType.ROLL_IN);
                buttonsOn = false;
            }
            else
            {
                this.menuReanim.animTime(1);
            };
            this.menuReanim.setTrackVisible("woodsign2", false);
            this.menuReanim.setTrackVisible("woodsign3", false);
            this.menuReanim.overrideImage("SelectorScreen_ZenGarden_button", this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED));
            if (this.app.mPuzzleLocked)
            {
                this.menuReanim.overrideImage("SelectorScreen_Challenges_button", this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON_LOCKED));
            };
            if (this.app.mSurvivalLocked)
            {
                this.menuReanim.overrideImage("SelectorScreen_Survival_button", this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON_LOCKED));
            };
            if (this.app.mLevel == 1)
            {
                this.menuReanim.setTrackVisible("SelectorScreen_Adventure_button", false);
                this.menuReanim.setTrackVisible("SelectorScreen_Adventure_shadow", false);
                this.mAdventureButton = new ImageButtonWidget(this.GameSelector_Adventure, this);
                this.mAdventureButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_BUTTON1);
                this.mAdventureButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_HIGHLIGHT);
                this.mAdventureButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_HIGHLIGHT);
                this.mAdventureButton.mDownOffset = aPoint;
                this.mAdventureButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_BUTTON1);
                this.mAdventureButton.resize(273.4, 43.9, this.mAdventureButton.mUpImage.width(), 80);
            }
            else
            {
                this.GetLevelNumbers();
                this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_button", false);
                this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_shadow", false);
                this.mAdventureButton = new ImageButtonWidget(this.GameSelector_Adventure, this);
                this.mAdventureButtonImage = this.MakeStartAdventureButton(this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_ADVENTURE_BUTTON));
                this.mAdventureButtonHighlight = this.MakeStartAdventureButton(this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_ADVENTURE_HIGHLIGHT));
                this.mAdventureButton.mUpImage = this.mAdventureButtonImage;
                this.mAdventureButton.mOverImage = this.mAdventureButtonHighlight;
                this.mAdventureButton.mDownImage = this.mAdventureButtonHighlight;
                this.mAdventureButton.mDownOffset = aPoint;
                this.mAdventureButton.mDisabledImage = this.mAdventureButtonImage;
                this.mAdventureButton.resize(273.4, 53, this.mAdventureButton.mUpImage.width(), 80);
                this.menuReanim.overrideImage("SelectorScreen_Adventure_button", this.mAdventureButtonImage);
            };
            this.mStartingGameCounter = 0;
            this.mStartingGame = false;
            this.mAdventureButton.doFinger = true;
            this.mAdventureButton.visible = false;
            this.mAdventureButton.setDisabled(false);
            this.app.widgetManager().addWidget(this.mAdventureButton);
            this.mSurvivalButton = new ImageButtonWidget(this.GameSelector_Minigame, this);
            this.mSurvivalButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED);
            this.mSurvivalButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED);
            this.mSurvivalButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED);
            this.mSurvivalButton.mDownOffset = aPoint;
            this.mSurvivalButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED);
            this.mSurvivalButton.resize(278.8, 221.4, this.mSurvivalButton.mUpImage.width(), 50);
            this.mSurvivalButton.doFinger = true;
            this.mSurvivalButton.visible = false;
            this.mSurvivalButton.setDisabled(false);
            this.app.widgetManager().addWidget(this.mSurvivalButton);
            this.mPuzzleButton = new ImageButtonWidget(this.GameSelector_Puzzle, this);
            if (this.app.mPuzzleLocked)
            {
                this.mPuzzleButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON_LOCKED);
                this.mPuzzleButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON_LOCKED);
                this.mPuzzleButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON_LOCKED);
                this.mPuzzleButton.mDownOffset = aPoint;
                this.mPuzzleButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON_LOCKED);
            }
            else
            {
                this.mPuzzleButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON);
                this.mPuzzleButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_HIGHLIGHT);
                this.mPuzzleButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_HIGHLIGHT);
                this.mPuzzleButton.mDownOffset = aPoint;
                this.mPuzzleButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON);
            };
            this.mPuzzleButton.resize(276.8, 171.9, this.mPuzzleButton.mUpImage.width(), 50);
            this.mPuzzleButton.doFinger = true;
            this.mPuzzleButton.visible = false;
            this.mPuzzleButton.setDisabled(false);
            this.app.widgetManager().addWidget(this.mPuzzleButton);
            this.mMinigameButton = new ImageButtonWidget(this.GameSelector_Survival, this);
            if (this.app.mSurvivalLocked)
            {
                this.mMinigameButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON_LOCKED);
                this.mMinigameButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON_LOCKED);
                this.mMinigameButton.mDownOffset = aPoint;
                this.mMinigameButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON_LOCKED);
                this.mMinigameButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON_LOCKED);
            }
            else
            {
                this.mMinigameButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON);
                this.mMinigameButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_HIGHLIGHT);
                this.mMinigameButton.mDownOffset = aPoint;
                this.mMinigameButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_HIGHLIGHT);
                this.mMinigameButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON);
            };
            this.mMinigameButton.resize(274.1, 114.8, this.mMinigameButton.mUpImage.width(), 50);
            this.mMinigameButton.doFinger = true;
            this.mMinigameButton.visible = false;
            this.mMinigameButton.setDisabled(false);
            this.app.widgetManager().addWidget(this.mMinigameButton);
            this.mUpsellButton = new CButtonWidget(this.GameSelector_Upsell, this);
            this.mUpsellButton.setDisabled(false);
            this.mUpsellButton.visible = false;
            this.mUpsellButton.label(this.app.stringManager().translateString("[UPSELL_BUY_BUTTON]"));
            this.mUpsellButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB(1, 1, 1));
            this.mUpsellButton.setColor(CButtonWidget.COLOR_LABEL_HILITE, Color.RGB((179 / 0xFF), (158 / 0xFF), (110 / 0xFF)));
            FontInst font5 =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            font5.scale(0.9);
            this.mUpsellButton.font(font5);
            this.mUpsellButton.resize(17, 110, 190, 30);
            this.app.widgetManager().addWidget(this.mUpsellButton);
            this.initSoundCheckboxes(buttonsOn);
            if (buttonsOn)
            {
                this.ButtonsOn();
            };
        }

        private final int GameSelector_Puzzle =102;

        public void  ButtonsOff (){
            this.mMusicCheckBox.disabled = true;
            this.mSoundCheckBox.disabled = true;
            this.mAdventureButton.setDisabled(true);
            this.mPuzzleButton.setDisabled(true);
            this.mMinigameButton.setDisabled(true);
            this.mSurvivalButton.setDisabled(true);
            this.mSurvivalButton.setDisabled(true);
            this.mAdventureButton.setVisible(false);
            this.mPuzzleButton.setVisible(false);
            this.mMinigameButton.setVisible(false);
            this.mSurvivalButton.setVisible(false);
            this.mUpsellButton.setVisible(false);
            this.menuReanim.setTrackVisible("SelectorScreen_Survival_button", true);
            this.menuReanim.setTrackVisible("SelectorScreen_Challenges_button", true);
            this.menuReanim.setTrackVisible("SelectorScreen_ZenGarden_button", true);
            if (this.app.mLevel == 1)
            {
                this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_button", true);
            }
            else
            {
                this.menuReanim.setTrackVisible("SelectorScreen_Adventure_button", true);
            };
        }
        public void  StartGame (){
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().resumeMusic();
                this.app.soundManager().resumeAll();
            };
            this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
        }

        private final int GameSelector_Minigame =101;
        private boolean mStartingGame ;
        private int mUpsellCounter ;

        public void  checkboxChecked (int id ,boolean checked ){
            switch ((int)id)
            {
                case Menu_SoundCheckBox:
                    if (checked)
                    {
                        this.app.soundManager().unmute();
                    }
                    else
                    {
                        this.app.soundManager().mute();
                    };
                    this.app.setSaveData(this.app.mSaveObject);
                    break;
                case Menu_MusicCheckBox:
                    if (checked)
                    {
                        this.app.musicManager().unmute();
                    }
                    else
                    {
                        this.app.musicManager().mute();
                    };
                    this.app.setSaveData(this.app.mSaveObject);
                    break;
            };
        }

        public CheckboxWidget mSoundCheckBox ;

        public void  unlockAllModes (){
            Point aPoint ;
            if (((this.app.mPuzzleLocked) || (this.app.mSurvivalLocked)))
            {
                this.app.mPuzzleLocked = false;
                this.app.mSurvivalLocked = false;
                aPoint = new Point(1, 1);
                this.mPuzzleButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON);
                this.mPuzzleButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_HIGHLIGHT);
                this.mPuzzleButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_HIGHLIGHT);
                this.mPuzzleButton.mDownOffset = aPoint;
                this.mPuzzleButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_CHALLENGES_BUTTON);
                this.mMinigameButton.mUpImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON);
                this.mMinigameButton.mDownImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_HIGHLIGHT);
                this.mMinigameButton.mDownOffset = aPoint;
                this.mMinigameButton.mOverImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_HIGHLIGHT);
                this.mMinigameButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_SURVIVAL_BUTTON);
                this.mPuzzleButton.setDisabled(false);
                this.mSurvivalButton.setDisabled(false);
            };
        }

        private ImageButtonWidget mSoundButton ;

        public void  buttonRelease (int id ){
            String aDialogHeader ;
            String aDialogMessage ;
            String aDialogOk ;
            String aDialogCancel ;
            int mode =-1;
            int level =-1;
            boolean doStart =false;
//            XML upsellXML =new XML("<data>DeluxeDownload</data>");
            switch (id)
            {
                case GameSelector_Upsell:
/*
                    if (this.app.mUpsellOn)
                    {
                        this.ButtonsOff();
                        this.app.stateManager().pushState(PVZApp.STATE_UPSELL_SCREEN);
                    }
                    else
                    {
                        this.app.adAPI.CustomEvent(upsellXML, this.DoUpsell);
                        navigateToURL(this.mURLRequest);
                    };
*/
                    break;
                case GameSelector_Adventure:
                    this.app.foleyManager().playFoley(PVZFoleyType.GRAVEBUTTON);
                    this.mStartingLevel = this.app.mLevel;
                    this.app.mGameMode = PVZApp.GAMEMODE_ADVENTURE;
                    this.playAdventureIntro();
                    break;
                case GameSelector_Puzzle:
                    if (this.app.mPuzzleLocked)
                    {
                        this.mShowingDialog = true;
                        this.ButtonsOff();
                        this.mDialogBox.mDialogType = DialogBox.DIALOG_LOCKED;
                        aDialogHeader = this.app.stringManager().translateString("[MODE_LOCKED]");
                        aDialogMessage = this.app.stringManager().translateString("[PUZZLE_LOCKED_MESSAGE]");
                        aDialogOk = this.app.stringManager().translateString("[DIALOG_BUTTON_OK]");
                        aDialogCancel = "";
                        this.mDialogBox.visible = true;
                        this.mDialogBox.move(75, 50);
                        this.mDialogBox.InitializeDialogBox(aDialogHeader, aDialogMessage, aDialogOk, aDialogCancel, 4, 2);
                    }
                    else
                    {
                        this.app.foleyManager().playFoley(PVZFoleyType.GRAVEBUTTON);
                        this.app.mGameMode = PVZApp.GAMEMODE_SCARY_POTTER_ENDLESS;
                        this.mStartingLevel = 1;
                        this.gameReady();
                    };
                    break;
                case GameSelector_Survival:
                    if (this.app.mSurvivalLocked)
                    {
                        this.mShowingDialog = true;
                        this.ButtonsOff();
                        this.mDialogBox.mDialogType = DialogBox.DIALOG_LOCKED;
                        aDialogHeader = this.app.stringManager().translateString("[MODE_LOCKED]");
                        aDialogMessage = this.app.stringManager().translateString("[SURVIVAL_LOCKED_MESSAGE]");
                        aDialogOk = this.app.stringManager().translateString("[DIALOG_BUTTON_OK]");
                        aDialogCancel = "";
                        this.mDialogBox.visible = true;
                        this.mDialogBox.move(75, 50);
                        this.mDialogBox.InitializeDialogBox(aDialogHeader, aDialogMessage, aDialogOk, aDialogCancel, 4, 2);
                    }
                    else
                    {
                        this.app.foleyManager().playFoley(PVZFoleyType.GRAVEBUTTON);
                        this.app.mGameMode = PVZApp.GAMEMODE_SURVIVAL_ENDLESS_STAGE_2;
                        this.mStartingLevel = 1;
                        this.gameReady();
                    };
                    break;
                case GameSelector_Minigame:
                    if (this.app.mUpsellOn)
                    {
                        this.ButtonsOff();
                        this.app.stateManager().pushState(PVZApp.STATE_UPSELL_SCREEN);
                    }
                    else
                    {
                        this.mShowingDialog = true;
                        this.ButtonsOff();
                        this.mDialogBox.mDialogType = DialogBox.DIALOG_LOCKED;
                        aDialogHeader = this.app.stringManager().translateString("[FULL_VERSION_ONLY]");
                        aDialogMessage = this.app.stringManager().translateString("[FULL_VERSION_MODE]");
                        aDialogOk = this.app.stringManager().translateString("[DIALOG_BUTTON_OK]");
                        this.mDialogBox.visible = true;
                        this.mDialogBox.move(75, 50);
//                        this.mDialogBox.InitializeDialogBox(aDialogHeader, aDialogMessage, aDialogOk, aDialogCancel, 4, 2);
                    };
            };
            if (doStart)
            {
                this.app.mGameMode = mode;
            };
        }

        public DialogBox mDialogBox ;

        private void  initDialog (){
            this.mDialogBox = new DialogBox(this.app, null);
            this.app.widgetManager().addWidget(this.mDialogBox);
            this.app.widgetManager().addWidget(this.mDialogBox.mOkButton);
            this.app.widgetManager().addWidget(this.mDialogBox.mCancelButton);
        }

        private final int GameSelector_Upsell =105;

        public void  GetLevelNumbers (){
            ImageInst img ;
            int theLevel =this.app.mLevel ;
            int aArea =TodCommon.ClampInt ((((theLevel -1)/Board.LEVELS_PER_AREA )+1),1,6);
            int aSubArea =(theLevel -((aArea -1)*Board.LEVELS_PER_AREA ));
            this.mStageImage = this.app.imageManager().getImageInst("SELECTORSCREEN_ADVENTURE_HIGHLIGHT");//PVZImages.IMAGE_SELECTORSCREEN_LEVELNUMBERS);
            this.mStageImage.frame(aArea);
            Vector digits=Utils.getDigits(aSubArea );
            Array images =new Array ();
            Array srcRects =new Array ();
            Array destPoints =new Array ();
            int numDigits =digits.size();
            int i =0;
            while (i < numDigits)
            {
                img = this.app.imageManager().getImageInst("SELECTORSCREEN_ADVENTURE_HIGHLIGHT");//PVZImages.IMAGE_SELECTORSCREEN_LEVELNUMBERS);
                img.frame( ((Integer)digits.elementAt(i)).intValue());
                images.add(i,img);
                srcRects.add(i,new Rectangle(0,0,img.width(),img.height()));
                destPoints.add(i,new Point((i*(-(img.width())+2)),((numDigits-i)-1)));
                i++;
            };
            this.mLevelImage = Utils.createMergedImage(images, srcRects, destPoints);
        }

        public CheckboxWidget mMusicCheckBox ;
        private ImageInst mLevelImage ;

        private void  playAdventureIntro (){
            this.mStartingGame = true;
            this.mAdventureButton.setDisabled(true);
            this.mPuzzleButton.setDisabled(true);
            this.mMinigameButton.setDisabled(true);
            this.mSurvivalButton.setDisabled(true);
            this.mUpsellButton.setDisabled(true);
            this.mSoundCheckBox.disabled = true;
            this.mMusicCheckBox.disabled = true;
            this.zombieHandReanim = this.app.reanimator().createReanimation(PVZReanims.REANIM_ZOMBIE_HAND);
            this.zombieHandReanim.x(-50);
            this.zombieHandReanim.y(0);
            this.zombieHandReanim.animRate(30);
            this.zombieHandReanim.currentTrack("anim_play");
            this.zombieHandReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
            this.app.musicManager().playMusic(PVZMusic.LOSEMUSIC,true,1);
            this.app.foleyManager().playFoley(PVZFoleyType.DIRT_RISE);
        }
        public void  onPush (){
        }

        private int mStartingGameCounter ;

        public void  onExit (){
            this.zombieHandReanim = null;
            this.app.widgetManager().removeAllWidgets(true);
        }
        public void  buttonDownTick (int id ){
        }

        private int mStartingLevel =1;

        public void  update (){
            this.app.widgetManager().updateFrame();
            if (this.menuReanim != null)
            {
                this.menuReanim.update();
                if (this.menuReanim.shouldTriggerTimedEvent(0.3))
                {
                    this.showMenuButtons();
                };
            };
            if (((!(this.mDialogBox.visible)) && (this.mShowingDialog)))
            {
                this.mShowingDialog = false;
                this.ButtonsOn();
            };
            if (this.zombieHandReanim != null)
            {
                this.zombieHandReanim.update();
            };
            if (this.mStartingGame)
            {
                this.mStartingGameCounter++;
                if (this.mStartingGameCounter > 450)
                {
                    this.zombieHandReanim = null;
                    this.app.widgetManager().removeAllWidgets(true);
                    this.gameReady();
                };
                if ((this.mStartingGameCounter % 20) < 10)
                {
                    if (this.app.mLevel == 1)
                    {
                        this.mAdventureButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_HIGHLIGHT);
                    }
                    else
                    {
                        this.mAdventureButton.mDisabledImage = this.mAdventureButtonHighlight;
                    };
                }
                else
                {
                    if (this.app.mLevel == 1)
                    {
                        this.mAdventureButton.mDisabledImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_STARTADVENTURE_BUTTON1);
                    }
                    else
                    {
                        this.mAdventureButton.mDisabledImage = this.mAdventureButtonImage;
                    };
                };
                if (this.mStartingGameCounter == 125)
                {
                    this.app.foleyManager().playFoley(PVZFoleyType.EVILLAUGH);
                };
            };
            if (((this.mUpsellButton.visible) && ((this.mUpsellCounter < 200))))
            {
                this.mUpsellCounter++;
            };
        }

        private final int GameSelector_Survival =103;

        public ImageInst  MakeStartAdventureButton (ImageInst theImage ){
            int w =theImage.width();
            int h =theImage.height();
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            bufferG.drawImage(theImage, 0, 0);
            bufferG.drawImage(this.mStageImage, 102, 59);
            bufferG.drawImage(this.mLevelImage, 114, 61);
            return (aBufferedImage);
        }
        private void  gameReady (){
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().pauseMusic();
                this.app.soundManager().pauseAll();
            };
            this.StartGame();
//            this.app.adAPI.GameReady(this.app.mGameMode, this.mStartingLevel, this.StartGame());
        }
        public ImageInst  MakeCheckBox (ImageInst theImage ){
            int w =theImage.width();
            int h =theImage.height();
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            bufferG.drawImage(theImage, 0, 0);
            ImageInst check =this.app.imageManager().getImageInst("SELECTORSCREEN_WOODSIGN1");//PVZImages.IMAGE_SELECTORSCREEN_SOUNDX );
            bufferG.drawImage(check, 16, 3);
            return (aBufferedImage);
        }

        private Reanimation zombieHandReanim ;
        private ImageButtonWidget mSurvivalButton ;

        private void  showMenuButtons (){
            this.menuReanim.overrideImage("SelectorScreen_ZenGarden_button", this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SELECTORSCREEN_VASEBREAKER_BUTTON_LOCKED));
            this.menuReanim.setTrackVisible("SelectorScreen_Survival_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_Challenges_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_ZenGarden_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_button", false);
            this.menuReanim.setTrackVisible("SelectorScreen_Adventure_button", false);
            if (this.app.mLevel == 1)
            {
                this.menuReanim.setTrackVisible("SelectorScreen_Adventure_shadow", false);
            }
            else
            {
                this.menuReanim.setTrackVisible("SelectorScreen_StartAdventure_shadow", false);
            };
            this.mAdventureButton.visible = true;
            this.mSurvivalButton.visible = true;
            this.mPuzzleButton.visible = true;
            this.mMinigameButton.visible = true;
            this.mSoundCheckBox.visible = true;
            this.mMusicCheckBox.visible = true;
            this.mUpsellButton.visible = true;
        }
        private void  initSoundCheckboxes (boolean visible ){
            ImageInst checkbox =this.app.imageManager().getImageInst("SELECTORSCREEN_WOODSIGN1");//PVZImages.IMAGE_SELECTORSCREEN_SOUND2 );
            ImageInst emptyCheckbox =this.MakeCheckBox(this.app.imageManager().getImageInst("SELECTORSCREEN_WOODSIGN2"));//PVZImages.IMAGE_SELECTORSCREEN_SOUND1 ));
            this.mSoundCheckBox = new CheckboxWidget(this.Menu_SoundCheckBox, checkbox, emptyCheckbox, this);
            this.mSoundCheckBox.resize(412, 352, 44, 29);
            if (!this.app.soundManager().isMuted())
            {
                this.mSoundCheckBox.setChecked(true, false);
            };
            this.mSoundCheckBox.visible = visible;
            this.app.widgetManager().addWidget(this.mSoundCheckBox);
            checkbox = this.app.imageManager().getImageInst("SELECTORSCREEN_WOODSIGN1");//PVZImages.IMAGE_SELECTORSCREEN_MUSIC2);
            emptyCheckbox = this.MakeCheckBox(this.app.imageManager().getImageInst("SELECTORSCREEN_WOODSIGN1"));//PVZImages.IMAGE_SELECTORSCREEN_MUSIC1));
            this.mMusicCheckBox = new CheckboxWidget(this.Menu_MusicCheckBox, checkbox, emptyCheckbox, this);
            this.mMusicCheckBox.resize(480, 350, 43, 31);
            if (!this.app.musicManager().isMuted())
            {
                this.mMusicCheckBox.setChecked(true, false);
            };
            this.mMusicCheckBox.visible = visible;
            this.app.widgetManager().addWidget(this.mMusicCheckBox);
        }
        public void  buttonMouseLeave (int id ){
        }
        public void  DoUpsell (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        private final int Menu_SoundCheckBox =104;
        private ImageInst boardImg ;
        private ImageInst mAdventureButtonHighlight ;

        public  MainMenuState (PVZApp app ){
            this.app = app;
/*
            initMenuReanim();

      	  gameLoopThread = new gameloop(this);
      	  holder = getHolder();

               holder.addCallback(new SurfaceHolder.Callback() {
    			@SuppressWarnings("deprecation")
    			@Override
              public void surfaceDestroyed(SurfaceHolder holder)
              {
    				 //for stoping the game
    				gameLoopThread.setRunning(false);
    				gameLoopThread.getThreadGroup().interrupt();
               }

              @SuppressLint("WrongCall")
    			@Override
              public void surfaceCreated(SurfaceHolder holder)
              {
              	  gameLoopThread.setRunning(true);
              	  gameLoopThread.start();

               }
              @Override
              public void surfaceChanged(SurfaceHolder holder, int format,int width, int height)
                      {
                      }
               });
*/
        }
    }


