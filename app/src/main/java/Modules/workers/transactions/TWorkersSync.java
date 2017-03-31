package Modules.workers.transactions;

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

import Modules.workers.*;
//import flash.events.*;

    public class TWorkersSync extends TWorkersTransaction
    {
        protected WorkerManager m_workerManager ;

        public  TWorkersSync (String param1 ,WorkerManager param2 )
        {
            this.m_workerManager = param2;
            super(param1);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("WorkerService.syncWorkers");
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (this.m_workerManager && param1)
            {
                this.m_workerManager.cleanUp();
                this.m_workerManager.loadObject(param1.workers);
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end  

    }



