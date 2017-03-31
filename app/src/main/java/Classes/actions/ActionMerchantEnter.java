package Classes.actions;

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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ActionMerchantEnter extends NPCAction
    {
        protected IMerchant m_merchant ;
        public static  String GAME_EVENT ="NPCEnterAction";

        public  ActionMerchantEnter (NPC param1 ,IMerchant param2 )
        {
            super(param1);
            this.m_merchant = param2;
            return;
        }//end

         public void  update (double param1 )
        {
            IMechanicUser _loc_2 =null ;
            Array _loc_3 =null ;
            super.update(param1);
            if (this.m_merchant instanceof IMechanicUser)
            {
                _loc_2 =(IMechanicUser) this.m_merchant;
                _loc_3 = _loc_2.getPrioritizedMechanicsForGameEvent(GAME_EVENT);
                if (_loc_3 && _loc_3.length())
                {
                    MechanicManager.getInstance().handleAction(_loc_2, GAME_EVENT, [m_npc]);
                    m_npc.getStateMachine().removeState(this);
                    return;
                }
            }
            this.m_merchant.crowdManager.makeNpcEnterMerchant(m_npc);
            m_npc.getStateMachine().removeState(this);
            return;
        }//end

    }



