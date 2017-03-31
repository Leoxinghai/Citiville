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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FriendEnterRewardDialogView extends GenericDialogView
    {
        protected DropShadowFilter m_textShadow ;
        protected int m_amount ;
        protected JTextField m_amountField ;
        protected Player m_friend ;
        protected Loader m_pic ;
        protected boolean m_picHasLoaded =false ;
        private AssetPane m_friendFrameAP ;
        private int m_coins ;
        private int m_energy ;
        private int m_xp ;
        public static  int FAKE_FRIEND_COMMODITY_AMOUNT =200;

        public  FriendEnterRewardDialogView (Dictionary param1 ,int param2 =0,int param3 =0,int param4 =0,String param5 ="",String param6 ="",int param7 =0,Function param8 =null )
        {
            this.m_coins = param2;
            this.m_energy = param3;
            this.m_xp = param4;
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
            this.m_textShadow = new DropShadowFilter(1, 90, 5602453, 1, 0, 0);
            m_acceptTextName = ZLoc.t("Dialogs", "Accept");
            m_cancelTextName = "";
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
            m_bgAsset =(DisplayObject) new m_assetDict.get("dialogBG");
            this.makeBackground();
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

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =new MarginBackground(m_bgAsset ,new Insets(10,0,0,0));
            this.setBackgroundDecorator(_loc_1);
            this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end

        protected JPanel  makeIconPanel (Class param1 ,int param2 ,String param3 )
        {
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            DisplayObject _loc_5 =(DisplayObject)new param1;
            AssetPane _loc_6 =new AssetPane(_loc_5 );
            _loc_7 = ZLoc.t("Dialogs",param3 ,{number param2 });
            double _loc_8 =110;
            _loc_9 = ASwingHelper.makeMultilineText(_loc_7 ,_loc_8 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,15,EmbeddedArt.brownTextColor ,null ,true );
            _loc_9.setMinimumHeight(_loc_5.height + 8);
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_10.appendAll(ASwingHelper.verticalStrut(3), _loc_9);
            ASwingHelper.prepare(_loc_10);
            _loc_4.appendAll(ASwingHelper.horizontalStrut(22), _loc_6, ASwingHelper.horizontalStrut(4), _loc_10);
            ASwingHelper.prepare(_loc_4);
            return _loc_4;
        }//end

         protected void  makeCenterPanel ()
        {
            String _loc_2 =null ;
            _loc_1 = this.makeInfo ();
            m_titlePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2 = ZLoc.t("Dialogs", "VRWhileVisiting", {name:ZLoc.tn(this.m_friend.snUser.firstName)});
            double _loc_3 =152;
            _loc_4 = ASwingHelper.makeMultilineText(_loc_2 ,_loc_3 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,13,3841479,null ,true );
            m_titlePanel.append(ASwingHelper.verticalStrut(54));
            m_titlePanel.append(_loc_4);
            _loc_5 = this.m_coins ;
            _loc_6 = this.m_energy ;
            _loc_7 = this.m_xp ;
            _loc_8 = this.makeIconPanel(m_assetDict.get( "coinIcon") ,_loc_5 ,"VRNumCoins");
            _loc_9 = this.makeIconPanel(m_assetDict.get( "energyIcon") ,_loc_6 ,"VRNumEnergy");
            _loc_10 = this.makeIconPanel(m_assetDict.get( "starXPIcon") ,_loc_7 ,"VRNumXP");
            _loc_11 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,0);
            _loc_11.appendAll(_loc_8, _loc_9, _loc_10);
            ASwingHelper.prepare(_loc_11);
            m_titlePanel.append(ASwingHelper.verticalStrut(7));
            m_titlePanel.append(_loc_11);
            m_titlePanel.append(ASwingHelper.verticalStrut(6));
            CustomButton _loc_12 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            _loc_12.addActionListener(onAccept, 0, true);
            m_titlePanel.append(_loc_12);
            ASwingHelper.prepare(m_titlePanel);
            _loc_1.append(m_titlePanel);
            DisplayObject _loc_13 =(DisplayObject)new m_assetDict.get( "closeBtnUp");
            DisplayObject _loc_14 =(DisplayObject)new m_assetDict.get( "closeBtnOver");
            DisplayObject _loc_15 =(DisplayObject)new m_assetDict.get( "closeBtnDown");
            SimpleButton _loc_16 =new SimpleButton(_loc_13 ,_loc_14 ,_loc_15 ,_loc_13 );
            JButton _loc_17 =new JButton ();
            _loc_17.wrapSimpleButton(_loc_16);
            _loc_17.addEventListener(MouseEvent.CLICK, onCancel, false, 0, true);
            _loc_18 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            double _loc_19 =19;
            if (Global.localizer.langCode == "ja")
            {
                _loc_19 = 5;
            }
            _loc_18.append(ASwingHelper.verticalStrut(_loc_19));
            _loc_18.append(_loc_17);
            _loc_1.append(ASwingHelper.horizontalStrut(10));
            _loc_1.append(_loc_18);
            ASwingHelper.prepare(_loc_1);
            this.append(_loc_1);
            ASwingHelper.prepare(this);
            return;
        }//end

        private JPanel  makeInfo ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            this.m_friendFrameAP = new AssetPane(new Sprite());
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = ASwingHelper.verticalStrut(32);
            _loc_2.append(_loc_3);
            _loc_2.append(this.m_friendFrameAP);
            _loc_2.append(ASwingHelper.horizontalStrut(54));
            ASwingHelper.prepare(_loc_2);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(121), _loc_2);
            return _loc_1;
        }//end

    }



