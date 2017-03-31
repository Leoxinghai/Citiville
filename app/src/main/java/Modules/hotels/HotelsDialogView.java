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
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.Transactions.*;
import Modules.stats.types.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.zynga.*;

    public class HotelsDialogView extends GenericDialogView implements IBaseLocKey
    {
        protected JPanel m_contentPanel ;
        protected Array m_tabStates ;
        protected Array m_tabs ;
        protected int m_addGuestCashAmount ;
        protected CustomButton m_addGuestsBtn ;
        protected JLabel m_guestsRatio ;
        protected JLabel m_cruiseShipGuests ;
        protected JLabel m_friendGuests ;
        protected JLabel m_npcGuests ;
        protected JLabel m_totalGuests ;
        protected Timer m_timer ;
        protected JLabel m_timerText ;
        protected int m_timeLeft ;
        protected JLabel m_timerLabelText ;
        protected JWindow m_toolTipWindow ;
        protected JWindow m_helpWindow ;
        protected SimpleButton m_helpCloseButton ;
        protected AssetPane m_InfographicPane ;
        protected double m_hotelId ;
        protected MechanicMapResource m_owner ;
        protected ICheckInHandler m_checkinHandler ;
        protected CustomButton m_inviteButton ;
        protected CustomButton m_guestListInvitebutton ;
        protected int m_tab ;
        public static  int TAB_STATE_SELECTED =0;
        public static  int TAB_STATE_UNSELECTED =1;
        public static  int TAB_WIDTH =208;
        public static  int TAB_HEIGHT =31;
        public static  int TAB_CAP_FONT_SIZE =20;
        public static  int TAB_FONT_SIZE =14;
public static HotelsDialogView m_instance =null ;

        public  HotelsDialogView (Dictionary param1 ,int param2 ,String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true )
        {
            this.m_timer = new Timer(1000);
            this.m_hotelId = param1.get("spawner").getId();
            this.m_owner = param1.get("spawner");
            this.m_checkinHandler =(ICheckInHandler) this.m_owner;
            this.m_tab = param2;
            m_instance = this;
            this.m_InfographicPane = new AssetPane();
            super(param1, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "view", this.m_owner.getItemName(), "owner_panel_ui", "hotel_ownerview");
            return;
        }//end

         protected void  closeMe ()
        {
            m_instance = null;
            super.closeMe();
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

        protected int  getColorFromSkin (String param1 ,int param2 )
        {
            _loc_3 = param2;
            _loc_4 = this.m_owner.getItem ().getUI_skin_param(param1 );
            if (this.m_owner.getItem().getUI_skin_param(param1))
            {
                _loc_3 = uint(_loc_4);
            }
            return _loc_3;
        }//end

        protected DisplayObject  getNpcHost ()
        {
            DisplayObject _loc_1 =(DisplayObject)new assetDict.get( "hotels_npc_host");
            _loc_1.x = 5;
            _loc_1.y = 70;
            return _loc_1;
        }//end

         protected void  makeCenterPanel ()
        {
            ASwingHelper.setBackground(this, m_bgAsset);
            this.setPreferredWidth(732);
            this.setPreferredHeight(560);
            _loc_1 = this.getNpcHost ();
            this.addChild(_loc_1);
            this.append(createHeaderPanel());
            this.append(this.makeContentPanel());
            ASwingHelper.prepare(this);
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "hotels_questionButton");
            SimpleButton _loc_3 =new SimpleButton(_loc_2 ,_loc_2 ,_loc_2 ,_loc_2 );
            _loc_3.x = 220;
            _loc_3.y = 130;
            this.addChild(_loc_3);
            _loc_3.addEventListener(MouseEvent.MOUSE_OVER, this.showToolTip, false, 0, true);
            _loc_3.addEventListener(MouseEvent.MOUSE_OUT, this.hideToolTip, false, 0, true);
            _loc_3.addEventListener(MouseEvent.CLICK, this.makeHelpPanel, false, 0, true);
            return;
        }//end

        protected JPanel  makeContentPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-1);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            ASwingHelper.setBackground(_loc_3, new m_assetDict.get("hotels_mainTabPanel"));
            ASwingHelper.setEasyBorder(this.m_contentPanel, 0, 0, 10, 0);
            _loc_3.append(this.m_contentPanel);
            _loc_2.appendAll(this.makeTabsPanel(), _loc_3);
            _loc_2.setPreferredWidth(464);
            _loc_1.appendAll(this.makeStatsPanel(), ASwingHelper.horizontalStrut(10), _loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeTimerPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,10);
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "hotels_timerBackground");
            ASwingHelper.setBackground(_loc_1, _loc_2);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,5);
            AssetPane _loc_4 =new AssetPane(new m_assetDict.get( "payroll_icon_clock") );
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            this.m_timerLabelText = ASwingHelper.makeLabel(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteGuests")), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.blueTextColor, JLabel.LEFT);
            this.m_timerText = ASwingHelper.makeLabel(this.getTimeLeft(), EmbeddedArt.titleFont, 18, EmbeddedArt.blueTextColor, JLabel.LEFT);
            this.m_timerText.setPreferredWidth(150);
            this.m_timer.addEventListener(TimerEvent.TIMER, this.updateClock);
            if (this.m_timeLeft > 0)
            {
                this.m_timer.start();
            }
            _loc_5.appendAll(this.m_timerLabelText, ASwingHelper.leftAlignElement(this.m_timerText));
            this.m_inviteButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "Invite")), null, "GreenButtonUI");
            this.m_inviteButton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix()));
            this.m_inviteButton.addActionListener(this.onInviteGuestClick);
            _loc_1.append(this.m_inviteButton);
            _loc_3.appendAll(_loc_4, _loc_5);
            ASwingHelper.setEasyBorder(_loc_3, 10, 12, 0, 11);
            _loc_1.setPreferredHeight(_loc_2.height);
            _loc_1.appendAll(_loc_3, ASwingHelper.centerElement(this.m_inviteButton));
            return _loc_1;
        }//end

        protected String  getTimeLeft ()
        {
            String _loc_1 =null ;
            this.m_timeLeft = Global.world.viralMgr.getTimeTillCanPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix());
            if (this.m_timeLeft <= 0)
            {
                _loc_1 = ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteNow"));
                this.m_timerLabelText.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteGuests")));
            }
            else
            {
                _loc_1 = this.getFormattedTime(this.m_timeLeft);
                this.m_timerLabelText.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteGuestsIn")));
            }
            return _loc_1;
        }//end

        protected String  getFormattedTime (int param1 )
        {
            _loc_2 = param1/3600;
            _loc_3 = param1(-_loc_2 *3600)/60;
            _loc_4 = param1% 60;
            return ZLoc.t("Dialogs", "TimeInfo_timerFormat", {hours:this.padSingleDigit(_loc_2), minutes:this.padSingleDigit(_loc_3), seconds:this.padSingleDigit(_loc_4)});
        }//end

        protected String  padSingleDigit (int param1 )
        {
            if (param1 < 10 && param1 >= 0)
            {
                return "0" + param1.toString();
            }
            return param1.toString();
        }//end

        protected void  hideToolTip (MouseEvent event )
        {
            this.m_toolTipWindow.hide();
            return;
        }//end

        protected AssetPane  makeOrangeBar (int param1 )
        {
            _loc_2 = param1(-100)/2;
            Sprite _loc_3 =new Sprite ();
            _loc_3.graphics.moveTo(_loc_2, 0);
            _loc_3.graphics.lineStyle(2, EmbeddedArt.lightOrangeTextColor, 2, true);
            _loc_3.graphics.lineTo(_loc_2 + 100, 0);
            AssetPane _loc_4 =new AssetPane(_loc_3 );
            return new AssetPane(_loc_3);
        }//end

        protected void  showToolTip (MouseEvent event )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "view", this.m_owner.getItemName(), "owner_panel_ui", "guest_distribution");
            this.m_toolTipWindow = new JWindow(this);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_3 =new EmbeddedArt.mkt_pop_info ()as DisplayObject ;
            ASwingHelper.setBackground(_loc_2, _loc_3);
            _loc_2.setPreferredWidth(_loc_3.width);
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltiptitle")),EmbeddedArt.titleFont ,16,EmbeddedArt.blueTextColor );
            this.m_friendGuests = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
            this.m_cruiseShipGuests = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
            this.m_npcGuests = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
            this.m_totalGuests = ASwingHelper.makeLabel("", EmbeddedArt.titleFont, 12, EmbeddedArt.lightOrangeTextColor);
            this.updateMechanicText();
            _loc_2.appendAll(_loc_4, this.makeOrangeBar(_loc_3.width), this.m_friendGuests, this.m_cruiseShipGuests, this.m_npcGuests, this.makeOrangeBar(_loc_3.width), this.m_totalGuests, ASwingHelper.verticalStrut(20));
            _loc_5 = (Sprite)event.currentTarget
            this.m_toolTipWindow.setContentPane(_loc_2);
            this.m_toolTipWindow.setX(157);
            this.m_toolTipWindow.setY(7);
            ASwingHelper.prepare(this.m_toolTipWindow);
            this.m_toolTipWindow.mouseChildren = false;
            this.m_toolTipWindow.mouseEnabled = false;
            this.m_toolTipWindow.show();
            return;
        }//end

        public void  setInfographic (DisplayObject param1 )
        {
            this.m_InfographicPane.setAsset(param1);
            ASwingHelper.prepare(this.m_InfographicPane);
            return;
        }//end

        protected void  makeHelpPanel (Event event )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "owner_panel_ui", "infographic");
            this.m_helpWindow = new JWindow(this);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "hotels_hotels_15");
            ASwingHelper.setBackground(_loc_2, _loc_3);
            ASwingHelper.setEasyBorder(this.m_InfographicPane, 10, 10, 10, 10);
            _loc_2.append(this.m_InfographicPane);
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "hotels_deleteMessageX");
            this.m_helpCloseButton = new SimpleButton(_loc_4, _loc_4, _loc_4, _loc_4);
            this.m_helpCloseButton.addEventListener(MouseEvent.CLICK, this.hideHelpPanel);
            this.m_helpWindow.setContentPane(_loc_2);
            this.m_helpWindow.setX(95);
            this.m_helpWindow.setY(20);
            this.m_helpCloseButton.x = this.m_helpWindow.getX();
            this.m_helpCloseButton.y = this.m_helpWindow.getY();
            ASwingHelper.prepare(this.m_helpWindow);
            this.m_helpWindow.mouseChildren = false;
            this.m_helpWindow.mouseEnabled = false;
            this.m_helpWindow.show();
            this.addChild(this.m_helpCloseButton);
            return;
        }//end

        protected void  hideHelpPanel (MouseEvent event )
        {
            this.m_helpWindow.hide();
            this.m_helpCloseButton.visible = false;
            return;
        }//end

        protected JPanel  makeGuestStatsPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "hotels_guestSign");
            ASwingHelper.setSizedBackground(_loc_1, _loc_2);
            _loc_3 = ZLoc.tk("Dialogs","Guest");
            _loc_4 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("guests"),{guest_loc_3}));
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_5.setPreferredWidth(100);
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_7 = ASwingHelper.makeLabel(_loc_4 ,EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor );
            _loc_8 = this.m_checkinHandler.getPeepCount ()+"/"+this.m_checkinHandler.getPeepMax ();
            int _loc_9 =16;
            if (this.m_checkinHandler.getPeepMax() > 999)
            {
                _loc_9 = 15;
            }
            this.m_guestsRatio = ASwingHelper.makeLabel(_loc_8, EmbeddedArt.titleFont, _loc_9, EmbeddedArt.lightOrangeTextColor);
            _loc_6.appendAll(_loc_7, this.m_guestsRatio);
            _loc_5.append(_loc_6);
            ASwingHelper.prepare(_loc_5);
            ASwingHelper.setEasyBorder(_loc_5, 8, 0, 3);
            _loc_10 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("addGuest")));
            _loc_11 = ASwingHelper.makeLabel(_loc_10 ,EmbeddedArt.defaultFontNameBold ,11,EmbeddedArt.brownTextColor );
            AssetIcon _loc_12 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            _loc_13 = this.m_checkinHandler.getPeepMax ();
            _loc_14 = this.m_checkinHandler.getPeepMax ()-this.m_checkinHandler.getPeepCount ();
            _loc_15 = this.m_owner.getItem ().hotel.guestFillCost ;
            this.m_addGuestCashAmount = Math.ceil(_loc_14 * _loc_15 / _loc_13);
            this.m_addGuestsBtn = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:this.m_addGuestCashAmount})), _loc_12, "CashButtonUI");
            _loc_16 = this.m_addGuestsBtn.getFont ();
            this.m_addGuestsBtn.setFont(_loc_16.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_16.getSize(), 1, [{locale:"it", ratio:0.84}])));
            this.m_addGuestsBtn.addActionListener(this.addGuestConfirm, 0, true);
            if (this.m_checkinHandler.getPeepCount() == this.m_checkinHandler.getPeepMax())
            {
                this.m_addGuestsBtn.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(_loc_5, 30);
            _loc_1.appendAll(ASwingHelper.centerElement(_loc_5), _loc_11, ASwingHelper.centerElement(this.m_addGuestsBtn));
            return _loc_1;
        }//end

        protected JPanel  makeStatsPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            ASwingHelper.setEasyBorder(_loc_1, 85);
            _loc_1.append(ASwingHelper.rightAlignElement(this.makeGuestStatsPanel()));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ASwingHelper.setSizedBackground(_loc_2, new assetDict.get("hotels_podium"));
            _loc_2.append(ASwingHelper.verticalStrut(15));
            _loc_2.append(ASwingHelper.centerElement(this.makeWelcomePanel()));
            _loc_2.append(ASwingHelper.verticalStrut(5));
            _loc_2.append(ASwingHelper.centerElement(this.makeTimerPanel()));
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeWelcomePanel ()
        {
            JPanel _loc_1 =new JPanel(new LayeredLayout ());
            ASwingHelper.setSizedBackground(_loc_1, new assetDict.get("hotels_welcomeSign"));
            _loc_2 = this.getColorFromSkin("welcome_font_front_color",9401434);
            _loc_3 = this.getColorFromSkin("welcome_font_back_color",6239763);
            _loc_4 = TextFieldUtil.getLocaleFontSize(24,24,[ {locale size "de",20locale },{"tr",size 20},locale {"id",20});
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("welcomeMessage")),200,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,_loc_4 ,_loc_3 );
            _loc_6 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("welcomeMessage")),200,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,_loc_4 ,_loc_2 );
            _loc_5.setVerticalAlignment(AsWingConstants.CENTER);
            _loc_5.setHorizontalAlignment(AsWingConstants.CENTER);
            _loc_6.setVerticalAlignment(AsWingConstants.CENTER);
            _loc_6.setHorizontalAlignment(AsWingConstants.CENTER);
            ASwingHelper.setEasyBorder(_loc_6, 3, 0, 0, 2);
            _loc_1.appendAll(_loc_5, _loc_6);
            return _loc_1;
        }//end

        protected JPanel  makeTabsPanel ()
        {
            JPanel _loc_4 =null ;
            UpgradeDefinition _loc_5 =null ;
            JPanel _loc_6 =null ;
            JTextField _loc_7 =null ;
            TextFormat _loc_8 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(FlowLayout.LEFT ,10);
            Array _loc_2 =new Array ();
            _loc_2.push(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("guests")));
            this.m_tabs = new Array();
            this.m_tabs.push(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
            if (!this.m_owner.getItem().getUI_skin_param("noupgrade"))
            {
                _loc_2.push(ZLoc.t("Dialogs", "Upgrade"));
                this.m_tabs.push(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
            }
            int _loc_3 =0;
            while (_loc_3 < this.m_tabs.length())
            {

                _loc_4 = this.m_tabs.get(_loc_3);
                _loc_4.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4.setMinimumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4.setMaximumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_5 = this.m_owner.getItem().upgrade;
                _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_7 = ASwingHelper.makeTextField(_loc_2.get(_loc_3), EmbeddedArt.titleFont, TAB_FONT_SIZE, EmbeddedArt.blueTextColor);
                _loc_8 = new TextFormat();
                _loc_8.size = TAB_CAP_FONT_SIZE;
                if (_loc_5 == null && _loc_3 == HotelsDialog.TAB_UPGRADE)
                {
                    _loc_8.color = EmbeddedArt.lightGrayTextColor;
                    this.m_tab = HotelsDialog.DEFAULT_TAB;
                }
                else
                {
                    _loc_4.addEventListener(MouseEvent.MOUSE_UP, this.onTabClicked, false, 0, true);
                }
                TextFieldUtil.formatSmallCaps(_loc_7.getTextField(), _loc_8);
                _loc_6.append(_loc_7);
                _loc_7.getTextField().height = TAB_FONT_SIZE * 1.5;
                _loc_4.append(_loc_6);
                _loc_1.append(_loc_4);
                _loc_3++;
            }
            this.setSelectedTab(this.m_tab, false);
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, 0, 10);
            return _loc_1;
        }//end

         protected void  init ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            m_bgAsset = m_assetDict.get("dialog_bg");
            this.m_tabStates = new Array();
            Array _loc_1 =new Array ();
            Array _loc_2 =new Array ();
            _loc_1.push(m_assetDict.get("hotels_whiteTabLeft"));
            _loc_1.push(m_assetDict.get("hotels_blueTabLeft"));
            _loc_2.push(m_assetDict.get("hotels_whiteTabRight"));
            _loc_2.push(m_assetDict.get("hotels_blueTabRight"));
            this.m_tabStates.push(_loc_1);
            this.m_tabStates.push(_loc_2);
            makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  onInviteGuestClick (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "owner_panel_ui", "click_invite");
            _loc_2 = this.m_owner.getCustomName ();
            Global.world.viralMgr.sendHotelCheckInFeed(this.m_hotelId, this.getViralSuffix(), _loc_2, Global.player.uid, null, this.startTimer);
            this.m_inviteButton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix()));
            if (this.m_guestListInvitebutton)
            {
                this.m_guestListInvitebutton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix()));
            }
            return;
        }//end

        public void  startTimer (Object param1)
        {
            this.m_timerText.setText(this.getTimeLeft());
            if (this.m_timeLeft > 0)
            {
                this.m_timer.start();
                this.m_inviteButton.setEnabled(false);
                if (this.m_guestListInvitebutton)
                {
                    this.m_guestListInvitebutton.setEnabled(false);
                }
            }
            else
            {
                this.m_inviteButton.setEnabled(true);
                if (this.m_guestListInvitebutton)
                {
                    this.m_guestListInvitebutton.setEnabled(true);
                }
            }
            return;
        }//end

        protected void  onTabClicked (MouseEvent event )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                if (this.m_tabs.get(_loc_2) == event.currentTarget)
                {
                    this.setSelectedTab(_loc_2, true);
                    if (_loc_2 == HotelsDialog.TAB_GUESTS)
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "owner_panel_ui", "guest_tab");
                    }
                    else
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", this.m_owner.getItemName(), "owner_panel_ui", "upgrade_tab");
                    }
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        protected JPanel  createGuestsPanel ()
        {
            return new HotelGuestsPanel(this.m_assetDict, this);
        }//end

        protected JPanel  createUpgradePanel ()
        {
            return new HotelUpgradePanel(m_assetDict, 466, close, this);
        }//end

        protected void  updateClock (TimerEvent event )
        {
            this.m_timeLeft = this.m_timeLeft - this.m_timer.delay / 1000;
            if (this.m_timeLeft <= 0)
            {
                this.m_timerText.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteNow")));
                this.m_timerLabelText.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteGuests")));
                this.m_inviteButton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix()));
                if (this.m_guestListInvitebutton)
                {
                    this.m_guestListInvitebutton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.getViralSuffix()));
                }
                this.m_timer.stop();
            }
            else
            {
                this.m_timerText.setText(this.getFormattedTime(Math.max(0, this.m_timeLeft)));
                this.m_timerLabelText.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("inviteGuestsIn")));
            }
            return;
        }//end

        public void  updateMechanicText ()
        {
            Object _loc_13 =null ;
            Dictionary _loc_14 =null ;
            _loc_15 = undefined;
            _loc_1 = this.m_checkinHandler.getPeepCount ();
            _loc_2 = this.m_checkinHandler.getPeepMax ();
            this.m_guestsRatio.setText(_loc_1 + "/" + _loc_2);
            ASwingHelper.prepare(this.m_guestsRatio);
            _loc_3 = _loc_2-_loc_1 ;
            _loc_4 = this.m_owner.getItem ().hotel.guestFillCost ;
            this.m_addGuestCashAmount = Math.ceil(_loc_3 * _loc_4 / _loc_2);
            this.m_addGuestsBtn.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:this.m_addGuestCashAmount})));
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            _loc_8 = this.m_owner.getMechanicData ();
            if (this.m_owner.getMechanicData().get("harvestState") && _loc_8.get("harvestState").get("customerSources"))
            {
                _loc_13 = _loc_8.get("harvestState").get("customerSources");
                _loc_14 = new Dictionary();
                for(int i0 = 0; i0 < _loc_13.size(); i0++)
                {
                		_loc_15 = _loc_13.get(i0);

                    if (_loc_15 == "harvest_ship")
                    {
                        _loc_6 = _loc_6 + _loc_13.get(_loc_15);
                        continue;
                    }
                    if (_loc_15 == "friend")
                    {
                        _loc_5 = _loc_5 + _loc_13.get(_loc_15);
                        continue;
                    }
                    _loc_7 = _loc_7 + _loc_13.get(_loc_15);
                }
            }
            _loc_9 = _loc_5+_loc_6+_loc_7;
            _loc_10 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipFriends"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{count_loc_5});
            _loc_11 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipShip"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{count_loc_6});
            _loc_12 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipBusiness"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{count_loc_7});
            if (_loc_5 == 1)
            {
                _loc_10 = ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipFriends")) + " = " + ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipNumGuestsS"), {count:_loc_5});
            }
            if (_loc_6 == 1)
            {
                _loc_11 = ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipShip")) + " = " + ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipNumGuestsS"), {count:_loc_6});
            }
            if (_loc_7 == 1)
            {
                _loc_12 = ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipBusiness")) + " = " + ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipNumGuestsS"), {count:_loc_7});
            }
            this.updateTooltipText(_loc_10, _loc_11, _loc_12, _loc_9);
            return;
        }//end

        protected void  updateTooltipText (String param1 ,String param2 ,String param3 ,int param4 )
        {
            if (this.m_cruiseShipGuests != null)
            {
                this.m_cruiseShipGuests.setText(param2);
                ASwingHelper.prepare(this.m_cruiseShipGuests);
            }
            if (this.m_friendGuests != null)
            {
                this.m_friendGuests.setText(param1);
                ASwingHelper.prepare(this.m_friendGuests);
            }
            if (this.m_npcGuests != null)
            {
                this.m_npcGuests.setText(param3);
                ASwingHelper.prepare(this.m_npcGuests);
            }
            if (this.m_totalGuests != null)
            {
                this.m_totalGuests.setText(ZLoc.t("Dialogs", this.getLocKeyBaseDialog("tooltipTotal"), {count:String(param4)}));
                ASwingHelper.prepare(this.m_totalGuests);
            }
            return;
        }//end

        protected void  addGuestConfirm (Event event )
        {
            String _loc_2 =null ;
            if (Global.player.cash >= this.m_addGuestCashAmount)
            {
                _loc_2 = ZLoc.t("Dialogs", this.getLocKeyBaseDialog("BuyGuestConfirm"), {amount:this.m_addGuestCashAmount});
                UI.displayMessage(_loc_2, GenericPopup.TYPE_YESNO, this.addGuest, "", true);
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "ImpulseMarketCash");
                UI.displayPopup(new GetCoinsDialog(_loc_2, "GetCash", GenericDialogView.TYPE_GETCASH, null, true), false);
            }
            return;
        }//end

        protected void  addGuest (GenericPopupEvent event )
        {
            Dictionary _loc_2 =null ;
            Object _loc_3 =null ;
            int _loc_4 =0;
            if (event.button == GenericPopup.ACCEPT)
            {
                _loc_2 = this.m_owner.getMechanicData();
                if (_loc_2.get("harvestState"))
                {
                    _loc_3 = _loc_2.get("harvestState");
                    if (_loc_3.hasOwnProperty("customers") && _loc_3.hasOwnProperty("customersReq"))
                    {
                        if (_loc_3.get("customers") < _loc_3.get("customersReq"))
                        {
                            if (Global.player.cash > this.m_addGuestCashAmount)
                            {
                                _loc_4 = _loc_3.get("customersReq") - _loc_3.get("customers");
                                _loc_3.put("customers",  _loc_3.get("customersReq"));
                                if (!_loc_3.hasOwnProperty("customerSources"))
                                {
                                    _loc_3.customerSources = new Dictionary();
                                }
                                if (!_loc_3.get("customerSources").hasOwnProperty("purchased"))
                                {
                                    _loc_3.get("customerSources").put("purchased",  0);
                                }
                                _loc_3.get("customerSources").put("purchased",  _loc_3.get("customerSources").get("purchased") + _loc_4);
                                Global.player.cash = Global.player.cash - this.m_addGuestCashAmount;
                                this.m_inviteButton.setEnabled(false);
                                this.m_owner.setDataForMechanic("harvestState", _loc_3, "NPCEnterAction");
                                GameTransactionManager.addTransaction(new TMechanicAction(this.m_owner, "hotelInterface", "GMPlay", {operation:"buyGuest", hotelId:this.m_owner.getId()}));
                                this.updateMechanicText();
                            }
                        }
                        if (_loc_3.get("customers") >= _loc_3.get("customersReq"))
                        {
                            this.m_addGuestsBtn.setEnabled(false);
                        }
                    }
                }
            }
            return;
        }//end

         protected Object  getTitleTokens ()
        {
            _loc_1 = this.m_owner.getCustomName ();
            return {hotelName:_loc_1};
        }//end

        public void  setGuestListInviteButton (CustomButton param1 )
        {
            this.m_guestListInvitebutton = param1;
            return;
        }//end

        public String  getViralSuffix ()
        {
            String _loc_3 =null ;
            if (this.m_owner == null)
            {
                return null;
            }
            _loc_1 = this.m_owner.getItemName ();
            _loc_2 = _loc_1.lastIndexOf("_");
            if (_loc_2 != -1)
            {
                _loc_3 = "_" + _loc_1.substr(0, _loc_2);
                return _loc_3;
            }
            return _loc_1;
        }//end

        public void  setSelectedTab (int param1 ,boolean param2 )
        {
            DisplayObject _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_tabs.length())
            {

                _loc_4 = (_loc_3 == param1 ? (new this.m_tabStates.get(_loc_3).get(TAB_STATE_SELECTED)) : (new this.m_tabStates.get(_loc_3).get(TAB_STATE_UNSELECTED)));
                _loc_4.width = TAB_WIDTH;
                _loc_4.height = TAB_HEIGHT;
                this.m_tabs.get(_loc_3).setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                this.m_tabs.get(_loc_3).setBackgroundDecorator(new AssetBackground(_loc_4));
                _loc_3 = _loc_3 + 1;
            }
            this.m_contentPanel.removeAll();
            switch(param1)
            {
                case HotelsDialog.TAB_GUESTS:
                {
                    this.m_contentPanel.appendAll(this.createGuestsPanel());
                    break;
                }
                case HotelsDialog.TAB_UPGRADE:
                {
                    this.m_contentPanel.appendAll(this.createUpgradePanel());
                    break;
                }
                default:
                {
                    break;
                }
            }
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public static HotelsDialogView  getInstance ()
        {
            return m_instance;
        }//end

    }



