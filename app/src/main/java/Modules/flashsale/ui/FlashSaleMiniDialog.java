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

    public class FlashSaleMiniDialog extends FlashSaleDialog
    {

        public  FlashSaleMiniDialog (PaymentsSale param1 ,String param2 ,String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",String param7 ="",boolean param8 =true ,int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = m_assetDependencies.get(DelayedAssetLoader.FLASHSALE_ASSETS) ;
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("dialog_bg", (DisplayObject) new _loc_1.dialog_flashSaleMini_bg());
            _loc_2.put("icon_cashStack_left", (DisplayObject) new _loc_1.icon_cashStack_left());
            _loc_2.put("icon_cashStack_right", (DisplayObject) new _loc_1.icon_cashStack_right());
            _loc_2.put("loc_heading1",  ZLoc.t("Dialogs", "FlashSale_heading1"));
            _loc_2.put("loc_heading2",  Global.player.name);
            _loc_2.put("loc_body1",  ZLoc.t("Dialogs", "FlashSale_body1"));
            _loc_2.put("loc_body2",  ZLoc.t("Dialogs", "FlashSale_body2", {discount:m_sale.getSaleDiscount()}));
            return _loc_2;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new FlashSaleMiniDialogView(param1, m_message, m_title, GenericDialogView.TYPE_MODAL, null, "", 0, {listdata:getListData()});
        }//end

    }



