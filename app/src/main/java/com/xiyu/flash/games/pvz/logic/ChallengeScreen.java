package com.xiyu.flash.games.pvz.logic;
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
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;
import com.xiyu.flash.games.pvz.PVZApp;
//import flash.geom.Point;
import com.xiyu.flash.framework.graphics.Color;

    public class ChallengeScreen extends CWidget implements IButtonListener {

        public void  buttonMouseMove (int id ,int x ,int y ){
        }
        public void  buttonMouseEnter (int id ){
        }

        public ImageButtonWidget mStartButton ;
        private String mButtonText ;

         public void  update (){
        }
        public void  buttonDownTick (int id ){
        }

        private final int StartGame =100;
        private StringRenderable mUpsellText ;

        public ImageInst  MakeDownButtonImage (){
            int w =80;
            int h =81;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            ImageInst anImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_CHALLENGE_WINDOW_HIGHLIGHT );
            bufferG.drawImage(anImage, 0, 0);
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_SURVIVAL_THUMBNAILS);
            StringRenderable aStringRenderable =new StringRenderable(0);
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            aFont.setColor(1, (250 / 0xFF), (40 / 0xFF), (40 / 0xFF));
            aFont.scale(0.6);
            aStringRenderable.text(this.mButtonText);
            aStringRenderable.font(aFont);
            aStringRenderable.setBounds(7, 50, 65, 23);
            aStringRenderable.draw(bufferG);
            return (aBufferedImage);
        }

        private String mTitleString ;
        public CButtonWidget mBackToMenuButton ;
        public PVZApp app ;

        public ImageInst  MakeUpButtonImage (){
            int w =80;
            int h =81;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            ImageInst anImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_CHALLENGE_WINDOW );
            bufferG.drawImage(anImage, 0, 0);
            anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_SURVIVAL_THUMBNAILS);
            StringRenderable aStringRenderable =new StringRenderable(0);
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            aFont.setColor(1, (42 / 0xFF), (42 / 0xFF), (90 / 0xFF));
            aFont.scale(0.6);
            aStringRenderable.text(this.mButtonText);
            aStringRenderable.font(aFont);
            aStringRenderable.setBounds(7, 50, 65, 23);
            aStringRenderable.draw(bufferG);
            return (aBufferedImage);
        }
        public void  buttonMouseLeave (int id ){
        }
         public void  draw (Graphics2D g ){
            int aPosX ;
            int aPosY ;
            int aRow ;
            int aCol ;
            g.scale(0.3, 0.3);
            g.drawImage(this.mBackgroundImage, 0, 0);
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR28 );
            aFont.scale(0.7);
            aFont.setColor(1, (220 / 0xFF), (220 / 0xFF), (220 / 0xFF));
            g.setFont(aFont);
            g.drawString(this.mTitleString, 230, 12);
            int i =1;
            ImageInst aBlank =this.app.imageManager().getImageInst(PVZImages.IMAGE_CHALLENGE_BLANK );
            if (this.app.IsScaryPotterLevel())
            {
                i = 1;
                while (i < 20)
                {
                    aRow = (i / 5);
                    aCol = (i % 5);
                    aPosX = (23 + (aCol * 105));
                    aPosY = (62 + (aRow * 81));
                    aPosX =0;
                    aPosY=0;
                    g.drawImage(aBlank, aPosX, aPosY);
                    i++;
                };
            }
            else
            {
                if (this.app.IsSurvivalMode())
                {
                    i = 1;
                    while (i < 10)
                    {
                        aRow = (i / 5);
                        aCol = (i % 5);
                        aPosX = (23 + (aCol * 105));
                        aPosY = (100 + (aRow * 100));
                        g.drawImage(aBlank, aPosX, aPosY);
                        i++;
                    };
                    aPosX = (23 + (2 * 105));
                    aPosY = (100 + (2 * 100));
                    g.drawImage(aBlank, aPosX, aPosY);
                };
            };
            this.mUpsellText.draw(g);
            g.drawImage(this.mBackToMenuImage, 20, 361);
        }

        private ImageInst mBackgroundImage ;
        private ImageInst mBackToMenuImage ;
        private final int BackToMenu =101;

        public void  buttonPress (int id ){
        }
        public void  buttonRelease (int id ){
            switch (id)
            {
                case StartGame:
                    this.app.stateManager().popState();
                    this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
                    break;
                case BackToMenu:
                    this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                    break;
            };
        }

        public Board mBoard ;

        public  ChallengeScreen (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mStartButton = new ImageButtonWidget(this.StartGame, this);
            if (app.IsScaryPotterLevel())
            {
                this.mButtonText = app.stringManager().translateString("[SCARY_POTTER_ENDLESS]");
                this.mStartButton.resize(20, 62, 80, 81);
            }
            else
            {
                if (app.IsSurvivalMode())
                {
                    this.mButtonText = app.stringManager().translateString("[SURVIVAL_POOL_ENDLESS]");
                    this.mStartButton.resize(20, 100, 80, 81);
                };
            };
            this.mStartButton.mUpImage = this.MakeUpButtonImage();
            this.mStartButton.mDownImage = this.MakeDownButtonImage();
            this.mStartButton.mOverImage = this.MakeDownButtonImage();
            this.mStartButton.mDownOffset = new Point(1, 1);
            this.mStartButton.mDisabledImage = this.MakeUpButtonImage();
            this.mBackToMenuButton = new CButtonWidget(this.BackToMenu, this);
            this.mBackToMenuButton.setDisabled(false);
            this.mBackToMenuButton.visible = true;
            this.mBackToMenuButton.label(app.stringManager().translateString("[BACK_TO_MENU]"));
            this.mBackToMenuButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB((218 / 0xFF), (184 / 0xFF), (33 / 0xFF)));
            FontInst font =app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            font.scale(0.6);
            this.mBackToMenuButton.font(font);
            this.mBackToMenuButton.resize(20, 360, 75, 18);
            this.mBackToMenuImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON2);
            this.mBackgroundImage = app.imageManager().getImageInst(PVZImages.IMAGE_CHALLENGE_BACKGROUND);
            font = app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36GREENINSET);
            font.scale(0.8);
            font.setColor(1, (90 / 0xFF), (40 / 0xFF), (30 / 0xFF));
            this.mUpsellText = new StringRenderable(0);
            this.mUpsellText.font(font);
            if (app.IsScaryPotterLevel())
            {
                this.mUpsellText.setBounds(20, 150, 500, 200);
                this.mUpsellText.text(app.stringManager().translateString("[PUZZLE_UPSELL_MESSAGE]"));
                this.mTitleString = app.stringManager().translateString("[SCARY_POTTER]");
            }
            else
            {
                if (app.IsSurvivalMode())
                {
                    this.mUpsellText.setBounds(70, 150, 420, 200);
                    this.mUpsellText.text(app.stringManager().translateString("[SURVIVAL_UPSELL_MESSAGE]"));
                    this.mTitleString = app.stringManager().translateString("[PICK_AREA]");
                };
            };
            this.mTitleString = "title";
        }
    }

