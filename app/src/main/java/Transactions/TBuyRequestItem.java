﻿package Transactions;

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

import Engine.Transactions.*;
    public class TBuyRequestItem extends Transaction
    {
        private String m_inventoryName ;
        private String m_itemName ;

        public  TBuyRequestItem (String param1 ,String param2 )
        {
            this.m_itemName = param1;
            this.m_inventoryName = param2;
            Global.player.requestInventory.addItem(param1, 1, param2);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.buyRequestItem", this.m_itemName, this.m_inventoryName);
            return;
        }//end  

    }



