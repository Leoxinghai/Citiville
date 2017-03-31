package Modules.flashsale.ui;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Display.aswingui.gridlistui.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class EoQFlashSaleDialogView extends GenericDialogView
    {
        protected GridList m_packageShelf ;
        protected double m_endDate ;

        public  EoQFlashSaleDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,Object param8 =null )
        {
            if (param8.hasOwnProperty("listdata") && !this.m_packageShelf)
            {
                this.m_packageShelf = this.createPackageGridList(param8.listdata);
            }
            this.m_endDate = param8.endDate;
            super(param1, param2, param3, param4, param5, param6, param7, "", param8.skipCallback, "");
            return;
        }//end

        protected int  HEADING_TOP_PAD ()
        {
            return 5;
        }//end

        protected int  HEADING_LEFT_PAD ()
        {
            return 190;
        }//end

        protected int  HEADING_WIDTH ()
        {
            return 500;
        }//end

        protected int  HEADINGA_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(26, 1, [{locale:"ja", ratio:0.9}]);
        }//end

        protected int  HEADINGB_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(26, 1, [{locale:"ja", ratio:0.9}]);
        }//end

        protected int  BODYB_TOP_PAD ()
        {
            return 0;
        }//end

        protected int  BODYB_BOTTOM_PAD ()
        {
            return 5;
        }//end

        protected int  BODYA_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(36, 1, [{locale:"ja", ratio:0.8}]);
        }//end

        protected int  BODYB_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(18, 1, [{locale:"ja", ratio:0.8}]);
        }//end

        protected Class  CELL_CLASS ()
        {
            return EoQPackageSaleCell;
        }//end

        private int  CELL_WIDTH ()
        {
            return this.CELL_CLASS.get("CELL_WIDTH");
        }//end

        private int  CELL_HEIGHT ()
        {
            return this.CELL_CLASS.get("CELL_HEIGHT");
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 10, 0));
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
                this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
                this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-10);
            _loc_1.setPreferredSize(new IntDimension(this.width, this.height));
            _loc_1.appendAll(createHeaderPanel(), this.createTextArea());
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_1.setPreferredSize(new IntDimension(this.width, this.height - 50));
            _loc_1.append(this.createTopPanel(), BorderLayout.NORTH);
            JPanel _loc_2 =new JPanel(new SoftBoxLayout(AsWingConstants.HORIZONTAL ));
            JPanel _loc_3 =new JPanel(new SoftBoxLayout(AsWingConstants.VERTICAL ));
            Date _loc_4 =new Date(this.m_endDate *1000);
            EoQTimerPanel _loc_5 =new EoQTimerPanel(_loc_4 ,assetDict.get( "EoQSale_timer_large") );
            ASwingHelper.setEasyBorder(_loc_5, 0, 0, 12, 0);
            _loc_3.append(_loc_5);
            _loc_2.appendAll(ASwingHelper.horizontalStrut(240), _loc_3);
            _loc_1.append(_loc_2, BorderLayout.CENTER);
            _loc_1.append(this.createBottomPanel(), BorderLayout.SOUTH);
            return _loc_1;
        }//end

        protected JPanel  createTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.setPreferredWidth(this.HEADING_WIDTH);
            _loc_2.setMaximumWidth(this.HEADING_WIDTH);
            ASwingHelper.setEasyBorder(_loc_2, this.HEADING_TOP_PAD, this.HEADING_LEFT_PAD, 0, 0);
            _loc_2.appendAll(this.createTopPanelHeader(), ASwingHelper.verticalStrut(5), this.createTopPanelBody());
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  createTopPanelHeader ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_congrats") );
            _loc_3 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_playerName") );
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.align = TextFormatAlign.CENTER;
            _loc_5 = ASwingHelper.makeTextField(_loc_2 ,EmbeddedArt.titleFont ,this.HEADINGA_FONTSIZE ,EmbeddedArt.yellowTextColor ,3,_loc_4 );
            _loc_6 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.titleFont ,this.HEADINGB_FONTSIZE ,EmbeddedArt.salePopupBlue ,JLabel.CENTER );
            _loc_5.setPreferredWidth(this.HEADING_WIDTH);
            _loc_5.filters = EmbeddedArt.newtitleFilters;
            _loc_1.appendAll(_loc_5, ASwingHelper.verticalStrut(-10), _loc_6);
            return _loc_1;
        }//end

        protected JPanel  createTopPanelBody ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = assetDict.get("loc_specialOffer") ;
            _loc_3 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_discountAmount") );
            _loc_4 = assetDict.get("loc_subText") ;
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_6 = ASwingHelper.makeLabel(_loc_2 ,EmbeddedArt.defaultFontNameBold ,this.BODYB_FONTSIZE ,EmbeddedArt.salePopupDarkBlue ,JLabel.CENTER );
            _loc_7 = ASwingHelper.makeTextField(_loc_3 ,EmbeddedArt.titleFont ,this.BODYA_FONTSIZE ,EmbeddedArt.yellowTextColor ,3,_loc_5 );
            _loc_8 = ASwingHelper.makeLabel(_loc_4 ,EmbeddedArt.defaultFontNameBold ,this.BODYB_FONTSIZE ,EmbeddedArt.salePopupDarkBlue ,JLabel.CENTER );
            _loc_7.setPreferredWidth(this.HEADING_WIDTH);
            _loc_7.filters = EmbeddedArt.newtitleFilters;
            ASwingHelper.setEasyBorder(_loc_7, this.BODYB_TOP_PAD, 0, this.BODYB_BOTTOM_PAD, 0);
            _loc_1.appendAll(_loc_6, _loc_7, _loc_8);
            return _loc_1;
        }//end

        protected JPanel  createBottomPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (this.m_packageShelf)
            {
                _loc_1.append(this.m_packageShelf);
                this.m_packageShelf.setPreferredHeight(this.CELL_HEIGHT);
                this.m_packageShelf.setTileWidth(this.CELL_WIDTH);
                this.m_packageShelf.setTileHeight(this.CELL_HEIGHT);
                this.m_packageShelf.setHGap(5);
            }
            return _loc_1;
        }//end

        protected GridList  createPackageGridList (Array param1 )
        {
            GridList _loc_2 =null ;
            VectorListModel _loc_3 =new VectorListModel(param1 );
            _loc_2 = new GridList(_loc_3, new GenericGridCellFactory(this.CELL_CLASS), 3, 0);
            return _loc_2;
        }//end

         protected JPanel  createTitlePanel ()
        {
            return ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
        }//end

         protected boolean  trackingIsSampled ()
        {
            return false;
        }//end

    }



