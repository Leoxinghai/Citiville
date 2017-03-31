package root;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.xiyu.util.Dictionary;

import Engine.*;
import Engine.Classes.*;
import Engine.Init.*;
import Engine.Managers.*;
//
import com.lia.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.system.*;
//import flash.ui.*;
//import flash.external.*;
//
import Engine.Interfaces.*;

import com.xinghai.net.*;

    public class BaseGame extends Sprite
    {
        public Object parameters ;
        protected Function gameUpdateFunction =null ;

        public  BaseGame (Object param1 ,ZEngineOptions param2 )
        {

            Security.allowDomain("*");

            	ExternalInterface.call("zaspActivity","BaseGame"+1);
            if (LoaderInfo(this.root.loaderInfo).parameters)
            {
            	ExternalInterface.call("zaspActivity","BaseGame"+2);
                this.addEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            }

            	ExternalInterface.call("zaspActivity","BaseGame"+3);

            ContextMenu _loc_3 =new ContextMenu ();
            _loc_3.hideBuiltInItems();
            contextMenu = _loc_3;
            if (param2 == null)
            {
                param2 = new ZEngineOptions();
            }
            GlobalEngine.engineOptions = param2;
            GlobalEngine.initializationManager = new InitializationManager();
            Constants.TILE_WIDTH = param2.tileWidth;
            Constants.TILE_HEIGHT = param2.tileHeight;
            Constants.TILE_SCALE = param2.tileScale;


            return;
        }//end

        protected void  onAddedToStage (Event event )
        {
            ExternalInterface.call("zaspActivity","onAddedToStage"+1);
            this.removeEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            ExternalInterface.call("zaspActivity","onAddedToStage"+2);
            this.stage.scaleMode = StageScaleMode.NO_SCALE;
            ExternalInterface.call("zaspActivity","onAddedToStage"+3);
            this.stage.align = StageAlign.TOP_LEFT;
            if (this.parameters == null)
            {
                this.parameters = LoaderInfo(this.root.loaderInfo).parameters;
            }
            ExternalInterface.call("zaspActivity","onAddedToStage"+4);
            this.initEngine();
            ExternalInterface.call("zaspActivity","onAddedToStage"+5);
            this.init();
            return;
        }//end

        private void  initEngine ()
        {
            int _loc_2 =0;
            GlobalEngine.stage = this.stage;
            GlobalEngine.stage.addEventListener(Event.ENTER_FRAME, this.onEngineEnterFrame, false, 10000);
            GlobalEngine.viewport =(Viewport) new GlobalEngine.engineOptions.viewportClass;
            GlobalEngine.parseFlashVars(this.parameters);
            GlobalEngine.zaspManager =(ZaspManager) new GlobalEngine.engineOptions.zaspManagerClass(GlobalEngine.stage);
            if (GlobalEngine.getFlashVar("zaspDebug") == "true")
            {
                GlobalEngine.zaspManager.forceActivateDebugTracking(this);
            }
            if (Config.DEBUG_MODE)
            {
                SWFProfiler.init(this.stage, this);
            }
            GlobalEngine.stage.addEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreenChanged);
            GlobalEngine.stage.addEventListener(MouseEvent.CLICK, this.onStageMouseClick, false, 3);
            GlobalEngine.stage.addEventListener(KeyboardEvent.KEY_DOWN, this.onStageKeyDown, false, 3);
            ErrorManager.getInstance().addEventListener(ErrorEvent.ERROR, this.onError);
            TransactionManager.initialize();
            CityChangeManager.initialize();

            Vector _loc_1.<IEngineComponent >=GlobalEngine.engineOptions.engineComponents ;
            if (_loc_1 != null)
            {
                _loc_2 = 0;
                while (_loc_2 < _loc_1.length())
                {

                    _loc_1.get(_loc_2).initialize();
                    _loc_2++;
                }
            }
            GlobalEngine.log("Init", "Base URL: " + Config.SERVICES_GATEWAY_PATH);
            GlobalEngine.log("Init", "Asset URLs: " + Config.ASSET_PATHS.join(", "));
            return;
        }//end

        private void  onEngineEnterFrame (Event event )
        {
            ProcessManager.instance.onFrame();
            GlobalEngine.updateTimers();
            if (this.gameUpdateFunction != null)
            {
                this.gameUpdateFunction(event);
            }
            return;
        }//end

        protected void  init ()
        {
            return;
        }//end

        protected void  onError (ErrorEvent event )
        {
            return;
        }//end

        protected void  onFullScreenChanged (FullScreenEvent event )
        {
            if (GlobalEngine.viewport)
            {
                GlobalEngine.viewport.centerViewport();
            }
            return;
        }//end

        public void  disableAllInput ()
        {
            GlobalEngine.stage.mouseChildren = false;
            InputManager.disableInput();
            return;
        }//end

        public void  enableAllInput ()
        {
            GlobalEngine.stage.mouseChildren = true;
            InputManager.enableInput();
            return;
        }//end

        protected void  onStageMouseMove (MouseEvent event )
        {
            return;
        }//end

        protected void  onStageMouseClick (MouseEvent event )
        {
            GlobalEngine.lastInputTick = GlobalEngine.currentTime;
            return;
        }//end

        protected void  onStageKeyDown (KeyboardEvent event )
        {
            GlobalEngine.lastInputTick = GlobalEngine.currentTime;
            Class _loc_2 =GlobalEngine.engineOptions.easterEggManager ;
            if (_loc_2 != null)
            {
                _loc_2.instance.processKeyStroke(event);
            }
            return;
        }//end

        protected String  getDetailString ()
        {
            return "";
        }//end

    }



