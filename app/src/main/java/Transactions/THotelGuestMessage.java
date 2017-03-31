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
    public class THotelGuestMessage extends Transaction
    {
        private String m_ownerID ;
        private String m_guestID ;
        private double m_hotelID ;
        private String m_newMessage ;

        public  THotelGuestMessage (String param1 ,String param2 ,double param3 ,String param4 )
        {
            this.m_ownerID = param1;
            this.m_hotelID = param3;
            this.m_guestID = param2;
            this.m_newMessage = param4;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("ResortService.guestMessage", this.m_ownerID, this.m_guestID, this.m_hotelID, this.m_newMessage);
            return;
        }//end  

    }



