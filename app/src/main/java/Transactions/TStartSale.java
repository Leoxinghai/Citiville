package Transactions;

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
    public class TStartSale extends TFarmTransaction
    {
        private String m_name ;
        private String m_type ;

        public  TStartSale (PaymentsSale param1 )
        {
            this.m_type = param1.type;
            this.m_name = param1.name;
            _loc_2 = (int)(GlobalEngine.getTimer ()/1000);
            Global.player.setLastActivationTime(param1.name + "_start", _loc_2, true);
            if (param1.duration != PaymentsSale.NOT_SET)
            {
                Global.player.setLastActivationTime(param1.name + "_finish", _loc_2 + param1.duration, true);
            }
            if (param1.cooldownPeriod != PaymentsSale.NOT_SET)
            {
                Global.player.setLastActivationTime(param1.name + "_cooldown", _loc_2 + param1.cooldownPeriod + param1.duration, true);
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.startSale", this.m_name, this.m_type);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (this.m_name == "out_of_cash")
            {
                Global.player.hasSaleTransactionCompleted = true;
            }
            return;
        }//end

    }



