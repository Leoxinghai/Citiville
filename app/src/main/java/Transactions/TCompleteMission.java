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

    public class TCompleteMission extends TFarmTransaction
    {
        protected String m_userId ;
        protected String m_missionHostId ;
        protected String m_missionType ;

        public  TCompleteMission (String param1 )
        {
            this.m_userId = Global.player.snUser.uid;
            this.m_missionHostId = Global.getVisiting();
            this.m_missionType = param1;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("MissionService.completeMission", this.m_missionHostId.toString(), this.m_missionType);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1 != null && param1.showFeed == true)
            {
            }
            return;
        }//end  

    }



