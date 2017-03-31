package Display.aswingui.buttonui;

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

//import flash.ui.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.plaf.basic.*;

    public class CashTimeEnergyButtonUI extends BasicButtonUI
    {
        private static IntRectangle viewRect =new IntRectangle ();
        private static IntRectangle textRect =new IntRectangle ();
        private static IntRectangle iconRect =new IntRectangle ();

        public  CashTimeEnergyButtonUI ()
        {
            return;
        }//end  

         protected String  getPropertyPrefix ()
        {
            return "CashTimeEnergyButton.";
        }//end  

        private void  __stateListener (AWEvent event )
        {
            button.repaint();
            return;
        }//end  

        private void  __onKeyDown (FocusKeyEvent event )
        {
            if (!(button.isShowing() && button.isEnabled()))
            {
                return;
            }
            _loc_2 = button.getModel();
            if (event.keyCode == Keyboard.SPACE && !(_loc_2.isRollOver() && _loc_2.isPressed()))
            {
                setTraversingTrue();
                _loc_2.setRollOver(true);
                _loc_2.setArmed(true);
                _loc_2.setPressed(true);
            }
            return;
        }//end  

        private void  __onKeyUp (FocusKeyEvent event )
        {
            ButtonModel _loc_2 =null ;
            if (!(button.isShowing() && button.isEnabled()))
            {
                return;
            }
            if (event.keyCode == Keyboard.SPACE)
            {
                _loc_2 = button.getModel();
                setTraversingTrue();
                _loc_2.setPressed(false);
                _loc_2.setArmed(false);
                _loc_2.setRollOver(false);
            }
            return;
        }//end  

    }



