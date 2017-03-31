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

import Display.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.types.*;

//import flash.geom.*;

    public class Pier extends Decoration
    {
        private  String PIER ="pier";

        public  Pier (String param1)
        {
            super(param1);
            setState(STATE_STATIC);
            m_typeName = this.PIER;
            m_isHighlightable = false;
            return;
        }//end

        public Array  getCommodityNames ()
        {
            Array _loc_1 =new Array();
            _loc_2 = m_item.commodityXml;
            _loc_1.push(String(_loc_2.@name));
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            XML _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                _loc_1 = super.getToolTipStatus();
                if (_loc_1)
                {
                    return _loc_1;
                }
                _loc_2 = getItem().commodityXml;
                if (_loc_2)
                {
                    _loc_3 = _loc_2.@name;
                    _loc_4 = ZLoc.t("Main", "Commodity_" + _loc_3 + "_friendlyName");
                    _loc_5 = Global.player.commodities.getCapacity(_loc_3);
                    _loc_6 = Global.player.commodities.getCount(_loc_3);
                    _loc_7 = int(_loc_2.@capacity);
                    _loc_1 = _loc_4 + "\n" + ZLoc.t("Main", "AddStorage", {amount:_loc_7});
                }
            }
            return _loc_1;
        }//end

        public double  waterLength ()
        {
            return m_item.waterLength;
        }//end

        public String  landSquares ()
        {
            return m_item.landSquares;
        }//end

        public String  dockSquares ()
        {
            return m_item.dockSquares;
        }//end

        public String  freeShipSquares ()
        {
            return m_item.freeShipSquares;
        }//end

        public String  freeShipType ()
        {
            return m_item.freeShipType;
        }//end

        public String  berthSquares ()
        {
            return m_item.berthSquares;
        }//end

        private boolean  checkCollisionWithDockSquare (double param1 ,double param2 )
        {
            String _loc_5 =null ;
            Array _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            if (m_item.dockSquares == null)
            {
                return false;
            }
            _loc_3 = m_item.dockSquares.split(";");
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = _loc_3.get(_loc_4);
                _loc_6 = _loc_5.split("|");
                _loc_7 = this.m_position.x + int(_loc_6.get(0));
                _loc_8 = this.m_position.y + int(_loc_6.get(1));
                if (param1 == _loc_7 && param2 == _loc_8)
                {
                    return true;
                }
                _loc_4++;
            }
            return false;
        }//end

        public boolean  shipCollidedWithDock (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (this.checkCollisionWithDockSquare(_loc_2, _loc_3))
                    {
                        return true;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return false;
        }//end

        private boolean  checkCollisionWithBerthSquare (double param1 ,double param2 )
        {
            String _loc_5 =null ;
            Array _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            if (m_item.berthSquares == null)
            {
                return false;
            }
            _loc_3 = m_item.berthSquares.split(";");
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = _loc_3.get(_loc_4);
                _loc_6 = _loc_5.split("|");
                _loc_7 = this.m_position.x + int(_loc_6.get(0));
                _loc_8 = this.m_position.y + int(_loc_6.get(1));
                if (param1 == _loc_7 && param2 == _loc_8)
                {
                    return true;
                }
                _loc_4++;
            }
            return false;
        }//end

        public boolean  isValidShipBerth (Rectangle param1 )
        {
            int _loc_3 =0;
            _loc_2 = param1.left;
            while (_loc_2 < param1.left + param1.width)
            {

                _loc_3 = param1.top;
                while (_loc_3 < param1.top + param1.height)
                {

                    if (this.checkCollisionWithBerthSquare(_loc_2, _loc_3))
                    {
                        return true;
                    }
                    _loc_3++;
                }
                _loc_2++;
            }
            return false;
        }//end

        private int  getBerthSquareLayer (double param1 ,double param2 )
        {
            String _loc_5 =null ;
            Array _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            if (m_item.berthSquares == null)
            {
                return -1;
            }
            _loc_3 = m_item.berthSquares.split(";");
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = _loc_3.get(_loc_4);
                _loc_6 = _loc_5.split("|");
                _loc_7 = this.m_position.x + int(_loc_6.get(0));
                _loc_8 = this.m_position.y + int(_loc_6.get(1));
                if (param1 == _loc_7 && param2 == _loc_8)
                {
                    return int(_loc_6.get(2));
                }
                _loc_4++;
            }
            return -1;
        }//end

        public int  getShipLayer (Rectangle param1 )
        {
            int _loc_5 =0;
            int _loc_2 =-1;
            int _loc_3 =-1;
            _loc_4 = param1.left;
            while (_loc_4 < param1.left + param1.width)
            {

                _loc_5 = param1.top;
                while (_loc_5 < param1.top + param1.height)
                {

                    _loc_3 = this.getBerthSquareLayer(_loc_4, _loc_5);
                    if (_loc_3 == 1)
                    {
                        return 1;
                    }
                    if (_loc_3 == 0)
                    {
                        return 0;
                    }
                    _loc_5++;
                }
                _loc_4++;
            }
            if (_loc_3 == -1)
            {
                return 1;
            }
            return _loc_2;
        }//end

         public void  sell ()
        {
            if (m_item)
            {
                if (m_item.cost <= 1)
                {
                    super.sellNow();
                }
                else
                {
                    UI.displayMessage(ZLoc.t("Main", "SellPierSpecific", {coins:getSellPrice()}), GenericPopup.TYPE_YESNO, sellConfirmationHandler);
                }
            }
            else
            {
                super.sell();
            }
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            this.sellAnyBerthedShips();
            return;
        }//end

        private void  sellAnyBerthedShips ()
        {
            Ship _loc_3 =null ;
            Ship _loc_4 =null ;
            _loc_1 = Global.world.getObjectsByClass(Ship);
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                if (!_loc_3.isValidShipPosition())
                {
                    _loc_2.push(_loc_3);
                }
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_4.forceSell();
            }
            return;
        }//end

        public Ship Vector  getAllConnectedShips ().<>
        {
            Ship _loc_3 =null ;
            Vector _loc_1.<Ship >=new Vector<Ship >(0);
            _loc_2 = Global.world.getObjectsByClass(Ship);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.connectedToPierNonExclusive(this))
                {
                    _loc_1.push(_loc_3);
                }
            }
            return _loc_1;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            this.moveAnyStrayShips(param1);
            return;
        }//end

        public void  moveAnyStrayShips (Vector3 param1 )
        {
            Ship _loc_5 =null ;
            Ship _loc_6 =null ;
            Vector3 _loc_2 =new Vector3(param1.x -this.m_position.x ,param1.y -this.m_position.y ,param1.z -this.m_position.z );
            _loc_3 = Global.world.getObjectsByClass(Ship);
            Array _loc_4 =new Array ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                if (!_loc_5.isValidShipPosition())
                {
                    _loc_4.push(_loc_5);
                }
            }
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_6 = _loc_4.get(i0);

                _loc_6.forceMove(_loc_2);
            }
            return;
        }//end

        private void  placeAnyFreeShips ()
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            _loc_1 = m_item.freeShipSquares.split(";");
            _loc_2 = m_item.freeShipType;
            int _loc_3 =0;
            while (_loc_3 < _loc_1.length())
            {

                _loc_4 = _loc_1.get(_loc_3);
                _loc_5 = _loc_4.split("|");
                _loc_6 = this.m_position.x + int(_loc_5.get(0));
                _loc_7 = this.m_position.y + int(_loc_5.get(1));
                _loc_8 = int(_loc_5.get(2));
                Ship.placeFreeShip(_loc_2, _loc_6, _loc_7, _loc_8);
                _loc_3++;
            }
            return;
        }//end

         public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            super.onBuildingConstructionCompleted_PostServerUpdate();
            this.placeAnyFreeShips();
            return;
        }//end

         public boolean  isMoveLocked ()
        {
            Ship _loc_2 =null ;
            _loc_1 = Global.world.getObjectsByClass(Ship);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.connectedToPier(this))
                {
                    return true;
                }
            }
            return false;
        }//end

         public boolean  isPlacedObjectNonBuilding ()
        {
            return false;
        }//end

         public String  GetNoMoveMessage ()
        {
            return ZLoc.t("Main", "NoMovePier");
        }//end

         public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            Ship _loc_5 =null ;
            Rectangle _loc_6 =null ;
            Vector3 _loc_7 =null ;
            Vector3 _loc_8 =null ;
            grantUpgradeRewards(param1);
            super.onUpgrade(param1, param2, param3);
            this.resetUpgradeActionCount();
            _loc_4 = this.getAllConnectedShips();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = _loc_5.getShipRectangle();
                if (this.shipCollidedWithDock(_loc_6))
                {
                    _loc_7 = _loc_5.getPosition();
                    _loc_8 = _loc_7.clone();
                    do
                    {

                        _loc_8.x--;
                        _loc_6.x--;
                    }while (this.shipCollidedWithDock(_loc_6))
                    _loc_5.setPosition(_loc_8.x, _loc_8.y, _loc_8.z);
                    _loc_5.conditionallyReattach();
                    _loc_5.onObjectDrop(_loc_7);
                }
            }
            Global.player.commodities.updateCapacities();
            Global.hud.updateCommodities();
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "pier", "upgrade_to", param2.name);
            return;
        }//end

    }



