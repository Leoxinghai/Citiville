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
import Classes.bonus.*;
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.BuyDialogs.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.remodel.*;
import Modules.stats.experiments.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class LargeMarketCell extends MarketCell implements IPlayerStateObserver, IGameWorldStateObserver
    {
        protected JPanel m_mainContainer ;
        protected JPanel m_titleContainer ;
        protected TextField m_timerText ;
        protected CustomButton m_skinButton ;
        private Sprite m_paintToolTipSprite ;
        private static  double ICON_PANE_WIDTH =88;
        private static  double ICON_PANE_HEIGHT =85;
public static  int MARKET_CELL_WIDTH =200;
public static  int CELL_WIDTH =210;
public static  int CELL_HEIGHT =130;

        public  LargeMarketCell (LayoutManager param1)
        {
            super(param1);
            return;
        }//end

        private DisplayObject  generateBackground ()
        {
            DisplayObject _loc_1 =null ;
            if (m_item.startDate && m_assetDict.hasOwnProperty("market2_leCard"))
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_leCard");
            }
            else if (m_item.isNew)
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_newCard");
            }
            else if (this.m_itemLocked)
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_lockedCard");
            }
            else
            {
                _loc_1 =(DisplayObject) new m_assetDict.get("market2_marketCard");
            }
            return _loc_1;
        }//end

        private int  getTextColor ()
        {
            if (m_item.startDate && Catalog.assetDict.hasOwnProperty("market2_leCard"))
            {
                return 16160555;
            }
            if (m_item.isNew)
            {
                return 8938745;
            }
            if (BuyLogic.isLocked(m_item))
            {
                return 5921370;
            }
            return 5155839;
        }//end

         protected void  buildCell ()
        {
            String _loc_2 =null ;
            AssetPane _loc_13 =null ;
            boolean _loc_14 =false ;
            if (m_item.localizedName == ZLoc.t("Items", "supply_all_business_friendlyName") || m_item.localizedName == ZLoc.t("Items", "mark_all_business_ready_friendlyName") || m_item.localizedName == ZLoc.t("Items", "instant_ready_crop_friendlyName"))
            {
                buildInstantReadyCell();
                return;
            }
            this.m_titleContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_mainContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            m_alignmentContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 5);
            ASwingHelper.setEasyBorder(m_alignmentContainer, 5);
            m_itemLocked = BuyLogic.isLocked(m_item);
            _loc_1 = this.generateBackground ();
            m_content = _loc_1;
            ASwingHelper.setBackground(this.m_mainContainer, m_content);
            this.m_mainContainer.setPreferredWidth(210);
            this.m_mainContainer.setPreferredHeight(130);
            this.m_titleContainer.setPreferredWidth(210);
            if (m_item.isBrandedBusiness)
            {
                _loc_2 = m_item.localizedName;
            }
            else
            {
                _loc_2 = TextFieldUtil.formatSmallCapsString(m_item.localizedName);
            }
            _loc_3 = ASwingHelper.makeLabel(_loc_2 ,EmbeddedArt.defaultFontNameBold ,12,this.getTextColor ());
            this.m_titleContainer.appendAll(_loc_3, ASwingHelper.verticalStrut(-4));
            Sprite _loc_4 =new Sprite ();
            _loc_4.graphics.beginFill(16777215, 1);
            _loc_4.graphics.drawRoundRect(0, 0, ICON_PANE_WIDTH, ICON_PANE_HEIGHT, 20, 20);
            _loc_4.graphics.endFill();
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            ASwingHelper.setSizedBackground(_loc_5, _loc_4);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Loading"),EmbeddedArt.defaultFontNameBold ,16,EmbeddedArt.darkBlueTextColor ,JLabel.CENTER );
            _loc_7 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_7.appendAll(_loc_6);
            m_itemIconPane.append(_loc_7);
            _loc_5.append(m_itemIconPane);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_8.append(_loc_5);
            m_alignmentContainer.appendAll(ASwingHelper.horizontalStrut(5));
            _loc_9 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REMODEL_V2 );
            if (m_item.type == "residence" && !m_item.isRemodelSkin() && m_item.hasRemodel() && _loc_9 == ExperimentDefinitions.REMODEL_V2_FEATURE)
            {
                _loc_13 = new AssetPane(new m_assetDict.get("market2_remodel_icon"));
                _loc_13.name = "skin";
                _loc_13.buttonMode = true;
                _loc_13.addEventListener(MouseEvent.MOUSE_OVER, this.makePaintToolTip, false, 0, true);
                _loc_13.addEventListener(MouseEvent.MOUSE_OUT, this.removePaintToolTip, false, 0, true);
                _loc_8.append(ASwingHelper.leftAlignElement(_loc_13));
            }
            m_alignmentContainer.appendAll(_loc_8);
            _loc_10 =             !m_itemLocked ? (this.makeStatsPanel()) : (this.makeLockedPanel());
            if ((!m_itemLocked ? (this.makeStatsPanel()) : (this.makeLockedPanel())) != null)
            {
                m_alignmentContainer.append(_loc_10);
            }
            m_alignmentContainer.setPreferredHeight(125);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel ();
            this.m_mainContainer.append(m_alignmentContainer);
            this.m_titleContainer.append(this.m_mainContainer);
            this.append(this.m_titleContainer);
            if (m_item.startDate && m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                this.addCountdownTimer(_loc_5, 88);
            }
            if (m_item.extraText && m_assetDict.hasOwnProperty("card_limited_counter"))
            {
                this.addExtraText(_loc_5, 88);
            }
            this.append(ASwingHelper.verticalStrut(6));
            _loc_12 = m_item.isEmptyLot;
            if (m_buyable)
            {
                this.setupPurchaseButton();
                if (m_purchaseButton != null)
                {
                    m_currencyIcon = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    m_currencyIcon.append(m_purchaseButton);
                    this.append(m_currencyIcon);
                }
                this.addEventListener(MouseEvent.ROLL_OVER, this.switchBG, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OUT, this.revertBG, false, 0, true);
                if (_loc_12 && this.m_itemLocked)
                {
                    if (!BuyLogic.isAtMaxFranchiseCount())
                    {
                        this.addEventListener(MouseEvent.CLICK, unlockorBuyForEmptyLot, false, 0, true);
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
                                this.addEventListener(MouseEvent.CLICK, unlockorBuyNeighbors, false, 0, true);
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
                else if (this.m_itemLocked && BuyLogic.hasBaseItemForSkinUnlock(m_item) && !RemodelManager.hasRemodelEligibleResidence(m_item))
                {
                    this.addEventListener(MouseEvent.CLICK, this.showSimpsonsMega, false, 0, true);
                }
                else
                {
                    this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                }
            }
            else if (m_item.type == "wonder")
            {
                m_purchaseButton = new CustomButton(ZLoc.t("Dialogs", "BuildInformation"), null, "CoinsButtonUI");
                _loc_14 = !BuyLogic.isLocked(m_item);
                m_purchaseButton.setEnabled(_loc_14);
                if (_loc_14)
                {
                    this.addEventListener(MouseEvent.CLICK, onWonderClick, false, 0, true);
                }
                if (m_purchaseButton != null)
                {
                    m_currencyIcon = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    m_currencyIcon.append(m_purchaseButton);
                    this.append(m_currencyIcon);
                }
            }
            else
            {
                this.append(ASwingHelper.verticalStrut(20));
                killListeners();
            }
            if (!Global.isVisiting() && Global.marketSaleManager.isItemOnSale(m_item))
            {
                addSaleSticker();
                _loc_8.append(createSaleLabel(Global.marketSaleManager.getDiscountPercent(m_item)));
            }
            if (Specials.getInstance().isValidSpecial(m_item.name))
            {
                _loc_8.append(createSpecialsLabel());
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  showSimpsonsMega (MouseEvent event )
        {
            SkinDialog _loc_2 =new SkinDialog(UI.m_catalog ,m_item );
            UI.displayPopup(_loc_2);
            return;
        }//end

        private void  makePaintToolTip (MouseEvent event )
        {
            TextField _loc_2 =null ;
            if (this.m_paintToolTipSprite == null)
            {
                this.m_paintToolTipSprite = new Sprite();
                _loc_2 = ASwingHelper.makeOneLineText(ZLoc.t("Market", "RemodelToolTip"), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.blueTextColor, TextFormatAlign.LEFT);
                this.m_paintToolTipSprite.addChild(_loc_2);
                this.m_paintToolTipSprite.graphics.lineStyle(2, EmbeddedArt.blueTextColor, 1, true);
                this.m_paintToolTipSprite.graphics.beginFill(16777215, 1);
                this.m_paintToolTipSprite.graphics.drawRoundRect(0, 0, _loc_2.width + 10, 20, 8, 8);
                this.m_paintToolTipSprite.graphics.endFill();
                this.m_paintToolTipSprite.x = 54;
                this.m_paintToolTipSprite.y = 113;
                this.addChild(this.m_paintToolTipSprite);
            }
            else
            {
                this.m_paintToolTipSprite.visible = true;
            }
            return;
        }//end

        private void  removePaintToolTip (MouseEvent event )
        {
            this.m_paintToolTipSprite.visible = false;
            return;
        }//end

         protected void  buyItem (MouseEvent event )
        {
            if (event.target.name == "skin")
            {
                dispatchEvent(new GenericObjectEvent(GenericObjectEvent.SKINS_CLICK, m_item, true));
                return;
            }
            super.buyItem(event);
            return;
        }//end

        public void  hideBuyButton ()
        {
            m_purchaseButton.visible = false;
            return;
        }//end

         protected void  setupPurchaseButton ()
        {
            super.setupPurchaseButton();
            if (!Global.isVisiting() && this.m_itemLocked)
            {
                if (m_item.unlock == Item.UNLOCK_NEIGHBOR)
                {
                    m_purchaseButton = new CustomButton(ZLoc.t("Dialogs", "TrainUI_Neighbors_button"), null, "CoinsButtonUI");
                }
            }
            if (Global.localizer.langCode == "ja")
            {
                m_purchaseButton.setFont(new ASFont(EmbeddedArt.titleFont, 12, false, false, false, EmbeddedArt.advancedFontProps));
                m_purchaseButton.setMargin(new Insets(0, 0, 0, 0));
                m_purchaseButton.setPreferredHeight(21);
            }
            return;
        }//end

         protected void  addCountdownTimer (JPanel param1 ,double param2 )
        {
            LocalizationObjectToken _loc_8 =null ;
            Timer _loc_9 =null ;
            Sprite _loc_3 =new Sprite ();
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "card_limited_counter");
            (new m_assetDict.get("card_limited_counter")).width = param2;
            _loc_3.addChild(_loc_4);
            int _loc_5 =new Date ().getTime ();
            int _loc_6 =m_item.endDate -_loc_5 ;
            _loc_7 = DateUtil.calculateTimeDifference(_loc_6);
            if (m_item.showTimer === false)
            {
                this.m_timerText = ASwingHelper.makeText(ZLoc.t("Main", "LimitedEditionMarketCellText"), param2, EmbeddedArt.defaultFontNameBold, 12);
            }
            else if (_loc_7.days > 0)
            {
                _loc_8 = ZLoc.tk("Main", "Day", "", _loc_7.days);
                this.m_timerText = ASwingHelper.makeText(ZLoc.t("Main", "LimitedEditionMarketCellDayText", {days:_loc_7.days, day:_loc_8}), param2, EmbeddedArt.defaultFontNameBold, 12);
            }
            else
            {
                this.m_timerText = ASwingHelper.makeText("", param2, EmbeddedArt.defaultFontNameBold, 12);
                this.renderCountdownTimer(null);
                _loc_9 = new Timer(100);
                _loc_9.addEventListener(TimerEvent.TIMER, this.renderCountdownTimer);
                _loc_9.start();
            }
            _loc_3.addChild(this.m_timerText);
            _loc_3.y = 85 - _loc_4.height;
            param1.addChild(_loc_3);
            return;
        }//end

         protected void  addExtraText (JPanel param1 ,double param2 )
        {
            Sprite _loc_3 =new Sprite ();
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "card_limited_counter");
            (new m_assetDict.get("card_limited_counter")).width = param2;
            _loc_3.addChild(_loc_4);
            this.m_timerText = ASwingHelper.makeText(ZLoc.t("Items", m_item.extraText), param2, EmbeddedArt.defaultFontNameBold, 12);
            _loc_3.addChild(this.m_timerText);
            _loc_3.y = 85 - _loc_4.height;
            param1.addChild(_loc_3);
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
            String _loc_5 =_loc_4.hours +":"+_loc_4.minutes +":"+_loc_4.seconds ;
            this.m_timerText.text = _loc_5;
            return;
        }//end

         protected void  initializeCell ()
        {
            AssetPane _loc_1 =null ;
            double _loc_3 =0;
            Sprite _loc_6 =null ;
            Sprite _loc_2 =new Sprite ();
            _loc_4 = m_item.isEmptyLot;
            m_itemIconPane.removeAll();
            if (Global.isVisiting())
            {
                _loc_3 = this.m_content.height;
                if (m_itemIcon)
                {
                    _loc_2.addChild(m_itemIcon);
                }
                m_itemIcon.alpha = 1;
                _loc_1 = new AssetPane(_loc_2);
                m_itemIconPane.append(_loc_1);
                if (Global.franchiseManager.placementMode)
                {
                    addFranchiseSaleSticker();
                }
            }
            else if (Global.isVisiting() && this.m_item.localizedName == ZLoc.t("Items", "biz_lotsite_4x4_friendlyName"))
            {
                _loc_3 = this.m_content.height;
                _loc_2.addChild(m_itemIcon);
                m_itemIcon.alpha = 0.5;
                _loc_1 = new AssetPane(_loc_2);
                m_itemIconPane.append(_loc_1);
            }
            else if (Global.player.gold < this.m_item.cost)
            {
                m_itemIcon.alpha = 1;
                _loc_1 = new AssetPane(m_itemIcon);
                m_itemIconPane.append(_loc_1);
            }
            else
            {
                _loc_1 = new AssetPane(m_itemIcon);
                m_itemIconPane.append(_loc_1);
                if (m_item.masteryLevels.length > 0)
                {
                    _loc_6 = makeMasteryRibbon(88);
                    if (_loc_6)
                    {
                        m_itemIconPane.addChild(_loc_6);
                    }
                }
            }
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(m_itemIconPane);
            _loc_5 = m_itemIconPane(88-.getWidth())/2;
            _loc_3 = (85 - m_itemIconPane.getHeight()) / 2;
            ASwingHelper.setEasyBorder(m_itemIconPane, _loc_3, _loc_5);
            return;
        }//end

         protected void  revertBG (MouseEvent event )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

         protected void  switchBG (MouseEvent event )
        {
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            Object _loc_4 =new Object ();
            DataItemEvent _loc_5 =new DataItemEvent(DataItemEvent.SHOW_TOOLTIP ,this.m_item ,_loc_3 ,true );
            _loc_5.setOffset(new Point(12, 70));
            dispatchEvent(_loc_5);
            return;
        }//end

        private Component  GetSalePanelForItem ()
        {
            if (m_item == null)
            {
                return null;
            }
            if (!Global.marketSaleManager.isItemOnSale(m_item))
            {
                return null;
            }
            Object _loc_1 =new Object ();
            _loc_1.type = TooltipType.SALE;
            _loc_1.title = ZLoc.t("Dialogs", "TT_Sale");
            _loc_2 = m_item.GetItemCost();
            _loc_1.firstData = _loc_2;
            _loc_1.lastLine = m_item.GetItemSalePrice();
            _loc_1.firstIcon =(DisplayObject) new EmbeddedArt.mkt_saleIcon();
            _loc_1.secondIcon =(DisplayObject) new EmbeddedArt.mkt_strikethroughIcon();
            _loc_3 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_1);
            return _loc_3;
        }//end

        private TextField  makeLockedPanelTitleTextField (String param1 )
        {
            _loc_2 = ASwingHelper.shrinkFontSizeToFit(101,param1 ,EmbeddedArt.titleFont ,14,EmbeddedArt.subtleBlackFilter ,null ,null ,9);
            _loc_3 = ASwingHelper.makeText(param1 ,101,EmbeddedArt.titleFont ,_loc_2 ,EmbeddedArt.whiteTextColor ,"left");
            _loc_3.filters = EmbeddedArt.subtleBlackFilter;
            return _loc_3;
        }//end

        private DisplayObject  makeLockedPanelContent ()
        {
            Sprite sprite ;
            double curY ;
            String neededPopulation ;
            String neededPermits ;
            TextField needsLabel ;
            TextField needsPopulationLabel ;
            TextField needsPermitsLabel ;
            int variant ;
            int activeVariant ;
            int cost ;
            AssetIcon icon ;
            TextField unlockLabel ;
            CustomButton unlockButton ;
            int populationRequired ;
            int populationAvailable ;
            int needed ;
            sprite = new Sprite();
            curY;
            requiresTextField = this.makeLockedPanelTitleTextField(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","RequiresText")));
            unlockTextField = ASwingHelper.makeText(BuyLogic.getUnlockText(m_item).line1,120,EmbeddedArt.defaultFontNameBold,11,EmbeddedArt.blueTextColor,"left");
            curY = curY + (requiresTextField.height - 8);
            unlockTextField.y = curY;
            curY = curY + unlockTextField.height;
            sprite.addChild(requiresTextField);
            sprite.addChild(unlockTextField);
            if (m_item.type == "expansion" && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
            {
                neededPopulation = ExpansionManager.instance.getNeededPopulationLabel();
                neededPermits = ExpansionManager.instance.getNeededPermitsLabel();
                if (neededPopulation.length + neededPermits.length > 0)
                {
                    needsLabel = ASwingHelper.makeText(ZLoc.t("Dialogs", "YouNeed"), 120, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.brownTextColor, "left");
                    needsLabel.y = curY - 4;
                    curY = curY + (needsLabel.height - 7);
                    sprite.addChild(needsLabel);
                    if (neededPopulation.length())
                    {
                        needsPopulationLabel = ASwingHelper.makeText(neededPopulation, 101, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.greenTextColor, "left");
                        needsPopulationLabel.y = curY;
                        curY = curY + (needsPopulationLabel.height - 4);
                        sprite.addChild(needsPopulationLabel);
                    }
                    if (neededPermits.length())
                    {
                        needsPermitsLabel = ASwingHelper.makeText(neededPermits, 101, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.greenTextColor, "left");
                        needsPermitsLabel.y = curY;
                        curY = curY + (needsPermitsLabel.height - 4);
                        sprite.addChild(needsPermitsLabel);
                    }
                }
            }
            boolean showUnlockEarlyText ;
            if (m_item.unlock == Item.UNLOCK_PERMITS || m_item.unlock == Item.UNLOCK_QUEST_FLAG || m_item.unlock == Item.UNLOCK_QUEST_AND_LEVEL || m_item.unlock == Item.UNLOCK_QUEST_AND_NEIGHBOR || m_item.unlock == Item.UNLOCK_SEEN_FLAG || m_item.unlock == Item.UNLOCK_NOT_SEEN_FLAG || m_item.unlock == Item.UNLOCK_PERMIT_BUNDLE)
            {
                if (m_item.unlock == Item.UNLOCK_PERMIT_BUNDLE || m_item.unlock == Item.UNLOCK_NOT_SEEN_FLAG)
                {
                    showUnlockEarlyText;
                }
                if (BuyLogic.hasUnlockedFlag(m_item))
                {
                    showUnlockEarlyText;
                }
            }
            if (m_item.unlock == Item.UNLOCK_OBJECT_COUNT)
            {
                showUnlockEarlyText;
            }
            if (m_item.isRemodelSkin() && m_item.unlock == Item.UNLOCK_CAN_ADD_POPULATION)
            {
                showUnlockEarlyText;
            }
            if (m_item.isRemodelSkin() && !RemodelManager.isFeatureAvailable())
            {
                showUnlockEarlyText;
            }
            if (m_item.derivedItemName == "res_simpsonmegabrick" && !RemodelManager.hasRemodelEligibleResidence(m_item))
            {
                showUnlockEarlyText;
            }
            if (m_item.derivedItemName == "res_beachfrontapt_a" && !RemodelManager.hasRemodelEligibleResidence(m_item))
            {
                showUnlockEarlyText;
            }
            if (m_buyable && showUnlockEarlyText && m_item.unlock != Item.UNLOCK_NEIGHBOR)
            {
                addUnlockButton = function(param1int)
            {
                AssetIcon _loc_2 =new AssetIcon(new m_assetDict.get( "icon_cash") );
                CustomButton _loc_3 =new CustomButton(param1.toString (),_loc_2 ,"CashButtonUI");
                ASwingHelper.prepare(_loc_3);
                _loc_4 = makeLockedPanelTitleTextField(TextFieldUtil.formatSmallCapsString(ZLoc.t("Market","UnlockText")));
                makeLockedPanelTitleTextField(TextFieldUtil.formatSmallCapsString(ZLoc.t("Market", "UnlockText"))).y = curY;
                curY = curY + _loc_4.height;
                _loc_3.y = curY;
                sprite.addChild(_loc_4);
                sprite.addChild(_loc_3);
                return;
            }//end
            ;
                if (m_item.unlock == Item.UNLOCK_PERMITS)
                {
                    if (m_item.requiredQuestFlag.length == 0 || Global.questManager.isFlagReached(m_item.requiredQuestFlag))
                    {
                        variant = ExpansionManager.instance.getBehaviorExperimentVariant();
                        activeVariant = ExperimentDefinitions.NEW_EXPANSION_BEHAVIOR_ACTIVATED;
                        if (variant != activeVariant || variant == activeVariant && ExpansionManager.instance.getNextExpansionNumPermitPurchaseToComplete() <= 0)
                        {
                            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
                            {
                                populationRequired = ExpansionManager.instance.getNextExpansionPopulationRequirement();
                                populationAvailable = Global.world.citySim.getPopulation();
                                needed = populationRequired - populationAvailable;
                                if (needed > 0)
                                {
                                    cost = ExpansionManager.instance.getNextExpansionLockOverrideCost(m_item) + ExpansionManager.instance.getNeededPermits() * 3;
                                }
                                else
                                {
                                    cost = ExpansionManager.instance.getNeededPermits() * 3;
                                }
                            }
                            else
                            {
                                cost = ExpansionManager.instance.getNextExpansionLockOverrideCost(m_item);
                            }
                            icon = new AssetIcon(new m_assetDict.get("icon_cash"));
                            unlockLabel = this.makeLockedPanelTitleTextField(TextFieldUtil.formatSmallCapsString(ZLoc.t("Market", "UnlockText")));
                            unlockButton = new CustomButton(String(cost), icon, "CashButtonUI");
                            ASwingHelper.prepare(unlockButton);
                            curY = curY - 4;
                            unlockLabel.y = curY;
                            curY = curY + unlockLabel.height;
                            unlockButton.y = curY;
                            curY = curY + (unlockLabel.height - 4);
                            sprite.addChild(unlockLabel);
                            sprite.addChild(unlockButton);
                        }
                    }
                }
                else if (m_item.unlock != Item.UNLOCK_LOCKED)
                {
                    addUnlockButton(m_item.unlockCostCash);
                }
            }
            return sprite;
        }//end

        protected Component  makeLockedPanel ()
        {
            return new AssetPane(this.makeLockedPanelContent());
        }//end

        private Component  makeResidenceStatsPanel ()
        {
            Sprite _loc_1 =new Sprite ();
            double _loc_2 =0;
            Object _loc_3 ={maxPop m_item.populationMax *Global.gameSettings().getNumber("populationScale",1),minPop.populationBase *Global.gameSettings().getNumber("populationScale",1)};
            _loc_4 = ZLoc.t("Dialogs","TT_Population");
            Class _loc_5 =PopulationHelper.getItemPopulationIcon(m_item );
            DisplayObject _loc_6 =(DisplayObject)new _loc_5;
            _loc_7 = ZLoc.t("Dialogs","TT_PopulationUpTo",_loc_3 )+"   ";
            _loc_8 = PopulationHelper.getItemPopulationSubTitle(m_item);
            _loc_9 = TooltipFactory.getInstance().createLargeMarketInfoTwoItems(_loc_4,_loc_6,_loc_7,_loc_8);
            _loc_9.y = _loc_2;
            _loc_2 = _loc_2 + _loc_9.height;
            _loc_1.addChild(_loc_9);
            _loc_10 = ZLoc.t("Dialogs","TT_Rent");
            DisplayObject _loc_11 =new EmbeddedArt.mkt_coinIcon ()as DisplayObject ;
            _loc_12 = m_item.getDooberMinimums(Doober.DOOBER_COIN).toString();
            _loc_13 = CardUtil.localizeTimeLeftLanguage(m_item.growTime);
            _loc_14 = TooltipFactory.getInstance().createLargeMarketInfoTwoItems(_loc_10,_loc_11,_loc_12,_loc_13);
            _loc_14.y = _loc_2;
            _loc_1.addChild(_loc_14);
            return new AssetPane(_loc_1);
        }//end

        private Component  makeBusinessStatsPanel ()
        {
            double _loc_2 =0;
            String _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            String _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            Sprite _loc_1 =new Sprite ();
            if (m_item.name != "biz_lotsite_4x4" && m_item.name != "supply_all_business" && m_item.name != "mark_all_business_ready" && m_item.name != "instant_ready_crop")
            {
                _loc_2 = 0;
                if (m_item.upgrade != null && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) && m_item.upgrade.meetsExperimentRequirement && Global.player.level >= Global.gameSettings().getInt("businessUpgradesRequiredLevel", 15))
                {
                    _loc_4 =(DisplayObject) new EmbeddedArt.mkt_masteryStar();
                    _loc_5 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "TT_Upgradeable") + " ");
                    _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoOneItem(null, _loc_4, _loc_5);
                    _loc_6.y = _loc_2;
                    _loc_2 = _loc_2 + _loc_6.height;
                    _loc_1.addChild(_loc_6);
                }
                _loc_3 = ZLoc.t("Dialogs", "TT_Earnings");
                _loc_4 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                _loc_5 = Math.ceil(m_item.getDooberMinimums(Doober.DOOBER_COIN) * m_item.commodityReq).toString();
                _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoOneItem(_loc_3, _loc_4, _loc_5);
                _loc_6.y = _loc_2;
                _loc_2 = _loc_2 + _loc_6.height;
                _loc_1.addChild(_loc_6);
                _loc_3 = ZLoc.t("Dialogs", "TT_SupplyReq");
                if (m_item.commodityName == Commodities.PREMIUM_GOODS_COMMODITY)
                {
                    _loc_4 =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                }
                else
                {
                    _loc_4 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                }
                _loc_5 = m_item.commodityReq.toString();
                _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoOneItemWLine(_loc_3, _loc_4, _loc_5);
                _loc_6.y = _loc_2;
                _loc_2 = _loc_2 + _loc_6.height;
                _loc_1.addChild(_loc_6);
            }
            return new AssetPane(_loc_1);
        }//end

        private Component  makeDecorationStatsPanel ()
        {
            ItemBonus _loc_2 =null ;
            DisplayObject _loc_6 =null ;
            ItemBonus _loc_7 =null ;
            String _loc_8 =null ;
            if (m_item.bonuses == null || m_item.bonuses.length == 0)
            {
                return null;
            }
            String _loc_1 ="";
            for(int i0 = 0; i0 < m_item.bonuses.size(); i0++)
            {
            		_loc_2 = m_item.bonuses.get(i0);

                if (_loc_2.description != "")
                {
                    _loc_1 = _loc_1 + ZLoc.t("Items", _loc_2.description);
                    _loc_1 = _loc_1 + "\n";
                }
            }
            if (m_item.bonusPercent <= 0 && _loc_1 == "")
            {
                return null;
            }
            _loc_3 = ItemBonus.keywordExperimentEnabled();
            Sprite _loc_4 =new Sprite ();
            double _loc_5 =0;
            if (_loc_1 != "")
            {
                _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoDescription(_loc_1);
                _loc_6.x = 0;
                _loc_6.y = _loc_5;
                _loc_5 = _loc_5 + _loc_6.height;
                _loc_4.addChild(_loc_6);
            }
            for(int i0 = 0; i0 < m_item.bonuses.size(); i0++)
            {
            		_loc_7 = m_item.bonuses.get(i0);

                if (_loc_7.maxPercentModifier != 0)
                {
                    _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoDescription(ZLoc.t("Dialogs", "TT_Payout", {amount:_loc_7.maxPercentModifier}));
                    _loc_6.x = 0;
                    _loc_6.y = _loc_5;
                    _loc_5 = _loc_5 + _loc_6.height;
                    _loc_4.addChild(_loc_6);
                }
                for(int i0 = 0; i0 < _loc_7.allowedTypes.size(); i0++)
                {
                		_loc_8 = _loc_7.allowedTypes.get(i0);

                    _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoIndentedDescriptorTextField(ZLoc.t("Dialogs", "TT_Payout_" + _loc_8));
                    _loc_6.x = 10;
                    _loc_6.y = _loc_5;
                    _loc_5 = _loc_5 + _loc_6.height;
                    _loc_4.addChild(_loc_6);
                }
                if (_loc_3 && _loc_7.allowedQueries.length > 0)
                {
                    _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoIndentedDescriptorTextField(ZLoc.t("Keywords", _loc_7.name + "_AOEName"));
                    _loc_6.x = 10;
                    _loc_6.y = _loc_5;
                    _loc_5 = _loc_5 + _loc_6.height;
                    _loc_4.addChild(_loc_6);
                }
            }
            return new AssetPane(_loc_4);
        }//end

        private Component  makePlotContractStatsPanel ()
        {
            DisplayObject _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_12 =null ;
            DisplayObject _loc_14 =null ;
            String _loc_15 =null ;
            String _loc_16 =null ;
            DisplayObject _loc_17 =null ;
            double _loc_1 =1;
            _loc_2 = ZLoc.t("Dialogs","TT_Makes");
            _loc_3 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_4 = _loc_3!= null && Global.player.isEligibleForMastery(m_item);
            if (_loc_3 != null && Global.player.isEligibleForMastery(m_item))
            {
                _loc_1 = _loc_3.getBonusMultiplier(m_item.name) / 100 + 1;
                _loc_2 = ZLoc.t("Dialogs", "TT_CurrentlyMakes");
            }
            Sprite _loc_5 =new Sprite ();
            double _loc_6 =0;
            _loc_9 = m_item.getDooberMinimums(Doober.DOOBER_PREMIUM_GOODS);
            if (m_item.getDooberMinimums(Doober.DOOBER_PREMIUM_GOODS) > 0)
            {
                _loc_7 =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                _loc_8 = Math.ceil(_loc_9 * _loc_1).toString();
            }
            else
            {
                _loc_7 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                _loc_8 = Math.ceil(m_item.getDooberMinimums(Doober.DOOBER_GOODS) * _loc_1).toString();
            }
            _loc_10 = TooltipFactory.getInstance().createLargeMarketInfoOneItem(_loc_2,_loc_7,_loc_8);
            _loc_10.y = _loc_6;
            _loc_6 = _loc_6 + _loc_10.height;
            _loc_5.addChild(_loc_10);
            if (_loc_4 && !_loc_3.isMaxLevel(m_item.name))
            {
                if (_loc_9 > 0)
                {
                    _loc_14 =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                }
                else
                {
                    _loc_14 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                }
                _loc_15 = ZLoc.t("Dialogs", "TT_StarBonus", {num:(_loc_3.getLevel(m_item.name) + 1)});
                _loc_16 = ZLoc.t("Dialogs", "TT_BonusPercent", {amount:_loc_3.getNextBonusMultiplier(m_item.name)});
                _loc_17 = TooltipFactory.getInstance().createLargeMarketInfoOneItem(_loc_15, _loc_14, _loc_16);
                _loc_17.y = _loc_6;
                _loc_6 = _loc_6 + _loc_17.height;
                _loc_5.addChild(_loc_17);
            }
            _loc_11 = ZLoc.t("Dialogs","TT_Harvest");
            if (m_item.growTime > 2.4e-005)
            {
                _loc_12 = CardUtil.localizeTimeLeft(m_item.growTime);
            }
            else
            {
                _loc_12 = ZLoc.t("Dialogs", "TT_InstantGrow");
            }
            _loc_13 = TooltipFactory.getInstance().createLargeMarketInfoOneLine(_loc_11,_loc_12);
            TooltipFactory.getInstance().createLargeMarketInfoOneLine(_loc_11, _loc_12).y = _loc_6;
            _loc_6 = _loc_6 + _loc_13.height;
            _loc_5.addChild(_loc_13);
            this.appendStatsPanelKeywords(_loc_5, _loc_6);
            return new AssetPane(_loc_5);
        }//end

        private Component  makeMunicipalStatsPanel ()
        {
            String _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            String _loc_11 =null ;
            DisplayObject _loc_12 =null ;
            Sprite _loc_1 =new Sprite ();
            double _loc_2 =0;
            if (m_item.name == "mun_policestation" && Global.player.level >= 10)
            {
                _loc_9 = ZLoc.t("Dialogs", "TT_Unlocks");
                _loc_10 =(DisplayObject) new EmbeddedArt.mkt_shieldIcon();
                _loc_11 = ZLoc.t("Dialogs", "TT_copsnbandits");
                _loc_12 = TooltipFactory.getInstance().createLargeMarketInfoOneItem(_loc_9, _loc_10, _loc_11);
                _loc_12.y = _loc_2;
                _loc_2 = _loc_2 + _loc_12.height;
                _loc_1.addChild(_loc_12);
            }
            _loc_3 = ZLoc.t("Dialogs","TT_Allows");
            Class _loc_4 =PopulationHelper.getItemPopulationIcon(m_item );

            DisplayObject _loc_5 =(DisplayObject)new _loc_4;

            _loc_6 = m_item(.populationCapYield*Global.gameSettings().getNumber("populationScale",1)).toString();
            _loc_7 = PopulationHelper.getItemPopulationSubTitle(m_item);
            _loc_8 = TooltipFactory.getInstance().createLargeMarketInfoTwoItems(_loc_3,_loc_5,_loc_6,_loc_7);
            TooltipFactory.getInstance().createLargeMarketInfoTwoItems(_loc_3, _loc_5, _loc_6, _loc_7).y = _loc_2;
            _loc_2 = _loc_2 + _loc_8.height;
            _loc_1.addChild(_loc_8);
            this.appendStatsPanelKeywords(_loc_1, _loc_2);
            return new AssetPane(_loc_1);
        }//end

        private DisplayObject  makeStatsPanelKeywords ()
        {
            String _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            if (m_item.type == "decoration" || m_item.type == "business" || m_item.type == "residence" || !ItemBonus.keywordExperimentEnabled())
            {
                return null;
            }
            _loc_1 = m_item.getVisibleItemKeywords();
            if (_loc_1 == null || _loc_1.length == 0)
            {
                return null;
            }
            Sprite _loc_2 =new Sprite ();
            double _loc_3 =0;
            _loc_4 = TooltipFactory.getInstance().createLargeMarketInfoTitle(ZLoc.t("Dialogs","TT_Bonus"));
            TooltipFactory.getInstance().createLargeMarketInfoTitle(ZLoc.t("Dialogs", "TT_Bonus")).y = _loc_3;
            _loc_3 = _loc_3 + _loc_4.height;
            _loc_2.addChild(_loc_4);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_5 = _loc_1.get(i0);

                _loc_6 = TooltipFactory.getInstance().createLargeMarketInfoIndentedDescriptor(ZLoc.t("Keywords", _loc_5 + "_friendlyName"));
                _loc_6.y = _loc_3;
                _loc_3 = _loc_3 + _loc_6.height;
                _loc_2.addChild(_loc_6);
            }
            return _loc_2;
        }//end

        private void  appendStatsPanelKeywords (DisplayObjectContainer param1 ,double param2 )
        {
            _loc_3 = this.makeStatsPanelKeywords ();
            if (_loc_3 != null)
            {
                _loc_3.y = param2;
                param1.addChild(_loc_3);
            }
            return;
        }//end

        protected Component  makeStatsPanel ()
        {
            Object _loc_3 =null ;
            Component _loc_4 =null ;
            Object _loc_5 =null ;
            Component _loc_6 =null ;
            Object _loc_7 =null ;
            Component _loc_8 =null ;
            Object _loc_9 =null ;
            Component _loc_10 =null ;
            Object _loc_11 =null ;
            Component _loc_12 =null ;
            Object _loc_13 =null ;
            Component _loc_14 =null ;
            Object _loc_15 =null ;
            Component _loc_16 =null ;
            Dictionary _loc_17 =null ;
            MechanicConfigData _loc_18 =null ;
            Sprite _loc_19 =null ;
            MechanicConfigData _loc_20 =null ;
            double _loc_21 =0;
            double _loc_22 =0;
            MechanicConfigData _loc_23 =null ;
            double _loc_24 =0;
            double _loc_25 =0;
            double _loc_26 =0;
            double _loc_27 =0;
            Object _loc_28 =null ;
            Component _loc_29 =null ;
            Object _loc_30 =null ;
            Component _loc_31 =null ;
            Object _loc_32 =null ;
            Component _loc_33 =null ;
            Array _loc_34 =null ;
            String _loc_35 =null ;
            int _loc_36 =0;
            String _loc_37 =null ;
            LocalizationObjectToken _loc_38 =null ;
            Object _loc_39 =null ;
            Component _loc_40 =null ;
            int _loc_41 =0;
            LocalizationObjectToken _loc_42 =null ;
            Object _loc_43 =null ;
            Component _loc_44 =null ;
            Object _loc_45 =null ;
            Component _loc_46 =null ;
            Object _loc_47 =null ;
            Component _loc_48 =null ;
            Object _loc_49 =null ;
            Component _loc_50 =null ;
            Object _loc_51 =null ;
            Component _loc_52 =null ;
            Object _loc_53 =null ;
            Component _loc_54 =null ;
            Object _loc_55 =null ;
            Component _loc_56 =null ;
            Object _loc_57 =null ;
            Component _loc_58 =null ;
            Component _loc_59 =null ;
            Object _loc_60 =null ;
            Class _loc_61 =null ;
            Component _loc_62 =null ;
            Object _loc_63 =null ;
            Component _loc_64 =null ;
            Object _loc_65 =null ;
            int _loc_66 =0;
            int _loc_67 =0;
            int _loc_68 =0;
            int _loc_69 =0;
            Object _loc_70 =null ;
            Object _loc_71 =null ;
            Component _loc_72 =null ;
            Object _loc_73 =null ;
            Component _loc_74 =null ;
            boolean _loc_75 =false ;
            Object _loc_76 =null ;
            Component _loc_77 =null ;
            Object _loc_78 =null ;
            Class _loc_79 =null ;
            Component _loc_80 =null ;
            Object _loc_81 =null ;
            Object _loc_82 =null ;
            Component _loc_83 =null ;
            DisplayObject _loc_84 =null ;
            DisplayObject _loc_85 =null ;
            int _loc_86 =0;
            Object _loc_87 =null ;
            Component _loc_88 =null ;
            Object _loc_89 =null ;
            Component _loc_90 =null ;
            Object _loc_91 =null ;
            Component _loc_92 =null ;
            Object _loc_93 =null ;
            Component _loc_94 =null ;
            Object _loc_95 =null ;
            Component _loc_96 =null ;
            String _loc_97 =null ;
            Object _loc_98 =null ;
            Component _loc_99 =null ;
            int _loc_100 =0;
            String _loc_101 =null ;
            double _loc_102 =0;
            String _loc_103 =null ;
            DisplayObject _loc_104 =null ;
            CustomerMaintenanceBonus _loc_105 =null ;
            ItemBonus _loc_106 =null ;
            Object _loc_107 =null ;
            Component _loc_108 =null ;
            String _loc_109 =null ;
            Object _loc_110 =null ;
            Component _loc_111 =null ;
            Object _loc_112 =null ;
            Object _loc_113 =null ;
            Object _loc_114 =null ;
            Object _loc_115 =null ;
            Component _loc_116 =null ;
            AssetPane _loc_117 =null ;
            switch(m_item.type)
            {
                case "residence":
                {
                    return this.makeResidenceStatsPanel();
                }
                case "business":
                {
                    return this.makeBusinessStatsPanel();
                }
                case "decoration":
                {
                    return this.makeDecorationStatsPanel();
                }
                case "plot_contract":
                {
                    return this.makePlotContractStatsPanel();
                }
                case "university":
                case "municipal":
                {
                    return this.makeMunicipalStatsPanel();
                }
                default:
                {
                    break;
                }
            }
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            switch(m_item.type)
            {
                case "genericBundle":
                {
                    _loc_3 = new Object();
                    _loc_3.type = TooltipType.GENERIC_BUNDLE;
                    _loc_3.dataItem = m_item;
                    _loc_3.contentPanel = _loc_1;
                    _loc_4 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_3);
                    _loc_1.appendAll(_loc_4);
                    break;
                }
                case "themed_bundle":
                {
                    _loc_5 = new Object();
                    _loc_5.type = TooltipType.THEMED_BUNDLE;
                    _loc_5.dataItem = m_item;
                    _loc_5.contentPanel = _loc_1;
                    _loc_6 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_5);
                    ASwingHelper.prepare(_loc_6);
                    break;
                }
                case "permit_pack2":
                case "permit_pack3":
                case "permit_pack4":
                {
                    _loc_7 = new Object();
                    _loc_7.type = TooltipType.PERMIT_PACK;
                    _loc_7.content = ZLoc.t("Items", "permit_pack_friendlyName");
                    _loc_7.dataItem = m_item;
                    _loc_7.contentPanel = _loc_1;
                    _loc_8 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_7);
                    _loc_1.appendAll(_loc_8);
                    break;
                }
                case "starter_pack2_1":
                case "starter_pack2_2":
                case "starter_pack2_3":
                case "starter_pack2_4":
                {
                    _loc_9 = new Object();
                    _loc_9.type = TooltipType.ONE_ITEM;
                    _loc_9.title = ZLoc.t("Dialogs", "TT_Energy");
                    _loc_9.firstIcon =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_9.firstData = String(m_item.energyRewards);
                    _loc_10 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_9);
                    _loc_11 = new Object();
                    _loc_11.type = TooltipType.ONE_ITEM;
                    _loc_11.title = ZLoc.t("Dialogs", "TT_Cash");
                    _loc_11.firstIcon =(DisplayObject) new EmbeddedArt.icon_cash();
                    _loc_11.firstData = String(m_item.cashRewards);
                    _loc_12 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_11);
                    _loc_13 = new Object();
                    _loc_13.type = TooltipType.DESCRIPTION;
                    _loc_13.content = ZLoc.t("Market", "TT_AndMore");
                    _loc_13.ttbgWidth = 97;
                    _loc_14 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_13);
                    _loc_1.appendAll(_loc_10, _loc_12, _loc_14);
                    break;
                }
                case "mystery_crate":
                {
                    _loc_15 = new Object();
                    _loc_15.type = "mystery_crate";
                    _loc_15.content = ZLoc.t("Items", "mystery_crate_friendlyName");
                    _loc_15.dataItem = m_item;
                    _loc_15.contentPanel = _loc_1;
                    _loc_16 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_15);
                    ASwingHelper.prepare(_loc_16);
                    break;
                }
                case "wonder":
                {
                    _loc_17 = Global.gameSettings().getDisplayedStatsByName(m_item.name);
                    for(int i0 = 0; i0 < _loc_17.bonus.size(); i0++)
                    {
                    		_loc_97 = _loc_17.bonus.get(i0);

                        _loc_98 = new Object();
                        _loc_98.type = TooltipType.ONE_ITEM;
                        _loc_98.title = ZLoc.t("Items", _loc_97 + "_market_header");
                        _loc_98.firstIcon = null;
                        _loc_98.firstData = ZLoc.t("Items", _loc_97 + "_market_data");
                        _loc_99 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_98);
                        _loc_1.append(_loc_99);
                    }
                    break;
                }
                case "energyToggle":
                {
                    _loc_18 = m_item.getMechanicConfigByEventAndName(MechanicManager.PLAY, "PaidRenewalMechanic");
                    _loc_19 = new Sprite();
                    if (_loc_18 && _loc_18.params != null)
                    {
                        _loc_100 = _loc_18.priceTestCost;
                        _loc_101 = _loc_100 > 0 ? (String(_loc_100)) : (_loc_18.params.get("cost"));
                        _loc_102 = Number(_loc_18.params.get("renewDuration")) / 3600;
                        _loc_103 = ZLoc.t("Items", m_item.name + "_market_desc", {duration:_loc_102, cost:_loc_101});
                        if (_loc_103 != "")
                        {
                            _loc_104 = TooltipFactory.getInstance().createLargeMarketInfoDescription(_loc_103);
                            _loc_104.x = 0;
                            _loc_104.y = 0;
                            _loc_19.addChild(_loc_104);
                        }
                    }
                    _loc_1.append(new AssetPane(_loc_19));
                    break;
                }
                case "attraction":
                {
                    _loc_20 = m_item.getMechanicConfigByEventAndName(MechanicManager.PLAY, "StartTimerHarvestSleepMechanic");
                    _loc_21 = _loc_20 && _loc_20.params && _loc_20.params.hasOwnProperty("totalTime") ? (int(_loc_20.params.get("totalTime"))) : (0);
                    _loc_22 = _loc_21 / (60 * 60 * 24);
                    _loc_23 = m_item.getMechanicConfigByEventAndName(MechanicManager.PLAY, "TimerHarvestSleepMechanic");
                    _loc_24 = TimerHarvestSleepMechanic.getSleepDuration(_loc_23.params);
                    _loc_25 = _loc_24 / (60 * 60 * 24);
                    _loc_26 = 0;
                    _loc_27 = 0;
                    if (m_item && m_item.harvestBonuses && m_item.harvestBonuses.length > 0)
                    {
                        _loc_105 =(CustomerMaintenanceBonus) m_item.harvestBonuses.get(0);
                        if (_loc_105 != null)
                        {
                            _loc_26 = _loc_105.minPayout;
                            _loc_27 = _loc_105.maxPercentModifier;
                        }
                    }
                    _loc_28 = {type:TooltipType.ONE_LINE, title:ZLoc.t("Dialogs", "TT_RunningTime"), firstData:CardUtil.localizeTimeLeft(_loc_22)};
                    _loc_29 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_28);
                    _loc_30 = {type:TooltipType.ONE_LINE, title:ZLoc.t("Dialogs", "TT_MaintenanceTime"), firstData:CardUtil.localizeTimeLeft(_loc_25)};
                    _loc_31 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_30);
                    _loc_32 = {type:TooltipType.ONE_ITEM, title:ZLoc.t("Dialogs", "TT_Earnings"), firstIcon:new EmbeddedArt.mkt_coinIcon(), firstData:ZLoc.t("Dialogs", "TT_MinMax", {min:_loc_26, max:_loc_27})};
                    _loc_33 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_32);
                    _loc_1.appendAll(_loc_29, _loc_31, _loc_33);
                    break;
                }
                case "neighborhood":
                {
                    _loc_34 = m_item.storables;
                    _loc_35 = m_item.name;
                    _loc_36 = Neighborhood.getCapacityForNeighborhood(_loc_35);
                    _loc_37 = Neighborhood.getResidenceTypeForNeighborhood(_loc_35);
                    _loc_38 = ZLoc.tk("Dialogs", "Neighborhood_" + _loc_37, "", _loc_36);
                    _loc_39 = {type:TooltipType.ONE_LINE, title:ZLoc.t("Dialogs", "TT_NeighborhoodCapacityTitle"), firstData:ZLoc.t("Dialogs", "TT_NeighborhoodResCount", {count:_loc_36, pluralizedType:_loc_38})};
                    _loc_40 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_39);
                    _loc_41 = Global.world.getObjectsByKeywords(_loc_37).length;
                    _loc_42 = ZLoc.tk("Dialogs", "Neighborhood_" + _loc_37, "", _loc_41);
                    _loc_43 = {type:TooltipType.ONE_LINE, title:ZLoc.t("Dialogs", "TT_NeighborhoodCurrCountTitle"), firstData:ZLoc.t("Dialogs", "TT_NeighborhoodResCount", {count:_loc_41, pluralizedType:_loc_42})};
                    _loc_44 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_43);
                    _loc_1.appendAll(_loc_40, _loc_44);
                    break;
                }
                case "storage":
                {
                    _loc_45 = new Object();
                    _loc_45.type = TooltipType.ONE_ITEM;
                    _loc_45.title = ZLoc.t("Dialogs", "TT_Storage");
                    _loc_45.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_45.firstData = String(m_item.commodityCapacity);
                    _loc_46 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_45);
                    _loc_1.appendAll(_loc_46);
                    break;
                }
                case "plot":
                {
                    break;
                }
                case "factory_contract":
                {
                    _loc_47 = new Object();
                    _loc_47.type = TooltipType.ONE_ITEM;
                    _loc_47.title = ZLoc.t("Dialogs", "TT_Makes");
                    if (Global.player.GetDooberMinimums(m_item, Doober.DOOBER_PREMIUM_GOODS) > 0)
                    {
                        _loc_47.firstIcon =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                        _loc_47.firstData = ZLoc.t("Dialogs", "TT_PerWorker", {amount:Global.player.GetDooberMinimums(m_item, Doober.DOOBER_PREMIUM_GOODS)});
                    }
                    else
                    {
                        _loc_47.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_47.firstData = ZLoc.t("Dialogs", "TT_PerWorker", {amount:Global.player.GetDooberMinimums(m_item, Doober.DOOBER_GOODS)});
                    }
                    _loc_48 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_47);
                    _loc_49 = new Object();
                    _loc_49.type = TooltipType.ONE_LINE;
                    _loc_49.title = ZLoc.t("Dialogs", "TT_BuildsIn");
                    if (m_item.growTime > 2.4e-005)
                    {
                        _loc_49.firstData = CardUtil.localizeTimeLeft(m_item.growTime);
                    }
                    else
                    {
                        _loc_49.firstData = ZLoc.t("Dialogs", "TT_InstantGrow");
                    }
                    _loc_50 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_49);
                    _loc_1.appendAll(_loc_48, _loc_50);
                    break;
                }
                case "garden":
                {
                    if (m_item.bonuses.length > 0)
                    {
                        for(int i0 = 0; i0 < m_item.bonuses.size(); i0++)
                        {
                        		_loc_106 = m_item.bonuses.get(i0);

                            _loc_107 = new Object();
                            _loc_107.type = TooltipType.DESCRIPTION;
                            _loc_107.ttbgWidth = 100;
                            _loc_107.content = _loc_106.getMarketPlaceString();
                            _loc_108 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_107);
                            _loc_1.appendAll(_loc_108);
                            for(int i0 = 0; i0 < _loc_106.allowedTypes.size(); i0++)
                            {
                            		_loc_109 = _loc_106.allowedTypes.get(i0);

                                _loc_110 = {type:"indentedDescriptor", firstData:ZLoc.t("Dialogs", "TT_Payout_" + _loc_109)};
                                _loc_111 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_110);
                                _loc_1.appendAll(_loc_111);
                            }
                        }
                    }
                    break;
                }
                case "pier":
                {
                    break;
                }
                case "ship":
                case "harvest_ship":
                {
                    _loc_51 = new Object();
                    _loc_51.type = TooltipType.DESCRIPTION;
                    _loc_51.ttbgWidth = 100;
                    _loc_51.content = ZLoc.t("Dialogs", "TT_ShipRules");
                    _loc_52 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_51);
                    _loc_53 = new Object();
                    _loc_53.type = TooltipType.REQUIREMENT;
                    _loc_53.ttbgWidth = 100;
                    _loc_53.content = ZLoc.t("Dialogs", "TT_ShipReq");
                    _loc_54 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_53);
                    _loc_1.appendAll(_loc_52, _loc_54);
                    break;
                }
                case "ship_contract":
                {
                    _loc_55 = new Object();
                    _loc_55.type = TooltipType.ONE_ITEM;
                    _loc_55.title = ZLoc.t("Dialogs", "TT_Makes");
                    _loc_55.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_55.firstData = String(Global.player.GetDooberMinimums(m_item, Doober.DOOBER_GOODS));
                    _loc_56 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_55);
                    _loc_57 = new Object();
                    _loc_57.type = TooltipType.ONE_LINE;
                    _loc_57.title = ZLoc.t("Dialogs", "TT_RoundTrip");
                    _loc_57.firstData = CardUtil.localizeTimeLeft(m_item.growTime);
                    _loc_58 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_57);
                    _loc_1.appendAll(_loc_56, _loc_58);
                    break;
                }
                case "municipal":
                {
                    if (m_item.name == "mun_policestation" && Global.player.level >= 10)
                    {
                        _loc_112 = new Object();
                        _loc_112.type = TooltipType.ONE_ITEM;
                        _loc_112.title = ZLoc.t("Dialogs", "TT_Unlocks");
                        _loc_112.firstIcon =(DisplayObject) new EmbeddedArt.mkt_shieldIcon();
                        _loc_112.firstData = ZLoc.t("Dialogs", "TT_copsnbandits");
                        _loc_59 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_112);
                    }
                    _loc_60 = new Object();
                    _loc_61 = PopulationHelper.getItemPopulationIcon(m_item);
                    _loc_60.type = TooltipType.TWO_ITEMS;
                    _loc_60.title = ZLoc.t("Dialogs", "TT_Allows");
                    _loc_60.firstIcon =(DisplayObject) new _loc_61;
                    _loc_60.firstData = String(m_item.populationCapYield * Global.gameSettings().getNumber("populationScale", 1));
                    _loc_60.lastLine = PopulationHelper.getItemPopulationSubTitle(m_item);
                    _loc_62 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_60);
                    _loc_63 = new Object();
                    _loc_63.type = TooltipType.DESCRIPTION;
                    _loc_63.ttbgWidth = 100;
                    _loc_63.content = ZLoc.t("Dialogs", "TT_MuniRules");
                    _loc_64 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_63);
                    _loc_1.appendAll(_loc_59, _loc_62, _loc_64);
                    break;
                }
                case "expansion":
                {
                    _loc_65 = {type:"requirement", content:ZLoc.t("Dialogs", "TT_Requires")};
                    _loc_1.append(TooltipFactory.getInstance().createLargeMarketInfo(_loc_65));
                    _loc_66 = ExpansionManager.instance.getNextExpansionPermitRequirement();
                    _loc_67 = Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM);
                    _loc_68 = ExpansionManager.instance.getNextExpansionPopulationRequirement();
                    _loc_69 = Global.world.citySim.getPopulation();
                    if (_loc_66 > 0)
                    {
                        _loc_113 = {type:"checkmarkwLine", firstData:ZLoc.t("Dialogs", "TT_ExpansionPermitsReq", {permits:Math.max(0, _loc_66 - _loc_67)}), isComplete:_loc_67 >= _loc_66};
                        _loc_1.append(TooltipFactory.getInstance().createLargeMarketInfo(_loc_113));
                    }
                    if (_loc_68 > 0)
                    {
                        _loc_114 = {type:"checkmarkwLine", firstColor:EmbeddedArt.greenTextColor, firstData:ZLoc.t("Dialogs", "TT_ExpansionPopulationReq", {population:_loc_68 * Global.gameSettings().getNumber("populationScale", 1)}), isComplete:_loc_69 >= _loc_68};
                        _loc_1.append(TooltipFactory.getInstance().createLargeMarketInfo(_loc_114));
                    }
                    _loc_70 = new Object();
                    _loc_70.type = TooltipType.DESCRIPTION;
                    _loc_70.ttbgWidth = 100;
                    _loc_70.content = ZLoc.t("Dialogs", "TT_ExpansionRules");
                    _loc_1.append(TooltipFactory.getInstance().createLargeMarketInfo(_loc_70));
                    break;
                }
                case "energy":
                {
                    _loc_71 = new Object();
                    _loc_71.type = TooltipType.ONE_ITEM;
                    _loc_71.title = ZLoc.t("Dialogs", "TT_Energy");
                    _loc_71.firstIcon =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_71.firstData = String(m_item.energyRewards);
                    _loc_72 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_71);
                    _loc_1.appendAll(_loc_72);
                    break;
                }
                case "goods":
                {
                    _loc_73 = new Object();
                    _loc_73.type = TooltipType.ONE_ITEM;
                    _loc_73.title = ZLoc.t("Dialogs", "TT_Goods");
                    _loc_73.firstIcon =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_73.firstData = String(m_item.goodsReward);
                    _loc_74 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_73);
                    _loc_1.appendAll(_loc_74);
                    break;
                }
                case "theme_collection":
                {
                    _loc_75 = !Global.world.isThemeCollectionEnabled(m_item);
                    _loc_76 = new Object();
                    _loc_76.type = TooltipType.DESCRIPTION;
                    _loc_76.ttbgWidth = 100;
                    _loc_76.content = ZLoc.t("Dialogs", "TT_ThemeCollection_" + m_item.name + "_" + (_loc_75 ? ("on") : ("off")));
                    _loc_77 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_76);
                    _loc_1.appendAll(_loc_77);
                    break;
                }
                case "landmark":
                {
                    _loc_78 = new Object();
                    _loc_79 = PopulationHelper.getItemPopulationIcon(m_item);
                    _loc_78.type = TooltipType.TWO_ITEMS;
                    _loc_78.title = ZLoc.t("Dialogs", "TT_Allows");
                    _loc_78.firstIcon =(DisplayObject) new _loc_79;
                    _loc_78.firstData = String(m_item.populationCapYield * Global.gameSettings().getNumber("populationScale", 1));
                    _loc_78.lastLine = PopulationHelper.getItemPopulationSubTitle(m_item);
                    _loc_80 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_78);
                    _loc_81 = new Object();
                    _loc_81.type = TooltipType.DESCRIPTION;
                    _loc_81.ttbgWidth = 100;
                    _loc_81.content = ZLoc.t("Dialogs", "TT_MuniRules");
                    _loc_1.appendAll(_loc_80);
                    break;
                }
                case "xgamegift":
                {
                    if (Global.player.level < m_item.requiredLevel)
                    {
                        _loc_115 = new Object();
                        _loc_115.type = TooltipType.REQUIREMENT;
                        _loc_115.content = ZLoc.t("Dialogs", "level_text", {amount:m_item.requiredLevel});
                        _loc_116 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_115);
                        _loc_1.append(_loc_116);
                    }
                    _loc_82 = new Object();
                    _loc_82.type = TooltipType.DESCRIPTION;
                    _loc_82.content = ZLoc.t("Items", m_item.name + "_toolTip");
                    _loc_83 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_82);
                    _loc_1.append(_loc_83);
                    break;
                }
                case "train":
                {
                    if (m_item.goods > 0)
                    {
                        _loc_84 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_85 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                        _loc_86 = m_item.goods;
                    }
                    else
                    {
                        _loc_85 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                        _loc_84 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                        _loc_86 = m_item.cost;
                    }
                    _loc_87 = {type:"oneItem", title:ZLoc.t("Dialogs", "TT_Costs"), firstIcon:_loc_84, firstData:_loc_86};
                    _loc_88 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_87);
                    _loc_89 = {type:"oneItem", title:ZLoc.t("Dialogs", "TT_Earns"), firstIcon:_loc_85, firstData:Global.gameSettings().getTieredInt(m_item.trainPayout, m_item.workers.amount)};
                    _loc_90 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_89);
                    _loc_91 = {type:"oneLine", title:ZLoc.t("Dialogs", "TT_StopsAvailable"), firstData:m_item.workers ? (m_item.workers.amount) : (0)};
                    _loc_92 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_91);
                    _loc_93 = {type:"oneLine", title:ZLoc.t("Dialogs", "TT_RoundTrip"), firstData:CardUtil.localizeTimeLeft(m_item.trainTripTime / 86400)};
                    _loc_94 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_93);
                    _loc_1.appendAll(_loc_88, _loc_90, _loc_92, _loc_94);
                    break;
                }
                case "play":
                {
                    _loc_95 = new Object();
                    _loc_95.type = TooltipType.DESCRIPTION;
                    _loc_95.content = ZLoc.t("Market", m_item.description + "_text");
                    _loc_95.ttbgWidth = 100;
                    _loc_96 = TooltipFactory.getInstance().createLargeMarketInfo(_loc_95);
                    _loc_1.appendAll(_loc_96);
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            _loc_2 = this.makeStatsPanelKeywords ();
            if (_loc_2 != null)
            {
                _loc_117 = new AssetPane(_loc_2);
                ASwingHelper.prepare(_loc_117);
                _loc_1.append(_loc_117);
            }
            return _loc_1;
        }//end

         protected void  unlockNeighbors (MouseEvent event )
        {
            FrameManager.showTray("invite.php?ref=market_click_neighbor_gated_item&action=unlockItem&name=" + m_item.name + "&requiredNeighbors=" + m_item.requiredNeighbors);
            return;
        }//end

        private static JLabel  createSaleLabel (int param1 )
        {
            _loc_2 = ZLoc.t("Market","SaleDiscount",{pct param1.toString ()});
            _loc_3 = EmbeddedArt.defaultFontNameBold;
            _loc_4 = ASwingHelper.shrinkFontSizeToFit(ICON_PANE_WIDTH ,_loc_2 ,_loc_3 ,14,null ,null ,null ,8);
            return ASwingHelper.makeLabel(_loc_2, _loc_3, _loc_4, EmbeddedArt.TEXT_SALE_COLOR, JLabel.LEFT);
        }//end

        private static JLabel  createSpecialsLabel ()
        {
            _loc_1 = ZLoc.t("Dialogs","specials_bogo_marketTag");
            _loc_2 = EmbeddedArt.defaultFontNameBold;
            _loc_3 = ASwingHelper.shrinkFontSizeToFit(ICON_PANE_WIDTH ,_loc_1 ,_loc_2 ,14,null ,null ,null ,8);
            return ASwingHelper.makeLabel(_loc_1, _loc_2, _loc_3, EmbeddedArt.TEXT_SALE_COLOR, JLabel.LEFT);
        }//end

    }



