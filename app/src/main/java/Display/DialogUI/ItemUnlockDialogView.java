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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class ItemUnlockDialogView extends GenericDialogView
    {
        private static  int ICON_TOP_MARGIN =20;

        public  ItemUnlockDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            return;
        }//end

         protected JPanel  createIconPane ()
        {
            AssetPane iconPane ;
            DisplayObject asset ;
            Loader iconLoader ;
            iconPane = new AssetPane();
            asset = this.m_assetDict.get("marketItem");
            iconLoader =LoadingManager .load (m_icon ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                iconPane.setAsset(iconLoader.content);
                _loc_3 = asset(.width-iconLoader.content.width)/2;
                _loc_4 = asset(.height-iconLoader.content.height)/2;
                ASwingHelper.setEasyBorder(iconPane, _loc_4 + ICON_TOP_MARGIN, _loc_3);
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , this));
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            ASwingHelper.setSizedBackground(iconInnerPane, this.m_assetDict.get("marketItem"), new Insets(ICON_TOP_MARGIN));
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

         protected JPanel  createTextArea ()
        {
            double _loc_2 =0;
            JPanel _loc_11 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            if (m_icon != null && m_icon != "")
            {
                _loc_2 = this.setMessageTextWidth(m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT);
                _loc_11 = this.createIconPane();
            }
            else
            {
                _loc_2 = this.setMessageTextWidth(false);
            }
            _loc_3 = this.createTextComponent(_loc_2);
            _loc_4 = this.makeUnlockedCostPanel(m_assetDict.get("unlockedCostString"),_loc_2);
            AssetPane _loc_5 =new AssetPane(m_assetDict.get( "hr") );
            _loc_6 = this.createBuyText();
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_7.appendAll(_loc_3, _loc_4, _loc_5, ASwingHelper.verticalStrut(20), _loc_6, ASwingHelper.verticalStrut(20));
            _loc_8 = _loc_11;
            _loc_9 = _loc_7;
            if (m_iconPos == ICON_POS_RIGHT || m_iconPos == ICON_POS_BOTTOM)
            {
                _loc_8 = _loc_7;
                _loc_9 = _loc_11;
            }
            _loc_1.append(ASwingHelper.horizontalStrut(35));
            if (m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_8)
                {
                    _loc_1.append(_loc_8);
                }
                if (_loc_9)
                {
                    _loc_1.append(_loc_9);
                }
            }
            _loc_1.append(ASwingHelper.horizontalStrut(50));
            _loc_10 = _loc_3.getHeight();
            return _loc_1;
        }//end

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            double _loc_2 =0;
            if (param1 == false)
            {
                _loc_2 = 350;
            }
            else
            {
                _loc_2 = 270;
            }
            int _loc_3 =150;
            return _loc_2 + _loc_3;
        }//end

        private JPanel  createBuyText ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_2 = ZLoc.t("Dialogs","CatalogCost");
            _loc_3 = TextFieldUtil.formatSmallCapsString(_loc_2);
            _loc_4 = BuyLogic.isLocked(m_assetDict.get("item"))? (String(m_assetDict.get("item").unlockCostCash)) : (m_assetDict.get("item").cash);
            _loc_5 = ASwingHelper.makeLabel(_loc_3,EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.blueTextColor,JLabel.LEFT);
            _loc_1.append(_loc_5);
            AssetPane _loc_6 =new AssetPane(new EmbeddedArt.icon_cash_big ()as DisplayObject );
            _loc_1.append(_loc_6);
            _loc_7 = ASwingHelper.makeLabel(_loc_4,EmbeddedArt.defaultFontNameBold,21,EmbeddedArt.redTextColor,JLabel.LEFT);
            _loc_1.append(_loc_7);
            return _loc_1;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_3 =null ;
            double _loc_6 =0;
            Container _loc_7 =null ;
            _loc_2 = TextFormatAlign.LEFT;
            _loc_4 = ASwingHelper.makeMultilineText(m_message,param1,EmbeddedArt.defaultFontNameBold,_loc_2,18,EmbeddedArt.brownTextColor);
            double _loc_5 =75;
            if (_loc_4.getHeight() < _loc_5)
            {
                _loc_6 = (_loc_5 - _loc_4.getHeight()) / 2;
                _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_7.append(ASwingHelper.verticalStrut(_loc_6));
                _loc_7.append(_loc_4);
                _loc_7.append(ASwingHelper.verticalStrut(_loc_6));
                _loc_3 = _loc_7;
            }
            else
            {
                _loc_3 = _loc_4;
            }
            return _loc_3;
        }//end

        protected JPanel  makeUnlockedCostPanel (String param1 ,double param2 )
        {
            Component _loc_4 =null ;
            String _loc_5 =null ;
            AssetPane _loc_6 =null ;
            JPanel _loc_7 =null ;
            JLabel _loc_8 =null ;
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical();
            if (param1 && m_assetDict.get("item"))
            {
                _loc_4 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", param1), param2, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 14, EmbeddedArt.blueTextColor);
                if (m_assetDict.get("item").cash > 0)
                {
                    _loc_5 = String(m_assetDict.get("item").cash);
                    _loc_6 = new AssetPane(new (DisplayObject)EmbeddedArt.icon_cash());
                }
                else
                {
                    _loc_5 = String(m_assetDict.get("item").cost);
                    _loc_6 = new AssetPane(new (DisplayObject)EmbeddedArt.mkt_coinIcon());
                }
                ASwingHelper.setEasyBorder(_loc_6, 3, 3, 3, 3);
                _loc_7 = ASwingHelper.makeSoftBoxJPanel();
                _loc_8 = ASwingHelper.makeLabel(_loc_5, EmbeddedArt.defaultFontNameBold, 18, EmbeddedArt.redTextColor);
                _loc_7.appendAll(_loc_6, _loc_8);
                _loc_3.appendAll(_loc_4, _loc_7, ASwingHelper.verticalStrut(10));
            }
            return _loc_3;
        }//end

    }




