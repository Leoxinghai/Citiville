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
import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Events.*;
import Mechanics.Transactions.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class SocialBusinessDialogView extends HotelsDialogView
    {

        public  SocialBusinessDialogView (Dictionary param1 ,int param2 ,String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected void  init ()
        {
            Object _loc_2 =null ;
            super.init();
            _loc_1 = m_owner.getMechanicData();
            if (_loc_1.get("harvestState"))
            {
                _loc_2 = _loc_1.get("harvestState");
                if (_loc_2.hasOwnProperty("cashFilled") && _loc_2.get("cashFilled") == true)
                {
                    m_inviteButton.setEnabled(false);
                    m_guestListInvitebutton.setEnabled(false);
                }
            }
            return;
        }//end

        protected int  getNumFriends ()
        {
            _loc_1 = m_owner.getMechanicData();
            _loc_2 = _loc_1.get("harvestState") ;
            if (_loc_2.hasOwnProperty("cashFilled") && _loc_2.get("cashFilled") == true)
            {
                return ((CheckinMechanicResource)m_owner).getTotalRooms();
            }
            return ((SocialBusiness)m_owner).visitorOrdersForHotel().length;
        }//end

        protected int  getMaxFriends ()
        {
            return ((CheckinMechanicResource)m_owner).getTotalRooms();
        }//end

        protected int  getFriendBonus ()
        {
            _loc_1 = (CheckinMechanicResource)m_owner(
            _loc_2 = (CheckinMechanicResource)m_owner(
            _loc_3 = m_owner.getItem ().hotel.floors.get(_loc_1) ;
            _loc_4 = _loc_3.socialBusinessHarvestBonus *_loc_2 ;
            return _loc_3.socialBusinessHarvestBonus * _loc_2;
        }//end

        protected int  getBaseFriendBonus ()
        {
            _loc_1 = (CheckinMechanicResource)m_owner(
            _loc_2 = m_owner.getItem ().hotel.floors.get(_loc_1) ;
            return _loc_2.socialBusinessHarvestBonus;
        }//end

        protected int  getUpgradedFriendBonus ()
        {
            ItemDefinitionHotelFloor _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_1 = (CheckinMechanicResource)m_owner(
            Array _loc_2 =new Array ();
            _loc_3 = m_owner.getItem().hotel.floors;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4.floorID != _loc_1 && _loc_4.floorID != 0)
                {
                    _loc_2.push(_loc_4);
                }
            }
            _loc_4 = null;
            _loc_5 = 0;
            _loc_6 = 0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_5 = ((CheckinMechanicResource)m_owner).getHotelVisitorCountForFloor(_loc_4.floorID);
                _loc_6 = _loc_6 + _loc_4.socialBusinessHarvestBonus * _loc_5;
            }
            return _loc_6;
        }//end

        protected int  getBaseUpgradedFriendBonus ()
        {
            ItemDefinitionHotelFloor _loc_3 =null ;
            _loc_1 = (CheckinMechanicResource)m_owner(
            _loc_2 = m_owner.getItem().hotel.floors;
            int _loc_4 =0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.floorID != _loc_1 && _loc_3.floorID != 0)
                {
                    _loc_4 = _loc_3.socialBusinessHarvestBonus;
                    break;
                }
            }
            return _loc_4;
        }//end

        protected int  getVipFriendBonus ()
        {
            _loc_1 = (CheckinMechanicResource)m_owner(
            _loc_2 = m_owner.getItem ().hotel.floors.get(0) ;
            _loc_3 = _loc_2.socialBusinessHarvestBonus *_loc_1 ;
            return _loc_3;
        }//end

        protected int  getBaseVipFriendBonus ()
        {
            _loc_1 = m_owner.getItem ().hotel.floors.get(0) ;
            return _loc_1.socialBusinessHarvestBonus;
        }//end

         protected void  addGuest (GenericPopupEvent event )
        {
            Dictionary _loc_2 =null ;
            Object _loc_3 =null ;
            int _loc_4 =0;
            if (event.button == GenericPopup.ACCEPT)
            {
                _loc_2 = m_owner.getMechanicData();
                if (_loc_2.get("harvestState"))
                {
                    _loc_3 = _loc_2.get("harvestState");
                    if (_loc_3.hasOwnProperty("customers") && _loc_3.hasOwnProperty("customersReq"))
                    {
                        if (Global.player.cash > m_addGuestCashAmount && !_loc_3.hasOwnProperty("cashFilled"))
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
                            Global.player.cash = Global.player.cash - m_addGuestCashAmount;
                            m_inviteButton.setEnabled(false);
                            m_guestListInvitebutton.setEnabled(false);
                            m_addGuestsBtn.setEnabled(false);
                            _loc_3.put("cashFilled",  true);
                            m_owner.setDataForMechanic("harvestState", _loc_3, "NPCEnterAction");
                            GameTransactionManager.addTransaction(new TMechanicAction(m_owner, "hotelInterface", "GMPlay", {operation:"buyGuestWithSocialBonus", hotelId:m_owner.getId()}));
                            this.updateMechanicText();
                        }
                    }
                }
            }
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
            _loc_8 = this.getNumFriends ()+"/"+this.getMaxFriends ();
            int _loc_9 =16;
            if (this.getMaxFriends() > 999)
            {
                _loc_9 = 15;
            }
            m_guestsRatio = ASwingHelper.makeLabel(_loc_8, EmbeddedArt.titleFont, _loc_9, EmbeddedArt.lightOrangeTextColor);
            _loc_6.appendAll(_loc_7, m_guestsRatio);
            _loc_5.append(_loc_6);
            ASwingHelper.prepare(_loc_5);
            ASwingHelper.setEasyBorder(_loc_5, 8, 0, 3);
            _loc_10 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs",this.getLocKeyBaseDialog("addGuest")));
            _loc_11 = ASwingHelper.makeLabel(_loc_10 ,EmbeddedArt.defaultFontNameBold ,11,EmbeddedArt.brownTextColor );
            AssetIcon _loc_12 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            _loc_13 = this.getMaxFriends ();
            _loc_14 = this.getMaxFriends ()-this.getNumFriends ();
            _loc_15 = m_owner.getItem().hotel.guestFillCost;
            m_addGuestCashAmount = Math.ceil(_loc_14 * _loc_15 / _loc_13);
            m_addGuestsBtn = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:m_addGuestCashAmount})), _loc_12, "CashButtonUI");
            _loc_16 = m_addGuestsBtn.getFont();
            m_addGuestsBtn.setFont(_loc_16.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_16.getSize(), 1, [{locale:"it", ratio:0.84}])));
            m_addGuestsBtn.addActionListener(addGuestConfirm, 0, true);
            if (this.getNumFriends() == this.getMaxFriends())
            {
                m_addGuestsBtn.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(_loc_5, 30);
            _loc_1.appendAll(ASwingHelper.centerElement(_loc_5), _loc_11, ASwingHelper.centerElement(m_addGuestsBtn));
            return _loc_1;
        }//end

         public void  updateMechanicText ()
        {
            _loc_1 = this.getNumFriends ();
            _loc_2 = this.getMaxFriends ();
            m_guestsRatio.setText(_loc_1 + "/" + _loc_2);
            ASwingHelper.prepare(m_guestsRatio);
            _loc_3 = _loc_2-_loc_1 ;
            _loc_4 = m_owner.getItem().hotel.guestFillCost;
            m_addGuestCashAmount = Math.ceil(_loc_3 * _loc_4 / _loc_2);
            m_addGuestsBtn.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:m_addGuestCashAmount})));
            _loc_5 = this.getFriendBonus ();
            _loc_6 = this.getUpgradedFriendBonus ();
            _loc_7 = this.getVipFriendBonus ();
            int _loc_8 =0;
            _loc_9 = m_owner.getMechanicData();
            _loc_10 = m_owner.getMechanicData ().get( "harvestState") ;
            if (m_owner.getMechanicData().get("harvestState").hasOwnProperty("cashFilled") && _loc_10.get("cashFilled") == true)
            {
                _loc_8 = ((SocialBusiness)m_owner).maxPossibleHarvestBonus;
            }
            else
            {
                _loc_8 = _loc_5 + _loc_6 + _loc_7;
            }
            _loc_11 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipFriends"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{countthis.getBaseFriendBonus()});
            _loc_12 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipShip"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{countthis.getBaseUpgradedFriendBonus()});
            _loc_13 = ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipBusiness"))+" = "+ZLoc.t("Dialogs",this.getLocKeyBaseDialog("tooltipNumGuestsP"),{countthis.getBaseVipFriendBonus()});
            this.updateTooltipText(_loc_11, _loc_12, _loc_13, _loc_8);
            return;
        }//end

         protected DisplayObject  getNpcHost ()
        {
            DisplayObject _loc_1 =(DisplayObject)new assetDict.get( "hotels_npc_host");
            _loc_1.x = 5;
            _loc_1.y = 35;
            return _loc_1;
        }//end

         protected JPanel  createGuestsPanel ()
        {
            return new SocialBusinessGuestsPanel(this.m_assetDict, this);
        }//end

         protected void  makeHelpPanel (Event event )
        {
            return;
        }//end

    }



