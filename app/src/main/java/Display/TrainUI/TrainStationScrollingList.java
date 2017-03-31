package Display.TrainUI;

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
import org.aswing.*;
import org.aswing.ext.*;

    public class TrainStationScrollingList extends MarketScrollingList
    {

        public  TrainStationScrollingList (Array param1 ,int param2 ,int param3 =5,int param4 =1)
        {
            super(param1, TrainStopCellFactory, param2, param3, param4);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_prev_up");
            DisplayObject _loc_2 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_prev_over");
            DisplayObject _loc_3 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_prev_down");
            DisplayObject _loc_4 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_next_up");
            DisplayObject _loc_5 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_next_over");
            DisplayObject _loc_6 =(DisplayObject)new TrainStationDialog.assetDict.get( "btn_next_down");
            leftBtn = new JButton();
            leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
            leftBtn.setEnabled(false);
            rightBtn = new JButton();
            rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
            if (m_data.length <= this.numItems)
            {
                rightBtn.setEnabled(false);
            }
            rightBtn.addActionListener(moveRight, 0, true);
            leftBtn.addActionListener(moveLeft, 0, true);
            return;
        }//end

         protected void  makeData ()
        {
            _loc_1 = m_data.length;
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
            m_scrollPane = new JScrollPane(m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

         protected void  prepare ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_1.append(ASwingHelper.verticalStrut(-30));
            _loc_1.append(leftBtn);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.verticalStrut(-30));
            _loc_2.append(rightBtn);
            this.appendAll(_loc_1, m_scrollPane, _loc_2);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected int  scrollingListGap ()
        {
            return 7;
        }//end

         protected double  scrollingListItemWidth ()
        {
            return TrainStopCell.CELL_WIDTH + 7;
        }//end

         protected double  scrollingListItemHeight ()
        {
            return TrainStopCell.CELL_HEIGHT;
        }//end

         public int  numItems ()
        {
            return m_columns;
        }//end

    }



