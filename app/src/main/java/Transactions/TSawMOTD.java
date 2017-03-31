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

    public class TSawMOTD extends TFarmTransaction
    {

        public  TSawMOTD ()
        {
            _loc_1 =Global.gameSettings().getString("motdFreeGift");
            if (_loc_1 != null && _loc_1 != "")
            {
                Global.player.addGift(_loc_1);
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.sawMOTD");
            return;
        }//end

         public boolean  isFaultable ()
        {
            return false;
        }//end

    }



