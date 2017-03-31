package Modules.garden.ui;

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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.garden.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class GardenDialogView extends GenericDialogView
    {
        private Garden m_garden ;
        private ISlotMechanic m_slotMechanic ;
        protected boolean m_closedEnabled =true ;
        private AssetPane m_backdropAssetPane ;
        protected CustomButton m_buyDonationsButton ;
        private GardenBackdrop m_backdrop ;
        private GardenHelpTextBubble m_helpTextDialog ;
        private MechanicMapResource m_spawner ;
        private Dictionary m_tooltipData ;
        private GardenScrollingList m_gardenScrollingList ;
        private SimpleButton m_guestsHelpButton ;
        protected GardenToolTip m_toolTip ;
        protected Array m_sortOrder ;
        public static  int WINDOW_WIDTH =749;
        public static  int WINDOW_HEIGHT =575;
        public static  int BACKDROP_PANE_HEIGHT =330;

        public  GardenDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,Garden param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null ,String param11 ="")
        {
            this.m_tooltipData = new Dictionary();
            this.m_sortOrder = .get(Garden.RARITY_COMMON, Garden.RARITY_UNCOMMON, Garden.RARITY_RARE);
            this.m_garden = param6;
            this.m_spawner = param1.get("spawner");
            super(param1, param2, param3, param4, param5, param7, param8, param9, param10, param11);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  init ()
        {
            this.m_slotMechanic =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_garden, GardenManager.MECHANIC_SLOTS, MechanicManager.ALL);
            super.init();
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =new MarginBackground(m_assetDict.get( "dialog_bg") ,new Insets(0,0,20,0));
            setBackgroundDecorator(_loc_1);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            topPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            topPanel.setPreferredHeight(BACKDROP_PANE_HEIGHT);
            setPreferredSize(new IntDimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            AssetPane backdropAssetPane =new AssetPane ();
            backdropAssetPane.setPreferredSize(new IntDimension(WINDOW_WIDTH, BACKDROP_PANE_HEIGHT));
            this.m_backdrop = new GardenBackdrop(this.m_garden, this.m_slotMechanic);
            this.m_backdrop.addEventListener(Event.CLOSE, this.onBackdropClose);
            backdropAssetPane.setAsset(this.m_backdrop);
            mainContentPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            mainContentPanel.setPreferredSize(new IntDimension(WINDOW_WIDTH, BACKDROP_PANE_HEIGHT));
            mainContentPanel.append(backdropAssetPane);
            marketPanelHolder = ASwingHelper.makeFlowJPanel(SoftBoxLayout.LEFT);
            marketPanelHolder.append(this.makeScrollingList());
            innerBottomPanel = ASwingHelper.makeSoftBoxJPanelVertical(1,-3);
            innerBottomPanel.appendAll(marketPanelHolder);
            bottomPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            bottomPanel.appendAll(innerBottomPanel, ASwingHelper.horizontalStrut(10));
            buttonPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.makeBuyDonationsButton();
            buttonPanel.append(this.m_buyDonationsButton);
            headerPanel = createHeaderPanel();
            topPanel.appendAll(mainContentPanel, ASwingHelper.verticalStrut(-BACKDROP_PANE_HEIGHT), headerPanel);
            CustomButton getGardens =new CustomButton(ZLoc.t("Dialogs","GardenDialog_getGardens"),null ,"OrangeMediumButtonUI");
            getGardens .addActionListener (void  (AWEvent event )
            {
                close();
                UI.displayCatalog(new CatalogParams().setType("decorRoads").setItemName("garden_roses_2x2"));
                return;
            }//end
            , 0, false);
            getGardensHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            ASwingHelper.setEasyBorder(getGardens, 0, 0, 5, 10);
            getGardensHolder.append(getGardens);
            ASwingHelper.prepare(getGardensHolder);
            appendAll(topPanel, getGardensHolder, bottomPanel, ASwingHelper.verticalStrut(-10), buttonPanel);
            ASwingHelper.prepare(this);
            DisplayObject questionIcon =(DisplayObject)new m_assetDict.get( "info_button");
            this.m_guestsHelpButton = new SimpleButton(questionIcon, questionIcon, questionIcon, questionIcon);
            ASwingHelper.prepare(headerPanel);
            this.m_guestsHelpButton.x = this.getWidth() - questionIcon.width - 50;
            this.m_guestsHelpButton.y = 10;
            this.addChild(this.m_guestsHelpButton);
            this.m_guestsHelpButton.addEventListener(MouseEvent.MOUSE_OVER, this.showHelpTip, false, 0, true);
            this.m_guestsHelpButton.addEventListener(MouseEvent.MOUSE_OUT, this.hideHelpTip, false, 0, true);
            this.m_helpTextDialog = new GardenHelpTextBubble(ZLoc.t("Dialogs", "GardenDialog_helptext_title"), ZLoc.t("Dialogs", "GardenDialog_helptext"));
            this.m_helpTextDialog.x = 390;
            this.m_helpTextDialog.y = 45;
            this.addChild(this.m_helpTextDialog);
            this.m_helpTextDialog.mouseChildren = false;
            this.m_helpTextDialog.mouseEnabled = false;
            this.m_helpTextDialog.visible = false;
            this.m_toolTip = new GardenToolTip();
            this.m_toolTip.visible = false;
            addChild(this.m_toolTip);
            return;
        }//end

        private void  showHelpTip (Event event )
        {
            this.m_helpTextDialog.visible = true;
            return;
        }//end

        private void  hideHelpTip (Event event )
        {
            this.m_helpTextDialog.visible = false;
            return;
        }//end

        private GardenScrollingList  makeScrollingList ()
        {
            Array _loc_1 =new Array();
            _loc_1 = Global.gameSettings().getItemsByKeywords(.get(this.m_garden.getMechanicConfig("slots").params.get("restrictByKeywords")));
            _loc_1.sort(this.sortFlowerConfigs);
            this.m_gardenScrollingList = new GardenScrollingList(this, this.m_slotMechanic, this.m_garden, _loc_1, GardenCellFactory, 0, 6, 1);
            ASwingHelper.setEasyBorder(this.m_gardenScrollingList, 0, 0, 10, 0);
            return this.m_gardenScrollingList;
        }//end

        public int  sortFlowerConfigs (Item param1 ,Item param2 )
        {
            if (this.m_sortOrder.indexOf(param1.rarity) < this.m_sortOrder.indexOf(param2.rarity))
            {
                return -1;
            }
            if (this.m_sortOrder.indexOf(param1.rarity) > this.m_sortOrder.indexOf(param2.rarity))
            {
                return 1;
            }
            if (param1.name < param2.name)
            {
                return -1;
            }
            if (param1.name > param2.name)
            {
                return 1;
            }
            return 0;
        }//end

        protected void  makeBuyDonationsButton ()
        {
            int cashValue ;
            AssetIcon icon =new AssetIcon(new GardenDialog.assetDict.get( "icon_cash") );
            cashValue = Global.gameSettings().getInt("GardenDonationMysteryBasket", 3);
            this.m_buyDonationsButton = new CustomButton(ZLoc.t("Dialogs", "GardenDialog_buyDonations", {price:cashValue}), icon, "CashButtonUI");
            this.m_buyDonationsButton.setMargin(new Insets(2, 2, 5, 5));
            listener = function()void
            {
                String confirmMessage ;
                TransactionManager.sendAllTransactions(true);
                if (Global.player.cash >= cashValue)
                {
                    confirmMessage = ZLoc.t("Dialogs", "GardenDialog_buyDonationsConfirm", {price:cashValue, garden:m_garden.getItemFriendlyName()});
                    UI .displayMessage (confirmMessage ,GenericPopup .TYPE_YESNO ,void  (GenericPopupEvent event )
                {
                    e = event;
                    if (e.button == GenericPopup.ACCEPT)
                    {
                        m_buyDonationsButton.setEnabled(false);
                        m_closedEnabled = false;
                        UI.freezeScreen(true);
                        m_garden .purchaseRandomFlower (cashValue ,void  ()
                    {
                        UI.thawScreen();
                        m_closedEnabled = true;
                        close();
                        return;
                    }//end
                    );
                    }
                    return;
                }//end
                , "GardenDialog_buyDonationsConfirm", true);
                }
                else
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                }
                return;
            }//end
            ;
            this.m_buyDonationsButton.addActionListener(listener);
            return;
        }//end

        public void  addTooltipTarget (DisplayObject param1 ,String param2 )
        {
            this.m_tooltipData.put(param1,  param2);
            param1.addEventListener(MouseEvent.MOUSE_OVER, this.onTooltipTargetMouseOver, false, 0, true);
            param1.addEventListener(MouseEvent.MOUSE_OUT, this.onTooltipTargetMouseOut, false, 0, true);
            return;
        }//end

        public void  removeTooltipTarget (DisplayObject param1 )
        {
            param1.removeEventListener(MouseEvent.MOUSE_OVER, this.onTooltipTargetMouseOver);
            param1.removeEventListener(MouseEvent.MOUSE_OUT, this.onTooltipTargetMouseOut);
            delete this.m_tooltipData.get(param1);
            return;
        }//end

        private void  onTooltipTargetMouseOver (MouseEvent event )
        {
            this.m_toolTip.changeInfo(event.currentTarget as GardenCell, this.m_tooltipData.get(event.currentTarget));
            if (!this.m_toolTip.visible)
            {
                this.m_toolTip.visible = true;
            }
            return;
        }//end

        private void  onTooltipTargetMouseOut (MouseEvent event )
        {
            this.m_toolTip.visible = false;
            return;
        }//end

        public Point  getGuideArrowLocation ()
        {
            Item _loc_2 =null ;
            Object _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            Item _loc_6 =null ;
            int _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            Point _loc_1 =new Point(0,0);
            if (this.m_gardenScrollingList && this.m_garden && this.m_gardenScrollingList.parent)
            {
                _loc_2 = this.m_garden.getItem();
                _loc_3 = GardenManager.instance.getFlowers(_loc_2.gardenType);
                _loc_4 = Global.gameSettings().getItemsByKeywords(.get(this.m_garden.getMechanicConfig("slots").params.get("restrictByKeywords")));
                _loc_4.sort(this.sortFlowerConfigs);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_5 = _loc_3.get(i0);

                    if (int(_loc_3.get(_loc_5)) > 0)
                    {
                        _loc_6 = Global.gameSettings().getItemByName(_loc_5);
                        _loc_7 = _loc_4.indexOf(_loc_6);
                        if (_loc_7 != -1)
                        {
                            _loc_8 = this.m_gardenScrollingList.getItemWidth() * _loc_7 + this.m_gardenScrollingList.getItemWidth() - 10;
                            _loc_9 = this.m_gardenScrollingList.getY() + this.m_gardenScrollingList.getItemHeight() / 2;
                            _loc_1 = this.m_gardenScrollingList.parent.localToGlobal(new Point(_loc_8, _loc_9));
                            break;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        private void  onBackdropClose (Event event )
        {
            this.close();
            return;
        }//end

         public void  close ()
        {
            if (this.m_closedEnabled)
            {
                super.close();
            }
            return;
        }//end

    }



