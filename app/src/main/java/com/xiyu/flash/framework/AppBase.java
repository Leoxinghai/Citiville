package com.xiyu.flash.framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

import com.thelikes.thegot2run.SGZView;
import com.xiyu.util.*;
//import flash.display.Sprite;
//import flash.net.URLLoader;
//import flash.net.URLRequest;
//import flash.events.Event;
//import flash.utils.Dictionary;
//import flash.events.MouseEvent;
//import flash.display.BitmapData;
//import flash.ui.Keyboard;
//import flash.display.StageQuality;
//import flash.events.KeyboardEvent;
//import flash.geom.Rectangle;
//import flash.geom.Point;
//import flash.display.Bitmap;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.widgets.CWidgetManager;
import com.xiyu.flash.framework.resources.ResourceManager;
import com.xiyu.flash.framework.states.CStateManager;
import com.xiyu.flash.framework.resources.music.MusicManager;
import com.xiyu.flash.framework.resources.particles.ParticleDefinition;
import com.xiyu.flash.framework.resources.images.ImageManager;
import com.xiyu.flash.framework.resources.sound.SoundManager;
import com.xiyu.flash.framework.resources.fonts.FontManager;
import com.xiyu.flash.framework.resources.strings.StringManager;






import java.sql.Date;
//import java.sql.Time;
//import flash.text.TextField;
//import flash.filters.GlowFilter;
//import flash.utils.getTimer;
//import flash.ui.Mouse;
//import flash.net.SharedObject;
//import flash.utils.ByteArray;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;

import com.xiyu.flash.framework.states.IStateManager;






//import java.util.*;
import android.os.SystemClock;

    public class AppBase extends Activity {

    	
        private static final int SHIFT_FLAG =4;

        public static boolean LOW_QUALITY =false ;
//        public static float scale = 2.0f;

        public static void  log (String msg ,String level){
            if (level == "Loading")
            {
                return;
            };
        }

        private static final int ALT_FLAG =2;
        public static  int UPDATE_STEP_TIME =10;
        private static final int CONTROL_FLAG =1;

        private void  handleStringsLoaded (){
        	/*
            URLLoader loader =new URLLoader ();
            URLRequest dataURL =new URLRequest("data.xml");
            loader.addEventListener(Event.COMPLETE, this.handleDataLoaded);
            loader.load(dataURL);
            */
        }

        private Dictionary mCodeMap ;

        public void onTouch(MotionEvent e) {
        	if(e.getAction() == MotionEvent.ACTION_DOWN) {
                this._widgetManager.doMouseDown((int)e.getX(), (int)e.getY());
        	} else if(e.getAction() == MotionEvent.ACTION_MOVE) {
                this._widgetManager.doMouseMove((int)e.getX(), (int)e.getY());
        	} else if(e.getAction() == MotionEvent.ACTION_UP) {
                this._widgetManager.doMouseUp((int)e.getX(), (int)e.getY());
        	} else if(e.getAction() == MotionEvent.AXIS_WHEEL) {
        		this._widgetManager.doMouseWheel((int)e.getAxisValue(0));
        	}
//            this._widgetManager.onMouseEnter();


        }


        public void  togglePause (boolean isPaused ){
            if (isPaused == true)
            {
                this.mPaused = true;
                this.musicManager().pauseMusic();
                this.soundManager().pauseAll();
            }
            else
            {
                this.mPaused = false;
                this.musicManager().resumeMusic();
                this.soundManager().resumeAll();
            };
        }

        private static BitmapData bufferData = null;

        private void  advanceFrame (){
        }

/*
        public void  addUpdateHook (Function hook ){
            this.mUpdateHooks.push(hook);
        }
 */

        public void  init (){
            if (this.initialized)
            {
                return;
            };
            this.mCodeMap = new Dictionary();
/*
            this.mCodeMap.put("UP",Keyboard.UP);
            this.mCodeMap.elementAt("DOWN")=Keyboard.DOWN;
            this.mCodeMap.elementAt("LEFT")=Keyboard.LEFT;
            this.mCodeMap.elementAt("RIGHT")=Keyboard.RIGHT;
            this.mCodeMap.elementAt("SHIFT")=Keyboard.SHIFT;
            this.mCodeMap.elementAt("CONTROL")=Keyboard.CONTROL;
            this.mCodeMap.elementAt("TAB")=Keyboard.TAB;
            this.mCodeMap.elementAt("CAPS_LOCK")=Keyboard.CAPS_LOCK;
            this.mCodeMap.elementAt("ENTER")=Keyboard.ENTER;
            this.mCodeMap.elementAt("ESCAPE")=Keyboard.ESCAPE;
            this.mCodeMap.elementAt("END")=Keyboard.END;
            this.mCodeMap.elementAt("HOME")=Keyboard.HOME;
            this.mCodeMap.elementAt("INSERT")=Keyboard.INSERT;
            this.mCodeMap.elementAt("PAGE_UP")=Keyboard.PAGE_UP;
            this.mCodeMap.elementAt("PAGE_DOWN")=Keyboard.PAGE_DOWN;
            this.mCodeMap.elementAt("DELETE")=Keyboard.DELETE;
            this.mCodeMap.elementAt("F1")=Keyboard.F1;
            this.mCodeMap.elementAt("F2")=Keyboard.F2;
            this.mCodeMap.elementAt("F3")=Keyboard.F3;
            this.mCodeMap.elementAt("F4")=Keyboard.F4;
            this.mCodeMap.elementAt("F5")=Keyboard.F5;
            this.mCodeMap.elementAt("F6")=Keyboard.F6;
            this.mCodeMap.elementAt("F7")=Keyboard.F7;
            this.mCodeMap.elementAt("F8")=Keyboard.F8;
            this.mCodeMap.elementAt("F9")=Keyboard.F9;
            this.mCodeMap.elementAt("F11")=Keyboard.F11;
            this.mCodeMap.elementAt("F12")=Keyboard.F12;
            this.mCodeMap.elementAt("F13")=Keyboard.F13;
            this.mCodeMap.elementAt("F14")=Keyboard.F14;
            this.mCodeMap.elementAt("F15")=Keyboard.F15;
            this.mCodeMap.elementAt("NUMPAD_0")=Keyboard.NUMPAD_0;
            this.mCodeMap.elementAt("NUMPAD_1")=Keyboard.NUMPAD_1;
            this.mCodeMap.elementAt("NUMPAD_2")=Keyboard.NUMPAD_2;
            this.mCodeMap.elementAt("NUMPAD_3")=Keyboard.NUMPAD_3;
            this.mCodeMap.elementAt("NUMPAD_4")=Keyboard.NUMPAD_4;
            this.mCodeMap.elementAt("NUMPAD_5")=Keyboard.NUMPAD_5;
            this.mCodeMap.elementAt("NUMPAD_6")=Keyboard.NUMPAD_6;
            this.mCodeMap.elementAt("NUMPAD_7")=Keyboard.NUMPAD_7;
            this.mCodeMap.elementAt("NUMPAD_8")=Keyboard.NUMPAD_8;
            this.mCodeMap.elementAt("NUMPAD_9")=Keyboard.NUMPAD_9;
            this.mCodeMap.elementAt("NUMPAD_MULTIPLY")=Keyboard.NUMPAD_MULTIPLY;
            this.mCodeMap.elementAt("NUMPAD_ADD")=Keyboard.NUMPAD_ADD;
            this.mCodeMap.elementAt("NUMPAD_ENTER")=Keyboard.NUMPAD_ENTER;
            this.mCodeMap.elementAt("NUMPAD_SUBTRACT")=Keyboard.NUMPAD_SUBTRACT;
            this.mCodeMap.elementAt("NUMPAD_DECIMAL")=Keyboard.NUMPAD_DECIMAL;
            this.mCodeMap.elementAt("NUMPAD_DIVIDE")=Keyboard.NUMPAD_DIVIDE;
            this.mCodeMap.elementAt("0")=48;
            this.mCodeMap.elementAt("1")=49;
            this.mCodeMap.elementAt("2")=50;
            this.mCodeMap.elementAt("3")=51;
            this.mCodeMap.elementAt("4")=52;
            this.mCodeMap.elementAt("5")=53;
            this.mCodeMap.elementAt("6")=54;
            this.mCodeMap.elementAt("7")=55;
            this.mCodeMap.elementAt("8")=56;
            this.mCodeMap.elementAt("9")=57;
            this.mCodeMap.elementAt("A")=65;
            this.mCodeMap.elementAt("B")=66;
            this.mCodeMap.elementAt("C")=67;
            this.mCodeMap.elementAt("D")=68;
            this.mCodeMap.elementAt("E")=69;
            this.mCodeMap.elementAt("F")=70;
            this.mCodeMap.elementAt("G")=71;
            this.mCodeMap.elementAt("H")=72;
			this.mCodeMap.elementAt("I")=73;
			this.mCodeMap.elementAt("J")=74;
			this.mCodeMap.elementAt("K")=75;
			this.mCodeMap.elementAt("L")=76;
			this.mCodeMap.elementAt("M")=77;
			this.mCodeMap.elementAt("N")=78;
			this.mCodeMap.elementAt("O")=79;
			this.mCodeMap.elementAt("P")=80;
			this.mCodeMap.elementAt("Q")=81;
			this.mCodeMap.elementAt("R")=82;
			this.mCodeMap.elementAt("S")=83;
			this.mCodeMap.elementAt("T")=84;
			this.mCodeMap.elementAt("U")=85;
			this.mCodeMap.elementAt("V")=86;
			this.mCodeMap.elementAt("W")=87;
			this.mCodeMap.elementAt("X")=88;
			this.mCodeMap.elementAt("Y")=89;
			this.mCodeMap.elementAt("Z")=90;
			this.mCodeMap.elementAt(";")=186;
			this.mCodeMap.elementAt(":")=186;
			this.mCodeMap.elementAt("=")=187;
			this.mCodeMap.elementAt("+")=187;
			this.mCodeMap.elementAt("-")=189;
			this.mCodeMap.elementAt("_")=189;
			this.mCodeMap.elementAt("/")=191;
			this.mCodeMap.elementAt("?")=191;
			this.mCodeMap.elementAt("`")=192;
			this.mCodeMap.elementAt("~")=192;
			this.mCodeMap.elementAt("[")=219;
			this.mCodeMap.elementAt("{")=219;
			this.mCodeMap.elementAt("\\")=220;
			this.mCodeMap.elementAt("|")=220;
			this.mCodeMap.elementAt("]")=221;
			this.mCodeMap.elementAt("}")=221;
			this.mCodeMap.elementAt("'")=222;
			this.mCodeMap.elementAt(",")=188;
			this.mCodeMap.elementAt("<")=188;
			this.mCodeMap.elementAt(".")=190;
			this.mCodeMap.elementAt(">")=190;
*/
            this.screenRect = new Rectangle(0, 0, this._screenWidth, this._screenHeight);
            this.destPt = new Point(0, 0);
            if(this.bufferData == null)
            	this.bufferData = new BitmapData(this._screenWidth, this._screenHeight, false,1);
//            this.screenData = new BitmapData(this._screenWidth, this._screenHeight, false);
            Bitmap.Config config = Bitmap.Config.ALPHA_8;

            if(this.screen == null)
            	this.screen = Bitmap.createBitmap(this._screenWidth,this._screenHeight,config);
            
            this.screenGraphics = new Graphics2D(this.bufferData);
            this._widgetManager = new CWidgetManager(this);
            this._resourceManager = new ResourceManager(this);
            this._stateManager = new CStateManager();
            this.mMusicManager = new MusicManager(this);
            this.mImageManager = new ImageManager(this);
            this.mSoundManager = new SoundManager(this);
            this._fontManager = new FontManager(this);
            this.mStringManager = new StringManager(this);

            this.fpsCount = 0;
            this.initialized = true;
        }

        private boolean mPaused =false ;
        private Graphics2D screenGraphics ;

        private void  globalPause (){
            this.togglePause(!(this.mPaused));
        }

        private Dictionary mCheatBindings ;


        public int  appWidth (){
            return (this._appWidth);
        }

        private int _appHeight =240;
        private long avgFlashTime =0;

        public void  handleFrame (){
            long thisTime ;
            long elapsed ;
//            stage.quality = StageQuality.BEST;

            
            //thisTime = System.currentTimeMillis();//SystemClock.currentThreadTimeMillis();
            thisTime = SystemClock.currentThreadTimeMillis();
            
            elapsed = (thisTime - this.lastUpdateTime);
            this.lastUpdateTime = thisTime;
            this.avgFlashTime = (this.avgFlashTime + (thisTime - this.lastFlashTime));
            this.excessUpdateTime = (this.excessUpdateTime + elapsed);
            if (this.mShowDebugInfo)
            {
                this.fpsTime = (this.fpsTime + elapsed);
                while (this.fpsTime >= 1000)
                {
                    this.fpsTime = (this.fpsTime - 1000);
                    if (this.fpsCount > 0)
                    {
                        this.avgUpdateTime = Math.round((this.avgUpdateTime / this.fpsCount));
                        this.avgRenderTime = Math.round((this.avgRenderTime / this.fpsCount));
                        this.avgTaskTime = Math.round((this.avgTaskTime / this.fpsCount));
                        this.avgFlashTime = Math.round((this.avgFlashTime / this.fpsCount));
                    };
                    if (this.fpsCount > 0)
                    {
                        this.avgUpdateTime = 0;
                        this.avgRenderTime = 0;
                        this.avgTaskTime = 0;
                        this.avgFlashTime = 0;
                    };
                    this.fpsCount = 0;
                };
            };
            int startTime =(int)SystemClock.currentThreadTimeMillis();
            while (this.excessUpdateTime >= UPDATE_STEP_TIME)
            {
                this.updateStep();
                this.excessUpdateTime = (this.excessUpdateTime - UPDATE_STEP_TIME);
            };
            this.avgUpdateTime = (this.avgUpdateTime + ((int)SystemClock.currentThreadTimeMillis() - startTime));
            startTime = (int)SystemClock.currentThreadTimeMillis();
            this.renderStep();
            this.avgRenderTime = (this.avgRenderTime + ((int)SystemClock.currentThreadTimeMillis() - startTime));
            this.fpsCount++;
            this.lastFlashTime = (int)SystemClock.currentThreadTimeMillis();
            this.excessUpdateTime = 0;
/*
            if (this.canvas.useHandCursor != this.widgetManager().mShowFinger)
            {
                this.canvas.useHandCursor = this.widgetManager().mShowFinger;
                Mouse.hide();
                Mouse.show();
            };
*/
        }
/*
        public void  removeUpdateHook (Function hook ){
            int index =this.mUpdateHooks.indexOf(hook );
            if (index >= 0)
            {
                this.mUpdateHooks.splice(index, 1);
            };
        }
*/

	      protected void onDraw(Canvas canvas)
	      {
	    	  canvas.drawColor(Color.BLACK);
	    	  canvas.drawBitmap(this.screen, 0, 0, null);
	      }

        private boolean mUpdatesPaused =false ;

        private void  updateStep (){
            int k ;
//            Function hook ;
            if (((this.mUpdatesPaused) && (!(this.mDoStep))))
            {
                return;
            };
            this.mDoStep = false;
            int i =0;
            while (i < this.mStepsPerTick)
            {
                k = 0;
                while (k < this.mUpdateHooks.length())
                {
//              	hook=this.mUpdateHooks.elementAt(k);
//                    hook();
                    k++;
                };
                this.musicManager().update();
                this._stateManager.update();
                i++;
            };
        }

        private BitmapData screenData ;

/*
        public XML  getProperties (){
            return (this.mDataXML);
        }
*/
        private Object  getSO (){
            return (this.mSaveDataSO);
        }
        public Object  getSaveData (){
            return (this.mSaveData);
        }

        private int mStepsPerTick =1;
        private Point destPt ;

        public void  setSaveData (Object data ){
        }

        public void  appWidth (int value ){
            if (this.initialized)
            {
                return;
            };
            if (value <= 0)
            {
                //throw (new ArgumentError("Application width must be >= 1"));
            };
            this._appWidth = value;
        }

        private Object mSaveData =null ;

        public void  appHeight (int value ){
            if (this.initialized)
            {
                return;
            };
            if (value <= 0)
            {
                //throw (new ArgumentError("Application height must be >= 1"));
            };
            this._appHeight = value;
        }

        private Canvas canvas ;

/*
        protected void  handleKeyDown (KeyboardEvent e ){
            int code ;
            String key ;
            Function cheatFunc ;
            boolean cheatsEnabled =AppUtils.asBoolean(this.getProperties ().cheats.enabled );
            if (cheatsEnabled)
            {
                code = (e.keyCode << 3);
                if (e.ctrlKey)
                {
                    code = (code | CONTROL_FLAG);
                };
                if (e.altKey)
                {
                    code = (code | ALT_FLAG);
                };
                if (e.shiftKey)
                {
                    code = (code | SHIFT_FLAG);
                };
                key=this.mCheatBindings.elementAt(code);
                if (key != null)
                {
                	cheatFunc=this.mCheats.elementAt(key);
                    if (cheatFunc != null)
                    {
                        (cheatFunc());
                    };
                };
            };
            this._widgetManager.doKeyDown(e.keyCode);
            this._widgetManager.doKeyChar(e.charCode);
        }
*/
        public StringManager  stringManager (){
            return (this.mStringManager);
        }

        private void  toggleDebug (){
            this.mShowDebugInfo = !(this.mShowDebugInfo);
        }

        public boolean  isMuted (){
            return (this.mMuted);
        }

        public void  start (){
            this.fpsTime = 0;
            this.excessUpdateTime = 0;
            this.lastUpdateTime = (int)SystemClock.currentThreadTimeMillis();
            this.lastFlashTime = this.lastUpdateTime;

//            addEventListener(Event.ENTER_FRAME, this.handleFrame);
        }

        private long fpsTime ;

        public void  screenHeight (int value ){
            if (this.initialized)
            {
                return;
            };
            if (value <= 0)
            {
                //throw (new ArgumentError("Screen height must be >= 1"));
            };
            this._screenHeight = value;
        }


        private long lastUpdateTime ;

        public FontManager  fontManager (){
            return (this._fontManager);
        }

        private Rectangle screenRect ;

        private void  slowerUpdates (){
            this.mStepsPerTick = Math.max(1, (this.mStepsPerTick - 1));
        }

        private ResourceManager _resourceManager ;

        private void  fasterUpdates (){
            this.mStepsPerTick = Math.min(30, (this.mStepsPerTick + 1));
        }
        private void  renderStep (){
//            this.screenGraphics.reset();
            this._stateManager.draw(this.screenGraphics);
//            this.screenData.lock();
//            this.screenData.copyPixels(this.bufferData, this.screenRect, this.destPt);
//            this.screenData.unlock();
        }

        public void  screenWidth (int value ){
            if (this.initialized)
            {
                return;
            };
            if (value <= 0)
            {
                //throw (new ArgumentError("Screen width must be >= 1"));
            };
            this._screenWidth = value;
        }

        private int _appWidth =320;
        private long excessUpdateTime ;

        public void  toggleMute (boolean isMuted ,boolean masterMute){
            if (masterMute)
            {
                this.mMasterMute = isMuted;
            };
            isMuted = ((isMuted) || (this.mMasterMute));
            if (isMuted == true)
            {
                this.mMuted = true;
                this.musicManager().mute();
                this.soundManager().mute();
            }
            else
            {
                this.mMuted = false;
                this.musicManager().unmute();
                this.soundManager().unmute();
            };
        }

        private static Bitmap screen = null;

        public void  shutdown (){
//            removeChild(this.canvas);
//            this.canvas.removeChild(this.screen);
            this.canvas = null;
            this.screen = null;
            this.screenData.dispose();
            this.screenData = null;
            this._resourceManager = null;
            this._widgetManager = null;
            this.initialized = false;
        }

        private int _screenHeight =240;


        public SoundManager  soundManager (){
            return (this.mSoundManager);
        }

        private FontManager _fontManager ;

        public boolean  canLoadData (){
        	return true;
        	//return (AppUtils.asBoolean(this.mDataXML.saveData.canLoad, true));
        }

        private int fpsCount ;
        private int avgUpdateTime =0;
        private String mVersion ="v0.0";


        private SoundManager mSoundManager ;

        public int  screenHeight (){
            return (this._screenHeight);
        }

        private String mAppId ="";
        private boolean mShowDebugInfo =false ;


        private Array mUpdateHooks ;
        private CStateManager _stateManager ;
        private Object mSaveDataSO =null ;

        public int  screenWidth (){
            return (this._screenWidth);
        }

        private Dictionary mServiceMap ;
        private boolean mDoStep =false ;

        public ResourceManager  resourceManager (){
            return (this._resourceManager);
        }
        public CWidgetManager  widgetManager (){
            return (this._widgetManager);
        }

        private CWidgetManager _widgetManager ;
        private Dictionary mCheats ;

        public MusicManager  musicManager (){
            return (this.mMusicManager);
        }

        private void  stepUpdates (){
            if (this.mUpdatesPaused == true)
            {
                this.mDoStep = true;
            };
            this.mUpdatesPaused = true;
        }

        public ImageManager  imageManager (){
            return (this.mImageManager);
        }

        private boolean initialized =false ;
        private MusicManager mMusicManager ;

        public boolean  canSaveData (){
        	return false;
//            return (AppUtils.asBoolean(this.mDataXML.saveData.canSave, true));
        }

        private Dictionary mReferences ;

        private void  resumeUpdates (){
            this.mUpdatesPaused = false;
        }

        private ImageManager mImageManager ;

        public int  appHeight (){
            return (this._appHeight);
        }
        public IStateManager stateManager (){
            return (this._stateManager);
        }

        private int avgTaskTime =0;
        public boolean mMasterMute =false ;


        private long lastFlashTime ;
        private StringManager mStringManager ;
        private int avgRenderTime =0;


        public boolean  isPaused (){
            return (this.mPaused);
        }

        private int _screenWidth =320;
        private boolean mMuted =false ;
        
        public int unitWidth = 46;
        public int unitHeight = 65;
        

    	protected void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		//for no title
            this.mServiceMap = new Dictionary();
            this.mReferences = new Dictionary();
            this.mCheats = new Dictionary();
            this.mCheatBindings = new Dictionary();
            this.mUpdateHooks = new Array();
            
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            this._screenWidth = size.x;
            this._screenHeight = size.y;
            unitWidth = _screenWidth / 11;
            unitHeight = (int)(_screenHeight / 4);
            

//    		setContentView(new CrazyDaveState(this));

//    		ParseXML parse = new ParseXML(sgzview);
//    	  	parse.doIt();

    	}

    	public Bitmap getResourcesImage(String imageId) {
    		return null;
    	}
        public XmlPullParser getXMLParser(String xmlid) {
        	return null;
        }
        public ParticleDefinition getParticleDefinition(String parid) {
        	return null;
        }
    	public AppBase()
	    {
	    	super();
            this.mServiceMap = new Dictionary();
            this.mReferences = new Dictionary();
            this.mCheats = new Dictionary();
            this.mCheatBindings = new Dictionary();
            this.mUpdateHooks = new Array();
/*
            if ((((id == null)) || ((id.length() == 0))))
            {
                //throw (new ArgumentError("You must specify an application id."));
            };
            this.mAppId = id;
*/
        }

    }


