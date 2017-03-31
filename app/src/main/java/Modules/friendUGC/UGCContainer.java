package Modules.friendUGC;

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

    public class UGCContainer
    {
        private String m_senderID ;
        private String m_receiverID ;
        private Object m_payload ;

        public  UGCContainer (String param1 ,String param2 ,Object param3 )
        {
            this.m_senderID = param1;
            this.m_receiverID = param2;
            this.m_payload = param3;
            return;
        }//end

        public String  senderID ()
        {
            return this.m_senderID;
        }//end

        public String  receiverID ()
        {
            return this.m_receiverID;
        }//end

        public Object  payload ()
        {
            return this.m_payload;
        }//end

        public void  payload (Object param1 )
        {
            this.m_payload = param1;
            return;
        }//end

    }



