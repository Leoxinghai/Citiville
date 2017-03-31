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


    public class ExpansionSaleDelegate implements IMarketSaleDelegate
    {
        private Vector<ExpansionSaleTier> m_saleTiers;

        public  ExpansionSaleDelegate ()
        {
            this.m_saleTiers = new Vector<ExpansionSaleTier>();
            return;
        }//end

        public void  loadObject (XML param1 )
        {
            XML _loc_3 =null ;
            ExpansionSaleTier _loc_4 =null ;
            _loc_2 = param1.saleTiers.saleTier ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = ExpansionSaleTier.createFromXML(_loc_3);
                this.m_saleTiers.push(_loc_4);
            }
            return;
        }//end

        public int  getDiscountPercent ()
        {
            ExpansionSaleTier _loc_2 =null ;
            int _loc_1 =0;
            if (Global.player.level >= 8)
            {
                _loc_2 = this.getSaleTierByExpansionLevel(Global.player.expansionCostLevel);
                if (_loc_2 != null)
                {
                    _loc_1 = _loc_2.discountPercent;
                }
            }
            return _loc_1;
        }//end

        private ExpansionSaleTier  getSaleTierByExpansionLevel (int param1 )
        {
            ExpansionSaleTier _loc_3 =null ;
            ExpansionSaleTier _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_saleTiers.size(); i0++)
            {
            		_loc_3 = this.m_saleTiers.get(i0);

                if (_loc_3.minExpansionNumber <= param1)
                {
                    _loc_2 = _loc_3;
                    continue;
                }
                break;
            }
            return _loc_2;
        }//end

    }



