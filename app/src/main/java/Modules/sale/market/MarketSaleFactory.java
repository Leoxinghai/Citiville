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

import Classes.util.*;
    public class MarketSaleFactory
    {
        private ReflectionFactory m_reflectionFactory ;

        public  MarketSaleFactory ()
        {
            this.m_reflectionFactory = new ReflectionFactory();
            this.m_reflectionFactory.register(ItemSaleDelegate);
            this.m_reflectionFactory.register(ExpansionSaleDelegate);
            this.m_reflectionFactory.defaultClass = ItemSaleDelegate;
            return;
        }//end

        public IMarketSale  createSale (XML param1 )
        {
            String _loc_2 =null ;
            if (param1.attribute("type") && param1.attribute("type").length() > 0)
            {
                _loc_2 = param1.attribute("type");
            }
            _loc_3 = this.m_reflectionFactory.createObject(_loc_2 )as IMarketSaleDelegate ;
            return new BaseMarketSale(_loc_3);
        }//end

    }



