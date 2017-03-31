package Display.MarketUI;

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
import Display.MarketUI.BuyDialogs.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.stats.types.*;

import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import ZLocalization.LocaleFormatter;

    public class LargeCatalogUI extends ItemCatalogUI
    {
        protected LargeCatalogModel m_model ;
        protected Array m_subTabNames ;
        protected Array m_subTabStates ;
        protected Array m_subTabs ;
        protected JLabel m_cashField ;
        protected JLabel m_coinField ;
        protected JTextField m_searchField ;
        protected CustomButton m_searchButton ;
        protected MarketCategoryToolTip m_selectedMarketToolTip ;
        protected Sprite m_toolTipHolder ;
        protected Sprite m_sortMenuHolder ;
        protected Sprite m_filterMenuHolder ;
        protected AssetPane m_emptySearch ;
        protected JWindow m_sortJW ;
        protected CustomButton m_sortButton ;
        protected JPanel m_sortButtonPanel ;
        protected boolean m_isSortOn ;
        protected CustomButton m_filterButton ;
        protected JWindow m_filterJW ;
        protected boolean m_isFilterOn ;
        protected JPanel m_promosPanel ;
        protected JPanel m_specialsPanel ;
        private JPanel m_comingSoonPanel ;
        public static  int TAB_STATE_SELECTED =0;
        public static  int TAB_STATE_UNSELECTED =1;
        public static  int TAB_HEIGHT =20;
        public static  int TAB_CAP_FONT_SIZE =14;
        public static  int TAB_FONT_SIZE =12;
        public static  String TYPE_SEARCH ="search";

        public  LargeCatalogUI (boolean param1 =false )
        {
            super(param1);
            this.m_model = LargeCatalogModel.instance;
            return;
        }//end

         protected double  layoutGap ()
        {
            return 0;
        }//end

         protected MarketScrollingList  makeShelf (Array param1 )
        {
            return new LargeMarketScrollingList(param1, LargeMarketCellFactory, 0, 3, 0);
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "market_BG_Blank");
            ASwingHelper.setSizedBackground(this, _loc_1);
            return;
        }//end

         public void  updateCoinsCash (int param1 ,int param2 )
        {
            this.m_coinField.setText(LocaleFormatter.formatNumber(param1));
            this.m_cashField.setText(LocaleFormatter.formatNumber(param2));
            this.m_coinField.setPreferredWidth(96);
            this.m_cashField.setPreferredWidth(96);
            return;
        }//end

         protected String  getTabUpName (String param1 )
        {
            return "marketIcons_" + param1 + "_up";
        }//end

         protected String  getTabOverName (String param1 )
        {
            return "marketIcons_" + param1 + "_over";
        }//end

         protected void  makeTopTabs (Array param1)
        {
            String _loc_10 =null ;
            int _loc_11 =0;
            DisplayObject _loc_12 =null ;
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT ,3);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            ASwingHelper.setEasyBorder(_loc_2, 9, 0, 0, 25);
            ASwingHelper.setEasyBorder(_loc_3, 10, 35);
            DisplayObject _loc_4 =(DisplayObject)new Catalog.assetDict.get( "getCash_Up");
            DisplayObject _loc_5 =(DisplayObject)new Catalog.assetDict.get( "getCash_Press");
            JButton _loc_6 =new JButton ();
            _loc_6.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_4, _loc_4));
            _loc_6.addActionListener(this.buyCash, 0, true);
            AssetPane _loc_7 =new AssetPane(new Catalog.assetDict.get( "coinsIcon") );
            AssetPane _loc_8 =new AssetPane(new EmbeddedArt.icon_cash ()as DisplayObject );
            this.m_coinField = ASwingHelper.makeLabel(Utilities.formatNumber(Global.player.gold), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.whiteTextColor, JLabel.LEFT);
            this.m_cashField = ASwingHelper.makeLabel(Utilities.formatNumber(Global.player.cash), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.whiteTextColor, JLabel.LEFT);
            this.m_coinField.setPreferredWidth(96);
            this.m_cashField.setPreferredWidth(96);
            this.m_searchField = new JTextField(ZLoc.t("Market", "search"));
            this.m_searchField.setPreferredWidth(150);
            this.m_searchField.addEventListener(MouseEvent.CLICK, this.clearSearchField, false, 0, true);
            this.m_searchField.addEventListener(KeyboardEvent.KEY_UP, this.search, false, 0, true);
            TextField _loc_9 =new TextField ();
            _loc_9.text = ZLoc.t("Market", "search");
            TextFieldUtil.formatSmallCaps(_loc_9, new TextFormat(EmbeddedArt.titleFont, 20));
            this.m_searchButton = new CustomButton(_loc_9.text, null, "GreenSmallButtonUI");
            this.m_searchButton.addActionListener(this.search, 0, true);
            ASwingHelper.setEasyBorder(this.m_searchButton, 1);
            this.m_emptySearch = ASwingHelper.makeMultilineText(ZLoc.t("Market", "searchEmpty"), 400, EmbeddedArt.defaultFontNameBold, "center", 24, EmbeddedArt.brownTextColor);
            this.m_emptySearch.x = m_containerWidth / 2 - this.m_emptySearch.width / 2;
            this.m_emptySearch.y = 290 - this.m_emptySearch.height / 2;
            m_closeButton = ASwingHelper.makeMarketCloseButton();
            m_closeButton.addActionListener(closeCatalog, 0, true);
            _loc_2.appendAll(this.m_searchField, this.m_searchButton, m_closeButton);
            _loc_3.appendAll(_loc_6, ASwingHelper.horizontalStrut(20), _loc_7, this.m_coinField, ASwingHelper.horizontalStrut(20), _loc_8, this.m_cashField);
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_10 = param1.get(i0);

                renderTab(_loc_10);
            }
            m_catalog.addEventListener(MouseEvent.MOUSE_DOWN, this.onMarketDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_MOVE, this.onMarketDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OVER, onMarketOver, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OUT, onMarketOut, false, 0, true);
            m_topPanel.append(_loc_2, BorderLayout.EAST);
            m_topPanel.append(_loc_3, BorderLayout.WEST);
            this.appendAll(m_topPanel, ASwingHelper.verticalStrut(-20), m_tabsPanel, ASwingHelper.verticalStrut(-3));
            _loc_11 = this.getChildIndex(m_topPanel);
            this.swapChildrenAt(_loc_11, _loc_11 + 3);
            this.switchTabOpacity(m_exclusive);
            _loc_12 =(DisplayObject) new Catalog.assetDict.get("market2_sortIcon");
            this.m_sortButton = new CustomButton(ZLoc.t("Market", "sortBy"), new AssetIcon(_loc_12), "MarketSortButtonUI");
            this.m_sortButton.addActionListener(this.clickSortMenu, 0, true);
            ASwingHelper.setEasyBorder(this.m_sortButton, 3, 3, 2, 3);
            _loc_12 =(DisplayObject) new Catalog.assetDict.get("market2_sortIcon");
            this.m_filterButton = new CustomButton(ZLoc.t("Market", "filterBy"), new AssetIcon(_loc_12), "MarketSortButtonUI");
            ASwingHelper.setEasyBorder(this.m_filterButton, 3, 3, 2, 3);
            this.m_filterButton.addActionListener(this.clickFilterMenu, 0, true);
            this.m_sortButtonPanel = ASwingHelper.makeSoftBoxJPanel();
            this.m_sortButtonPanel.append(this.m_sortButton);
            ASwingHelper.setBackground(this.m_sortButtonPanel, new Catalog.assetDict.get("market2_tabPress"));
            this.m_sortButtonPanel.setPreferredHeight(26);
            ASwingHelper.setEasyBorder(this.m_sortButtonPanel, 0, 0, 0, 50);
            this.m_sortMenuHolder = new Sprite();
            this.m_filterMenuHolder = new Sprite();
            this.m_sortJW = new JWindow(this.m_sortMenuHolder);
            this.m_filterJW = new JWindow(this.m_filterMenuHolder);
            m_centerPanel.setPreferredHeight(2 * (LargeCatalog.CARD_HEIGHT + 6));
            this.addEventListener(GenericObjectEvent.SKINS_CLICK, this.switchSkin, false, 0, true);
            this.addEventListener(GenericObjectEvent.MARKET_SORT, this.sort, false, 0, true);
            this.addEventListener(GenericObjectEvent.MARKET_FILTER, this.filter, false, 0, true);
            return;
        }//end

        protected void  clearSearchField (MouseEvent event )
        {
            StatsManager.sample(100, "market_views", "enter_search_field");
            this.m_searchField.setText("");
            this.search();
            if (Utilities.isFullScreen())
            {
                Utilities.toggleFullScreen();
            }
            return;
        }//end

        protected void  buyCash (AWEvent event )
        {
            StatsManager.count("get_coins", "click_on_coinIcon");
            FrameManager.showTray("money.php?ref=coinsDialog");
            return;
        }//end

        protected void  replaceTooltip (DataItemEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            _loc_2 = event.pt ;
            dispatchEvent(new DataItemEvent(DataItemEvent.SHOW_OLD_TOOLTIP, event.item, _loc_2, true));
            return;
        }//end

        protected void  hideOldToolTip (Event event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            dispatchEvent(new Event("turnOffOldToolTip", true));
            return;
        }//end

         protected void  setupPromos ()
        {
            PromoMarketCell _loc_8 =null ;
            _loc_1 = this.m_model.getPromoItems ();
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT ,0);
            DisplayObject _loc_3 =(DisplayObject)new Catalog.assetDict.get( "bestSellers_bg");
            ASwingHelper.setBackground(_loc_2, _loc_3, new Insets(0, 27, 0, 34));
            _loc_2.setPreferredHeight(_loc_3.height);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_5 = ASwingHelper.makeMultilineCapsText(ZLoc.t("Market","bestSellers"),150,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,16,EmbeddedArt.lightOrangeTextColor );
            _loc_4.append(_loc_5);
            this.m_promosPanel = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER, 5);
            ASwingHelper.setBackground(this.m_promosPanel, new Catalog.assetDict.get("bestSellers_inset"));
            this.m_promosPanel.append(ASwingHelper.horizontalStrut(1));
            ASwingHelper.setEasyBorder(this.m_promosPanel, 3);
            _loc_6 = Math.min(4,_loc_1.length );
            int _loc_7 =0;
            while (_loc_7 < _loc_6)
            {

                _loc_8 = new PromoMarketCell();
                _loc_8.setCellValue(_loc_1.get(_loc_7));
                _loc_8.addEventListener(DataItemEvent.SHOW_TOOLTIP, this.replaceTooltip, false, 0, true);
                _loc_8.addEventListener("turnOffToolTip", this.hideOldToolTip, false, 0, true);
                this.m_promosPanel.append(_loc_8);
                ASwingHelper.setEasyBorder(_loc_8, 2, 0, 2);
                _loc_7++;
            }
            this.m_promosPanel.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, 0, true);
            this.m_promosPanel.append(ASwingHelper.horizontalStrut(1));
            _loc_2.appendAll(_loc_4, this.m_promosPanel, ASwingHelper.horizontalStrut(40));
            this.appendAll(ASwingHelper.verticalStrut(9), _loc_2);
            return;
        }//end

         public void  updateElementsByItemNames (Array param1 )
        {
            Component _loc_4 =null ;
            PromoMarketCell _loc_5 =null ;
            Item _loc_6 =null ;
            super.updateElementsByItemNames(param1);
            _loc_2 = this.m_promosPanel.getComponentCount ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                _loc_4 = this.m_promosPanel.getComponent(_loc_3);
                _loc_5 =(PromoMarketCell) _loc_4;
                if (_loc_5 != null)
                {
                    _loc_6 =(Item) _loc_5.getCellValue();
                    if (_loc_6)
                    {
                        if (param1.indexOf(_loc_6.name) != -1)
                        {
                            _loc_5.performUpdate(true);
                        }
                    }
                }
                _loc_3++;
            }
            return;
        }//end

         protected void  makeSpacerPanel ()
        {
            return;
        }//end

        protected JPanel  makePromoCell ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            ASwingHelper.setSizedBackground(_loc_1, new Catalog.assetDict.get("card_available_selected"));
            return _loc_1;
        }//end

         protected void  makeSubTabs (String param1 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            String _loc_5 =null ;
            JPanel _loc_6 =null ;
            JLabel _loc_7 =null ;
            Array _loc_8 =null ;
            m_subTabsPanel.removeAll();
            ASwingHelper.setEasyBorder(m_subTabsPanel, 0, 0, 2);
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,2);
            _loc_2.append(ASwingHelper.horizontalStrut(50));
            ASwingHelper.setEasyBorder(_loc_2, 6);
            this.m_subTabs = new Array();
            this.m_subTabStates = new Array();
            this.m_subTabNames = this.m_model.getCategorySubCategories(param1);
            if (this.m_subTabNames != null && this.m_subTabNames.length > 1 && !Global.isVisiting())
            {
                if (this.m_model.getSaleItems().length == 0)
                {
                    _loc_4 = this.m_subTabNames.indexOf("sale");
                    if (_loc_4 >= 0)
                    {
                        this.m_subTabNames.splice(_loc_4, 1);
                    }
                }
                _loc_3 = 0;
                while (_loc_3 < this.m_subTabNames.length())
                {

                    _loc_5 = this.m_subTabNames.get(_loc_3);
                    this.m_subTabs.push(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
                    _loc_6 = this.m_subTabs.get(_loc_3);
                    _loc_7 = ASwingHelper.makeLabel(ZLoc.t("Market", _loc_5), EmbeddedArt.defaultFontNameBold, TAB_FONT_SIZE, EmbeddedArt.blueTextColor);
                    _loc_6.setPreferredSize(new IntDimension(_loc_7.width + 10, TAB_HEIGHT));
                    _loc_6.setMinimumSize(new IntDimension(_loc_7.width + 10, TAB_HEIGHT));
                    _loc_6.setMaximumSize(new IntDimension(_loc_7.width + 10, TAB_HEIGHT));
                    _loc_6.append(_loc_7);
                    _loc_2.append(_loc_6);
                    _loc_8 = new Array();
                    _loc_8.push(Catalog.assetDict.get("market2_tabPress"));
                    _loc_8.push(Catalog.assetDict.get("market2_tabUp"));
                    this.m_subTabStates.push(_loc_8);
                    _loc_6.addEventListener(MouseEvent.CLICK, this.onSubTabClicked, false, 0, true);
                    _loc_3++;
                }
                this.switchSubType(this.m_subTabNames.get(0));
            }
            else
            {
                m_subType = null;
            }
            this.hideSortMenu();
            this.hideFilterMenu();
            if (isChild(this.m_emptySearch))
            {
                removeChild(this.m_emptySearch);
            }
            m_subTabsPanel.append(_loc_2, BorderLayout.WEST);
            this.m_sortButtonPanel.removeAll();
            if (m_subType == "skins")
            {
                this.m_sortButtonPanel.append(this.m_filterButton);
            }
            this.m_sortButtonPanel.append(this.m_sortButton);
            m_subTabsPanel.append(this.m_sortButtonPanel, BorderLayout.EAST);
            ASwingHelper.prepare(m_subTabsPanel);
            return;
        }//end

        protected void  clickSortMenu (AWEvent event )
        {
            if (this.m_isSortOn)
            {
                this.hideSortMenu();
            }
            else
            {
                this.showSortMenu(event);
            }
            return;
        }//end

        protected void  showSortMenu (AWEvent event )
        {
            MarketSortToolTip _loc_2 =null ;
            if (m_type != "wonders")
            {
                this.hideFilterMenu();
                _loc_2 = new MarketSortToolTip(m_type);
                this.m_sortJW.setContentPane(_loc_2);
                ASwingHelper.prepare(this.m_sortJW);
                this.m_sortJW.show();
                this.addChild(this.m_sortMenuHolder);
                this.m_sortMenuHolder.x = this.m_sortButtonPanel.x + this.m_sortButtonPanel.getWidth() - this.m_sortJW.getWidth() - 50;
                this.m_sortMenuHolder.y = 120;
                TweenLite.from(this.m_sortMenuHolder, 0.3, {alpha:0});
                this.m_isSortOn = true;
            }
            return;
        }//end

        protected void  hideSortMenu ()
        {
            if (this.m_isSortOn)
            {
                this.removeChild(this.m_sortMenuHolder);
            }
            this.m_isSortOn = false;
            return;
        }//end

        protected void  clickFilterMenu (AWEvent event )
        {
            if (this.m_isFilterOn)
            {
                this.hideFilterMenu();
            }
            else
            {
                this.showFilterMenu(event);
            }
            return;
        }//end

        protected void  showFilterMenu (AWEvent event )
        {
            this.hideSortMenu();
            MarketFilterMenu _loc_2 =new MarketFilterMenu(this.m_model.getSkinnableResidences ());
            this.m_filterJW.setContentPane(_loc_2);
            ASwingHelper.prepare(this.m_filterJW);
            this.m_filterJW.show();
            this.addChild(this.m_filterMenuHolder);
            this.m_filterMenuHolder.x = this.m_sortButtonPanel.x + this.m_sortButtonPanel.getWidth() - this.m_filterJW.getWidth() - 50;
            this.m_filterMenuHolder.y = 120;
            TweenLite.from(this.m_filterMenuHolder, 0.3, {alpha:0});
            this.m_isFilterOn = true;
            return;
        }//end

        protected void  hideFilterMenu ()
        {
            if (this.m_isFilterOn)
            {
                this.removeChild(this.m_filterMenuHolder);
            }
            this.m_isFilterOn = false;
            return;
        }//end

         protected void  switchTabOpacity (boolean param1 )
        {
            String _loc_2 =null ;
            ToolTip _loc_3 =null ;
            JButton _loc_4 =null ;
            JPanel _loc_5 =null ;
            for(int i0 = 0; i0 < m_tabNames.size(); i0++)
            {
            	_loc_2 = m_tabNames.get(i0);

                _loc_3 = m_toolTipDict.get(_loc_2);
                _loc_4 = m_iconsDict.get(_loc_2).jbtn;
                _loc_5 = m_iconsDict.get(_loc_2).tab;
                if (param1 !=null)
                {
                    _loc_5.removeAll();
                    _loc_5.append(ASwingHelper.verticalStrut(68));
                    _loc_4.visible = false;
                    continue;
                }
                _loc_5.removeAll();
                _loc_4.visible = true;
                _loc_5.append(_loc_4);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  switchActiveTab (String param1 ,String param2 )
        {
            SimpleButton _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            JPanel _loc_6 =null ;
            Object _loc_7 =null ;
            SimpleButton _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            JPanel _loc_10 =null ;
            _loc_3 = m_iconsDict.get(param2);
            if (_loc_3)
            {
                _loc_4 = _loc_3.button;
                _loc_5 = _loc_3.image;
                _loc_6 = _loc_3.tab;
                _loc_4.upState = _loc_4.overState;
            }
            if (param1 != param2 && !m_exclusive)
            {
                _loc_7 = m_iconsDict.get(param1);
                if (_loc_7)
                {
                    _loc_8 = _loc_7.button;
                    _loc_9 = _loc_7.image;
                    _loc_10 = _loc_7.tab;
                    _loc_8.upState = _loc_9;
                }
            }
            return;
        }//end

         protected void  onMarketDown (MouseEvent event )
        {
            GMDefault _loc_6 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            Point _loc_3 =new Point(Global.ui.mouseX ,Global.ui.mouseY );
            _loc_4 =Global.stage.getObjectsUnderPoint(new Point(Global.stage.mouseX ,Global.stage.mouseY ));
            int _loc_5 =0;
            _loc_5 = 0;
            while (_loc_5 < _loc_4.length())
            {

                if (_loc_4.get(_loc_5) instanceof Catalog.assetDict.get("market_BG_Blank"))
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

        private void  onSubTabClicked (MouseEvent event )
        {
            _loc_2 = this.m_subTabs.indexOf(event.currentTarget );
            if (_loc_2 >= 0)
            {
                this.switchSubType(this.m_subTabNames.get(_loc_2));
            }
            return;
        }//end

        private void  setSelectedSubTab (String param1 )
        {
            DisplayObject _loc_3 =null ;
            int _loc_4 =0;
            JPanel _loc_5 =null ;
            JLabel _loc_6 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.m_subTabs.length())
            {

                if (this.m_subTabNames.get(_loc_2) == param1)
                {
                    _loc_3 =(DisplayObject) new this.m_subTabStates.get(_loc_2).get(TAB_STATE_SELECTED);
                    _loc_4 = EmbeddedArt.blueTextColor;
                }
                else
                {
                    _loc_3 =(DisplayObject) new this.m_subTabStates.get(_loc_2).get(TAB_STATE_UNSELECTED);
                    _loc_4 = EmbeddedArt.whiteTextColor;
                }
                _loc_3.width = this.m_subTabs.get(_loc_2).getWidth();
                _loc_3.height = TAB_HEIGHT;
                _loc_5 =(JPanel) this.m_subTabs.get(_loc_2);
                _loc_6 =(JLabel) _loc_5.getChildByName("JLabel");
                _loc_6.setForeground(new ASColor(_loc_4));
                this.m_subTabs.get(_loc_2).setBackgroundDecorator(new AssetBackground(_loc_3));
                _loc_2++;
            }
            this.hideSortMenu();
            this.hideFilterMenu();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  setToolTip (MouseEvent event =null ,String param2)
        {
            JButton _loc_3 =null ;
            String _loc_4 =null ;
            Object _loc_9 =null ;
            if (event)
            {
                _loc_3 =(JButton) event.currentTarget;
                _loc_4 = m_tabsDict.get(event.currentTarget as JButton);
            }
            else
            {
                _loc_4 = param2;
                for(int i0 = 0; i0 < m_tabsDict.size(); i0++)
                {
                	_loc_9 = m_tabsDict.get(i0);

                    if (m_tabsDict.get(_loc_9) == _loc_4)
                    {
                        _loc_3 =(JButton) _loc_9;
                        break;
                    }
                }
            }
            if (this.m_toolTipHolder != null)
            {
                this.removeChild(this.m_toolTipHolder);
            }
            this.m_toolTipHolder = new Sprite();
            this.addChild(this.m_toolTipHolder);
            boolean _loc_10 =false ;
            this.m_toolTipHolder.mouseEnabled = false;
            this.m_toolTipHolder.mouseChildren = _loc_10;
            this.m_selectedMarketToolTip = new MarketCategoryToolTip(13762559);
            this.m_toolTipHolder.addChild(this.m_selectedMarketToolTip);
            _loc_5 = _loc_3.getGlobalLocation ();
            _loc_6 = _loc_3.getGlobalLocation ().x ;
            _loc_7 = _loc_5.y ;
            _loc_8 =Global(.ui.screenWidth -Catalog.SCROLL_WIDTH )/2;
            this.m_selectedMarketToolTip.changeInfo(m_type);
            this.m_selectedMarketToolTip.x = _loc_6 - this.m_selectedMarketToolTip.width * 0.5 - _loc_8 + 30;
            this.m_selectedMarketToolTip.y = this.y + 69;
            return;
        }//end

        protected void  search (Event event =null )
        {
            String _loc_2 =null ;
            if (!Global.isVisiting() && !m_exclusive)
            {
                m_type = TYPE_SEARCH;
                this.makeSubTabs(null);
                _loc_2 = this.m_searchField.getText().toLowerCase();
                m_items = this.m_model.search(_loc_2);
                if (_loc_2.length > 4)
                {
                    StatsManager.sample(100, "market_views", "search", _loc_2);
                }
                this.refreshShelf();
                if (!isChild(this.m_emptySearch) && m_items.length == 0)
                {
                    if (_loc_2 != "")
                    {
                        StatsManager.sample(100, "market_views", "search", "empty", _loc_2);
                    }
                    addChild(this.m_emptySearch);
                }
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        public void  sort (GenericObjectEvent event )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            Array _loc_5 =null ;
            int _loc_6 =0;
            this.hideSortMenu();
            StatsManager.sample(100, "market_views", "sort", "e.obj.compareItems");
            if (event.obj == null || !event.obj.hasOwnProperty("compareItems"))
            {
                return;
            }
            _loc_2 =(Function) event.obj.compareItems;
            if (_loc_2 == m_items.get("compareItems") as Function)
            {
                return;
            }
            if (_loc_2 == null)
            {
                this.loadSubItems();
                if (m_type != TYPE_SEARCH)
                {
                    this.refreshShelf();
                }
            }
            else
            {
                _loc_3 = m_items.sort(_loc_2, Array.RETURNINDEXEDARRAY);
                _loc_4 = _loc_3.length;
                _loc_5 = new Array(_loc_4);
                _loc_6 = 0;
                while (_loc_6 < _loc_4)
                {

                    _loc_5.put(_loc_6,  m_items.get(_loc_3.get(_loc_6)));
                    _loc_6++;
                }
                m_items = _loc_5;
                this.refreshShelf();
            }
            m_items.put("compareItems",  _loc_2);
            return;
        }//end

        public void  filter (GenericObjectEvent event )
        {
            e = event;
            this.hideFilterMenu();
            if (e.obj instanceof String)
            {
                m_items = this.m_model.getSubCategoryItems(m_type, m_subType);
            }
            else
            {
                m_items =this .m_model .getSubCategoryItems (m_type ,m_subType ).filter (boolean  (Item param1 ,int param2 ,Array param3 )
            {
                if (param1.derivedItemName == e.obj.name)
                {
                    return true;
                }
                return false;
            }//end
            );
            }
            this.refreshShelf();
            return;
        }//end

        public boolean  compare (Object param1 ,Object param2 )
        {
            _loc_3 = null;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_3 = param1.get(i0);

                if (param1.get(_loc_3) != param2.get(_loc_3))
                {
                    return false;
                }
            }
            return true;
        }//end

        protected void  launchCategory (GenericObjectEvent event )
        {
            String _loc_2 =null ;
            m_type = event.obj.category;
            this.makeSubTabs(m_type);
            this.switchActiveTab("specials", event.obj.category);
            this.setToolTip(null, event.obj.category);
            this.loadAvailableItems();
            if (event.obj.subcategory)
            {
                _loc_2 = event.obj.subcategory;
                this.switchSubType(_loc_2);
            }
            this.refreshShelf();
            return;
        }//end

         public void  switchType (String param1 )
        {
            Array _loc_2 =null ;
            if (param1 == "farming")
            {
                param1 = "goods";
            }
            if (!Global.isVisiting() && !m_exclusive)
            {
                this.switchActiveTab(m_type, param1);
                if (this.m_selectedMarketToolTip)
                {
                    this.m_selectedMarketToolTip.visible = true;
                }
                if (this.m_promosPanel && this.m_promosPanel.parent)
                {
                    this.m_promosPanel.visible = true;
                    this.m_promosPanel.parent.visible = true;
                }
            }
            else
            {
                if (this.m_selectedMarketToolTip)
                {
                    this.m_selectedMarketToolTip.visible = false;
                }
                if (this.m_promosPanel && this.m_promosPanel.parent)
                {
                    this.m_promosPanel.parent.visible = false;
                    this.m_promosPanel.visible = false;
                }
            }
            m_type = param1;
            this.loadAvailableItems();
            this.makeSubTabs(m_type);
            if (param1 == "specials")
            {
                m_centerPanel.removeAll();
                if (this.m_specialsPanel == null)
                {
                    this.m_specialsPanel = new SpecialsPanel();
                    this.m_specialsPanel.addEventListener(GenericObjectEvent.SPECIALS_CLICK, this.launchCategory, false, 0, true);
                }
                ASwingHelper.prepare(this.m_specialsPanel);
                m_centerPanel.appendAll(this.m_specialsPanel);
                trackPageView(m_type, m_subType, 0);
                _loc_2 = new Array();
                _loc_2.push(Global.gameSettings().getItemByName("res_apartments"));
                setShelf(this.makeShelf(_loc_2));
            }
            else if (m_subType == null || m_exclusive)
            {
                this.refreshShelf();
                if (shelf)
                {
                    trackPageView(m_type, m_subType, shelf.currentPageIndex);
                }
            }
            return;
        }//end

         public void  switchSubType (String param1 )
        {
            super.switchSubType(param1);
            this.setSelectedSubTab(param1);
            this.loadSubItems();
            this.refreshShelf();
            if (shelf)
            {
                trackPageView(m_type, m_subType, 0);
            }
            this.m_sortButtonPanel.removeAll();
            if (m_subType == "skins")
            {
                this.m_sortButtonPanel.append(this.m_filterButton);
            }
            this.m_sortButtonPanel.append(this.m_sortButton);
            ASwingHelper.prepare(this.m_sortButtonPanel);
            return;
        }//end

         public void  refreshShelf ()
        {
            if (shelf && shelf.hasEventListener(DataItemEvent.MARKET_BUY))
            {
                shelf.removeListeners();
                shelf.removeEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick);
                shelf.removeEventListener(Event.CHANGE, onShelfChange);
            }
            m_centerPanel.removeAll();
            if (m_items != null && m_items.length > 0)
            {
                setShelf(this.makeShelf(m_items));
                shelf.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, 0, true);
                shelf.addEventListener(Event.CHANGE, onShelfChange);
                m_centerPanel.appendAll(shelf);
            }
            ASwingHelper.prepare(m_centerPanel);
            return;
        }//end

         protected void  onContainerClick (DataItemEvent event )
        {
            SkinDialog _loc_4 =null ;
            Sounds.playFromSet(Sounds.SET_CLICK);
            _loc_2 = event.item ;
            _loc_3 = _loc_2? (_loc_2) : (null);
            if (_loc_3 && _loc_3.isRemodelSkin())
            {
                _loc_4 = new SkinDialog(m_catalog, _loc_3);
                UI.displayPopup(_loc_4);
            }
            else if (m_catalog.canBuy(_loc_3))
            {
                m_catalog.onBuy(_loc_3, event.target);
            }
            return;
        }//end

         protected void  loadAvailableItems (Array param1)
        {
            if (m_exclusive)
            {
                m_items = Global.gameSettings().getCurrentBuyableItems(m_type);
            }
            else if (m_type == TYPE_SEARCH)
            {
                this.search();
            }
            else if (Global.isVisiting() && m_type == "business")
            {
                m_items = Global.franchiseManager.eligibleFranchises;
            }
            else
            {
                m_items = this.m_model.getCategoryItems(m_type);
            }
            if (m_items == null)
            {
                m_items = new Array();
            }
            return;
        }//end

        protected void  loadSubItems ()
        {
            if (m_subType == null || m_subType == "all")
            {
                this.loadAvailableItems();
            }
            else
            {
                switch(m_subType)
                {
                    case "bogo":
                    {
                        m_items = this.m_model.getBogoSpecialItems();
                        break;
                    }
                    case "sale":
                    {
                        m_items = this.m_model.getSaleItems();
                        break;
                    }
                    default:
                    {
                        m_items = this.m_model.getSubCategoryItems(m_type, m_subType);
                        break;
                        break;
                    }
                }
            }
            if (m_items == null)
            {
                m_items = new Array();
            }
            return;
        }//end

        protected void  switchSkin (GenericObjectEvent event )
        {
            StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "remodel", "click_paint_icon");
            this.switchType("residence");
            this.switchSubType("skins");
            this.filter(event);
            return;
        }//end

    }



