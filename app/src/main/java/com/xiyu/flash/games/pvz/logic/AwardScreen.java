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
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.render.RenderManager;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
//import flash.geom.Point;

    public class AwardScreen extends CWidget implements IButtonListener {

        public static final int SEED_PUFFSHROOM =8;
        private static final int SEED_NONE =-1;
        private static final int SEED_CHERRYBOMB =2;
        private static final int SEED_SQUASH =4;
        private static final int SEED_PEASHOOTER =0;
        private static final int SEED_CHOMPER =6;
        public static final int SEED_SUNSHROOM =9;
        private static final int SEED_REPEATER =7;
        private static final int SEED_WALLNUT =3;
        public static final int SEED_FUMESHROOM =10;
        public static final int SEED_GRAVEBUSTER =11;
        private static final int SEED_SUNFLOWER =1;
        private static final int SEED_SNOWPEA =5;

        public boolean mFadedIn ;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        private ImageInst mZombieNote ;
        public ImageButtonWidget mStartButton ;

        public String  GetPlantDefinition (int theSeedType ){
            String aReanimType ="";
            switch (theSeedType)
            {
                case SEED_PEASHOOTER:
                    aReanimType = PVZReanims.REANIM_PEASHOOTERSINGLE;
                    break;
                case SEED_SUNFLOWER:
                    aReanimType = PVZReanims.REANIM_SUNFLOWER;
                    break;
                case SEED_CHERRYBOMB:
                    aReanimType = PVZReanims.REANIM_CHERRYBOMB;
                    break;
                case SEED_CHOMPER:
                    aReanimType = PVZReanims.REANIM_CHOMPER;
                    break;
                case SEED_SNOWPEA:
                    aReanimType = PVZReanims.REANIM_SNOWPEA;
                    break;
                case SEED_SQUASH:
                    aReanimType = PVZReanims.REANIM_SQUASH;
                    break;
                case SEED_REPEATER:
                    aReanimType = PVZReanims.REANIM_PEASHOOTER;
                    break;
                case SEED_WALLNUT:
                    aReanimType = PVZReanims.REANIM_WALLNUT;
                    break;
                case SEED_PUFFSHROOM:
                    aReanimType = PVZReanims.REANIM_PUFFSHROOM;
                    break;
                case SEED_SUNSHROOM:
                    aReanimType = PVZReanims.REANIM_SUNSHROOM;
                    break;
                case SEED_FUMESHROOM:
                    aReanimType = PVZReanims.REANIM_FUMESHROOM;
                    break;
                case SEED_GRAVEBUSTER:
                    aReanimType = PVZReanims.REANIM_GRAVEBUSTER;
                    break;
            };
            return (aReanimType);
        }
        public void  buttonMouseEnter (int id ){
        }

        private int mFadeInCounter ;
        private ImageInst mButtonImage ;
        private PVZApp app ;

        public String  GetSeedToolTip (int theType ){
            String aName ="";
            switch (theType)
            {
                case 0:
                    aName = this.app.stringManager().translateString("[PEASHOOTER_TOOLTIP]");
                    break;
                case 1:
                    aName = this.app.stringManager().translateString("[SUNFLOWER_TOOLTIP]");
                    break;
                case 2:
                    aName = this.app.stringManager().translateString("[CHERRY_BOMB_TOOLTIP]");
                    break;
                case 3:
                    aName = this.app.stringManager().translateString("[WALL_NUT_TOOLTIP]");
                    break;
                case 4:
                    aName = this.app.stringManager().translateString("[SQUASH_TOOLTIP]");
                    break;
                case 5:
                    aName = this.app.stringManager().translateString("[SNOW_PEA_TOOLTIP]");
                    break;
                case 6:
                    aName = this.app.stringManager().translateString("[CHOMPER_TOOLTIP]");
                    break;
                case 7:
                    aName = this.app.stringManager().translateString("[REPEATER_TOOLTIP]");
                    break;
                case 8:
                    aName = this.app.stringManager().translateString("[PUFF_SHROOM_TOOLTIP]");
                    break;
                case 9:
                    aName = this.app.stringManager().translateString("[SUN_SHROOM_TOOLTIP]");
                    break;
                case 10:
                    aName = this.app.stringManager().translateString("[FUME_SHROOM_TOOLTIP]");
                    break;
                case 11:
                    aName = this.app.stringManager().translateString("[GRAVE_BUSTER_TOOLTIP]");
                    break;
            };
            return (aName);
        }

        private StringRenderable mDescriptionTextRenderable ;

        public void  DrawBottom (Graphics2D g ){
            g.drawImage(this.mBackground, 0, 0);
        }
        public int  GetCurrentPlantCost (int theSeedType ){
            int aCost ;
            switch (theSeedType)
            {
                case SEED_PEASHOOTER:
                    aCost = 100;
                    break;
                case SEED_SUNFLOWER:
                    aCost = 50;
                    break;
                case SEED_CHERRYBOMB:
                    aCost = 150;
                    break;
                case SEED_WALLNUT:
                    aCost = 50;
                    break;
                case SEED_CHOMPER:
                    aCost = 150;
                    break;
                case SEED_SQUASH:
                    aCost = 50;
                    break;
                case SEED_SNOWPEA:
                    aCost = 175;
                    break;
                case SEED_REPEATER:
                    aCost = 200;
                    break;
                case SEED_PUFFSHROOM:
                    aCost = 0;
                    break;
                case SEED_SUNSHROOM:
                    aCost = 25;
                    break;
                case SEED_FUMESHROOM:
                    aCost = 75;
                    break;
                case SEED_GRAVEBUSTER:
                    aCost = 75;
                    break;
                default:
                    aCost = 100;
            };
            return (aCost);
        }
        public void  buttonDownTick (int id ){
        }
        public int  GetAwardSeedForLevel (int aLevel ){
            int aArea =(((aLevel -1)/10)+1);
            int aSubArea =(((aLevel -1)% 10) + 1);
            int aSeeds =(((aArea -1)*8)+aSubArea );
            if (aSubArea >= 10)
            {
                aSeeds = (aSeeds - 2);
            }
            else
            {
                if (aSubArea >= 5)
                {
                    aSeeds--;
                };
            };
            if (aSeeds > 40)
            {
                aSeeds = 40;
            };
            return (aSeeds);
        }

        private StringRenderable mNameTextRenderable ;

        public void  buttonPress (int id ){
        }
         public void  draw (Graphics2D g ){
            int aLevel =this.app.mLevel ;
            if (aLevel == 4)
            {
                this.DrawBottom(g);
                g.drawImage(this.mShovelImage, 250, 115);
            }
            else
            {
                if (aLevel == 9)
                {
                    g.pushState();
                    g.scale(2, 2);
                    g.drawImage(this.mBackground, -472, -202);
                    g.popState();
                    g.drawImage(this.mZombieNote, 60, 55);
                }
                else
                {
                    this.DrawAwardSeed(g);
                };
            };
            int aAlpha =TodCommon.TodAnimateCurve(180,0,this.mFadeInCounter ,0xFF ,0,TodCommon.CURVE_LINEAR );
            int aColor =(aAlpha <<24);
            if (this.IsPaperNote())
            {
                aColor = (aColor | 0);
            }
            else
            {
                aColor = (aColor | 0xFFFFFF);
            };
            this.mRenderManager.draw(g);
            g.fillRect(0, 0, 540, 405, aColor);
            if (aAlpha <= 0)
            {
                this.mFadedIn = true;
            };
        }

        private StringRenderable mTitleTextRenderable ;

         public void  update (){
            this.mRenderManager.update();
            markDirty(null);
            if (this.mFadeInCounter > 0)
            {
                this.mFadeInCounter--;
            };
        }

        public RenderManager mRenderManager ;
        private ImageInst mShovelImage ;
        private ImageInst mZombieNote1 ;

        public ImageInst  MakeGlowButton (ImageInst theImage ,ImageInst theGlowImage ,FontInst theFont ,String theText ){
            int w =theImage.width() ;
            int h =theImage.height() ;
            ImageInst aBufferedImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =aBufferedImage.graphics();
            bufferG.drawImage(theImage, 0, 0);
            bufferG.drawImage(theGlowImage, 0, 0);
            while (theFont.stringImageWidth(theText) > theImage.width())
            {
                theFont.scale((theFont.scale() - 0.1));
            };
            bufferG.setFont(theFont);
            int offsetX =(int)((theImage.width() -theFont.stringImageWidth(theText ))/2);
            int offsetY =(int)((theImage.height() -theFont.getHeight ())/2);
            bufferG.drawString(theText, offsetX, offsetY);
            return (aBufferedImage);
        }

        private ImageInst mBufferedImage ;

        public void  SaveUserData (){
            this.app.setSaveData(this.app.mSaveObject);
        }
        private void  GameContinue (){
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().resumeMusic();
                this.app.soundManager().resumeAll();
            };
            this.app.mLevel++;
            this.app.setSaveData(this.app.mSaveObject);
            this.app.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
        }
        public void  DrawSeedPacket (Graphics2D g ,int theType ){
            ImageInst aSeedBackImg ;
            ImageInst aPlantImg =null;
            aSeedBackImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SEEDPACKET_LARGER);
            int w =aSeedBackImg.width() ;
            int h =aSeedBackImg.height() ;
            this.mBufferedImage = new ImageInst(new ImageData(new BitmapData(w, h, true, 0)));
            Graphics2D bufferG =this.mBufferedImage.graphics();
            bufferG.drawImage(aSeedBackImg, 0, 0);
            int aOffsetX =10;
            int aOffsetY =15;
            double aScale =1;
            switch (theType)
            {
                case SEED_PEASHOOTER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTERSINGLE);
                    aOffsetY = (aOffsetY + 4);
                    break;
                case SEED_SUNFLOWER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SUNFLOWER);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + 2);
                    break;
                case SEED_CHERRYBOMB:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_CHERRYBOMB);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + -1);
                    break;
                case SEED_SQUASH:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SQUASH);
                    aOffsetX = (aOffsetX + 0);
                    aOffsetY = (aOffsetY + -2);
                    break;
                case SEED_CHOMPER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_CHOMPER);
                    aOffsetX = (aOffsetX + -2);
                    aOffsetY = (aOffsetY + -3);
                    aScale = 0.85;
                    break;
                case SEED_SNOWPEA:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SNOWPEA);
                    aOffsetY = (aOffsetY + 3);
                    break;
                case SEED_REPEATER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTER);
                    aOffsetY = (aOffsetY + 5);
                    break;
                case SEED_PUFFSHROOM:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_PUFFSHROOM);
                    aOffsetY = (aOffsetY + 12);
                    aOffsetX = (aOffsetX + 9);
                    break;
                case SEED_SUNSHROOM:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SUNSHROOM);
                    aOffsetY = (aOffsetY + 13);
                    aOffsetX = (aOffsetX + 10);
                    break;
                case SEED_GRAVEBUSTER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_GRAVEDIGGER);
                    aOffsetY = (aOffsetY + 1);
                    aOffsetX = (aOffsetX + -5);
                    aScale = 0.85;
                    break;
                case SEED_FUMESHROOM:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_FUMESHROOM);
                    aOffsetX = (aOffsetX - 2);
                    aOffsetY = (aOffsetY + 1);
                    aScale = 0.95;
                    break;
                case SEED_WALLNUT:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                    aOffsetX = (aOffsetX + 3);
                    break;
            };
            bufferG.pushState();
            bufferG.scale(aScale, aScale);
            bufferG.drawImage(aPlantImg, aOffsetX, aOffsetY);
            bufferG.popState();
            FontInst theFont =this.app.fontManager().getFontInst(PVZFonts.FONT_PICO129 );
            theFont.scale(1.6);
            theFont.setColor(1, 0, 0, 0);
            int aCost =this.GetCurrentPlantCost(theType );
            int width =(int)theFont.stringWidth(""+aCost);
            int height =(int)theFont.getAscent ();
            int aTextOffsetX =(45-width );
            int aTextOffsetY =72;
            bufferG.setFont(theFont);
            bufferG.drawString(""+ aCost, aTextOffsetX, aTextOffsetY);
        }
        public ImageInst  MakeButton (ImageInst theImage ,FontInst theFont ,String theText ){
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
            int offsetY =(int)((theImage.height() -theFont.getHeight ())/2);
            bufferG.drawString(theText, offsetX, offsetY);
            return (aBufferedImage);
        }
        public void  buttonMouseLeave (int id ){
        }
        public void  DrawAwardSeed (Graphics2D g ){
            int aLevel =this.app.mLevel ;
            int awardSeed =this.GetAwardSeedForLevel(aLevel );
            this.DrawBottom(g);
            if (this.mBufferedImage == null)
            {
                this.DrawSeedPacket(g, awardSeed);
            };
            g.drawImage(this.mBufferedImage, 236, 87);
        }
        public String  GetSeedName (int theType ){
            String aName ="";
            switch (theType)
            {
                case 0:
                    aName = this.app.stringManager().translateString("[PEASHOOTER]");
                    break;
                case 1:
                    aName = this.app.stringManager().translateString("[SUNFLOWER]");
                    break;
                case 2:
                    aName = this.app.stringManager().translateString("[CHERRY_BOMB]");
                    break;
                case 3:
                    aName = this.app.stringManager().translateString("[WALL_NUT]");
                    break;
                case 4:
                    aName = this.app.stringManager().translateString("[SQUASH]");
                    break;
                case 5:
                    aName = this.app.stringManager().translateString("[SNOW_PEA]");
                    break;
                case 6:
                    aName = this.app.stringManager().translateString("[CHOMPER]");
                    break;
                case 7:
                    aName = this.app.stringManager().translateString("[REPEATER]");
                    break;
                case 8:
                    aName = this.app.stringManager().translateString("[PUFF_SHROOM]");
                    break;
                case 9:
                    aName = this.app.stringManager().translateString("[SUN_SHROOM]");
                    break;
                case 10:
                    aName = this.app.stringManager().translateString("[FUME_SHROOM]");
                    break;
                case 11:
                    aName = this.app.stringManager().translateString("[GRAVE_BUSTER]");
                    break;
            };
            return (aName);
        }
        public boolean  IsPaperNote (){
            int aLevel ;
            if (this.app.IsAdventureMode())
            {
                aLevel = this.app.mLevel;
                if (aLevel == 9)
                {
                    return (true);
                };
            };
            return (false);
        }

        private ImageInst mBackground ;

        public void  buttonRelease (int id ){
            if (this.app.adAPI.enabled())
            {
                this.app.musicManager().pauseMusic();
                this.app.soundManager().pauseAll();
            };
            this.mStartButton.setDisabled(true);
//            this.app.adAPI.GameBreak(this.app.mLevel, this.GameContinue);
            this.GameContinue();
        }

        public  AwardScreen (PVZApp app ){
            super();
            this.mRenderManager = new RenderManager();
            this.app = app;
            this.mFadeInCounter = 180;
            this.mStartButton = new ImageButtonWidget(0, this);
            this.mStartButton.visible = true;
            String aString =app.stringManager().translateString("[NEXT_LEVEL_BUTTON]");
            FontInst font =app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18YELLOW );
            font.scale(0.7);
            ImageInst anImage =app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON );
            ImageInst aGlowImage =app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON_GLOW );
            this.mStartButton.mUpImage = this.MakeButton(anImage, font, aString);
            this.mStartButton.mDisabledImage = this.MakeButton(anImage, font, aString);
            font.setColor(1, 1.5, 1.5, 1.5);
            this.mStartButton.mDownImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            this.mStartButton.mDownOffset = new Point(1, 1);
            this.mStartButton.mOverImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            anImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON_DISABLED);
            this.mStartButton.resize(215, 337, 105, 28);
            if (app.mLevel == 9)
            {
                this.mBackground = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND1);
                this.mZombieNote = app.imageManager().getImageInst(PVZImages.IMAGE_ZOMBIENOTE);
            }
            else
            {
                this.mBackground = app.imageManager().getImageInst(PVZImages.IMAGE_AWARDSCREEN_BACK);
            };
            this.mButtonImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON);
            this.mShovelImage = app.imageManager().getImageInst(PVZImages.IMAGE_SHOVEL_SMALL);
            int aLevel =app.mLevel ;
            int awardSeed =this.GetAwardSeedForLevel(aLevel );
            String seedName =this.GetSeedName(awardSeed );
            String seedToolTip =this.GetSeedToolTip(awardSeed );
            String title =app.stringManager().translateString("[NEW_PLANT]");
            if (aLevel == 4)
            {
                title = app.stringManager().translateString("[GOT_SHOVEL]");
                seedName = app.stringManager().translateString("[SHOVEL]");
                seedToolTip = app.stringManager().translateString("[SHOVEL_DESCRIPTION]");
            }
            else
            {
                if (aLevel == 9)
                {
                    title = app.stringManager().translateString("[FOUND_NOTE]");
                    seedName = "";
                    seedToolTip = "";
                };
            };
            font = app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT24);
            font.scale(0.9);
            font.setColor(1, (213 / 0xFF), (159 / 0xFF), (43 / 0xFF));
            this.mTitleTextRenderable = new StringRenderable(Board.RENDER_LAYER_ABOVE_UI);
            this.mTitleTextRenderable.setBounds(95, 20, 350, 27);
            this.mTitleTextRenderable.font(font);
            this.mTitleTextRenderable.text(title);
            font = app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18YELLOW);
            font.setColor(1, 1, 1, 1);
            font.scale(1);
            this.mNameTextRenderable = new StringRenderable(Board.RENDER_LAYER_ABOVE_UI);
            this.mNameTextRenderable.setBounds(157, 197, 225, 40);
            this.mNameTextRenderable.font(font);
            this.mNameTextRenderable.text(seedName);
            font = app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16);
            font.setColor(1, (40 / 0xFF), (50 / 0xFF), (90 / 0xFF));
            font.scale(1);
            this.mDescriptionTextRenderable = new StringRenderable(Board.RENDER_LAYER_ABOVE_UI);
            this.mDescriptionTextRenderable.setBounds(175, 235, 185, 80);
            this.mDescriptionTextRenderable.font(font);
            this.mDescriptionTextRenderable.text(seedToolTip);
            this.mRenderManager.add(this.mDescriptionTextRenderable);
            this.mRenderManager.add(this.mTitleTextRenderable);
            this.mRenderManager.add(this.mNameTextRenderable);
        }
    }


