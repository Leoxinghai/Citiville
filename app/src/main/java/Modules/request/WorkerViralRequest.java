package Modules.request;

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

import Classes.virals.*;
    public class WorkerViralRequest extends ViralRequest
    {
        protected String m_messageKey ;
        protected String m_worldObjectId ;
        protected String m_featureName ;

        public  WorkerViralRequest (Array param1 ,Object param2 ,Function param3 =null ,Object param4 =null )
        {
            super(RequestType.WORKER_REQUEST, param1, param2, param3, param4);
            return;
        }//end

         public Object  getData ()
        {
            _loc_1 = super.getData();
            _loc_1.worldObjectId = this.m_worldObjectId;
            _loc_1.message = this.m_messageKey;
            _loc_1.featureName = this.m_featureName;
            return _loc_1;
        }//end

         public boolean  canSend ()
        {
            return m_recipientIds.length > 0;
        }//end

         protected void  onSendSuccessful (Array param1 )
        {
            return;
        }//end

         public void  setData (Object param1 )
        {
            super.setData(param1);
            this.m_worldObjectId = param1.worldObjectId ? (param1.worldObjectId) : ("");
            this.m_messageKey = param1.message ? (param1.message) : ("");
            this.m_featureName = param1.featureName ? (param1.featureName) : ("");
            return;
        }//end

    }



