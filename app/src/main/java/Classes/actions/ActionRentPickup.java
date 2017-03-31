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
//import flash.utils.*;
import com.xinghai.Debug;

    public class ActionRentPickup extends ActionPickup
    {
        private Timer m_pickupTimer ;
        private static  int PICKUP_DELAY =1000;

        public  ActionRentPickup (NPC param1 ,int param2 ,MapResource param3 )
        {
            super(param1, param2, param3);
            Debug.debug4("ActionRentPickup."+param3);
            return;
        }//end  

         protected void  onAppear ()
        {
            Debug.debug4("ActionRentPickup.onAppear");
            super.onAppear();
            return;
        }//end  

         protected void  onTravel ()
        {
            Debug.debug4("ActionRentPickup.onTravel");
            super.onTravel();
            if (!getTarget())
            {
                return;
            }
            return;
        }//end  

         protected void  onPickup ()
        {
            super.onPickup();
            _loc_1 = (HarvestableResource)getTarget()
            Debug.debug5("ActionRentPickup.onPickup" + _loc_1);
            if (!_loc_1)
            {
                return;
            }
            m_npc.getStateMachine().addActions(new ActionProgressBar(m_npc, _loc_1, _loc_1.getActionText(), _loc_1.getHarvestTime()));
            return;
        }//end  

         protected void  onDisappear ()
        {
            Debug.debug4("ActionRentPickup.onDisappear");
            super.onDisappear();
            return;
        }//end  

    }



