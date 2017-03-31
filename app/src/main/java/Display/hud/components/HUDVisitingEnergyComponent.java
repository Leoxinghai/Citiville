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
import Display.aswingui.*;
import com.greensock.*;
//import flash.display.*;
//import flash.filters.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDVisitingEnergyComponent extends HUDComponent
    {
        private int m_visitEnergy ;
        private Component m_icon ;
        private JTextField m_visitEnergyCounter ;

        public  HUDVisitingEnergyComponent ()
        {
            this.m_visitEnergy = Global.gameSettings().getInt("maxVisitingEnergy", 5);
            this.m_icon = null;
            this.m_visitEnergyCounter = null;
            return;
        }//end

         public boolean  isVisible ()
        {
            return this.m_visitEnergy > 0;
        }//end

         protected void  buildComponent ()
        {
            Container _loc_3 =null ;
            AssetPane _loc_4 =null ;
            Container _loc_5 =null ;
            m_jPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 0);
            _loc_1 =Global.gameSettings().getInt("visitingEnergyIconAppearDelay",200);
            _loc_2 =Global.gameSettings().getInt("visitingEnergyIconAppearTime",1000)/1000;
            if (this.m_visitEnergy > 0)
            {
                _loc_3 = ASwingHelper.makeSoftBoxJPanel(1, -20);
                _loc_4 = (DisplayObject)new AssetPane(new EmbeddedArt.visitEnergyIcon());
                _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
                this.m_visitEnergyCounter = ASwingHelper.makeTextField(this.getCounterText(this.m_visitEnergy), EmbeddedArt.titleFont, 24, EmbeddedArt.yellowTextColor);
                this.m_visitEnergyCounter.filters = .get(new GlowFilter(EmbeddedArt.orangeTextColor, 1, 3, 3, 5, BitmapFilterQuality.LOW), new DropShadowFilter(1, 45, EmbeddedArt.blackTextColor, 0.6, 3, 3, 6));
                _loc_5.append(new AssetPane(this.m_visitEnergyCounter));
                _loc_3.append(_loc_4);
                _loc_3.append(_loc_5);
                m_jPanel.append(_loc_3);
                this.m_icon = _loc_3;
                this.m_icon.alpha = 0;
            }
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            super.refresh(param1);
            this.updateCounter(this.m_visitEnergy);
            return;
        }//end

         public void  updateCounter (...args )
        {
            int argsvalue =args.get(0) ;
            _loc_3 = Math.min(Math.max(argsvalue ,0),Global.gameSettings().getInt("maxVisitingEnergy",5));
            this.m_visitEnergy = _loc_3;
            _loc_4 =Global.gameSettings().getInt("visitingEnergyIconAppearTime",1000)/1000;
            this.m_visitEnergyCounter.setText(this.getCounterText(_loc_3));
            if (_loc_3 <= 0)
            {
                TweenLite.to(this.m_icon, _loc_4, {alpha:0});
            }
            else
            {
                TweenLite.to(this.m_icon, _loc_4, {alpha:1});
            }
            return;
        }//end

        private String  getCounterText (int param1 )
        {
            return "x" + param1.toString() + " ";
        }//end

    }


