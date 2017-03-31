package Modules.sale.market;

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

    public class ItemSaleDelegate implements IMarketSaleDelegate
    {
        private int m_discountPercent ;

        public  ItemSaleDelegate ()
        {
            this.m_discountPercent = 0;
            return;
        }//end

        public void  loadObject (XML param1 )
        {
            this.m_discountPercent = this.parseDiscountPercent(param1);
            return;
        }//end

        public int  getDiscountPercent ()
        {
            return this.m_discountPercent;
        }//end

        private int  parseDiscountPercent (XML param1 )
        {
            int _loc_2 =0;
            if (param1 != null)
            {
                _loc_2 = parseInt(param1.toString());
            }
            return _loc_2;
        }//end

    }



