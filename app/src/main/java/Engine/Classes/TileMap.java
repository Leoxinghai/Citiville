package Engine.Classes;

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
import Engine.Helpers.*;
import Engine.Interfaces.*;
//import flash.display.*;
//import flash.geom.*;

    public class TileMap
    {
        protected int m_width ;
        protected int m_height ;
        protected Object m_drawingTarget ;
        protected Sprite m_lotOutline ;
        protected ITerrainMap m_terrain ;
        protected int m_originX =0;
        protected int m_originY =0;
        protected double m_oldZoom =0;
        protected BitmapData m_bitmapData =null ;
        protected boolean m_useFast =true ;
public static  double LOT_OUTLINE_THICKNESS =1;
public static  double LOT_OUTLINE_BORDER_THICKNESS =1;
public static int m_fastXOffset ;
public static int m_fastYOffset ;

        public void  TileMap (int param1 ,int param2 )
        {
            this.m_lotOutline = new Sprite();
            this.m_width = param1;
            this.m_height = param2;
            this.m_drawingTarget = GlobalEngine.viewport;
            this.m_drawingTarget.graphics.clear();
            while (this.m_drawingTarget.numChildren > 0)
            {

                this.m_drawingTarget.removeChildAt(0);
            }
            this.generateLotOutline();
            this.m_lotOutline.visible = false;
            this.m_drawingTarget.addChild(this.m_lotOutline);
            _loc_3 = IsoMath.tilePosToPixelPos(0,0,0,true );
            this.m_originX = _loc_3.x - Constants.TILE_WIDTH / 2;
            this.m_originY = _loc_3.y - Constants.TILE_HEIGHT;
            m_fastXOffset = Constants.TILE_WIDTH / 2 / Constants.TILE_SCALE;
            m_fastYOffset = Constants.TILE_HEIGHT / 2 / Constants.TILE_SCALE;
            return;
        }//end

        public void  setTerrain (ITerrainMap param1 )
        {
            this.m_terrain = param1;
            return;
        }//end

        public ITerrainMap  getTerrain ()
        {
            return this.m_terrain;
        }//end

        protected boolean  useFastSystem ()
        {
            return this.m_useFast;
        }//end

        public void  render (Viewport param1 )
        {
            target = param1;
            if (this.m_terrain)
            {
                if (this.useFastSystem())
                {
                    try
                    {
                        this.renderFast(target);
                    }
                    catch (err:Error)
                    {
                        m_useFast = false;
                        renderLowMem(target);
                    }
                }
                else
                {
                    this.renderLowMem(target);
                }
            }
            return;
        }//end

        protected void  renderFast (Viewport param1 )
        {
            Tile _loc_3 =null ;
            BitmapData _loc_4 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            _loc_2 = GlobalEngine.viewport.getZoom ();
            Point _loc_5 =new Point(0,0);
            if (this.m_oldZoom != _loc_2 || this.m_terrain.isDirty())
            {
                _loc_6 = (this.m_width + this.m_height) * m_fastXOffset * _loc_2;
                _loc_7 = (this.m_width + this.m_height) * m_fastYOffset * _loc_2;
                if (this.m_bitmapData)
                {
                    this.m_bitmapData.dispose();
                }
                this.m_bitmapData = new BitmapData(_loc_6, _loc_7, false, Config.VIEWPORT_CLEAR_COLOR);
                _loc_7 = _loc_7 - Constants.TILE_HEIGHT * _loc_2;
                _loc_6 = _loc_6 - Constants.TILE_WIDTH * _loc_2;
                _loc_8 = 0;
                while (_loc_8 < this.m_height)
                {

                    _loc_5.x = _loc_6 / 2 - _loc_8 * m_fastXOffset * _loc_2;
                    _loc_5.y = _loc_7 - _loc_8 * m_fastYOffset * _loc_2;
                    _loc_9 = 0;
                    while (_loc_9 < this.m_width)
                    {

                        _loc_3 =(Tile) this.m_terrain.tileAt(_loc_9, _loc_8);
                        if (_loc_3)
                        {
                            _loc_4 = _loc_3.getBitmapData();
                            this.m_bitmapData.copyPixels(_loc_4, _loc_4.rect, _loc_5, null, null, true);
                        }
                        else
                        {
                            GlobalEngine.log("TileMap", "TileMap tile not found");
                        }
                        _loc_5.x = _loc_5.x + m_fastXOffset * _loc_2;
                        _loc_5.y = _loc_5.y - m_fastYOffset * _loc_2;
                        _loc_9++;
                    }
                    _loc_8++;
                }
                this.m_terrain.cleanDirty();
            }
            param1.copyPixels(this.m_bitmapData, this.m_bitmapData.rect, new Point(0, 0), null, null, true);
            this.m_oldZoom = _loc_2;
            return;
        }//end

        protected void  renderLowMem (Viewport param1 )
        {
            Tile _loc_3 =null ;
            BitmapData _loc_4 =null ;
            int _loc_13 =0;
            _loc_2 = GlobalEngine.viewport.getZoom ();
            Point _loc_5 =new Point(0,0);
            if (this.m_bitmapData)
            {
                this.m_bitmapData.dispose();
                this.m_bitmapData = null;
            }
            _loc_6 = param1.getBitmapData ().width ;
            _loc_7 = param1.getBitmapData ().height ;
            _loc_8 = IsoMath.screenPosToTilePos(_loc_6 ,_loc_7 ).y ;
            _loc_8 = IsoMath.screenPosToTilePos(_loc_6, _loc_7).y < 0 ? (0) : (_loc_8);
            _loc_9 = IsoMath.screenPosToTilePos(0,_loc_7 ).x ;
            _loc_9 = IsoMath.screenPosToTilePos(0, _loc_7).x < 0 ? (0) : (_loc_9);
            _loc_10 = IsoMath.screenPosToTilePos(0,0).y ;
            _loc_10 = IsoMath.screenPosToTilePos(0, 0).y > this.m_height ? (this.m_height) : (_loc_10);
            _loc_11 = IsoMath.screenPosToTilePos(_loc_6 ,0).x ;
            _loc_11 = IsoMath.screenPosToTilePos(_loc_6, 0).x > this.m_width ? (this.m_width) : (_loc_11);
            _loc_12 = _loc_8;
            while (_loc_12 < _loc_10)
            {

                _loc_5.x = this.m_originX - (_loc_12 - _loc_9) * m_fastXOffset;
                _loc_5.y = this.m_originY - (_loc_12 + _loc_9) * m_fastYOffset;
                _loc_13 = _loc_9;
                while (_loc_13 < _loc_11)
                {

                    _loc_3 =(Tile) this.m_terrain.tileAt(_loc_13, _loc_12);
                    if (_loc_3)
                    {
                        _loc_4 = _loc_3.getBitmapData();
                        param1.copyPixels(_loc_4, _loc_4.rect, _loc_5, null, null, true);
                    }
                    else
                    {
                        GlobalEngine.log("TileMap", "TileMap tile not found");
                    }
                    _loc_5.x = _loc_5.x + m_fastXOffset;
                    _loc_5.y = _loc_5.y - m_fastYOffset;
                    _loc_13++;
                }
                _loc_12++;
            }
            this.m_terrain.cleanDirty();
            this.m_oldZoom = _loc_2;
            return;
        }//end

        public Array  getReferencedAssets ()
        {
            int _loc_3 =0;
            Tile _loc_4 =null ;
            Array _loc_1 =new Array();
            int _loc_2 =0;
            while (_loc_2 < this.m_height)
            {

                _loc_3 = 0;
                while (_loc_3 < this.m_width)
                {

                    _loc_4 = this.m_terrain.tileAt(_loc_3, _loc_2);
                    if (_loc_4 != null)
                    {
                        if (_loc_1.indexOf(_loc_4) == -1)
                        {
                            _loc_1.push(_loc_4);
                        }
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        public void  setLotOutlineVisible (boolean param1 )
        {
            this.m_lotOutline.visible = param1;
            return;
        }//end

        public boolean  isLotOutlineVisible ()
        {
            return this.m_lotOutline.visible;
        }//end

        private void  generateLotOutline ()
        {
            _loc_1 = IsoMath.tilePosToPixelPos(0,0,0,true );
            _loc_2 = IsoMath.getPixelDeltaFromTileDelta(this.m_width ,0,true );
            _loc_3 = IsoMath.getPixelDeltaFromTileDelta(0,this.m_height ,true );
            this.m_lotOutline.graphics.clear();
            this.m_lotOutline.graphics.moveTo(_loc_1.x, _loc_1.y);
            this.m_lotOutline.graphics.lineStyle(TileMap.LOT_OUTLINE_THICKNESS + 2 * TileMap.LOT_OUTLINE_BORDER_THICKNESS, Constants.COLOR_LOT_OUTLINE_BORDER, Constants.ALPHA_LOT_OUTLINE_BORDER);
            drawTileArea(this.m_lotOutline, _loc_1, _loc_2, _loc_3);
            this.m_lotOutline.graphics.lineStyle(0, 0);
            this.m_lotOutline.graphics.moveTo(_loc_1.x, _loc_1.y);
            this.m_lotOutline.graphics.lineStyle(TileMap.LOT_OUTLINE_THICKNESS, Constants.COLOR_LOT_OUTLINE, Constants.ALPHA_LOT_OUTLINE);
            drawTileArea(this.m_lotOutline, _loc_1, _loc_2, _loc_3);
            this.m_lotOutline.graphics.lineStyle(0, 0);
            return;
        }//end

        public void  floodFill (int param1 ,int param2 ,Tile param3 )
        {
            IntVector2 _loc_8 =null ;
            int _loc_9 =0;
            Array _loc_4 =new Array ();
            _loc_5 = this.getTileForPosition(param1 ,param2 );
            boolean _loc_6 =false ;
            boolean _loc_7 =true ;
            if (_loc_5 != param3)
            {
                _loc_4.push(new IntVector2(param1, param2));
                while (_loc_4.length())
                {

                    _loc_8 =(IntVector2) _loc_4.pop();
                    _loc_9 = _loc_8.y;
                    while (this.getTileForPosition(_loc_8.x, _loc_9) == _loc_5)
                    {

                        _loc_9 = _loc_9 - 1;
                    }
                    _loc_9++;
                    _loc_7 = false;
                    _loc_6 = false;
                    while (this.getTileForPosition(_loc_8.x, _loc_9) == _loc_5)
                    {

                        this.m_terrain.setTile(_loc_8.x, _loc_9, param3);
                        if (!_loc_6 && this.getTileForPosition((_loc_8.x - 1), _loc_9) == _loc_5)
                        {
                            _loc_4.push(new IntVector2((_loc_8.x - 1), _loc_9));
                            _loc_6 = true;
                        }
                        else if (_loc_6 && this.getTileForPosition((_loc_8.x - 1), _loc_9) != _loc_5)
                        {
                            _loc_6 = false;
                        }
                        if (!_loc_7 && this.getTileForPosition((_loc_8.x + 1), _loc_9) == _loc_5)
                        {
                            _loc_4.push(new IntVector2((_loc_8.x + 1), _loc_9));
                            _loc_7 = true;
                        }
                        else if (_loc_7 && this.getTileForPosition((_loc_8.x + 1), _loc_9) != _loc_5)
                        {
                            _loc_7 = false;
                        }
                        _loc_9++;
                    }
                }
                this.fullyRedrawTileMap();
            }
            return;
        }//end

        public int  getWidth ()
        {
            return this.m_width;
        }//end

        public int  getHeight ()
        {
            return this.m_height;
        }//end

        public Tile  getTileForPosition (int param1 ,int param2 )
        {
            Tile _loc_3 =null ;
            if (param1 >= 0 && param1 < this.m_width && param2 >= 0 && param2 < this.m_height)
            {
                _loc_3 = this.m_terrain.tileAt(param1, param2);
            }
            return _loc_3;
        }//end

        public void  fullyRedrawTileMap ()
        {
            this.m_drawingTarget.graphics.clear();
            this.generateLotOutline();
            return;
        }//end

        public static void  drawTileArea (Object param1 ,Point param2 ,Point param3 ,Point param4 )
        {
            param1.graphics.moveTo(param2.x, param2.y);
            param1.graphics.lineTo(param2.x + param3.x, param2.y + param3.y);
            param1.graphics.lineTo(param2.x + param3.x + param4.x, param2.y + param3.y + param4.y);
            param1.graphics.lineTo(param2.x + param4.x, param2.y + param4.y);
            param1.graphics.lineTo(param2.x, param2.y);
            return;
        }//end

    }


