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
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class SocialLevelUpDialogView extends GenericDialogView
    {
        protected int m_level ;
        protected int m_rewardGoods ;
        protected int m_rewardSocialHelp ;
        protected String m_dialogIcon ;
        protected String m_titleIcon ;
        protected Loader m_pic ;
        protected Object m_levelIconPane ;
        protected Dictionary m_panelDict ;
        protected JPanel socialTitlePanel ;

        public  SocialLevelUpDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,int param8 =0,int param9 =0,int param10 =0,String param11 ="",String param12 ="")
        {
            this.m_level = param8;
            this.m_rewardGoods = param9;
            this.m_rewardSocialHelp = param10;
            this.m_dialogIcon = param11;
            this.m_titleIcon = param12;
            super(param1, param2, param3, param4, param5, param6, param7);
            this.socialTitlePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            return;
        }//end  

        protected JPanel  createTitleIconPane ()
        {
            AssetPane iconPane ;
            Loader iconLoader ;
            iconPane = new AssetPane();
            iconString = Global.getAssetURL(this.m_titleIcon);
            iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end  
            , this));
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,50);
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end  

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_2 =null ;
            _loc_1 = this.createTitleIconPane();
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, -35);
            this.socialTitlePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = Global.getAssetURL(this.m_titleIcon);
            _loc_4 = this.m_level.toString();
            _loc_5 = ASwingHelper.makeTextField(_loc_4,EmbeddedArt.titleFont,26,16777215);
            _loc_5.filters = EmbeddedArt.socialLevelUpTitleFilters;
            _loc_2.append(_loc_1);
            ASwingHelper.setEasyBorder(_loc_5, 3);
            _loc_2.append(_loc_5);
            _loc_2.setPreferredWidth(43);
            _loc_6 = ZLoc.t("Dialogs",m_titleString+"_title");
            title = ASwingHelper.makeTextField(_loc_6, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_7 =new TextFormat ();
            _loc_7.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_7);
            this.socialTitlePanel.append(_loc_2);
            this.socialTitlePanel.append(ASwingHelper.horizontalStrut(10));
            this.socialTitlePanel.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            return this.socialTitlePanel;
        }//end  

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            String _loc_14 =null ;
            JLabel _loc_15 =null ;
            _loc_3 = TextFieldUtil.getLocaleFontSize(19,17,[{localesize"es",14.5locale},{"fr",size15},locale{"id",14.5},{"it",12.5},{"ja",19});
            _loc_4 = ZLoc.t("Dialogs","CollectionTradeInReward");
            _loc_5 = ASwingHelper.makeTextField(_loc_4+" ",EmbeddedArt.defaultFontNameBold,15,EmbeddedArt.blueTextColor);
            _loc_6 = ZLoc.t("Dialogs","GoodsSuffix",{amountthis.m_rewardGoods});
            _loc_7 = ASwingHelper.makeTextField(_loc_6+"  ",EmbeddedArt.defaultFontNameBold,_loc_3,EmbeddedArt.blueTextColor);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_8.append(_loc_7);
            if (this.m_rewardSocialHelp > 0)
            {
                _loc_14 = ZLoc.t("Dialogs", "SocialHelpSuffix", {amount:this.m_rewardSocialHelp});
                _loc_15 = ASwingHelper.makeLabel(_loc_14 + " ", EmbeddedArt.defaultFontNameBold, _loc_3, EmbeddedArt.blueTextColor);
                _loc_8.append(_loc_15);
            }
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP);
            _loc_9.append(ASwingHelper.verticalStrut(10));
            _loc_9.append(_loc_5);
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_10.append(_loc_9);
            _loc_10.append(ASwingHelper.horizontalStrut(10));
            _loc_10.append(_loc_8);
            _loc_10.setPreferredWidth(param1);
            _loc_10.setMinimumWidth(param1);
            _loc_10.setMaximumWidth(param1);
            _loc_11 = ASwingHelper.horizontalStrut(param1);
            _loc_12 = ASwingHelper.makeMultilineText(m_message,param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,15,EmbeddedArt.brownTextColor);
            _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_13.append(_loc_10);
            _loc_13.append(_loc_11);
            _loc_13.append(_loc_12);
            _loc_2 = _loc_13;
            return _loc_2;
        }//end  

    }



