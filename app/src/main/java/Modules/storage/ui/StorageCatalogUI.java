package Modules.storage.ui;

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
import Classes.gates.*;
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Modules.storage.*;

//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class StorageCatalogUI extends InventoryCatalogUI
    {
        protected JLabel m_capacityLabel ;
        protected BaseStorageUnit m_storageUnit ;
        public static  int RIGHT_MARGIN =50;
        public static  int TOP_INSET_HEIGHT =50;
        public static  int CAPACITY_TEXT_SIZE =14;
        public static  int SHELF_COLS =5;
        public static  int SHELF_ROWS =2;

        public  StorageCatalogUI ()
        {
            m_catalogTitle = ZLoc.t("Dialogs", "ItemStorage_Title");
            return;
        }//end

         protected Dictionary  assets ()
        {
            return StorageView.assetDict;
        }//end






         public void  init (Catalog param1 ,CatalogParams param2 )
        {
            _loc_3 = param1as StorageView ;
            if (_loc_3.storageKey == "warehouse_deluxe")
            {
                m_doHighlight = true;
            }
            this.m_storageUnit = Global.player.storageComponent.getStorageUnit(_loc_3.storageType, _loc_3.storageKey);
            super.init(param1, param2);
            return;
        }//end

         public void  switchType (String param1 )
        {
            if (shelf != null)
            {
                m_centerPanel.remove(shelf);
            }
            removeListeners();
            _loc_2 = StorageView.getStorageTypeFromCatalogType(param1);
            _loc_3 = StorageView.getStorageKeyFromCatalogType(param1);
            this.m_storageUnit = Global.player.storageComponent.getStorageUnit(_loc_2, _loc_3);
            m_items = this.makeItemsFromStorage();
            setShelf(new StorageScrollingList(m_items, StoredItemCellFactory, 0, SHELF_COLS, SHELF_ROWS));
            ASwingHelper.prepare(shelf);
            m_centerPanel.append(shelf);
            m_centerPanel.setPreferredHeight(SHELF_ROWS * StoredItemCellFactory.getDefaultCellHeight());
            this.updateStorageCapacityText();
            addListeners();
            ASwingHelper.prepare(m_centerPanel);
            return;
        }//end

         protected void  makeTopTabs (Array param1)
        {
            m_topLeftPanel.append(ASwingHelper.horizontalStrut(INVENTORY_CLOSE_BUTTON_WIDTH));
            m_topLeftPanel.append(makeTitlePanel());
            m_topLeftPanel.append(makeCloseButtonPanel());
            m_tabsPanel.append(ASwingHelper.horizontalStrut(10));
            m_tabsPanel.append(this.makeCrewButtonPanel());
            m_tabsPanel.append(ASwingHelper.verticalStrut(36));
            m_topPanel.append(m_topLeftPanel);
            m_topPanel.append(m_tabsPanel);
            m_topPanel.append(ASwingHelper.verticalStrut(5));
            m_topPanel.append(this.makeStorageCapacityText());
            ASwingHelper.prepare(m_topPanel);
            return;
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.storage_bg ()as DisplayObject ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 );
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height + TOP_INSET_HEIGHT));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeCrewButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_1.setPreferredWidth(this.width);
            _loc_1.setMaximumWidth(this.width);
            _loc_2 = ZLoc.t("Dialogs","ItemStorage_AddCrewButton");
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"GreenButtonUI");
            _loc_3.addActionListener(this.onClickCrewButton, 0, false);
            _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(RIGHT_MARGIN));
            return _loc_1;
        }//end

        protected JPanel  makeStorageCapacityText ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            this.m_capacityLabel = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontNameBold, CAPACITY_TEXT_SIZE, 0);
            this.updateStorageCapacityText();
            ASwingHelper.prepare(this.m_capacityLabel);
            _loc_1.appendAll(this.m_capacityLabel, ASwingHelper.horizontalStrut(RIGHT_MARGIN));
            return _loc_1;
        }//end

        protected Array  makeItemsFromStorage ()
        {
            String _loc_7 =null ;
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            int _loc_3 =0;
            _loc_4 = this.m_storageUnit.capacity ;
            _loc_5 = SHELF_COLS*SHELF_ROWS;
            _loc_6 = this.m_storageUnit.getItemNames ();
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                _loc_1.push({item:Global.gameSettings().getItemByName(_loc_7), storage:this.m_storageUnit});
                _loc_2 = _loc_2 + this.m_storageUnit.getItemCountByName(_loc_7);
                _loc_3++;
            }
            while (_loc_2 < _loc_4)
            {

                _loc_1.push({item:StoredItemCellFactory.getDummyOpenItem(), storage:this.m_storageUnit});
                _loc_2++;
                _loc_3++;
            }
            while (_loc_3 % _loc_5 != 0)
            {

                _loc_1.push({item:StoredItemCellFactory.getDummyLockedItem(), storage:this.m_storageUnit});
                _loc_2++;
                _loc_3++;
            }
            return _loc_1;
        }//end

        protected void  onClickCrewButton (Event event =null )
        {
            _loc_2 = (StorageView)m_catalog(
            _loc_2.displayGate();
            this.closeCatalog(null);
            return;
        }//end

        private void  updateStorageCapacityText ()
        {
            _loc_1 = ZLoc.t("Dialogs","ItemStorage_StorageCapacityText",{capacity this.m_storageUnit.capacity ,size.m_storageUnit.size });
            this.m_capacityLabel.setText(_loc_1);
            return;
        }//end

    }



