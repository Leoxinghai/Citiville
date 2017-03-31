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

import Display.*;
import Events.*;
import Modules.matchmaking.*;
//import flash.events.*;

    public class TInitNeighbors extends TFarmTransaction
    {

        public  TInitNeighbors ()
        {
            addEventListener(Event.COMPLETE, MatchmakingManager.instance.onNeighborsLoad, false, 0, true);
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.initNeighbors",Config.userid);
            return;
        }//end

         public boolean  isInitTransaction ()
        {
            return true;
        }//end

         protected void  onComplete (Object param1 )
        {
            _loc_2 = param1.userInfo ;
            Global.player.gold = _loc_2.gold;
            Global.player.cash = _loc_2.cash;
            Global.player.level = _loc_2.level;
            Global.player.xp = _loc_2.xp;
            Global.player.heldEnergy = _loc_2.energy;
            Global.player.kdbomb = _loc_2.kdbomb;
            Global.player.lrbomb = _loc_2.lrbomb;

            Global.player.pregold = _loc_2.gold;


            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end


         protected void  onComplete (Object param1 )
        {
            _loc_2 = param1.neighbors ;
            Global.player.maxNeighbors = int(param1.neighborMax);
            this.parseNeighbors(_loc_2);
            Global.world.dispatchEvent(new FarmGameWorldEvent(FarmGameWorldEvent.USER_CHANGED));
            Global.world.dispatchEvent(new Event("InitNeighbors"));
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end
*/

        protected void  parseNeighbors (Array param1 )
        {
            _loc_2 =Global.friendbar != null;
            UI.populateFriendBarData(param1);
            if (Global.ui)
            {
                if (_loc_2)
                {
                    Global.ui.m_friendBar.addPlayerToFriends(Global.friendbar);
                    Global.ui.m_friendBar.updateNeighbors(Global.friendbar);
                }
                else
                {
                    Global.ui.m_friendBar.populateNeighbors(Global.friendbar);
                }
                Global.ui.setFriendBarPos(Math.max(Global.friendbar.length - 15, 0));
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



