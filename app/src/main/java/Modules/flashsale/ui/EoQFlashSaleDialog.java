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
//import flash.display.*;
//import flash.utils.*;

    public class EoQFlashSaleDialog extends FlashSaleDialog
    {

        public  EoQFlashSaleDialog (PaymentsSale param1 ,String param2 ,String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",String param7 ="",boolean param8 =true ,int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            m_sale = param1;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            EoQFlashSaleDialogView _loc_2 =new EoQFlashSaleDialogView(param1 ,m_message ,m_title ,GenericDialogView.TYPE_MODAL ,null ,"",0,{listdata getListData (),endDate.endDate });
            return _loc_2;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.EOQSALE_ASSETS, DelayedAssetLoader.FLASHSALE_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = m_assetDependencies.get(DelayedAssetLoader.EOQSALE_ASSETS) ;
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.FLASHSALE_ASSETS) ;
            Dictionary _loc_3 =new Dictionary ();
            _loc_3.put("dialog_bg", (DisplayObject) new _loc_1.EoQSale_bg());
            _loc_3.put("EoQSale_cash", (DisplayObject) new _loc_1.EoQSale_cash());
            _loc_3.put("EoQSale_burst_bg", (DisplayObject) new _loc_1.EoQSale_burst_bg());
            _loc_3.put("EoQSale_timer_large", (DisplayObject) new _loc_1.EoQSale_timer_large());
            _loc_3.put("EoQSale_sideOption_bg", (DisplayObject) new _loc_1.EoQSale_sideOption_bg());
            _loc_3.put("EoQSale_middleOption_bg", (DisplayObject) new _loc_1.EoQSale_middleOption_bg());
            _loc_3.put("EoQSale_flashSaleButton_up", (DisplayObject) new _loc_1.EoQSale_flashSaleButton_up());
            _loc_3.put("EoQSale_flashSaleButton_over", (DisplayObject) new _loc_1.EoQSale_flashSaleButton_over());
            _loc_3.put("loc_congrats",  ZLoc.t("Dialogs", "congratulations_title"));
            _loc_3.put("loc_playerName",  Global.player.name);
            _loc_3.put("loc_specialOffer",  ZLoc.t("Dialogs", "FlashSale_body1"));
            _loc_3.put("loc_discountAmount",  ZLoc.t("Dialogs", "FlashSale_body2", {discount:m_sale.getSaleDiscount()}));
            _loc_3.put("loc_subText",  ZLoc.t("Dialogs", "eoq_subText"));
            return _loc_3;
        }//end

    }



