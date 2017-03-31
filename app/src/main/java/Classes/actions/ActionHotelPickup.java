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
    public class ActionHotelPickup extends ActionPickup
    {

        public  ActionHotelPickup (NPC param1 ,int param2 ,MapResource param3 )
        {
            super(param1, param2, param3);
            return;
        }//end  

         protected void  onTravel ()
        {
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
            if (!_loc_1)
            {
                return;
            }
            m_npc.getStateMachine().addActions(new ActionProgressBar(m_npc, _loc_1, _loc_1.getActionText(), _loc_1.getHarvestTime()));
            return;
        }//end  

    }



