package com.xiyu.flash.games.pvz.logic.UI;
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

import com.xiyu.flash.framework.widgets.CWidget;
import com.xiyu.flash.framework.widgets.ui.IButtonListener;
import com.xiyu.flash.framework.widgets.ui.ICheckboxListener;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.widgets.ui.CheckboxWidget;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
//import flash.net.navigateToURL;
import com.xiyu.flash.games.pvz.logic.Board;
//import flash.net.URLRequest;
import com.xiyu.flash.games.pvz.resources.PVZImages;
//import flash.geom.Point;

    public class OptionsDialog extends CWidget implements IButtonListener, ICheckboxListener {

        public static final int DIALOG_RESTART =0;
        public static final int DIALOG_MAINMENU =1;

        public String mDialogHeader ;
        private ImageInst mOptionsButton ;
        private final int Menu_MusicCheckBox =105;
        public CheckboxWidget mMusicCheckBox ;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        public ImageButtonWidget mUpsellButton ;

        public void  buttonMouseEnter (int id ){
        }

        public PVZApp app ;

        public void  KillDialogBox (){
            this.mDialogBox.setVisible(false);
            this.mDialogBox.KillButtons();
            this.mRestartButton.setDisabled(false);
            this.mBackToGameButton.setDisabled(false);
            this.mBackToMainButton.setDisabled(false);
            this.mUpsellButton.setDisabled(false);
        }

        public boolean mSeedChooserShowing =false ;

        public void  buttonMouseLeave (int id ){
        }

        public boolean mShowingDialogBox ;
        private final int Menu_Restart =100;

        public void  buttonPress (int id ){
        }

        public String mDialogMessage ;
        public ImageButtonWidget mBackToGameButton ;
        private final int Menu_BackToGame =103;
        private ImageInst mBackdropImage ;
        public ImageButtonWidget mBackToMainButton ;
        public ImageButtonWidget mRestartButton ;
        private ImageInst mReturnToGameButton ;

        public void  PickDialogType (int theType ){
            switch (theType)
            {
                case DIALOG_RESTART:
                    if (this.app.IsAdventureMode())
                    {
                        this.mDialogHeader = this.app.stringManager().translateString("[RESTART_LEVEL_HEADER]");
                        this.mDialogMessage = this.app.stringManager().translateString("[RESTART_LEVEL_BODY]");
                        this.mDialogOk = this.app.stringManager().translateString("[RESTART_BUTTON]");
                        this.mDialogCancel = this.app.stringManager().translateString("[DIALOG_BUTTON_CANCEL]");
                    }
                    else
                    {
                        if (this.app.IsSurvivalMode())
                        {
                            this.mDialogHeader = this.app.stringManager().translateString("[RESTART_LEVEL_HEADER]");
                            this.mDialogMessage = this.app.stringManager().translateString("[RESTART_LEVEL_BODY]");
                            this.mDialogOk = this.app.stringManager().translateString("[RESTART_BUTTON]");
                            this.mDialogCancel = this.app.stringManager().translateString("[DIALOG_BUTTON_CANCEL]");
                        }
                        else
                        {
                            if (this.app.IsScaryPotterLevel())
                            {
                                this.mDialogHeader = this.app.stringManager().translateString("[RESTART_PUZZLE_HEADER]");
                                this.mDialogMessage = this.app.stringManager().translateString("[RESTART_PUZZLE_BODY]");
                                this.mDialogOk = this.app.stringManager().translateString("[RESTART_BUTTON]");
                                this.mDialogCancel = this.app.stringManager().translateString("[DIALOG_BUTTON_CANCEL]");
                            };
                        };
                    };
                    break;
                case DIALOG_MAINMENU:
                    this.mDialogHeader = this.app.stringManager().translateString("[RETURN_MAIN_HEADER]");
                    this.mDialogMessage = this.app.stringManager().translateString("[RETURN_MAIN_BODY]");
                    this.mDialogOk = this.app.stringManager().translateString("[LEAVE_BUTTON]");
                    this.mDialogCancel = this.app.stringManager().translateString("[DIALOG_BUTTON_CANCEL]");
                    break;
            };
        }
         public void  draw (Graphics2D g ){
            g.drawImage(this.mBackdropImage, 120, 50);
            int aMusicOffset ;
            int aSoundFXOffset ;
            int a3DAccelOffset ;
            int aFullScreenOffset ;
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18 );
            aFont.setColor(1, (107 / 0xFF), (109 / 0xFF), (145 / 0xFF));
            String aString =this.app.stringManager().translateString("[SOUND_CHECKBOX]");
            g.pushState();
            g.setFont(aFont);
            g.drawString(aString, 200, 135);
            aString = this.app.stringManager().translateString("[MUSIC_CHECKBOX]");
            g.drawString(aString, 200, 165);
            g.popState();
        }
        public void  buttonDownTick (int id ){
        }

        private final int Menu_Upsell =101;
        public String mDialogOk ;
        public boolean mIsSound ;

        public ImageInst  MakeButtonImage (String theText ,FontInst theFont ,ImageInst theImage ){
            int w =theImage.width() ;
            int h =theImage.height() ;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            bufferG.drawImage(theImage, 0, 0);
            while (theFont.stringImageWidth(theText) > theImage.width())
            {
                theFont.scale((theFont.scale() - 0.1));
            };
            bufferG.setFont(theFont);
            int offsetX =(int)((theImage.width() -theFont.stringImageWidth(theText ))/2);
            int offsetY =(int)(((theImage.height() -theFont.getHeight ())-5)/2);
            bufferG.drawString(theText, offsetX, offsetY);
            return (aBufferedImage);
        }
         public void  onKeyUp (int keyCode ){
            if (keyCode == 32)
            {
                this.mBoard.onKeyUp(keyCode);
            };
        }
         public void  update (){
        }

        public CheckboxWidget mSoundCheckBox ;

        public boolean mIsMusic ;
        private final int Menu_SoundCheckBox =104;

        public void  checkboxChecked (int id ,boolean checked ){
            switch (id)
            {
                case Menu_SoundCheckBox:
                    this.mIsSound = checked;
                    break;
                case Menu_MusicCheckBox:
                    this.mIsMusic = checked;
                    break;
            };
        }
        public void  buttonRelease (int id ){
            switch (id)
            {
                case Menu_BackToGame:
                    this.mBoard.mPaused = false;
                    this.app.stateManager().changeState(PVZApp.STATE_PLAY_LEVEL);
                    break;
                case Menu_BackToMain:
                    if (this.app.mSeedChooserScreen != null)
                    {
                        this.app.stateManager().popState();
                        this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                    }
                    else
                    {
                        this.PickDialogType(DIALOG_MAINMENU);
                        this.mDialogBox.mDialogType = DIALOG_MAINMENU;
                        this.mDialogBox.resize((0xFF - (63 * 2)), (130 - (36 * 2)), (153 + (63 * 2)), (184 + (36 * 2)));
                        this.mDialogBox.InitializeDialogBox(this.mDialogHeader, this.mDialogMessage, this.mDialogOk, this.mDialogCancel, 2, 2);
                        this.mDialogBox.setVisible(true);
                        this.mRestartButton.setDisabled(true);
                        this.mBackToGameButton.setDisabled(true);
                        this.mBackToMainButton.setDisabled(true);
                        this.mUpsellButton.setDisabled(true);
                    };
                    break;
                case Menu_Restart:
                    this.PickDialogType(DIALOG_RESTART);
                    this.mDialogBox.mDialogType = DIALOG_RESTART;
                    this.mDialogBox.resize((0xFF - (63 * 2)), (130 - (36 * 2)), (153 + (63 * 2)), (184 + (36 * 2)));
                    this.mDialogBox.InitializeDialogBox(this.mDialogHeader, this.mDialogMessage, this.mDialogOk, this.mDialogCancel, 2, 2);
                    this.mDialogBox.setVisible(true);
                    this.mRestartButton.setDisabled(true);
                    this.mBackToGameButton.setDisabled(true);
                    this.mBackToMainButton.setDisabled(true);
                    this.mUpsellButton.setDisabled(true);
                    break;
                case Menu_Upsell:
                    if (!this.app.adAPI.enabled())                    {
                    };
                    break;
            };
        }

        public Board mBoard ;
        private final int Menu_BackToMain =102;
        public DialogBox mDialogBox ;
        public String mDialogCancel ;

        public void  DoUpsell (){
        }

        public  OptionsDialog (PVZApp app ,Board theBoard ){
            super();
            FontInst font ;
            String aString ;
            this.app = app;
            this.mBoard = theBoard;
            this.mDialogBox = new DialogBox(app, this.mBoard);
            this.mDialogBox.setVisible(false);
            this.mOptionsButton = app.imageManager().getImageInst(PVZImages.IMAGE_OPTIONS_STANDARDBUTTON);
            this.mReturnToGameButton = app.imageManager().getImageInst(PVZImages.IMAGE_OPTIONS_BACKTOGAMEBUTTON0);
            font = app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36GREENINSET);
            font.scale(0.4);
            FontInst hiliteFont =app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36BRIGHTGREENINSET );
            hiliteFont.scale(0.4);
            Point aPoint =new Point(1,1);
            this.mRestartButton = new ImageButtonWidget(this.Menu_Restart, this);
            aString = app.stringManager().translateString("[RESTART_LEVEL]");
            this.mRestartButton.mUpImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mRestartButton.mOverImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mRestartButton.mDownImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mRestartButton.mDownOffset = aPoint;
            this.mRestartButton.mDisabledImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mRestartButton.doFinger = true;
            this.mRestartButton.visible = true;
            this.mRestartButton.setDisabled(false);
            this.mRestartButton.resize(182, 200, 156, 31);
            font.scale(0.4);
            hiliteFont.scale(0.4);
            this.mUpsellButton = new ImageButtonWidget(this.Menu_Upsell, this);
            aString = app.stringManager().translateString("[TRY_FULL_VERSION_BUTTON]");
            this.mUpsellButton.mUpImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mUpsellButton.mOverImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mUpsellButton.mDownImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mUpsellButton.mDownOffset = aPoint;
            this.mUpsellButton.mDisabledImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mUpsellButton.doFinger = true;
            this.mUpsellButton.visible = true;
            this.mUpsellButton.setDisabled(false);
            this.mUpsellButton.resize(182, 232, 156, 31);
            font.scale(0.4);
            hiliteFont.scale(0.4);
            this.mBackToMainButton = new ImageButtonWidget(this.Menu_BackToMain, this);
            aString = app.stringManager().translateString("[MAIN_MENU_BUTTON]");
            this.mBackToMainButton.mUpImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mBackToMainButton.mOverImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mBackToMainButton.mDownImage = this.MakeButtonImage(aString, hiliteFont, this.mOptionsButton);
            this.mBackToMainButton.mDownOffset = aPoint;
            this.mBackToMainButton.mDisabledImage = this.MakeButtonImage(aString, font, this.mOptionsButton);
            this.mBackToMainButton.doFinger = true;
            this.mBackToMainButton.visible = true;
            this.mBackToMainButton.setDisabled(false);
            this.mBackToMainButton.resize(182, 264, 156, 31);
            this.mBackToGameButton = new ImageButtonWidget(this.Menu_BackToGame, this);
            aString = app.stringManager().translateString("[BACK_TO_GAME]");
            font.scale(0.6);
            hiliteFont.scale(0.6);
            this.mBackToGameButton.mUpImage = this.MakeButtonImage(aString, font, this.mReturnToGameButton);
            this.mBackToGameButton.mOverImage = this.MakeButtonImage(aString, hiliteFont, this.mReturnToGameButton);
            this.mBackToGameButton.mDownImage = this.MakeButtonImage(aString, hiliteFont, this.mReturnToGameButton);
            this.mBackToGameButton.mDownOffset = aPoint;
            this.mBackToGameButton.mDisabledImage = this.MakeButtonImage(aString, font, this.mReturnToGameButton);
            this.mBackToGameButton.doFinger = true;
            this.mBackToGameButton.visible = true;
            this.mBackToGameButton.setDisabled(false);
            this.mBackToGameButton.resize(132, 305, 243, 68);
            if (app.mSeedChooserScreen != null)
            {
                this.mRestartButton.visible = false;
            };
            this.mBackdropImage = app.imageManager().getImageInst(PVZImages.IMAGE_OPTIONS_MENUBACK);
            ImageInst emptyCheckbox =app.imageManager().getImageInst(PVZImages.IMAGE_OPTIONS_CHECKBOX0 );
            ImageInst checkbox =app.imageManager().getImageInst(PVZImages.IMAGE_OPTIONS_CHECKBOX1 );
            this.mSoundCheckBox = new CheckboxWidget(this.Menu_SoundCheckBox, checkbox, emptyCheckbox, this);
            this.mSoundCheckBox.resize(320, 140, 28, 26);
            this.mIsSound = false;
            this.mIsMusic = false;
            if (!app.soundManager().isMuted())
            {
                this.mIsSound = true;
                this.mSoundCheckBox.setChecked(true, false);
            };
            this.mMusicCheckBox = new CheckboxWidget(this.Menu_MusicCheckBox, checkbox, emptyCheckbox, this);
            if (!app.musicManager().isMuted())
            {
                this.mIsMusic = true;
                this.mMusicCheckBox.setChecked(true, false);
            };
            this.mMusicCheckBox.resize(320, 170, 28, 26);
        }
    }


