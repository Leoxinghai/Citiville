package Display.FreeGiftMFS;

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
import Classes.Managers.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FreeGiftMFSDialogView extends GenericDialogView
    {
        protected String m_chosenGiftName ;
        protected JPanel m_chosenGiftImagePanel ;
        protected AssetPane m_chosenGiftNameLabel ;
        protected Sprite m_chosenGiftSprite ;
        protected Loader m_giftLoader ;
        protected Array m_recipients ;
        protected CustomButton m_sendButton ;

        public  FreeGiftMFSDialogView (Dictionary param1 ,String param2 ,Array param3 )
        {
            this.m_chosenGiftName = param2;
            this.m_recipients = param3;
            super(param1);
            this.loadAssets();
            return;
        }//end

        protected void  loadAssets ()
        {
            this.m_giftLoader = LoadingManager.load(Global.gameSettings().getImageByName(this.m_chosenGiftName, "icon"), this.onGiftLoad, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

         protected void  init ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            m_bgAsset =(DisplayObject) new assetDict.get("dialogBack");
            MarginBackground _loc_1 =new MarginBackground(m_bgAsset ,new Insets(0,0,20,0));
            this.setBackgroundDecorator(_loc_1);
            ASwingHelper.setForcedSize(this, new IntDimension(m_bgAsset.width, m_bgAsset.height + 20));
            Sprite _loc_2 =new Sprite ();
            DisplayObject _loc_3 =(DisplayObject)new assetDict.get( "samGift");
            _loc_2.addChild(_loc_3);
            this.addChild(_loc_2);
            _loc_2.x = 15;
            _loc_2.y = 30;
            Sprite _loc_4 =new Sprite ();
            DisplayObject _loc_5 =(DisplayObject)new assetDict.get( "mainPanel");
            _loc_4.addChild(_loc_5);
            this.addChild(_loc_4);
            _loc_5.width = m_bgAsset.width - 30;
            _loc_5.height = 340;
            _loc_4.x = (m_bgAsset.width - _loc_5.width) / 2;
            _loc_4.y = _loc_2.y + _loc_2.height - 10;
            Sprite _loc_6 =new Sprite ();
            DisplayObject _loc_7 =(DisplayObject)new assetDict.get( "pigeon");
            _loc_6.addChild(_loc_7);
            this.addChild(_loc_6);
            _loc_6.x = 15;
            _loc_6.y = _loc_2.y + _loc_3.height - _loc_7.height + 2;
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.appendAll(createHeaderPanel(), ASwingHelper.verticalStrut(3));
            this.appendAll(this.createBubbleAndGiftPanel(), ASwingHelper.verticalStrut(8));
            this.appendAll(this.createProgressPanel(), ASwingHelper.verticalStrut(1));
            this.appendAll(this.createListPanel(), ASwingHelper.verticalStrut(5));
            this.append(this.createButtonPanel());
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            String _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(32, 27, [{locale:"ja", size:32}]);
            _loc_2 = ZLoc.t("Dialogs", "SendGifts");
            _loc_2 = TextFieldUtil.formatSmallCapsString(_loc_2);
            title = ASwingHelper.makeTextField(_loc_2 + " ", EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
            title.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = this.getTitleTextSizeHeader(_loc_2.length());
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        public int  getTitleTextSizeHeader (int param1 )
        {
            if (param1 > 30)
            {
                return m_titleFontSize * 0.89;
            }
            return m_titleFontSize;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 2, 0, 0, 3);
            return _loc_1;
        }//end

        protected JPanel  createBubbleAndGiftPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_2 = (RecentlyPlayedMFSManager)Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS)
            _loc_1.append(ASwingHelper.horizontalStrut(130));
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_4 =(DisplayObject)new assetDict.get( "speechBubble");
            MarginBackground _loc_5 =new MarginBackground(_loc_4 );
            _loc_3.setBackgroundDecorator(_loc_5);
            ASwingHelper.setForcedSize(_loc_3, new IntDimension(400, 130));
            _loc_6 = ZLoc.t("Dialogs","recentlyPlayedMFS_message",{friendCount_loc_2.initialTotal});
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_8 = ASwingHelper.makeMultilineText(_loc_6,340,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,16,EmbeddedArt.blueTextColor);
            _loc_7.appendAll(ASwingHelper.verticalStrut(10), _loc_8);
            _loc_3.appendAll(ASwingHelper.horizontalStrut(40), _loc_7);
            _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(20));
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_chosenGiftSprite = new Sprite();
            DisplayObject _loc_10 =(DisplayObject)new assetDict.get( "giftWindow");
            MarginBackground _loc_11 =new MarginBackground(_loc_10 );
            _loc_12 = ZLoc.t("Items",this.m_chosenGiftName+"_friendlyName");
            this.m_chosenGiftNameLabel = ASwingHelper.makeMultilineText(_loc_12, _loc_10.width, EmbeddedArt.DEFAULT_FONT_NAME_BOLD, TextFormatAlign.CENTER, 14, EmbeddedArt.blueTextColor);
            _loc_9.setBackgroundDecorator(_loc_11);
            _loc_9.addChild(this.m_chosenGiftSprite);
            _loc_9.appendAll(ASwingHelper.verticalStrut(87), this.m_chosenGiftNameLabel);
            ASwingHelper.setForcedSize(_loc_9, new IntDimension(_loc_10.width, _loc_10.height));
            _loc_1.append(_loc_9);
            return _loc_1;
        }//end

        protected JPanel  createProgressPanel ()
        {
            _loc_1 = (RecentlyPlayedMFSManager)Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS)
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            DisplayObject _loc_3 =(DisplayObject)new assetDict.get( "meterBack");
            _loc_4 = _loc_1.numSent/_loc_1.initialTotal;
            _loc_5 = _loc_3.width;
            _loc_6 = Math.max(_loc_5*_loc_4,12);
            Dictionary _loc_7 =new Dictionary ();
            _loc_7.put("upgradeBar",  assetDict.get("meterBar"));
            _loc_7.put("upgradeBarBG",  assetDict.get("meterBack"));
            ProgressBarDictionary _loc_8 =new ProgressBarDictionary(_loc_7 ,_loc_5 ,14);
            _loc_8.setProgress(_loc_4);
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_10 = ZLoc.t("Dialogs","recentlyPlayedMFS_giftSent",{numGifts_loc_1.numSent,numAvailable.initialTotal});
            _loc_10 = TextFieldUtil.formatSmallCapsString(_loc_10);
            JLabel _loc_11 =new JLabel(_loc_10 );
            _loc_11.setFont(ASwingHelper.getTitleFont(16));
            _loc_11.setForeground(new ASColor(EmbeddedArt.lightOrangeTextColor));
            _loc_9.append(_loc_11);
            _loc_2.appendAll(_loc_9, ASwingHelper.horizontalStrut(10));
            _loc_2.appendAll(_loc_8, ASwingHelper.horizontalStrut(23));
            return _loc_2;
        }//end

        protected JPanel  createListPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            FlashMFSRecipientScrollingList _loc_2 =new FlashMFSRecipientScrollingList(this.m_recipients ,FlashMFSRecipientCellFactory ,0,3,8);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_sendButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "SendGifts")), null, "GreenWhiteButtonUI");
            this.m_sendButton.setFont(ASwingHelper.getTitleFont(22));
            ASwingHelper.setForcedHeight(this.m_sendButton, 50);
            this.m_sendButton.addEventListener(MouseEvent.CLICK, this.onSendClick, false, 0, true);
            _loc_1.append(this.m_sendButton);
            if ((Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS) as RecentlyPlayedMFSManager).numCurrentSelected == 0)
            {
                this.m_sendButton.setEnabled(false);
            }
            return _loc_1;
        }//end

        private void  onSendClick (MouseEvent event )
        {
            (Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS) as RecentlyPlayedMFSManager).onSend();
            return;
        }//end

        private void  onGiftLoad (Event event )
        {
            DisplayObject _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            if (this.m_giftLoader && this.m_giftLoader.content)
            {
                _loc_2 = this.m_giftLoader.content;
                if (this.m_chosenGiftSprite.numChildren)
                {
                    this.m_chosenGiftSprite.removeChildAt(0);
                }
                this.m_chosenGiftSprite.addChildAt(_loc_2, 0);
                _loc_3 =(DisplayObject) new assetDict.get("giftWindow");
                this.m_chosenGiftSprite.x = _loc_3.width / 2 - _loc_2.width / 2;
                this.m_chosenGiftSprite.y = 10;
            }
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            (Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS) as RecentlyPlayedMFSManager).onClose();
            super.onCancelX(param1);
            return;
        }//end

    }




