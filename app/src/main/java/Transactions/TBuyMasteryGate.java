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

import Engine.Transactions.*;
    public class TBuyMasteryGate extends Transaction
    {
        private String m_itemName ;
        private String m_gateName ;
        private String m_seenFlagName ;
        private boolean m_success ;
        private String m_unlockItemName ;
        private int m_unlockCost ;

        public  TBuyMasteryGate (String param1 ,String param2 ,String param3 ,double param4 )
        {
            this.m_itemName = param1;
            this.m_gateName = param2;
            this.m_seenFlagName = param3;
            this.m_unlockCost = int(param4);
            Global.player.cash = Global.player.cash - this.m_unlockCost;
            Global.player.setSeenFlag(this.m_seenFlagName, false);
            return;
        }//end  

         public void  perform ()
        {
            signedCall("MasteryGateService.buyMasteryGate", this.m_itemName, this.m_gateName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            return;
        }//end  

    }



