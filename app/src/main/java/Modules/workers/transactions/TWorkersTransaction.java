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

import Engine.Transactions.*;
    public class TWorkersTransaction extends Transaction
    {
        protected String m_feature ;

        public  TWorkersTransaction (String param1 )
        {
            this.m_feature = param1;
            return;
        }//end  

         protected void  signedCall (String param1 ,...args )
        {
            Array argsvalue =.get(param1 ,this.m_feature) ;
            super.signedCall.apply(this, argsvalue.concat(args));
            return;
        }//end  

    }



