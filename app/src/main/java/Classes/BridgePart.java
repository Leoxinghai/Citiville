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

    public class BridgePart extends ParkingLot
    {
        private String bridgeParentLocation ;
        protected Bridge m_bridgeOwner ;

        public  BridgePart (String param1)
        {
            super(param1);
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            Bridge _loc_4 =null ;
            Rectangle _loc_5 =null ;
            super.loadObject(param1);
            _loc_2 = Global.world.getObjectsByClass(Bridge);
            Rectangle _loc_3 =new Rectangle(getPosition ().x ,getPosition ().y ,getSize ().x ,getSize ().y );
            if (_loc_2.length())
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_4 = _loc_2.get(i0);

                    if (_loc_4 instanceof Bridge)
                    {
                        _loc_5 = new Rectangle(_loc_4.getPosition().x, _loc_4.getPosition().y - _loc_4.getItem().bridge.bridgeBoundary.y, _loc_4.getItem().bridge.bridgeBoundary.x, _loc_4.getItem().bridge.bridgeBoundary.y);
                        if (_loc_5.intersects(_loc_3))
                        {
                            _loc_4.addBridgePart(this);
                            break;
                        }
                    }
                }
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
            if (m_decoration)
            {
                _loc_4 = m_decoOffset.x;
                _loc_5 = m_decoOffset.y;
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
                m_decoration.setPosition(param1 + _loc_4, param2 + _loc_5, param3);
                m_decoration.conditionallyReattach(true);
            }
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            if (m_decoration)
            {
                m_decoration.rotate();
                this.setPosition(m_position.x, m_position.y, m_position.z);
            }
            Global.world.citySim.roadManager.updateAllRoadTiles();
            Global.world.citySim.roadManager.updateRoads(this);
            return;
        }//end

         protected void  updateAdjacent ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            if (m_adjacentRoads != null)
            {
                return;
            }
            initalUpdateAdjacentCalculations();
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
            _loc_2 = 0;
            while (_loc_2 < m_adjacentRoads.length())
            {

                _loc_3 = _loc_2;
                if (_loc_3 == LEFT || _loc_3 == RIGHT)
                {
                    m_adjacentRoads.put(_loc_3,  null);
                    m_adjacentPartial.splice(_loc_2, 1);
                }
                _loc_2++;
            }
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = m_item.getCachedImage(m_state,this,m_direction,m_statePhase);
            return _loc_1;
        }//end

        public Bridge  bridgeOwner ()
        {
            return this.m_bridgeOwner;
        }//end

        public void  bridgeOwner (Bridge param1 )
        {
            this.m_bridgeOwner = param1;
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return ZLoc.t("Items", "bridge_standard_friendlyName");
        }//end

         public Vector3  getToolTipPosition ()
        {
            if (this.bridgeOwner)
            {
                return this.bridgeOwner.getToolTipPosition();
            }
            return super.getToolTipPosition();
        }//end

         public Vector2  getToolTipScreenOffset ()
        {
            _loc_1 = this.bridgeOwner;
            if (_loc_1)
            {
                return _loc_1.getToolTipScreenOffset();
            }
            return null;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public boolean  canBeRotated ()
        {
            return false;
        }//end

         public boolean  cornerShouldAdjust (Road param1 ,int param2 )
        {
            return false;
        }//end

         public boolean  isSellable ()
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



