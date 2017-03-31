package Modules.mechanics.ui;

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

import Display.GridlistUI.model.*;
import Display.MarketUI.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class SlottedContainerScrollingList extends TabbedMarketScrollingList
    {
        protected SlottedContainerConfig m_config ;

        public  SlottedContainerScrollingList (Array param1 ,Class param2 ,SlottedContainerConfig param3 ,int param4 ,int param5 =0,int param6 =2)
        {
            this.m_config = param3;
            super(param1, param2, param4, param5, param6);
            return;
        }//end

         protected GridListCellFactory  createCellFactory ()
        {
            return new SlottedContainerGridCellFactory(m_gridCellClass, this.m_config);
        }//end

         protected void  makeData ()
        {
            m_model = new VectorListModel();
            _loc_1 = Math.min(numItems ,m_data.length );
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

         public void  removeListeners ()
        {
            GenericCellModel _loc_2 =null ;
            rightBtn.removeActionListener(moveRight);
            leftBtn.removeActionListener(moveLeft);
            int _loc_1 =0;
            while (_loc_1 < this.m_curCount)
            {

                _loc_2 =(GenericCellModel) m_dataList.getCellByIndex(_loc_1);
                if (_loc_2)
                {
                    _loc_2.removeListeners();
                }
                _loc_1++;
            }
            return;
        }//end

    }



