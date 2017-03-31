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

import Classes.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDTimerComponent extends HUDComponent
    {
        private Sprite m_timerSprite =null ;
        private JWindow m_timerWindow ;
        private Sprite m_buttonSprite ;
        private JWindow m_buttonWindow ;
        private JPanel m_timerPanel ;
        private JTextField m_timerText ;
        private String m_timerIcon ;
        private Function m_clickCallback ;
        private boolean m_created =false ;
        private int m_timeLeft =0;
        private TimelineLite m_pulse ;
        private static  double SCALE_OFFSET =10;
        private static  double PULSE_SCALE =1.2;
        private static  double PULSE_DURATION =0.2;
        private static  Point SIZE =new Point(66,76);
        private static  Point ICON_POS =new Point(10,9);
        private static  Point ICON_SIZE =new Point(45,45);

        public  HUDTimerComponent (String param1 )
        {
            this.visible = true;
            this.reset();
            this.m_timerIcon = param1;
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
                this.createTimerWindow();
                this.m_created = true;
            }
            return;
        }//end

        private void  createTimerWindow ()
        {
            this.m_timerSprite = new Sprite();
            this.m_timerWindow = new JWindow(this.m_timerSprite);
            this.m_timerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.defaultFontNameBold ,18,16777215,true );
            _loc_1.align = TextFormatAlign.RIGHT;
            this.m_timerText = ASwingHelper.makeTextField(CardUtil.formatTime(0), EmbeddedArt.defaultFontNameBold, 16, 16777215, 0, _loc_1);
            LoadingManager.load(Global.getAssetURL("assets/hud/Mini_Game/slices_bg.png"), this.onIconLoaded);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(ASwingHelper.verticalStrut(50));
            _loc_3.append(this.m_timerText, BorderLayout.CENTER);
            _loc_2.append(_loc_3);
            this.m_timerPanel.append(_loc_2);
            this.m_timerWindow.setContentPane(this.m_timerPanel);
            ASwingHelper.prepare(this.m_timerWindow);
            this.m_timerWindow.cacheAsBitmap = true;
            this.m_timerWindow.show();
            this.alpha = 0;
            this.m_pulse = new TimelineLite();
            this.addChild(this.m_timerSprite);
            return;
        }//end

        private void  onIconLoaded2 (Event event )
        {
            Bitmap _loc_3 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                _loc_3.x = ICON_POS.x;
                _loc_3.y = ICON_POS.y;
                _loc_3.width = ICON_SIZE.x;
                _loc_3.height = ICON_SIZE.y;
                this.m_timerSprite.addChildAt(_loc_3, 1);
            }
            return;
        }//end

        private void  onIconLoaded (Event event )
        {
            Bitmap _loc_3 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                _loc_3.x = 0;
                _loc_3.y = 0;
                this.m_timerSprite.addChildAt(_loc_3, 0);
            }
            LoadingManager.load(Global.getAssetURL(this.m_timerIcon), this.onIconLoaded2);
            return;
        }//end

        private void  onMouseClick (Event event )
        {
            return;
        }//end

        private void  addTooltip ()
        {
            return;
        }//end

        private void  addCounter ()
        {
            return;
        }//end

         public void  updateCounter (...args )
        {
            int argsvalue =Math.floor(args.get(0) );
            if (argsvalue != this.m_timeLeft)
            {
                this.m_timeLeft = argsvalue;
                if (argsvalue <= 15)
                {
                    if (!this.m_pulse.active)
                    {
                        this.m_pulse = new TimelineLite();
                        this.m_pulse.insert(TweenLite.to(this, PULSE_DURATION, {scaleX:PULSE_SCALE, scaleY:PULSE_SCALE, x:this.x - SCALE_OFFSET, y:this.y - SCALE_OFFSET}), 0);
                        this.m_pulse.insert(TweenLite.to(this, PULSE_DURATION, {scaleX:1, scaleY:1, x:this.x, y:this.y}), PULSE_DURATION);
                        this.m_pulse.insert(TweenLite.to(this.m_timerText.getTextField(), PULSE_DURATION, {textColor:16711680}), 0);
                        this.m_pulse.insert(TweenLite.to(this.m_timerText.getTextField(), PULSE_DURATION, {textColor:this.m_timerText.getTextField().textColor}), PULSE_DURATION);
                        this.m_pulse.play();
                    }
                }
                if (this.m_timerText)
                {
                    this.m_timerText.setText(CardUtil.formatTime(argsvalue) + " ");
                }
            }
            return;
        }//end

    }

