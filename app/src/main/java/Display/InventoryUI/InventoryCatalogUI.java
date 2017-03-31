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
import Display.*;
import Display.MarketUI.*;
import Display.WishlistUI.*;
import Display.aswingui.*;
import GameMode.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class InventoryCatalogUI extends ItemCatalogUI implements ICatalogUI
    {
        protected String m_catalogTitle ;
        protected boolean m_doHighlight =false ;
        protected WishlistView m_wishlistPane ;
        public static  int CATALOG_TOTAL_WIDTH =560;
        public static  int INVENTORY_TITLE_WIDTH =490;
        public static  int INVENTORY_CLOSE_BUTTON_WIDTH =36;
        public static  int TOP_INSET_HEIGHT =0;
        public static  int CENTER_PANEL_MARGIN =25;
        public static  double INVENTORY_TITLE_FONT_SIZE =32;

        public  InventoryCatalogUI ()
        {
            boolean _loc_1 =true ;
            this.mouseChildren = true;
            this.mouseEnabled = _loc_1;
            this.m_catalogTitle = ZLoc.t("Dialogs", "InventoryTitle");
            return;
        }//end

        protected Dictionary  assets ()
        {
            return InventoryView.assetDict;
        }//end

        protected int  wishListGap ()
        {
            return 16;
        }//end

         public void  init (Catalog param1 ,CatalogParams param2 )
        {
            this.makeBackground();
            this.m_containerWidth = CATALOG_TOTAL_WIDTH;
            this.m_catalog = param1;
            this.m_type = param2.type;
            m_topPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            m_topLeftPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_tabsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            m_centerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.makeTopTabs();
            this.append(m_topPanel);
            this.append(ASwingHelper.verticalStrut(this.wishListGap));
            this.append(m_centerPanel);
            ASwingHelper.prepare(this);
            m_catalog.addEventListener(MouseEvent.MOUSE_DOWN, this.onInventoryDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_MOVE, this.onInventoryDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OVER, this.onInventoryOver, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OUT, this.onInventoryOut, false, 0, true);
            this.switchType(param2.type);
            addListeners();
            return;
        }//end

         protected void  makeTopTabs (Array param1)
        {
            m_topLeftPanel.append(ASwingHelper.horizontalStrut(INVENTORY_CLOSE_BUTTON_WIDTH));
            m_topLeftPanel.append(this.makeTitlePanel());
            m_topLeftPanel.append(this.makeCloseButtonPanel());
            m_tabsPanel.append(ASwingHelper.horizontalStrut(this.wishListTab));
            m_tabsPanel.append(this.makeWishlistPanel());
            m_topPanel.append(ASwingHelper.verticalStrut(-5));
            m_topPanel.append(m_topLeftPanel);
            double _loc_2 =-15;
            if (Global.localizer.langCode == "ja")
            {
                _loc_2 = -5;
            }
            m_topPanel.append(ASwingHelper.verticalStrut(_loc_2));
            m_topPanel.append(m_tabsPanel);
            ASwingHelper.prepare(m_topPanel);
            return;
        }//end

        protected int  wishListTab ()
        {
            return 40;
        }//end

        protected boolean  showInventory ()
        {
            return true;
        }//end

        protected JPanel  makeWishlistPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_wishlistPane = new WishlistView(this.showInventory);
            ASwingHelper.prepare(this.m_wishlistPane);
            _loc_1.append(this.m_wishlistPane);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeCloseButtonPanel ()
        {
            DisplayObject _loc_4 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 =(DisplayObject) new this.assets.get( "btn_close_big_up");
            _loc_3 =(DisplayObject) new this.assets.get( "btn_close_big_down");
            _loc_4 =(DisplayObject) new this.assets.get("btn_close_big_over");
            m_closeButton = new JButton();
            m_closeButton.wrapSimpleButton(new SimpleButton(_loc_2, _loc_4, _loc_3, _loc_2));
            m_closeButton.addActionListener(closeCatalog, 0, true);
            _loc_1.append(ASwingHelper.verticalStrut(this.closeButtonGapFromTop));
            _loc_1.append(m_closeButton);
            return _loc_1;
        }//end

        protected int  closeButtonGapFromTop ()
        {
            return 5;
        }//end

        protected JPanel  makeTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = INVENTORY_TITLE_FONT_SIZE;
            _loc_3 = ASwingHelper.makeTextField(this.m_catalogTitle ,EmbeddedArt.titleFont ,_loc_2 ,16508714);
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.align = TextFormatAlign.CENTER;
            _loc_4.size = INVENTORY_TITLE_FONT_SIZE + 12;
            TextFieldUtil.formatSmallCaps(_loc_3.getTextField(), _loc_4);
            _loc_3.setPreferredWidth(this.titleWidth);
            if (this.m_doHighlight)
            {
                _loc_3.filters = EmbeddedArt.newtitleFilters;
            }
            else
            {
                _loc_3.filters = .get(new GlowFilter(33724, 1, 6, 6, 6));
            }
            _loc_1.append(_loc_3, SoftBoxLayout.CENTER);
            return _loc_1;
        }//end

        protected int  titleWidth ()
        {
            return INVENTORY_TITLE_WIDTH;
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.inventory_bg ()as DisplayObject ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(TOP_INSET_HEIGHT ));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height + TOP_INSET_HEIGHT));
            return;
        }//end

         public void  switchType (String param1 )
        {
            if (shelf != null)
            {
                m_centerPanel.remove(shelf);
            }
            this.m_items = Global.player.inventory.getItems();
            setShelf(new InventoryScrollingList(this.m_items, InventoryCellFactory, 0, 5, 2));
            ASwingHelper.prepare(shelf);
            m_centerPanel.append(shelf);
            m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT * 2 + CENTER_PANEL_MARGIN + 36);
            ASwingHelper.prepare(m_centerPanel);
            return;
        }//end

         protected double  layoutGap ()
        {
            return 0;
        }//end

        protected void  onInventoryDown (MouseEvent event )
        {
            GMDefault _loc_6 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            Point _loc_3 =new Point(Global.ui.mouseX ,Global.ui.mouseY );
            _loc_4 =Global.ui.m_bottomUI.getObjectsUnderPoint(_loc_3 );
            int _loc_5 =0;
            _loc_5 = 0;
            while (_loc_5 < _loc_4.length())
            {

                if (_loc_4.get(_loc_5) instanceof InventoryCatalogUI)
                {
                    if (_loc_2 instanceof GMDefault)
                    {
                        _loc_6 =(GMDefault) _loc_2;
                        _loc_6.turnOffObject();
                    }
                    event.stopImmediatePropagation();
                    event.stopPropagation();
                }
                _loc_5++;
            }
            return;
        }//end

        protected void  onInventoryOver (MouseEvent event )
        {
            UI.pushBlankCursor();
            return;
        }//end

        protected void  onInventoryOut (MouseEvent event )
        {
            UI.popBlankCursor();
            return;
        }//end

         public void  updateElements ()
        {
            shelf.updateElements();
            this.m_wishlistPane.refreshInventoryLimit();
            return;
        }//end

         public void  updatePriceMultiplier (double param1 )
        {
            shelf.updatePriceMultiplier(param1);
            return;
        }//end

         public void  refreshShelf ()
        {
            removeListeners();
            m_centerPanel.remove(shelf);
            m_items = Global.player.inventory.getItems();
            setShelf(new InventoryScrollingList(m_items, InventoryCellFactory, 0, 5, 2));
            ASwingHelper.prepare(shelf);
            m_centerPanel.append(shelf);
            m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT * 2 + CENTER_PANEL_MARGIN + 36);
            ASwingHelper.prepare(m_centerPanel);
            addListeners();
            this.refreshInventoryLimit();
            return;
        }//end

        public void  refreshInventoryLimit ()
        {
            this.m_wishlistPane.refreshInventoryLimit();
            return;
        }//end


    }



