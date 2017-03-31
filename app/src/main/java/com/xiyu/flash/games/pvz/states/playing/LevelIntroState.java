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

import com.pgh.mahjong.MJCards;
import com.xiyu.util.*;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.renderables.ZombieRenderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.logic.WeightedGridArray;

    public class LevelIntroState implements IState {

        public static int TimeRollSodStart =7500;
        public static int TimeRollSodEnd =(TimeRollSodStart +2000);

        public void  onEnter (){
            this.app.widgetManager().removeAllWidgets(true);
            if (this.app.mBoard != null)
            {
                this.app.widgetManager().removeWidget(this.app.mBoard);
                this.app.mBoard = null;
            };
            this.app.mBoard = new Board(this.app);
            this.app.widgetManager().addWidget(this.app.mBoard);
            this.app.widgetManager().setFocus(this.app.mBoard);
            this.app.widgetManager().addWidget(this.app.mBoard.mMenuButton);
            this.app.mBoard.mMenuButton.setVisible(false);
            this.app.mBoard.mSeedBank.mVisible = false;
            this.app.mBoard.mToolTip.mVisible = false;
            this.app.mBoard.mGameScene = PVZApp.SCENE_LEVEL_INTRO;
            this.app.mBoard.initLevel();
            this.app.mCutsceneTime = 0;
            boolean aHasSod =false;
            if (this.app.IsAdventureMode())
            {
                if ((((((this.app.mBoard.mLevel == 1)) || ((this.app.mBoard.mLevel == 2)))) || ((this.app.mBoard.mLevel == 4))))
                {
                    aHasSod = true;
                };
            };
            if (aHasSod)
            {
                this.app.mSodTime = (TimeRollSodEnd - TimeRollSodStart);
                this.app.mBoard.mSodPosition = 0;
            }
            else
            {
                this.app.mSodTime = 0;
                this.app.mBoard.mSodPosition = 1000;
            };
//            this.app.mBoard.PlaceStreetZombies();
            if (this.app.mBoard.IsWallnutBowlingLevel())
            {
                this.app.musicManager().playMusic(PVZMusic.CRAZY_DAVE, true, 50);
                this.app.stateManager().changeState(PVZApp.STATE_CRAZY_DAVE);
                //this.app.stateManager().changeState(PVZApp.STATE_PLAY_LEVEL);
            }
            else
            {
                if (this.app.IsScaryPotterLevel())
                {
                    this.app.mBoard.mSeedBank.Move(0, -78);
                    if (this.app.mShowedCrazyDaveVasebreaker)
                    {
                        this.app.musicManager().playMusic(PVZMusic.CEREBRAWL, true, 50);
                        //this.app.stateManager().changeState(PVZApp.STATE_START_LEVEL);
                        this.app.stateManager().changeState(PVZApp.STATE_START_LEVEL);
                    }
                    else
                    {
                        this.app.mShowedCrazyDaveVasebreaker = true;
                        this.app.musicManager().playMusic(PVZMusic.CRAZY_DAVE, true, 50);
                        //this.app.stateManager().changeState(PVZApp.STATE_CRAZY_DAVE);
                        //this.app.stateManager().changeState(PVZApp.STATE_PLAY_LEVEL);
                        this.app.stateManager().changeState(PVZApp.STATE_START_LEVEL);
                    };
                }
                else
                {
                    if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 11))))
                    {
                        this.app.musicManager().playMusic(PVZMusic.CRAZY_DAVE, true, 50);
                        this.app.stateManager().changeState(PVZApp.STATE_CRAZY_DAVE);
                    }
                    else
                    {
                        this.app.musicManager().playMusic(PVZMusic.CHOOSE_YOUR_SEEDS, true, 50);
                        //this.app.stateManager().changeState(PVZApp.STATE_CRAZY_DAVE);
                        //this.app.stateManager().changeState(PVZApp.STATE_PAN_RIGHT);
                        this.app.stateManager().changeState(PVZApp.STATE_PAN_LEFT);
                    };
                };
            };
        }

        private PVZApp app ;

        public void  PlaceStreetZombies (){
            int aZombieType =0;
            int aGridY =0;
            Array aZombieArray ;
            int aNumToShow =0;
            int aZombieIndex =0;
            if (this.app.mPlacedZombies)
            {
                return;
            };
            this.app.mPlacedZombies = true;
            int aTotalZombieCount =0;
            Array aZombieTypeCount =new Array ();
            int aZombieValueTotal =0;
            int i =0;
            while (i < 8)
            {
            	aZombieTypeCount.add(i,0);
                i++;
            };
            int aWaveIndex =0;
            while (aWaveIndex < this.app.mBoard.mNumWaves)
            {
                i = 0;
                while (i < Board.MAX_ZOMBIES_IN_WAVE)
                {
                	aZombieType=((Integer)((Array)this.app.mBoard.mZombiesInWave.elementAt(aWaveIndex)).elementAt(i)).intValue();
                    if (aZombieType == Board.ZOMBIE_INVALID)
                    {
                        break;
                    };
                    aZombieValueTotal = (aZombieValueTotal + this.app.mBoard.GetZombieDefinition(aZombieType).mZombieValue);
                    if (aZombieType == Board.ZOMBIE_FLAG)
                    {
                    }
                    else
                    {
                        Array _local14 =aZombieTypeCount ;
                        int _local15 =aZombieType ;
                        int _local16= ((Integer)_local14.elementAt(_local15)).intValue() + 1;
                        _local14.add(_local15,_local16);
                        aTotalZombieCount++;
                    };
                    i++;
                };
                aWaveIndex++;
            };
            Array aZombieGrid =new Array ();
            int aGridX =0;
            while (aGridX < 5)
            {
                aGridY = 0;
                while (aGridY < 5)
                {
                    aZombieArray = new Array();
                    aZombieGrid.push(aZombieArray);
                    ((Array)aZombieGrid.elementAt(aGridX)).add(aGridY,false);
                    aGridY++;
                };
                aGridX++;
            };
            int aApproxNumberToShow =10;
            aZombieType = 0;
            while (aZombieType < 7)
            {
            	if(((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue()==0)
                {
                }
                else
                {
                	aNumToShow=(( ((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue() * aApproxNumberToShow)/aTotalZombieCount);
                	aNumToShow=TodCommon.ClampInt(aNumToShow,1,((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue() );
                    aZombieIndex = 0;
                    while (aZombieIndex < aNumToShow)
                    {
                        this.FindAndPlaceZombie((int)(aZombieType), aZombieGrid);
                        aZombieIndex++;
                    };
                };
                aZombieType++;
            };
        }

        public void  draw (Canvas canvas ){
  			if(canvas ==null)
  				return;
  			Graphics2D g = new Graphics2D(canvas);
//  			this.app.mBoard.draw(g);
            this.app.widgetManager().drawScreen(g);
  		}

  		public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  PlaceAZombie (int theZombieType ,int theGridX ,int theGridY ){
            Zombie aZombie =new Zombie ();
            boolean aVariant =false;
            if ((Math.random() * 5) == 0)
            {
                aVariant = true;
            };
            aZombie.ZombieInitialize(MJCards.getInstance().fabai(),0, theZombieType, aVariant, null, -2, this.app, this.app.mBoard);
            this.app.mBoard.mZombies.push(aZombie);
            aZombie.mPosX = (600 + (28 * theGridX));
            aZombie.mPosY = (77 + (50 * theGridY));
            if ((theGridX % 2) == 1)
            {
                aZombie.mPosY = (aZombie.mPosY + 21);
            }
            else
            {
                aZombie.mPosY = (int)(aZombie.mPosY + (Math.random() * 10));
                aZombie.mPosX = (int)(aZombie.mPosX + (Math.random() * 10));
            };
            this.app.mBoard.mRenderManager.add(new ZombieRenderable(aZombie, (int)(Board.RENDER_LAYER_ZOMBIE + aZombie.mPosY)));
        }
        public void  onPush (){
        }

        private ImageInst boardImg ;

        public boolean  CanZombieGoInGridSpot (int theZombieType ,int theGridX ,int theGridY ,Array theZombieGrid ){
        	if(((Boolean)((Array)theZombieGrid.elementAt(theGridX)).elementAt(theGridY)).booleanValue())
            {
                return (false);
            };
            if ((((theGridX == 4)) && ((theGridY == 0))))
            {
                return (false);
            };
            if (theZombieType == Board.ZOMBIE_POLEVAULTER)
            {
                if (theGridX == 0)
                {
                    return (false);
                };
                if ((((theGridX == 1)) && ((theGridY == 0))))
                {
                    return (false);
                };
            };
            return (true);
        }
        public void  FindAndPlaceZombie (int theZombieType ,Array theZombieGrid ){
            int aGridX =0;
            int aGridY =0;
            this.FindPlaceForStreetZombies(theZombieType, theZombieGrid, aGridX, aGridY);
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
            if (this.app.resourceManager().isLoading())
            {
                return;
            };
        }
        public void  onPop (){
        }
        public void  FindPlaceForStreetZombies (int theZombieType ,Array theZombieGrid ,int thePosX ,int thePosY ){
            int y =0;
            WeightedGridArray aWeightedGridArray ;
            Array aPicks =new Array ();
            int aPickCount =0;
            int x =0;
            while (x < 5)
            {
                y = 0;
                while (y < 5)
                {
                    if (!this.CanZombieGoInGridSpot(theZombieType, x, y, theZombieGrid))
                    {
                    }
                    else
                    {
                        aWeightedGridArray = new WeightedGridArray();
                        aPicks.push(aWeightedGridArray);
                        ((WeightedGridArray)aPicks.elementAt(aPickCount)).mX=x;
                        ((WeightedGridArray)aPicks.elementAt(aPickCount)).mY=y;
                        ((WeightedGridArray)aPicks.elementAt(aPickCount)).mWeight=1;
                        aPickCount++;
                    };
                    y++;
                };
                x++;
            };
            if (aPickCount == 0)
            {
                thePosX = 2;
                thePosY = 2;
                return;
            };
            WeightedGridArray aResultItem =TodCommon.TodPickFromWeightedGridArray(aPicks ,aPickCount );
            thePosX = aResultItem.mX;
            thePosY = aResultItem.mY;
            ((Array)theZombieGrid.elementAt(thePosX)).add(thePosY,true);
            this.PlaceAZombie(theZombieType, thePosX, thePosY);
        }
        public void  onExit (){
            this.app.mPlacedZombies = false;
        }

        public  LevelIntroState (PVZApp app ){
            this.app = app;
//            app.start();
//            onEnter ();
        }

        public void setView() {
			app.getMainView().setState(this);
        }
    }


