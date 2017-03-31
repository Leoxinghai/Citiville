package Modules.hotels;

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
import Classes.orders.Hotel.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.Transactions.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class HotelsGuestDialogView extends GenericDialogView
    {
        protected MechanicMapResource m_owner ;
        protected ICheckInHandler m_checkinHandler ;
        protected Function m_upgradeCallback ;
        protected JPanel m_scrollViewportContents ;
        protected JViewport m_scrollViewport ;
        protected JPanel m_roomPreviewContentPanel ;
        protected JPanel m_PossibleRewardsContentPanel ;
        protected JPanel m_SpeechPanel ;
        protected JPanel m_MainPanel ;
        protected JPanel m_RewardPanel ;
        protected JPanel m_PossibleRewards ;
        protected Dictionary m_rollOverGuide ;
        protected Dictionary m_upgradeGuide ;
        protected Dictionary m_floorPanels ;
        protected Dictionary m_floorIndicators ;
        protected AssetPane m_hotelText ;
        protected AssetPane roomPane ;
        protected int previewFloorID =-1;
        protected Array floorRewards ;
        protected int previewFloorRewardIndex =-1;
        protected Timer rewardRotationTimer ;
        protected Timer flashUpgradeTimer ;
        protected boolean givenReward =false ;
        protected int m_upgradeFloor =0;
        protected Sprite m_litWindow ;
        protected Array m_flashData ;
        protected int m_flashCursor =0;
        protected boolean m_doingUpgradeFlashing =false ;
        protected CustomButton m_vipButton ;
        public static  int SCROLL_AMOUNT =20;
        public static  int TITLE_TEXT_WIDTH =155;
        public static  int ROW_MINIMUM =3;

        public  HotelsGuestDialogView (Dictionary param1 ,MechanicMapResource param2 ,Function param3 ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="",boolean param13 =true )
        {
            this.m_rollOverGuide = new Dictionary();
            this.m_upgradeGuide = new Dictionary();
            this.m_floorPanels = new Dictionary();
            this.m_floorIndicators = new Dictionary();
            this.floorRewards = new Array();
            this.m_flashData = new Array();
            this.m_owner = param2;
            this.m_checkinHandler =(ICheckInHandler) this.m_owner;
            this.m_upgradeCallback = param3;
            if (this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting(), Global.player.uid) != this.m_checkinHandler.defaultCheckInFloor)
            {
                this.givenReward = true;
            }
            super(param1, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13);
            this.rewardRotationTimer = new Timer(500);
            this.rewardRotationTimer.addEventListener(TimerEvent.TIMER, this.rewardRotationTimerTick);
            this.flashUpgradeTimer = new Timer(1000);
            this.flashUpgradeTimer.addEventListener(TimerEvent.TIMER, this.flashUpgradeTimerTick);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "view", this.m_owner.getItemName(), "visitor_panel_ui", "view_panel");
            return;
        }//end

        public String  getLocKeyBase (String param1 )
        {
            _loc_2 = this.m_owner.getItem ().getUI_skin_param("base_loc_override");
            if (_loc_2)
            {
                return _loc_2 + "_" + param1;
            }
            return "Hotel_" + param1;
        }//end

        public String  getLocKeyBaseDialog (String param1 )
        {
            _loc_2 = this.m_owner.getItem ().getUI_skin_param("base_dialog_loc_override");
            if (_loc_2)
            {
                return _loc_2 + "_" + param1;
            }
            return "HotelDialog_" + param1;
        }//end

        public String  getLocKeyGuestDialog (String param1 )
        {
            _loc_2 = this.m_owner.getItem ().getUI_skin_param("guest_dialog_loc_override");
            if (_loc_2)
            {
                return _loc_2 + "_" + param1;
            }
            return "HotelGuestDialog_" + param1;
        }//end

        private void  resolveSpriteDisable (Sprite param1 )
        {
            if (param1 !=null)
            {
                if (param1.parent != null)
                {
                    this.removeChild(param1);
                }
            }
            return;
        }//end

         protected void  closeMe ()
        {
            super.closeMe();
            this.resolveSpriteDisable(this.m_litWindow);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.setPreferredWidth(732);
            this.setPreferredHeight(520);
            ASwingHelper.setBackground(this, m_bgAsset);
            this.addChild(new assetDict.get("hotels_npc_visitor"));
            this.append(createHeaderPanel());
            this.m_SpeechPanel = ASwingHelper.makeSoftBoxJPanel();
            this.createSpeechDialog();
            this.append(this.m_SpeechPanel);
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            this.m_RewardPanel = ASwingHelper.makeSoftBoxJPanel();
            this.createRewardPanel();
            _loc_1.append(this.m_RewardPanel);
            this.m_RewardPanel = ASwingHelper.makeSoftBoxJPanel();
            _loc_1.append(this.createScrollPanel());
            this.append(_loc_1);
            this.createHotel();
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "hotels_floorLock");
            this.roomPane = new AssetPane(_loc_2, AssetPane.PREFER_SIZE_IMAGE);
            ASwingHelper.setEasyBorder(this.roomPane, 20, 50);
            this.m_roomPreviewContentPanel.append(this.roomPane);
            _loc_2 =(DisplayObject) new assetDict.get("hotels_floorLock");
            AssetPane _loc_3 =new AssetPane(_loc_2 ,AssetPane.PREFER_SIZE_IMAGE );
            ASwingHelper.setEasyBorder(_loc_3, 20, 50);
            this.m_PossibleRewardsContentPanel.append(_loc_3);
            if (this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting(), Global.player.uid) == this.m_checkinHandler.defaultCheckInFloor)
            {
                this.showFloor(this.m_checkinHandler.defaultCheckInFloor);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_titleString != "")
            {
                _loc_2 = m_titleString;
                title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
                title.filters = EmbeddedArt.toolTipNormalTitleFilters;
                _loc_3 = new TextFormat();
                _loc_3.size = m_titleSmallCapsFontSize;
                TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
                _loc_1.append(title);
                title.getTextField().height = m_titleFontSize * 1.5;
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        protected void  createSpeechDialog ()
        {
            DisplayObject _loc_1 =(DisplayObject)new assetDict.get( "hotels_npcMessageBubble");
            ASwingHelper.setBackground(this.m_SpeechPanel, _loc_1);
            ASwingHelper.setEasyBorder(this.m_SpeechPanel, 0, 160, 0, 50);
            this.m_hotelText = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", this.getLocKeyGuestDialog("greetingDialog")), 495, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 18, EmbeddedArt.brownTextColor);
            ASwingHelper.setEasyBorder(this.m_hotelText, 0, 20, 20);
            this.m_hotelText.setMinimumHeight(100);
            this.m_hotelText.setMaximumHeight(100);
            this.m_hotelText.setPreferredHeight(100);
            this.m_SpeechPanel.append(this.m_hotelText);
            return;
        }//end

        protected void  setupPossibleRewardsPanel ()
        {
            if (this.m_PossibleRewards)
            {
                this.m_RewardPanel.remove(this.m_PossibleRewards);
            }
            this.m_PossibleRewards = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            ASwingHelper.setEasyBorder(this.m_PossibleRewards, 0, 12, 15, 12);
            this.m_PossibleRewards.setPreferredHeight(150);
            DisplayObject _loc_1 =(DisplayObject)new assetDict.get( "hotels_previewBoxesGradient");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(10));
            this.m_PossibleRewards.setBackgroundDecorator(_loc_2);
            _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.getLocKeyGuestDialog("reward")),TITLE_TEXT_WIDTH ,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,16,EmbeddedArt.titleColor );
            _loc_3.filters = EmbeddedArt.toolTipNormalTitleFilters;
            ASwingHelper.setEasyBorder(_loc_3, 0, 10);
            this.m_PossibleRewards.append(_loc_3);
            this.m_PossibleRewardsContentPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_PossibleRewardsContentPanel.setPreferredHeight(100);
            this.m_PossibleRewards.append(this.m_PossibleRewardsContentPanel);
            this.m_RewardPanel.append(this.m_PossibleRewards);
            return;
        }//end

        protected void  createRewardPanel ()
        {
            this.m_RewardPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            DisplayObject _loc_1 =(DisplayObject)new assetDict.get( "hotels_previewBackground");
            ASwingHelper.setBackground(this.m_RewardPanel, _loc_1);
            this.m_RewardPanel.setPreferredWidth(225);
            ASwingHelper.setEasyBorder(this.m_RewardPanel, 50, 15, 0, 20);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ASwingHelper.setEasyBorder(_loc_2, 0, 12, 12, 12);
            _loc_2.setPreferredHeight(150);
            DisplayObject _loc_3 =(DisplayObject)new assetDict.get( "hotels_previewBoxes");
            MarginBackground _loc_4 =new MarginBackground(_loc_3 ,new Insets(10));
            _loc_2.setBackgroundDecorator(_loc_4);
            this.m_RewardPanel.append(_loc_2);
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.getLocKeyGuestDialog("roomPreview")),TITLE_TEXT_WIDTH ,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,16,EmbeddedArt.titleColor );
            _loc_5.filters = EmbeddedArt.toolTipNormalTitleFilters;
            ASwingHelper.setEasyBorder(_loc_5, 0, 10);
            _loc_2.append(_loc_5);
            this.m_roomPreviewContentPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_roomPreviewContentPanel.setPreferredHeight(120);
            _loc_2.append(this.m_roomPreviewContentPanel);
            this.setupPossibleRewardsPanel();
            return;
        }//end

        protected JPanel  createScrollPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-15);
            ASwingHelper.setEasyBorder(_loc_1, 15);
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "hotels_hotels_15");
            ASwingHelper.setBackground(_loc_1, _loc_2);
            _loc_3 = this.m_owner.getItem ().hotel.floors.length ;
            _loc_4 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.getLocKeyGuestDialog("chooseFloorTitle"),{amount _loc_3.toString ()}),450,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,18,EmbeddedArt.brownTextColor );
            ASwingHelper.setEasyBorder(_loc_4, 5, 5);
            _loc_1.append(_loc_4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_6 =(DisplayObject)new assetDict.get( "hotels_scrollAreaTop");
            ASwingHelper.setEasyBorder(_loc_5, 15, 2, 0, -43);
            _loc_5.setPreferredHeight(40);
            ASwingHelper.setBackground(_loc_5, _loc_6);
            _loc_1.append(_loc_5);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            ASwingHelper.setEasyBorder(_loc_7, 0, 0, 6);
            this.m_scrollViewport = new JViewport(ASwingHelper.makeSoftBoxJPanelVertical());
            this.m_scrollViewport.setPreferredHeight(300);
            this.m_scrollViewport.setPreferredWidth(420);
            this.m_scrollViewportContents = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_scrollViewport.append(this.m_scrollViewportContents);
            _loc_7.append(this.m_scrollViewport);
            _loc_8 = this.makeNavPanel ();
            ASwingHelper.setEasyBorder(_loc_8, 0, 0, 5);
            _loc_7.append(_loc_8);
            _loc_1.append(_loc_7);
            return _loc_1;
        }//end

        protected void  createHotel ()
        {
            this.buildHotelFloor(0);
            _loc_1 = this.m_owner.getItem ().hotel.floors.length -1;
            while (_loc_1 > 0)
            {

                this.buildHotelFloor(_loc_1);
                _loc_1 = _loc_1 - 1;
            }
            return;
        }//end

        protected void  setupControlForRolloverEvents (Object param1 ,int param2 )
        {
            param1.addEventListener(MouseEvent.ROLL_OVER, this.onMouseOver, false, 0, true);
            param1.addEventListener(MouseEvent.ROLL_OUT, this.onMouseOut, false, 0, true);
            this.m_rollOverGuide.put(param1,  param2);
            return;
        }//end

        protected void  buildHotelFloor (int param1 )
        {
            DisplayObject _loc_3 =null ;
            AssetPane _loc_4 =null ;
            CustomButton _loc_6 =null ;
            int _loc_8 =0;
            AssetPane _loc_9 =null ;
            AssetPane _loc_10 =null ;
            AssetIcon _loc_11 =null ;
            int _loc_12 =0;
            AssetPane _loc_13 =null ;
            AssetIcon _loc_14 =null ;
            int _loc_15 =0;
            int _loc_16 =0;
            _loc_2 = this.m_owner.getItem ().hotel ;
            String _loc_5 ="";
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            if (param1 == 0)
            {
                _loc_3 =(DisplayObject) new m_assetDict.get("hotels_topFloor");
            }
            else if (param1 == 1)
            {
                _loc_3 =(DisplayObject) new m_assetDict.get("hotels_bottomFloor");
            }
            else
            {
                _loc_3 =(DisplayObject) new m_assetDict.get("hotels_midFloors");
            }
            if (param1 == 0)
            {
                _loc_4 = new AssetPane(new m_assetDict.get("hotels_penthouseStar"));
            }
            else
            {
                _loc_8 = param1;
                _loc_4 = ASwingHelper.makeMultilineText(_loc_8.toString(), 50, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 48, 13165299);
            }
            if (this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting(), Global.player.uid) == param1)
            {
                _loc_5 = ZLoc.t("Dialogs", this.getLocKeyGuestDialog("checkedIn"));
                _loc_9 = new AssetPane(new m_assetDict.get("payroll_checkin_confirmCheckmark"));
                ASwingHelper.setEasyBorder(_loc_9, 0, 0, 10, 10);
                _loc_7.append(_loc_9);
                this.setupControlForRolloverEvents(_loc_9, param1);
                if (param1 != this.m_checkinHandler.defaultCheckInFloor)
                {
                    this.givenReward = true;
                    this.overrideRewardIndex();
                }
            }
            else if (param1 == 0)
            {
                _loc_10 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "UpgradeLabelText"), 120, EmbeddedArt.defaultFontNameBold, TextFormatAlign.RIGHT, 12, EmbeddedArt.darkBlueTextColor);
                _loc_7.append(_loc_10);
                if (this.m_checkinHandler.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) != HotelGuest.VIP_GRANTED)
                {
                    _loc_11 = new AssetIcon(new EmbeddedArt.icon_cash());
                    _loc_12 = _loc_2.floors.get(param1).upgradeCash;
                    _loc_6 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:_loc_12.toString()})), _loc_11, "CashButtonUI");
                    ASwingHelper.setEasyBorder(_loc_6, 0, 0, 0, 10);
                    _loc_6.addActionListener(this.onBuyVIPSuite);
                    if (!this.m_checkinHandler.isRoomOnFloor(param1))
                    {
                        _loc_6.setEnabled(false);
                    }
                    _loc_7.append(_loc_6);
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("requestVIP")), null, "OrangeSmallButtonUI");
                    ASwingHelper.setEasyBorder(this.m_vipButton, 0, 0, 10, 10);
                    _loc_7.append(this.m_vipButton);
                    this.m_vipButton.addActionListener(this.onRequestVIP);
                    if (this.m_checkinHandler.getGuestVIPStatus(Global.getVisiting(), Global.player.uid) == HotelGuest.VIP_REQUESTED)
                    {
                        this.m_vipButton.setEnabled(false);
                    }
                    if (!this.m_checkinHandler.isRoomOnFloor(param1))
                    {
                        this.m_vipButton.setEnabled(false);
                    }
                }
                else
                {
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("acceptVIP")), null, "OrangeSmallButtonUI");
                    ASwingHelper.setEasyBorder(this.m_vipButton, 0, 0, 0, 10);
                    _loc_7.append(this.m_vipButton);
                    this.m_vipButton.addActionListener(this.onAcceptVIP);
                    if (!this.m_checkinHandler.isRoomOnFloor(param1))
                    {
                        this.m_vipButton.setEnabled(false);
                    }
                }
            }
            else if (this.m_checkinHandler.hasVisitorCheckedIntoDefaultFloor(Global.getVisiting(), Global.player.uid))
            {
                _loc_13 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "UpgradeLabelText"), 120, EmbeddedArt.defaultFontNameBold, TextFormatAlign.RIGHT, 12, EmbeddedArt.darkBlueTextColor);
                if (this.m_checkinHandler.hasVisitorCheckedIntoDefaultFloor(Global.getVisiting(), Global.player.uid))
                {
                    _loc_7.append(_loc_13);
                    _loc_14 = new AssetIcon(new EmbeddedArt.icon_coin());
                    _loc_15 = _loc_2.floors.get(param1).upgradeCost;
                    _loc_6 = new CustomButton(_loc_15.toString(), _loc_14, "GreenSmallButtonUI");
                    _loc_6.addActionListener(this.onUpgrade);
                    if (!this.m_checkinHandler.isRoomOnFloor(param1))
                    {
                        _loc_6.setEnabled(false);
                    }
                    this.m_upgradeGuide.put(_loc_6,  param1);
                    ASwingHelper.setEasyBorder(_loc_6, 0, 0, 10, 10);
                    _loc_7.append(_loc_6);
                }
            }
            if (_loc_5.length == 0)
            {
                _loc_16 = _loc_2.floors.get(param1).roomCount - this.m_checkinHandler.getHotelVisitorCountForFloor(param1);
                if (param1 == 0)
                {
                    _loc_5 = ZLoc.t("Dialogs", this.getLocKeyGuestDialog("suitesLeft"), {amount:_loc_16.toString()});
                }
                else
                {
                    _loc_5 = ZLoc.t("Dialogs", this.getLocKeyGuestDialog("roomsLeft"), {amount:_loc_16.toString()});
                }
            }
            this.setupControlForRolloverEvents(_loc_3, param1);
            this.setupControlForRolloverEvents(_loc_7, param1);
            this.setupControlForRolloverEvents(_loc_4, param1);
            if (_loc_6)
            {
                this.setupControlForRolloverEvents(_loc_6, param1);
            }
            this.appendHotelFloor(_loc_3, _loc_5, _loc_4, _loc_7, param1);
            return;
        }//end

        protected void  appendHotelFloor (DisplayObject param1 ,String param2 ,AssetPane param3 ,JPanel param4 ,int param5 )
        {
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            AssetPane _loc_7 =new AssetPane(param1 ,AssetPane.PREFER_SIZE_IMAGE );
            _loc_6.append(_loc_7);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ASwingHelper.setBackground(_loc_8, new m_assetDict.get("hotels_floorUnlocked"));
            _loc_6.append(_loc_8);
            _loc_9 = ASwingHelper.makeMultilineText(param2 ,260,EmbeddedArt.titleFont ,TextFormatAlign.LEFT ,12,EmbeddedArt.darkBlueTextColor );
            ASwingHelper.setEasyBorder(_loc_9, 0, 20);
            _loc_8.append(_loc_9);
            JPanel _loc_10 =new JPanel(new BorderLayout ());
            if (param3 != null)
            {
                ASwingHelper.setEasyBorder(param3, 0, 20);
                _loc_10.append(param3, BorderLayout.WEST);
            }
            _loc_10.append(param4, BorderLayout.EAST);
            _loc_8.append(_loc_10);
            this.m_floorIndicators.put(param5,  _loc_8);
            this.m_scrollViewportContents.append(_loc_6);
            this.setupControlForRolloverEvents(_loc_7, param5);
            this.setupControlForRolloverEvents(_loc_6, param5);
            this.setupControlForRolloverEvents(_loc_9, param5);
            this.setupControlForRolloverEvents(_loc_10, param5);
            this.m_floorPanels.put(param5,  _loc_6);
            return;
        }//end

        protected JPanel  makeNavPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "pic_dot");
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "btn_up_normal");
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "btn_up_down");
            DisplayObject _loc_5 =(DisplayObject)new m_assetDict.get( "btn_up_over");
            DisplayObject _loc_6 =(DisplayObject)new m_assetDict.get( "btn_down_normal");
            DisplayObject _loc_7 =(DisplayObject)new m_assetDict.get( "btn_down_down");
            DisplayObject _loc_8 =(DisplayObject)new m_assetDict.get( "btn_down_over");
            JButton _loc_9 =new JButton ();
            _loc_9.wrapSimpleButton(new SimpleButton(_loc_3, _loc_5, _loc_4, _loc_3));
            _loc_9.addActionListener(this.moveUp, 0, true);
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_11 =new AssetPane(_loc_2 );
            _loc_10.append(_loc_11);
            JButton _loc_12 =new JButton ();
            _loc_12.wrapSimpleButton(new SimpleButton(_loc_6, _loc_8, _loc_7, _loc_6));
            _loc_12.addActionListener(this.moveDown, 0, true);
            if (this.m_owner.getItem().hotel.floors.length <= ROW_MINIMUM)
            {
                _loc_9.setEnabled(false);
                _loc_12.setEnabled(false);
            }
            _loc_1.appendAll(_loc_9, ASwingHelper.verticalStrut(50), _loc_10, ASwingHelper.verticalStrut(50), _loc_12);
            MarginBackground _loc_13 =new MarginBackground(new m_assetDict.get( "vertical_scrollBar_border") );
            _loc_1.setBackgroundDecorator(_loc_13);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected void  clearWindows ()
        {
            if (this.m_litWindow == null)
            {
                return;
            }
            this.m_litWindow.graphics.clear();
            return;
        }//end

        protected void  drawSquareWindow (Rectangle param1 )
        {
            double _loc_3 =0;
            boolean _loc_2 =false ;
            if (!this.m_litWindow)
            {
                this.m_litWindow = new Sprite();
                _loc_2 = true;
            }
            if (this.m_owner.getItem().getUI_skin_param("bonus_image_override"))
            {
                this.m_litWindow.x = param1.x;
                this.m_litWindow.y = param1.y;
                this.m_litWindow.width = param1.width;
                this.m_litWindow.height = param1.height;
                this.m_litWindow.addChild(new m_assetDict.get("hotels_bonusImage"));
            }
            else
            {
                _loc_3 = param1.y;
                _loc_3 = _loc_3 + this.m_scrollViewportContents.y;
                this.m_litWindow.graphics.beginFill(16776960, 0.5);
                this.m_litWindow.graphics.drawRect(param1.x, _loc_3, param1.width, param1.height);
                this.m_litWindow.graphics.endFill();
                this.m_litWindow.alpha = 1;
            }
            if (_loc_2)
            {
                this.addChild(this.m_litWindow);
            }
            return;
        }//end

        protected void  upgradeGuest_Start (int param1 )
        {
            int _loc_8 =0;
            int _loc_9 =0;
            HotelUpgradeFlashData _loc_10 =null ;
            this.m_upgradeFloor = param1;
            this.m_doingUpgradeFlashing = true;
            this.m_flashData.splice(0, this.m_flashData.length());
            _loc_2 = Utilities.randBetween(20,40);
            _loc_3 = Utilities.randBetween(0,(this.m_owner.getItem().hotel.getFloor(this.m_upgradeFloor).windows.length-1));
            _loc_4 = this.m_owner.getItem ().hotel.getFloor(this.m_upgradeFloor ).windows.length ;
            this.floorRewards = this.m_checkinHandler.getAllRewardDooberDataForFloor(this.m_upgradeFloor);
            _loc_5 = this.floorRewards.length ;
            int _loc_6 =-1;
            int _loc_7 =0;
            while (_loc_7 < _loc_2)
            {

                _loc_8 = Utilities.randBetween(0, (_loc_5 - 1));
                while (_loc_8 == _loc_6)
                {

                    _loc_8 = Utilities.randBetween(0, (_loc_5 - 1));
                }
                _loc_6 = _loc_8;
                _loc_9 = 100;
                if (_loc_7 > _loc_2 * 0.5)
                {
                    _loc_9 = 250;
                    if (_loc_7 > _loc_2 * 0.75)
                    {
                        _loc_9 = 500;
                        if (_loc_7 > _loc_2 * 0.9)
                        {
                            _loc_9 = 1000;
                        }
                    }
                }
                _loc_10 = new HotelUpgradeFlashData(_loc_9, _loc_3, _loc_8);
                this.m_flashData.push(_loc_10);
                _loc_3++;
                if (_loc_3 >= _loc_4)
                {
                    _loc_3 = 0;
                }
                _loc_7++;
            }
            this.m_flashCursor = 0;
            this.rewardRotationTimer.stop();
            this.showFlash();
            return;
        }//end

        protected void  flashUpgradeTimerTick (TimerEvent event )
        {
            this.showFlash();
            return;
        }//end

        protected void  showFlash ()
        {
            _loc_1 = this.m_flashData.get(this.m_flashCursor) ;
            this.clearWindows();
            this.drawSquareWindow(this.m_owner.getItem().hotel.getFloor(this.m_upgradeFloor).windows.get(_loc_1.window));
            this.previewFloorRewardIndex = _loc_1.prizeIndex;
            this.showRewardImage();
            this.m_flashCursor++;
            if (this.m_flashCursor >= this.m_flashData.length())
            {
                this.flashUpgradeTimer.stop();
                this.upgradeGuest_End();
                return;
            }
            this.flashUpgradeTimer.delay = _loc_1.timeDelay;
            this.flashUpgradeTimer.start();
            return;
        }//end

        protected void  upgradeGuest_End ()
        {
            _loc_1 = this.m_upgradeFloor ;
            if (!this.m_checkinHandler.giveGuestRewards(_loc_1))
            {
                return;
            }
            _loc_2 = RewardMechanic.rewardIndex;
            if (_loc_2 == -1)
            {
                return;
            }
            if (!this.m_upgradeCallback(this.m_checkinHandler, Global.getVisiting(), Global.player.uid, _loc_1, _loc_2))
            {
                return;
            }
            this.m_checkinHandler.setGuestVIPStatus(Global.getVisiting(), Global.player.uid, HotelGuest.VIP_ACCEPTED);
            this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "hotelVisitCheckedIn", true, "all"));
            this.givenReward = true;
            this.flashUpgradeTimer.stop();
            this.loadRewardImages(_loc_1);
            this.m_doingUpgradeFlashing = false;
            this.showFloor(_loc_1, false);
            this.overrideRewardIndex();
            this.m_scrollViewportContents.removeAll();
            this.createHotel();
            this.m_checkinHandler.updateSparkleEffect();
            return;
        }//end

        protected void  onUpgrade (AWEvent event )
        {
            CustomButton _loc_4 =null ;
            if (this.m_doingUpgradeFlashing)
            {
                return;
            }
            if (this.givenReward)
            {
                return;
            }
            _loc_2 = this.m_owner.getItem ().hotel ;
            _loc_3 = this.m_upgradeGuide.get(event.target) ;
            if (_loc_2.floors.get(_loc_3).upgradeCost <= Global.player.gold)
            {
                _loc_4 =(CustomButton) event.target;
                _loc_4.setEnabled(false);
                this.upgradeGuest_Start(_loc_3);
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_COINS);
            }
            return;
        }//end

        protected void  onBuyVIPSuite (AWEvent event )
        {
            CustomButton _loc_3 =null ;
            _loc_2 = this.m_owner.getItem ().hotel ;
            if (_loc_2.floors.get(0).upgradeCash <= Global.player.cash)
            {
                _loc_3 =(CustomButton) event.target;
                _loc_3.setEnabled(false);
                this.upgradeGuest_Start(0);
            }
            else
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            }
            return;
        }//end

        protected void  onRequestVIP (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "visitor_panel_ui", "request_vip");
            Global.world.viralMgr.sendHotelVIPRequest(this.m_owner.getId());
            this.m_checkinHandler.setGuestVIPStatus(Global.getVisiting(), Global.player.uid, HotelGuest.VIP_REQUESTED);
            this.m_vipButton.setEnabled(false);
            this.m_owner.trackSocialAction(StatsCounterType.SOCIAL_VERB_VISIT_NEIGHBOR, this.m_owner.getItemName(), TrackedActionType.HOTEL_VIPREQUEST);
            this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, "hotelVisitCheckedIn", true, "all"));
            return;
        }//end

        protected void  onAcceptVIP (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "visitor_panel_ui", "accept_vip");
            GameTransactionManager.addTransaction(new TMechanicAction(this.m_owner, "hotelVisitClosed", "GMVisit", {operation:"acceptVIP", ownerId:Global.getVisiting(), hotelId:this.m_owner.getId()}));
            this.m_owner.trackSocialAction(StatsCounterType.SOCIAL_VERB_VISIT_NEIGHBOR, this.m_owner.getItemName(), TrackedActionType.HOTEL_VIPACCEPT);
            this.m_checkinHandler.setGuestVIPStatus(Global.getVisiting(), Global.player.uid, HotelGuest.VIP_ACCEPTED);
            this.m_vipButton.setEnabled(false);
            this.upgradeGuest_Start(0);
            return;
        }//end

        protected void  moveUp (AWEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_doingUpgradeFlashing)
            {
                return;
            }
            this.m_scrollViewport.scrollVertical(-SCROLL_AMOUNT);
            return;
        }//end

        protected void  moveDown (AWEvent event =null )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            if (this.m_doingUpgradeFlashing)
            {
                return;
            }
            this.m_scrollViewport.scrollVertical(SCROLL_AMOUNT);
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            boolean _loc_2 =false ;
            _loc_3 = (Int)(this.m_rollOverGuide.get(event.currentTarget) );
            _loc_4 = this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting (),Global.player.uid );
            if (this.m_doingUpgradeFlashing)
            {
                return;
            }
            if (_loc_3 == 0)
            {
                _loc_2 = true;
            }
            if (_loc_4 == this.m_checkinHandler.defaultCheckInFloor)
            {
                _loc_2 = true;
            }
            if (_loc_2)
            {
                this.showFloor(_loc_3);
            }
            else
            {
                this.loadRewardImages(this.previewFloorID);
                this.overrideRewardIndex();
            }
            return;
        }//end

        protected void  showFloor (int param1 ,boolean param2 =true )
        {
            JPanel _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            DisplayObject _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            if (param1 == this.previewFloorID)
            {
                return;
            }
            if (this.m_doingUpgradeFlashing)
            {
                return;
            }
            this.previewFloorID = param1;
            if (param1 == -1)
            {
                this.m_SpeechPanel.remove(this.m_hotelText);
                this.createSpeechDialog();
                this.m_SpeechPanel.append(this.m_hotelText);
                this.previewFloorRewardIndex = -1;
                this.m_PossibleRewardsContentPanel.visible = false;
                this.m_roomPreviewContentPanel.visible = false;
                return;
            }
            int _loc_3 =0;
            while (_loc_3 <= this.m_owner.getItem().hotel.maxFloors)
            {

                _loc_6 = this.m_floorIndicators.get(_loc_3);
                _loc_7 = _loc_6.getBackgroundDecorator().getDisplay(_loc_6).width;
                _loc_8 = _loc_6.getBackgroundDecorator().getDisplay(_loc_6).height;
                _loc_9 =(DisplayObject) new m_assetDict.get("hotels_floorLocked");
                _loc_9.width = _loc_7;
                _loc_9.height = _loc_8;
                _loc_10 =(DisplayObject) new m_assetDict.get("hotels_floorUnlocked");
                _loc_10.width = _loc_7;
                _loc_10.height = _loc_8;
                if (_loc_3 == this.previewFloorID)
                {
                    ASwingHelper.setBackground(_loc_6, _loc_9);
                }
                else
                {
                    ASwingHelper.setBackground(_loc_6, _loc_10);
                }
                ASwingHelper.prepare(_loc_6);
                _loc_3++;
            }
            _loc_4 = this.m_owner.getItem ().hotel.getFloor(this.previewFloorID ).imageURL ;
            _loc_5 = assetDict.get(_loc_4);
            assetDict.get(_loc_4).smoothing = true;
            this.setupFloorPicture(_loc_5, this.previewFloorID);
            if (!param2)
            {
                return;
            }
            this.loadRewardImages(this.previewFloorID);
            if (this.rewardRotationTimer)
            {
                this.rewardRotationTimer.start();
            }
            return;
        }//end

        protected void  loadRewardImages (int param1 )
        {
            this.floorRewards = this.m_checkinHandler.getAllRewardDooberDataForFloor(param1);
            if (this.floorRewards.length == 0)
            {
                this.previewFloorRewardIndex = -1;
                this.m_PossibleRewardsContentPanel.visible = false;
            }
            else
            {
                this.previewFloorRewardIndex = Utilities.randBetween(0, (this.floorRewards.length - 1));
                this.showRewardImage();
                this.m_PossibleRewardsContentPanel.visible = true;
            }
            return;
        }//end

        protected void  rewardRotationTimerTick (TimerEvent event )
        {
            this.incrementRewardIndex();
            return;
        }//end

        protected void  incrementRewardIndex ()
        {
            if (this.floorRewards.length == 0)
            {
                return;
            }
            if (this.previewFloorRewardIndex == -1)
            {
                return;
            }
            this.previewFloorRewardIndex++;
            if (this.previewFloorRewardIndex >= this.floorRewards.length())
            {
                this.previewFloorRewardIndex = 0;
            }
            this.showRewardImage();
            this.rewardRotationTimer.start();
            return;
        }//end

        protected void  overrideRewardIndex ()
        {
            HotelDooberData _loc_4 =null ;
            if (this.floorRewards.length == 0)
            {
                return;
            }
            if (this.previewFloorRewardIndex == -1)
            {
                return;
            }
            this.rewardRotationTimer.stop();
            _loc_1 = this.m_checkinHandler.getVisitorHotelOrder(Global.getVisiting (),Global.player.uid );
            _loc_2 = this.m_checkinHandler.getRewardDooberDataByFloorAndByIndex(_loc_1.guestData.floor ,_loc_1.guestData.gotGift );
            int _loc_3 =0;
            for(int i0 = 0; i0 < this.floorRewards.size(); i0++)
            {
            		_loc_4 = this.floorRewards.get(i0);

                if (_loc_2.equals(_loc_4))
                {
                    this.previewFloorRewardIndex = _loc_3;
                }
                _loc_3++;
            }
            this.showRewardImage(true);
            return;
        }//end

        private void  setupFloorPicture (Bitmap param1 ,int param2 )
        {
            this.m_roomPreviewContentPanel.height = param1.height + 40;
            this.m_roomPreviewContentPanel.setPreferredHeight(param1.height + 40);
            this.m_roomPreviewContentPanel.setMinimumHeight(param1.height + 40);
            this.m_roomPreviewContentPanel.setMaximumHeight(param1.height + 40);
            this.m_roomPreviewContentPanel.setMinimumHeight(param1.height);
            this.m_roomPreviewContentPanel.setMaximumHeight(param1.height);
            this.m_roomPreviewContentPanel.setPreferredWidth(param1.width);
            this.m_roomPreviewContentPanel.setMinimumWidth(param1.width);
            this.m_roomPreviewContentPanel.setMaximumWidth(param1.width);
            this.m_roomPreviewContentPanel.addChild(param1);
            param1.x = 10;
            param1.y = 10;
            _loc_3 = this.m_checkinHandler.getFloorCaption(param2 );
            _loc_4 = ASwingHelper.makeMultilineText(_loc_3 ,TITLE_TEXT_WIDTH -20,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,14,EmbeddedArt.titleColor );
            _loc_4.filters = EmbeddedArt.toolTipNormalTitleFilters;
            this.m_roomPreviewContentPanel.addChild(_loc_4);
            _loc_4.x = 15;
            _loc_4.y = 80;
            return;
        }//end

        public void  initGivenReward ()
        {
            this.previewFloorRewardIndex = this.m_checkinHandler.getGuestGiftIndex(Global.getVisiting(), Global.player.uid);
            if (this.previewFloorRewardIndex == -1)
            {
                return;
            }
            _loc_1 = this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting (),Global.player.uid );
            this.floorRewards = this.m_checkinHandler.getAllRewardDooberDataForFloor(_loc_1);
            this.showRewardImage(true);
            _loc_2 = this.m_owner.getItem ().hotel.getFloor(_loc_1 ).imageURL ;
            _loc_3 = assetDict.get(_loc_2);
            if (_loc_3 == null)
            {
                return;
            }
            this.setupFloorPicture(_loc_3, _loc_1);
            return;
        }//end

        protected void  showRewardImage (boolean param1 =false )
        {
            if (this.previewFloorRewardIndex >= this.floorRewards.length())
            {
                return;
            }
            _loc_2 = HotelsGuestDialog.urlbackOrig;
            if (param1 !=null)
            {
                _loc_2 = HotelsGuestDialog.urlbackOrig2;
            }
            _loc_3 = assetDict.get(_loc_2);
            _loc_3.smoothing = true;
            _loc_3.x = 5;
            this.m_PossibleRewardsContentPanel.addChild(_loc_3);
            _loc_4 = this.floorRewards.get(this.previewFloorRewardIndex) ;
            _loc_5 = this.floorRewards.get(this.previewFloorRewardIndex).pictureURL ;
            _loc_6 = assetDict.get(_loc_5);
            assetDict.get(_loc_5).smoothing = true;
            _loc_7 = _loc_3(.width-_loc_6.width)/2;
            _loc_8 = _loc_3(.height-_loc_6.height)/2;
            _loc_6.x = _loc_3.x + _loc_7;
            _loc_6.y = _loc_3.y + _loc_8 - 10;
            this.m_PossibleRewardsContentPanel.addChild(_loc_6);
            _loc_9 = _loc_4.rewardText;
            int _loc_10 =14;
            if (_loc_9.length > 10)
            {
                _loc_10 = 12;
            }
            _loc_11 = ASwingHelper.makeMultilineText(_loc_9 ,TITLE_TEXT_WIDTH -30,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,_loc_10 ,EmbeddedArt.darkBlueTextColor );
            this.m_PossibleRewardsContentPanel.addChild(_loc_11);
            _loc_11.x = 15;
            _loc_11.y = 75;
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            if (!this.givenReward)
            {
                return;
            }
            if (this.m_doingUpgradeFlashing)
            {
                this.showFloor(this.m_upgradeFloor, false);
                return;
            }
            _loc_2 = this.m_checkinHandler.visitorCheckedIntoHotelFloor(Global.getVisiting (),Global.player.uid );
            this.showFloor(_loc_2, false);
            this.loadRewardImages(_loc_2);
            this.overrideRewardIndex();
            return;
        }//end

         protected Object  getTitleTokens ()
        {
            _loc_1 = this.m_owner.getCustomName ();
            return {hotelName:_loc_1};
        }//end

    }



