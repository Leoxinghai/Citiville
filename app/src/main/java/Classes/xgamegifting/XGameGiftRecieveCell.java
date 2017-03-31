package Classes.xgamegifting;

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
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class XGameGiftRecieveCell extends DataItemCell
    {
        private AssetPane m_iconAssetPane ;
        private JPanel m_iconPane ;
        protected JPanel m_countPane ;
        protected JLabel m_countLabel ;
        protected JPanel m_card ;
        protected int m_amount ;
        protected int m_requiredLevel ;
        public static  int CARD_WIDTH =121;
        public static  int CARD_HEIGHT =136;
        public static  int CELL_HGAP =20;
        public static  int CELL_VGAP =20;

        public  XGameGiftRecieveCell (LayoutManager param1)
        {
            super(param1);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            m_item = Global.gameSettings().getItemByName(param1.get("name"));
            this.m_amount = param1.get("quantity");
            this.m_requiredLevel = param1.get("level");
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            this.buildCell();
            return;
        }//end

         protected void  initializeCell ()
        {
            double _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            Array _loc_6 =null ;
            if (Global.player.getXGameLevelByGameId(ExternalGameIds.FARMVILLE) >= this.m_requiredLevel)
            {
                m_itemIcon.filters = new Array();
            }
            else
            {
                _loc_3 = 0.212671;
                _loc_4 = 0.71516;
                _loc_5 = 0.072169;
                _loc_6 = .get(_loc_3, _loc_4, _loc_5, 0, 0, _loc_3, _loc_4, _loc_5, 0, 0, _loc_3, _loc_4, _loc_5, 0, 0, 0, 0, 0, 1, 0);
                m_itemIcon.filters = .get(new ColorMatrixFilter(_loc_6));
            }
            this.m_iconAssetPane.setAsset(m_itemIcon);
            ASwingHelper.prepare(this.m_iconAssetPane);
            _loc_1 = this(88-.m_iconAssetPane.getWidth ())/2;
            _loc_2 = this(88-.m_iconAssetPane.getHeight ())/2;
            ASwingHelper.setEasyBorder(this.m_iconAssetPane, _loc_2, _loc_1);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_iconAssetPane = new AssetPane();
            DisplayObject _loc_1 =(DisplayObject)new XGameGiftingDialog.assetDict.get( "item_card");
            this.m_iconPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setSizedBackground(this.m_iconPane, _loc_1);
            this.m_iconPane.appendAll(this.m_iconAssetPane);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(this.m_iconPane);
            this.m_card = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_card.setPreferredSize(new IntDimension(CARD_WIDTH, CARD_HEIGHT));
            this.m_card.appendAll(ASwingHelper.verticalStrut(10), _loc_2, ASwingHelper.verticalStrut(-18), ASwingHelper.rightAlignElement(this.makeCountPane()));
            _loc_2.addEventListener(MouseEvent.ROLL_OVER, this.showToolTip, false, 0, true);
            _loc_2.addEventListener(MouseEvent.ROLL_OUT, this.hideToolTip, false, 0, true);
            append(this.m_card);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeCountPane ()
        {
            DisplayObject _loc_2 =null ;
            this.m_countPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_countPane.mouseChildren = false;
            this.m_countPane.mouseEnabled = false;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 =(DisplayObject) new XGameGiftingDialog.assetDict.get("countBG");
            _loc_3 = (String)(this.m_amount );
            int _loc_4 =16777215;
            this.m_countPane.setBackgroundDecorator(new AssetBackground(_loc_2));
            this.m_countLabel = ASwingHelper.makeLabel(_loc_3, EmbeddedArt.defaultFontNameBold, 10, _loc_4);
            ASwingHelper.setEasyBorder(this.m_countLabel, 0, 3, 0, 3);
            _loc_1.append(this.m_countLabel);
            this.m_countPane.appendAll(_loc_1);
            ASwingHelper.setEasyBorder(this.m_countPane, 0, 0, 0, 20);
            ASwingHelper.prepare(this.m_countPane);
            return this.m_countPane;
        }//end

        private void  showToolTip (MouseEvent event )
        {
            _loc_2 = this.getGlobalLocation(new IntPoint(this.m_card.getX (),this.m_card.getY ()));
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            this.dispatchEvent(new DataItemEvent(DataItemEvent.SHOW_TOOLTIP, m_item, _loc_3, true, false));
            return;
        }//end

        private void  hideToolTip (MouseEvent event )
        {
            this.dispatchEvent(new DataItemEvent(DataItemEvent.HIDE_TOOLTIP, m_item, null, true, false));
            return;
        }//end

    }


