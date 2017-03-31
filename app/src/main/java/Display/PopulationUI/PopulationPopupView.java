package Display.PopulationUI;

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
import Engine.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class PopulationPopupView extends NotifyPopupView
    {
        protected JTextField m_popNumber ;
        protected JTextField m_happiness ;
        protected PopulationPopup m_popup ;

        public  PopulationPopupView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,PopulationPopup param6 =null )
        {
            super(param1, param2, param3, param4, param5);
            this.m_popup = param6;
            m_state = STATE_LONG;
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setBackgroundDecorator(new AssetBackground(m_bgAsset));
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.append(ASwingHelper.verticalStrut(5));
            switch(m_state)
            {
                case STATE_LONG:
                {
                    _loc_1.append(this.makeLongPanel());
                    break;
                }
                case STATE_SHORT:
                {
                    _loc_1.append(this.makeShortPanel());
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_1.append(ASwingHelper.verticalStrut(7));
            ASwingHelper.prepare(_loc_1);
            this.appendAll(ASwingHelper.horizontalStrut(10), _loc_1, ASwingHelper.horizontalStrut(3));
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  makeLongPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-10);
            _loc_1.appendAll(ASwingHelper.verticalStrut(13), this.makeLongPopulationPanel(), ASwingHelper.verticalStrut(15));
            _loc_2 =Global.world.citySim.getHappinessState ();
            String _loc_3 ="HappinessState_";
            if (Global.gameSettings().hasMultiplePopulations())
            {
                _loc_3 = "HappinessStateAll_";
            }
            _loc_3 = _loc_3 + _loc_2;
            this.m_happiness = ASwingHelper.makeTextField(ZLoc.t("Main", _loc_3), EmbeddedArt.defaultFontNameBold, 13, EmbeddedArt.greenTextColor);
            _loc_1.append(this.m_happiness);
            ASwingHelper.prepare(_loc_1);
            if (!Global.isVisiting())
            {
                m_tip = ASwingHelper.makeMultilineText(ZLoc.t("Main", "PopulationPopupTip_" + _loc_2), 180, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.blueTextColor);
                ASwingHelper.setEasyBorder(m_tip, 5, 3);
                _loc_1.append(m_tip);
            }
            _loc_1.append(ASwingHelper.verticalStrut(28));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeLongPopulationPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.setPreferredWidth(180);
            _loc_1.setMinimumWidth(180);
            JPanel _loc_2 =new JPanel(new BorderLayout ());
            _loc_3 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT ,-3);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.RIGHT ,-3);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Main","PopulationPopup")+" ",EmbeddedArt.defaultFontNameBold ,13,EmbeddedArt.orangeTextColor ,JLabel.LEFT );
            _loc_7 = ASwingHelper.makeLabel(ZLoc.t("Main","PopulationCap")+" ",EmbeddedArt.defaultFontNameBold ,13,EmbeddedArt.orangeTextColor ,JLabel.LEFT );
            _loc_4.appendAll(_loc_6, _loc_7);
            _loc_3.appendAll(ASwingHelper.horizontalStrut(3), _loc_4);
            ASwingHelper.prepare(_loc_3);
            _loc_8 = ASwingHelper.makeLabel(Utilities.formatNumber(Global.world.citySim.getScaledPopulation ())+"  ",EmbeddedArt.defaultFontNameBold ,13,EmbeddedArt.orangeTextColor ,JLabel.RIGHT );
            _loc_9 = ASwingHelper.makeLabel(Utilities.formatNumber(Global.world.citySim.getScaledPopulationCap ())+"  ",EmbeddedArt.defaultFontNameBold ,13,EmbeddedArt.orangeTextColor ,JLabel.RIGHT );
            _loc_5.appendAll(_loc_8, _loc_9);
            ASwingHelper.prepare(_loc_5);
            _loc_2.append(_loc_3, BorderLayout.WEST);
            _loc_2.append(_loc_5, BorderLayout.EAST);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  makeShortPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeTextField(ZLoc.t("Main","Population",{amount Utilities.formatNumber(Global.world.citySim.getScaledPopulation ())})+"  ",EmbeddedArt.titleFont ,18,EmbeddedArt.blueTextColor );
            this.m_popNumber = ASwingHelper.makeTextField(String(Global.world.citySim.getScaledPopulation()) + "/" + Utilities.formatNumber(Global.world.citySim.getScaledPopulationCap()), EmbeddedArt.titleFont, 20, EmbeddedArt.darkBlueTextColor);
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 10);
            _loc_1.appendAll(_loc_2, this.m_popNumber);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

    }



