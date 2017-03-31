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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;

    public class BusinessUpgradesDialogView extends GenericDialogView
    {
        protected MapResource m_building ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyHandler ;
        protected JPanel m_buttonsPanel ;
        protected JPanel m_progressBarPane ;
        protected JPanel m_progressBarBGPanel ;
        protected JLabel m_progressLabel ;
        protected CustomButton m_buyButton ;
        protected Array m_upgradeCells ;
        protected JPanel m_upgradeCellsContainer ;
        protected int m_buyBatchCount =0;
        protected int m_buyBatchSequence =0;
public static  int PROGRESS_BAR_WIDTH =420;
public static  int PROGRESS_BAR_MIN_WIDTH =12;
public static  int PROGRESS_TEXT_WIDTH =170;
public static  int MAX_LEVELS =3;
public static  int MAX_BATCH_COUNT =20;
public static  int MAX_BATCH_WAIT_TIME =5;

        public  BusinessUpgradesDialogView (MapResource param1 ,Function param2 ,Function param3 ,Function param4 )
        {
            this.m_upgradeCells = new Array();
            this.m_building = param1;
            this.m_finishCheckCB = param2;
            this.m_finishCB = param3;
            this.m_buyHandler = param4;
            super(null);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = UpgradesCommonAssets.bizupsAssetDict.get("dialogBG");
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
                ASwingHelper.setForcedWidth(this, m_bgAsset.width);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.append(createHeaderPanel());
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.createUpgradeCellsPanel());
            this.append(ASwingHelper.verticalStrut(15));
            this.append(this.createProgressPanel());
            this.append(ASwingHelper.verticalStrut(15));
            this.append(this.createHelpersPanel());
            this.append(ASwingHelper.verticalStrut(25));
            this.append(this.createButtonsPanel());
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            TextFormat _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (this.m_building && this.m_building.canCountUpgradeActions())
            {
                _loc_2 = Item.findUpgradeRoot(this.m_building.getItem());
                if (_loc_2)
                {
                    _loc_3 = ZLoc.t("Items", _loc_2 + "_friendlyName");
                    _loc_4 = ZLoc.t("Dialogs", "UpgradeX", {itemName:_loc_3});
                    m_titleFontSize = TextFieldUtil.getLocaleFontSize(27, 20, null);
                    _loc_4 = TextFieldUtil.formatSmallCapsString(_loc_4);
                    title = ASwingHelper.makeTextField(_loc_4, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
                    title.filters = EmbeddedArt.newtitleFilters;
                    _loc_5 = new TextFormat();
                    _loc_5.size = m_titleSmallCapsFontSize;
                    TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_5);
                    _loc_1.append(title);
                    title.getTextField().height = m_titleFontSize * 1.5;
                    ASwingHelper.setEasyBorder(_loc_1, 4);
                }
            }
            return _loc_1;
        }//end

        protected JPanel  createUpgradeCellsPanel ()
        {
            BusinessUpgradesInfoBox _loc_3 =null ;
            Item _loc_5 =null ;
            JPanel _loc_6 =null ;
            this.m_upgradeCellsContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 10);
            _loc_1 = Item.findUpgradeRoot(this.m_building.getItem());
            _loc_2 = UpgradeDefinition.getUpgradeChain(_loc_1);
            _loc_3 = new BusinessUpgradesInfoBox(UpgradesCommonAssets.bizupsAssetDict.get("currentCard"), _loc_1);
            this.m_upgradeCellsContainer.append(_loc_3);
            this.m_upgradeCells = new Array();
            this.m_upgradeCells.push(_loc_3);
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                _loc_5 = Global.gameSettings().getItemByName(_loc_2.get(_loc_4).newItemName);
                if (_loc_5.level <= this.m_building.getItem().level)
                {
                    _loc_3 = new BusinessUpgradesInfoBox(UpgradesCommonAssets.bizupsAssetDict.get("currentCard"), _loc_5.name);
                }
                else
                {
                    _loc_3 = new BusinessUpgradesInfoBox(UpgradesCommonAssets.bizupsAssetDict.get("upgradeCard"), _loc_5.name);
                }
                _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_6.append(new AssetPane(new UpgradesCommonAssets.bizupsAssetDict.get("arrow")));
                this.m_upgradeCellsContainer.append(_loc_6);
                this.m_upgradeCellsContainer.append(_loc_3);
                this.m_upgradeCells.push(_loc_3);
                _loc_4++;
            }
            return this.m_upgradeCellsContainer;
        }//end

        protected JPanel  createProgressPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,8);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,2);
            DisplayObject _loc_4 =(DisplayObject)new UpgradesCommonAssets.bizupsAssetDict.get( "upgradeBarBG");
            this.m_progressBarBGPanel = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            ASwingHelper.setBackground(this.m_progressBarBGPanel, _loc_4);
            this.m_progressBarBGPanel.setPreferredWidth(PROGRESS_BAR_WIDTH);
            DisplayObject _loc_5 =(DisplayObject)new UpgradesCommonAssets.bizupsAssetDict.get( "upgradeBar");
            this.m_progressBarPane = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            ASwingHelper.setBackground(this.m_progressBarPane, _loc_5);
            this.m_progressBarPane.setPreferredHeight(_loc_5.height);
            this.m_progressBarBGPanel.append(this.m_progressBarPane);
            this.updateProgressBar();
            _loc_3.append(this.m_progressBarBGPanel);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_6.setPreferredWidth(this.m_progressBarBGPanel.getPreferredWidth());
            _loc_7 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","BusinessUpgradesUI_cashHint"),this.m_progressBarBGPanel.getPreferredWidth (),EmbeddedArt.defaultFontNameBold ,TextFormatAlign.RIGHT ,16,EmbeddedArt.darkBlueTextColor );
            _loc_6.append(_loc_7);
            AssetIcon _loc_8 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            _loc_9 = UpgradesUtil.calculateCashCostPerUpgradeAction(this.m_building.getItem().commodityReq);
            this.m_buyButton = new CustomButton(_loc_9.toString(), _loc_8, "BigCashButtonUI");
            this.m_buyButton.addEventListener(MouseEvent.CLICK, this.onBuy, false, 0, true);
            ASwingHelper.setForcedWidth(this.m_buyButton, 100);
            _loc_6.appendAll(ASwingHelper.verticalStrut(5), this.m_buyButton);
            if (this.m_finishCheckCB())
            {
                this.m_buyButton.setEnabled(false);
            }
            _loc_3.append(_loc_6);
            _loc_2.append(_loc_3);
            DisplayObject _loc_10 =(DisplayObject)new UpgradesCommonAssets.bizupsAssetDict.get( "verticleRule");
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setBackground(_loc_11, _loc_10);
            _loc_11.setPreferredWidth(_loc_10.width);
            _loc_11.setPreferredHeight(_loc_2.getPreferredHeight());
            _loc_2.append(_loc_11);
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_progressLabel = ASwingHelper.makeLabel("", EmbeddedArt.titleFont, 30, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT);
            this.updateProgressText();
            _loc_13 = ASwingHelper.makeMultilineCapsText(ZLoc.t("Dialogs","BusinessUpgradesUI_action_text"),PROGRESS_TEXT_WIDTH ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,12,EmbeddedArt.blueTextColor );
            _loc_12.appendAll(this.m_progressLabel, ASwingHelper.verticalStrut(-8), _loc_13);
            _loc_2.append(_loc_12);
            DisplayObject _loc_14 =(DisplayObject)new UpgradesCommonAssets.bizupsAssetDict.get( "divider");
            _loc_1.setBackgroundDecorator(new MarginBackground(_loc_14, new Insets(0, 9, 0, 10)));
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(10));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected void  updateProgressBar ()
        {
            _loc_1 = this.m_building.upgradeActions.getTotal ();
            _loc_2 = (int)(this.m_building.getItem ().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS ));
            _loc_3 = Math.min(1,_loc_1 /_loc_2 );
            _loc_4 = _loc_1==0? (0) : (PROGRESS_BAR_MIN_WIDTH + Math.ceil(_loc_3 * (this.m_progressBarBGPanel.getPreferredWidth() - PROGRESS_BAR_MIN_WIDTH)));
            this.m_progressBarPane.setPreferredWidth(_loc_4);
            ASwingHelper.prepare(this.m_progressBarBGPanel);
            return;
        }//end

        protected void  updateProgressText ()
        {
            _loc_1 = this.m_building.upgradeActions.getTotal ();
            _loc_2 = (int)(this.m_building.getItem ().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS ));
            _loc_1 = Math.min(_loc_1, _loc_2);
            this.m_progressLabel.setText("" + _loc_1 + "/" + _loc_2);
            return;
        }//end

        protected JPanel  createHelpersPanel ()
        {
            JPanel _loc_10 =null ;
            String _loc_11 =null ;
            JPanel _loc_12 =null ;
            JLabel _loc_13 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            _loc_3 = this.m_building.upgradeActions.getFriendHelperIds ();
            _loc_4 = this.m_building.upgradeActions.getFriendHelperDefinition ();
            if (!this.m_building.upgradeActions.getFriendHelperDefinition())
            {
                return _loc_1;
            }
            _loc_5 = _loc_4.max ;
            _loc_6 = _loc_4.actionValue ;
            int _loc_7 =0;
            while (_loc_7 < _loc_5)
            {

                _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_11 = null;
                if (_loc_3.length > _loc_7)
                {
                    _loc_11 = _loc_3.get(_loc_7);
                }
                _loc_12 = this.makeImagePanel(_loc_11);
                _loc_10.append(_loc_12);
                _loc_13 = ASwingHelper.makeLabel("+" + _loc_6, EmbeddedArt.titleFont, 16, EmbeddedArt.greenTextColor, JLabel.CENTER);
                _loc_10.append(_loc_13);
                _loc_2.append(_loc_10);
                _loc_7++;
            }
            _loc_1.append(_loc_2);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_9 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","BusinessUpgradesUI_hint"),_loc_2.getPreferredWidth (),EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.darkBlueTextColor );
            _loc_8.append(_loc_9);
            _loc_1.append(_loc_8);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeImagePanel (String param1)
        {
            DisplayObject imageAsset ;
            AssetPane ap ;
            Player friend ;
            zid = param1;
            jp = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            imageAsset =(DisplayObject) new UpgradesCommonAssets.bizupsAssetDict.get("blankPortrait");
            if (zid)
            {
                friend = Global.player.findFriendById(zid);
                if (friend)
                {
                    LoadingManager .load (friend .snUser .picture ,void  (Event event )
            {
                imageAsset = event.target.content;
                ap.setAsset(imageAsset);
                return;
            }//end
            );
                }
            }
            ap = new AssetPane(imageAsset);
            jp.append(ap);
            return jp;
        }//end

        protected JPanel  createButtonsPanel ()
        {
            CustomButton _loc_1 =null ;
            CustomButton _loc_2 =null ;
            UpgradeHelperDefinition _loc_3 =null ;
            if (this.m_buttonsPanel)
            {
                this.m_buttonsPanel.removeAll();
            }
            else
            {
                this.m_buttonsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            }
            if (this.m_finishCheckCB())
            {
                _loc_1 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "FinishBuilding")), null, "BigGreenButtonUI");
                _loc_1.addEventListener(MouseEvent.CLICK, this.onFinish, false, 0, true);
                ASwingHelper.setForcedHeight(_loc_1, 45);
                this.m_buttonsPanel.append(_loc_1);
            }
            else
            {
                _loc_2 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "ValUI_askforhelp")), null, "BigGreenButtonUI");
                _loc_2.addEventListener(MouseEvent.CLICK, this.onFinish, false, 0, true);
                ASwingHelper.setForcedHeight(_loc_2, 45);
                this.m_buttonsPanel.append(_loc_2);
                _loc_3 = this.m_building.upgradeActions.getFriendHelperDefinition();
                if (!_loc_3 || this.m_building.upgradeActions.getFriendHelperIds().length >= _loc_3.max)
                {
                    _loc_2.setEnabled(false);
                }
            }
            ASwingHelper.prepare(this.m_buttonsPanel);
            return this.m_buttonsPanel;
        }//end

        protected void  onBuy (MouseEvent event )
        {
            String msg ;
            e = event;
            if (this.m_finishCheckCB())
            {
                return;
            }
            cashCost = UpgradesUtil.calculateCashCostPerUpgradeAction(this.m_building.getItem().commodityReq);
            if (!Global.player.getSeenSessionFlag("bizUpsCashBuy"))
            {
                msg = ZLoc.t("Dialogs", "BusinessUpgradesUI_cashQuestion", {amount:cashCost});
                UI .displayMessage (msg ,GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    Global.player.setSeenSessionFlag("bizUpsCashBuy");
                    onBuy(e);
                }
                return;
            }//end
            , "", true);
                return;
            }
            if (Global.player.canBuyCash(cashCost))
            {
                Global.player.cash = Global.player.cash - cashCost;
                this.m_building.incrementUpgradeActionCount();
                this.m_buyBatchCount++;
            }
            else
            {
                return;
            }
            if (this.m_buyBatchCount >= MAX_BATCH_COUNT)
            {
                this.sendBatchedBuyTransaction();
            }
            else
            {
                this.startBatchTimer();
            }
            this.updateProgressBar();
            this.updateProgressText();
            StatsManager.sample(100, StatsCounterType.DIALOG, "businessupgradesui", "buy_one", this.m_building.getItemName());
            if (this.m_finishCheckCB())
            {
                this.m_buyButton.setEnabled(false);
                this.createButtonsPanel();
            }
            return;
        }//end

        private void  startBatchTimer ()
        {
            int previousBatchCount ;
            int previousBatchSequence ;
            previousBatchCount = this.m_buyBatchCount;
            previousBatchSequence = this.m_buyBatchSequence;
            TimerUtil .callLater (void  ()
            {
                if (previousBatchCount != m_buyBatchCount || previousBatchSequence != m_buyBatchSequence || m_buyBatchCount == 0)
                {
                    return;
                }
                sendBatchedBuyTransaction();
                return;
            }//end
            , MAX_BATCH_WAIT_TIME * 1000);
            return;
        }//end

        private void  sendBatchedBuyTransaction ()
        {
            GameTransactionManager.addTransaction(new TBuyUpgradeAction(this.m_building, this.m_buyBatchCount));
            this.m_buyBatchCount = 0;
            this.m_buyBatchSequence++;
            return;
        }//end

        protected void  onFinish (MouseEvent event )
        {
            if (this.m_buyBatchCount > 0)
            {
                this.sendBatchedBuyTransaction();
            }
            this.m_finishCB(event);
            return;
        }//end

         protected void  closeMe ()
        {
            if (this.m_buyBatchCount > 0)
            {
                this.sendBatchedBuyTransaction();
            }
            super.closeMe();
            return;
        }//end

         public void  countDialogViewAction (String param1 ="view",boolean param2 =true ,int param3 =1,String param4 ="",String param5 ="",String param6 ="")
        {
            if (m_doStatsTrackingFunction == null || m_doStatsTrackingFunction == true)
            {
                return;
            }
            _loc_7 = m_doStatsTrackingFunction();
            if (!m_doStatsTrackingFunction())
            {
                return;
            }
            _loc_8 = m_getStatsTitleFunction();
            _loc_9 = GameUtil.trimBadStatsCharacters(param1);
            _loc_10 = this.m_building && this.m_building.hasOwnProperty("franchiseType") ? (this.m_building.get("franchiseType")) : ("");
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, _loc_8, _loc_9, _loc_10);
            if (param2)
            {
                m_acceptTextName = "";
                m_cancelTextName = "";
            }
            return;
        }//end

    }



