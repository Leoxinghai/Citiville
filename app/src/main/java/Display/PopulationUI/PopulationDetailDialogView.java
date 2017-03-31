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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class PopulationDetailDialogView extends GenericDialogView
    {
        protected JTextField m_popNumber ;
        protected JTextField m_popCapNumber ;
        protected JLabel m_happinessText ;
        protected GridList m_gridList ;
        protected GridListCellFactory m_cellFactory ;
        protected VectorListModel m_model ;
        public static  int HR_OFFSET =11;
        public static  int TIP_MARGIN =10;

        public  PopulationDetailDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

         protected void  init ()
        {
            Array _loc_1 =.get(m_assetDict.get( "population_bg_happy") ,m_assetDict.get( "population_bg_neutral") ,m_assetDict.get( "population_bg_sad")) ;
            _loc_2 =Global.world.citySim.getHappinessState ();
            m_bgAsset =(DisplayObject) new _loc_1.get(_loc_2);
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset);
                this.setBackgroundDecorator(_loc_1);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setBackgroundDecorator(new AssetBackground(m_bgAsset));
            this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.appendAll(this.makeTopPanel(), this.makeBottomPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_1.appendAll(ASwingHelper.horizontalStrut(110), this.makeInfoAndClosePanel());
            _loc_1.setPreferredSize(new IntDimension(440, 188));
            _loc_1.setMaximumSize(new IntDimension(440, 188));
            _loc_1.setMinimumSize(new IntDimension(440, 188));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeInfoAndClosePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.appendAll(this.makePopulationAndClosePanel(), this.makeInfoPanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makePopulationAndClosePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.appendAll(this.makePopulationPanel(), this.makeCloseButtonPanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeCloseButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 2, 4, 2, 2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makePopulationPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.setPreferredSize(new IntDimension(295, 65));
            _loc_1.setMaximumSize(new IntDimension(295, 65));
            _loc_1.setMinimumSize(new IntDimension(295, 65));
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_4 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical ();
            TextFormat _loc_7 =new TextFormat ();
            TextFormat _loc_8 =new TextFormat ();
            _loc_7.align = "left";
            _loc_8.align = "right";
            _loc_9 = ASwingHelper.makeTextField(ZLoc.t("Main","Population")+" ",EmbeddedArt.defaultFontNameBold ,12,16759693,0,_loc_8 );
            _loc_10 = ASwingHelper.makeTextField(ZLoc.t("Main","PopulationCap")+" ",EmbeddedArt.defaultFontNameBold ,12,16759693,0,_loc_8 );
            _loc_5.appendAll(ASwingHelper.verticalStrut(14), _loc_9, ASwingHelper.verticalStrut(7), _loc_10);
            ASwingHelper.prepare(_loc_5);
            _loc_3.append(_loc_5);
            ASwingHelper.prepare(_loc_3);
            this.m_popNumber = ASwingHelper.makeTextField("" + Global.world.citySim.getScaledPopulation(), EmbeddedArt.defaultFontNameBold, 18, 16759693, 0, _loc_7);
            this.m_popCapNumber = ASwingHelper.makeTextField("" + Global.world.citySim.getScaledPopulationCap(), EmbeddedArt.defaultFontNameBold, 18, 16759693, 0, _loc_7);
            _loc_6.appendAll(ASwingHelper.verticalStrut(10), this.m_popNumber, this.m_popCapNumber);
            ASwingHelper.prepare(_loc_6);
            _loc_4.append(_loc_6);
            ASwingHelper.prepare(_loc_4);
            _loc_2.appendAll(_loc_3, _loc_4);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.setPreferredSize(new IntDimension(330, 125));
            _loc_1.setMaximumSize(new IntDimension(330, 125));
            _loc_1.setMinimumSize(new IntDimension(330, 125));
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3 =Global.world.citySim.getHappinessState ();
            this.m_happinessText = ASwingHelper.makeLabel(ZLoc.t("Main", "HappinessState_" + _loc_3), EmbeddedArt.defaultFontNameBold, 18, 5296949);
            _loc_2.append(this.m_happinessText);
            ASwingHelper.prepare(_loc_2);
            _loc_4 = ASwingHelper.makeMultilineText(ZLoc.t("Main","PopulationPopupTipDetailed_"+_loc_3 ),330-2*TIP_MARGIN ,EmbeddedArt.defaultFontNameBold ,"left",14,13404265);
            ASwingHelper.setEasyBorder(_loc_4, 0, TIP_MARGIN, 0, TIP_MARGIN);
            _loc_1.appendAll(_loc_2, ASwingHelper.verticalStrut(10), _loc_4);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeBottomPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.setPreferredSize(new IntDimension(440, 135));
            _loc_1.setMaximumSize(new IntDimension(440, 135));
            _loc_1.setMinimumSize(new IntDimension(440, 135));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_2.appendAll(this.makeGridTitlePanel(), this.makeGridListPanel());
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeGridTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            AssetPane _loc_3 =new AssetPane(new m_assetDict.get( "population_horizontalRule") );
            AssetPane _loc_4 =new AssetPane(new m_assetDict.get( "population_horizontalRule") );
            _loc_5 = ASwingHelper.makeTextField(ZLoc.t("Dialogs","municipal_menu"),EmbeddedArt.titleFont ,14,3382725);
            _loc_2.append(_loc_5);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.setEasyBorder(_loc_3, HR_OFFSET);
            ASwingHelper.setEasyBorder(_loc_4, HR_OFFSET);
            _loc_1.appendAll(_loc_3, _loc_2, _loc_4);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeGridListPanel ()
        {
            int _loc_4 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 =Global.gameSettings().getCurrentBuyableItems("municipal");
            this.m_model = new VectorListModel();
            int _loc_3 =0;
            while (_loc_3 < 4)
            {

                _loc_4 = MathUtil.randomIndex(_loc_2);
                this.m_model.append(_loc_2.get(_loc_4));
                _loc_2.splice(_loc_4, 1);
                _loc_3++;
            }
            this.m_cellFactory = new PopulationMunicipalCellFactory();
            this.m_gridList = new GridList(this.m_model, this.m_cellFactory, 4, 1);
            this.m_gridList.setPreferredSize(new IntDimension(440, 135));
            this.m_gridList.setMaximumSize(new IntDimension(440, 135));
            this.m_gridList.setMinimumSize(new IntDimension(440, 135));
            ASwingHelper.prepare(this.m_gridList);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(6), this.m_gridList);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

    }



