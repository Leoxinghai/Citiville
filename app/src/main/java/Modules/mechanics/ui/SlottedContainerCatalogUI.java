package Modules.mechanics.ui;

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
import Classes.sim.*;
import Display.*;
import Display.GridlistUI.*;
import Display.MarketUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.MarketUI.model.*;
import Modules.mechanics.MarketUI.view.*;
import Modules.mechanics.ui.items.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class SlottedContainerCatalogUI extends MechanicTabbedCatalogUI
    {
        protected SlottedContainerConfig m_config =null ;

        public  SlottedContainerCatalogUI (IMechanicUser param1 ,SlottedContainerConfig param2 )
        {
            super(param1);
            this.m_config = param2;
            this.overrideTitle = this.m_config.overrideTitle;
            Global.ui.addEventListener(GenericObjectEvent.OBJECT_STORED, this.onObjectStored, false, 0, true);
            Global.ui.addEventListener(GenericObjectEvent.OBJECT_PLACED_FROM_STORAGE, this.onPlacedFromStorage, false, 0, true);
            Global.ui.addEventListener(GenericObjectEvent.CREW_PURCHASE, this.onRefresh, false, 0, true);
            Global.ui.addEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.onRefreshTabs, false, 0, true);
            Global.ui.addEventListener(GenericObjectEvent.STORED_ITEM_UPGRADE, this.onStoredItemUpgrade, false, 0, true);
            Global.ui.addEventListener(GenericObjectEvent.STORED_ITEM_SUPPLY, this.onStoredItemSupply, false, 0, true);
            return;
        }//end

         public void  init (Catalog param1 ,CatalogParams param2 )
        {
            super.init(param1, param2);
            _loc_3 = this.m_config.storageMechanic ;
            _loc_4 = (IStorage)MechanicManager.getInstance().getMechanicInstance(m_generator,_loc_3.type,_loc_3.mode)
            _loc_5 = m_generator.getDataForMechanic(_loc_3.type);
            _loc_6 = this.m_config.slotsPerTab ;
            _loc_7 = _loc_5.length ;
            _loc_8 = _loc_4.capacity;
            _loc_9 = Math.min(_loc_7 ,(_loc_8 -1))/_loc_6 ;
            this.setCurrentTabIndex(_loc_9);
            return;
        }//end

         protected DisplayObject  getTabBackground (String param1 )
        {
            DisplayObject _loc_2 =null ;
            if (!this.isTabLocked(param1))
            {
                _loc_2 = super.getTabBackground(param1);
            }
            else
            {
                _loc_2 = new TabbedCatalog.assetDict.get("tab_locked");
            }
            return _loc_2;
        }//end

        protected boolean  isTabLocked (String param1 )
        {
            _loc_2 = (ItemInstance)m_generator(
            _loc_3 = UpgradeDefinition.getFullUpgradeChainNames(Item.findUpgradeRoot(_loc_2)).indexOf(_loc_2.name);
            _loc_4 = _loc_3>=getIndexFromTabId(param1 );
            return _loc_3 < getIndexFromTabId(param1);
        }//end

         protected Array  loadItemsForTab (int param1 )
        {
            boolean _loc_14 =false ;
            Component _loc_15 =null ;
            Component _loc_16 =null ;
            Component _loc_17 =null ;
            ItemInstance _loc_18 =null ;
            ISlottedContainer _loc_19 =null ;
            String _loc_20 =null ;
            int _loc_21 =0;
            Component _loc_22 =null ;
            _loc_2 = this.m_config.storageMechanic ;
            _loc_3 = (IStorage)MechanicManager.getInstance().getMechanicInstance(m_generator,_loc_2.type,_loc_2.mode)
            _loc_4 = m_generator.getDataForMechanic(_loc_2.type);
            _loc_5 = this.m_config.slotsPerTab ;
            Array _loc_6 =new Array ();
            _loc_7 = param1*_loc_5 ;
            _loc_8 = param1(+1)*_loc_5 ;
            _loc_9 = _loc_7;
            while (_loc_9 < _loc_8)
            {

                if (_loc_9 >= _loc_4.length())
                {
                    break;
                }
                _loc_6.push(_loc_4.get(_loc_9));
                _loc_9++;
            }
            Array _loc_10 =new Array ();
            _loc_11 = _loc_3.capacity;
            _loc_12 = _loc_3.capacity-param1*_loc_5;
            int _loc_13 =0;
            while (_loc_13 < _loc_5)
            {

                _loc_14 = true;
                if ((_loc_13 + 1) == _loc_5)
                {
                    _loc_14 = false;
                }
                if (_loc_13 < _loc_6.length && _loc_13 < _loc_12)
                {
                    _loc_15 = new SlottedContainerSlotCellView(m_generator, this.m_config, _loc_13, param1, _loc_6.get(_loc_13), _loc_14);
                    _loc_10.push(new GenericGridListData(_loc_6.get(_loc_13), _loc_15));
                }
                else if (_loc_13 < _loc_12)
                {
                    _loc_16 = new SlottedContainerSlotCellView(m_generator, this.m_config, _loc_13, param1, null, _loc_14);
                    _loc_10.push(new GenericGridListData(null, _loc_16));
                }
                else
                {
                    _loc_17 = new SlottedContainerCrewableCellView(m_generator, this.m_config, _loc_13, _loc_14);
                    _loc_10.push(new GenericGridListData(null, _loc_17));
                }
                _loc_13++;
            }
            if (this.m_config.hasMysteryItem())
            {
                _loc_18 = null;
                _loc_19 =(ISlottedContainer) m_generator;
                _loc_20 = _loc_19.getMysteryItemName(param1);
                if (_loc_19.hasMysteryItemInInventory(param1))
                {
                    _loc_18 = new ItemInstance(_loc_20);
                }
                _loc_21 = _loc_5;
                _loc_22 = new SlottedContainerMysteryCellView(m_generator, this.m_config, _loc_21, param1);
                _loc_10.push(new GenericGridListData(_loc_18, _loc_22));
            }
            return _loc_10;
        }//end

         protected void  changeCategoryFromTab (MouseEvent event )
        {
            MechanicItem _loc_3 =null ;
            UpgradeMechanic _loc_4 =null ;
            _loc_2 = event.currentTarget.name ;
            if (!this.isTabLocked(_loc_2))
            {
                super.changeCategoryFromTab(event);
            }
            else
            {
                _loc_3 = this.m_config.upgradeMechanic;
                _loc_4 =(UpgradeMechanic) MechanicManager.getInstance().getMechanicInstance(m_generator, _loc_3.type, _loc_3.mode);
                if (_loc_4)
                {
                    _loc_4.doDisplayGate();
                }
            }
            return;
        }//end

         protected void  showToolTip (MouseEvent event )
        {
            m_tabsDict.get(_loc_2 = event.currentTarget as JPanel) ;
            _loc_3 = getIndexFromTabId(_loc_2);
            _loc_4 = (JPanel)event.currentTarget
            _loc_5 = (JPanel)event(.currentTarget
            Point _loc_6 =new Point(_loc_5.x ,_loc_5.y );
            int _loc_7 =0;
            _loc_8 = (ISlottedContainer)m_generator
            if ((ISlottedContainer)m_generator)
            {
                _loc_7 = _loc_8.getInitialHarvestBonus(_loc_3);
            }
            _loc_9 = this.m_config.getTooltipItem("tab");
            Object _loc_10 =new Object ();
            _loc_10.put("type",  _loc_9.type);
            _loc_10.put("title",  ZLoc.t(_loc_9.title.pkg, _loc_9.title.key + _loc_3));
            _loc_10.put("description",  ZLoc.t(_loc_9.text.pkg, _loc_9.text.key + _loc_3, {bonus:_loc_7}));
            if (_loc_3 > 0)
            {
                _loc_10.put("requirement",  ZLoc.t(_loc_9.gate.pkg, _loc_9.gate.key + _loc_3));
            }
            Point _loc_11 =new Point(47,-55);
            DataItemEvent _loc_12 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,null ,_loc_6 ,true );
            _loc_12.setParams(_loc_10);
            _loc_12.setOffset(_loc_11);
            dispatchEvent(_loc_12);
            return;
        }//end

         protected void  hideToolTip (MouseEvent event )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

         public String  getTabbedCatalogKey ()
        {
            return this.m_config.key;
        }//end

         protected MarketScrollingList  makeShelf (Array param1 )
        {
            _loc_2 = SlottedContainerCellModel;
            SlottedContainerScrollingList _loc_3 =new SlottedContainerScrollingList(param1 ,_loc_2 ,this.m_config ,0,7,1);
            return _loc_3;
        }//end

         public void  goToItem (String param1 )
        {
            return;
        }//end

         public String  getLocalizedTabName (int param1 )
        {
            _loc_2 = this.m_config.getTitleZlocItem("tab");
            return ZLoc.t(_loc_2.pkg, _loc_2.key + param1);
        }//end

        private void  onObjectStored (GenericObjectEvent event )
        {
            refreshShelf();
            return;
        }//end

        private void  onPlacedFromStorage (GenericObjectEvent event )
        {
            refreshShelf();
            return;
        }//end

        private void  onRefresh (GenericObjectEvent event )
        {
            refreshShelf();
            return;
        }//end

        private void  onStoredItemUpgrade (GenericObjectEvent event )
        {
            refreshShelf();
            return;
        }//end

        private void  onStoredItemSupply (GenericObjectEvent event )
        {
            refreshShelf();
            return;
        }//end

        private void  onRefreshTabs (GenericObjectEvent event )
        {
            refreshTabs();
            return;
        }//end

         protected void  closeCatalog (AWEvent event )
        {
            super.closeCatalog(event);
            UI.clearTabbedCatalog();
            Global.ui.removeEventListener(GenericObjectEvent.OBJECT_STORED, this.onObjectStored);
            Global.ui.removeEventListener(GenericObjectEvent.OBJECT_PLACED_FROM_STORAGE, this.onPlacedFromStorage);
            Global.ui.removeEventListener(GenericObjectEvent.CREW_PURCHASE, this.onRefresh);
            Global.ui.removeEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.onRefreshTabs);
            Global.ui.removeEventListener(GenericObjectEvent.STORED_ITEM_UPGRADE, this.onStoredItemUpgrade);
            Global.ui.removeEventListener(GenericObjectEvent.STORED_ITEM_SUPPLY, this.onStoredItemSupply);
            Global.world.setDefaultGameMode();
            return;
        }//end

         protected void  trackPageView (String param1 ,String param2 ,int param3 )
        {
            _loc_4 = this"tab"+.getCurrentTabIndex ();
            StatsManager.sample(100, "market_views", this.m_config.key, _loc_4);
            return;
        }//end

    }



