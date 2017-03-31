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


    public class Pathfinder
    {
        protected Vector m_distance.<Number >=null ;
        protected Vector m_score.<Number >=null ;
        protected Vector m_isPathable.<Boolean >=null ;
        protected Vector m_isCollide.<int >=null ;
        protected Object m_ignoredSpace =null ;
        protected Vector m_prevPos.<Number >=null ;
        protected Array m_openList =null ;
        protected int m_sizeX =-1;
        protected int m_sizeY =-1;
        protected double m_startPos =-1;
        protected double m_endPos =-1;
        protected int m_endX =-1;
        protected int m_endY =-1;
        protected double m_bestLocation =-1;
        protected Array m_ignoreList ;
        protected CollisionLookup m_colData ;
        protected CollisionMap m_colMap ;
        protected double m_minDist ;
public static  double HEURISTIC =0.9;
public static  int COLLIDE_DIRTY =2;
public static  int COLLIDE_FALSE =0;
public static  int COLLIDE_TRUE =1;

        public  Pathfinder (int param1 ,int param2 ,CollisionMap param3 )
        {
            this.m_sizeX = param1;
            this.m_sizeY = param2;
            this.m_colMap = param3;
            this.m_distance = new Vector<Number>(this.m_sizeX * this.m_sizeY, true);
            this.m_score = new Vector<Number>(this.m_sizeX * this.m_sizeY, true);
            this.m_isPathable = new Vector<Boolean>(this.m_sizeX * this.m_sizeY, true);
            this.m_isCollide = new Vector<int>(this.m_sizeX * this.m_sizeY, true);
            this.m_prevPos = new Vector<Number>(this.m_sizeX * this.m_sizeY, true);
            this.m_ignoredSpace = new Object();
            int _loc_4 =0;
            _loc_4 = 0;
            while (_loc_4 < this.m_sizeX * this.m_sizeY)
            {

                this.m_isPathable.put(_loc_4,  false);
                this.m_isCollide.put(_loc_4,  COLLIDE_DIRTY);
                _loc_4++;
            }
            this.m_openList = new Array();
            return;
        }//end

        public void  setPathable (int param1 ,int param2 ,boolean param3 =true )
        {
            this.m_isPathable.put(param2 * this.m_sizeX + param1,  param3);
            return;
        }//end

        public boolean  findPath (int param1 ,int param2 ,int param3 ,int param4 ,Array param5 ,Array param6 =null ,boolean param7 =false ,double param8 =0.1)
        {
            double _loc_11 =0;
            int _loc_12 =0;
            int _loc_13 =0;
            boolean _loc_14 =false ;
            boolean _loc_17 =false ;
            boolean _loc_9 =false ;
            boolean _loc_10 =false ;
            int _loc_15 =0;
            _loc_16 = this(.m_sizeX +this.m_sizeY )*20;
            this.m_minDist = param8;
            this.m_ignoreList = param6;
            this.clear();
            this.setupIgnoredSpace();
            param1 = param1 >= this.m_sizeX ? (this.m_sizeX) : (param1);
            param2 = param2 >= this.m_sizeY ? (this.m_sizeY) : (param2);
            param1 = param1 < 0 ? (0) : (param1);
            param2 = param2 < 0 ? (0) : (param2);
            param3 = param3 >= this.m_sizeX ? (this.m_sizeX) : (param3);
            param4 = param4 >= this.m_sizeY ? (this.m_sizeY) : (param4);
            param3 = param3 < 0 ? (0) : (param3);
            param4 = param4 < 0 ? (0) : (param4);
            this.m_endX = param3;
            this.m_endY = param4;
            this.m_startPos = param2 * this.m_sizeX + param1;
            this.m_endPos = param4 * this.m_sizeX + param3;
            this.m_distance.put(this.m_startPos,  0);
            this.m_score.put(this.m_startPos,  0);
            this.m_isCollide.put(this.m_startPos,  COLLIDE_DIRTY);
            if (this.m_minDist < 1 && (!this.m_isPathable.get(this.m_endPos) || !this.canPath(param3, param4)))
            {
                _loc_9 = true;
            }
            this.canPath(param1, param2);
            this.m_openList.push(this.m_startPos);
            while (!_loc_9)
            {

                if (this.m_openList.length == 0)
                {
                    if (this.m_bestLocation >= 0)
                    {
                        this.pathBack(this.m_bestLocation, this.m_startPos, param5);
                    }
                    _loc_9 = true;
                    continue;
                }
                _loc_11 = this.m_openList.shift();
                if (this.m_startPos != _loc_11 && (this.m_bestLocation == -1 || this.m_score.get(this.m_bestLocation) > this.m_score.get(_loc_11)))
                {
                    this.m_bestLocation = _loc_11;
                }
                _loc_12 = _loc_11 % this.m_sizeX;
                _loc_13 = (_loc_11 - _loc_12) / this.m_sizeX;
                _loc_14 = false;
                _loc_17 = !this.canPath(_loc_12, _loc_13);
                _loc_14 = this.addNode((_loc_12 + 1), _loc_13, _loc_11, _loc_17, 1);
                _loc_14 = this.addNode((_loc_12 - 1), _loc_13, _loc_11, _loc_17, 1) || _loc_14;
                _loc_14 = this.addNode(_loc_12, (_loc_13 + 1), _loc_11, _loc_17, 1) || _loc_14;
                _loc_14 = this.addNode(_loc_12, (_loc_13 - 1), _loc_11, _loc_17, 1) || _loc_14;
                if (!param7)
                {
                    _loc_14 = this.addNode((_loc_12 + 1), (_loc_13 + 1), _loc_11, _loc_17, Math.SQRT2, 1, 1) || _loc_14;
                    _loc_14 = this.addNode((_loc_12 - 1), (_loc_13 + 1), _loc_11, _loc_17, Math.SQRT2, -1, 1) || _loc_14;
                    _loc_14 = this.addNode((_loc_12 + 1), (_loc_13 - 1), _loc_11, _loc_17, Math.SQRT2, 1, -1) || _loc_14;
                    _loc_14 = this.addNode((_loc_12 - 1), (_loc_13 - 1), _loc_11, _loc_17, Math.SQRT2, -1, -1) || _loc_14;
                }
                if (_loc_14)
                {
                    _loc_9 = true;
                    _loc_10 = true;
                    this.pathBack(this.m_bestLocation, this.m_startPos, param5);
                }
                _loc_15++;
                if (_loc_15 > _loc_16)
                {
                    this.m_openList = new Array();
                }
            }
            return _loc_10;
        }//end

        protected boolean  addNode (int param1 ,int param2 ,double param3 ,boolean param4 ,double param5 ,int param6 =0,int param7 =0)
        {
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            double _loc_11 =0;
            boolean _loc_12 =false ;
            _loc_13 = param1>=0&& param1 < this.m_sizeX && param2 >= 0 && param2 < this.m_sizeY;
            _loc_14 = param2*this.m_sizeX+param1;
            boolean _loc_15 =true ;
            if (_loc_13 && this.m_score.get(_loc_14) == -1)
            {
                if (param4)
                {
                    _loc_15 = true;
                }
                else if (!this.canPath(param1, param2))
                {
                    _loc_15 = false;
                }
                else
                {
                    if (param5 != 1)
                    {
                        _loc_15 = this.canPath(param1 - param6, param2) && this.canPath(param1, param2 - param7);
                    }
                    if (_loc_15 && !this.m_isPathable.get(_loc_14) && this.m_isPathable.get(param3))
                    {
                        _loc_15 = false;
                    }
                }
                if (_loc_15)
                {
                    this.m_distance.put(_loc_14,  this.m_distance.get(param3) + param5);
                    _loc_11 = Math.sqrt((param1 - this.m_endX) * (param1 - this.m_endX) + (param2 - this.m_endY) * (param2 - this.m_endY));
                    this.m_score.put(_loc_14,  this.m_distance.get(_loc_14) + HEURISTIC * _loc_11);
                    this.m_prevPos.put(_loc_14,  param3);
                    if (_loc_11 < this.m_minDist || _loc_14 == this.m_endPos)
                    {
                        _loc_12 = true;
                        if (_loc_14 == this.m_endPos || this.m_bestLocation == -1 || this.m_score.get(this.m_bestLocation) - this.m_distance.get(this.m_bestLocation) > HEURISTIC * _loc_11)
                        {
                            this.m_bestLocation = _loc_14;
                        }
                    }
                    else
                    {
                        _loc_8 = 0;
                        _loc_9 = this.m_openList.length;
                        _loc_10 = _loc_8 + _loc_9 >> 1;
                        while (_loc_10++ < _loc_9)
                        {

                            if (this.m_score.get(_loc_14) < this.m_score.get(this.m_openList.get(_loc_10)))
                            {
                                _loc_9 = _loc_10;
                                _loc_10 = _loc_8 + _loc_9 >> 1;
                                continue;
                            }
                            if (this.m_score.get(_loc_14) > this.m_score.get(this.m_openList.get(_loc_10)))
                            {
                                _loc_10 =_loc_10++ + _loc_9 >> 1;
                                continue;
                            }
                            break;
                        }
                        this.m_openList.splice(_loc_10, 0, _loc_14);
                    }
                }
            }
            return _loc_12;
        }//end

        protected boolean  canPath (int param1 ,int param2 )
        {
            int _loc_3 =0;
            boolean _loc_4 =true ;
            _loc_5 = param2*this.m_sizeX +param1 ;
            if (this.m_isCollide.get(_loc_5) == COLLIDE_DIRTY)
            {
                if (this.m_colData == null)
                {
                    this.m_colData = new CollisionLookup();
                }
                this.m_colData.init(param1, param2, (param1 + 1), (param2 + 1));
                this.m_colData.flags = Constants.COLLISION_AVATAR;
                this.m_isCollide.put(_loc_5,  this.m_colMap.checkCollisionForCell(this.m_colData) ? (COLLIDE_TRUE) : (COLLIDE_FALSE));
            }
            if (this.m_isCollide.get(_loc_5) == COLLIDE_TRUE)
            {
                if (this.m_ignoredSpace.get(_loc_5) == COLLIDE_DIRTY)
                {
                    if (this.m_colData == null)
                    {
                        this.m_colData = new CollisionLookup();
                    }
                    this.m_colData.init(param1, param2, (param1 + 1), (param2 + 1));
                    this.m_colData.ignoreObjects = this.m_ignoreList;
                    this.m_colData.flags = Constants.COLLISION_AVATAR;
                    this.m_ignoredSpace.put(_loc_5,  this.m_colMap.checkCollisionForCell(this.m_colData) ? (COLLIDE_TRUE) : (COLLIDE_FALSE));
                }
                if (this.m_ignoredSpace.hasOwnProperty(_loc_5) && this.m_ignoredSpace.get(_loc_5) == COLLIDE_FALSE)
                {
                    _loc_4 = true;
                }
                else
                {
                    _loc_4 = false;
                }
            }
            return _loc_4;
        }//end

        protected void  clear ()
        {
            int _loc_1 =0;
            _loc_2 = this.m_sizeX *this.m_sizeY ;
            _loc_1 = 0;
            while (_loc_1 < _loc_2)
            {

                this.m_score.put(_loc_1,  -1);
                _loc_1++;
            }
            if (this.m_openList.length > 0)
            {
                this.m_openList = new Array();
            }
            this.m_ignoredSpace = new Object();
            this.m_bestLocation = -1;
            return;
        }//end

        protected void  pathBack (double param1 ,double param2 ,Array param3 )
        {
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_6 = param1;
            while (_loc_6 != -1)
            {

                _loc_4 = _loc_6 % this.m_sizeX;
                _loc_5 = (_loc_6 - _loc_4) / this.m_sizeX;
                param3.push(new Vector3(_loc_4, _loc_5));
                if (_loc_6 == param2)
                {
                    break;
                }
                _loc_6 = this.m_prevPos.get(_loc_6);
            }
            param3.reverse();
            return;
        }//end

        public void  setCollisionMapDirty (int param1 ,int param2 ,int param3 ,int param4 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            double _loc_7 =0;
            param2 = param2 > this.m_sizeX ? (this.m_sizeX) : (param2);
            param4 = param4 > this.m_sizeY ? (this.m_sizeY) : (param4);
            param1 = param1 < 0 ? (0) : (param1);
            param3 = param3 < 0 ? (0) : (param3);
            _loc_6 = param3;
            while (_loc_6 < param4)
            {

                _loc_7 = _loc_6 * this.m_sizeX;
                _loc_5 = param1;
                while (_loc_5 < param2)
                {

                    this.m_isCollide.put(_loc_7 + _loc_5,  COLLIDE_DIRTY);
                    _loc_5++;
                }
                _loc_6++;
            }
            return;
        }//end

        protected void  setupIgnoredSpace ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            WorldObject _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Vector3 _loc_6 =null ;
            double _loc_7 =0;
            _loc_3 = 0;
            while (_loc_3 < this.m_ignoreList.length())
            {

                _loc_4 = this.m_ignoreList.get(_loc_3);
                _loc_5 = _loc_4.getPositionNoClone();
                _loc_6 = _loc_4.getSizeNoClone();
                _loc_2 = _loc_5.y;
                while (_loc_2 < _loc_5.y + _loc_6.y)
                {

                    _loc_7 = _loc_2 * this.m_sizeX;
                    _loc_1 = _loc_5.x;
                    while (_loc_1 < _loc_5.x + _loc_6.x)
                    {

                        this.m_ignoredSpace.put(_loc_7 + _loc_1,  COLLIDE_DIRTY);
                        _loc_1++;
                    }
                    _loc_2++;
                }
                _loc_3++;
            }
            return;
        }//end

        protected void  printSurroundings (int param1 ,int param2 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            double _loc_5 =0;
            trace("printing area surrounding " + param1 + ", " + param2);
            param1 = param1 >= this.m_sizeX - 3 ? (this.m_sizeX - 3) : (param1);
            param2 = param2 >= this.m_sizeY - 3 ? (this.m_sizeY - 3) : (param2);
            param1 = param1 < 2 ? (2) : (param1);
            param2 = param2 < 2 ? (2) : (param2);
            String _loc_6 ="";
            String _loc_7 ="";
            String _loc_8 ="";
            String _loc_9 ="";
            _loc_4 = param2 + 2;
            while (_loc_4 >= param2 - 2)
            {

                _loc_5 = _loc_4 * this.m_sizeX;
                _loc_7 = "";
                _loc_9 = "";
                _loc_3 = param1 - 2;
                while (_loc_3 < param1 + 3)
                {

                    _loc_7 = _loc_7 + this.m_isCollide.get(_loc_5 + _loc_3);
                    _loc_9 = _loc_9 + this.m_ignoredSpace.get(_loc_5 + _loc_3);
                    _loc_3++;
                }
                _loc_8 = _loc_8 + "\n" + _loc_9;
                _loc_6 = _loc_6 + "\n" + _loc_7;
                _loc_4 = _loc_4 - 1;
            }
            trace("cmap = \n" + _loc_6);
            trace("imap = \n" + _loc_8);
            return;
        }//end

    }



