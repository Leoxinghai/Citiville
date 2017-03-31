package Classes.xgamegifting;

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

import Display.InventoryUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;

    public class XGameGiftingScrollingList extends InventoryScrollingList
    {
        public static  int NUM_ITEMS_PER_PAGE =8;

        public  XGameGiftingScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2,boolean param6 =false )
        {
            super(param1, param2, param3, param4, param5, param6);
            return;
        }//end

        public VectorListModel  model ()
        {
            return this.m_model;
        }//end

         public int  numItems ()
        {
            return NUM_ITEMS_PER_PAGE;
        }//end

        public void  disableElements ()
        {
            XGameGiftSendCell _loc_3 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_curCount)
            {

                _loc_3 =(XGameGiftSendCell) m_dataList.getCellByIndex(_loc_1).getCellComponent();
                _loc_3.disableCell();
                _loc_1++;
            }
            int _loc_2 =0;
            while (_loc_2 < m_data.length())
            {

                m_data.get(_loc_2).put(1,  false);
                _loc_2++;
            }
            return;
        }//end

        public void  enableElements ()
        {
            XGameGiftSendCell _loc_3 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_curCount)
            {

                _loc_3 =(XGameGiftSendCell) m_dataList.getCellByIndex(_loc_1).getCellComponent();
                if (_loc_3)
                {
                    _loc_3.enableCellMindingEligibility();
                }
                _loc_1++;
            }
            int _loc_2 =0;
            while (_loc_2 < m_data.length())
            {

                m_data.get(_loc_2).put(1,  true);
                _loc_2++;
            }
            return;
        }//end

         protected void  initSize ()
        {
            m_scrollPane.setPreferredWidth(XGameGiftSendCell.CARD_WIDTH * m_columns);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            if (!m_hideScrollButtons)
            {
                _loc_1 =(DisplayObject) new assets.get("btn_prev_up");
                _loc_2 =(DisplayObject) new assets.get("btn_prev_over");
                _loc_3 =(DisplayObject) new assets.get("btn_prev_down");
                _loc_4 =(DisplayObject) new assets.get("btn_next_up");
                _loc_5 =(DisplayObject) new assets.get("btn_next_over");
                _loc_6 =(DisplayObject) new assets.get("btn_next_down");
                leftBtn = new JButton();
                leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
                ASwingHelper.setEasyBorder(leftBtn, 35);
                rightBtn = new JButton();
                rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
                ASwingHelper.setEasyBorder(rightBtn, 35);
                rightBtn.addActionListener(moveRight, 0, true);
                leftBtn.addActionListener(moveLeft, 0, true);
                leftBtn.setEnabled(false);
                if (m_data.length <= this.numItems)
                {
                    rightBtn.setEnabled(false);
                }
            }
            return;
        }//end

         protected double  scrollingListWidth ()
        {
            return XGameGiftSendCell.CARD_WIDTH * m_columns;
        }//end

    }



