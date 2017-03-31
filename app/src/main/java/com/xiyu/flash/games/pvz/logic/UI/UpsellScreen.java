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
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.resources.PVZImages;
//import flash.net.URLRequest;
//import flash.net.navigateToURL;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;

    public class UpsellScreen extends CWidget implements IButtonListener {

        public static final int ADVENTURE =0;
        public static final int PUZZLE =1;
        public static final int MINIGAME =3;
        public static final int SURVIVAL =2;

        private StringRenderable mBackToGameButtonText ;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }
        public void  buttonMouseEnter (int id ){
        }

        public CButtonWidget mUpsellButton ;
        private StringRenderable mTitleDescription ;
        private StringRenderable mHeader1 ;
        private StringRenderable mHeader2 ;
        private ImageInst mBackgroundImage ;
        public PVZApp app ;
        private final int UpsellLink =100;
        public CButtonWidget mBackToGameButton ;

        public void  buttonDownTick (int id ){
        }

        private ImageInst mUpsellImage2 ;
        private ImageInst mUpsellImage ;
        private StringRenderable mHeader2Description ;
        private int mType ;

        public void  buttonPress (int id ){
        }
         public void  draw (Graphics2D g ){
            g.drawImage(this.mBackgroundImage, 0, 0);
        }
         public void  update (){
        }

        private StringRenderable mUpsellButtonText ;
        private StringRenderable mTitleString ;

        public void  Survival (){
            this.mBackgroundImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_UPSELL3);
        }
        public void  buttonMouseLeave (int id ){
        }

        private StringRenderable mHeader1Description ;

        public void  Minigame (){
            this.mBackgroundImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_UPSELL2);
        }

//        private XML upsellXML ;

        public void  CloseUpsell (){
            if (this.mType == MINIGAME)
            {
                this.app.stateManager().popState();
            }
            else
            {
                this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
            };
        }

  //      private URLRequest mURLRequest ;

        public void  Adventure (){
            this.mBackgroundImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_UPSELL1);
        }

        private final int BackToGame =101;

        public void  buttonRelease (int id ){
            switch (id)
            {
                case UpsellLink:
/*
                	this.app.adAPI.CustomEvent(this.upsellXML, this.CloseUpsell);
                    if (!this.app.adAPI.enabled)
                    {
                        navigateToURL(this.mURLRequest);
                    };
*/                    
                    break;
                case BackToGame:
                    if (this.mType == MINIGAME)
                    {
                        this.app.stateManager().popState();
                    }
                    else
                    {
                        this.app.adAPI.ScoreSubmit();
                        this.app.adAPI.GameEnd();
                        this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                    };
                    break;
            };
        }
        public void  Puzzle (){
            this.mBackgroundImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_UPSELL4);
        }

        public  UpsellScreen (PVZApp app ,int theType ){
            super();
            /*
            this.upsellXML = new XML("<data>DeluxeDownload</data>");
            this.mURLRequest = new URLRequest(app.mUpsellLink);
            */
            this.app = app;
            this.mBackToGameButton = new CButtonWidget(this.BackToGame, this);
            this.mBackToGameButton.setDisabled(false);
            this.mBackToGameButton.visible = true;
            this.mBackToGameButton.label(app.stringManager().translateString("[UPSELL_RETURN_TO_GAME_BUTTON]"));
            this.mBackToGameButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB((25 / 0xFF), (30 / 0xFF), (200 / 0xFF)));
            this.mBackToGameButton.setColor(CButtonWidget.COLOR_LABEL_HILITE, Color.RGB((100 / 0xFF), (150 / 0xFF), (240 / 0xFF)));
            FontInst font4 =app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            font4.scale(0.9);
            this.mBackToGameButton.font(font4);
            this.mBackToGameButton.resize(287, 358, 152, 37);
            this.mUpsellButton = new CButtonWidget(this.UpsellLink, this);
            this.mUpsellButton.setDisabled(false);
            this.mUpsellButton.visible = true;
            this.mUpsellButton.label(app.stringManager().translateString("[UPSELL_BUY_BUTTON]"));
            this.mUpsellButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB((25 / 0xFF), (30 / 0xFF), (200 / 0xFF)));
            this.mUpsellButton.setColor(CButtonWidget.COLOR_LABEL_HILITE, Color.RGB((100 / 0xFF), (150 / 0xFF), (240 / 0xFF)));
            FontInst font5 =app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            font5.scale(0.9);
            this.mUpsellButton.font(font5);
            this.mUpsellButton.resize(99, 358, 153, 37);
            switch (theType)
            {
                case ADVENTURE:
                    this.Adventure();
                    break;
                case PUZZLE:
                    this.Puzzle();
                    break;
                case SURVIVAL:
                    this.Survival();
                    break;
                case MINIGAME:
                    this.Minigame();
                    break;
            };
            this.mType = theType;
        }
    }


