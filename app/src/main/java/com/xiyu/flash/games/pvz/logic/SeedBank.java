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

import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
//import flash.geom.Rectangle;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.utils.Utils;
import com.xiyu.flash.games.pvz.PVZApp;

    public class SeedBank extends GameObject {

        private static final int CURSOR_TYPE_PLANT_FROM_BANK =1;
        public static final int SEED_PUFFSHROOM =8;
        private static final int OBJECT_TYPE_PLANT =1;
        private static final int CURSOR_TYPE_HAMMER =4;
        public static final int SEED_SUNSHROOM =9;
        public static final int CONVEYOR_PACKET_OFFSET_X =96; //48*2;
        private static final int CURSOR_TYPE_PLANT_FROM_USABLE_COIN =2;
        private static final int OBJECT_TYPE_PROJECTILE =2;
        public static final int SEED_REPEATER =7;
        private static final int OBJECT_TYPE_NONE =0;
        private static final int CURSOR_TYPE_NORMAL =0;
        public static final int SEED_WALLNUT =3;
        public static final int SEED_FUMESHROOM =10;
        public static final int SEED_PEASHOOTER =0;
        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int SEEDBANK_MAX =10;
        private static final int CONVEYOR_SPEED =4;
        public static final int SEED_SUNFLOWER =1;
        public static final int SEED_SNOWPEA =5;
        private static final int OBJECT_TYPE_SHOVEL =5;
        public static final int SEED_CHERRYBOMB =2;
        public static final int SEED_CHOMPER =6;
        public static final int SEED_SQUASH =4;
        private static final int OBJECT_TYPE_SEEDPACKET =4;
        private static final int CURSOR_TYPE_SHOVEL =3;
        public static final int CONVEYOR_PACKET_MIN_OFFSET =80;//40;
        public static final int SEED_NONE =-1;
        public static final int SEED_LEFTPEATER =98;//49;
        private static final int OBJECT_TYPE_COIN =3;
        public static final int SEED_GRAVEBUSTER =11;

        private int mConveyorBeltCounter ;

        public boolean  MouseHitTest (int x ,int y ,HitResult theHitResult ){
            SeedPacket aSeedPacket ;
            int i =0;
            //add by xinghai
//            x = (int)(x/1.5);
//            y = y/3-20;
            while (i < this.mNumPackets)
            {
            	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);

            	if (aSeedPacket.MouseHitTest((x - mX), (y - mY), theHitResult))
                {
                	return (true);
                };
                i++;
            };
            theHitResult.mObject = null;
            theHitResult.mObjectType = OBJECT_TYPE_NONE;
            return (false);
        }

        public FontInst mSeedBankFont ;

        public void  AddSeed (int theSeedType ,boolean thePlaceOnLeft ){
            SeedPacket aSeedPacket ;
            SeedPacket aPrevSeedPacket ;
            int aNextIndex =this.GetNumSeedsOnConveyorBelt ();
            if (aNextIndex == this.mNumPackets)
            {
                return;
            };
            aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(aNextIndex);
            aSeedPacket.mUpdateImage = true;
            aSeedPacket.mPacketType = theSeedType;
            aSeedPacket.mRefreshCounter = 0;
            aSeedPacket.mRefreshTime = 0;
            aSeedPacket.mRefreshing = false;
            aSeedPacket.mActive = true;
            aSeedPacket.mOffsetX = (380 - (CONVEYOR_PACKET_OFFSET_X * aNextIndex));
            if (thePlaceOnLeft)
            {
                aSeedPacket.mOffsetX = 0;
            };
            if (aNextIndex > 0)
            {
            	aPrevSeedPacket=(SeedPacket)this.mSeedPackets.elementAt((aNextIndex-1));
                if (aSeedPacket.mOffsetX < aPrevSeedPacket.mOffsetX)
                {
                    aSeedPacket.mOffsetX = (aPrevSeedPacket.mOffsetX + CONVEYOR_PACKET_MIN_OFFSET);
                };
            };
            this.mSeedPackets.elementAt(aNextIndex);
        }
        public void  Draw (Graphics2D g ){
        	try {
        	ImageInst aImage ;
            int aFrame ;
            SeedPacket aSeedPacket ;
            int aCenterX ;
            int aCenterY ;
            int aPosX ;
            int aPosY ;
            if (!mVisible)
            {
                return;
            };
            g.pushState();
            g.translate(-(mBoard.x), -(mBoard.y));
            g.translate(mX, mY);
            aImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDBANK);
            
            if (mBoard.HasConveyorBeltSeedBank())
            {
                aImage = app.imageManager().getImageInst(PVZImages.IMAGE_CONVEYORBELT_BACKDROP);
                g.drawImage(aImage, 0, 0);
                aFrame = ((this.mConveyorBeltCounter / CONVEYOR_SPEED) % 6);
                aImage = app.imageManager().getImageInst(PVZImages.IMAGE_CONVEYORBELT);
                aImage.frame(aFrame);
                g.drawImage(aImage, 5, 56);
            }
            else
            {
                aImage = app.imageManager().getImageInst(PVZImages.IMAGE_SEEDBANK);
                g.drawImage(aImage, 0, 0);
            };
            
            int i =0;
            while (i < this.mNumPackets)
            {
            	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);
                if (aSeedPacket.mPacketType == SEED_NONE)
                {
                }
                else
                {
            		//System.out.println("package " + i+":"+aSeedPacket);
                	aSeedPacket.Draw(g);
                };
                i++;
            };
            if (((!(mBoard.HasConveyorBeltSeedBank())) && (this.mVisible)))
            {
                this.mSunText.setColor(1, 0, 0, 0);
                this.mSunText.useColor = true;
                if (mBoard.mOutOfMoneyCounter > 0)
                {
                    if ((mBoard.mOutOfMoneyCounter % 20) < 10)
                    {
                        this.mSunText.setColor(1, 1, 0, 0);
                    };
                };
                aCenterX = (this.mSunBounds.x + (this.mSunBounds.width() / 2));
                aCenterY = (this.mSunBounds.y + (this.mSunBounds.height() / 2));
                aPosX = (aCenterX - (this.mSunText.width() / 2));
                aPosY = (aCenterY - (this.mSunText.height() / 2));
                g.drawImage(this.mSunText, aPosX, aPosY);
            };
            g.popState();
        	} catch(Exception ex) {
        		System.out.println(ex.toString());
        	}
        }

        public ImageInst mSunText ;
        public Array mSeedPackets ;

        public int GetNumSeedsOnConveyorBelt (){
            SeedPacket aSeedPacket ;
            int i =0;
            while (i < this.mNumPackets)
            {
            	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);
                if (aSeedPacket.mPacketType == SEED_NONE)
                {
                    return (i);
                };
                i++;
            };
            return (this.mNumPackets);
        }

        public StringRenderable mSunRenderable ;

        public void  Move (int x ,int y ){
            mX = x;
            mY = y;
        }

        public Rectangle mSunBounds ;

        public int  CountOfTypeOnConveyorBelt (int aSeedType ){
            SeedPacket aSeedPacket ;
            int aCount =0;
            int i =0;
            while (i < this.mNumPackets)
            {
            	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);
                if (aSeedPacket.mPacketType == aSeedType)
                {
                    aCount++;
                };
                i++;
            };
            return (aCount);
        }

        private int mCutSceneDarken ;
        public int mNumPackets ;

        public void RemoveSeed (int theIndex ){
            SeedPacket aSeedPacket;
            SeedPacket aNextSeedPacket;
            int i =theIndex ;
            while (i < this.mNumPackets)
            {
            	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);
                if (aSeedPacket.mPacketType == SEED_NONE)
                {
                    break;
                };
                if (i == (this.mNumPackets - 1))
                {
                    aSeedPacket.mPacketType = SEED_NONE;
                    aSeedPacket.mOffsetX = 0;
                }
                else
                {
                	aNextSeedPacket=(SeedPacket)this.mSeedPackets.elementAt((i+1));
                    aSeedPacket.mPacketType = aNextSeedPacket.mPacketType;
                    aSeedPacket.mOffsetX = (aNextSeedPacket.mOffsetX + CONVEYOR_PACKET_OFFSET_X);
                };
                aSeedPacket.mUpdateImage = true;
                aSeedPacket.mRefreshCounter = 0;
                aSeedPacket.mRefreshTime = 0;
                aSeedPacket.mRefreshing = false;
                aSeedPacket.mActive = true;
                i++;
            };
        }
        public void  UpdateConveyorBelt (){
            int i =0;
            SeedPacket aSeedPacket ;
            this.mConveyorBeltCounter++;
            if ((this.mConveyorBeltCounter % CONVEYOR_SPEED) == 0)
            {
                i = 0;
                while (i < this.mNumPackets)
                {
                	aSeedPacket=(SeedPacket)this.mSeedPackets.elementAt(i);
                    if (aSeedPacket.mOffsetX > 0)
                    {
                        aSeedPacket.mOffsetX = Math.max((aSeedPacket.mOffsetX - 1), 0);
                    };
                    i++;
                };
            };
        }

        public  SeedBank (PVZApp app ,Board theBoard ){
            super();
            this.mSeedPackets = new Array();
            this.mSunBounds = new Rectangle(11, 59, 40, 15);
            this.mSeedBankFont = app.fontManager().getFontInst(PVZFonts.FONT_CONTINUUMBOLD14);
            this.mSeedBankFont.scale(0.8);
            mBoard = theBoard;
            this.app = app;
            if (mBoard.HasConveyorBeltSeedBank())
            {
                mWidth = 387;
            }
            else
            {
                mWidth = 401;
            };
            mHeight = 78;
            this.mNumPackets = 6;
            this.mCutSceneDarken = 0xFF;
            this.mConveyorBeltCounter = 0;
            mVisible = true;
            Rectangle bounds =new Rectangle(0,0,40,15);
            this.mSunText = Utils.createStringImage(""+mBoard.mSunMoney, this.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
            this.mSunText.useColor = true;
            this.mSunText.setColor(1, 0, 0, 0);
        }
    }


