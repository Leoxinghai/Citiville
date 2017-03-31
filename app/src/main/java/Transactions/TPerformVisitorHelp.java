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
import Classes.orders.*;
import Engine.Transactions.*;
import Modules.quest.Helpers.*;

    public class TPerformVisitorHelp extends Transaction
    {
        private VisitorHelpOrder m_visitorHelpOrder ;
        private String m_type ;
        private GameObject m_gameObj ;

        public  TPerformVisitorHelp (String param1 ,GameObject param2 ,String param3 )
        {
            _loc_4 = param2.getId ();
            this.m_gameObj = param2;
            this.m_visitorHelpOrder = new VisitorHelpOrder(param1, Global.player.snUser.uid, [_loc_4], OrderStatus.SENT);
            this.m_type = param3;
            if (this.shouldPlaceOrder(param3))
            {
                Global.world.orderMgr.placeOrder(this.m_visitorHelpOrder);
            }
            return;
        }//end

        public String  getHelpType ()
        {
            return this.m_type;
        }//end

        public GameObject  getGameObject ()
        {
            return this.m_gameObj;
        }//end

         public void  perform ()
        {
            if (Global.world.mostFrequentHelpers.get("i" + Global.player.snUser.uid))
            {
                _loc_1 =Global.world.mostFrequentHelpers.get( "i"+Global.player.snUser.uid) ;
                int _loc_2 =2;
                _loc_3 =Global.world.mostFrequentHelpers.get( "i"+Global.player.snUser.uid).get(2) +1;
                _loc_1.put(_loc_2,  _loc_3);
            }
            else
            {
                Global.world.mostFrequentHelpers.put("i" + Global.player.snUser.uid,  .get(Global.player.name, Global.player.cityName, 1));
            }
            signedCall("VisitorService.help", "visitorHelp", this.m_type, this.m_visitorHelpOrder.getParams());
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            return;
        }//end

        protected boolean  shouldPlaceOrder (String param1 )
        {
            boolean _loc_2 =false ;
            switch(param1)
            {
                case VisitorHelpType.HOTEL_CHECKIN:
                {
                    _loc_2 = false;
                    break;
                }
                default:
                {
                    _loc_2 = true;
                    break;
                    break;
                }
            }
            return _loc_2;
        }//end

    }



