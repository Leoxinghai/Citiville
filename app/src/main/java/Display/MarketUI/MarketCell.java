package Display.MarketUI;

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
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.landmarks.*;
import Modules.remodel.*;
import Modules.stats.experiments.*;
import Transactions.*;
import ZLocalization.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

import com.xinghai.Debug;

    public class MarketCell extends DataItemCell implements IPlayerStateObserver, IGameWorldStateObserver
    {
        private int m_amountNeeded ;
        protected JLabel m_itemCost ;
        protected MarginBackground m_bgDec ;
        protected JPanel m_currencyIcon ;
        protected JPanel m_setIcon ;
        protected JPanel m_alignmentContainer ;
        protected JPanel m_itemIconPane ;
        protected DisplayObject m_content ;
        protected DisplayObject m_bgOver ;
        protected DisplayObject m_swappedBG ;
        protected boolean m_itemLocked ;
        protected DisplayObject m_curBGAsset ;
        protected Loader m_setIconLoader ;
        protected JButton m_purchaseButton ;
        protected JPanel m_limitedTimePane ;
        protected JTextField m_timeLeft ;
        protected TextField m_reqText ;
        protected double m_secondsLeft ;
        protected boolean m_hideImage ;
        protected boolean m_bDisabled =false ;
        protected boolean m_buyable =true ;
        protected boolean m_isQuest =false ;
        private double m_priceMultiplier =1;
        public boolean m_isFranchise =true ;
        protected JLabel m_timerLabel ;
        protected Dictionary m_assetDict ;
public static  int REQUIRED_WIDTH =78;
public static  int REQUIRED_HEIGHT =30;
public static  int MARKET_CELL_WIDTH =88;
        private static Dictionary m_itemIconCache =new Dictionary ();
        private static Array unlockCellsList =new Array ();
        private static Array lockedCellsList =new Array ();

        public  MarketCell (LayoutManager param1)
        {
            this.m_hideImage = false;
            _loc_2 = param1;
            if (_loc_2 == null)
            {
                _loc_2 = new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -2, SoftBoxLayout.CENTER);
            }
            this.m_assetDict = Catalog.assetDict;
            super(_loc_2);
            Global.player.addObserver(this);
            Global.world.addObserver(this);
            return;
        }//end

        public void  setAssetDict (Dictionary param1 )
        {
            this.m_assetDict = param1;
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            _loc_2 = m_item;
            m_item =(Item) param1;
            if (m_item == null || m_item == _loc_2)
            {
                return;
            }
            m_item.m_isFranchise = this.m_isFranchise;
            this.m_amountNeeded = m_item.cost;
            if (m_item.marketBuyable)
            {
                this.m_buyable = m_item.buyable;
            }
            this.loadCellIcon();
            this.buildCell();
            return;
        }//end

        private void  loadCellIcon ()
        {
            ItemGroup _loc_2 =null ;
            BitmapData _loc_3 =null ;
            _loc_1 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            if (_loc_1 != null)
            {
                _loc_2 = Global.gameSettings().getItemByName(m_item.name).getSet();
                if (_loc_2)
                {
                    this.m_setIconLoader = LoadingManager.load(_loc_2.getIcon(), this.onSetIconLoad, LoadingManager.PRIORITY_HIGH);
                }
                else
                {
                    this.m_setIconLoader = null;
                }
                _loc_3 = m_itemIconCache.get(_loc_1);
                if (_loc_3 == null)
                {
                    m_loader = LoadingManager.load(_loc_1, this.makeOnIconLoaded(_loc_1), LoadingManager.PRIORITY_HIGH);
                }
                else
                {
                    this.delayedSetItemIcon(_loc_3);
                }
            }
            return;
        }//end

        private void  delayedSetItemIcon (BitmapData param1 )
        {
            Sprite dummy ;
            Function delayedCall ;
            iconBitmapData = param1;
            delayedCall =void  (Event event )
            {
                removeChild(dummy);
                dummy.removeEventListener(Event.ENTER_FRAME, delayedCall);
                m_itemIcon = new Bitmap(iconBitmapData, "auto", true);
                onItemIconSet();
                return;
            }//end
            ;
            dummy = new Sprite();
            addChild(dummy);
            dummy.addEventListener(Event.ENTER_FRAME, delayedCall);
            return;
        }//end

        private Function  makeOnIconLoaded (String param1 )
        {
            url = param1;
            return void  (Event event )
            {
                if (m_loader == null)
                {
                    return;
                }
                m_itemIcon = m_loader.content;
                if (m_itemIcon == null)
                {
                    return;
                }
                _loc_2 = (Bitmap)m_itemIcon
                if (_loc_2 != null)
                {
                    _loc_2.smoothing = true;
                    m_itemIconCache.put(url,  _loc_2.bitmapData);
                }
                onItemIconSet();
                return;
            }//end
            ;
        }//end

        protected void  onItemIconSet ()
        {
            _loc_1 = scaleToFit(m_itemIcon);
            m_itemIcon.scaleY = scaleToFit(m_itemIcon);
            m_itemIcon.scaleX = _loc_1;
            this.initializeCell();
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

        public void  setBuyable (boolean param1 )
        {
            if (this.m_buyable != param1)
            {
                this.m_buyable = param1;
                removeAll();
                this.buildCell();
            }
            return;
        }//end

        protected void  setCellBackground ()
        {
            DisplayObject _loc_1 =null ;
            if (m_item.startDate && this.m_assetDict.hasOwnProperty("card_limited_item"))
            {
                _loc_1 =(DisplayObject) new this.m_assetDict.get("card_limited_item");
            }
            else
            {
                _loc_1 =(DisplayObject) new this.m_assetDict.get("card_available_unselected");
            }
            this.m_content = _loc_1;
            if (!BuyLogic.franchiseCheck(m_item) && Global.isVisiting() || !Global.isVisiting() && !BuyLogic.lotSiteCheck(this.m_item) || !Global.isVisiting() && BuyLogic.maxReceivedFranchisesCheck(this.m_item))
            {
                this.m_content.alpha = 0.7;
            }
            else if (Global.player.gold < m_item.cost || this.m_itemLocked)
            {
                this.m_content.alpha = 0.7;
            }
            if (m_item.startDate && this.m_assetDict.hasOwnProperty("card_limited_item"))
            {
                this.m_bgOver =(DisplayObject) new this.m_assetDict.get("card_limited_item");
            }
            else
            {
                this.m_bgOver =(DisplayObject) new this.m_assetDict.get("card_available_selected");
            }
            ASwingHelper.setSizedBackground(this.m_alignmentContainer, this.m_content);
            this.m_alignmentContainer.setPreferredWidth(this.m_content.width);
            return;
        }//end

        protected void  insertCountDownTimer ()
        {
            if (m_item.extraText && this.m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                this.addExtraText(this, MARKET_CELL_WIDTH);
            }
            if (m_item.startDate && this.m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                this.addCountdownTimer(this, MARKET_CELL_WIDTH);
            }
            return;
        }//end

        public void  setImageVisibility (boolean param1 )
        {
            this.m_hideImage = !param1;
            return;
        }//end

        protected void  buildCell ()
        {
            if (m_item.localizedName == ZLoc.t("Items", "supply_all_business_friendlyName") || m_item.localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName") || m_item.localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
            {
                this.buildInstantReadyCell();
                return;
            }
            this.m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_itemLocked = BuyLogic.isLocked(m_item);
            this.setCellBackground();
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Loading"),EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.darkBlueTextColor ,JLabel.CENTER );
            _loc_3 = ASwingHelper.centerElement(_loc_2 );
            _loc_2.setPreferredWidth(this.m_content.width);
            _loc_3.setPreferredWidth(this.m_content.width);
            if (this.m_hideImage)
            {
                _loc_1.visible = false;
                this.m_alignmentContainer.visible = false;
            }
            ASwingHelper.prepare(_loc_3);
            this.m_itemIconPane.append(_loc_3);
            _loc_1.append(this.m_itemIconPane);
            this.m_alignmentContainer.append(_loc_1);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel ();
            this.append(this.m_alignmentContainer);
            this.insertCountDownTimer();
            this.append(ASwingHelper.verticalStrut(6));
            _loc_5 = m_item.isEmptyLot;
            if (this.m_buyable)
            {
                this.setupPurchaseButton();
                if (this.m_purchaseButton != null)
                {
                    this.m_currencyIcon = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    this.m_currencyIcon.append(this.m_purchaseButton);
                    this.append(this.m_currencyIcon);
                }
                this.addEventListener(MouseEvent.ROLL_OVER, this.switchBG, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OUT, this.revertBG, false, 0, true);
                if (_loc_5 && this.m_itemLocked)
                {
                    if (!BuyLogic.isAtMaxFranchiseCount())
                    {
                        this.addEventListener(MouseEvent.CLICK, this.unlockorBuyForEmptyLot, false, 0, true);
                    }
                }
                else if (this.m_itemLocked && m_item.unlock == Item.UNLOCK_NEIGHBOR)
                {
                    this.addEventListener(MouseEvent.CLICK, this.unlockNeighbors, false, 0, true);
                }
                else if (this.m_itemLocked && m_item.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR)
                {
                    if (m_item.requiredQuestFlag.length > 0)
                    {
                        if (Global.questManager.isFlagReached(m_item.requiredQuestFlag))
                        {
                            if (Global.player.checkLicense(this.m_item.name))
                            {
                                this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                            }
                            else
                            {
                                this.addEventListener(MouseEvent.CLICK, this.unlockorBuyNeighbors, false, 0, true);
                            }
                        }
                        else
                        {
                            this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                        }
                    }
                    else
                    {
                        this.addEventListener(MouseEvent.CLICK, this.unlockNeighbors, false, 0, true);
                    }
                }
                else
                {
                    this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                }
            }
            else
            {
                this.append(ASwingHelper.verticalStrut(20));
                this.killListeners();
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildInstantReadyCell ()
        {
            AssetIcon _loc_9 =null ;
            double _loc_10 =0;
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_LINK_TEST );
            int _loc_2 =0;
            _loc_3 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_COST );
            if (_loc_3 == 2)
            {
                _loc_2 = 2;
            }
            else if (_loc_3 == 3)
            {
                _loc_2 = 10;
            }
            else if (_loc_3 == 4)
            {
                _loc_2 = 20;
            }
            else if (_loc_3 == 5)
            {
                _loc_2 = 50;
            }
            else
            {
                _loc_2 = 2;
            }
            if (m_item.cash > 0)
            {
                _loc_2 = _loc_2;
            }
            this.m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_itemLocked = BuyLogic.isLocked(m_item);
            _loc_4 =(DisplayObject) new this.m_assetDict.get( "card_available_unselected");
            this.m_content = _loc_4;
            if (!BuyLogic.franchiseCheck(m_item) && Global.isVisiting() || !Global.isVisiting() && !BuyLogic.lotSiteCheck(this.m_item) || !Global.isVisiting() && BuyLogic.maxReceivedFranchisesCheck(this.m_item))
            {
                this.m_content.alpha = 0.7;
            }
            else if (Global.player.gold < m_item.cost || this.m_itemLocked)
            {
                this.m_content.alpha = 0.7;
            }
            if (m_item.startDate && this.m_assetDict.hasOwnProperty("card_limited_item"))
            {
                this.m_bgOver =(DisplayObject) new this.m_assetDict.get("card_limited_item");
            }
            else
            {
                this.m_bgOver =(DisplayObject) new this.m_assetDict.get("card_available_selected");
            }
            this.setCellBackground();
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Loading"),EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.darkBlueTextColor ,JLabel.CENTER );
            _loc_7 = ASwingHelper.centerElement(_loc_6 );
            _loc_6.setPreferredWidth(this.m_content.width);
            _loc_7.setPreferredWidth(this.m_content.width);
            ASwingHelper.prepare(_loc_7);
            this.m_itemIconPane.append(_loc_7);
            _loc_5.append(this.m_itemIconPane);
            this.m_alignmentContainer.append(_loc_5);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel ();
            this.append(this.m_alignmentContainer);
            this.append(ASwingHelper.verticalStrut(6));
            if (this.m_buyable)
            {
                _loc_10 = _loc_2;
                if (!Global.isVisiting() && this.m_itemLocked)
                {
                    this.m_purchaseButton = this.createPurchaseButton(_loc_10);
                    this.m_bDisabled = true;
                    this.m_purchaseButton.setEnabled(false);
                }
                else if (Global.isVisiting() && !BuyLogic.franchiseCheck(m_item))
                {
                    _loc_9 = new AssetIcon(new this.m_assetDict.get("icon_coins"));
                    this.m_purchaseButton = new CustomButton(".", _loc_9, "CoinsButtonUI");
                    this.m_purchaseButton.alpha = 0;
                }
                else if (_loc_2 > 0)
                {
                    _loc_9 = new AssetIcon(new this.m_assetDict.get("icon_cash"));
                    this.m_purchaseButton = new CustomButton(String(_loc_10), _loc_9, "CashButtonUI");
                }
                else if (Global.player.gold < _loc_10)
                {
                    this.m_purchaseButton = this.createPurchaseButton(_loc_10);
                    this.m_bDisabled = true;
                    this.m_purchaseButton.setEnabled(false);
                }
                else if (!Global.isVisiting() && !BuyLogic.lotSiteCheck(this.m_item) || !Global.isVisiting() && BuyLogic.maxReceivedFranchisesCheck(this.m_item))
                {
                    _loc_9 = new AssetIcon(new this.m_assetDict.get("icon_coins"));
                    this.m_purchaseButton = new CustomButton(".", _loc_9, "CoinsButtonUI");
                    this.m_purchaseButton.alpha = 0;
                }
                else
                {
                    this.m_purchaseButton = this.createPurchaseButton(_loc_10);
                    this.m_purchaseButton.setEnabled(true);
                }
                if (this.m_purchaseButton != null)
                {
                    this.m_currencyIcon = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    this.m_currencyIcon.append(this.m_purchaseButton);
                    this.append(this.m_currencyIcon);
                }
                this.addEventListener(MouseEvent.ROLL_OVER, this.switchBG, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OUT, this.revertBG, false, 0, true);
                if (this.m_itemLocked && m_item.unlock == Item.UNLOCK_NEIGHBOR)
                {
                    this.addEventListener(MouseEvent.CLICK, this.unlockNeighbors, false, 0, true);
                }
                else if (this.m_itemLocked && m_item.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR)
                {
                    if (m_item.requiredQuestFlag.length > 0)
                    {
                        if (Global.questManager.isFlagReached(m_item.requiredQuestFlag))
                        {
                            if (Global.player.checkLicense(this.m_item.name))
                            {
                                this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                            }
                            else
                            {
                                this.addEventListener(MouseEvent.CLICK, this.unlockorBuyNeighbors, false, 0, true);
                            }
                        }
                        else
                        {
                            this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                        }
                    }
                    else
                    {
                        this.addEventListener(MouseEvent.CLICK, this.unlockNeighbors, false, 0, true);
                    }
                }
                else
                {
                    this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                }
            }
            else
            {
                this.append(ASwingHelper.verticalStrut(20));
                this.killListeners();
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JButton  createLockedButton (int param1 )
        {
            JButton _loc_2 =null ;
            if (m_item.unlock == Item.UNLOCK_QUEST_FLAG && m_item.marketQuestName && m_item.derivedItemName != "res_simpsonmegabrick")
            {
                _loc_2 = this.createTextButton(ZLoc.t("Dialogs", "Unlock"));
            }
            else if (m_item.unlock == Item.UNLOCK_OBJECT_COUNT)
            {
                if (m_item.requiredObjectsNeedUpgrade)
                {
                    _loc_2 = this.createTextButton(ZLoc.t("Dialogs", "Upgrade"));
                }
                else
                {
                    _loc_2 = this.createPurchaseButton(param1);
                    _loc_2.setEnabled(m_item.enabledWhenLocked);
                }
            }
            else
            {
                _loc_2 = this.createPurchaseButton(param1);
                _loc_2.setEnabled(m_item.enabledWhenLocked);
            }
            return _loc_2;
        }//end

        protected JButton  createTextButton (String param1 )
        {
            CustomButton _loc_2 =new CustomButton(param1 ,null ,"CashButtonUI");
            _loc_2.setFont(new ASFont(EmbeddedArt.titleFont, 10, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_2.setMargin(new Insets(3, 2, 3, 3));
            return _loc_2;
        }//end

        protected void  setupPurchaseButton ()
        {
            AssetIcon _loc_1 =null ;
            _loc_2 = Math.round(m_item.GetItemSalePrice ()*this.m_priceMultiplier );
            if (!Global.isVisiting() && this.m_itemLocked)
            {
                this.m_purchaseButton = this.createLockedButton(_loc_2);
                this.m_bDisabled = !this.m_purchaseButton.isEnabled();
            }
            else if (Global.isVisiting() && !BuyLogic.franchiseCheck(m_item))
            {
                _loc_1 = new AssetIcon(new this.m_assetDict.get("icon_coins"));
                this.m_purchaseButton = new CustomButton(".", _loc_1, "CoinsButtonUI");
                this.m_purchaseButton.alpha = 0;
            }
            else if (m_item.type.toString() == "play")
            {
                this.m_purchaseButton = new CustomButton(ZLoc.t("Dialogs", "Play"), _loc_1, "CashButtonUI");
                this.m_purchaseButton.setMargin(new Insets(1, 5, 1, 5));
            }
            else if (m_item.goods > 0)
            {
                _loc_1 = new AssetIcon(new this.m_assetDict.get("icon_goods"));
                this.m_purchaseButton = new CustomButton(String(_loc_2), _loc_1, "CoinsButtonUI");
                if (_loc_2 > Global.player.commodities.getCount(Commodities.GOODS_COMMODITY))
                {
                    this.m_bDisabled = true;
                    this.m_purchaseButton.setEnabled(false);
                }
            }
            else if (m_item.fbc > 0)
            {
                this.m_purchaseButton = new CustomButton(ZLoc.t("Dialogs", "Buy"), _loc_1, "CashButtonUI");
                this.m_purchaseButton.setMargin(new Insets(1, 5, 1, 5));
            }
            else if (m_item.cash > 0)
            {
                _loc_1 = new AssetIcon(new this.m_assetDict.get("icon_cash"));
                this.m_purchaseButton = new CustomButton(String(_loc_2), _loc_1, "CashButtonUI");
            }
            else if (Global.player.gold < _loc_2)
            {
                this.m_purchaseButton = this.createPurchaseButton(_loc_2);
                this.m_bDisabled = true;
                this.m_purchaseButton.setEnabled(false);
            }
            else if (!Global.isVisiting() && !BuyLogic.lotSiteCheck(this.m_item) || !Global.isVisiting() && BuyLogic.maxReceivedFranchisesCheck(this.m_item))
            {
                _loc_1 = new AssetIcon(new this.m_assetDict.get("icon_coins"));
                this.m_purchaseButton = new CustomButton(".", _loc_1, "CoinsButtonUI");
                this.m_purchaseButton.alpha = 0;
            }
            else
            {
                this.m_purchaseButton = this.createPurchaseButton(_loc_2);
                this.m_purchaseButton.setEnabled(true);
            }
            return;
        }//end

        protected JButton  createPurchaseButton (int param1 )
        {
            JButton _loc_3 =null ;
            _loc_2 = new AssetIcon(new this.m_assetDict.get( "icon_coins") );
            if (m_item.cash > 0)
            {
                _loc_2 = new AssetIcon(new this.m_assetDict.get("icon_cash"));
                _loc_3 = new CustomButton(String(param1), _loc_2, "CashButtonUI");
            }
            else if (m_item.unlock == Item.UNLOCK_LOCKED)
            {
                _loc_2 = new AssetIcon(new this.m_assetDict.get("icon_cash"));
                _loc_3 = new CustomButton(String(m_item.unlockCostCash), _loc_2, "CashButtonUI");
            }
            else
            {
                _loc_3 = new CustomButton(String(param1), _loc_2, "CoinsButtonUI");
                if (String(param1).length > 7)
                {
                    _loc_3.setFont(new ASFont(EmbeddedArt.TITLE_FONT, 10, false, false, false, EmbeddedArt.advancedFontProps));
                    _loc_3.setMargin(new Insets(3, 2, 3, 3));
                }
                else if (String(param1).length > 5)
                {
                    _loc_3.setFont(new ASFont(EmbeddedArt.TITLE_FONT, 11, false, false, false, EmbeddedArt.advancedFontProps));
                    _loc_3.setMargin(new Insets(3, 2, 2, 1));
                }
            }
            return _loc_3;
        }//end

        protected void  killListeners ()
        {
            if (this.hasEventListener(MouseEvent.CLICK))
            {
                this.removeEventListener(MouseEvent.CLICK, this.buyItem);
                this.removeEventListener(MouseEvent.CLICK, this.unlockNeighbors);
                this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyForEmptyLot);
                this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyNeighbors);
            }
            if (this.hasEventListener(MouseEvent.ROLL_OVER))
            {
                this.removeEventListener(MouseEvent.ROLL_OVER, this.switchBG);
            }
            if (this.hasEventListener(MouseEvent.ROLL_OUT))
            {
                this.removeEventListener(MouseEvent.ROLL_OUT, this.revertBG);
            }
            return;
        }//end

        public void  updatePriceMultiplier (double param1 )
        {
            int _loc_2 =0;
            if (param1 != this.m_priceMultiplier && this.m_purchaseButton && m_item)
            {
                this.m_priceMultiplier = param1;
                _loc_2 = m_item.GetItemSalePrice();
                this.m_purchaseButton.setText(String(Math.round(_loc_2 * this.m_priceMultiplier)));
            }
            return;
        }//end

        public void  performUpdate (boolean param1 =false )
        {
            _loc_2 = BuyLogic.isLocked(m_item);
            boolean _loc_3 =false ;
            _loc_4 = m_item.isEmptyLot;
            if (m_item.unlock == Item.UNLOCK_PERMITS || _loc_2 == this.m_itemLocked)
            {
                if (m_item.requiredQuestFlag.length > 0)
                {


                        _loc_3 = true;

                }
            }
            if (m_item.unlock == Item.UNLOCK_QUEST_AND_LEVEL || _loc_2 == this.m_itemLocked)
            {
                if (m_item.requiredQuestFlag.length > 0)
                {


                        _loc_3 = true;

                }
            }
            if (m_item.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR || _loc_2 == this.m_itemLocked)
            {
                if (m_item.requiredQuestFlag.length > 0)
                {


                        _loc_3 = true;

                }
            }
            if (param1 || _loc_2 != this.m_itemLocked || _loc_3 || _loc_4 && _loc_2)
            {
                this.refreshCell();
            }
            if (this.m_purchaseButton != null)
            {
                if (!_loc_2 && this.m_bDisabled == true && Global.player.gold >= m_item.cost && this.m_purchaseButton.getUIClassID() == "CoinsButtonUI")
                {
                    this.m_purchaseButton.setEnabled(true);
                    this.m_bDisabled = false;
                }
                else if (this.m_bDisabled == false && Global.player.gold < m_item.cost && this.m_purchaseButton.getUIClassID() == "CoinsButtonUI")
                {
                    this.m_purchaseButton.setEnabled(false);
                    this.m_bDisabled = true;
                }
            }
            return;
        }//end

        protected void  requestPermits (GenericPopupEvent event )
        {
            if (event.button != GenericDialogView.SKIP)
            {
                return;
            }
            this.permitFeed();
            return;
        }//end

        protected void  permitFeed ()
        {
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            GenericDialog _loc_4 =null ;
            _loc_1 =Global.player.cityName ;
            _loc_2 = Global.world.viralMgr.sendPermitsRequest(Global.player, _loc_1);
            if (!_loc_2)
            {
                _loc_3 = ZLoc.t("Dialogs", "ExpansionThrottlingMessage");
                _loc_4 = new GenericDialog(_loc_3);
                UI.displayPopup(_loc_4);
            }
            return;
        }//end

        protected void  buyPermits (GenericPopupEvent event )
        {
            int _loc_4 =0;
            if (event.button != GenericDialogView.YES)
            {
                return;
            }
            _loc_2 = ExpansionManager.instance.getNextExpansionNumPermitPurchaseToComplete();
            _loc_3 = ExpansionManager.instance.getNextExpansionPermitCostToComplete();
            if (Global.player.cash >= _loc_3)
            {
                if (!Global.player.inventory.isItemCountBelowInventoryLimit("permits", _loc_2))
                {
                    UI.displayInventoryMaxExceededDialog();
                    return;
                }
                _loc_4 = Global.player.cash - _loc_3;
                if (_loc_4 < 0)
                {
                    _loc_4 = 0;
                }
                Global.player.cash = _loc_4;
                Global.player.inventory.addItems("permits", _loc_2);
                unlockCellsList.push(this);
                GameTransactionManager.addTransaction(new TAcquirePermit(m_item.name));
                refreshUnlockedCells(m_item.name);
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return;
        }//end

        protected void  confirmPurchase (GenericPopupEvent event )
        {
            Item _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            MysteryCrateDialog _loc_5 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = Global.gameSettings().getItemByName(m_item.name);
                _loc_3 = ZLoc.t("Items", m_item.name + "_friendlyName");
                _loc_4 = ZLoc.t("Dialogs", "Mystery_Crate_anticipation", {item:_loc_3});
                _loc_5 = new MysteryCrateDialog(_loc_4, "mysteryCrateDialog", GenericDialogView.TYPE_CUSTOM_OK, null, "Mystery_Crate", _loc_2.iconRelative, true, GenericDialogView.ICON_POS_LEFT, "", null, ZLoc.t("Dialogs", "Mystery_Crate_ok"));
                UI.displayPopup(_loc_5, true);
                GameTransactionManager.addTransaction(new TBuyMysteryCrate(m_item, 1));
            }
            return;
        }//end

        protected void  unlockItem (GenericPopupEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            if (event.button == GenericDialogView.YES)
            {
                Global.player.acquireLicense(m_item.name);
                _loc_2 = m_item.unlockCostCash;
                if (m_item.unlock == Item.UNLOCK_PERMITS)
                {
                    _loc_2 = ExpansionManager.instance.getNextExpansionLockOverrideCost(m_item);
                }
                _loc_3 = Global.player.cash - _loc_2;
                if (_loc_3 < 0)
                {
                    _loc_3 = 0;
                }
                Global.player.cash = _loc_3;
                unlockCellsList.push(this);
                if (m_item.type == "expansion" && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
                {
                    _loc_4 = ExpansionManager.instance.getNextExpansionNumPermitPurchaseToComplete();
                    _loc_5 = ExpansionManager.instance.getNextExpansionPermitCostToComplete();
                    if (Global.player.cash - _loc_5 > 0)
                    {
                        Global.player.cash = Global.player.cash - _loc_5;
                    }
                    else
                    {
                        Global.player.cash = 0;
                    }
                    Global.player.inventory.addItems("permits", _loc_4);
                    GameTransactionManager.addTransaction(new TAcquirePermit(m_item.name));
                }
                GameTransactionManager.addTransaction(new TAcquireLicense(m_item.name));
                refreshUnlockedCells(m_item.name);
            }
            else if (event.button == GenericDialogView.SHARE)
            {
                if (m_item.unlockShareFunc != null)
                {
                    //m_item.unlockShareFunc();
                }
            }
            return;
        }//end

        private void  refreshCell ()
        {
            this.killListeners();
            this.removeAll();
            if (this.m_itemIcon)
            {
                this.m_itemIcon.alpha = 1;
            }
            this.loadCellIcon();
            this.buildCell();
            this.initializeCell();
            return;
        }//end

        protected boolean  runFranchiseMarketCellChecks ()
        {
            if (!BuyLogic.franchiseCountLevelCheck(m_item))
            {
                UI.displayMessage(ZLoc.t("Dialogs", "MaxFranchisesCheck", {level:Global.franchiseManager.nextFranchiseUnlock}));
                return false;
            }
            if (!BuyLogic.franchiseCheck(this.m_item))
            {
                UI.displayMessage(ZLoc.t("Dialogs", "FranchiseCheck"));
                return false;
            }
            return true;
        }//end

        protected void  unlockNeighbors (MouseEvent event )
        {
            if (event.target.name != "JButton")
            {
                FrameManager.showTray("invite.php?ref=market_click_neighbor_gated_item&action=unlockItem&name=" + m_item.name + "&requiredNeighbors=" + m_item.requiredNeighbors);
            }
            return;
        }//end

        protected void  showNeighborMFS (GenericPopupEvent event )
        {
            FrameManager.showTray("invite.php?ref=market_click_neighbor_gated_item&action=unlockItem&name=" + m_item.name + "&requiredNeighbors=" + m_item.requiredNeighbors);
            return;
        }//end

        protected void  showEmptyLotNeighborMFS (GenericPopupEvent event )
        {
            FrameManager.showTray("invite.php?ref=market_click_neighbor_gated_item&action=unlockItem&name=" + m_item.name + "&requiredNeighbors=" + BuyLogic.getNeededNeighborCountForNextFranchise);
            return;
        }//end

        protected void  buyNeighbors (GenericPopupEvent event )
        {
            int _loc_3 =0;
            if (event.button != GenericDialogView.YES)
            {
                return;
            }
            _loc_2 = this.m_item.unlockCostCash ;
            if (Global.player.cash >= _loc_2)
            {
                _loc_3 = Global.player.cash - _loc_2;
                if (_loc_3 < 0)
                {
                    _loc_3 = 0;
                }
                Global.player.cash = _loc_3;
                Global.player.acquireLicense(this.m_item.name);
                unlockCellsList.push(this);
                GameTransactionManager.addTransaction(new TAcquireLicense(m_item.name));
                this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyNeighbors);
                refreshUnlockedCells(m_item.name);
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return;
        }//end

        private void  buyFranchiseUnlocks (GenericPopupEvent event )
        {
            int _loc_3 =0;
            if (event.button != GenericDialogView.YES)
            {
                return;
            }
            _loc_2 = BuyLogic.getNextFranchiseUnlockCost;
            if (Global.player.cash >= _loc_2)
            {
                _loc_3 = Global.player.cash - _loc_2;
                if (_loc_3 < 0)
                {
                    _loc_3 = 0;
                }
                Global.player.cash = _loc_3;
                (Global.player.franchiseUnlocksPurchased + 1);
                unlockCellsList.push(this);
                GameTransactionManager.addTransaction(new TAcquireFranchiseUnlock(m_item.name));
                this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyForEmptyLot);
                refreshUnlockedCells(m_item.name);
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return;
        }//end

        protected void  unlockorBuyNeighbors (MouseEvent event )
        {
            UI.displayNeighborBuyPopup(this.m_item.requiredNeighbors, this.m_item.unlockCostCash, this.showNeighborMFS, this.buyNeighbors);
            return;
        }//end

        protected void  unlockorBuyForEmptyLot (MouseEvent event )
        {
            UI.displayFranchiseUnlockBuyPopup(BuyLogic.getNeededNeighborCountForNextFranchise, BuyLogic.getNextFranchiseUnlockCost, this.showEmptyLotNeighborMFS, this.buyFranchiseUnlocks);
            return;
        }//end

        protected void  showCantBuyDialog ()
        {
            String _loc_1 =null ;
            int _loc_2 =0;
            Array _loc_3 =null ;
            String _loc_4 =null ;
            GenericDialog _loc_5 =null ;
            switch(m_item.unlock)
            {
                case Item.UNLOCK_NOT_SEEN_FLAG:
                {
                    UI.displayMessage(ZLoc.t("Dialogs", m_item.requiredSeenFlagText), GenericDialogView.TYPE_OK);
                    break;
                }
                case Item.UNLOCK_SEEN_FLAG:
                {
                    UI.displayMessage(ZLoc.t("Collections", m_item.requiredSeenFlag + "_hint"), GenericDialogView.TYPE_OK);
                    break;
                }
                case Item.UNLOCK_PERMIT_BUNDLE:
                {
                    _loc_2 = m_item.itemRewards.length;
                    if (Global.player.inventory.spareCapacity < _loc_2)
                    {
                        _loc_1 = ZLoc.t("Dialogs", "InventoryFull");
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Dialogs", "CannonPurchasePermits_hint");
                    }
                    UI.displayMessage(_loc_1, GenericDialogView.TYPE_OK);
                    break;
                }
                case Item.UNLOCK_OBJECT_COUNT:
                {
                    if (m_item.requiredObjectsBase)
                    {
                        UI.closeCatalog();
                        _loc_3 = Global.world.getObjectsByNames(.get(m_item.requiredObjectsBase));
                        if (_loc_3.length > 0)
                        {
                            Global.world.setDefaultGameMode();
                            Global.world.centerOnObject(_loc_3.get(0));
                        }
                        else if (Global.player.inventory.getItemCountByName(m_item.requiredObjectsBase))
                        {
                            UI.displayInventory(m_item.requiredObjectsBase);
                        }
                        else
                        {
                            _loc_4 = ZLoc.t("Items", m_item.requiredObjectsBase + "_friendlyName");
                            UI.displayMessage(ZLoc.t("Dialogs", "needItem_hint", {itemCount:m_item.requiredObjectsCount, item:_loc_4}));
                        }
                    }
                    break;
                }
                case Item.UNLOCK_CAN_ADD_POPULATION:
                {
                    break;
                }
                default:
                {
                    if (m_item.marketQuestName)
                    {
                        UI.closeCatalog();
                        Global.questManager.showOrStartQuest(m_item.marketQuestName, m_item.marketQuestBegin);
                    }
                    else if (m_item.isRemodelSkin())
                    {
                        if (!RemodelManager.isFeatureAvailable())
                        {
                            if (RemodelManager.isConstructionSitePending())
                            {
                                UI.displayMessage(ZLoc.t("Dialogs", "skin_hint_finish"));
                            }
                            else if (Global.player.level < 15)
                            {
                                UI.displayMessage(ZLoc.t("Dialogs", "skin_hint_level"));
                            }
                            else
                            {
                                _loc_5 = new GenericDialog(ZLoc.t("Dialogs", "skin_hint_place"), "", GenericDialogView.TYPE_CUSTOM_OK, this.placeHQ, "", "", true, 0, "", null, ZLoc.t("Dialogs", "Mystery_Crate_ok"));
                                UI.displayPopup(_loc_5);
                            }
                        }
                    }
                    else
                    {
                        UI.displayMessage(ZLoc.t("Quest", m_item.requiredQuestFlag + "_hint"), GenericDialogView.TYPE_OK);
                    }
                    break;
                    break;
                }
            }
            return;
        }//end

        protected void  onWonderClick (MouseEvent event )
        {
            UI.closeCatalog();
            UI.displayPopup(new LandmarksTechTreeDialog(m_item), true, "LandmarksTechTreeDialog", true);
            return;
        }//end

        private void  placeHQ (GenericPopupEvent event )
        {
            MarketEvent _loc_2 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, "mun_constructioncompany", true);
                _loc_2.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_2);
                UI.closeCatalog();
            }
            return;
        }//end

        protected void  buyItem (MouseEvent event )
        {
            int cash_cost ;
            int instant_ready_cost ;
            int itemPrice ;
            Function unlockGetCashHandler ;
            int populationRequired ;
            int populationAvailable ;
            int needed ;
            Function warningHandler ;
            String residenceType ;
            LocalizationObjectToken localizedResType ;
            String warningText ;
            GenericDialog warningDialog ;
            Item scopedItem ;
            int cratePrice ;
            e = event;
            boolean is_instant_ready ;
            String instant_ready_msg ;

            if (m_item.localizedName == ZLoc.t("Items", "supply_all_business_friendlyName"))
            {
                is_instant_ready;
                instant_ready_msg = ZLoc.t("Items", "supply_all_business_popup");
            }
            if (m_item.localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName"))
            {
                is_instant_ready;
                instant_ready_msg = ZLoc.t("Items", "mark_all_business_ready_popup");
            }
            if (m_item.localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
            {
                is_instant_ready;
                instant_ready_msg = ZLoc.t("Items", "instant_ready_crop_popup");
            }
            if (is_instant_ready)
            {
                cash_cost = 0;
                instant_ready_cost = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_COST);
                if (instant_ready_cost == 2)
                {
                    cash_cost = 2;
                }
                else if (instant_ready_cost == 3)
                {
                    cash_cost = 3;
                }
                else if (instant_ready_cost == 4)
                {
                    cash_cost = 4;
                }
                else if (instant_ready_cost == 5)
                {
                    cash_cost = 5;
                }
                else
                {
                    cash_cost = 6;
                }
                UI .displayMessage (instant_ready_msg ,GenericDialogView .TYPE_OK ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    StatsManager.count("dialog", "link_test", m_item.name, "show", "", "", cash_cost);
                }
                return;
            }//end
            );
                return;
            }
            boolean buyOk =false ;
            if (Global.isVisiting())
            {
                buyOk = this.runFranchiseMarketCellChecks();
            }
            else if (this.m_itemLocked == true)
            {
                itemPrice = this.m_item.unlockCostCash;
                buyOk;
                if (m_item.unlock == Item.UNLOCK_PERMIT_BUNDLE || m_item.isRemodelSkin() && !RemodelManager.isFeatureAvailable())
                {
                    this.showCantBuyDialog();
                    return;
                }
                if (m_item.unlock == Item.UNLOCK_PERMITS)
                {
                    if (m_item.requiredQuestFlag.length > 0)
                    {
                        if (!Global.questManager.isFlagReached(m_item.requiredQuestFlag))
                        {
                            this.showCantBuyDialog();
                            return;
                        }
                    }
                    unlockGetCashHandler =Curry .curry (void  (MarketCell param1 ,GenericPopupEvent param2 )
            {
                if (param2.button == GenericDialogView.YES)
                {
                    UI.goToCashPage(param2);
                }
                else if (param2.button == GenericDialogView.SHARE)
                {
                    if (param1.get(param1.m_item.unlockShareFunc) != null)
                    {
                        _loc_3 = param1;
                        _loc_3.param1.get(param1.m_item.unlockShareFunc)();
                    }
                }
                return;
            }//end
            , this);
                    if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
                    {
                        populationRequired = ExpansionManager.instance.getNextExpansionPopulationRequirement();
                        populationAvailable = Global.world.citySim.getPopulation();
                        needed = populationRequired - populationAvailable;
                        if (needed > 0)
                        {
                            itemPrice = ExpansionManager.instance.getNextExpansionLockOverrideCost(m_item) + ExpansionManager.instance.getNeededPermits() * 3;
                        }
                        else
                        {
                            itemPrice = ExpansionManager.instance.getNeededPermits() * 3;
                        }
                        m_item.unlockCostCash = itemPrice;
                        UI.displayItemUnlock(this.unlockItem, this.m_item, Global.player.cash >= itemPrice, unlockGetCashHandler, !ExpansionManager.instance.hasEnoughPermits());
                    }
                    else
                    {
                        itemPrice = ExpansionManager.instance.hasEnoughPermits() ? (ExpansionManager.instance.getNextExpansionLockOverrideCost(m_item)) : (ExpansionManager.instance.getNextExpansionPermitCostToComplete());
                        m_item.unlockCostCash = itemPrice;
                        if (ExpansionManager.instance.hasEnoughPermits())
                        {
                            UI.displayItemUnlock(this.unlockItem, this.m_item, Global.player.cash >= itemPrice, unlockGetCashHandler, !ExpansionManager.instance.hasEnoughPermits());
                        }
                        else
                        {
                            UI.displayPermitBuyPopup(ExpansionManager.instance.getNextExpansionNumPermitPurchaseToComplete(), itemPrice, this.requestPermits, this.buyPermits);
                        }
                    }
                    return;
                }
                else if (BuyLogic.shouldShowCantBuyDialog(m_item))
                {
                    this.showCantBuyDialog();
                    return;
                }
                if (Global.player.cash >= itemPrice)
                {
                    UI.displayItemUnlock(this.unlockItem, this.m_item, true);
                }
                else
                {
                    UI.displayItemUnlock(this.unlockItem, this.m_item, false);
                }
            }
            else if (!BuyLogic.lotSiteCheck(this.m_item))
            {
                buyOk;
                UI.displayMessage(ZLoc.t("Dialogs", "LotSiteCheck"));
            }
            else if (BuyLogic.maxReceivedFranchisesCheck(this.m_item))
            {
                buyOk;
                UI.displayMessage(ZLoc.t("Dialogs", "MaxFranchisesDialogText"));
            }
            else if (m_item.type == "neighborhood" && !BuyLogic.hasResidencesForNeighborhood(m_item.name) && !this.m_isQuest)
            {
                buyOk;
                warningHandler =void  (GenericPopupEvent event )
            {
                if (event.button == GenericPopup.YES)
                {
                    dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, m_item, null, true));
                }
                return;
            }//end
            ;
                residenceType = Neighborhood.getResidenceTypeForNeighborhood(m_item.name);
                localizedResType = ZLoc.tk("Dialogs", "Neighborhood_" + residenceType);
                warningText = ZLoc.t("Dialogs", "NoEligibleResidenceWarning", {residences:localizedResType, neighborhood:m_item.localizedName});
                warningDialog = new GenericDialog(warningText, "storageWarning", GenericDialogView.TYPE_YESNO, warningHandler);
                UI.displayPopup(warningDialog);
            }
            else
            {
                buyOk;
            }
            //added by xinghai
            buyOk = true;

            if (buyOk)
            {
                if (m_item.unlock == Item.UNLOCK_PERMITS)
                {
                    GMExpand.setMarketCell(this);
                }
                scopedItem = m_item;
                if (m_item.type == "mystery_crate")
                {
                    cratePrice = m_item.cash;
                    if (Global.player.cash >= cratePrice && cratePrice != 0)
                    {
                        UI.displayItemPurchaseConfirm(this.confirmPurchase, this.m_item, true);
                    }
                    else
                    {
                        dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, this.m_item, null, true));
                    }
                }
                else if (m_item.type == "plot_contract" && Global.player.cash >= m_item.GetItemSalePrice() && m_item.GetItemSalePrice() > 0 && m_item.cash > 0)
                {
                    if (m_item.name != "plot_blueberries")
                    {
                        UI .displayMessage (ZLoc .t ("Dialogs","BuyCashQuestion",{m_item object .localizedName ,String amount (m_item .GetItemSalePrice ())}),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, scopedItem, null, true));
                }
                return;
            }//end
            );
                    }
                    else
                    {
                        UI .displayDonationMessage ("BlueberriesDonation","assets/dialogs/cityCare_blueberry.png",GenericDialogView .TYPE_OK ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, scopedItem, null, true));
                }
                return;
            }//end
            , "BlueberriesDonation", false, null, ZLoc.t("Dialogs", "BlueberriesDonation_title"), 0, m_item.cash);
                    }
                }
                else
                {
                    dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, this.m_item, null, true));
                }
            }
            return;
        }//end

         protected void  initializeCell ()
        {
            DisplayObject _loc_2 =null ;
            TextFormat _loc_3 =null ;
            TextFormat _loc_7 =null ;
            TextFormat _loc_8 =null ;
            AssetPane _loc_9 =null ;
            double _loc_11 =0;
            Sprite _loc_15 =null ;
            TextField _loc_16 =null ;
            TextFormat _loc_17 =null ;
            String _loc_18 =null ;
            String _loc_19 =null ;
            Sprite _loc_20 =null ;
            TextFormat _loc_21 =null ;
            String _loc_22 =null ;
            JPanel _loc_23 =null ;
            DisplayObject _loc_24 =null ;
            AssetPane _loc_25 =null ;
            Sprite _loc_26 =null ;
            Sprite _loc_1 =new Sprite ();
            TextField _loc_4 =new TextField ();
            TextField _loc_5 =new TextField ();
            TextField _loc_6 =new TextField ();
            Sprite _loc_10 =new Sprite ();
            boolean _loc_12 =true ;
            _loc_13 = m_item.isEmptyLot;
            this.m_itemIconPane.removeAll();
            if (Global.isVisiting())
            {
                _loc_11 = this.m_content.height;
                if (m_itemIcon)
                {
                    _loc_10.addChild(m_itemIcon);
                }
                m_itemIcon.alpha = 1;
                _loc_9 = new AssetPane(_loc_10);
                this.m_itemIconPane.append(_loc_9);
                this.addFranchiseSaleSticker();
            }
            else if (this.m_itemLocked == true && !_loc_13)
            {
                _loc_11 = this.m_content.height;
                if (m_itemIcon)
                {
                    _loc_10.addChild(m_itemIcon);
                    m_itemIcon.y = (90 - _loc_11) / 2;
                }
                _loc_15 = makeRequiredSprite(m_item, (DisplayObject)this.m_assetDict.get("pop_lock"));
                if (!m_item.enabledWhenLocked)
                {
                    _loc_10.addChild(_loc_15);
                }
                _loc_15.y = 0;
                if (m_itemIcon)
                {
                    if (!m_item.enabledWhenLocked)
                    {
                        m_itemIcon.alpha = 0.5;
                    }
                    if (_loc_15.width > m_itemIcon.width)
                    {
                        m_itemIcon.x = (_loc_15.width - m_itemIcon.width) / 2;
                    }
                    else
                    {
                        _loc_15.x = (m_itemIcon.width - _loc_15.width) / 2;
                    }
                    m_itemIcon.y = (_loc_11 - m_itemIcon.height) / 2;
                }
                _loc_16 = new TextField();
                _loc_17 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(11, 11, .get({locale:"ja", size:9})), 542337, null, null, null, null, null, TextFormatAlign.CENTER);
                _loc_16.antiAliasType = AntiAliasType.ADVANCED;
                _loc_16.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                _loc_16.selectable = false;
                _loc_16.wordWrap = true;
                _loc_16.multiline = true;
                if (m_item.unlock == Item.UNLOCK_PERMITS || m_item.unlock == Item.UNLOCK_QUEST_FLAG || m_item.unlock == Item.UNLOCK_QUEST_AND_LEVEL || m_item.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR || m_item.unlock == Item.UNLOCK_SEEN_FLAG || m_item.unlock == Item.UNLOCK_NOT_SEEN_FLAG || m_item.unlock == Item.UNLOCK_PERMIT_BUNDLE || m_item.rawUnlock == Item.UNLOCK_REQUIREMENTS)
                {
                    if (m_item.unlock == Item.UNLOCK_PERMIT_BUNDLE || m_item.rawUnlock == Item.UNLOCK_REQUIREMENTS)
                    {
                        _loc_12 = false;
                    }
                    if (m_item.unlock == Item.UNLOCK_NOT_SEEN_FLAG)
                    {
                        _loc_12 = false;
                    }
                    if (BuyLogic.hasUnlockedFlag(m_item))
                    {
                        _loc_12 = false;
                    }
                }
                _loc_18 = BuyLogic.getUnlockTipText(m_item);
                if (this.m_buyable && m_item.unlock != Item.UNLOCK_NEIGHBOR)
                {
                    if (_loc_18)
                    {
                        _loc_19 = _loc_18;
                    }
                    else if (_loc_12)
                    {
                        _loc_19 = BuyLogic.getUnlockEarlyText(m_item);
                    }
                    if (_loc_19)
                    {
                        _loc_16 = new TextField();
                        _loc_16.multiline = true;
                        _loc_16.wordWrap = true;
                        _loc_16.text = _loc_19;
                        _loc_16.setTextFormat(_loc_17);
                        _loc_16.width = Math.max(m_itemIcon.width, _loc_15.width);
                        _loc_16.height = _loc_19.length > 12 ? (30) : (15);
                        _loc_10.addChild(_loc_16);
                        _loc_16.y = _loc_19.length > 12 ? (62) : (75);
                        _loc_16.antiAliasType = AntiAliasType.ADVANCED;
                        _loc_16.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                        _loc_16.selectable = false;
                    }
                }
                _loc_9 = new AssetPane(_loc_10);
                _loc_9.setPreferredHeight(90);
                this.m_itemIconPane.append(_loc_9);
            }
            else if (Global.isVisiting() && this.m_item.localizedName == ZLoc.t("Items", "biz_lotsite_4x4_friendlyName"))
            {
                _loc_11 = this.m_content.height;
                _loc_10.addChild(m_itemIcon);
                m_itemIcon.alpha = 0.5;
                _loc_2 =(DisplayObject) new this.m_assetDict.get("pop_lock");
                _loc_10.addChild(_loc_2);
                m_itemIcon.y = (_loc_11 - m_itemIcon.height) / 2;
                _loc_2.y = 3;
                if (_loc_2.width > m_itemIcon.width)
                {
                    m_itemIcon.x = (_loc_2.width - m_itemIcon.width) / 2;
                }
                else
                {
                    _loc_2.x = (m_itemIcon.width - _loc_2.width) / 2;
                }
                _loc_16 = new TextField();
                _loc_16.text = ZLoc.t("Dialogs", "UnlockEarly");
                _loc_16.width = Math.max(m_itemIcon.width, _loc_2.width);
                _loc_16.height = 30;
                _loc_10.addChild(_loc_16);
                _loc_16.y = _loc_11 - 28;
                _loc_4 = new TextField();
                _loc_4.text = ZLoc.t("Dialogs", "RequiresText");
                _loc_4.width = _loc_2.width;
                _loc_4.height = 20;
                _loc_10.addChild(_loc_4);
                _loc_4.y = 1;
                _loc_5 = new TextField();
                _loc_5.text = ZLoc.t("Dialogs", "BusinessZoneRestrictOverlay");
                _loc_5.width = _loc_2.width;
                _loc_5.height = 20;
                _loc_10.addChild(_loc_5);
                _loc_5.y = 13;
                _loc_17 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(11, 11, [{locale:"ja", size:9}]), 542337, null, null, null, null, null, TextFormatAlign.CENTER);
                _loc_16.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                _loc_16.selectable = false;
                _loc_16.wordWrap = true;
                _loc_16.multiline = true;
                _loc_3 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:11}]), 16777215, null, null, null, null, null, TextFormatAlign.CENTER);
                _loc_4.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                _loc_4.selectable = false;
                _loc_8 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:11}]), 16773933, null, null, null, null, null, TextFormatAlign.CENTER);
                _loc_5.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                _loc_5.selectable = false;
                _loc_16.setTextFormat(_loc_17);
                _loc_4.setTextFormat(_loc_3);
                _loc_5.setTextFormat(_loc_8);
                if (_loc_2.width > m_itemIcon.width)
                {
                    m_itemIcon.x = (_loc_2.width - m_itemIcon.width) / 2;
                }
                else
                {
                    _loc_27 = m_itemIcon(.width-_loc_2.width)/2;
                    _loc_2.x = _loc_27;

                    _loc_4.x = _loc_27;
                    _loc_5.x = _loc_27;
                }
                _loc_9 = new AssetPane(_loc_10);
                this.m_itemIconPane.append(_loc_9);
            }
            else if (!BuyLogic.lotSiteCheck(this.m_item))
            {
                _loc_11 = this.m_content.height;
                if (m_itemIcon)
                {
                    _loc_10.addChild(m_itemIcon);
                    m_itemIcon.y = (90 - _loc_11) / 2;
                }
                _loc_20 = makeRequiredSprite(m_item, new this.m_assetDict.get("pop_lock"));
                _loc_10.addChild(_loc_20);
                _loc_20.y = 0;
                if (m_itemIcon)
                {
                    m_itemIcon.alpha = 0.5;
                    if (_loc_20.width > m_itemIcon.width)
                    {
                        m_itemIcon.x = (_loc_20.width - m_itemIcon.width) / 2;
                    }
                    else
                    {
                        _loc_20.x = (m_itemIcon.width - _loc_20.width) / 2;
                    }
                    m_itemIcon.y = (_loc_11 - m_itemIcon.height) / 2;
                }
                if (!BuyLogic.isAtMaxFranchiseCount())
                {
                    _loc_16 = new TextField();
                    _loc_21 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(11, 11, [{locale:"ja", size:9}]), 542337, null, null, null, null, null, TextFormatAlign.CENTER);
                    _loc_22 = ZLoc.t("Dialogs", "UnlockEarly", {amount:BuyLogic.getNextFranchiseUnlockCost});
                    _loc_16.multiline = true;
                    _loc_16.wordWrap = true;
                    _loc_16.text = _loc_22;
                    _loc_16.setTextFormat(_loc_21);
                    if (m_itemIcon == null)
                    {
                        _loc_16.width = _loc_20.width;
                    }
                    else
                    {
                        _loc_16.width = Math.max(m_itemIcon.width, _loc_20.width);
                    }
                    _loc_16.height = 30;
                    _loc_10.addChild(_loc_16);
                    _loc_16.y = 62;
                    _loc_16.antiAliasType = AntiAliasType.ADVANCED;
                    _loc_16.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                    _loc_16.selectable = false;
                }
                _loc_9 = new AssetPane(_loc_10);
                _loc_9.setPreferredHeight(90);
                this.m_itemIconPane.append(_loc_9);
            }
            else if (Global.player.gold < this.m_item.cost)
            {
                m_itemIcon.alpha = 1;
                _loc_9 = new AssetPane(m_itemIcon);
                this.m_itemIconPane.append(_loc_9);
                if (Global.marketSaleManager.isItemOnSale(m_item))
                {
                    this.addSaleSticker();
                }
            }
            else
            {
                if (m_item.name == "plot_lettuce")
                {
                    _loc_23 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                    _loc_24 =(DisplayObject) new EmbeddedArt.hud_act_cvcarsbug();
                    _loc_25 = new AssetPane(_loc_24);
                    _loc_23.append(_loc_25);
                    this.m_itemIconPane.append(_loc_23);
                }
                _loc_9 = new AssetPane(m_itemIcon);
                this.m_itemIconPane.append(_loc_9);
                if (m_item.masteryLevels.length > 0)
                {
                    _loc_26 = this.makeMasteryRibbon(this.getWidth());
                    if (_loc_26)
                    {
                        this.addChild(_loc_26);
                    }
                }
                if (Global.marketSaleManager.isItemOnSale(m_item))
                {
                    this.addSaleSticker();
                }
            }
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(this.m_itemIconPane);
            _loc_14 = this(.getWidth ()-this.m_itemIconPane.getWidth ())/2;
            ASwingHelper.setEasyBorder(this.m_itemIconPane, 0, _loc_14);
            return;
        }//end

        protected Sprite  makeMasteryRibbon (int param1 )
        {
            Sprite _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            _loc_5 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (Global.goalManager.getGoal(GoalManager.GOAL_MASTERY) as MasteryGoal && Global.player.isEligibleForMastery(m_item))
            {
                _loc_3 = _loc_5.getLevel(m_item.name);
                _loc_4 = _loc_5.getMaxLevel(m_item.name);
                _loc_2 = new StarRatingRibbon(param1, 18, _loc_3, true, DelayedAssetLoader.MASTERY_ASSETS, _loc_4, 8, "cropMastery_smallStar");
                ASwingHelper.prepare(_loc_2);
            }
            return _loc_2;
        }//end

        protected void  addFranchiseSaleSticker ()
        {
            Sprite _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            TextField _loc_3 =null ;
            TextField _loc_4 =null ;
            if (this.m_assetDict.get("saleSticker") == null)
            {
                return;
            }
            _loc_1 = new Sprite();
            _loc_2 =(DisplayObject) new this.m_assetDict.get("saleSticker");
            _loc_1.addChild(_loc_2);
            _loc_2.y = 3;
            _loc_3 = new TextField();
            _loc_3.text = Global.gameSettings().getInt("franchiseDiscountPct").toString() + "%";
            _loc_3.selectable = false;
            _loc_3.mouseEnabled = false;
            _loc_3.width = _loc_2.width;
            _loc_3.height = 20;
            _loc_1.addChild(_loc_3);
            _loc_3.x = 0;
            _loc_3.y = 12;
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_5.bold = true;
            _loc_5.color = 16777215;
            _loc_3.setTextFormat(_loc_5);
            _loc_4 = new TextField();
            _loc_4.text = "OFF!";
            _loc_4.mouseEnabled = false;
            _loc_4.selectable = false;
            _loc_4.width = _loc_2.width;
            _loc_4.height = 20;
            _loc_1.addChild(_loc_4);
            _loc_4.x = 0;
            _loc_4.y = _loc_3.y + 12;
            _loc_4.setTextFormat(_loc_5);
            this.addChild(_loc_1);
            _loc_1.x = this.width - _loc_1.width;
            _loc_1.y = 0;
            return;
        }//end

        protected void  addSaleSticker ()
        {
            Sprite _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            if (this.m_assetDict.get("sale_tag") == null)
            {
                return;
            }
            _loc_1 = new Sprite();
            _loc_2 =(DisplayObject) new this.m_assetDict.get("sale_tag");
            _loc_1.addChild(_loc_2);
            _loc_2.y = 3;
            this.addChild(_loc_1);
            _loc_1.x = 0;
            _loc_1.y = 0;
            return;
        }//end

        public void  onSetIconLoad (Event event )
        {
            AssetPane _loc_2 =new AssetPane(this.m_setIconLoader.content );
            this.m_setIcon.appendAll(_loc_2);
            ASwingHelper.prepare(this.m_setIcon);
            return;
        }//end

        public void  removeListeners ()
        {
            this.removeEventListener(MouseEvent.ROLL_OVER, this.switchBG);
            this.removeEventListener(MouseEvent.ROLL_OUT, this.revertBG);
            this.removeEventListener(MouseEvent.CLICK, this.buyItem);
            this.removeEventListener(MouseEvent.CLICK, this.unlockNeighbors);
            this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyForEmptyLot);
            this.removeEventListener(MouseEvent.CLICK, this.unlockorBuyNeighbors);
            return;
        }//end

         public void  removeFromContainer ()
        {
            super.removeFromContainer();
            Global.player.removeObserver(this);
            Global.world.removeObserver(this);
            return;
        }//end

        protected void  revertBG (MouseEvent event )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            MarginBackground _loc_2 =new MarginBackground(this.m_swappedBG ,new Insets(0,0,0,0));
            this.m_alignmentContainer.setBackgroundDecorator(_loc_2);
            ASwingHelper.prepare(this.m_alignmentContainer);
            return;
        }//end

        protected void  switchBG (MouseEvent event )
        {
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            dispatchEvent(new DataItemEvent(DataItemEvent.SHOW_TOOLTIP, this.m_item, _loc_3, true));
            MarginBackground _loc_4 =new MarginBackground(this.m_bgOver ,new Insets(0,0,0,0));
            this.m_swappedBG = this.m_alignmentContainer.getBackgroundDecorator().getDisplay(this.m_alignmentContainer);
            this.m_alignmentContainer.setBackgroundDecorator(_loc_4);
            ASwingHelper.prepare(this.m_alignmentContainer);
            return;
        }//end

        protected void  onBuyClick (MouseEvent event )
        {
            int _loc_2 =0;
            if (m_item.cash && Global.player.cash >= m_item.cash)
            {
                _loc_2 = this.getIndex(this.m_itemCost);
                this.removeAt(_loc_2);
                this.m_itemCost = null;
                this.m_itemCost = new JLabel(Global.player.inventory.getItemCountByName(m_item.name).toString() + "/" + this.m_amountNeeded);
                this.m_itemCost.setForeground(new ASColor(16777215, 1));
                this.m_itemCost.setFont(ASwingHelper.getBoldFont(14));
                this.insert(_loc_2, this.m_itemCost);
            }
            else if (m_item.fbc && m_item.fbc > 0)
            {
                _loc_2 = this.getIndex(this.m_itemCost);
                this.removeAt(_loc_2);
                this.m_itemCost = null;
                this.m_itemCost = new JLabel(ZLoc.t("Dialogs", "Buy"));
                this.m_itemCost.setForeground(new ASColor(16777215, 1));
                this.m_itemCost.setFont(ASwingHelper.getBoldFont(14));
                this.insert(_loc_2, this.m_itemCost);
            }
            return;
        }//end

        protected void  addCountdownTimer (JPanel param1 ,double param2 )
        {
            LocalizationObjectToken _loc_8 =null ;
            Timer _loc_9 =null ;
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "card_limited_counter");
            JPanel _loc_4 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.CENTER ));
            _loc_4.setBackgroundDecorator(new AssetBackground(_loc_3));
            _loc_4.setPreferredSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            _loc_4.setMaximumSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            _loc_4.setMinimumSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            int _loc_5 =new Date ().getTime ();
            int _loc_6 =m_item.endDate -_loc_5 ;
            _loc_7 = DateUtil.calculateTimeDifference(_loc_6);
            if (m_item.showTimer === false)
            {
                this.m_timerLabel = ASwingHelper.makeLabel(ZLoc.t("Main", "LimitedEditionMarketCellText"), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.whiteTextColor, JLabel.CENTER);
            }
            else if (_loc_7.days > 0)
            {
                _loc_8 = ZLoc.tk("Main", "Day", "", _loc_7.days);
                this.m_timerLabel = ASwingHelper.makeLabel(ZLoc.t("Main", "LimitedEditionMarketCellDayText", {days:_loc_7.days, day:_loc_8}), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.whiteTextColor, JLabel.CENTER);
            }
            else
            {
                this.m_timerLabel = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.whiteTextColor, JLabel.CENTER);
                this.m_timerLabel.setPreferredWidth(_loc_3.width);
                this.renderCountdownTimer(null);
                _loc_9 = new Timer(100);
                _loc_9.addEventListener(TimerEvent.TIMER, this.renderCountdownTimer);
                _loc_9.start();
            }
            _loc_4.append(this.m_timerLabel);
            param1.append(ASwingHelper.verticalStrut(-12));
            param1.append(_loc_4);
            return;
        }//end

        protected void  addExtraText (JPanel param1 ,double param2 )
        {
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "card_limited_counter");
            JPanel _loc_4 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.CENTER ));
            _loc_4.setBackgroundDecorator(new AssetBackground(_loc_3));
            _loc_4.setPreferredSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            _loc_4.setMaximumSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            _loc_4.setMinimumSize(new IntDimension((param2 - 1), (_loc_3.height + 1)));
            this.m_timerLabel = ASwingHelper.makeLabel(ZLoc.t("Items", m_item.extraText), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.whiteTextColor, JLabel.CENTER);
            _loc_4.append(this.m_timerLabel);
            param1.append(ASwingHelper.verticalStrut(-12));
            param1.append(_loc_4);
            return;
        }//end

        protected void  renderCountdownTimer (TimerEvent event )
        {
            int _loc_2 =new Date ().getTime ();
            int _loc_3 =m_item.endDate -_loc_2 ;
            _loc_4 = DateUtil.calculateTimeDifference(_loc_3);
            if (_loc_3 < 0)
            {
                _loc_4.days = 0;
                _loc_4.hours = 0;
                _loc_4.minutes = 0;
                _loc_4.seconds = 0;
            }
            if (_loc_4.minutes < 10)
            {
                _loc_4.minutes = "0" + _loc_4.minutes;
            }
            if (_loc_4.seconds < 10)
            {
                _loc_4.seconds = "0" + _loc_4.seconds;
            }
            _loc_5 = _loc_4.hours +":"+_loc_4.minutes +":"+_loc_4.seconds ;
            this.m_timerLabel.setText(_loc_5);
            this.m_timerLabel.repaint();
            return;
        }//end

        protected void  onAskClick (MouseEvent event )
        {
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public static void  refreshUnlockedCells (String param1 )
        {
            int _loc_2 =-1;
            int _loc_3 =0;
            while (_loc_3 < unlockCellsList.length())
            {

                if (unlockCellsList.get(_loc_3).m_item.name == param1)
                {
                    _loc_2 = _loc_3;
                }
                _loc_3++;
            }
            if (_loc_2 > -1)
            {
                unlockCellsList.get(_loc_2).refreshCell();
                unlockCellsList.splice(_loc_2);
            }
            return;
        }//end

        public static void  addLockCell (MarketCell param1 )
        {
            lockedCellsList.push(param1);
            return;
        }//end

        public static void  reLockCells (String param1 )
        {
            int _loc_2 =-1;
            int _loc_3 =0;
            while (_loc_3 < lockedCellsList.length())
            {

                if (lockedCellsList.get(_loc_3).m_item.name == param1)
                {
                    _loc_2 = _loc_3;
                }
                _loc_3++;
            }
            if (_loc_2 > -1)
            {
                lockedCellsList.get(_loc_2).killListeners();
                lockedCellsList.get(_loc_2).removeAll();
                if (lockedCellsList.get(_loc_2).m_itemIcon)
                {
                    lockedCellsList.get(_loc_2).m_itemIcon.alpha = 1;
                }
                lockedCellsList.get(_loc_2).buildCell();
                lockedCellsList.get(_loc_2).initializeCell();
                lockedCellsList.splice(_loc_2);
            }
            return;
        }//end

        public static Sprite  makeRequiredSprite (Item param1 ,DisplayObject param2 )
        {
            DisplayObject _loc_5 =null ;
            TextFormat _loc_6 =null ;
            TextFormat _loc_10 =null ;
            TextFormat _loc_11 =null ;
            int _loc_14 =0;
            Array _loc_15 =null ;
            _loc_3 = param1.unlock ;
            Sprite _loc_4 =new Sprite ();
            TextField _loc_7 =new TextField ();
            TextField _loc_8 =new TextField ();
            TextField _loc_9 =new TextField ();
            _loc_12 = param1.isEmptyLot ;
            if (param1.isEmptyLot)
            {
                if (!BuyLogic.isAtMaxFranchiseCount())
                {
                    _loc_3 = Item.UNLOCK_NEIGHBOR;
                }
            }
            switch(_loc_3)
            {
                case Item.UNLOCK_NEIGHBOR:
                {
                    _loc_5 =(DisplayObject) new EmbeddedArt.mkt_btn_green();
                    break;
                }
                case Item.UNLOCK_QUEST_AND_NEIGHBOR:
                {
                    if (param1.requiredQuestFlag.length > 0)
                    {
                        if (!Global.questManager.isFlagReached(param1.requiredQuestFlag))
                        {
                            _loc_5 = param2;
                        }
                        else if (!Global.player.checkNeighbors(param1.requiredNeighbors))
                        {
                            _loc_5 =(DisplayObject) new EmbeddedArt.mkt_btn_green();
                        }
                    }
                    else
                    {
                        _loc_5 =(DisplayObject) new EmbeddedArt.mkt_btn_green();
                    }
                    break;
                }
                default:
                {
                    _loc_5 = param2;
                    break;
                    break;
                }
            }
            if (_loc_5)
            {
                _loc_5.width = REQUIRED_WIDTH;
                _loc_5.height = REQUIRED_HEIGHT;
                _loc_5.y = 3;
                _loc_4.addChild(_loc_5);
            }
            _loc_13 = BuyLogic.getUnlockText(param1);
            _loc_7 = new TextField();
            _loc_7.text = _loc_13.requires;
            _loc_7.width = REQUIRED_WIDTH;
            _loc_7.height = 20;
            _loc_4.addChild(_loc_7);
            _loc_7.y = 1;
            _loc_8 = new TextField();
            _loc_8.text = _loc_13.line1;
            _loc_8.width = REQUIRED_WIDTH;
            _loc_8.height = 20;
            _loc_8.autoSize = TextFieldAutoSize.CENTER;
            _loc_8.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_8.selectable = false;
            _loc_8.multiline = true;
            _loc_8.wordWrap = true;
            _loc_4.addChild(_loc_8);
            _loc_8.y = 13;
            if (_loc_5)
            {
                _loc_14 = TextFieldUtil.mb_strwidth(_loc_8.text);
                if (_loc_14 >= 23)
                {
                    _loc_5.height = 60;
                }
                else if (_loc_14 >= 15)
                {
                    _loc_15 = _loc_8.text.split(" ");
                    _loc_5.height = _loc_15.get(1) && _loc_15.get(1).length < 13 ? (40) : (60);
                }
                else if (_loc_14 >= 12)
                {
                    _loc_5.height = 40;
                }
            }
            _loc_11 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:11}]), 16773933, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_10 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:11}]), 16777215, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_6 = new TextFormat(EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(12, 12, [{locale:"ja", size:11}]), 16777215, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_7.antiAliasType = AntiAliasType.ADVANCED;
            _loc_7.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_7.selectable = false;
            _loc_7.setTextFormat(_loc_6);
            if (_loc_3 == Item.UNLOCK_NEIGHBOR)
            {
                _loc_7.filters = .get(new DropShadowFilter(1, 90, 0, 0.2, 1, 1, 3, 1), new GlowFilter(0, 0.2, 2, 2, 10));
            }
            if (_loc_3 == Item.UNLOCK_QUEST_AND_NEIGHBOR)
            {
                if (Global.questManager.isFlagReached(param1.requiredQuestFlag))
                {
                    _loc_7.filters = .get(new DropShadowFilter(1, 90, 0, 0.2, 1, 1, 3, 1), new GlowFilter(0, 0.2, 2, 2, 10));
                    _loc_8.setTextFormat(_loc_10);
                    _loc_8.filters = .get(new DropShadowFilter(1, 90, 0, 0.2, 1, 1, 3, 1), new GlowFilter(0, 0.2, 2, 2, 10));
                }
                else
                {
                    _loc_8.setTextFormat(_loc_11);
                }
                return _loc_4;
            }
            if (_loc_3 == Item.UNLOCK_NEIGHBOR)
            {
                _loc_8.setTextFormat(_loc_10);
                _loc_8.filters = .get(new DropShadowFilter(1, 90, 0, 0.2, 1, 1, 3, 1), new GlowFilter(0, 0.2, 2, 2, 10));
            }
            else
            {
                _loc_8.setTextFormat(_loc_11);
            }
            if (param1.unlock == Item.UNLOCK_OLD_QUEST_OR_PURCHASE)
            {
                _loc_4.visible = false;
            }
            return _loc_4;
        }//end

    }




