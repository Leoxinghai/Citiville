package Modules.guide.actions;

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
//import flash.events.*;
//import flash.geom.*;

    public class GAWaitForTransactionOrClickGround extends GAWaitForTransaction
    {

        public  GAWaitForTransactionOrClickGround ()
        {
            return;
        }//end

        private void  stageClickHandler (MouseEvent event )
        {
            Point _loc_2 =new Point(event.stageX ,event.stageY );
            _loc_3 = IsoMath.stageToViewport(_loc_2 );
            _loc_4 =Global.world.getPickObjects(_loc_3 );
            _loc_5 = IsoMath.screenPosToTilePos(_loc_2.x ,_loc_2.y );
            _loc_6 =Global.world.getCollisionMap ().getObjectsByPosition(Math.floor(_loc_5.x ),Math.floor(_loc_5.y ));
            if (_loc_4.length == 0 && _loc_6.length == 0)
            {
                m_seq.stop();
            }
            return;
        }//end

         public void  enter ()
        {
            super.enter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end

         public void  reenter ()
        {
            super.reenter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end

         public void  exit ()
        {
            super.exit();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end

         public void  removed ()
        {
            super.removed();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end

    }



