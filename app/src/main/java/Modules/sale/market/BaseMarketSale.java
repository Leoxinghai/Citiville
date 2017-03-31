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

//import flash.utils.*;
import validation.*;

    public class BaseMarketSale implements IMarketSale
    {
        private IMarketSaleDelegate m_saleDelegate ;
        private GenericValidationScript m_validator ;
        private Dictionary m_alsoAffects ;

        public  BaseMarketSale (IMarketSaleDelegate param1 )
        {
            this.m_saleDelegate = param1;
            this.m_validator = null;
            this.m_alsoAffects = new Dictionary();
            return;
        }//end

        public void  loadObject (XML param1 )
        {
            XML _loc_2 =null ;
            param1 = this.filterXMLVariants(param1);
            if (param1.validate.length > 0)
            {
                this.m_validator = Global.gameSettings().parseValidateTag(param1.validate.get(0));
            }
            for(int i0 = 0; i0 < param1.alsoAffects.affects.size(); i0++)
            {
            		_loc_2 = param1.alsoAffects.affects.get(i0);

                this.m_alsoAffects.put(String(_loc_2.attribute("type")),  true);
            }
            this.m_saleDelegate.loadObject(param1);
            return;
        }//end

        public int  getDiscountPercent ()
        {
            return this.m_saleDelegate.getDiscountPercent();
        }//end

        public boolean  doesSaleAffect (String param1 )
        {
            return this.m_alsoAffects.get(param1);
        }//end

        public boolean  isValid ()
        {
            return !this.m_validator || this.m_validator.validate();
        }//end

        private XML  filterXMLVariants (XML param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_5 =null ;
            if (param1 == null)
            {
                return null;
            }
            _loc_2 = param1.variant ;
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_4 = _loc_3.@experimentName.get(0);
                    if (_loc_4 == null || _loc_4.length == 0)
                    {
                        continue;
                    }
                    _loc_5 = this.parseIntList(_loc_3.@experimentVariants.get(0));
                    if (_loc_5 == null)
                    {
                        continue;
                    }
                    if (_loc_5.indexOf(Global.experimentManager.getVariant(_loc_4)) >= 0)
                    {
                        return _loc_3;
                    }
                }
            }
            return param1;
        }//end

        private Array  parseIntList (String param1 )
        {
            if (param1 == null)
            {
                return null;
            }
            _loc_2 = param1.split(",");
            _loc_3 = _loc_2.length ;
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_2.put(_loc_4,  parseInt(_loc_2.get(_loc_4)));
                _loc_4++;
            }
            return _loc_2;
        }//end

    }



