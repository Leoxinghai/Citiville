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

    public class TGetMFSData extends TFarmTransaction
    {

        public  TGetMFSData ()
        {
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.getMFSData");
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Object _loc_3 =null ;
            Array _loc_4 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.get("nonAppFriends").size(); i0++) 
            {
            		_loc_3 = param1.get("nonAppFriends").get(i0);

                _loc_2.push(_loc_3);
            }
            Global.player.nonAppFriends = _loc_2;
            _loc_4 = new Array();
            for(int i0 = 0; i0 < param1.get("appFriends").size(); i0++) 
            {
            		_loc_3 = param1.get("appFriends").get(i0);

                _loc_4.push(_loc_3);
            }
            Global.player.appFriends = _loc_4;
            return;
        }//end

    }



