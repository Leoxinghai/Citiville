package Classes.inventory;

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
import Display.*;
import Display.DialogUI.*;
import Engine.Classes.*;
import GameMode.*;

//import flash.utils.*;

    public class Commodities extends BaseInventory
    {
        protected Dictionary m_capacities ;
        public static  String GOODS_COMMODITY ="goods";
        public static  String PREMIUM_GOODS_COMMODITY ="premium_goods";

        public  Commodities (Object param1 )
        {
            this.m_capacities = new Dictionary();
            this.updateCapacities();
            super(param1);
            return;
        }//end

         public boolean  isValidName (String param1 )
        {
            _loc_2 =Global.gameSettings().getCommodityXMLByName(param1 );
            return _loc_2 != null;
        }//end

         public int  getCapacity (String param1 )
        {
            return int(this.m_capacities.get(param1));
        }//end

        public void  updateCapacities ()
        {
            MapResource _loc_3 =null ;
            XMLList _loc_4 =null ;
            XML _loc_5 =null ;
            boolean _loc_6 =false ;
            String _loc_7 =null ;
            XML _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_10 =0;
            _loc_1 = this.m_capacities;
            this.m_capacities = new Dictionary();
            _loc_2 = Global.world.getObjectsByPredicate(Commodities.isCommodityStorage);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_8 = _loc_3.getItem().commodityXml;
                if (_loc_8)
                {
                    _loc_9 = _loc_8.@name;
                    if (this.m_capacities.get(_loc_9) == null)
                    {
                        this.m_capacities.put(_loc_9,  0);
                    }
                    _loc_10 = int(_loc_8.@capacity);
                    this.m_capacities.put(_loc_9,  this.m_capacities.get(_loc_9) + _loc_10);
                }
            }
            _loc_4 = Global.gameSettings().getCommodityXML();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_9 = String(_loc_5.@name);
                if (this.m_capacities.get(_loc_9) == null)
                {
                    this.m_capacities.put(_loc_9,  0);
                }
                if (_loc_5.@initCap)
                {
                    this.m_capacities.put(_loc_9,  this.m_capacities.get(_loc_9) + parseInt(_loc_5.@initCap));
                }
                if (m_storage && m_storage.get(_loc_9) && m_storage.get(_loc_9) > this.getCapacity(_loc_9))
                {
                    m_storage.put(_loc_9,  this.getCapacity(_loc_9));
                }
            }
            _loc_6 = false;
            for(int i0 = 0; i0 < this.m_capacities.size(); i0++)
            {
            		_loc_7 = this.m_capacities.get(i0);

                if (_loc_1.hasOwnProperty(_loc_7))
                {
                    if (_loc_1.get(_loc_7) != this.m_capacities.get(_loc_7))
                    {
                        _loc_6 = true;
                        break;
                    }
                    continue;
                }
                _loc_6 = true;
                break;
            }
            if (_loc_6)
            {
                this.onDataChange(_loc_9);
            }
            return;
        }//end

         protected void  onDataChange (String param1 )
        {
            Global.hud.updateCommodities();
            Global.player.commodityNotifyObservers(param1);
            return;
        }//end

        public Array  getCommodityNames ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < this.m_capacities.size(); i0++)
            {
            		_loc_2 = this.m_capacities.get(i0);

                _loc_1.push(_loc_2);
            }
            return _loc_1;
        }//end

         public boolean  add (String param1 ,int param2 )
        {
            if (!this.isValidName(param1) || param2 <= 0)
            {
                return false;
            }
            if (m_storage.get(param1) == null)
            {
                m_storage.put(param1,  0);
            }
            m_storage.put(param1,  m_storage.get(param1) + param2);
            if (m_storage.get(param1) > this.getCapacity(param1))
            {
                m_storage.put(param1,  this.getCapacity(param1));
            }
            this.onDataChange(param1);
            return true;
        }//end

        public boolean  isAtCommodityCapacity (Item param1 ,boolean param2 =true )
        {
            StorageDialog _loc_6 =null ;
            _loc_3 = param1.commodityXml;
            _loc_4 = _loc_3.@name;
            _loc_5 = Global.isVisiting()? (Global.gameSettings().getInt("friendHelpDefaultGoodsReward", 1)) : (parseInt(_loc_3.@produces) + Global.player.GetDooberMinimums(param1, _loc_4));
            if (_loc_4 == Commodities.PREMIUM_GOODS_COMMODITY)
            {
                return false;
            }
            if (this.getCapacity(_loc_4) <= getCount(_loc_4) || getSpareCapacity(_loc_4) < _loc_5)
            {
                if (param2)
                {
                    _loc_6 = new StorageDialog(StorageDialog.TYPE_NEED_MORE_STORAGE);
                    UI.displayPopup(_loc_6);
                }
                return true;
            }
            return false;
        }//end

        public boolean  isAtCommodityCapacityForBulkCollection (Vector param1 .<Item >,boolean param2 =true )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            Item _loc_7 =null ;
            StorageDialog _loc_8 =null ;
            Object _loc_6 =new Object ();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_7 = param1.get(i0);

                _loc_3 = _loc_7.commodityXml;
                _loc_4 = _loc_3.@name;
                _loc_5 = Global.isVisiting() ? (Global.gameSettings().getInt("friendHelpDefaultGoodsReward", 1)) : (parseInt(_loc_3.@produces) + Global.player.GetDooberMinimums(_loc_7, _loc_4));
                if (_loc_6.hasOwnProperty(_loc_4))
                {
                    _loc_6.put(_loc_4,  _loc_6.get(_loc_4) + _loc_5);
                    continue;
                }
                _loc_6.put(_loc_4,  _loc_5);
            }
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_4 = _loc_6.get(i0);

                _loc_5 = _loc_6.get(_loc_4);
                if (this.getCapacity(_loc_4) <= getCount(_loc_4) || getSpareCapacity(_loc_4) < _loc_5)
                {
                    if (param2)
                    {
                        _loc_8 = new StorageDialog(StorageDialog.TYPE_NEED_MORE_STORAGE);
                        UI.displayPopup(_loc_8);
                    }
                    return true;
                }
            }
            return false;
        }//end

        public static boolean  isCommodityStorage (WorldObject param1 )
        {
            MapResource _loc_2 =null ;
            if (Global.world.getTopGameMode() instanceof GMPlaceMapResource)
            {
                _loc_2 = ((GMPlaceMapResource)Global.world.getTopGameMode()).displayedMapResource;
            }
            return param1 instanceof Pier && param1 != _loc_2 || param1 instanceof Storage && param1 != _loc_2;
        }//end


        public static String  getNoCommoditiesTooltip ()
        {
            String _loc_1 ="";
            _loc_1 = ZLoc.t("Main", "NoGoods");
            return _loc_1;
        }//end

    }



