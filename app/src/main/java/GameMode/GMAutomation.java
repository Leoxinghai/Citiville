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
import Classes.sim.*;
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class GMAutomation extends GMDefault implements IToolTipTarget
    {
        private Array m_highlighted ;
        private Shape m_aoePreview =null ;
        private Vector3 m_dragPosition ;
        private boolean m_startup ;
        private boolean m_startClick =false ;
        private  boolean TEST_CANCEL =true ;
        public static  double TWEEN_TIME =0.25;

        public  GMAutomation ()
        {
            this.m_highlighted = new Array();
            m_cursorImage = EmbeddedArt.hud_actMenu_auto;
            m_showMousePointer = true;
            return;
        }//end

         protected boolean  isObjectHighlightable (GameObject param1 )
        {
            return isObjectSelectable(param1);
        }//end

        private void  fadeThenRemove (DisplayObject param1 )
        {
            preview = param1;
            if (preview == null)
            {
                return;
            }
            cont = Curry.curry(function(param11DisplayObject)
            {
                if (param11.parent != null)
                {
                    param11.parent.removeChild(param11);
                }
                return;
            }//end
            , preview);
            TweenLite.to(preview, TWEEN_TIME, {alpha:0, onComplete:cont});
            return;
        }//end

         public void  disableMode ()
        {
            MapResource _loc_1 =null ;
            double _loc_2 =0;
            Timer _loc_3 =null ;
            Global.ui.hideToolTip(this);
            if (this.m_aoePreview)
            {
                this.fadeThenRemove(this.m_aoePreview);
                this.m_aoePreview = null;
            }
            for(int i0 = 0; i0 < this.m_highlighted.size(); i0++) 
            {
            	_loc_1 = this.m_highlighted.get(i0);

                _loc_1.setHighlighted(false);
            }
            this.m_highlighted = new Array();
            super.disableMode();
            _loc_2 = 500;
            _loc_3 = new Timer(_loc_2, 1);
            _loc_3.addEventListener(TimerEvent.TIMER_COMPLETE, closeAutomationUI, false, 0, true);
            _loc_3.start();
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            this.m_startup = true;
            return;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            super.onMouseDown(event);
            this.m_startClick = true;
            return true;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            MapResource _loc_6 =null ;
            _loc_2 = getMouseTilePos(event);
            Vector3 _loc_3 =new Vector3(_loc_2.x ,_loc_2.y );
            Point _loc_4 =new Point(event.stageX ,event.stageY );
            if (m_viewDragStartPos && _loc_4 && m_mouseDownPos && _loc_4.subtract(m_mouseDownPos).length >= Constants.DRAG_CLICK_EPSILON)
            {
                m_dragging = true;
                this.m_startClick = false;
            }
            if (m_dragging && m_viewDragStartPos)
            {
                return super.onMouseMove(event);
            }
            if (this.m_startup && UI.cursorIsNull())
            {
                UI.popBlankCursor();
            }
            this.m_startup = false;
            _loc_5 = ActionAutomationManager.instance.getActionablesInRadius(_loc_3);
            for(int i0 = 0; i0 < this.m_highlighted.size(); i0++) 
            {
            	_loc_6 = this.m_highlighted.get(i0);

                if (_loc_5.indexOf(_loc_6) < 0)
                {
                    _loc_6.setHighlighted(false);
                }
            }
            for(int i0 = 0; i0 < _loc_5.size(); i0++) 
            {
            	_loc_6 = _loc_5.get(i0);

                if (this.m_highlighted.indexOf(_loc_6) < 0)
                {
                    _loc_6.setHighlighted(true);
                }
            }
            this.m_highlighted = _loc_5;
            if (this.m_highlighted.length > 0)
            {
                Global.ui.showToolTip(this);
            }
            else
            {
                Global.ui.hideToolTip(this);
            }
            _loc_7 = IsoMath.tilePosToPixelPos(_loc_3.x ,_loc_3.y );
            this.m_dragPosition = _loc_3;
            if (this.m_aoePreview == null)
            {
                this.m_aoePreview = this.startAOEPreview(ActionAutomationManager.instance.actionDiameter * 0.5, _loc_7, Constants.COLOR_VALID_PLACEMENT);
            }
            this.m_aoePreview.x = _loc_7.x;
            this.m_aoePreview.y = _loc_7.y;
            return true;
        }//end

        public Shape  startAOEPreview (double param1 ,Point param2 ,int param3 )
        {
            Point _loc_12 =null ;
            Shape _loc_4 =new Shape ();
            _loc_4.alpha = 0;
            _loc_4.x = param2.x;
            _loc_4.y = param2.y;
            _loc_5 =Global.world.getObjectLayerByName("road").getDisplayObject ()as Sprite ;
            (Global.world.getObjectLayerByName("road").getDisplayObject() as Sprite).addChild(_loc_4);
            _loc_6 = IsoMath.getPixelDeltaFromTileDelta(1,0);
            _loc_7 = IsoMath.getPixelDeltaFromTileDelta(0,1);
            _loc_8 = param1-;
            _loc_9 = param1;
            _loc_10 = param1-;
            _loc_11 = param1;
            _loc_4.graphics.lineStyle(1, param3, 0.75);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(_loc_8, _loc_10);
            _loc_4.graphics.moveTo(_loc_12.x, _loc_12.y);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(_loc_9, _loc_10);
            _loc_4.graphics.lineTo(_loc_12.x, _loc_12.y);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(_loc_9, _loc_11);
            _loc_4.graphics.lineTo(_loc_12.x, _loc_12.y);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(_loc_8, _loc_11);
            _loc_4.graphics.lineTo(_loc_12.x, _loc_12.y);
            _loc_12 = IsoMath.getPixelDeltaFromTileDelta(_loc_8, _loc_10);
            _loc_4.graphics.lineTo(_loc_12.x, _loc_12.y);
            TweenLite.to(_loc_4, TWEEN_TIME, {alpha:1});
            return _loc_4;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            if (!this.m_startClick || UI.cursorIsNull())
            {
                return;
            }
            _loc_2 = getMouseTilePos(event);
            _loc_2.x = Math.round(_loc_2.x);
            _loc_2.y = Math.round(_loc_2.y);
            Vector3 _loc_3 =new Vector3(_loc_2.x ,_loc_2.y );
            _loc_4 = ActionAutomationManager.instance.automate(_loc_3);
            if (ActionAutomationManager.instance.automate(_loc_3))
            {
                this.m_highlighted = new Array();
            }
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            if (m_dragging == false)
            {
                this.handleClick(event);
            }
            this.m_startClick = false;
            updateCursor();
            finishMouseUpEvent();
            return true;
        }//end

        public String  getToolTipStatus ()
        {
            return "";
        }//end

        public String  getToolTipHeader ()
        {
            return "";
        }//end

        public String  getToolTipNotes ()
        {
            return "";
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 ="";
            if (UI.cursorIsNull())
            {
                return _loc_1;
            }
            String _loc_2 ="";
            if (_loc_2 == "" && this.m_highlighted.length == 0)
            {
                return _loc_1;
            }
            if (ActionAutomationManager.instance.mode == ActionAutomationManager.TYPE_RENT_COLLECTOR)
            {
                _loc_2 = "RipeResidence";
            }
            else if (ActionAutomationManager.instance.mode == ActionAutomationManager.TYPE_ARMORED_TRUCK)
            {
                _loc_2 = "ClickToCollectBusinesses";
            }
            else if (ActionAutomationManager.instance.mode == ActionAutomationManager.TYPE_SUPPLY_TRUCK)
            {
                _loc_2 = "ClickToSupply";
            }
            _loc_1 = ZLoc.t("Main", _loc_2);
            return _loc_1;
        }//end

        public boolean  toolTipFollowsMouse ()
        {
            return true;
        }//end

        public Vector3  getToolTipPosition ()
        {
            return this.m_dragPosition;
        }//end

        public boolean  getToolTipVisibility ()
        {
            return true;
        }//end

        public Vector2  getToolTipScreenOffset ()
        {
            return null;
        }//end

        public Vector3  getDimensions ()
        {
            return null;
        }//end

        public int  getToolTipFloatOffset ()
        {
            return 0;
        }//end

        public ToolTipSchema  getToolTipSchema ()
        {
            return null;
        }//end

        private static void  closeAutomationUI (TimerEvent event )
        {
            UI.hideAutomationCatalog();
            return;
        }//end

    }



