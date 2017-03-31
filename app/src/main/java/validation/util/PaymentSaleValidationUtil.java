package validation.util;

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
//import flash.utils.*;
import validation.*;

    public class PaymentSaleValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  PaymentSaleValidationUtil ()
        {
            this.loadValidators();
            return;
        }//end

        public Function  getValidationCallback (String param1 )
        {
            return this.m_validators.get(param1);
        }//end

        private boolean  isDeadBuyer (int param1 )
        {
            if (Global.player.lastPurchaseDate <= 0)
            {
                return true;
            }
            if (!Global.player.hasMadeRealPurchase)
            {
                return false;
            }
            _loc_2 = (int)(GlobalEngine.getTimer ()/1000);
            _loc_3 = _loc_2(-Global.player.lastPurchaseDate )/DateUtil.SECONDS_PER_DAY ;
            return _loc_3 >= param1;
        }//end

        protected void  loadValidators ()
        {
            this.m_validators = new Dictionary();
            this .m_validators.put( "newBuyer", boolean  (Object param1 );
            {
                return Global.player.hasMadeRealPurchase == false;
            }//end
            ;
            this .m_validators.put( "newBuyerAbandoned", boolean  (Object param1 );
            {
                return Global.player.hasMadeRealPurchase == false && Global.player.getLastActivationTime("new_buyer_abandon") != -1;
            }//end
            ;
            this .m_validators.put( "deadBuyer", boolean  (Object param1 );
            {
                return isDeadBuyer(30);
            }//end
            ;
            this .m_validators.put( "deadBuyerAbandoned", boolean  (Object param1 );
            {
                return isDeadBuyer(30) && Global.player.getLastActivationTime("dead_buyer_abandon") != -1;
            }//end
            ;
            this .m_validators.put( "lessDeadBuyer", boolean  (Object param1 );
            {
                return isDeadBuyer(14);
            }//end
            ;
            this .m_validators.put( "lessDeadBuyerAbandoned", boolean  (Object param1 );
            {
                return isDeadBuyer(14) && Global.player.getLastActivationTime("dead_buyer_abandon") != -1;
            }//end
            ;
            this .m_validators.put( "eoqFlashSale", boolean  (Object param1 );
            {
                return true;
            }//end
            ;
            return;
        }//end

    }



