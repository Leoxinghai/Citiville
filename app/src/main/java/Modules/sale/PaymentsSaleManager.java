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

import Modules.sale.payments.*;
//import flash.utils.*;
import validation.*;

    public class PaymentsSaleManager
    {
        public Dictionary m_saleTypes ;
        protected Dictionary m_validations ;
        public static  String TYPE_FLASH_SALE ="flashSale";
        public static  String TYPE_FREE_GIFT_SALE ="freeGift";
        public static  String TYPE_OUT_OF_CASH_SALE ="outOfCashSale";
        public static  String TYPE_EOQ_SALE ="EoQSale";

        public  PaymentsSaleManager ()
        {
            _loc_1 =Global.gameSettings().saleSettingsXML ;
            this.initValidationScripts(_loc_1);
            this.initSales(_loc_1);
            return;
        }//end

        protected void  initValidationScripts (XMLList param1 )
        {
            XML _loc_2 =null ;
            GenericValidationScript _loc_3 =null ;
            this.m_validations = new Dictionary();
            for(int i0 = 0; i0 < param1.validate.size(); i0++)
            {
            		_loc_2 = param1.validate.get(i0);

                _loc_3 = Global.gameSettings().parseValidateTag(_loc_2);
                if (_loc_3)
                {
                    this.m_validations.put(_loc_3.name,  _loc_3);
                }
            }
            return;
        }//end

        protected void  initSales (XMLList param1 )
        {
            XML _loc_2 =null ;
            this.m_saleTypes = new Dictionary();
            for(int i0 = 0; i0 < param1.type.size(); i0++)
            {
            		_loc_2 = param1.type.get(i0);

                this.m_saleTypes.put(String(_loc_2.@name),  null);
            }
            return;
        }//end

        public void  refreshSales ()
        {
            String _loc_1 =null ;
            Array _loc_2 =null ;
            int _loc_3 =0;
            Object _loc_4 =null ;
            Object _loc_5 =null ;
            for(int i0 = 0; i0 < this.m_saleTypes.size(); i0++)
            {
            		_loc_1 = this.m_saleTypes.get(i0);

                _loc_2 = Global.gameSettings().getSaleDataByExperiment(_loc_1);
                _loc_3 = -1;
                _loc_4 = null;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_5 = _loc_2.get(i0);

                    if (_loc_5.priority > _loc_3 && this.validate(_loc_5))
                    {
                        _loc_3 = _loc_5.priority;
                        _loc_4 = _loc_5;
                    }
                }
                if (!this.m_saleTypes.get(_loc_1))
                {
                    this.m_saleTypes.put(_loc_1,  this.createSale(_loc_4));
                    continue;
                }
                this.m_saleTypes.get(_loc_1).init(_loc_4);
            }
            return;
        }//end

        protected boolean  validate (Object param1 )
        {
            GenericValidationScript _loc_3 =null ;
            boolean _loc_2 =true ;
            if (param1.hasOwnProperty("ifTrue"))
            {
                _loc_3 = this.m_validations.get(param1.ifTrue);
                _loc_2 = _loc_3 != null && _loc_3.validate(param1);
            }
            return _loc_2;
        }//end

        public PaymentsSale  getSaleByType (String param1 )
        {
            if (this.m_saleTypes)
            {
                return this.m_saleTypes.get(param1);
            }
            return null;
        }//end

        public boolean  isSaleActive (String param1 ,boolean param2 =true )
        {
            return this.m_saleTypes.get(param1) && this.m_saleTypes.get(param1).canShowSale(param2, param1);
        }//end

        public PaymentsSale  createSale (Object param1 )
        {
            _loc_2 = PaymentsSale;
            return new _loc_2(param1);
        }//end

    }



