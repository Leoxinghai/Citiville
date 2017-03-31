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

import Engine.Transactions.*;
    public class TUpdatePendingCityName extends Transaction
    {
        private String m_cityName ;

        public  TUpdatePendingCityName (String param1 )
        {
            this.m_cityName = param1;
            Global.player.pendingCityName = this.m_cityName;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.updatePendingCityName", this.m_cityName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1.hasOwnProperty("name") && param1.get("name") != this.m_cityName)
            {
                Global.player.cityName = param1.get("name");
            }
            return;
        }//end  

    }



