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

import Classes.ClientDataObjects.*;
//import flash.utils.*;

    public class TNPCVisitBatch extends TFarmTransaction
    {
        protected Object m_params ;

        public  TNPCVisitBatch (Dictionary param1 )
        {
            Dictionary _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            this.m_params = {};
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_3 = param1.get(i0);
                
                _loc_2 =(Dictionary) param1.get(_loc_3);
                this.m_params.put(_loc_3,  {});
                for(int i0 = 0; i0 < _loc_2.size(); i0++) 
                {
                		_loc_4 = _loc_2.get(i0);
                    
                    this.m_params.get(_loc_3).put(_loc_4,  (_loc_2.get(_loc_4) as NPCVisitBatchObject).exportTransactionParamsObject());
                }
            }
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.processVisitsBatch", this.m_params);
            return;
        }//end  

    }



