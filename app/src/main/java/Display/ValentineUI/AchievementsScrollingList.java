package Display.ValentineUI;

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
import Modules.franchise.display.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class AchievementsScrollingList extends FranchiseTabScrollingList
    {
        public static  int NUM_ITEMS =4;
public static  int ROW_HEIGHT =308;
public static  int ROW_WIDTH =548;

        public  AchievementsScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =1)
        {
            super(param1, param2, param3, param4, param5, NUM_ITEMS);
            this.removeAll();
            this.makeSize();
            this.append(prevButton);
            this.appendAll(ASwingHelper.horizontalStrut(10), m_scrollPane, ASwingHelper.horizontalStrut(10));
            this.append(nextButton);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new ValentineDialog.assetDict.get( "prev_normal");
            DisplayObject _loc_2 =(DisplayObject)new ValentineDialog.assetDict.get( "prev_down");
            DisplayObject _loc_3 =(DisplayObject)new ValentineDialog.assetDict.get( "prev_over");
            DisplayObject _loc_4 =(DisplayObject)new ValentineDialog.assetDict.get( "next_normal");
            DisplayObject _loc_5 =(DisplayObject)new ValentineDialog.assetDict.get( "next_down");
            DisplayObject _loc_6 =(DisplayObject)new ValentineDialog.assetDict.get( "next_over");
            prevButton = new JButton();
            prevButton.wrapSimpleButton(new SimpleButton(_loc_1, _loc_3, _loc_2, _loc_1));
            prevButton.setEnabled(false);
            nextButton = new JButton();
            nextButton.wrapSimpleButton(new SimpleButton(_loc_4, _loc_6, _loc_5, _loc_4));
            if (m_data.length <= NUM_ITEMS)
            {
                nextButton.setEnabled(false);
            }
            else
            {
                nextButton.setEnabled(true);
            }
            prevButton.addActionListener(this.moveLeft, 0, true);
            nextButton.addActionListener(this.moveRight, 0, true);
            return;
        }//end

         protected void  makeTabs (int param1 =4)
        {
            m_model = new VectorListModel();
            _loc_2 = Math.min(param1 ,m_data.length );
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                m_model.append(m_data.get(_loc_3));
                _loc_5 = m_curCount+1;
                m_curCount = _loc_5;
                _loc_3++;
            }
            m_dataList = new GridList(m_model, m_cellFactory, m_columns, m_rows);
            m_dataList.setHGap(0);
            m_scrollPane = new JScrollPane(m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

         protected void  makeSize ()
        {
            this.setPreferredHeight(ROW_HEIGHT);
            m_scrollPane.setPreferredWidth(this.getScrollWidth());
            m_scrollPane.setMinimumWidth(this.getScrollWidth());
            m_scrollPane.setMaximumWidth(this.getScrollWidth());
            m_dataList.setHGap(0);
            return;
        }//end

         protected void  moveLeft (AWEvent event )
        {
            m_curIndex = m_curIndex - NUM_ITEMS;
            if (m_curIndex <= 0)
            {
                prevButton.setEnabled(false);
                m_curIndex = 0;
            }
            m_dataList.scrollHorizontal(-this.getScrollWidth());
            nextButton.setEnabled(true);
            return;
        }//end

         protected void  moveRight (AWEvent event )
        {
            int _loc_4 =0;
            int _loc_2 =m_curCount ;
            int _loc_3 =Math.min(m_data.length ,m_curCount +NUM_ITEMS );
            if (_loc_3 - _loc_2 > 0)
            {
                if (m_model.getSize() < NUM_ITEMS + m_curCount)
                {
                    _loc_4 = _loc_2;
                    while (_loc_4 < _loc_3)
                    {

                        m_model.append(m_data.get(_loc_4));

                        m_curCount++;

                        m_curIndex++;
                        _loc_4++;
                    }
                    if (m_curIndex >= this.m_data.length - NUM_ITEMS)
                    {
                        nextButton.setEnabled(false);
                    }
                }
            }
            else if (m_curIndex < m_curCount)
            {
                m_curIndex = m_curIndex + NUM_ITEMS;
                if (m_curIndex >= this.m_data.length - NUM_ITEMS)
                {
                    nextButton.setEnabled(false);
                    m_curIndex = m_data.length - NUM_ITEMS;
                }
            }
            prevButton.setEnabled(true);
            m_dataList.scrollHorizontal(this.getScrollWidth());
            return;
        }//end

        public int  getScrollWidth ()
        {
            return ROW_WIDTH;
        }//end

    }



