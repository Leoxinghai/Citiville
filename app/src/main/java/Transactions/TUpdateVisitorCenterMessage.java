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

import Engine.Managers.*;
import Engine.Transactions.*;

    public class TUpdateVisitorCenterMessage extends Transaction
    {
        private String m_message ;

        public  TUpdateVisitorCenterMessage (String param1 )
        {
            this.m_message = param1;
            StatsManager.sample(1000, "dialog", "visitorcenter", "message_edit");
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.updateVisitorCenterMessage", this.m_message);
            return;
        }//end  

    }



