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

import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class XGameGiftSendCell extends DataItemCell
    {
        private AssetPane m_iconAssetPane ;
        private JPanel m_iconPane ;
        protected CustomButton m_sendButton ;
        protected JPanel m_card ;
        protected boolean m_enabled ;
        public static  int CARD_WIDTH =121;
        public static  int CARD_HEIGHT =105;
        public static  int CELL_HGAP =20;
        public static  int CELL_VGAP =20;

        public  XGameGiftSendCell (LayoutManager param1)
        {
            super(param1);
            return;
        }//end

        public void  disableCell ()
        {
            double _loc_1 =0.212671;
            double _loc_2 =0.71516;
            double _loc_3 =0.072169;
            Array _loc_4 =.get(_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,_loc_1 ,_loc_2 ,_loc_3 ,0,0,0,0,0,1,0) ;
            this.m_iconPane.filters = .get(new ColorMatrixFilter(_loc_4));
            this.m_sendButton.setEnabled(false);
            return;
        }//end

        public void  enableCell ()
        {
            this.m_iconPane.filters = new Array();
            this.m_sendButton.setEnabled(true);
            return;
        }//end

        public void  enableCellMindingEligibility ()
        {
            if (m_item.requiredLevel > Global.player.level || !this.m_enabled)
            {
                this.disableCell();
            }
            else
            {
                this.enableCell();
            }
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            param1 =(Array) param1;
            m_item = param1.get(0);
            this.m_enabled = param1.get(1);
            _loc_2 =Global.gameSettings().getImageByName(m_item.name ,m_item.iconImageName );
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            this.buildCell();
            return;
        }//end

         protected void  initializeCell ()
        {
            this.m_iconAssetPane.setAsset(m_itemIcon);
            ASwingHelper.prepare(this.m_iconAssetPane);
            _loc_1 = this(88-.m_iconAssetPane.getWidth ())/2;
            ASwingHelper.setEasyBorder(this.m_iconAssetPane, 0, _loc_1);
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
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_2.append(this.m_iconPane);
            this.m_card = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_card.setPreferredSize(new IntDimension(CARD_WIDTH, CARD_HEIGHT));
            this.m_card.appendAll(ASwingHelper.verticalStrut(10), _loc_2);
            append(this.m_card);
            this.m_sendButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Send")), null, "CoinsButtonUI");
            this.m_sendButton.addActionListener(this.sendGift, 0, true);
            append(ASwingHelper.centerElement(this.m_sendButton));
            append(ASwingHelper.verticalStrut(20));
            _loc_2.addEventListener(MouseEvent.ROLL_OVER, this.showToolTip, false, 0, true);
            _loc_2.addEventListener(MouseEvent.ROLL_OUT, this.hideToolTip, false, 0, true);
            if (m_item.requiredLevel > Global.player.level || !this.m_enabled)
            {
                this.disableCell();
            }
            ASwingHelper.prepare(this);
            return;
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

        private void  sendGift (AWEvent event )
        {
            XGameGiftingManager.instance.send(m_item.name);
            dispatchEvent(new DataItemEvent(DataItemEvent.XPROMO_GIFT_SENT, m_item, null, true));
            return;
        }//end

    }


