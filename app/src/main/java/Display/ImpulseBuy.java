package Display;

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

import Classes.util.*;
import Display.DialogUI.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;

    public class ImpulseBuy extends GenericDialog
    {
        private String m_impulseType ;
        public static  String TYPE_FUEL ="out_of_fuel";
        public static  String TYPE_COINS ="need_coins";
        public static  String TYPE_MARKET_COINS ="market_coins";
        public static  String TYPE_MARKET_CASH ="market_cash";
        public static  String TYPE_MARKET_COMMODITY ="market_commodity";
        public static  String POPUP_NAME ="impuseBuyMarket";

        public  ImpulseBuy (String param1 )
        {
            String _loc_2 =null ;
            this.m_impulseType = param1;
            switch(this.m_impulseType)
            {
                case ImpulseBuy.TYPE_FUEL:
                {
                    _loc_2 = ZLoc.t("Dialogs", "ImpulseFuel");
                    break;
                }
                case ImpulseBuy.TYPE_COINS:
                {
                    _loc_2 = ZLoc.t("Dialogs", "ImpulseCoins");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COINS:
                {
                    _loc_2 = ZLoc.t("Dialogs", "ImpulseMarketCoins");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_CASH:
                {
                    _loc_2 = ZLoc.t("Dialogs", "ImpulseMarketCash");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COMMODITY:
                {
                    _loc_2 = ZLoc.t("Dialogs", "ImpulseMarketCommodity");
                    break;
                }
                default:
                {
                    break;
                }
            }
            super(_loc_2, "", GenericDialogView.TYPE_YESNO, this.onClose, "", "", true);
            return;
        }//end  

        private void  onClose (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                if (Utilities.isFullScreen())
                {
                    Utilities.setFullScreen(false);
                }
                this.onAccept();
            }
            else if (event.button == GenericDialogView.NO)
            {
                this.onCancel();
            }
            removeEventListener(GenericPopupEvent.SELECTED, this.onClose);
            return;
        }//end  

        protected void  onAccept ()
        {
            switch(this.m_impulseType)
            {
                case ImpulseBuy.TYPE_FUEL:
                {
                    UI.displayMarketDialog("impulse_fuel", "vehicle");
                    break;
                }
                case ImpulseBuy.TYPE_COINS:
                {
                    FrameManager.navigateTo("money.php?ref=imp_coins");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COINS:
                {
                    FrameManager.navigateTo("money.php?ref=imp_market");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COMMODITY:
                {
                    FrameManager.navigateTo("money.php?ref=imp_market");
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_CASH:
                {
                    StatsManager.count("fv_cash_buy_fail", "click_yes_please");
                    StatsManager.sendStats(true);
                    FrameManager.navigateTo("money.php?ref=tab");
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end  

        protected void  onCancel ()
        {
            switch(this.m_impulseType)
            {
                case ImpulseBuy.TYPE_FUEL:
                {
                    break;
                }
                case ImpulseBuy.TYPE_COINS:
                {
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COINS:
                {
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_COMMODITY:
                {
                    break;
                }
                case ImpulseBuy.TYPE_MARKET_CASH:
                {
                    StatsManager.count("fv_cash_buy_fail", "click_no_thanks");
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end  

    }



