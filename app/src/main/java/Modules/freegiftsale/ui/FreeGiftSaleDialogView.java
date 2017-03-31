package Modules.freegiftsale.ui;

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
import Display.aswingui.gridlistui.*;
import Modules.flashsale.ui.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class FreeGiftSaleDialogView extends FlashSaleDialogView
    {

        public  FreeGiftSaleDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,Object param8 =null )
        {
            if (param8.hasOwnProperty("listdata") && param8.listdata.length > 0)
            {
                m_packageShelf = this.createPackageGridList(param8.listdata);
            }
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

         protected Class  CELL_CLASS ()
        {
            return FreeGiftSaleCell;
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
            _loc_1.setPreferredSize(new IntDimension(this.width - 50, this.height - 50));
            _loc_1.append(this.createTopPanel(), BorderLayout.NORTH);
            JPanel _loc_2 =new JPanel(new SoftBoxLayout(AsWingConstants.VERTICAL ));
            Date _loc_3 =new Date(m_endDate *1000);
            double _loc_4 =33;
            if (Global.localizer.langCode == "ja")
            {
                _loc_4 = int(_loc_4 * 0.7);
            }
            _loc_2.append(ASwingHelper.verticalStrut(_loc_4));
            _loc_2.append(new TimerPanel(_loc_3));
            _loc_1.append(_loc_2, BorderLayout.CENTER);
            _loc_1.append(this.createBottomPanel(), BorderLayout.SOUTH);
            return _loc_1;
        }//end

         protected JPanel  createTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.setPreferredWidth(HEADING_WIDTH);
            _loc_2.setMaximumWidth(HEADING_WIDTH);
            ASwingHelper.setEasyBorder(_loc_2, HEADING_TOP_PAD, HEADING_LEFT_PAD, 0, 0);
            _loc_2.appendAll(this.createTopPanelHeader(), ASwingHelper.verticalStrut(5), this.createTopPanelBody());
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected JPanel  createTopPanelHeader ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_heading1") );
            _loc_3 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_heading2") );
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.align = TextFormatAlign.CENTER;
            _loc_5 = ASwingHelper.makeTextField(_loc_2 ,EmbeddedArt.titleFont ,HEADINGA_FONTSIZE ,EmbeddedArt.yellowTextColor ,3,_loc_4 );
            _loc_6 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.titleFont ,HEADINGB_FONTSIZE ,EmbeddedArt.darkBlueTextColor ,JLabel.CENTER );
            _loc_5.setPreferredWidth(HEADING_WIDTH);
            _loc_5.filters = EmbeddedArt.newtitleFilters;
            _loc_1.appendAll(_loc_5, ASwingHelper.verticalStrut(-10), _loc_6);
            return _loc_1;
        }//end

         protected JPanel  createTopPanelBody ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = assetDict.get("loc_body1") ;
            _loc_3 = TextFieldUtil.formatSmallCapsString(assetDict.get( "loc_body2") );
            _loc_4 = assetDict.get("loc_body3") ;
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_6 = ASwingHelper.makeLabel(_loc_2 ,EmbeddedArt.defaultFontNameBold ,BODYB_FONTSIZE ,EmbeddedArt.darkBrownTextColor ,JLabel.CENTER );
            _loc_7 = ASwingHelper.makeTextField(_loc_3 ,EmbeddedArt.titleFont ,BODYA_FONTSIZE ,EmbeddedArt.yellowTextColor ,3,_loc_5 );
            _loc_8 = ASwingHelper.makeLabel(_loc_4 ,EmbeddedArt.defaultFontNameBold ,BODYB_FONTSIZE ,EmbeddedArt.darkBrownTextColor ,JLabel.CENTER );
            _loc_7.setPreferredWidth(HEADING_WIDTH);
            _loc_7.filters = EmbeddedArt.newtitleFilters;
            ASwingHelper.setEasyBorder(_loc_7, BODYB_TOP_PAD, 0, BODYB_BOTTOM_PAD, 0);
            _loc_1.appendAll(_loc_6, _loc_7, _loc_8);
            return _loc_1;
        }//end

         protected JPanel  createBottomPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_packageShelf)
            {
                m_packageShelf.setPreferredHeight(this.CELL_HEIGHT);
                _loc_1.append(m_packageShelf);
                m_packageShelf.setTileWidth(this.CELL_WIDTH);
                m_packageShelf.setTileHeight(this.CELL_HEIGHT);
                m_packageShelf.setHGap(5);
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

    }



