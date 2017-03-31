package Modules.franchise.transactions;

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
    public class TFranchiseCollect extends Transaction
    {
        private String m_franchise ;
        private String m_neighbor ;
        private double m_expectedYield ;
        private Object m_result ;

        public  TFranchiseCollect (String param1 ,String param2 ,double param3 )
        {
            this.m_franchise = param1;
            this.m_neighbor = param2;
            this.m_expectedYield = param3;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("FranchiseService.onCollect", this.m_franchise, this.m_neighbor);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            this.m_result = param1;
            return;
        }//end  

    }



