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
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.guide.*;
import Modules.stats.experiments.*;

import com.adobe.utils.*;
//import flash.events.*;
//import flash.geom.*;
import tool.*;
import com.xinghai.Debug;

    public class GMDefault extends GameMode
    {
        protected  int CANCEL_WARNING_NUM =1;
        protected  double BORDER_COLOR =0;
        protected  double BORDER_ALPHA =1;
        protected  double PATH_COLOR =16755200;
        protected  double PATH_ALPHA =1;
        protected Point m_viewDragStartPos =null ;
        protected boolean m_dragging =false ;
        protected GameObject m_highlightedObject =null ;
        protected GameObject m_selectedObject =null ;
        protected Object m_currentCursor =null ;
        protected Point m_moveToPos ;
        private static boolean m_skipObjectCulling =false ;

        public  GMDefault ()
        {
            m_skipObjectCulling = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_OBJECT_CULLING);
            return;
        }//end

        public void  setVisit (String param1 )
        {
            Global.setVisiting(param1);
            return;
        }//end

         public void  enableMode ()
        {
            MapResource _loc_2 =null ;
            super.enableMode();
            _loc_1 =Global.world.getObjectsByClass(MapResource );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_2.onEditModeSwitch();
            }
            return;
        }//end

         public void  disableMode ()
        {
            this.dehighlightObject();
            super.disableMode();
            return;
        }//end

         public void  removeMode ()
        {
            if (this.m_selectedObject && this.m_selectedObject instanceof IMechanicUser)
            {
                MechanicManager.getInstance().handleAction(this.m_selectedObject as IMechanicUser, Global.getClassName(this));
            }
            super.removeMode();
            this.deselectObject();
            return;
        }//end

        protected int  getSelectableTypes ()
        {
            return Constants.WORLDOBJECT_ALL;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            boolean _loc_3 =false ;
            super.onMouseDown(event);
            if (this.isViewDraggingEnabled())
            {
                this.m_viewDragStartPos = GlobalEngine.viewport.getScrollPosition().clone();
                this.m_dragging = false;
            }
            _loc_2 = this.m_highlightedObject ;
            if (_loc_2 instanceof LotSite && Global.isVisiting())
            {
                _loc_3 = this.checkLotOrderAvailability();
                this.checkLotSite(_loc_2);
            }
            if (_loc_2 && this.isObjectSelectable(_loc_2))
            {
                this.selectObject(_loc_2, event);
            }
            else
            {
                this.deselectObject();
            }

            Debug.debug4("GMDefault.onMouseDown."+m_highlightedObject);

            if (this.m_highlightedObject && this.m_highlightedObject.isAttached())
            {
                this.m_highlightedObject.onMouseOut();
            }
            return true;
        }//end

        protected boolean  checkLotOrderAvailability ()
        {
            return Global.world.citySim.lotManager.checkLotOrderAvailablity();
        }//end

        protected void  checkLotSite (GameObject param1 )
        {
            Vector _loc_4.<Quest >=null ;
            boolean _loc_5 =false ;
            Quest _loc_6 =null ;
            Point _loc_7 =null ;
            Point _loc_8 =null ;
            if (Global.world.ownerId == Player.FAKE_USER_ID_STRING)
            {
                _loc_4 = Global.questManager.getIncompleteActiveQuests();
                _loc_5 = false;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_6 = _loc_4.get(i0);

                    if (_loc_6.name == "q_expanding_ventures")
                    {
                        _loc_5 = true;
                        break;
                    }
                }
                if (!_loc_5)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "NotReadyForFranchises"));
                    return;
                }
            }
            _loc_2 =Global.franchiseManager.model.getBusinessCount ()>0;
            _loc_3 = this.checkLotOrderAvailability ();
            if (param1 instanceof LotSite && !Global.franchiseManager.placementMode)
            {
                if (!_loc_2)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "HasBusinessCheck"));
                }
                else if (!_loc_3)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "LotOrderAvailableCheck"));
                }
                else
                {
                    m_selectedLotSite =(LotSite) param1;
                    _loc_7 = new Point(160, 160);
                    _loc_8 = new Point(param1.positionX + param1.sizeX / 2, param1.positionY + param1.sizeY / 2);
                    Global.guide.displaySpotLightOnTile(_loc_8, _loc_7, GuideConstants.MASK_STYLE_ELLIPSE, GuideConstants.MASK_LOT_SITES, 0.3, false);
                    Global.franchiseManager.placementMode = true;
                    UI.displayCatalog(new CatalogParams("business"));
                }
            }
            else
            {
                Global.franchiseManager.placementMode = false;
            }
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            if (this.m_highlightedObject && this.m_highlightedObject.isAttached())
            {
                this.m_highlightedObject.onMouseOver(event);
            }
            if (this.m_dragging == false)
            {
                this.handleClick(event);
            }
            this.setHighlightObjectFromPoint(getMouseStagePos());
            this.updateCursor();
            this.finishMouseUpEvent();
            super.onMouseUp(event);
            return true;
        }//end

        protected void  finishMouseUpEvent ()
        {
            this.m_dragging = false;
            this.m_viewDragStartPos = null;
            return;
        }//end

        protected boolean  canBeClicked (MouseEvent event )
        {
            return true;
        }//end

        protected boolean  isViewDraggingEnabled ()
        {
            return !Global.guide.isActive();
        }//end

        protected void  handleClick (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (this.m_highlightedObject)
            {
                _loc_2 = true;
                if (this.m_selectedObject && this.m_selectedObject instanceof IMechanicUser)
                {
                    _loc_2 = !MechanicManager.getInstance().handleAction(this.m_selectedObject as IMechanicUser, Global.getClassName(this));
                }
                if (_loc_2 && this.canBeClicked(event) && this.m_highlightedObject.isAttached() == true)
                {
                    this.m_highlightedObject.onClick();
                }
                if (this.m_highlightedObject && this.m_highlightedObject.isAttached() == false)
                {
                    this.dehighlightObject();
                }
            }
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            Vector2 _loc_3 =null ;
            Vector2 _loc_4 =null ;
            Point _loc_2 =new Point(event.stageX ,event.stageY );
            if (this.m_viewDragStartPos && _loc_2 && m_mouseDownPos && _loc_2.subtract(m_mouseDownPos).length >= Constants.DRAG_CLICK_EPSILON)
            {
                this.m_dragging = true;
            }
            if (this.m_dragging && this.m_viewDragStartPos)
            {
                _loc_3 = new Vector2();
                _loc_3.x = event.stageX - m_mouseDownPos.x;
                _loc_3.y = event.stageY - m_mouseDownPos.y;
                _loc_4 = new Vector2();
                _loc_4.x = this.m_viewDragStartPos.x + _loc_3.x / GlobalEngine.viewport.getZoom();
                _loc_4.y = this.m_viewDragStartPos.y + _loc_3.y / GlobalEngine.viewport.getZoom();
                _loc_4 = limitBackgroundScrolling(_loc_4);
                GlobalEngine.viewport.setScrollPosition(_loc_4);
                if (!skipObjectCulling)
                {
                    Global.world.updateWorldObjectCulling(GameWorld.CULL_CHECK_ALL);
                }
            }
            else
            {
                this.setHighlightObjectFromPoint(_loc_2);
            }
            this.updateCursor();
            this.updateOverlay();
            return true;
        }//end

        public void  updateObjectUnderMouse ()
        {
            Point _loc_1 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
            this.setHighlightObjectFromPoint(_loc_1);
            this.updateCursor();
            this.updateOverlay();
            return;
        }//end

        public GameObject  getSelectedObject ()
        {
            return this.m_selectedObject;
        }//end

        protected Array  getObjectListFromPoint (Point param1 )
        {
            WorldObject _loc_5 =null ;
            _loc_2 = IsoMath.stageToViewport(param1 );
            Array _loc_3 =Global.world.getPickObjects(_loc_2 ,this.getSelectableTypes ());
            Array _loc_4 =Global.world.getCollisionMap ().getObjectsByPosition(Math.floor(getMouseTilePos ().x ),Math.floor(getMouseTilePos ().y ));

            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5 instanceof MapResource && (_loc_5 as MapResource).usingTilePicking())
                {
                    if (!ArrayUtil.arrayContainsValue(_loc_3, _loc_5))
                    {
                        _loc_3.push(_loc_5);
                    }
                }
            }

            return _loc_3;
        }//end

        protected void  setHighlightObjectFromPoint (Point param1 )
        {
            int _loc_5 =0;
            Array _loc_2 =TransparencyUtil.getObjectListFromPoint(param1 );
            GameObject _loc_3 =null ;

            if (_loc_2.length())
            {
                _loc_5 = _loc_2.length - 1;
                while (_loc_5 >= 0)
                {


                    if (this.isObjectHighlightable(_loc_2.get(_loc_5)))
                    {
                        _loc_3 = _loc_2.get(_loc_5);
                    }
                    _loc_5 = _loc_5 - 1;
                }
                if (!_loc_3)
                {
                    _loc_3 = _loc_2.get(0);
                }
            }


            m_displayObject.graphics.clear();

            if(_loc_3 instanceof WeiboPeep) {
               Debug.debug7("GMDefault.setHighlight " + _loc_3);
            }

            if (_loc_3 instanceof MapResource && this.isObjectHighlightable(_loc_3))
            {
                this.drawFlatTileOutline((MapResource)_loc_3);
            }
            _loc_4 = boolean(_loc_3&& this.isObjectHighlightable(_loc_3));
            if (Boolean(_loc_3 && this.isObjectHighlightable(_loc_3)))
            {
		if(_loc_3 instanceof WeiboPeep) {
		   Debug.debug7("GMDefault.setHighlight.2");
		}
                this.highlightObject(_loc_3);
            }
            else if (_loc_3 == null)
            {
                this.dehighlightObject();
            }
            if (OffsetEditor.active)
            {
                OffsetEditor.instance.consider(_loc_3);
            }
            return;
        }//end

        protected void  drawFlatTileOutline (MapResource param1 )
        {
            if (param1.usingTileHighlight())
            {
                m_displayObject.graphics.lineStyle(1, param1.getHighlightColor());
                m_displayObject.graphics.beginFill(0, 0);
                drawTileArea(m_displayObject.graphics, new Point(param1.positionX, param1.positionY), param1.sizeX, param1.sizeY);
                m_displayObject.graphics.endFill();
            }
            return;
        }//end

        protected boolean  isObjectHighlightable (GameObject param1 )
        {
            if (param1 instanceof Doober && (param1 as Doober).isFlying)
            {
                return false;
            }
            return true;
        }//end

        protected boolean  isObjectSelectable (GameObject param1 )
        {
            return true;
        }//end

        protected boolean  isObjectDraggable (GameObject param1 )
        {
            return param1.canBeDragged();
        }//end

        public void  turnOffObject ()
        {
            this.dehighlightObject();
            return;
        }//end

        protected void  highlightedObjectStateChangeHandler (GameObjectEvent event )
        {
            this.updateCursor();
            return;
        }//end

        protected void  highlightObject (GameObject param1 ,MouseEvent param2 )
        {
            if (param1 != this.m_highlightedObject)
            {
                this.dehighlightObject();
                this.m_highlightedObject = param1;
                this.m_highlightedObject.addEventListener(GameObjectEvent.STATE_CHANGE, this.highlightedObjectStateChangeHandler, false, 0, true);
                if (param1 !=null)
                {
                    param1.onMouseOver(param2);
                }
                this.updateCursor();
            }
            if (param1 != null)
            {
                Global.ui.showToolTip(param1);
            }
            return;
        }//end

        protected void  dehighlightObject ()
        {
            Global.ui.hideToolTip(this.m_highlightedObject);
            if (this.m_highlightedObject)
            {
                this.m_highlightedObject.removeEventListener(GameObjectEvent.STATE_CHANGE, this.highlightedObjectStateChangeHandler);
                this.m_highlightedObject.onMouseOut();
                this.m_highlightedObject = null;
            }
            this.updateCursor();
            return;
        }//end

        protected void  selectObject (GameObject param1 ,MouseEvent param2 )
        {
            if (param1 != this.m_selectedObject)
            {
                this.deselectObject();
                this.m_selectedObject = param1;
                if (param1 !=null)
                {
                    param1.onSelect();
                }
            }
            return;
        }//end

        protected void  deselectObject ()
        {
            if (this.m_selectedObject)
            {
                this.m_selectedObject.onDeselect();
                this.m_selectedObject = null;
            }
            return;
        }//end

        protected void  updateCursor ()
        {
            _loc_1 = this.getCursor ();
            if (_loc_1 != this.m_currentCursor)
            {
                if (m_cursorId > 0)
                {
                    UI.removeCursor(m_cursorId);
                    m_cursorId = 0;
                }
                m_cursorId = UI.setCursor(_loc_1, true);
                this.m_currentCursor = _loc_1;
            }
            return;
        }//end

        protected Object  getCursor ()
        {
            Object _loc_1 =null ;
            if (this.m_highlightedObject && this.m_highlightedObject.overrideCursor())
            {
                _loc_1 = GameObject.USE_CUSTOM_CURSORS ? (this.m_highlightedObject.getCustomCursor()) : (null);
                if (!_loc_1)
                {
                    _loc_1 = this.m_highlightedObject.getCursor();
                }
            }
            if (_loc_1 == null)
            {
                _loc_1 = m_cursorImage;
            }
            return _loc_1;
        }//end

         public boolean  onMouseWheel (MouseEvent event )
        {
            double _loc_2 =0;
            int _loc_3 =0;
            Vector2 _loc_4 =null ;
            if (Global.world.isZoomEnabled())
            {
                _loc_2 = GlobalEngine.viewport.getZoom();
                if (event.delta > 1)
                {
                    _loc_2 = _loc_2 + Global.gameSettings().getNumber("zoomStep");
                    _loc_3 = GameWorld.CULL_ZOOM_IN;
                }
                else if (event.delta < -1)
                {
                    _loc_2 = _loc_2 - Global.gameSettings().getNumber("zoomStep");
                    _loc_3 = GameWorld.CULL_ZOOM_OUT;
                }
                _loc_2 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_2);
                _loc_2 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_2);
                if (GlobalEngine.viewport.getZoom() == _loc_2)
                {
                    return true;
                }
                GlobalEngine.viewport.setZoom(_loc_2);
                _loc_4 = GlobalEngine.viewport.getScrollPosition();
                //_loc_4 = GameMode.limitBackgroundScrolling(_loc_4);
                GlobalEngine.viewport.setScrollPosition(_loc_4);
                Global.world.updateWorldObjectCulling(_loc_3);
            }
            return true;
        }//end

        protected void  updateOverlay ()
        {
            return;
        }//end

        public static boolean  skipObjectCulling ()
        {
            return m_skipObjectCulling;
        }//end

    }



