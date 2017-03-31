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
import org.aswing.zynga.*;
import Classes.sim.*;

    public class HUDXPComponent extends HUDComponent
    {
        public DisplayObject m_xpSlider =HUDThemeManager.getAsset(HUDThemeManager.XP_BAR );
        public JTextField m_xpText ;
        private JPanel m_xpHolder ;
        public JTextField m_levelText ;
        private int m_level =1;

        public  HUDXPComponent (Function param1)
        {
            //this.m_xpSlider = HUDThemeManager.getAsset(HUDThemeManager.XP_BAR);
            //this.m_level = 1;
            return;
        }//end

         protected void  buildComponent ()
        {
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,16,16777215,true ,false ,false ,null ,null ,TextFormatAlign.CENTER );
            TextFormat _loc_2 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,15,16777215,true ,false ,false ,null ,null ,TextFormatAlign.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_xpHolder = new JPanel(new LayeredLayout());
            this.m_xpHolder.addChild(this.m_xpSlider);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_4.setBorder(new EmptyBorder(null, new Insets(13, 8, 0, 0)));
            this.m_xpHolder.append(_loc_4);
            this.m_xpText = ASwingHelper.makeTextField("0", EmbeddedArt.DEFAULT_FONT_NAME_BOLD, 18, 16777215, 0, _loc_1);
            this.m_xpText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            this.m_xpText.setBorder(new EmptyBorder(null, new Insets(3)));
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_5.append(this.m_xpText);
            _loc_5.setPreferredWidth(84);

            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,0);
            _loc_6.setPreferredWidth(40);
            //ASwingHelper.setEasyBorder(_loc_6, 0, 0, 12);

            this.m_levelText = ASwingHelper.makeTextField("0", EmbeddedArt.TITLE_FONT, 18, EmbeddedArt.yellowTextColor, 0, _loc_2);
            this.m_levelText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            _loc_6.append(this.m_levelText);
            _loc_4.append(_loc_5);
            _loc_4.append(ASwingHelper.horizontalStrut(2));
            _loc_4.append(_loc_6);
            _loc_4.append(ASwingHelper.horizontalStrut(5));
            _loc_3.append(this.m_xpHolder);
            _loc_3.setBorder(new EmptyBorder(null, new Insets(3)));
            m_jPanel.append(_loc_3);
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip = new ToolTip();
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            return;
        }//end

         protected void  setCounter ()
        {
            m_counter =new HUDCounter (void  (double param1 )
            {
                m_xpText.setText(String(int(param1)));
                updateCounter(Global.player.xp);
                return;
            }//end
            );
            this.m_xpText.setText(String(Global.player.xp));
            refresh(false);
            return;
        }//end

         public void  updateCounter (...args )
        {
            boolean _loc_12 =false ;
            int _loc_13 =0;
            XML _loc_14 =null ;
            double _loc_15 =0;
            int argsvalue =args.get(0) ;
            _loc_3 =Global.gameSettings().getLevelsXML ().level ;
            _loc_4 = this.m_level ;
            double _loc_5 =0;
            double _loc_6 =0;
            double _loc_7 =0;
            if (argsvalue > 0)
            {
                _loc_12 = false;
                _loc_13 = _loc_4;
                while (_loc_13 < _loc_3.length())
                {

                    _loc_14 = _loc_3.get(_loc_13);
                    _loc_15 = Number(_loc_3.get((_loc_13 - 1)).@requiredXP);
                    _loc_6 = Number(_loc_3.get(_loc_13).@requiredXP);
                    _loc_7 = _loc_6 - argsvalue;
                    _loc_5 = (_loc_6 - argsvalue) / (_loc_6 - _loc_15);
                    if (_loc_14.@requiredXP <= argsvalue)
                    {
                        _loc_4 = _loc_13 + 1;
                        _loc_12 = true;
                    }
                    else
                    {
                        break;
                    }
                    _loc_13++;
                }
            }
            else
            {
                _loc_4 = 1;
                _loc_5 = 1;
            }
            if (_loc_4 != this.m_level)
            {
                this.m_level = _loc_4;
            }
            _loc_8 =(MovieClip) this.m_xpSlider.get( "sliderMask");
            _loc_9 = double(_loc_3.get((Global.player.level-1)).@requiredXP);
            _loc_10 = double(_loc_3.get((_loc_4-1)).@requiredXP);
            if (Global.player.level == _loc_3.length())
            {
                _loc_7 = 0;
                _loc_5 = 0;
                m_toolTip.toolTip = ZLoc.t("Main", "XP_ToolTip", {experience:_loc_7, level:Global.player.level});
            }
            else if (_loc_9 > _loc_10)
            {
                _loc_7 = Number(_loc_3.get(Global.player.level).@requiredXP) - argsvalue;
                _loc_5 = _loc_7 / _loc_3.get(Global.player.level).@requiredXP;
                m_toolTip.toolTip = ZLoc.t("Main", "XP_ToolTip", {experience:_loc_7, level:(Global.player.level + 1)});
            }
            else
            {
                m_toolTip.toolTip = ZLoc.t("Main", "XP_ToolTip", {experience:_loc_7, level:(Global.player.level + 1)});
            }
            _loc_8.x = (-_loc_5) * _loc_8.width + 5;
            _loc_11 =Global.player.level <10? ("0" + Global.player.level.toString()) : (Global.player.level.toString());
            this.m_levelText.setText(_loc_11);
            m_counter.value = argsvalue;
            this.m_xpText.setText(String(argsvalue));
            return;
        }//end

    }



