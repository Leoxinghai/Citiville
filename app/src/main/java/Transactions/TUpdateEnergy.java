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

    public class TUpdateEnergy extends TFarmTransaction
    {

        public  TUpdateEnergy ()
        {
            m_functionName = "UserService.updateEnergy";
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.updateEnergy",Config.userid,Global.player.pregold-Global.player.gold,Global.player.cash,Global.player.kdbomb,Global.player.lrbomb);
            Global.player.pregold = Global.player.gold;
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            Global.player.gold = param1.gold;
            Global.player.pregold = Global.player.gold;
            
            _loc_2 = TFarmTransaction.m_gamedata;
            if (_loc_2 != null)
            {
                Global.player.setEnergyFromServer(_loc_2.energy, _loc_2.energyMax, _loc_2.lastEnergyCheck);
            }
            else if (Config.DEBUG_MODE)
            {
            }
            return;
        }//end  

    }



