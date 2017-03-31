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
import com.xinghai.Debug;

    public class THarvestBusiness extends TWorldState
    {
        protected Business m_business ;
        protected int m_npcCount ;
        protected int m_commodityLeft ;

        public  THarvestBusiness (Business param1 ,int param2 ,int param3 )
        {
            Debug.debug5("THarvestBusiness");
            this.m_business = param1;
            this.m_npcCount = param2;
            this.m_commodityLeft = param3;
            super(param1);
            return;
        }//end  

         public void  perform ()
        {
            signedWorldAction("harvest", this.m_npcCount, this.m_commodityLeft);
            return;
        }//end  

         protected void  onWorldActionComplete (Object param1 )
        {
            this.m_business.onHarvestComplete(param1);
            return;
        }//end  

    }



