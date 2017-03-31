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

import com.xiyu.flash.games.pvz.logic.Plants.CPlant;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.renderables.ReanimationRenderable;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.renderables.GridItemRenderable;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.resources.PVZImages;

    public class Challenge {

        private static final int OBJECT_TYPE_PLANT =1;
        private static final int CURSOR_TYPE_HAMMER =4;
        public static final int FLAG_RAISE_TIME =100;
        public static final int GRIDSQUARE_NONE =0;
        public static final int REANIM_CHERRYBOMB =3;
        public static final int BACKGROUND_1_DAY =0;
        private static final int CURSOR_TYPE_NORMAL =0;
        private static final int OBJECT_TYPE_NONE =0;
        public static final int ZOMBIE_DOOR =6;
        public static final int SCARYPOT_SUN =3;
        private static final int SCENE_CREDIT =6;
        private static  String COIN_NONE ="none";
        private static final int SCENE_CHALLENGE =7;
        public static final int PLANTROW_DIRT =1;
        public static final int GRIDSQUARE_GRASS =1;
        private static final int LAWN_XMIN =20;
        public static final int SCARYPOT_SEED =1;
        public static final int MAX_ZOMBIE_WAVES =100;
        private static final int OBJECT_TYPE_SHOVEL =5;
        private static final int SCENE_LOADING =0;
        public static final int GRIDITEM_STATE_SCARY_POT_LEAF =4;
        public static final int GRIDITEM_STATE_SCARY_POT_QUESTION =3;
        private static final int SEED_NONE =-1;
        private static final int SEED_CHERRYBOMB =2;
        private static final int SCENE_LEVEL_INTRO =2;
        public static final int GRIDSQUARE_DIRT =2;
        public static final int RENDER_LAYER_GROUND =200000;
        public static final int PROGRESS_METER_COUNTER =100;
        private static  String COIN_FINAL_SEED_PACKET ="finalseedpacket";
        public static final int MAX_SCARY_POTS =(9*6);//54
        private static final int LAWN_YMIN =77;
        public static final int SEED_FUMESHROOM =10;
        private static final int SCENE_AWARD =5;
        private static final int ZOMBIE_COUNTDOWN_MIN =400;
        private static final int ZOMBIE_COUNTDOWN_FIRST_WAVE =1800;
        private static final int SEED_SNOWPEA =5;
        public static final int ZOMBIE_INVALID =-1;
        private static final int BOARD_HEIGHT =405;
        private static  String COIN_MOTION_COIN ="coin";
        private static final int SEED_SQUASH =4;
        private static final int SCENE_ZOMBIES_WON =4;
        private static final int ZOMBIE_COUNTDOWN_BEFORE_REPICK =5500;
        public static final int SCARYPOT_DYNAMITE =4;
        public static final int REANIM_PEASHOOTER =0;
        public static final int STATECHALLENGE_NORMAL =0;
        public static final int SEED_LEFTPEATER =49;
        private static final int BOARD_WIDTH =540;
        public static final int ZOMBIE_POLEVAULTER =3;
        public static final int REANIM_WALLNUT =1;
        private static final int GRIDSIZEX =9;
        private static final int GRIDSIZEY =5;
        public static final int RENDER_LAYER_COIN_BANK =600000;
        public static final int SEED_GRAVEBUSTER =11;
        public static final int SCARYPOT_NONE =0;
        private static final int CURSOR_TYPE_PLANT_FROM_BANK =1;
        public static final int SEED_PUFFSHROOM =8;
        private static final int OBJECT_TYPE_PROJECTILE =2;
        private static final int CURSOR_TYPE_PLANT_FROM_USABLE_COIN =2;
        private static final int SEED_PEASHOOTER =0;
        public static final int REANIM_SQUASH =4;
        public static final int ZOMBIE_NORMAL =0;
        public static final int RENDER_LAYER_FOG =500000;
        private static final int SEED_EXPLODE_O_NUT =50;
        private static final int ZOMBIE_COUNTDOWN =2500;
        private static final int NUM_ZOMBIE_TYPES =8;
        private static final int SCENE_PLAYING =3;
        private static final int LAWN_XMAX =513;
        private static final int SEED_SUNFLOWER =1;
        private static final int SUN_COUNTDOWN_MAX =950;
        private static final int SEED_CHOMPER =6;
        public static final int ZOMBIE_PAIL =4;
        private static final int SCENE_MENU =1;
        private static final int ZOMBIE_COUNTDOWN_RANGE =600;
        private static final int SUN_COUNTDOWN_RANGE =275;
        private static final int OBJECT_TYPE_SEEDPACKET =4;
        public static final int ZOMBIE_FOOTBALL =7;
        public static final int SCARYPOT_ZOMBIE =2;
        public static final int GRIDITEM_STATE_PORTAL_CLOSED =2;
        private static  String COIN_MOTION_FROM_PLANT ="from plant";
        private static final int OBJECT_TYPE_COIN =3;
        private static  String COIN_SUN ="sun";
        private static  String PLANTING_OK ="ok";
        public static final int MAX_ZOMBIES_IN_WAVE =50;
        public static final int SEED_SUNSHROOM =9;
        private static final int SEED_WALLNUT =3;
        private static final int SEED_REPEATER =7;
        public static final int PLANTROW_NORMAL =0;
        private static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
        public static final int GRIDITEM_STATE_NORMAL =0;
        public static final int GRIDITEM_STATE_GRAVESTONE_SPECIAL =1;
        private static final int LAWN_YMAX =385;
        public static final int BOARD_SHAKE_TIME =12;
        public static final int ZOMBIE_NEWSPAPER =5;
        public static final int RENDER_LAYER_LAWN =300000;
        private static  String PLANTING_NOT_HERE ="not here";
        private static  String COIN_MOTION_FROM_SKY ="from sky";
        private static final int ZOMBIE_COUNTDOWN_BEFORE_FLAG =4500;
        public static final int RENDER_LAYER_ABOVE_UI =800000;
        public static final int STATECHALLENGE_SCARY_POTTER_MALLETING =1;
        public static final int RENDER_LAYER_PARTICLE =(RENDER_LAYER_LAWN +7000);//307000
        private static  String COIN_USABLE_SEED_PACKET ="usableseedpacket";
        private static final int SUN_COUNTDOWN =425;
        public static final int GRIDITEM_STATE_SCARY_POT_ZOMBIE =5;
        public static final int REANIM_SUNFLOWER =2;
        public static final int ZOMBIE_TRAFFIC_CONE =2;
        public static final int REANIM_NONE =-1;
        public static final int RENDER_LAYER_TOP =400000;
        private static final int CURSOR_TYPE_SHOVEL =3;
        public static final int RENDER_LAYER_UI_TOP =700000;
        public static final int ZOMBIE_FLAG =1;
        public static final int RENDER_LAYER_SCREEN_FADE =900000;
        public static final int RENDER_LAYER_UI_BOTTOM =100000;
        public static final int RENDER_LAYER_PLANT =(RENDER_LAYER_LAWN +2000);//302000
        public static final int RENDER_LAYER_LAWN_MOWER =(RENDER_LAYER_LAWN +6000);//306000
        public static final int RENDER_LAYER_GRAVE_STONE =(RENDER_LAYER_LAWN +1000);//301000
        public static final int RENDER_LAYER_PROJECTILE =(RENDER_LAYER_LAWN +5000);//305000
        public static final int RENDER_LAYER_ZOMBIE =(RENDER_LAYER_LAWN +3000);//303000

        public int mConveyorBeltCounter ;

        public GridItem  GetScaryPotAt (int theGridX ,int theGridY ){
            GridItem aGridItem ;
			for(int i =0; i<this.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mBoard.mGridItems.elementAt(i);
                if (((!((aGridItem.mGridX == theGridX))) || (!((aGridItem.mGridY == theGridY)))))
                {
                }
                else
                {
                    return (aGridItem);
                };
            };
            return (null);
        }
        public boolean  MouseUp (int x ,int y ){
            return (false);
        }

        private Board mBoard ;

        public int  ScaryPotterCountPots (){
            GridItem aGridItem ;
            int aPots =0;
			for(int i =0; i<this.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mBoard.mGridItems.elementAt(i);
                if (aGridItem.mGridItemType == GridItem.GRIDITEM_SCARY_POT)
                {
                    aPots++;
                };
            };
            return (aPots);
        }
        public void  GraveDangerSpawnRandomGrave (){
            int y =0;
            WeightedGridArray aWeightedGridArray ;
            CPlant aPlant =null;
            Array aPicks =new Array ();
            int aPickCount =0;
            int x =4;
            while (x < GRIDSIZEX)
            {
                y = 0;
                while (y < GRIDSIZEY)
                {
                    aWeightedGridArray = new WeightedGridArray();
                    aPicks.add(aPickCount,aWeightedGridArray);
                    if (!this.mBoard.CanAddGraveStoneAt(x, y))
                    {
                    }
                    else
                    {
                        aPlant = this.mBoard.GetPlantsOnLawn(x, y);
                        if (aPlant!=null)
                        {
                        	((WeightedGridArray)aPicks.elementAt(aPickCount)).mWeight=1;
                        }
                        else
                        {
                        	((WeightedGridArray)aPicks.elementAt(aPickCount)).mWeight=100000;
                        };
                        ((WeightedGridArray)aPicks.elementAt(aPickCount)).mX=x;
                        ((WeightedGridArray)aPicks.elementAt(aPickCount)).mY=y;
                        aPickCount++;
                    };
                    y++;
                };
                x++;
            };
            if (aPickCount == 0)
            {
                return;
            };
            WeightedGridArray aResultItem =TodCommon.TodPickFromWeightedGridArray(aPicks ,aPickCount );
            this.GraveDangerSpawnGraveAt(aResultItem.mX, aResultItem.mY);
        }

        public int mScaryPotterPots ;

        public boolean  UpdateZombieSpawning (){
            if (this.app.IsScaryPotterLevel())
            {
                return (true);
            };
            return (false);
        }
        public void  PuzzlePhaseComplete (int theGridX ,int theGridY ){
            if (this.app.mGameMode == PVZApp.GAMEMODE_SCARY_POTTER_ENDLESS)
            {
                if ((this.mSurvivalStage + 1) == 15)
                {
                };
            };
            this.mBoard.FadeOutLevel();
        }
        public void  ScaryPotterJackExplode (int aPosX ,int aPosY ){
            GridItem aGridItem ;
            int aGridX =this.mBoard.PixelToGridX(aPosX ,aPosY );
            int aGridY =this.mBoard.PixelToGridY(aPosX ,aPosY );
			for(int i =0; i<this.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mBoard.mGridItems.elementAt(i);
                if (aGridItem.mGridItemType != GridItem.GRIDITEM_SCARY_POT)
                {
                }
                else
                {
                    if ((((((((aGridItem.mGridX < (aGridX - 1))) || ((aGridItem.mGridX > (aGridX + 1))))) || ((aGridItem.mGridY < (aGridY - 1))))) || ((aGridItem.mGridY > (aGridY + 1)))))
                    {
                    }
                    else
                    {
                        this.ScaryPotterOpenPot(aGridItem);
                    };
                };
            };
        }

        private PVZApp app ;

        public void  InitZombieWavesSurvival (){
            int aZombieType ;
            this.mBoard.mZombieAllowed.put(ZOMBIE_NORMAL,true);
            double aEasyZombieRand =(Math.random ()*5);
            if (aEasyZombieRand == 0)
            {
            	this.mBoard.mZombieAllowed.put(ZOMBIE_NEWSPAPER,true);
            }
            else
            {
            	this.mBoard.mZombieAllowed.put(ZOMBIE_TRAFFIC_CONE,true);
            };
            int aNumZombieTypes =Math.min ((this.mSurvivalStage +1),5);
            while (aNumZombieTypes > 0)
            {
                aZombieType = (int)(Math.random() * NUM_ZOMBIE_TYPES);
                if(((Boolean)this.mBoard.mZombieAllowed.elementAt(aZombieType)).booleanValue())
                {
                }
                else
                {
                	this.mBoard.mZombieAllowed.put(aZombieType,true);
                    aNumZombieTypes--;
                };
            };
        }
        public void  GraveDangerSpawnGraveAt (int x ,int y ){
            CPlant aPlant ;
            GridItem aGraveStone ;
			for(int i =0; i<this.mBoard.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mBoard.mPlants.elementAt(i);
                if ((((aPlant.mPlantCol == x)) && ((aPlant.mRow == y))))
                {
                    aPlant.Die();
                };
            };
            this.mBoard.mEnableGraveStones = true;
            aGraveStone = this.mBoard.AddAGraveStone(x, y);
            aGraveStone.AddGraveStoneParticles();
        }
        public void  ScaryPotterDontPlaceInCol (int theCol ,Array theGridArray ,int theGridArrayCount ){
            int i =0;
            while (i < theGridArrayCount)
            {
            	if(((WeightedGridArray)theGridArray.elementAt(i)).mX==theCol)
                {
            		((WeightedGridArray)theGridArray.elementAt(i)).mWeight=0;
                };
                i++;
            };
        }
        public void  ScaryPotterUpdate (){
            GridItem aGridItem =null;
            if (this.mChallengeState == STATECHALLENGE_SCARY_POTTER_MALLETING)
            {
                if (this.mMalletReanim.mIsDead)
                {
                    aGridItem = this.GetScaryPotAt(this.mChallengeGridX, this.mChallengeGridY);
                    if (aGridItem!=null)
                    {
                        this.ScaryPotterOpenPot(aGridItem);
                    };
                    this.mChallengeGridX = 0;
                    this.mChallengeGridY = 0;
                    this.mMalletReanim.mIsDead = true;
                    this.mMalletReanim = null;
                    this.mChallengeState = STATECHALLENGE_NORMAL;
                };
            };
        }
        public void  ScaryPotterMalletPot (GridItem theScaryPot ){
            this.mChallengeGridX = theScaryPot.mGridX;
            this.mChallengeGridY = theScaryPot.mGridY;
            int aXPos =this.mBoard.GridToPixelX(theScaryPot.mGridX ,theScaryPot.mGridY );
            int aYPos =this.mBoard.GridToPixelY(theScaryPot.mGridX ,theScaryPot.mGridY );
            this.mMalletReanim = this.app.reanimator().createReanimation("REANIM_HAMMER");
            this.mMalletReanim.x(aXPos);
            this.mMalletReanim.y(aYPos);
            this.mMalletReanim.currentTrack("anim_open_pot");
            this.mMalletReanim.animRate(40);
            this.mMalletReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
            this.mBoard.mRenderManager.add(new ReanimationRenderable(this.mMalletReanim, Board.RENDER_LAYER_ABOVE_UI, true));
            this.mChallengeState = STATECHALLENGE_SCARY_POTTER_MALLETING;
            this.app.foleyManager().playFoley(PVZFoleyType.SWING);
        }
        public void  ScaryPotterPlacePot (int theScaryPotType ,int theZombieType ,int theSeedType ,int theCount ,Array theGridArray ,int theGridArrayCount ){
            WeightedGridArray aResult ;
            GridItem aGridItem ;
            int i =0;
            System.out.println("ScaryPotterPlacePot " + theCount);
            while (i < theCount)
            {
                aResult = TodCommon.TodPickFromWeightedGridArray(theGridArray, theGridArrayCount);
                aResult.mWeight = 0;
                aGridItem = new GridItem(this.app, this.mBoard);
                aGridItem.mGridItemType = GridItem.GRIDITEM_SCARY_POT;
                aGridItem.mGridItemState = GridItem.GRIDITEM_STATE_SCARY_POT_QUESTION;
                aGridItem.mGridX = aResult.mX;
                aGridItem.mGridY = aResult.mY;
                aGridItem.mSeedType = theSeedType;
                aGridItem.mZombieType = theZombieType;
                aGridItem.mScaryPotType = theScaryPotType;
                this.mBoard.mGridItems.push(aGridItem);
                this.mBoard.mRenderManager.add(new GridItemRenderable(aGridItem, RENDER_LAYER_PLANT));
                if (theScaryPotType == SCARYPOT_SUN)
                {
                    aGridItem.mSunCount = TodCommon.RandRangeInt(1, 3);
                };
                i++;
            };
        }
        public void  InitLevel (){
            if (this.mBoard.mLevel == 5)
            {
                this.mBoard.NewPlant(5, 1, SEED_PEASHOOTER);
                this.mBoard.NewPlant(7, 2, SEED_PEASHOOTER);
                this.mBoard.NewPlant(6, 3, SEED_PEASHOOTER);
            };
            if (this.app.IsScaryPotterLevel())
            {
                this.ScaryPotterPopulate();
            };
        }

        public int mChallengeState ;
        public int mChallengeScore ;

        public void  PuzzleNextStageClear (){
            Zombie aZombie ;
            CPlant aPlant ;
            Coin aCoin ;
            GridItem aGridItem ;
            ParticleSystem anEffect ;
            this.app.foleyManager().playFoley(PVZFoleyType.HUGEWAVE);
            this.mBoard.mNextSurvivalStageCounter = 0;
            this.mBoard.mProgressMeterWidth = 0;
			for(int i =0; i<this.mBoard.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mBoard.mZombies.elementAt(i);
                if (aZombie.IsOnBoard())
                {
                    aZombie.DieNoLoot();
                };
            };
			for(int i =0; i<this.mBoard.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mBoard.mPlants.elementAt(i);
                if (aPlant.IsOnBoard())
                {
                    aPlant.Die();
                };
            };
            this.mBoard.RefreshSeedPacketFromCursor();
			for(int i =0; i<this.mBoard.mCoins.length();i++)
			{
				aCoin = (Coin)this.mBoard.mCoins.elementAt(i);
                if (aCoin.mType == COIN_USABLE_SEED_PACKET)
                {
                    aCoin.Die();
                };
            };
			for(int i =0; i<this.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mBoard.mGridItems.elementAt(i);
                aGridItem.GridItemDie();
            };
            this.mSurvivalStage++;
            this.mBoard.ClearAdviceImmediately();
            this.mBoard.mLevelAwardSpawned = false;
            anEffect = this.app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SCREENFLASH);
            anEffect.setPosition(0, 0);
            this.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_SCREEN_FADE));
        }

        public int mChallengeStateCounter ;

        public void  InitZombieWaves (){
            Array aZombieList ;
            if (this.app.IsSurvivalMode())
            {
                if (this.mSurvivalStage == 0)
                {
                    int[] iZombieList = {ZOMBIE_NORMAL, ZOMBIE_TRAFFIC_CONE, ZOMBIE_PAIL};
                	aZombieList = new Array(iZombieList);
                    this.InitZombieWavesFromList(aZombieList, aZombieList.length());
                }
                else
                {
                    this.InitZombieWavesSurvival();
                };
            };
        }
        public boolean  MouseDown (int x ,int y ,HitResult theHitResult ){
            if (this.mBoard.mGameScene != SCENE_PLAYING)
            {
                return (false);
            };
            if (theHitResult.mObjectType == OBJECT_TYPE_COIN)
            {
                return (false);
            };
            if (((this.app.IsScaryPotterLevel()) && ((theHitResult.mObjectType == Board.OBJECT_TYPE_SCARY_POT))))
            {
                this.ScaryPotterMalletPot((GridItem)theHitResult.mObject);
                this.mBoard.ClearCursor();
                return (true);
            };
            return (false);
        }
        public void  InitZombieWavesFromList (Array theZombieList ,int theListLength ){
            int aZombieType ;
            int i =0;
            while (i < theListLength)
            {
            	aZombieType=((Integer)theZombieList.elementAt(i)).intValue();
            	this.mBoard.mZombieAllowed.put(aZombieType, true);
                i++;
            };
        }
        public void  ScaryPotterPopulate (){
            int y =0;
            WeightedGridArray aGridItem ;
            int aNumExtraGargantuars ;
            Array aGridArray =new Array ();
            int aGridArrayCount =0;
            int x =0;
            while (x < 9)
            {
                y = 0;
                while (y < 5)
                {
                    aGridItem = new WeightedGridArray();
                    aGridItem.mX=x;
                    aGridItem.mY=y;
                    aGridItem.mWeight=1;
                    aGridArray.add(aGridArrayCount,aGridItem);
                    aGridArrayCount++;
                    y++;
                };
                x++;
            };
            //add by xinghai
            this.app.mGameMode = PVZApp.GAMEMODE_SCARY_POTTER_ENDLESS;
            
            if (this.app.mGameMode == PVZApp.GAMEMODE_SCARY_POTTER_ENDLESS)
            {
                if (this.mSurvivalStage == 0)
                {
                    this.ScaryPotterDontPlaceInCol(0, aGridArray, aGridArrayCount);
                    this.ScaryPotterDontPlaceInCol(1, aGridArray, aGridArrayCount);
                    this.ScaryPotterDontPlaceInCol(2, aGridArray, aGridArrayCount);
                    this.ScaryPotterDontPlaceInCol(3, aGridArray, aGridArrayCount);
                    this.ScaryPotterDontPlaceInCol(4, aGridArray, aGridArrayCount);
                    this.ScaryPotterDontPlaceInCol(5, aGridArray, aGridArrayCount);
                    this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_PEASHOOTER, 5, aGridArray, aGridArrayCount);
                    this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SQUASH, 5, aGridArray, aGridArrayCount);
                    this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_NORMAL, SEED_NONE, 4, aGridArray, aGridArrayCount);
                    this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_PAIL, SEED_NONE, 1, aGridArray, aGridArrayCount);
                }
                else
                {
                    if (this.mSurvivalStage == 1)
                    {
                        this.ScaryPotterDontPlaceInCol(0, aGridArray, aGridArrayCount);
                        this.ScaryPotterDontPlaceInCol(1, aGridArray, aGridArrayCount);
                        this.ScaryPotterDontPlaceInCol(2, aGridArray, aGridArrayCount);
                        this.ScaryPotterDontPlaceInCol(3, aGridArray, aGridArrayCount);
                        this.ScaryPotterDontPlaceInCol(4, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_PEASHOOTER, 4, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SQUASH, 4, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SNOWPEA, 5, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_NORMAL, SEED_NONE, 5, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_PAIL, SEED_NONE, 1, aGridArray, aGridArrayCount);
                        this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_FOOTBALL, SEED_NONE, 1, aGridArray, aGridArrayCount);
                        this.ScaryPotterChangePotType(GRIDITEM_STATE_SCARY_POT_LEAF, 2);
                    }
                    else
                    {
                        if (this.mSurvivalStage == 2)
                        {
                            this.ScaryPotterDontPlaceInCol(0, aGridArray, aGridArrayCount);
                            this.ScaryPotterDontPlaceInCol(1, aGridArray, aGridArrayCount);
                            this.ScaryPotterDontPlaceInCol(2, aGridArray, aGridArrayCount);
                            this.ScaryPotterDontPlaceInCol(8, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_LEFTPEATER, 7, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SQUASH, 2, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SNOWPEA, 3, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_WALLNUT, 3, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_NORMAL, SEED_NONE, 6, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_PAIL, SEED_NONE, 3, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_DYNAMITE, ZOMBIE_INVALID, SEED_NONE, 1, aGridArray, aGridArrayCount);
                            this.ScaryPotterChangePotType(GRIDITEM_STATE_SCARY_POT_LEAF, 2);
                        }
                        else
                        {
                            aNumExtraGargantuars = TodCommon.ClampInt((this.mSurvivalStage / 10), 0, 8);
                            this.ScaryPotterDontPlaceInCol(0, aGridArray, aGridArrayCount);
                            this.ScaryPotterDontPlaceInCol(1, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_LEFTPEATER, 8, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SNOWPEA, 2, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_PEASHOOTER, 2, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_SQUASH, 5, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SEED, ZOMBIE_INVALID, SEED_WALLNUT, 2, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_SUN, ZOMBIE_INVALID, SEED_NONE, 1, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_NORMAL, SEED_NONE, (8 - aNumExtraGargantuars), aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_PAIL, SEED_NONE, 5, aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_ZOMBIE, ZOMBIE_FOOTBALL, SEED_NONE, (1 + aNumExtraGargantuars), aGridArray, aGridArrayCount);
                            this.ScaryPotterPlacePot(SCARYPOT_DYNAMITE, ZOMBIE_INVALID, SEED_NONE, 1, aGridArray, aGridArrayCount);
                            this.ScaryPotterChangePotType(GRIDITEM_STATE_SCARY_POT_LEAF, 2);
                        };
                    };
                };
            };
        }
        public void  ScaryPotterOpenPot (GridItem theScaryPot ){
            Coin aCoin ;
            String aParticleType ;
            Zombie aZombie ;
            int aNumSum ;
            int i =0;
            int aXPos =this.mBoard.GridToPixelX(theScaryPot.mGridX ,theScaryPot.mGridY );
            int aYPos =this.mBoard.GridToPixelY(theScaryPot.mGridX ,theScaryPot.mGridY );
            switch (theScaryPot.mScaryPotType)
            {
                case SCARYPOT_DYNAMITE:
                    aCoin = this.mBoard.AddCoin(aXPos, aYPos, Coin.COIN_DYNAMITE, COIN_MOTION_COIN);
                    break;
                case SCARYPOT_SEED:
                    aCoin = this.mBoard.AddCoin((aXPos + 20), aYPos, COIN_USABLE_SEED_PACKET, COIN_MOTION_FROM_PLANT);
                    aCoin.mUsableSeedType = theScaryPot.mSeedType;
                    break;
                case SCARYPOT_ZOMBIE:
                    aZombie = this.mBoard.AddZombieInRow(theScaryPot.mZombieType, theScaryPot.mGridY, 0);
                    aZombie.mPosX = aXPos;
                    break;
                case SCARYPOT_SUN:
                    aNumSum = theScaryPot.mSunCount;
                    i = 0;
                    while (i < aNumSum)
                    {
                        this.mBoard.AddCoin(aXPos, aYPos, COIN_SUN, COIN_MOTION_FROM_PLANT);
                        aXPos = (aXPos + 15);
                        i++;
                    };
                    break;
            };
            theScaryPot.GridItemDie();
            if (this.ScaryPotterIsCompleted())
            {
                this.PuzzlePhaseComplete(theScaryPot.mGridX, theScaryPot.mGridY);
            };
            this.app.foleyManager().playFoley(PVZFoleyType.BONK);
            this.app.foleyManager().playFoley(PVZFoleyType.VASE_BREAKING);
            if (theScaryPot.mGridItemState == GRIDITEM_STATE_SCARY_POT_LEAF)
            {
                aParticleType = PVZParticles.PARTICLE_VASESHATTERLEAF;
            }
            else
            {
                if (theScaryPot.mGridItemState == GRIDITEM_STATE_SCARY_POT_ZOMBIE)
                {
                    aParticleType = PVZParticles.PARTICLE_VASESHATTERZOMBIE;
                }
                else
                {
                    aParticleType = PVZParticles.PARTICLE_VASESHATTER;
                };
            };
            ParticleSystem anEffect =this.app.particleManager().spawnParticleSystem(aParticleType );
            anEffect.setPosition((aXPos + 20), aYPos);
            this.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, (Board.RENDER_LAYER_PARTICLE + 1)));
        }
        public boolean  ScaryPotterIsCompleted (){
            GridItem aGridItem ;
			for(int i =0; i<this.mBoard.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mBoard.mGridItems.elementAt(i);
                if ((((aGridItem.mGridItemType == GridItem.GRIDITEM_SCARY_POT)) && (!(aGridItem.mDead))))
                {
                    return (false);
                };
            };
            if (this.mBoard.AreEnemyZombiesOnScreen())
            {
                return (false);
            };
            return (true);
        }
        public void  StartLevel (){
            String aMessage ;
            if (this.mBoard.IsWallnutBowlingLevel())
            {
                this.mBoard.mZombieCountDown = 200;
                this.mBoard.mZombieCountDownStart = this.mBoard.mZombieCountDown;
                this.mBoard.mSeedBank.AddSeed(SEED_WALLNUT, false);
                this.mConveyorBeltCounter = 400;
            };
            if (this.mBoard.IsWallnutBowlingLevel())
            {
                this.mShowBowlingLine = true;
            };
            if (((this.app.IsSurvivalMode()) && ((this.mSurvivalStage == 0))))
            {
                aMessage = "[ADVICE_SURVIVE_ENDLESS]";
                this.mBoard.DisplayAdvice(aMessage, Board.MESSAGE_STYLE_HINT_FAST, Board.ADVICE_SURVIVE_FLAGS);
            };
            if (this.mBoard.mLevel == 10)
            {
                this.mBoard.mZombieCountDown = 100;
                this.mBoard.mZombieCountDownStart = this.mBoard.mZombieCountDown;
                this.mConveyorBeltCounter = 200;
            };
        }
        public void  ClearCursor (){
        }
        public boolean  PuzzleIsAwardStage (){
            if (this.app.IsAdventureMode())
            {
                return (false);
            };
            int aStagesPerAward =1;
            if (this.app.mGameMode == PVZApp.GAMEMODE_SCARY_POTTER_ENDLESS)
            {
                aStagesPerAward = 10;
            };
            if (((this.mSurvivalStage + 1) % aStagesPerAward) == 0)
            {
                return (true);
            };
            return (false);
        }

        public boolean mShowBowlingLine ;

        public void  Update (){
            int aTotalSun ;
            ImageInst anImage ;
            if (this.mBoard.mPaused)
            {
                return;
            };
            if (this.mBoard.mGameScene != SCENE_PLAYING)
            {
                return;
            };
            if (this.mBoard.HasConveyorBeltSeedBank())
            {
                this.UpdateConveyorBelt();
            };
            if (this.app.IsScaryPotterLevel())
            {
                this.ScaryPotterUpdate();
            };
            if (this.app.IsScaryPotterLevel())
            {
                if (this.mBoard.mSeedBank.mY < 0)
                {
                    aTotalSun = (this.mBoard.mSunMoney + this.mBoard.CountSunBeingCollected());
                    anImage = this.app.imageManager().getImageInst(PVZImages.IMAGE_SEEDBANK);
                    if (aTotalSun > 0)
                    {
                    };
                    if ((((aTotalSun > 0)) || ((this.mBoard.mSeedBank.mY > -78))))
                    {
                        this.mBoard.mSeedBank.mVisible = true;
                        this.mBoard.mSeedBank.mY = (this.mBoard.mSeedBank.mY + 2);
//                        trace(this.mBoard.mSeedBank.mY);
                        if (this.mBoard.mSeedBank.mY > 0)
                        {
                            this.mBoard.mSeedBank.mY = 0;
                        };
                    };
                };
            };
        }

        public int mSurvivalStage ;
        public Reanimation mMalletReanim ;
        public int mLastConveyorSeedType ;

        public void  SpawnZombieWave (){
            int aCount ;
            int i =0;
            boolean aIsFlagWave =this.mBoard.IsFlagWave(this.mBoard.mCurrentWave );
            if (((this.app.IsSurvivalMode()) && ((this.mBoard.mBackground == Board.BACKGROUND_2_NIGHT))))
            {
                if (this.mBoard.mCurrentWave == (this.mBoard.mNumWaves - 1))
                {
                    aCount = this.mBoard.GetGraveStoneCount();
                    i = 0;
                    while (i < (this.mSurvivalStage + 1))
                    {
                        if (aCount < 12)
                        {
                            this.GraveDangerSpawnRandomGrave();
                        };
                        i++;
                    };
                };
            };
        }
        public void  ScaryPotterChangePotType (int thePotType ,int theCount ){
            GridItem aGridItem ;
            WeightedArray aResult ;
            GridItem theGridItem ;
            Array aPotArray =new Array ();
            int aPotArrayCount =0;
            int i =0;
            while (i < this.mBoard.mGridItems.length())
            {
            	aGridItem=(GridItem)this.mBoard.mGridItems.elementAt(i);
            	aPotArray.add(aPotArrayCount, new WeightedArray());
                if (aGridItem.mGridItemState != GRIDITEM_STATE_SCARY_POT_QUESTION)
                {
                }
                else
                {
                    if ((((thePotType == GRIDITEM_STATE_SCARY_POT_LEAF)) && (!((aGridItem.mScaryPotType == SCARYPOT_SEED)))))
                    {
                    }
                    else
                    {
                        if (thePotType == GRIDITEM_STATE_SCARY_POT_ZOMBIE)
                        {
                        }
                        else
                        {
                        	((WeightedArray)aPotArray.elementAt(aPotArrayCount)).mItem=i;
                        	((WeightedArray)aPotArray.elementAt(aPotArrayCount)).mWeight=1;
                            aPotArrayCount++;
                        };
                    };
                };
                i++;
            };
            if (theCount > aPotArrayCount)
            {
                theCount = aPotArrayCount;
            };
            i = 0;
            while (i < theCount)
            {
                aResult = TodCommon.TodPickArrayItemFromWeightedArray(aPotArray, aPotArrayCount);
                aResult.mWeight = 0;
                theGridItem=(GridItem)this.mBoard.mGridItems.elementAt(aResult.mItem);
                theGridItem.mGridItemState = thePotType;
                i++;
            };
        }

        public int mChallengeGridX ;
        public int mChallengeGridY ;

        public void  ScaryPotterStart (){
            if (this.app.IsAdventureMode())
            {
            };
        }
        public void  UpdateConveyorBelt (){
            int aSeedType ;
            WeightedArray aArrayItem ;
            int aCountInBank ;
            if (this.mBoard.HasLevelAwardDropped())
            {
                return;
            };
            this.mBoard.mSeedBank.UpdateConveyorBelt();
            this.mConveyorBeltCounter--;
            if (this.mConveyorBeltCounter > 0)
            {
                return;
            };
            int aConveyorSpeedMultiplier =1;
            if (this.mBoard.mSeedBank.GetNumSeedsOnConveyorBelt() > 8)
            {
                this.mConveyorBeltCounter = (1000 * aConveyorSpeedMultiplier);
            }
            else
            {
                if (this.mBoard.mSeedBank.GetNumSeedsOnConveyorBelt() > 6)
                {
                    this.mConveyorBeltCounter = (500 * aConveyorSpeedMultiplier);
                }
                else
                {
                    if (this.mBoard.mSeedBank.GetNumSeedsOnConveyorBelt() > 4)
                    {
                        this.mConveyorBeltCounter = (425 * aConveyorSpeedMultiplier);
                    }
                    else
                    {
                        this.mConveyorBeltCounter = (400 * aConveyorSpeedMultiplier);
                    };
                };
            };
            int MAX_SPAWNING_SEED_TYPES =20;
            Array aSeedPickArray =new Array ();
            int aNumSeedsType =0;
            int i =0;
            while (i < 7)
            {
            	aSeedPickArray.add(i, new WeightedArray());
                i++;
            };
            if (this.mBoard.mLevel == 10)
            {
            	((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_PEASHOOTER;
            	((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=20;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_CHERRYBOMB;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=20;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_WALLNUT;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=15;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_REPEATER;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=20;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_SNOWPEA;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=10;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_CHOMPER;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=5;
                aNumSeedsType++;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_SQUASH;
                ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=10;
                aNumSeedsType++;
            }
            else
            {
                if (this.mBoard.IsWallnutBowlingLevel())
                {
                	((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_WALLNUT;
                	((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=85;
                    aNumSeedsType++;
                    ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mItem=SEED_EXPLODE_O_NUT;
                    ((WeightedArray)aSeedPickArray.elementAt(aNumSeedsType)).mWeight=15;
                    aNumSeedsType++;
                };
            };
            i = 0;
            while (i < aNumSeedsType)
            {
            	aArrayItem=(WeightedArray)aSeedPickArray.elementAt(i);
                aSeedType = aArrayItem.mItem;
                aCountInBank = this.mBoard.mSeedBank.CountOfTypeOnConveyorBelt(aArrayItem.mItem);
                if (aNumSeedsType > 2)
                {
                    if (aCountInBank >= 4)
                    {
                        aArrayItem.mWeight = 1;
                    }
                    else
                    {
                        if (aCountInBank >= 3)
                        {
                            aArrayItem.mWeight = 5;
                        }
                        else
                        {
                            if (aSeedType == this.mLastConveyorSeedType)
                            {
                                aArrayItem.mWeight = (aArrayItem.mWeight / 2);
                            };
                        };
                    };
                };
                i++;
            };
            aSeedType = TodCommon.TodPickFromWeightedArray(aSeedPickArray, aNumSeedsType);
            this.mBoard.mSeedBank.AddSeed(aSeedType, false);
            this.mLastConveyorSeedType = aSeedType;
        }

        public  Challenge (PVZApp app ,Board theBoard ){
            this.app = app;
            this.mBoard = theBoard;
            this.mChallengeStateCounter = 0;
            this.mConveyorBeltCounter = 0;
            this.mChallengeScore = 0;
            this.mShowBowlingLine = false;
            this.mLastConveyorSeedType = SEED_NONE;
            this.mSurvivalStage = 0;
            this.mChallengeGridX = 0;
            this.mChallengeGridY = 0;
            this.mScaryPotterPots = 0;
        }
    }

