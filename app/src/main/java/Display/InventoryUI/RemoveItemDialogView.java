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

import Classes.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class RemoveItemDialogView extends GenericDialogView
    {
        protected String m_itemName ;
        protected JPanel m_countPane ;
        protected int m_itemCount =1;
        protected JPanel m_textAreaInnerPane ;
        protected Component m_textAP ;
        protected JPanel m_overlapPane ;
        protected JPanel m_bottomPane ;
        protected JButton m_rightBtn ;
        protected JButton m_leftBtn ;
        public static  int DIALOG_TEXT_WIDTH =350;
        public static  int COUNT_PANE_SIZE =20;
        public static  int OVERLAP_AMOUNT =22;

        public  RemoveItemDialogView (Dictionary param1 ,String param2 ,String param3 ="",String param4 ="",Function param5 =null ,Function param6 =null )
        {
            this.m_itemName = param2;
            super(param1, param3, param4, TYPE_REMOVECANCEL, param5, "", 0, "", param6);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(30, 20, [{locale:"ja", size:30}]);
            _loc_2 = ASwingHelper.makeMultilineText(m_titleString ,DIALOG_TEXT_WIDTH ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,18,EmbeddedArt.blueTextColor );
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_1, 60, 10, 0, 10);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_2 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            _loc_3 = createButtonPanel();
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,10);
            ASwingHelper.setEasyBorder(_loc_2, 10);
            _loc_3 = this.createCenterArea ();
            _loc_2.append(_loc_3);
            this.m_textAP = this.createTextComponent(DIALOG_TEXT_WIDTH);
            this.m_textAreaInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_textAreaInnerPane.append(this.m_textAP);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4.append(this.m_textAreaInnerPane);
            ASwingHelper.prepare(_loc_4);
            _loc_2.append(_loc_4);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            _loc_2 = ASwingHelper.makeMultilineText(m_message ,param1 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,22,EmbeddedArt.brownTextColor );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_3.append(_loc_2);
            _loc_3.append(ASwingHelper.verticalStrut(10));
            _loc_3.append(ASwingHelper.verticalStrut(10));
            return _loc_3;
        }//end

        protected JPanel  createCenterArea ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "btn_minus_up");
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "btn_minus_over");
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "btn_minus_down");
            DisplayObject _loc_5 =(DisplayObject)new m_assetDict.get( "btn_plus_up");
            DisplayObject _loc_6 =(DisplayObject)new m_assetDict.get( "btn_plus_over");
            DisplayObject _loc_7 =(DisplayObject)new m_assetDict.get( "btn_plus_down");
            this.m_leftBtn = new JButton();
            this.m_leftBtn.wrapSimpleButton(new SimpleButton(_loc_2, _loc_3, _loc_4, _loc_2));
            if (Global.player.inventory.getItemCountByName(this.m_itemName) <= 1)
            {
                this.m_leftBtn.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(this.m_leftBtn, 0, 0, 20, 3);
            this.m_rightBtn = new JButton();
            this.m_rightBtn.wrapSimpleButton(new SimpleButton(_loc_5, _loc_6, _loc_7, _loc_5));
            if (Global.player.inventory.getItemCountByName(this.m_itemName) <= 1)
            {
                this.m_rightBtn.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(this.m_rightBtn, 0, 3, 20);
            this.m_rightBtn.addActionListener(this.incrementCount, 0, true);
            this.m_leftBtn.addActionListener(this.decrementCount, 0, true);
            this.m_overlapPane = this.createCell();
            _loc_1.appendAll(this.m_leftBtn, this.m_overlapPane, this.m_rightBtn);
            return _loc_1;
        }//end

        protected void  incrementCount (AWEvent event )
        {
            if (this.m_itemCount < Global.player.inventory.getItemCountByName(this.m_itemName))
            {
                (this.m_itemCount + 1);
            }
            else if (this.m_itemCount == Global.player.inventory.getItemCountByName(this.m_itemName))
            {
                this.m_itemCount = 1;
            }
            this.m_bottomPane.remove(this.m_countPane);
            this.m_countPane = this.makeCountPane();
            this.m_bottomPane.append(this.m_countPane, BorderLayout.EAST);
            _loc_2 = ZLoc.t("Items",this.m_itemName +"_friendlyName");
            m_message = ZLoc.t("Dialogs", "RemoveItemMessage", {numItem:this.m_itemCount, itemName:_loc_2});
            this.m_textAreaInnerPane.remove(this.m_textAP);
            this.m_textAP = this.createTextComponent(DIALOG_TEXT_WIDTH);
            this.m_textAreaInnerPane.append(this.m_textAP);
            return;
        }//end

        protected void  decrementCount (AWEvent event )
        {
            if (this.m_itemCount > 1)
            {
                (this.m_itemCount - 1);
            }
            else if (this.m_itemCount == 1)
            {
                this.m_itemCount = Global.player.inventory.getItemCountByName(this.m_itemName);
            }
            this.m_bottomPane.remove(this.m_countPane);
            this.m_countPane = this.makeCountPane();
            this.m_bottomPane.append(this.m_countPane, BorderLayout.EAST);
            _loc_2 = ZLoc.t("Items",this.m_itemName +"_friendlyName");
            m_message = ZLoc.t("Dialogs", "RemoveItemMessage", {numItem:this.m_itemCount, itemName:_loc_2});
            this.m_textAreaInnerPane.remove(this.m_textAP);
            this.m_textAP = this.createTextComponent(DIALOG_TEXT_WIDTH);
            this.m_textAreaInnerPane.append(this.m_textAP);
            return;
        }//end

        protected JPanel  createCell ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "cell_bg");
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "countBG");
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "countMaxBG");
            MarginBackground _loc_5 =new MarginBackground(_loc_2 );
            _loc_1.setBackgroundDecorator(_loc_5);
            _loc_1.setPreferredSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_1.setMinimumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_1.setMaximumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM ,-OVERLAP_AMOUNT );
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_8 = m_assetDict.get("itemIcon") ;
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_10 =new AssetPane(_loc_8 );
            _loc_11 = _loc_2(.width-_loc_8.width)/2;
            _loc_9.append(ASwingHelper.horizontalStrut(_loc_11));
            _loc_9.append(_loc_10);
            _loc_7.append(_loc_9);
            _loc_1.append(_loc_7);
            _loc_6.append(_loc_1);
            this.m_countPane = this.makeCountPane();
            this.m_bottomPane = new JPanel(new BorderLayout());
            this.m_bottomPane.append(this.m_countPane, BorderLayout.EAST);
            _loc_6.append(this.m_bottomPane);
            ASwingHelper.prepare(_loc_6);
            return _loc_6;
        }//end

        protected JPanel  makeCountPane ()
        {
            DisplayObject _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            int _loc_5 =16777215;
            if (this.m_itemCount >= Global.player.inventory.getItemCountByName(this.m_itemName))
            {
                _loc_3 =(DisplayObject) new InventoryView.assetDict.get("countMaxBG");
            }
            else
            {
                _loc_3 =(DisplayObject) new InventoryView.assetDict.get("countBG");
            }
            _loc_4 = String(this.m_itemCount);
            _loc_1.setBackgroundDecorator(new AssetBackground(_loc_3));
            _loc_1.setPreferredHeight(COUNT_PANE_SIZE);
            _loc_1.setMinimumHeight(COUNT_PANE_SIZE);
            _loc_1.setMaximumHeight(COUNT_PANE_SIZE);
            _loc_6 = ASwingHelper.makeLabel(_loc_4 ,EmbeddedArt.defaultFontNameBold ,10,_loc_5 );
            ASwingHelper.setEasyBorder(_loc_6, 0, 3, 0, 3);
            _loc_2.append(_loc_6);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected void  onAccept (AWEvent event )
        {
            dispatchEvent(new RemoveItemEvent(GenericPopupEvent.SELECTED, YES, this.m_itemName, this.m_itemCount, true));
            if (m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_closeCallback);
            }
            closeMe();
            return;
        }//end

    }



