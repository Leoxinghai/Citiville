package Modules.streetFair;

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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Init.*;
import Modules.sunset.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class StreetFairDialogView extends GenericDialogView
    {
        protected String m_ticketTheme ;
        protected Array m_giftableItems ;
        protected CustomButton m_shareBtn ;
        protected Dictionary m_buyButtons ;
        protected Dictionary m_rewardCells ;
        protected Dictionary m_buyBundleButtons ;
        protected JLabel m_ticketAmountLabel ;
        protected AssetPane m_check ;
        protected DisplayObject m_ticketMeter ;
        protected JWindow m_toolTipWindow ;
        protected Item rand ;
public static  double DISTANCE_BETWEEN_TICK_MARKS =38;

        public  StreetFairDialogView (String param1 ,Dictionary param2 ,String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true )
        {
            this.m_buyButtons = new Dictionary();
            this.m_rewardCells = new Dictionary();
            this.m_buyBundleButtons = new Dictionary();
            this.m_ticketTheme = param1;
            this.m_giftableItems = new Array();
            this.getGiftableItems();
            super(param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            addEventListener(DataItemEvent.SHOW_TOOLTIP, this.showToolTip, false, 0, true);
            addEventListener(DataItemEvent.HIDE_TOOLTIP, this.hideToolTip, false, 0, true);
            return;
        }//end

        private void  getGiftableItems ()
        {
            Item _loc_2 =null ;
            int _loc_1 =0;
            do
            {

                _loc_2 = Global.gameSettings().getItemByName(this.m_ticketTheme + "_ticket_" + _loc_1);
                if (_loc_2 != null)
                {
                    this.m_giftableItems.push(_loc_2);
                }
                _loc_1++;
            }while (_loc_2 != null)
            this.rand = this.m_giftableItems.get(int(Math.random() * this.m_giftableItems.length()));
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            m_bgAsset = this.m_assetDict.get("streetFair_bg");
            ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 20));
            m_acceptTextName = ZLoc.t("Tickets", "get_tickets_" + this.m_ticketTheme);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.append(createHeaderPanel());
            this.append(this.makeContentPanel());
            double _loc_1 =20;
            if (Global.localizer.langCode == "ja")
            {
                _loc_1 = 14;
            }
            this.append(ASwingHelper.verticalStrut(_loc_1));
            this.append(this.makeCheckBox());
            this.append(this.createButtonPanel());
            this.m_ticketMeter =(DisplayObject) new assetDict.get("streetFair_meter");
            addChildAt(this.m_ticketMeter, 1);
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "streetFair_ticket_meter");
            addChildAt(_loc_2, 2);
            _loc_2.x = 633;
            _loc_2.y = 445;
            this.updateHeight();
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  checkFeeds ()
        {
            String _loc_18 =null ;
            GenericDialog _loc_19 =null ;
            GenericDialog _loc_20 =null ;
            _loc_1 =Global.ticketManager.getTicket(this.m_ticketTheme );
            _loc_2 =Global.ticketManager.getTicketLevel(this.m_ticketTheme );
            _loc_3 =Global.ticketManager.getTicketCountByLevel(this.m_ticketTheme );
            _loc_4 = this"feed_dialog_"+.m_ticketTheme ;
            _loc_5 = GenericDialogView.TYPE_CUSTOM_OK;
            _loc_6 = Delegate.create(this ,this.showTicketFeedLevel ,.get(this.m_ticketTheme) );
            _loc_7 = this"ticket_booth_feed_dialog_"+.m_ticketTheme ;
            String _loc_8 ="assets/dialogs/genericDialog_samCongrats.png";
            boolean _loc_9 =true ;
            int _loc_10 =0;
            String _loc_11 ="";
            Function _loc_12 =null ;
            _loc_13 = ZLoc.t("Dialogs","ticket_booth_feed_dialog_"+this.m_ticketTheme+"_button");
            boolean _loc_14 =true ;
            String _loc_15 ="";
            _loc_16 =Global.player.snUser.gender =="M"? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
            Object _loc_17 =null ;
            if (_loc_2 > _loc_1.lastLevel && _loc_1.count && _loc_3 && Global.world.viralMgr.canPost("ticket_" + this.m_ticketTheme + "_level"))
            {
                _loc_18 = "level_feed_" + this.m_ticketTheme + "_message";
                _loc_17 = {threshold:_loc_3};
                _loc_15 = ZLoc.t("Tickets", _loc_18, _loc_17);
                _loc_7 = "level_feed_" + this.m_ticketTheme;
                _loc_19 = new CharacterResponseDialog(_loc_15, _loc_4, _loc_5, this.showTicketFeedLevel, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13, _loc_14);
                UI.displayPopup(_loc_19, false, _loc_18);
            }
            else if (_loc_1.lastCount < _loc_1.count && _loc_1.count && _loc_3 && _loc_1.count % 5 == 0 && Global.world.viralMgr.canPost("ticket_" + this.m_ticketTheme + "_5"))
            {
                _loc_17 = {city_user:Global.player.cityName};
                _loc_15 = ZLoc.t("Tickets", "5_feed_" + this.m_ticketTheme + "_message", _loc_17);
                _loc_7 = "5_feed_" + this.m_ticketTheme;
                _loc_20 = new CharacterResponseDialog(_loc_15, _loc_4, _loc_5, this.showTicketFeedFive, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, _loc_12, _loc_13, _loc_14);
                UI.displayPopup(_loc_20, false, "5_feed_" + this.m_ticketTheme + "_message");
            }
            GameTransactionManager.addTransaction(new TTicket(TTicket.UPDATE_LEVEL, {theme:this.m_ticketTheme}));
            Global.ticketManager.updateLevel(this.m_ticketTheme);
            return;
        }//end

        private void  showTicketFeedLevel (GenericPopupEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            Object _loc_4 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = this.m_ticketTheme;
                _loc_3 = Global.player.snUser.gender == "M" ? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
                _loc_4 = {threshold:Global.ticketManager.getTicketCountByLevel(_loc_2), ticketLevel:Global.ticketManager.getTicketLevel(_loc_2), user:ZLoc.tn(Global.player.firstName, _loc_3)};
                Global.world.viralMgr.sendFeedorAutoPublish("ticket_" + _loc_2 + "_level", _loc_2, _loc_4);
            }
            return;
        }//end

        private void  showTicketFeedFive (GenericPopupEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            Object _loc_4 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = this.m_ticketTheme;
                _loc_3 = Global.player.snUser.gender == "M" ? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
                _loc_4 = {cityname:Global.player.cityName, user:ZLoc.tn(Global.player.firstName, _loc_3)};
                Global.world.viralMgr.sendFeedorAutoPublish("ticket_" + _loc_2 + "_5", _loc_2, _loc_4);
            }
            return;
        }//end

        public void  updateHeight (boolean param1 =false )
        {
            this.m_ticketMeter.x = 648;
            this.m_ticketMeter.y = 438;
            this.m_ticketMeter.height = 50;
            _loc_2 =Global(.ticketManager.getCount(this.m_ticketTheme )as Number )/(TicketManager.MAX_TICKETS as Number );
            _loc_3 = (Int)(Global.ticketManager.getTicketLevel(this.m_ticketTheme ));
            this.fillToLevel(_loc_3);
            this.addDelta(_loc_3);
            if (param1 !=null)
            {
                this.checkFeeds();
            }
            return;
        }//end

        private void  addDelta (int param1 )
        {
            int _loc_4 =0;
            param1 = Math.max((param1 - 1), 0);
            _loc_2 =Global.ticketManager.getTicketLevels(this.m_ticketTheme );
            int _loc_3 =0;
            if (_loc_2.get((param1 + 1)))
            {
                _loc_4 = int(_loc_2.get((param1 + 1))) - int(_loc_2.get(param1));
                _loc_3 = int(DISTANCE_BETWEEN_TICK_MARKS / Number(_loc_4) * (Number(Global.ticketManager.getCount(this.m_ticketTheme)) - Number(_loc_2.get(param1))));
            }
            this.m_ticketMeter.y = this.m_ticketMeter.y - _loc_3;
            this.m_ticketMeter.height = this.m_ticketMeter.height + _loc_3;
            return;
        }//end

        private void  fillToLevel (double param1 )
        {
            param1 = Math.max((param1 - 1), 0);
            _loc_2 = (int)(param1 *DISTANCE_BETWEEN_TICK_MARKS );
            this.m_ticketMeter.y = this.m_ticketMeter.y - _loc_2;
            this.m_ticketMeter.height = this.m_ticketMeter.height + _loc_2;
            return;
        }//end

        protected JPanel  makeContentPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_1.name = "main panel";
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2.appendAll(this.makeSpeechPanel(), ASwingHelper.verticalStrut(38), this.makeRewardPanel());
            _loc_1.appendAll(_loc_2);
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            this.m_shareBtn = new CustomButton(m_acceptTextName, null, "BigGreenButtonUI");
            if (Global.world.viralMgr.canPost("ticket_" + this.m_ticketTheme + "_share"))
            {
                this.m_shareBtn.visible = true;
                this.m_shareBtn.addActionListener(this.onGetTickets, 0, true);
                this.m_shareBtn.setEnabled(true);
            }
            else
            {
                this.m_shareBtn.visible = false;
            }
            _loc_1.append(this.m_shareBtn);
            return _loc_1;
        }//end

        protected void  onGetTickets (Object param1)
        {
            if (Global.world.viralMgr.canPost("ticket_" + this.m_ticketTheme + "_share"))
            {
                Global.world.viralMgr.sendFeedorAutoPublish("ticket_" + this.m_ticketTheme + "_share", this.m_ticketTheme);
                this.m_shareBtn.setEnabled(false);
            }
            return;
        }//end

        protected JPanel  makeCheckBox ()
        {
            JLabel _loc_5 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "streetFair_checkbox");
            DisplayObject _loc_3 =(DisplayObject)new assetDict.get( "streetFair_checkmark");
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            ASwingHelper.setSizedBackground(_loc_4, _loc_2, new Insets(0, 3, 2, 3));
            if (!this.isSunset())
            {
                this.m_check = new AssetPane(_loc_3);
                this.m_check.visible = Global.world.viralMgr.getGlobalPublishStreamCheck(this.m_ticketTheme);
                _loc_4.append(this.m_check);
                _loc_5 = ASwingHelper.makeLabel(ZLoc.t("Tickets", "always_send_feeds"), EmbeddedArt.defaultFontNameBold, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), EmbeddedArt.whiteTextColor);
                _loc_1.appendAll(_loc_4, _loc_5);
                _loc_1.addEventListener(MouseEvent.CLICK, this.toggleCheck, false, 0, true);
            }
            return _loc_1;
        }//end

        private boolean  isInSunsetInterval ()
        {
            return TicketManager.isThemeValid(this.m_ticketTheme) && this.themeSunset.isInSunsetInterval();
        }//end

        private boolean  isSunset ()
        {
            return !TicketManager.isThemeValid(this.m_ticketTheme);
        }//end

        private void  toggleCheck (MouseEvent event )
        {
            if (this.m_check.visible)
            {
                this.m_check.visible = false;
            }
            else
            {
                this.m_check.visible = true;
            }
            Global.world.viralMgr.setGlobalPublishStreamCheck(this.m_check.visible, this.m_ticketTheme);
            return;
        }//end

        private Sunset  themeSunset ()
        {
            return Global.sunsetManager.getSunsetByThemeName(this.m_ticketTheme);
        }//end

        protected JPanel  makeSpeechPanel ()
        {
            double _loc_3 =0;
            String _loc_4 =null ;
            AssetPane _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(FlowLayout.CENTER );
            _loc_2 = ASwingHelper.makeMultilineText(ZLoc.t("Tickets",this.m_ticketTheme +"_speech_bubble"),509,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor );
            ASwingHelper.setEasyBorder(_loc_2, 10, 25, 0, 0);
            ASwingHelper.setBackground(_loc_1, new m_assetDict.get("streetFair_npcSpeechBubble"));
            _loc_1.setPreferredHeight(117);
            if (this.isInSunsetInterval())
            {
                _loc_3 = (this.themeSunset.endDate - GameSettingsInit.getCurrentTime()) / (1000 * 3600 * 24);
                if (_loc_3 < 1)
                {
                    _loc_4 = ZLoc.t("Tickets", this.m_ticketTheme + "_time_left_singular");
                }
                else
                {
                    _loc_4 = ZLoc.t("Tickets", this.m_ticketTheme + "_time_left_plural", {days_left:Math.round(_loc_3)});
                }
                _loc_5 = ASwingHelper.makeMultilineText(_loc_4, 510, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 16, EmbeddedArt.brownTextColor);
                _loc_1.appendAll(_loc_2, ASwingHelper.verticalStrut(10), _loc_5);
            }
            else
            {
                _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(0));
            }
            ASwingHelper.setEasyBorder(_loc_1, 0, 165, 0, 35);
            return _loc_1;
        }//end

        protected JPanel  makeRewardPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_1.appendAll(ASwingHelper.horizontalStrut(40), this.makeItemInfoPanel(), ASwingHelper.horizontalStrut(65), this.makeMeterPanel());
            return _loc_1;
        }//end

        protected JPanel  makeItemInfoPanel ()
        {
            CustomButton _loc_9 =null ;
            String _loc_16 =null ;
            Item _loc_17 =null ;
            JPanel _loc_18 =null ;
            JPanel _loc_19 =null ;
            AssetPane _loc_20 =null ;
            JLabel _loc_21 =null ;
            AssetIcon _loc_22 =null ;
            CustomButton _loc_23 =null ;
            Object _loc_24 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeTextField(ZLoc.t("Tickets","get_tickets_"+this.m_ticketTheme ),EmbeddedArt.titleFont ,TextFieldUtil.getLocaleFontSize(24,24,.get( {locale size "ja",18) }),EmbeddedArt.titleColor ,JLabel.LEFT );
            _loc_2.filters = EmbeddedArt.newtitleFilters;
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,20);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            AssetPane _loc_6 =new AssetPane(new m_assetDict.get( "streetFair_ticket_big") );
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_8 = ASwingHelper.makeLabel(ZLoc.t("Tickets",this.m_ticketTheme +"_you_have"),EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            this.m_ticketAmountLabel = ASwingHelper.makeLabel(String(Global.ticketManager.getCount(this.m_ticketTheme)), EmbeddedArt.titleFont, 20, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT);
            if (this.rand && this.rand.giftable)
            {
                _loc_9 = new CustomButton(ZLoc.t("Tickets", "send_tickets_" + this.m_ticketTheme), null, "GreenButtonUI");
            }
            else if (this.rand && this.rand.requestable)
            {
                _loc_9 = new CustomButton(ZLoc.t("Tickets", "get_tickets_request_" + this.m_ticketTheme), null, "GreenButtonUI");
            }
            _loc_9.setFont(new ASFont(EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(16, 11, null), false, false, false, EmbeddedArt.advancedFontProps));
            _loc_9.addActionListener(this.onCheck, 0, true);
            _loc_7.appendAll(_loc_8, this.m_ticketAmountLabel);
            if (!this.isSunset())
            {
                _loc_7.appendAll(_loc_9);
            }
            _loc_5.appendAll(_loc_6, _loc_7);
            _loc_10 = ASwingHelper.makeLabel(ZLoc.t("Tickets","buy_tickets_"+this.m_ticketTheme ),EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            _loc_11 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            _loc_12 =Global.ticketManager.getTicketBundles(this.m_ticketTheme );
            int _loc_13 =0;
            while (_loc_13 < 3)
            {

                _loc_16 = _loc_12.get(_loc_13);
                _loc_17 = Global.gameSettings().getItemByName(_loc_16);
                _loc_18 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, 5);
                _loc_19 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
                ASwingHelper.setSizedBackground(_loc_19, new assetDict.get("streetFair_card_ticket"));
                _loc_20 = new AssetPane(new assetDict.get("streetFair_ticket_button"));
                _loc_21 = ASwingHelper.makeLabel(String(_loc_17.quantity), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT);
                _loc_19.appendAll(_loc_20, _loc_21);
                ASwingHelper.setEasyBorder(_loc_20, 12);
                ASwingHelper.setEasyBorder(_loc_21, 12);
                _loc_22 = new AssetIcon(new EmbeddedArt.icon_cash());
                _loc_23 = new CustomButton(String(_loc_17.cash), _loc_22, "CashButtonUI");
                _loc_23.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
                _loc_23.setMargin(new Insets(1, 3, 1, 3));
                _loc_23.addActionListener(this.onBuyBundle, 0, true);
                _loc_24 = new Object();
                this.m_buyBundleButtons.put(_loc_23,  _loc_16);
                _loc_18.appendAll(_loc_19, _loc_23);
                _loc_11.append(_loc_18);
                _loc_13++;
            }
            DisplayObject _loc_14 =(DisplayObject)new assetDict.get( "streetFair_subBox");
            _loc_4.appendAll(ASwingHelper.verticalStrut(10), _loc_8, _loc_5, ASwingHelper.verticalStrut(10), _loc_10, _loc_11);
            _loc_15 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            ASwingHelper.setBackground(_loc_15, _loc_14);
            _loc_15.append(_loc_4);
            ASwingHelper.setEasyBorder(_loc_4, 0, 13);
            _loc_4.setPreferredSize(new IntDimension(250, 230));
            _loc_3.appendAll(this.makeRewardRows(), _loc_15);
            _loc_1.appendAll(ASwingHelper.leftAlignElement(_loc_2), _loc_3);
            return _loc_1;
        }//end

        protected void  onCheck (Object param1)
        {
            if (this.rand && this.rand.giftable && (this.rand.freeGiftExperiment == "" || Global.experimentManager.getVariant(this.rand.freeGiftExperiment)))
            {
                FrameManager.navigateTo("Gifts.php?action=chooseRecipient&gift=" + this.rand.name + "&view=app");
            }
            else if (this.rand && this.rand.requestable)
            {
                Global.world.viralMgr.sendCustomRequest(this.rand.name, "ticket_" + this.m_ticketTheme + "_request");
            }
            return;
        }//end

        protected JPanel  makeRewardRows ()
        {
            _loc_1 =Global.ticketManager.getTicketRewards(this.m_ticketTheme );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,10);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,20);
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,20);
            int _loc_5 =0;
            int _loc_6 =0;
            while (_loc_6 < _loc_1.length())
            {

                if (_loc_6 < 3)
                {
                    _loc_3.appendAll(this.makeRewardCell(_loc_1.get(_loc_6).get("rewardName"), _loc_1.get(_loc_6).get("rewardCost")));
                }
                else
                {
                    _loc_4.appendAll(this.makeRewardCell(_loc_1.get(_loc_6).get("rewardName"), _loc_1.get(_loc_6).get("rewardCost")));
                }
                _loc_6++;
            }
            _loc_2.appendAll(_loc_3, _loc_4);
            return _loc_2;
        }//end

        protected JPanel  makeRewardCell (String param1 ,String param2 )
        {
            int _loc_3 =85;
            int _loc_4 =83;
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,3);
            _loc_6 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            ASwingHelper.setBackground(_loc_6, new assetDict.get("itemCard"));
            _loc_6.setPreferredSize(new IntDimension(_loc_3, _loc_4));
            AssetIcon _loc_7 =new AssetIcon(new assetDict.get( "streetFair_ticket_button") );
            CustomButton _loc_8 =new CustomButton(param2 ,_loc_7 ,"CoinsButtonUI");
            _loc_8.setFont(new ASFont(EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:15}]), false, false, false, EmbeddedArt.advancedFontProps));
            _loc_8.addActionListener(this.onBuyReward, 0, true);
            Object _loc_9 =new Object ();
            _loc_9.put("rewardName",  param1);
            _loc_9.put("rewardCost",  int(param2));
            this.m_buyButtons.put(_loc_8,  _loc_9);
            _loc_10 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1) != null)
            {
                ASwingHelper.createImageCardCell(_loc_6, _loc_10.icon);
            }
            _loc_5.appendAll(_loc_6, _loc_8);
            _loc_5.addEventListener(MouseEvent.ROLL_OVER, this.onRewardCellOver, false, 0, true);
            _loc_5.addEventListener(MouseEvent.ROLL_OUT, this.onRewardCellOut, false, 0, true);
            this.m_rewardCells.put(_loc_5,  _loc_10);
            return _loc_5;
        }//end

        private void  onRewardCellOver (MouseEvent event )
        {
            _loc_2 =(JPanel) event.target;
            _loc_3 = this.m_rewardCells.get(_loc_2) ;
            _loc_4 = _loc_2.parent.localToGlobal(new Point(_loc_2.getX (),_loc_2.getY ()));
            _loc_4 = this.globalToLocal(_loc_4);
            this.globalToLocal(_loc_4).x = _loc_4.x - _loc_2.width / 2;
            this.dispatchEvent(new DataItemEvent(DataItemEvent.SHOW_TOOLTIP, _loc_3, _loc_4, true, false));
            return;
        }//end

        private void  onRewardCellOut (MouseEvent event )
        {
            _loc_2 =(JPanel) event.target;
            _loc_3 = this.m_rewardCells.get(_loc_2) ;
            this.dispatchEvent(new DataItemEvent(DataItemEvent.HIDE_TOOLTIP, _loc_3, null, true, false));
            return;
        }//end

        private void  showToolTip (DataItemEvent event )
        {
            if (!this.m_toolTipWindow)
            {
                this.m_toolTipWindow = new JWindow(this);
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ItemCatalogToolTip _loc_3 =new ItemCatalogToolTip ();
            _loc_3.changeInfo(event.item);
            _loc_2.appendAll(_loc_3);
            ASwingHelper.prepare(_loc_3);
            this.m_toolTipWindow.setContentPane(_loc_2);
            this.m_toolTipWindow.setX(event.pt.x);
            this.m_toolTipWindow.setY(event.pt.y - _loc_3.height + 10);
            ASwingHelper.prepare(this.m_toolTipWindow);
            this.m_toolTipWindow.mouseChildren = false;
            this.m_toolTipWindow.mouseEnabled = false;
            this.m_toolTipWindow.show();
            Sounds.play("UI_mouseover");
            return;
        }//end

        private void  hideToolTip (DataItemEvent event )
        {
            this.m_toolTipWindow.hide();
            return;
        }//end

        protected JPanel  makeMeterPanel ()
        {
            Object _loc_4 =null ;
            JTextField _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,9);
            _loc_1.append(ASwingHelper.verticalStrut(-15));
            _loc_2 =Global.ticketManager.getTicketLevels(this.m_ticketTheme );
            _loc_2 = _loc_2.reverse();
            int _loc_3 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_5 = ASwingHelper.makeTextField("-" + _loc_4, EmbeddedArt.TITLE_FONT, 18, EmbeddedArt.whiteTextColor);
                _loc_5.filters = .get(new GlowFilter(EmbeddedArt.darkBlueTextColor, 1, 4, 4, 10, BitmapFilterQuality.LOW));
                _loc_1.appendAll(_loc_5);
                _loc_3++;
            }
            _loc_1.append(ASwingHelper.verticalStrut(8));
            _loc_1.setPreferredWidth(65);
            return _loc_1;
        }//end

        protected void  onBuyReward (AWEvent event )
        {
            _loc_2 =(CustomButton) event.target;
            _loc_3 = this.m_buyButtons.get(_loc_2) ;
            _loc_4 = _loc_3.get("rewardName") ;
            _loc_5 = _loc_3.get("rewardCost") ;
            _loc_6 =Global.ticketManager.redeemTickets(this.m_ticketTheme ,_loc_4 );
            if (Global.ticketManager.redeemTickets(this.m_ticketTheme, _loc_4))
            {
                Global.ticketManager.updateLevel(this.m_ticketTheme);
                this.refreshDialogView();
            }
            return;
        }//end

        protected void  onBuyBundle (AWEvent event )
        {
            _loc_2 =(CustomButton) event.target;
            _loc_3 = this.m_buyBundleButtons.get(_loc_2) ;
            _loc_4 =Global.gameSettings().getItemByName(_loc_3 );
            if (Global.player.canBuyCash(_loc_4.cash, true, false))
            {
                GameTransactionManager.addTransaction(new TBuyItem(_loc_4.name, 1));
                this.refreshDialogView();
            }
            return;
        }//end

        public void  refreshDialogView ()
        {
            this.m_ticketAmountLabel.setText(String(Global.ticketManager.getCount(this.m_ticketTheme)));
            this.m_ticketAmountLabel.setPreferredWidth(120);
            ASwingHelper.prepare(this.m_ticketAmountLabel);
            this.updateHeight(true);
            return;
        }//end

         protected Object  getCloseButtonBorder ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put("top",  3);
            _loc_1.put("left",  5);
            _loc_1.put("bottom",  2);
            _loc_1.put("right",  3);
            return _loc_1;
        }//end

         public void  close ()
        {
            this.m_buyButtons = null;
            this.m_buyBundleButtons = null;
            super.close();
            return;
        }//end

    }



