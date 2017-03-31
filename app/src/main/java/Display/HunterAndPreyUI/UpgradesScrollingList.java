package Display.HunterAndPreyUI;

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
import Events.*;
import Modules.bandits.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class UpgradesScrollingList extends JPanel
    {
        protected GridListCellFactory m_cellFactory ;
        protected GridList m_dataList ;
        protected Array m_data ;
        protected JScrollPane m_scrollPane ;
        protected JButton prevButton ;
        protected JButton nextButton ;
        protected int m_columns ;
        protected int m_rows ;
        protected VectorListModel m_model ;
        protected int m_curCount =0;
        protected int m_curIndex =0;
        protected int m_initNumItems ;
        protected boolean m_showScrollButtons ;
        protected  int ROW_WIDTH =UpgradesCell.CELL_WIDTH *NUM_ITEMS ;
        protected  int ROW_HEIGHT =UpgradesCell.CELL_HEIGHT ;
        public static  int NUM_ITEMS =4;

        public  UpgradesScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =1,int param6 =4)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_cellFactory = new param2;
            this.m_columns = param4;
            this.m_rows = param5;
            this.m_data = param1;
            this.m_initNumItems = param6;
            this.m_showScrollButtons = this.m_columns < this.m_data.length;
            return;
        }//end

        public void  create ()
        {
            this.makeScroller();
            this.makeButtons();
            this.makeSize();
            if (this.m_showScrollButtons)
            {
                this.append(this.prevButton);
            }
            this.appendAll(ASwingHelper.horizontalStrut(10), this.m_scrollPane, ASwingHelper.horizontalStrut(10));
            if (this.m_showScrollButtons)
            {
                this.append(this.nextButton);
            }
            this.addBorder();
            this.scrollToCurrent();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  addBorder ()
        {
            Sprite _loc_1 =new Sprite ();
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_innerBorder");
            int _loc_3 =0;
            if (!this.m_showScrollButtons)
            {
                _loc_3 = 31;
            }
            _loc_2.width = 597;
            _loc_2.height = 343;
            _loc_2.x = 39 - _loc_3;
            _loc_2.y = -1.5;
            _loc_1.addChild(_loc_2);
            boolean _loc_7 =false ;
            _loc_1.mouseEnabled = false;
            _loc_1.mouseChildren = _loc_7;
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            (new HunterDialog.assetDict.get("police_divider")).x = 186 - _loc_3;
            _loc_4.y = 50;
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            (new HunterDialog.assetDict.get("police_divider")).x = 333 - _loc_3;
            _loc_5.y = 50;
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "police_divider");
            (new HunterDialog.assetDict.get("police_divider")).x = 481 - _loc_3;
            _loc_6.y = 50;
            _loc_1.addChild(_loc_4);
            _loc_1.addChild(_loc_5);
            _loc_1.addChild(_loc_6);
            addChild(_loc_1);
            return;
        }//end

        protected void  makeScroller ()
        {
            this.m_model = new VectorListModel();
            _loc_1 = Math.min(this.m_initNumItems ,this.m_data.length );
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                this.m_model.append(this.m_data.get(_loc_2));
                this.m_curCount++;
                _loc_2++;
            }
            this.m_dataList = new GridList(this.m_model, this.m_cellFactory, this.m_columns, this.m_rows);
            this.m_dataList.setHGap(0);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            this.m_dataList.addEventListener(UIEvent.CHANGE_CREW_STATE, this.refreshUpperCells, false, 0, true);
            return;
        }//end

        protected void  refreshUpperCells (UIEvent event )
        {
            UpgradesCell _loc_4 =null ;
            _loc_2 =(UpgradesCell) event.target;
            _loc_3 = _loc_2.position ;
            if (_loc_3 < this.m_data.length())
            {
                if (_loc_3 < 6 && this.m_data.get(_loc_3).newItemName != null)
                {
                    _loc_4 =(UpgradesCell) this.m_dataList.getCellByIndex(_loc_3);
                    if (_loc_4 != null)
                    {
                        _loc_4.enableUpgradeButton();
                    }
                }
            }
            return;
        }//end

        protected void  makeButtons ()
        {
            if (!this.m_showScrollButtons)
            {
                return;
            }
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_up");
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_down");
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "police_prevButton_over");
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_up");
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_down");
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "police_nextButton_over");
            this.prevButton = new JButton();
            this.prevButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            this.prevButton.setEnabled(false);
            this.nextButton = new JButton();
            this.nextButton.wrapSimpleButton(new SimpleButton(_loc_4, _loc_6, _loc_5, _loc_4));
            if (this.m_data.length <= NUM_ITEMS)
            {
                this.nextButton.setEnabled(false);
            }
            else
            {
                this.nextButton.setEnabled(true);
            }
            ASwingHelper.setEasyBorder(this.nextButton, 0, 0, 31);
            ASwingHelper.setEasyBorder(this.prevButton, 0, 0, 31, 0);
            this.prevButton.addActionListener(this.moveLeft, 0, true);
            this.nextButton.addActionListener(this.moveRight, 0, true);
            return;
        }//end

        protected void  makeSize ()
        {
            this.setPreferredHeight(this.ROW_HEIGHT);
            ASwingHelper.setForcedWidth(this.m_scrollPane, this.ROW_WIDTH);
            this.m_dataList.setHGap(0);
            return;
        }//end

        public void  scrollToCurrent ()
        {
            int _loc_2 =0;
            _loc_1 = PreyUtil.getHubLevel(HunterDialog.groupId);
            if (this.m_initNumItems > 0 && _loc_1 > 0)
            {
                _loc_2 = Math.max(Math.ceil(_loc_1 / this.m_initNumItems), 0);
                while (--_loc_2 > 0)
                {

                    this.moveRight(null);
                }
            }
            return;
        }//end

        protected void  moveLeft (AWEvent event )
        {
            this.m_curIndex = this.m_curIndex - NUM_ITEMS;
            if (this.m_curIndex <= 0)
            {
                this.prevButton.setEnabled(false);
                this.m_curIndex = 0;
            }
            this.m_dataList.scrollHorizontal(-this.ROW_WIDTH);
            this.nextButton.setEnabled(true);
            return;
        }//end

        protected void  moveRight (AWEvent event )
        {
            int _loc_4 =0;
            _loc_2 = this.m_curCount ;
            _loc_3 = Math.min(this.m_data.length ,this.m_curCount +NUM_ITEMS );
            if (_loc_3 - _loc_2 > 0)
            {
                if (this.m_model.getSize() < NUM_ITEMS + this.m_curCount)
                {
                    _loc_4 = _loc_2;
                    while (_loc_4 < _loc_3)
                    {

                        this.m_model.append(this.m_data.get(_loc_4));
                        this.m_curCount++;
                        this.m_curIndex++;
                        _loc_4++;
                    }
                    if (this.m_curIndex >= this.m_data.length - NUM_ITEMS)
                    {
                        this.nextButton.setEnabled(false);
                    }
                }
            }
            else if (this.m_curIndex < this.m_curCount)
            {
                this.m_curIndex = this.m_curIndex + NUM_ITEMS;
                if (this.m_curIndex >= this.m_data.length - NUM_ITEMS)
                {
                    this.nextButton.setEnabled(false);
                    this.m_curIndex = this.m_data.length - NUM_ITEMS;
                }
            }
            this.prevButton.setEnabled(true);
            this.m_dataList.scrollHorizontal(this.ROW_WIDTH);
            return;
        }//end

    }



