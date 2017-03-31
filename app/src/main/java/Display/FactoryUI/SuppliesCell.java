package Display.FactoryUI;

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
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import com.zynga.skelly.util.color.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;

    public class SuppliesCell extends DataItemCell
    {
        private int m_amountNeeded ;
        private JLabel m_itemCost ;
        private MarginBackground m_bgDec ;
        protected JPanel m_currencyIcon ;
        protected JPanel m_alignmentContainer ;
        protected JPanel m_itemIconPane ;
        protected DisplayObject m_content ;
        protected DisplayObject m_bgOver ;
        protected DisplayObject m_swappedBG ;
        protected boolean m_itemLocked ;
        protected DisplayObject m_curBGAsset ;
        protected int m_containerWidth ;
        protected int m_containerHeight ;
        protected JPanel m_itemIconHolder ;

        public  SuppliesCell (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            param1 =(Object) param1;
            m_item = param1;
            this.m_amountNeeded = m_item.cost;
            _loc_2 = Global.gameSettings().getImageByName(m_item.name,"icon");
            m_loader = LoadingManager.load(_loc_2, onIconLoad, LoadingManager.PRIORITY_HIGH);
            this.buildCell();
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_alignmentContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            if (m_item.unlock == "level")
            {
                if (Global.player.level < m_item.requiredLevel)
                {
                    this.m_itemLocked = true;
                }
                else
                {
                    this.m_itemLocked = false;
                }
            }
            else if (m_item.unlock == "neighbors")
            {
            }
            this.m_content = this.m_itemLocked ? (new (DisplayObject)SuppliesDialog.assetDict.get("supplies_lockedcard")) : (new SuppliesDialog.assetDict.get("supplies_card"));
            this.setPreferredWidth(this.m_content.width + 2);
            this.setMinimumWidth(this.m_content.width + 2);
            this.setMaximumWidth(this.m_content.width + 2);
            this.m_bgDec = new MarginBackground(this.m_content, new Insets(0, 0, 0, 0));
            this.m_alignmentContainer.setBackgroundDecorator(this.m_bgDec);
            this.m_alignmentContainer.setPreferredSize(new IntDimension(this.m_content.width, this.m_content.height));
            this.m_alignmentContainer.setMinimumSize(new IntDimension(this.m_content.width, this.m_content.height));
            this.m_alignmentContainer.setMaximumSize(new IntDimension(this.m_content.width, this.m_content.height));
            ASwingHelper.prepare(this);
            JLabel _loc_1 =new JLabel(m_item.localizedName );
            _loc_1.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            if (this.m_itemLocked)
            {
                _loc_1.setForeground(new ASColor(9079434, 1));
            }
            else
            {
                _loc_1.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            }
            _loc_1.setFont(ASwingHelper.getBoldFont(18));
            this.m_alignmentContainer.append(_loc_1);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_4 = this.m_itemLocked? (this.makeLockedRightSide()) : (this.makeNormalRightSide());
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            JLabel _loc_6 =new JLabel(ZLoc.t("Dialogs","FactoryCosts"));
            _loc_6.setFont(ASwingHelper.getStandardFont(14));
            if (this.m_itemLocked)
            {
                _loc_6.setForeground(new ASColor(5855577, 1));
            }
            else
            {
                _loc_6.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            }
            _loc_5.append(_loc_6);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            JLabel _loc_8 =new JLabel(String(m_item.cost ));
            _loc_8.setFont(ASwingHelper.getBoldFont(18));
            if (this.m_itemLocked)
            {
                _loc_8.setForeground(new ASColor(9079434, 1));
            }
            else
            {
                _loc_8.setForeground(new ASColor(11737883, 1));
            }
            AssetPane _loc_9 =new AssetPane(new (DisplayObject)SuppliesDialog.assetDict.get( "coin_small") );
            _loc_7.appendAll(_loc_9, _loc_8);
            _loc_3.appendAll(_loc_5, _loc_7);
            _loc_2.appendAll(_loc_3, ASwingHelper.horizontalStrut(15), _loc_4);
            this.m_alignmentContainer.append(_loc_2);
            this.m_itemIconHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_10 =(DisplayObject)new SuppliesDialog.assetDict.get( "icon_bg");
            this.m_itemIconHolder.setBackgroundDecorator(new AssetBackground(_loc_10));
            this.m_containerWidth = _loc_10.width;
            this.m_containerHeight = _loc_10.height;
            ASwingHelper.prepare(this.m_itemIconHolder);
            _loc_3.appendAll(ASwingHelper.verticalStrut(15), this.m_itemIconHolder);
            ASwingHelper.prepare(this.m_alignmentContainer);
            ASwingHelper.prepare(_loc_2);
            this.m_alignmentContainer.append(_loc_2);
            if (!this.m_itemLocked)
            {
                this.addEventListener(MouseEvent.CLICK, this.buyItem, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OVER, this.rollOverItem, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OUT, this.rollOutItem, false, 0, true);
            }
            this.append(this.m_alignmentContainer);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  rollOverItem (MouseEvent event )
        {
            ColorMatrix _loc_2 =new ColorMatrix ();
            _loc_2.adjustContrast(0.3, 0.3, 0.3);
            this.filters = .get(_loc_2.filter);
            return;
        }//end

        private void  rollOutItem (MouseEvent event )
        {
            this.filters = new Array();
            return;
        }//end

        private JPanel  makeLockedRightSide ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_2 =(DisplayObject)new SuppliesDialog.assetDict.get( "lock_bg");
            _loc_1.setBackgroundDecorator(new AssetBackground(_loc_2));
            _loc_1.setMaximumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_1.setMinimumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,-3);
            _loc_3.setBorder(new EmptyBorder(null, new Insets(0, 10, 0, 10)));
            _loc_1.append(_loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,-6);
            DisplayObject _loc_5 =(DisplayObject)new SuppliesDialog.assetDict.get( "slot_locked");
            _loc_4.setBackgroundDecorator(new AssetBackground(_loc_5));
            JLabel _loc_6 =new JLabel(ZLoc.t("Dialogs","FactoryRequires"));
            _loc_6.setFont(ASwingHelper.getStandardFont(13));
            _loc_6.setForeground(ASwingHelper.getWhite());
            JLabel _loc_7 =new JLabel(ZLoc.t("Dialogs","FactoryLevel",{level String(m_item.requiredLevel )}));
            _loc_7.setFont(ASwingHelper.getBoldFont(15));
            _loc_7.setForeground(ASwingHelper.getWhite());
            _loc_4.appendAll(_loc_6, _loc_7);
            JLabel _loc_8 =new JLabel(ZLoc.t("Dialogs","FactoryOr"));
            _loc_8.setFont(ASwingHelper.getBoldFont(16));
            _loc_8.setForeground(new ASColor(9079434, 1));
            JLabel _loc_9 =new JLabel(ZLoc.t("Dialogs","FactoryEarly"));
            _loc_9.setFont(ASwingHelper.getStandardFont(14));
            _loc_9.setForeground(new ASColor(23188, 1));
            CustomButton _loc_10 =new CustomButton(String(m_item.cash ),new SuppliesDialog.assetDict.get( "cash") as Icon ,"BuyGateKeyButtonUI");
            _loc_3.appendAll(ASwingHelper.verticalStrut(35), _loc_4, _loc_8, _loc_9, _loc_10, ASwingHelper.verticalStrut(10));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        private JPanel  makeNormalRightSide ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,5);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            JLabel _loc_3 =new JLabel(ZLoc.t("Dialogs","FactoryEarns"));
            _loc_3.setFont(ASwingHelper.getStandardFont(14));
            _loc_3.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            _loc_2.append(_loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            JLabel _loc_5 =new JLabel(String(m_item.coinYield ));
            _loc_5.setFont(ASwingHelper.getBoldFont(26));
            _loc_5.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            AssetPane _loc_6 =new AssetPane(new (DisplayObject)SuppliesDialog.assetDict.get( "coin_big") );
            _loc_4.appendAll(_loc_6, _loc_5);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            JLabel _loc_8 =new JLabel(String(m_item.xpYield ));
            _loc_8.setFont(ASwingHelper.getBoldFont(21));
            _loc_8.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            AssetPane _loc_9 =new AssetPane(new (DisplayObject)SuppliesDialog.assetDict.get( "star") );
            _loc_7.appendAll(_loc_9, _loc_8);
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            JLabel _loc_11 =new JLabel(CardUtil.localizeTimeLeft(m_item.growTime ));
            _loc_11.setFont(ASwingHelper.getBoldFont(16));
            _loc_11.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            AssetPane _loc_12 =new (DisplayObject)AssetPane(new SuppliesDialog.assetDict.get( "truck") );
            _loc_10.appendAll(_loc_12, _loc_11);
            _loc_1.appendAll(_loc_2, _loc_4, _loc_7, _loc_10);
            return _loc_1;
        }//end

         protected double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            _loc_4 = this.m_containerHeight-4;
            _loc_5 = this.m_containerWidth-4;
            if (param1.width > _loc_5)
            {
                _loc_2 = _loc_5 / param1.width;
            }
            if (param1.height > _loc_4)
            {
                _loc_3 = _loc_4 / param1.height;
            }
            return Math.min(_loc_2, _loc_3);
        }//end

        private void  buyItem (MouseEvent event )
        {
            dispatchEvent(new DataItemEvent(DataItemEvent.MARKET_BUY, this.m_item, null, true));
            return;
        }//end

         protected void  initializeCell ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            AssetPane _loc_3 =new AssetPane(m_itemIcon );
            ASwingHelper.prepare(_loc_3);
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            _loc_2.append(_loc_1);
            ASwingHelper.prepare(_loc_2);
            this.m_itemIconHolder.append(_loc_2);
            ASwingHelper.prepare(this.m_itemIconHolder);
            return;
        }//end

        public void  removeListeners ()
        {
            this.removeEventListener(MouseEvent.CLICK, this.buyItem);
            return;
        }//end

        private void  onBuyClick (MouseEvent event )
        {
            int _loc_2 =0;
            if (Global.player.cash >= m_item.cash)
            {
                _loc_2 = this.getIndex(this.m_itemCost);
                this.removeAt(_loc_2);
                this.m_itemCost = null;
                this.m_itemCost = new JLabel(Global.player.inventory.getItemCountByName(m_item.name).toString() + "/" + this.m_amountNeeded);
                this.m_itemCost.setForeground(new ASColor(16777215, 1));
                this.m_itemCost.setFont(ASwingHelper.getBoldFont(14));
                this.insert(_loc_2, this.m_itemCost);
            }
            return;
        }//end

    }




