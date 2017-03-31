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
    public class TAutoCollectRent extends TFarmTransaction
    {
        private Array m_mapResourceIds ;

        public  TAutoCollectRent (Array param1 )
        {
            MapResource _loc_2 =null ;
            this.m_mapResourceIds = new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                this.m_mapResourceIds.push(_loc_2.getId());
            }
            return;
        }//end

        public Array  mapResourceIds ()
        {
            return this.m_mapResourceIds;
        }//end

        public int  numResources ()
        {
            return this.m_mapResourceIds.length;
        }//end

         public void  perform ()
        {
            signedCall("AutomationService.autoCollectRent", this.m_mapResourceIds, m_clientEnqueueTime);
            return;
        }//end

    }



