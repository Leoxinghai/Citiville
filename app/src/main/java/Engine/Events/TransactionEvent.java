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
//import flash.events.*;

    public class TransactionEvent extends Event
    {
        public Transaction transaction ;
        public static  String COMPLETED ="completed";
        public static  String QUEUE_LIMIT_EXCEEDED ="exceeded_max_queued";
        public static  String QUEUE_LIMIT_NORMAL ="queue_normal";
        public static  String VERSION_MISMATCH ="version_mismatch";
        public static  String ADDED ="added";
        public static  String DISPATCHED ="dispatched";
        public static  String BATCH_SEND ="batch_send";
        public static  String INACTIVE ="inactive";
        public static  String OUT_OF_SYNC ="out_of_sync";
        public static  String RETRY_SUCCESS ="retry_success";

        public void  TransactionEvent (String param1 ,Transaction param2 )
        {
            this.transaction = param2;
            super(param1);
            return;
        }//end

    }



