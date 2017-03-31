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

    public class TCollectionTradeIn extends TFarmTransaction
    {
        protected String m_collectionName ;

        public  TCollectionTradeIn (String param1 )
        {
            this.m_collectionName = param1;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("CollectionsService.onTradeIn", this.m_collectionName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            return;
        }//end  

    }



