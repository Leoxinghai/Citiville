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
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.PVZApp;
//import flash.geom.Point;
//import flash.net.URLRequest;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.SeedChooserScreen;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;

    public class DialogBox extends CWidget implements IButtonListener {

        public static final int DIALOG_LOCKED =6;
        public static final int DIALOG_UPSELL =5;
        public static final int DIALOG_REPICK =4;
        public static final int DIALOG_MAINMENU =1;
        public static final int DIALOG_RETRY =3;
        public static final int DIALOG_RESTART =0;
        public static final int DIALOG_GAMEOVER =2;

        public ImageInst mDialogBoxImage ;

        public ImageInst  MakeDownButtonImage (String theText ){
            int size =2;
            if ((((this.mDialogType == DIALOG_GAMEOVER)) || ((this.mDialogType == DIALOG_UPSELL))))
            {
                size = 4;
            }
            else
            {
                if ((((this.mDialogType == DIALOG_RETRY)) || ((this.mDialogType == DIALOG_LOCKED))))
                {
                    size = 6;
                };
            };
            int w =(48+(31*size ));
            int h =31;
            int x =0;
            int y =0;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            ImageInst anImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_LEFT );
            bufferG.drawImage(anImage, x, y);
            int i =0;
            while (i < size)
            {
                x = (x + anImage.width());
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_MIDDLE);
                bufferG.drawImage(anImage, x, y);
                i++;
            };
            x = (x + anImage.width());
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_RIGHT);
            bufferG.drawImage(anImage, x, y);
            FontInst font =this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36BRIGHTGREENINSET );
            font.scale(0.4);
            int offsetX =(int)((aBufferedImage.width() -font.stringImageWidth(theText ))/2);
            int offsetY =(int)(((aBufferedImage.height() -font.getHeight ())-5)/2);
            bufferG.setFont(font);
            bufferG.drawString(theText, offsetX, offsetY);
            return (aBufferedImage);
        }
        public void  buttonDownTick (int id ){
        }

        private final int Menu_Ok =100;

        public ImageInst  MakeUpButtonImage (String theText ){
            int size =2;
            if ((((this.mDialogType == DIALOG_GAMEOVER)) || ((this.mDialogType == DIALOG_UPSELL))))
            {
                size = 4;
            }
            else
            {
                if ((((this.mDialogType == DIALOG_RETRY)) || ((this.mDialogType == DIALOG_LOCKED))))
                {
                    size = 6;
                };
            };
            int w =(48+(31*size ));
            int h =31;
            int x =0;
            int y =0;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            ImageInst anImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_LEFT );
            bufferG.drawImage(anImage, x, y);
            int i =0;
            while (i < size)
            {
                x = (x + anImage.width());
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_MIDDLE);
                bufferG.drawImage(anImage, x, y);
                i++;
            };
            x = (x + anImage.width());
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_RIGHT);
            bufferG.drawImage(anImage, x, y);
            FontInst font =this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36GREENINSET );
            font.scale(0.4);
            int offsetX =(int)((aBufferedImage.width() -font.stringImageWidth(theText ))/2);
            int offsetY =(int)(((aBufferedImage.height() -font.getHeight ())-5)/2);
            bufferG.setFont(font);
            bufferG.drawString(theText, offsetX, offsetY);
            return (aBufferedImage);
        }
         public void  update (){
        }
        public void  KillAll (){
            this.mOkButton.setVisible(false);
            this.mCancelButton.setVisible(false);
            this.setVisible(false);
        }

        public String mCancelText ;

         public void  draw (Graphics2D g ){
            if (this.mDialogBoxImage != null)
            {
                g.drawImage(this.mDialogBoxImage, 0, 0);
            };
        }

        public ImageButtonWidget mCancelButton ;
        private static final int Menu_Cancel =101;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }
        public void  buttonMouseEnter (int id ){
        }
        public void  GameContinue (Object obj ){
            this.app.soundManager().resumeAll();
            this.app.musicManager().resumeMusic();
            this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
        }

        public PVZApp app ;

        public void  InitializeDialogBox (String theHeader ,String theMessage ,String theOkText ,String theCancelText ,int theWidth ,int theHeight ){
            this.mHeader = theHeader;
            this.mMessage = theMessage;
            this.mOkText = theOkText;
            this.mCancelText = theCancelText;
            this.mDialogBoxImage = this.MakeDialogBoxImage(theWidth, theHeight);
            Point aPoint =new Point(1,1);
            this.mOkButton.mUpImage = this.MakeUpButtonImage(this.mOkText);
            this.mOkButton.mOverImage = this.MakeDownButtonImage(this.mOkText);
            this.mOkButton.mDownImage = this.MakeDownButtonImage(this.mOkText);
            this.mOkButton.mDownOffset = aPoint;
            this.mOkButton.mDisabledImage = this.MakeUpButtonImage(this.mOkText);
            this.mOkButton.doFinger = true;
            this.mOkButton.visible = true;
            this.mOkButton.setDisabled(false);
            if ((((this.mDialogType == DIALOG_GAMEOVER)) || ((this.mDialogType == DIALOG_UPSELL))))
            {
                this.mOkButton.resize((this.x + 20), ((this.y + 130) + (theHeight * 36)), 172, 31);
            }
            else
            {
                if ((((this.mDialogType == DIALOG_RETRY)) || ((this.mDialogType == DIALOG_LOCKED))))
                {
                    this.mOkButton.resize((this.x + 80), ((this.y + 130) + (theHeight * 36)), 234, 31);
                }
                else
                {
                    this.mOkButton.resize((this.x + 20), ((this.y + 130) + (theHeight * 36)), 110, 31);
                };
            };
            if ((((((((this.mDialogType == DIALOG_GAMEOVER)) || ((this.mDialogType == DIALOG_RESTART)))) || ((this.mDialogType == DIALOG_UPSELL)))) || ((this.mDialogType == DIALOG_MAINMENU))))
            {
                this.mCancelButton.mUpImage = this.MakeUpButtonImage(this.mCancelText);
                this.mCancelButton.mOverImage = this.MakeDownButtonImage(this.mCancelText);
                this.mCancelButton.mDownImage = this.MakeDownButtonImage(this.mCancelText);
                this.mCancelButton.mDownOffset = aPoint;
                this.mCancelButton.mDisabledImage = this.MakeUpButtonImage(this.mCancelText);
                this.mCancelButton.doFinger = true;
                this.mCancelButton.visible = true;
                this.mCancelButton.setDisabled(false);
                this.mCancelButton.resize(((this.x + 15) + (theWidth * 63)), ((this.y + 130) + (theHeight * 36)), 110, 31);
                if (this.mDialogType == DIALOG_UPSELL)
                {
                    this.mCancelButton.resize(((this.x - 50) + (theWidth * 63)), ((this.y + 130) + (theHeight * 36)), 172, 31);
                };
            };
        }
        public void  KillButtons (){
            this.mOkButton.setVisible(false);
            this.mCancelButton.setVisible(false);
        }
        public void  buttonMouseLeave (int id ){
        }

        public String mHeader ;
        public int mDialogType ;

        public void  buttonPress (int id ){
        }
        public void  buttonRelease (int id ){
            switch (id)
            {
                case Menu_Ok:
                    if (this.mDialogType == DIALOG_MAINMENU)
                    {
                        this.app.adAPI.ScoreSubmit();
                        this.app.adAPI.GameEnd();
                        this.app.musicManager().playMusic(PVZMusic.CRAZY_DAVE,true,0);
                        this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                    }
                    else
                    {
                        if ((((this.mDialogType == DIALOG_GAMEOVER)) || ((this.mDialogType == DIALOG_RETRY))))
                        {
                            if (this.app.adAPI.enabled())
                            {
                                this.app.adAPI.ScoreSubmit();
                                this.app.adAPI.GameEnd();
                                this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                            };
                            if (this.app.mUpsellOn)
                            {
                                this.app.stateManager().changeState(PVZApp.STATE_UPSELL_SCREEN);
                            }
                            else
                            {
                                this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                            };
                        }
                        else
                        {
                            if (this.mDialogType == DIALOG_RESTART)
                            {
                                if (this.app.adAPI.enabled())
                                {
                                    this.app.soundManager().pauseAll();
                                    this.app.musicManager().pauseMusic();
                                    this.app.adAPI.ScoreSubmit();
                                    this.app.mTotalZombiesKilled = 0;
                                    this.app.adAPI.setScore(this.app.mTotalZombiesKilled);
                                    this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
                                }
                                else
                                {
                                    this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
                                };
                            }
                            else
                            {
                                if (this.mDialogType == DIALOG_REPICK)
                                {
                                    this.app.mSeedChooserScreen.mChooseState = SeedChooserScreen.CHOOSE_LEAVE;
                                    this.app.mSeedChooserScreen.KillDialogBox();
                                    this.app.mSeedChooserScreen.CloseSeedChooser();
                                }
                                else
                                {
                                    if (this.mDialogType == DIALOG_LOCKED)
                                    {
                                        this.KillAll();
                                    }
                                    else
                                    {
                                        if (this.mDialogType == DIALOG_UPSELL)
                                        {
                                        };
                                    };
                                };
                            };
                        };
                    };
                    break;
                case Menu_Cancel:
                    if (this.mDialogType == DIALOG_REPICK)
                    {
                        this.app.mSeedChooserScreen.KillDialogBox();
                    }
                    else
                    {
                        if ((((this.mDialogType == DIALOG_RESTART)) || ((this.mDialogType == DIALOG_MAINMENU))))
                        {
                            this.app.mOptionsMenu.KillDialogBox();
                        }
                        else
                        {
                            if (this.mDialogType == DIALOG_UPSELL)
                            {
                                this.KillAll();
                            };
                        };
                    };
                    break;
            };
        }

        public Board mBoard ;
        public ImageButtonWidget mOkButton ;
        public String mMessage ;
        public String mOkText ;

        public ImageInst  MakeDialogBoxImage (int width,int height){
            ImageInst anImage ;
            int j =0;
            if (width <= 0)
            {
                width = 1;
            };
            if (height <= 0)
            {
                height = 1;
            };
            int w =(153+(63*width ));
            int h =(184+(36*height ));
            int x =0;
            int y =50;
            int i =0;
            int headerX ;
            int headerY =50;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_TOPLEFT);
            bufferG.drawImage(anImage, (x + 1), y);
            i = 0;
            while (i < width)
            {
                x = (x + (anImage.width() - 1));
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_TOPMIDDLE);
                bufferG.drawImage(anImage, x, y);
                i++;
            };
            ImageInst headerImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_HEADER );
            headerX = (int)((((headerImage.width() / 2) - ((anImage.width() / 2) + anImage.x())) + 5) + (31 * (width - 1)));
            headerY = (int)(headerY + (anImage.y() - 30));
            x = (x + anImage.width());
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_TOPRIGHT);
            bufferG.drawImage(anImage, (x - 3), y);
            i = 0;
            while (i < height)
            {
                x = 0;
                y = (y + (anImage.height() - 1));
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_CENTERLEFT);
                bufferG.drawImage(anImage, x, y);
                j = 0;
                while (j < width)
                {
                    x = (x + (anImage.width() - 1));
                    anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_CENTERMIDDLE);
                    bufferG.drawImage(anImage, x, y);
                    j++;
                };
                x = (x + anImage.width());
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_CENTERRIGHT);
                bufferG.drawImage(anImage, (x - 3), y);
                i++;
            };
            x = 0;
            y = (y + (anImage.height() - 2));
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_BOTTOMLEFT);
            bufferG.drawImage(anImage, (x + 1), (y - 1));
            i = 0;
            while (i < width)
            {
                x = (x + (anImage.width() - 1));
                anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_BOTTOMMIDDLE);
                bufferG.drawImage(anImage, x, (y - 1));
                i++;
            };
            x = (x + anImage.width());
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_DIALOG_BOTTOMRIGHT);
            bufferG.drawImage(anImage, (x - 3), (y - 1));
            bufferG.drawImage(headerImage, headerX, headerY);
            StringRenderable aStringRenderable =new StringRenderable(0);
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT24 );
            aFont.setColor(1, (224 / 0xFF), (187 / 0xFF), (98 / 0xFF));
            if (this.mDialogType == DIALOG_GAMEOVER)
            {
                aFont.scale(0.85);
                aStringRenderable.text(this.mHeader);
                aStringRenderable.font(aFont);
                aStringRenderable.setBounds(20, 70, (102 + (width * 63)), 75);
                aStringRenderable.justification(StringRenderable.JUSTIFY_CENTER);
                aStringRenderable.draw(bufferG);
            }
            else
            {
                if (this.mDialogType == DIALOG_RETRY)
                {
                    aFont.scale(1);
                    aStringRenderable.text(this.mHeader);
                    aStringRenderable.font(aFont);
                    aStringRenderable.setBounds(20, 70, (102 + (width * 63)), 40);
                    aStringRenderable.justification(StringRenderable.JUSTIFY_CENTER);
                    aStringRenderable.draw(bufferG);
                }
                else
                {
                    aFont.scale(0.6);
                    aStringRenderable.text(this.mHeader);
                    aStringRenderable.font(aFont);
                    aStringRenderable.setBounds(20, 70, (102 + (width * 63)), 30);
                    aStringRenderable.justification(StringRenderable.JUSTIFY_CENTER);
                    aStringRenderable.draw(bufferG);
                };
            };
            aFont = this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT15);
            aFont.scale(0.85);
            aFont.setColor(1, (224 / 0xFF), (187 / 0xFF), (98 / 0xFF));
            if (this.mMessage == "")
            {
                aStringRenderable.dead(true);
            }
            else
            {
                if (this.mDialogType == DIALOG_RETRY)
                {
                    aFont.scale(1);
                };
                aStringRenderable.text(this.mMessage);
                aStringRenderable.font(aFont);
                aStringRenderable.setBounds(22, 90, (97 + (width * 63)), (29 + (height * 36)));
                aStringRenderable.draw(bufferG);
            };
            this.mDialogBoxImage = aBufferedImage;
            return (aBufferedImage);
        }

        public  DialogBox (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mOkButton = new ImageButtonWidget(this.Menu_Ok, this);
            this.mCancelButton = new ImageButtonWidget(this.Menu_Cancel, this);
        }
    }


