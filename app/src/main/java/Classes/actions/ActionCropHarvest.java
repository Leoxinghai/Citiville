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
import Classes.util.*;
import com.xinghai.Debug;

    public class ActionCropHarvest extends ActionPickup
    {

        public  ActionCropHarvest (NPC param1 ,int param2 ,MapResource param3 )
        {
            super(param1, param2, param3);
            return;
        }//end

         protected void  onAppear ()
        {
            super.onAppear();
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

            Ship _loc_2 =null ;
            Plot _loc_3 =null ;
            super.onPickup();
            _loc_1 = (HarvestableResource)getTarget()

            Debug.debug5("ActionCropHarvest.onPickup"+_loc_1);

            if (!_loc_1)
            {
                return;
            }
            m_npc.getStateMachine().addActions(new ActionProgressBar(m_npc, _loc_1, _loc_1.getActionText(), _loc_1.getHarvestTime()));
            if (_loc_1 instanceof Ship)
            {
                _loc_2 = Ship(_loc_1);
                if (_loc_2.isLoaded())
                {
                    return;
                }
            }
            if (_loc_1 instanceof Plot)
            {
                _loc_3 = Plot(_loc_1);
                if (_loc_3.isHarvestable())
                {
                    return;
                }
            }
            Sounds.play("speedup");
            return;
        }//end

         protected void  onDisappear ()
        {
            super.onDisappear();
            return;
        }//end

    }



