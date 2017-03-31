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
import Classes.util.*;
import Display.*;
import Engine.Transactions.*;

    public class TBuyRemainingCrew extends Transaction
    {
        private double m_targetObjectId ;
        private String m_gateName ;
        private static boolean m_alreadySeenThisSession =false ;

        public  TBuyRemainingCrew (double param1 ,String param2 ,double param3 =0)
        {
            this.m_targetObjectId = param1;
            this.m_gateName = param2;
            _loc_4 =Global.world.getObjectById(param1 )as GameObject ;
            if (Global.world.getObjectById(param1) as GameObject)
            {
                _loc_4.onAddCrewMember();
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.purchaseRemainingCrew", this.m_targetObjectId, this.m_gateName);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            if (param1 == "purchaseRemainingCrew_refresh" && !m_alreadySeenThisSession)
            {
                UI.displayMessage(ZLoc.t("Main", "FriendsRespondedAlready"), GenericPopup.TYPE_OK, GameUtil.refreshUserState, "", true);
                m_alreadySeenThisSession = true;
            }
            return;
        }//end

    }



