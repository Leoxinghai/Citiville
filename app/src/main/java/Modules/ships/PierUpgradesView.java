package Modules.ships;

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
import Modules.hotels.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.zynga.*;

    public class PierUpgradesView extends GenericDialogView implements IBaseLocKey
    {
        protected MechanicMapResource m_owner ;
        protected JPanel m_contentPanel ;
public static PierUpgradesView m_instance =null ;

        public  PierUpgradesView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true )
        {
            this.m_owner = param1.get("spawner");
            m_instance = this;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

         protected void  init ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            m_bgAsset = m_assetDict.get("shippingUpgrades_bg");
            makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            ASwingHelper.setBackground(this, m_bgAsset);
            this.setPreferredWidth(733);
            this.setPreferredHeight(549);
            this.append(createHeaderPanel());
            this.append(this.makeContentPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeContentPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_1, 15);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-1);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            ASwingHelper.setBackground(_loc_3, new m_assetDict.get("shippingUpgrades_bgQuest"));
            ASwingHelper.setEasyBorder(this.m_contentPanel, 0, 0, 10, 0);
            this.m_contentPanel.append(new HotelUpgradePanel(m_assetDict, 466, close, this));
            _loc_3.append(this.m_contentPanel);
            _loc_2.append(_loc_3);
            _loc_2.setPreferredWidth(464);
            _loc_1.appendAll(this.makeStatsPanel(), ASwingHelper.horizontalStrut(10), _loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeStatsPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            ASwingHelper.setEasyBorder(_loc_1, 308);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_3 =(DisplayObject)new assetDict.get( "shippingUpgrades_bgCurrentLevel");
            ASwingHelper.setSizedBackground(_loc_2, _loc_3);
            _loc_4 = this.m_owner.getItem ();
            _loc_5 = this.makeUpgradeStatsPanel(_loc_4 );
            JPanel _loc_6 =new JPanel(new LayeredLayout ());
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_7.setPreferredWidth(233);
            AssetPane _loc_8 =new AssetPane(new m_assetDict.get( "payroll_checkin_confirmCheckmark") );
            ASwingHelper.setEasyBorder(_loc_7, 5);
            _loc_7.append(_loc_8);
            _loc_6.appendAll(_loc_5, _loc_7);
            _loc_2.append(ASwingHelper.verticalStrut(5));
            _loc_2.append(ASwingHelper.centerElement(_loc_6));
            _loc_9 = _loc_4.upgrade.newItemName;
            _loc_10 =Global.gameSettings().getItemByName(_loc_9 );
            _loc_5 = this.makeUpgradeStatsPanel(_loc_10);
            _loc_2.append(ASwingHelper.centerElement(_loc_5));
            _loc_2.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeUpgradeStatsPanel (Item param1 )
        {
            AssetPane icon ;
            item = param1;
            itemName = item.name;
            jp = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            upgradeStatsPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            upgradeStatsPane.setPreferredWidth(233);
            upgradeStatsPane.setPreferredHeight(81);
            icon = new AssetPane();
            LoadingManager .load (item .icon ,void  (Event event )
            {
                icon.setAsset(event.target.content);
                return;
            }//end
            );
            upgradeStatsPane.append(icon);
            upgradeStatsPane.append(ASwingHelper.horizontalStrut(10));
            textPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            levelString = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs",itemName+"_upgradeLevel"));
            levelLabel = ASwingHelper.makeLabel(levelString,EmbeddedArt.defaultFontNameBold,12,EmbeddedArt.blueTextColor,JLabel.LEFT);
            upgrateText1String = ZLoc.t("Dialogs",itemName+"_upgradeText1");
            upgradeText1Label = ASwingHelper.makeTextField(upgrateText1String,EmbeddedArt.defaultFontNameBold,12,EmbeddedArt.greenTextColor);
            upgrateText2String = ZLoc.t("Dialogs",itemName+"_upgradeText2");
            upgradeText2Label = ASwingHelper.makeTextField(upgrateText2String,EmbeddedArt.defaultFontNameBold,12,EmbeddedArt.greenTextColor);
            textPanel.appendAll(levelLabel, upgradeText1Label, upgradeText2Label);
            ASwingHelper.prepare(textPanel);
            upgradeStatsPane.append(textPanel, BorderLayout.EAST);
            ASwingHelper.setEasyBorder(upgradeStatsPane, 0, 5, 0, 5);
            jp.appendAll(ASwingHelper.centerElement(upgradeStatsPane));
            return jp;
        }//end

        public String  getLocKeyBaseDialog (String param1 )
        {
            return "PierDialog_" + param1;
        }//end

    }



