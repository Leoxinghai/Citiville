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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class FriendFTVRewardDialogView extends GenericDialogView
    {
        protected int m_amount ;
        protected JTextField m_amountField ;
        protected Player m_friend ;
        protected Loader m_pic ;
        protected boolean m_picHasLoaded =false ;
        private AssetPane m_friendFrameAP ;
        private int m_coins ;
        private int m_energy ;
        private int m_xp ;
        private boolean m_firstTime =false ;
        protected String m_acceptTextToken ;
        protected DisplayObject m_mainImage ;
        private JPanel m_imagePanel ;

        public  FriendFTVRewardDialogView (Dictionary param1 ,int param2 =0,int param3 =0,int param4 =0,String param5 ="",String param6 ="",int param7 =0,Function param8 =null ,boolean param9 =false )
        {
            this.m_coins = param2;
            this.m_energy = param3;
            this.m_xp = param4;
            this.m_firstTime = param9;
            this.m_acceptTextToken = "Accept";
            super(param1, param5, param6, param7, param8);
            return;
        }//end

        public int  amount ()
        {
            return this.m_amount;
        }//end

        public Player  friend ()
        {
            return this.m_friend;
        }//end

         protected void  init ()
        {
            String _loc_1 =null ;
            m_acceptTextName = ZLoc.t("Dialogs", this.m_acceptTextToken);
            m_bgAsset = m_assetDict.get("dialog_bg");
            this.m_friend = m_assetDict.get("friend");
            if (this.m_friend && this.m_friend.snUser)
            {
                _loc_1 = this.m_friend.snUser.picture;
                if (_loc_1)
                {
                    this.m_pic = LoadingManager.load(_loc_1, this.fillFriendFrame, LoadingManager.PRIORITY_LOW);
                    (this.m_pic as Loader).contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onLoadPicFail, false, 2);
                }
            }
            if (this.m_pic == null)
            {
                this.m_pic = new EmbeddedArt.hud_no_profile_pic();
                this.m_pic.width = 48;
                this.m_pic.height = 48;
            }
            makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            title = ASwingHelper.makeTextField(m_titleString, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_2);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_13 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-18,SoftBoxLayout.TOP ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            int _loc_3 =260;
            _loc_4 = ASwingHelper.makeMultilineText(this.m_message ,_loc_3 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.blueTextColor );
            _loc_5 = ASwingHelper.centerElement(_loc_4 );
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            textArea.appendAll(this.makeFriendPanel(), _loc_5);
            _loc_6.append(ASwingHelper.verticalStrut(10));
            _loc_6.append(textArea);
            _loc_6.append(ASwingHelper.verticalStrut(5));
            _loc_7 = new EmbeddedArt.neighborVisitBlueHighlight ();
            MarginBackground _loc_8 =new MarginBackground(_loc_7 ,new Insets(0,0,0,0));
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_9.setBackgroundDecorator(_loc_8);
            _loc_9.setPreferredWidth(_loc_7.width);
            _loc_9.setMinimumWidth(_loc_7.width);
            _loc_9.setMaximumWidth(_loc_7.width);
            _loc_9.setPreferredHeight(_loc_7.height);
            _loc_9.setMinimumHeight(_loc_7.height);
            _loc_9.setMaximumHeight(_loc_7.height);
            _loc_10 = this.makeIconPanel(EmbeddedArt.neighborVisitLargeXP ,this.m_xp ,"VRNumXP",_loc_7.width /3);
            _loc_9.append(_loc_10);
            _loc_10 = this.makeIconPanel(EmbeddedArt.neighborVisitLargeCoin, this.m_coins, "VRNumCoins", _loc_7.width / 3);
            _loc_9.append(_loc_10);
            _loc_10 = this.makeIconPanel(EmbeddedArt.neighborVisitLargeEnergy, this.m_energy, "VRNumEnergy", _loc_7.width / 3);
            _loc_9.append(_loc_10);
            _loc_6.append(_loc_9);
            this.m_imagePanel = this.makeImagePanel();
            JPanel _loc_11 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-21,SoftBoxLayout.TOP ));
            _loc_11.append(this.m_imagePanel);
            _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_12.append(_loc_11);
            _loc_12.append(ASwingHelper.horizontalStrut(10));
            _loc_12.append(_loc_6);
            _loc_12.append(ASwingHelper.horizontalStrut(20));
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(30));
            _loc_1.append(_loc_12);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_13 = createButtonPanel();
                _loc_1.append(_loc_13);
            }
            ASwingHelper.prepare(_loc_1);
            _loc_2.setPreferredWidth(_loc_1.getWidth());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeImagePanel ()
        {
            Insets _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (this.m_firstTime)
            {
                _loc_2 = new Insets(5, 0, 0, 0);
            }
            else
            {
                _loc_2 = new Insets(5, 8, 0, 0);
            }
            ASwingHelper.setSizedBackground(_loc_1, m_assetDict.get("image"), _loc_2);
            return _loc_1;
        }//end

        private void  fillFriendFrame (Event event )
        {
            Sprite _loc_2 =new Sprite ();
            _loc_3 = this.m_pic.content ;
            _loc_2.addChild(_loc_3);
            this.m_friendFrameAP.setAsset(_loc_2);
            this.m_picHasLoaded = true;
            ASwingHelper.prepare(this);
            return;
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

        protected JPanel  makeIconPanel (Class param1 ,int param2 ,String param3 ,double param4 )
        {
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_6 =(DisplayObject)new param1;
            AssetPane _loc_7 =new AssetPane(_loc_6 );
            _loc_8 = ZLoc.t("Dialogs",param3 ,{number param2 });
            _loc_9 = ASwingHelper.makeMultilineText(_loc_8 ,param4 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.brownTextColor ,null ,true );
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_10.appendAll(ASwingHelper.verticalStrut(3), _loc_9);
            ASwingHelper.prepare(_loc_10);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_11.append(ASwingHelper.horizontalStrut((param4 - _loc_6.width) / 2));
            _loc_11.append(_loc_7);
            _loc_5.appendAll(ASwingHelper.verticalStrut(12), _loc_11, ASwingHelper.verticalStrut(4), _loc_10);
            _loc_5.setPreferredWidth(param4);
            ASwingHelper.prepare(_loc_5);
            return _loc_5;
        }//end

        private JPanel  makeFriendPanel ()
        {
            this.m_friendFrameAP = new AssetPane(new Sprite());
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.verticalStrut(2);
            _loc_1.append(_loc_2);
            _loc_1.append(this.m_friendFrameAP);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

    }



