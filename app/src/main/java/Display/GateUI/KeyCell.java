package Display.GateUI;

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
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;

    public class KeyCell extends ItemCell
    {
        protected int m_amountNeeded ;
        protected JLabel m_itemCount ;
        protected int m_gridCellType =1;
        protected Function m_buyCallback =null ;
        private Function m_askCallback ;
        private boolean m_canAskBuddyForKey ;
        private Function m_askBuddyCallback ;
        protected Component m_buyHolder ;
        protected Component m_askHolder ;
        protected Component m_subButtonPanel ;
        protected AssetPane m_wishlistButton ;
        protected Component m_footerComponent ;
        protected String m_url ;
        protected String m_name ;
        protected double m_cost ;
        protected double m_count ;
        protected double m_sale ;
        protected Class m_displayObjectClass ;
        protected Object m_value ;
        public static  int CELL_WIDTH =140;
        public static  ASFont BUTTON_FONT =ASwingHelper.getBoldFont(12);
        public static  ASFont TEXT_FONT =ASwingHelper.getBoldFont(16);
        public static  int TEXT_COLOR =9463372;
        private static IStaticCellRenderer s_sendGift ;
        private static IStaticCellRenderer s_askBuddy ;

        public  KeyCell (String param1 ,String param2 ,double param3 ,double param4 ,double param5 ,Class param6 =null )
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, SoftBoxLayout.LEFT));
            this.m_url = param1;
            this.m_name = param2;
            this.m_cost = param3;
            this.m_count = param4;
            this.m_sale = param5;
            this.m_displayObjectClass = param6;
            m_initialized = false;
            Global.stage.addEventListener(DataItemEvent.WISHLIST_CHANGED, this.onWishlistChanged, false, 1, true);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            if (param1.item)
            {
                m_item = param1.item;
            }
            this.m_value = param1;
            param1 =(Object) param1;
            this.m_amountNeeded = param1.amountNeeded;
            this.m_gridCellType = param1.gridCellType;
            this.m_buyCallback = param1.buyCallback;
            this.m_askCallback = param1.askCallback;
            if (param1.hasOwnProperty("askBuddyCallback"))
            {
                this.m_askBuddyCallback = param1.askBuddyCallback;
            }
            if (param1.hasOwnProperty("canAskBuddy"))
            {
                this.m_canAskBuddyForKey = param1.canAskBuddy;
            }
            if (this.m_url)
            {
                m_loader = LoadingManager.load(this.m_url, onIconLoad, LoadingManager.PRIORITY_HIGH, onIconFail);
            }
            else
            {
                m_itemIcon = new this.m_displayObjectClass();
                addEventListener(Event.ADDED_TO_STAGE, onIconLoad, false, 0, true);
            }
            return;
        }//end

        protected Component  createHeaderComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.align = TextFormatAlign.CENTER;
            _loc_3 = TextFieldUtil.getLocaleFontSize(16,10,.get({localesize"ja",16)});
            _loc_4 = ASwingHelper.makeTextField("",EmbeddedArt.titleFont,_loc_3,EmbeddedArt.brownTextColor,0,_loc_2);
            _loc_4.setText(this.m_name + " ");
            TextFieldUtil.formatSmallCaps(_loc_4.getTextField(), new TextFormat(EmbeddedArt.titleFont, _loc_3 + 4));
            _loc_1.append(_loc_4);
            return _loc_1;
        }//end

        protected Component  createFooterComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ZLoc.t("Dialogs","KeyAmount",{currentthis.m_count.toString(),required.m_amountNeeded});
            this.m_itemCount = new JLabel(_loc_2);
            this.m_itemCount.setForeground(new ASColor(EmbeddedArt.brownTextColor, 1));
            this.m_itemCount.setFont(TEXT_FONT);
            this.m_itemCount.setTextFilters(.get(new GlowFilter(16777215, 1, 2, 2, 10)));
            this.m_itemCount.setPreferredWidth(CELL_WIDTH);
            _loc_1.append(this.m_itemCount);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            DisplayObject _loc_4 =(DisplayObject)new GateDialog.assets.get( "buildables_wishlistIcon");
            this.m_wishlistButton = new AssetPane(_loc_4);
            _loc_5 = this.m_wishlistButton.getPreferredWidth();
            _loc_3.append(this.m_wishlistButton);
            _loc_1.setPreferredHeight(this.m_wishlistButton.getPreferredHeight());
            if (!this.allowWishlistButton())
            {
                this.m_wishlistButton.visible = false;
            }
            else
            {
                this.m_wishlistButton.addEventListener(MouseEvent.CLICK, this.onWishlistClicked, false, 0, true);
                _loc_1.append(ASwingHelper.horizontalStrut(-_loc_5));
            }
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected Component  createImageComponent ()
        {
            Sprite _loc_5 =null ;
            TextField _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            DisplayObject _loc_3 =(DisplayObject)new GateDialog.assets.get( "buildables_item");
            ASwingHelper.setSizedBackground(_loc_2, _loc_3, new Insets(5));
            AssetPane _loc_4 =new AssetPane(m_itemIcon );
            ASwingHelper.setEasyBorder(_loc_4, 10, 10);
            _loc_2.appendAll(_loc_4);
            _loc_1.append(_loc_2);
            if (this.m_sale > 0)
            {
                _loc_5 = new Sprite();
                _loc_5.graphics.beginFill(10170623);
                _loc_5.graphics.drawRoundRect(0, 0, _loc_3.width, 33, 10, 10);
                _loc_5.graphics.endFill();
                _loc_5.x = 40;
                _loc_5.y = 60;
                _loc_1.addChild(_loc_5);
                _loc_6 = ASwingHelper.makeText(this.m_sale.toString() + "%\n" + ZLoc.t("Main", "Discount"), 200, EmbeddedArt.defaultFontNameBold, 12, 16777215, "center");
                _loc_6.width = _loc_3.width;
                _loc_6.x = 40;
                _loc_6.y = 60;
                _loc_6.multiline = true;
                _loc_1.addChild(_loc_6);
            }
            return _loc_1;
        }//end

        protected Component  createTopButtonComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","AskButtonLabel"),null ,"AskForKeyButtonUI");
            _loc_3 = TextFieldUtil.getLocaleFontSize(18,10,.get({localesize"ja",18)});
            _loc_2.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_3));
            _loc_2.addEventListener(MouseEvent.CLICK, this.onAskClick);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected Component  createBottomButtonComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            CustomButton _loc_3 =new CustomButton(ZLoc.t("Dialogs","BuyButtonLabel",{amount this.m_cost.toString ()}),_loc_2 ,"BuyGateKeyButtonUI");
            _loc_4 = TextFieldUtil.getLocaleFontSize(18,10,[{localesize"es",10locale},{"ja",size18});
            _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_4));
            _loc_3.addEventListener(MouseEvent.CLICK, this.onBuyClick);
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
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            if (this.m_footerComponent)
            {
                _loc_1.append(this.m_footerComponent);
            }
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
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_2 =(DisplayObject)new GateDialog.assets.get( "buildables_check");
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

         protected void  initializeCell ()
        {
            IStaticCellRenderer _loc_1 =null ;
            IStaticCellRenderer _loc_2 =null ;
            Component _loc_3 =null ;
            Component _loc_4 =null ;
            this.removeAll();
            if (this.m_gridCellType == GridCellType.SEND_GIFT)
            {
                _loc_1 = this.createSendGiftRenderer();
                _loc_1.render(this);
            }
            else if (this.m_gridCellType == GridCellType.ASK_BUILDING_BUDDY)
            {
                _loc_2 = this.createAskBuddyRenderer();
                _loc_2.buttonEnabled = this.m_canAskBuddyForKey;
                _loc_2.render(this);
                this.setName("askbuddy");
            }
            else
            {
                _loc_3 = this.createHeaderComponent();
                _loc_4 = this.createImageComponent();
                this.m_footerComponent = this.createFooterComponent();
                this.m_askHolder = this.createTopButtonComponent();
                this.m_buyHolder = this.createBottomButtonComponent();
                this.m_subButtonPanel = this.createActionComponent();
                this.append(_loc_3);
                this.append(_loc_4);
                this.append(ASwingHelper.verticalStrut(20));
                this.append(this.m_subButtonPanel);
                this.setPreferredWidth(CELL_WIDTH);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected IStaticCellRenderer  createSendGiftRenderer ()
        {
            StaticCellRenderer _loc_1 =null ;
            if (!s_sendGift)
            {
                _loc_1 = new StaticCellRenderer();
                _loc_1.label = "SendGiftsToFriends";
                _loc_1.buttonLabel = "SendGifts";
                _loc_1.icon = m_itemIcon;
                s_sendGift = _loc_1;
            }
            s_sendGift.refreshHandler = this.onTimerComplete;
            s_sendGift.buttonClickHandler = this.onSendGift;
            return s_sendGift;
        }//end

        protected IStaticCellRenderer  createAskBuddyRenderer ()
        {
            StaticCellRenderer _loc_1 =null ;
            Class _loc_2 =null ;
            if (!s_askBuddy)
            {
                _loc_1 = new StaticCellRenderer();
                _loc_1.label = "askBuddyItemHeader";
                _loc_1.buttonLabel = "askBuddyItemButton";
                _loc_2 =(Class) GateDialog.assets.buildingBuddy_cellPic;
                _loc_1.icon = new _loc_2;
                s_askBuddy = _loc_1;
            }
            s_askBuddy.refreshHandler = this.onTimerComplete;
            s_askBuddy.buttonClickHandler = this.onAskBuddy;
            return s_askBuddy;
        }//end

        protected boolean  showButtons ()
        {
            _loc_1 = this.m_count ;
            return _loc_1 >= this.m_amountNeeded ? (false) : (true);
        }//end

        protected void  onBuyClick (MouseEvent event )
        {
            DisplayObject _loc_3 =null ;
            AssetPane _loc_4 =null ;
            JPanel _loc_5 =null ;
            _loc_2 = this.m_buyCallback(m_item);
            if (_loc_2)
            {
                this.m_count++;
                this.m_itemCount.setText(ZLoc.t("Dialogs", "KeyAmount", {current:this.m_count.toString(), required:this.m_amountNeeded}));
                if (!this.showButtons())
                {
                    this.remove(this.m_subButtonPanel);
                    _loc_3 =(DisplayObject) new GateDialog.assets.get("buildables_check");
                    _loc_4 = new AssetPane(_loc_3);
                    _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    _loc_5.append(_loc_4);
                    this.append(_loc_5);
                    ASwingHelper.prepare(this);
                }
            }
            this.dispatchEvent(new Event("buyCellItemEvent", true));
            return;
        }//end

        private void  onAskClick (MouseEvent event )
        {
            this.m_askCallback(m_item, m_itemIcon);
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

        protected void  onAskBuddy (Event event )
        {
            if (this.m_askBuddyCallback != null)
            {
                this.m_askBuddyCallback();
            }
            this.performUpdate();
            return;
        }//end

        protected void  onTimerComplete (Event event )
        {
            this.m_canAskBuddyForKey = true;
            this.performUpdate();
            return;
        }//end

        private void  onSendGift (Event event )
        {
            FrameManager.navigateTo("gifts.php?ref=neighbor_ladder");
            return;
        }//end

    }




