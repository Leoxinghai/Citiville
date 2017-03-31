﻿package Transactions.lot;

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

import Classes.orders.*;
import Engine.Transactions.*;

    public class TLotOrderCancel extends Transaction
    {
        private LotOrder m_lotOrder ;

        public  TLotOrderCancel (LotOrder param1 )
        {
            this.m_lotOrder = param1;
            this.m_lotOrder.setState(OrderStates.CANCEL);
            Global.world.orderMgr.removeOrder(this.m_lotOrder);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("LotOrderService.cancelOrder", this.m_lotOrder.getParams());
            return;
        }//end  

    }



