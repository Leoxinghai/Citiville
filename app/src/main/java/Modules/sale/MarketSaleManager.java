package Modules.sale;

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
    public class MarketSaleManager
    {
        public static  String EXPANSION_LOCK_OVERRIDE_COST ="ExpansionLockOverrideCost";

        public  MarketSaleManager ()
        {
            return;
        }//end

        public boolean  isItemOnSale (Item param1 ,String param2 )
        {
            boolean _loc_3 =false ;
            if (param1 && param1.sale != null)
            {
                if (param1.sale.isValid())
                {
                    _loc_3 = param1.sale.getDiscountPercent() > 0;
                }
            }
            if (param2 && _loc_3)
            {
                _loc_3 = param1.sale.doesSaleAffect(param2);
            }
            return _loc_3;
        }//end

        public int  getDiscountPercent (Item param1 )
        {
            int _loc_2 =0;
            if (param1 && param1.sale)
            {
                _loc_2 = param1.sale.getDiscountPercent();
            }
            return _loc_2;
        }//end

    }



