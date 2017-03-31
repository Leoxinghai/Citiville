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

import Classes.*;
    public class THolidayCheckPresents extends TWorldState
    {

        public  THolidayCheckPresents (HolidayTree param1 )
        {
            super(param1);
            return;
        }//end  

         public void  perform ()
        {
            signedWorldAction("holiday2010TreeUpdatePresentLevels");
            return;
        }//end  

         protected void  onWorldActionComplete (Object param1 )
        {
            if (!param1)
            {
                return;
            }
            if (param1.get("maxCount"))
            {
                HolidayTree.m_transactionMostPresents = param1.maxCount;
                HolidayTree.m_transactionLastPresents = param1.lastCount;
            }
            return;
        }//end  

    }



