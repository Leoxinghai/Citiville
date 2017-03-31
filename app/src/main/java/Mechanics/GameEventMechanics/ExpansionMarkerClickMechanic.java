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

import Engine.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.geom.*;

    public class ExpansionMarkerClickMechanic implements IActionGameMechanic
    {

        public  ExpansionMarkerClickMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return param1 == "GMPlay";
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Point _loc_3 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
            _loc_4 = IsoMath.screenPosToTilePos(_loc_3.x ,_loc_3.y );
            Global.world.wildernessSim.trySellExpansion(_loc_4.x, _loc_4.y, true);
            return new MechanicActionResult(true, true, false, null);
        }//end

    }



