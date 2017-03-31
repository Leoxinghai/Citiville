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

import Engine.Classes.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;

    public class GameViewport extends IsoViewport
    {
        private static boolean m_skipObjectCulling =false ;

        public  GameViewport ()
        {
            return;
        }//end

         protected void  onAddedToStage (Event event )
        {
            super.onAddedToStage(event);
            m_skipObjectCulling = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_OBJECT_CULLING);
            return;
        }//end

         protected void  drawBackground (BitmapData param1 )
        {
            param1.fillRect(param1.rect, Config.VIEWPORT_CLEAR_COLOR);
            _loc_2 = World.getInstance();
            _loc_3 = _loc_2.getTileMap ();
            _loc_3.render(this);
            if (!skipObjectCulling)
            {
                _loc_2.updateCulling();
            }
            return;
        }//end

        public static boolean  skipObjectCulling ()
        {
            return m_skipObjectCulling;
        }//end

    }


