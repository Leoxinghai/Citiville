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
//import flash.geom.Point;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.logic.UI.ToolTipWidget;
import com.xiyu.flash.framework.resources.images.ImageData;
//import flash.display.BitmapData;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.logic.UI.DialogBox;

    public class SeedChooserScreen extends CWidget implements IButtonListener {

        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int SEED_PUFFSHROOM =8;
        private static final int SEED_NONE =-1;
        public static final int SEED_IN_BANK =1;
        private static final int SEED_CHERRYBOMB =2;
        private static final int SEED_SQUASH =4;
        private static final int SEED_PEASHOOTER =0;
        public static final int SEED_IN_CHOOSER =3;
        public static final int SEED_FLYING_TO_CHOOSER =2;
        private static final int SEED_CHOMPER =6;
        public static final int SEED_SUNSHROOM =9;
        private static final int SEED_REPEATER =7;
        public static final int NUM_SEEDS_IN_CHOOSER =14;
        public static final int CHOOSE_ENTER =1;
        private static final int SEED_WALLNUT =3;
        public static final int SEED_FUMESHROOM =10;
        public static final int CHOOSE_NORMAL =0;
        public static final int SEED_FLYING_TO_BANK =0;
        public static final int CHOOSE_LEAVE =2;
        public static final int SEED_LEFTPEATER =49;
        public static final int SEED_PACKET_HIDDEN =4;
        public static final int SEED_GRAVEBUSTER =11;
        private static final int SEED_SUNFLOWER =1;
        private static final int SEED_SNOWPEA =5;
        public static final int CHOOSE_VIEW_LAWN =3;

        public void  buttonMouseEnter (int id ){
        }
        public void  ShowToolTip (){
            if (this.mChooseState != CHOOSE_NORMAL)
            {
                this.RemoveToolTip();
                return;
            };
            if (this.mSeedsInFlight > 0)
            {
                return;
            };
            int aSeedType =this.SeedHitTest(this.mLastMouseX ,this.mLastMouseY );
            if (aSeedType == SEED_NONE)
            {
                this.RemoveToolTip();
                return;
            };
            if (aSeedType == this.mToolTipSeed)
            {
                return;
            };
            this.RemoveToolTip();
            ChosenSeed aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aSeedType);
            this.mToolTip.SetWarningText("");
            this.mToolTip.SetTitle(this.mBoard.GetSeedName(aSeedType));
            this.mToolTip.SetLabel(this.mBoard.GetSeedToolTip(aSeedType));
            Point aPoint =new Point ();
            if (aChosenSeed.mSeedState == SEED_IN_BANK)
            {
                this.GetSeedPositionInBank(aChosenSeed.mSeedIndexInBank, aPoint);
            }
            else
            {
                this.GetSeedPositionInChooser(aSeedType, aPoint);
            };
            int aPosX =(aPoint.x +((50-this.mToolTip.mWidth )/2));
            aPosX = TodCommon.ClampInt(aPosX, 0, (Board.BOARD_WIDTH - this.mToolTip.mWidth));
            this.mToolTip.mX = aPosX;
            this.mToolTip.mY = (aPoint.y + 70);
            this.mToolTip.mVisible = true;
            this.mToolTipSeed = aSeedType;
        }

        public ImageButtonWidget mStartButton ;

        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        public int mChooseState ;

        public void  EnableStartButton (boolean theEnabled ){
            this.mStartButton.setDisabled(!(theEnabled));
        }
        public void  LandFlyingSeed (ChosenSeed theChosenSeed ){
            if (theChosenSeed.mSeedState == SEED_FLYING_TO_BANK)
            {
                theChosenSeed.mTimeStartMotion = 0;
                theChosenSeed.mTimeEndMotion = 0;
                theChosenSeed.mSeedState = SEED_IN_BANK;
                theChosenSeed.mX = theChosenSeed.mEndX;
                theChosenSeed.mY = theChosenSeed.mEndY;
                this.mSeedsInFlight--;
            }
            else
            {
                if (theChosenSeed.mSeedState == SEED_FLYING_TO_CHOOSER)
                {
                    theChosenSeed.mTimeStartMotion = 0;
                    theChosenSeed.mTimeEndMotion = 0;
                    theChosenSeed.mSeedState = SEED_IN_CHOOSER;
                    theChosenSeed.mX = theChosenSeed.mEndX;
                    theChosenSeed.mY = theChosenSeed.mEndY;
                    this.mSeedsInFlight--;
                };
            };
        }
        public void  CloseSeedChooser (){
            int aSeedType ;
            ChosenSeed aChosenSeed ;
            SeedPacket aSeedPacket ;
            this.mBoard.mSeedBank.mVisible = true;
            int i =0;
            while (i < this.mBoard.mSeedBank.mNumPackets)
            {
                aSeedType = this.FindSeedInBank(i);
                aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aSeedType);
                aSeedPacket=(SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i);
                aSeedPacket.mPacketType = aSeedType;
                aSeedPacket.mUpdateImage = true;
                aSeedPacket.mVisible = true;
                if (aChosenSeed != null)
                {
                    if (aChosenSeed.mRefreshing)
                    {
                    	aSeedPacket.mRefreshCounter=((ChosenSeed)this.mChosenSeeds.elementAt(aSeedType)).mRefreshCounter;
                        aSeedPacket.mRefreshTime = aSeedPacket.GetRefreshTime(aSeedPacket.mPacketType);
                        aSeedPacket.mRefreshing = true;
                        aSeedPacket.mActive = false;
                        aSeedPacket.mUpdateImage = true;
                        aSeedPacket.mVisible = true;
                    }
                    else
                    {
                        aSeedPacket.mRefreshCounter = 0;
                        aSeedPacket.mRefreshTime = 0;
                        aSeedPacket.mRefreshing = false;
                        aSeedPacket.mActive = true;
                    };
                };
                if (aSeedPacket.GetRefreshTime(aSeedPacket.mPacketType) == 3000)
                {
                	aSeedPacket.mRefreshCounter=((ChosenSeed)this.mChosenSeeds.elementAt(aSeedType)).mRefreshCounter;
                    aSeedPacket.mRefreshing = true;
                    aSeedPacket.mRefreshTime = 2000;
                    aSeedPacket.mActive = false;
                }
                else
                {
                    if (aSeedPacket.GetRefreshTime(aSeedPacket.mPacketType) == 5000)
                    {
                    	aSeedPacket.mRefreshCounter=((ChosenSeed)this.mChosenSeeds.elementAt(aSeedType)).mRefreshCounter;
                        aSeedPacket.mRefreshing = true;
                        aSeedPacket.mRefreshTime = 5000;
                        aSeedPacket.mActive = false;
                    };
                };
                i++;
            };
        }

        private ImageInst mButtonImage ;
        public int mSeedsInBank ;

        public void  ClickedSeedInChooser (ChosenSeed theChosenSeed ){
            if (this.mSeedsInBank == this.mBoard.mSeedBank.mNumPackets)
            {
                return;
            };
            theChosenSeed.mTimeStartMotion = this.mSeedChooserAge;
            theChosenSeed.mTimeEndMotion = (this.mSeedChooserAge + 25);
            theChosenSeed.mStartX = theChosenSeed.mX;
            theChosenSeed.mStartY = theChosenSeed.mY;
            Point aPoint =new Point ();
            aPoint.x = theChosenSeed.mEndX;
            aPoint.y = theChosenSeed.mEndY;
            this.GetSeedPositionInBank(this.mSeedsInBank, aPoint);
            theChosenSeed.mEndX = aPoint.x;
            theChosenSeed.mEndY = aPoint.y;
            theChosenSeed.mSeedState = SEED_FLYING_TO_BANK;
            theChosenSeed.mSeedIndexInBank = this.mSeedsInBank;
            this.mSeedsInFlight++;
            this.mSeedsInBank++;
            this.RemoveToolTip();
            this.app.foleyManager().playFoley(PVZFoleyType.DROP);
            if (this.mSeedsInBank == this.mBoard.mSeedBank.mNumPackets)
            {
                this.EnableStartButton(true);
            };
        }

        private StringRenderable mUpsellText ;
        public int Options_Menu =100;
        public PVZApp app ;

        public boolean  SeedNotAllowedToPick (int theSeedType ){
            return (false);
        }

        public Dictionary mChosenSeeds ;
        private Dictionary mSeedPacket ;

        public boolean  PickedPlantType (int theSeedType ){
            ChosenSeed chosenSeed ;
            int i =0;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
            	chosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                if (chosenSeed != null)
                {
                    if (chosenSeed.mSeedState != SEED_IN_BANK)
                    {
                    }
                    else
                    {
                        if (chosenSeed.mSeedType == theSeedType)
                        {
                            return (true);
                        };
                    };
                };
                i++;
            };
            return (false);
        }
        public void  GetSeedPositionInChooser (int theIndex ,Point thePoint ){
            int aRow =(theIndex /6);
            int aCol =(theIndex % 6);
            thePoint.x = (12 + (aCol * 50 * 2));
            thePoint.y = (110 + (aRow * 65 * 2));
        }

        public ImageButtonWidget mLawnViewButton ;

        public void  buttonPress (int id ){
        }
        public void  KillDialogBox (){
            this.mDialogBox.setVisible(false);
            this.mDialogBox.KillButtons();
            this.mLawnViewButton.setDisabled(false);
            this.mStartButton.setDisabled(false);
        }
        public void  OnStartButton (){
            if (((((this.app.IsAdventureMode()) && ((this.mBoard.mLevel == 11)))) && (!(this.PickedPlantType(SEED_PUFFSHROOM)))))
            {
                this.DisplayRepickWarningDialog("[SEED_CHOOSER_PUFFSHROOM_WARNING]");
            }
            else
            {
                if (((((!(this.PickedPlantType(SEED_SUNFLOWER))) && (!(this.PickedPlantType(SEED_SUNSHROOM))))) && (this.app.IsAdventureMode())))
                {
                    if (this.mBoard.mLevel == 11)
                    {
                        this.DisplayRepickWarningDialog("[SEED_CHOOSER_NIGHT_SUN_WARNING]");
                    }
                    else
                    {
                        this.DisplayRepickWarningDialog("[SEED_CHOOSER_SUN_WARNING]");
                    };
                }
                else
                {
                    this.mChooseState = CHOOSE_LEAVE;
                    this.CloseSeedChooser();
                };
            };
        }

        public ToolTipWidget mToolTip ;

        public void  RemoveToolTip (){
            this.mToolTip.mVisible = false;
            this.mToolTipSeed = SEED_NONE;
        }
        public void  ClickedSeedInBank (ChosenSeed theChosenSeed ){
            int aSeedIndex ;
            ChosenSeed aMoveSeed ;
            Point aPoint =new Point ();
            int i =(theChosenSeed.mSeedIndexInBank +1);
            while (i < this.mBoard.mSeedBank.mNumPackets)
            {
            	if(((SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i)).mPacketType!= SEED_NONE)
                {
            		((SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i)).mVisible=false;
                };
                aSeedIndex = this.FindSeedInBank(i);
                if (aSeedIndex == SEED_NONE)
                {
                }
                else
                {
                	aMoveSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aSeedIndex);
                    aMoveSeed.mTimeStartMotion = this.mSeedChooserAge;
                    aMoveSeed.mTimeEndMotion = (this.mSeedChooserAge + 15);
                    aMoveSeed.mStartX = aMoveSeed.mX;
                    aMoveSeed.mStartY = aMoveSeed.mY;
                    aPoint.x = aMoveSeed.mEndX;
                    aPoint.y = aMoveSeed.mEndY;
                    this.GetSeedPositionInBank((i - 1), aPoint);
                    aMoveSeed.mEndX = aPoint.x;
                    aMoveSeed.mEndY = aPoint.y;
                    aMoveSeed.mSeedState = SEED_FLYING_TO_BANK;
                    aMoveSeed.mSeedIndexInBank = (i - 1);
                    this.mSeedsInFlight++;
                };
                i++;
            };
            i = theChosenSeed.mSeedIndexInBank;
            if(((SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i)).mPacketType!= SEED_NONE)
            {
            	((SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i)).mVisible=false;
            };
            theChosenSeed.mTimeStartMotion = this.mSeedChooserAge;
            theChosenSeed.mTimeEndMotion = (this.mSeedChooserAge + 25);
            theChosenSeed.mStartX = theChosenSeed.mX;
            theChosenSeed.mStartY = theChosenSeed.mY;
            aPoint.x = theChosenSeed.mEndX;
            aPoint.y = theChosenSeed.mEndY;
            this.GetSeedPositionInChooser(theChosenSeed.mSeedType, aPoint);
            theChosenSeed.mEndX = aPoint.x;
            theChosenSeed.mEndY = aPoint.y;
            theChosenSeed.mSeedState = SEED_FLYING_TO_CHOOSER;
            theChosenSeed.mSeedIndexInBank = 0;
            this.mSeedsInFlight++;
            this.mSeedsInBank--;
            this.EnableStartButton(false);
            this.app.foleyManager().playFoley(PVZFoleyType.DROP);
        }

        public ImageButtonWidget mMenuButton ;

        public void  UpdateCursor (){
            boolean aShowFinger ;
            ChosenSeed aChosenSeed ;
            int aHitSeedType =this.SeedHitTest(this.mLastMouseX ,this.mLastMouseY );
            if (aHitSeedType != SEED_NONE)
            {
            	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aHitSeedType);
                if ((((aChosenSeed.mSeedState == SEED_IN_BANK)) && (aChosenSeed.mCrazyDavePicked)))
                {
                    aHitSeedType = SEED_NONE;
                };
            };
            if (this.mChooseState == CHOOSE_VIEW_LAWN)
            {
                aShowFinger = false;
            }
            else
            {
                if (aHitSeedType != SEED_NONE)
                {
                    if (this.SeedNotAllowedToPick(aHitSeedType))
                    {
                        aShowFinger = false;
                    }
                    else
                    {
                        aShowFinger = true;
                    };
                }
                else
                {
                    aShowFinger = false;
                };
            };
            showFinger(aShowFinger);
        }

        public int mToolTipSeed ;

         public void  onMouseDown (int x ,int y ){
            int i =0;
            System.out.println("SeedChooserScreen.mousedown");
            ChosenSeed aChosenSeed ;
            if (this.mSeedsInFlight > 0)
            {
                i = 0;
                while (i < NUM_SEEDS_IN_CHOOSER)
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if (aChosenSeed != null)
                    {
                        this.LandFlyingSeed(aChosenSeed);
                    };
                    i++;
                };
            };
            int aSeedType =this.SeedHitTest(x ,y );
            if (aSeedType == SEED_NONE)
            {
                return;
            };
            aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aSeedType);
            if ((((aChosenSeed.mSeedState == SEED_IN_BANK)) && (aChosenSeed.mCrazyDavePicked)))
            {
                return;
            };
            if (aChosenSeed.mSeedState == SEED_IN_BANK)
            {
                this.ClickedSeedInBank(aChosenSeed);
            }
            else
            {
                if (aChosenSeed.mSeedState == SEED_IN_CHOOSER)
                {
                    this.ClickedSeedInChooser(aChosenSeed);
                };
            };
        }

        public int mSeedsInFlight ;
        public int mViewLawnTime ;
        private ImageInst mPacketSilhouette ;

         public void  update (){
            ChosenSeed aChosenSeed ;
            this.mLastMouseX = this.app.widgetManager().lastMouseX;
            this.mLastMouseY = this.app.widgetManager().lastMouseY;
            this.mSeedChooserAge++;
            this.mToolTip.Update();
            int i =0;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
                if (!this.HasSeedType(i))
                {
                }
                else
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if ((((aChosenSeed.mSeedState == SEED_FLYING_TO_BANK)) || ((aChosenSeed.mSeedState == SEED_FLYING_TO_CHOOSER))))
                    {
                        aChosenSeed.mX = TodCommon.TodAnimateCurve(aChosenSeed.mTimeStartMotion, aChosenSeed.mTimeEndMotion, this.mSeedChooserAge, aChosenSeed.mStartX, aChosenSeed.mEndX, TodCommon.CURVE_EASE_IN_OUT);
                        aChosenSeed.mY = TodCommon.TodAnimateCurve(aChosenSeed.mTimeStartMotion, aChosenSeed.mTimeEndMotion, this.mSeedChooserAge, aChosenSeed.mStartY, aChosenSeed.mEndY, TodCommon.CURVE_EASE_IN_OUT);
                    };
                    if ((((aChosenSeed.mSeedState == SEED_FLYING_TO_BANK)) && ((this.mSeedChooserAge >= aChosenSeed.mTimeEndMotion))))
                    {
                        this.LandFlyingSeed(aChosenSeed);
                    };
                    if ((((aChosenSeed.mSeedState == SEED_FLYING_TO_CHOOSER)) && ((this.mSeedChooserAge >= aChosenSeed.mTimeEndMotion))))
                    {
                        this.LandFlyingSeed(aChosenSeed);
                    };
                };
                i++;
            };
            this.ShowToolTip();
            this.UpdateCursor();
            markDirty(null);
        }
        public int  FindSeedInBank (int theIndexInBank ){
            ChosenSeed aChosenSeed ;
            int i =0;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
                if (!this.HasSeedType(i))
                {
                }
                else
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if ((((aChosenSeed.mSeedState == SEED_IN_BANK)) && ((aChosenSeed.mSeedIndexInBank == theIndexInBank))))
                    {
                        return (i);
                    };
                };
                i++;
            };
            return (SEED_NONE);
        }
        public void  buttonDownTick (int id ){
        }
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

        public int mLastMouseX ;
        public int mLastMouseY ;
        public int mNumSeedsToChoose ;

         public void  draw (Graphics2D g ){
            Point aPoint =new Point(0,0);
            double x0 =0;
            double y0 =0;
            int aSeedType ;
            ChosenSeed aChosenSeed ;
            ImageInst upsellImage ;
            if (!this.mBoard.ChooseSeedsOnCurrentLevel())
            {
                return;
            };
            g.drawImage(this.mBackground, 0, 78);

            FontInst font =this.app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18YELLOW );
            font.setColor(1, 1, 1, 1);
            font.scale(0.8);
            g.pushState();
            g.setFont(font);
            g.drawString(this.app.stringManager().translateString("[CHOOSE_YOUR_PLANTS]"), 55, 82);
            g.popState();
            int aSeedPlaces =24;
            int i =0;
            for (;i < aSeedPlaces;i++)
            {
                aPoint = new Point();
                this.GetSeedPositionInChooser(i, aPoint);
                x0 = aPoint.x;
                y0 = aPoint.y;
//                x0 = x0*2;
//                y0 = y0*2;

                aSeedType = i;
                if (this.HasSeedType(aSeedType))
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if (aChosenSeed.mSeedState == SEED_IN_CHOOSER)
                    {
                        continue;
                    };
                    if(this.mSeedPacket.elementAt(i)==null)
                    {
                        this.DrawSeedPacket(g, i);
                    };
                    ((ImageInst)this.mSeedPacket.elementAt(i)).useColor=true;
                    ((ImageInst)this.mSeedPacket.elementAt(i)).setColor(1,0.25,0.25,0.25);
                    g.drawImage((ImageInst)this.mSeedPacket.elementAt(i),x0,y0);
                }
                else
                {
                    g.drawImage(this.mPacketSilhouette, x0, y0);
                };
            };
            int aNumPlacesInBank =this.mBoard.mSeedBank.mNumPackets ;
            i = 0;
            while (i < aNumPlacesInBank)
            {
                if (this.FindSeedInBank(i) != SEED_NONE)
                {
                }
                else
                {
                    this.GetSeedPositionInBank(i, aPoint);
                    g.drawImage(this.mPacketSilhouette, aPoint.x, aPoint.y);
                };
                i++;
            };
            i = 0;
            ImageInst seedInst;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
                if (!this.HasSeedType(i))
                {
                }
                else
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if ((((((aChosenSeed.mSeedState == SEED_FLYING_TO_CHOOSER)) || ((aChosenSeed.mSeedState == SEED_FLYING_TO_BANK)))) || ((aChosenSeed.mSeedState == SEED_PACKET_HIDDEN))))
                    {
                    }
                    else
                    {
                        x0 = aChosenSeed.mX;
                        y0 = aChosenSeed.mY;
                        if (aChosenSeed.mSeedState == SEED_IN_BANK)
                        {
                            x0 = (int)(x0 - this.x);
                            y0 = (int)(y0 - this.y);
                        };
                        if(this.mSeedPacket.elementAt(i)==null)
                        {
                            this.DrawSeedPacket(g, i);
                        };
//                        x0 = x0*2;
//                        y0 = y0*2 - 100;
                        seedInst = (ImageInst)this.mSeedPacket.elementAt(i);
                        seedInst.useColor=true;
                        seedInst.setColor(1,1,1,1);
                        g.drawImage(seedInst, x0, y0);
                    };
                };
                i++;
            };
            i = 0;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
                if (!this.HasSeedType(i))
                {
                }
                else
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if (((!((aChosenSeed.mSeedState == SEED_FLYING_TO_CHOOSER))) && (!((aChosenSeed.mSeedState == SEED_FLYING_TO_BANK)))))
                    {
                    }
                    else
                    {
                    	((ImageInst)this.mSeedPacket.elementAt(i)).useColor=true;
                    	((ImageInst)this.mSeedPacket.elementAt(i)).setColor(1,1,1,1);
                    	g.drawImage((ImageInst)this.mSeedPacket.elementAt(i),aChosenSeed.mX,aChosenSeed.mY);
                    };
                };
                i++;
            };
            if ((((this.mBoard.mLevel > 12)) || (this.app.IsSurvivalMode())))
            {
                upsellImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_UPSELL);
                //g.drawImage(upsellImage, 15, 242);
                this.mUpsellText.draw(g);
            };
            this.mToolTip.Draw(g);

        }
        private void  DrawSeedPacket (Graphics2D g ,int theType ){
            ImageInst aSeedBackImg ;
            ImageInst aPlantImg =null;
            aSeedBackImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SEEDBACK);
            int w =aSeedBackImg.width() ;
            int h =aSeedBackImg.height() ;
            this.mSeedPacket.put(theType, new ImageInst(new ImageData(new BitmapData(w,h,true,0))));
            Graphics2D bufferG=((ImageInst)this.mSeedPacket.elementAt(theType)).graphics();
//            bufferG.drawImage(aSeedBackImg, 0, 0);
            int aOffsetX =5;
            int aOffsetY =10;
            double aScale =0.75;
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
                    aOffsetY = (aOffsetY + 3);
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
                    aScale = 0.6;
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
                    aOffsetY = (aOffsetY + 10);
                    aOffsetX = (aOffsetX + 7);
                    break;
                case SEED_SUNSHROOM:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_SUNSHROOM);
                    aOffsetY = (aOffsetY + 10);
                    aOffsetX = (aOffsetX + 7);
                    break;
                case SEED_GRAVEBUSTER:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_GRAVEDIGGER);
                    aOffsetY = (aOffsetY + 0);
                    aOffsetX = (aOffsetX + -3);
                    aScale = 0.6;
                    break;
                case SEED_FUMESHROOM:
                    aPlantImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_FUMESHROOM);
                    aOffsetY = (aOffsetY + 1);
                    aScale = 0.65;
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
            int aCost =this.mBoard.GetCurrentPlantCost(theType );
            FontInst aSeedPacketFont =this.app.fontManager().getFontInst(PVZFonts.FONT_PICO129 );
            aSeedPacketFont.setColor(1, 0, 0, 0);
            int width =(int)aSeedPacketFont.stringWidth(""+aCost);
            int height =(int)aSeedPacketFont.getAscent();
            int aTextOffsetX =(30-width );
            int aTextOffsetY =(40+height );
            bufferG.setFont(aSeedPacketFont);
            bufferG.drawString(""+aCost, aTextOffsetX, aTextOffsetY);
        }

        public int mSeedChooserAge ;

        public void  buttonMouseLeave (int id ){
        }
        public boolean  HasSeedType (int theType ){
            int aArea =(((this.app.mLevel -1)/10)+1);
            int aSubArea =(((this.app.mLevel -1)% 10) + 1);
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
            if (!this.app.IsAdventureMode())
            {
                aSeeds = 12;
            };
            int theSeedType =Math.min(12,aSeeds );
            if (theType >= theSeedType)
            {
                return (false);
            };
            return (true);
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

        public final int Start_Game =102;

        public void  DisplayRepickWarningDialog (String theMessage ){
            String aDialogHeader =this.app.stringManager().translateString("[DIALOG_WARNING]");
            String aDialogMessage =this.app.stringManager().translateString(theMessage );
            String aDialogOk =this.app.stringManager().translateString("[DIALOG_BUTTON_YES]");
            String aDialogCancel =this.app.stringManager().translateString("[REPICK_BUTTON]");
            this.mDialogBox.resize((0xFF - (63 * 2)), (130 - (36 * 2)), (153 + (63 * 2)), (184 + (36 * 2)));
            this.mDialogBox.InitializeDialogBox(aDialogHeader, aDialogMessage, aDialogOk, aDialogCancel, 2, 2);
            this.mDialogBox.mDialogType = DialogBox.DIALOG_REPICK;
            this.mDialogBox.setVisible(true);
            this.mDialogBox.mOkButton.setVisible(true);
            this.mDialogBox.mCancelButton.setVisible(true);
            this.mLawnViewButton.setDisabled(true);
            this.mStartButton.setDisabled(true);
        }
        public int  SeedHitTest (int x0 ,int y0 ){
            ChosenSeed aChosenSeed ;
            int i =0;
            while (i < NUM_SEEDS_IN_CHOOSER)
            {
                if (!this.HasSeedType(i))
                {
                }
                else
                {
                	aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(i);
                    if (aChosenSeed.mSeedState == SEED_PACKET_HIDDEN)
                    {
                    }
                    else
                    {
                        if ((((((((x0 >= aChosenSeed.mX)) && ((y0 >= aChosenSeed.mY)))) && ((x0 < (aChosenSeed.mX + 46))))) && ((y0 < (aChosenSeed.mY + 65)))))
                        {
                            return (i);
                        };
                    };
                };
                i++;
            };
            return (SEED_NONE);
        }
        public void  buttonRelease (int id ){
            switch (id)
            {
                case Start_Game:
                    this.OnStartButton();
                    break;
                case Lawn_View:
                    this.CloseSeedChooser();
                    this.mChooseState = CHOOSE_VIEW_LAWN;
                    break;
            };
        }
        public void  GetSeedPositionInBank (int theIndex ,Point thePoint ){
            //thePoint.x = (int)((this.mBoard.mSeedBank.mX + this.mBoard.GetSeedPacketPositionX(theIndex)) - this.x);
            //thePoint.y = (int)((this.mBoard.mSeedBank.mY + 6) - this.y);
            thePoint.x = (int)((this.mBoard.mSeedBank.mX + this.mBoard.GetSeedPacketPositionX(theIndex)) + 67 * 2);
            thePoint.y = (int)((this.mBoard.mSeedBank.mY + 6) - 0.0);
        }

        public final int Lawn_View =101;
        public Board mBoard ;
        private ImageInst mBackground ;
        public DialogBox mDialogBox ;

        public  SeedChooserScreen (PVZApp app ,Board theBoard ){
            super();
            int i =0;
            ChosenSeed aChosenSeed ;
            Point aPoint ;
            SeedPacket aSeedPacket ;
            int aSeedType ;
            this.mChosenSeeds = new Dictionary();
            this.mSeedPacket = new Dictionary();
            x = 200;
            y = 200;
            width = 460;
            height = 405;
            this.app = app;
            this.mBoard = theBoard;
            this.mSeedsInFlight = 0;
            this.mSeedsInBank = 0;
            this.mLastMouseX = -1;
            this.mLastMouseY = -1;
            this.mViewLawnTime = 0;
            this.mToolTip = new ToolTipWidget(app, theBoard);
            FontInst font =app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18YELLOW );
            font.scale(0.7);
            ImageInst anImage =app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON );
            String aString =app.stringManager().translateString("[LETS_ROCK_BUTTON]");
            this.mStartButton = new ImageButtonWidget(this.Start_Game, this);
            this.mStartButton.mUpImage = this.MakeButton(anImage, font, aString);
            font.setColor(1, 1.5, 1.5, 1.5);
            ImageInst aGlowImage =app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON_GLOW );
            this.mStartButton.mDownImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            this.mStartButton.mDownOffset = new Point(1, 1);
            this.mStartButton.mOverImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            anImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON_DISABLED);
            font.setColor(1, (64 / 0xFF), (64 / 0xFF), (64 / 0xFF));
            this.mStartButton.mDisabledImage = this.MakeButton(anImage, font, aString);
            this.mStartButton.visible = true;
            this.mStartButton.resize(115, 1500, 105*24, 28*24);
            
            this.EnableStartButton(true);
            this.mLawnViewButton = new ImageButtonWidget(this.Lawn_View, this);
            anImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON2);
            aString = app.stringManager().translateString("[VIEW_LAWN]");
            font = app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16);
            font.scale(0.6);
            font.setColor(1, (42 / 0xFF), (42 / 0xFF), (90 / 0xFF));
            this.mLawnViewButton.mUpImage = this.MakeButton(anImage, font, aString);
            aGlowImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON2_GLOW);
            this.mLawnViewButton.mDownImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            this.mLawnViewButton.mDownOffset = new Point(1, 1);
            this.mLawnViewButton.mOverImage = this.MakeGlowButton(anImage, aGlowImage, font, aString);
            this.mLawnViewButton.mDisabledImage = this.MakeButton(anImage, font, aString);
            this.mLawnViewButton.visible = true;
            this.mLawnViewButton.resize(20, 720, 75, 18);
            if (((app.IsAdventureMode()) || ((this.mBoard.mChallenge.mSurvivalStage == 0))))
            {
                this.mLawnViewButton.visible = false;
                this.mLawnViewButton.setDisabled(true);
            };
            i = 0;
            while (i < 12)
            {
                aChosenSeed = new ChosenSeed();
                aPoint = new Point();
                this.GetSeedPositionInChooser(i, aPoint);
                aChosenSeed.mX = aPoint.x;
                aChosenSeed.mY = aPoint.y;
                aChosenSeed.mSeedType = i;
                aChosenSeed.mTimeStartMotion = 0;
                aChosenSeed.mTimeEndMotion = 0;
                aChosenSeed.mStartX = aChosenSeed.mX;
                aChosenSeed.mStartY = aChosenSeed.mY;
                aChosenSeed.mEndX = aChosenSeed.mX;
                aChosenSeed.mEndY = aChosenSeed.mY;
                aChosenSeed.mSeedState = SEED_IN_CHOOSER;
                aChosenSeed.mSeedIndexInBank = 0;
                aChosenSeed.mRefreshCounter = 0;
                aChosenSeed.mRefreshing = false;
                aChosenSeed.mCrazyDavePicked = false;
                this.mChosenSeeds.put(i,aChosenSeed);
                i++;
            };
//            if (((((app.IsSurvivalMode()) && ((this.mBoard.mChallenge.mSurvivalStage > 0)))) && ((this.mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            if (((((app.IsSurvivalMode()) && ((this.mBoard.mChallenge.mSurvivalStage >= 0)))) && ((this.mBoard.mGameScene == Board.SCENE_LEVEL_INTRO))))
            {
                i = 0;
                while (i < this.mBoard.mSeedBank.mNumPackets)
                {
                	aSeedPacket=(SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i);
                    aSeedType = aSeedPacket.mPacketType;
                    if(aSeedType <0)
                    	aSeedType = 1;

                    aChosenSeed=(ChosenSeed)this.mChosenSeeds.elementAt(aSeedType);
                    aChosenSeed.mRefreshing = aSeedPacket.mRefreshing;
                    aChosenSeed.mRefreshCounter = aSeedPacket.mRefreshCounter;
                    i++;
                };
                i = 0;
                while (i < 6)
                {
                	aSeedPacket=(SeedPacket)this.mBoard.mSeedBank.mSeedPackets.elementAt(i);
                    aSeedPacket.mX = this.mBoard.GetSeedPacketPositionX(i);
                    aSeedPacket.mPacketType = SEED_NONE;
                    i++;
                };
                this.mBoard.mSeedBank.mNumPackets = 4;
            };
            this.mBackground = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BACKGROUND);
            this.mButtonImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BUTTON);
            this.mPacketSilhouette = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDPACKETSILHOUETTE);
            this.mUpsellText = new StringRenderable(0);
            this.mUpsellText.setBounds(27, 240, 270, 130);
            String upsellString =app.stringManager().translateString("[SEEDCHOOSER_UPSELL_MESSAGE]");
            font = app.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT18YELLOW);
            font.setColor(1, 1, 1, 1);
            font.scale(1.2);
            this.mUpsellText.font(font);
            this.mUpsellText.text(upsellString);
            this.mDialogBox = new DialogBox(app, this.mBoard);
            this.mDialogBox.setVisible(false);
        }
    }


