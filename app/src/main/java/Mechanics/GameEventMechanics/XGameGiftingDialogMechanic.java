package Mechanics.GameEventMechanics;

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

import Engine.Managers.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.utils.*;
import Mechanics.GameMechanicInterfaces.*;

    public class XGameGiftingDialogMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {

        public  XGameGiftingDialogMechanic ()
        {
            return;
        }//end

         public boolean  canPopDialog ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FARMVILLE_GIFTING );
            return _loc_1 == ExperimentDefinitions.FARMVILLE_GIFTING;
        }//end

         public DisplayObject  instantiateDialog ()
        {
            StatsManager.count("game_actions", "click_farm_balloon");
            _loc_1 = getDefinitionByName(m_config.params.get( "dialogToPop") )as Class ;
            _loc_2 = new _loc_1(m_config.params.get( "gameId") );
            return _loc_2;
        }//end

    }



