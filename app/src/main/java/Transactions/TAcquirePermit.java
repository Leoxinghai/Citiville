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

import Classes.*;
import Display.MarketUI.*;

    public class TAcquirePermit extends TFarmTransaction
    {
        protected Item m_item ;
        protected double m_donation =0;

        public  TAcquirePermit (String param1 )
        {
            this.m_item = Global.gameSettings().getItemByName(param1);
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.acquirePermit", this.m_item.name);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 != null && param1.itemName == this.m_item.name)
            {
                MarketCell.refreshUnlockedCells(this.m_item.name);
            }
            return;
        }//end

    }



