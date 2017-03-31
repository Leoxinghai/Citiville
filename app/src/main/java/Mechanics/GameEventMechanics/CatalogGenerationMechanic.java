package Mechanics.GameEventMechanics;

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
import Display.MarketUI.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.utils.*;

    public class CatalogGenerationMechanic implements ICatalogMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        private static Array catalogs =new Array();

        public  CatalogGenerationMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            String _loc_5 =null ;
            CatalogParams _loc_6 =null ;
            ItemCatalogUI _loc_7 =null ;
            TabbedCatalogUI _loc_8 =null ;
            int _loc_9 =0;
            String _loc_10 =null ;
            Array _loc_11 =null ;
            Object _loc_12 =null ;
            boolean _loc_13 =false ;
            XMLList _loc_14 =null ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (this.canShowCatalog())
            {
                if (this.m_config.params.get("blockOthers") == "true")
                {
                    _loc_4 = true;
                }
                _loc_5 = this.m_config.params.get("style");
                _loc_6 = new CatalogParams();
                if (_loc_5 == "tabbed")
                {
                    _loc_7 = this.makeCatalog();
                    _loc_8 =(TabbedCatalogUI) _loc_7;
                    _loc_6.type = this.m_config.params.get("catalogType");
                    _loc_6.itemName = this.m_config.params.get("itemName");
                    if (_loc_8.overrideTitle != null && _loc_8.overrideTitle.length > 0)
                    {
                        _loc_6.overrideTitle = _loc_8.overrideTitle;
                    }
                    _loc_9 = this.m_config.params.get("numTabs");
                    _loc_10 = this.m_config.params.get("tabWidth");
                    _loc_8.numTabs = _loc_9;
                    if (_loc_10 == null)
                    {
                        _loc_8.tabWidth = -1;
                    }
                    else if (_loc_10 == "auto")
                    {
                        _loc_8.allowAutoTabWidth = true;
                    }
                    else if (_loc_10 == "default")
                    {
                        _loc_8.tabWidth = -1;
                    }
                    else
                    {
                        _loc_8.tabWidth = int(_loc_10);
                    }
                    _loc_11 = _loc_8.categoryNames != null ? (_loc_8.categoryNames) : (null);
                    UI.clearTabbedCatalog();
                    UI.displayTabbedCatalog(_loc_8, _loc_11, _loc_6);
                }
                else
                {
                    _loc_6.type = this.m_config.params.get("catalogType");
                    _loc_6.itemName = this.m_config.params.get("itemName");
                    _loc_6.exclusiveCategory = this.m_config.params.get("exclusive") == "true";
                    _loc_6.overrideTitle = this.m_config.params.get("overrideTitle");
                    _loc_12 = {};
                    _loc_13 = this.m_config.params.get("inferFromItem") == "true";
                    if (_loc_13)
                    {
                        _loc_6.itemName = this.getInferredItemName();
                        _loc_6.type = this.getInferredCatalogType();
                        _loc_12.put("itemName",  _loc_6.itemName);
                    }
                    _loc_14 = this.m_config.rawXMLConfig.hasOwnProperty("itemList") ? (this.m_config.rawXMLConfig.itemList) : (null);
                    _loc_6.customItems = Global.gameSettings().getItemList(_loc_14, _loc_12);
                    UI.displayCatalog(_loc_6);
                }
                _loc_3 = true;
            }
            return new MechanicActionResult(_loc_3, !_loc_4, false);
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blocksOthers");
        }//end

        public boolean  canShowCatalog ()
        {
            return !(this.m_owner instanceof HarvestableResource && (this.m_owner as HarvestableResource).isHarvestable());
        }//end

        protected ItemCatalogUI  makeCatalog ()
        {
            _loc_1 = getDefinitionByName(this.m_config.params.get( "catalogUI") )as Class ;
            return new _loc_1(this.m_owner, this.m_config);
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Items","warehouse_defaultToolTip");
            return _loc_1;
        }//end

        protected String  getInferredItemName ()
        {
            String _loc_1 ="";
            if (this.m_owner)
            {
                _loc_1 = this.m_owner.getItemName();
            }
            return _loc_1;
        }//end

        protected String  getInferredCatalogType ()
        {
            Item _loc_2 =null ;
            String _loc_1 ="new";
            if (this.m_owner)
            {
                _loc_2 = this.m_owner.getItem();
                if (_loc_2)
                {
                    _loc_1 = _loc_2.type;
                }
            }
            return _loc_1;
        }//end

    }



