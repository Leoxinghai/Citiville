package Display.TrainUI;

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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.workers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class TrainStationDialogView extends GenericDialogView
    {
        protected Array m_items ;
        private JLabel m_txtTime ;
        private JLabel m_txtReward ;
        private JPanel m_scrollingListPanel ;
        private CustomButton m_button ;
        private TrainManager m_trainManager ;
        private CustomButton m_btnSpeedUp ;
        private CustomButton m_btnPurchaseStation ;
        private JPanel m_speedUpPanel ;
        private JPanel m_purchaseStationPanel ;
        private boolean m_grantedBuyPermissionForStationPurchase =false ;
        private boolean m_hasOverlay =false ;

        public  TrainStationDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            this.m_trainManager = Global.world.citySim.trainManager;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

        public boolean  hasOverlay ()
        {
            return this.m_hasOverlay;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("dialog_bg");
            this.makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            this.m_trainManager.addEventListener(TrainManagerEvent.TIME_UPDATE, this.timeUpdateHandler);
            this.m_trainManager.addEventListener(TrainManagerEvent.REWARD_UPDATE, this.rewardUpdateHandler);
            this.m_trainManager.addEventListener(TrainManagerEvent.TRAIN_STATE_UPDATE, this.trainStateUpdateHandler);
            this.m_trainManager.addEventListener(TrainManagerEvent.TRAIN_STATION_LIST_UPDATE, this.trainStationListUpdateHandler);
            return;
        }//end

         protected void  makeBackground ()
        {
            super.makeBackground();
            setPreferredWidth(m_bgAsset.width);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.append(ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "TrainUI_TrainStation_message"), 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 16, EmbeddedArt.brownTextColor));
            _loc_2 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_2.appendAll(this.createStationList(), this.createLogisticsPanel());
            ASwingHelper.setEasyBorder(_loc_2, 0, 10, 0, 10);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3.appendAll(createHeaderPanel(), ASwingHelper.verticalStrut(20), _loc_1, ASwingHelper.verticalStrut(20), _loc_2, ASwingHelper.verticalStrut(60), this.createButtonPanel());
            return _loc_3;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            this.m_button = new CustomButton(this.getGreenButtonStringBasedOnTrainState(), null, "GreenButtonUI");
            this.m_button.addActionListener(this.btnRequestStationClickHandler, 0, true);
            _loc_1.append(this.m_button);
            return _loc_1;
        }//end

        protected JPanel  createStationList ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(20,20,[{localesize"id",19.5locale},{"it",size21},locale{"tr",23});
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.makeTextField(ZLoc.t("Dialogs", "TrainUI_CurrentStations"), EmbeddedArt.titleFont, _loc_1, EmbeddedArt.blueTextColor));
            this.m_scrollingListPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_3.setPreferredWidth(460);
            _loc_3.appendAll(_loc_2, this.m_scrollingListPanel);
            this.updateScrollingList();
            return _loc_3;
        }//end

        private void  updateScrollingList ()
        {
            this.m_scrollingListPanel.removeAll();
            _loc_1 = this.m_trainManager.isWaitingForTrain ? (this.m_trainManager.trainStopData) : (this.m_trainManager.candidateTrainStopData);
            TrainStationScrollingList _loc_2 =new TrainStationScrollingList(_loc_1 ,0);
            ASwingHelper.prepare(_loc_2);
            this.m_scrollingListPanel.append(_loc_2);
            this.m_scrollingListPanel.setPreferredHeight(TrainStopCell.CELL_HEIGHT);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createLogisticsPanel ()
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            if (!this.m_trainManager.isWaitingForTrain)
            {
                _loc_2 = this.m_trainManager.purchaseCandidateItem.trainTripTime;
                _loc_3 = this.m_trainManager.purchaseCandidateItem.goods > 0 ? (TrainWorkers.OP_SELL) : (TrainWorkers.OP_BUY);
                _loc_4 = Global.gameSettings().getTieredInt(this.m_trainManager.purchaseCandidateItem.trainPayout, 0);
                _loc_5 = this.m_trainManager.purchaseCandidateItem.trainSpeedUpCost;
                _loc_6 = this.m_trainManager.purchaseCandidateItem.workers.cashCost;
            }
            else
            {
                _loc_2 = this.m_trainManager.trainArrivalTime;
                _loc_3 = this.m_trainManager.currentTrip.transactionType;
                _loc_4 = this.m_trainManager.trainRewardAmount;
                _loc_5 = this.m_trainManager.currentTrip.item.trainSpeedUpCost;
                _loc_6 = this.m_trainManager.currentTrip.item.workers.cashCost;
            }
            this.m_txtTime = ASwingHelper.makeLabel(GameUtil.formatMinutesSeconds(_loc_2), EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(24, 24, .get({locale:"ja", size:20})), EmbeddedArt.darkBlueTextColor);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_7.appendAll(new AssetPane(m_assetDict.get("clock")), this.m_txtTime);
            this.m_speedUpPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_txtReward = ASwingHelper.makeLabel(_loc_4.toString(), EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(24, 24, .get({locale:"ja", size:20})), EmbeddedArt.greenTextColor);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_8.appendAll(new AssetPane(_loc_3 == TrainWorkers.OP_BUY ? (m_assetDict.get("crate_big")) : (m_assetDict.get("coin_big"))), this.m_txtReward);
            this.m_btnSpeedUp = new CustomButton(ZLoc.t("Dialogs", "TrainUI_speedUp", {amount:_loc_5}), new AssetIcon(new EmbeddedArt.icon_cash()), "CashButtonUI");
            this.m_btnSpeedUp.addActionListener(this.btnSpeedUpClickHandler);
            this.m_speedUpPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.m_speedUpPanel.append(this.m_btnSpeedUp);
            this.m_btnPurchaseStation = new CustomButton(ZLoc.t("Dialogs", "TrainUI_purchaseStation", {amount:_loc_6}), new AssetIcon(new EmbeddedArt.icon_cash()), "CashButtonUI");
            this.m_btnPurchaseStation.addActionListener(this.btnPurchaseStationClickHandler);
            this.m_purchaseStationPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.m_purchaseStationPanel.append(this.m_btnPurchaseStation);
            _loc_1.appendAll(_loc_7, ASwingHelper.verticalStrut(10), this.m_speedUpPanel, ASwingHelper.verticalStrut(10), _loc_8, ASwingHelper.verticalStrut(10), this.m_purchaseStationPanel);
            this.updateButtonStatus();
            return _loc_1;
        }//end

        protected String  getGreenButtonStringBasedOnTrainState ()
        {
            String _loc_1 ="";
            if (this.m_trainManager.isWaitingForTrain)
            {
                if (this.m_trainManager.isTrainAwaitingClearance)
                {
                    _loc_1 = ZLoc.t("Dialogs", "TrainUI_ReceiveWaitingTrain");
                }
                else
                {
                    _loc_1 = ZLoc.t("Dialogs", "TrainUI_RequestStations");
                }
            }
            else
            {
                _loc_1 = ZLoc.t("Dialogs", "TrainUI_DispatchTrainAndRequestStations");
            }
            return _loc_1;
        }//end

        private void  updateButtonStatus ()
        {
            this.m_btnSpeedUp.setEnabled(this.m_trainManager.canSpeedUp);
            this.m_btnPurchaseStation.setEnabled(this.m_trainManager.canBuyNewStations);
            return;
        }//end

         protected void  closeMe ()
        {
            this.m_trainManager.removeEventListener(TrainManagerEvent.TIME_UPDATE, this.timeUpdateHandler);
            this.m_trainManager.removeEventListener(TrainManagerEvent.REWARD_UPDATE, this.rewardUpdateHandler);
            this.m_trainManager.removeEventListener(TrainManagerEvent.TRAIN_STATE_UPDATE, this.trainStateUpdateHandler);
            this.m_trainManager.removeEventListener(TrainManagerEvent.TRAIN_STATION_LIST_UPDATE, this.trainStationListUpdateHandler);
            super.closeMe();
            return;
        }//end

        private void  btnRequestStationClickHandler (Event event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_4 =null ;
            if (this.m_trainManager.isWaitingForTrain)
            {
                if (this.m_trainManager.isTrainAwaitingClearance)
                {
                    if (this.m_trainManager.currentTrip.transactionType == TrainWorkers.OP_BUY)
                    {
                        _loc_2 = Global.player.commodities.getSpareCapacity(Commodities.GOODS_COMMODITY);
                        _loc_3 = this.m_trainManager.currentTrip.commodityAmount;
                        if (_loc_2 < _loc_3)
                        {
                            _loc_4 = ZLoc.t("Dialogs", "TrainUI_confirmAcceptOverflowTrain", {amount:_loc_3 - _loc_2});
                            UI.displayMessage(_loc_4, GenericDialogView.TYPE_YESNO, this.grantClearanceConfirmClickHandler, "", true);
                            this.m_hasOverlay = true;
                        }
                        else
                        {
                            this.grantClearanceConfirmClickHandler(null);
                        }
                    }
                    else
                    {
                        this.grantClearanceConfirmClickHandler(null);
                    }
                }
                else
                {
                    this.m_trainManager.popRequestStationsFeed();
                }
            }
            else
            {
                this.m_trainManager.performSchedulePurchase();
                this.m_trainManager.popRequestStationsFeed();
            }
            this.closeMe();
            return;
        }//end

        private void  grantClearanceConfirmClickHandler (GenericPopupEvent event )
        {
            if (!event || event.button == GenericDialogView.YES)
            {
                this.m_trainManager.scrollToTrainStation();
                this.m_trainManager.grantTrainClearance();
            }
            return;
        }//end

        private void  btnSpeedUpClickHandler (Event event )
        {
            _loc_2 = ZLoc.t("Dialogs","TrainUI_confirmPurchaseSpeedUp",{amount this.m_trainManager.currentTrip.item.trainSpeedUpCost });
            UI.displayMessage(_loc_2, GenericDialogView.TYPE_YESNO, this.speedUpConfirmClickHandler, "", true);
            this.m_hasOverlay = true;
            return;
        }//end

        private void  speedUpConfirmClickHandler (GenericPopupEvent event )
        {
            this.m_hasOverlay = false;
            if (event.button == GenericDialogView.YES)
            {
                this.m_trainManager.speedUpCurrentTrain();
            }
            return;
        }//end

        private void  btnPurchaseStationClickHandler (Event event )
        {
            String _loc_2 =null ;
            this.m_hasOverlay = false;
            if (this.m_grantedBuyPermissionForStationPurchase)
            {
                this.m_trainManager.purchaseNewStation();
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "TrainUI_confirmPurchaseStation", {amount:this.m_trainManager.currentTrip.item.workers.cashCost});
                UI.displayMessage(_loc_2, GenericDialogView.TYPE_YESNO, this.purchaseStationConfirmClickHandler, "", true);
                this.m_hasOverlay = true;
            }
            return;
        }//end

        private void  purchaseStationConfirmClickHandler (GenericPopupEvent event )
        {
            this.m_hasOverlay = false;
            if (event.button == GenericDialogView.YES)
            {
                this.m_trainManager.purchaseNewStation();
                this.m_grantedBuyPermissionForStationPurchase = true;
            }
            return;
        }//end

        private void  timeUpdateHandler (TrainManagerEvent event )
        {
            this.m_txtTime.setText(GameUtil.formatMinutesSeconds(this.m_trainManager.trainArrivalTime));
            this.updateButtonStatus();
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  rewardUpdateHandler (TrainManagerEvent event )
        {
            this.m_txtReward.setText(this.m_trainManager.trainRewardAmount.toString());
            this.updateButtonStatus();
            return;
        }//end

        private void  trainStateUpdateHandler (TrainManagerEvent event )
        {
            this.m_button.setText(this.getGreenButtonStringBasedOnTrainState());
            this.updateButtonStatus();
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  trainStationListUpdateHandler (TrainManagerEvent event )
        {
            this.m_txtReward.setText(this.m_trainManager.trainRewardAmount.toString());
            this.updateScrollingList();
            return;
        }//end

    }



