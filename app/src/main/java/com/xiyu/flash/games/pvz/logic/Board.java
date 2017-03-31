package com.xiyu.flash.games.pvz.logic;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
import android.graphics.Point;

import com.pgh.mahjong.MJCards;
import com.xiyu.util.*;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.render.RenderManager;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.framework.resources.images.ImageData;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.utils.Utils;
import com.xiyu.flash.framework.widgets.CWidget;
import com.xiyu.flash.framework.widgets.ui.IButtonListener;
import com.xiyu.flash.framework.widgets.ui.ImageButtonWidget;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.games.pvz.logic.Plants.CPlant;
import com.xiyu.flash.games.pvz.logic.Plants.CherryBomb;
import com.xiyu.flash.games.pvz.logic.Plants.Chomper;
import com.xiyu.flash.games.pvz.logic.Plants.ExplodoNut;
import com.xiyu.flash.games.pvz.logic.Plants.FumeShroom;
import com.xiyu.flash.games.pvz.logic.Plants.GraveBuster;
import com.xiyu.flash.games.pvz.logic.Plants.LeftPeater;
import com.xiyu.flash.games.pvz.logic.Plants.PeaShooter;
import com.xiyu.flash.games.pvz.logic.Plants.PuffShroom;
import com.xiyu.flash.games.pvz.logic.Plants.Repeater;
import com.xiyu.flash.games.pvz.logic.Plants.SnowPea;
import com.xiyu.flash.games.pvz.logic.Plants.Squash;
import com.xiyu.flash.games.pvz.logic.Plants.SunShroom;
import com.xiyu.flash.games.pvz.logic.Plants.Sunflower;
import com.xiyu.flash.games.pvz.logic.Plants.Wallnut;
import com.xiyu.flash.games.pvz.logic.UI.OptionsDialog;
import com.xiyu.flash.games.pvz.logic.UI.ToolTipWidget;
import com.xiyu.flash.games.pvz.logic.Zombies.Zombie;
import com.xiyu.flash.games.pvz.logic.Zombies.ZombieDef;
import com.xiyu.flash.games.pvz.logic.Zombies.ZombiePicker;
import com.xiyu.flash.games.pvz.renderables.CoinRenderable;
import com.xiyu.flash.games.pvz.renderables.GridItemRenderable;
import com.xiyu.flash.games.pvz.renderables.ImageRenderable;
import com.xiyu.flash.games.pvz.renderables.LawnMowerRenderable;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.renderables.PlantRenderable;
import com.xiyu.flash.games.pvz.renderables.ProjectileRenderable;
import com.xiyu.flash.games.pvz.renderables.ZombieRenderable;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.resources.PVZReanims;

//import flash.display.BitmapData;
//import flash.geom.Matrix;
//import flash.geom.Point;
//import flash.geom.Rectangle;

    public class Board extends CWidget implements IButtonListener {

        public static final int RENDER_LAYER_LAWN =300000;
        public static final int TUTORIAL_MORESUN_COMPLETED =12;
        public static final int OBJECT_TYPE_PLANT =1;
        public static final int RENDER_LAYER_SCREEN_FADE =900000;
        public static final int GRIDITEM_SCARY_POT =2;
        public static final int ADVICE_CLICK_TO_CONTINUE =7;
        public static final int MESSAGE_STYLE_TUTORIAL_LATER =3;
        public static final int FLAG_RAISE_TIME =100;
        public static final int GRIDSQUARE_NONE =0;
        public static final int REANIM_CHERRYBOMB =3;
        public static final int BACKGROUND_1_DAY =0;
        public static final int ADVICE_NONE =-1;
        public static final int CURSOR_TYPE_NORMAL =0;
        public static final int OBJECT_TYPE_NONE =0;
        
        public static final int ZOMBIE_DOOR =6;
        public static final int SCARYPOT_SUN =3;
        public static final int SCENE_CREDIT =6;
        public static final int GRIDITEM_GRAVESTONE =1;
        public static final int CURSOR_TYPE_HAMMER =4;
        public static final int SCARYPOT_SEED =1;
        public static  String COIN_NONE ="none";
        public static  String PLANTING_NOT_PASSED_LINE ="not passed line";
        public static final int SCENE_CHALLENGE =7;
        public static final int GRIDSQUARE_GRASS =1;
        public static final int LAWN_XMIN =150;//25;
        public static  String PLANTING_ONLY_ON_GRAVES ="only on graves";
        public static final int PLANTROW_DIRT =1;
        public static final int TUTORIAL_SHOVEL_DIG =14;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL1 =0;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL2 =2;
        public static final int MAX_ZOMBIE_WAVES =100;
        public static final int OBJECT_TYPE_SHOVEL =5;
        public static final int SCENE_LOADING =0;
        public static final int SEED_NONE =-1;
        public static final int TUTORIAL_SHOVEL_COMPLETED =16;
        public static final int GRIDITEM_NONE =0;
        public static final int SEED_CHERRYBOMB =2;
        public static final int GRIDSQUARE_DIRT =2;
        public static final int SCENE_LEVEL_INTRO =2;
        public static final int ADVICE_PLANT_SUNFLOWER5 =5;
        public static final int ADVICE_CLICK_ON_SUN =0;
        public static final int PROGRESS_METER_COUNTER =100;
        public static final int TUTORIAL_SHOVEL_PICKUP =13;
        public static final int TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER =5;
        public static final int TUTORIAL_SHOVEL_KEEP_DIGGING =15;
        public static final int ADVICE_UNLOCKED_MODE =9;
        public static final int RENDER_LAYER_GROUND =200000;
        public static  String COIN_FINAL_SEED_PACKET ="finalseedpacket";
        public static final int MESSAGE_STYLE_HUGE_WAVE =14;
        public static final int TUTORIAL_LEVEL_2_PLANT_SUNFLOWER =6;
        public static final int LAWN_YMIN =77;
        public static final int RENDER_LAYER_PLANT =(RENDER_LAYER_LAWN +2000);//302000
        public static final int MESSAGE_STYLE_BIG_MIDDLE =11;
        public static final int TUTORIAL_LEVEL_1_COMPLETED =4;
        public static final int SEED_FUMESHROOM =10;
        public static final int ZOMBIE_COUNTDOWN_MIN =400;
        public static final int SCENE_CRAZY_DAVE =8;
        public static final int ZOMBIE_COUNTDOWN_FIRST_WAVE =1800;
        public static final int SCENE_AWARD =5;
        public static final int SEED_SNOWPEA =5;
        public static final int MESSAGE_STYLE_HINT_TALL_FAST =8;
        public static final int BOARD_HEIGHT =405*3;
        public static final int ZOMBIE_INVALID =-1;
        public static  String COIN_MOTION_COIN ="coin";
        public static final int SEED_SQUASH =4;
        public static final int TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER =3;
        public static final int SCENE_ZOMBIES_WON =4;
        public static final int RENDER_LAYER_LAWN_MOWER =(RENDER_LAYER_LAWN +6000);//306000
        public static final int ADVICE_SEED_REFRESH =3;
        public static final int MESSAGE_STYLE_HINT_TALL_LONG =10;
        public static final int MESSAGE_STYLE_BIG_MIDDLE_FAST =12;
        public static final int ADVICE_PLANT_NOT_PASSED_LINE =13;
        public static final int ZOMBIE_COUNTDOWN_BEFORE_REPICK =5500;
        public static final int REANIM_PEASHOOTER =0;
        public static final int BOARD_WIDTH =540*3;
        public static final int TUTORIAL_OFF =0;
        public static final int REANIM_WALLNUT =1;
        public static final int SEED_LEFTPEATER =49;
        public static final int GRIDSIZEY =5;
        public static final int RENDER_LAYER_COIN_BANK =600000;
        public static final int RENDER_LAYER_GRAVE_STONE =(RENDER_LAYER_LAWN +1000);//301000
        public static final int SEED_GRAVEBUSTER =11;
        public static final int SCARYPOT_NONE =0;
        public static final int ADVICE_PEASHOOTER_DIED =10;
        public static final int ZOMBIE_POLEVAULTER =3;
        public static final int GRIDSIZEX =9;
        public static final int CURSOR_TYPE_PLANT_FROM_BANK =1;
        public static final int RENDER_LAYER_ZOMBIE =(RENDER_LAYER_LAWN +3000);//303000
        public static final int SEED_PUFFSHROOM =8;
        public static final int CURSOR_TYPE_PLANT_FROM_USABLE_COIN =2;
        public static final int OBJECT_TYPE_PROJECTILE =2;
        public static final int TUTORIAL_MORESUN_PICK_UP_SUNFLOWER =9;
        public static final int REANIM_SQUASH =4;
        public static final int SEED_PEASHOOTER =0;
        public static final int ZOMBIE_NORMAL =0;
        public static final int RENDER_LAYER_FOG =500000;
        public static final int TUTORIAL_MORESUN_PLANT_SUNFLOWER =10;
        public static final int ADVICE_CLICKED_ON_SUN =1;
        public static final int MESSAGE_STYLE_HOUSE_NAME =13;
        public static final int SEED_EXPLODE_O_NUT =50;
        public static final int NUM_ZOMBIE_TYPES =8;
        public static final int TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER =7;
        public static final int LAWN_XMAX =513;
        public static final int SEED_SUNFLOWER =1;
        public static final int SUN_COUNTDOWN_MAX =950;
        public static final int ZOMBIE_COUNTDOWN =2500;
        public static final int MESSAGE_STYLE_OFF =-1;
        public static final int SCENE_PLAYING =3;
        public static final int SEED_CHOMPER =6;
        public static final int ZOMBIE_PAIL =4;
        public static final int TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER =1;
        public static final int SCENE_MENU =1;
        public static final int ZOMBIE_COUNTDOWN_RANGE =600;
        public static final int ADVICE_PLANT_GRAVEBUSTERS_ON_GRAVES =12;
        public static  String PLANTING_NOT_ON_GRAVE ="not on graves";
        public static final int OBJECT_TYPE_SEEDPACKET =4;
        public static final int LEVELS_PER_AREA =10;
        public static final int TUTORIAL_LEVEL_2_COMPLETED =8;
        public static final int SUN_COUNTDOWN_RANGE =275;
        public static final int SCARYPOT_ZOMBIE =2;
        public static final int ZOMBIE_FOOTBALL =7;
        public static  String COIN_MOTION_FROM_PLANT ="from plant";
        public static final int ADVICE_CLICKED_ON_COIN =2;
        public static final int OBJECT_TYPE_COIN =3;
        public static final int RENDER_LAYER_PROJECTILE =(RENDER_LAYER_LAWN +5000);//305000
        public static  String COIN_SUN ="sun";
        public static  String PLANTING_OK ="ok";
        public static final int MESSAGE_STYLE_HINT_STAY =7;
        public static final int ADVICE_HUGE_WAVE =6;
        public static final int OBJECT_TYPE_MENU_BUTTON =7;
        public static final int MESSAGE_STYLE_HINT_FAST =6;
        public static final int SEED_REPEATER =7;
        public static final int PLANTROW_NORMAL =0;
        public static  String COIN_MOTION_FROM_SKY_SLOW ="from sky slow";
        public static final int BACKGROUND_2_NIGHT =1;
        public static final int SEED_SUNSHROOM =9;
        public static final int LAWN_YMAX =385;
        public static final int BOARD_SHAKE_TIME =12;
        public static final int ZOMBIE_NEWSPAPER =5;
        public static final int SEED_WALLNUT =3;
        public static final int ZOMBIE_COUNTDOWN_BEFORE_FLAG =4500;
        public static final int OBJECT_TYPE_SCARY_POT =6;
        public static  String PLANTING_NOT_HERE ="not here";
        public static final int ADVICE_PLANT_NOT_ON_GRAVE =11;
        public static final int RENDER_LAYER_ABOVE_UI =800000;
        public static final int MESSAGE_STYLE_HINT_LONG =5;
        public static final int ADVICE_SURVIVE_FLAGS =8;
        public static  String COIN_MOTION_FROM_SKY ="from sky";
        public static final int TUTORIAL_LEVEL_1_PLANT_PEASHOOTER =2;
        public static final int MESSAGE_STYLE_HINT_TALL_UNLOCKMESSAGE =9;
        public static final int MESSAGE_STYLE_TUTORIAL_LATER_STAY =4;
        public static final int REANIM_SUNFLOWER =2;
        public static final int SUN_COUNTDOWN =425;
        public static final int REANIM_NONE =-1;
        public static final int ZOMBIE_TRAFFIC_CONE =2;
        public static final int TUTORIAL_MORESUN_REFRESH_SUNFLOWER =11;
        public static final int CURSOR_TYPE_SHOVEL =3;
        public static final int ZOMBIE_FLAG =1;
        public static final int ADVICE_CANT_AFFORD_PLANT =4;
        public static final int RENDER_LAYER_UI_TOP =700000;
        public static final int MAX_ZOMBIES_IN_WAVE =50;
        public static final int RENDER_LAYER_TOP =400000;
        public static final int MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY =1;
        public static final int RENDER_LAYER_PARTICLE =(RENDER_LAYER_LAWN +7000);//307000
        public static final int RENDER_LAYER_UI_BOTTOM =100000;

        public static final int OBJECT_TYPE_PENGGANGHU = 8;
        
        public boolean  CanZombieSpawnOnLevel (int theZombieType ,int theLevel ){
            ZombieDef aZombieDef =this.GetZombieDefinition(theZombieType );
            if (theLevel < aZombieDef.mStartingLevel)
            {
                return (false);
            };
            if (aZombieDef.mPickWeight == 0)
            {
                return (false);
            };
            int aLevelIndex =TodCommon.ClampInt ((theLevel -1),0,(14-1));
            if(((Integer)((Array)this.mZombieAllowedLevels.elementAt(theZombieType)).elementAt(aLevelIndex)).intValue()==0)
            {
                return (false);
            };
            return (true);
        }
        public void  SpawnZombieWave (){
            int aZombieType ;
            this.mChallenge.SpawnZombieWave();
            int counter =0;
            int i =0;
            /*
//            System.out.println("SpawnZombieWave");
            while (i < MAX_ZOMBIES_IN_WAVE)
            {
            	aZombieType=((Integer)((Array)this.mZombiesInWave.elementAt(this.mCurrentWave)).elementAt(i)).intValue();
                if (this.mCurrentWave == 4)
                {
                };
                if (aZombieType == ZOMBIE_INVALID)
                {
                    break;
                };
                counter++;
//                this.AddZombie(aZombieType, this.mCurrentWave, -1);//0);
                i++;
            };
            */
            if (this.mRiseFromGraveCounter == 0)//(this.mNumWaves - 1))
            {
                this.mRiseFromGraveCounter = 15;//200;
            };
            if (this.IsFlagWave(this.mCurrentWave))
            {
                this.mFlagRaiseCounter = FLAG_RAISE_TIME;
            };
            this.mCurrentWave++;
            this.mTotalSpawnedWaves++;
        }
        public boolean  CanZombieGoInGridSpot (int theZombieType ,int theGridX ,int theGridY ,Array theZombieGrid ){
        	if(((Boolean)((Array)theZombieGrid.elementAt(theGridX)).elementAt(theGridY)).booleanValue())
            {
                return (false);
            };
            if (theGridX == 4 && theGridY == 0)
            {
                return (false);
            };
            if (theZombieType == Board.ZOMBIE_POLEVAULTER)
            {
                if (theGridX == 0)
                {
                    return (false);
                };
                if (theGridX == 1 && theGridY == 0)
                {
                    return (false);
                };
            };
            return (true);
        }

        public FontInst mLevelFont ;

        public Zombie  AddZombie (int theZombieType ,int theFromWave ,int row){
            int aRow =row ;
            if (aRow == -1)
            {
                aRow = this.PickRowForNewZombie(theZombieType);
            };
            return (this.AddZombieInRow(theZombieType, aRow, theFromWave));
        }
        public int  PlantingPixelToGridY (int theX ,int theY ,int theSeedType ){
            int aPlantingY =theY ;
            return (this.PixelToGridY(theX, theY));
        }
        public boolean  IsFinalSurvivalStage (){
            return (false);
        }
        public int  CountPlantByType (int theSeedType ){
            CPlant aPlant ;
            int aCount = 0;
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                if (aPlant.mSeedType != theSeedType)
                {
                }
                else
                {
                    aCount++;
                };
            };
            return (aCount);
        }
        public boolean  CanDropLoot (){
            return (false);
        }
        public String  GetPlantDefinition (int theSeedType ){
            String aReanimType = "";
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
                case SEED_WALLNUT:
                case SEED_EXPLODE_O_NUT:
                    aReanimType = PVZReanims.REANIM_WALLNUT;
                    break;
                case SEED_REPEATER:
                    aReanimType = PVZReanims.REANIM_PEASHOOTER;
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
                case SEED_LEFTPEATER:
                    aReanimType = PVZReanims.REANIM_PEASHOOTER;
                    break;
            };
            return (aReanimType);
        }
        public void  FindAndPlaceZombie (int theZombieType ,Array theZombieGrid ){
            int aGridX =0;
            int aGridY =0;
            this.FindPlaceForStreetZombies(theZombieType, theZombieGrid, aGridX, aGridY);
        }
        public void  PutZombieInWave (int theZombieType ,int theWaveNumber ,ZombiePicker theZombiePicker ){
        	((Array)this.mZombiesInWave.elementAt(theWaveNumber)).add(theZombiePicker.mZombieCount,theZombieType);
            theZombiePicker.mZombieCount++;
            if (theZombiePicker.mZombieCount < MAX_ZOMBIES_IN_WAVE)
            {
            	((Array)this.mZombiesInWave.elementAt(theWaveNumber)).add(theZombiePicker.mZombieCount,ZOMBIE_INVALID);
            };
            ZombieDef aIntroZombieDef =this.GetZombieDefinition(theZombieType );
            theZombiePicker.mZombiePoints = (theZombiePicker.mZombiePoints - aIntroZombieDef.mZombieValue);
            Dictionary _local5 =theZombiePicker.mZombieTypeCount ;
            int _local6 =theZombieType ;
            int _local7=((Integer)_local5.elementAt(_local6)).intValue() + 1;
            _local5.put(_local6, _local7);
            _local5 = theZombiePicker.mAllWavesZombieTypeCount;
            _local6 = theZombieType;
            _local7=((Integer)_local5.elementAt(_local6)).intValue() + 1;
            _local5.put(_local6,_local7);
        }
        public boolean  IsSlotMachineLevel (){
            return (false);
        }
        public boolean  ChooseSeedsOnCurrentLevel (){
            if (this.HasConveyorBeltSeedBank())
            {
                return (false);
            };
            if (this.mApp.IsSurvivalMode())
            {
                return (true);
            };
            if (this.mLevel <= 7)
            {
                return (false);
            };
            return (true);
        }
        public void  UpdateZombieSpawning (){

            int SPAWN_DELAY_AFTER_HEALTH_TRIGGER ;
            int aTotalHealth ;
            if (this.mFinalWaveSoundCounter > 0)
            {
                this.mFinalWaveSoundCounter--;
                if (this.mFinalWaveSoundCounter == 0)
                {
                };
            };
/*
            if ((((((this.mTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER)) || ((this.mTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)))) || ((this.mTutorialState == TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER))))
            {
                return;
            };
            if (this.HasLevelAwardDropped())
            {
                return;
            };
*/
            if (this.mRiseFromGraveCounter > 0)
            {
                this.mRiseFromGraveCounter--;
                if (this.mRiseFromGraveCounter == 0)
                {
                    this.SpawnZombiesFromGraves();
                };
            };
            if (this.mHugeWaveCountDown > 0)
            {
                this.mHugeWaveCountDown--;
                if (this.mHugeWaveCountDown == 0)
                {
                    this.ClearAdvice(ADVICE_HUGE_WAVE);
                    this.NextWaveComing();
                    this.mZombieCountDown = 1;
                }
                else
                {
                    if (this.mHugeWaveCountDown != 725)
                    {
                        return;
                    };
                };
            };
            if (this.mChallenge.UpdateZombieSpawning())
            {
                return;
            };
            if (this.mCurrentWave == this.mNumWaves)
            {
                if (!this.mApp.IsSurvivalMode())
                {
                    return;
                };
            };
            this.mZombieCountDown--;

            /*
            if ((((this.mCurrentWave == this.mNumWaves)) && (this.mApp.IsSurvivalMode())))
            {
                if (this.mZombieCountDown == 0)
                {
                    this.FadeOutLevel();
                };
                return;
            };
            */
            
            int aTimeSinceLastSpawn =(this.mZombieCountDownStart -this.mZombieCountDown );
            if (this.mZombieCountDown > 5 && aTimeSinceLastSpawn > ZOMBIE_COUNTDOWN_MIN)
            {
                SPAWN_DELAY_AFTER_HEALTH_TRIGGER = 200;
                aTotalHealth = this.TotalZombiesHealthInWave((this.mCurrentWave - 1));
                if (aTotalHealth <= this.mZombieHealthToNextWave && this.mZombieCountDown > SPAWN_DELAY_AFTER_HEALTH_TRIGGER)
                {
                    this.mZombieCountDown = SPAWN_DELAY_AFTER_HEALTH_TRIGGER;
                };
            };
            if (this.mZombieCountDown == 5)
            {
                if (this.IsFlagWave(this.mCurrentWave))
                {
                    this.mHugeWaveCountDown = 750;
                    this.mWaveWarning.showHugeWave(this.mHugeWaveCountDown);
                    return;
                };
                this.NextWaveComing();
            };
            
            if(this.mZombies.length()<10)
            	this.SpawnZombieWave();

            if (this.mZombieCountDown != 0)
            {
                return;
            };

            this.mZombieHealthWaveStart = this.TotalZombiesHealthInWave((this.mCurrentWave - 1));
            boolean aNoPauseBeforeHugeWave =this.IsWallnutBowlingLevel();
            if (this.mCurrentWave == this.mNumWaves && this.mApp.IsSurvivalMode())
            {
                this.mZombieHealthToNextWave = 0;
                this.mZombieCountDown = ZOMBIE_COUNTDOWN_BEFORE_REPICK;
            }
            else
            {
                if (this.IsFlagWave(this.mCurrentWave) && !aNoPauseBeforeHugeWave)
                {
                    this.mZombieHealthToNextWave = 0;
                    this.mZombieCountDown = ZOMBIE_COUNTDOWN_BEFORE_FLAG;
                }
                else
                {
                    this.mZombieHealthToNextWave = (int)((TodCommon.RandRangeFloat(0.5, 0.65) * this.mZombieHealthWaveStart ));
                    this.mZombieCountDown = (int)(ZOMBIE_COUNTDOWN + (Math.random() * ZOMBIE_COUNTDOWN_RANGE));
                };
            };
            this.mZombieCountDownStart = this.mZombieCountDown;
        }
        public int  PlantingPixelToGridX (int theX ,int theY ,int theSeedType ){
            int aPlantingY =theY ;
            return this.PixelToGridX(theX, aPlantingY);
        }
        public GridItem  GetGraveStoneAt (int theGridX ,int theGridY ){
            return this.GetGridItemAt(GRIDITEM_GRAVESTONE, theGridX, theGridY);
        }
        public void  UpdateGameObjects (){
            this.mRenderManager.update();
            this.mCursorObject.update();
            this.mCursorPreview.update();
            int i =0;

            while (i < this.mSeedBank.mNumPackets)
            {
            	((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).update();
                i++;
            };
            mPengGangHu.update();
        }
        public void  GetPlantSeedImage (Graphics2D g ){
        }

        private ImageInst mLevelLabel ;

        public GridItem  GetScaryPotAt (int theGridX ,int theGridY ){
            return (this.GetGridItemAt(GRIDITEM_SCARY_POT, theGridX, theGridY));
        }
        public void  MakeMenuButton (){
            int w =79;
            int h =31;
            this.mMenuButton = new ImageButtonWidget(this.Menu_button, this);
            this.mMenuButton.resize(465, 0, w, h);
            this.mMenuButton.setDisabled(false);
            int x =0;
            int y =0;
            String theText =this.mApp.stringManager().translateString("[MENU_BUTTON]");
            ImageInst upImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            Graphics2D bufferG =upImage.graphics();
            ImageInst anImage =this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_LEFT );
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_MIDDLE);
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_RIGHT);
            bufferG.drawImage(anImage, x, y);
            FontInst font =this.mApp.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36GREENINSET );
            font.scale(0.4);
            int offsetX =(int)((w -font.stringImageWidth(theText ))/2);
            int offsetY =(int)(((h -font.getHeight ())-5)/2);
            bufferG.setFont(font);
            bufferG.drawString(theText, offsetX, offsetY);
            x = 0;
            y = 0;
            ImageInst downImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            bufferG = downImage.graphics();
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_DOWN_LEFT);
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_DOWN_MIDDLE);
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_DOWN_RIGHT);
            bufferG.drawImage(anImage, x, y);
            font = this.mApp.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36BRIGHTGREENINSET);
            font.scale(0.4);
            bufferG.setFont(font);
            bufferG.drawString(theText, (offsetX + 1), (offsetY + 1));
            x = 0;
            y = 0;
            ImageInst overImage =new ImageInst(new ImageData(new BitmapData(w ,h ,true ,0)));
            bufferG = overImage.graphics();
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_LEFT);
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_MIDDLE);
            bufferG.drawImage(anImage, x, y);
            x = (int)(x + anImage.width());
            anImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_BUTTON_RIGHT);
            bufferG.drawImage(anImage, x, y);
            font = this.mApp.fontManager().getFontInst(PVZFonts.FONT_DWARVENTODCRAFT36BRIGHTGREENINSET);
            font.scale(0.4);
            bufferG.setFont(font);
            bufferG.drawString(theText, offsetX, offsetY);
            this.mMenuButton.mUpImage = upImage;
            this.mMenuButton.mOverImage = overImage;
            this.mMenuButton.mDownImage = downImage;
            this.mMenuButton.mDownOffset = new Point(1, 1);
            this.mMenuButton.mDisabledImage = upImage;
        }

        private Array seeds ;
        public int mSodPosition ;
        public CursorPreview mCursorPreview ;

        private void  spawnWaveCheat (){
            if (this.mHugeWaveCountDown > 0)
            {
                this.mHugeWaveCountDown = 1;
            }
            else
            {
                this.mZombieCountDown = 6;
            };
        }

        public int mShakeAmountX ;
        public int mShakeAmountY ;

        public void  ClearCursor (){
            this.mCursorObject.mType = SEED_NONE;
            this.mCursorObject.mCursorType = CURSOR_TYPE_NORMAL;
            this.mCursorObject.mSeedBankIndex = -1;
            if (this.mTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)
            {
                this.SetTutorialState(TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER);
            }
            else
            {
                if (this.mTutorialState == TUTORIAL_LEVEL_2_PLANT_SUNFLOWER || this.mTutorialState == TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER)
                {
                    if (!((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).CanPickUp())
                    {
                        this.SetTutorialState(TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER);
                    }
                    else
                    {
                        this.SetTutorialState(TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER);
                    };
                }
                else
                {
                    if (this.mTutorialState == TUTORIAL_MORESUN_PLANT_SUNFLOWER || this.mTutorialState == TUTORIAL_MORESUN_REFRESH_SUNFLOWER)
                    {
                        if (!((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).CanPickUp())
                        {
                            this.SetTutorialState(TUTORIAL_MORESUN_REFRESH_SUNFLOWER);
                        }
                        else
                        {
                            this.SetTutorialState(TUTORIAL_MORESUN_PICK_UP_SUNFLOWER);
                        };
                    }
                    else
                    {
                        if (this.mTutorialState == TUTORIAL_SHOVEL_DIG)
                        {
                            this.SetTutorialState(TUTORIAL_SHOVEL_PICKUP);
                        };
                    };
                };
            };
        }
        public void  SpawnZombiesFromGraves (){
            GridItem aGridItem ;
            int aZombieType ;
            ZombieDef aZombieDef ;
            Zombie aZombie =null;
            int aZombiePoints =this.GetGraveStoneCount ();
            System.out.println("RiseFromGrave.0");
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (aGridItem.mGridItemType == GRIDITEM_GRAVESTONE)
                {
                    if (aGridItem.mGridItemCounter >= 30)
                    {
                    	aGridItem.mGridItemCounter = 0;
                    	
                    	aZombieType = this.PickGraveRisingZombieType(aZombiePoints);
                        aZombieDef = this.GetZombieDefinition(aZombieType);
                        aZombie = this.AddZombie(aZombieType, this.mCurrentWave, aGridItem.mGridY);
                        if (aZombie ==null)
                        {
                            return;
                        };
                        
                        System.out.println("RiseFromGrave."+aGridItem.mGridX+":"+aGridItem.mGridY);
                        aZombie.RiseFromGrave(aGridItem.mGridX, aGridItem.mGridY);
                        aZombiePoints = (aZombiePoints - aZombieDef.mZombieValue);
                        aZombiePoints = Math.max(1, aZombiePoints);
                        //return;
                    };
                };
            };
        }

        public int mHelpIndex ;

        public boolean  IsWallnutBowlingLevel (){
            if (this.mLevel == 5)
            {
                return (true);
            };
            return (false);
        }

        public Array mGridItems ;
        private ImageInst sod3Row ;

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
                        aPicks.add(aPickCount,aWeightedGridArray);
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
        public void  buttonMouseEnter (int id ){
        }

        public Array mPlants ;

        public void  PlaceStreetZombies (){
            int aZombieType;
            int aGridY;
            Array aZombieArray;
            int aNumToShow ;
            int aZombieIndex ;
            System.out.println("PlaceStreetZombies.1");
            if (this.mApp.mPlacedZombies)
            {
                return;
            };
            this.mApp.mPlacedZombies = true;
            int aTotalZombieCount = 0;
            Array aZombieTypeCount = new Array();
            int aZombieValueTotal = 0;
            int i = 0;
            while (i < 8)
            {
            	aZombieTypeCount.add(i, 0);
                i++;
            };
            int aWaveIndex = 0;
            while (aWaveIndex < this.mApp.mBoard.mNumWaves)
            {
                i = 0;
            	Array temp = (Array)this.mApp.mBoard.mZombiesInWave.elementAt(aWaveIndex);
                while (i < Board.MAX_ZOMBIES_IN_WAVE)
                {
                	aZombieType= ((Integer)temp.elementAt(i)).intValue();
                    if (aZombieType == Board.ZOMBIE_INVALID)
                    {
                        break;
                    };
                    aZombieValueTotal = (aZombieValueTotal + this.mApp.mBoard.GetZombieDefinition(aZombieType).mZombieValue);
                    if (aZombieType != Board.ZOMBIE_FLAG)
                    {
                        Array _local14 =aZombieTypeCount ;
                        int _local15 =aZombieType ;
                        int _local16= ((Integer)_local14.elementAt(_local15)).intValue() + 1;
                        _local14.add(_local15, _local16);
                        aTotalZombieCount++;
                    };
                    i++;
                };
                aWaveIndex++;
            };
            Array aZombieGrid = new Array();
            int aGridX =0;
            while (aGridX < 5)
            {
                aGridY = 0;
                aZombieArray = new Array();
                aZombieGrid.add(aGridX, aZombieArray);
                while (aGridY < 5)
                {
                	aZombieArray.add(aGridY, false);
                    aGridY++;
                };
                aGridX++;
            };
            int amApproxNumberToShow =10;
            if (this.mLevel == 10)
            {
                amApproxNumberToShow = 18;
            };
            aZombieType = 0;
            System.out.println("PlaceStreetZombies.2");
            while (aZombieType < 8)
            {
            	if(((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue()!=0)
                {
                	aNumToShow=((((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue()*amApproxNumberToShow)/aTotalZombieCount);
                	aNumToShow=TodCommon.ClampInt(aNumToShow,1,((Integer)aZombieTypeCount.elementAt(aZombieType)).intValue());
                    aZombieIndex = 0;
                    while (aZombieIndex < aNumToShow)
                    {
                        this.FindAndPlaceZombie(aZombieType, aZombieGrid);
                        aZombieIndex++;
                    };
                };
                aZombieType++;
            };
            System.out.println("PlaceStreetZombies.3");
        }
        public void  buttonPress (int id ){
        }
        public void  ClearAdviceImmediately (){
            this.ClearAdvice(ADVICE_NONE);
            this.mAdvice.mDuration = 0;
        }
        public void  RefreshSeedPacketFromCursor (){
            Coin aCoin ;
            SeedPacket aSeedPacket ;
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_USABLE_COIN)
            {
                aCoin = this.mCursorObject.mCoin;
                aCoin.DroppedUsableSeed();
            };
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_BANK)
            {
            	aSeedPacket=(SeedPacket)this.mSeedBank.mSeedPackets.elementAt(this.mCursorObject.mSeedBankIndex);
                aSeedPacket.Activate();
            };
            this.ClearCursor();
        }

        private CursorObject cursorObject ;

        public void  MouseDownWithPlant (int x0 ,int y0 ){
            int aCost ;
            CPlant aPlant ;
            int aPeashooters ;
            int aSuns ;
//            x = (int)(x);
//            y = (int)(y);
            int aPlantingSeedType =this.GetSeedTypeInCursor ();
            int aGridX =this.PlantingPixelToGridX(x0 ,y0 ,aPlantingSeedType );
            int aGridY =this.PlantingPixelToGridY(x0 ,y0 ,aPlantingSeedType );
            System.out.println("MouseDownWithPlant." + x0+":"+y0 + ":"+aGridX+":"+aGridY+":"+GRIDSIZEX+":"+GRIDSIZEY);

            if (aGridX < 0 || aGridX >= GRIDSIZEX || aGridY < 0 || aGridY >= GRIDSIZEY)
            {
                this.RefreshSeedPacketFromCursor();
                this.mApp.foleyManager().playFoley(PVZFoleyType.DROP);
                return;
            };
            String aReason =this.CanPlantAt(aGridX ,aGridY ,aPlantingSeedType );
            System.out.println("MouseDownWithPlant." + aReason+":"+aGridX+":"+aGridY);
            if (aReason != PLANTING_OK)
            {
                if (aReason == PLANTING_ONLY_ON_GRAVES)
                {
                    this.DisplayAdvice("[ADVICE_GRAVEBUSTERS_ON_GRAVES]", MESSAGE_STYLE_HINT_FAST, ADVICE_PLANT_GRAVEBUSTERS_ON_GRAVES);
                }
                else
                {
                    if (aReason == PLANTING_NOT_PASSED_LINE)
                    {
                        this.DisplayAdvice("[ADVICE_NOT_PASSED_LINE]", MESSAGE_STYLE_HINT_FAST, ADVICE_PLANT_NOT_PASSED_LINE);
                    }
                    else
                    {
                        if (aReason == PLANTING_NOT_ON_GRAVE)
                        {
                            this.DisplayAdvice("[ADVICE_PLANT_NOT_ON_GRAVE]", MESSAGE_STYLE_HINT_FAST, ADVICE_PLANT_NOT_ON_GRAVE);
                        };
                    };
                };
                return;
            };
            this.ClearAdvice(ADVICE_PLANT_NOT_ON_GRAVE);
            this.ClearAdvice(ADVICE_PLANT_NOT_PASSED_LINE);
            this.ClearAdvice(ADVICE_PLANT_GRAVEBUSTERS_ON_GRAVES);
            this.ClearAdvice(ADVICE_SURVIVE_FLAGS);
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_BANK && !this.HasConveyorBeltSeedBank())
            {
                aCost = this.GetCurrentPlantCost(this.mCursorObject.mType);
                if (!this.TakeSunMoney(aCost))
                {
                    return;
                };
            };
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_USABLE_COIN)
            {
            	this.AddPlant(aGridX, aGridY, this.mCursorObject.mType);
                this.mCursorObject.mCoin.Die();
                this.mCursorObject.mCoin = null;
            }
            else
            {
                if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_BANK)
                {
                    aPlant = this.AddPlant(aGridX, aGridY, this.mCursorObject.mType);
                    System.out.println("AddPlant"+aGridX+":"+aGridY);
                    ((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(this.mCursorObject.mSeedBankIndex)).WasPlanted();
                };
            };
            if (this.mTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)
            {
                aPeashooters = this.mPlants.length();
                if (aPeashooters >= 2)
                {
                    this.SetTutorialState(TUTORIAL_LEVEL_1_COMPLETED);
                }
                else
                {
                    this.SetTutorialState(TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER);
                };
            };
            if (this.mTutorialState == TUTORIAL_LEVEL_2_PLANT_SUNFLOWER)
            {
                aSuns = this.CountSunFlowers();
                if (aPlantingSeedType == SEED_SUNFLOWER && aSuns == 2)
                {
                    this.DisplayAdvice("[ADVICE_MORE_SUNFLOWERS]", MESSAGE_STYLE_TUTORIAL_LEVEL2, ADVICE_NONE);
                };
                if (aSuns >= 3)
                {
                    this.SetTutorialState(TUTORIAL_LEVEL_2_COMPLETED);
                }
                else
                {
                    if (!((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).CanPickUp())
                    {
                        this.SetTutorialState(TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER);
                    }
                    else
                    {
                        this.SetTutorialState(TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER);
                    };
                };
            };
            if (this.mTutorialState == TUTORIAL_MORESUN_PLANT_SUNFLOWER)
            {
                aSuns = this.CountSunFlowers();
                if (aSuns >= 3)
                {
                    this.SetTutorialState(TUTORIAL_MORESUN_COMPLETED);
                    this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER5]", MESSAGE_STYLE_TUTORIAL_LATER, ADVICE_PLANT_SUNFLOWER5);
                    this.mTutorialTimer = -1;
                }
                else
                {
                    if (!((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).CanPickUp())
                    {
                        this.SetTutorialState(TUTORIAL_MORESUN_REFRESH_SUNFLOWER);
                    }
                    else
                    {
                        this.SetTutorialState(TUTORIAL_MORESUN_PICK_UP_SUNFLOWER);
                    };
                };
            };
            if (this.IsWallnutBowlingLevel())
            {
            };
            this.ClearCursor();
        }
        private void  previousLevelCheat (){
            if (this.mApp.IsAdventureMode())
            {
                this.mApp.mLevel--;
                if (this.mApp.mLevel < 1)
                {
                    this.mApp.mLevel = 1;
                };
            }
            else
            {
                if (this.mApp.IsSurvivalMode())
                {
                };
            };
            this.mApp.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
        }

        public int mNumWaves ;

        public void  DisplayAdvice (String theAdvice ,int theMessageStyle ,int theHelpIndex ){
            if (theHelpIndex != ADVICE_NONE)
            {
            	if(((Boolean)this.mHelpDisplayed.elementAt(theHelpIndex)).booleanValue())
                {
                    return;
                };
                this.mHelpDisplayed.add(theHelpIndex,true);
            };
            this.mAdvice.SetLabel(theAdvice, theMessageStyle);
            this.mHelpIndex = theHelpIndex;
        }
        public boolean  GetCircleRectOverlap (int theCircleX ,int theCircleY ,int theRadius ,Rectangle theRect ){
            int distSquared ;
            boolean aCircleIsLeftOrRight =false;
            boolean aCircleIsAboveOrBelow =false;
            int aDistX =0;
            int aDistY =0;
            if (theCircleX < theRect.x)
            {
                aCircleIsLeftOrRight = true;
                aDistX = (theRect.x - theCircleX);
            }
            else
            {
                if (theCircleX > (theRect.x + theRect.width()))
                {
                    aCircleIsLeftOrRight = true;
                    aDistX = ((theCircleX - theRect.x) - theRect.width());
                };
            };
            if (theCircleY < theRect.y)
            {
                aCircleIsAboveOrBelow = true;
                aDistY = (theRect.y - theCircleY);
            }
            else
            {
                if (theCircleY > (theRect.y + theRect.height()))
                {
                    aCircleIsAboveOrBelow = true;
                    aDistY = ((theCircleY - theRect.y) - theRect.height());
                };
            };
            if (!aCircleIsAboveOrBelow && !aCircleIsLeftOrRight)
            {
                return (true);
            };
            if (aCircleIsLeftOrRight && aCircleIsAboveOrBelow)
            {
                distSquared = ((aDistX * aDistX) + (aDistY * aDistY));
                return ((distSquared <= (theRadius * theRadius)));
            };
            if (aCircleIsLeftOrRight)
            {
                return ((aDistX <= theRadius));
            };
            return ((aDistY <= theRadius));
        }

        public boolean mShowShovel;

        public void  UpdateSunSpawning (){
            if (this.StageIsNight())
            {
                return;
            };
            if (this.mApp.IsScaryPotterLevel())
            {
                return;
            };
            if (this.HasLevelAwardDropped())
            {
                return;
            };
            if (this.HasConveyorBeltSeedBank())
            {
                return;
            };
            if (this.mTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER || this.mTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)
            {
                if (this.mPlants.length() == 0)
                {
                    return;
                };
            };
            this.mSunCountDown--;
            if (this.mSunCountDown != 0)
            {
                return;
            };
            int x =(int)(67+(Math.random ()*371));
            this.mNumSunsFallen++;
            this.mSunCountDown = (int)(Math.min(SUN_COUNTDOWN_MAX, (SUN_COUNTDOWN + (this.mNumSunsFallen * 10))) + (Math.random() * SUN_COUNTDOWN_RANGE));
            String aCoinType =COIN_SUN ;
            this.AddCoin(x, 40, aCoinType, COIN_MOTION_FROM_SKY);
        }
        public void  DrawLevelLabel (Graphics2D g ){
            String aLevelStr ="";
            int aFlags ;
            String aFlagStr ;
            String aCompletedStr ;
            int aStreak ;
            String aStr ;
            Rectangle screenBounds ;
            if (this.mGameScene != PVZApp.SCENE_PLAYING)
            {
                return;
            };
            if (this.mApp.IsSurvivalMode())
            {
                aFlags = this.GetSurvivalFlagsCompleted();
                if (aFlags > 0)
                {
                    aFlagStr = this.Pluralize(aFlags, "[ONE_FLAG]", "[COUNT_FLAGS]");
                    aCompletedStr = this.mApp.stringManager().translateString("[FLAGS_COMPLETED]").replace("{FLAGS}", aFlagStr);
                    aLevelStr = ((this.mApp.stringManager().translateString("[SURVIVAL_POOL_ENDLESS]") + " ") + aCompletedStr);
                }
                else
                {
                    aLevelStr = this.mApp.stringManager().translateString("[SURVIVAL_POOL_ENDLESS]");
                };
            }
            else
            {
                if (this.mApp.IsAdventureMode())
                {
                    aLevelStr = ((this.mApp.stringManager().translateString("[LEVEL]") + " ") + this.GetStageString(this.mLevel));
                }
                else
                {
                    if (this.mApp.IsScaryPotterLevel())
                    {
                        aStreak = this.mChallenge.mSurvivalStage;
                        if (this.mNextSurvivalStageCounter > 0)
                        {
                            aStreak++;
                        };
                        if (aStreak > 0)
                        {
                            aStr = this.mApp.stringManager().translateString("[ENDLESS_STREAK]");//.replaceAll("{STREAK}", aStreak);
                            aLevelStr = aStr;
                        }
                        else
                        {
                            aLevelStr = this.mApp.stringManager().translateString("[SCARY_POTTER_ENDLESS]");
                        };
                    };
                };
            };
            if (aLevelStr != this.mLevelLabelString)
            {
                this.mLevelLabel = null;
            };
            this.mLevelLabelString = aLevelStr;
            if (this.mLevelLabel == null)
            {
                screenBounds = new Rectangle(0, 0, this.mApp.screenWidth(), this.mApp.screenHeight());
                this.mLevelFont.setColor(1, (224 / 0xFF), (187 / 0xFF), (98 / 0xFF));
                this.mLevelLabel = Utils.createStringImage(aLevelStr, this.mLevelFont, screenBounds, Utils.JUSTIFY_CENTER);
            };
            int aPosX =530;
            int aPosY =375;
            if (this.HasProgressMeter())
            {
                aPosX = 400;
            };
            aPosX = (int)(aPosX - this.mLevelLabel.width());
            g.drawImage(this.mLevelLabel, aPosX, aPosY);
        }
        private void  spawnNewspaperZombieCheat (){
            this.AddZombieInRow(ZOMBIE_NEWSPAPER, (int)((Math.random() * 5)), 1);
        }
        public void  PickBackground (){
        	Dictionary newArray ;
            Dictionary newGridCelArray ;
            Dictionary newGridCellOffsetArray ;
            int y =0;
            //add by xinghai
            System.out.println("PickBackground.1");
            this.mLevel = 11;

//            Array anArray ;
            if (this.mApp.IsAdventureMode() && this.mLevel <= 10)
            {
                this.mBackground = BACKGROUND_1_DAY;
            }
            else
            {
                this.mBackground = BACKGROUND_2_NIGHT;
            };
            if (this.mBackground == BACKGROUND_1_DAY || this.mBackground == BACKGROUND_2_NIGHT)
            {
            	this.mPlantRow.add(0,PLANTROW_NORMAL);
            	this.mPlantRow.add(1,PLANTROW_NORMAL);
            	this.mPlantRow.add(2,PLANTROW_NORMAL);
            	this.mPlantRow.add(3,PLANTROW_NORMAL);
            	this.mPlantRow.add(4,PLANTROW_NORMAL);
            	this.mPlantRow.add(5,PLANTROW_DIRT);
                if (this.mLevel == 1)
                {
                	this.mPlantRow.add(0,PLANTROW_DIRT);
                	this.mPlantRow.add(1,PLANTROW_DIRT);
                	this.mPlantRow.add(3,PLANTROW_DIRT);
                	this.mPlantRow.add(4,PLANTROW_DIRT);
                }
                else
                {
                    if (this.mLevel == 2 || this.mLevel == 3)
                    {
                    	this.mPlantRow.add(0,PLANTROW_DIRT);
                    	this.mPlantRow.add(4,PLANTROW_DIRT);
                    };
                };
            };
            int x =0;
            while (x < GRIDSIZEX)
            {
                newArray = new Dictionary();
                newGridCelArray = new Dictionary();
                newGridCellOffsetArray = new Dictionary();
                y = 0;
                while (y < GRIDSIZEY)
                {
                	Dictionary anArray = new Dictionary();
                	newGridCelArray.put(y, (Math.random()*2));
                    anArray.put(0,((Math.random()*2)-3));
                    anArray.put(1,((Math.random()*2)-3));
                	newGridCellOffsetArray.put(y,anArray);

                    if(((Integer)this.mPlantRow.elementAt(y)).intValue()==PLANTROW_DIRT)
                    {
                    	newArray.put(y,GRIDSQUARE_DIRT);
                    }
                    else
                    {
                    	newArray.put(y,GRIDSQUARE_GRASS);
                    };
                    y++;
                };
                this.mGridSquareType.put(x, newArray);
                this.mGridCelLook.put(x, newGridCelArray);
                this.mGridCelOffset.put(x, newGridCellOffsetArray);
                x++;
            };
            if (this.mBackground == BACKGROUND_2_NIGHT)
            {
                if (!this.mApp.IsAdventureMode() && !this.mApp.IsScaryPotterLevel())
                {
                    this.AddGraveStones(4, 1);
                    this.AddGraveStones(5, 1);
                    this.AddGraveStones(6, 2);
                    this.AddGraveStones(7, 2);
                    this.AddGraveStones(8, 3);
                }
                else
                {
                    if (this.mLevel == 11 || this.mLevel == 12 || this.mLevel == 13)
                    {
                        this.AddGraveStones(6, 1);
                        this.AddGraveStones(7, 1);
                        this.AddGraveStones(8, 2);
                    }
                    else
                    {
                        if (this.mLevel == 14 || this.mLevel == 16 || this.mLevel == 18)
                        {
                            this.AddGraveStones(5, 1);
                            this.AddGraveStones(6, 1);
                            this.AddGraveStones(7, 2);
                            this.AddGraveStones(8, 3);
                        };
                    };
                };
            };
            this.PickSpecialGraveStone();
        }
        public boolean  StageIsNight (){
            if (this.mBackground == BACKGROUND_2_NIGHT)
            {
                return (true);
            };
            return (false);
        }
        public void  DrawProgressMeter (Graphics2D g ){
        	/*
            int aNumWavesPerFlag ;
            int i =0;
            int aWave ;
            int aHeight ;
            int aLeft ;
            int aRight ;
            int aPosX ;
            if (!this.HasProgressMeter())
            {
                return;
            };
            if (this.mGameScene != PVZApp.SCENE_PLAYING)
            {
                return;
            };
            int aMeterX =405;
            int aMeterY =388*2;
            g.pushState();
            g.drawImage(this.FlagMeterFull, aMeterX, aMeterY);
            int aBarSize =TodCommon.TodAnimateCurve(0,PROGRESS_METER_COUNTER ,this.mProgressMeterWidth ,107,0,TodCommon.CURVE_LINEAR );
            g.setClipRect(aMeterX, aMeterY, aBarSize, 16,true);
//            g.drawImage(this.FlagMeterEmpty, aMeterX, aMeterY);
            g.setClipRect(aMeterX, aMeterY, aBarSize, 16,true);
            g.popState();
            if (this.ProgressMeterHasFlags())
            {
                aNumWavesPerFlag = this.GetNumWavesPerFlag();
                i = 1;
                while (i <= (this.mNumWaves / aNumWavesPerFlag))
                {
                    aWave = (i * aNumWavesPerFlag);
                    aHeight = 0;
                    if (aWave < this.mCurrentWave)
                    {
                        aHeight = 11;
                    }
                    else
                    {
                        if (aWave == this.mCurrentWave)
                        {
                            aHeight = TodCommon.TodAnimateCurve(FLAG_RAISE_TIME, 0, this.mFlagRaiseCounter, 0, 11, TodCommon.CURVE_LINEAR);
                        };
                    };
                    aLeft = (aMeterX + 4);
                    aRight = ((aMeterX + 107) - 8);
                    aPosX = TodCommon.TodAnimateCurve(0, this.mNumWaves, aWave, aRight, aLeft, TodCommon.CURVE_LINEAR);
                    g.drawImage(this.FlagMeterBar, aPosX, (aMeterY - 4));
                    g.drawImage(this.FlagMeterFlag, aPosX, ((aMeterY - aHeight) - 3));
                    i++;
                };
            };
            g.drawImage(this.FlagMeterBar, (aMeterX + 30), (aMeterY + 11));
            int aHeadPosX =TodCommon.TodAnimateCurve(0,PROGRESS_METER_COUNTER ,this.mProgressMeterWidth ,0,100,TodCommon.CURVE_LINEAR );
            g.drawImage(this.FlagMeterHead, (((aMeterX + 107) - aHeadPosX) - 14), (aMeterY - 2));
            */
        }
        public void  KillAllZombies (){
            Zombie aZombies;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombies = (Zombie)this.mZombies.elementAt(i);
                aZombies.DieNoLoot();
            };
        }
        
        public void KillOneZombie() {
            Zombie aZombies;
            int first = Math.min(2, this.mZombies.length());
			for(int i =0; i<first;i++)
			{
				aZombies = (Zombie)this.mZombies.elementAt(i);
                aZombies.DieNoLoot();
            };
        }
        
        public void  PutInMissingZombies (int theWaveNumber ,ZombiePicker theZombiePicker ){
            int aZombieType ;
            int i =0;
            while (i < NUM_ZOMBIE_TYPES)
            {
                aZombieType = i;
                if(((Integer)theZombiePicker.mZombieTypeCount.elementAt(aZombieType)).intValue()<=0)
                {
                    if (this.CanZombieSpawnOnLevel(aZombieType, this.mLevel))
                    {
                        this.PutZombieInWave(aZombieType, theWaveNumber, theZombiePicker);
                    };
                };
                i++;
            };
        }

        public int mTimeStopCounter ;

        public void  StartLevel (){
            if (this.mApp.IsSurvivalMode() && this.mChallenge.mSurvivalStage > 0)
            {
            };
            this.mChallenge.StartLevel();
        }
        public void  initLevel (){
            Rectangle bounds;
            SmoothArray aSmoothArray;
            SeedPacket aSeedPacket;
            this.mMainCounter = 0;
            this.mEnableGraveStones = false;
            this.mSodPosition = 0;
            if (!this.mApp.IsAdventureMode())
            {
                this.mLevel = 0;
            };
            this.PickBackground();
            this.mCurrentWave = 0;
            this.InitZombieWaves();
            if (this.mApp.IsAdventureMode() && this.mLevel == 1)
            {
                this.mSunMoney = 150;
                bounds = new Rectangle(0, 0, 40, 15);
                this.mSeedBank.mSunText = Utils.createStringImage(""+this.mSunMoney, this.mSeedBank.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
            }
            else
            {
                if (this.mApp.IsScaryPotterLevel())
                {
                    this.mSunMoney = 0;
                    bounds = new Rectangle(0, 0, 40, 15);
                    this.mSeedBank.mSunText = Utils.createStringImage(""+this.mSunMoney, this.mSeedBank.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
                }
                else
                {
                    this.mSunMoney = 50;
                    bounds = new Rectangle(0, 0, 40, 15);
                    this.mSeedBank.mSunText = Utils.createStringImage(""+this.mSunMoney, this.mSeedBank.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
                };
            };
            this.mSunMoney = 9999;
            int y =0;
            while (y < GRIDSIZEY)
            {
            	this.mWaveRowGotLawnMowered.add(y,-100);
                aSmoothArray = new SmoothArray();
                aSmoothArray.mItem=y;
                aSmoothArray.mLastPicked=0;
                aSmoothArray.mSecondLastPicked=0;

                this.mRowPickingArray.add(y,aSmoothArray);
                y++;
            };
            this.mNumSunsFallen = 0;
            if (!this.StageIsNight())
            {
                this.mSunCountDown = TodCommon.RandRangeInt(SUN_COUNTDOWN, (SUN_COUNTDOWN + SUN_COUNTDOWN_RANGE));
            };
            int i =0;
            while (i < 17)
            {
            	this.mHelpDisplayed.add(i,false);
                i++;
            };
            this.mSeedBank.mNumPackets = this.GetNumSeedsInBank();
            i = 0;
            while (i < SeedBank.SEEDBANK_MAX)
            {
                aSeedPacket = new SeedPacket(this.mApp, this);
                aSeedPacket.mIndex = i;
                aSeedPacket.mX = this.GetSeedPacketPositionX(i);
                if (this.HasConveyorBeltSeedBank())
                {
                    aSeedPacket.mY = 5;
                }
                else
                {
                    aSeedPacket.mY = 6;
                };
                aSeedPacket.mPacketType = SEED_NONE;
                this.mSeedBank.mSeedPackets.add(i,aSeedPacket);
                i++;
            };
            if (this.mApp.IsScaryPotterLevel())
            {
            	((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mPacketType=SEED_CHERRYBOMB;
            }
            else
            {
                if (!this.ChooseSeedsOnCurrentLevel() && !this.HasConveyorBeltSeedBank())
                {
                    this.mSeedBank.mNumPackets = this.GetNumSeedsInBank();
                    i = 0;
                    while (i < this.mSeedBank.mNumPackets)
                    {
                    	((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mPacketType=i;
                    	if(((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).GetRefreshTime(((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mPacketType)==3000)
                        {
                    		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshCounter=0;
                    		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshing=true;
                    		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshTime=2000;
                    		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mActive=false;
                        }
                        else
                        {
                        	if(((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).GetRefreshTime(((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mPacketType)==5000)
                            {
                        		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshCounter=0;
                        		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshing=true;
                        		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mRefreshTime=5000;
                        		((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i)).mActive=false;
                            };
                        };
                        i++;
                    };
                };
            };
            this.mApp.widgetManager().markAllDirty();
            this.mPaused = false;
            this.mOutOfMoneyCounter = 0;
//            this.InitLawnMowers();
            this.doorMask.x(-(PVZApp.BOARD_OFFSET));
            this.doorMask.y(154);
//            this.mRenderManager.add(new ImageRenderable(this.doorMask, RENDER_LAYER_ABOVE_UI));
            this.mChallenge.InitLevel();
        }

        public int mZombieCountDownStart ;
        public int mTutorialState ;
        public Array mZombiePail ;
        private ImageInst FlagMeterFlag ;
        public int mTriggeredLawnMowers ;

        public void  ProcessDeleteQueue (){
            Array newGridItems ;
            GridItem aGridItem ;
            Array newCoins ;
            Coin aCoin ;
            Array newZombies ;
            Zombie aZombie ;
            Array newLawnMowers ;
            LawnMower aLawnMower ;

            class AliveFilter implements Filter {
            	public boolean doFilt(Object item) {
                    CPlant aPlant =(CPlant)item;
                    boolean result =!(aPlant.mDead);
                    return (result);
            	}
            }

            this.mPlants = this.mPlants.filter(new AliveFilter());

            if (this.mGridItems.length() > 0)
            {
                newGridItems = new Array();
				for(int i =0; i<this.mGridItems.length();i++)
				{
					aGridItem = (GridItem)this.mGridItems.elementAt(i);
                    if (!aGridItem.mDead)
                    {
                        newGridItems.push(aGridItem);
                    };
                };
                this.mGridItems = newGridItems;
            };
            if (this.mCoins.length() > 0)
            {
                newCoins = new Array();
				for(int i =0; i<this.mCoins.length();i++)
				{
					aCoin = (Coin)this.mCoins.elementAt(i);
                    if (!aCoin.mDead)
                    {
                        newCoins.push(aCoin);
                    };
                };
                this.mCoins = newCoins;
            };
            if (this.mZombies.length() > 0)
            {
                newZombies = new Array();
				for(int i =0; i<this.mZombies.length();i++)
				{
					aZombie = (Zombie)this.mZombies.elementAt(i);
                    if (!aZombie.mDead)
                    {
                        newZombies.push(aZombie);
                    };
                };
                this.mZombies = newZombies;
            };
            if (this.mLawnMowers.length() > 0)
            {
                newLawnMowers = new Array();
				for(int i =0; i<this.mLawnMowers.length();i++)
				{
					aLawnMower = (LawnMower)this.mLawnMowers.elementAt(i);
                    if (!aLawnMower.mDead)
                    {
                        newLawnMowers.push(aLawnMower);
                    };
                };
                this.mLawnMowers = newLawnMowers;
            };
        }

        public OptionsDialog mOptionsMenu ;
        private SeedPacket seed ;
        public int mSunCountDown ;

        public int  GridToPixelX (int theGridX ,int theGridY ){
            //return (((theGridX * 54 * 3) + LAWN_XMIN));
        	//return (((theGridX * 150 ) + LAWN_XMIN));
        	
        	return (((theGridX * this.mApp.unitWidth ) + LAWN_XMIN));
        	
        }

        private PVZApp mApp ;

        public void  buttonRelease (int id ){
            switch (id)
            {
                case Menu_button:
                    this.mPaused = true;
                    this.mApp.stateManager().pushState(PVZApp.STATE_OPTIONS_MENU);
                    break;
            };
        }

        public Challenge mChallenge ;

        private void  freePlantingCheat (){
            this.mEasyPlantingCheat = true;
        }
        public void  ZombiesWon (Zombie aZombie ){
            Zombie aOtherZombie ;
            Rectangle aRect ;
            if (this.mGameScene == SCENE_ZOMBIES_WON)
            {
                return;
            };
            if (this.mNextSurvivalStageCounter > 0)
            {
                this.mNextSurvivalStageCounter = 0;
            };
			for(int i =0; i<this.mZombies.length();i++)
			{
				aOtherZombie = (Zombie)this.mZombies.elementAt(i);
                if (aOtherZombie != aZombie)
                {
                    aRect = aOtherZombie.GetZombieRect();
                    if (aRect.x < -35)
                    {
                        aOtherZombie.DieNoLoot();
                    };
                };
            };
            this.mGameScene = SCENE_ZOMBIES_WON;
            aZombie.WalkIntoHouse();
            this.TutorialArrowRemove();
            this.ClearCursor();
            this.UpdateCursor();
            this.mApp.stateManager().changeState(PVZApp.STATE_ZOMBIES_WON);
        }

        public int mZombieHealthWaveStart ;

        public void  UpdateGame (){
            this.UpdateGameObjects();
            if (this.mGameScene != PVZApp.SCENE_PLAYING)
            {
                return;
            };
            this.mMainCounter++;
            this.UpdateSunSpawning();
            this.UpdateZombieSpawning();
            if (this.mMainCounter == 1)
            {
                if (this.mLevel == 1)
                {
                    this.SetTutorialState(TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER);
                }
                else if (this.mLevel == 2)
                {
                    this.SetTutorialState(TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER);
                    this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER1]", MESSAGE_STYLE_TUTORIAL_LEVEL2, ADVICE_NONE);
                    this.mTutorialTimer = 500;
                };
            };
            this.UpdateProgressMeter();
        }
        public int  GetNumWavesPerSurvivalStage (){
            return (20);
        }
        public boolean  HasConveyorBeltSeedBank (){
            if (this.IsWallnutBowlingLevel() || this.mLevel == 10)
            {
                return (true);
            };
            return (false);
        }
        private void  spawnAwardCheat (){
            String aCoinType;
            if (this.mApp.IsAdventureMode() && this.mLevel <= 14)
            {
                if (this.mLevel == 9)
                {
                    aCoinType = Coin.COIN_NOTE;
                }
                else
                {
                    if (this.mLevel == 4)
                    {
                        aCoinType = Coin.COIN_SHOVEL;
                    }
                    else
                    {
                        aCoinType = Coin.COIN_FINAL_SEED_PACKET;
                    };
                };
                this.AddCoin(200, 200, aCoinType, COIN_MOTION_COIN);
                this.mLevelAwardSpawned = true;
            };
        }
        public boolean  MouseHitTest (int x ,int y ,HitResult theHitResult ){
            Coin aTopCoin ;
            Coin aCoin ;
            HitResult aHitResultCoin ;
            Coin aHitCoin ;
            int aGridX ;
            int aGridY ;
            GridItem aGridItem =null;
            if (this.mBoardFadeOutCounter >= 0)
            {
                theHitResult.mObject = null;
                theHitResult.mObjectType = OBJECT_TYPE_NONE;
                return (false);
            };
            if (this.mSeedBank.MouseHitTest(x, y, theHitResult))
            {
                if (this.mCursorObject.mCursorType == CURSOR_TYPE_NORMAL || this.mCursorObject.mCursorType == CURSOR_TYPE_HAMMER)
                {
                    return (true);
                };
            };
            if (this.mShowShovel && this.mShovelButtonRect.contains(x, y))
            {
                theHitResult.mObjectType = OBJECT_TYPE_SHOVEL;
                return (true);
            };
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_NORMAL || this.mCursorObject.mCursorType == CURSOR_TYPE_HAMMER)
            {
                aTopCoin = null;
			for(int i =0; i<this.mCoins.length();i++)
			{
				aCoin = (Coin)this.mCoins.elementAt(i);
                    aHitResultCoin = new HitResult();
                    if (aCoin.MouseHitTest(x, y, aHitResultCoin))
                    {
                        aHitCoin = (Coin)aHitResultCoin.mObject;
                        if ( aTopCoin ==null || aHitCoin.mRenderOrder >= aTopCoin.mRenderOrder)
                        {
                            theHitResult.mObject = aHitResultCoin.mObject;
                            theHitResult.mObjectType = aHitResultCoin.mObjectType;
                            aTopCoin = aHitCoin;
                        };
                    };
                };
                if (aTopCoin!=null)
                {
                    return (true);
                };
            };
            if (this.MouseHitTestPlant(x, y, theHitResult))
            {
                return (true);
            };
            if (this.mApp.IsScaryPotterLevel())
            {
                if (this.mCursorObject.mCursorType == CURSOR_TYPE_NORMAL && this.mChallenge.mChallengeState != Challenge.STATECHALLENGE_SCARY_POTTER_MALLETING && this.mGameScene == SCENE_PLAYING)
                {
                    aGridX = this.PixelToGridX(x, y);
                    aGridY = this.PixelToGridY(x, y);
                    aGridItem = this.GetScaryPotAt(aGridX, aGridY);
                    if (aGridItem!=null)
                    {
                        theHitResult.mObject = aGridItem;
                        theHitResult.mObjectType = OBJECT_TYPE_SCARY_POT;
                        return (true);
                    };
                };
            };

            aHitResultCoin = new HitResult();
            mHoldObject = null;
            if (this.mPengGangHu.MouseHitTest(x, y, aHitResultCoin)) {
            	mHoldObject = mPengGangHu;
                theHitResult.mObject = mPengGangHu;
                theHitResult.mObjectType = OBJECT_TYPE_PENGGANGHU;
                return (true);
            }

            theHitResult.mObject = null;
            theHitResult.mObjectType = OBJECT_TYPE_NONE;
            return (false);
        }
        public void  KillAllZombiesInRadius (int theRow ,int theX ,int theY ,int theRadius ,int theRowRange ,boolean theBurn ){
            Zombie aZombie ;
            Rectangle aZombieRect ;
            int aRowDiff ;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                aZombieRect = aZombie.GetZombieRect();
                aRowDiff = (aZombie.mRow - theRow);
                if (aRowDiff <= theRowRange && aRowDiff >= -(theRowRange))
                {
                    if (this.GetCircleRectOverlap(theX, theY, theRadius, aZombieRect))
                    {
                        if (theBurn)
                        {
                            aZombie.ApplyBurn();
                        }
                        else
                        {
//                            aZombie.TakeDamage(1800, 1);
/*                        	
                        	if(aZombie.mPosX - theX > 0) {
                        		aZombie.mPosX = aZombie.mPosX + 100;
                        	} else {
                        		aZombie.mPosX = aZombie.mPosX - 100;
                        	}
                        	
                        	if(aZombie.mPosX - theY > 0) {
                        		aZombie.mPosX = aZombie.mPosX + 100;
                        	} else {
                        		aZombie.mPosX = aZombie.mPosX - 100;
                        	}
*/
                        	aZombie.showMjHead();
                        	aZombie.mPosX = aZombie.mPosX + (aZombie.mPosX - theX) * Math.max(0.4, Math.random());
                        	aZombie.mPosY = aZombie.mPosY + (aZombie.mPosY - theY) * Math.max(0.4, Math.random());
                        	
                        };
                    };
                };
            };
        }

        private int lastTime =0;

        public void  UpdateMousePosition (){
            CPlant aPlant ;
            int aCursorSeedType ;
            int aMouseX ;
            int aMouseY ;
            GridItem aGridItem ;
            HitResult aHitResult ;
            GridItem aScaryPot ;
            this.UpdateCursor();
            this.UpdateToolTip();
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                aPlant.mHighlighted = false;
            };
            aCursorSeedType = this.GetSeedTypeInCursor();
            aMouseX = (int)(this.mApp.widgetManager().lastMouseX - x);
            aMouseY = (int)(this.mApp.widgetManager().lastMouseY - y);
            if (this.mApp.IsScaryPotterLevel())
            {
				for(int i =0; i<this.mGridItems.length();i++)
				{
					aGridItem = (GridItem)this.mGridItems.elementAt(i);
                    if (aGridItem.mGridItemType == GRIDITEM_SCARY_POT)
                    {
                        aGridItem.mHighlighted = false;
                    };
                };
                aHitResult = new HitResult();
                this.MouseHitTest(aMouseX, aMouseY, aHitResult);
                if (aHitResult.mObjectType == OBJECT_TYPE_SCARY_POT)
                {
                    aScaryPot = (GridItem)aHitResult.mObject;
                    aScaryPot.mHighlighted = true;
                    return;
                };
            };
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_SHOVEL)
            {
                this.HighlightPlantsForMouse(aMouseX, aMouseY);
            };
        }

        private int mFlagRaiseCounter ;

        public void  NextWaveComing (){
            boolean aShowFinalWaveMessage ;
            if ((this.mCurrentWave + 1) == this.mNumWaves)
            {
                aShowFinalWaveMessage = true;
                if (this.mApp.IsSurvivalMode())
                {
                    aShowFinalWaveMessage = false;
                };
                if (aShowFinalWaveMessage)
                {
                    this.mFinalWaveSoundCounter = 60;
                    this.mWaveWarning.showFinalWave(460);
                };
            };
            if (this.mCurrentWave == 0)
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.AWOOGA);
            }
            else
            {
                if (this.IsFlagWave(this.mCurrentWave))
                {
                };
            };
        }

        private String mLevelLabelString ="";

        public String  CanPlantAt (int theGridX ,int theGridY ,int theType ){
            CPlant aPlantOnLawn =null;
            if (theGridX < 0 || theGridX >= GRIDSIZEX || theGridY < 0 || theGridY >= GRIDSIZEY)
            {
                return (PLANTING_NOT_HERE);
            };
            if (this.IsWallnutBowlingLevel())
            {
                if (theGridX > 2)
                {
                    return (PLANTING_NOT_PASSED_LINE);
                };
            };
            aPlantOnLawn = this.GetPlantsOnLawn(theGridX, theGridY);
            boolean aHasGraveStone =!((this.GetGraveStoneAt(theGridX, theGridY) == null));
            System.out.println("CanPlantAt."+theGridX+":"+theGridY+":"+theType+":"+aPlantOnLawn+":"+aHasGraveStone);
            if (theType == SEED_GRAVEBUSTER)
            {
                System.out.println("CanPlantAt.1");
                if (aPlantOnLawn!=null)
                {
                    return (PLANTING_NOT_HERE);
                };
                if (aHasGraveStone)
                {
                    return (PLANTING_OK);
                };
                return (PLANTING_ONLY_ON_GRAVES);
            };

            if (aHasGraveStone)
            {
                return (PLANTING_NOT_ON_GRAVE);
            };
            boolean aHasScaryPot =!((this.GetScaryPotAt(theGridX, theGridY) == null));
            if (aHasScaryPot)
            {
                return (PLANTING_NOT_HERE);
            };
            if(((Integer)((Dictionary)this.mGridSquareType.elementAt(theGridX)).elementAt(theGridY)).intValue()==GRIDSQUARE_DIRT)
            {
                return (PLANTING_NOT_HERE);
            };
            if (aPlantOnLawn == null)
            {
                return (PLANTING_OK);
            };
            return (PLANTING_NOT_HERE);
        }

        public int mOutOfMoneyCounter ;

        private void  spawnPolevaultZombieCheat (){
            this.AddZombieInRow(ZOMBIE_POLEVAULTER, (int)((Math.random() * 5)), 1);
        }
        public void  PickUpTool (int theObjectType ){
            if (theObjectType == OBJECT_TYPE_SHOVEL)
            {
                if (this.mTutorialState == TUTORIAL_SHOVEL_PICKUP)
                {
                    this.SetTutorialState(TUTORIAL_SHOVEL_DIG);
                };
                this.mCursorObject.mCursorType = CURSOR_TYPE_SHOVEL;
                this.mApp.foleyManager().playFoley(PVZFoleyType.SHOVEL);
            };
        }
        public void  HighlightPlantsForMouse (int theMouseX ,int theMouseY ){
            CPlant aPlant =this.ToolHitTest(theMouseX ,theMouseY );
            if (aPlant!= null)
            {
                aPlant.mHighlighted = true;
            };
        }

        public int mNextSurvivalStageCounter ;
        public int mZombieCountDown ;

        public void  UpdateLevelEndSequence (){
            int aFlags ;
            boolean aIsRepick ;
            boolean aIsNote ;
            if (this.mNextSurvivalStageCounter > 0)
            {
                this.mNextSurvivalStageCounter--;
                if (this.mNextSurvivalStageCounter == 0)
                {
            			System.out.println("Board.UpdateLevelEndSequence.1 "+this.mApp.adAPI.enabled());
                    if (this.mApp.IsScaryPotterLevel())
                    {
                        if (this.mApp.adAPI.enabled())
                        {
                            this.mApp.musicManager().pauseMusic();
                            this.mApp.soundManager().pauseAll();
//                            this.mApp.adAPI.GameBreak(this.mChallenge.mSurvivalStage, this.ContinueVasebreaker);
                        }
                        else
                        {
                            this.mChallenge.PuzzleNextStageClear();
                            this.mChallenge.ScaryPotterPopulate();
                            this.mMenuButton.visible = true;
                        };
                    }
                    else
                    {
                        this.mLevelComplete = true;
                        this.RemoveZombiesForRepick();
                        aFlags = (this.GetSurvivalFlagsCompleted() + 1);
                        if (((this.mApp.IsSurvivalEndless()) && ((aFlags == 20))))
                        {
                        };
                    };
                    return;
                };
            };
            if (this.mBoardFadeOutCounter < 0)
            {
                return;
            };
            this.mBoardFadeOutCounter--;
            if (this.mBoardFadeOutCounter == 0)
            {
                this.mLevelComplete = true;
                return;
            };
            if (this.mBoardFadeOutCounter == 300)
            {
                aIsRepick = this.mApp.IsSurvivalMode();
                aIsNote = (this.mLevel == 9 || this.mLevel == 19 || this.mLevel == 29 || this.mLevel == 39 || this.mLevel == 49);
                if (!aIsRepick && !aIsNote)
                {
                };
            };
            if (!this.CanDropLoot())
            {
                return;
            };
            if (this.mApp.IsSurvivalMode())
            {
                return;
            };
        }

        public boolean mEnableGraveStones ;
        public int mNumSunsFallen ;

        public boolean  StageHasGraveStones (){
            if (this.IsWallnutBowlingLevel() || this.mApp.IsScaryPotterLevel())
            {
                return (false);
            };
            if (this.mBackground == BACKGROUND_2_NIGHT)
            {
                return (true);
            };
            return (false);
        }

        public Array mLawnMowers ;

        public int  GetSeedTypeInCursor (){
            if (!this.IsPlantInCursor())
            {
                return (SEED_NONE);
            };
            return (this.mCursorObject.mType);
        }
        public int  GridToPixelY (int theGridX ,int theGridY ){
            int aResult ;
            //aResult = ((theGridY * 67 * 3) + LAWN_YMIN);
            //aResult = ((theGridY * 180) + LAWN_YMIN);
            aResult = ((theGridY * this.mApp.unitHeight) + LAWN_YMIN);
            return (aResult);
        }
        public Zombie  AddZombieInRow (int theZombieType ,int theRow ,int theFromWave ){
            boolean aVariant =false;
            if ((Math.random() * 5) == 0)
            {
                aVariant = true;
            };
            /*
            Zombie aZombie =new Zombie ();
            if(MJCards.getInstance().isCompleted())
            	return null;
            aZombie.ZombieInitialize(MJCards.getInstance().fabai(), theRow, theZombieType, aVariant, null, theFromWave, this.mApp, this);
            */
            
            
            System.out.println("AddZombieInRow." + MJCards.getInstance().leftBai());
            
            Zombie aZombie = this.mMahJongBank.becomeZombie(theRow, theZombieType);
            System.out.println("AddZombieInRow.end ");
            
            this.mZombies.push(aZombie);
            this.mRenderManager.add(new ZombieRenderable(aZombie, (RENDER_LAYER_ZOMBIE + aZombie.mRow)));
            return (aZombie);
        }

        private int mFinalWaveSoundCounter ;

        public CPlant  AddPlant (int theGridX ,int theGridY ,int theSeedType ){
            CPlant aPlant =this.NewPlant(theGridX ,theGridY ,theSeedType );
            this.DoPlantingEffects(theGridX, theGridY, aPlant);
            return (aPlant);
        }
        public void  SetTutorialState (int theTutorialState ){
            int aPosX ;
            int aPosY ;
            Rectangle aShovelButtonRect ;
            if (theTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER)
            {
                if (this.mPlants.length() == 0)
                {
                	aPosX=((this.mSeedBank.mX+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mX)+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mWidth/3);
                	aPosY=((this.mSeedBank.mY+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mY)+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mHeight/2);
                    this.TutorialArrowShow(aPosX, aPosY);
                    this.DisplayAdvice("[ADVICE_CLICK_SEED_PACKET]", MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, ADVICE_NONE);
                }
                else
                {
                    this.DisplayAdvice("[ADVICE_ENOUGH_SUN]", MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, ADVICE_NONE);
                    this.mTutorialTimer = 400;
                };
            }
            else
            {
                if (theTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)
                {
                    this.mTutorialTimer = -1;
                    this.TutorialArrowRemove();
                    if (this.mPlants.length() == 0)
                    {
                        this.DisplayAdvice("[ADVICE_CLICK_ON_GRASS]", MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, ADVICE_NONE);
                    }
                    else
                    {
                        this.ClearAdvice(ADVICE_NONE);
                    };
                }
                else
                {
                    if (theTutorialState == TUTORIAL_LEVEL_1_REFRESH_PEASHOOTER)
                    {
                        this.DisplayAdvice("[ADVICE_PLANTED_PEASHOOTER]", MESSAGE_STYLE_TUTORIAL_LEVEL1, ADVICE_NONE);
                        this.mSunCountDown = 200;
                    }
                    else
                    {
                        if (theTutorialState == TUTORIAL_LEVEL_1_COMPLETED)
                        {
                            this.DisplayAdvice("[ADVICE_ZOMBIE_ONSLAUGHT]", MESSAGE_STYLE_TUTORIAL_LEVEL1, ADVICE_NONE);
                            this.mZombieCountDown = 100;
                            this.mZombieCountDownStart = this.mZombieCountDown;
                        }
                        else
                        {
                            if (theTutorialState == TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER || theTutorialState == TUTORIAL_MORESUN_PICK_UP_SUNFLOWER)
                            {
                            	aPosX=((this.mSeedBank.mX+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).mX)+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).mWidth/3);
                            	aPosY=((this.mSeedBank.mY+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).mY)+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).mHeight/2);
                                this.TutorialArrowShow(aPosX, aPosY);
                            }
                            else
                            {
                                if (theTutorialState == TUTORIAL_LEVEL_2_PLANT_SUNFLOWER || theTutorialState == TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER || theTutorialState == TUTORIAL_MORESUN_PLANT_SUNFLOWER || theTutorialState == TUTORIAL_MORESUN_REFRESH_SUNFLOWER)
                                {
                                    this.TutorialArrowRemove();
                                }
                                else
                                {
                                    if (theTutorialState == TUTORIAL_LEVEL_2_COMPLETED)
                                    {
                                        if (this.mCurrentWave == 0)
                                        {
                                            this.mZombieCountDown = 1000;
                                            this.mZombieCountDownStart = this.mZombieCountDown;
                                        };
                                    }
                                    else
                                    {
                                        if (theTutorialState == TUTORIAL_SHOVEL_PICKUP)
                                        {
                                            this.DisplayAdvice("[ADVICE_CLICK_SHOVEL]", MESSAGE_STYLE_HINT_STAY, ADVICE_NONE);
                                            aShovelButtonRect = this.mShovelButtonRect;
                                            aPosX = (aShovelButtonRect.x + (aShovelButtonRect.width() / 2));
                                            aPosY = (aShovelButtonRect.y + aShovelButtonRect.height());
                                            this.TutorialArrowShow(aPosX, aPosY);
                                        }
                                        else
                                        {
                                            if (theTutorialState == TUTORIAL_SHOVEL_DIG)
                                            {
                                                this.DisplayAdvice("[ADVICE_CLICK_PLANT]", MESSAGE_STYLE_HINT_STAY, ADVICE_NONE);
                                                this.TutorialArrowRemove();
                                            }
                                            else
                                            {
                                                if (theTutorialState == TUTORIAL_SHOVEL_KEEP_DIGGING)
                                                {
                                                    this.DisplayAdvice("[ADVICE_KEEP_DIGGING]", MESSAGE_STYLE_HINT_STAY, ADVICE_NONE);
                                                }
                                                else
                                                {
                                                    if (theTutorialState == TUTORIAL_SHOVEL_COMPLETED)
                                                    {
                                                        this.ClearAdvice(ADVICE_NONE);
                                                    };
                                                };
                                            };
                                        };
                                    };
                                };
                            };
                        };
                    };
                };
            };
            this.mTutorialState = theTutorialState;
        }
        public Coin  AddCoin (int theX ,int theY ,String theCoinType ,String theCoinMotion ){
            Coin aCoin =new Coin ();
            aCoin.CoinInitialize(theX, theY, theCoinType, theCoinMotion, this.mApp, this);
            if (this.mLevel == 1)
            {
                this.DisplayAdvice("[ADVICE_CLICK_ON_SUN]", MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, ADVICE_CLICK_ON_SUN);
            };
            this.mCoins.push(aCoin);
            this.mRenderManager.add(new CoinRenderable(aCoin, RENDER_LAYER_COIN_BANK));
            return (aCoin);
        }

        public Array mZombiePolevaulter ;

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

        public int mZombieHealthToNextWave ;

        public void  PickSpecialGraveStone (){
            GridItem aGridItem ;
            int MAX_GRAVE_STONES =(GRIDSIZEX *GRIDSIZEY );
            Array aPicks =new Array ();
            int aPickCount =0;
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (aGridItem.mGridItemType == GRIDITEM_GRAVESTONE)
                {
                	aPicks.add(aPickCount,aGridItem);
                    aPickCount++;
                };
            };
            if (aPickCount == 0)
            {
                return;
            };
            GridItem aResultItem=(GridItem)aPicks.elementAt(TodCommon.TodPickFromArray(aPicks,aPickCount));
            aResultItem.mGridItemState = GridItem.GRIDITEM_STATE_GRAVESTONE_SPECIAL;
        }
        public void  UpdateToolTip (){
            Zombie aZombie ;
            ZombieDef aZombieDef ;
            String aZombieName ;
            Rectangle aRect ;
            SeedPacket aSeedPacket ;
            int aUseSeedType ;
            int aCost ;
            if (this.mTimeStopCounter > 0 || this.mGameScene == SCENE_ZOMBIES_WON || this.mPaused)
            {
                this.mToolTip.mVisible = false;
                return;
            };
            int aMouseX =(int)(this.mApp.widgetManager().lastMouseX -x );
            int aMouseY =(int)(this.mApp.widgetManager().lastMouseY -y );
            if (((!((this.mGameScene == SCENE_LEVEL_INTRO))) && ((aMouseY > 80))))
            {
                this.mToolTip.mVisible = false;
                return;
            };
            if ((((this.mGameScene == SCENE_LEVEL_INTRO)) && (this.ChooseSeedsOnCurrentLevel())))
            {
                aZombie = this.ZombieHitTest(aMouseX, aMouseY);
                if ((((aZombie == null)) || (!((aZombie.mFromWave == Zombie.ZOMBIE_WAVE_CUTSCENE)))))
                {
                    this.mToolTip.mVisible = false;
                    return;
                };
                aZombieDef = this.GetZombieDefinition(aZombie.mZombieType);
                aZombieName = (("[" + aZombieDef.mZombieName) + "]");
                this.mToolTip.SetTitle(aZombieName);
                this.mToolTip.SetLabel("");
                this.mToolTip.SetWarningText("");
                aRect = aZombie.GetZombieRect();
                this.mToolTip.mX = ((aRect.x + (aRect.width() / 2)) + 5);
                this.mToolTip.mY = ((aRect.y + aRect.height()) - 10);
                this.mToolTip.mVisible = true;
                this.mToolTip.mCenter = true;
                this.mToolTip.mMinLeft = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_SEEDCHOOSER_BACKGROUND).width();
                this.mToolTip.mMaxBottom = BOARD_HEIGHT;
                return;
            };
            if (this.mGameScene != SCENE_PLAYING)
            {
                return;
            };
            this.mToolTip.mMinLeft = 0;
            this.mToolTip.mMaxBottom = BOARD_HEIGHT;
            this.mToolTip.SetLabel("");
            this.mToolTip.SetTitle("");
            this.mToolTip.SetWarningText("");
            this.mToolTip.mCenter = false;
            HitResult aHitResult =new HitResult ();
            this.MouseHitTest(aMouseX, aMouseY, aHitResult);
            if (aHitResult.mObjectType == OBJECT_TYPE_SHOVEL)
            {
                this.mToolTip.SetLabel("[SHOVEL_TOOLTIP]");
                this.mToolTip.mX = (this.mShovelButtonRect.x + 35);
                this.mToolTip.mY = (this.mShovelButtonRect.y + 72);
                this.mToolTip.mCenter = true;
                this.mToolTip.mVisible = true;
            }
            else
            {
                if (aHitResult.mObjectType == OBJECT_TYPE_SEEDPACKET)
                {
                    aSeedPacket = (SeedPacket)aHitResult.mObject;
                    aUseSeedType = aSeedPacket.mPacketType;
                    this.mToolTip.SetLabel(this.GetSeedName(aSeedPacket.mPacketType));
                    aCost = this.GetCurrentPlantCost(aSeedPacket.mPacketType);
                    if (this.mEasyPlantingCheat)
                    {
                        this.mToolTip.SetWarningText("FREE_PLANTING_CHEAT");
                    }
                    else
                    {
                        if (!aSeedPacket.mActive)
                        {
                            this.mToolTip.SetWarningText("[WAITING_FOR_SEED]");
                        }
                        else
                        {
                            if (!this.CanTakeSunMoney(aCost) && !this.HasConveyorBeltSeedBank())
                            {
                                this.mToolTip.SetWarningText("[NOT_ENOUGH_SUN]");
                            };
                        };
                    };
                    this.mToolTip.mX = (((this.mSeedBank.mX + aSeedPacket.mX) + aSeedPacket.mOffsetX) + ((50 - this.mToolTip.mWidth) / 2));
                    this.mToolTip.mY = ((this.mSeedBank.mY + aSeedPacket.mY) + 70);
                    this.mToolTip.mVisible = true;
                }
                else
                {
                    this.mToolTip.mVisible = false;
                };
            };
        }

        public Array mMaxZombiesInWave ;

        public int  GetRectOverlap (Rectangle rect1 ,Rectangle rect2 ){
            int aLeft1 ;
            int aRight1 ;
            int aLeft2 ;
            int aRight2 ;
            if (rect1.x < rect2.x)
            {
                aLeft1 = rect1.x;
                aRight1 = (rect1.x + rect1.width());
                aLeft2 = rect2.x;
                aRight2 = (rect2.x + rect2.width());
            }
            else
            {
                aLeft1 = rect2.x;
                aRight1 = (rect2.x + rect2.width());
                aLeft2 = rect1.x;
                aRight2 = (rect1.x + rect1.width());
            };
            if (aRight1 <= aLeft2)
            {
                return ((aRight1 - aLeft2));
            };
            if (aRight1 <= aRight2)
            {
                return ((aRight1 - aLeft2));
            };
            return ((aRight2 - aLeft2));
        }

        public ToolTipWidget mToolTip ;

        public int  GetNumWavesPerFlag (){
            if (((this.mApp.IsAdventureMode()) && ((this.mNumWaves < 10))))
            {
                return (this.mNumWaves);
            };
            return (10);
        }
        public int  GetPosYBasedOnRow (int thePosX ,int theRow ){
            return this.GridToPixelY(0, theRow);
        }
        public void  InitZombieWaves (){
            int i =0;
            while (i < 8)
            {
            	this.mZombieAllowed.put(i,false);
                i++;
            };
            if (this.mApp.IsAdventureMode())
            {
                this.InitZombieWavesForLevel(this.mLevel);
            }
            else
            {
                this.mChallenge.InitZombieWaves();
            };
            this.PickZombieWaves();
            this.mCurrentWave = 0;
            this.mTotalSpawnedWaves = 0;
            if (this.mLevel == 2)
            {
                this.mZombieCountDown = 5000;
            }
            else
            {
                if (this.mApp.IsSurvivalMode() && this.mChallenge.mSurvivalStage > 0)
                {
                    this.mZombieCountDown = 600;
                }
                else
                {
                    this.mZombieCountDown = 1800;
                };
            };
            this.mZombieCountDownStart = this.mZombieCountDown;
            this.mZombieHealthToNextWave = -1;
            this.mZombieHealthWaveStart = 0;
            this.mLastBungeeWave = 0;
            this.mProgressMeterWidth = 0;
            this.mHugeWaveCountDown = 0;
            this.mLevelAwardSpawned = false;
        }
        private void  spawnGraveZombieCheat (){
            this.SpawnZombiesFromGraves();
        }

        public Array mZombieNewsPaper ;

        private void  spawnBucketZombieCheat (){
            this.AddZombieInRow(ZOMBIE_PAIL, (int)((Math.random() * 5)), 1);
        }

        private ImageInst doorOverlay ;

        public CPlant  NewPlant (int theGridX ,int theGridY ,int theSeedType ){
            CPlant aPlant ;
            switch (theSeedType)
            {
                case SEED_PEASHOOTER:
                    aPlant = new PeaShooter(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_SUNFLOWER:
                    aPlant = new Sunflower(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_CHERRYBOMB:
                    aPlant = new CherryBomb(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_WALLNUT:
                    aPlant = new Wallnut(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_SQUASH:
                    aPlant = new Squash(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_CHOMPER:
                    aPlant = new Chomper(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_SNOWPEA:
                    aPlant = new SnowPea(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_REPEATER:
                    aPlant = new Repeater(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_EXPLODE_O_NUT:
                    aPlant = new ExplodoNut(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_PUFFSHROOM:
                    aPlant = new PuffShroom(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_FUMESHROOM:
                    aPlant = new FumeShroom(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_SUNSHROOM:
                    aPlant = new SunShroom(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_GRAVEBUSTER:
                    aPlant = new GraveBuster(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                case SEED_LEFTPEATER:
                    aPlant = new LeftPeater(theGridX, theGridY, theSeedType, this.mApp, this);
                    break;
                default:
                    aPlant = new PeaShooter(theGridX, theGridY, theSeedType, this.mApp, this);
            };
            this.mPlants.push(aPlant);
            this.mRenderManager.add(new PlantRenderable(aPlant, RENDER_LAYER_PLANT));
            return (aPlant);
        }
        private void  spawnNormalZombieCheat (){
            this.AddZombieInRow(ZOMBIE_NORMAL, (int)((Math.random() * 5)), 1);
        }
        public void  TutorialArrowShow (int x ,int y ){
            this.TutorialArrowRemove();
            this.mTutorialArrow = this.mApp.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SEEDPACKETPICK);
            this.mTutorialArrow.setPosition(x, (y + 10));
            this.mRenderManager.add(new ParticleRenderable(this.mTutorialArrow, RENDER_LAYER_TOP));
        }

        public Array mZombieGoldenPail ;

         public void  onKeyDown (int keyCode ){
        }
         public void  onKeyUp (int keyCode ){
            switch (keyCode)
            {
                case 32:
                    if (!this.mMenuButton.visible)
                    {
                        return;
                    };
                    if (this.mPaused)
                    {
                        this.mPaused = false;
                        this.mApp.stateManager().changeState(PVZApp.STATE_PLAY_LEVEL);
                    }
                    else
                    {
                        this.mPaused = true;
                        this.mApp.stateManager().changeState(PVZApp.STATE_OPTIONS_MENU);
                    };
                    break;
            };
        }
        public void  DrawSeedType (Graphics2D g ,int theSeedType ,String theDrawVariation ,int thePosX ,int thePosY ){
            Reanimation thePlant ;
            Matrix aTransform ;
            int aOffsetX =0;
            int aOffsetY =0;
            int aFrame =2;
            int aRow ;
            int aDrawSeedType =theSeedType ;
            String aDrawVariation =theDrawVariation ;
            String aPlantDef =this.GetPlantDefinition(aDrawSeedType );
            if (aPlantDef != "none")
            {
                thePlant = this.mApp.reanimator().createReanimation(aPlantDef);
                thePlant.x((thePosX + aOffsetX));
                thePlant.y((thePosY + aOffsetY));
                if (theSeedType == SEED_PEASHOOTER)
                {
                    thePlant.currentTrack("anim_full_idle");
                }
                else
                {
                    thePlant.currentTrack("anim_idle");
                };
                thePlant.update();
                aTransform = new Matrix();
                if (theDrawVariation == "cursor")
                {
                    thePlant.drawLerp(g, aTransform, 0.5);
                }
                else
                {
                    thePlant.drawLerp(g, aTransform, 0);
                };
                return;
            };
        }
        public int  PixelToGridY (int theX ,int theY ){
            int aGridX =this.PixelToGridX(theX ,theY );
            if (aGridX == -1 || theY < LAWN_YMIN)
            {
                return (-1);
            };
//            return (TodCommon.ClampInt(((theY - LAWN_YMIN) / 67), 0, 4));
          return (TodCommon.ClampInt(((theY - LAWN_YMIN) / (this.mApp.unitHeight * 2)), 0, 4));
        }
        public void  buttonDownTick (int id ){
        }
        public void  RemoveCutsceneZombies (){
            Zombie aZombie ;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (aZombie.mFromWave == -2)
                {
                    aZombie.DieNoLoot();
                };
            };
        }
         public void  update (){
            markDirty(null);
            this.UpdateMousePosition();
            this.ProcessDeleteQueue();
            if (this.mPaused)
            {
                this.mChallenge.Update();
                this.mCursorPreview.mVisible = false;
                this.mCursorObject.mVisible = false;
                return;
            };
            this.mAdvice.Update();
            this.UpdateTutorial();
            if (this.mOutOfMoneyCounter > 0)
            {
                this.mOutOfMoneyCounter--;
            };
            if (this.mShakeCounter > 0)
            {
                this.mShakeCounter--;
                if (this.mShakeCounter == 0)
                {
                    x = 0;
                    y = 0;
                }
                else
                {
                    if ((Math.random() * 2) == 0)
                    {
                        this.mShakeAmountX = -(this.mShakeAmountX);
                    };
                    x = TodCommon.TodAnimateCurve(BOARD_SHAKE_TIME, 0, this.mShakeCounter, 0, this.mShakeAmountX, TodCommon.CURVE_BOUNCE);
                    y = TodCommon.TodAnimateCurve(BOARD_SHAKE_TIME, 0, this.mShakeCounter, 0, this.mShakeAmountY, TodCommon.CURVE_BOUNCE);
                    move((int)x, (int)y);
                };
            };
            this.UpdateGridItems();
            this.UpdateGame();
            this.mChallenge.Update();
//            this.UpdateLevelEndSequence();
            this.mWaveWarning.update();
            
        }
        public boolean  HasLevelAwardDropped (){
            if (this.mLevelAwardSpawned)
            {
                return (true);
            };
            if ((((this.mNextSurvivalStageCounter > 0)) || ((this.mBoardFadeOutCounter >= 0))))
            {
                return (true);
            };
            return (false);
        }

        public ImageInst mShovelBankImage ;
        private ImageInst FlagMeterHead ;
        public CutScene mCutScene ;
        private ImageInst seedBack ;

        public void  UpdateProgressMeter (){
            int aNumFlags ;
            int aNumFlagsPassed ;
            int aTotalHealth ;
            int aHealthRange ;
            int aHealthFraction ;
            if (this.mCurrentWave == 0)
            {
                return;
            };
            if (this.mFlagRaiseCounter > 0)
            {
                this.mFlagRaiseCounter--;
            };
            int FLAG_GAP_SIZE =9;
            int aWaveMeterPixelsToCover =PROGRESS_METER_COUNTER ;
            int aNumWavesPerFlag =this.GetNumWavesPerFlag ();
            if (this.ProgressMeterHasFlags())
            {
                aNumFlags = (this.mNumWaves / aNumWavesPerFlag);
                aWaveMeterPixelsToCover = (aWaveMeterPixelsToCover - (aNumFlags * FLAG_GAP_SIZE));
            };
            int aWaveWidth =(aWaveMeterPixelsToCover /(this.mNumWaves -1));
            int aFlagMeterWidthPrev =(((this.mCurrentWave -1)*aWaveMeterPixelsToCover )/(this.mNumWaves -1));
            int aFlagMeterWidthNext =((this.mCurrentWave *aWaveMeterPixelsToCover )/(this.mNumWaves -1));
            if (this.ProgressMeterHasFlags())
            {
                aNumFlagsPassed = (this.mCurrentWave / aNumWavesPerFlag);
                aFlagMeterWidthPrev = (aFlagMeterWidthPrev + (aNumFlagsPassed * FLAG_GAP_SIZE));
                aFlagMeterWidthNext = (aFlagMeterWidthNext + (aNumFlagsPassed * FLAG_GAP_SIZE));
            };
            int aFraction =(this.mZombieCountDownStart -this.mZombieCountDown )/this.mZombieCountDownStart;
            if (this.mZombieHealthToNextWave != -1)
            {
                aTotalHealth = this.TotalZombiesHealthInWave((this.mCurrentWave - 1));
                aHealthRange = Math.max((this.mZombieHealthWaveStart - this.mZombieHealthToNextWave), 1);
                aHealthFraction = ((aHealthRange - aTotalHealth) + this.mZombieHealthToNextWave) / aHealthRange;
                if (aHealthFraction > aFraction)
                {
                    aFraction = aHealthFraction;
                };
            };
            int aDestPosition = aFlagMeterWidthPrev +Math.round ( (aFlagMeterWidthNext -aFlagMeterWidthPrev )*aFraction );
            aDestPosition = TodCommon.ClampInt(aDestPosition, 1, PROGRESS_METER_COUNTER);
            int aDelta =(aDestPosition -this.mProgressMeterWidth );
            if ((((aDelta > aWaveWidth)) && (((this.mMainCounter % 5) == 0))))
            {
                this.mProgressMeterWidth++;
            }
            else
            {
                if ((((aDelta > 0)) && (((this.mMainCounter % 20) == 0))))
                {
                    this.mProgressMeterWidth++;
                };
            };
        }

        public boolean mLevelAwardSpawned ;
        public Array mPlantRow ;
        public Array mProjectiles ;

        public boolean  RowCanHaveZombies (int theRow ){
            if ((((theRow < 0)) || ((theRow >= GRIDSIZEY))))
            {
                return (false);
            };
            if(((Integer)this.mPlantRow.elementAt(theRow)).intValue()==PLANTROW_DIRT)
            {
                return (false);
            };
            return (true);
        }

        public Array mZombieFlag ;

        public int  GetSeedPacketPositionX (int theIndex ){
            int aSeedsInBank =this.mSeedBank.mNumPackets ;
            if (this.IsSlotMachineLevel())
            {
                return (((theIndex * 59) + 247));
            };
            if (this.HasConveyorBeltSeedBank())
            {
                return (((theIndex * 47) + 5));
            };
            if (aSeedsInBank <= 6)
            {
                return (((theIndex * mApp.unitWidth) + 74));
            };
            if (aSeedsInBank == 7)
            {
                return (((theIndex * 59) + 85));
            };
            if (aSeedsInBank == 8)
            {
                return (((theIndex * 54) + 81));
            };
            if (aSeedsInBank == 9)
            {
                return (((theIndex * 52) + 80));
            };
            return (((theIndex * 51) + 79));
        }
        public int  PickGraveRisingZombieType (int theZombiePoints ){
            int aZombieType ;
            ZombieDef aZombieDef ;
            Array aZombieWeightArray = new Array();
            int aPickCount =0;
            WeightedArray temp1 = new WeightedArray();
            temp1.mItem=ZOMBIE_NORMAL;
            temp1.mWeight=this.GetZombieDefinition(ZOMBIE_NORMAL).mPickWeight;
            aZombieWeightArray.add(aPickCount,temp1);

            aPickCount++;
            temp1 = new WeightedArray();
            temp1.mItem=ZOMBIE_TRAFFIC_CONE;
            temp1.mWeight=this.GetZombieDefinition(ZOMBIE_TRAFFIC_CONE).mPickWeight;
            aZombieWeightArray.add(aPickCount,temp1);

            aPickCount++;
            if (!this.StageHasGraveStones())
            {
                temp1 = new WeightedArray();
                aZombieWeightArray.add(aPickCount,temp1);
                temp1.mItem=ZOMBIE_PAIL;
                temp1.mWeight=this.GetZombieDefinition(ZOMBIE_PAIL).mPickWeight;
                aPickCount++;
            };
            int i =0;
            while (i < aPickCount)
            {
            	aZombieType=(int)(((WeightedArray)aZombieWeightArray.elementAt(i)).mItem);
                aZombieDef = this.GetZombieDefinition(aZombieType);
                if (this.mLevel < aZombieDef.mStartingLevel)
                {
                	((WeightedArray)aZombieWeightArray.elementAt(i)).mWeight=0;
                }
                else
                {
                    if (!((Boolean)this.mZombieAllowed.elementAt(aZombieType)).booleanValue() && aZombieType != ZOMBIE_NORMAL)
                    {
                    	((WeightedArray)aZombieWeightArray.elementAt(i)).mWeight=0;
                    }
                    else
                    {
                    	((WeightedArray)aZombieWeightArray.elementAt(i)).mWeight=aZombieDef.mPickWeight;
                    };
                };
                i++;
            };
            return ((int)(TodCommon.TodPickFromWeightedArray(aZombieWeightArray, aPickCount)));
        }

        public Array mZombieAllowedLevels ;

        public void  DrawDebugRects (Graphics2D g ){
            int x =0;
            int y =0;
            int h ;
            int w ;
            Rectangle aRect ;
            CPlant aPlant ;
            Zombie aZombie ;
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                aRect = aPlant.GetPlantRect();
                g.fillRect(aRect.x, aRect.y, aRect.width(), aRect.height(), 0xFFE60000);
            };
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                aRect = aZombie.GetZombieRect();
                g.fillRect(aRect.x, aRect.y, aRect.width(), aRect.height(), 0xFFE60000);
                aRect = aZombie.GetZombieAttackRect();
                g.fillRect(aRect.x, aRect.y, aRect.width(), aRect.height(), 0xFF00E616);
            };
        }

        public int mLastBungeeWave ;

        public boolean  HasProgressMeter (){
            if (this.mProgressMeterWidth == 0)
            {
                return (false);
            };
            if (this.mApp.IsScaryPotterLevel())
            {
                return (false);
            };
            return (true);
        }
        public boolean  AreEnemyZombiesOnScreen (){
            Zombie aZombie ;
            if (this.mZombies.length() == 0)
            {
                return (false);
            };
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (aZombie.mHasHead)
                {
                    if (!aZombie.IsDeadOrDying())
                    {
                        if (!aZombie.mMindControlled)
                        {
                            return (true);
                        };
                    };
                };
            };
            return (false);
        }
        private void  refreshSeedsCheat (){
            SeedPacket aSeedPacket;
			for(int i =0; i<this.mSeedBank.mSeedPackets.length();i++)
			{
				aSeedPacket = (SeedPacket)this.mSeedBank.mSeedPackets.elementAt(i);
                if (aSeedPacket.mPacketType == SEED_NONE)
                {
                    break;
                };
                if (aSeedPacket.mRefreshing)
                {
                    aSeedPacket.mRefreshCounter = 0;
                    aSeedPacket.mRefreshing = false;
                    aSeedPacket.Activate();
                    aSeedPacket.FlashIfReady();
                };
            };
        }
        
        public void  onMouseMove (int x ,int y ){
        	if(mHoldObject == mPengGangHu)
        		this.mPengGangHu.onMouseMove(x, y);
        }
        
        public void  onMouseUp (int x ,int y ){
        	if(mHoldObject == mPengGangHu)
        		this.mPengGangHu.onMouseUp(x, y);
        	mHoldObject = null;
        }
        
        
        public void  InitSurvivalStage (){
            this.RefreshSeedPacketFromCursor();
            this.mLevelComplete = false;
            this.InitZombieWaves();
            this.mGameScene = SCENE_LEVEL_INTRO;
            int y =0;
            while (y < GRIDSIZEY)
            {
            	this.mWaveRowGotLawnMowered.add(y, -100);
                y++;
            };
        }
        private void  nextLevelCheat (){
        	System.out.println("nextLevelCheat.1");
        	
            if (this.mApp.IsAdventureMode())
            {
                this.mApp.mLevel++;
                if (this.mApp.mLevel > 14)
                {
                    this.mApp.mLevel = 14;
                };
                this.mApp.stateManager().changeState(PVZApp.STATE_LEVEL_INTRO);
            }
            else
            {
                if (this.mApp.IsSurvivalMode())
                {
                    this.KillAllZombies();
                    this.mChallenge.mSurvivalStage++;
                    this.InitSurvivalStage();
                    this.PlaceStreetZombies();
                    this.mApp.mCutsceneTime = 0;
                    //this.mApp.stateManager().changeState(PVZApp.STATE_SURVIVAL_REPICK);
                };
            };
            System.out.println("nextLevelCheat.2");
            
        }
        public void  PickZombieWaves (){
            int aWaveLevelIndex;
            Array aZombieWave;
            boolean aIsFlagWave;
            boolean aIsFinalWave;
            boolean aSpawnIntroZombie;
            int aBasicZombiePoints;
            int i =0;
            int aZombieType ;
            System.out.println("PickZombieWaves");
            if (this.mApp.IsAdventureMode())
            {
                aWaveLevelIndex = TodCommon.ClampInt((this.mLevel - 1), 0, (14 - 1));
                this.mNumWaves= ((Integer)(this.mZombieWaves.elementAt(aWaveLevelIndex))).intValue();
            }
            else
            {
                if (this.mApp.IsSurvivalMode())
                {
                    this.mNumWaves = this.GetNumWavesPerSurvivalStage();
                };
            };
            ZombiePicker aZombiePicker =new ZombiePicker();
            this.ZombiePickerInit(aZombiePicker);
            int aIntroZombieType =this.GetIntroducedZombieType();
            int aWaveNumber =0;
            while (aWaveNumber < this.mNumWaves)
            {
                this.ZombiePickerInitForWave(aZombiePicker);
                aZombieWave = new Array();
                aZombieWave.add(0,ZOMBIE_INVALID);
                this.mZombiesInWave.add(aWaveNumber,aZombieWave);
                aIsFlagWave = this.IsFlagWave(aWaveNumber);
                aIsFinalWave = (aWaveNumber == (this.mNumWaves - 1));
                if (((this.mApp.IsSurvivalMode()) && ((this.mChallenge.mSurvivalStage > 0))))
                {
                    aZombiePicker.mZombiePoints = (((((this.mChallenge.mSurvivalStage * this.GetNumWavesPerSurvivalStage()) + aWaveNumber) * 2) / 5) + 1);
                }
                else
                {
                    aZombiePicker.mZombiePoints = ((aWaveNumber / 3) + 1);
                };
                if (aIsFlagWave)
                {
                    aBasicZombiePoints = Math.min(aZombiePicker.mZombiePoints, 8);
                    aZombiePicker.mZombiePoints = (int)(aZombiePicker.mZombiePoints * 2.5);
                    i = 0;
                    while (i < aBasicZombiePoints)
                    {
                        this.PutZombieInWave(ZOMBIE_NORMAL, aWaveNumber, aZombiePicker);
                        i++;
                    };
                    this.PutZombieInWave(ZOMBIE_FLAG, aWaveNumber, aZombiePicker);
                };
                if (this.IsWallnutBowlingLevel())
                {
                    aZombiePicker.mZombiePoints = (aZombiePicker.mZombiePoints * 4);
                }
                else
                {
                    if (this.mApp.IsAdventureMode() && this.mLevel == 10)
                    {
                        aZombiePicker.mZombiePoints = (aZombiePicker.mZombiePoints * 3);
                    };
                };
                aSpawnIntroZombie = false;
                if (aWaveNumber == (this.mNumWaves / 2) || aIsFinalWave)
                {
                    aSpawnIntroZombie = true;
                };
                if (aSpawnIntroZombie)
                {
                    if (aIntroZombieType != ZOMBIE_INVALID)
                    {
                        this.PutZombieInWave(aIntroZombieType, aWaveNumber, aZombiePicker);
                    };
                };
                if (this.mApp.IsAdventureMode() && aIsFinalWave)
                {
                    this.PutInMissingZombies(aWaveNumber, aZombiePicker);
                };
                while (aZombiePicker.mZombiePoints > 0 && aZombiePicker.mZombieCount < MAX_ZOMBIES_IN_WAVE)
                {
                    aZombieType = this.PickZombieType(aZombiePicker.mZombiePoints, aWaveNumber, aZombiePicker);
                    this.PutZombieInWave(aZombieType, aWaveNumber, aZombiePicker);
                };
                aWaveNumber++;
            };
            i = 0;
            while (i < MAX_ZOMBIES_IN_WAVE)
            {
                i++;
            };
        }

        public int mProgressMeterWidth ;
        public int mLevel ;

        public void  InitZombieWavesForLevel (int aForLevel ){
            int i =0;
            while (i < NUM_ZOMBIE_TYPES)
            {
            	this.mZombieAllowed.put(i, this.CanZombieSpawnOnLevel(i,aForLevel));
                if (this.CanZombieSpawnOnLevel(i, aForLevel))
                {
                };
                i++;
            };
        }

        public int mTutorialTimer ;

        public void  PlaceAZombie (int theZombieType ,int theGridX ,int theGridY ){
        	System.out.println("PlaceAZombie."+theGridX+":"+theGridY);

        	/*
        	Zombie aZombie =new Zombie ();
            boolean aVariant =false;
            if ((Math.random() * 5) == 0)
            {
                aVariant = true;
            };
            aZombie.ZombieInitialize(MJCards.getInstance().fabai(),0, theZombieType, aVariant, null, -2, this.mApp, this.mApp.mBoard);
            */
        	System.out.println("PlaceAZombie.1");
        	return; 
        	/*
        	Zombie aZombie = this.mMahJongBank.becomeZombie(0, theZombieType);
            
            this.mApp.mBoard.mZombies.push(aZombie);
            aZombie.mPosX = (600 + (28 * theGridX));
            aZombie.mPosY = (77 + (50 * theGridY));
            if ((theGridX % 2) == 1)
            {
                aZombie.mPosY = (aZombie.mPosY + 21);
            }
            else
            {
                aZombie.mPosY = (aZombie.mPosY + (Math.random() * 10));
                aZombie.mPosX = (aZombie.mPosX + (Math.random() * 10));
            };
            this.mApp.mBoard.mRenderManager.add(new ZombieRenderable(aZombie, (int)(Board.RENDER_LAYER_ZOMBIE + aZombie.mPosY)));
            System.out.println("PlaceAZombie.2");
            */
        }

        public int mShakeCounter ;
        


        public boolean  CanAddGraveStoneAt (int theGridX ,int theGridY ){
            GridItem aGridItem;
            if(((Integer)((Dictionary)this.mGridSquareType.elementAt(theGridX)).elementAt(theGridY)).intValue()!= GRIDSQUARE_GRASS)
            {
                return (false);
            };
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (aGridItem.mGridX == theGridX && aGridItem.mGridY == theGridY)
                {
                    if (aGridItem.mGridItemType == GRIDITEM_GRAVESTONE)
                    {
                        return (false);
                    };
                };
            };
            return (true);
        }
        public void  ZombiePickerInitForWave (ZombiePicker theZombiePicker ){
            theZombiePicker.mZombieCount = 0;
            theZombiePicker.mZombiePoints = 0;
            int i =0;
            while (i < NUM_ZOMBIE_TYPES)
            {
            	theZombiePicker.mZombieTypeCount.put(i,0);
                i++;
            };
        }
        
        public void  buttonMouseMove (int id ,int x ,int y ){
        }
        
        public void  DrawShovel (Graphics2D g ){
            int x =0;
            if (this.HasConveyorBeltSeedBank())
            {
                this.mShovelButtonRect.x = 387;
                x = 387;
            }
            else
            {
                x = 401;
            };
            x = x*7;
            if (this.mShowShovel)
            {
                g.pushState();
//                g.scale(0.5, 0.5);
                g.blitImage(this.mShovelBankImage, x, 0);
                g.popState();
                g.pushState();
//                g.scale(0.3, 0.3);
                if (this.mCursorObject.mCursorType != CURSOR_TYPE_SHOVEL)
                {
                    g.blitImage(this.mShovelImage, (x + 11), 12);
                	//g.blitImage(this.mShovelImage, (x + 1110), 12);
                };
                g.popState();
                g.scale(1, 1);
            };
        }
        public String  GetSeedToolTip (int theType ){
            String aName ="";
            switch (theType)
            {
                case 0:
                    aName = "[PEASHOOTER_TOOLTIP]";
                    break;
                case 1:
                    aName = "[SUNFLOWER_TOOLTIP]";
                    break;
                case 2:
                    aName = "[CHERRY_BOMB_TOOLTIP]";
                    break;
                case 3:
                    aName = "[WALL_NUT_TOOLTIP]";
                    break;
                case 4:
                    aName = "[SQUASH_TOOLTIP]";
                    break;
                case 5:
                    aName = "[SNOW_PEA_TOOLTIP]";
                    break;
                case 6:
                    aName = "[CHOMPER_TOOLTIP]";
                    break;
                case 7:
                    aName = "[REPEATER_TOOLTIP]";
                    break;
                case 8:
                    aName = "[PUFF_SHROOM_TOOLTIP]";
                    break;
                case 9:
                    aName = "[SUN_SHROOM_TOOLTIP]";
                    break;
                case 10:
                    aName = "[FUME_SHROOM_TOOLTIP]";
                    break;
                case 11:
                    aName = "[GRAVE_BUSTER_TOOLTIP]";
                    break;
                case 50:
                    aName = "[EXPLODE_O_NUT_TOOLTIP]";
                    break;
            };
            return (aName);
        }
        private void  spawnFootballZombieCheat (){
            this.AddZombieInRow(ZOMBIE_FOOTBALL, (int)((Math.random() * 5)), 1);
        }

        private ImageInst background ;

        public String  Pluralize (int theCount ,String theSingular ,String thePlural ){
            String aResult ;
            if (theCount == 1)
            {
                aResult = this.mApp.stringManager().translateString(theSingular);
                if (aResult.startsWith("{COUNT}"))
                {
                    aResult.replace("{COUNT}", (theCount + " "));
                };
                return (aResult);
            };
            aResult = this.mApp.stringManager().translateString(thePlural);
            if (aResult.startsWith("{COUNT}"))
            {
                aResult = aResult.replace("{COUNT}", (theCount + " "));
            };
            return (aResult);
        }
        public void  UpdateGridItems (){
            GridItem aGridItem ;
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (((this.mEnableGraveStones) && ((aGridItem.mGridItemType == GRIDITEM_GRAVESTONE))))
                {
                    if (aGridItem.mGridItemCounter < 30)
                    {
                        aGridItem.mGridItemCounter++;
                    };
                };
            };
        }
        public int TotalZombiesHealthInWave (int theWaveIndex ){
            Zombie aZombie;
            int aTotalHealth =0;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (aZombie.mFromWave != theWaveIndex)
                {
                }
                else
                {
                    if (aZombie.IsDeadOrDying())
                    {
                    }
                    else
                    {
                        aTotalHealth = (aTotalHealth + aZombie.mBodyHealth);
                        aTotalHealth = (aTotalHealth + aZombie.mHelmHealth);
                        aTotalHealth = (int)(aTotalHealth + (aZombie.mShieldHealth * 0.2));
                        aTotalHealth = (aTotalHealth + aZombie.mFlyingHealth);
                    };
                };
            };
            return (aTotalHealth);
        }

        public Array mHelpDisplayed ;

        public void  RemoveZombiesForRepick (){
            Zombie aZombie ;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (aZombie.IsDeadOrDying())
                {
                }
                else
                {
                    if (aZombie.mMindControlled && aZombie.mPosX > 486)
                    {
                        aZombie.DieNoLoot();
                    };
                };
            };
        }

        public Dictionary mZombieAllowed ;
        public Dictionary mGridCelLook ;

        public int  GetNumSeedsInBank (){
            if (this.mApp.IsScaryPotterLevel())
            {
                return (1);
            };
            if (this.HasConveyorBeltSeedBank())
            {
                return (8);
            };
            int aSeedPackets =6;
            if (this.mApp.IsSurvivalMode())
            {
                aSeedPackets = 4;
            }
            else
            {
                if (this.mLevel < aSeedPackets)
                {
                    aSeedPackets = this.mLevel;
                }
                else
                {
                    if (this.mLevel == 6)
                    {
                        aSeedPackets = 5;
                    };
                };
            };

            //return (aSeedPackets);
            return 6;
        }
        public void  DrawFadeOut (Graphics2D g ){
            if (this.mBoardFadeOutCounter < 0)
            {
                return;
            };
            if (((this.mApp.IsSurvivalMode()) && (!(this.IsFinalSurvivalStage()))))
            {
                return;
            };
            if (this.mLevel == 14)
            {
                return;
            };
            int aAlpha =TodCommon.TodAnimateCurve(200,0,this.mBoardFadeOutCounter ,0,0xFF ,TodCommon.CURVE_LINEAR );
            g.pushState();
            int aColor =(aAlpha <<24);
            if (this.mLevel == 9 || this.mLevel == 19 || this.mLevel == 29 || this.mLevel == 39 || this.mLevel == 49)
            {
                aColor = (aColor | 0);
            }
            else
            {
                aColor = (aColor | 0xFFFFFF);
            };
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT, aColor);
            g.popState();
        }
        public int  GetSurvivalFlagsCompleted (){
            int aNumWavesPerFlag =this.GetNumWavesPerFlag ();
            int aFromPreviousStages =((this.mChallenge.mSurvivalStage *this.GetNumWavesPerSurvivalStage ())/aNumWavesPerFlag );
            if (this.IsFlagWave(this.mCurrentWave - 1) && this.mBoardFadeOutCounter < 0 && this.mNextSurvivalStageCounter == 0)
            {
                return ((((this.mCurrentWave - 1) / aNumWavesPerFlag) + aFromPreviousStages));
            };
            return (((this.mCurrentWave / aNumWavesPerFlag) + aFromPreviousStages));
        }
        public void  DoPlantingEffects (int gridX ,int gridY ,CPlant plant ){
            int aXPos =(int)(this.GridToPixelX(gridX ,gridY )+(41*0.675));
            int aYPos =(int)(this.GridToPixelY(gridY ,gridY )+(74*0.675));
            this.mApp.foleyManager().playFoley(PVZFoleyType.PLANT);
            ParticleSystem anEffect =this.mApp.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_PLANTING );
            anEffect.setPosition(aXPos, aYPos);
            this.mRenderManager.add(new ParticleRenderable(anEffect, RENDER_LAYER_PARTICLE));
        }

        public CursorObject mCursorObject ;
        public PengGangHu mPengGangHu;
        
        public int mBoardFadeOutCounter ;

        private void  spawnScreendoorZombieCheat (){
            this.AddZombieInRow(ZOMBIE_DOOR, (int)((Math.random() * 5)), 1);
        }
        public boolean  IsInShovelTutorial (){
            if (this.mTutorialState == TUTORIAL_SHOVEL_PICKUP || this.mTutorialState == TUTORIAL_SHOVEL_DIG || this.mTutorialState == TUTORIAL_SHOVEL_KEEP_DIGGING)
            {
                return (true);
            };
            return (false);
        }
        public Projectile  AddProjectile (int theX ,int theY ,int aRenderOrder ,int theRow ,int projectileType ){
            Projectile proj =null;
            Projectile aProjectile =null;
            int aNumProjectiles =this.mProjectiles.length() ;
            int i =0;
            while (i < aNumProjectiles)
            {
            	proj=(Projectile)this.mProjectiles.elementAt(i);
                if (proj.mDead)
                {
                    aProjectile = proj;
                    break;
                };
                i++;
            };
            if (aProjectile == null)
            {
                aProjectile = new Projectile();
                this.mProjectiles.push(aProjectile);
            };
            aProjectile.ProjectileInitialize(theX, theY, aRenderOrder, theRow, projectileType, this.mApp, this,1);
            this.mRenderManager.add(new ProjectileRenderable(aProjectile, RENDER_LAYER_PROJECTILE));
            return (aProjectile);
        }
         public void  draw (Graphics2D g ){
            this.DrawBackdrop(g);
//            this.mAdvice.Draw(g);
            this.mMahJongBank.Draw(g);
            
            if (this.mGameScene != SCENE_LEVEL_INTRO)
            {
                if (this.mGameScene != SCENE_ZOMBIES_WON)
                {
                    //this.mSeedBank.Draw(g);
                    this.DrawShovel(g);
                };
                this.mRenderManager.draw(g);
            }
            else
            {
                this.mRenderManager.draw(g);
                if (this.mGameScene != SCENE_ZOMBIES_WON)
                {
                    //this.mSeedBank.Draw(g);
                    this.DrawShovel(g);
                };
            };
            
            
            this.mWaveWarning.draw(g);
            this.DrawProgressMeter(g);
            this.DrawLevelLabel(g);
            this.mCursorPreview.draw(g);
            this.mCursorObject.draw(g);
            this.mPengGangHu.draw(g);
            
            this.mToolTip.Draw(g);
            this.DrawFadeOut(g);
        }

        private Array grid ;

        private void  spawnConeheadZombieCheat (){
            this.AddZombieInRow(ZOMBIE_TRAFFIC_CONE, (int)((Math.random() * 5)), 1);
        }

        public int mGravesCleared ;

        public boolean  CanTakeSunMoney (int theAmount ){
            if (theAmount <= (this.mSunMoney + this.CountSunBeingCollected()))
            {
                return (true);
            };
            return (false);
        }
        public void  DisplayAdviceAgain (String theAdvice ,int theMessageStyle ,int theHelpIndex ){
            if (( theHelpIndex != ADVICE_NONE && ((Boolean)this.mHelpDisplayed.elementAt(theHelpIndex)).booleanValue() ))
            {
            	this.mHelpDisplayed.add(theHelpIndex,false);
            };
            this.DisplayAdvice(theAdvice, theMessageStyle, theHelpIndex);
        }

        public int mMainCounter ;

        public void  SetZombieDefinitions (){
        }
        public GridItem  AddAGraveStone (int theGridX ,int theGridY ){
            GridItem aGridItem =new GridItem(this.mApp ,this );
            aGridItem.mGridItemType = GRIDITEM_GRAVESTONE;
            aGridItem.mGridItemCounter = (int)(Math.random() * -50);
            aGridItem.mGridX = theGridX;
            aGridItem.mGridY = theGridY;
            this.mRenderManager.add(new GridItemRenderable(aGridItem, RENDER_LAYER_GRAVE_STONE));
            this.mGridItems.push(aGridItem);
            return (aGridItem);
        }

        public int mSunMoney ;

        public GridItem  GetGridItemAt (int theGridItemType ,int theGridX ,int theGridY ){
            GridItem aGridItem ;
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (((!((aGridItem.mGridX == theGridX))) || (!((aGridItem.mGridY == theGridY)))))
                {
                }
                else
                {
                    if (aGridItem.mGridItemType == theGridItemType)
                    {
                        return (aGridItem);
                    };
                };
            };
            return (null);
        }

        public int mCurrentWave ;
        public Array mZombieWaves ;

        public boolean  ProgressMeterHasFlags (){
            if (this.mLevel == 1)
            {
                return (false);
            };
            return (true);
        }

        public Dictionary mGridCelOffset ;
        private Object activeGrid =null ;

        public boolean  RowCanHaveZombieType (int theRow ,int theZombieType ){
            if (!this.RowCanHaveZombies(theRow))
            {
                return (false);
            };
            int aWaveNumber =this.mCurrentWave ;
            return (true);
        }
        public void  UpdateTutorial (){
            int aPosX ;
            int aPosY ;
            if (this.mTutorialTimer > 0)
            {
                this.mTutorialTimer--;
            };
            if (this.mTutorialState == TUTORIAL_LEVEL_1_PICK_UP_PEASHOOTER)
            {
                if (this.mTutorialTimer == 0)
                {
                    this.DisplayAdvice("[ADVICE_CLICK_PEASHOOTER]", MESSAGE_STYLE_TUTORIAL_LEVEL1_STAY, ADVICE_NONE);
                    aPosX=((this.mSeedBank.mX+ ((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mX)+(((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mWidth/3));
                    aPosY=((this.mSeedBank.mY+ ((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mY)+((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(0)).mHeight/2);
                    this.TutorialArrowShow(aPosX, aPosY);
                    this.mTutorialTimer = -1;
                };
            };
            if ((((((this.mTutorialState == TUTORIAL_LEVEL_2_PICK_UP_SUNFLOWER)) || ((this.mTutorialState == TUTORIAL_LEVEL_2_PLANT_SUNFLOWER)))) || ((this.mTutorialState == TUTORIAL_LEVEL_2_REFRESH_SUNFLOWER))))
            {
                if (this.mTutorialTimer == 0)
                {
                    this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER2]", MESSAGE_STYLE_TUTORIAL_LEVEL2, ADVICE_NONE);
                    this.mTutorialTimer = -1;
                }
                else
                {
                    if ((((this.mZombieCountDown == 750)) && ((this.mCurrentWave == 0))))
                    {
                        this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER3]", MESSAGE_STYLE_TUTORIAL_LEVEL2, ADVICE_NONE);
                    };
                };
            };
            if ((((((this.mTutorialState == TUTORIAL_MORESUN_PICK_UP_SUNFLOWER)) || ((this.mTutorialState == TUTORIAL_MORESUN_PLANT_SUNFLOWER)))) || ((this.mTutorialState == TUTORIAL_MORESUN_REFRESH_SUNFLOWER))))
            {
                if (this.mTutorialTimer == 0)
                {
                    this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER5]", MESSAGE_STYLE_TUTORIAL_LATER, ADVICE_PLANT_SUNFLOWER5);
                    this.mTutorialTimer = -1;
                };
            };
            if (this.mLevel >= 3 && this.mLevel != 5 && this.mLevel <= 7 && this.mTutorialState == TUTORIAL_OFF && this.mCurrentWave >= 5 && !this.mShownMoreSunTutorial && ((SeedPacket)this.mSeedBank.mSeedPackets.elementAt(1)).CanPickUp() && this.CountPlantByType(SEED_SUNFLOWER) < 3)
            {
                this.DisplayAdvice("[ADVICE_PLANT_SUNFLOWER4]", MESSAGE_STYLE_TUTORIAL_LATER_STAY, ADVICE_NONE);
                this.mShownMoreSunTutorial = true;
                this.SetTutorialState(TUTORIAL_MORESUN_PICK_UP_SUNFLOWER);
                this.mTutorialTimer = 500;
            };
        }

        public boolean mLevelComplete ;
        private ImageInst sod1Row ;

        public boolean  IsPlantInCursor (){
            if (this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_BANK || this.mCursorObject.mCursorType == CURSOR_TYPE_PLANT_FROM_USABLE_COIN)
            {
                return (true);
            };
            return (false);
        }

        private ImageInst FlagMeterBar ;

        public CPlant  GetPlantsOnLawn (int theGridX ,int theGridY ){
            CPlant aPlant ;
            if (((this.IsWallnutBowlingLevel()) && (!(this.IsInShovelTutorial()))))
            {
                return (null);
            };
            if (theGridX < 0 || theGridX >= GRIDSIZEX || theGridY < 0 || theGridY >= GRIDSIZEY)
            {
                return (null);
            };
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                if (aPlant.getCol() == theGridX && aPlant.getRow() == theGridY)
                {
                    return aPlant;
                };
            };
            return (null);
        }
        public int  GetGraveStoneCount (){
            GridItem aGridItem ;
            int aCount =0;
			for(int i =0; i<this.mGridItems.length();i++)
			{
				aGridItem = (GridItem)this.mGridItems.elementAt(i);
                if (aGridItem.mGridItemType == GRIDITEM_GRAVESTONE)
                {
                    aCount++;
                };
            };
            return (aCount);
        }
        public int  PixelToGridX (int theX ,int theY ){
            if (theX < LAWN_XMIN)
            {
                return (-1);
            };
//            return (TodCommon.ClampInt(((theX - LAWN_XMIN) / (54*3)), 0, (GRIDSIZEX - 1)));
          return (TodCommon.ClampInt(((theX - LAWN_XMIN) / (this.mApp.unitWidth * 2) ), 0, (GRIDSIZEX - 1)));
        }
        public void  MouseDownWithTool (int x ,int y ,int theCursorType ){
            CPlant aPlant =this.ToolHitTest(x ,y );
            if (aPlant==null)
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.DROP);
                this.ClearCursor();
                return;
            };
            if (theCursorType == CURSOR_TYPE_SHOVEL)
            {
                this.mApp.foleyManager().playFoley(PVZFoleyType.USE_SHOVEL);
                aPlant.Die();
                if ((((this.mTutorialState == TUTORIAL_SHOVEL_DIG)) || ((this.mTutorialState == TUTORIAL_SHOVEL_KEEP_DIGGING))))
                {
                    if (this.CountPlantByType(SEED_PEASHOOTER) == 1)
                    {
                        this.SetTutorialState(TUTORIAL_SHOVEL_COMPLETED);
                    }
                    else
                    {
                        this.SetTutorialState(TUTORIAL_SHOVEL_KEEP_DIGGING);
                    };
                };
            };
            this.ClearCursor();
        }
        public ZombieDef  GetZombieDefinition (int aZombieType ){
            ZombieDef aZombieDef =new ZombieDef ();
            switch (aZombieType)
            {
                case ZOMBIE_NORMAL:
                    aZombieDef.mZombieValue = 1;
                    aZombieDef.mStartingLevel = 1;
                    aZombieDef.mFirstAllowedWave = 1;
                    aZombieDef.mPickWeight = 4000;
                    aZombieDef.mZombieName = "ZOMBIE";
                    break;
                case ZOMBIE_FLAG:
                    aZombieDef.mZombieValue = 1;
                    aZombieDef.mStartingLevel = 1;
                    aZombieDef.mFirstAllowedWave = 1;
                    aZombieDef.mPickWeight = 0;
                    aZombieDef.mZombieName = "FLAG_ZOMBIE";
                    break;
                case ZOMBIE_TRAFFIC_CONE:
                    aZombieDef.mZombieValue = 2;
                    aZombieDef.mStartingLevel = 3;
                    aZombieDef.mFirstAllowedWave = 1;
                    aZombieDef.mPickWeight = 4000;
                    aZombieDef.mZombieName = "CONEHEAD_ZOMBIE";
                    break;
                case ZOMBIE_POLEVAULTER:
                    aZombieDef.mZombieValue = 2;
                    aZombieDef.mStartingLevel = 6;
                    aZombieDef.mFirstAllowedWave = 5;
                    aZombieDef.mPickWeight = 2000;
                    aZombieDef.mZombieName = "POLE_VAULTING_ZOMBIE";
                    break;
                case ZOMBIE_PAIL:
                    aZombieDef.mZombieValue = 4;
                    aZombieDef.mStartingLevel = 8;
                    aZombieDef.mFirstAllowedWave = 1;
                    aZombieDef.mPickWeight = 3000;
                    aZombieDef.mZombieName = "BUCKETHEAD_ZOMBIE";
                    break;
                case ZOMBIE_NEWSPAPER:
                    aZombieDef.mZombieValue = 2;
                    aZombieDef.mStartingLevel = 11;
                    aZombieDef.mFirstAllowedWave = 1;
                    aZombieDef.mPickWeight = 1000;
                    aZombieDef.mZombieName = "NEWSPAPER_ZOMBIE";
                    break;
                case ZOMBIE_DOOR:
                    aZombieDef.mZombieValue = 4;
                    aZombieDef.mStartingLevel = 13;
                    aZombieDef.mFirstAllowedWave = 5;
                    aZombieDef.mPickWeight = 3500;
                    aZombieDef.mZombieName = "SCREEN_DOOR_ZOMBIE";
                    break;
                case ZOMBIE_FOOTBALL:
                    aZombieDef.mZombieValue = 7;
                    aZombieDef.mStartingLevel = 16;
                    aZombieDef.mFirstAllowedWave = 5;
                    aZombieDef.mPickWeight = 2000;
                    aZombieDef.mZombieName = "FOOTBALL_ZOMBIE";
                    break;
            };
            return (aZombieDef);
        }
        public LawnMower  FindLawnMowerInRow (int theRow ){
            LawnMower aLawnMower ;
			for(int i =0; i<this.mLawnMowers.length();i++)
			{
				aLawnMower = (LawnMower)this.mLawnMowers.elementAt(i);
                if (aLawnMower.mRow == theRow)
                {
                    return (aLawnMower);
                };
            };
            return (null);
        }

        public int mHugeWaveCountDown ;
        public int mBackground ;
        public Array mRowPickingArray ;

        public int  CountSunBeingCollected (){
            Coin aCoin ;
            int anAmount =0;
			for(int i =0; i<this.mCoins.length();i++)
			{
				aCoin = (Coin)this.mCoins.elementAt(i);
                if (aCoin.mIsBeingCollected && aCoin.IsSun())
                {
                    anAmount = (anAmount + aCoin.GetSunValue());
                };
            };
            return (anAmount);
        }

        public boolean mShowZombieWalking ;
        public Array mZombieTraffic ;
        public Array mZombieScreenDoor ;

        public void  TutorialArrowRemove (){
            if (this.mTutorialArrow != null)
            {
                this.mTutorialArrow.die();
            };
            this.mTutorialArrow = null;
        }
        public void  FadeOutLevel (){
            Coin aCoin ;
            String aMessage ;
            boolean aIsNote ;
            this.mMenuButton.visible = false;
            if (this.mGameScene != SCENE_PLAYING)
            {
                this.RefreshSeedPacketFromCursor();
                this.mLevelComplete = true;
                return;
            };
            if (this.mLevel == 14)
            {
/*
                this.mApp.mSaveObject.level = 1;
                this.mApp.mLevel = 1;
                if (this.mApp.mSaveObject.puzzleLocked)
                {
                    this.mApp.mSaveObject.puzzleLocked = false;
                    this.mApp.mPuzzleLocked = false;
                };
                if (this.mApp.mSaveObject.survivalLocked)
                {
                    this.mApp.mSaveObject.survivalLocked = false;
                    this.mApp.mSurvivalLocked = false;
                };
*/
                this.mApp.setSaveData(this.mApp.mSaveObject);
            };
            boolean aCutMusic =true ;
            if (((this.mApp.IsSurvivalMode()) || (this.mApp.IsScaryPotterLevel())))
            {
                aCutMusic = false;
            };
            if (aCutMusic)
            {
                this.mApp.musicManager().playMusic(PVZMusic.WINMUSIC, false,1);
            };
            if (this.mLevel == 14)
            {
                this.mBoardFadeOutCounter = 600;
            }
            else
            {
                if (this.mApp.IsScaryPotterLevel())
                {
                    this.mLevelAwardSpawned = true;
                    this.mNextSurvivalStageCounter = 500;
                    for(int i =0; i<this.mCoins.length();i++)
                    {
						aCoin = (Coin)this.mCoins.elementAt(i);
                        if (aCoin.mType == Coin.COIN_USABLE_SEED_PACKET)
                        {
                            aCoin.Die();
                        }
                        else
                        {
                            aCoin.TryAutoCollectAfterLevelAward();
                        };
                    };
                    aMessage = this.mApp.stringManager().translateString("[ADVICE_MORE_SCARY_POTS]");
                    this.ClearAdvice(ADVICE_NONE);
                    this.DisplayAdvice(aMessage, MESSAGE_STYLE_BIG_MIDDLE, ADVICE_NONE);
                }
                else
                {
                    if (!this.mApp.IsSurvivalMode())
                    {
                        this.RefreshSeedPacketFromCursor();
                        this.mMenuButton.visible = false;
                        this.mBoardFadeOutCounter = 600;
						for(int i =0; i<this.mCoins.length();i++)
						{
							aCoin = (Coin)this.mCoins.elementAt(i);
                            aCoin.TryAutoCollectAfterLevelAward();
                        };
                        aIsNote = (this.mLevel == 9 || this.mLevel == 19 || this.mLevel == 29 || this.mLevel == 39 || this.mLevel == 49);
                        if (aIsNote)
                        {
                            this.mBoardFadeOutCounter = 500;
                        };
                    }
                    else
                    {
                        this.mNextSurvivalStageCounter = 500;
                        this.DisplayAdvice("[ADVICE_MORE_ZOMBIES]", MESSAGE_STYLE_BIG_MIDDLE, ADVICE_NONE);
                        this.mApp.foleyManager().playFoley(PVZFoleyType.HUGEWAVE);
                    };
                };
            };
        }

        public Array mCoins ;

        public void  AddSunMoney (int theAmount ){
            this.mSunMoney = (this.mSunMoney + theAmount);
            if (this.mSunMoney > 9990)
            {
                this.mSunMoney = 9990;
            };
            Rectangle bounds =new Rectangle(0,0,40,15);
            this.mSeedBank.mSunText = Utils.createStringImage(""+this.mSunMoney, this.mSeedBank.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
        }

        private ImageInst FlagMeterFull ;

        public void  ClearAdvice (int theHelpIndex ){
            if (theHelpIndex != ADVICE_NONE && theHelpIndex != this.mHelpIndex)
            {
                return;
            };
            this.mAdvice.ClearLabel();
            this.mHelpIndex = ADVICE_NONE;
        }
        public int  PixelToGridXKeepOnBoard (int theX ,int theY ){
            return (Math.max(this.PixelToGridX(theX, theY), 0));
        }
        public Zombie  ZombieHitTest (int theMouseX ,int theMouseY ){
            Zombie aZombie =null;
            Rectangle aRect ;
            Zombie aTopZombie =null;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (aZombie.IsDeadOrDying())
                {
                }
                else
                {
                    aRect = aZombie.GetZombieRect();
                    if (!aRect.contains(theMouseX, theMouseY))
                    {
                    }
                    else
                    {
                        if (aTopZombie==null || aZombie.mY > aTopZombie.mY)
                        {
                            aTopZombie = aZombie;
                        };
                    };
                };
            };
            return (aTopZombie);
        }

        private ImageInst unsoddedBackground ;

        public void  ZombiePickerInit (ZombiePicker theZombiePicker ){
            this.ZombiePickerInitForWave(theZombiePicker);
            int i =0;
            while (i < NUM_ZOMBIE_TYPES)
            {
            	theZombiePicker.mAllWavesZombieTypeCount.put(i,0);
                i++;
            };
        }
        public int  PickRowForNewZombie (int theZombieType ){
            int aWavesSinceMowed =0;
            int i =0;
            while (i < GRIDSIZEY)
            {
                if (!this.RowCanHaveZombieType(i, theZombieType))
                {
                	((SmoothArray)this.mRowPickingArray.elementAt(i)).mWeight=0;
                }
                else
                {
                	aWavesSinceMowed=(this.mCurrentWave-((Integer)this.mWaveRowGotLawnMowered.elementAt(i)).intValue());
                    if (aWavesSinceMowed <= 1)
                    {
                    	((SmoothArray)this.mRowPickingArray.elementAt(i)).mWeight=0.01;
                    }
                    else
                    {
                        if (aWavesSinceMowed <= 2)
                        {
                        	((SmoothArray)this.mRowPickingArray.elementAt(i)).mWeight=0.5;
                        }
                        else
                        {
                        	((SmoothArray)this.mRowPickingArray.elementAt(i)).mWeight=1;
                        };
                    };
                };
                i++;
            };
            i = 0;
            while (i < GRIDSIZEY)
            {
                i++;
            };
            return (int)(TodCommon.TodPickFromSmoothArray(this.mRowPickingArray, GRIDSIZEY));
        }
        public void  UpdateCursor (){
            HitResult aHitResult ;
            SeedPacket aSeedPacket ;
            boolean aShowFinger =false;
            boolean aShowDrag ;
            boolean aHideCursor ;
            int aMouseX =(int)(this.mApp.widgetManager().lastMouseX -x );
            int aMouseY =(int)(this.mApp.widgetManager().lastMouseY -y );
            if (this.mApp.mSeedChooserScreen !=null && this.mApp.mSeedChooserScreen.contains((int)(aMouseX + x), (int)(aMouseY + y)))
            {
                return;
            };
            if (this.mPaused || this.mBoardFadeOutCounter >= 0 || this.mTimeStopCounter > 0 || this.mGameScene == SCENE_ZOMBIES_WON)
            {
                aShowFinger = false;
            }
            else
            {
                aHitResult = new HitResult();
                this.MouseHitTest(aMouseX, aMouseY, aHitResult);
                if (aHitResult.mObjectType == OBJECT_TYPE_SHOVEL || aHitResult.mObjectType == OBJECT_TYPE_COIN)
                {
                    aShowFinger = true;
                }
                else
                {
                    if (aHitResult.mObjectType == OBJECT_TYPE_SEEDPACKET)
                    {
                        aSeedPacket = (SeedPacket)aHitResult.mObject;
                        if (aSeedPacket.CanPickUp())
                        {
                            aShowFinger = true;
                        };
                    }
                    else
                    {
                        if (aHitResult.mObjectType == OBJECT_TYPE_SCARY_POT)
                        {
                            aShowFinger = true;
                        };
                    };
                };
            };

            aShowFinger = true;
            showFinger(aShowFinger);
        }
        public void  ShakeBoard (int theShakeAmountX ,int theShakeAmountY ){
            this.mShakeCounter = BOARD_SHAKE_TIME;
            this.mShakeAmountX = theShakeAmountX;
            this.mShakeAmountY = theShakeAmountY;
        }
        public String  GetStageString (int theLevel ){
            int aArea =TodCommon.ClampInt ((((theLevel -1)/LEVELS_PER_AREA )+1),1,6);
            int aSubArea =(theLevel -((aArea -1)*LEVELS_PER_AREA ));
            return (((aArea + "-") + aSubArea));
        }

        public Dictionary mGridSquareType ;
        public boolean mEasyPlantingCheat ;

        public int  GetSeedBankExtraWidth (){
            return (0);
        }
        public boolean  TakeSunMoney (int theAmount ){
            Rectangle bounds ;
            if (this.mEasyPlantingCheat)
            {
                return (true);
            };
            if (theAmount <= (this.mSunMoney + this.CountSunBeingCollected()))
            {
                this.mSunMoney = (this.mSunMoney - theAmount);
                bounds = new Rectangle(0, 0, 40, 15);
                this.mSeedBank.mSunText = Utils.createStringImage(""+this.mSunMoney, this.mSeedBank.mSeedBankFont, bounds, Utils.JUSTIFY_CENTER);
                return (true);
            };
            this.mApp.foleyManager().playFoley(PVZFoleyType.BUZZER);
            this.mOutOfMoneyCounter = 70;
            return (false);
        }

        private WaveWarning mWaveWarning ;

        public int  PickZombieType (int theZombiePoints ,int theWaveIndex ,ZombiePicker theZombiePicker ){
            int aFlags ;
            WeightedArray aWeightedArray ;
            int aZombieType ;
            ZombieDef aZombieDef ;
            int aFirstWave ;
            int aWeight ;
            int aWavesEarlier ;
            int aLimit ;
            Array aZombieWeightArray =new Array(NUM_ZOMBIE_TYPES );
            int aPickCount =0;
            int i =0;
            for (;i < NUM_ZOMBIE_TYPES;i++)
            {
                aWeightedArray = new WeightedArray();
                aZombieWeightArray.add(aPickCount,aWeightedArray);
                aZombieType = i;
                aZombieDef = this.GetZombieDefinition(aZombieType);
                if (((Boolean)this.mZombieAllowed.elementAt(aZombieType)).booleanValue())
                {
                    aFirstWave = aZombieDef.mFirstAllowedWave;
                    if (this.mApp.IsSurvivalEndless())
                    {
                        aFlags = this.GetSurvivalFlagsCompleted();
                        aWavesEarlier = TodCommon.TodAnimateCurve(18, 50, aFlags, 0, 15, TodCommon.CURVE_LINEAR);
                        aFirstWave = Math.max((aFirstWave - aWavesEarlier), 1);
                    };
                    if ((theWaveIndex + 1) < aFirstWave)
                    {
                    }
                    else
                    {
                        if (theZombiePoints >= aZombieDef.mZombieValue)
                        {
                            aWeight = aZombieDef.mPickWeight;
                            if (this.mApp.IsSurvivalMode())
                            {
                                aFlags = this.GetSurvivalFlagsCompleted();
                                if (aZombieType == 8)
                                {
                                    aLimit = TodCommon.TodAnimateCurve(10, 50, aFlags, 2, 50, TodCommon.CURVE_LINEAR);
                                    if(((Integer)theZombiePicker.mZombieTypeCount.elementAt(aZombieType)).intValue()>=aLimit)
                                    {
                                        continue;
                                    };
                                };
                                if (aZombieType == 8)
                                {
                                    if (this.IsFlagWave(theWaveIndex))
                                    {
                                        aLimit = TodCommon.TodAnimateCurve(14, 100, aFlags, 1, 50, TodCommon.CURVE_LINEAR);
                                        if(((Integer)theZombiePicker.mZombieTypeCount.elementAt(aZombieType)).intValue()>=aLimit)
                                        {
                                            continue;
                                        };
                                    }
                                    else
                                    {
                                        aLimit = TodCommon.TodAnimateCurve(10, 110, aFlags, 1, 50, TodCommon.CURVE_LINEAR);
                                        if(((Integer)theZombiePicker.mAllWavesZombieTypeCount.elementAt(aZombieType)).intValue()>=aLimit)
                                        {
                                            continue;
                                        };
                                        aWeight = 1000;
                                    };
                                };
                                if (aZombieType == ZOMBIE_NORMAL)
                                {
                                    aWeight = TodCommon.TodAnimateCurve(10, 50, aFlags, aZombieDef.mPickWeight, (aZombieDef.mPickWeight / 10), TodCommon.CURVE_LINEAR);
                                };
                                if (aZombieType == ZOMBIE_TRAFFIC_CONE)
                                {
                                    aWeight = TodCommon.TodAnimateCurve(10, 50, aFlags, aZombieDef.mPickWeight, (aZombieDef.mPickWeight / 4), TodCommon.CURVE_LINEAR);
                                };
                            };
                            ((WeightedArray)aZombieWeightArray.elementAt(aPickCount)).mItem=i;
                            ((WeightedArray)aZombieWeightArray.elementAt(aPickCount)).mWeight=aWeight;
                            aPickCount++;
                        };
                    };
                };
            };
            return (TodCommon.TodPickFromWeightedArray(aZombieWeightArray, aPickCount));
        }

        public Array mZombieNormal ;
        public FontInst mAdviceFont ;
        public Array mZombiesInWave ;

        public void  KillAllPlantsInRadius (int theX ,int theY ,int theRadius ){
            CPlant aPlant ;
            Rectangle aPlantRect ;
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                aPlantRect = aPlant.GetPlantRect();
                if (this.GetCircleRectOverlap(theX, theY, theRadius, aPlantRect))
                {
                    aPlant.Die();
                };
            };
        }

        public MessageWidget mAdvice ;
        public Rectangle mShovelButtonRect ;

         public void  onMouseDown (int x0 ,int y0 ){
            Coin aCoin ;
            SeedPacket aSeedPacket ;
            //x = x/2;
            //y = y/2;
            if (this.mPaused)
            {
                return;
            };
            HitResult aHitResult =new HitResult ();
            this.MouseHitTest(x0, y0, aHitResult);
            
            if(aHitResult.mObjectType == this.OBJECT_TYPE_PENGGANGHU) {
            	mPengGangHu.onMouseDown(x0, y0);
            	return;
            }
            this.mHoldObject = null;
            System.out.println("Board.onMouseDown.1  "+aHitResult.mObjectType);
            if (this.mChallenge.MouseDown(x0, y0, aHitResult))
            {
                System.out.println("onMouseDown.chanllenge."+aHitResult.mObjectType);
                return;
            };
            if (this.mGameScene == SCENE_ZOMBIES_WON)
            {
                return;
            };
                System.out.println("onMouseDown._."+aHitResult.mObjectType+":"+this.mCursorObject.mCursorType);
            if (aHitResult.mObjectType == OBJECT_TYPE_COIN)
            {
                aCoin = (Coin)aHitResult.mObject;
                aCoin.MouseDown(x0, y0);
            }
            else
            {
                if (this.mCursorObject.mCursorType == CURSOR_TYPE_SHOVEL)
                {
                    this.MouseDownWithTool(x0, y0, this.mCursorObject.mCursorType);
                }
                else
                {
                    if (this.IsPlantInCursor())
                    {
                        this.MouseDownWithPlant(x0, y0);
                    }
                    else
                    {
                        if (aHitResult.mObjectType == OBJECT_TYPE_SEEDPACKET)
                        {
                            aSeedPacket = (SeedPacket)aHitResult.mObject;
                            aSeedPacket.MouseDown(x0, y0);
                        }
                        else
                        {
                            if (aHitResult.mObjectType == OBJECT_TYPE_SHOVEL)
                            {
                                this.PickUpTool(aHitResult.mObjectType);
                            };
                        };
                    };
                };

            };
            this.UpdateCursor();
            mPengGangHu.update();
        }

        private ImageInst doorMask;
        public RenderManager mRenderManager;
        public ImageInst mShovelImage;
        private int mSpecialGraveStoneX;
        private int mSpecialGraveStoneY;

        public void  ContinueVasebreaker (){
            if (this.mApp.adAPI.enabled())
            {
                this.mApp.musicManager().resumeMusic();
                this.mApp.soundManager().resumeAll();
            };
            this.mChallenge.PuzzleNextStageClear();
            this.mChallenge.ScaryPotterPopulate();
            this.mMenuButton.visible = true;
        }

        public int mGameScene ;
        public ImageButtonWidget mMenuButton ;
        public int mTotalSpawnedWaves ;
        private int plantTime =0;

        public void  AddGraveStones (int theGridX ,int theCount ){
            int aGridY ;
            int i =0;
            //this.AddPlant(theGridX, (int)((Math.random() * GRIDSIZEY)), CPlant.SEED_SUNFLOWER);
            
            while (i < theCount)
            {
                aGridY = (int)((Math.random() * GRIDSIZEY));
                if (!this.CanAddGraveStoneAt(theGridX, aGridY))
                {
                }
                else
                {
                    if(this.mPlants.length()<=2)
                    	this.AddPlant(theGridX, (int)((Math.random() * GRIDSIZEY)), CPlant.SEED_SUNFLOWER);
                    else
                    	this.AddAGraveStone(theGridX, aGridY);
                };
                i++;
            };
            
        }

        public SeedBank mSeedBank ;
        public MahJongBank mMahJongBank;

        public int  GetIntroducedZombieType (){
            int aZombieType ;
            ZombieDef aZombieDef ;
            if (!this.mApp.IsAdventureMode() || this.mLevel == 1)
            {
                return (ZOMBIE_INVALID);
            };
            int i =0;
            while (i < NUM_ZOMBIE_TYPES)
            {
                aZombieType = i;
                aZombieDef = this.GetZombieDefinition(aZombieType);
                if (aZombieDef.mStartingLevel == this.mLevel)
                {
                    return (aZombieType);
                };
                i++;
            };
            return (ZOMBIE_INVALID);
        }

        public Array mWaveRowGotLawnMowered ;

        public void  DrawBackdrop (Graphics2D g ){
            int aWidth ;
            Rectangle aSrcRect ;
            ImageInst aImage ;
            ImageInst flashSod ;
            Color aFlashingColor ;
//            g.scale(1, 1);
            if (this.mApp.IsAdventureMode())
            {
                if (this.mLevel == 1)
                {
                    g.blitImage(this.unsoddedBackground, -(PVZApp.BOARD_OFFSET), 0);
                    aWidth = TodCommon.TodAnimateCurve(0, 1000, this.mSodPosition, 0, this.sod1Row.width(), TodCommon.CURVE_LINEAR);
                    aSrcRect = new Rectangle(0, 0, aWidth, this.sod1Row.height());
                    g.pushState();
                    g.setClipRect((-(PVZApp.BOARD_OFFSET) + 161.5), 197, aWidth, this.sod1Row.height(),true);
                    g.drawImage(this.sod1Row, (int)(-(PVZApp.BOARD_OFFSET) + 161.5), 197);
                    g.popState();
                }
                else
                {
                    if ((((this.mLevel == 2)) || ((this.mLevel == 3))))
                    {
                        g.blitImage(this.unsoddedBackground, -(PVZApp.BOARD_OFFSET), 0);
                        g.drawImage(this.sod1Row, (int)(-(PVZApp.BOARD_OFFSET) + 161.5), 197);
                        aWidth = TodCommon.TodAnimateCurve(0, 1000, this.mSodPosition, 0, this.sod3Row.width(), TodCommon.CURVE_LINEAR);
                        g.pushState();
                        if (this.mLevel == 2)
                        {
                            g.setClipRect((-(PVZApp.BOARD_OFFSET) + 158.7), 118.5, aWidth, this.sod3Row.height(),true);
                        };
                        g.drawImage(this.sod3Row, (int)(-(PVZApp.BOARD_OFFSET) + 158.5), (int)118.5);
                        g.popState();
                    }
                    else
                    {
                        if (this.mLevel == 4)
                        {
                            g.drawImage(this.unsoddedBackground, -(PVZApp.BOARD_OFFSET), 0);
                            g.drawImage(this.sod3Row, (int)(-(PVZApp.BOARD_OFFSET) + 158.7), (int)118.5);
                            aWidth = TodCommon.TodAnimateCurve(0, 1000, this.mSodPosition, 0, this.sod3Row.width(), TodCommon.CURVE_LINEAR);
                            g.pushState();
                            aSrcRect = new Rectangle(156, 0, aWidth, this.background.height());
                            g.setClipRect((-(PVZApp.BOARD_OFFSET) + 158.7), 0, aWidth, this.background.height(),true);
                            g.drawImage(this.background, -(PVZApp.BOARD_OFFSET), 0);
                            g.popState();
                        }
                        else
                        {
                            g.blitImage(this.background, -(PVZApp.BOARD_OFFSET), 0);
                        };
                    };
                };
            }
            else
            {
            	g.blitImage(this.background, -(PVZApp.BOARD_OFFSET), 0);
            };
            
            if (this.mGameScene == SCENE_ZOMBIES_WON)
            {
                g.blitImage(this.doorOverlay, -85, 168);
            };
            if (this.IsWallnutBowlingLevel())
            {
                if (this.mChallenge.mShowBowlingLine)
                {
                    aImage = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_WALLNUT_BOWLINGSTRIPE);
                    g.drawImage(aImage, 179, 65);
                };
            };
            if (this.mTutorialState == TUTORIAL_LEVEL_1_PLANT_PEASHOOTER)
            {
                flashSod = this.mApp.imageManager().getImageInst(PVZImages.IMAGE_SOD1ROW);
                g.pushState();
                aFlashingColor = TodCommon.GetFlashingColor(this.mMainCounter, 75);
                flashSod.useColor = true;
                flashSod.setColor(aFlashingColor.alpha, aFlashingColor.red, aFlashingColor.blue, aFlashingColor.green);
                g.drawImage(flashSod, (int)(-(PVZApp.BOARD_OFFSET) + 161.5), 197);
                g.popState();
            };
        }
        public boolean  MouseHitTestPlant (int x ,int y ,HitResult theHitResult ){
            int aGridX =this.PixelToGridX(x ,y );
            int aGridY =this.PixelToGridY(x ,y );
            CPlant aPlant =this.GetPlantsOnLawn(aGridX ,aGridY );
            if (aPlant!=null)
            {
                theHitResult.mObject = aPlant;
                theHitResult.mObjectType = OBJECT_TYPE_PLANT;
                return (true);
            };
            return (false);
        }

        public ParticleSystem mTutorialArrow ;

        public void  InitLawnMowers (){
            boolean aHasMower ;
            LawnMower aLawnMower ;
            int y =0;
            while (y < GRIDSIZEY)
            {
                if (this.mApp.IsScaryPotterLevel())
                {
                    aHasMower = false;
                }
                else
                {
                	if(((Integer)this.mPlantRow.elementAt(y)).intValue()==PLANTROW_DIRT)
                    {
                        aHasMower = false;
                    }
                    else
                    {
                        aHasMower = true;
                    };
                };
                if (aHasMower)
                {
                    aLawnMower = new LawnMower(this.mApp, this, y);
                    aLawnMower.mVisible = false;
                    this.mRenderManager.add(new LawnMowerRenderable(aLawnMower, ((RENDER_LAYER_ZOMBIE + y) + 1)));
                    this.mLawnMowers.push(aLawnMower);
                };
                y++;
            };
        }

        public Array mZombies ;

        public int  CountSunFlowers (){
            CPlant aPlant ;
            int aCount =0;
			for(int i =0; i<this.mPlants.length();i++)
			{
				aPlant = (CPlant)this.mPlants.elementAt(i);
                if (aPlant.mSeedType != SEED_SUNFLOWER)
                {
                }
                else
                {
                    aCount++;
                };
            };
            return (aCount);
        }
        public void  buttonMouseLeave (int id ){
        }
        public CPlant  ToolHitTest (int x ,int y ){
            HitResult aHitResult =new HitResult ();
            this.MouseHitTest(x, y, aHitResult);
            if (aHitResult.mObjectType != OBJECT_TYPE_PLANT)
            {
                return (null);
            };
            CPlant aPlant =(CPlant)aHitResult.mObject;
            return (aPlant);
        }
        public boolean  IsFlagWave (int theWaveNumber ){
            if (((this.mApp.IsAdventureMode()) && ((this.mLevel == 1))))
            {
                return (false);
            };
            int aNumWavesPerFlag =this.GetNumWavesPerFlag ();
            return (((theWaveNumber % aNumWavesPerFlag) == (aNumWavesPerFlag - 1)));
        }

        private ImageInst FlagMeterEmpty ;
        public boolean mShownMoreSunTutorial =false ;
        public final int Menu_button =100;
        
        public GameObject mHoldObject = null;

        public String  GetSeedName (int theType ){
            String aName ="";
            switch (theType)
            {
                case 0:
                    aName = "[PEASHOOTER]";
                    break;
                case 1:
                    aName = "[SUNFLOWER]";
                    break;
                case 2:
                    aName = "[CHERRY_BOMB]";
                    break;
                case 3:
                    aName = "[WALL_NUT]";
                    break;
                case 4:
                    aName = "[SQUASH]";
                    break;
                case 5:
                    aName = "[SNOW_PEA]";
                    break;
                case 6:
                    aName = "[CHOMPER]";
                    break;
                case 7:
                    aName = "[REPEATER]";
                    break;
                case 8:
                    aName = "[PUFF_SHROOM]";
                    break;
                case 9:
                    aName = "[SUN_SHROOM]";
                    break;
                case 10:
                    aName = "[FUME_SHROOM]";
                    break;
                case 11:
                    aName = "[GRAVE_BUSTER]";
                    break;
                case 50:
                    aName = "[EXPLODE_O_NUT]";
                    break;
            };
            return (aName);
        }

        public boolean mPaused ;
        public int mRiseFromGraveCounter ;

        public void  RemoveAllZombies (){
            Zombie aZombie ;
			for(int i =0; i<this.mZombies.length();i++)
			{
				aZombie = (Zombie)this.mZombies.elementAt(i);
                if (!aZombie.IsDeadOrDying())
                {
                    aZombie.DieNoLoot();
                };
            };
        }

        public  Board (PVZApp app ){
            super();
            app.mBoard = this;
            this.seeds = new Array();
            this.grid = new Array();
            this.mPlants = new Array();
            this.mPlantRow = new Array();
            this.mGridSquareType = new Dictionary();
            this.mProjectiles = new Array();
            this.mCoins = new Array();
            this.mLawnMowers = new Array();
            this.mZombies = new Array();
            int[] values0 = {4, 6, 8, 10, 8, 10, 20, 10, 20, 20, 10, 20, 10, 20, 10, 10, 20, 10, 20, 20};
            this.mZombieWaves = new Array(values0);
            int[] values1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            this.mZombieNormal = new Array(values1);
            int[] values2 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            this.mZombieFlag = new Array(values2);
            int[] values3 = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1};
            this.mZombieTraffic = new Array(values3);
            int[] values4 = {0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1};
            this.mZombiePolevaulter = new Array(values4);
            int[] values5 = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0};
            this.mZombiePail = new Array(values5);
            int[] values6 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            this.mZombieGoldenPail = new Array(values6);
            int[] values7 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0};
            this.mZombieNewsPaper = new Array(values7);
            int[] values8 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1};
            this.mZombieScreenDoor = new Array(values8);
            Array[] values9 = {this.mZombieNormal, this.mZombieFlag, this.mZombieTraffic, this.mZombiePolevaulter, this.mZombiePail, this.mZombieNewsPaper, this.mZombieScreenDoor};
            this.mZombieAllowedLevels = new Array(values9);
            this.mZombieAllowed = new Dictionary();
            this.mMaxZombiesInWave = new Array(MAX_ZOMBIES_IN_WAVE);
            this.mZombiesInWave = new Array(MAX_ZOMBIE_WAVES);
            this.mRowPickingArray = new Array(GRIDSIZEY);
            this.mGridItems = new Array();
            this.mGridCelLook = new Dictionary();
            this.mGridCelOffset = new Dictionary();
            this.mWaveRowGotLawnMowered = new Array(GRIDSIZEY);
            this.mShovelButtonRect = new Rectangle(402, 0, 63, 65);
            this.mHelpDisplayed = new Array();
            this.mRenderManager = new RenderManager();
/*
            app.registerCheat("refreshSeedsCheat", this.refreshSeedsCheat);
            app.registerCheat("spawnAwardCheat", this.spawnAwardCheat);
            app.registerCheat("freePlantingCheat", this.freePlantingCheat);
            app.registerCheat("previousLevelCheat", this.previousLevelCheat);
            app.registerCheat("nextLevelCheat", this.nextLevelCheat);
            app.registerCheat("spawnWaveCheat", this.spawnWaveCheat);
            app.registerCheat("spawnNormalZombieCheat", this.spawnNormalZombieCheat);
            app.registerCheat("spawnScreendoorZombieCheat", this.spawnScreendoorZombieCheat);
            app.registerCheat("spawnNewspaperZombieCheat", this.spawnNewspaperZombieCheat);
            app.registerCheat("spawnFootballZombieCheat", this.spawnFootballZombieCheat);
            app.registerCheat("spawnBucketZombieCheat", this.spawnBucketZombieCheat);
            app.registerCheat("spawnConeheadZombieCheat", this.spawnConeheadZombieCheat);
            app.registerCheat("spawnPolevaultZombieCheat", this.spawnPolevaultZombieCheat);
            app.registerCheat("spawnGraveZombieCheat", this.spawnGraveZombieCheat);
*/
            x = 0;
            y = 0;
            width = app.screenWidth();//this.BOARD_WIDTH;
            height = app.screenHeight();//this.BOARD_HEIGHT;
            this.mApp = app;
            this.mCursorObject = new CursorObject(app, this.mApp.mBoard);
            
            this.mPengGangHu = new PengGangHu(app, this.mApp.mBoard);
            
            this.mCursorPreview = new CursorPreview(app, this.mApp.mBoard);
            this.mSpecialGraveStoneX = -1;
            this.mSpecialGraveStoneY = -1;
            this.mEnableGraveStones = false;
            this.mGravesCleared = 0;
            this.mRiseFromGraveCounter = 0;
            this.mEasyPlantingCheat = false;
            this.mNextSurvivalStageCounter = 0;
            this.mToolTip = new ToolTipWidget(this.mApp, this.mApp.mBoard);
            this.mTimeStopCounter = 0;
            this.mLevel = this.mApp.mLevel;
            if (this.mLevel > 10 || this.mApp.IsSurvivalMode() || this.mApp.IsScaryPotterLevel())
            {
                this.background = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND2);
                
                this.background.createScaleImage(2900, 1100);
//                this.background=Bitmap.createScaledBitmap(this.background, 25,25, true);
                
                this.doorOverlay = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND2_GAMEOVER_INTERIOR_OVERLAY);
                this.doorMask = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND2_GAMEOVER_MASK);
            }
            else
            {
                this.background = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND1);
                this.background.createScaleImage(2900, 1100);
                this.doorOverlay = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND1_GAMEOVER_INTERIOR_OVERLAY);
                this.doorMask = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND1_GAMEOVER_MASK);
            };
            this.unsoddedBackground = app.imageManager().getImageInst(PVZImages.IMAGE_BACKGROUND1UNSODDED);
            this.sod1Row = app.imageManager().getImageInst(PVZImages.IMAGE_SOD1ROW);
            this.sod3Row = app.imageManager().getImageInst(PVZImages.IMAGE_SOD3ROW);
            this.FlagMeterBar = app.imageManager().getImageInst(PVZImages.IMAGE_FLAGMETERBAR);
            this.FlagMeterFlag = app.imageManager().getImageInst(PVZImages.IMAGE_FLAGMETERBAR);
            this.FlagMeterHead = app.imageManager().getImageInst(PVZImages.IMAGE_FLAGMETERBAR);
            this.FlagMeterFull = app.imageManager().getImageInst(PVZImages.IMAGE_FLAGMETERBAR);
            this.FlagMeterFull.setFrame(1, 1, 2);
            this.FlagMeterEmpty = app.imageManager().getImageInst(PVZImages.IMAGE_FLAGMETERBAR);
            this.FlagMeterEmpty.setFrame(0, 1, 2);
            this.mAdvice = null;
            this.mAdviceFont = app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR28);
            this.mLevelFont = app.fontManager().getFontInst(PVZFonts.FONT_HOUSEOFTERROR16);
            this.mAdvice = new MessageWidget(app, this);
            this.mSeedBank = new SeedBank(app, this);
            mMahJongBank = new MahJongBank(app, this);
            
            this.mLevelAwardSpawned = false;
            this.mLevelComplete = false;
            this.mBoardFadeOutCounter = -1;
            this.mFlagRaiseCounter = 0;
            this.mTriggeredLawnMowers = 0;
            this.mShowShovel = false;
            this.mShowZombieWalking = false;
            this.mTutorialState = TUTORIAL_OFF;
            this.mTutorialTimer = -1;
            this.mShovelImage = app.imageManager().getImageInst(PVZImages.IMAGE_SHOVEL_SMALL);
            this.mShovelBankImage = app.imageManager().getImageInst(PVZImages.IMAGE_SHOVELBANK);
            this.mChallenge = new Challenge(this.mApp, this);
            this.MakeMenuButton();
            this.mWaveWarning = new WaveWarning(app);        }
    }


