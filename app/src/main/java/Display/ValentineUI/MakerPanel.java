package Display.ValentineUI;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class MakerPanel extends JPanel
    {
        public Dictionary m_data ;
        protected Array m_messages ;
        protected int m_currentMessage =0;
        protected JPanel m_cardHolder ;
        protected String m_newMessage ;
        protected PicturePane m_picture ;
        public static  String PREPARE ="prepare";

        public  MakerPanel (Dictionary param1 ,LayoutManager param2 )
        {
            this.m_messages = new Array();
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.m_data = param1;
            this.init();
            return;
        }//end

        protected void  init ()
        {
            _loc_1 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","ValUI_introstring"),600,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.darkBrownTextColor );
            _loc_2 = this.createPicturePanel ();
            _loc_3 = ASwingHelper.centerElement(_loc_2 );
            CustomButton _loc_4 =new CustomButton(ZLoc.t("Dialogs","ValUI_sendValentine"),null ,"PinkButtonUI");
            _loc_4.addActionListener(this.sendValentine, 0, true);
            _loc_5 = ASwingHelper.centerElement(_loc_4 );
            this.appendAll(_loc_1, ASwingHelper.verticalStrut(-5), _loc_3, ASwingHelper.verticalStrut(-7), _loc_5);
            ASwingHelper.setEasyBorder(this, 5, 20);
            return;
        }//end

        protected void  sendValentine (AWEvent event )
        {
            ValentineManager.sendValentineCard(this.m_picture.leftID, this.m_picture.centerID, this.m_picture.rightID, this.m_newMessage);
            _loc_2 = this""+.m_picture.leftID +"_"+this.m_picture.centerID +"_"+this.m_picture.rightID ;
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "send");
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "send_creative", "left", String(this.m_picture.leftID));
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "send_creative", "center", String(this.m_picture.centerID));
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "send_creative", "right", String(this.m_picture.rightID));
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "send_creative", "combo", _loc_2);
            return;
        }//end

        protected JPanel  createPicturePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_picture = new PicturePane(this.m_data);
            ASwingHelper.setSizedBackground(_loc_1, this.m_picture, new Insets(10, 0, 15, 0));
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","ValUI_changeText"),null ,"PurpleButtonUI");
            _loc_3 = ASwingHelper.centerElement(_loc_2 );
            this.m_cardHolder = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            this.m_cardHolder.setPreferredHeight(35);
            this.m_cardHolder.setMinimumHeight(35);
            this.m_cardHolder.setMaximumHeight(35);
            this.m_newMessage = ZLoc.t("Dialogs", "ValUI_message1");
            _loc_4 = ASwingHelper.makeMultilineCapsText(this.m_newMessage ,342,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,14,EmbeddedArt.whiteTextColor );
            this.m_cardHolder.append(_loc_4);
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,50);
            CustomButton _loc_6 =new CustomButton(ZLoc.t("Dialogs","ValUI_change"),null ,"PurpleButtonUI");
            CustomButton _loc_7 =new CustomButton(ZLoc.t("Dialogs","ValUI_change"),null ,"PurpleButtonUI");
            CustomButton _loc_8 =new CustomButton(ZLoc.t("Dialogs","ValUI_change"),null ,"PurpleButtonUI");
            _loc_2.addActionListener(this.launchChangeText, 0, true);
            _loc_6.addActionListener(this.m_picture.swapSender, 0, true);
            _loc_7.addActionListener(this.m_picture.swapObject, 0, true);
            _loc_8.addActionListener(this.m_picture.swapRecipient, 0, true);
            _loc_5.appendAll(_loc_6, _loc_7, _loc_8);
            _loc_1.appendAll(ASwingHelper.verticalStrut(25), this.m_cardHolder, _loc_3, ASwingHelper.verticalStrut(200), _loc_5);
            return _loc_1;
        }//end

        protected void  launchChangeText (AWEvent event )
        {
            InputTextDialogNormal _loc_2 =new InputTextDialogNormal(ZLoc.t("Dialogs","ValUI_ChangeText"),"ValUI_Change","",this.m_newMessage ,50,0,this.changeText ,true ,null ,true );
            _loc_2.textField.addEventListener(Event.CHANGE, this.onNameDialogChange);
            UI.displayPopup(_loc_2);
            return;
        }//end

        protected void  changeText (GenericPopupEvent event )
        {
            this.m_cardHolder.removeAll();
            _loc_2 = ASwingHelper.makeMultilineCapsText(this.m_newMessage ,342,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,14,EmbeddedArt.whiteTextColor );
            this.m_cardHolder.append(_loc_2);
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.CARDMAKER, "textchange", event.button == 0 ? ("close") : ("ok"));
            return;
        }//end

        protected void  onNameDialogChange (Event event )
        {
            _loc_2 =(TextField) event.target;
            if (_loc_2)
            {
                this.m_newMessage = _loc_2.text;
            }
            return;
        }//end

    }



