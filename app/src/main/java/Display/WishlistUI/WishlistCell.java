package Display.WishlistUI;

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

import Classes.util.*;
import Display.*;
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class WishlistCell extends DataItemCell
    {
        protected JPanel m_itemIconPane ;
        protected JPanel m_removePane ;
        protected JButton m_removeButton ;
        protected IntDimension m_slotSize ;
        protected int m_slotOffset ;
        protected boolean m_initialized =false ;
        protected DisplayObject m_content ;
        protected MarginBackground m_bgDec ;
        public static  int OVERLAP_AMOUNT =28;

        public  WishlistCell (LayoutManager param1 ,IntDimension param2 ,int param3 =2)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            if (param2)
            {
                this.m_slotSize = param2;
            }
            else
            {
                this.m_slotSize = new IntDimension(Catalog.CARD_WIDTH, Catalog.CARD_HEIGHT);
            }
            this.m_slotOffset = param3;
            setPreferredSize(this.m_slotSize);
            return;
        }//end

         protected double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            return Math.min(_loc_2, _loc_3);
        }//end

         public void  setCellValue (Object param1)
        {
            m_item = param1;
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,"icon");
            if (_loc_2 != null)
            {
                m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            }
            else
            {
                this.initializeCell();
            }
            return;
        }//end

         protected void  initializeCell ()
        {
            double _loc_5 =0;
            DisplayObject _loc_6 =null ;
            DisplayObject _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            JPanel _loc_9 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_item.name == "blankItem")
            {
                this.m_content =(DisplayObject) new InventoryView.assetDict.get("inventory_card_locked");
            }
            else
            {
                this.m_content =(DisplayObject) new InventoryView.assetDict.get("inventory_card");
            }
            ASwingHelper.setSizedBackground(_loc_3, this.m_content);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            AssetPane _loc_4 =new AssetPane(m_itemIcon );
            ASwingHelper.prepare(_loc_4);
            if (m_item.name != "blankItem" && m_itemIcon)
            {
                _loc_5 = (this.m_content.width - m_itemIcon.width) / 2;
                ASwingHelper.setEasyBorder(_loc_4, 0, _loc_5);
            }
            this.m_itemIconPane.append(_loc_4);
            ASwingHelper.prepare(this.m_itemIconPane);
            _loc_3.append(this.m_itemIconPane);
            ASwingHelper.prepare(_loc_3);
            _loc_2.append(_loc_3);
            if (m_item.name != "blankItem")
            {
                _loc_6 =(DisplayObject) new InventoryView.assetDict.get("btn_close_small_up");
                _loc_7 =(DisplayObject) new InventoryView.assetDict.get("btn_close_small_over");
                _loc_8 =(DisplayObject) new InventoryView.assetDict.get("btn_close_small_down");
                _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.TOP);
                this.m_removePane = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.RIGHT);
                this.m_removeButton = new JButton();
                this.m_removeButton.wrapSimpleButton(new SimpleButton(_loc_6, _loc_7, _loc_8, _loc_6));
                this.m_removeButton.setFont(ASwingHelper.getBoldFont(12));
                this.m_removeButton.setForeground(new ASColor(16777215));
                this.m_removeButton.filters = .get(new GlowFilter(0));
                this.m_removeButton.addEventListener(MouseEvent.MOUSE_UP, this.onRemove, false, 0, true);
                this.m_removePane.append(this.m_removeButton);
                ASwingHelper.prepare(this.m_removePane);
                _loc_9.append(this.m_removePane);
                ASwingHelper.prepare(_loc_9);
                _loc_2.append(ASwingHelper.horizontalStrut(-_loc_6.width));
                _loc_2.append(_loc_9);
            }
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(WishlistView.GRID_MARGIN));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(WishlistView.GRID_MARGIN));
            ASwingHelper.prepare(_loc_1);
            this.append(_loc_1);
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

        protected void  onRemove (MouseEvent event )
        {
            GameTransactionManager.addTransaction(new TRemoveFromWishlist(m_item.name));
            dispatchEvent(new DataItemEvent(DataItemEvent.WISHLIST_CHANGED, m_item, null, true));
            return;
        }//end

    }



