package Modules.flashsale.ui;

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
import Modules.sale.payments.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class FlashSaleDialog extends GenericDialog
    {
        protected PaymentsSale m_sale ;

        public  FlashSaleDialog (PaymentsSale param1 ,String param2 ,String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",String param7 ="",boolean param8 =true ,int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            this.m_sale = param1;
            super(param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.FLASHSALE_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = m_assetDependencies.get(DelayedAssetLoader.FLASHSALE_ASSETS) ;
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("dialog_bg", (DisplayObject) new _loc_1.dialog_flashSale_bg());
            _loc_2.put("icon_cashStack_left", (DisplayObject) new _loc_1.icon_cashStack_left());
            _loc_2.put("icon_cashStack_right", (DisplayObject) new _loc_1.icon_cashStack_right());
            _loc_2.put("loc_heading1",  ZLoc.t("Dialogs", "FlashSale_heading1"));
            _loc_2.put("loc_heading2",  Global.player.name);
            _loc_2.put("loc_body1",  ZLoc.t("Dialogs", "FlashSale_body1"));
            _loc_2.put("loc_body2",  ZLoc.t("Dialogs", "FlashSale_body2", {discount:this.m_sale.getSaleDiscount()}));
            _loc_2.put("loc_body3",  this.getBodyTextVariant());
            return _loc_2;
        }//end

      	 public void  show ()
        {
            if (!Global.ui)
            {
                return;
            }
            Debug.debug4("FlashSaleDialog.show");
            hide();
        }
         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            FlashSaleDialogView _loc_2 =new FlashSaleDialogView(param1 ,m_message ,m_title ,GenericDialogView.TYPE_MODAL ,null ,"",0,{listdata this.getListData ()});
            return _loc_2;
        }//end

        protected Array  getListData ()
        {
            return this .m_sale .getSales ().map (Object  (Object param1 ,int param2 ,Array param3 =null )
            {
                param1.index = param2;
                param1.callback = Curry.curry(purchasePackage, param1.id, param1.hasOwnProperty("statId") ? (param1.statId) : (0));
                return param1;
            }//end
            );
        }//end

        protected String  getBodyTextVariant ()
        {
            return ZLoc.t("Dialogs", "FlashSale_body3_variantA");
        }//end

        protected void  purchasePackage (int param1 ,int param2 ,*param3 =null )
        {
            this.m_sale.performPurchaseAction(param1, "flashsale", param2);
            countDialogAction("click_to_purchase", 1, "package_" + param1);
            (this.m_jpanel as GenericDialogView).close();
            return;
        }//end

         protected boolean  trackingIsSampled ()
        {
            return false;
        }//end

    }



