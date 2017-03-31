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
    public class TAcquireSubLicense extends TFarmTransaction
    {
        protected Item m_item ;
        protected Item m_subitem ;
        protected double m_donation =0;

        public  TAcquireSubLicense (String param1 ,String param2 ,Function param3 =null )
        {
            this.m_item = Global.gameSettings().getItemByName(param1);
            this.m_subitem = Global.gameSettings().getItemByName(param2);
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.acquireSubLicense", this.m_item.name, this.m_subitem.name);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 != null && param1.itemName == this.m_item.name)
            {
            }
            return;
        }//end

    }



