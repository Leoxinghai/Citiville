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

import Engine.Events.*;
import Engine.Transactions.*;

    public class TFranchiseDailyBonus extends Transaction
    {
        private String m_franchise ;
        private Object m_result ;

        public  TFranchiseDailyBonus (String param1 )
        {
            this.m_franchise = param1;
            return;
        }//end

         public void  perform ()
        {
            signedCall("FranchiseService.onCollectDailyBonus", this.m_franchise);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            this.m_result = param1;
            return;
        }//end

         protected void  onAmfComplete (Object param1 )
        {
            m_rawResult = param1;
            if (param1 !=null)
            {
                if (param1.errorType == NO_ERROR)
                {
                    logEvent("Complete");
                    if (param1.data != null)
                    {
                        this.onComplete(param1.data);
                    }
                }
                else
                {
                    if (param1.errorData)
                    {
                        logEvent("Error " + param1.errorType + " " + param1.errorData);
                    }
                    else
                    {
                        logEvent("Error " + param1.errorType);
                    }
                    onFault(param1.errorType, param1.errorData);
                }
            }
            dispatchEvent(new TransactionEvent(TransactionEvent.COMPLETED, this));
            return;
        }//end

    }



