package Classes.bonus;

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
    public class CustomerMaintenanceBonus extends HarvestBonus
    {
        protected double m_payoutPerCustomer ;
        protected int m_maxPayout ;
        protected int m_minPayout ;
        protected int m_maintenancePercentModifier ;
        public static  int DEFAULT_MODIFIER =100;

        public  CustomerMaintenanceBonus (XML param1 )
        {
            super(param1);
            this.m_payoutPerCustomer = param1.@payoutPerCustomer;
            this.m_maxPayout = param1.@maxPayout;
            this.m_minPayout = param1.@minPayout;
            this.m_maintenancePercentModifier = param1.@maintenancePercentModifier;
            return;
        }//end

         public void  init (MapResource param1 )
        {
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            double _loc_5 =0;
            double _loc_6 =0;
            int _loc_7 =0;
            _loc_2 = param1as MechanicMapResource ;
            if (_loc_2 && this.m_payoutPerCustomer && this.m_maxPayout && this.m_minPayout)
            {
                _loc_3 = _loc_2.getDataForMechanic("harvestState");
                _loc_4 = _loc_2.getDataForMechanic("sleepState");
                if (_loc_3 && _loc_4 && _loc_3.customers != null && _loc_4.maintenance != null)
                {
                    _loc_5 = _loc_3.customers * this.m_payoutPerCustomer + this.m_minPayout;
                    _loc_6 = 0;
                    if (_loc_5 > this.m_maxPayout)
                    {
                        _loc_6 = this.m_maxPayout;
                    }
                    else
                    {
                        _loc_6 = _loc_5;
                    }
                    _loc_7 = _loc_4.maintenance == 0 ? (1) : (this.m_maintenancePercentModifier / 100 + 1);
                    m_percentModifier = (_loc_6 * _loc_7 - 1) * 100;
                }
                else
                {
                    m_percentModifier = DEFAULT_MODIFIER;
                }
            }
            else
            {
                m_percentModifier = DEFAULT_MODIFIER;
            }
            return;
        }//end

        public double  minPayout ()
        {
            return this.m_minPayout;
        }//end

         public double  maxPercentModifier ()
        {
            return this.m_maxPayout * (this.m_maintenancePercentModifier / 100 + 1);
        }//end

    }



