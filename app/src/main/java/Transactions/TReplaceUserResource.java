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
    public class TReplaceUserResource extends TWorldState
    {
        private double m_oldResourceId ;
        private String m_newResourceName ;
        private boolean m_isGift ;
        private boolean m_isUsingConstruction ;

        public  TReplaceUserResource (String param1 ,MapResource param2 ,boolean param3 =false ,boolean param4 =false )
        {
            Item _loc_5 =null ;
            super(param2);
            this.m_oldResourceId = param2.getId();
            this.m_newResourceName = param1;
            this.m_isGift = param3;
            this.m_isUsingConstruction = param4;
            if (!param3)
            {
                _loc_5 = Global.gameSettings().getItemByName(this.m_newResourceName);
                if (_loc_5.cost > 0)
                {
                    Global.player.gold = Global.player.gold - _loc_5.cost;
                }
                else if (_loc_5.cash > 0)
                {
                    Global.player.cash = Global.player.cash - _loc_5.cash;
                }
                else
                {
                    GlobalEngine.msg(_loc_5.name + " cost nothing, make sure this is intentional behavior.");
                }
                if (_loc_5.plantXp > 0)
                {
                    Global.player.xp = Global.player.xp + _loc_5.plantXp;
                }
            }
            return;
        }//end

        public String  newResourceName ()
        {
            return this.m_newResourceName;
        }//end

         public void  perform ()
        {
            signedCall("UserService.onReplaceUserResource", Global.world.ownerId, Global.player.uid, this.m_oldResourceId, this.m_newResourceName, this.m_isGift, this.m_isUsingConstruction);
            return;
        }//end

    }



