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
import Classes.orders.*;
import Classes.orders.Hotel.*;
import Display.RenameUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class HotelGuestsPanel extends JPanel
    {
        protected Dictionary m_assetDict ;
        protected HotelsDialogView m_view ;
        public static  int LIST_WIDTH =452;
        public static  int LIST_HEIGHT =350;
        public static  int ROW_MINIMUM =2;
        public static  int ROW_DISPLAY =3;

        public  HotelGuestsPanel (Dictionary param1 ,HotelsDialogView param2 )
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.m_assetDict = param1;
            this.m_view = param2;
            this.init();
            return;
        }//end

        protected void  init ()
        {
            HotelGuest _loc_26 =null ;
            Dictionary _loc_27 =null ;
            Player _loc_28 =null ;
            Dictionary _loc_29 =null ;
            _loc_1 = this.m_assetDict.get( "spawner") ;
            _loc_2 = _loc_1as ICheckInHandler ;
            _loc_3 = _loc_1.getItem ().hotel ;
            _loc_4 = _loc_1.getItem ().getUI_skin_param("guest_panel_font_size")? (int(_loc_1.getItem().getUI_skin_param("guest_panel_font_size"))) : (14);
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.m_view.getLocKeyBaseDialog("guestsblurb")),400,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,_loc_4 ,EmbeddedArt.brownTextColor );
            JPanel _loc_6 =new JPanel(new BorderLayout ());
            _loc_7 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",this.m_view.getLocKeyBaseDialog("friendsasguests")),EmbeddedArt.defaultFontNameBold ,10,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            _loc_8 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",this.m_view.getLocKeyBaseDialog("vipsuites")),EmbeddedArt.defaultFontNameBold ,10,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            _loc_9 = _loc_3.floors.get(0).roomCount -_loc_2.getHotelVisitorCountForFloor(0);
            _loc_10 = ASwingHelper.makeLabel(_loc_9.toString (),EmbeddedArt.titleFont ,14,EmbeddedArt.blueTextColor ,JLabel.LEFT );
            _loc_11 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_11.appendAll(_loc_8, _loc_10);
            ASwingHelper.setEasyBorder(_loc_11, 0, 0, 0, 15);
            _loc_6.append(_loc_11, BorderLayout.EAST);
            _loc_6.append(_loc_7, BorderLayout.WEST);
            ASwingHelper.setEasyBorder(_loc_5, 0, 7);
            ASwingHelper.setEasyBorder(_loc_7, 0, 7);
            Sprite _loc_12 =new Sprite ();
            _loc_12.graphics.beginFill(EmbeddedArt.blueTextColor);
            _loc_12.graphics.drawRect(3, 0, 465, 2);
            _loc_12.graphics.moveTo(0, 2);
            _loc_12.graphics.endFill();
            Shape _loc_13 =new Shape ();
            Matrix _loc_14 =new Matrix ();
            _loc_14.createGradientBox(163, 54, Math.PI / 180 * 90, 0, 0);
            Array _loc_15 =.get(EmbeddedArt.lightBlueTextColor ,16777215) ;
            Array _loc_16 =.get(1 ,1) ;
            Array _loc_17 =.get(0 ,255) ;
            _loc_13.graphics.beginGradientFill(GradientType.LINEAR, _loc_15, _loc_16, _loc_17, _loc_14);
            _loc_13.graphics.drawRect(4, 2, 465, 54);
            _loc_13.graphics.endFill();
            _loc_12.addChild(_loc_13);
            AssetPane _loc_18 =new AssetPane(_loc_12 );
            _loc_19 = Global.world.orderMgr.getOrdersByType(OrderType.HOTEL);
            Array _loc_20 =new Array ();
            int _loc_21 =0;
            int _loc_22 =0;
            while (_loc_22 < _loc_19.length())
            {

                if (_loc_1.getId() == _loc_19.get(_loc_22).hotelID)
                {
                    _loc_26 = _loc_19.get(_loc_22).guestData;
                    _loc_27 = new Dictionary(true);
                    _loc_28 = Global.player.findFriendById(_loc_26.guestID);
                    if (_loc_28)
                    {
                        _loc_27.put("name",  Global.player.findFriendById(_loc_26.guestID).name);
                    }
                    else
                    {
                        _loc_27.put("name",  "");
                    }
                    _loc_27.put("guestId",  _loc_26.guestID);
                    _loc_27.put("vipStatus",  _loc_26.VIPStatus);
                    _loc_27.put("hotelId",  _loc_1.getId());
                    _loc_27.put("hotel",  _loc_1);
                    _loc_27.put("user_picture",  ASwingHelper.makeFriendImageFromZid(_loc_26.guestID));
                    if (_loc_26.floor == 0)
                    {
                        _loc_27.put("room",  ZLoc.t("Dialogs", this.m_view.getLocKeyBaseDialog("friendsFloorVip"), {amount:_loc_1.getItem().hotel.floors.get(_loc_26.floor).guestUpgradeBonus}));
                    }
                    else
                    {
                        _loc_27.put("room",  ZLoc.t("Dialogs", this.m_view.getLocKeyBaseDialog("friendsFloor"), {floor:_loc_26.floor, amount:_loc_1.getItem().hotel.floors.get(_loc_26.floor).guestUpgradeBonus}));
                    }
                    _loc_27.put("num",  _loc_21 + 1);
                    _loc_27.put("message",  _loc_26.message);
                    _loc_27.put("guest",  _loc_26);
                    _loc_27.put("removeMessage",  ZLoc.t("Dialogs", this.m_view.getLocKeyBaseDialog("removeMessage")));
                    _loc_27.put("blankRecord",  false);
                    _loc_20.push(_loc_27);
                }
                _loc_22++;
            }
            _loc_22 = _loc_21;
            while (_loc_22 < ROW_MINIMUM)
            {

                _loc_29 = new Dictionary(true);
                _loc_29.put("blankRecord",  true);
                _loc_29.put("num",  _loc_21 + 1);
                _loc_20.push(_loc_29);
                _loc_22++;
            }
            Dictionary _loc_23 =new Dictionary(true );
            _loc_23.put("inviteButton",  true);
            _loc_23.put("num",  _loc_21 + 1);
            _loc_23.put("view",  this.m_view);
            _loc_23.put("hotel",  _loc_1);
            _loc_20.push(_loc_23);
            VectorListModel _loc_24 =new VectorListModel(_loc_20 );
            VerticalScrollingList _loc_25 =new VerticalScrollingList(this.m_assetDict ,_loc_24 ,new RenameCellFactory(HotelGuestsCell ,this.m_assetDict ),1,ROW_DISPLAY ,LIST_WIDTH ,LIST_HEIGHT ,true ,45);
            ASwingHelper.setEasyBorder(_loc_25, 0, 5, 0, 5);
            this.appendAll(ASwingHelper.verticalStrut(5), _loc_5, ASwingHelper.verticalStrut(10), _loc_6, _loc_18, ASwingHelper.verticalStrut(-40), _loc_25, ASwingHelper.verticalStrut(5));
            return;
        }//end

    }



