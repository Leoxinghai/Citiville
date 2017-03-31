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
import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class EoQPackageSaleCell extends JPanel implements GridListCell
    {
        protected Dictionary m_assets ;
        protected Object m_cellValue ;
        private static int iCnt =0;
        private static int maxBoxReset =3;
        private static int myMagicalSelectedBox =2;
        private static int saleHighlightVariant =-2;

        public  EoQPackageSaleCell ()
        {
            super(new BorderLayout());
            return;
        }//end

        protected String  CELL_BG_CLASS ()
        {
            if (saleHighlightVariant == -2)
            {
                saleHighlightVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EOQ_SALE_HIGHLIGHT);
            }
            switch(saleHighlightVariant)
            {
                case 3:
                {
                    myMagicalSelectedBox = 2;
                    break;
                }
                case 4:
                {
                    myMagicalSelectedBox = 3;
                    break;
                }
                default:
                {
                    myMagicalSelectedBox = -1;
                    break;
                }
            }
            _loc_2 = iCnt+1;
            iCnt = _loc_2;
            if (iCnt > maxBoxReset)
            {
                iCnt = 1;
            }
            if (iCnt == myMagicalSelectedBox)
            {
                return "EoQSale_middleOption_bg";
            }
            return "EoQSale_sideOption_bg";
        }//end

        protected int  HEADING_FONTSIZE ()
        {
            return 16;
        }//end

        protected int  HEADING_BOTTOM_PAD ()
        {
            return 5;
        }//end

        protected int  BODYA_FONTSIZE ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(40,38,.get( {locale size "fr",20) });
            return _loc_1;
        }//end

        protected int  BODYB_FONTSIZE ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(20,18,.get( {locale size "fr",14) });
            return _loc_1;
        }//end

        protected int  BODYC_FONTSIZE ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(22,20,.get( {locale size "fr",14) });
            return _loc_1;
        }//end

        protected int  BODY_BOTTOM_PAD ()
        {
            return 10;
        }//end

        public int  optionNum ()
        {
            return (this.m_cellValue.index + 1);
        }//end

        public String  type ()
        {
            return this.m_cellValue.type;
        }//end

        public int  amount ()
        {
            return this.m_cellValue.amount;
        }//end

        public int  baseCost ()
        {
            return this.m_cellValue.baseCostUSD;
        }//end

        public int  discount ()
        {
            return this.m_cellValue.discount;
        }//end

        public int  discountCost ()
        {
            return this.m_cellValue.baseCostUSD * (1 - this.m_cellValue.discount / 100);
        }//end

        public Function  purchaseCallback ()
        {
            return this.m_cellValue.callback;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_cellValue = param1;
            Global.delayedAssets.get(DelayedAssetLoader.EOQSALE_ASSETS, this.onAssetsLoaded);
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_cellValue;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  onAssetsLoaded (DisplayObject param1 ,String param2)
        {
            _loc_3 = param1as Object ;
            this.m_assets = new Dictionary(false);
            this.m_assets.put("bg", (DisplayObject) new _loc_3.get(this.CELL_BG_CLASS));
            this.m_assets.put("icon_cash", (DisplayObject) new _loc_3.EoQSale_cash());
            this.m_assets.put("icon_coins", (DisplayObject) new _loc_3.EoQSale_cash());
            this.m_assets.put("burst_bg", (DisplayObject) new _loc_3.EoQSale_burst_bg());
            this.buildCell();
            return;
        }//end

        protected void  makeBackground ()
        {
            _loc_1 = this.m_assets.get( "bg") ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,10,0));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
            this.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
            this.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            return;
        }//end

        protected void  buildCell ()
        {
            this.makeBackground();
            this.append(this.createHeaderPanel(), BorderLayout.NORTH);
            this.append(this.createBodyPanel(), BorderLayout.CENTER);
            this.append(this.createFooterPanel(), BorderLayout.SOUTH);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, this.HEADING_BOTTOM_PAD, 0);
            return _loc_1;
        }//end

        protected JPanel  createBodyPanel ()
        {
            int _loc_10 =0;
            int _loc_11 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, this.BODY_BOTTOM_PAD, 0);
            _loc_5 = this.amount.toString ();
            _loc_6 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","FlashSale_PackageCell_body1",{costthis.baseCost}));
            _loc_7 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","FlashSale_PackageCell_body2",{costthis.discountCost}));
            _loc_8 = this.m_assets.get( "icon_cash") ;
            int _loc_9 =2;
            if (Global.localizer.langCode == "ja")
            {
                _loc_9 = _loc_9 - 7;
            }
            if (iCnt == myMagicalSelectedBox)
            {
                _loc_10 = EmbeddedArt.whiteTextColor;
                _loc_11 = EmbeddedArt.whiteTextColor;
            }
            else
            {
                _loc_10 = EmbeddedArt.salePopupLightBlue;
                _loc_11 = EmbeddedArt.salePopupTealBlue;
            }
            _loc_12 = ASwingHelper.makeTextField(_loc_5 ,EmbeddedArt.titleFont ,this.BODYA_FONTSIZE ,EmbeddedArt.yellowTextColor ,0);
            _loc_13 = ASwingHelper.makeLabel(_loc_6 ,EmbeddedArt.titleFont ,this.BODYB_FONTSIZE ,_loc_10 );
            _loc_14 = ASwingHelper.makeLabel(_loc_7 ,EmbeddedArt.titleFont ,this.BODYC_FONTSIZE ,_loc_11 );
            AssetPane _loc_15 =new AssetPane(_loc_8 );
            _loc_12.filters = EmbeddedArt.newtitleFilters;
            _loc_4.appendAll(ASwingHelper.verticalStrut(8), _loc_15);
            _loc_16 = this.m_assets.get( "burst_bg") ;
            MarginBackground _loc_17 =new MarginBackground(_loc_16 ,new Insets(0,0,0,0));
            _loc_3.setBackgroundDecorator(_loc_17);
            _loc_3.setPreferredSize(new IntDimension(_loc_16.width, _loc_16.height));
            _loc_3.setMinimumSize(new IntDimension(_loc_16.width, _loc_16.height));
            _loc_3.setMaximumSize(new IntDimension(_loc_16.width, _loc_16.height));
            _loc_3.appendAll(_loc_4, _loc_12);
            _loc_2.appendAll(_loc_3, ASwingHelper.verticalStrut(-10), _loc_13, ASwingHelper.verticalStrut(_loc_9), _loc_14);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  createFooterPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Dialogs","Purchase");
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"GreenButtonUI");
            _loc_3.addActionListener(this.purchaseCallback, 0, false);
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        public static int  CELL_WIDTH ()
        {
            return 208;
        }//end

        public static int  CELL_HEIGHT ()
        {
            return 178;
        }//end

    }



