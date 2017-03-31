package Display.FactoryUI;

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
import org.aswing.event.*;
import org.aswing.ext.*;

    public class SuppliesScrollingList extends MarketScrollingList
    {

        public  SuppliesScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =0)
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected void  makeData ()
        {
            Array _loc_1 =new Array ();
            Array _loc_2 =new Array ();
            int _loc_3 =0;
            while (_loc_3 < m_data.length())
            {

                if (_loc_3 % 2 == 0)
                {
                    _loc_1.push(m_data.get(_loc_3));
                }
                else
                {
                    _loc_2.push(m_data.get(_loc_3));
                }
                _loc_3++;
            }
            _loc_4 = _loc_1.concat(_loc_2);
            VectorListModel _loc_5 =new VectorListModel ();
            int _loc_6 =0;
            while (_loc_6 < _loc_4.length())
            {

                _loc_5.append(_loc_4.get(_loc_6));
                _loc_6++;
            }
            m_dataList = new GridList(_loc_5, m_cellFactory, m_columns, m_rows);
            m_scrollPane = new JScrollPane(m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

         protected void  prepare ()
        {
            this.append(leftBtn);
            this.append(m_scrollPane);
            this.append(rightBtn);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new SuppliesDialog.assetDict.get( "left_green_arrow");
            DisplayObject _loc_2 =(DisplayObject)new SuppliesDialog.assetDict.get( "right_green_arrow");
            leftBtn = new JButton();
            leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_1, _loc_1, _loc_1));
            rightBtn = new JButton();
            rightBtn.wrapSimpleButton(new SimpleButton(_loc_2, _loc_2, _loc_2, _loc_2));
            rightBtn.addActionListener(this.moveRight, 0, true);
            leftBtn.addActionListener(this.moveLeft, 0, true);
            return;
        }//end

         protected void  initSize ()
        {
            this.m_dataList.setHGap(4);
            this.m_dataList.setVGap(10);
            m_scrollPane.setPreferredWidth(494);
            return;
        }//end

         protected void  moveLeft (AWEvent event )
        {
            m_dataList.scrollHorizontal(-249);
            return;
        }//end

         protected void  moveRight (AWEvent event )
        {
            m_dataList.scrollHorizontal(249);
            return;
        }//end

    }




