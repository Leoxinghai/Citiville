package Display.hud.components;

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

import Display.aswingui.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDTimerExtensionComponent extends HUDComponent
    {
        private int m_timeLeft =0;
        private TimelineLite m_pulse ;
        private Sprite m_buttonSprite ;
        private JWindow m_buttonWindow ;
        private Function m_clickCallback ;
        private boolean m_created =false ;
        private static  int BUTTON_HEIGHT =43;
        private static  double SCALE_OFFSET_X =10;
        private static  double SCALE_OFFSET_Y =0;
        private static  double PULSE_SCALE =1.2;
        private static  double PULSE_DURATION =0.2;
        private static  Point SIZE =new Point(68,28);
        private static  Point ORIGIN_OFFSET =new Point (-47,-15);

        public  HUDTimerExtensionComponent ()
        {
            this.visible = true;
            this.reset();
            this.m_pulse = new TimelineLite();
            return;
        }//end

         public double  width ()
        {
            return SIZE.x * scaleX;
        }//end

         public void  width (double param1 )
        {
            scaleX = param1 / SIZE.x;
            return;
        }//end

         public double  height ()
        {
            return SIZE.y * scaleY;
        }//end

         public void  height (double param1 )
        {
            scaleY = param1 / SIZE.y;
            return;
        }//end

         public void  reset ()
        {
            if (!this.m_created)
            {
                this.createButtonWindow();
                this.m_created = true;
            }
            return;
        }//end

        private void  createButtonWindow ()
        {
            this.m_buttonSprite = new Sprite();
            this.m_buttonSprite.x = ORIGIN_OFFSET.x;
            this.m_buttonSprite.y = ORIGIN_OFFSET.y;
            this.m_buttonWindow = new JWindow(this.m_buttonSprite);
            _loc_1 = this.makeButtonPanel ();
            this.m_buttonWindow.setContentPane(_loc_1);
            ASwingHelper.prepare(this.m_buttonWindow);
            this.m_buttonWindow.cacheAsBitmap = true;
            this.m_buttonWindow.show();
            this.addChild(this.m_buttonSprite);
            return;
        }//end

        private JPanel  makeButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Main","AddTimeEnergy"),null ,"CashTimeEnergyButtonUI");
            _loc_2.setPreferredHeight(BUTTON_HEIGHT);
            _loc_2.addActionListener(this.onButtonClick, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        private void  onButtonClick (Event event )
        {
            if (this.m_clickCallback != null)
            {
                this.m_clickCallback();
            }
            return;
        }//end

        public void  setClickCallback (Function param1 )
        {
            this.m_clickCallback = param1;
            return;
        }//end

         public void  updateCounter (...args )
        {
            int argsvalue =Math.ceil(args.get(0) );
            if (argsvalue != this.m_timeLeft)
            {
                this.m_timeLeft = argsvalue;
                if (argsvalue <= 15)
                {
                    if (!this.m_pulse.active)
                    {
                        this.m_pulse = new TimelineLite();
                        this.m_pulse.insert(TweenLite.to(this, PULSE_DURATION, {scaleX:PULSE_SCALE, scaleY:PULSE_SCALE, x:this.x - SCALE_OFFSET_X, y:this.y - SCALE_OFFSET_Y}), 0);
                        this.m_pulse.insert(TweenLite.to(this, PULSE_DURATION, {scaleX:1, scaleY:1, x:this.x, y:this.y}), PULSE_DURATION);
                        this.m_pulse.play();
                    }
                }
            }
            return;
        }//end

    }


