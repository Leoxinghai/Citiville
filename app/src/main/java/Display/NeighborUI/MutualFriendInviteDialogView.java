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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class MutualFriendInviteDialogView extends GenericDialogView
    {
        private boolean m_picHasLoaded =false ;
        private Player m_friend ;
        private Loader m_pic ;
        private AssetPane m_friendFrameAP ;
        private DisplayObject m_samAsset ;
        private DisplayObject m_hRuleAsset ;
        private Array m_portraits ;
        private Array m_loaders ;
        private JPanel m_portraitsPane ;
        public static  int NUM_PORTRAITS =4;

        public  MutualFriendInviteDialogView (Dictionary param1 )
        {
            super(param1);
            return;
        }//end

         protected void  init ()
        {
            Loader _loc_1 =null ;
            String _loc_2 =null ;
            m_acceptTextName = ZLoc.t("Dialogs", "MFI_button");
            m_cancelTextName = "";
            this.m_friend = m_assetDict.get("friend");
            this.m_portraits = m_assetDict.get("portraits");
            this.m_loaders = new Array();
            if (this.m_portraits && this.m_portraits.length())
            {
                for(int i0 = 0; i0 < this.m_portraits.size(); i0++)
                {
                	_loc_2 = this.m_portraits.get(i0);

                    _loc_1 = LoadingManager.load(_loc_2, this.fillFriendFrame, LoadingManager.PRIORITY_LOW);
                    _loc_1.contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onLoadPicFail, false, 2);
                }
            }
            this.m_hRuleAsset =(DisplayObject) new m_assetDict.get("hrule");
            m_bgAsset =(DisplayObject) new m_assetDict.get("dialogBG");
            this.m_samAsset =(DisplayObject) new m_assetDict.get("sam");
            this.createTitlePanel();
            this.makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  fillFriendFrame (Event event )
        {
            Sprite _loc_2 =new Sprite ();
            _loc_3 = event.currentTarget.content ;
            _loc_2.addChild(_loc_3);
            AssetPane _loc_4 =new AssetPane ();
            _loc_4.setAsset(_loc_2);
            this.m_portraitsPane.appendAll(_loc_4, ASwingHelper.horizontalStrut(5));
            this.m_picHasLoaded = true;
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            super.m_titleFontSize = 17;
            super.m_titleSmallCapsFontSize = 25;
            super.m_titleString = ZLoc.t("Dialogs", "MFI_title");
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(m_titleSmallCapsFontSize, m_titleFontSize, [{locale:"ja", size:m_titleSmallCapsFontSize}]);
            if (m_titleString != "")
            {
                _loc_2 = m_titleString;
                title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
                title.filters = EmbeddedArt.newtitleFilters;
                _loc_3 = new TextFormat();
                _loc_3.size = m_titleSmallCapsFontSize;
                TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
                _loc_1.append(title);
                title.getTextField().height = m_titleFontSize * 1.5;
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            ASwingHelper.setEasyBorder(_loc_1, 20);
            return _loc_1;
        }//end

        private void  onLoadPicFail (Event event )
        {
            if (!this.m_picHasLoaded)
            {
                event.stopPropagation();
                event.stopImmediatePropagation();
                LoadingManager.cancelLoad(this.m_pic);
                this.m_picHasLoaded = true;
            }
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =new MarginBackground(m_bgAsset ,new Insets(10,0,7,0));
            this.setBackgroundDecorator(_loc_1);
            this.setPreferredWidth(m_bgAsset.width);
            this.setMinimumWidth(m_bgAsset.width);
            this.setMaximumWidth(m_bgAsset.width);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = this.createCloseButtonPanel ();
            _loc_3 = this.createTitlePanel ();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            return _loc_1;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onCancel, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 14, 0, 0, 4);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_1.setPreferredHeight(this.m_samAsset.height + 15);
            _loc_1.setMinimumHeight(this.m_samAsset.height + 15);
            _loc_1.setMaximumHeight(this.m_samAsset.height + 15);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            AssetPane _loc_3 =new AssetPane(this.m_samAsset );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_5 = ZLoc.t("Dialogs","MFI_message",{name ZLoc.tn(this.m_friend.snUser.firstName )});
            this.m_portraitsPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6 = ASwingHelper.makeMultilineText(_loc_5 ,270,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,18,3841479,null ,true );
            AssetPane _loc_7 =new AssetPane(this.m_hRuleAsset );
            _loc_4.appendAll(ASwingHelper.verticalStrut(10), this.m_portraitsPane, ASwingHelper.verticalStrut(9), _loc_7, ASwingHelper.verticalStrut(9), _loc_6);
            _loc_2.append(ASwingHelper.horizontalStrut(9));
            _loc_2.append(_loc_3);
            _loc_2.append(ASwingHelper.horizontalStrut(9));
            _loc_2.append(_loc_4);
            _loc_1.append(ASwingHelper.verticalStrut(15));
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = this.createHeaderPanel ();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = createButtonPanel();
                _loc_1.appendAll(ASwingHelper.verticalStrut(-10), _loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected void  onAccept (AWEvent event )
        {
            super.onAccept(event);
            FrameManager.navigateTo("invite.php?mutual=true&ref=mutual_friend_invite&fId=" + this.m_friend.uid);
            Global.player.setShowMFIByID(this.m_friend.uid, false);
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            String _loc_2 =null ;
            super.onCancel(param1);
            for(int i0 = 0; i0 < Global.player.MFIDict.size(); i0++)
            {
            	_loc_2 = Global.player.MFIDict.get(i0);

                Global.player.setShowMFIByID(_loc_2, false);
            }
            return;
        }//end

    }



