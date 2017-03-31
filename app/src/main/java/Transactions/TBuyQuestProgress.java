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

import Engine.Managers.*;
import Engine.Transactions.*;
import Modules.quest.Managers.*;

    public class TBuyQuestProgress extends Transaction
    {
        private String m_questName ;
        private int m_taskIndex ;
        private int m_expectedPrice ;

        public  TBuyQuestProgress (String param1 ,int param2 ,int param3 )
        {
            this.m_questName = param1;
            this.m_taskIndex = param2;
            this.m_expectedPrice = param3;
            return;
        }//end  

         public void  perform ()
        {
            if (GameQuestUtility.isQuestValid(this.m_questName))
            {
                signedCall("UserService.purchaseQuestProgress", this.m_questName, this.m_taskIndex);
            }
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            if (this.m_expectedPrice && param1.price != null)
            {
                if (this.m_expectedPrice != param1.price)
                {
                    ErrorManager.addError("Quest price did not match for " + this.m_questName + ", expected " + this.m_expectedPrice + ", received " + param1.price, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
            }
            return;
        }//end  

    }



