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
import Events.*;

    public class TBuyCrew extends Transaction
    {
        private double m_targetObjectId ;
        private double m_cost ;
        private double m_cash ;
        private String m_gateName ;
        private static boolean m_alreadySeenThisSession =false ;

        public  TBuyCrew (double param1 ,double param2 ,double param3 ,String param4 ="")
        {
            GameObject _loc_6 =null ;
            this.m_targetObjectId = param1;
            this.m_cost = param2;
            this.m_gateName = param4;
            boolean _loc_5 =false ;
            if (param2 > 0 && Global.player.gold >= param2)
            {
                Global.player.gold = Global.player.gold - param2;
                _loc_5 = true;
            }
            else if (param3 > 0 && Global.player.cash >= param3)
            {
                Global.player.cash = Global.player.cash - param3;
                _loc_5 = true;
            }
            if (_loc_5)
            {
                Global.crews.addCrewById(param1, "-1");
                _loc_6 =(GameObject) Global.world.getObjectById(param1);
                if (!_loc_6)
                {
                    _loc_6 = MunicipalCenter.getStoredMunicipalById(param1);
                }
                if (_loc_6)
                {
                    _loc_6.onAddCrewMember();
                }
                Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.CREW_PURCHASE, null));
            }
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.purchaseCrewMember", this.m_targetObjectId, this.m_gateName);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            if (param1 == "purchaseCrewMember_refresh" && !m_alreadySeenThisSession)
            {
                UI.displayMessage(ZLoc.t("Main", "FriendsRespondedAlready"), GenericPopup.TYPE_OK, GameUtil.refreshUserState, "", true);
                m_alreadySeenThisSession = true;
            }
            return;
        }//end  

    }



