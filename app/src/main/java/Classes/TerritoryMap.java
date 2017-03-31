package Classes;

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

//import flash.geom.*;

    public class TerritoryMap
    {
        protected int m_gridDelta ;
        protected Array m_cells ;
        protected Rectangle m_boundingRect ;
        protected Rectangle m_occupiedBoundingRect ;
        protected Vector<Rectangle> m_occupiedRects;
        protected Array m_surroundingCellOffsets ;

        public  TerritoryMap (int param1 )
        {
            this.m_surroundingCellOffsets = .get(new Point(0, -1), new Point(-1, 0), new Point(1, 0), new Point(0, 1));
            this.m_gridDelta = param1;
            this.m_boundingRect = new Rectangle(0, 0, 0, 0);
            this.m_occupiedBoundingRect = new Rectangle(0, 0, 0, 0);
            this.m_cells = new Array();
            this.m_occupiedRects = new Vector<Rectangle>();
            return;
        }//end

        public int  gridDelta ()
        {
            return this.m_gridDelta;
        }//end

        public Rectangle  occupiedBoundingRect ()
        {
            return this.m_occupiedBoundingRect;
        }//end

        public void  addTerritory (Rectangle param1 )
        {
            Rectangle _loc_12 =null ;
            int _loc_13 =0;
            int _loc_2 =0;
            while (_loc_2 < this.m_occupiedRects.length())
            {

                _loc_12 = this.m_occupiedRects.get(_loc_2);
                if (_loc_12.containsRect(param1))
                {
                    return;
                }
                if (param1.containsRect(_loc_12))
                {
                    this.m_occupiedRects.splice(_loc_2, 1);
                    _loc_2 = _loc_2 - 1;
                }
                _loc_2++;
            }
            this.m_occupiedRects.push(param1);
            _loc_3 = param1.x /this.m_gridDelta ;
            _loc_4 = param1.y /this.m_gridDelta ;
            _loc_5 = param1.width /this.m_gridDelta ;
            _loc_6 = param1.height /this.m_gridDelta ;
            int _loc_7 =_loc_3 ;
            while (_loc_7 < _loc_3 + _loc_5)
            {

                if (this.m_cells.get(_loc_7) == null)
                {
                    this.m_cells.put(_loc_7,  new Array());
                }
                _loc_13 = _loc_4;
                while (_loc_13 < _loc_4 + _loc_6)
                {

                    this.m_cells.get(_loc_7).put(_loc_13,  1);
                    _loc_13++;
                }
                _loc_7++;
            }
            _loc_8 = Math.min(_loc_3 ,(this.m_boundingRect.x +1));
            _loc_9 = Math.max(_loc_3 +_loc_5 -1,this.m_boundingRect.right -2);
            _loc_10 = Math.min(_loc_4 ,(this.m_boundingRect.y +1));
            _loc_11 = Math.max(_loc_4 +_loc_6 -1,this.m_boundingRect.bottom -2);
            this.m_occupiedBoundingRect.x = _loc_8 * this.m_gridDelta;
            this.m_occupiedBoundingRect.y = _loc_10 * this.m_gridDelta;
            this.m_occupiedBoundingRect.width = (_loc_9 - _loc_8 + 1) * this.m_gridDelta;
            this.m_occupiedBoundingRect.height = (_loc_11 - _loc_10 + 1) * this.m_gridDelta;
            this.m_boundingRect.x = _loc_8 - 1;
            this.m_boundingRect.y = _loc_10 - 1;
            this.m_boundingRect.width = _loc_9 - _loc_8 + 3;
            this.m_boundingRect.height = _loc_11 - _loc_10 + 3;
            _loc_7 = this.m_boundingRect.x;
            while (_loc_7 < this.m_boundingRect.right)
            {

                if (this.m_cells.get(_loc_7) == null)
                {
                    this.m_cells.put(_loc_7,  new Array());
                }
                _loc_13 = this.m_boundingRect.y;
                while (_loc_13 < this.m_boundingRect.bottom)
                {

                    if (this.m_cells.get(_loc_7).get(_loc_13) == null)
                    {
                        this.m_cells.get(_loc_7).put(_loc_13,  0);
                    }
                    _loc_13++;
                }
                _loc_7++;
            }


            return;
        }//end

        public boolean  pointInTerritory (int param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            int _loc_4 =this.m_boundingRect.x +Math.floor(param1 /this.m_gridDelta -this.m_boundingRect.x );
            int _loc_5 =this.m_boundingRect.y +Math.floor(param2 /this.m_gridDelta -this.m_boundingRect.y );


            if (_loc_4 >= this.m_boundingRect.x && _loc_4 < this.m_boundingRect.right && (_loc_5 >= this.m_boundingRect.y && _loc_5 < this.m_boundingRect.bottom))
            {

                _loc_3 = this.m_cells.get(_loc_4).get(_loc_5);
            }
            return _loc_3;
        }//end

        public boolean  rectInTerritory (Rectangle param1 )
        {
            int _loc_4 =0;
            boolean _loc_2 =true ;
            _loc_3 = param1.x ;
            while (_loc_2 && _loc_3 < param1.right)
            {

                _loc_4 = param1.y;
                while (_loc_2 && _loc_4 < param1.bottom)
                {

                    _loc_2 = this.pointInTerritory(_loc_3, _loc_4);
                    _loc_4++;
                }
                _loc_3++;
            }

            return _loc_2;
        }//end

        protected boolean  validCell (int param1 ,int param2 )
        {
            return param1 >= this.m_boundingRect.x && param1 < this.m_boundingRect.right && (param2 >= this.m_boundingRect.y && param2 < this.m_boundingRect.bottom);
        }//end

        protected boolean  cellOccupied (int param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            if (this.validCell(param1, param2))
            {
                _loc_3 = this.m_cells.get(param1).get(param2);
            }
            return _loc_3;
        }//end

        protected boolean  cellHasOccupiedNeighbor (int param1 ,int param2 )
        {
            Point _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            boolean _loc_3 =false ;
            for(int i0 = 0; i0 < this.m_surroundingCellOffsets.size(); i0++)
            {
            	_loc_4 = this.m_surroundingCellOffsets.get(i0);

                _loc_5 = param1 + _loc_4.x;
                _loc_6 = param2 + _loc_4.y;
                if (this.validCell(_loc_5, _loc_6) && this.m_cells.get(_loc_5).get(_loc_6))
                {
                    _loc_3 = true;
                    break;
                }
            }
            return _loc_3;
        }//end

        public Rectangle  getClosestExpansionRect (Vector2 param1 )
        {
            Rectangle _loc_2 =null ;
            _loc_3 = this.m_boundingRect.x +Math.floor(param1.x /this.m_gridDelta -this.m_boundingRect.x );
            _loc_4 = this.m_boundingRect.y +Math.floor(param1.y /this.m_gridDelta -this.m_boundingRect.y );
            _loc_3 = MathUtil.clamp(_loc_3, this.m_boundingRect.x, (this.m_boundingRect.right - 1));
            _loc_4 = MathUtil.clamp(_loc_4, this.m_boundingRect.y, (this.m_boundingRect.bottom - 1));
            _loc_2 = new Rectangle(_loc_3 * this.m_gridDelta, _loc_4 * this.m_gridDelta, this.m_gridDelta, this.m_gridDelta);
            return _loc_2;
        }//end

        public Rectangle  getExpansionRect (Vector2 param1 )
        {
            Rectangle _loc_2 =null ;
            _loc_3 = this.m_boundingRect.x +Math.floor(param1.x /this.m_gridDelta -this.m_boundingRect.x );
            _loc_4 = this.m_boundingRect.y +Math.floor(param1.y /this.m_gridDelta -this.m_boundingRect.y );
            _loc_2 = new Rectangle(_loc_3 * this.m_gridDelta, _loc_4 * this.m_gridDelta, this.m_gridDelta, this.m_gridDelta);
            return _loc_2;
        }//end

        public Rectangle  getClosestAdjacentRect (Vector2 param1 )
        {
            Rectangle _loc_2 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            int _loc_10 =0;
            int _loc_11 =0;
            int _loc_12 =0;
            _loc_3 = this.m_boundingRect.x +Math.floor(param1.x /this.m_gridDelta -this.m_boundingRect.x );
            _loc_4 = this.m_boundingRect.y +Math.floor(param1.y /this.m_gridDelta -this.m_boundingRect.y );
            _loc_3 = MathUtil.clamp(_loc_3, this.m_boundingRect.x, (this.m_boundingRect.right - 1));
            _loc_4 = MathUtil.clamp(_loc_4, this.m_boundingRect.y, (this.m_boundingRect.bottom - 1));
            if (this.m_cells.get(_loc_3).get(_loc_4) == 0 && this.cellHasOccupiedNeighbor(_loc_3, _loc_4))
            {
                _loc_2 = new Rectangle(_loc_3 * this.m_gridDelta, _loc_4 * this.m_gridDelta, this.m_gridDelta, this.m_gridDelta);
            }
            else
            {
                _loc_5 = 99999;
                _loc_8 = -1;
                _loc_9 = -1;
                _loc_10 = this.m_boundingRect.x;
                while (_loc_10 < this.m_boundingRect.right)
                {

                    _loc_11 = this.m_boundingRect.y;
                    while (_loc_11 < this.m_boundingRect.bottom)
                    {

                        if (this.m_cells.get(_loc_10).get(_loc_11) == 0 && this.cellHasOccupiedNeighbor(_loc_10, _loc_11))
                        {
                            _loc_6 = _loc_10 - _loc_3;
                            _loc_7 = _loc_11 - _loc_4;
                            _loc_12 = _loc_6 * _loc_6 + _loc_7 * _loc_7;
                            if (_loc_12 < _loc_5)
                            {
                                _loc_5 = _loc_12;
                                _loc_8 = _loc_10;
                                _loc_9 = _loc_11;
                            }
                        }
                        _loc_11++;
                    }
                    _loc_10++;
                }
                _loc_2 = new Rectangle(_loc_8 * this.m_gridDelta, _loc_9 * this.m_gridDelta, this.m_gridDelta, this.m_gridDelta);
            }
            return _loc_2;
        }//end

        public Rectangle Vector  occupiedRects ().<>
        {
            return this.m_occupiedRects;
        }//end

        public Point Vector  getMapBoundary ().<>
        {
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            Vector<Point> _loc_1 =new Vector<Point>();
            _loc_2 = this.m_boundingRect.x ;
            while (_loc_2 < this.m_boundingRect.right)
            {

                _loc_3 = this.m_boundingRect.y;
                while (_loc_3 < this.m_boundingRect.bottom)
                {

                    _loc_4 = _loc_2 * this.m_gridDelta;
                    _loc_5 = _loc_3 * this.m_gridDelta;
                    if (this.m_cells.get(_loc_2).get(_loc_3) == 1)
                    {
                        if (!this.cellOccupied((_loc_2 - 1), _loc_3))
                        {
                            _loc_1.push(new Point(_loc_4, _loc_5));
                            _loc_1.push(new Point(_loc_4, _loc_5 + this.m_gridDelta));
                        }
                        if (!this.cellOccupied((_loc_2 + 1), _loc_3))
                        {
                            _loc_1.push(new Point(_loc_4 + this.m_gridDelta, _loc_5));
                            _loc_1.push(new Point(_loc_4 + this.m_gridDelta, _loc_5 + this.m_gridDelta));
                        }
                        if (!this.cellOccupied(_loc_2, (_loc_3 - 1)))
                        {
                            _loc_1.push(new Point(_loc_4, _loc_5));
                            _loc_1.push(new Point(_loc_4 + this.m_gridDelta, _loc_5));
                        }
                        if (!this.cellOccupied(_loc_2, (_loc_3 + 1)))
                        {
                            _loc_1.push(new Point(_loc_4, _loc_5 + this.m_gridDelta));
                            _loc_1.push(new Point(_loc_4 + this.m_gridDelta, _loc_5 + this.m_gridDelta));
                        }
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

    }



