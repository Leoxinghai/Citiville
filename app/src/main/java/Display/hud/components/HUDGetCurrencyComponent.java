package Display.hud.components;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.flashsale.ui.*;
import Modules.sale.*;
import Modules.sale.payments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.geom.*;
import Classes.sim.*;

    public class HUDGetCurrencyComponent extends HUDComponent
    {
public static boolean inEoQSale =false ;

        public  HUDGetCurrencyComponent ()
        {
            return;
        }//end

         protected void  buildComponent ()
        {
            JButton _loc_2 =null ;
            PaymentsSale _loc_5 =null ;
            int _loc_6 =0;
            JPanel _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            MarginBackground _loc_9 =null ;
            PaymentsSale _loc_10 =null ;
            int _loc_11 =0;
            String _loc_12 =null ;
            JPanel _loc_13 =null ;
            JTextField _loc_14 =null ;
            double _loc_15 =0;
            Date _loc_16 =null ;
            int _loc_17 =0;
            JPanel _loc_18 =null ;
            Sprite _loc_1 =new Sprite ();
            this.addChild(_loc_1);
            if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_FLASH_SALE, false))
            {
                _loc_5 = Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FLASH_SALE);
                _loc_6 = _loc_5.getSaleDiscount();
                _loc_2 = new CustomButton(ZLoc.t("Main", "AddCoinsCash_Sale", {discount:_loc_6}), null, "AddCoinsSaleButtonUI");
                _loc_2.addActionListener(this.onGetCurrency, 0, true);
            }
            else if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_FREE_GIFT_SALE, false))
            {
                _loc_2 = new CustomButton(ZLoc.t("Main", "FreeGift_Sale"), null, "FreeGiftSaleButtonUI");
                _loc_2.addActionListener(this.onFreeGiftOfferClick, 0, true);
            }
            else if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_EOQ_SALE, false))
            {
                inEoQSale = true;
                _loc_7 = new JPanel(new BorderLayout());
                _loc_8 =(DisplayObject) new EmbeddedArt.eoq_sale_timer_small();
                _loc_9 = new MarginBackground(_loc_8, new Insets(0, 0, 10, 0));
                _loc_7.setBackgroundDecorator(_loc_9);
                _loc_7.setPreferredSize(new IntDimension(_loc_8.width, _loc_8.height));
                _loc_7.setMinimumSize(new IntDimension(_loc_8.width, _loc_8.height));
                _loc_7.setMaximumSize(new IntDimension(_loc_8.width, _loc_8.height));
                _loc_7.buttonMode = true;
                _loc_10 = Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_EOQ_SALE);
                _loc_11 = _loc_10.getSaleDiscount();
                _loc_12 = ZLoc.t("Main", "EoQAddCoinsCash_Sale", {discount:_loc_11});
                _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_14 = ASwingHelper.makeTextField(_loc_12, EmbeddedArt.titleFont, 10, EmbeddedArt.whiteTextColor, 2);
                _loc_15 = _loc_10.endDate;
                _loc_16 = new Date(_loc_15 * 1000);
                _loc_17 = 35;
                _loc_13.append(_loc_14);
                ASwingHelper.setEasyBorder(_loc_13, 0, _loc_17 + 5, -2, 0);
                _loc_7.append(_loc_13, BorderLayout.NORTH);
                _loc_18 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                ASwingHelper.setEasyBorder(_loc_18, 0, _loc_17 + 10, 8, 0);
                _loc_18.append(new MiniEoQTimerPanel(_loc_16));
                _loc_7.append(_loc_18, BorderLayout.SOUTH);
                _loc_7.addEventListener(MouseEvent.CLICK, this.onGetCurrency);
            }
            else
            {
                _loc_2 = new CustomButton(ZLoc.t("Main", "AddCoinsCash"), null, "AddCoinsButtonUI");
                _loc_2.addActionListener(this.onGetCurrency, 0, true);
            }

            /*
            JWindow _loc_3 =new JWindow(_loc_1 );

            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            if (inEoQSale)
            {
                _loc_4.append(_loc_7);
            }
            else
            {
                _loc_4.append(_loc_2);
            }

            _loc_3.setContentPane(_loc_4);
            ASwingHelper.prepare(_loc_3);
            _loc_3.show();
            */

            return;
        }//end

         public void  refresh (boolean param1 )
        {
            this.buildComponent();
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Main", "GetCurrency_ToolTip");
            }//end
            );
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            return;
        }//end

        private void  onGetCurrency (Event event )
        {
            StatsManager.count("get_coins", "click_ok");
            if (inEoQSale)
            {
                this.trackSales(PaymentsSaleManager.TYPE_EOQ_SALE);
            }
            else
            {
                this.trackSales(PaymentsSaleManager.TYPE_FLASH_SALE);
            }
            FrameManager.showTray("money.php?ref=coinsDialog");
            return;
        }//end

        private void  onFreeGiftOfferClick (Event event )
        {
            this.trackSales(PaymentsSaleManager.TYPE_FREE_GIFT_SALE);
            UI.displayPopup(Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FREE_GIFT_SALE).createStartupDialog());
            return;
        }//end

        private void  trackSales (String param1 )
        {
            PaymentsSale _loc_2 =null ;
            if (Global.paymentsSaleManager.isSaleActive(param1, false))
            {
                _loc_2 = Global.paymentsSaleManager.getSaleByType(param1);
                StatsManager.count(StatsCounterType.GAME_ACTIONS, _loc_2.statsName, "hud_icon", "clicked");
            }
            return;
        }//end

    }


