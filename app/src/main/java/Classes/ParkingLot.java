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

    public class ParkingLot extends Road
    {
        protected FauxDecoration m_decoration =null ;
        protected Vector3 m_decoOffset ;

        public  ParkingLot (String param1)
        {
            String _loc_4 =null ;
            this.m_decoOffset = new Vector3(0, 0);
            super(param1);
            _loc_2 = getItem().xml;
            _loc_3 = _loc_2.attached_deco;
            _loc_2 = _loc_3.get(0);
            if (_loc_2)
            {
                _loc_4 = _loc_2.@deco;
                this.m_decoOffset.x = _loc_2.@xOffset;
                this.m_decoOffset.y = _loc_2.@yOffset;
                this.m_decoOffset.z = _loc_2.@zOffset;
                if (_loc_4 && _loc_4.length > 0)
                {
                    this.m_decoration = new FauxDecoration(_loc_4);
                    this.m_decoration.setOuter(Global.world);
                }
            }
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            if (this.m_decoration)
            {
                this.m_decoration.rotateToDirection(m_direction);
                this.setPosition(m_position.x, m_position.y, m_position.z);
            }
            return;
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            double _loc_4 =0;
            double _loc_5 =0;
            Vector3 _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            super.setPosition(param1, param2, param3);
            if (this.m_decoration)
            {
                _loc_4 = this.m_decoOffset.x;
                _loc_5 = this.m_decoOffset.y;
                _loc_6 = this.getSize().scale(0.5);
                _loc_4 = _loc_4 - _loc_6.x;
                _loc_5 = _loc_5 - _loc_6.y;
                switch(m_direction)
                {
                    case Constants.DIRECTION_SW:
                    {
                        _loc_7 = _loc_4;
                        _loc_8 = _loc_5;
                        break;
                    }
                    case Constants.DIRECTION_SE:
                    {
                        _loc_7 = -_loc_5;
                        _loc_8 = _loc_4;
                        break;
                    }
                    case Constants.DIRECTION_NW:
                    {
                        _loc_7 = _loc_5;
                        _loc_8 = -_loc_4;
                        break;
                    }
                    case Constants.DIRECTION_NE:
                    {
                        _loc_7 = -_loc_4;
                        _loc_8 = -_loc_5;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                _loc_4 = _loc_7 + _loc_6.x;
                _loc_5 = _loc_8 + _loc_6.y;
                this.m_decoration.setPosition(param1 + _loc_4, param2 + _loc_5, param3);
                this.m_decoration.conditionallyReattach(true);
            }
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            if (this.m_decoration)
            {
                this.m_decoration.rotate();
                this.setPosition(m_position.x, m_position.y, m_position.z);
            }
            Global.world.citySim.roadManager.updateAllRoadTiles();
            Global.world.citySim.roadManager.updateRoads(this);
            return;
        }//end

         public void  rotateToDirection (int param1 )
        {
            super.rotateToDirection(param1);
            if (this.m_decoration)
            {
                this.m_decoration.rotateToDirection(param1);
            }
            return;
        }//end

         public void  attach ()
        {
            super.attach();
            if (this.m_decoration)
            {
                this.m_decoration.attach();
            }
            return;
        }//end

         public void  detach ()
        {
            super.detach();
            if (this.m_decoration)
            {
                this.m_decoration.detach();
            }
            return;
        }//end

         protected void  updateAdjacent ()
        {
            int _loc_1 =0;
            int _loc_6 =0;
            if (m_adjacentRoads != null)
            {
                return;
            }
            super.updateAdjacent();
            for(int i0 = 0; i0 < m_adjacentStretch.size(); i0++)
            {
            		_loc_1 = m_adjacentStretch.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentStretch = new Array();
            for(int i0 = 0; i0 < m_adjacentTight.size(); i0++)
            {
            		_loc_1 = m_adjacentTight.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentTight = new Array();
            _loc_1 = m_direction;
            if (_loc_1 < 0)
            {
                _loc_1 = 0;
            }
            if (_loc_1 >= DIRECTION_MAX)
            {
                _loc_1 = 0;
            }
            _loc_2 = DOWN+_loc_1;
            if (_loc_2 >= DIRECTION_MAX)
            {
                _loc_2 = _loc_2 - DIRECTION_MAX;
            }
            _loc_3 = UP+_loc_1;
            if (_loc_3 >= DIRECTION_MAX)
            {
                _loc_3 = _loc_3 - DIRECTION_MAX;
            }
            _loc_4 = m_adjacentRoads.get(_loc_2);
            if (m_adjacentRoads.get(_loc_2) instanceof ParkingLot)
            {
                _loc_4 = null;
            }
            int _loc_5 =0;
            while (_loc_5 < m_adjacentPartial.length())
            {

                _loc_6 = m_adjacentPartial.get(_loc_5);
                if (_loc_6 == _loc_3)
                {
                    m_adjacentRoads.put(_loc_6,  null);
                    m_adjacentPartial.splice(_loc_5, 1);
                    _loc_5 = _loc_5 - 1;
                }
                else if (_loc_4 && _loc_6 != _loc_2)
                {
                    if (!(m_adjacentRoads.get(_loc_6) instanceof ParkingLot))
                    {
                        m_adjacentRoads.put(_loc_6,  null);
                        m_adjacentPartial.splice(_loc_5, 1);
                        _loc_5 = _loc_5 - 1;
                    }
                }
                _loc_5++;
            }
            _loc_5 = 0;
            while (_loc_5 < m_adjacent.length())
            {

                _loc_6 = m_adjacent.get(_loc_5);
                if (_loc_6 == _loc_3)
                {
                    m_adjacentRoads.put(_loc_6,  null);
                    m_adjacent.splice(_loc_5, 1);
                    _loc_5 = _loc_5 - 1;
                }
                else if (_loc_4 && _loc_6 != _loc_2)
                {
                    if (!(m_adjacentRoads.get(_loc_6) instanceof ParkingLot))
                    {
                        m_adjacentRoads.put(_loc_6,  null);
                        m_adjacent.splice(_loc_5, 1);
                        _loc_5 = _loc_5 - 1;
                    }
                }
                _loc_5++;
            }
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage(m_state,this,m_direction,m_statePhase);
            return _loc_1;
        }//end

         public boolean  cornerShouldAdjust (Road param1 ,int param2 )
        {
            return false;
        }//end

         public boolean  shouldAdjust (Road param1 ,int param2 )
        {
            return false;
        }//end

         public Road  prepareToDrop (Vector3 param1 ,Vector2 param2 )
        {
            return this;
        }//end

         public boolean  isPositionValid ()
        {
            return false;
        }//end

    }



