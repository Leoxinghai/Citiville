package Display.InventoryUI;

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
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class InventoryCell extends MarketCell
    {
        protected JPanel m_countPane ;
        protected JLabel m_countLabel ;
        protected JButton m_useButton ;
        protected JButton m_giftButton ;
        protected JPanel m_overlapPane ;
        protected boolean m_initialized =false ;
        protected JPanel m_bottomPane ;
        protected JPanel m_removePane ;
        protected JButton m_removeButton ;
        public static  int COUNT_PANE_SIZE =20;
        public static  int OVERLAP_AMOUNT =28;
        public static  int BOTTOM_GAP =1;
        public static  int BOTTOM_GAP_WISHLIST =22;
        public static  int BOTTOM_GAP_WISHLIST_USEBUTTON =51;
        public static  int BOTTOM_GAP_GIFT =22;
        public static  int CELL_BORDER_GAP =6;

        public  InventoryCell (LayoutManager param1)
        {
            super(param1);
            this.setAssetDict(InventoryView.assetDict);
            Global.stage.addEventListener(DataItemEvent.WISHLIST_CHANGED, this.onWishlistChanged, false, 1, true);
            return;
        }//end

         protected void  buildCell ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (m_item.unlock == "level")
            {
                if (Global.player.level < m_item.requiredLevel)
                {
                    m_itemLocked = true;
                }
                else
                {
                    m_itemLocked = false;
                }
            }
            else if (m_item.unlock == "neighbors")
            {
            }
            m_content =(DisplayObject) new m_assetDict.get("inventory_card");
            this.setPreferredWidth(m_content.width + CELL_BORDER_GAP * 2);
            this.setMinimumWidth(m_content.width + CELL_BORDER_GAP * 2);
            this.setMaximumWidth(m_content.width + CELL_BORDER_GAP * 2);
            m_bgDec = new MarginBackground(m_content);
            m_alignmentContainer.setBackgroundDecorator(m_bgDec);
            m_alignmentContainer.setPreferredSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMinimumSize(new IntDimension(m_content.width, m_content.height));
            m_alignmentContainer.setMaximumSize(new IntDimension(m_content.width, m_content.height));
            this.m_overlapPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM, -OVERLAP_AMOUNT);
            m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_alignmentContainer.append(m_itemIconPane);
            ASwingHelper.prepare(m_alignmentContainer);
            _loc_1.append(m_alignmentContainer);
            if (!m_item.persistent)
            {
                _loc_2 = this.makeRemoveButtonPanel();
                if (_loc_2)
                {
                    _loc_1.append(ASwingHelper.horizontalStrut(-_loc_2.width));
                    _loc_1.append(_loc_2);
                }
            }
            this.m_overlapPane.append(_loc_1);
            this.m_bottomPane = this.makeBottomPane();
            this.m_overlapPane.append(this.m_bottomPane);
            ASwingHelper.prepare(this.m_overlapPane);
            this.append(this.m_overlapPane);
            this.append(this.makeNameAndSetPane());
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

        protected JPanel  makeRemoveButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.TOP );
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "btn_close_small_up");
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "btn_close_small_over");
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "btn_close_small_down");
            this.m_removePane = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.RIGHT);
            this.m_removeButton = new JButton();
            this.m_removeButton.wrapSimpleButton(new SimpleButton(_loc_2, _loc_3, _loc_4, _loc_2));
            this.m_removeButton.setFont(ASwingHelper.getBoldFont(12));
            this.m_removeButton.setForeground(new ASColor(16777215));
            this.m_removeButton.filters = .get(new GlowFilter(0));
            this.m_removeButton.addEventListener(MouseEvent.MOUSE_UP, this.onRemove, false, 0, true);
            this.m_removePane.append(this.m_removeButton);
            ASwingHelper.prepare(this.m_removePane);
            _loc_1.append(this.m_removePane);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeNameAndSetPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = TextFieldUtil.getLocaleFontSize(12,12,.get( {locale size "ja",9) });
            _loc_3 = ASwingHelper.makeTextField("",EmbeddedArt.defaultFontNameBold ,_loc_2 ,2645358);
            _loc_3.setText(m_item.localizedName);
            _loc_1.append(_loc_3);
            _loc_4 =Global.gameSettings().getItemByName(m_item.name ).getSet ();
            if (Global.gameSettings().getItemByName(m_item.name).getSet())
            {
                m_setIcon = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_1.append(m_setIcon);
            }
            return _loc_1;
        }//end

        protected JPanel  makeBottomPane ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            this.m_useButton = this.makeUseButton();
            if (this.m_useButton)
            {
                _loc_1.append(this.m_useButton, BorderLayout.WEST);
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.appendAll(ASwingHelper.verticalStrut(10), this.makeCountPane());
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 5);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            ASwingHelper.setEasyBorder(_loc_1, 0, 5, 5, 5);
            return _loc_1;
        }//end

        protected JButton  makeGiftButton ()
        {
            this.m_giftButton = new JButton("");
            this.m_giftButton.setForeground(new ASColor(16777215));
            DisplayObject _loc_1 =(DisplayObject)new m_assetDict.get( "gift_up");
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "gift_down");
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "gift_over");
            this.m_giftButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            this.m_giftButton.addActionListener(this.giftItem, 0, true);
            return this.m_giftButton;
        }//end

        protected JButton  makeUseButton ()
        {
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            JButton _loc_1 =new JButton("");
            _loc_1.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, 16));
            _loc_1.setForeground(new ASColor(16777215));
            if (this.allowUseButton())
            {
                this.m_overlapPane.addEventListener(MouseEvent.CLICK, this.useItem, false, 0, true);
                return null;
            }
            if (this.allowWishlistButton())
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("add_to_wishlist");
            }
            else
            {
                return null;
            }
            _loc_1.wrapSimpleButton(new SimpleButton(_loc_2, _loc_2, _loc_2, _loc_2));
            _loc_1.addActionListener(this.wishItem, 0, true);
            return _loc_1;
        }//end

        protected JPanel  makeCountPane ()
        {
            DisplayObject _loc_2 =null ;
            String _loc_3 =null ;
            this.m_countPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            int _loc_4 =16777215;
            if (m_item.inventoryLimit != -1)
            {
                _loc_3 = String(this.getCount()) + "/" + String(m_item.inventoryLimit);
                if (this.getCount() == m_item.inventoryLimit)
                {
                    _loc_2 =(DisplayObject) new m_assetDict.get("countMaxBG");
                }
                else
                {
                    _loc_2 =(DisplayObject) new m_assetDict.get("countBG");
                }
            }
            else
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("countBG");
                _loc_3 = String(this.getCount());
            }
            this.m_countPane.setBackgroundDecorator(new AssetBackground(_loc_2));
            this.m_countLabel = ASwingHelper.makeLabel(_loc_3, EmbeddedArt.defaultFontNameBold, 10, _loc_4);
            if (m_item.inventoryLimit == -1)
            {
                ASwingHelper.setEasyBorder(this.m_countLabel, 0, 3, 0, 3);
            }
            _loc_1.append(this.m_countLabel);
            this.m_countPane.appendAll(_loc_1);
            ASwingHelper.prepare(this.m_countPane);
            return this.m_countPane;
        }//end

        protected int  getCount ()
        {
            return Global.player.inventory.getItemCount(m_item);
        }//end

        protected boolean  allowUseButton ()
        {
            _loc_1 = boolean(m_item.placeable|| m_item.consumable);
            return _loc_1;
        }//end

        protected boolean  allowWishlistButton ()
        {
            return Global.player.canAddToWishlist(m_item.name);
        }//end

        private void  useItem (MouseEvent event )
        {
            if (event.target instanceof SimpleButton)
            {
                return;
            }
            dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, this.m_item, null, true));
            this.invalidateData();
            return;
        }//end

        private void  wishItem (AWEvent event )
        {
            if (Global.player.canAddToWishlist(m_item.name))
            {
                GameTransactionManager.addTransaction(new TAddToWishlist(m_item.name));
                dispatchEvent(new DataItemEvent(DataItemEvent.WISHLIST_CHANGED, this.m_item, null, true));
            }
            return;
        }//end

        private void  closeInventory ()
        {
            DisplayObject _loc_2 =null ;
            boolean _loc_3 =false ;
            InventoryView _loc_4 =null ;
            _loc_1 =Global.ui.numChildren ;
            while (_loc_1--)
            {

                _loc_2 = Global.ui.getChildAt(_loc_1);
                _loc_3 = Boolean(_loc_2 instanceof InventoryView);
                if (_loc_3)
                {
                    _loc_4 =(InventoryView) _loc_2;
                    _loc_4.close();
                    _loc_4.allowClose();
                    break;
                }
            }
            return;
        }//end

        private void  giftItem (AWEvent event )
        {
            return;
        }//end

         protected void  initializeCell ()
        {
            if (!m_itemIcon)
            {
                return;
            }
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_2 =new AssetPane(m_itemIcon );
            if (this.getCount() == 0)
            {
                _loc_2.alpha = 0.5;
            }
            else
            {
                _loc_2.alpha = 1;
            }
            _loc_3 = m_content(.width-m_itemIcon.width)/2;
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_3));
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            m_itemIconPane.append(_loc_1);
            return;
        }//end

        public void  invalidateData ()
        {
            String _loc_1 =null ;
            if (Global.ui.inventoryView)
            {
                Global.ui.inventoryView.refreshInventoryLimit();
            }
            if (this.m_initialized)
            {
                if (m_item.inventoryLimit != -1)
                {
                    _loc_1 = String(this.getCount()) + "/" + String(m_item.inventoryLimit);
                }
                else
                {
                    _loc_1 = String(this.getCount());
                }
                this.m_countLabel.setText(_loc_1);
            }
            return;
        }//end

         public void  performUpdate (boolean param1 =false )
        {
            this.removeAll();
            this.buildCell();
            this.initializeCell();
            return;
        }//end

        protected void  onWishlistChanged (DataItemEvent event )
        {
            if (event.item.name == m_item.name)
            {
                this.performUpdate();
            }
            return;
        }//end

        protected void  onRemove (MouseEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            RemoveItemDialog _loc_2 =new RemoveItemDialog(m_item.name ,this.onRemoveConfirm );
            UI.displayPopup(_loc_2, false, "", true);
            return;
        }//end

        protected void  onRemoveConfirm (GenericPopupEvent event )
        {
            if (!(event instanceof RemoveItemEvent))
            {
                return;
            }
            _loc_2 = event(as RemoveItemEvent ).itemName ;
            _loc_3 = event(as RemoveItemEvent ).itemCount ;
            Global.player.inventory.removeItems(_loc_2, _loc_3, false);
            TransactionManager.addTransaction(new TRemoveFromInventory(_loc_2, _loc_3));
            if (Global.ui.inventoryView)
            {
                Global.ui.inventoryView.refreshInventory();
            }
            return;
        }//end

    }



