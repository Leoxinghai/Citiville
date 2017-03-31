package Modules.downtown;

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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.quest.Managers.*;
import Transactions.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;
import org.aswing.zynga.*;

    public class DownTownCenterDialog extends FlyFishDialog
    {
        protected Array m_listData ;
        protected Object m_params ;
        private GridListCellFactory m_cellFactory ;
        protected AbstractGate m_gate ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyAllCB ;
        protected boolean m_canBeFinished ;
        protected String m_stringKeyPrefix ="Default";
        protected Item m_item ;
        protected Item m_upgradeItem ;
        protected String m_timedQuestSource =null ;
        protected Timer m_timer ;
        protected GameQuest m_quest ;
        protected DownTownCenterTooltip m_tooltip ;
        protected Dictionary m_levelMap ;
        public static  String STRING_KEY_BASE ="_wonderUpgrade_";

        public  DownTownCenterDialog (Array param1 ,GridListCellFactory param2 ,String param3 ,String param4 ,Function param5 ,Function param6 ,Function param7 ,boolean param8 =true ,AbstractGate param9 =null ,Object param10 =null )
        {
            UpgradeDefinition _loc_11 =null ;
            this.m_levelMap = new Dictionary();
            this.m_listData = param1;
            this.m_cellFactory = param2;
            this.m_gate = param9;
            this.m_finishCheckCB = param5;
            this.m_finishCB = param6;
            this.m_buyAllCB = param7;
            m_title = param3;
            this.m_params = param10;
            if (param9)
            {
                this.m_item = Global.gameSettings().getItemByName(param9.unlockItemName);
            }
            if (this.m_params)
            {
                this.m_stringKeyPrefix = this.m_params.get("dialogClassStringKey") ? (this.m_params.get("dialogClassStringKey")) : (this.m_stringKeyPrefix);
                this.m_item = !this.m_item && this.m_params.get("dialogClassItemName") ? (this.m_params.get("dialogClassItemName")) : (this.m_item);
                this.m_timedQuestSource = this.m_params.get("dialogClassQuestTimerSource") ? (this.m_params.get("dialogClassQuestTimerSource")) : (null);
                this.m_quest = this.m_timedQuestSource ? (Global.questManager.getQuestByName(this.m_timedQuestSource)) : (null);
                if (this.m_item)
                {
                    _loc_11 = this.m_item ? (this.m_item.upgrade) : (null);
                    this.m_upgradeItem = Global.gameSettings().getItemByName(_loc_11.newItemName);
                }
            }
            this.m_canBeFinished = this.m_finishCheckCB();
            super("assets/flyfish/DownTownCenter.xml");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.DOWN_TOWN_CENTER_ASSETS, DelayedAssetLoader.ATTRACTIONS_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected void  performDialogActions ()
        {
            GameQuest _loc_16 =null ;
            _loc_1 = pane.getComponent("close_btn");
            _loc_1.addActionListener(this.closeDialog, 0, true);
            _loc_2 = (JLabel)pane.getComponent("Title_Text")
            _loc_3 = this.m_stringKeyPrefix +STRING_KEY_BASE +"title";
            _loc_2.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", _loc_3)));
            ASwingHelper.setProperFont(_loc_2, EmbeddedArt.titleFont);
            _loc_4 = pane.getComponent("samTextFieldHolder");
            _loc_5 = (MultilineLabel)pane.getComponent("sam_text")
            _loc_4.remove(_loc_5);
            _loc_6 = this.m_stringKeyPrefix +STRING_KEY_BASE +"speachText";
            _loc_7 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",_loc_6 ),450,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,7553852);
            _loc_4.setLayout(new SoftBoxLayout(SoftBoxLayout.LEFT));
            _loc_4.append(_loc_7);
            _loc_8 = (JLabel)pane.getComponent("downtownUpgrade_titleText")
            _loc_9 = this.m_stringKeyPrefix +STRING_KEY_BASE +"ugpradeHeader";
            Object _loc_10 ={levelNum this.m_upgradeItem ? (this.m_upgradeItem.level) : (0)};
            _loc_8.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", _loc_9, _loc_10)));
            ASwingHelper.setProperFont(_loc_8, EmbeddedArt.titleFont);
            _loc_11 = (JPanel)pane.getComponent("downTownClockHolder")
            _loc_12 = (JPanel)pane.getComponent("DownTownCenterAssets_clock")
            _loc_11.remove(_loc_12);
            _loc_13 = (JPanel)pane.getComponent("downTownClockTimerText")
            (pane.getComponent("downTownClockTimerText") as JPanel).setLayout(new SoftBoxLayout(SoftBoxLayout.LEFT, 0, SoftBoxLayout.CENTER));
            _loc_14 =Global.runspace.getAsset("DownTownCenterAssets_ClockBar");
            _loc_15 =Global.runspace.getAsset("DownTownCenterAssets_ClockIcon");
            ASwingHelper.setBackground(_loc_13, _loc_14, new Insets(4, 5, 4, 0));
            _loc_13.insert(0, new AssetPane(_loc_15));
            _loc_13.insert(1, ASwingHelper.horizontalStrut(-10));
            if (this.m_quest)
            {
                this.updateDuration();
                this.m_timer = new Timer(1000);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.updateDuration, false, 0, true);
                this.m_timer.start();
            }
            else
            {
                if (!this.m_quest && this.m_timedQuestSource)
                {
                    this.showExpired();
                    this.close();
                    return;
                }
                (_loc_11.parent as Container).remove(_loc_11);
            }
            this.refreshProgressBar();
            this.refreshAllCells();
            _loc_17 = pane.getComponent("bonus_TextArea");
            pane.getComponent("bonus_TextArea").setText(ZLoc.t("Dialogs", "TT_Payout_Bonus"));
            ASwingHelper.setProperFont(_loc_17, EmbeddedArt.defaultFontNameBold);
            _loc_18 = (AssetPane)pane.getComponent("buidling_deco")
            DataItemImageCell _loc_19 =new DataItemImageCell(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.CENTER ));
            _loc_19.targetAssetPane = _loc_18;
            _loc_19.setCellValue(this.m_upgradeItem);
            _loc_20 = (JLabel)pane.getComponent("downTownAllowsText")
            (pane.getComponent("downTownAllowsText") as JLabel).setText(ZLoc.t("Dialogs", "TT_Allows"));
            ASwingHelper.setProperFont(_loc_20, EmbeddedArt.defaultFontNameBold);
            _loc_21 = (JLabel)pane.getComponent("downTownEarningsText")
            (pane.getComponent("downTownEarningsText") as JLabel).setText(ZLoc.t("Dialogs", "TT_Earnings"));
            ASwingHelper.setProperFont(_loc_21, EmbeddedArt.defaultFontNameBold);
            _loc_22 = (JLabel)pane.getComponent("downTownHoldsText")
            (pane.getComponent("downTownHoldsText") as JLabel).setText(ZLoc.t("Dialogs", "DownTownCenter_NeighborhoodCapacityTitle"));
            ASwingHelper.setProperFont(_loc_22, EmbeddedArt.defaultFontNameBold);
            _loc_23 = (JLabel)pane.getComponent("downTownBonusText")
            (pane.getComponent("downTownBonusText") as JLabel).setText(ZLoc.t("Dialogs", "TT_Bonus"));
            ASwingHelper.setProperFont(_loc_23, EmbeddedArt.defaultFontNameBold);
            _loc_24 = (JLabel)pane.getComponent("populationText")
            (pane.getComponent("populationText") as JLabel).setText(LocaleFormatter.formatNumber(this.m_upgradeItem.populationCapYield * Global.gameSettings().getNumber("populationScale", 10)));
            ASwingHelper.setProperFont(_loc_24, EmbeddedArt.defaultFontNameBold);
            _loc_25 = (JLabel)pane.getComponent("coinPayoutText")
            (pane.getComponent("coinPayoutText") as JLabel).setText(LocaleFormatter.formatNumber(this.m_upgradeItem.coinYield));
            ASwingHelper.setProperFont(_loc_25, EmbeddedArt.defaultFontNameBold);
            _loc_26 = (JLabel)pane.getComponent("buildingCountText")
            (pane.getComponent("buildingCountText") as JLabel).setText(ZLoc.t("Dialogs", this.m_stringKeyPrefix + STRING_KEY_BASE + "building_count", {count:LocaleFormatter.formatNumber(this.m_upgradeItem.storageInitCapacity)}));
            ASwingHelper.setProperFont(_loc_26, EmbeddedArt.defaultFontNameBold);
            _loc_27 = (JLabel)pane.getComponent("downtownBonusInfoText")
            (pane.getComponent("downtownBonusInfoText") as JLabel).setText(ZLoc.t("Dialogs", this.m_stringKeyPrefix + STRING_KEY_BASE + "mystery_bonus", {multiplier:LocaleFormatter.formatNumber(this.m_upgradeItem.bonusPercent / 100)}));
            ASwingHelper.setProperFont(_loc_27, EmbeddedArt.defaultFontNameBold);
            _loc_28 = (JButton)pane.getComponent("buyAll_btn")
            AssetIcon _loc_29 =new AssetIcon(new EmbeddedArt.icon_cash_big ()as DisplayObject );
            _loc_28.setIcon(_loc_29);
            ASwingHelper.setProperFont(_loc_28, EmbeddedArt.titleFont);
            _loc_28.setFont(_loc_28.getFont().changeSize(18));
            this.refreshBuyAllFinishButton();
            return;
        }//end

        protected void  updateDuration (TimerEvent event =null )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            GameQuestTier _loc_6 =null ;
            JPanel _loc_7 =null ;
            _loc_2 = (JLabel)pane.getComponent("clockTimerText")
            _loc_3 = this.m_quest.getRemainingTieredTime ();
            if (_loc_3 > 0)
            {
                _loc_4 = DateUtil.getFormattedDayCounter(_loc_3);
                _loc_5 = int(_loc_4);
                _loc_6 = this.m_quest.getCurrentTierObject();
                if (_loc_5 > 0)
                {
                    _loc_4 = ZLoc.t("Dialogs", "DownTownCenter_daysLeft", {count:_loc_5});
                }
                else
                {
                    _loc_7 =(JPanel) pane.getComponent("downTownClockTimerText");
                    _loc_7.setPreferredWidth(150);
                }
                _loc_2.setText(_loc_4);
                ASwingHelper.setProperFont(_loc_2, EmbeddedArt.titleFont);
            }
            else
            {
                this.showExpired();
                this.close();
                return;
            }
            return;
        }//end

        public void  refreshAllCells ()
        {
            _loc_1 = this.m_listData.length ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                this.refreshCell(_loc_2);
                _loc_2++;
            }
            return;
        }//end

        public void  refreshCell (int param1 )
        {
            Item requiredItem ;
            int currentAmount ;
            int amountNeeded ;
            boolean complete ;
            JLabel amountText ;
            JLabel itemNameText ;
            JPanel itemIconPanel ;
            DataItemImageCell dataCell ;
            AssetPane wishlistButton ;
            JButton askFriendsBtn ;
            JButton buyBtn ;
            String askFriendsText ;
            String buyText ;
            AssetIcon buyIcon ;
            JPanel btnHolder ;
            DisplayObject checkmark ;
            AssetPane checkPane ;
            cellIndex = param1;
            if (cellIndex < 0 || cellIndex >= this.m_listData.length())
            {
                return;
            }
            cellNum = cellIndex(+1);
            if (this.m_listData.get(cellIndex).amountNeeded > 0)
            {
                requiredItem = this.m_listData.get(cellIndex).item;
                currentAmount = Global.player.inventory.getItemCountByName(requiredItem.name);
                amountNeeded = this.m_listData.get(cellIndex).amountNeeded;
                complete = currentAmount >= amountNeeded;
                amountText =(JLabel) pane.getComponent("Buildable_" + cellNum + "_QuantityText");
                amountText.setText(ZLoc.t("Dialogs", "KeyAmount", {current:currentAmount, required:amountNeeded}));
                ASwingHelper.setProperFont(amountText, EmbeddedArt.defaultFontNameBold);
                amountText.setForeground(new ASColor(7553852));
                itemNameText =(JLabel) pane.getComponent("Buildable_" + cellNum + "_TitleText");
                itemNameText.setText(TextFieldUtil.formatSmallCapsString(requiredItem.localizedName));
                ASwingHelper.setProperFont(itemNameText, EmbeddedArt.titleFont);
                itemNameText.setForeground(new ASColor(7553852));
                itemIconPanel =(JPanel) pane.getComponent("buildable_" + cellNum + "_holder");
                dataCell = new DataItemImageCell(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
                dataCell.append(ASwingHelper.verticalStrut(10));
                dataCell.setCellValue(requiredItem);
                itemIconPanel.removeAll();
                itemIconPanel.append(dataCell);
                this.m_listData.get(cellIndex).cell = dataCell;
                wishlistButton =(AssetPane) pane.getComponent("DownTownCenterAssets_addtoWishList" + cellNum);
                wishlistButton .addEventListener (MouseEvent .CLICK ,void  ()
            {
                if (Global.player.canAddToWishlist(requiredItem.name))
                {
                    GameTransactionManager.addTransaction(new TAddToWishlist(requiredItem.name));
                    dispatchEvent(new DataItemEvent(DataItemEvent.WISHLIST_CHANGED, requiredItem, null, true));
                }
                return;
            }//end
            );
                askFriendsBtn = pane.getComponent("buildable_" + cellNum + "_askfriends");
                buyBtn = pane.getComponent("buildable_" + cellNum + "_buy");
                if (!complete)
                {
                    askFriendsText = ZLoc.t("Dialogs", "AskFriends");
                    askFriendsBtn.setText(TextFieldUtil.formatSmallCapsString(askFriendsText));
                    ASwingHelper.setProperFont(askFriendsBtn, EmbeddedArt.titleFont);
                    askFriendsBtn.removeEventListener(MouseEvent.CLICK, this.askClick);
                    askFriendsBtn.addEventListener(MouseEvent.CLICK, this.askClick);
                    this.m_listData.get(cellIndex).askRef = askFriendsBtn;
                    buyText = ZLoc.t("Dialogs", "BuyButtonLabel", {amount:requiredItem.cash});
                    buyIcon = new AssetIcon(new EmbeddedArt.icon_cash());
                    buyBtn.setText(TextFieldUtil.formatSmallCapsString(buyText));
                    buyBtn.setIcon(buyIcon);
                    ASwingHelper.setProperFont(buyBtn, EmbeddedArt.titleFont);
                    buyBtn.removeEventListener(MouseEvent.CLICK, this.buyClick);
                    buyBtn.addEventListener(MouseEvent.CLICK, this.buyClick);
                    this.m_listData.get(cellIndex).btnRef = buyBtn;
                }
                else
                {
                    btnHolder = pane.getComponent("buildable_" + cellNum);
                    if (btnHolder.containsChild(askFriendsBtn))
                    {
                        btnHolder.remove(askFriendsBtn);
                    }
                    if (btnHolder.containsChild(buyBtn))
                    {
                        btnHolder.remove(buyBtn);
                    }
                    checkmark = Global.runspace.getAsset("NewQuestLayoutAssets_quest_check");
                    checkPane = new AssetPane(checkmark);
                    btnHolder.append(checkPane);
                }
            }
            return;
        }//end

        protected void  refreshProgressBar ()
        {
            JPanel _loc_5 =null ;
            AssetPane _loc_6 =null ;
            _loc_1 = UpgradeDefinition.getFullUpgradeChain(Item.findUpgradeRoot(this.m_item));
            _loc_2 = _loc_1.get((_loc_1.length -1)).level ;
            _loc_3 = pane.getComponent("levels_bigCircles");
            _loc_3.mouseEnabled = false;
            int _loc_4 =2;
            while (_loc_4 <= _loc_2)
            {

                _loc_5 = pane.getComponent("level" + _loc_4);
                _loc_6 = pane.getComponent("bigCircle_" + _loc_4);
                _loc_5.removeEventListener(MouseEvent.MOUSE_OVER, this.showToolTip);
                _loc_5.removeEventListener(MouseEvent.MOUSE_OUT, this.hideToolTip);
                _loc_6.removeEventListener(MouseEvent.MOUSE_OVER, this.showToolTip);
                _loc_6.removeEventListener(MouseEvent.MOUSE_OUT, this.hideToolTip);
                if (this.m_item.level >= _loc_4)
                {
                    _loc_5.alpha = 1;
                }
                else
                {
                    _loc_5.alpha = 0;
                }
                if (_loc_6)
                {
                    if (_loc_4 == this.m_item.level)
                    {
                        _loc_6.alpha = 1;
                    }
                    else
                    {
                        _loc_6.alpha = 0;
                    }
                    _loc_6.addEventListener(MouseEvent.MOUSE_OVER, this.showToolTip);
                    _loc_6.addEventListener(MouseEvent.MOUSE_OUT, this.hideToolTip);
                    _loc_6.name = "bigCircle_" + _loc_4;
                    this.m_levelMap.put(_loc_6.name,  _loc_1.get((_loc_4 - 1)));
                }
                else
                {
                    _loc_5.addEventListener(MouseEvent.MOUSE_OVER, this.showToolTip);
                    _loc_5.addEventListener(MouseEvent.MOUSE_OUT, this.hideToolTip);
                    _loc_5.name = "bigCircle_" + _loc_4;
                    this.m_levelMap.put(_loc_5.name,  _loc_1.get((_loc_4 - 1)));
                }
                _loc_4++;
            }
            return;
        }//end

        protected void  showToolTip (MouseEvent event )
        {
            String _loc_2 =null ;
            AssetPane _loc_3 =null ;
            Point _loc_4 =null ;
            double _loc_5 =0;
            if (this.m_levelMap.get(event.currentTarget.name))
            {
                _loc_2 = (this.m_levelMap.get(event.currentTarget.name) as Item).name;
                if (!this.m_tooltip)
                {
                    this.m_tooltip = new DownTownCenterTooltip(_loc_2, this.m_stringKeyPrefix);
                    this.m_tooltip.mouseEnabled = false;
                    this.m_tooltip.mouseChildren = false;
                    addChild(this.m_tooltip);
                }
                _loc_3 =(AssetPane) event.currentTarget;
                _loc_4 = _loc_3.localToGlobal(new Point(_loc_3.getAsset().x, _loc_3.getAsset().y));
                _loc_5 = 56;
                this.m_tooltip.x = _loc_4.x - this.x - _loc_5;
                this.m_tooltip.y = _loc_4.y - this.y + 30;
                this.m_tooltip.setItem(_loc_2);
                ASwingHelper.prepare(this.m_tooltip);
                this.m_tooltip.enabled = true;
                this.m_tooltip.visible = true;
            }
            return;
        }//end

        protected void  hideToolTip (MouseEvent event )
        {
            if (this.m_tooltip)
            {
                this.m_tooltip.enabled = false;
                this.m_tooltip.visible = false;
            }
            return;
        }//end

        protected void  buyClick (MouseEvent event )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_listData.length())
            {

                if (this.m_listData.get(_loc_2).btnRef == event.target)
                {
                    this.m_listData.get(_loc_2).buyCallback(this.m_listData.get(_loc_2).item);
                    this.refreshCell(_loc_2);
                    this.refreshBuyAllFinishButton();
                    return;
                }
                _loc_2++;
            }
            return;
        }//end

        protected void  askClick (MouseEvent event )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_listData.length())
            {

                if (this.m_listData.get(_loc_2).askRef == event.target)
                {
                    this.m_listData.get(_loc_2).askCallback(this.m_listData.get(_loc_2).item, this.m_listData.get(_loc_2).cell.itemIcon);
                    return;
                }
                _loc_2++;
            }
            return;
        }//end

        protected void  refreshBuyAllFinishButton ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            _loc_1 = (JButton)pane.getComponent("buyAll_btn")
            _loc_1.removeEventListener(MouseEvent.CLICK, this.buyAllClick);
            _loc_1.removeActionListener(this.closeDialog);
            if (this.m_finishCheckCB())
            {
                _loc_2 = this.m_upgradeItem.level == 7 ? ("FinishBuilding") : ("Upgrade");
                _loc_1.addActionListener(this.finishClick, 0, true);
                _loc_1.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", _loc_2)));
                _loc_1.setIcon(null);
                _loc_1.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_green_", Global.runspace.getApplicationDomain()));
            }
            else
            {
                _loc_3 = this.m_gate.totalCost;
                _loc_1.setText(ZLoc.t("Dialogs", "BuyAllForButtonLabel", {cashAmount:_loc_3}));
                _loc_1.setText(TextFieldUtil.formatSmallCapsString(_loc_1.getText()));
                _loc_1.addEventListener(MouseEvent.CLICK, this.buyAllClick);
            }
            _loc_1.setPreferredWidth(-1);
            ASwingHelper.prepare(_loc_1);
            ASwingHelper.prepare(m_jpanel);
            return;
        }//end

        protected void  buyAllClick (MouseEvent event )
        {
            this.m_buyAllCB();
            if (this.m_finishCheckCB())
            {
                this.m_finishCB();
            }
            this.closeDialog(null);
            return;
        }//end

        protected void  finishClick (Event event )
        {
            if (this.m_finishCheckCB())
            {
                this.m_finishCB();
            }
            this.closeDialog(null);
            return;
        }//end

        protected void  showExpired ()
        {
            Global.questManager.showTimedQuestExpired(this.m_quest);
            return;
        }//end

         public void  close ()
        {
            this.cleanUp();
            super.close();
            return;
        }//end

        private void  cleanUp ()
        {
            if (this.m_timer)
            {
                this.m_timer.stop();
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.updateDuration);
            }
            int _loc_1 =0;
            while (_loc_1 < this.m_listData.length())
            {

                if (this.m_listData.get(_loc_1).btnRef)
                {
                    this.m_listData.get(_loc_1).btnRef.removeEventListener(MouseEvent.CLICK, this.buyClick);
                }
                _loc_1++;
            }
            return;
        }//end

        private void  closeDialog (AWEvent event )
        {
            this.close();
            return;
        }//end

         public boolean  isLockable ()
        {
            return true;
        }//end

    }



