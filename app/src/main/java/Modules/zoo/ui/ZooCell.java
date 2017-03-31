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

import Classes.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.zoo.events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.event.*;

    public class ZooCell extends DataItemCell
    {
        protected JPanel m_itemIconPane ;
        protected JPanel m_cardPane ;
        private AssetPane m_iconPane ;
        private JTextField m_counter ;
        private int m_count ;
        private boolean m_bHasActiveAnimals =true ;
        protected JPanel m_counterPane ;
        private CustomButton m_placeBtn ;
        private CustomButton m_buyBtn ;

        public  ZooCell (LayoutManager param1)
        {
            _loc_2 = param1;
            if (_loc_2 == null)
            {
                _loc_2 = new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 2, SoftBoxLayout.CENTER);
            }
            super(_loc_2);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            param1 =(Object) param1;
            m_item = param1;
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            ZooDialogView.currentInstance.addTooltipTarget(this, ZLoc.t("Dialogs", "ZooDialog_animalTooltip_" + m_item.name));
            this.buildCell();
            return;
        }//end

        public Item  animal ()
        {
            return this.m_item;
        }//end

        public String  animalName ()
        {
            return this.m_item.name;
        }//end

        public int  count ()
        {
            return this.m_count;
        }//end

        public void  changeChrome ()
        {
            DisplayObject _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            if (!this.m_bHasActiveAnimals)
            {
                this.m_bHasActiveAnimals = true;
                _loc_1 =(DisplayObject) new ZooDialog.assetDict.get("card_available_selected");
                _loc_2 =(DisplayObject) new ZooDialog.assetDict.get("counter_active");
                m_itemIcon.filters = new Array();
                ASwingHelper.setSizedBackground(this.m_counterPane, _loc_2);
                ASwingHelper.setSizedBackground(this.m_cardPane, _loc_1);
                this.m_placeBtn.setEnabled(this.m_bHasActiveAnimals);
            }
            return;
        }//end

        public void  changeCounter (int param1 )
        {
            ZooEnclosure _loc_2 =null ;
            this.m_count = param1;
            this.m_counter.setText(this.m_count + "/" + m_item.inventoryLimit);
            if (ZooDialogView.currentInstance.currZooEnclosure)
            {
                _loc_2 = ZooDialogView.currentInstance.currZooEnclosure;
                this.m_buyBtn.setEnabled(_loc_2.getNumSpecificAnimal(m_item.name) < m_item.inventoryLimit);
            }
            this.m_placeBtn.setEnabled(this.m_count > 0);
            return;
        }//end

         protected void  initializeCell ()
        {
            this.m_itemIconPane.removeAll();
            double _loc_1 =0.212671;
            double _loc_2 =0.71516;
            double _loc_3 =0.072169;
            Array _loc_4 =.get(_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,0,0,0,1,0) ;
            if (this.m_bHasActiveAnimals)
            {
                m_itemIcon.filters = new Array();
            }
            else
            {
                m_itemIcon.filters = .get(new ColorMatrixFilter(_loc_4));
            }
            this.m_iconPane = new AssetPane(m_itemIcon);
            this.m_itemIconPane.addEventListener(MouseEvent.CLICK, this.placeItem, false, 0, true);
            ASwingHelper.setSizedBackground(this.m_itemIconPane, m_itemIcon);
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(this.m_itemIconPane);
            _loc_5 = this(.m_cardPane.getWidth ()-m_itemIcon.width )/2;
            _loc_6 = this(.m_cardPane.getHeight ()-m_itemIcon.height )/2;
            ASwingHelper.setEasyBorder(this.m_itemIconPane, _loc_6, _loc_5);
            return;
        }//end

        protected void  buildCell ()
        {
            DisplayObject _loc_6 =null ;
            String _loc_9 =null ;
            this.m_count = 0;
            if (ZooDialogView.currentInstance.currStorageMechanic)
            {
                this.m_count = ZooDialogView.currentInstance.currStorageMechanic.getCount(m_item.name);
            }
            _loc_1 = ZooDialogView.currentInstance.currSlotMechanic;
            int _loc_2 =0;
            if (_loc_1)
            {
                _loc_2 = _loc_1.numSlots;
            }
            int _loc_3 =0;
            int _loc_4 =0;
            while (_loc_4 < _loc_2)
            {

                if (!_loc_1.isSlotFilled(_loc_4))
                {
                }
                else
                {
                    _loc_9 = _loc_1.getSlot(_loc_4);
                    if (_loc_9 == this.animalName)
                    {
                        _loc_3++;
                    }
                }
                _loc_4++;
            }
            if (this.m_count == 0 && _loc_3 == 0)
            {
                this.m_bHasActiveAnimals = false;
            }
            else
            {
                this.m_bHasActiveAnimals = true;
            }
            DisplayObject _loc_5 =(DisplayObject)new ZooDialog.assetDict.get( "card_available_selected");
            if (this.m_bHasActiveAnimals)
            {
                _loc_5 =(DisplayObject) new ZooDialog.assetDict.get("card_available_selected");
            }
            else
            {
                _loc_5 =(DisplayObject) new ZooDialog.assetDict.get("card_available_unselected");
            }
            this.m_cardPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_counter = ASwingHelper.makeTextField(this.m_count + "/" + m_item.inventoryLimit + " ", EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.whiteTextColor);
            this.m_counter.getTextField().gridFitType = GridFitType.SUBPIXEL;
            this.m_counter.setPreferredWidth(38);
            this.m_counter.setPreferredHeight(15);
            this.m_counterPane = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            if (this.m_bHasActiveAnimals)
            {
                _loc_6 =(DisplayObject) new ZooDialog.assetDict.get("counter_active");
            }
            else
            {
                _loc_6 =(DisplayObject) new ZooDialog.assetDict.get("counter_inactive");
            }
            ASwingHelper.setSizedBackground(this.m_counterPane, _loc_6);
            this.m_counterPane.append(this.m_counter);
            ASwingHelper.prepare(this.m_counterPane);
            ASwingHelper.setSizedBackground(this.m_cardPane, _loc_5);
            AssetIcon _loc_7 =new AssetIcon(new ZooDialog.assetDict.get( "icon_cash") );
            this.m_buyBtn = new CustomButton(String(m_item.cash), _loc_7, "CashButtonUI");
            this.m_buyBtn.addActionListener(this.buyItem, 0, true);
            this.m_placeBtn = new CustomButton(ZLoc.t("Dialogs", "Place"), null, "GreenButtonUI");
            this.m_placeBtn.setEnabled(this.m_bHasActiveAnimals);
            _loc_8 =Global.localizer.langCode =="de"|| Global.localizer.langCode == "id" ? (10) : (12);
            this.m_placeBtn.setFont(new ASFont(EmbeddedArt.titleFont, _loc_8, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_placeBtn.setMargin(new Insets(1, 0, 1, 0));
            this.m_placeBtn.setPreferredWidth(_loc_5.width);
            this.m_placeBtn.addActionListener(this.placeItem, 0, true);
            if (!_loc_1)
            {
                this.m_buyBtn.setEnabled(false);
                this.m_placeBtn.setEnabled(false);
            }
            this.m_cardPane.append(this.m_itemIconPane);
            this.m_cardPane.addChild(this.m_counterPane);
            this.m_counterPane.x = _loc_5.width - this.m_counterPane.getWidth();
            this.m_counterPane.y = 74;
            this.appendAll(ASwingHelper.verticalStrut(5), this.m_cardPane, this.m_placeBtn);
            if (!m_item.requestOnly)
            {
                this.append(this.m_buyBtn);
            }
            this.changeCounter(this.m_count);
            return;
        }//end

        protected void  buyItem (AWEvent event )
        {
            if (Global.player.cash >= m_item.cash)
            {
                dispatchEvent(new ZooDialogEvent(ZooDialogEvent.BUY_NEW_ANIMAL, m_item.name, true));
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return;
        }//end

        protected void  placeItem (Event event )
        {
            if (this.m_count > 0)
            {
                m_itemIcon = null;
                m_itemIcon =(DisplayObject) new EmbeddedArt.hud_act_construction();
                ASwingHelper.prepare(this.parent.parent.parent);
                dispatchEvent(new ZooDialogEvent(ZooDialogEvent.ADD_ANIMAL_TO_DISPLAY, m_item.name, true));
            }
            return;
        }//end

    }



