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

import Display.MarketUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class InventoryScrollingList extends MarketScrollingList
    {
        protected boolean m_hideScrollButtons ;
        protected boolean m_initialized =false ;
        public static  int NUM_INVENTORY_ITEMS =10;
        public static  int INVENTORY_SCROLLINGLIST_WIDTH_OFFSET =7;

        public  InventoryScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2,boolean param6 =false )
        {
            this.m_hideScrollButtons = param6;
            super(param1, param2, param3, param4, param5);
            return;
        }//end

        protected Dictionary  assets ()
        {
            return InventoryView.assetDict;
        }//end

         protected int  scrollingListGap ()
        {
            return -2;
        }//end

         public int  numItems ()
        {
            return NUM_INVENTORY_ITEMS;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            if (!this.m_hideScrollButtons)
            {
                _loc_1 =(DisplayObject) new this.assets.get("btn_prev_up");
                _loc_2 =(DisplayObject) new this.assets.get("btn_prev_over");
                _loc_3 =(DisplayObject) new this.assets.get("btn_prev_down");
                _loc_4 =(DisplayObject) new this.assets.get("btn_next_up");
                _loc_5 =(DisplayObject) new this.assets.get("btn_next_over");
                _loc_6 =(DisplayObject) new this.assets.get("btn_next_down");
                leftBtn = new JButton();
                leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
                rightBtn = new JButton();
                rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
                rightBtn.addActionListener(this.moveRight, 0, true);
                leftBtn.addActionListener(this.moveLeft, 0, true);
                leftBtn.setEnabled(false);
                if (m_data.length <= this.numItems)
                {
                    rightBtn.setEnabled(false);
                }
            }
            return;
        }//end

         protected void  prepare ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            if (!this.m_hideScrollButtons)
            {
                _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_1.append(leftBtn);
                _loc_1.append(ASwingHelper.verticalStrut(28));
                this.append(ASwingHelper.horizontalStrut(3));
                this.append(_loc_1);
                this.append(m_scrollPane);
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_2.append(rightBtn);
                _loc_2.append(ASwingHelper.verticalStrut(28));
                this.append(_loc_2);
            }
            else
            {
                this.append(m_scrollPane);
            }
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

         protected void  initSize ()
        {
            m_scrollPane.setPreferredWidth((Catalog.CARD_WIDTH + Catalog.CARD_OFFSET + INVENTORY_SCROLLINGLIST_WIDTH_OFFSET) * m_columns);
            return;
        }//end

        public void  invalidateData ()
        {
            int _loc_1 =0;
            InventoryCell _loc_2 =null ;
            if (this.m_initialized)
            {
                _loc_1 = 0;
                while (_loc_1 < m_data.length())
                {

                    _loc_2 =(InventoryCell) m_dataList.getCellByIndex(_loc_1);
                    if (_loc_2 != null)
                    {
                        _loc_2.invalidateData();
                    }
                    _loc_1++;
                }
            }
            return;
        }//end

         protected void  makeData ()
        {
            _loc_1 = Math.min(this.numItems ,m_data.length );
            m_model = new VectorListModel();
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                m_model.append(m_data.get(_loc_2));
                _loc_4 = m_curCount+1;
                m_curCount = _loc_4;
                _loc_2++;
            }
            m_dataList = new GridList(m_model, m_cellFactory, m_columns, m_rows);
            m_dataList.setHolderLayout(new GridListHorizontalLayout(m_rows, m_columns, 0, 0));
            m_scrollPane = new JScrollPane(m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

         protected double  scrollingListWidth ()
        {
            return (Catalog.CARD_WIDTH + Catalog.CARD_OFFSET + INVENTORY_SCROLLINGLIST_WIDTH_OFFSET) * m_columns;
        }//end

         public void  scrollToItemIndex (int param1 )
        {
            if (param1 < 0 || param1 >= m_data.length())
            {
                return;
            }
            while (param1 < m_curIndex)
            {

                this.moveLeft(null);
            }
            while (param1 >= m_curIndex + this.numItems)
            {

                this.moveRight(null);
            }
            return;
        }//end

         protected void  moveRight (AWEvent event )
        {
            int _loc_4 =0;
            _loc_2 = m_curCount;
            _loc_3 = Math.min(m_data.length ,m_curCount +this.numItems );
            if (_loc_3 - _loc_2 > 0)
            {
                if (m_model.getSize() < this.numItems + m_curCount)
                {
                    _loc_4 = _loc_2;
                    while (_loc_4 < _loc_3)
                    {

                        m_model.append(m_data.get(_loc_4));
                        _loc_6 = m_curCount+1;
                        m_curCount = _loc_6;
                        _loc_4++;
                    }
                }
            }
            m_curIndex = Math.min(m_curIndex + this.numItems, (m_data.length - 1));
            if (m_curIndex >= m_data.length - this.numItems)
            {
                rightBtn.setEnabled(false);
            }
            leftBtn.setEnabled(true);
            m_dataList.scrollHorizontal(this.scrollingListWidth);
            return;
        }//end

         protected void  moveLeft (AWEvent event )
        {
            m_curIndex = Math.max(m_curIndex - this.numItems, 0);
            if (m_curIndex <= 0)
            {
                leftBtn.setEnabled(false);
                m_curIndex = 0;
            }
            rightBtn.setEnabled(true);
            m_dataList.scrollHorizontal(-this.scrollingListWidth);
            return;
        }//end

         public int  currentItem ()
        {
            return m_curIndex;
        }//end

         public void  updateElements ()
        {
            InventoryCell _loc_2 =null ;
            int _loc_1 =0;
            while (_loc_1 < m_dataList.getCells().getSize())
            {

                _loc_2 =(InventoryCell) m_dataList.getCellByIndex(_loc_1).getCellComponent();
                if (_loc_2)
                {
                    _loc_2.performUpdate();
                }
                _loc_1++;
            }
            return;
        }//end

    }



