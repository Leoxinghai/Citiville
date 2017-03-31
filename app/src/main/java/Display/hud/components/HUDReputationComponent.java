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
import Display.*;
import Display.aswingui.*;
import Display.hud.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import Classes.sim.*;

    public class HUDReputationComponent extends HUDComponent
    {
        public DisplayObject m_repSlider =HUDThemeManager.getAsset(HUDThemeManager.REPUTATION_BAR );
        protected JPanel m_repPanel ;
        private JPanel m_repHolder ;
        public JTextField m_repText ;
        public JTextField m_levelText ;
        private int m_socialLevel =1;

        public  HUDReputationComponent ()
        {
            //this.m_repSlider = HUDThemeManager.getAsset(HUDThemeManager.REPUTATION_BAR);
            this.m_socialLevel = 1;
            _loc_1 = this.m_socialLevel <10? ("0" + this.m_socialLevel.toString()) : (this.m_socialLevel.toString());
            this.m_levelText.setText(_loc_1);
            return;
        }//end

         protected void  buildComponent ()
        {
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.defaultFontNameBold ,18,16777215,true ,false ,false ,null ,null ,TextFormatAlign.LEFT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_repPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_3.setBorder(new EmptyBorder(null, new Insets(11, 10, 0, 0)));
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,0);
            _loc_4.setPreferredWidth(30);
            this.m_levelText = ASwingHelper.makeTextField("0", EmbeddedArt.defaultFontNameBold, 18, 16777215, 0, _loc_1);
            this.m_levelText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            _loc_4.append(this.m_levelText);
            _loc_3.append(_loc_4);
            _loc_3.append(ASwingHelper.horizontalStrut(53));
            this.m_repText = ASwingHelper.makeTextField("0", EmbeddedArt.defaultFontNameBold, 18, 16777215, 0, _loc_1);
            this.m_repText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            ASwingHelper.setSizedBackground(this.m_repPanel, this.m_repSlider);
            _loc_3.appendAll(this.m_repText);
            this.m_repPanel.append(_loc_3);
            _loc_2.append(this.m_repPanel);
            _loc_2.setBorder(new EmptyBorder(null, new Insets(3)));
            m_jPanel.append(_loc_2);
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip = new ToolTip();
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            super.refresh(param1);
            this.m_repSlider.scaleX = 1;
            return;
        }//end

         protected void  setCounter ()
        {
            m_counter =new HUDCounter (void  (double param1 )
            {
                m_repText.setText(String(Global.player.socialXp));
                updateCounter(Global.player.socialXp);
                return;
            }//end
            );
            m_counter.value = Global.player.socialXp;
            this.refresh(false);
            return;
        }//end

         public void  updateCounter (...args )
        {
            boolean _loc_10 =false ;
            int _loc_11 =0;
            XML _loc_12 =null ;
            double _loc_13 =0;
            double _loc_14 =0;
            int argsvalue =args.get(0) ;
            _loc_3 =Global.gameSettings().getXML ();
            _loc_4 = _loc_3.reputation.level ;
            int _loc_5 =this.m_socialLevel ;
            double _loc_6 =0;
            double _loc_7 =0;
            if (argsvalue > 0)
            {
                _loc_10 = false;
                _loc_11 = _loc_5;
                while (_loc_11 < _loc_4.length())
                {

                    _loc_12 = _loc_4.get(_loc_11);
                    _loc_13 = Number(_loc_4.get((_loc_11 - 1)).@requiredXP);
                    _loc_14 = Number(_loc_4.get(_loc_11).@requiredXP);
                    _loc_7 = _loc_14 - argsvalue;
                    _loc_6 = _loc_7 / (_loc_14 - _loc_13);
                    if (_loc_12.@requiredXP <= argsvalue)
                    {
                        _loc_5 = _loc_11 + 1;
                        _loc_10 = true;
                    }
                    else
                    {
                        break;
                    }
                    _loc_11++;
                }
            }
            else
            {
                _loc_5 = 1;
                _loc_6 = 1;
            }
            if (_loc_5 != this.m_socialLevel)
            {
                this.m_socialLevel = _loc_5;
            }
            MovieClip _loc_8 =(MovieClip)this.m_repSlider.get( "sliderMask");
            (this.m_repSlider.get("sliderMask") as MovieClip).x = (-_loc_6) * _loc_8.width + 45;
            m_toolTip.toolTip = ZLoc.t("Main", "XP_ToolTip", {experience:_loc_7, level:(_loc_5 + 1)});
            String _loc_9 =this.m_socialLevel <10? ("0" + this.m_socialLevel.toString()) : (this.m_socialLevel.toString());
            this.m_levelText.setText(_loc_9);
            this.m_repText.setText(String(argsvalue));
            m_counter.value = argsvalue;
            return;
        }//end

    }


