package Display.DialogUI;

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
import Display.aswingui.*;
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class FeedDialogView extends GenericDialogView
    {
        protected String m_action ;
        protected String m_caption ;
        protected String m_inputField ;
        public JTextField m_textField ;
        protected Function m_successCallback ;
        protected AssetPane m_iconPane ;
        protected Object m_titleTokens ;
        protected String m_viralType ;
        public static  int DESCRIPTION_MIN_WIDTH =300;
        public static  int DESCRIPTION_MIN_HEIGHT =90;
        private static  double ICON_WIDTH =90;
        private static  double ICON_HEIGHT =90;

        public  FeedDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 ,String param7 ,String param8 ,Function param9 =null ,int param10 =25,Function param11 =null ,String param12 ="",int param13 =0,Function param14 =null )
        {
            this.m_viralType = param2;
            this.m_action = param3;
            this.m_caption = param5;
            this.m_inputField = param7;
            this.m_successCallback = param9;
            if (param8 != Global.player.uid && param8 != null)
            {
                this.m_titleTokens = {};
                this.m_titleTokens.put("friend_user",  ZLoc.tn(Global.player.getFriendFirstName(param8), Global.player.getFriendGender(param8)));
            }
            super(param1, param4, param6, param10, param11, param12, param13, "", param14);
            return;
        }//end

         protected Object  getTitleTokens ()
        {
            return this.m_titleTokens;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            _loc_1 = this.createHeaderPanel ();
            _loc_1.name = "headerPanel";
            this.append(_loc_1);
            this.append(this.createInteriorHolder());
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_9 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.CENTER ));
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT,10);
            _loc_3 = (DisplayObject)assetDict.get("dialog_icon");
            this.m_iconPane = new AssetPane(_loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_5 = Math.max(DESCRIPTION_MIN_WIDTH,title.getTextField().textWidth-_loc_3.width);
            _loc_6 = ASwingHelper.makeMultilineText(this.m_action,_loc_5,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,16,EmbeddedArt.darkBrownTextColor,null,true);
            _loc_7 = ASwingHelper.makeMultilineText(m_message,_loc_5,EmbeddedArt.defaultFontName,TextFormatAlign.LEFT,12,30109,null,true);
            _loc_8 = ASwingHelper.makeMultilineText(this.m_caption,_loc_5,EmbeddedArt.defaultFontName,TextFormatAlign.LEFT,12,30109,null,true);
            _loc_4.appendAll(_loc_6, ASwingHelper.verticalStrut(5), _loc_7, _loc_8);
            ASwingHelper.setEasyBorder(_loc_1, 30, 20, 0, 20);
            _loc_2.append(this.m_iconPane);
            _loc_2.append(_loc_4);
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(70));
            this.m_textField = this.createTextField();
            _loc_1.append(ASwingHelper.verticalStrut(-20));
            _loc_1.append(this.m_textField);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_9 = createButtonPanel();
                _loc_1.append(_loc_9);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        public void  setIcon (DisplayObject param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (param1.width > FeedDialogView.ICON_WIDTH)
            {
                _loc_2 = FeedDialogView.ICON_WIDTH / param1.width;
                param1.width = int(param1.width * _loc_2);
                param1.height = int(param1.height * _loc_2);
            }
            if (param1.height > FeedDialogView.ICON_HEIGHT)
            {
                _loc_3 = FeedDialogView.ICON_HEIGHT / param1.height;
                param1.width = int(param1.width * _loc_3);
                param1.height = int(param1.height * _loc_3);
            }
            this.m_iconPane.setAsset(param1);
            if (param1.height < DESCRIPTION_MIN_HEIGHT)
            {
                ASwingHelper.setForcedHeight(this.m_iconPane, DESCRIPTION_MIN_HEIGHT);
            }
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT,5);
            _loc_3 = createCloseButtonPanel();
            _loc_4 = this.createTitlePanel();
            _loc_2.append(_loc_3);
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(_loc_4);
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, 0);
            return _loc_1;
        }//end

         protected JPanel  createTitlePanel ()
        {
            m_titleFontSize = 28;
            return super.createTitlePanel();
        }//end

        protected JTextField  createTextField ()
        {
            _loc_1 = ASwingHelper.makeTextField(this.m_inputField,EmbeddedArt.defaultSerifFont,16,6710886);
            _loc_1.setEditable(true);
            _loc_1.setFont(new ASFont("_sans", 16, false, false, false));
            _loc_1.getTextField().selectable = true;
            _loc_1.setWordWrap(true);
            _loc_1.setBackground(ASwingHelper.getWhite());
            _loc_1.setBorder(new EmptyBorder(null, new Insets(2, 5, 0, 5)));
            _loc_1.setPreferredHeight(54);
            _loc_1.getTextField().addEventListener(MouseEvent.MOUSE_UP, this.highlightCurrentText, false, 0, true);
            return _loc_1;
        }//end

        protected Component  createTextFieldContainer (Component param1 )
        {
            JPanel _loc_2 =new JPanel(new FlowLayout(FlowLayout.CENTER ,0,0));
            _loc_2.append(param1);
            return _loc_2;
        }//end

        private void  highlightCurrentText (MouseEvent event )
        {
            _loc_2 = (TextField)event.target
            if (_loc_2 && _loc_2.text == this.m_inputField)
            {
                _loc_2.setSelection(0, _loc_2.text.length());
            }
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            if (this.m_textField.getText() != this.m_inputField && this.m_textField.getText() != "")
            {
                countDialogViewAction(StatsPhylumType.FEED_DIALOG_SHARE, false, 1, this.m_viralType, "text");
                this.m_successCallback(this.m_textField.getText());
            }
            else
            {
                countDialogViewAction(StatsPhylumType.FEED_DIALOG_SHARE, false, 1, this.m_viralType, "null");
                this.m_successCallback("");
            }
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES, true));
            if (m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_closeCallback);
            }
            closeMe();
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            countDialogViewAction(StatsPhylumType.FEED_DIALOG_CLOSE, false, 1, this.m_viralType);
            onCancel(param1);
            return;
        }//end

    }




