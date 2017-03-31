package Modules.guide.actions;

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
import Engine.Classes.*;
import Engine.Helpers.*;
import com.greensock.*;
import GameMode.*;

    public class GAPanAndFollow extends GuideAction
    {
        protected double m_originalZoom =-1;
        protected int m_mode ;
        protected int m_offsetX =0;
        protected int m_offsetY =0;
        public static double SCROLL_PER_SECOND_DEFAULT =200;
        public static double SCROLL_PER_SECOND_SLOW =5;
        public static double MAX_SCROLL_TIME =2;
        public static double MIN_SCROLL_TIME =0.4;
        public static  int MODE_NPC_HOTEL_GOING_TO_CHECK_IN =0;
        public static  int MODE_NPC_HOTEL_UNABLE_TO_CHECK_IN =1;
        public static  int MODE_NPC_HOTEL_SPEND_MONEY =2;
        public static boolean isPanning =false ;

        public  GAPanAndFollow ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1 ,"location").get(0) ;
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = _loc_2.@mode;
            switch(_loc_3)
            {
                case "npc_hotel_check_in":
                {
                    break;
                }
                case "npc_hotel_unable_check_in":
                {
                    break;
                }
                case "npc_hotel_spend_money":
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            GameObject _loc_2 =null ;
            Vector3 _loc_3 =null ;
            super.update(param1);
            this.m_originalZoom = -1;
            switch(this.m_mode)
            {
                case MODE_NPC_HOTEL_GOING_TO_CHECK_IN:
                {
                    _loc_2 = Global.world.citySim.resortManager.getFocusNPC();
                    if (!_loc_2)
                    {
                        break;
                    }
                    _loc_3 = _loc_2.getPositionNoClone();
                    this.panTo(_loc_3.x, _loc_3.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_DEFAULT);
                    this.zoomIn();
                    break;
                }
                case MODE_NPC_HOTEL_SPEND_MONEY:
                {
                    _loc_2 = Global.world.citySim.resortManager.getFocusNPC();
                    if (!_loc_2)
                    {
                        break;
                    }
                    _loc_3 = _loc_2.getPositionNoClone();
                    this.panTo(_loc_3.x, _loc_3.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_DEFAULT);
                    this.zoomIn();
                    break;
                }
                default:
                {
                    _loc_2 = Global.world.citySim.resortManager.getFocusNPC();
                    if (!_loc_2)
                    {
                        break;
                    }
                    _loc_3 = _loc_2.getPositionNoClone();
                    this.panTo(_loc_3.x, _loc_3.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_DEFAULT);
                    this.zoomIn();
                    break;
                    break;
                }
            }
            removeState(this);
            return;
        }//end

        protected void  zoomIn ()
        {
            double _loc_1 =0;
            if (Global.world.isZoomEnabled())
            {
                this.m_originalZoom = GlobalEngine.viewport.getZoom();
                _loc_1 = GlobalEngine.viewport.getZoom();
                _loc_1 = _loc_1 + Global.gameSettings().getNumber("zoomStep");
                _loc_1 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_1);
                _loc_1 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_1);
                if (GlobalEngine.viewport.getZoom() == _loc_1)
                {
                    return;
                }
                GlobalEngine.viewport.setZoom(_loc_1);
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_IN);
            }
            return;
        }//end

        protected void  panTo (int param1 ,int param2 ,int param3 ,int param4 ,double param5 )
        {
            _loc_6 = GlobalEngine.viewport.getScrollPosition();
            Object _loc_7 ={startPos _loc_6 ,alpha 0};
            _loc_8 = (IsoViewport)GlobalEngine.viewport
            _loc_8.centerOnTilePos(param1 + param3, param2 + param4);
            _loc_7.endPos = _loc_8.getScrollPosition();
            _loc_8.setScrollPosition(_loc_7.startPos);
            _loc_9 = _loc_6.subtract(_loc_7.endPos).length;
            _loc_10 = _loc_6.subtract(_loc_7.endPos).length/param5;
            _loc_10 = Math.max(_loc_10, MIN_SCROLL_TIME);
            _loc_10 = Math.min(_loc_10, MAX_SCROLL_TIME);
            isPanning = true;
            TweenLite.to(_loc_7, _loc_10, {alpha:1, onUpdate:this.onUpdateScrollTween, onUpdateParams:.get(_loc_7), onComplete:this.finishedPanning});
            return;
        }//end

        protected void  panToFollow (int param1 ,int param2 ,int param3 ,int param4 ,double param5 )
        {
            _loc_6 = GlobalEngine.viewport.getScrollPosition();
            Object _loc_7 ={startPos _loc_6 ,alpha 0};
            _loc_8 = (IsoViewport)GlobalEngine.viewport
            _loc_8.centerOnTilePos(param1 + param3, param2 + param4);
            _loc_7.endPos = _loc_8.getScrollPosition();
            _loc_8.setScrollPosition(_loc_7.startPos);
            _loc_9 = _loc_6.subtract(_loc_7.endPos).length;
            _loc_10 = _loc_6.subtract(_loc_7.endPos).length/param5;
            _loc_10 = Math.max(_loc_10, MIN_SCROLL_TIME);
            _loc_10 = Math.min(_loc_10, MAX_SCROLL_TIME);
            isPanning = true;
            TweenLite.to(_loc_7, _loc_10, {alpha:1, onUpdate:this.onUpdateScrollTween, onUpdateParams:.get(_loc_7), onComplete:this.finishedPanAndFollow});
            return;
        }//end

        protected void  onUpdateScrollTween (Object param1 )
        {
            Vector2 _loc_2 =Vector2.lerp(param1.startPos ,param1.endPos ,param1.alpha );
            //_loc_2 = GameMode.limitBackgroundScrolling(_loc_2);
            GlobalEngine.viewport.setScrollPosition(_loc_2);
            Global.world.updateWorldObjectCulling(GameWorld.CULL_CHECK_ALL);
            if (Global.world.getTopGameMode() instanceof GMObjectMove)
            {
                (Global.world.getTopGameMode() as GMObjectMove).onMouseMove(null);
            }
            return;
        }//end

        protected void  finishedPanning ()
        {
            isPanning = false;
            return;
        }//end

        protected void  finishedPanAndFollow ()
        {
            GameObject _loc_1 =null ;
            Vector3 _loc_2 =null ;
            isPanning = false;
            if (Global.world.isZoomEnabled() && this.m_originalZoom != -1)
            {
                GlobalEngine.viewport.setZoom(this.m_originalZoom);
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_OUT);
            }
            if (this.m_mode == MODE_NPC_HOTEL_GOING_TO_CHECK_IN)
            {
                _loc_1 = Global.world.citySim.resortManager.getFocusNPC();
                if (_loc_1)
                {
                    _loc_2 = _loc_1.getPositionNoClone();
                    this.panToFollow(_loc_2.x, _loc_2.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_SLOW);
                    this.zoomIn();
                }
            }
            else if (this.m_mode == MODE_NPC_HOTEL_UNABLE_TO_CHECK_IN)
            {
                _loc_1 = Global.world.citySim.resortManager.getFocusNPC();
                if (_loc_1)
                {
                    _loc_2 = _loc_1.getPositionNoClone();
                    this.panToFollow(_loc_2.x, _loc_2.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_SLOW);
                    this.zoomIn();
                }
            }
            else if (this.m_mode == MODE_NPC_HOTEL_SPEND_MONEY)
            {
                _loc_1 = Global.world.citySim.resortManager.getFocusNPC();
                if (_loc_1)
                {
                    _loc_2 = _loc_1.getPositionNoClone();
                    this.panToFollow(_loc_2.x, _loc_2.y, this.m_offsetX, this.m_offsetY, SCROLL_PER_SECOND_SLOW);
                    this.zoomIn();
                }
            }
            return;
        }//end

    }



