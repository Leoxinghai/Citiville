package Modules.garden.ui;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class GardenScrollingList extends MarketScrollingList
    {
        protected JPanel m_leftButtonPanel ;
        protected JPanel m_rightButtonPanel ;
        protected ISlotMechanic m_slotMechanic ;
        protected Garden m_garden ;
        protected GardenDialogView m_gardenView ;
        protected boolean m_showButtons ;
        public static  int GARDEN_ITEMS =6;
        public static  int BUTTON_TOP_BORDER =40;

        public  GardenScrollingList (GardenDialogView param1 ,ISlotMechanic param2 ,Garden param3 ,Array param4 ,Class param5 ,int param6 ,int param7 =0,int param8 =2)
        {
            this.m_showButtons = param4.length > param7;
            this.m_slotMechanic = param2;
            this.m_garden = param3;
            this.m_gardenView = param1;
            super(param4, param5, param6, param7, param8);
            return;
        }//end

         protected GridListCellFactory  createCellFactory ()
        {
            return new GardenCellFactory(this.m_gardenView, this.m_slotMechanic, this.m_garden);
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
            return GARDEN_ITEMS;
        }//end

         protected int  scrollingListGap ()
        {
            return -5;
        }//end

         protected void  prepare ()
        {
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_1.appendAll(m_scrollPane);
            ASwingHelper.setSizedBackground(_loc_1, GardenDialog.assetDict.get("market_bg"));
            if (this.m_showButtons)
            {
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_2.appendAll(this.m_leftButtonPanel, ASwingHelper.verticalStrut(10));
                _loc_3.appendAll(this.m_rightButtonPanel, ASwingHelper.verticalStrut(10));
            }
            if (this.m_showButtons)
            {
                this.append(_loc_2);
            }
            this.append(ASwingHelper.horizontalStrut(1));
            this.append(_loc_1);
            this.append(ASwingHelper.horizontalStrut(-2));
            if (this.m_showButtons)
            {
                this.append(_loc_3);
                this.setChildIndex(_loc_2, 4);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButtons ()
        {
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            if (!this.m_showButtons)
            {
                return;
            }
            DisplayObject _loc_1 =(DisplayObject)new GardenDialog.assetDict.get( "left_button_up");
            _loc_2 =(DisplayObject) new GardenDialog.assetDict.get("left_button_over");
            _loc_3 =(DisplayObject) new GardenDialog.assetDict.get("right_button_up");
            _loc_4 =(DisplayObject) new GardenDialog.assetDict.get("right_button_over");
            _loc_5 =(DisplayObject) new GardenDialog.assetDict.get("left_button_tab");
            _loc_6 =(DisplayObject) new GardenDialog.assetDict.get("right_button_tab");
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



