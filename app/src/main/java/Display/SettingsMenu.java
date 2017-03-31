package Display;

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

import Classes.*;
import Classes.util.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class SettingsMenu extends Sprite
    {
        private DisplayObject m_bg ;
        private OptionButton m_trayToggleBtn ;
        private OptionButton m_graphicsToggleBtn ;
        private OptionButton m_sfxToggleBtn ;
        private OptionButton m_musicToggleBtn ;
        private SpriteButton m_zoomInBtn ;
        private SpriteButton m_zoomOutBtn ;
        private SpriteButton m_fullscreenBtn ;
        private Vector<GameSprite> m_coreButtons;
        private boolean m_isEnabled ;
        private Vector<GameSprite> m_tutorialButtons;

        public  SettingsMenu ()
        {
            this.m_bg =(DisplayObject) new EmbeddedArt.Options_holder();
            addChild(this.m_bg);
            this.m_trayToggleBtn = new OptionButton(EmbeddedArt.Options_settings_open_up, EmbeddedArt.Options_settings_open_over, EmbeddedArt.Options_settings_open_down, this.trayToggleBtnClickHandler);
            this.m_trayToggleBtn.toolTip = ZLoc.t("Main", "Settings_ToolTip");
            this.m_trayToggleBtn.x = 32;
            this.m_trayToggleBtn.y = 3;
            addChild(this.m_trayToggleBtn);
            this.m_coreButtons = new Vector<GameSprite>();
            this.m_tutorialButtons = new Vector<GameSprite>();
            this.m_zoomOutBtn = new SpriteButton(EmbeddedArt.FV_Main_ZoomOut_Norm, EmbeddedArt.FV_Main_ZoomOut_Over, EmbeddedArt.FV_Main_ZoomOut_Down, this.zoomOutBtnClickHandler);
            this.m_zoomOutBtn.toolTip = ZLoc.t("Main", "ZoomOut_ToolTip");
            this.m_coreButtons.push(this.m_zoomOutBtn);
            addChild(this.m_zoomOutBtn);
            this.m_zoomInBtn = new SpriteButton(EmbeddedArt.FV_Main_ZoomIn_Norm, EmbeddedArt.FV_Main_ZoomIn_Over, EmbeddedArt.FV_Main_ZoomIn_Down, this.zoomInBtnClickHandler);
            this.m_zoomInBtn.toolTip = ZLoc.t("Main", "ZoomIn_ToolTip");
            this.m_coreButtons.push(this.m_zoomInBtn);
            addChild(this.m_zoomInBtn);
            this.m_graphicsToggleBtn = new OptionButton(EmbeddedArt.FV_Main_Options_Visual_Normal, EmbeddedArt.FV_Main_Options_Visual_Over, EmbeddedArt.FV_Main_Options_Visual_Down, this.graphicsToggleBtnClickHandler);
            this.m_graphicsToggleBtn.toolTip = ZLoc.t("Main", "Graphics_ToolTip");
            this.m_coreButtons.push(this.m_graphicsToggleBtn);
            addChild(this.m_graphicsToggleBtn);
            this.m_fullscreenBtn = new SpriteButton(EmbeddedArt.FV_Main_Zoom_FullScreen_Norm, EmbeddedArt.FV_Main_Zoom_FullScreen_Over, EmbeddedArt.FV_Main_Zoom_FullScreen_Over, this.fullscreenBtnClickHandler);
            this.m_fullscreenBtn.toolTip = ZLoc.t("Main", "FullScreen_ToolTip");
            this.m_coreButtons.push(this.m_fullscreenBtn);
            addChild(this.m_fullscreenBtn);
            this.m_sfxToggleBtn = new OptionButton(EmbeddedArt.FV_Main_Options_SFX_Normal, EmbeddedArt.FV_Main_Options_SFX_Over, EmbeddedArt.FV_Main_Options_SFX_Down, this.sfxToggleBtnClickHandler);
            this.m_sfxToggleBtn.toolTip = ZLoc.t("Main", "SoundFX_ToolTip");
            this.m_coreButtons.push(this.m_sfxToggleBtn);
            this.m_tutorialButtons.push(this.m_sfxToggleBtn);
            addChild(this.m_sfxToggleBtn);
            this.m_musicToggleBtn = new OptionButton(EmbeddedArt.FV_Main_Options_Music_Normal, EmbeddedArt.FV_Main_Options_Music_Over, EmbeddedArt.FV_Main_Options_Music_Down, this.musicToggleBtnClickHandler);
            this.m_musicToggleBtn.toolTip = ZLoc.t("Main", "Music_ToolTip");
            this.m_coreButtons.push(this.m_musicToggleBtn);
            this.m_tutorialButtons.push(this.m_musicToggleBtn);
            addChild(this.m_musicToggleBtn);
            double _loc_1 =34;
            double _loc_2 =5;
            double _loc_3 =26;
            int _loc_4 =0;
            while (_loc_4 < this.m_coreButtons.length())
            {

                this.m_coreButtons.get(_loc_4).x = _loc_4 % 2 * _loc_3 + _loc_2;
                this.m_coreButtons.get(_loc_4).y = Math.floor(_loc_4 / 2) * _loc_3 + _loc_1;
                _loc_4++;
            }
            Global.world.addEventListener(FarmGameWorldEvent.USER_CHANGED, this.userChangedHandler);
            this.isEnabled = false;
            this.updateView();
            addEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler, false, 0, true);
            addEventListener(MouseEvent.MOUSE_MOVE, this.mouseOverHandler, false, 0, true);
            addEventListener(MouseEvent.MOUSE_DOWN, this.mouseOverHandler, false, 0, true);
            addEventListener(MouseEvent.MOUSE_UP, this.mouseOverHandler, false, 0, true);
            addEventListener(MouseEvent.CLICK, this.mouseOverHandler, false, 0, true);
            return;
        }//end

        public boolean  isEnabled ()
        {
            return this.m_isEnabled;
        }//end

        public void  isEnabled (boolean param1 )
        {
            GameSprite _loc_2 =null ;
            this.m_isEnabled = param1;
            for(int i0 = 0; i0 < this.m_coreButtons.size(); i0++)
            {
            	_loc_2 = this.m_coreButtons.get(i0);

                _loc_5 = this.m_isEnabled ;
                _loc_2.mouseChildren = this.m_isEnabled;
                _loc_2.mouseEnabled = _loc_5;
            }
            if (this.m_isEnabled)
            {
                this.m_trayToggleBtn.hoverImage = EmbeddedArt.Options_settings_open_over;
                this.m_trayToggleBtn.nonSelectedImage = EmbeddedArt.Options_settings_open_up;
                this.m_trayToggleBtn.selectedImage = EmbeddedArt.Options_settings_open_down;
                this.m_trayToggleBtn.updateImageState();
            }
            else
            {
                this.m_trayToggleBtn.hoverImage = EmbeddedArt.Options_settings_closed_over;
                this.m_trayToggleBtn.nonSelectedImage = EmbeddedArt.Options_settings_closed_up;
                this.m_trayToggleBtn.selectedImage = EmbeddedArt.Options_settings_closed_down;
                this.m_trayToggleBtn.updateImageState();
            }
            return;
        }//end

        private void  setGraphics (boolean param1 =false )
        {
            Global.player.options.put("graphicsLowQuality",  param1);
            if (param1 !=null)
            {
                Global.stage.quality = StageQuality.LOW;
            }
            else if (!Game.m_blitting)
            {
                Global.stage.quality = StageQuality.HIGH;
            }
            this.updateView();
            return;
        }//end

        private void  setSfx (boolean param1 =false )
        {
            Global.player.options.put("sfxDisabled",  param1);
            Sounds.setSoundManagerSFXMute();
            if (!Global.player.options.get("sfxDisabled") && Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_LOADTIME, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_SFX_LOADING))
            {
                Sounds.startPostloading();
            }
            Global.ui.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.SFX_CHANGED));
            this.updateView();
            return;
        }//end

        private void  setMusic (boolean param1 =false )
        {
            Global.player.options.put("musicDisabled",  param1);
            Sounds.setSoundManagerMusicMute();
            this.updateView();
            return;
        }//end

        private void  updateView ()
        {
            this.m_musicToggleBtn.selected = !Global.player.options.get("musicDisabled");
            this.m_sfxToggleBtn.selected = !Global.player.options.get("sfxDisabled");
            this.m_graphicsToggleBtn.selected = !Global.player.options.get("graphicsLowQuality");
            return;
        }//end

        public void  setInTransition ()
        {
            GameSprite _loc_2 =null ;
            Point _loc_3 =null ;
            Global.stage.removeEventListener(Event.ENTER_FRAME, this.stageEnterFrameHandler);
            _loc_1 = this.m_coreButtons.slice ();
            _loc_1.push(this.m_trayToggleBtn);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2.parent == Global.stage)
                {
                    _loc_3 = this.globalToLocal(new Point(_loc_2.x, _loc_2.y));
                    _loc_2.x = _loc_3.x;
                    _loc_2.y = _loc_3.y;
                    addChild(_loc_2);
                }
            }
            return;
        }//end

        public void  setEndTransition ()
        {
            Point _loc_1 =null ;
            GameSprite _loc_2 =null ;
            if (this.m_trayToggleBtn.parent == this)
            {
                _loc_1 = this.localToGlobal(new Point(this.m_trayToggleBtn.x, this.m_trayToggleBtn.y));
                this.m_trayToggleBtn.x = _loc_1.x;
                this.m_trayToggleBtn.y = _loc_1.y;
            }
            if (this.m_isEnabled)
            {
                for(int i0 = 0; i0 < this.m_tutorialButtons.size(); i0++)
                {
                	_loc_2 = this.m_tutorialButtons.get(i0);

                    if (_loc_2.parent == this)
                    {
                        _loc_1 = this.localToGlobal(new Point(_loc_2.x, _loc_2.y));
                        _loc_2.x = _loc_1.x;
                        _loc_2.y = _loc_1.y;
                    }
                }
            }
            Global.stage.addEventListener(Event.ENTER_FRAME, this.stageEnterFrameHandler);
            return;
        }//end

        public void  setEndPostTutorialTransition ()
        {
            this.setInTransition();
            return;
        }//end

        private void  addedToStageHandler (Event event )
        {
            removeEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
            this.setEndTransition();
            return;
        }//end

        private void  mouseOverHandler (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  stageEnterFrameHandler (Event event )
        {
            Point _loc_3 =null ;
            Point _loc_4 =null ;
            GameSprite _loc_5 =null ;
            localTo_loc_2 =Global(new Point(32,3));
            this.m_trayToggleBtn.x = _loc_2.x;
            this.m_trayToggleBtn.y = _loc_2.y;
            Global.stage.addChild(this.m_trayToggleBtn);
            if (this.m_isEnabled)
            {
                _loc_3 = localToGlobal(new Point(5, 86));
                this.m_sfxToggleBtn.x = _loc_3.x;
                this.m_sfxToggleBtn.y = _loc_3.y;
                _loc_4 = localToGlobal(new Point(31, 86));
                this.m_musicToggleBtn.x = _loc_4.x;
                this.m_musicToggleBtn.y = _loc_4.y;
                for(int i0 = 0; i0 < this.m_tutorialButtons.size(); i0++)
                {
                	_loc_5 = this.m_tutorialButtons.get(i0);

                    Global.stage.addChild(_loc_5);
                }
            }
            return;
        }//end

        private void  userChangedHandler (FarmGameWorldEvent event )
        {
            this.updateView();
            return;
        }//end

        private void  trayToggleBtnClickHandler (Event event )
        {
            event.stopImmediatePropagation();
            dispatchEvent(new UIEvent(UIEvent.SETTINGS_TOGGLE_CLICK, null));
            return;
        }//end

        private void  graphicsToggleBtnClickHandler (Event event )
        {
            event.stopImmediatePropagation();
            this.setGraphics(!this.m_graphicsToggleBtn.selected);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "graphics_quality");
            GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
            return;
        }//end

        private void  sfxToggleBtnClickHandler (Event event )
        {
            event.stopImmediatePropagation();
            this.setSfx(!this.m_sfxToggleBtn.selected);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "sfx_on_off");
            GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
            return;
        }//end

        private void  musicToggleBtnClickHandler (Event event )
        {
            event.stopImmediatePropagation();
            this.setMusic(!this.m_musicToggleBtn.selected);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "music_on_off");
            GameTransactionManager.addTransaction(new TSaveOptions(Global.player.options));
            return;
        }//end

        private void  zoomInBtnClickHandler (Event event )
        {
            double _loc_2 =0;
            event.stopImmediatePropagation();
            if (Global.world.isZoomEnabled())
            {
                _loc_2 = GlobalEngine.viewport.getZoom();
                _loc_2 = _loc_2 + Global.gameSettings().getNumber("zoomStep");
                _loc_2 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_2);
                _loc_2 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_2);
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "zoom_in");
                if (GlobalEngine.viewport.getZoom() != _loc_2)
                {
                    UI.updateZoom(_loc_2, false);
                }
            }
            return;
        }//end

        private void  zoomOutBtnClickHandler (Event event )
        {
            double _loc_2 =0;
            event.stopImmediatePropagation();
            if (Global.world.isZoomEnabled())
            {
                _loc_2 = GlobalEngine.viewport.getZoom();
                _loc_2 = _loc_2 - Global.gameSettings().getNumber("zoomStep");
                _loc_2 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_2);
                _loc_2 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_2);
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "zoom_out");
                if (GlobalEngine.viewport.getZoom() != _loc_2)
                {
                    UI.updateZoom(_loc_2, true);
                }
            }
            return;
        }//end

        private void  fullscreenBtnClickHandler (Event event )
        {
            event.stopImmediatePropagation();
            handleFullscreenToggle();
            return;
        }//end

        public static void  handleFullscreenToggle ()
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "full_screen");
            Utilities.toggleFullScreen();
            UI.updateZoom(GlobalEngine.viewport.getZoom(), Utilities.isFullScreen());
            UI.displayNewFranchise(true);
            return;
        }//end

    }



