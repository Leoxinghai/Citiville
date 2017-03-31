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

import Classes.inventory.*;
import Engine.Transactions.*;
import Modules.franchise.data.*;

    public class TFinalizeFranchisePendingName extends Transaction
    {
        private String m_franchiseType ;

        public  TFinalizeFranchisePendingName (String param1 )
        {
            this.m_franchiseType = param1;
            _loc_2 =Global.franchiseManager.getFranchise(param1 );
            if (_loc_2)
            {
                Global.franchiseManager.model.setFranchiseName(_loc_2.pendingName, param1);
                Global.franchiseManager.model.setPendingName(null, param1);
                Global.player.requestInventory.removeItem(RequestItemType.SIGNATURE, Global.franchiseManager.getSignatureCount(param1), param1);
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("FranchiseService.finalizeFranchisePendingName", this.m_franchiseType);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            if (param1.hasOwnProperty("name") && param1.name != "")
            {
                Global.franchiseManager.model.setFranchiseName(param1.name, this.m_franchiseType);
            }
            return;
        }//end

    }



