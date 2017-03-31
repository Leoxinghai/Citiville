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

    public class TUnwither extends TFarmTransaction
    {
        private String m_action =null ;
        private Object m_params =null ;
        public static  String UNWITHER_ONE ="unwitherOne";
        public static  String UNWITHER_TYPE ="unwitherType";

        public  TUnwither (String param1 ,Object param2 )
        {
            this.m_action = param1;
            this.m_params = param2;
            return;
        }//end

         public void  perform ()
        {
            switch(this.m_action)
            {
                case UNWITHER_ONE:
                {
                    signedCall("UnwitherService.setUnwitheredForOne", this.m_params.get("objectId"));
                    break;
                }
                case UNWITHER_TYPE:
                {
                    signedCall("UnwitherService.setUnwitheredForType", this.m_params.get("type"));
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

    }



