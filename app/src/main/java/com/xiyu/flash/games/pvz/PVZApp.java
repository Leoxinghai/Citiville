package com.xiyu.flash.games.pvz;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
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
import android.util.DisplayMetrics;

import com.pgh.mahjong.resource.MahJongImages;
import com.thelikes.thegot2run.R;
import com.thelikes.thegot2run.SGZView;
import com.thelikes.thegot2run.gameloop;
import com.xiyu.util.*;
import com.xiyu.flash.framework.AppBase;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.logic.ChallengeScreen;
import com.xiyu.flash.framework.resources.foley.FoleyManager;
//import flash.display.Loader;
import com.xiyu.flash.framework.resources.particles.ParticleManager;
import com.xiyu.flash.framework.resources.foley.FoleyType;
import com.xiyu.flash.framework.resources.reanimator.Reanimator;
//import flash.utils.describeType;
import com.xiyu.flash.games.pvz.logic.SeedChooserScreen;
import com.xiyu.flash.games.pvz.logic.UI.UpsellScreen;
import com.xiyu.flash.games.pvz.states.loading.LoadingState;
import com.xiyu.flash.games.pvz.states.loading.TitleScreenState;
import com.xiyu.flash.games.pvz.states.mainmenu.MainMenuState;
import com.xiyu.flash.games.pvz.states.playing.LevelIntroState;
import com.xiyu.flash.games.pvz.states.playing.LawnViewState;
import com.xiyu.flash.games.pvz.states.playing.PanLeftState;
import com.xiyu.flash.games.pvz.states.playing.PanRightState;
import com.xiyu.flash.games.pvz.states.playing.ChooseSeedsState;
import com.xiyu.flash.games.pvz.states.playing.ReadySetStartState;
import com.xiyu.flash.games.pvz.states.playing.SlideUIState;
import com.xiyu.flash.games.pvz.states.playing.StartLevelState;
import com.xiyu.flash.games.pvz.states.playing.SodRollState;
import com.xiyu.flash.games.pvz.states.playing.ShowAwardState;
import com.xiyu.flash.games.pvz.states.playing.ZombiesWonState;
import com.xiyu.flash.games.pvz.states.playing.CrazyDaveState;
import com.xiyu.flash.games.pvz.states.playing.SurvivalRepickState;
import com.xiyu.flash.games.pvz.states.playing.OptionsMenuState;
import com.xiyu.flash.games.pvz.states.playing.ChallengeScreenState;
import com.xiyu.flash.games.pvz.states.playing.DialogState;
import com.xiyu.flash.games.pvz.states.playing.PlayLevelState;
import com.xiyu.flash.games.pvz.states.playing.UpsellScreenState;
import com.xiyu.flash.games.pvz.states.playing.PauseState;
import com.xiyu.flash.games.pvz.logic.UI.OptionsDialog;
import com.xiyu.flash.games.pvz.logic.UI.DialogBox;
import com.xiyu.flash.games.pvz.logic.AwardScreen;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.games.pvz.states.playing.MainViewState;
import com.xiyu.flash.games.pvz.states.playing.PlayingMainViewState;

import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;

import com.xiyu.flash.framework.resources.particles.*;

import java.io.*;


    public class PVZApp extends AppBase {

        public static  String STATE_PAUSE_SCREEN ="PauseScreen";
        public static  String STATE_RESUME_FROM_PAUSE ="ResumeFromPause";
        public static final int GAMEMODE_SURVIVAL_ENDLESS_STAGE_2 =1;
        public static  String STATE_SLIDE_UI ="SlideUI";
        public static final int BACKGROUND_IMAGE_WIDTH =945;
        public static  String STATE_SHOWAWARD ="ShowAward";
        public static final int BOARDRESULT_RESTART =3;
        public static  String STATE_SODROLL ="SodRoll";
        public static  String APP_ID ="pvz";
        public static  String STATE_PLAY_LEVEL ="PlayLevel";
        public static final int SCENE_CREDIT =6;
        public static  String STATE_UPSELL_SCREEN ="UpsellScreen";
        public static final int GAMEMODE_SURVIVAL_NORMAL_STAGE_3 =3;
        public static final int GAMEMODE_SURVIVAL_NORMAL_STAGE_5 =5;
        public static  String STATE_SURVIVAL_REPICK ="SurvivalRepick";
        public static final int SCENE_CHALLENGE =7;
        public static final int GAMEMODE_SURVIVAL_NORMAL_STAGE_4 =4;
        public static final int SCENE_PLAYING =3;
        public static final int GAMEMODE_SURVIVAL_NORMAL_STAGE_2 =17;
        public static final int BOARD_OFFSET =248;
        public static final int SCENE_LOADING =0;
        public static  String STATE_LOADING ="Loading";
        public static final int SCENE_MENU =1;
        public static  String STATE_ZOMBIES_WON ="ZombiesWon";
        public static final int GAMEMODE_UPSELL =7;
        public static  String STATE_START_LEVEL ="StartLevel";
        public static  String STATE_TITLE_SCREEN ="TitleScreen";
        public static final int SCENE_LEVEL_INTRO =2;
        public static  String STATE_PAN_RIGHT ="PanRight";
        public static final int BOARDRESULT_QUIT_APP =5;
        public static  String STATE_READY_SET_START ="ReadySetStart";
        public static final int BOARDRESULT_LOST =2;
        public static  String STATE_CRAZY_DAVE ="CrazyDave";
        public static  String STATE_OPTIONS_MENU ="OptionsMenu";
        public static final int BOARDRESULT_NONE =0;
        public static  String STATE_PAN_LEFT ="PanLeft";
        public static final int BOARDRESULT_WON =1;
        public static final int GAMEMODE_CHALLENGE_WALLNUT_BOWLING =6;
        public static  String STATE_LAWN_VIEW ="LawnView";
        public static final int BOARDRESULT_QUIT =4;
        public static final int SCENE_CRAZY_DAVE =8;
        public static final int GAMEMODE_SCARY_POTTER_1 =8;
        public static final int GAMEMODE_SCARY_POTTER_2 =9;
        public static final int SCENE_AWARD =5;
        public static final int GAMEMODE_SCARY_POTTER_6 =13;
        public static final int GAMEMODE_SCARY_POTTER_8 =15;
        public static final int GAMEMODE_SCARY_POTTER_9 =16;
        public static final int GAMEMODE_SCARY_POTTER_3 =10;
        public static final int GAMEMODE_SCARY_POTTER_4 =11;
        public static final int GAMEMODE_SCARY_POTTER_5 =12;
        public static  String STATE_SEEDCHOOSER ="SeedChooser";
        public static final int GAMEMODE_SCARY_POTTER_7 =14;
        public static  String STATE_DIALOG_BOX ="DialogBox";
        public static final int GAMEMODE_ADVENTURE =0;
        public static final int SCENE_ZOMBIES_WON =4;
        public static final int GAMEMODE_SCARY_POTTER_ENDLESS =2;
        public static  String STATE_MAIN_MENU ="MainMenu";
        public static final int BOARDRESULT_CHEAT =6;
        public static  String STATE_LEVEL_INTRO ="LevelIntro";
        public static  String STATE_CHALLENGE_SCREEN ="ChallengeScreen";

		public static  String STATE_ANIMATEEDITOR_SCREEN ="AnimateEditor";


		public Board mBoard ;
        public ChallengeScreen mChallengeScreen ;
        private FoleyManager mFoleyManager ;

         public void  togglePause (boolean isPaused ){
            super.togglePause(isPaused);
            if (isPaused)
            {
                stateManager().pushState(STATE_PAUSE_SCREEN);
            }
            else
            {
                stateManager().popState();
            };
        }

        public int mCutsceneTime ;
        public int mSurvivalFlags ;
        public Hashtable resIDs;
        public Hashtable resXMLIDs;
        public static float mDesity;
//        private Loader mResourceLoader ;

        public FoleyManager  foleyManager (){
            return (this.mFoleyManager);
        }

        public void  GetPlayerData (){
            this.mSaveObject = getSaveData();
            setSaveData(this.mSaveObject);
        }

        private ParticleManager mParticleManager ;
        public int mLevel ;

         public void  init (){
            super.init();
//            XML constant ;
            String foleyName ;
            FoleyType ft ;
            this.adAPI = new MSNAdAPI(this);
            this.mReanimator = new Reanimator(this);
            this.mParticleManager = new ParticleManager(this);
            this.mFoleyManager = new FoleyManager(this);
//            XML foleyType =describeType(PVZFoleyType );
//			for(int i =0; i<foleyType.constant.length();i++)
			{
//				constant = ()foleyType.constant.elementAt(i);
//                foleyName = constant.@name;
  //              ft=(FoleyType)PVZFoleyType.elementAt(foleyName));
//                this.mFoleyManager.loadFoley(ft);
            };
            musicManager().registerMusic(PVZMusic.CEREBRAWL, PVZMusic.CEREBRAWL_FILE);
            musicManager().registerMusic(PVZMusic.CHOOSE_YOUR_SEEDS, PVZMusic.CHOOSE_YOUR_SEEDS_FILE);
            musicManager().registerMusic(PVZMusic.CONVEYOR, PVZMusic.CONVEYOR_FILE);
            musicManager().registerMusic(PVZMusic.CRAZY_DAVE, PVZMusic.CRAZY_DAVE_FILE);
            musicManager().registerMusic(PVZMusic.GRASS_WALK, PVZMusic.GRASS_WALK_FILE);
            musicManager().registerMusic(PVZMusic.LOON_BOON, PVZMusic.LOON_BOON_FILE);
            musicManager().registerMusic(PVZMusic.WINMUSIC, PVZMusic.WINMUSIC_FILE);
            musicManager().registerMusic(PVZMusic.LOSEMUSIC, PVZMusic.LOSEMUSIC_FILE);
            musicManager().registerMusic(PVZMusic.ZENGARDEN, PVZMusic.ZENGARDEN_FILE);
            musicManager().registerMusic(PVZMusic.MOONGRAINS, PVZMusic.MOONGRAINS_FILE);
        }

        public boolean mSoundOn ;
        public boolean mPlacedZombies =false ;
        public int mMaxTime ;
        public SeedChooserScreen mSeedChooserScreen ;

        public boolean  IsSurvivalEndless (){
            if (this.mGameMode == GAMEMODE_SURVIVAL_ENDLESS_STAGE_2)
            {
                return (true);
            };
            return (false);
        }
        public boolean  IsAdventureMode (){
        	return false;
        	/*
            if (this.mGameMode == GAMEMODE_ADVENTURE)
            {
                return true;
            };
            return false;
            */
        }

        public Reanimator  reanimator (){
            return (this.mReanimator);
        }

        public boolean mSurvivalLocked ;
        public boolean mMusicOn ;
        public boolean mUpsellOn ;

        public boolean  IsScaryPotterLevel (){
        	return false;
        	/*
            if (this.mGameMode == GAMEMODE_SCARY_POTTER_ENDLESS)
            {
                return (true);
            };
            return (false);
            */
        }

        public int mGameMode ;
        public UpsellScreen mUpsellScreen ;
        public int mGamesPlayed ;
        public int mSodTime ;
        public int mMaxPlays ;
        public int mTotalZombiesKilled =0;
        public String mUpsellLink ;

        public ParticleManager  particleManager (){
            return (this.mParticleManager);
        }

         public void  start (){
            this.GetPlayerData();

            stateManager().bindState(STATE_LOADING, new LoadingState(this));
            stateManager().bindState(STATE_TITLE_SCREEN, new TitleScreenState(this));
            stateManager().bindState(STATE_MAIN_MENU, new MainMenuState(this));
            stateManager().bindState(STATE_LEVEL_INTRO, new LevelIntroState(this));
            stateManager().bindState(STATE_LAWN_VIEW, new LawnViewState(this));
            stateManager().bindState(STATE_PAN_LEFT, new PanLeftState(this));
            stateManager().bindState(STATE_PAN_RIGHT, new PanRightState(this));
            stateManager().bindState(STATE_SEEDCHOOSER, new ChooseSeedsState(this));
            stateManager().bindState(STATE_READY_SET_START, new ReadySetStartState(this));
            stateManager().bindState(STATE_SLIDE_UI, new SlideUIState(this));
            stateManager().bindState(STATE_START_LEVEL, new StartLevelState(this));
            stateManager().bindState(STATE_SODROLL, new SodRollState(this));
            stateManager().bindState(STATE_SHOWAWARD, new ShowAwardState(this));
            stateManager().bindState(STATE_ZOMBIES_WON, new ZombiesWonState(this));
            stateManager().bindState(STATE_CRAZY_DAVE, new CrazyDaveState(this));
            stateManager().bindState(STATE_SURVIVAL_REPICK, new SurvivalRepickState(this));
            stateManager().bindState(STATE_OPTIONS_MENU, new OptionsMenuState(this));
            stateManager().bindState(STATE_CHALLENGE_SCREEN, new ChallengeScreenState(this));
            stateManager().bindState(STATE_DIALOG_BOX, new DialogState(this));
            stateManager().bindState(STATE_PLAY_LEVEL, new PlayLevelState(this));
            stateManager().bindState(STATE_UPSELL_SCREEN, new UpsellScreenState(this));
            stateManager().bindState(STATE_PAUSE_SCREEN, new PauseState(this));

//			stateManager().bindState(STATE_ANIMATEEDITOR_SCREEN, new AnimateEditorState(this));

            if (!this.mSoundOn)
            {
                soundManager().mute();
            };
            if (!this.mMusicOn)
            {
                musicManager().mute();
            };
//            stateManager().changeState(STATE_LOADING);
            this.adAPI.init();
            super.start();
        }

        private Reanimator mReanimator ;
        public Object mSaveObject ;

        public boolean  IsSurvivalMode (){
            if (this.mGameMode == GAMEMODE_SURVIVAL_ENDLESS_STAGE_2)
            {
                return (true);
            };
            return true;
            //return (false);
        }

        public OptionsDialog mOptionsMenu ;
        public boolean mPuzzleLocked ;
        public DialogBox mDialogBox ;
        public MSNAdAPI adAPI ;
        public int mMaxExecutions ;
        public boolean mShowedCrazyDaveVasebreaker ;
        public AwardScreen mAwardScreen ;
        public int mBoardResult ;


        public Bitmap getResourcesImage(String imageid) {
        	/*
        	int id = SGZResources.getInstance().getImageID(imageid);
        	BitmapFactory.Options option = new BitmapFactory.Options();
        	option.inJustDecodeBounds = true;
        	BitmapFactory.decodeResource(getResources(), id,option);
        	int imageHeight = option.outHeight;
        	int imageWidth = option.outWidth;

        	option.inJustDecodeBounds =false;
        	option.inSampleSize = 1;
        	if(imageWidth>400) {
        		option.inSampleSize = 2;//option.inSampleSize*(int)option.outWidth/600;
        	}

        	if(imageHeight>400) {
        		//option.inSampleSize = 2;//(int)option.outHeight/150;
        	}

        	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id,option);
        	return bitmap;
        	*/
        	return MahJongImages.getInstance(this.getAssets(),null).getGameImage(imageid.toUpperCase());
        }

        public XmlPullParser getXMLParser(String xmlid) {
        	int id = SGZResources.getInstance().getXMLID(xmlid);
        	XmlPullParser parser = getResources().getXml(id);
        	return parser;
        }

        public ParticleDefinition getParticleDefinition(String parid) {

        	/*
        	int id = SGZResources.getInstance().getParticlesID(parid);
        	InputStream in = getResources().openRawResource(id);
        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        	int size =0;
        	byte[] buffer = new byte[1024];
        	try {
	        	while((size = in.read(buffer,0,1024))>=0) {
	        		outputStream.write(buffer,0,size);
	        	}
	        	in.close();
        	} catch(IOException iex) {
        		iex.printStackTrace();
        	}
        	buffer = outputStream.toByteArray();
        	ByteParticleDescriptor mDesc = new ByteParticleDescriptor(buffer);
        	*/

        	int id = SGZResources.getInstance().getParticlesID(parid);
        	XmlPullParser parser = getResources().getXml(id);
        	XMLParticleDescriptor mDesc = new XMLParticleDescriptor(this,parser);
        	ParticleDefinition mData = mDesc.createData(this);

        	return mData;
        }

        private SurfaceHolder holder;
   	    private gameloop gameLoopThread;
   	    private MainViewState mainView;

        protected void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
//    		init();
    		//for no title
    		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            this.mBoard = new Board(this);
//    		start();

    		DisplayMetrics  metrics = getResources().getDisplayMetrics();
    		mDesity = metrics.density;
    		mDesity = 1;
    		
    		System.out.println ("metrics.density: "+metrics.density+":"+metrics.widthPixels+":"+metrics.heightPixels);

    		
    		IState newState = new LevelIntroState(this);
//    		IState newState = new PanRightState(this);

    		mainView = new MainViewState(this,newState);

    		newState.onEnter();
    		setContentView(mainView);


			gameLoopThread = new gameloop(mainView);
			holder = mainView.getHolder();

               holder.addCallback(new SurfaceHolder.Callback() {
    			@SuppressWarnings("deprecation")
    			@Override
              public void surfaceDestroyed(SurfaceHolder holder)
              {
    				 //for stoping the game
    				gameLoopThread.setRunning(false);
    				gameLoopThread.getThreadGroup().interrupt();
               }

              @SuppressLint("WrongCall")
    			@Override
              public void surfaceCreated(SurfaceHolder holder)
              {
              	  gameLoopThread.setRunning(true);
              	  gameLoopThread.start();

               }
              @Override
              public void surfaceChanged(SurfaceHolder holder, int format,int width, int height)
                      {
                      }
               });

//    		MainMenuState Title  =new MainMenuState(this);

//    		setContentView(Title);

//    		ParseXML parse = new ParseXML(sgzview);
//    	  	parse.doIt();

    	}

    	@Override
    	public boolean onCreateOptionsMenu(Menu menu) {
    		// Inflate the menu; this adds items to the action bar if it is present.
    		getMenuInflater().inflate(R.menu.main, menu);

    		return true;
    	}

    	public  PVZApp (){
            super();
    		SGZResources.getInstance();
    		
//            this.mBoard = new Board(this);
//            this.mResourceLoader = new Loader();
        }

    	public MainViewState getMainView() {
    		return mainView;
    	}
    }


