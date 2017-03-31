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
import Classes.orders.Valentines2011.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class ValentineCardViewDialogView extends GenericDialogView
    {
        protected Array m_cardData ;
        protected Admirer m_admirer ;
        protected Loader m_loader ;
        private AssetPane m_picPane ;
        protected int m_currentCard =0;
        protected JPanel m_cardHolder ;
        protected PicturePane m_activeCard ;
        protected AssetPane m_cardMessage ;
        protected JButton m_leftButton ;
        protected JButton m_rightButton ;
        protected TextFormat m_tform ;
        protected JPanel m_textHolder ;

        public  ValentineCardViewDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            this.m_cardData = param1.get("cardData");
            this.m_admirer = param1.get("admirer");
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 10));
            }
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            CustomButton _loc_2 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            if (this.m_admirer.uid == "-1")
            {
                _loc_2.setEnabled(false);
            }
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, SoftBoxLayout.TOP));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(30));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            _loc_3 = this.createButtonPanel ();
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,6);
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,13);
            ASwingHelper.setEasyBorder(_loc_2, 0, 3);
            _loc_3 = this.createSenderHolder ();
            this.m_leftButton = new JButton();
            this.m_rightButton = new JButton();
            this.m_currentCard = this.m_cardData.get("length") - 1;
            this.m_cardHolder = this.createCardWithTitle(this.m_currentCard);
            this.m_leftButton.wrapSimpleButton(new SimpleButton(m_assetDict.get("market_prev_up"), m_assetDict.get("market_prev_over"), m_assetDict.get("market_prev_down"), m_assetDict.get("market_prev_up")));
            this.m_rightButton.wrapSimpleButton(new SimpleButton(m_assetDict.get("market_next_up"), m_assetDict.get("market_next_over"), m_assetDict.get("market_next_down"), m_assetDict.get("market_next_up")));
            this.m_leftButton.addActionListener(this.movePrev, 0, true);
            this.m_rightButton.addActionListener(this.moveNext, 0, true);
            this.m_leftButton.setEnabled(false);
            if (this.m_cardData.get("length") <= 1)
            {
                this.m_rightButton.setEnabled(false);
            }
            ASwingHelper.setEasyBorder(this.m_leftButton, 2);
            ASwingHelper.setEasyBorder(this.m_rightButton, 2);
            _loc_2.appendAll(this.m_leftButton, this.m_cardHolder, this.m_rightButton);
            _loc_1.appendAll(_loc_3, _loc_2);
            return _loc_1;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancel, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 3, 0, 0, 10);
            return _loc_1;
        }//end

        protected void  movePrev (AWEvent event )
        {
            this.m_currentCard++;
            _loc_2 = this.m_cardData.get(this.m_currentCard) ;
            _loc_3 = _loc_2.message ;
            this.m_activeCard.changeCard(_loc_2);
            _loc_4 = this.m_cardMessage.getAsset ()as TextField ;
            (this.m_cardMessage.getAsset() as TextField).text = _loc_3.toUpperCase();
            _loc_4.setTextFormat(this.m_tform);
            this.m_cardMessage.setAsset(_loc_4);
            if (this.m_currentCard > 0)
            {
                this.m_rightButton.setEnabled(true);
            }
            if (this.m_currentCard == (this.m_cardData.get("length") - 1))
            {
                this.m_leftButton.setEnabled(false);
            }
            Object _loc_5 =new Object ();
            _loc_5.index = this.m_currentCard;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.REFRESH_ACHIEVEMENT, _loc_5, true));
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  moveNext (AWEvent event )
        {
            this.m_currentCard--;
            _loc_2 = this.m_cardData.get(this.m_currentCard) ;
            _loc_3 = _loc_2.message ;
            this.m_activeCard.changeCard(_loc_2);
            _loc_4 = this.m_cardMessage.getAsset ()as TextField ;
            (this.m_cardMessage.getAsset() as TextField).text = _loc_3.toUpperCase();
            _loc_4.setTextFormat(this.m_tform);
            this.m_cardMessage.setAsset(_loc_4);
            if (this.m_leftButton.isEnabled() == false)
            {
                this.m_leftButton.setEnabled(true);
            }
            if (this.m_currentCard == 0)
            {
                this.m_rightButton.setEnabled(false);
            }
            Object _loc_5 =new Object ();
            _loc_5.index = this.m_currentCard;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.REFRESH_ACHIEVEMENT, _loc_5, true));
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  createSenderHolder ()
        {
            DisplayObject _loc_2 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            this.m_picPane = new AssetPane();
            this.m_picPane.setPreferredSize(new IntDimension(50, 50));
            if (this.m_admirer.portrait != null)
            {
                this.m_loader = LoadingManager.load(this.m_admirer.portrait, this.onPortraitLoad, LoadingManager.PRIORITY_HIGH);
            }
            else
            {
                _loc_2 =(DisplayObject) new EmbeddedArt.hud_no_profile_pic();
                this.m_picPane.setAsset(_loc_2);
            }
            _loc_3 = this.m_admirer.name ;
            _loc_4 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","ValUIView_message",{friendName _loc_3 }),360,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.darkBrownTextColor );
            this.m_tform = new TextFormat(EmbeddedArt.titleFont, 14, EmbeddedArt.whiteTextColor, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_1.appendAll(this.m_picPane, _loc_4);
            _loc_5 = ASwingHelper.centerElement(_loc_1 );
            return ASwingHelper.centerElement(_loc_1);
        }//end

        protected void  onPortraitLoad (Event event )
        {
            this.m_picPane.setAsset(event.target.content);
            return;
        }//end

        protected JPanel  createCardWithTitle (int param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 =Global.gameSettings().getValentinesAssets ();
            _loc_4 = this.m_cardData.get(param1) ;
            _loc_5 = this.m_cardData.get(param1).message ;
            this.m_textHolder = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
            this.m_textHolder.setPreferredHeight(35);
            this.m_textHolder.setMinimumHeight(35);
            this.m_textHolder.setMaximumHeight(35);
            this.m_cardMessage = ASwingHelper.makeMultilineCapsText(_loc_5, 342, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 14, EmbeddedArt.whiteTextColor);
            this.m_textHolder.append(this.m_cardMessage);
            this.m_activeCard = new PicturePane(_loc_3, _loc_4);
            ASwingHelper.setSizedBackground(_loc_2, this.m_activeCard);
            _loc_2.appendAll(ASwingHelper.verticalStrut(10), this.m_textHolder);
            return _loc_2;
        }//end

    }



