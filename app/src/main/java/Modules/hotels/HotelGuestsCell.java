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
import Classes.virals.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.Transactions.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class HotelGuestsCell extends JPanel implements GridListCell
    {
        protected Dictionary m_assetDict ;
        protected Dictionary m_data ;
        protected AssetPane m_textField ;
        protected CustomButton m_vipButton ;
        protected CustomButton m_inviteButton ;
        protected HotelsDialogView m_view ;
        protected MechanicMapResource m_owner ;
        protected ICheckInHandler m_checkinHandler ;
        private static  int INVITE_BUTTON_WIDTH =300;

        public  HotelGuestsCell (Dictionary param1 ,LayoutManager param2 )
        {
            this.m_assetDict = param1;
            super(ASwingHelper.softBoxLayoutVertical);
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_data = param1;
            this.buildCell();
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_data;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  makeBackground ()
        {
            Sprite _loc_1 =null ;
            if (int(this.m_data.get("num")) % 2 == 1)
            {
                _loc_1 = new Sprite();
                _loc_1.graphics.beginFill(15135738);
                _loc_1.graphics.drawRect(0, 0, 462, 113);
                _loc_1.graphics.endFill();
                ASwingHelper.setSizedBackground(this, _loc_1);
            }
            return;
        }//end

        protected void  buildCell ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            SimpleButton _loc_4 =null ;
            MarginBackground _loc_5 =null ;
            this.makeBackground();
            if (this.m_data.get("blankRecord"))
            {
                return;
            }
            if (this.m_data.get("inviteButton"))
            {
                this.m_view = this.m_data.get("view");
                _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_1.append(this.makeInviteButtonPanel());
                this.appendAll(ASwingHelper.verticalStrut(20), _loc_1);
                return;
            }
            _loc_1 = new JPanel(new BorderLayout());
            _loc_1.append(this.makeLeftPanel(), BorderLayout.WEST);
            _loc_1.append(this.makeRightPanel(), BorderLayout.EAST);
            this.appendAll(_loc_1, ASwingHelper.verticalStrut(-15));
            if (this.m_data.get("message") != null && this.m_data.get("message") != "")
            {
                this.m_textField = ASwingHelper.makeMultilineText(this.m_data.get("message"), 397, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.brownTextColor);
                ASwingHelper.setEasyBorder(this.m_textField, 18, 10, 5);
                _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
                _loc_2.append(this.m_textField);
                _loc_3 =(DisplayObject) new this.m_assetDict.get("hotels_deleteMessageX");
                _loc_4 = new SimpleButton(_loc_3, _loc_3, _loc_3, _loc_3);
                _loc_4.addEventListener(MouseEvent.CLICK, this.deleteMessage);
                _loc_2.addChild(_loc_4);
                _loc_4.x = 390;
                _loc_4.y = 10;
                _loc_5 = new MarginBackground(new this.m_assetDict.get("hotels_friendMessage"), new Insets(0, 5, 0, 5));
                _loc_2.setBackgroundDecorator(_loc_5);
                if (this.m_data.get("message") != this.m_data.get("removeMessage"))
                {
                    this.append(_loc_2);
                }
            }
            return;
        }//end

        protected JPanel  makeInviteButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_inviteButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "Invite")), null, "BigGreenButtonUI");
            this.m_inviteButton.setPreferredWidth(INVITE_BUTTON_WIDTH);
            this.m_inviteButton.setEnabled(Global.world.viralMgr.canPost(ViralType.HOTEL_CHECKIN + this.m_view.getViralSuffix()));
            this.m_inviteButton.addActionListener(this.onInviteGuestClick);
            this.m_owner = this.m_data.get("hotel");
            this.m_checkinHandler =(ICheckInHandler) this.m_owner;
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            if (this.m_view)
            {
                this.m_view.setGuestListInviteButton(this.m_inviteButton);
            }
            _loc_1.append(this.m_inviteButton);
            return _loc_1;
        }//end

        protected void  onInviteGuestClick (AWEvent event )
        {
            this.m_inviteButton.setEnabled(false);
            if (this.m_view)
            {
                this.m_view.onInviteGuestClick(event);
            }
            return;
        }//end

        protected void  onMechanicDataChanged (Event event )
        {
            if (this.m_checkinHandler.getPeepCount() == this.m_checkinHandler.getPeepMax())
            {
                this.m_inviteButton.setEnabled(false);
            }
            return;
        }//end

        protected JPanel  makeLeftPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = this.m_data.get( "user_picture") ;
            _loc_2.setPreferredHeight(_loc_3.getHeight());
            _loc_4 = ASwingHelper.makeLabel(this.m_data.get( "name").toString ().toUpperCase (),EmbeddedArt.defaultFontNameBold ,13,1275549,JLabel.LEFT );
            _loc_5 = ASwingHelper.makeLabel(this.m_data.get( "room").toString ().toUpperCase (),EmbeddedArt.defaultFontNameBold ,11,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            _loc_2.appendAll(_loc_4, _loc_5);
            _loc_1.appendAll(_loc_3, _loc_2);
            ASwingHelper.setEasyBorder(_loc_1, 5, 5, 5, 4);
            return _loc_1;
        }//end

        protected JPanel  makeRightPanel ()
        {
            boolean _loc_2 =false ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            switch(this.m_data.get("vipStatus"))
            {
                case HotelGuest.VIP_ACCEPTED:
                {
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", "HotelDialog_isVIP"), null, "GreenButtonUI");
                    _loc_2 = false;
                    break;
                }
                case HotelGuest.VIP_NOTREQUESTED:
                {
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", "HotelDialog_makeVIP"), null, "GreenButtonUI");
                    this.m_vipButton.addActionListener(this.onGrantVIP);
                    _loc_2 = true;
                    break;
                }
                case HotelGuest.VIP_REQUESTED:
                {
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", "HotelDialog_grantVIP"), null, "GreenButtonUI");
                    this.m_vipButton.addActionListener(this.onGrantVIP);
                    _loc_2 = true;
                    break;
                }
                case HotelGuest.VIP_GRANTED:
                {
                    this.m_vipButton = new CustomButton(ZLoc.t("Dialogs", "HotelDialog_grantedVIP"), null, "GreenButtonUI");
                    _loc_2 = false;
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (this.m_data.get("guest").floor == 0)
            {
                _loc_2 = false;
            }
            this.m_vipButton.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_vipButton.setEnabled(_loc_2);
            ASwingHelper.setEasyBorder(_loc_1, 5, 0, 0, 5);
            _loc_1.append(this.m_vipButton);
            return _loc_1;
        }//end

        protected void  onGrantVIP (AWEvent event )
        {
            String _loc_2 ="hotel";
            if (this.m_data.get("hotel") as MapResource)
            {
                _loc_2 = (this.m_data.get("hotel") as MapResource).getItemName();
            }
            if (this.m_data.get("hotel").getGuestVIPStatus(Global.player.uid, this.m_data.get("guestId")) == HotelGuest.VIP_REQUESTED)
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_2, "owner_panel_ui", "grant_vip");
            }
            else
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_2, "owner_panel_ui", "invite_vip");
            }
            if (Global.world.viralMgr.canPost(ViralType.HOTEL_GRANTVIP, this.m_data.get("guestId")))
            {
                Global.world.viralMgr.sendHotelVIPFeed(this.m_data.get("hotelId"), Global.player.uid, this.m_data.get("guestId"));
            }
            GameTransactionManager.addTransaction(new TMechanicAction(this.m_data.get("hotel"), "hotelVisitClosed", "GMVisit", {operation:"grantVIP", guestId:this.m_data.get("guestId"), hotelId:this.m_data.get("hotelId")}));
            StatsManager.social(StatsCounterType.SOCIAL_VERB_UPGRADE, this.m_data.get("guestId"), _loc_2, TrackedActionType.HOTEL_VIPGRANT, "", "", 1);
            this.m_data.get("hotel").setGuestVIPStatus(Global.player.uid, this.m_data.get("guestId"), HotelGuest.VIP_GRANTED);
            this.m_vipButton.setText(ZLoc.t("Dialogs", "HotelDialog_grantedVIP"));
            this.m_vipButton.setEnabled(false);
            this.m_vipButton.repaintAndRevalidate();
            return;
        }//end

        protected void  deleteMessage (MouseEvent event )
        {
            String _loc_2 ="hotel";
            if (this.m_data.get("hotel") as MapResource)
            {
                _loc_2 = (this.m_data.get("hotel") as MapResource).getItemName();
            }
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_2, "owner_panel_ui", "delete_message");
            _loc_3 = this.m_data.get( "removeMessage") ;
            GameTransactionManager.addTransaction(new THotelGuestMessage(Global.player.uid, this.m_data.get("guestId"), this.m_data.get("hotelId"), _loc_3));
            this.m_textField.setAsset(ASwingHelper.makeMultilineText(_loc_3, 397, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.brownTextColor));
            this.m_data.get("guest").message = _loc_3;
            return;
        }//end

    }



