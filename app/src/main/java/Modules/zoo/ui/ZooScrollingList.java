package Modules.zoo.ui;

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

    public class ZooScrollingList extends MarketScrollingList
    {
        protected JPanel m_leftButtonPanel ;
        protected JPanel m_rightButtonPanel ;
        public static  int ZOO_ITEMS =6;
        public static  int BUTTON_TOP_BORDER =40;

        public  ZooScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2)
        {
            super(param1, param2, param3, param4, param5);
            return;
        }//end

        public VectorListModel  model ()
        {
            return this.m_model;
        }//end

         protected double  scrollingListItemHeight ()
        {
            return 180;
        }//end

         protected double  scrollingListItemWidth ()
        {
            return 90;
        }//end

         public int  numItems ()
        {
            return ZOO_ITEMS;
        }//end

         protected int  scrollingListGap ()
        {
            return -5;
        }//end

         protected void  prepare ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_1.appendAll(m_scrollPane);
            ASwingHelper.setSizedBackground(_loc_1, ZooDialog.assetDict.get("market_bg"));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.appendAll(this.m_leftButtonPanel, ASwingHelper.verticalStrut(10));
            _loc_3.appendAll(this.m_rightButtonPanel, ASwingHelper.verticalStrut(10));
            this.append(_loc_2);
            this.append(ASwingHelper.horizontalStrut(1));
            this.append(_loc_1);
            this.append(ASwingHelper.horizontalStrut(-2));
            this.append(_loc_3);
            this.setChildIndex(_loc_2, 4);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_6 =null ;
            DisplayObject _loc_1 =(DisplayObject)new ZooDialog.assetDict.get( "left_button_up");
            _loc_2 =(DisplayObject) new ZooDialog.assetDict.get("left_button_over");
            _loc_3 =(DisplayObject) new ZooDialog.assetDict.get("right_button_up");
            DisplayObject _loc_4 =(DisplayObject)new ZooDialog.assetDict.get( "right_button_over");
            DisplayObject _loc_5 =(DisplayObject)new ZooDialog.assetDict.get( "left_button_tab");
            _loc_6 =(DisplayObject) new ZooDialog.assetDict.get("right_button_tab");
            leftBtn = new JButton();
            leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_2, _loc_1));
            leftBtn.setEnabled(false);
            ASwingHelper.setEasyBorder(leftBtn, 15, 2, 0, 0);
            rightBtn = new JButton();
            rightBtn.wrapSimpleButton(new SimpleButton(_loc_3, _loc_4, _loc_4, _loc_3));
            if (m_data.length <= this.numItems)
            {
                rightBtn.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(rightBtn, 15, 2, 0);
            this.m_leftButtonPanel = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            this.m_leftButtonPanel.append(leftBtn);
            ASwingHelper.setSizedBackground(this.m_leftButtonPanel, _loc_5);
            this.m_rightButtonPanel = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            this.m_rightButtonPanel.append(rightBtn);
            ASwingHelper.setSizedBackground(this.m_rightButtonPanel, _loc_6);
            rightBtn.addActionListener(moveRight, 0, true);
            leftBtn.addActionListener(moveLeft, 0, true);
            return;
        }//end

    }



