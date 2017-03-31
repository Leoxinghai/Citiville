package Display.UpgradesUI;

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
import Classes.gates.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;

    public class MunInventoryUpgradesDialogView extends GenericDialogView
    {
        protected String m_buildingName ;
        protected Array m_inventoryData ;
        protected JPanel m_buttonsPanel ;
        protected JLabel m_numbersLabel ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyAllHandler ;
        protected UpgradesInventoryScrollingList m_scrollingInventory ;
        protected double m_targetObjectId ;
        protected AbstractGate m_gate ;
        protected CustomButton m_buyAllButton ;
        public static  int BIG_BUTTON_HEIGHT =40;

        public  MunInventoryUpgradesDialogView (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 ,AbstractGate param7 )
        {
            this.m_gate = param7;
            this.m_buildingName = param1;
            this.m_inventoryData = param2;
            this.m_finishCheckCB = param3;
            this.m_finishCB = param4;
            this.m_buyAllHandler = param5;
            this.m_targetObjectId = param6;
            super(null);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            if (this.m_gate.showBuyAllCost)
            {
                addEventListener("upgradeInventoryCellUpdateEvent", this.updateForBuyAll, false, 0, true);
            }
            return;
        }//end

         protected void  closeMe ()
        {
            if (this.m_gate.showBuyAllCost)
            {
                removeEventListener("upgradeInventoryCellUpdateEvent", this.updateForBuyAll);
            }
            super.closeMe();
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = UpgradesCommonAssets.assetDict.get("dialogBG");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset);
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredWidth(m_bgAsset.width);
                this.setMinimumWidth(m_bgAsset.width);
                this.setMaximumWidth(m_bgAsset.width);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.append(createHeaderPanel());
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.createUpgradeInfoPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.createInventoryPanel());
            this.append(ASwingHelper.verticalStrut(1));
            this.append(this.createButtonsPanel());
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(27, 20, null);
            _loc_2 = ZLoc.t("Dialogs","UpgradeX",{itemName ZLoc.t("Items",this.m_buildingName +"_friendlyName")});
            _loc_2 = _loc_2 + "  ";
            _loc_2 = TextFieldUtil.formatSmallCapsString(_loc_2);
            title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
            title.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = this.getTitleTextSizeHeader(_loc_2.length());
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        public int  getTitleTextSizeHeader (int param1 )
        {
            if (param1 > 30)
            {
                return m_titleFontSize * 0.89;
            }
            return m_titleFontSize;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 3, 0, 0, 5);
            return _loc_1;
        }//end

         protected void  onCancelX (Object param1)
        {
            this.m_cancelTextName = "";
            _loc_2 =Global.world.getObjectById(this.m_targetObjectId )as MapResource ;
            if (_loc_2)
            {
                _loc_2.setShowUpgradeArrow(false);
                _loc_2.setState(_loc_2.getState());
            }
            _loc_3 =Global.gameSettings().getItemByName(this.m_buildingName );
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.X, _loc_3.upgrade.newItemName);
            onCancel(param1);
            return;
        }//end

        protected JPanel  createUpgradeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 =Global.gameSettings().getItemByName(this.m_buildingName );
            UpgradesInfoBox _loc_3 =new UpgradesInfoBox(UpgradesCommonAssets.assetDict.get( "manilaCard") ,this.m_buildingName );
            UpgradesInfoBox _loc_4 =new UpgradesInfoBox(UpgradesCommonAssets.assetDict.get( "manilaCard") ,_loc_2.upgrade.newItemName );
            DisplayObject _loc_5 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "upgradeArrow");
            AssetPane _loc_6 =new AssetPane(_loc_5 );
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_7.append(_loc_6);
            _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(8), _loc_7, ASwingHelper.horizontalStrut(8), _loc_4);
            return _loc_1;
        }//end

        protected JPanel  createInventoryPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_scrollingInventory = new UpgradesInventoryScrollingList(UpgradesCommonAssets.assetDict, this.m_inventoryData);
            _loc_1.append(this.m_scrollingInventory);
            return _loc_1;
        }//end

        protected void  updateForBuyAll (Event event )
        {
            String _loc_2 =null ;
            if (!this.m_gate.showBuyAllCost)
            {
                _loc_2 = ZLoc.t("Dialogs", "BuyAllButtonLabel");
            }
            else if (this.m_gate.showBuyAllCost && this.m_gate.progress < this.m_gate.threshold)
            {
                _loc_2 = ZLoc.t("Dialogs", "BuyAllForPercentButtonLabel", {percentAmount:20});
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "BuyAllForButtonLabel", {cashAmount:this.m_gate.totalCost});
            }
            _loc_3 = TextFieldUtil.formatSmallCapsString(_loc_2);
            this.m_buyAllButton.setText(_loc_3);
            ASwingHelper.prepare(this.m_buyAllButton);
            return;
        }//end

        protected JPanel  createButtonsPanel ()
        {
            AssetIcon cashIcon ;
            String zlocBuy ;
            String buyBtnText ;
            if (this.m_buttonsPanel)
            {
                this.m_buttonsPanel.removeAll();
            }
            this.m_buttonsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (!this.m_finishCheckCB())
            {
                cashIcon = new AssetIcon(new EmbeddedArt.icon_cash());
                if (!this.m_gate.showBuyAllCost)
                {
                    zlocBuy = ZLoc.t("Dialogs", "BuyAllButtonLabel");
                }
                else if (this.m_gate.showBuyAllCost && this.m_gate.progress < this.m_gate.threshold)
                {
                    zlocBuy = ZLoc.t("Dialogs", "BuyAllForPercentButtonLabel", {percentAmount:20});
                }
                else
                {
                    zlocBuy = ZLoc.t("Dialogs", "BuyAllForButtonLabel", {cashAmount:this.m_gate.totalCost});
                }
                buyBtnText = TextFieldUtil.formatSmallCapsString(zlocBuy);
                this.m_buyAllButton = new CustomButton(buyBtnText, cashIcon, "BigCashButtonUI");
                this.m_buyAllButton.addEventListener(MouseEvent.CLICK, this.onbuyAll, false, 0, true);
                this.m_buyAllButton.setPreferredHeight(BIG_BUTTON_HEIGHT);
                this.m_buyAllButton.setMinimumHeight(BIG_BUTTON_HEIGHT);
                this.m_buyAllButton.setMaximumHeight(BIG_BUTTON_HEIGHT);
                this.m_buttonsPanel.append(this.m_buyAllButton);
                this.m_buttonsPanel.append(ASwingHelper.horizontalStrut(20));
            }
            CustomButton finishButton =new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","FinishBuilding")),null ,"GreenButtonUI");
            finishButton .addEventListener (MouseEvent .CLICK ,void  (Event event )
            {
                _loc_2 =Global.world.getObjectById(m_targetObjectId )as MapResource ;
                if (!_loc_2)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "CannotUpgradeMunicipalInMunicipalCenter", {buildingName:ZLoc.t("Items", m_buildingName + "_friendlyName")}));
                    onCancel(event);
                }
                else
                {
                    m_finishCB(event);
                }
                return;
            }//end
            , false, 0, true);
            finishButton.setPreferredHeight(BIG_BUTTON_HEIGHT);
            finishButton.setMinimumHeight(BIG_BUTTON_HEIGHT);
            finishButton.setMaximumHeight(BIG_BUTTON_HEIGHT);
            this.m_buttonsPanel.append(finishButton);
            finishButton.setEnabled(this.m_finishCheckCB());
            ASwingHelper.prepare(this.m_buttonsPanel);
            return this.m_buttonsPanel;
        }//end

        public void  rebuildButtons ()
        {
            this.remove(this.m_buttonsPanel);
            this.append(this.createButtonsPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  onbuyAll (Event event )
        {
            Object data ;
            Item item ;
            double numNeeded ;
            e = event;
            muni = (MapResource)Global.world.getObjectById(this.m_targetObjectId)
            if (!muni)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "CannotUpgradeMunicipalInMunicipalCenter", {buildingName:ZLoc.t("Items", this.m_buildingName + "_friendlyName")}));
                onCancel(e);
                return;
            }
            buyAllVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUYALL_THRESHOLD);
            double totalCost ;
            double buyCount ;
            if (buyAllVariant > 2 && this.m_gate.progress < this.m_gate.threshold)
            {
                totalCost = this.m_gate.totalCost;
                buyCount = this.m_gate.amountNeeded;
                UI .displayMessage (ZLoc .t ("Dialogs","InventoryCashConfirm",{buyCount inventoryAmount ,totalCost cashAmount }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (!m_buyAllHandler(m_inventoryData))
                    {
                    }
                    else
                    {
                        m_scrollingInventory.rebuildCellsAfterBuyAll();
                        rebuildButtons();
                    }
                }
                return;
            }//end
            , "", true);
            }
            else
            {
                int _loc_3 =0;
                _loc_4 = this.m_inventoryData ;
                for(int i0 = 0; i0 < this.m_inventoryData.size(); i0++)
                {
                	data = this.m_inventoryData.get(i0);


                    item = data.item;
                    numNeeded = data.amountNeeded - Global.player.inventory.getItemCountByName(item.name);
                    if (numNeeded < 0)
                    {
                        numNeeded;
                    }
                    buyCount = buyCount + numNeeded;
                    totalCost = totalCost + numNeeded * item.cash;
                }
                UI .displayMessage (ZLoc .t ("Dialogs","InventoryCashConfirm",{buyCount inventoryAmount ,totalCost cashAmount }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (!m_buyAllHandler(m_inventoryData))
                    {
                    }
                    else
                    {
                        m_scrollingInventory.rebuildCellsAfterBuyAll();
                        rebuildButtons();
                    }
                }
                return;
            }//end
            , "", true);
            }
            return;
        }//end

    }



