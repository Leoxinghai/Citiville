package com.xiyu.flash.games.pvz.logic;

import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageData;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.util.BitmapData;
import com.pgh.mahjong.CardType;
import com.pgh.mahjong.resource.MahJongImages;

public class MahJongSeedPacket extends GameObject {

    private static final int CURSOR_TYPE_PLANT_FROM_BANK =1;
    public static final int TUTORIAL_MORESUN_COMPLETED =12;
    private static final int SLOT_MACHINE_TIME =400;
    public static final int TUTORIAL_LEVEL_2_PLANT_SUNFLOWER =6;
    private static final int OBJECT_TYPE_PLANT =1;
    private static final int CURSOR_TYPE_HAMMER =4;
    private static final int OBJECT_TYPE_PROJECTILE =2;
    public static final int SEED_REPEATER =7;
    public static final int TUTORIAL_MORESUN_PICK_UP_SUNFLOWER =9;
    private static final int CURSOR_TYPE_PLANT_FROM_USABLE_COIN =2;
    private static final int OBJECT_TYPE_NONE =0;
    public static final int TUTORIAL_LEVEL_1_COMPLETED =4;
    public static final int SEED_WALLNUT =3;
    public static final int SEED_PUFFSHROOM =8;
    public static final int TUTORIAL_MORESUN_PLANT_SUNFLOWER =10;
    private static double SECONDS_PER_UPDATE =0.01;
    private static final int CURSOR_TYPE_NORMAL =0;
    private static  String VARIATION_NORMAL ="normal";
    public static final int SEED_EXPLODE_O_NUT =50;
    public static final int SEED_SUNFLOWER =1;
    public static final int SEED_PEASHOOTER =0;
    public static final int SEED_FUMESHROOM =10;
    public static final int TUTORIAL_LEVEL_1_PLANT_PEASHOOTER =2;
    public static final int TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER =7;
    public static final int TUTORIAL_SHOVEL_DIG =14;
    private static final int OBJECT_TYPE_SHOVEL =5;
    public static final int SEED_SNOWPEA =5;
    public static final int SEED_CHOMPER =6;
    public static final int SEED_NONE =-1;
    public static final int TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER =3;
    public static final int TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER =1;
    public static final int TUTORIAL_SHOVEL_COMPLETED =16;
    public static final int SEED_CHERRYBOMB =2;
    public static final int TUTORIAL_MORESUN_REFRESH_SUNFLOWER =11;
    private static final int OBJECT_TYPE_SEEDPACKET =4;
    private static final int CURSOR_TYPE_SHOVEL =3;
    public static final int TUTORIAL_LEVEL_2_COMPLETED =8;
    public static final int SEED_SQUASH =4;
    public static final int SEED_LEFTPEATER =49;
    public static final int TUTORIAL_OFF =0;
    public static final int SEED_SUNSHROOM =9;
    private static final int OBJECT_TYPE_COIN =3;
    public static final int TUTORIAL_SHOVEL_PICKUP =13;
    public static final int TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER =5;
    public static final int TUTORIAL_SHOVEL_KEEP_DIGGING =15;
    public static final int SEED_GRAVEBUSTER =11;

    public MahJongSeedPacket (PVZApp app ,Board theBoard, CardType cardType){
        this.mSeedPacketFont = app.fontManager().getFontInst(PVZFonts.FONT_PICO129);
        mBoard = theBoard;
        this.app = app;
        this.mPacketType = SEED_PEASHOOTER;
        this.mImitaterType = SEED_NONE;
        this.mIndex = -1;
        mCardType = cardType;
//        mWidth = 46;
//        mHeight = 65;
        
        mWidth = app.unitWidth;
        mHeight = app.unitHeight;
        
        this.mRefreshCounter = 0;
        this.mRefreshTime = 0;
        this.mRefreshing = false;
        this.mActive = true;
        this.mOffsetX = 0;
        this.mSlotMachineCountDown = 0;
        this.mSlotMachiningNextSeed = SEED_NONE;
        this.mSlotMachiningPosition = 0;
        this.mTimesUsed = 0;
        mVisible = true;
        this.mUpdateImage = true;
    }

    public boolean  MouseHitTest (int theX ,int theY ,HitResult theHitResult ){
        if (theX >= mX + this.mOffsetX && theX < mX + mWidth + this.mOffsetX && theY >= mY && theY < mY + mHeight && this.mPacketType != SEED_NONE )
        {
        	System.out.println("SeedPacket.MouseHitTest" + theX+":"+theY);
            theHitResult.mObject = this;
            theHitResult.mObjectType = OBJECT_TYPE_SEEDPACKET;
            return (true);
        };

        theHitResult.mObject = null;
        theHitResult.mObjectType = OBJECT_TYPE_NONE;
        return (false);

    }
    public void  WasPlanted (){
        if (mBoard.HasConveyorBeltSeedBank())
        {
            mBoard.mSeedBank.RemoveSeed(this.mIndex);
        }
        else
        {
            this.mTimesUsed++;
            this.mRefreshing = true;
            this.mRefreshTime = this.GetRefreshTime(this.mPacketType);
        };
    }

    private int mSlotMachiningPosition;
    private CardType mCardType;
    

    public void  FlashIfReady (){
        ParticleSystem anEffect ;
        if (!this.CanPickUp())
        {
            return;
        };
        if (mBoard.mEasyPlantingCheat)
        {
            return;
        };
        if (!mBoard.HasConveyorBeltSeedBank())
        {
            anEffect = app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SEEDPACKETFLASH);
            anEffect.setPosition((mX + 6), (mY + 9));
            mBoard.mRenderManager.add(new ParticleRenderable(anEffect, (Board.RENDER_LAYER_UI_BOTTOM + 2)));
        };
        if (mBoard.mTutorialState == TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER)
        {
            mBoard.SetTutorialState(TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER);
        }
        else
        {
            if ((((mBoard.mTutorialState == TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER)) && ((this.mPacketType == SEED_SUNFLOWER))))
            {
                mBoard.SetTutorialState(TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER);
            }
            else
            {
                if ((((mBoard.mTutorialState == TUTORIAL_MORESUN_REFRESH_SUNFLOWER)) && ((this.mPacketType == SEED_SUNFLOWER))))
                {
                    mBoard.SetTutorialState(TUTORIAL_MORESUN_PICK_UP_SUNFLOWER);
                };
            };
        };
    }
    public void  PickNextSlotMachineSeed (){
    }

    public boolean mUpdateImage ;

    public void  update (){
        if (mBoard.mGameScene != PVZApp.SCENE_PLAYING)
        {
            return;
        };
        if (this.mPacketType == SEED_NONE)
        {
            return;
        };
        if (mBoard.mMainCounter == 0)
        {
            this.FlashIfReady();
        };
        if (((!(this.mActive)) && (this.mRefreshing)))
        {
            this.mRefreshCounter++;
            if (this.mRefreshCounter > this.mRefreshTime)
            {
                this.mRefreshCounter = 0;
                this.mRefreshing = false;
                this.Activate();
                this.FlashIfReady();
            };
        };
    }
    public void  MouseDown (int x ,int y ){
        int aCost ;
        //add by xinghai
        this.mActive = true;
        
        if (((mBoard.mPaused) || (!((mBoard.mGameScene == Board.SCENE_PLAYING)))))
        {
            return;
        };
        System.out.println("SeedPacket.mousedown."+x+":"+y);
        if (this.mPacketType == SEED_NONE)
        {
            return;
        };
        int aPlantingSeedType =this.mPacketType ;
        if (!mBoard.mEasyPlantingCheat)
        {
            if (!this.mActive)
            {
                app.foleyManager().playFoley(PVZFoleyType.BUZZER);
                if ( mBoard.mLevel == 1 && ((Boolean)mBoard.mHelpDisplayed.elementAt(Board.ADVICE_CLICK_ON_SUN)).booleanValue())
                {
                    mBoard.DisplayAdvice("[ADVICE_SEED_REFRESH]", Board.MESSAGE_STYLE_TUTORIAL_LEVEL1, Board.ADVICE_SEED_REFRESH);
                };
                return;
            };
            aCost = mBoard.GetCurrentPlantCost(this.mPacketType);
            if (((!(mBoard.CanTakeSunMoney(aCost))) && (!(mBoard.HasConveyorBeltSeedBank()))))
            {
                app.foleyManager().playFoley(PVZFoleyType.BUZZER);
                mBoard.mOutOfMoneyCounter = 70;
                if ((((mBoard.mLevel == 1)) && ((Boolean)(mBoard.mHelpDisplayed.elementAt(Board.ADVICE_CLICK_ON_SUN))).booleanValue() ))
                {
                    mBoard.DisplayAdvice("[ADVICE_CANT_AFFORD_PLANT]", Board.MESSAGE_STYLE_TUTORIAL_LEVEL1, Board.ADVICE_CANT_AFFORD_PLANT);
                };
                return;
            };
        };
        mBoard.mCursorObject.mType = this.mPacketType;
        mBoard.mCursorObject.mCursorType = CURSOR_TYPE_PLANT_FROM_BANK;
        mBoard.mCursorObject.mSeedBankIndex = this.mIndex;
        app.foleyManager().playFoley(PVZFoleyType.SEEDLIFT);
        if (mBoard.mTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER)
        {
            mBoard.SetTutorialState(TUTORIAL_LEVEL_1_PLANT_PEASHOOTER);
        }
        else
        {
            if (mBoard.mTutorialState == TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER)
            {
                if (this.mPacketType == SEED_SUNFLOWER)
                {
                    mBoard.SetTutorialState(TUTORIAL_LEVEL_2_PLANT_SUNFLOWER);
                }
                else
                {
                    mBoard.SetTutorialState(TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER);
                };
            }
            else
            {
                if (mBoard.mTutorialState == TUTORIAL_MORESUN_PICK_UP_SUNFLOWER)
                {
                    if (this.mPacketType == SEED_SUNFLOWER)
                    {
                        mBoard.SetTutorialState(TUTORIAL_MORESUN_PLANT_SUNFLOWER);
                    }
                    else
                    {
                        mBoard.SetTutorialState(TUTORIAL_MORESUN_REFRESH_SUNFLOWER);
                    };
                };
            };
        };
        this.Deactivate();
    }

    private ImageInst mBufferedImage ;
    public boolean mActive ;

    public void  Deactivate (){
        this.mActive = false;
        this.mRefreshCounter = 0;
        this.mRefreshTime = 0;
        this.mRefreshing = false;
    }

    private FontInst mSeedPacketFont ;
    private int mImitaterType ;

    private void  drawBufferedImage (Graphics2D g ){
        ImageInst aSeedBackImg =null;
        ImageInst aPlantImg =null;
        int aCost ;
        int width ;
        int height ;
        int aTextOffsetX ;
        int aTextOffsetY ;
        aSeedBackImg = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDBACK);
        int w =aSeedBackImg.width() ;
        int h =aSeedBackImg.height() ;
//        w=40;
//        h=60;
//        bufferG.drawImage(aSeedBackImg, 0, 0);
        int aOffsetX =5;
        int aOffsetY =10;
        double aScale = 2;
        mX = (int)(mX);
        mY = (int)(mY);
        aPlantImg = new ImageInst(new ImageData(new BitmapData(MahJongImages.getMahJongImage(this.mCardType.name.toUpperCase()))));
        this.mBufferedImage = new ImageInst(new ImageData(new BitmapData(aPlantImg.width()*2, aPlantImg.height()*2, true, 0)));
        
        Graphics2D bufferG =this.mBufferedImage.graphics() ;
        System.out.println("seedpacket." + w+":"+h);
        
        /*
        switch (this.mPacketType)
        {
            case SEED_EXPLODE_O_NUT:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                aPlantImg.useColor = true;
                aPlantImg.setColor(1, 1, (64 / 0xFF), (64 / 0xFF));
                aOffsetX = (aOffsetX + 3);
                break;
            case SEED_PEASHOOTER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTERSINGLE);
                aOffsetY = (aOffsetY + 4);
                break;
            case SEED_SUNFLOWER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNFLOWER);
                aOffsetY = (aOffsetY + 1);
                aOffsetX = (aOffsetX + 2);
                break;
            case SEED_CHERRYBOMB:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHERRYBOMB);
                aOffsetY = (aOffsetY + 3);
                aOffsetX = (aOffsetX + -1);
                break;
            case SEED_SQUASH:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SQUASH);
                aOffsetX = (aOffsetX + 0);
                aOffsetY = (aOffsetY + -2);
                break;
            case SEED_CHOMPER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_CHOMPER);
                aOffsetX = (aOffsetX + -2);
                aOffsetY = (aOffsetY + -3);
                aScale = 0.6;
                break;
            case SEED_SNOWPEA:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SNOWPEA);
                aOffsetY = (aOffsetY + 3);
                break;
            case SEED_REPEATER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PEASHOOTER);
                aOffsetY = (aOffsetY + 5);
                break;
            case SEED_PUFFSHROOM:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_PUFFSHROOM);
                aOffsetY = (aOffsetY + 10);
                aOffsetX = (aOffsetX + 7);
                break;
            case SEED_SUNSHROOM:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_SUNSHROOM);
                aOffsetY = (aOffsetY + 10);
                aOffsetX = (aOffsetX + 7);
                break;
            case SEED_GRAVEBUSTER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_GRAVEDIGGER);
                aOffsetY = (aOffsetY + 0);
                aOffsetX = (aOffsetX + -3);
                aScale = 0.6;
                break;
            case SEED_FUMESHROOM:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_FUMESHROOM);
                aOffsetY = (aOffsetY + 1);
                aScale = 0.65;
                break;
            case SEED_WALLNUT:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT);
                aOffsetX = (aOffsetX + 3);
                break;
            case SEED_LEFTPEATER:
                aPlantImg = app.imageManager().getImageInst(PVZImages.IMAGE_LEFTFACINGPEASHOOTER);
                aOffsetX = (aOffsetX + 4);
                break;
        };
        */
        bufferG.pushState();
        bufferG.scale(aScale, aScale);
        bufferG.drawImage(aPlantImg, aOffsetX, aOffsetY);
        bufferG.popState();
        /*
        if (!mBoard.HasConveyorBeltSeedBank())
        {
            aCost = mBoard.GetCurrentPlantCost(this.mPacketType);
            width = (int)this.mSeedPacketFont.stringWidth("" + aCost);
            height = (int)this.mSeedPacketFont.getAscent();
            aTextOffsetX = (30 - width);
            aTextOffsetY = (40 + height);
            this.mSeedPacketFont.setColor(1, 0, 0, 0);
            bufferG.setFont(this.mSeedPacketFont);
            bufferG.drawString(""+ aCost, aTextOffsetX, aTextOffsetY);
        };
        */
    }
    public void  SlotMachineStart (){
    }
    public void  Activate (){
        this.mActive = true;
    }

    public int mRefreshTime ;
    private int mSlotMachiningNextSeed ;

    public void  Draw (Graphics2D g ){
//        if (!mVisible)
//        {
//            return;
//        };
    	
        if (this.mUpdateImage)
        {
            this.mUpdateImage = false;
            this.drawBufferedImage(g);
        };
        int aPercentDark =0;
        if (!this.mActive)
        {
            if (this.mRefreshTime == 0)
            {
                aPercentDark = 1;
            }
            else
            {
                aPercentDark = (this.mRefreshTime - this.mRefreshCounter) / this.mRefreshTime;
            };
        };
        int aUseSeedType =this.mPacketType ;
        boolean aDrawCost =true ;
        g.pushState();
        //g.scale(2.0f, 2.0f);
        
        if (mBoard.HasConveyorBeltSeedBank())
        {
            g.setClipRect(2, 0, 380, 405,false);
            aDrawCost = false;
        };
        int aCost =mBoard.GetCurrentPlantCost(this.mPacketType );
        double aGrayness =1;
        if (mBoard.mGameScene != PVZApp.SCENE_PLAYING)
        {
            aGrayness = 0.5;
            aPercentDark = 0;
        }
        else
        {
            if ((((((mBoard.mTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER)) && ((mBoard.mTutorialTimer == -1)))) && ((this.mPacketType == SEED_PEASHOOTER))))
            {
                aGrayness = TodCommon.GetFlashingColor(mBoard.mMainCounter, 75).red;
            }
            else
            {
                if ((((mBoard.mTutorialState == TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER)) && ((this.mPacketType == SEED_SUNFLOWER))))
                {
                    aGrayness = TodCommon.GetFlashingColor(mBoard.mMainCounter, 75).red;
                }
                else
                {
                    if ((((mBoard.mTutorialState == TUTORIAL_MORESUN_PICK_UP_SUNFLOWER)) && ((this.mPacketType == SEED_SUNFLOWER))))
                    {
                        aGrayness = TodCommon.GetFlashingColor(mBoard.mMainCounter, 75).red;
                    }
                    else
                    {
                        if (mBoard.mEasyPlantingCheat)
                        {
                            aGrayness = 1;
                            aPercentDark = 0;
                        }
                        else
                        {
                            if (((!(mBoard.CanTakeSunMoney(aCost))) && (aDrawCost)))
                            {
                                aGrayness = 0.5;
                            }
                            else
                            {
                                if (aPercentDark > 0)
                                {
                                    aGrayness = 0.5;
                                };
                            };
                        };
                    };
                };
            };
        };
        this.mBufferedImage.useColor = true;
        this.mBufferedImage.setColor(1, aGrayness, aGrayness, aGrayness);
        g.drawImage(this.mBufferedImage, (mX + this.mOffsetX), mY);
        g.setClipRect((mX + this.mOffsetX), mY, this.mBufferedImage.width(), (this.mBufferedImage.height() * aPercentDark), false);
        this.mBufferedImage.useColor = true;
        this.mBufferedImage.setColor(1, 0.25, 0.25, 0.25);
        g.drawImage(this.mBufferedImage, (mX + this.mOffsetX), mY);
//        System.out.println("SeedPacket" + mX +":"+ mY);
        g.popState();
    }

    public int mIndex ;

    public void  SetPacketType (int theSeedType ){
    }

    public int mRefreshCounter ;
    public int mPacketType ;

    public int  GetRefreshTime (int thePacketType ){
        int aRefreshTime ;
        switch (thePacketType)
        {
            case SEED_PEASHOOTER:
            case SEED_SUNFLOWER:
            case SEED_REPEATER:
            case SEED_SNOWPEA:
            case SEED_CHOMPER:
            case SEED_PUFFSHROOM:
            case SEED_GRAVEBUSTER:
            case SEED_FUMESHROOM:
            case SEED_SUNSHROOM:
                aRefreshTime = 750;
                break;
            case SEED_CHERRYBOMB:
                aRefreshTime = 5000;
                break;
            case SEED_WALLNUT:
            case SEED_SQUASH:
                aRefreshTime = 3000;
                break;
            default:
                aRefreshTime = 750;
        };
        return (aRefreshTime);
    }

    public boolean mRefreshing ;
    private int mSlotMachineCountDown ;
    public int mTimesUsed ;
    public int mOffsetX ;

    public boolean  CanPickUp (){
        if (((mBoard.mPaused) || (!((mBoard.mGameScene == Board.SCENE_PLAYING)))))
        {
            return (false);
        };
        if (this.mPacketType == SEED_NONE)
        {
            return (false);
        };
        if (mBoard.mEasyPlantingCheat)
        {
            return (true);
        };
        int aUseSeedType =this.mPacketType ;
        if (!this.mActive)
        {
            return (false);
        };
        int aCost =mBoard.GetCurrentPlantCost(this.mPacketType );
        if (((!(mBoard.CanTakeSunMoney(aCost))) && (!(mBoard.HasConveyorBeltSeedBank()))))
        {
            return (false);
        };
        return (true);
    }

    
    public CardType getCardType() {
    	return mCardType;
    }
}


