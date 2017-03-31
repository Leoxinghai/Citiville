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
import Display.aswingui.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class MarketScrollingList extends JPanel
    {
        protected GridList m_dataList ;
        protected JScrollPane m_scrollPane ;
        protected Class m_cellFactoryClass ;
        protected GridListCellFactory m_cellFactory ;
        protected JViewport m_viewport ;
        protected JWindow m_frame ;
        protected JButton rightBtn ;
        protected JButton leftBtn ;
        protected int m_rows ;
        protected int m_columns ;
        protected Array m_data ;
        protected int m_curCount =0;
        protected int m_curIndex =0;
        protected VectorListModel m_model ;
        private double m_priceMultiplier =1;
        public static  int SCROLL_VERTICAL =0;
        public static  int SCROLL_HORIZONTAL =1;
        public static  int NUM_ITEMS =7;

        public  MarketScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, this.scrollingListGap, SoftBoxLayout.CENTER));
            this.m_columns = param4;
            this.m_rows = param5;
            this.m_data = param1;
            this.m_cellFactoryClass = param2;
            this.m_cellFactory = this.createCellFactory();
            this.makeData();
            this.makeButtons();
            this.initSize();
            this.prepare();
            return;
        }//end

        public void  dispose ()
        {
            return;
        }//end

        public int  numItems ()
        {
            return NUM_ITEMS;
        }//end

        public int  curIndex ()
        {
            return this.m_curIndex;
        }//end

        public int  currentPageIndex ()
        {
            return (this.m_curIndex + (this.numItems - 1)) / this.numItems;
        }//end

        protected int  scrollingListGap ()
        {
            return 3;
        }//end

        protected GridListCellFactory  createCellFactory ()
        {
            return new this.m_cellFactoryClass();
        }//end

        protected void  makeData ()
        {
            int _loc_1 =0;
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_SHOW );
            if (!_loc_2)
            {
                _loc_1 = 0;
                while (_loc_1 < this.m_data.length())
                {

                    if (this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "supply_all_business_friendlyName") || this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName") || this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
                    {
                        this.m_data.splice(_loc_1, 1);
                        _loc_1 = _loc_1 - 1;
                    }
                    _loc_1++;
                }
            }
            else
            {
                _loc_1 = 0;
                while (_loc_1 < this.m_data.length())
                {

                    if (_loc_2 == 1)
                    {
                        if (this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "supply_all_business_friendlyName") || this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName"))
                        {
                            this.m_data.splice(_loc_1, 1);
                            _loc_1 = _loc_1 - 1;
                        }
                    }
                    else if (_loc_2 == 2)
                    {
                        if (this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName") || this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
                        {
                            this.m_data.splice(_loc_1, 1);
                            _loc_1 = _loc_1 - 1;
                        }
                    }
                    else if (this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "supply_all_business_friendlyName") || this.m_data.get(_loc_1).localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
                    {
                        this.m_data.splice(_loc_1, 1);
                        _loc_1 = _loc_1 - 1;
                    }
                    _loc_1++;
                }
            }
            _loc_3 = Math.min(this.numItems ,this.m_data.length );
            this.m_model = new VectorListModel();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                this.m_model.append(this.m_data.get(_loc_4));
                this.m_curCount++;
                _loc_4++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, this.m_columns, this.m_rows);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        public Array  data ()
        {
            return this.m_data;
        }//end

        public GridList  dataList ()
        {
            return this.m_dataList;
        }//end

        protected void  prepare ()
        {
            this.append(this.leftBtn);
            this.append(this.m_scrollPane);
            this.append(this.rightBtn);
            ASwingHelper.setEasyBorder(this, 0, 0, 0, 3);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  initSize ()
        {
            this.setPreferredHeight(this.scrollingListItemHeight * this.m_rows);
            this.m_scrollPane.setPreferredWidth((this.scrollingListItemWidth - 1) * this.numItems);
            this.m_dataList.setHGap(7);
            return;
        }//end

        protected Insets  getLeftInsets ()
        {
            return new Insets(0, 0, 20, 3);
        }//end

        protected Insets  getRightInsets ()
        {
            return new Insets(0, 3, 20, 0);
        }//end

        protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "btn_left_offstate");
            DisplayObject _loc_2 =(DisplayObject)new Catalog.assetDict.get( "btn_left_onstate");
            DisplayObject _loc_3 =(DisplayObject)new Catalog.assetDict.get( "btn_left_downstate");
            DisplayObject _loc_4 =(DisplayObject)new Catalog.assetDict.get( "btn_right_offstate");
            DisplayObject _loc_5 =(DisplayObject)new Catalog.assetDict.get( "btn_right_onstate");
            DisplayObject _loc_6 =(DisplayObject)new Catalog.assetDict.get( "btn_right_downstate");
            this.leftBtn = new JButton();
            this.leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
            this.leftBtn.setEnabled(false);
            _loc_7 = this.getLeftInsets ();
            ASwingHelper.setEasyBorder(this.leftBtn, _loc_7.top, _loc_7.left, _loc_7.bottom, _loc_7.right);
            this.rightBtn = new JButton();
            this.rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
            if (this.m_data.length <= this.numItems)
            {
                this.rightBtn.setEnabled(false);
            }
            _loc_8 = this.getRightInsets ();
            ASwingHelper.setEasyBorder(this.rightBtn, _loc_8.top, _loc_8.left, _loc_8.bottom);
            this.rightBtn.addActionListener(this.moveRight, 0, true);
            this.leftBtn.addActionListener(this.moveLeft, 0, true);
            return;
        }//end

        public void  removeListeners ()
        {
            MarketCell _loc_2 =null ;
            this.rightBtn.removeActionListener(this.moveRight);
            this.leftBtn.removeActionListener(this.moveLeft);
            int _loc_1 =0;
            while (_loc_1 < this.m_curCount)
            {

                _loc_2 =(MarketCell) this.m_dataList.getCellByIndex(_loc_1).getCellComponent();
                _loc_2.removeListeners();
                _loc_1++;
            }
            return;
        }//end

        public void  updateElements ()
        {
            MarketCell _loc_2 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_curCount)
            {

                _loc_2 =(MarketCell) this.m_dataList.getCellByIndex(_loc_1).getCellComponent();
                _loc_2.performUpdate();
                _loc_1++;
            }
            return;
        }//end

        public void  updatePriceMultiplier (double param1 )
        {
            int _loc_2 =0;
            MarketCell _loc_3 =null ;
            if (param1 != this.m_priceMultiplier)
            {
                this.m_priceMultiplier = param1;
                _loc_2 = 0;
                while (_loc_2 < this.m_curCount)
                {

                    _loc_3 =(MarketCell) this.m_dataList.getCellByIndex(_loc_2).getCellComponent();
                    _loc_3.updatePriceMultiplier(param1);
                    _loc_2++;
                }
            }
            return;
        }//end

        public void  updateElementsByItemNames (Array param1 )
        {
            MarketCell _loc_3 =null ;
            Item _loc_4 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.m_curCount)
            {

                _loc_3 =(MarketCell) this.m_dataList.getCellByIndex(_loc_2).getCellComponent();
                _loc_4 =(Item) _loc_3.getCellValue();
                if (_loc_4)
                {
                    if (param1.indexOf(_loc_4.name) != -1)
                    {
                        _loc_3.performUpdate(true);
                    }
                }
                _loc_2++;
            }
            return;
        }//end

        public void  scrollToItemIndex (int param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            MarketCell _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            if (param1 < this.m_curCount - this.numItems)
            {
                this.m_dataList.scrollHorizontal(0);
                this.m_dataList.scrollHorizontal(this.scrollingListItemWidth * param1);
            }
            else if (param1 < this.m_curCount)
            {
                _loc_2 = this.m_curCount;
                _loc_3 = Math.min(this.m_data.length, param1 + this.numItems);
                if (param1 != 0)
                {
                    this.leftBtn.setEnabled(true);
                }
                if (_loc_3 - _loc_2 > 0)
                {
                    if (this.m_model.getSize() < this.numItems + param1)
                    {
                        _loc_5 = _loc_2;
                        while (_loc_5 < _loc_3)
                        {

                            this.m_model.append(this.m_data.get(_loc_5));
                            _loc_4 =(MarketCell) this.m_dataList.getCellByIndex(_loc_5).getCellComponent();
                            _loc_4.updatePriceMultiplier(this.m_priceMultiplier);
                            this.m_curCount++;
                            this.m_curIndex++;
                            _loc_5++;
                        }
                    }
                }
                this.m_dataList.scrollHorizontal(0);
                this.m_dataList.scrollHorizontal(this.scrollingListItemWidth * param1);
            }
            else
            {
                _loc_2 = this.m_curCount;
                if (_loc_2 != 0)
                {
                    this.leftBtn.setEnabled(true);
                }
                _loc_3 = Math.min(this.m_data.length, param1 + this.numItems);
                if (_loc_3 - _loc_2 > 0)
                {
                    if (this.m_model.getSize() < this.numItems + param1)
                    {
                        _loc_6 = _loc_2;
                        while (_loc_6 < _loc_3)
                        {

                            this.m_model.append(this.m_data.get(_loc_6));
                            _loc_4 =(MarketCell) this.m_dataList.getCellByIndex(_loc_6).getCellComponent();
                            _loc_4.updatePriceMultiplier(this.m_priceMultiplier);
                            this.m_curCount++;
                            this.m_curIndex++;
                            _loc_6++;
                        }
                    }
                }
            }
            this.m_dataList.scrollHorizontal(0);
            this.m_dataList.scrollHorizontal(this.scrollingListItemWidth * param1);
            return;
        }//end

        protected void  moveLeft (AWEvent event )
        {
            this.m_curIndex = this.m_curIndex - this.numItems;
            if (this.m_curIndex <= 0)
            {
                this.leftBtn.setEnabled(false);
                this.m_curIndex = 0;
            }
            this.m_dataList.scrollHorizontal(-this.scrollingListWidth);
            this.rightBtn.setEnabled(true);
            dispatchEvent(new Event(Event.CHANGE));
            return;
        }//end

        protected void  moveRight (AWEvent event )
        {
            MarketCell _loc_4 =null ;
            int _loc_5 =0;
            _loc_2 = this.m_curCount ;
            _loc_3 = Math.min(this.m_data.length ,this.m_curCount +this.numItems );
            if (_loc_3 - _loc_2 > 0)
            {
                if (this.m_model.getSize() < this.numItems + this.m_curCount)
                {
                    _loc_5 = _loc_2;
                    while (_loc_5 < _loc_3)
                    {

                        this.m_model.append(this.m_data.get(_loc_5));
                        _loc_4 =(MarketCell) this.m_dataList.getCellByIndex(_loc_5).getCellComponent();
                        _loc_4.updatePriceMultiplier(this.m_priceMultiplier);
                        this.m_curCount++;
                        this.m_curIndex++;
                        _loc_5++;
                    }
                    if (this.m_curIndex >= this.m_data.length - this.numItems)
                    {
                        this.rightBtn.setEnabled(false);
                    }
                }
            }
            else if (this.m_curIndex < this.m_curCount)
            {
                this.m_curIndex = this.m_curIndex + this.numItems;
                if (this.m_curIndex >= this.m_data.length - this.numItems)
                {
                    this.rightBtn.setEnabled(false);
                    this.m_curIndex = this.m_data.length - this.numItems;
                }
            }
            this.leftBtn.setEnabled(true);
            this.m_dataList.scrollHorizontal(this.scrollingListWidth);
            dispatchEvent(new Event(Event.CHANGE));
            return;
        }//end

        public int  currentItem ()
        {
            _loc_1 = this.m_curIndex -this.numItems ;
            if (_loc_1 < 0)
            {
                _loc_1 = 0;
            }
            return _loc_1;
        }//end

        public String  currentItemName ()
        {
            _loc_1 = this.m_data.get(this.currentItem) ;
            if (_loc_1 != null)
            {
                return _loc_1.name;
            }
            return null;
        }//end

        protected double  scrollingListWidth ()
        {
            return this.scrollingListItemWidth * this.numItems;
        }//end

        protected double  scrollingListItemWidth ()
        {
            return Catalog.CARD_WIDTH + Catalog.CARD_OFFSET + 5;
        }//end

        protected double  scrollingListItemHeight ()
        {
            return Catalog.CARD_HEIGHT;
        }//end

        public double  getItemWidth ()
        {
            return this.scrollingListItemWidth;
        }//end

        public double  getItemHeight ()
        {
            return this.scrollingListItemHeight;
        }//end

    }



