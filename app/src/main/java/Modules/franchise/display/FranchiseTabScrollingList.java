package Modules.franchise.display;

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

import Display.aswingui.*;
import Modules.franchise.data.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class FranchiseTabScrollingList extends JPanel
    {
        protected GridListCellFactory m_cellFactory ;
        protected GridList m_dataList ;
        protected VectorListModel m_tabData ;
        protected Array m_data ;
        protected JScrollPane m_scrollPane ;
        protected JButton prevButton ;
        protected JButton nextButton ;
        protected int m_columns ;
        protected int m_rows ;
        protected VectorListModel m_model ;
        protected int m_curCount =0;
        protected int m_curIndex =0;
        protected Array m_tabs ;
        public static  int NUM_ITEMS =6;
        private static  int TAB_WIDTH =103;

        public  FranchiseTabScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =1,int param6 =6)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_cellFactory = new param2;
            this.m_columns = param4;
            this.m_rows = param5;
            this.m_data = param1;
            this.makeTabs(param6);
            this.makeButtons();
            this.makeSize();
            this.append(this.prevButton);
            this.append(this.m_scrollPane);
            this.append(this.nextButton);
            ASwingHelper.prepare(this);
            if (FranchiseMenu.selectedFranchise)
            {
                this.setSelected(FranchiseMenu.selectedFranchise.franchiseType);
            }
            return;
        }//end

        protected void  makeTabs (int param1 =6)
        {
            this.m_model = new VectorListModel();
            _loc_2 = Math.min(param1 ,this.m_data.length );
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                this.m_model.append(this.m_data.get(_loc_3));
                this.m_curCount++;
                _loc_3++;
            }
            this.m_curIndex = this.m_curCount - 1;
            OwnedFranchiseData _loc_4 =new OwnedFranchiseData("bus_dummy","",null ,null ,0);
            this.m_tabData = new VectorListModel();
            this.m_tabData.appendAll(this.m_model.toArray());
            this.m_tabData.append(_loc_4);
            this.m_dataList = new GridList(this.m_tabData, this.m_cellFactory, this.m_columns, this.m_rows);
            this.m_tabs = this.m_dataList.getCells().toArray();
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        public void  setSelected (String param1 )
        {
            FranchiseTab _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_tabs.size(); i0++)
            {
            		_loc_2 = this.m_tabs.get(i0);

                if (_loc_2.franchiseType == param1)
                {
                    _loc_2.selectionStatus = true;
                    continue;
                }
                _loc_2.selectionStatus = false;
            }
            return;
        }//end

        protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_prev_normal");
            DisplayObject _loc_2 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_prev_down");
            DisplayObject _loc_3 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_prev_over");
            DisplayObject _loc_4 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_next_normal");
            DisplayObject _loc_5 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_next_down");
            DisplayObject _loc_6 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_next_over");
            this.prevButton = new JButton();
            this.prevButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            this.prevButton.setEnabled(false);
            this.nextButton = new JButton();
            this.nextButton.wrapSimpleButton(new SimpleButton(_loc_4, _loc_6, _loc_5, _loc_4));
            if (this.m_model.getSize() < NUM_ITEMS)
            {
                this.nextButton.setEnabled(false);
            }
            else
            {
                this.nextButton.setEnabled(true);
            }
            this.prevButton.addActionListener(this.moveLeft, 0, true);
            this.nextButton.addActionListener(this.moveRight, 0, true);
            return;
        }//end

        protected void  moveLeft (AWEvent event )
        {
            this.m_curIndex = Math.max((this.m_curIndex - 1), 0);
            if (this.m_curIndex < NUM_ITEMS)
            {
                this.prevButton.setEnabled(false);
                this.m_curIndex = NUM_ITEMS - 1;
            }
            this.nextButton.setEnabled(true);
            this.m_dataList.scrollHorizontal(-TAB_WIDTH);
            return;
        }//end

        protected void  moveRight (AWEvent event )
        {
            int _loc_4 =0;
            _loc_2 = this.m_curCount ;
            _loc_3 = Math.min(this.m_tabData.getSize (),(this.m_curCount +1));
            _loc_3 = Math.min(_loc_3, this.m_data.length());
            if (_loc_3 - _loc_2 > 0)
            {
                if (this.m_model.getSize() < (this.m_curCount + 1))
                {
                    _loc_4 = _loc_2;
                    while (_loc_4 < _loc_3)
                    {

                        this.m_model.append(this.m_data.get(_loc_4));
                        this.m_tabData.insertElementAt(this.m_data.get(_loc_4), (this.m_tabData.getSize() - 1));
                        this.m_curCount++;
                        _loc_4++;
                    }
                    this.m_tabs = this.m_dataList.getCells().toArray();
                }
            }
            this.m_curIndex = Math.min((this.m_curIndex + 1), (this.m_tabData.getSize() - 1));
            if (this.m_curIndex >= this.m_data.length())
            {
                this.nextButton.setEnabled(false);
            }
            this.prevButton.setEnabled(true);
            this.m_dataList.scrollHorizontal(TAB_WIDTH);
            return;
        }//end

        public void  scrollToItemIndex (int param1 )
        {
            if (param1 < 0 || param1 >= this.m_tabData.getSize())
            {
                return;
            }
            while (param1 < this.m_curIndex)
            {

                this.moveLeft(null);
            }
            while (param1 >= (this.m_curIndex + 1))
            {

                this.moveRight(null);
            }
            return;
        }//end

        protected void  makeSize ()
        {
            this.setPreferredHeight(120);
            this.m_scrollPane.setPreferredWidth(TAB_WIDTH * NUM_ITEMS);
            return;
        }//end

        public GridList  dataList ()
        {
            return this.m_dataList;
        }//end

    }



