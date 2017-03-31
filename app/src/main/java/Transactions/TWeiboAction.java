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
//import flash.geom.*;
import com.xinghai.weibo.*;
    
import com.xinghai.Debug;
    

    public class TWeiboAction extends TFarmTransaction
    {
        protected int m_npcCount ;
        protected int m_commodityLeft ;
        protected String m_saying ;
        protected Point m_go ;
        

        public  TWeiboAction (String param2 ,Point param3 )
        {
            Debug.debug7("TWeiboAction");
            this.m_saying = param2;
            this.m_go = param3;
            funName = "Weibo.performAction";
            return;
        }//end  

         public void  perform ()
        {
            signedCall("Weibo.performAction", Config.userid,Global.weiboManager.weiboseq ,Global.weiboManager.weiboactionseq , m_saying, m_go);
            m_functionName = "Weibo.performAction";
            
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            Debug.debug7("TWeiboAction.onWorldActionComplete ");
	    WeiboTaskCommand weiboComm =new WeiboTaskCommand ();
	    weiboComm.execute(param1);
            Global.weiboManager.m_sending = false;
            return;
        }//end  

    }



