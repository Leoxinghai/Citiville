package Transactions.lot;

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
import Classes.orders.*;
import Engine.Transactions.*;

    public class TLotOrderPlace extends Transaction
    {
        private LotOrder m_lotOrder ;
        private Function m_onFinish =null ;

        public  TLotOrderPlace (String param1 ,int param2 ,String param3 ,String param4 ,int param5 ,Function param6 )
        {
            this.m_lotOrder = new LotOrder(param1, Global.player.snUser.uid, param2, param3, param4, param5, OrderStatus.SENT);
            this.m_onFinish = param6;
            Global.world.orderMgr.placeOrder(this.m_lotOrder);
            _loc_7 =Global.gameSettings().getItemByName(param3 );
            if (Global.gameSettings().getItemByName(param3).cost > 0)
            {
                Global.player.gold = Global.player.gold - _loc_7.cost;
            }
            else if (_loc_7.cash > 0)
            {
                Global.player.cash = Global.player.cash - _loc_7.cash;
            }
            if (_loc_7.plantXp > 0)
            {
                Global.player.xp = Global.player.xp + _loc_7.plantXp;
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("LotOrderService.placeOrder", this.m_lotOrder.getParams());
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (this.m_onFinish != null)
            {
                this.m_onFinish(param1);
            }
            return;
        }//end

    }



