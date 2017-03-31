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
    public class TOpenBusiness extends TWorldState
    {
        protected String m_desiredComm ;
        protected String m_usedComm ;
        protected boolean m_automated =false ;

        public  TOpenBusiness (Business param1 ,String param2 )
        {
            this.m_desiredComm = param2;
            this.m_usedComm = "";
            this.m_automated = param1.isBeingAutoHarvested();
            super(param1);
            return;
        }//end  

         public void  perform ()
        {
            if (this.m_automated)
            {
                signedWorldAction("autoOpenBusiness", this.m_desiredComm);
            }
            else
            {
                signedWorldAction("openBusiness", this.m_desiredComm);
            }
            return;
        }//end  

         protected void  onWorldActionComplete (Object param1 )
        {
            if (!param1)
            {
                return;
            }
            if (param1.get("usedCommodity"))
            {
                this.m_usedComm = param1.usedCommodity;
            }
            return;
        }//end  

        public String  desiredCommodity ()
        {
            return this.m_desiredComm;
        }//end  

        public String  usedCommodity ()
        {
            return this.m_usedComm;
        }//end  

    }



