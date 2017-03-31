package Engine.Events;

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
    public class TransactionFaultEvent extends TransactionEvent
    {
        public int errorType ;
        public Object errorData ;
        public static  String FAULT ="fault";
        public static  String RETRY ="retry";

        public void  TransactionFaultEvent (String param1 ,Transaction param2 ,int param3 ,Object param4 )
        {
            this.transaction = param2;
            this.errorType = param3;
            this.errorData = param4;
            super(param1);
            return;
        }//end  

    }



