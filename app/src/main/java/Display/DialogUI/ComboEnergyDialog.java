package Display.DialogUI;

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

import Classes.util.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.utils.*;

    public class ComboEnergyDialog extends GenericDialog
    {
        private Function m_buyCallback ;
        private Function m_askCallback ;
        private Function m_closeCallback ;

        public  ComboEnergyDialog (Function param1 ,Function param2 ,Function param3 =null )
        {
            StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_ENABLED, "prompted");
            this.m_buyCallback = param1;
            this.m_askCallback = param2;
            this.m_closeCallback = param3;
            super("");
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.ENERGY_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.energyDialog_bg());
            _loc_1.put("energyDialog_energyGuy",  new (DisplayObject)m_comObject.energyDialog_energyGuy());
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            ComboEnergyDialogView _loc_2 =new ComboEnergyDialogView(param1 ,this.m_buyCallback ,this.m_askCallback ,this.m_closeCallback );
            return _loc_2;
        }//end

    }



