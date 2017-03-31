package GameMode;

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
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import Engine.Interfaces.*;
import com.xinghai.Debug;

    public class GameMode implements IInputHandler
    {
        protected boolean m_enabled =false ;
        protected Object m_cursorImage =null ;
        protected double m_cursorId =0;
        protected Point m_mouseDownPos ;
        protected boolean m_captureMouse =false ;
        protected boolean m_showMousePointer =false ;
        protected Timer m_scrollTimer ;
        protected Sprite m_displayObject ;
        protected LotSite m_selectedLotSite =null ;
        public boolean m_spotlightOn =false ;
        protected String m_uiMode ;
        public static boolean s_gotVScrollCap =false ;
        public static boolean s_useVScrollCap =false ;
        public static double s_VScrollCap =0;

        public void  GameMode ()
        {
            this.m_scrollTimer = new Timer(125);
            this.m_displayObject = new Sprite();
            this.m_uiMode = UIEvent.OTHER;
            return;
        }//end

        public boolean  supportsEditing ()
        {
            return true;
        }//end

        public String  getToolboxId ()
        {
            return null;
        }//end

        public String  uiMode ()
        {
            return this.m_uiMode;
        }//end

        public void  enableMode ()
        {
            InputManager.addHandler(this);
            if (this.m_captureMouse)
            {
                Global.stage.addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp, false, 1, true);
                Global.stage.addEventListener(MouseEvent.MOUSE_MOVE, this.onMouseMove, false, 1, true);
            }
            if (this.m_cursorImage)
            {
                this.m_cursorId = UI.setCursor(this.m_cursorImage, this.m_showMousePointer);
            }
            if (Global.ui)
            {
                Global.ui.updateToolIcon(this);
            }
            GlobalEngine.viewport.overlayBase.addChild(this.m_displayObject);
            if (Config.VIEWPORT_CLEAR_COLOR != this.viewportClearColor)
            {
                Config.VIEWPORT_CLEAR_COLOR = this.viewportClearColor;
            }
            this.m_enabled = true;
            return;
        }//end

        public void  disableMode ()
        {
            this.setViewScrollEnabled(false);
            this.m_enabled = false;
            InputManager.removeHandler(this);
            if (Global.stage != null)
            {
                Global.stage.removeEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
                Global.stage.removeEventListener(MouseEvent.MOUSE_MOVE, this.onMouseMove);
            }
            if (this.m_cursorId != 0)
            {
                UI.removeCursor(this.m_cursorId);
                this.m_cursorId = 0;
            }
            if (this.m_displayObject && this.m_displayObject.parent)
            {
                this.m_displayObject.parent.removeChild(this.m_displayObject);
            }
            return;
        }//end

        public void  removeMode ()
        {
            return;
        }//end

        protected int  viewportClearColor ()
        {
            _loc_1 = EmbeddedArt.VIEWPORT_CLEAR_COLOR;
            if (Global.world.isThemeEnabled("halloween_theme"))
            {
                _loc_1 = 4280759361;
            }
            return _loc_1;
        }//end

        protected void  setViewScrollEnabled (boolean param1 )
        {
            this.m_scrollTimer.removeEventListener(TimerEvent.TIMER, this.onScrollTimer);
            this.m_scrollTimer.stop();
            if (param1 !=null)
            {
                this.m_scrollTimer.addEventListener(TimerEvent.TIMER, this.onScrollTimer);
                this.m_scrollTimer.start();
            }
            return;
        }//end

        protected void  onScrollTimer (TimerEvent event )
        {
            return;
        }//end

        public boolean  onMouseOut (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseDown (MouseEvent event )
        {

            this.m_mouseDownPos = new Point(event.stageX, event.stageY);
            Global.stage.addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp, false, 1, true);
            Global.stage.addEventListener(MouseEvent.MOUSE_MOVE, this.onMouseMove, false, 1, true);

            return false;
        }//end

        public boolean  onMouseUp (MouseEvent event )
        {
            this.m_mouseDownPos = null;
            Global.stage.removeEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
            Global.stage.removeEventListener(MouseEvent.MOUSE_MOVE, this.onMouseMove);
            return false;
        }//end

        public boolean  onMouseDoubleClick (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseMove (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseWheel (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onKeyDown (KeyboardEvent event )
        {
            return false;
        }//end

        public boolean  onKeyUp (KeyboardEvent event )
        {
            return false;
        }//end

        public boolean  isDragging ()
        {
            return this.m_mouseDownPos != null;
        }//end

        public void  drawTileArea (Graphics param1 ,Point param2 ,double param3 ,double param4 )
        {
            _loc_5 = IsoMath.tilePosToPixelPos(param2.x ,param2.y );
            _loc_6 = IsoMath.getPixelDeltaFromTileDelta(param3 ,0);
            _loc_7 = IsoMath.getPixelDeltaFromTileDelta(0,param4 );
            param1.moveTo(_loc_5.x, _loc_5.y);
            param1.lineTo(_loc_5.x + _loc_6.x, _loc_5.y + _loc_6.y);
            param1.lineTo(_loc_5.x + _loc_6.x + _loc_7.x, _loc_5.y + _loc_6.y + _loc_7.y);
            param1.lineTo(_loc_5.x + _loc_7.x, _loc_5.y + _loc_7.y);
            param1.lineTo(_loc_5.x, _loc_5.y);
            return;
        }//end

        public LotSite  getLotSite ()
        {
            return this.m_selectedLotSite;
        }//end

        public boolean  allowHighlight (MapResource param1 )
        {
            return true;
        }//end

        public int  getCustomMarketEvent (Item param1 )
        {
            return 0;
        }//end

        public static Point  getMouseStagePos ()
        {
            return new Point(Global.stage.mouseX, Global.stage.mouseY);
        }//end

        public static Point  getMouseTilePos (MouseEvent event =null ,Point param2 )
        {
            Point _loc_3 =null ;
            if (event)
            {
                _loc_3 = new Point(event.stageX, event.stageY);
            }
            else
            {
                _loc_3 = getMouseStagePos();
            }
            if (param2)
            {
                _loc_3 = _loc_3.subtract(param2);
            }
            return IsoMath.screenPosToTilePos(_loc_3.x, _loc_3.y);
        }//end


        public static double  undoZoomTransformations (double param1 ,double param2 )
        {
            param1 = param1 - param2 / 2;
            param1 = param1 * GlobalEngine.viewport.getZoom();
            param1 = param1 + param2 / 2;
            return param1;
        }//end

        public static double  redoZoomTransformations (double param1 ,double param2 )
        {
            param1 = param1 - param2 / 2;
            param1 = param1 / GlobalEngine.viewport.getZoom();
            param1 = param1 + param2 / 2;
            return param1;
        }//end


        public static Vector2  limitBackgroundScrolling (Vector2 param1 )
        {
            double _loc_8 =0;
            double _loc_10 =0;
            double _loc_12 =0;
            double _loc_14 =0;
            XML _loc_16 =null ;
            XMLList _loc_17 =null ;
            int _loc_18 =0;
            String _loc_19 =null ;
            XMLList _loc_20 =null ;
            String _loc_21 =null ;
            double _loc_22 =0;
            double _loc_23 =0;
            double _loc_24 =0;
            if (Global.world.overlayBackgroundRect == null)
            {
                return GlobalEngine.viewport.getScrollPosition();
            }
            if (GlobalEngine.viewport.stage === null)
            {
                return param1;
            }
            _loc_2 = GlobalEngine.viewport.stage.stageWidth /GlobalEngine.viewport.getZoom ();
            while (_loc_2 > Global.gameSettings().getNumber("maxNormalizedStageWidth") && GlobalEngine.viewport.getZoom() < Global.gameSettings().getNumber("maxZoom"))
            {

                GlobalEngine.viewport.setZoom(Global.gameSettings().getNumber("zoomStep") + GlobalEngine.viewport.getZoom());
                _loc_2 = GlobalEngine.viewport.stage.stageWidth / GlobalEngine.viewport.getZoom();
            }
            _loc_3 =Global.world.overlayBackgroundRect.width *GlobalEngine.viewport.getZoom ();
            _loc_4 =Global.world.overlayBackgroundRect.height *GlobalEngine.viewport.getZoom ();
            while (_loc_3 < GlobalEngine.viewport.stage.stageWidth && GlobalEngine.viewport.getZoom() < Global.gameSettings().getNumber("maxZoom"))
            {

                GlobalEngine.viewport.setZoom(Global.gameSettings().getNumber("zoomStep") + GlobalEngine.viewport.getZoom());
                _loc_3 = Global.world.overlayBackgroundRect.width * GlobalEngine.viewport.getZoom();
                _loc_4 = Global.world.overlayBackgroundRect.height * GlobalEngine.viewport.getZoom();
            }
            if (!s_gotVScrollCap)
            {
                s_gotVScrollCap = true;
                _loc_16 = Global.gameSettings().getXML();
                _loc_17 = _loc_16.collisionBackground.scrollLock;
                if (_loc_17 != null)
                {
                    _loc_19 = _loc_17.@verticalTiles;
                    if (_loc_19 == null)
                    {
                        s_useVScrollCap = false;
                        s_VScrollCap = 0;
                    }
                    else
                    {
                        s_useVScrollCap = true;
                        s_VScrollCap = int(_loc_19);
                    }
                }
                _loc_18 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPAND_MAP_1);
                if (_loc_18 > 0)
                {
                    _loc_20 = _loc_16.collisionBackground.expansion;
                    if (_loc_20 != null)
                    {
                        _loc_21 = _loc_20.@verticalTiles;
                        if (_loc_21 != null)
                        {
                            s_VScrollCap = int(_loc_21);
                        }
                    }
                }
            }
            if (s_useVScrollCap)
            {
                _loc_22 = IsoMath.getPixelDeltaFromTileDelta(1, 1).y;
                _loc_23 = s_VScrollCap;
                _loc_24 = (-_loc_22) * _loc_23 * GlobalEngine.viewport.getZoom();
                if (_loc_24 < _loc_4 / 2)
                {
                    _loc_4 = _loc_4 - (_loc_4 / 2 - _loc_24);
                }
            }
            Point _loc_5 =new Point ();
            _loc_5.x = param1.x;
            _loc_5.y = param1.y;
            _loc_6 =Global.world.overlayBackgroundRect.x ;
            _loc_7 =Global.world.overlayBackgroundRect.y ;
            _loc_5.x = _loc_5.x + _loc_6;
            _loc_5.y = _loc_5.y + _loc_7;
            _loc_5.x = undoZoomTransformations(_loc_5.x, GlobalEngine.viewport.stage.stageWidth);
            _loc_5.y = undoZoomTransformations(_loc_5.y, GlobalEngine.viewport.stage.stageHeight);
            double _loc_9 =0;
            _loc_8 = redoZoomTransformations(_loc_9, GlobalEngine.viewport.stage.stageWidth);
            _loc_8 = _loc_8 - _loc_6;
            _loc_11 = GlobalEngine.viewport.stage.stageWidth -_loc_3 ;
            _loc_10 = redoZoomTransformations(_loc_11, GlobalEngine.viewport.stage.stageWidth);
            _loc_10 = _loc_10 - _loc_6;
            double _loc_13 =0;
            _loc_12 = redoZoomTransformations(_loc_13, GlobalEngine.viewport.stage.stageHeight);
            _loc_12 = _loc_12 - _loc_7;
            _loc_15 = GlobalEngine.viewport.stage.stageHeight -_loc_4 ;
            _loc_14 = redoZoomTransformations(_loc_15, GlobalEngine.viewport.stage.stageHeight);
            _loc_14 = _loc_14 - _loc_7;
            if (_loc_3 >= GlobalEngine.viewport.stage.stageWidth)
            {
                if (_loc_5.x > 0)
                {
                    param1.x = _loc_8;
                }
                if (_loc_5.x + _loc_3 < GlobalEngine.viewport.stage.stageWidth)
                {
                    param1.x = _loc_10;
                }
            }
            else
            {
                if (_loc_5.x < 0)
                {
                    param1.x = _loc_8;
                }
                if (_loc_5.x + _loc_3 > GlobalEngine.viewport.stage.stageWidth)
                {
                    param1.x = _loc_10;
                }
            }
            if (_loc_4 >= GlobalEngine.viewport.stage.stageHeight)
            {
                if (_loc_5.y > 0)
                {
                    param1.y = _loc_12;
                }
                if (_loc_5.y + _loc_4 < GlobalEngine.viewport.stage.stageHeight)
                {
                    param1.y = _loc_14;
                }
            }
            else
            {
                if (_loc_5.y < 0)
                {
                    param1.y = _loc_12;
                }
                if (_loc_5.y + _loc_4 > GlobalEngine.viewport.stage.stageHeight)
                {
                    param1.y = _loc_14;
                }
            }
            param1.y = Math.max(param1.y, -25);
            return param1;
        }//end

    }


