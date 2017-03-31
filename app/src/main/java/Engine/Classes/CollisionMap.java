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
import com.adobe.utils.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;
import com.xinghai.Debug;
import Classes.*;



    public class CollisionMap
    {
        protected int m_width ;
        protected int m_height ;
        protected Array m_cells =null ;
        protected Sprite m_visualCollisionMap ;
        private Dictionary m_areaObjectCache =null ;
        private int m_areaObjectCacheSize ;
        protected Pathfinder m_pathfinder ;
        protected int m_xStart ;
        protected int m_xEnd ;
        protected int m_yStart ;
        protected int m_yEnd ;
        public static  int COLLISIONMAP_COLOR =16729156;
        public static  double COLLISIONMAP_ALPHA =0.6;
        private static Array m_ignoreObjectsInCollision ;
        private static  int MAX_CACHE_SIZE =50;

        public  CollisionMap ()
        {
            if (m_ignoreObjectsInCollision == null)
            {
                m_ignoreObjectsInCollision = new Array();
            }
            return;
        }//end

        public void  dispose ()
        {
            int _loc_2 =0;
            int _loc_1 =this.m_xStart ;
            while (_loc_1 < this.m_xEnd)
            {

                _loc_2 = this.m_yStart;
                while (_loc_2 < this.m_yEnd)
                {

                    (this.m_cells.get(_loc_1).get(_loc_2) as CollisionCell).dispose();
                    this.m_cells.get(_loc_1).put(_loc_2,  null);
                    _loc_2++;
                }
                _loc_1++;
            }
            return;
        }//end

        public void  initialize (int param1 ,int param2 ,boolean param3 =true )
        {
            this.initRect(0, 0, param1, param2, param3);
            return;
        }//end

        public void  initRect (int param1 ,int param2 ,int param3 ,int param4 ,boolean param5 =true )
        {
            int _loc_7 =0;
            this.m_xStart = param1;
            this.m_yStart = param2;
            this.m_xEnd = param1 + param3;
            this.m_yEnd = param2 + param4;
            this.m_width = param3;
            this.m_height = param4;
            this.m_cells = new Array(param3);
            int _loc_6 =this.m_xStart ;
            while (_loc_6 < this.m_xEnd)
            {

                this.m_cells.put(_loc_6,  new Array(param4));
                _loc_7 = this.m_yStart;
                while (_loc_7 < this.m_yEnd)
                {

                    this.m_cells.get(_loc_6).put(_loc_7,  new CollisionCell(_loc_6, _loc_7));
                    _loc_7++;
                }
                _loc_6++;
            }
            if (param5)
            {
                this.m_pathfinder = new Pathfinder(this.m_width, this.m_height, this);
            }
            return;
        }//end

        public void  insertObject (WorldObject param1 )
        {
            Vector3 _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            int _loc_13 =0;
            int _loc_14 =0;
            CollisionCell _loc_15 =null ;
            Array _loc_16 =null ;

            if (param1.isCollidable())
            {
                _loc_2 = param1.getSizeNoClone();
                _loc_3 = Constants.DIRECTION_NE;
                _loc_4 = Constants.DIRECTION_NW;
                _loc_5 = Constants.DIRECTION_SE;
                _loc_6 = Constants.DIRECTION_SW;
                _loc_7 = param1.positionX;
                _loc_8 = param1.positionY;
                _loc_9 = _loc_7 + _loc_2.x;
                _loc_10 = _loc_8 + _loc_2.y;
                _loc_11 = _loc_2.x;
                _loc_12 = _loc_2.y;
                _loc_9 = _loc_9 > this.m_xEnd ? (this.m_xEnd) : (_loc_9 < this.m_xStart ? (this.m_xStart) : (_loc_9));
                _loc_10 = _loc_10 > this.m_yEnd ? (this.m_yEnd) : (_loc_10 < this.m_yStart ? (this.m_yStart) : (_loc_10));
                _loc_7 = _loc_7 > this.m_xEnd ? (this.m_xEnd) : (_loc_7 < this.m_xStart ? (this.m_xStart) : (_loc_7));
                _loc_8 = _loc_8 > this.m_yEnd ? (this.m_yEnd) : (_loc_8 < this.m_yStart ? (this.m_yStart) : (_loc_8));
                if (_loc_11 > 0 && _loc_12 > 0)
                {
                    _loc_13 = _loc_7;
                    while (_loc_13 < _loc_9)
                    {

                        _loc_16 = this.m_cells.get(_loc_13);
                        _loc_14 = _loc_8;
                        while (_loc_14 < _loc_10)
                        {

                            _loc_15 = _loc_16.get(_loc_14);
                            _loc_15.addObject(param1);
                            this.m_areaObjectCache = null;
                            if (_loc_13 > this.m_xStart)
                            {
                                _loc_15.setWall(_loc_6, param1);
                                CollisionCell(this.m_cells.get((_loc_13 - 1)).get(_loc_14)).setWall(_loc_3, param1);
                            }
                            if (_loc_14 > this.m_yStart)
                            {
                                _loc_15.setWall(_loc_5, param1);
                                CollisionCell(_loc_16.get((_loc_14 - 1))).setWall(_loc_4, param1);
                            }
                            _loc_14++;
                        }
                        _loc_13++;
                    }
                }
                else
                {
                    _loc_13 = _loc_7;
                    while (_loc_13 <= _loc_9)
                    {

                        _loc_16 = this.m_cells.get(_loc_13);
                        _loc_14 = _loc_8;
                        while (_loc_14 <= _loc_10)
                        {

                            if (_loc_14 < _loc_10 && _loc_11 == 0)
                            {
                                CollisionCell(_loc_16.get(_loc_14)).setWall(_loc_6, param1);
                                if (_loc_13 > this.m_xStart)
                                {
                                    CollisionCell(this.m_cells.get((_loc_13 - 1)).get(_loc_14)).setWall(_loc_3, param1);
                                }
                            }
                            if (_loc_13 < _loc_9 && _loc_12 == 0)
                            {
                                CollisionCell(_loc_16.get(_loc_14)).setWall(_loc_5, param1);
                                if (_loc_14 > this.m_yStart)
                                {
                                    CollisionCell(_loc_16.get((_loc_14 - 1))).setWall(_loc_4, param1);
                                }
                            }
                            _loc_14++;
                        }
                        _loc_13++;
                    }
                }
                if (this.m_pathfinder != null)
                {
                    this.m_pathfinder.setCollisionMapDirty(_loc_7, _loc_9, _loc_8, _loc_10);
                }
            }
            if (this.m_visualCollisionMap)
            {
                this.drawCollisionMap(this.m_visualCollisionMap);
            }
            return;
        }//end

        public void  setTerrain (ITerrainMap param1 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            Array _loc_9 =null ;
            int _loc_10 =0;
            _loc_2 = this.m_width /param1.width ;
            _loc_3 = this.m_height /param1.height ;
            int _loc_4 =0;
            while (_loc_4 < param1.width)
            {

                _loc_5 = 0;
                while (_loc_5 < param1.height)
                {

                    if (param1.isPathable(_loc_4, _loc_5))
                    {
                        _loc_6 = _loc_4 * _loc_2;
                        _loc_7 = _loc_5 * _loc_3;
                        _loc_8 = 0;
                        while (_loc_8 < _loc_2)
                        {

                            _loc_9 = this.m_cells.get(_loc_6 + _loc_8);
                            _loc_10 = 0;
                            while (_loc_10 < _loc_3)
                            {

                                CollisionCell(_loc_9.get(_loc_7 + _loc_10)).isPathable = true;
                                if (this.m_pathfinder != null)
                                {
                                    this.m_pathfinder.setPathable(_loc_6 + _loc_8, _loc_7 + _loc_10);
                                }
                                _loc_10++;
                            }
                            _loc_8++;
                        }
                    }
                    _loc_5++;
                }
                _loc_4++;
            }
            return;
        }//end

        public boolean  isPositionPathable (int param1 ,int param2 )
        {
            return CollisionCell(this.m_cells.get(param1).get(param2)).isPathable;
        }//end

        public boolean  isValidCellPos (int param1 ,int param2 )
        {
            return param1 >= this.m_xStart && param1 < this.m_xEnd && param2 >= this.m_yStart && param2 < this.m_yEnd;
        }//end

        public void  removeObject (WorldObject param1 )
        {
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            double _loc_4 =0;
            double _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            int _loc_8 =0;
            Array _loc_9 =null ;
            int _loc_10 =0;
            if (param1.isCollidable() && param1.isAttached())
            {
                _loc_2 = param1.getAttachSizeNoClone();
                _loc_3 = param1.getAttachPositionNoClone();
                _loc_4 = _loc_3.x - 1;
                _loc_5 = _loc_3.y - 1;
                _loc_6 = _loc_3.x + _loc_2.x + 1;
                _loc_7 = _loc_3.y + _loc_2.y + 1;
                _loc_6 = _loc_6 > this.m_xEnd ? (this.m_xEnd) : (_loc_6 < this.m_xStart ? (this.m_xStart) : (_loc_6));
                _loc_7 = _loc_7 > this.m_yEnd ? (this.m_yEnd) : (_loc_7 < this.m_yStart ? (this.m_yStart) : (_loc_7));
                _loc_4 = _loc_4 > this.m_xEnd ? (this.m_xEnd) : (_loc_4 < this.m_xStart ? (this.m_xStart) : (_loc_4));
                _loc_5 = _loc_5 > this.m_yEnd ? (this.m_yEnd) : (_loc_5 < this.m_yStart ? (this.m_yStart) : (_loc_5));
                _loc_8 = _loc_4;
                while (_loc_8 < _loc_6)
                {

                    _loc_9 = this.m_cells.get(_loc_8);
                    _loc_10 = _loc_5;
                    while (_loc_10 < _loc_7)
                    {

                        CollisionCell(_loc_9.get(_loc_10)).removeObject(param1);
                        this.m_areaObjectCache = null;
                        _loc_10++;
                    }
                    _loc_8++;
                }
                if (this.m_pathfinder != null)
                {
                    this.m_pathfinder.setCollisionMapDirty(_loc_4, _loc_6, _loc_5, _loc_7);
                }
            }
            if (this.m_visualCollisionMap)
            {
                this.drawCollisionMap(this.m_visualCollisionMap);
            }
            return;
        }//end

        public void  ignoreObjectForSession (WorldObject param1 )
        {
            CollisionMap.m_ignoreObjectsInCollision.push(param1);
            return;
        }//end

        public boolean  checkCollision (CollisionLookup param1 ,WorldObject param2 )
        {
            boolean _loc_3 =false ;
            if (param1.isLine())
            {
                _loc_3 = this.checkCollisionForLine(param1);
            }
            else
            {
                _loc_3 = this.checkCollisionForBox(param1, param2);
            }
            return _loc_3;
        }//end

        public Array  getObjectsInRect (int param1 ,int param2 ,int param3 ,int param4 )
        {
            Array _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            Array _loc_9 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            WorldObject _loc_12 =null ;
            int _loc_13 =0;
            int _loc_14 =0;
            if (param1 < this.m_xStart)
            {
                param1 = this.m_xStart;
            }
            if (param2 < this.m_yStart)
            {
                param2 = this.m_yStart;
            }
            if (param3 > this.m_xEnd)
            {
                param3 = this.m_xEnd;
            }
            if (param4 > this.m_yEnd)
            {
                param4 = this.m_yEnd;
            }
            _loc_5 = String(param1)+":"+String(param2)+":"+String(param3)+":"+String(param4);
            if (this.m_areaObjectCache == null)
            {
                this.m_areaObjectCache = new Dictionary();
                this.m_areaObjectCacheSize = 0;
            }
            if (this.m_areaObjectCache.get(_loc_5))
            {
                _loc_6 = this.m_areaObjectCache.get(_loc_5);
            }
            else
            {
                if (this.m_areaObjectCacheSize == MAX_CACHE_SIZE)
                {
                    this.m_areaObjectCache = new Dictionary();
                    this.m_areaObjectCacheSize = 0;
                }
                _loc_6 = new Array();
                _loc_7 = param1;
                while (_loc_7 < param3)
                {

                    _loc_8 = param2;
                    while (_loc_8 < param4)
                    {

                        if (this.m_cells.get(_loc_7).get(_loc_8) == null)
                        {
                        }
                        else
                        {
                            _loc_9 = (this.m_cells.get(_loc_7).get(_loc_8) as CollisionCell).items;
                            if (_loc_9 == null)
                            {
                            }
                            else
                            {
                                _loc_10 = _loc_9.length;
                                _loc_11 = 0;
                                while (_loc_11 < _loc_10)
                                {

                                    _loc_12 =(WorldObject) _loc_9.get(_loc_11);

                                    _loc_13 = _loc_12.positionX;
                                    _loc_14 = _loc_12.positionY;
                                    if (_loc_13 < param1)
                                    {
                                        _loc_13 = param1;
                                    }
                                    if (_loc_14 < param2)
                                    {
                                        _loc_14 = param2;
                                    }
                                    if (_loc_13 == _loc_7 && _loc_14 == _loc_8)
                                    {
                                        _loc_6.push(_loc_12);
                                    }
                                    _loc_11++;
                                }
                            }
                        }
                        _loc_8++;
                    }
                    _loc_7++;
                }
                this.m_areaObjectCache.put(_loc_5,  _loc_6);
                this.m_areaObjectCacheSize++;
            }


            return _loc_6;
        }//end

        protected boolean  checkCollisionForBox (CollisionLookup param1 ,WorldObject param2 )
        {
            int _loc_6 =0;
            CollisionCell _loc_7 =null ;
            boolean _loc_3 =false ;
            _loc_4 = param1.colliders ==null ;
            int _loc_5 =param1.startX ;
            while (_loc_5 < param1.endX)
            {

                _loc_6 = param1.startY;
                while (_loc_6 < param1.endY)
                {

                    if (this.isValidCellPos(_loc_5, _loc_6) && this.isPositionPathable(_loc_5, _loc_6))
                    {
                        _loc_7 = this.m_cells.get(_loc_5).get(_loc_6);
                        if (param1.startY < _loc_6)
                        {
                            _loc_3 = this.addCollidersToResults(_loc_5, _loc_6, _loc_7.getWalls(Constants.DIRECTION_SE), param1) || _loc_3;
                            if (_loc_3 && _loc_4)
                            {
                                break;
                            }
                        }
                        if (param1.startX < _loc_5)
                        {
                            _loc_3 = this.addCollidersToResults(_loc_5, _loc_6, _loc_7.getWalls(Constants.DIRECTION_SW), param1) || _loc_3;
                            if (_loc_3 && _loc_4)
                            {
                                break;
                            }
                        }
                        _loc_3 = this.addCollidersToResults(_loc_5, _loc_6, _loc_7.items, param1, param2) || _loc_3;
                        if (_loc_3 && _loc_4)
                        {
                            break;
                        }
                        if (_loc_3 && _loc_4)
                        {
                            return _loc_3;
                        }
                    }
                    else
                    {
                        _loc_3 = true;
                        if (_loc_4)
                        {
                            return _loc_3;
                        }
                    }
                    _loc_6++;
                }
                _loc_5++;
            }
            return _loc_3;
        }//end

        public boolean  checkCollisionForCell (CollisionLookup param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.m_cells.get(param1.startX).get(param1.startY) ;
            _loc_2 = this.addCollidersToResults(param1.startX, param1.startY, _loc_3.items, param1, null);
            return _loc_2;
        }//end

        protected boolean  checkCollisionForLine (CollisionLookup param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            CollisionCell _loc_6 =null ;
            boolean _loc_2 =false ;
            _loc_7 = param1.colliders ==null ;
            if (param1.startX == param1.endX)
            {
                _loc_4 = param1.startX;
                _loc_5 = param1.startY;
                while (_loc_5 < param1.endY)
                {

                    if (this.isValidCellPos(_loc_4, _loc_5))
                    {
                        _loc_6 = this.m_cells.get(_loc_4).get(_loc_5);
                        _loc_3 = _loc_6.getWalls(Constants.DIRECTION_SW);
                        _loc_2 = this.addCollidersToResults(_loc_4, _loc_5, _loc_3, param1) || _loc_2;
                        if (_loc_2 && _loc_7)
                        {
                            break;
                        }
                    }
                    else if (this.isValidCellPos((_loc_4 - 1), _loc_5))
                    {
                        _loc_6 = this.m_cells.get((_loc_4 - 1)).get(_loc_5);
                        _loc_3 = _loc_6.getWalls(Constants.DIRECTION_NE);
                        _loc_2 = this.addCollidersToResults(_loc_4, _loc_5, _loc_3, param1) || _loc_2;
                        if (_loc_2 && _loc_7)
                        {
                            break;
                        }
                    }
                    _loc_5++;
                }
            }
            else
            {
                _loc_5 = param1.startY;
                _loc_4 = param1.startX;
                while (_loc_4 < param1.endX)
                {

                    if (this.isValidCellPos(_loc_4, _loc_5))
                    {
                        _loc_6 = this.m_cells.get(_loc_4).get(_loc_5);
                        _loc_3 = _loc_6.getWalls(Constants.DIRECTION_SE);
                        _loc_2 = this.addCollidersToResults(_loc_4, _loc_5, _loc_3, param1) || _loc_2;
                        if (_loc_2 && _loc_7)
                        {
                            break;
                        }
                    }
                    else if (this.isValidCellPos(_loc_4, (_loc_5 - 1)))
                    {
                        _loc_6 = this.m_cells.get(_loc_4).get((_loc_5 - 1));
                        _loc_3 = _loc_6.getWalls(Constants.DIRECTION_NW);
                        _loc_2 = this.addCollidersToResults(_loc_4, _loc_5, _loc_3, param1) || _loc_2;
                        if (_loc_2 && _loc_7)
                        {
                            break;
                        }
                    }
                    _loc_4++;
                }
            }
            return _loc_2;
        }//end

        private boolean  addCollidersToResults (int param1 ,int param2 ,Array param3 ,CollisionLookup param4 ,WorldObject param5 =null )
        {
            WorldObject _loc_9 =null ;
            boolean _loc_10 =false ;
            Vector3 _loc_11 =null ;
            int _loc_12 =0;
            int _loc_13 =0;
            boolean _loc_14 =false ;
            boolean _loc_15 =false ;
            boolean _loc_6 =false ;
            _loc_7 = param4.getFlags(param1,param2);
            int _loc_8 =0;
            while (_loc_8 < param3.length())
            {

                _loc_9 =(WorldObject) param3.get(_loc_8);
                _loc_10 = param4.ignoreObjects == null || param4.ignoreObjects.indexOf(_loc_9) == -1;
                if (_loc_10 && _loc_9 != param5)
                {
                    _loc_11 = _loc_9.getPositionNoClone();
                    _loc_12 = param1 - _loc_11.x;
                    _loc_13 = param2 - _loc_11.y;
                    _loc_14 = _loc_9 != null && _loc_9.checkInternalCollision(_loc_12, _loc_13, _loc_7);
                    _loc_15 = _loc_9 != null && (param4.colliderTypes & _loc_9.getObjectType()) > 0;
                    if (_loc_14 && _loc_15)
                    {
                        _loc_6 = true;
                        if (param4.colliders != null)
                        {
                            if (param4.colliders.indexOf(_loc_9) == -1)
                            {
                                param4.colliders.push(_loc_9);
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                _loc_8++;
            }
            return _loc_6;
        }//end

        public void  showCollisionMap (boolean param1 )
        {
            if (this.m_visualCollisionMap == null && param1)
            {
                this.m_visualCollisionMap = new Sprite();
                this.drawCollisionMap(this.m_visualCollisionMap);
                GlobalEngine.viewport.overlayBase.addChild(this.m_visualCollisionMap);
            }
            else if (this.m_visualCollisionMap != null && !param1)
            {
                if (this.m_visualCollisionMap.parent)
                {
                    this.m_visualCollisionMap.parent.removeChild(this.m_visualCollisionMap);
                }
                this.m_visualCollisionMap = null;
            }
            return;
        }//end

        public void  drawCollisionMap (Sprite param1 )
        {
            int _loc_6 =0;
            CollisionCell _loc_7 =null ;
            Point _loc_8 =null ;
            _loc_2 = param1.graphics ;
            _loc_3 = IsoMath.getPixelDeltaFromTileDelta(1,0);
            _loc_4 = IsoMath.getPixelDeltaFromTileDelta(0,1);
            _loc_2.clear();
            int _loc_5 =this.m_xStart ;
            while (_loc_5 < this.m_xEnd)
            {

                _loc_6 = this.m_yStart;
                while (_loc_6 < this.m_yEnd)
                {

                    Debug.debug4("drawCollisionMap."+_loc_5+";"+_loc_6);
                    _loc_7 =(CollisionCell) this.m_cells.get(_loc_5).get(_loc_6);
                    _loc_8 = IsoMath.tilePosToPixelPos(_loc_5, _loc_6);
                    if (_loc_7.items.length())
                    {
                        _loc_2.lineStyle(0, 0, 0);
                        _loc_2.moveTo(_loc_8.x, _loc_8.y);
                        _loc_2.beginFill(COLLISIONMAP_COLOR, COLLISIONMAP_ALPHA);
                        _loc_2.lineTo(_loc_8.x + _loc_3.x, _loc_8.y + _loc_3.y);
                        _loc_2.lineTo(_loc_8.x + _loc_3.x + _loc_4.x, _loc_8.y + _loc_3.y + _loc_4.y);
                        _loc_2.lineTo(_loc_8.x + _loc_4.x, _loc_8.y + _loc_4.y);
                        _loc_2.endFill();
                    }
                    _loc_2.lineStyle(4, COLLISIONMAP_COLOR);
                    if (_loc_7.getWalls(Constants.DIRECTION_SW).length > 0)
                    {
                        _loc_2.moveTo(_loc_8.x, _loc_8.y);
                        _loc_2.lineTo(_loc_8.x + _loc_4.x, _loc_8.y + _loc_4.y);
                    }
                    if (_loc_7.getWalls(Constants.DIRECTION_SE).length > 0)
                    {
                        _loc_2.moveTo(_loc_8.x, _loc_8.y);
                        _loc_2.lineTo(_loc_8.x + _loc_3.x, _loc_8.y + _loc_3.y);
                    }
                    if (_loc_7.getWalls(Constants.DIRECTION_NE).length > 0)
                    {
                        _loc_2.moveTo(_loc_8.x + _loc_3.x, _loc_8.y + _loc_3.y);
                        _loc_2.lineTo(_loc_8.x + _loc_3.x + _loc_4.x, _loc_8.y + _loc_3.y + _loc_4.y);
                    }
                    if (_loc_7.getWalls(Constants.DIRECTION_NW).length > 0)
                    {
                        _loc_2.moveTo(_loc_8.x + _loc_4.x, _loc_8.y + _loc_4.y);
                        _loc_2.lineTo(_loc_8.x + _loc_3.x + _loc_4.x, _loc_8.y + _loc_3.y + _loc_4.y);
                    }
                    _loc_6++;
                }
                _loc_5++;
            }
            return;
        }//end

        public Array  getObjectsByPosition (int param1 ,int param2 )
        {
            CollisionCell _loc_4 =null ;
            Array _loc_3 =new Array();
            if (this.isValidCellPos(param1, param2))
            {
                _loc_4 = this.m_cells.get(param1).get(param2);
                _loc_3 = _loc_4.items.concat([]);
            }

            return _loc_3;
        }//end

        public Array  findCoordinatesForObject (WorldObject param1 )
        {
            CollisionCell _loc_2 =null ;
            WorldObject _loc_3 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            Array _loc_4 =new Array ();
            _loc_4.put(0,  -1);
            _loc_4.put(1,  -1);
            for(int i0 = 0; i0 < this.m_cells.size(); i0++)
            {
            		_loc_5 = this.m_cells.get(i0);

                for(int i0 = 0; i0 < this.m_cells.get(_loc_5).size(); i0++)
                {
                		_loc_6 = this.m_cells.get(_loc_5).get(i0);

                    _loc_2 = this.m_cells.get(_loc_5).get(_loc_6);
                    for(int i0 = 0; i0 < _loc_2.items.size(); i0++)
                    {
                    		_loc_7 = _loc_2.items.get(i0);

                        _loc_3 = _loc_2.items.get(_loc_7);
                        if ((WorldObject)_loc_3 == param1)
                        {
                            _loc_4.put(0,  _loc_2.x);
                            _loc_4.put(1,  _loc_2.y);
                            return _loc_4;
                        }
                    }
                }
            }
            return _loc_4;
        }//end

        public Array  getIntersectingObjects (Box3D param1 )
        {
            int _loc_5 =0;
            CollisionCell _loc_6 =null ;
            WorldObject _loc_7 =null ;
            Dictionary _loc_2 =new Dictionary ();
            int _loc_3 =param1.min.x ;
            while (_loc_3 < param1.max.x)
            {

                _loc_5 = param1.min.y;
                while (_loc_5 < param1.max.y)
                {

                    if (this.isValidCellPos(_loc_3, _loc_5))
                    {
                        _loc_6 = this.m_cells.get(_loc_3).get(_loc_5);
                        for(int i0 = 0; i0 < _loc_6.items.size(); i0++)
                        {
                        		_loc_7 = _loc_6.items.get(i0);

                            if (_loc_2.get(_loc_7) == null)
                            {
                                _loc_2.put(_loc_7,  _loc_7);
                            }
                        }
                    }
                    _loc_5++;
                }
                _loc_3++;
            }
            _loc_4 = DictionaryUtil.getKeys(_loc_2);
            return DictionaryUtil.getKeys(_loc_2);
        }//end

        public boolean  getPath (Vector3 param1 ,Vector3 param2 ,Array param3 =null ,boolean param4 =false ,double param5 =0.1,Array param6 =null ,WorldObject param7 =null ,boolean param8 =false )
        {
            boolean _loc_9 =false ;
            if (this.m_pathfinder != null)
            {
                if (param6 == null)
                {
                    param6 = new Array();
                }
                param2.x = Utilities.clamp(param2.x, this.m_xStart, (this.m_xEnd - 1));
                param2.y = Utilities.clamp(param2.y, this.m_yStart, (this.m_yEnd - 1));
                _loc_9 = this.m_pathfinder.findPath(param1.x, param1.y, param2.x, param2.y, param6, param3, param8, param5);
            }
            return _loc_9;
        }//end

        public Array  findFirstAvailableCoordsForBox (int param1 ,int param2 )
        {
            Array _loc_3 =new Array ();
            _loc_3.put(0,  -1);
            _loc_3.put(1,  -1);
            _loc_4 = this.m_width /2;
            _loc_5 = this.m_width /2;
            int _loc_6 =0;
            int _loc_7 =1;
            int _loc_8 =0;
            CollisionLookup _loc_9 =new CollisionLookup ();
            while (_loc_4 < this.m_xEnd && _loc_4 >= this.m_xStart && _loc_5 < this.m_yEnd && _loc_5 >= this.m_yStart)
            {

                _loc_9.startX = _loc_4;
                _loc_9.endX = _loc_4 + param1;
                _loc_9.startY = _loc_5;
                _loc_9.endY = _loc_5 + param2;
                if (!this.checkCollision(_loc_9))
                {
                    _loc_3.put(0,  _loc_4);
                    _loc_3.put(1,  _loc_5);
                    return _loc_3;
                }
                if (_loc_6 == 0)
                {
                    _loc_5 = _loc_5 - 1;
                }
                else if (_loc_6 == 1)
                {
                    _loc_4 = _loc_4 - 1;
                }
                else if (_loc_6 == 2)
                {
                    _loc_5 = _loc_5 + 1;
                }
                else if (_loc_6 == 3)
                {
                    _loc_4 = _loc_4 + 1;
                }
                if (_loc_8++ >= _loc_7)
                {
                    _loc_6 = _loc_6++ % 4;
                    _loc_8 = 0;
                    if (_loc_6++ == 0 || _loc_6 == 2)
                    {
                        _loc_7 = _loc_7 + 1;
                    }
                }
            }
            return _loc_3;
        }//end

        public static boolean  compareCollisionFlags (int param1 ,int param2 )
        {
            boolean _loc_3 =true ;
            if ((param2 & Constants.COLLISION_AVATAR) << 0 == Constants.COLLISION_AVATAR)
            {
                if ((param1 & Constants.COLLISION_ALL_EXCEPT_AVATAR) << 0 == Constants.COLLISION_ALL_EXCEPT_AVATAR)
                {
                    _loc_3 = false;
                }
            }
            if ((param1 & Constants.COLLISION_OVERLAP) << 0 == Constants.COLLISION_OVERLAP && (param2 & Constants.COLLISION_OVERLAP) << 0 == Constants.COLLISION_OVERLAP)
            {
                _loc_3 = false;
            }
            if ((param2 & Constants.COLLISION_SAME_ONLY) << 0 == Constants.COLLISION_SAME_ONLY && (param1 & Constants.COLLISION_SAME_ONLY) << 0 != Constants.COLLISION_SAME_ONLY)
            {
                _loc_3 = false;
            }
            return _loc_3;
        }//end

        public static Array  getIgnoredCollisionObjects ()
        {
            return CollisionMap.m_ignoreObjectsInCollision;
        }//end

    }



