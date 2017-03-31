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
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.colorchooser.*;
import org.aswing.geom.*;

    public class BusinessUpgradesInfoBox extends JPanel
    {
        protected Item m_item ;
        protected Dictionary m_stats ;
        protected DisplayObject m_bgAsset ;
        protected JPanel m_cellContainer ;
        protected JPanel m_levelStarContainer ;
        protected JPanel m_pictureContainer ;
        protected JPanel m_statsInnerContainer ;
        protected JPanel m_statsOuterContainer ;
        protected DisplayObject m_picture ;
        protected Loader m_pictureLoader ;
        public static  double BOX_HEIGHT =275;
        public static  double CELL_WIDTH =185;
        public static  double CELL_HEIGHT =250;
        public static  double CELL_BUFFER =20;
        public static  double PIC_HEIGHT =80;

        public  BusinessUpgradesInfoBox (Object param1 ,String param2 )
        {
            this.m_bgAsset =(DisplayObject) new param1;
            this.m_item = Global.gameSettings().getItemByName(param2);
            if (this.m_item)
            {
                this.m_pictureLoader = LoadingManager.load(Global.getAssetURL(this.m_item.iconRelative), this.onPicLoad, LoadingManager.PRIORITY_HIGH);
            }
            super(new VerticalLayout(VerticalLayout.CENTER));
            this.buildCell();
            return;
        }//end

        protected void  buildCell ()
        {
            DisplayObject _loc_1 =null ;
            Sprite _loc_2 =null ;
            this.m_cellContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_levelStarContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 5);
            this.m_pictureContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_statsInnerContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT);
            this.m_statsOuterContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.buildBackground();
            this.makeLevelPanel();
            this.makePicturePanel();
            this.makeStatsPanel();
            this.append(this.m_cellContainer);
            this.m_cellContainer.append(ASwingHelper.verticalStrut(5));
            this.m_cellContainer.append(this.m_levelStarContainer);
            this.m_cellContainer.append(ASwingHelper.verticalStrut(18));
            this.m_cellContainer.append(this.m_pictureContainer);
            this.m_cellContainer.append(this.m_statsOuterContainer);
            if (this.m_bgAsset)
            {
                if (this.m_bgAsset.name.indexOf("current") != -1)
                {
                    _loc_1 =(DisplayObject) new UpgradesCommonAssets.bizupsAssetDict.get("checkMark");
                    _loc_1.x = CELL_WIDTH - CELL_BUFFER / 2 - _loc_1.width + 5;
                    _loc_1.y = CELL_HEIGHT - _loc_1.height;
                    _loc_2 = new Sprite();
                    _loc_2.addChild(_loc_1);
                    this.addChild(_loc_2);
                }
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (this.m_bgAsset)
            {
                _loc_1 = new MarginBackground(this.m_bgAsset);
                this.m_cellContainer.setBackgroundDecorator(_loc_1);
                this.m_cellContainer.setPreferredSize(new IntDimension(CELL_WIDTH - CELL_BUFFER, CELL_HEIGHT));
                this.setPreferredSize(new IntDimension(CELL_WIDTH, CELL_HEIGHT));
            }
            return;
        }//end

        protected void  makeLevelPanel ()
        {
            DisplayObject _loc_2 =null ;
            AssetPane _loc_3 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_item.level)
            {

                _loc_2 =(DisplayObject) new UpgradesCommonAssets.bizupsAssetDict.get("star");
                _loc_3 = new AssetPane(_loc_2);
                this.m_levelStarContainer.append(_loc_3);
                _loc_1++;
            }
            return;
        }//end

        protected void  makePicturePanel ()
        {
            this.m_pictureContainer.setPreferredHeight(PIC_HEIGHT);
            return;
        }//end

        protected void  makeStatsPanel ()
        {
            ASColor _loc_1 =new ASColor(EmbeddedArt.blueTextColor );
            ASColor _loc_2 =new ASColor(EmbeddedArt.greenTextColor );
            ASColor _loc_3 =new ASColor(EmbeddedArt.redTextColor );
            this.m_statsOuterContainer.append(ASwingHelper.horizontalStrut(10));
            this.m_statsOuterContainer.append(this.m_statsInnerContainer);
            this.m_statsInnerContainer.append(ASwingHelper.verticalStrut(10));
            JLabel _loc_4 =new JLabel(ZLoc.t("Dialogs","TT_Earnings"));
            _loc_4.setFont(ASwingHelper.getBoldFont(11));
            _loc_4.setForeground(_loc_1);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_5.append(_loc_4);
            this.m_statsInnerContainer.append(_loc_5);
            DisplayObject _loc_6 =new EmbeddedArt.mkt_coinIcon ()as DisplayObject ;
            JLabel _loc_7 =new JLabel(ZLoc.t("Dialogs","NumCoins",{num Math.ceil(this.m_item.commodityReq *Global.player.GetDooberMinimums(this.m_item ,"coin"))}));
            _loc_7.setFont(ASwingHelper.getBoldFont(12));
            _loc_7.setForeground(_loc_2);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_8.appendAll(new AssetPane(_loc_6), ASwingHelper.horizontalStrut(5), _loc_7);
            this.m_statsInnerContainer.append(_loc_8);
            JLabel _loc_9 =new JLabel(ZLoc.t("Dialogs","TT_SupplyReq"));
            _loc_9.setFont(ASwingHelper.getBoldFont(11));
            _loc_9.setForeground(_loc_1);
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_10.append(_loc_9);
            this.m_statsInnerContainer.append(_loc_10);
            DisplayObject _loc_11 =new EmbeddedArt.mkt_goodsIcon ()as DisplayObject ;
            JLabel _loc_12 =new JLabel(ZLoc.t("Dialogs","NumGoods",{num this.m_item.commodityReq }));
            _loc_12.setFont(ASwingHelper.getBoldFont(12));
            _loc_12.setForeground(_loc_2);
            _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_13.appendAll(new AssetPane(_loc_11), ASwingHelper.horizontalStrut(5), _loc_12);
            this.m_statsInnerContainer.append(_loc_13);
            this.m_statsInnerContainer.setPreferredHeight(BOX_HEIGHT);
            this.m_statsInnerContainer.setMinimumHeight(BOX_HEIGHT);
            this.m_statsInnerContainer.setMaximumHeight(BOX_HEIGHT);
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
            Sprite _loc_1 =new Sprite ();
            _loc_1.addChild(this.m_picture);
            this.m_pictureContainer.append(new AssetPane(_loc_1));
            _loc_1.x = this.m_pictureContainer.width / 2 - this.m_picture.width / 2;
            _loc_1.y = this.m_pictureContainer.height / 2 - this.m_picture.height / 2;
            ASwingHelper.prepare(this);
            return;
        }//end

    }



