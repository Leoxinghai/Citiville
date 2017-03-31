package Modules.realtime;

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

import Engine.Events.*;
import Engine.Managers.*;
import Classes.sim.*;

    public class RealtimeObserver implements IPlayerStateObserver
    {

        public  RealtimeObserver ()
        {
            Global.player.addObserver(this);
            TransactionManager.getInstance().addEventListener(TransactionEvent.ADDED, this.onTransactionAdded);
            TransactionManager.getInstance().addEventListener(TransactionEvent.COMPLETED, this.onTransactionCompleted);
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

        public void  onTransactionAdded (TransactionEvent event )
        {
            return;
        }//end

        public void  onTransactionCompleted (TransactionEvent event )
        {
            return;
        }//end

    }



