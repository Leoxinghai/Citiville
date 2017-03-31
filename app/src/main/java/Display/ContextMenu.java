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
//import flash.display.*;
//import flash.events.*;

    public class ContextMenu extends Sprite
    {
        protected Object m_style ;
        protected Array m_items ;
        protected Shape m_menuBackground ;
        public static  int ITEM_WIDTH =100;
        public static  int ITEM_HEIGHT =20;
public static ContextMenu m_currentMenu ;

        public  ContextMenu (Array param1 ,Object param2 )
        {
            Object _loc_4 =null ;
            this.m_style = {contextMenu:{backgroundColor:EmbeddedArt.TEXT_BACKGROUND_COLOR, borderColor:EmbeddedArt.BORDER_MAIN_COLOR, borderWidth:1}, contextMenuItem:{width:ITEM_WIDTH, height:ITEM_HEIGHT, font:EmbeddedArt.defaultFontName, fontSize:12, textAlign:"left", padding:2, color:EmbeddedArt.TEXT_MAIN_COLOR, backgroundColor:EmbeddedArt.TEXT_BACKGROUND_COLOR, hoverColor:EmbeddedArt.TEXT_BACKGROUND_COLOR, hoverBackgroundColor:EmbeddedArt.TEXT_MAIN_COLOR}};
            this.m_items = new Array();
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_4 = param1.get(_loc_3);
                if (_loc_4 instanceof Object && _loc_4.hasOwnProperty("label") && _loc_4.hasOwnProperty("action"))
                {
                    this.m_items.push(new ContextMenuItem(_loc_4.label, _loc_4.action, _loc_4.style || this.m_style));
                }
                else if (_loc_4 instanceof ContextMenuItem)
                {
                    this.m_items.push(_loc_4);
                }
                else
                {
                    throw new ArgumentError("items array can only contain objects with label and action properties or ContextMenuItems");
                }
                _loc_3++;
            }
            this.setStyle(param2);
            this.draw();
            this.addEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler, false, 0, true);
            return;
        }//end

        public void  show (double param1 ,double param2 )
        {
            if (ContextMenu.m_currentMenu != null)
            {
                ContextMenu.m_currentMenu.hide();
            }
            this.x = param1;
            this.y = param2;
            Global.stage.addChild(this);
            ContextMenu.m_currentMenu = this;
            UI.updateCurrentCursor(true);
            return;
        }//end

        public void  hide ()
        {
            this.removeListeners();
            if (Global.stage == this.parent)
            {
                Global.stage.removeChild(this);
            }
            ContextMenu.m_currentMenu = null;
            return;
        }//end

        protected void  setStyle (Object param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_2 = param1.get(i0);

                    this.m_style.put(_loc_2,  this.m_style.get(_loc_2) || {});
                    for(int i0 = 0; i0 < param1.get(_loc_2).size(); i0++)
                    {
                    	_loc_3 = param1.get(_loc_2).get(i0);

                        this.m_style.get(_loc_2).put(_loc_3,  param1.get(_loc_2).get(_loc_3));
                    }
                }
            }
            return;
        }//end

        protected void  draw ()
        {
            ContextMenuItem _loc_1 =null ;
            int _loc_5 =0;
            while (this.numChildren > 0)
            {

                this.removeChildAt(0);
            }
            int _loc_2 =0;
            _loc_3 = this.m_style.contextMenuItem.height;
            _loc_4 = this.m_style.contextMenuItem.padding;
            for(int i0 = 0; i0 < this.m_items.size(); i0++)
            {
            		_loc_1 = this.m_items.get(i0);

                _loc_1.y = _loc_2 * _loc_3 + _loc_2 * _loc_4;
                this.addChild(_loc_1);
                _loc_2 = _loc_2 + 1;
            }
            this.m_menuBackground = new Shape();
            _loc_5 = this.m_style.contextMenu.borderWidth;
            this.m_menuBackground.graphics.lineStyle(_loc_5, this.m_style.contextMenu.backgroundColor);
            this.m_menuBackground.graphics.beginFill(this.m_style.contextMenu.borderColor);
            this.m_menuBackground.graphics.drawRoundRect(-_loc_5, -_loc_5, this.width + _loc_5, this.height + _loc_5, _loc_5 * 4, _loc_5 * 4);
            this.m_menuBackground.graphics.endFill();
            this.addChildAt(this.m_menuBackground, 0);
            for(int i0 = 0; i0 < this.m_items.size(); i0++)
            {
            	_loc_1 = this.m_items.get(i0);

                _loc_1.draw();
            }
            return;
        }//end

        protected void  addedToStageHandler (Event event )
        {
            this.removeEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
            this.addListeners();
            return;
        }//end

        protected void  mouseOverHandler (MouseEvent event )
        {
            ContextMenuItem _loc_2 =null ;
            if (event.target.parent == this && event.target.hasOwnProperty("state"))
            {
                for(int i0 = 0; i0 < this.m_items.size(); i0++)
                {
                	_loc_2 = this.m_items.get(i0);

                    if (_loc_2 != event.target)
                    {
                        _loc_2.state = ContextMenuItem.STATE_UP;
                        continue;
                    }
                    _loc_2.state = ContextMenuItem.STATE_OVER;
                }
            }
            return;
        }//end

        protected void  mouseClickHandler (MouseEvent event )
        {
            this.hide();
            return;
        }//end

        protected void  stageMouseClickHandler (MouseEvent event )
        {
            if (!this.hitTestPoint(Global.stage.mouseX, Global.stage.mouseY, true))
            {
                this.hide();
            }
            return;
        }//end

        protected void  stageMouseMoveHandler (MouseEvent event )
        {
            ContextMenuItem _loc_2 =null ;
            if (!this.hitTestPoint(Global.stage.mouseX, Global.stage.mouseY, true))
            {
                for(int i0 = 0; i0 < this.m_items.size(); i0++)
                {
                		_loc_2 = this.m_items.get(i0);

                    _loc_2.state = ContextMenuItem.STATE_UP;
                }
            }
            return;
        }//end

        protected void  addListeners ()
        {
            this.addEventListener(MouseEvent.CLICK, this.mouseClickHandler, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.mouseOverHandler, false, 0, true);
            if (this.parent && this.parent.get("constructor") != this.get("constructor"))
            {
                Global.stage.addEventListener(MouseEvent.CLICK, this.stageMouseClickHandler, false, 0, true);
                Global.stage.addEventListener(MouseEvent.MOUSE_OUT, this.stageMouseMoveHandler, false, 0, true);
            }
            return;
        }//end

        protected void  removeListeners ()
        {
            this.removeEventListener(MouseEvent.CLICK, this.mouseClickHandler);
            this.removeEventListener(MouseEvent.MOUSE_OVER, this.mouseOverHandler);
            if (this.parent && this.parent.get("constructor") != this.get("constructor"))
            {
                this.stage.removeEventListener(MouseEvent.CLICK, this.stageMouseClickHandler);
            }
            return;
        }//end

    }




