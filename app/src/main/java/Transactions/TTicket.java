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

    public class TTicket extends TFarmTransaction
    {
        private String m_action =null ;
        private Object m_params =null ;
        private Function m_callback =null ;
        public static  String REDEEM_TICKETS ="redeemTickets";
        public static  String UPDATE_LEVEL ="updateLevel";

        public  TTicket (String param1 ,Object param2 ,Function param3 =null )
        {
            this.m_action = param1;
            this.m_params = param2;
            this.m_callback = param3;
            return;
        }//end

        public String  getTheme ()
        {
            return this.m_params.get("theme");
        }//end

         public void  perform ()
        {
            switch(this.m_action)
            {
                case REDEEM_TICKETS:
                {
                    signedCall("TicketService.redeemTickets", this.m_params.get("theme"), this.m_params.get("rewardName"));
                    break;
                }
                case UPDATE_LEVEL:
                {
                    signedCall("TicketService.updateLevel", this.m_params.get("theme"));
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 && param1.result == "success")
            {
            }
            if (this.m_callback != null)
            {
                this.m_callback.call();
            }
            return;
        }//end

    }



