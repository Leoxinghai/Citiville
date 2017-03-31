package Modules.franchise.transactions;

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

import Engine.Transactions.*;
    public class TUpdateFranchisePendingName extends Transaction
    {
        private String m_franchiseType ;
        private String m_franchiseName ;

        public  TUpdateFranchisePendingName (String param1 ,String param2 )
        {
            this.m_franchiseType = param1;
            this.m_franchiseName = param2;
            Global.franchiseManager.model.setPendingName(this.m_franchiseName, this.m_franchiseType);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("FranchiseService.updateFranchisePendingName", this.m_franchiseType, this.m_franchiseName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1.hasOwnProperty("name") && param1.get("name") != this.m_franchiseName)
            {
                Global.franchiseManager.model.setPendingName(param1.get("name"), this.m_franchiseType);
            }
            return;
        }//end  

    }



