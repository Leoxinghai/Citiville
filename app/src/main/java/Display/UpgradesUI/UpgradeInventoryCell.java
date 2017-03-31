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
import Classes.util.*;
import Display.*;
import Display.GridlistUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class UpgradeInventoryCell extends ItemCell
    {
        protected int m_amountNeeded ;
        protected JLabel m_itemCount ;
        private int m_gridCellType =1;
        protected Function m_buyCallback =null ;
        private Function m_askCallback ;
        protected Component m_buyHolder ;
        protected Component m_askHolder ;
        protected Component m_subButtonPanel ;
        protected AssetPane m_wishlistButton ;
        protected Component m_footerComponent ;
        protected String m_url ;
        protected String m_name ;
        protected double m_cost ;
        protected double m_count ;
        protected Dictionary m_assets ;
        public static  int CELL_HEIGHT =104;
        public static  int CELL_WIDTH =280;
        public static  int BUTTON_HEIGHT =30;
        public static  int BUTTON_WIDTH =170;
        public static  ASFont TEXT_FONT =ASwingHelper.getBoldFont(16);

        public  UpgradeInventoryCell (String param1 ,String param2 ,double param3 ,double param4 ,Dictionary param5 )
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, SoftBoxLayout.LEFT));
            this.m_url = param1;
            this.m_name = param2;
            this.m_cost = param3;
            this.m_count = param4;
            m_initialized = false;
            this.m_assets = param5;
            Global.stage.addEventListener(DataItemEvent.WISHLIST_CHANGED, this.onWishlistChanged, false, 1, true);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            if (param1.item)
            {
                m_item = param1.item;
            }
            param1 =(Object) param1;
            this.m_amountNeeded = param1.amountNeeded;
            this.m_buyCallback = param1.buyCallback;
            this.m_askCallback = param1.askCallback;
            this.m_gridCellType = param1.gridCellType;
            if (this.m_url)
            {
                m_loader = LoadingManager.load(this.m_url, onIconLoad, LoadingManager.PRIORITY_HIGH, onIconFail);
            }
            else
            {
                m_itemIcon =(DisplayObject) new EmbeddedArt.emptyAvatar();
            }
            return;
        }//end

        protected Component  createHeaderComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.align = TextFormatAlign.LEFT;
            _loc_3 = TextFieldUtil.getLocaleFontSize(11,9,.get({localesize"ja",14)});
            _loc_4 = ASwingHelper.makeTextField("",EmbeddedArt.titleFont ,_loc_3 ,EmbeddedArt.brownTextColor ,0,_loc_2 );
            _loc_4.setText(this.m_name + " ");
            TextFieldUtil.formatSmallCaps(_loc_4.getTextField(), new TextFormat(EmbeddedArt.titleFont, _loc_3 + 4));
            _loc_1.append(_loc_4);
            return _loc_1;
        }//end

        protected Component  createFooterComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 =(DisplayObject) new this.m_assets.get( "blue_btn");
            _loc_3 = ZLoc.t("Dialogs","KeyAmount",{current this.m_count.toString (),required.m_amountNeeded });
            _loc_3 = "  " + _loc_3 + "  ";
            this.m_itemCount = new JLabel(_loc_3);
            this.m_itemCount.setForeground(new ASColor(16777215, 1));
            this.m_itemCount.setFont(TEXT_FONT);
            ASwingHelper.setBackground(this.m_itemCount, _loc_2);
            _loc_1.append(this.m_itemCount);
            return _loc_1;
        }//end

        protected Component  createImageComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 =(DisplayObject) new this.m_assets.get( "buildables_item");
            ASwingHelper.setSizedBackground(_loc_2, _loc_3, new Insets(5));
            AssetPane _loc_4 =new AssetPane(m_itemIcon );
            ASwingHelper.setEasyBorder(_loc_4, 10, 10);
            _loc_2.appendAll(_loc_4);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected Component  createTopButtonComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","AskButtonLabel"),null ,"AskForKeyButtonUI");
            _loc_3 = TextFieldUtil.getLocaleFontSize(14,10,.get( {locale size "ja",14) });
            _loc_2.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_3));
            _loc_2.addEventListener(MouseEvent.CLICK, this.onAskClick);
            _loc_2.setPreferredHeight(BUTTON_HEIGHT);
            _loc_2.setPreferredWidth(BUTTON_WIDTH);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected Component  createBottomButtonComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            CustomButton _loc_3 =new CustomButton(ZLoc.t("Dialogs","BuyButtonLabel",{amount this.m_cost.toString ()}),_loc_2 ,"BuyGateKeyButtonUI");
            _loc_4 = TextFieldUtil.getLocaleFontSize(14,10,[{localesize"es",10locale},{"ja",size14});
            _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_4));
            _loc_3.addEventListener(MouseEvent.CLICK, this.onBuyClick);
            _loc_3.setPreferredHeight(BUTTON_HEIGHT);
            _loc_3.setPreferredWidth(BUTTON_WIDTH);
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected Component  createActionComponent ()
        {
            Component _loc_1 =null ;
            if (this.showButtons())
            {
                _loc_1 = this.createActionIncompleteComponent();
            }
            else
            {
                _loc_1 = this.createActionCompleteComponent();
            }
            return _loc_1;
        }//end

        protected Component  createActionIncompleteComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            if (this.m_askHolder)
            {
                _loc_1.append(this.m_askHolder);
            }
            _loc_1.append(ASwingHelper.verticalStrut(3));
            if (this.m_buyHolder)
            {
                _loc_1.append(this.m_buyHolder);
            }
            return _loc_1;
        }//end

        protected Component  createActionCompleteComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 =(DisplayObject) new this.m_assets.get( "buildables_check");
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

         protected void  initializeCell ()
        {
            this.removeAll();
            if (this.m_gridCellType == GridCellType.SEND_GIFT)
            {
                this.buildSendGiftCell();
                return;
            }
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_2 = this.createHeaderComponent ();
            _loc_3 = this.createImageComponent ();
            this.m_footerComponent = this.createFooterComponent();
            this.m_askHolder = this.createTopButtonComponent();
            this.m_buyHolder = this.createBottomButtonComponent();
            this.m_subButtonPanel = this.createActionComponent();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_4.appendAll(_loc_3, ASwingHelper.verticalStrut(-14), this.m_footerComponent);
            _loc_1.append(_loc_4);
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT );
            _loc_6 =(DisplayObject) new this.m_assets.get( "buildables_wishlistIcon");
            this.m_wishlistButton = new AssetPane(_loc_6);
            _loc_7 = this.m_wishlistButton.getPreferredWidth ();
            _loc_5.append(this.m_wishlistButton);
            if (!this.allowWishlistButton())
            {
                this.m_wishlistButton.visible = false;
            }
            else
            {
                this.m_wishlistButton.addEventListener(MouseEvent.CLICK, this.onWishlistClicked, false, 0, true);
                _loc_1.append(ASwingHelper.horizontalStrut(-_loc_7));
            }
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_8.append(_loc_5);
            _loc_1.append(_loc_8);
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_9.append(_loc_2);
            _loc_9.append(ASwingHelper.verticalStrut(-4));
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_10, 0, 5);
            _loc_10.append(this.m_subButtonPanel);
            _loc_9.setPreferredWidth(BUTTON_WIDTH + 4);
            _loc_9.append(_loc_10);
            _loc_1.append(_loc_9);
            this.append(_loc_1);
            this.setPreferredHeight(CELL_HEIGHT);
            this.setPreferredWidth(CELL_WIDTH);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected boolean  showButtons ()
        {
            _loc_1 = this.m_count ;
            return _loc_1 >= this.m_amountNeeded ? (false) : (true);
        }//end

        protected void  onBuyClick (MouseEvent event )
        {
            Container _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            AssetPane _loc_5 =null ;
            JPanel _loc_6 =null ;
            _loc_2 = this.m_buyCallback(m_item );
            dispatchEvent(new Event("upgradeInventoryCellUpdateEvent", true));
            if (_loc_2)
            {
                this.m_count++;
                this.m_itemCount.setText(ZLoc.t("Dialogs", "KeyAmount", {current:this.m_count.toString(), required:this.m_amountNeeded}));
                if (!this.showButtons())
                {
                    _loc_3 =(Container) this.m_subButtonPanel.getParent();
                    if (_loc_3)
                    {
                        _loc_3.remove(this.m_subButtonPanel);
                        _loc_4 =(DisplayObject) new this.m_assets.get("buildables_check");
                        _loc_5 = new AssetPane(_loc_4);
                        _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_6.append(_loc_5);
                        _loc_3.append(_loc_6);
                    }
                    ASwingHelper.prepare(this);
                }
            }
            return;
        }//end

        private void  onAskClick (MouseEvent event )
        {
            this.m_askCallback(m_item, m_itemIcon);
            return;
        }//end

        private void  buildSendGiftCell ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,0);
            _loc_2 =(DisplayObject) new this.m_assets.get( "buildables_item");
            ASwingHelper.setSizedBackground(_loc_1, _loc_2, new Insets(0));
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_4 =new AssetPane(m_itemIcon );
            ASwingHelper.setEasyBorder(_loc_4, 6, 10);
            _loc_1.append(_loc_4);
            _loc_3.append(_loc_1);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            CustomButton _loc_6 =new CustomButton(ZLoc.t("Dialogs","SendGifts"),null ,"AskForKeyButtonUI");
            _loc_7 = TextFieldUtil.getLocaleFontSize(18,10,.get({localesize"ja",14)});
            _loc_6.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_7));
            _loc_6.addActionListener(this.onSendGift);
            _loc_6.setPreferredHeight(BUTTON_HEIGHT);
            _loc_6.setMaximumHeight(BUTTON_HEIGHT);
            _loc_6.setPreferredWidth(BUTTON_WIDTH);
            _loc_5.append(_loc_6);
            _loc_5.append(ASwingHelper.verticalStrut(2));
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            ASwingHelper.setEasyBorder(_loc_8, 5, 0);
            _loc_8.append(_loc_3);
            _loc_8.append(ASwingHelper.horizontalStrut(2));
            _loc_8.append(_loc_5);
            this.append(_loc_8);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  onSendGift (Event event )
        {
            FrameManager.navigateTo("gifts.php?ref=neighbor_ladder");
            return;
        }//end

        protected boolean  allowWishlistButton ()
        {
            return !Global.player.isItemOnWishlist(m_item.name);
        }//end

        protected void  onWishlistClicked (Event event )
        {
            if (Global.player.canAddToWishlist(m_item.name))
            {
                GameTransactionManager.addTransaction(new TAddToWishlist(m_item.name));
                dispatchEvent(new DataItemEvent(DataItemEvent.WISHLIST_CHANGED, this.m_item, null, true));
                this.m_wishlistButton.visible = false;
            }
            return;
        }//end

        public void  performUpdate (int param1 =-1)
        {
            if (param1 != -1)
            {
                this.m_count = param1;
            }
            this.initializeCell();
            return;
        }//end

        protected void  onWishlistChanged (DataItemEvent event )
        {
            if (event.item.name == m_item.name)
            {
                this.performUpdate();
            }
            return;
        }//end

    }



