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
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class VisitBuyDialogView extends GenericDialogView
    {
        private Item m_buyableItem ;
        private int m_experimentVariant ;
        private static  double ICON_PANE_WIDTH =88;
        private static  double ICON_PANE_HEIGHT =85;
        private static boolean m_confirmationFlag =false ;

        public  VisitBuyDialogView (Dictionary param1 ,Item param2 )
        {
            this.m_buyableItem = param2;
            this.m_buyableItem.m_isFranchise = false;
            super(param1, "visit_buy_dialog", "visit_buy_dialog", TYPE_YESNO, null, this.m_buyableItem.iconRelative, GenericDialogView.ICON_POS_BOTTOM);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_5 =0;
            Container _loc_6 =null ;
            _loc_3 = ASwingHelper.makeMultilineText("",param1,EmbeddedArt.defaultFontNameBold,m_align,18,EmbeddedArt.brownTextColor);
            double _loc_4 =40;
            if (_loc_3.getHeight() < _loc_4)
            {
                _loc_5 = _loc_4 - _loc_3.getHeight();
                _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_6.append(_loc_3);
                _loc_2 = _loc_6;
            }
            else
            {
                _loc_2 = _loc_3;
            }
            return _loc_2;
        }//end

         protected JPanel  createIconPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            Object _loc_3 ={};
            _loc_3.put("itemName",  ZLoc.t("Items", this.m_buyableItem.name + "_friendlyName"));
            _loc_4 = ZLoc.t("Dialogs","visit_buy_dialog_body",_loc_3);
            _loc_5 = ASwingHelper.makeMultilineText(_loc_4,setMessageTextWidth(true),EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,18,EmbeddedArt.brownTextColor);
            _loc_2.append(_loc_5);
            DisplayObject _loc_6 =new EmbeddedArt.mkt_rollover_horizontalRule ()as DisplayObject ;
            (new (DisplayObject)EmbeddedArt.mkt_rollover_horizontalRule()).width = setMessageTextWidth(true) - 10;
            AssetPane _loc_7 =new AssetPane(_loc_6 );
            _loc_7.setPreferredWidth(setMessageTextWidth(true));
            _loc_2.appendAll(ASwingHelper.horizontalStrut(10), _loc_7, ASwingHelper.horizontalStrut(10));
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_9 = ZLoc.t("Main","Cash")+":";
            AssetPane _loc_10 =new AssetPane ();
            _loc_11 = assetDict.get("icon_cash") ;
            if (this.m_buyableItem.cash == 0)
            {
                _loc_11 = assetDict.get("icon_coins");
                _loc_9 = ZLoc.t("Main", "Coins") + ":";
            }
            _loc_8.append(ASwingHelper.makeLabel(_loc_9, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor));
            _loc_10.setAsset(_loc_11);
            _loc_8.append(_loc_10);
            _loc_12 = String(this.m_buyableItem.GetItemSalePrice());
            _loc_8.appendAll(ASwingHelper.horizontalStrut(10), ASwingHelper.makeLabel(_loc_12, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor));
            LargeMarketCell _loc_13 =new LargeMarketCell ();
            _loc_13.setCellValue(this.m_buyableItem);
            _loc_13.hideBuyButton();
            _loc_2.append(_loc_13);
            _loc_2.append(_loc_8);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(10), _loc_2);
            return _loc_1;
        }//end

         protected void  onCancel (Object param1)
        {
            closeMe();
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            double population ;
            Dialog d ;
            event = event;
            if (this.m_buyableItem.cash > 0)
            {
                if (Global.player.canBuyCash(this.m_buyableItem.GetItemSalePrice(), true))
                {
                    GameTransactionManager.addTransaction(new TBuyItem(this.m_buyableItem.name, 1, "", true, false));
                    close();
                }
            }
            else if (this.m_buyableItem.cost > 0)
            {
                if (Global.player.canBuy(this.m_buyableItem.GetItemSalePrice(), true))
                {
                    if (this.m_buyableItem.requiredLevel > Global.player.level)
                    {
                        close();
                        d =new GenericDialog (ZLoc .t ("Dialogs","TT_RequiresLevel",{this level .m_buyableItem .requiredLevel ,this item .m_buyableItem .localizedName }),"",GenericDialogView .TYPE_OK ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    close();
                }
                return;
            }//end
            );
                        UI.displayPopup(d, true);
                    }
                    else if (this.m_buyableItem.requiredPopulation > Global.playerPopulation)
                    {
                        close();
                        d =new GenericDialog (ZLoc .t ("Dialogs","TT_RequiresPopulation",{this population .m_buyableItem .requiredPopulation *10,this item .m_buyableItem .localizedName }),"",GenericDialogView .TYPE_OK ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    close();
                }
                return;
            }//end
            );
                        UI.displayPopup(d, true);
                    }
                    else
                    {
                        GameTransactionManager.addTransaction(new TBuyItem(this.m_buyableItem.name, 1, "", true, false));
                        close();
                    }
                }
            }
            StatsManager.count("dialog", "visit_buy", "buy_item", "", this.m_buyableItem.name);
            return;
        }//end

        private Item  getItemFromObject ()
        {
            return this.m_buyableItem;
        }//end

        private boolean  updateConfirmationFlag (String param1 ,boolean param2 ,boolean param3 )
        {
            m_confirmationFlag = param2;
            return true;
        }//end

        private boolean  getConfirmationFlag (String param1 ,boolean param2 )
        {
            return m_confirmationFlag;
        }//end

        protected JPanel  iconLoaderPane (String param1 ,DisplayObject param2 )
        {
            AssetPane iconPane ;
            JPanel iconInnerPane ;
            Loader iconLoader ;
            iconRelativePath = param1;
            bkgAsset = param2;
            iconPane = new AssetPane();
            iconString = Global.getAssetURL(iconRelativePath);
            iconInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            iconLoader =LoadingManager .load (iconString ,Curry .curry (void  (JPanel param11 ,Event param21 )
            {
                _loc_3 = undefined;
                _loc_4 = undefined;
                iconPane.setAsset(iconLoader.content);
                ASwingHelper.prepare(param11);
                if (bkgAsset)
                {
                    _loc_3 = Math.abs(bkgAsset.height - iconLoader.content.height) / 2;
                    _loc_4 = Math.abs(bkgAsset.width - iconLoader.content.width) / 2;
                    ASwingHelper.setEasyBorder(iconInnerPane, _loc_3, _loc_4, _loc_3, _loc_4);
                }
                param11.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , iconInnerPane));
            iconInnerPane.append(iconPane);
            return iconInnerPane;
        }//end

    }




