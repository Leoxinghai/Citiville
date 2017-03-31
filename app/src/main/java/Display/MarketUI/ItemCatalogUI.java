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
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import com.zynga.skelly.util.*;
import com.zynga.skelly.util.color.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

import com.xinghai.Debug;

    public class ItemCatalogUI extends JPanel implements ICatalogUI
    {
        protected Array m_items ;
        protected Array m_newItems ;
        private MarketScrollingList m_shelf ;
        private double m_priceModifier =1;
        protected String m_type ;
        protected String m_subType ;
        protected Catalog m_catalog ;
        protected String m_itemName ;
        private boolean m_ignoreExcludeExperiments ;
        protected Dictionary m_subTabsNames ;
        protected double m_containerWidth ;
        protected JPanel m_tabsPanel ;
        protected JPanel m_subTabsPanel ;
        protected JPanel m_centerPanel ;
        protected JPanel m_middlePanel ;
        protected JPanel m_topPanel ;
        protected JPanel m_topLeftPanel ;
        protected JPanel m_rightPanel ;
        protected JPanel m_closeButtonPanel ;
        private Array m_tabsArray ;
        protected JButton m_closeButton ;
        protected JButton m_menuButton ;
        protected ItemCatalogToolTip m_toolTip ;
        protected boolean m_isInitialized ;
        protected Dictionary m_tabIndices ;
        protected boolean m_exclusive ;
        protected String m_overrideTitle ;
        private Dictionary m_tweenChildren ;
        protected Dictionary m_tabsDict ;
        protected Dictionary m_iconsDict ;
        protected Dictionary m_toolTipDict ;
        protected  double BIG_FONT_SIZE =22;
        protected JTextField m_sectionTitle ;
        protected Array m_tabNames ;
        protected AssetPane m_selectedTab ;

        public  ItemCatalogUI (boolean param1 =false )
        {
            this.m_tabIndices = new Dictionary(true);
            this.m_tweenChildren = new Dictionary(true);
            this.m_tabsDict = new Dictionary(true);
            this.m_iconsDict = new Dictionary(true);
            this.m_toolTipDict = new Dictionary(true);
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, this.layoutGap, SoftBoxLayout.TOP));
            return;
        }//end

        protected void  setShelf (MarketScrollingList param1 )
        {
            if (param1 == this.m_shelf)
            {
                return;
            }
            if (this.m_shelf != null)
            {
                this.m_shelf.dispose();
            }
            this.m_shelf = param1;
            return;
        }//end

        public MarketCell  getCellByName (String param1 )
        {
            _loc_2 = this.getIndexOfItem(param1 );
            if (_loc_2 >= 0)
            {
                return this.m_shelf.dataList.getCellByIndex(_loc_2).getCellComponent() as MarketCell;
            }
            return null;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public String  subType ()
        {
            return this.m_subType;
        }//end

        public Array  items ()
        {
            return this.m_items;
        }//end

        public MarketScrollingList  shelf ()
        {
            return this.m_shelf;
        }//end

        public JPanel  topPanel ()
        {
            return this.m_topPanel;
        }//end

        public boolean  exclusive ()
        {
            return this.m_exclusive;
        }//end

        protected double  layoutGap ()
        {
            return -13;
        }//end

        public void  overrideTitle (String param1 )
        {
            this.m_overrideTitle = param1;
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.size = this.BIG_FONT_SIZE;
            if (this.m_sectionTitle)
            {
                this.m_sectionTitle.setText(this.getTitle());
                TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), _loc_2);
            }
            return;
        }//end

        public String  overrideTitle ()
        {
            return this.m_overrideTitle;
        }//end

        public void  exclusive (boolean param1 )
        {
            this.m_exclusive = param1;
            this.switchTabOpacity(this.m_exclusive);
            return;
        }//end

        public void  updateCoinsCash (int param1 ,int param2 )
        {
            return;
        }//end

        public void  init (Catalog param1 ,CatalogParams param2 )
        {
            this.makeBackground();
            this.m_overrideTitle = param2.overrideTitle;
            this.m_exclusive = param2.exclusiveCategory;
            this.m_containerWidth = 760;
            this.m_catalog = param1;
            this.m_type = param2.type;
            this.m_subType = param2.subType;
            this.m_itemName = param2.itemName;
            this.m_ignoreExcludeExperiments = param2.ignoreExcludeExperiments;
            this.m_selectedTab = new AssetPane(new Catalog.assetDict.get("selectedTab"));
            ASwingHelper.setEasyBorder(this.m_selectedTab, 0, 10);
            this.m_topPanel = new JPanel(new BorderLayout());
            this.m_topLeftPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_tabsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_subTabsPanel = new JPanel(new BorderLayout());
            this.m_centerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT);
            this.m_tabNames = this.m_catalog.categoryNames;
            this.makeTopTabs(this.m_tabNames);
            this.append(this.m_subTabsPanel);
            this.makeSubTabs(this.m_tabNames.get(0));
            this.makeSpacerPanel();
            this.append(this.m_centerPanel);
            ASwingHelper.prepare(this);
            this.switchType(param2.type);
            if (param2.subType != null && param2.subType.length > 0)
            {
                this.switchSubType(param2.subType);
            }
            this.switchItems(param2.customItems, param2.type);
            if (param2.type != "specials")
            {
                this.goToItem(param2.itemName);
            }
            this.addListeners();
            this.setupPromos();
            this.m_toolTip = new ItemCatalogToolTip();
            addChild(this.m_toolTip);
            this.m_toolTip.visible = false;
            this.m_isInitialized = true;
            return;
        }//end

        protected void  setupPromos ()
        {
            return;
        }//end

        protected void  makeSpacerPanel ()
        {
            return;
        }//end

        protected MarketScrollingList  makeShelf (Array param1 )
        {
            return new MarketScrollingList(param1, MarketCellFactory, 0, 7, 1);
        }//end

        protected void  switchTabOpacity (boolean param1 )
        {
            String _loc_2 =null ;
            ToolTip _loc_3 =null ;
            JButton _loc_4 =null ;
            JPanel _loc_5 =null ;
            for(int i0 = 0; i0 < this.m_tabNames.size(); i0++)
            {
            		_loc_2 = this.m_tabNames.get(i0);

                _loc_3 = this.m_toolTipDict.get(_loc_2);
                _loc_4 = this.m_iconsDict.get(_loc_2).jbtn;
                _loc_5 = this.m_iconsDict.get(_loc_2).tab;
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
                if (_loc_2 == this.m_type)
                {
                    _loc_5.append(this.m_selectedTab);
                    ASwingHelper.setEasyBorder(_loc_5, 0);
                }
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  loadMarketNewIcon (DisplayObject param1 ,DisplayObject param2 ,String param3 ,String param4 ,SimpleButton param5 ,JButton param6 ,JPanel param7 ,JPanel param8 ,String param9 )
        {
            Loader upiconLoader ;
            loadIcon = param1;
            otherIcon = param2;
            iconName = param3;
            iconPath = param4;
            btn = param5;
            jbtn = param6;
            tab = param7;
            vert = param8;
            str = param9;
            Debug.debug6("ItemCatalogUI.loadMarketNewIcon "+param3 );
            upiconLoader =LoadingManager .load (Global .getAssetURL (iconPath ),Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                loadIcon = upiconLoader.content;
                if (iconName == "up")
                {
                    btn = new SimpleButton(loadIcon, otherIcon, otherIcon, loadIcon);
                }
                else
                {
                    btn = new SimpleButton(otherIcon, loadIcon, loadIcon, otherIcon);
                }
                jbtn.wrapSimpleButton(btn);
                ASwingHelper.prepare(tab);
                Object _loc_3 =new Object ();
                _loc_3.tab = vert;
                _loc_3.image = loadIcon;
                _loc_3.button = btn;
                _loc_3.jbtn = jbtn;
                m_iconsDict.put(str,  _loc_3);
                return;
            }//end
            , this));
            return;
        }//end

        protected String  getTabUpName (String param1 )
        {
            return "tab_icon_" + param1 + "_offstate";
        }//end

        protected String  getTabOverName (String param1 )
        {
            return "tab_icon_" + param1 + "_onstate";
        }//end

        protected void  renderTab (String param1 )
        {
            JPanel tab ;
            JPanel vert ;
            JButton btn ;
            DisplayObject upIcon ;
            DisplayObject overIcon ;
            SimpleButton simpBtn ;
            XMLList iconList ;
            XML icon ;
            Loader upiconLoader ;
            str = param1;
            tab = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            vert = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, -6);
            btn = new JButton();
            upState = this.getTabUpName(str);
            overState = this.getTabOverName(str);
            if (str == "new")
            {
                iconList = Global.gameSettings().getMarketNewIcons();
                if (iconList.icon.length() == 0)
                {
                    upIcon =(DisplayObject) new Catalog.assetDict.get(upState);
                    overIcon =(DisplayObject) new Catalog.assetDict.get(overState);
                    simpBtn = new SimpleButton(upIcon, overIcon, overIcon, upIcon);
                    btn.wrapSimpleButton(simpBtn);
                }
                else
                {
                    int _loc_3 =0;
                    _loc_4 = iconList.icon;
                    for(int i0 = 0; i0 < iconList.icon.size(); i0++)
                    {
                    		icon = iconList.icon.get(i0);


                        upiconLoader = LoadingManager.load(Global.getAssetURL(icon.@path), Curry.curry(function (param11:JPanel, param21:String, param31:Event) : void
            {
                Object _loc_4 =new Object ();
                if (param21 == "up")
                {
                    upIcon = param31.currentTarget.content;
                    _loc_4.image = upIcon;
                }
                else if (param21 == "over")
                {
                    overIcon = param31.currentTarget.content;
                    _loc_4.image = overIcon;
                }
                simpBtn = new SimpleButton(upIcon, overIcon, overIcon, upIcon);
                btn.wrapSimpleButton(simpBtn);
                ASwingHelper.prepare(tab);
                _loc_4.tab = vert;
                _loc_4.button = simpBtn;
                _loc_4.jbtn = btn;
                m_iconsDict.put(str,  _loc_4);
                return;
            }//end
            , this, icon.@name));
                    }
                }
            }
            else
            {
                upIcon =(DisplayObject) new Catalog.assetDict.get(upState);
                overIcon =(DisplayObject) new Catalog.assetDict.get(overState);
                simpBtn = new SimpleButton(upIcon, overIcon, overIcon, upIcon);
                btn.wrapSimpleButton(simpBtn);
            }
            Object assetObj =new Object ();
            assetObj.tab = vert;
            assetObj.image = upIcon;
            assetObj.button = simpBtn;
            assetObj.jbtn = btn;
            this.m_iconsDict.put(str,  assetObj);
            this.m_tabsDict.put(btn,  str);
            btn.name = str;
            vert.append(btn);
            ASwingHelper.setEasyBorder(vert, 14);
            tab.append(vert);
            btn.addActionListener(this.changeCategory, 0, true);
            ASwingHelper.prepare(tab);
            this.m_tabsPanel.append(tab);
            btn.addEventListener(MouseEvent.ROLL_OVER, this.showToolTip, false, 0, true);
            btn.addEventListener(MouseEvent.ROLL_OUT, this.hideToolTip, false, 0, true);
            btn.addEventListener(MouseEvent.CLICK, this.setToolTip, false, 0, true);
            if (Global.isVisiting() && str != "business")
            {
                btn.setEnabled(false);
            }
            return;
        }//end

        protected void  makeTopTabs (Array param1)
        {
            String _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            TextFormat _loc_5 =null ;
            AssetPane _loc_6 =null ;
            AssetPane _loc_7 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.renderTab(_loc_2);
            }
            this.m_catalog.addEventListener(MouseEvent.MOUSE_DOWN, this.onMarketDown, false, 0, true);
            this.m_catalog.addEventListener(MouseEvent.MOUSE_MOVE, this.onMarketDown, false, 0, true);
            this.m_catalog.addEventListener(MouseEvent.MOUSE_OVER, this.onMarketOver, false, 0, true);
            this.m_catalog.addEventListener(MouseEvent.MOUSE_OUT, this.onMarketOut, false, 0, true);
            this.m_rightPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            this.m_closeButtonPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_closeButton = ASwingHelper.makeMarketCloseButton();
            this.m_closeButton.addActionListener(this.closeCatalog, 0, true);
            this.m_closeButtonPanel.appendAll(this.m_closeButton, ASwingHelper.verticalStrut(10));
            this.m_rightPanel.append(this.m_closeButtonPanel);
            this.m_rightPanel.append(ASwingHelper.horizontalStrut(15));
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_3.append(ASwingHelper.horizontalStrut(15));
            this.m_topPanel.append(_loc_3, BorderLayout.WEST);
            this.m_topPanel.append(this.m_tabsPanel, BorderLayout.CENTER);
            this.m_topPanel.append(this.m_rightPanel, BorderLayout.EAST);
            this.append(this.m_topPanel);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 10);
            this.m_sectionTitle = ASwingHelper.makeTextField(this.getTitle(), EmbeddedArt.titleFont, 16, EmbeddedArt.blueTextColor);
            _loc_5 = new TextFormat();
            _loc_5.size = this.BIG_FONT_SIZE;
            TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), _loc_5);
            _loc_6 = new AssetPane(new Catalog.assetDict.get("marketTitleRule"));
            _loc_7 = new AssetPane(new Catalog.assetDict.get("marketTitleRule"));
            ASwingHelper.setEasyBorder(_loc_6, 18);
            ASwingHelper.setEasyBorder(_loc_7, 18);
            ASwingHelper.setEasyBorder(_loc_4, 3, 0, 2);
            _loc_4.appendAll(_loc_6, this.m_sectionTitle, _loc_7);
            this.append(_loc_4);
            this.switchTabOpacity(this.m_exclusive);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeSubTabs (String param1 )
        {
            this.m_subTabsPanel.append(ASwingHelper.verticalStrut(20));
            return;
        }//end

        protected void  onMarketDown (MouseEvent event )
        {
            GMDefault _loc_6 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            Point _loc_3 =new Point(Global.ui.mouseX ,Global.ui.mouseY );
            Array _loc_4 =Global.ui.m_bottomUI.getObjectsUnderPoint(_loc_3 );
            int _loc_5 =0;


            _loc_5 = 0;
            while (_loc_5 < _loc_4.length())
            {

                if (_loc_4.get(_loc_5) instanceof Catalog.assetDict.get("bg") || _loc_4.get(_loc_5) instanceof Catalog.assetDict.get("no_scroll_bg"))
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

        public void  forceShowToolTip ()
        {
            if (this.m_type == "plot_contract" || this.m_type == "ship_contract")
            {
                return;
            }
            _loc_1 = this.m_iconsDict.get(this.m_type) ;
            _loc_2 = _loc_1.jbtn ;
            _loc_3 = this.m_tabsDict.get(_loc_2) ;
            _loc_4 = _loc_2.getGlobalLocation ();
            _loc_5 = _loc_2.getGlobalLocation ().x ;
            _loc_6 =Global.ui.m_bottomUI.localToGlobal(new Point(Global.ui.m_bottomUI.x ,0));
            if (this.m_type == "residence" && _loc_5 != 80 + _loc_6.x)
            {
                _loc_5 = 80 + _loc_6.x;
            }
            Object _loc_7 =new Object ();
            _loc_7.category = _loc_3;
            _loc_7.posX = _loc_5;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, _loc_7));
            return;
        }//end

        protected void  showToolTip (MouseEvent event )
        {
            JButton _loc_2 =null ;
            String _loc_3 =null ;
            _loc_2 =(JButton) event.currentTarget;
            _loc_3 = this.m_tabsDict.get(event.currentTarget as JButton);
            _loc_4 = _loc_2.getGlobalLocation ();
            _loc_5 = _loc_2.getGlobalLocation ().x ;
            _loc_6 = _loc_4.y ;
            Object _loc_7 =new Object ();
            _loc_7.category = _loc_3;
            _loc_7.posX = _loc_5;
            _loc_7.posY = _loc_6;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, _loc_7));
            return;
        }//end

        protected void  hideToolTip (MouseEvent event )
        {
            dispatchEvent(new Event("hidetooltip"));
            return;
        }//end

        protected void  setToolTip (MouseEvent event =null ,String param2)
        {
            return;
        }//end

        protected void  onMarketOver (MouseEvent event )
        {
            UI.pushBlankCursor();
            return;
        }//end

        protected void  onMarketOut (MouseEvent event )
        {
            UI.popBlankCursor();
            return;
        }//end

        protected void  onMenuClose (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        protected void  onCloseDown (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        protected void  closeOver (MouseEvent event )
        {
            _loc_2 =(JButton) event.target;
            ColorMatrix _loc_3 =new ColorMatrix ();
            _loc_3.adjustBrightness(1.5, 1.5, 1.5);
            _loc_3.adjustContrast(1.5, 1.5, 1.5);
            _loc_2.filters = .get(_loc_3.filter);
            return;
        }//end

        protected void  closeOut (MouseEvent event )
        {
            _loc_2 =(JButton) event.target;
            _loc_2.filters = new Array();
            return;
        }//end

        public void  updateElements ()
        {
            this.shelf.updateElements();
            return;
        }//end

        public void  updatePriceMultiplier (double param1 )
        {
            this.shelf.updatePriceMultiplier(param1);
            return;
        }//end

        public void  updateElementsByItemNames (Array param1 )
        {
            this.m_shelf.updateElementsByItemNames(param1);
            return;
        }//end

        protected void  changeCategory (AWEvent event )
        {
            String _loc_2 =null ;
            GameMode _loc_3 =null ;
            if (!Global.isVisiting())
            {
                _loc_2 = event.currentTarget.name;
                _loc_3 = Global.world.getTopGameMode();
                if (_loc_3 instanceof GMPlaceMapResource)
                {
                    Global.world.addGameMode(new GMPlay());
                }
                event.stopImmediatePropagation();
                event.stopPropagation();
                this.switchType(_loc_2);
            }
            return;
        }//end

        protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "bg");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(Catalog.TAB_HEIGHTOFFFSET ));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height + Catalog.TAB_HEIGHTOFFFSET));
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
            _loc_3 = this.m_iconsDict.get(param2) ;
            if (_loc_3)
            {
                _loc_4 = _loc_3.button;
                _loc_5 = _loc_3.image;
                _loc_6 = _loc_3.tab;
                if (_loc_4)
                {
                    _loc_4.upState = _loc_4.overState;
                }
                _loc_6.append(this.m_selectedTab);
                ASwingHelper.setEasyBorder(_loc_6, 0);
            }
            if (param1 != param2 && param1 != "plot_contract" && param1 != "ship_contract")
            {
                _loc_7 = this.m_iconsDict.get(param1);
                if (_loc_7)
                {
                    _loc_8 = _loc_7.button;
                    _loc_9 = _loc_7.image;
                    _loc_10 = _loc_7.tab;
                    _loc_8.upState = _loc_9;
                    _loc_10.remove(this.m_selectedTab);
                    ASwingHelper.setEasyBorder(_loc_10, 14);
                }
            }
            return;
        }//end

        public void  switchType (String param1 )
        {
            JPanel comingSoonPanel ;
            type = param1;
            itemIndex = this.m_tabIndices.get(type) ;
            if (this.shelf)
            {
                this.m_tabIndices.put(this.m_type,  this.shelf.currentItem);
            }
            if (type != "plot_contract" && type != "ship_contract")
            {
                this.switchActiveTab(this.m_type, type);
            }
            this.m_type = type;


            if (this.shelf && this.shelf.hasEventListener(DataItemEvent.MARKET_BUY))
            {
                this.shelf.removeListeners();
                this.shelf.removeEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick);
                this.shelf.removeEventListener(Event.CHANGE, this.onShelfChange);
            }
            this.m_sectionTitle.setText(this.getTitle());
            TextFormat bigForm =new TextFormat ();
            bigForm.size = this.BIG_FONT_SIZE;
            TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), bigForm);
            this.m_centerPanel.removeAll();
            this.loadAvailableItems();
            if (type != "vehicles" && type != "wonders")
            {
                if (type == "new")
                {
                    this .m_newItems =Global .gameSettings .newItems .filter (boolean  (Item param11 ,int param2 ,Array param3 )
            {
                return !LargeCatalogModel.isItemOutOfSchedule(param11);
            }//end
            );
                    this.setShelf(this.makeShelf(this.m_newItems));
                }
                else
                {
                    this.setShelf(this.makeShelf(this.m_items));
                }


                this.shelf.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, 0, true);
                this.shelf.addEventListener(Event.CHANGE, this.onShelfChange);
                ASwingHelper.prepare(this.shelf);
                this.m_centerPanel.append(this.shelf);
            }
            else
            {
                Debug.debug6("ItemCatalogUI.comingSoonPanel.");

                comingSoonPanel = new ComingSoonPanel(type);
                this.m_centerPanel.append(comingSoonPanel);
            }
            this.m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT + 25);
            ASwingHelper.prepare(this.m_centerPanel);
            if (itemIndex != -1)
            {
                this.shelf.scrollToItemIndex(itemIndex);
            }
            this.trackPageView(this.m_type, this.m_subType, this.shelf.currentPageIndex);


            return;
        }//end

        public void  switchSubType (String param1 )
        {
            this.m_subType = param1;
            return;
        }//end

        public int  getIndexOfItem (String param1 )
        {
            _loc_2 = this.shelf.data ;
            _loc_3 = CardUtil.indexOfByCardName(_loc_2,param1);
            return _loc_3;
        }//end

        public void  switchItems (Array param1 ,String param2 )
        {
            if (!param1)
            {
                return;
            }
            _loc_3 = this.m_tabIndices.get(param2) ;
            if (this.shelf)
            {
                this.m_tabIndices.put(this.m_type,  this.shelf.currentItem);
            }
            this.m_type = param2;
            if (this.shelf && this.shelf.hasEventListener(DataItemEvent.MARKET_BUY))
            {
                this.shelf.removeListeners();
                this.shelf.removeEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick);
                this.shelf.removeEventListener(Event.CHANGE, this.onShelfChange);
            }
            this.m_sectionTitle.setText(this.getTitle());
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.size = this.BIG_FONT_SIZE;
            TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), _loc_4);
            this.m_centerPanel.removeAll();
            this.loadAvailableItems(param1);
            this.setShelf(this.makeShelf(this.m_items));
            this.shelf.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, 0, true);
            this.shelf.addEventListener(Event.CHANGE, this.onShelfChange);
            ASwingHelper.prepare(this.shelf);
            this.m_centerPanel.append(this.shelf);
            this.m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT + 25);
            ASwingHelper.prepare(this.m_centerPanel);
            if (_loc_3 != -1)
            {
                this.shelf.scrollToItemIndex(_loc_3);
            }
            this.trackPageView(this.m_type, this.m_subType, this.shelf.currentPageIndex);
            return;
        }//end

        protected String  getTitle ()
        {
            String _loc_1 =null ;
            if (this.m_overrideTitle != null && this.m_overrideTitle.length > 0)
            {
                _loc_1 = ZLoc.t("Dialogs", this.m_overrideTitle + "_menu");
            }
            else
            {
                _loc_1 = ZLoc.t("Dialogs", this.m_type + "_menu");
            }
            return _loc_1;
        }//end

        public void  goToItem (String param1 )
        {
            _loc_2 = this.getIndexOfItem(param1 );
            if (_loc_2 != -1)
            {
                this.shelf.scrollToItemIndex(_loc_2);
            }
            return;
        }//end

        public void  shutdown ()
        {
            this.removeListeners();
            this.setShelf(null);
            this.m_type = null;
            this.m_catalog = null;
            return;
        }//end

        public boolean  isInitialized ()
        {
            return this.m_isInitialized;
        }//end

        public void  onTweenIn ()
        {
            return;
        }//end

        public void  onTweenOut ()
        {
            return;
        }//end

        protected void  removeListeners ()
        {
            JPanel _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_tabsArray.size(); i0++)
            {
            		_loc_1 = this.m_tabsArray.get(i0);

                _loc_1.removeEventListener(MouseEvent.CLICK, this.changeCategory);
            }
            if (stage && stage.hasEventListener(MouseEvent.CLICK))
            {
                stage.removeEventListener(MouseEvent.CLICK, this.onStageClick);
            }
            if (this.shelf && this.shelf.hasEventListener(DataItemEvent.MARKET_BUY))
            {
                this.shelf.removeEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick);
            }
            if (this.shelf)
            {
                this.shelf.removeListeners();
            }
            return;
        }//end

        protected void  addListeners ()
        {
            ItemCatalogUI me ;
            if (stage != null)
            {
                stage.addEventListener(MouseEvent.CLICK, this.onStageClick);
            }
            else
            {
                me = new ItemCatalogUI();
                this .addEventListener (Event .ADDED_TO_STAGE ,void  (Event event )
            {
                me.removeEventListener(Event.ADDED_TO_STAGE, arguments.callee);
                stage.addEventListener(MouseEvent.CLICK, onStageClick);
                return;
            }//end
            );
            }
            this.shelf.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, 0, true);
            return;
        }//end

        protected void  closeCatalog (AWEvent event )
        {
            dispatchEvent(new Event("hidetooltip"));
            this.m_catalog.close();
            Global.marketSessionTracker.endSession(this.m_type);
            return;
        }//end

        protected void  openMenu (AWEvent event )
        {
            dispatchEvent(new UIEvent(UIEvent.OPEN_ACTION_MENU, "", true));
            return;
        }//end

        protected void  onStageClick (MouseEvent event )
        {
            return;
        }//end

        protected void  onBackdropClick (MouseEvent event )
        {
            return;
        }//end

        protected void  onContainerClick (DataItemEvent event )
        {
	    Debug.debug6("ItemCatalogUI.onContainerClick");
            Sounds.playFromSet(Sounds.SET_CLICK);
            _loc_2 = event.item ;
            _loc_3 = _loc_2? (_loc_2) : (null);
            if (this.m_catalog.canBuy(_loc_3))
            {
                this.m_catalog.onBuy(_loc_3, event.target);
            }
            return;
        }//end

        protected void  onShelfChange (Event event )
        {
            this.trackPageView(this.m_type, this.m_subType, this.shelf.currentPageIndex);
            return;
        }//end

        protected void  trackPageView (String param1 ,String param2 ,int param3 )
        {
            StatsManager.count("market_views", param1, param2, "page" + ((param3 + 1)).toString());
            return;
        }//end

        protected void  loadAvailableItems (Array param1)
        {
            Array cats ;
            String str ;
            Array tempItems ;
            overrideItems = param1;
            if (overrideItems)
            {
                this.m_items = overrideItems;
                return;
            }
            this.m_newItems = Global.gameSettings().newItems;

            Debug.debug6("ItemCatalogUI.loadAvailableItems.1."+this.m_newItems.length());

            if (Global.isVisiting() && this.m_type == "business")
            {
                this.m_items = Global.franchiseManager.eligibleFranchises;
                Debug.debug6("ItemCatalogUI.loadAvailableItems.1.1 "+this.m_items.length());
            }
            else
            {
                cats = Global.gameSettings().getBuyableCategories(this.m_type);
                this.m_items = Global.gameSettings().getCurrentBuyableItems(this.m_type, this.m_ignoreExcludeExperiments);
                if (!this.m_exclusive)
                {


                    for(int i0 = 0; i0 < cats.size(); i0++)
                    {
                    		str = cats.get(i0);


                        tempItems = Global.gameSettings().getCurrentBuyableItems(str);
                        this.m_items = this.m_items.concat(tempItems);

                        Debug.debug6("ItemCatalogUI.loadAvailableItems.1.3 "+this.m_items.length());

                    }
                }
            }

            Debug.debug6("ItemCatalogUI.loadAvailableItems.2."+this.m_items.length());


            this .m_items =this .m_items .filter (boolean  (Item param11 ,int param2 ,Array param3 )
            {
                return !LargeCatalogModel.isItemOutOfSchedule(param11);
            }//end
            );

            Debug.debug6("ItemCatalogUI.loadAvailableItems.3."+this.m_items.length());

            this .m_items =this .m_items .filter (boolean  (Item param12 ,int param2 ,Array param3 )
            {
                return !param12.isRemodelSkin();
            }//end
            );

            Debug.debug6("ItemCatalogUI.loadAvailableItems.4."+this.m_items.length());

            return;
        }//end

        public void  setIgnoreExcludeExperiments (boolean param1 )
        {
            this.m_ignoreExcludeExperiments = param1;
            return;
        }//end

        public void  refreshShelf ()
        {
            return;
        }//end

    }



