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

    public class TStoredObjectAction extends TFarmTransaction
    {
        private String m_action =null ;
        private Function m_callback =null ;
        private Object m_params =null ;
        public static  String UPGRADE ="upgrade";

        public  TStoredObjectAction (String param1 ,Function param2 ,Object param3 =null )
        {
            this.m_action = param1;
            this.m_callback = param2;
            this.m_params = param3;
            return;
        }//end

         public void  perform ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            switch(this.m_action)
            {
                case UPGRADE:
                {
                    _loc_1 = this.m_params.get("storageId");
                    _loc_2 = this.m_params.get("storedObjectId");
                    signedCall("StoredObjectService.upgrade", _loc_1, _loc_2);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 && param1.success)
            {
            }
            if (this.m_callback != null)
            {
                this.m_callback.call();
            }
            return;
        }//end

    }



