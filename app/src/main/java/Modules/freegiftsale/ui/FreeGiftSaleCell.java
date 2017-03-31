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
import Engine.Managers.*;
import Modules.flashsale.ui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;

    public class FreeGiftSaleCell extends PackageSaleMiniCell
    {
        private int m_cashAmount ;
        private Loader m_loader ;
        private AssetPane m_iconAssetPane ;

        public  FreeGiftSaleCell (int param1 =10)
        {
            this.m_iconAssetPane = new AssetPane();
            this.setLayout(new SoftBoxLayout(AsWingConstants.VERTICAL));
            return;
        }//end

         protected String  CELL_BG_CLASS ()
        {
            return "dialog_flashSaleMini_packageSaleCard";
        }//end

         protected int  HEADING_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(18, 1, [{locale:"ja", ratio:0.7}]);
        }//end

         protected int  BODYA_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(32, 1, [{locale:"ja", ratio:0.8}]);
        }//end

         protected int  BODYB_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(16, 1, [{locale:"ja", ratio:0.8}]);
        }//end

         protected int  BODYC_FONTSIZE ()
        {
            return TextFieldUtil.getLocaleFontSizeByRatio(20, 1, [{locale:"ja", ratio:1}]);
        }//end

         protected int  BODY_BOTTOM_PAD ()
        {
            return 0;
        }//end

         protected void  buildCell ()
        {
            makeBackground();
            this.appendAll(this.createHeaderPanel(), this.createBodyPanel(), this.createFooterPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, HEADING_BOTTOM_PAD, 0);
            _loc_2 = ZLoc.t("Items",m_cellValue.giftItem +"_friendlyName");
            _loc_3 = ZLoc.t("Dialogs","FreeGiftSaleHeader",{business _loc_2 });
            _loc_4 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.defaultFontNameBold ,this.HEADING_FONTSIZE ,EmbeddedArt.blueTextColor );
            _loc_1.append(_loc_4);
            return _loc_1;
        }//end

         protected JPanel  createBodyPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            JPanel _loc_2 =new JPanel(new FlowLayout ());
            JPanel _loc_3 =new JPanel(new SoftBoxLayout(AsWingConstants.VERTICAL ));
            JPanel _loc_4 =new JPanel(new SoftBoxLayout(AsWingConstants.VERTICAL ));
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, this.BODY_BOTTOM_PAD, 0);
            _loc_5 = amount.toString();
            _loc_6 = ZLoc.t("Dialogs","FreeGiftSaleCost",{cost m_cellValue.cost });
            _loc_7 = ZLoc.t("Dialogs","FreeGiftSaleFree");
            _loc_8 = type=="cash"? (m_assets.get("icon_cash")) : (m_assets.get("icon_coins"));
            _loc_9 =Global.gameSettings().getItemByName(m_cellValue.giftItem );
            _loc_10 =Global.gameSettings().getImageByName(_loc_9.name ,_loc_9.iconImageName );
            this.m_loader = LoadingManager.load(_loc_10, this.onIconLoad, LoadingManager.PRIORITY_HIGH);
            TextFormat _loc_11 =new TextFormat ();
            _loc_11.align = TextFormatAlign.CENTER;
            _loc_12 = ASwingHelper.makeLabel(_loc_5 ,EmbeddedArt.titleFont ,this.BODYA_FONTSIZE ,EmbeddedArt.yellowTextColor ,JLabel.CENTER );
            _loc_13 = ASwingHelper.makeLabel(_loc_6 ,EmbeddedArt.titleFont ,this.BODYC_FONTSIZE ,EmbeddedArt.blueTextColor ,JLabel.CENTER );
            _loc_14 = ASwingHelper.makeTextField(_loc_7 ,EmbeddedArt.titleFont ,this.BODYC_FONTSIZE ,EmbeddedArt.yellowTextColor ,0,_loc_11 );
            AssetPane _loc_15 =new AssetPane(_loc_8 );
            _loc_12.setTextFilters(EmbeddedArt.newtitleFilters);
            _loc_14.filters = EmbeddedArt.newtitleFilters;
            _loc_4.appendAll(this.m_iconAssetPane, _loc_14);
            ASwingHelper.setEasyBorder(_loc_15, 0);
            double _loc_16 =-9;
            double _loc_17 =-8;
            if (Global.localizer.langCode == "ja")
            {
                _loc_16 = int(_loc_16 * 0.7);
                _loc_17 = int(_loc_17 * 0.7);
            }
            ASwingHelper.setEasyBorder(_loc_12, _loc_16);
            ASwingHelper.setEasyBorder(_loc_13, _loc_17);
            _loc_3.appendAll(_loc_15, _loc_12, _loc_13);
            _loc_2.appendAll(_loc_3, _loc_4);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_1, -6);
            return _loc_1;
        }//end

         protected JPanel  createFooterPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Dialogs","Purchase");
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"GreenButtonUI");
            _loc_3.addActionListener(this.clickCallback, 0, false);
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected void  onIconLoad (Event event )
        {
            DisplayObject _loc_2 =null ;
            if (this.m_loader && this.m_loader.content)
            {
                _loc_2 = this.m_loader.content;
            }
            if (_loc_2 instanceof Bitmap)
            {
                ((Bitmap)_loc_2).smoothing = true;
            }
            this.m_iconAssetPane.setAsset(_loc_2);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  clickCallback (Event event )
        {
            if (m_cellValue.callback)
            {
                m_cellValue.callback(event);
            }
            return;
        }//end

        public static int  CELL_WIDTH ()
        {
            return 199;
        }//end

        public static int  CELL_HEIGHT ()
        {
            return 174;
        }//end

    }



