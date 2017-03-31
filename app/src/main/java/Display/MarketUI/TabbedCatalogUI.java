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
import Display.*;
import Display.aswingui.*;
import Events.*;
import GameMode.*;
import Modules.factory.ui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class TabbedCatalogUI extends ItemCatalogUI
    {
        private Array m_categoryNames =null ;
        private int m_numTabs =0;
        private int m_autoTabWidth =-1;
        private int m_manualTabWidth =-1;
        private boolean m_allowAutoTabWidth =false ;
        private int m_currentTabIndex =0;
        private static  int PREFERRED_CATALOG_WIDTH =703;
        private static  int PREFERRED_CATALOG_WIDTH_OFFSET =10;

        public  TabbedCatalogUI ()
        {
            this.m_numTabs = 0;
            this.m_autoTabWidth = -1;
            this.m_manualTabWidth = -1;
            this.m_allowAutoTabWidth = false;
            this.m_currentTabIndex = 0;
            return;
        }//end

        public int  numTabs ()
        {
            return this.m_numTabs;
        }//end

        public void  numTabs (int param1 )
        {
            if (param1 == this.m_numTabs)
            {
                return;
            }
            this.m_numTabs = param1;
            this.updateCategoryNames();
            if (this.m_allowAutoTabWidth && this.m_numTabs > 0)
            {
                this.m_autoTabWidth = (PREFERRED_CATALOG_WIDTH - PREFERRED_CATALOG_WIDTH_OFFSET) / this.m_numTabs;
            }
            return;
        }//end

        public int  tabWidth ()
        {
            _loc_1 = this.m_manualTabWidth ;
            if (this.m_allowAutoTabWidth)
            {
                _loc_1 = this.m_autoTabWidth;
            }
            return _loc_1;
        }//end

        public void  tabWidth (int param1 )
        {
            this.allowAutoTabWidth = false;
            this.m_manualTabWidth = param1;
            return;
        }//end

        public boolean  allowAutoTabWidth ()
        {
            return this.m_allowAutoTabWidth;
        }//end

        public void  allowAutoTabWidth (boolean param1 )
        {
            this.m_allowAutoTabWidth = param1;
            if (param1 && this.m_numTabs > 0)
            {
                this.m_autoTabWidth = (PREFERRED_CATALOG_WIDTH - PREFERRED_CATALOG_WIDTH_OFFSET) / this.m_numTabs;
            }
            return;
        }//end

        public Array  categoryNames ()
        {
            if (this.m_categoryNames == null)
            {
                this.updateCategoryNames();
            }
            return this.m_categoryNames;
        }//end

        private void  updateCategoryNames ()
        {
            this.m_categoryNames = new Array();
            int _loc_1 =0;
            while (_loc_1 < this.numTabs)
            {

                this.m_categoryNames.push(this.getTabIdFromIndex(_loc_1));
                _loc_1++;
            }
            return;
        }//end

        protected String  getTabIdFromIndex (int param1 )
        {
            return this.getTabbedCatalogKey() + param1;
        }//end

        protected int  getIndexFromTabId (String param1 )
        {
            if (param1 == null)
            {
                return 0;
            }
            return int(param1.substr(this.getTabbedCatalogKey().length, param1.length()));
        }//end

        public String  getTabbedCatalogKey ()
        {
            return "";
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new TabbedCatalog.assetDict.get( "tab_bg");
            ASwingHelper.setSizedBackground(this, _loc_1);
            return;
        }//end

         protected double  layoutGap ()
        {
            return -3;
        }//end

         protected void  renderTab (String param1 )
        {
            DisplayObject _loc_3 =null ;
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_3 = this.getTabBackground(param1);
            if (this.m_allowAutoTabWidth)
            {
                _loc_3.width = this.m_autoTabWidth;
            }
            else if (this.m_manualTabWidth > 0)
            {
                _loc_3.width = this.m_manualTabWidth;
            }
            ASwingHelper.setSizedBackground(_loc_2, _loc_3);
            _loc_4 = this.getIndexFromTabId(param1 );
            _loc_5 = this.getLocalizedTabName(_loc_4 );
            _loc_6 = EmbeddedArt.titleFont;
            int _loc_7 =14;
            _loc_8 = EmbeddedArt.darkBlueTextColor;
            _loc_9 = ASwingHelper.makeTextField(_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 );
            TextFormat _loc_10 =new TextFormat ();
            _loc_10.size = 16;
            TextFieldUtil.formatSmallCaps(_loc_9.getTextField(), _loc_10);
            _loc_11 = ASwingHelper.centerElement(_loc_9 );
            _loc_2.name = param1;
            m_tabsDict.put(_loc_2,  param1);
            _loc_2.append(_loc_11);
            _loc_2.addEventListener(MouseEvent.CLICK, this.changeCategoryFromTab, false, 0, true);
            _loc_2.addEventListener(MouseEvent.ROLL_OVER, this.showToolTip, false, 0, true);
            _loc_2.addEventListener(MouseEvent.ROLL_OUT, hideToolTip, false, 0, true);
            m_tabsPanel.append(_loc_2);
            return;
        }//end

        protected void  refreshTabs ()
        {
            String _loc_1 =null ;
            m_tabsPanel.removeAll();
            for(int i0 = 0; i0 < this.m_categoryNames.size(); i0++)
            {
            	_loc_1 = this.m_categoryNames.get(i0);

                this.renderTab(_loc_1);
            }
            return;
        }//end

        protected DisplayObject  getTabBackground (String param1 )
        {
            if (param1 == this.getTabIdFromIndex(this.getCurrentTabIndex()))
            {
                return new TabbedCatalog.assetDict.get("tab_active");
            }
            return new TabbedCatalog.assetDict.get("tab_inactive");
        }//end

        public void  setCurrentTabIndex (int param1 )
        {
            _loc_2 = this.getTabIdFromIndex(param1 );
            switchType(_loc_2);
            return;
        }//end

        public int  getCurrentTabIndex ()
        {
            return this.m_currentTabIndex;
        }//end

        protected Array  loadItemsForTab (int param1 )
        {
            return new Array();
        }//end

         protected void  loadAvailableItems (Array param1)
        {
            m_items = this.loadItemsForTab(this.getCurrentTabIndex());
            return;
        }//end

         protected void  showToolTip (MouseEvent event )
        {
            _loc_2 =(JPanel) event.currentTarget;
            m_tabsDict.get(_loc_3 = event.currentTarget as JPanel) ;
            _loc_4 = _loc_2.getGlobalLocation ();
            _loc_5 = _loc_2.getGlobalLocation ().x ;
            Object _loc_6 =new Object ();
            _loc_6.category = _loc_3;
            _loc_6.posX = _loc_5;
            _loc_6.xOffset = 55;
            _loc_6.yOffset = 15;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, _loc_6));
            return;
        }//end

         protected void  switchActiveTab (String param1 ,String param2 )
        {
            _loc_3 = this.getIndexFromTabId(param2 );
            if (this.m_currentTabIndex == _loc_3)
            {
                return;
            }
            _loc_4 = this.getTabIdFromIndex(this.m_currentTabIndex );
            _loc_5 = (JPanel)m_tabsPanel.getChildByName(_loc_4)
            DisplayObject _loc_6 =(DisplayObject)new TabbedCatalog.assetDict.get( "tab_inactive");
            if (this.m_allowAutoTabWidth)
            {
                _loc_6.width = this.m_autoTabWidth;
            }
            else if (this.m_manualTabWidth > 0)
            {
                _loc_6.width = this.m_manualTabWidth;
            }
            ASwingHelper.setSizedBackground(_loc_5, _loc_6);
            _loc_7 = (JPanel)m_tabsPanel.getChildByName(param2)
            DisplayObject _loc_8 =(DisplayObject)new TabbedCatalog.assetDict.get( "tab_active");
            if (this.m_allowAutoTabWidth)
            {
                _loc_8.width = this.m_autoTabWidth;
            }
            else if (this.m_manualTabWidth > 0)
            {
                _loc_8.width = this.m_manualTabWidth;
            }
            ASwingHelper.setSizedBackground(_loc_7, _loc_8);
            _loc_9 = this.getIndexFromTabId(param2 );
            this.m_currentTabIndex = _loc_9;
            return;
        }//end

        protected void  changeCategoryFromTab (MouseEvent event )
        {
            String _loc_2 =null ;
            GameMode _loc_3 =null ;
            if (!Global.isVisiting())
            {
                _loc_2 = event.currentTarget.name;
                this.setCurrentTabIndex(this.getIndexFromTabId(_loc_2));
                _loc_3 = Global.world.getTopGameMode();
                if (_loc_3 instanceof GMPlaceMapResource)
                {
                    Global.world.addGameMode(new GMPlay());
                }
                event.stopImmediatePropagation();
                event.stopPropagation();
            }
            return;
        }//end

         protected MarketScrollingList  makeShelf (Array param1 )
        {
            return new TabbedMarketScrollingList(param1, FactoryCell, 0, 7, 1);
        }//end

         protected void  onMarketDown (MouseEvent event )
        {
            GMDefault _loc_6 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            Point _loc_3 =new Point(Global.ui.mouseX ,Global.ui.mouseY );
            _loc_4 =Global.ui.m_bottomUI.getObjectsUnderPoint(_loc_3 );
            int _loc_5 =0;
            _loc_5 = 0;
            while (_loc_5 < _loc_4.length())
            {

                if (_loc_4.get(_loc_5) instanceof TabbedCatalog.assetDict.get("tab_bg"))
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

         protected void  makeTopTabs (Array param1)
        {
            String _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            JPanel _loc_5 =null ;
            JPanel _loc_6 =null ;
            String _loc_7 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                this.renderTab(_loc_2);
            }
            m_tabsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0));
            m_tabsPanel.setPreferredWidth(PREFERRED_CATALOG_WIDTH);
            ASwingHelper.setEasyBorder(m_tabsPanel, 0, 25);
            m_catalog.addEventListener(MouseEvent.MOUSE_DOWN, this.onMarketDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_MOVE, this.onMarketDown, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OVER, onMarketOver, false, 0, true);
            m_catalog.addEventListener(MouseEvent.MOUSE_OUT, onMarketOut, false, 0, true);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_closeButton = ASwingHelper.makeMarketCloseButton();
            m_closeButton.addActionListener(closeCatalog, 0, true);
            _loc_4.appendAll(m_closeButton, ASwingHelper.verticalStrut(10));
            _loc_3.append(_loc_4);
            _loc_3.append(ASwingHelper.horizontalStrut(15));
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_5.append(ASwingHelper.horizontalStrut(15));
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 10);
            if (this.m_overrideTitle != "")
            {
                _loc_7 = ZLoc.t("Dialogs", m_overrideTitle + "_menu");
            }
            else
            {
                _loc_7 = ZLoc.t("Dialogs", m_type + "_menu");
            }
            m_sectionTitle = ASwingHelper.makeTextField("", EmbeddedArt.titleFont, 30, EmbeddedArt.titleColor, 5);
            _loc_8 = ASwingHelper.makeTextField(_loc_7 ,EmbeddedArt.titleFont ,24,EmbeddedArt.titleColor ,5);
            _loc_8.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_9 =new TextFormat ();
            _loc_9.size = 30;
            TextFieldUtil.formatSmallCaps(_loc_8.getTextField(), _loc_9);
            ASwingHelper.setEasyBorder(_loc_6, 3, 0, 2);
            _loc_6.appendAll(_loc_8);
            m_topPanel.append(_loc_5, BorderLayout.WEST);
            m_topPanel.append(_loc_6, BorderLayout.CENTER);
            m_topPanel.append(_loc_3, BorderLayout.EAST);
            this.append(m_topPanel);
            this.append(m_tabsPanel);
            this.append(ASwingHelper.verticalStrut(-5));
            ASwingHelper.prepare(this);
            return;
        }//end

        public String  getLocalizedTabName (int param1 )
        {
            return "";
        }//end

         public void  refreshShelf ()
        {
            this.setCurrentTabIndex(this.getCurrentTabIndex());
            return;
        }//end

    }



