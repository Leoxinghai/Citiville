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

import GameMode.*;
//import flash.utils.*;

    public class TSellStoredItem extends TFarmTransaction
    {
        protected boolean m_sellAll ;
        protected Object m_gifts ;
        protected String m_origin ;
        private Function m_callBackFunc ;

        public  TSellStoredItem (Object param1 ,boolean param2 ,Function param3 ,int param4 =-1)
        {
            this.m_gifts = param1;
            this.m_sellAll = param2;
            this.m_callBackFunc = param3;
            this.m_origin = param4.toString();
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.sellStoredItem", this.m_gifts, this.m_sellAll, this.m_origin);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 !=null)
            {
                Global.player.gold = Global.player.gold + param1.total;
                if (param1.giftsReset && param1.giftsReset == true)
                {
                    Global.player.gifts = new Dictionary();
                }
            }
            Global.hud.conditionallyRefreshHUD(true);
            this.m_callBackFunc(param1);
            _loc_2 =Global.world.getTopGameMode ();
            if (_loc_2 is GMObjectMove)
            {
                Global.world.popGameMode();
            }
            return;
        }//end

    }



