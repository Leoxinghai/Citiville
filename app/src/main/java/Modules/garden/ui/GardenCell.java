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
import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.garden.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.event.*;

    public class GardenCell extends DataItemCell
    {
        private JTextField m_counter ;
        private int m_count ;
        private int m_capacity ;
        private boolean m_bHasActiveFlowers =true ;
        protected JPanel m_counterPane ;
        private CustomButton m_placeBtn ;
        private CustomButton m_buyBtn ;
        private ISlotMechanic m_slotMechanic ;
        protected JPanel m_itemIconPane ;
        protected JPanel m_cardPane ;
        private AssetPane m_iconPane ;
        private Garden m_garden ;
        private GardenDialogView m_gardenView ;

        public  GardenCell (GardenDialogView param1 ,ISlotMechanic param2 ,Garden param3 )
        {
            SoftBoxLayout _loc_4 =new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,2,SoftBoxLayout.CENTER );
            super(_loc_4);
            this.m_slotMechanic = param2;
            this.m_garden = param3;
            this.m_gardenView = param1;
            this.m_garden.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.refreshCount, false, 0, true);
            addEventListener(Event.REMOVED_FROM_STAGE, this.onRemoveFromStage, false, 0, true);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            param1 =(Object) param1;
            m_item = param1;
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            this.buildCell();
            if (m_item.rarity == Garden.RARITY_UNCOMMON)
            {
                this.m_gardenView.addTooltipTarget(this, ZLoc.t("Dialogs", "GardenDialog_flowerTooltip_" + m_item.name, {amount:this.m_garden.getItem().bonuses.get(0).dynamicModifierArray.get("uncommon")}));
            }
            else if (m_item.rarity == Garden.RARITY_RARE)
            {
                this.m_gardenView.addTooltipTarget(this, ZLoc.t("Dialogs", "GardenDialog_flowerTooltip_" + m_item.name, {amount:this.m_garden.getItem().bonuses.get(0).dynamicModifierArray.get("rare")}));
            }
            else
            {
                this.m_gardenView.addTooltipTarget(this, ZLoc.t("Dialogs", "GardenDialog_flowerTooltip_" + m_item.name));
            }
            return;
        }//end

        public Item  flower ()
        {
            return this.m_item;
        }//end

        public String  flowerName ()
        {
            return this.m_item.name;
        }//end

        public int  count ()
        {
            return this.m_count;
        }//end

        public void  changeCounter (int param1 )
        {
            this.m_count = param1;
            this.m_counter.setText(this.m_count + "/" + this.m_capacity);
            this.m_buyBtn.setEnabled(this.m_count < this.m_capacity);
            this.m_placeBtn.setEnabled(this.m_count > 0 && this.m_slotMechanic.numEmptySlots > 0);
            return;
        }//end

         protected void  initializeCell ()
        {
            if (this.m_itemIconPane)
            {
                this.m_itemIconPane.removeAll();
            }
            double _loc_1 =0.212671;
            double _loc_2 =0.71516;
            double _loc_3 =0.072169;
            Array _loc_4 =.get(_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,0,0,0,1,0) ;
            if (this.m_bHasActiveFlowers)
            {
                m_itemIcon.filters = new Array();
            }
            else
            {
                m_itemIcon.filters = .get(new ColorMatrixFilter(_loc_4));
            }
            this.m_iconPane = new AssetPane(m_itemIcon);
            ASwingHelper.setSizedBackground(this.m_itemIconPane, m_itemIcon);
            this.m_itemIconPane.append(this.m_iconPane);
            ASwingHelper.prepare(this.m_itemIconPane);
            if (this.m_bHasActiveFlowers)
            {
                this.m_itemIconPane.addEventListener(MouseEvent.CLICK, this.placeItem, false, 0, true);
            }
            return;
        }//end

        protected void  buildCell ()
        {
            String _loc_8 =null ;
            this.m_count = 0;
            this.m_count = GardenManager.instance.getAmountForFlower(m_item.gardenType, m_item.name);
            this.m_capacity = GardenManager.instance.getMaxFlowerCapacity(m_item.gardenType);
            int _loc_1 =0;
            _loc_1 = this.m_slotMechanic.numSlots;
            int _loc_2 =0;
            int _loc_3 =0;
            while (_loc_3 < _loc_1)
            {

                if (this.m_slotMechanic.isSlotFilled(_loc_3))
                {
                    _loc_8 = this.m_slotMechanic.getSlot(_loc_3);
                    if (_loc_8 == this.flowerName)
                    {
                        _loc_2++;
                        break;
                    }
                }
                _loc_3++;
            }
            if (this.m_count == 0 && _loc_2 == 0)
            {
                this.m_bHasActiveFlowers = false;
            }
            else
            {
                this.m_bHasActiveFlowers = true;
            }
            DisplayObject _loc_4 =(DisplayObject)new GardenDialog.assetDict.get( "card_available_selected");
            if (!this.m_bHasActiveFlowers)
            {
                _loc_4 =(DisplayObject) new GardenDialog.assetDict.get("card_available_unselected");
            }
            this.m_cardPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_counter = ASwingHelper.makeTextField(this.m_count + "/" + this.m_capacity + " ", EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.whiteTextColor);
            this.m_counter.getTextField().gridFitType = GridFitType.SUBPIXEL;
            this.m_counter.setPreferredWidth(38);
            this.m_counter.setPreferredHeight(15);
            this.m_counterPane = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            DisplayObject _loc_5 =(DisplayObject)new GardenDialog.assetDict.get( "counter_active");
            if (!this.m_bHasActiveFlowers)
            {
                _loc_5 =(DisplayObject) new GardenDialog.assetDict.get("counter_inactive");
            }
            ASwingHelper.setSizedBackground(this.m_counterPane, _loc_5);
            this.m_counterPane.append(this.m_counter);
            ASwingHelper.prepare(this.m_counterPane);
            ASwingHelper.setSizedBackground(this.m_cardPane, _loc_4);
            AssetIcon _loc_6 =new AssetIcon(new GardenDialog.assetDict.get( "icon_cash") );
            this.m_buyBtn = new CustomButton(String(m_item.cash), _loc_6, "CashButtonUI");
            this.m_buyBtn.addActionListener(this.purchase, 0, true);
            this.m_placeBtn = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Plant")), null, "GreenButtonUI");
            this.m_placeBtn.setEnabled(this.m_bHasActiveFlowers && this.m_slotMechanic.numEmptySlots > 0);
            _loc_7 =Global.localizer.langCode =="de"|| Global.localizer.langCode == "id" ? (10) : (12);
            this.m_placeBtn.setFont(new ASFont(EmbeddedArt.titleFont, _loc_7, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_placeBtn.setMargin(new Insets(1, 0, 1, 0));
            this.m_placeBtn.setPreferredWidth(_loc_4.width);
            this.m_placeBtn.addActionListener(this.placeItem, 0, true);
            this.m_cardPane.append(this.m_itemIconPane);
            this.m_cardPane.addChild(this.m_counterPane);
            this.m_counterPane.x = _loc_4.width - this.m_counterPane.getWidth();
            this.m_counterPane.y = 74;
            this.appendAll(ASwingHelper.verticalStrut(5), this.m_cardPane, this.m_placeBtn);
            if (!m_item.requestOnly)
            {
                this.append(this.m_buyBtn);
            }
            this.changeCounter(this.m_count);
            return;
        }//end

        public void  refreshCount (Event event =null )
        {
            int _loc_2 =0;
            if (this.m_iconPane)
            {
                _loc_2 = GardenManager.instance.getAmountForFlower(m_item.gardenType, m_item.name);
                if (_loc_2 != this.m_count)
                {
                    removeAll();
                    this.buildCell();
                    this.initializeCell();
                }
                this.m_buyBtn.setEnabled(this.m_count < this.m_capacity);
                this.m_placeBtn.setEnabled(this.m_count > 0 && this.m_slotMechanic.numEmptySlots > 0);
            }
            return;
        }//end

        public void  purchase (AWEvent event )
        {
            if (m_item && Global.player.canBuyCash(m_item.cash, true))
            {
                GameTransactionManager.addTransaction(new TBuyItem(m_item.name, 1));
                this.refreshCount();
            }
            return;
        }//end

        protected void  placeItem (Event event )
        {
            if (this.m_count > 0)
            {
                if (this.m_slotMechanic.numEmptySlots > 0)
                {
                    this.m_slotMechanic.fillNextSlot(m_item.name);
                    this.refreshCount();
                }
            }
            return;
        }//end

        private void  onRemoveFromStage (Event event )
        {
            this.m_garden.removeEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.refreshCount, false);
            return;
        }//end

    }



