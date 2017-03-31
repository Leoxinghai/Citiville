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
//import flash.geom.*;
import com.adobe.crypto.*;
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;
    
import Classes.sim.*;
import Classes.Desires.*;
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Helpers.*;
import by.blooddy.crypto.*;

    
import com.xinghai.weibo.*;
import com.xinghai.Debug;
    
    
    public class TWeiboInit extends TFarmTransaction
    {
        protected int m_npcCount ;
        protected int m_commodityLeft ;
        protected String m_saying ;
        protected Point m_go ;
        
        public  TWeiboInit ()
        {
            Debug.debug7("TWeiboInit");
            funName = "Weibo.init";
            return;
        }//end  

         public void  perform ()
        {
            signedCall("Weibo.init", Config.userid);
            
            m_functionName = "Weibo.init";
            
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {


	     WeiboInitCommand weiboComm =new WeiboInitCommand ();
	     weiboComm.execute(param1);
	     
             //Global.weiboManager.levelUpgrading = false;
            return;
        }//end  

    }



