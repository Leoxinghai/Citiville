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

    public class TRoundTripSentinel extends TFarmTransaction
    {
        private Function m_performCallback =null ;
        private Function m_completeCallback =null ;

        public  TRoundTripSentinel (Function param1 ,Function param2 )
        {
            this.m_performCallback = param1;
            this.m_completeCallback = param2;
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.pingFeedQuests");
            if (this.m_performCallback != null)
            {
                this.m_performCallback();
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            if (this.m_completeCallback != null)
            {
                this.m_completeCallback();
            }
            return;
        }//end

    }



