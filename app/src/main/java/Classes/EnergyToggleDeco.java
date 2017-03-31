package Classes;

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

import Mechanics.GameMechanicInterfaces.*;

    public class EnergyToggleDeco extends MechanicMapResource implements IMultiPickOwner
    {

        public  EnergyToggleDeco (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.ENERGY_TOGGLE_DECO;
            m_typeName = "energyToggle";
            return;
        }//end

         public void  onPurchase ()
        {
            Global.player.setEnergyEnableTime(this.getItem().firstTimeBonus + GlobalEngine.getTimer() / 1000);
            return;
        }//end

        public void  renewal (Object param1)
        {
            Global.player.setEnergyEnableTime(param1);
            if (Global.hud)
            {
                Global.hud.conditionallyRefreshHUD(true);
            }
            return;
        }//end

        public double  renewal ()
        {
            double _loc_1 =0;
            if (Global.player.featureData.get("energyEnableTimestamp"))
            {
                _loc_1 = Number(Global.player.featureData.get("energyEnableTimestamp"));
            }
            return _loc_1;
        }//end

        public boolean  clearDirtyOnUpdate ()
        {
            return false;
        }//end

    }



