package Classes.util;

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
import Engine.Managers.*;

    public class UpgradesUtil
    {

        public  UpgradesUtil ()
        {
            return;
        }//end

        public static void  redirectToGiftPage (String param1 ,Array param2 )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3 == null || !_loc_3.giftable)
            {
                ErrorManager.addError("Invalid gift: " + param1);
                return;
            }
            Array _loc_4 =new Array();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            	_loc_5 = param2.get(i0);

                if (Global.player.isFriendIDInList(_loc_5))
                {
                    _loc_4.push(_loc_5);
                    continue;
                }
                ErrorManager.addError("ID does not belong to a friend: " + _loc_5);
            }
            _loc_6 = "gifts.php?action=chooseRecipient&gift=" + param1 + "&view=limited&include=" + _loc_4.join("%20") + "&ref=biz_upgrade";
            FrameManager.navigateTo(_loc_6);
            return;
        }//end

        public static int  calculateCashCostPerUpgradeAction (int param1 )
        {
            _loc_2 =Global.gameSettings().getNumber("businessUpgradeActionMultiplier",0.07);
            _loc_3 =Global.gameSettings().getNumber("businessUpgradeActionExponent",0.75);
            _loc_4 =Global.gameSettings().getNumber("businessUpgradeActionPremium",1);
            return Math.ceil(_loc_2 * Math.pow(param1, _loc_3) + _loc_4);
        }//end

    }



