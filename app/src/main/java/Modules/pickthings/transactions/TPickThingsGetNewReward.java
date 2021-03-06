package Modules.pickthings.transactions;

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
    public class TPickThingsGetNewReward extends Transaction
    {
        private String m_pickThings ;
        private int m_index ;
        private Function m_completeFunction ;

        public  TPickThingsGetNewReward (String param1 ,int param2 ,Function param3 =null )
        {
            this.m_pickThings = param1;
            this.m_index = param2;
            this.m_completeFunction = param3;
            return;
        }//end

         public void  perform ()
        {
            signedCall("PickThingsService.onSelectPiece", this.m_pickThings, this.m_index);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 && this.m_completeFunction instanceof Function)
            {
                this.m_completeFunction(param1);
            }
            return;
        }//end

    }



