package Display.hud;

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
import com.xiyu.util.Event;

import Classes.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Events.*;
import GameMode.*;
import root.Global;
import root.ZLoc;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;

    public class BuildModeMenu extends MovieClip
    {
        public  int BTN_UP =1;
        public  int BTN_OVER =2;
        public  int BTN_SET =3;
        public SimpleButton closeButton ;
        public MovieClip giftsButton ;
        public MovieClip housesButton ;
        public MovieClip leisureButton ;
        public MovieClip factoriesButton ;
        public MovieClip terrainButton ;
        public MovieClip roadsButton ;
        public TextField buildText ;
        protected Object m_callbacks ;

        public  BuildModeMenu ()
        {
            this.m_callbacks = {giftsButton:this.onGiftsClick, roadsButton:this.onRoadClick, housesButton:this.makeCatalogClickHandler("residence"), leisureButton:this.makeCatalogClickHandler("business"), factoriesButton:this.makeCatalogClickHandler("factory"), terrainButton:this.makeCatalogClickHandler("decoration")};
            addEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            return;
        }//end  

        private void  onAddedToStage (Event event )
        {
            removeEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            addEventListener(Event.ENTER_FRAME, this.onFrame);
            MovieClipUtil.playAndStop(this, 1, 34, true, this.onMenuReadyCallback);
            return;
        }//end  

        private void  onMenuReadyCallback ()
        {
            removeEventListener(Event.ENTER_FRAME, this.onFrame);
            this.addListeners();
            this.clearAllButtons();
            EmbeddedArt.updateFont(this.buildText, true);
            this.buildText.text = ZLoc.t("Main", "BuildMenuLabel");
            return;
        }//end  

        private void  onCloseButtonClick (MouseEvent event )
        {
            event = event;
            this.removeListeners();
            MovieClipUtil .playAndStop (this ,35,-1,false ,void  ()
            {
                Global.ui.showBuildMenu(false);
                return;
            }//end  
            );
            return;
        }//end  

        private void  onFrame (Event event )
        {
            String _loc_2 =null ;
            MovieClip _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_callbacks.size(); i0++) 
            {
            	_loc_2 = this.m_callbacks.get(i0);
                
                _loc_3 =(MovieClip) this.get(_loc_2);
                if (_loc_3 != null && _loc_3.currentFrame != 1)
                {
                    _loc_3.gotoAndStop(1);
                }
            }
            return;
        }//end  

        private void  addListeners ()
        {
            String _loc_1 =null ;
            MovieClip _loc_2 =null ;
            ToolTip _loc_3 =null ;
            this.addEventListener(MouseEvent.MOUSE_MOVE, this.mouseEventKiller);
            this.addEventListener(MouseEvent.MOUSE_DOWN, this.mouseEventKiller);
            this.addEventListener(MouseEvent.MOUSE_UP, this.mouseEventKiller);
            this.addEventListener(MouseEvent.CLICK, this.mouseEventKiller);
            this.closeButton.addEventListener(MouseEvent.CLICK, this.onCloseButtonClick);
            for(int i0 = 0; i0 < this.m_callbacks.size(); i0++) 
            {
            	_loc_1 = this.m_callbacks.get(i0);
                
                _loc_2 =(MovieClip) this.get(_loc_1);
                _loc_3 = new ToolTip();
                _loc_3.toolTip = ZLoc.t("Main", "BuildMenu_" + _loc_1);
                _loc_3.attachToolTip(_loc_2);
                _loc_2.addEventListener(MouseEvent.CLICK, this.m_callbacks.get(_loc_1));
                _loc_2.addEventListener(MouseEvent.MOUSE_OVER, this.onButtonMouseOver);
                _loc_2.addEventListener(MouseEvent.MOUSE_OUT, this.onButtonMouseOut);
                _loc_2.addEventListener(MouseEvent.MOUSE_DOWN, this.onButtonMouseDown);
            }
            return;
        }//end  

        private void  removeListeners ()
        {
            String _loc_1 =null ;
            MovieClip _loc_2 =null ;
            this.removeEventListener(MouseEvent.MOUSE_MOVE, this.mouseEventKiller);
            this.removeEventListener(MouseEvent.MOUSE_UP, this.mouseEventKiller);
            this.removeEventListener(MouseEvent.MOUSE_DOWN, this.mouseEventKiller);
            this.removeEventListener(MouseEvent.CLICK, this.mouseEventKiller);
            this.closeButton.removeEventListener(MouseEvent.CLICK, this.onCloseButtonClick);
            for(int i0 = 0; i0 < this.m_callbacks.size(); i0++) 
            {
            	_loc_1 = this.m_callbacks.get(i0);
                
                _loc_2 =(MovieClip) this.get(_loc_1);
                _loc_2.removeEventListener(MouseEvent.CLICK, this.m_callbacks.get(_loc_1));
                _loc_2.removeEventListener(MouseEvent.MOUSE_OVER, this.onButtonMouseOver);
                _loc_2.removeEventListener(MouseEvent.MOUSE_OUT, this.onButtonMouseOut);
                _loc_2.removeEventListener(MouseEvent.MOUSE_DOWN, this.onButtonMouseDown);
            }
            return;
        }//end  

        private void  onButtonMouseOut (MouseEvent event )
        {
            _loc_2 = (MovieClip)event.currentTarget
            if (_loc_2.currentFrame != this.BTN_SET)
            {
                _loc_2.gotoAndStop(this.BTN_UP);
            }
            return;
        }//end  

        private void  onButtonMouseOver (MouseEvent event )
        {
            _loc_2 = (MovieClip)event.currentTarget;
            if (_loc_2.currentFrame != this.BTN_SET)
            {
                _loc_2.gotoAndStop(this.BTN_OVER);
            }
            return;
        }//end  

        private void  onButtonMouseDown (MouseEvent event )
        {
            this.clearAllButtons();
            (event.currentTarget as MovieClip).gotoAndStop(this.BTN_SET);
            return;
        }//end  

        private void  clearAllButtons ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_callbacks.size(); i0++) 
            {
            	_loc_1 = this.m_callbacks.get(i0);
                
                (this.get(_loc_1) as MovieClip).gotoAndStop(this.BTN_UP);
            }
            return;
        }//end  

        private void  mouseEventKiller (MouseEvent event )
        {
            event.stopImmediatePropagation();
            return;
        }//end  

        private void  onGiftsClick (MouseEvent event )
        {
            this.clearAllButtons();
            UI.closeCatalog();
            return;
        }//end  

        private void  onRoadClick (MouseEvent event )
        {
            this.clearAllButtons();
            UI.closeCatalog();
            Global.world.addGameMode(new GMPlaceMapResource("road", Road, false));
            return;
        }//end  

        private Function  makeCatalogClickHandler (String param1 )
        {
            type = param1;
            return void  (MouseEvent event )
            {
                clearAllButtons();
                (event.currentTarget as MovieClip).gotoAndStop(BTN_SET);
                _loc_2 = UI.displayCatalog(newCatalogParams(type));
                _loc_2.addEventListener(CloseEvent.CLOSE, onCatalogClose);
                return;
            }//end  
            ;
        }//end  

        private void  onCatalogClose (Event event )
        {
            _loc_2 = (Catalog)event.currentTarget;
            _loc_2.removeEventListener(CloseEvent.CLOSE, this.onCatalogClose);
            this.clearAllButtons();
            return;
        }//end  

    }




