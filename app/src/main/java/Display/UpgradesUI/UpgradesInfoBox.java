package Display.UpgradesUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class UpgradesInfoBox extends JPanel
    {
        protected Item m_item ;
        protected Dictionary m_stats ;
        protected JPanel m_statsPanel ;
        protected DisplayObject m_picture ;
        protected Loader m_pictureLoader ;
        protected JPanel m_picturePanel ;
        protected DisplayObject m_bgAsset ;
        public static  double BOX_WIDTH_PIC =300;
        public static  double BOX_WIDTH_NO_PIC =200;
        public static  double BOX_HEIGHT =175;

        public  UpgradesInfoBox (Object param1 ,String param2 )
        {
            this.m_bgAsset =(DisplayObject) new param1;
            this.m_item = Global.gameSettings().getItemByName(param2);
            this.m_stats = Global.gameSettings().getDisplayedStatsByName(param2);
            if (this.m_stats.get("picture") != null)
            {
                this.m_pictureLoader = LoadingManager.load(Global.getAssetURL(this.m_stats.get("picture")), this.onPicLoad, LoadingManager.PRIORITY_HIGH);
            }
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.TOP));
            this.buildCell();
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_statsPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT);
            this.buildBackground();
            if (this.m_stats.get("picture") != null)
            {
                this.m_picturePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                this.makePicturePanel();
            }
            this.makeStatsPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (this.m_bgAsset)
            {
                _loc_1 = new MarginBackground(this.m_bgAsset);
                this.setBackgroundDecorator(_loc_1);
                if (this.m_stats.get("picture") != null)
                {
                    this.setPreferredSize(new IntDimension(BOX_WIDTH_PIC, BOX_HEIGHT));
                    this.setMinimumSize(new IntDimension(BOX_WIDTH_PIC, BOX_HEIGHT));
                    this.setMaximumSize(new IntDimension(BOX_WIDTH_PIC, BOX_HEIGHT));
                }
                else
                {
                    this.setPreferredSize(new IntDimension(BOX_WIDTH_NO_PIC, BOX_HEIGHT));
                    this.setMinimumSize(new IntDimension(BOX_WIDTH_NO_PIC, BOX_HEIGHT));
                    this.setMaximumSize(new IntDimension(BOX_WIDTH_NO_PIC, BOX_HEIGHT));
                }
            }
            return;
        }//end

        protected void  makePicturePanel ()
        {
            this.m_picturePanel.setPreferredWidth(BOX_WIDTH_PIC - BOX_WIDTH_NO_PIC);
            this.m_picturePanel.setMinimumWidth(BOX_WIDTH_PIC - BOX_WIDTH_NO_PIC);
            this.m_picturePanel.setMaximumWidth(BOX_WIDTH_PIC - BOX_WIDTH_NO_PIC);
            this.appendAll(ASwingHelper.horizontalStrut(10), this.m_picturePanel);
            return;
        }//end

        protected void  makeStatsPanel ()
        {
            JLabel _loc_4 =null ;
            JPanel _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            JLabel _loc_7 =null ;
            JPanel _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            JLabel _loc_10 =null ;
            JPanel _loc_11 =null ;
            DisplayObject _loc_12 =null ;
            JLabel _loc_13 =null ;
            JPanel _loc_14 =null ;
            DisplayObject _loc_15 =null ;
            JLabel _loc_16 =null ;
            JPanel _loc_17 =null ;
            DisplayObject _loc_18 =null ;
            JLabel _loc_19 =null ;
            JPanel _loc_20 =null ;
            JLabel _loc_21 =null ;
            JPanel _loc_22 =null ;
            Class _loc_23 =null ;
            DisplayObject _loc_24 =null ;
            JLabel _loc_25 =null ;
            JPanel _loc_26 =null ;
            JLabel _loc_27 =null ;
            JPanel _loc_28 =null ;
            int _loc_29 =0;
            String _loc_30 =null ;
            int _loc_31 =0;
            JLabel _loc_32 =null ;
            int _loc_33 =0;
            JPanel _loc_34 =null ;
            AssetPane _loc_35 =null ;
            ASColor _loc_1 =new ASColor(EmbeddedArt.blueTextColor );
            ASColor _loc_2 =new ASColor(EmbeddedArt.greenTextColor );
            ASColor _loc_3 =new ASColor(EmbeddedArt.brownTextColor );
            this.m_statsPanel.append(ASwingHelper.verticalStrut(10));
            if (this.m_stats.get("coin") || this.m_stats.get("xp") || this.m_stats.get("goods") || this.m_stats.get("premium_goods") || this.m_stats.get("energy"))
            {
                _loc_4 = new JLabel(ZLoc.t("Dialogs", "TT_Earnings"));
                _loc_4.setFont(ASwingHelper.getBoldFont(11));
                _loc_4.setForeground(_loc_1);
                _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_5.append(_loc_4);
                this.m_statsPanel.append(_loc_5);
                if (this.m_stats.get("coin"))
                {
                    _loc_6 =(DisplayObject) new EmbeddedArt.mkt_coinIcon();
                    _loc_7 = new JLabel(ZLoc.t("Dialogs", "NumCoins", {num:this.m_stats.get("coin")}));
                    _loc_7.setFont(ASwingHelper.getBoldFont(12));
                    _loc_7.setForeground(_loc_2);
                    _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_8.appendAll(new AssetPane(_loc_6), ASwingHelper.horizontalStrut(5), _loc_7);
                    this.m_statsPanel.append(_loc_8);
                }
                if (this.m_stats.get("xp"))
                {
                    _loc_9 =(DisplayObject) new EmbeddedArt.mkt_xpIcon();
                    _loc_10 = new JLabel(ZLoc.t("Dialogs", "NumXP", {num:this.m_stats.get("xp")}));
                    _loc_10.setFont(ASwingHelper.getBoldFont(12));
                    _loc_10.setForeground(_loc_2);
                    _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_11.appendAll(new AssetPane(_loc_9), ASwingHelper.horizontalStrut(5), _loc_10);
                    this.m_statsPanel.append(_loc_11);
                }
                if (this.m_stats.get("goods"))
                {
                    _loc_12 =(DisplayObject) new EmbeddedArt.mkt_goodsIcon();
                    _loc_13 = new JLabel(ZLoc.t("Dialogs", "NumGoods", {num:this.m_stats.get("goods")}));
                    _loc_13.setFont(ASwingHelper.getBoldFont(12));
                    _loc_13.setForeground(_loc_2);
                    _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_14.appendAll(new AssetPane(_loc_12), ASwingHelper.horizontalStrut(5), _loc_13);
                    this.m_statsPanel.append(_loc_14);
                }
                if (this.m_stats.get("premium_goods"))
                {
                    _loc_15 =(DisplayObject) new EmbeddedArt.mkt_premiumGoodsIcon();
                    _loc_16 = new JLabel(ZLoc.t("Dialogs", "NumPremiumGoods", {num:this.m_stats.get("premium_goods")}));
                    _loc_16.setFont(ASwingHelper.getBoldFont(12));
                    _loc_16.setForeground(_loc_2);
                    _loc_17 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_17.appendAll(new AssetPane(_loc_15), ASwingHelper.horizontalStrut(5), _loc_16);
                    this.m_statsPanel.append(_loc_17);
                }
                if (this.m_stats.get("energy"))
                {
                    _loc_18 =(DisplayObject) new EmbeddedArt.mkt_energyIcon();
                    _loc_19 = new JLabel(ZLoc.t("Dialogs", "NumEnergy", {num:this.m_stats.get("energy")}));
                    _loc_19.setFont(ASwingHelper.getBoldFont(12));
                    _loc_19.setForeground(_loc_2);
                    _loc_20 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_20.appendAll(new AssetPane(_loc_18), ASwingHelper.horizontalStrut(5), _loc_19);
                    this.m_statsPanel.append(_loc_20);
                }
            }
            if (this.m_stats.get("population"))
            {
                _loc_21 = new JLabel(ZLoc.t("Dialogs", "TT_Allows"));
                _loc_21.setFont(ASwingHelper.getBoldFont(11));
                _loc_21.setForeground(_loc_1);
                _loc_22 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_22.append(_loc_21);
                _loc_23 = PopulationHelper.getItemPopulationIcon(this.m_item);
                _loc_24 =(DisplayObject) new _loc_23;
                _loc_25 = new JLabel(ZLoc.t("Dialogs", "TT_ExpansionPopulationReq", {population:this.m_stats.get("population")}));
                _loc_25.setFont(ASwingHelper.getBoldFont(12));
                _loc_25.setForeground(_loc_2);
                _loc_26 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_26.appendAll(new AssetPane(_loc_24), ASwingHelper.horizontalStrut(5), _loc_25);
                _loc_27 = new JLabel(PopulationHelper.getItemPopulationSubTitle(this.m_item));
                _loc_27.setFont(ASwingHelper.getBoldFont(10));
                _loc_27.setForeground(_loc_3);
                _loc_28 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_28.append(_loc_27);
                this.m_statsPanel.appendAll(_loc_22, _loc_26, _loc_28);
            }
            if (this.m_stats.get("bonus") != null && this.m_stats.get("bonus").length > 0)
            {
                _loc_29 = 0;
                for(int i0 = 0; i0 < this.m_stats.get("bonus").size(); i0++)
                {
                	_loc_30 = this.m_stats.get("bonus").get(i0);

                    _loc_29++;
                }
                _loc_31 = 2;
                _loc_32 = new JLabel(ZLoc.t("Dialogs", "TT_Bonus"));
                _loc_33 = 11;
                if (_loc_29 < _loc_31)
                {
                    _loc_33 = 14;
                    this.m_statsPanel.append(ASwingHelper.verticalStrut(6));
                }
                _loc_32.setFont(ASwingHelper.getBoldFont(_loc_33));
                _loc_32.setForeground(_loc_1);
                _loc_34 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_34.append(_loc_32);
                this.m_statsPanel.append(_loc_34);
                _loc_33 = 12;
                if (_loc_29 < _loc_31)
                {
                    _loc_33 = 14;
                }
                for(int i0 = 0; i0 < this.m_stats.get("bonus").size(); i0++)
                {
                	_loc_30 = this.m_stats.get("bonus").get(i0);

                    _loc_35 = ASwingHelper.makeMultilineText(ZLoc.t("Items", _loc_30), 190, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, _loc_33, EmbeddedArt.greenTextColor);
                    this.m_statsPanel.append(_loc_35);
                }
            }
            this.m_statsPanel.setPreferredHeight(BOX_HEIGHT);
            this.m_statsPanel.setMinimumHeight(BOX_HEIGHT);
            this.m_statsPanel.setMaximumHeight(BOX_HEIGHT);
            this.append(this.m_statsPanel);
            return;
        }//end

        protected void  onPicLoad (Event event )
        {
            if (this.m_pictureLoader && this.m_pictureLoader.content)
            {
                this.m_picture = this.m_pictureLoader.content;
            }
            if (this.m_picture instanceof Bitmap)
            {
                ((Bitmap)this.m_picture).smoothing = true;
            }
            this.placePicture();
            return;
        }//end

        protected void  placePicture ()
        {
            Sprite _loc_1 =null ;
            _loc_1 = new Sprite();
            _loc_1.addChild(this.m_picture);
            this.m_picturePanel.addChild(_loc_1);
            _loc_1.x = this.m_picturePanel.width / 2 - this.m_picture.width / 2;
            _loc_1.y = this.m_picturePanel.height / 2 - this.m_picture.height / 2;
            ASwingHelper.prepare(this);
            return;
        }//end

    }



