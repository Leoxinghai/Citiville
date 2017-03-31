package Display.NeighborUI;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class FriendHelpDialogView extends GenericDialogView
    {
        protected DropShadowFilter m_textShadow ;
        protected int m_amount ;
        protected JTextField m_amountField ;
        protected Player m_friend ;
        protected Loader m_pic ;
        private AssetPane m_friendFrameAP ;
        private String m_AcceptText ;
        protected JPanel helpFriendTitlePanel ;
        public static  int FAKE_FRIEND_COMMODITY_AMOUNT =200;

        public  FriendHelpDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,String param5 ="IdleGoHelpOutNow_button",Function param6 =null )
        {
            this.m_AcceptText = param5;
            m_message = param2;
            m_titleString = param3;
            super(param1, param2, param3, param4, param6);
            return;
        }//end

        public Player  friend ()
        {
            return this.m_friend;
        }//end

         protected void  init ()
        {
            String _loc_1 =null ;
            this.m_textShadow = new DropShadowFilter(1, 90, 5602453, 1, 0, 0);
            m_acceptTextName = ZLoc.t("Dialogs", this.m_AcceptText);
            m_cancelTextName = "";
            this.m_friend = m_assetDict.get("friend");
            if (this.m_friend && this.m_friend.snUser)
            {
                _loc_1 = this.m_friend.snUser.picture;
                if (_loc_1)
                {
                    this.m_pic = LoadingManager.load(_loc_1, this.fillFriendFrame, LoadingManager.PRIORITY_LOW);
                }
            }
            if (this.m_pic == null)
            {
                this.m_pic = new EmbeddedArt.hud_no_profile_pic();
                this.m_pic.width = 48;
                this.m_pic.height = 48;
            }
            m_bgAsset =(DisplayObject) new m_assetDict.get("dialogBG");
            makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  fillFriendFrame (Event event )
        {
            Sprite _loc_2 =new Sprite ();
            _loc_3 = this.m_pic.content ;
            _loc_2.addChild(_loc_3);
            this.m_friendFrameAP.setAsset(_loc_2);
            ASwingHelper.prepare(this);
            return;
        }//end

        private JPanel  makeInfo ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            this.m_friendFrameAP = new AssetPane(new Sprite());
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2.append(ASwingHelper.verticalStrut(23));
            _loc_2.append(this.m_friendFrameAP);
            _loc_2.append(ASwingHelper.horizontalStrut(0));
            ASwingHelper.prepare(_loc_2);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(80), _loc_2);
            return _loc_1;
        }//end

         protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            _loc_1 = this.makeInfo ();
            _loc_2 = ZLoc.t("Dialogs", m_titleString + "_title");
            _loc_3 = ASwingHelper.makeTextField(_loc_2 +" ",EmbeddedArt.defaultFontNameBold ,15,EmbeddedArt.blueTextColor );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_4.appendAll(ASwingHelper.verticalStrut(50), _loc_3);
            ASwingHelper.prepare(_loc_4);
            this.helpFriendTitlePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.helpFriendTitlePanel.append(_loc_1);
            this.helpFriendTitlePanel.appendAll(ASwingHelper.horizontalStrut(20), _loc_4);
            ASwingHelper.prepare(this.helpFriendTitlePanel);
            return this.helpFriendTitlePanel;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 9, 0, 0, 16);
            return _loc_1;
        }//end

         protected void  makeCenterPanel ()
        {
            _loc_1 = createInteriorHolder();
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_1.setPreferredHeight(m_bgAsset.height);
            _loc_1.setMinimumHeight(m_bgAsset.height);
            _loc_1.setMaximumHeight(m_bgAsset.height);
            this.append(_loc_1);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            _loc_3 = param1-character_width -10;
            _loc_4 = ASwingHelper.horizontalStrut(_loc_3 );
            _loc_5 = ZLoc.t("Dialogs",m_message );
            _loc_6 = ASwingHelper.makeMultilineText(m_message ,_loc_3 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,15,EmbeddedArt.brownTextColor );
            double _loc_7 =75;
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_8.appendAll(ASwingHelper.horizontalStrut(character_width + 5), _loc_6);
            ASwingHelper.prepare(_loc_8);
            _loc_9 = _loc_7(-_loc_6.getHeight ())/2;
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_10.append(ASwingHelper.verticalStrut(_loc_9));
            _loc_10.append(_loc_8);
            _loc_10.append(ASwingHelper.verticalStrut(_loc_9));
            _loc_2 = _loc_10;
            return _loc_2;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_2 = TextFieldUtil.getLocaleFontSize(22,18,.get({localesize"ja",22)});
            CustomButton _loc_3 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_2));
            _loc_3.addActionListener(onAccept, 0, true);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(character_width + 42), _loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_4.append(ASwingHelper.verticalStrut(20));
            _loc_4.append(_loc_1);
            return _loc_4;
        }//end

    }



