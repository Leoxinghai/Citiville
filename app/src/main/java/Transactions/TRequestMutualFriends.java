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
import Display.*;
import Display.NeighborUI.*;
import Engine.Transactions.*;

    public class TRequestMutualFriends extends Transaction
    {
        private String m_neighborID ;
        private boolean m_show ;

        public  TRequestMutualFriends (String param1 ,boolean param2 )
        {
            this.m_neighborID = param1;
            this.m_show = param2;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("VisitorService.listMutualFriends", MutualFriendInviteDialogView.NUM_PORTRAITS, this.m_neighborID);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            Player _loc_2 =null ;
            Array _loc_3 =null ;
            if (param1.hasOwnProperty("mutualFriends") && Global.isVisiting())
            {
                _loc_2 = Global.player.findFriendById(Global.getVisiting());
                _loc_3 = param1.get("mutualFriends");
                if (_loc_3 && _loc_3.length && Global.player.getShowMFIByID(_loc_2.uid))
                {
                    Global.player.mutualFriendsDialog = new MutualFriendInviteDialog(_loc_2, _loc_3);
                    if (this.m_show)
                    {
                        UI.displayPopup(Global.player.mutualFriendsDialog);
                    }
                }
                else if (this.m_show)
                {
                    NeighborVisitManager.triggerNeighborVisitFeeds();
                }
            }
            return;
        }//end  

    }



