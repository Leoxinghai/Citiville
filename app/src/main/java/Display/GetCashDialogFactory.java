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

import Classes.*;
import Modules.sale.*;
//import flash.display.*;

    public class GetCashDialogFactory
    {

        public void  GetCashDialogFactory (String param1 ,String param2 ,Item param3 =null )
        {
            _loc_4 =Global.stage.getChildByName(param2 );
            if (Global.stage.getChildByName(param2) == null)
            {
                Global.paymentsSaleManager.refreshSales();
                if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_OUT_OF_CASH_SALE))
                {
                    UI.displayPopup(Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_OUT_OF_CASH_SALE).createManualDialog(param3));
                }
                else
                {
                    UI.displayPopup(new ImpulseBuy(param1), false, param2, true);
                }
            }
            return;
        }//end

    }


