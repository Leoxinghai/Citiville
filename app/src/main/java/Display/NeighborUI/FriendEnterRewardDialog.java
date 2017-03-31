package Display.NeighborUI;

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
import Display.DialogUI.*;
//import flash.events.*;
//import flash.utils.*;

    public class FriendEnterRewardDialog extends GenericDialog
    {
        private Player m_friend ;
        private int m_coins ;
        private int m_energy ;
        private int m_xp ;

        public  FriendEnterRewardDialog (Player param1 ,int param2 =0,int param3 =0,int param4 =0,Function param5 =null ,boolean param6 =true )
        {
            this.m_friend = param1;
            this.m_coins = param2;
            this.m_energy = param3;
            this.m_xp = param4;
            super("", "", 0, param5, "", "", param6);
            return;
        }//end  

         protected void  loadAssets ()
        {
            Global.delayedAssets.get("assets/dialogs/NeighborVisitAssets.swf", makeAssets);
            return;
        }//end  

        public int  amount ()
        {
            _loc_1 = (FriendEnterRewardDialogView)m_jpanel
            return _loc_1.amount;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("closeBtnDown",  m_comObject.closeBtnDown);
            _loc_2.put("closeBtnOver",  m_comObject.closeBtnOver);
            _loc_2.put("closeBtnUp",  m_comObject.closeBtnUp);
            _loc_2.put("dialogBG",  m_comObject.dialogBG);
            _loc_2.put("coinIcon",  m_comObject.coinIcon);
            _loc_2.put("energyIcon",  m_comObject.energyIcon);
            _loc_2.put("starXPIcon",  m_comObject.starXPIcon);
            _loc_2.put("friend",  this.m_friend);
            m_jpanel = new FriendEnterRewardDialogView(_loc_2, this.m_coins, this.m_energy, this.m_xp, m_message, m_title, m_type, m_callback);
            finalizeAndShow();
            return;
        }//end  

    }



