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
import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
//import flash.events.*;
//import flash.geom.*;
import tool.*;

    public class GMEditMove extends GMEdit
    {
        protected GameObject m_curTransObj =null ;
        protected GameObject m_prevTransObj =null ;
        protected GameObject m_curUnoccludedObj =null ;
        protected GameObject m_prevUnoccludedObj =null ;
        protected GameObject m_bareGndObj ;

        public  GMEditMove ()
        {
            this.m_bareGndObj = new GameObject();
            m_showMousePointer = true;
            m_cursorImage = EmbeddedArt.hud_act_move;
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            return;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return param1.canBeDragged();
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            _loc_2 = (MapResource)m_selectedObject
            if (_loc_2 && !m_dragging)
            {
                if (_loc_2.isMoveLocked)
                {
                    _loc_2.unlockMovement();
                }
                else
                {
                    _loc_2.setActive(false);
                    Global.world.addGameMode(new GMEditMove());
                    if (_loc_2 instanceof DockHouse)
                    {
                        Global.world.addGameMode(new GMLinkedObjectMove(_loc_2), false);
                    }
                    else
                    {
                        Global.world.addGameMode(new GMObjectMove(_loc_2), false);
                    }
                }
            }
            return super.onMouseUp(event);
        }//end

        protected void  setOccluderObjectTransparency (Point param1 )
        {
            Array _loc_2 =TransparencyUtil.getObjectListFromPoint(param1 );
            if (this.m_prevTransObj != null)
            {
                this.m_prevTransObj.showOccluder();
            }
            if (this.m_curTransObj != null)
            {
                this.m_curTransObj.showOccluder();
            }
            this.m_curTransObj = null;
            this.m_curUnoccludedObj = null;
            if (_loc_2.length())
            {
                if (_loc_2.length >= 2)
                {
                    this.m_curTransObj = _loc_2.get(0);
                    this.m_curUnoccludedObj = _loc_2.get(1);
                    if (TransparencyUtil.isTransparencyType(this.m_curTransObj))
                    {
                        this.m_curTransObj.hideOccluder();
                    }
                }
                else if (_loc_2.length < 2)
                {
                    if (this.m_prevTransObj != null)
                    {
                        if (this.m_prevUnoccludedObj == _loc_2.get(0))
                        {
                            if (TransparencyUtil.isTransparencyType(this.m_prevTransObj))
                            {
                                this.m_prevTransObj.hideOccluder();
                            }
                            this.m_curUnoccludedObj = this.m_prevUnoccludedObj;
                            this.m_curTransObj = this.m_prevTransObj;
                        }
                        else if (this.m_prevTransObj == _loc_2.get(0) && this.m_prevUnoccludedObj == this.m_bareGndObj)
                        {
                            if (!TransparencyUtil.pointingToObjBase(_loc_2.get(0), IsoMath.screenPosToTilePos(param1.x, param1.y, true)))
                            {
                                this.m_curTransObj = _loc_2.get(0);
                                this.m_curUnoccludedObj = this.m_bareGndObj;
                                if (TransparencyUtil.isTransparencyType(this.m_curTransObj))
                                {
                                    this.m_curTransObj.hideOccluder();
                                }
                            }
                        }
                    }
                    else if (!TransparencyUtil.pointingToObjBase(_loc_2.get(0), IsoMath.screenPosToTilePos(param1.x, param1.y, true)))
                    {
                        this.m_curTransObj = _loc_2.get(0);
                        this.m_curUnoccludedObj = this.m_bareGndObj;
                        if (TransparencyUtil.isTransparencyType(this.m_curTransObj))
                        {
                            this.m_curTransObj.hideOccluder();
                        }
                    }
                }
            }
            this.m_prevTransObj = this.m_curTransObj;
            this.m_prevUnoccludedObj = this.m_curUnoccludedObj;
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            Vector2 _loc_3 =null ;
            Vector2 _loc_4 =null ;
            Point _loc_2 =new Point(event.stageX ,event.stageY );
            if (m_viewDragStartPos && _loc_2 && m_mouseDownPos && _loc_2.subtract(m_mouseDownPos).length >= Constants.DRAG_CLICK_EPSILON)
            {
                m_dragging = true;
            }
            if (m_dragging && m_viewDragStartPos)
            {
                _loc_3 = new Vector2();
                _loc_3.x = event.stageX - m_mouseDownPos.x;
                _loc_3.y = event.stageY - m_mouseDownPos.y;
                _loc_4 = new Vector2();
                _loc_4.x = m_viewDragStartPos.x + _loc_3.x / GlobalEngine.viewport.getZoom();
                _loc_4.y = m_viewDragStartPos.y + _loc_3.y / GlobalEngine.viewport.getZoom();
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
                if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TRANS))
                {
                    this.setOccluderObjectTransparency(_loc_2);
                }
            }
            updateCursor();
            updateOverlay();
            return true;
        }//end

         protected void  setHighlightObjectFromPoint (Point param1 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            Array _loc_2 =TransparencyUtil.getObjectListFromPoint(param1 );
            GameObject _loc_3 =null ;
            if (_loc_2.length())
            {
                _loc_5 = 0;
                if (_loc_2.length >= 2)
                {
                    _loc_5 = 1;
                }
                _loc_6 = _loc_2.length - 1;
                while (_loc_6 >= 0)
                {

                    if (isObjectHighlightable(_loc_2.get(_loc_6)))
                    {
                        _loc_3 = _loc_2.get(_loc_6 + _loc_5);
                    }
                    _loc_6 = _loc_6 - 1;
                }
                if (!_loc_3)
                {
                    _loc_3 = _loc_2.get(0 + _loc_5);
                }
            }
            m_displayObject.graphics.clear();
            if (_loc_3 instanceof MapResource && isObjectHighlightable(_loc_3))
            {
                drawFlatTileOutline((MapResource)_loc_3);
            }
            _loc_4 = boolean(_loc_3&& isObjectHighlightable(_loc_3));
            if (Boolean(_loc_3 && isObjectHighlightable(_loc_3)))
            {
                highlightObject(_loc_3);
            }
            else if (_loc_3 == null)
            {
                dehighlightObject();
            }
            if (OffsetEditor.active)
            {
                OffsetEditor.instance.consider(_loc_3);
            }
            return;
        }//end

         public void  removeMode ()
        {
            if (this.m_curTransObj)
            {
                this.m_curTransObj.showOccluder();
                this.m_curTransObj = null;
            }
            if (this.m_prevTransObj)
            {
                this.m_prevTransObj.showOccluder();
                this.m_prevTransObj = null;
            }
            this.m_curUnoccludedObj = null;
            this.m_prevUnoccludedObj = null;
            this.m_bareGndObj = null;
            super.removeMode();
            return;
        }//end

    }



