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

import Classes.inventory.*;
import Display.*;
import Engine.Transactions.*;
import Modules.franchise.*;

    public class TFinalizePendingCityName extends Transaction
    {

        public  TFinalizePendingCityName ()
        {
            Global.player.cityName = Global.player.pendingCityName;
            UI.setCityName(Global.player.pendingCityName);
            Global.player.pendingCityName = null;

            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.finalizePendingCityName");
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            if (param1.hasOwnProperty("name") && param1.name != "")
            {
                Global.player.cityName = param1.name;
            }
            UI.setCityName(Global.player.cityName);
            return;
        }//end  

    }



