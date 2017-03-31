package Display.NeighborUI;

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
import Classes.sim.*;
import Display.*;
import Display.PopulationUI.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class CityNamePanel extends JPanel implements IPopulationStateObserver
    {
        protected JTextField m_nameText ;
        private TextFormat m_bigForm ;
        private Dictionary m_popLabels ;
        private boolean m_bMade =false ;
        private Timer popTimer ;

        public  CityNamePanel (LayoutManager param1)
        {
            this.m_popLabels = new Dictionary();
            this.popTimer = new Timer(1000);
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            Global.world.citySim.addObserver(this);
            this.init();
            this.addEventListener(MouseEvent.ROLL_OVER, this.onMouseOver, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onMouseOut, false, 0, true);
            return;
        }//end

        protected void  init ()
        {
            Object _loc_9 =null ;
            String _loc_10 =null ;
            String _loc_11 =null ;
            int _loc_12 =0;
            int _loc_13 =0;
            String _loc_14 =null ;
            String _loc_15 =null ;
            JPanel _loc_16 =null ;
            this.mouseChildren = false;
            _loc_1 = HUDThemeManager.getAsset(HUDThemeManager.CITY_NAME_BG);
            this.setBackgroundDecorator(new MarginBackground(_loc_1, new Insets(0, 20)));
            _loc_2 = ASwingHelper.makeFlowJPanel ();
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.align = TextFormatAlign.CENTER;
            this.m_bigForm = new TextFormat();
            this.m_bigForm.size = 20;
            this.m_bigForm.align = TextFormatAlign.CENTER;
            _loc_4 =Global.player.cityName ;
            if (TextFieldUtil.isLowerCase(_loc_4))
            {
                _loc_4 = _loc_4.toUpperCase();
            }
            _loc_5 = HUDThemeManager.getColor(HUDThemeManager.CITY_NAME_BG,"nameColor");
            this.m_nameText = ASwingHelper.makeTextField(_loc_4, EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(13, 13, .get({locale:"ja", size:14})), isNaN(_loc_5) ? (EmbeddedArt.titleColor) : (_loc_5), 0, _loc_3);
            _loc_5 = HUDThemeManager.getColor(HUDThemeManager.CITY_NAME_BG, "nameGlowColor");
            this.m_nameText.filters = isNaN(_loc_5) ? (EmbeddedArt.titleFilters) : (.get(new GlowFilter(_loc_5, 1, 4, 4, 10, BitmapFilterQuality.LOW)));
            TextFieldUtil.formatSmallCaps(this.m_nameText.getTextField(), this.m_bigForm);
            _loc_2.appendAll(ASwingHelper.horizontalStrut(40), this.m_nameText, ASwingHelper.horizontalStrut(15));
            ASwingHelper.prepare(_loc_2);
            this.append(ASwingHelper.verticalStrut(-3));
            this.append(_loc_2);
            double _loc_6 =-9;
            if (Global.localizer.langCode == "ja")
            {
                _loc_6 = -5;
            }
            _loc_7 =Global.gameSettings().getPopulationsForDisplay ();
            String _loc_8 ="Population";
            if (_loc_7.length > 1)
            {
                _loc_8 = "PopulationType";
            }
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            	_loc_9 = _loc_7.get(i0);

                _loc_10 = _loc_9.get("id");
                _loc_11 = PopulationHelper.getPopulationZlocType(_loc_10);
                _loc_12 = Global.world.citySim.getScaledPopulation(_loc_10);
                _loc_13 = Global.world.citySim.getScaledPopulationCap(_loc_10);
                _loc_14 = (ZLoc.t("Main", _loc_8, {popType:_loc_11}) + "  ").toLocaleUpperCase();
                _loc_15 = Utilities.formatNumber(_loc_12) + "/" + Utilities.formatNumber(_loc_13);
                _loc_16 = this.makePopulationEntry(_loc_10, _loc_14, _loc_15);
                this.append(ASwingHelper.verticalStrut(_loc_6));
                this.append(_loc_16);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makePopulationEntry (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = ASwingHelper.makeFlowJPanel ();
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_6 = HUDThemeManager.getColor(HUDThemeManager.CITY_NAME_BG,"popColor");
            _loc_7 = ASwingHelper.makeLabel(param2 ,EmbeddedArt.titleFont ,9,isNaN(_loc_6 )? (EmbeddedArt.blueTextColor) : (_loc_6));
            double _loc_8 =3;
            if (Global.localizer.langCode == "ja")
            {
                _loc_8 = 2;
            }
            _loc_5.appendAll(ASwingHelper.verticalStrut(_loc_8), _loc_7);
            _loc_9 = ASwingHelper.makeLabel(param3 ,EmbeddedArt.TITLE_FONT ,13,isNaN(_loc_6 )? (EmbeddedArt.blueTextColor) : (_loc_6));
            _loc_4.appendAll(ASwingHelper.horizontalStrut(42), _loc_5, _loc_9, ASwingHelper.horizontalStrut(10));
            ASwingHelper.prepare(_loc_4);
            this.m_popLabels.put(param1,  _loc_9);
            return _loc_4;
        }//end

        public void  cityName (String param1 )
        {
            _loc_2 = param1;
            if (TextFieldUtil.isLowerCase(_loc_2))
            {
                _loc_2 = _loc_2.toUpperCase();
            }
            this.m_nameText.setText(_loc_2);
            TextFieldUtil.formatSmallCaps(this.m_nameText.getTextField(), this.m_bigForm);
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            if (!Global.ui.bNeighborActionsMenuOn)
            {
                PopulationPopup.getInstance().show();
            }
            event.stopPropagation();
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            PopulationPopup.getInstance().hide();
            event.stopPropagation();
            return;
        }//end

        protected void  onMouseUp (MouseEvent event )
        {
            PopulationDetailDialog _loc_2 =null ;
            if (!Global.isVisiting())
            {
                _loc_2 = new PopulationDetailDialog();
                UI.displayPopup(_loc_2);
                event.stopPropagation();
            }
            return;
        }//end

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            Object _loc_6 =null ;
            String _loc_7 =null ;
            _loc_5 =Global.gameSettings().getPopulationsForDisplay ();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                _loc_7 = _loc_6.get("id");
                this.updatePopulationLabel(_loc_7);
            }
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 =Global.gameSettings().getPopulationsForDisplay ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.get("id");
                this.updatePopulationLabel(_loc_4);
            }
            return;
        }//end

        private void  updatePopulationLabel (String param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            String _loc_8 =null ;
            _loc_2 = this.m_popLabels.get(param1) ;
            if (_loc_2)
            {
                _loc_3 = "Population";
                if (Global.gameSettings().hasMultiplePopulations())
                {
                    _loc_3 = "PopulationType";
                }
                _loc_4 = PopulationHelper.getPopulationZlocType(param1);
                _loc_5 = (ZLoc.t("Main", _loc_3, {popType:_loc_4}) + "  ").toLocaleUpperCase();
                _loc_6 = Global.world.citySim.getScaledPopulation(param1);
                _loc_7 = Global.world.citySim.getScaledPopulationCap(param1);
                _loc_8 = Utilities.formatNumber(_loc_6) + "/" + Utilities.formatNumber(_loc_7);
                _loc_2.setText(_loc_8);
                ASwingHelper.prepare(this);
                ASwingHelper.prepare(this.parent);
            }
            return;
        }//end

    }



