package Modules.zoo.ui;

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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.zoo.*;
import Modules.zoo.events.*;
import Transactions.*;

//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.util.*;

    public class ZooDialogView extends GenericDialogView
    {
        private Array m_enclosureData ;
        private int m_currentSelectedTabIndex ;
        private ZooEnclosure m_currentSelectedEnclosure ;
        private ISlotMechanic m_currSlotMechanic ;
        private IStorageMechanic m_currStorageMechanic ;
        private Item m_selectedEnclosureDefinition ;
        private Dictionary m_tooltipData ;
        protected Vector<JPanel> m_tabs;
        protected boolean m_closedEnabled =true ;
        private EnclosureBackdrop m_currentBackdrop ;
        private JPanel m_mainContentPanel ;
        private AssetPane m_backdropAssetPane ;
        protected JPanel m_bottomPanel ;
        protected JPanel m_marketPanel ;
        protected ZooScrollingList m_zooMarket ;
        protected CustomButton m_seeZooButton ;
        protected CustomButton m_buyDonationsButton ;
        private MechanicMapResource m_spawner ;
        protected ZooToolTip m_toolTip ;
        protected ItemCategoryToolTip m_tabToolTip ;
        public String lastDefaultKey ;
        protected JPanel m_buttonPanel ;
        protected Array m_enclosureNamesArray ;
        private static ZooDialogView m_currentInstance ;
        public static  int WINDOW_WIDTH =749;
        public static  int WINDOW_HEIGHT =545;
        public static  int BACKDROP_PANE_HEIGHT =290;
        public static  int TAB_WIDTH =50;
        public static  int TAB_HEIGHT =50;
        public static  int TAB_CAP_FONT_SIZE =22;
        public static  int TAB_FONT_SIZE =16;

        public  ZooDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,ZooEnclosure param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null ,String param11 ="")
        {
            m_currentInstance = this;
            this.m_tooltipData = new Dictionary();
            this.m_currentSelectedEnclosure = param6;
            this.m_spawner = param1.get("spawner");
            this.m_enclosureNamesArray = .get(ZLoc.t("Dialogs", "ZooDialog_savannah"), ZLoc.t("Dialogs", "ZooDialog_jungle"));
            super(param1, param2, param3, param4, param5, param7, param8, param9, param10, param11);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

        public ISlotMechanic  currSlotMechanic ()
        {
            return this.m_currSlotMechanic;
        }//end

        public IStorageMechanic  currStorageMechanic ()
        {
            return this.m_currStorageMechanic;
        }//end

        public ZooEnclosure  currZooEnclosure ()
        {
            return this.m_currentSelectedEnclosure;
        }//end

         protected void  init ()
        {
            this.m_enclosureData = Global.gameSettings().getItemsByType("zooEnclosure");
            this.m_currSlotMechanic =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_currentSelectedEnclosure, ZooManager.MECHANIC_SLOTS, MechanicManager.ALL);
            this.m_currStorageMechanic =(IStorageMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_currentSelectedEnclosure, ZooManager.MECHANIC_STORAGE, MechanicManager.ALL);
            this.m_currSlotMechanic.sendTransactions = false;
            this.m_currStorageMechanic.sendTransactions = false;
            this.m_tabs = new Vector<JPanel>();
            super.init();
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = createCloseButtonPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            return _loc_1;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 20, 0));
                this.setBackgroundDecorator(_loc_1);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            int _loc_4 =0;
            setBackgroundDecorator(new AssetBackground(m_bgAsset));
            setPreferredSize(new IntDimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            this.m_backdropAssetPane = new AssetPane();
            this.m_backdropAssetPane.setPreferredSize(new IntDimension(WINDOW_WIDTH, BACKDROP_PANE_HEIGHT));
            this.m_mainContentPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_mainContentPanel.setPreferredSize(new IntDimension(WINDOW_WIDTH, BACKDROP_PANE_HEIGHT));
            this.m_mainContentPanel.append(this.m_backdropAssetPane);
            this.m_marketPanel = ASwingHelper.makeFlowJPanel(SoftBoxLayout.LEFT);
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(1,-3);
            _loc_1.appendAll(this.makeTabsPanel(), this.m_marketPanel);
            this.m_bottomPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            this.m_bottomPanel.appendAll(_loc_1, ASwingHelper.horizontalStrut(10));
            this.m_toolTip = new ZooToolTip();
            this.m_toolTip.visible = false;
            this.m_tabToolTip = new ItemCategoryToolTip();
            this.m_tabToolTip.visible = false;
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_2.setPreferredHeight(BACKDROP_PANE_HEIGHT);
            _loc_2.appendAll(this.m_mainContentPanel, ASwingHelper.verticalStrut(-BACKDROP_PANE_HEIGHT), this.createHeaderPanel());
            this.m_buttonPanel = this.makeButtonPanel();
            appendAll(_loc_2, this.m_bottomPanel, ASwingHelper.verticalStrut(-15), this.m_buttonPanel);
            addChild(this.m_toolTip);
            addChild(this.m_tabToolTip);
            int _loc_3 =0;
            if (this.m_spawner)
            {
                _loc_4 = 0;
                while (_loc_4 < this.m_enclosureData.length())
                {

                    if ((this.m_enclosureData.get(_loc_4) as Item).name == this.m_spawner.getItem().name)
                    {
                        _loc_3 = _loc_4;
                        break;
                    }
                    _loc_4++;
                }
            }
            this.setSelectedTab(_loc_3);
            return;
        }//end

        private void  makeAndUpdateMarketPanel ()
        {
            Item _loc_2 =null ;
            MechanicConfigData _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            this.m_marketPanel.removeAll();
            Array _loc_1 =new Array();
            if (this.m_currStorageMechanic)
            {
                _loc_1 = Global.gameSettings().getItemsByKeywords(this.m_currStorageMechanic.restrictedKeywords);
            }
            else
            {
                _loc_2 =(Item) this.m_enclosureData.get(this.m_currentSelectedTabIndex);
                _loc_3 = _loc_2.getGameEventMechanicConfig(MechanicManager.ALL, "storage");
                if (_loc_3)
                {
                    _loc_4 =(String) _loc_3.params.get("restrictByKeywords");
                    _loc_1 = Global.gameSettings().getItemsByKeywords(_loc_4.split(","));
                }
            }
            if (this.m_zooMarket == null)
            {
                this.m_zooMarket = new ZooScrollingList(_loc_1, ZooCellFactory, 0, 6, 1);
                this.m_zooMarket.addEventListener(ZooDialogEvent.ADD_ANIMAL_TO_DISPLAY, this.onAnimalCellPlaceButtonClicked, false, 0, true);
                this.m_zooMarket.addEventListener(ZooDialogEvent.BUY_NEW_ANIMAL, this.onAnimalBought, false, 0, true);
            }
            else
            {
                _loc_5 = Math.min(6, _loc_1.length());
                this.m_zooMarket.model.removeRange(0, (this.m_zooMarket.model.getSize() - 1));
                _loc_6 = 0;
                while (_loc_6 < _loc_5)
                {

                    this.m_zooMarket.model.append(_loc_1.get(_loc_6));
                    _loc_6++;
                }
            }
            ASwingHelper.setEasyBorder(this.m_zooMarket, 0, 0, 10, 0);
            this.m_marketPanel.append(this.m_zooMarket);
            ASwingHelper.prepare(this);
            return;
        }//end

        private JPanel  makeTabsPanel ()
        {
            Item _loc_5 =null ;
            ImageTab _loc_6 =null ;
            boolean _loc_7 =false ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,4);
            int _loc_2 =0;
            while (_loc_2 < this.m_enclosureData.length())
            {

                _loc_5 =(Item) this.m_enclosureData.get(_loc_2);
                _loc_6 = new ImageTab(_loc_5.getImageByName("tabs_off"), _loc_5.getImageByName("tabs_on"));
                _loc_6.addEventListener(MouseEvent.MOUSE_UP, this.onTabClicked, false, 0, true);
                Global.ui.addEventListener(ZooDialogEvent.UNLOCK_ENCLOSURE, this.onUnlockEnclosure);
                this.addTooltipTarget(_loc_6, ZLoc.t("Dialogs", "ZooDialog_tabTooltip_" + _loc_5.name));
                _loc_7 = BuyLogic.isLocked(_loc_5);
                _loc_6.setLocked(_loc_7);
                _loc_1.append(_loc_6);
                this.m_tabs.push(_loc_6);
                _loc_2++;
            }
            ImageTab _loc_3 =new ImageTab ();
            DisplayObject _loc_4 =(DisplayObject)new ZooDialog.assetDict.get( "tab_unselected");
            (new ZooDialog.assetDict.get("tab_unselected")).width = TAB_WIDTH;
            _loc_4.height = TAB_HEIGHT;
            _loc_3.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
            _loc_3.setBackgroundDecorator(new AssetBackground(_loc_4));
            this.addTooltipTarget(_loc_3, ZLoc.t("Dialogs", "ZooDialog_comingSoon"));
            _loc_1.append(_loc_3);
            this.m_tabs.push(_loc_3);
            ASwingHelper.setEasyBorder(_loc_1, 0, 21, 0, 8);
            return _loc_1;
        }//end

        private JPanel  makeButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_seeZooButton = new CustomButton(ZLoc.t("Dialogs", "ZooDialog_seeMyZoo"), null, "GreenButtonUI");
            this.m_seeZooButton.addActionListener(this.onSeeMyZoo);
            _loc_1.append(this.m_seeZooButton);
            _loc_1.append(ASwingHelper.horizontalStrut(10));
            this.makeBuyDonationsButton();
            _loc_1.append(this.m_buyDonationsButton);
            return _loc_1;
        }//end

        protected void  makeBuyDonationsButton ()
        {
            AssetIcon icon ;
            int cashValue ;
            int inventoryLimit ;
            Array data ;
            Object item ;
            Function listener ;
            if (this.m_currentSelectedEnclosure != null)
            {
                icon = new AssetIcon(new ZooDialog.assetDict.get("icon_cash"));
                cashValue = Global.gameSettings().getInt("ZooDonationNPCPrice_" + this.m_currentSelectedEnclosure.getItemName(), 20);
                inventoryLimit;
                data = Global.gameSettings().getItemsByKeywords(this.m_currStorageMechanic.restrictedKeywords);
                int _loc_2 =0;
                _loc_3 = data;
                for(int i0 = 0; i0 < data.size(); i0++)
                {
                		item = data.get(i0);


                    inventoryLimit = inventoryLimit + item.inventoryLimit;
                }
                this.m_buyDonationsButton = new CustomButton(ZLoc.t("Dialogs", "ZooDialog_buyDonations", {price:cashValue}), icon, "CashButtonUI");
                if (this.m_currentSelectedEnclosure.getAnimals() >= inventoryLimit)
                {
                    this.m_buyDonationsButton.setEnabled(false);
                }
                listener =void  ()
            {
                String _loc_1 =null ;
                TransactionManager.sendAllTransactions(true);
                if (Global.player.cash >= cashValue)
                {
                    _loc_1 = ZLoc.t("Dialogs", "ZooDialog_buyDonationsConfirm", {price:cashValue, enclosure:m_currentSelectedEnclosure.getItemFriendlyName()});
                    UI.displayMessage(_loc_1, GenericPopup.TYPE_YESNO, onBuyDonation, "ZooDialog_buyDonationsConfirm", true);
                }
                else
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                }
                return;
            }//end
            ;
                this.m_buyDonationsButton.addActionListener(listener);
            }
            else if (this.m_buyDonationsButton != null)
            {
                this.m_buyDonationsButton.setEnabled(false);
            }
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

        public void  setSelectedTab (int param1 ,boolean param2 =false )
        {
            DisplayObject _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_tabs.length())
            {

                _loc_5 = (_loc_3 == param1 ? (new ZooDialog.assetDict.get("tab_selected")) : (new ZooDialog.assetDict.get("tab_unselected")));
                _loc_5.width = TAB_WIDTH;
                _loc_5.height = TAB_HEIGHT;
                this.m_tabs.get(_loc_3).setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                this.m_tabs.get(_loc_3).setBackgroundDecorator(new AssetBackground(_loc_5));
                _loc_3 = _loc_3 + 1;
            }
            this.m_selectedEnclosureDefinition =(Item) this.m_enclosureData.get(param1);
            if (this.m_currentBackdrop)
            {
                this.m_currentBackdrop.removeEventListener(ZooDialogEvent.DISPLAY_UPDATE, this.onBackdropDisplayUpdate);
                this.m_currentBackdrop.removeEventListener(ZooDialogEvent.REMOVE_ANIMAL_FROM_DISPLAY, this.onBackdropRemoveAnimalFromDisplay);
                this.m_currentBackdrop.removeEventListener(Event.CLOSE, this.onBackdropClose);
            }
            this.m_currentBackdrop = new EnclosureBackdrop(this.m_selectedEnclosureDefinition.name);
            this.m_currentBackdrop.addEventListener(ZooDialogEvent.DISPLAY_UPDATE, this.onBackdropDisplayUpdate);
            this.m_currentBackdrop.addEventListener(ZooDialogEvent.REMOVE_ANIMAL_FROM_DISPLAY, this.onBackdropRemoveAnimalFromDisplay);
            this.m_currentBackdrop.addEventListener(Event.CLOSE, this.onBackdropClose);
            this.m_backdropAssetPane.setAsset(this.m_currentBackdrop);
            this.m_currentSelectedEnclosure = null;
            this.m_currSlotMechanic = null;
            this.m_currStorageMechanic = null;
            _loc_4 =Global.world.getObjectsByNames(.get(this.m_selectedEnclosureDefinition.name) );
            if (Global.world.getObjectsByNames(.get(this.m_selectedEnclosureDefinition.name)).length > 0)
            {
                this.m_currentSelectedEnclosure =(ZooEnclosure) _loc_4.get(0);
                this.m_currSlotMechanic =(ISlotMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_currentSelectedEnclosure, ZooManager.MECHANIC_SLOTS, MechanicManager.ALL);
                this.m_currStorageMechanic =(IStorageMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_currentSelectedEnclosure, ZooManager.MECHANIC_STORAGE, MechanicManager.ALL);
                this.m_currSlotMechanic.sendTransactions = false;
                this.m_currStorageMechanic.sendTransactions = false;
                _loc_6 = 0;
                while (_loc_6 < this.m_currSlotMechanic.numSlots)
                {

                    _loc_7 = this.m_currSlotMechanic.getSlot(_loc_6);
                    if (_loc_7)
                    {
                        this.m_currentBackdrop.addAnimalToSlot(_loc_7, _loc_6);
                    }
                    _loc_6++;
                }
            }
            remove(this.m_buttonPanel);
            this.m_buttonPanel = null;
            this.m_buttonPanel = this.makeButtonPanel();
            append(this.m_buttonPanel);
            this.m_seeZooButton.setEnabled(this.m_currentSelectedEnclosure != null);
            this.updateDefaultMessage();
            this.m_currentSelectedTabIndex = param1;
            this.makeAndUpdateMarketPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        public boolean  addAnimalToDisplay (String param1 )
        {
            TMechanicDataTransfer _loc_3 =null ;
            _loc_2 = this.currSlotMechanic.fillNextSlot(param1 );
            if (_loc_2 == -1)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "ZooFull_message"), 0, null, "", true);
                return false;
            }
            this.m_currStorageMechanic.remove(param1, 1);
            this.m_currentBackdrop.addAnimalToSlot(param1, _loc_2);
            _loc_3 = new TMechanicDataTransfer(this.m_currentSelectedEnclosure);
            _loc_3.addDataTransfer(ZooManager.MECHANIC_STORAGE, ZooManager.MECHANIC_SLOTS, param1, {slot:_loc_2});
            GameTransactionManager.addTransaction(_loc_3);
            if (!Global.player.getSeenFlag("hasPlacedAnimalsInDisplay"))
            {
                Global.player.setSeenFlag("hasPlacedAnimalsInDisplay");
            }
            this.updateDefaultMessage();
            return true;
        }//end

        private void  updateDefaultMessage ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            int _loc_3 =0;
            ZooEnclosure _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            boolean _loc_10 =false ;
            Array _loc_11 =null ;
            ConstructionSite _loc_12 =null ;
            if (this.m_currentBackdrop && this.m_selectedEnclosureDefinition)
            {
                _loc_1 = "";
                _loc_2 = null;
                _loc_3 = 0;
                _loc_4 = null;
                if (this.m_currentSelectedEnclosure)
                {
                    if (!Global.player.getSeenFlag("hasPlacedAnimalsInDisplay"))
                    {
                        if (this.m_currentSelectedEnclosure.getItem().name == "enclosure_jungle")
                        {
                            _loc_1 = ZLoc.t("Dialogs", "ZooDialog_firstAnimalPlace");
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Dialogs", "ZooDialog_firstAnimalPlaceArctic");
                        }
                    }
                    else
                    {
                        _loc_5 = "ZooDialog_defaultMsg_" + this.m_selectedEnclosureDefinition.name + "_" + Math.floor(Math.random() * 2.99);
                        this.lastDefaultKey = _loc_5;
                        _loc_6 = this.m_currentSelectedEnclosure.getPayout();
                        _loc_1 = ZLoc.t("Dialogs", _loc_5, {percent:_loc_6});
                        if (Math.random() < 0.3)
                        {
                            _loc_7 = this.m_selectedEnclosureDefinition.unlocksItem;
                            if (_loc_7)
                            {
                                _loc_8 = BuyLogic.isLocked(Global.gameSettings().getItemByName(_loc_7));
                                this.lastDefaultKey = "ZooDialog_defaultMsg_" + this.m_selectedEnclosureDefinition.name + "_" + "unlock";
                                _loc_1 = ZLoc.t("Dialogs", "ZooDialog_defaultMsg_" + this.m_selectedEnclosureDefinition.name + "_" + "unlock");
                            }
                        }
                    }
                    _loc_4 = this.m_currentSelectedEnclosure;
                }
                else
                {
                    _loc_9 = Global.player.inventory.getItemCountByName(this.m_selectedEnclosureDefinition.name) > 0;
                    _loc_10 = false;
                    _loc_11 = Global.world.getObjectsByClass(ConstructionSite);
                    for(int i0 = 0; i0 < _loc_11.size(); i0++)
                    {
                    		_loc_12 = _loc_11.get(i0);

                        if (_loc_12.targetName == this.m_selectedEnclosureDefinition.name)
                        {
                            _loc_10 = true;
                            break;
                        }
                    }
                    if (_loc_9)
                    {
                        _loc_1 = ZLoc.t("Dialogs", "ZooDialog_placeMessage_" + this.m_selectedEnclosureDefinition.name);
                        _loc_2 = this.m_selectedEnclosureDefinition.name;
                    }
                    else if (_loc_10)
                    {
                        _loc_1 = ZLoc.t("Dialogs", "ZooDialog_underConstruction_" + this.m_selectedEnclosureDefinition.name);
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Dialogs", "ZooDialog_locked_" + this.m_selectedEnclosureDefinition.name);
                    }
                }
                this.m_currentBackdrop.speechBubble.setDefaultMessage(_loc_1, _loc_2, _loc_4);
            }
            return;
        }//end

        private void  onAnimalCellPlaceButtonClicked (ZooDialogEvent event )
        {
            ZooCell _loc_3 =null ;
            _loc_2 = this.addAnimalToDisplay(event.animalItemName );
            if (_loc_2)
            {
                _loc_3 =(ZooCell) event.target;
                _loc_3.changeCounter((_loc_3.count - 1));
            }
            return;
        }//end

        private void  onBackdropClose (Event event )
        {
            this.close();
            return;
        }//end

        private void  onAnimalBought (ZooDialogEvent event )
        {
            ZooCell _loc_4 =null ;
            StatsManager.sample(100, "Zoo", "onAnimalBought", this.lastDefaultKey);
            _loc_2 =Global.gameSettings().getItemByName(event.animalItemName );
            _loc_3 = this.m_currentSelectedEnclosure.getNumSpecificAnimal(event.animalItemName );
            if (_loc_3 < _loc_2.inventoryLimit)
            {
                this.m_currStorageMechanic.sendTransactions = true;
                this.m_currStorageMechanic.purchase(event.animalItemName, 1, "zoo|adopted_animal|" + event.animalItemName);
                this.m_currStorageMechanic.sendTransactions = false;
                _loc_4 =(ZooCell) event.target;
                (event.target as ZooCell).changeCounter((_loc_4.count + 1));
                _loc_4.changeChrome();
                if (this.m_currentSelectedEnclosure.readyToUnlock())
                {
                    MechanicManager.getInstance().handleAction(this.m_currentSelectedEnclosure as IMechanicUser, "GMPlay");
                }
                this.updateDefaultMessage();
            }
            remove(this.m_buttonPanel);
            this.m_buttonPanel = null;
            this.m_buttonPanel = this.makeButtonPanel();
            append(this.m_buttonPanel);
            return;
        }//end

        public void  onAnimalRolled (Object param1 )
        {
            this.m_closedEnabled = true;
            this.m_currStorageMechanic.sendTransactions = false;
            this.m_currStorageMechanic.add(param1.loot, 1);
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("-1",  param1.loot);
            this.m_currentSelectedEnclosure.setDataForMechanic("giftSenders", _loc_2, "all");
            this.close();
            MechanicManager.getInstance().handleAction(this.m_currentSelectedEnclosure, "GMPlay");
            return;
        }//end

        private void  onSeeMyZoo (AWEvent event )
        {
            if (this.m_currentSelectedEnclosure)
            {
                Global.world.centerOnObject(this.m_currentSelectedEnclosure);
            }
            this.close();
            return;
        }//end

        private void  onBuyDonation (GenericPopupEvent event )
        {
            int _loc_2 =0;
            if (event.button == GenericPopup.ACCEPT)
            {
                if (this.m_currentSelectedEnclosure)
                {
                    _loc_2 = Global.gameSettings().getInt("ZooDonationNPCPrice_" + this.m_currentSelectedEnclosure.getItemName(), 20);
                    Global.player.cash = Global.player.cash - _loc_2;
                    GameTransactionManager.addTransaction(new TPurchaseRandomZooAnimal(this.m_currentSelectedEnclosure, this), true, true);
                }
                this.m_buyDonationsButton.setEnabled(false);
                this.m_closedEnabled = false;
            }
            return;
        }//end

        protected void  onTabClicked (MouseEvent event )
        {
            int _loc_2 =0;
            if (this.m_closedEnabled)
            {
                _loc_2 = 0;
                while (_loc_2 < this.m_tabs.length())
                {

                    if (this.m_tabs.get(_loc_2) == event.currentTarget)
                    {
                        this.setSelectedTab(_loc_2, true);
                        break;
                    }
                    _loc_2++;
                }
            }
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

        private void  onBackdropDisplayUpdate (ZooDialogEvent event )
        {
            this.m_backdropAssetPane.unloadAsset();
            this.m_backdropAssetPane.setAsset(this.m_currentBackdrop);
            setBackgroundDecorator(new AssetBackground(this.m_currentBackdrop.background));
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  onBackdropRemoveAnimalFromDisplay (ZooDialogEvent event )
        {
            TMechanicDataTransfer _loc_2 =null ;
            ArrayList _loc_3 =null ;
            int _loc_4 =0;
            ZooCell _loc_5 =null ;
            if (this.m_currSlotMechanic && this.m_currStorageMechanic)
            {
                this.m_currSlotMechanic.emptySlot(event.slotIndex);
                this.m_currStorageMechanic.add(event.animalItemName, 1);
                _loc_2 = new TMechanicDataTransfer(this.m_currentSelectedEnclosure);
                _loc_2.addDataTransfer(ZooManager.MECHANIC_SLOTS, ZooManager.MECHANIC_STORAGE, event.animalItemName, {slot:event.slotIndex});
                GameTransactionManager.addTransaction(_loc_2);
                _loc_3 = this.m_zooMarket.dataList.getCells();
                _loc_4 = 0;
                while (_loc_4 < _loc_3.size())
                {

                    _loc_5 =(ZooCell) _loc_3.elementAt(_loc_4);
                    if (_loc_5.animalName == event.animalItemName)
                    {
                        _loc_5.changeCounter(this.m_currStorageMechanic.getCount(event.animalItemName));
                        break;
                    }
                    _loc_4++;
                }
            }
            return;
        }//end

        private void  onTooltipTargetMouseOver (MouseEvent event )
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            Item _loc_4 =null ;
            if (event.currentTarget instanceof ZooCell)
            {
                this.m_toolTip.changeInfo(event.currentTarget as ZooCell);
                if (!this.m_toolTip.visible)
                {
                    this.m_toolTip.visible = true;
                }
            }
            else if (event.currentTarget instanceof ImageTab)
            {
                this.m_tabToolTip.visible = true;
                _loc_2 = this.m_tabs.indexOf(event.currentTarget);
                this.m_tabToolTip.changeTitle(this.m_tooltipData.get(this.m_tabs.get(_loc_2)));
                _loc_3 = "";
                _loc_4 =(Item) this.m_enclosureData.get(_loc_2);
                if (_loc_4)
                {
                    if (BuyLogic.isLocked(_loc_4))
                    {
                        _loc_3 = ZLoc.t("Dialogs", "ZooDialog_tabLocked_rollover_" + _loc_4.name);
                    }
                }
                this.m_tabToolTip.changeSubtitle(_loc_3);
                this.m_tabToolTip.x = this.m_tabs.get(_loc_2).x + 2 + (150 - this.m_tabToolTip.width / 2);
                this.m_tabToolTip.y = this.m_tabs.get(_loc_2).y + 240;
            }
            return;
        }//end

        private void  onTooltipTargetMouseOut (MouseEvent event )
        {
            if (event.currentTarget instanceof ZooCell)
            {
                this.m_toolTip.visible = false;
            }
            else if (event.currentTarget instanceof ImageTab)
            {
                this.m_tabToolTip.visible = false;
            }
            return;
        }//end

        private void  onUnlockEnclosure (ZooDialogEvent event )
        {
            Item _loc_3 =null ;
            ImageTab _loc_4 =null ;
            boolean _loc_5 =false ;
            int _loc_2 =0;
            while (_loc_2 < this.m_enclosureData.length())
            {

                _loc_3 = this.m_enclosureData.get(_loc_2);
                if (_loc_3.name == event.enclosureName)
                {
                    _loc_4 =(ImageTab) this.m_tabs.get(_loc_2);
                    if (_loc_4)
                    {
                        _loc_5 = BuyLogic.isLocked(_loc_3);
                        _loc_4.setLocked(_loc_5);
                    }
                }
                _loc_2++;
            }
            return;
        }//end

        public static ZooDialogView  currentInstance ()
        {
            return m_currentInstance;
        }//end

    }

import flash.display.*;
import flash.events.*;
import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.util.*;
import Modules.zoo.ui.*;
import Engine.Managers.LoadingManager;

class ImageTab extends JPanel
    private String m_outUrl ;
    private String m_overUrl ;
    private DisplayObject m_outAsset ;
    private DisplayObject m_overAsset ;
    private AssetPane m_assetPane ;
    private boolean m_isLocked ;
    public static  int TAB_WIDTH =50;
    public static  int TAB_HEIGHT =50;

     ImageTab (String param1 ,String param2 )
    {
        super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, 1));
        setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
        this.m_assetPane = new AssetPane();
        append(this.m_assetPane);
        addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
        addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
        this.m_outUrl = param1;
        this.m_overUrl = param2;
        if (!this.m_outUrl || !this.m_overUrl)
        {
            this.m_outAsset =(DisplayObject) new ZooDialog.assetDict.get("tab_locked");
            this.m_overAsset =(DisplayObject) new ZooDialog.assetDict.get("tab_locked");
            this.setTabAsset(this.m_outAsset);
        }
        else
        {
            LoadingManager.loadFromUrl(this.m_overUrl, {priority:LoadingManager.PRIORITY_HIGH, completeCallback:this.onOverUrlLoad});
            LoadingManager.loadFromUrl(this.m_outUrl, {priority:LoadingManager.PRIORITY_HIGH, completeCallback:this.onOutUrlLoad});
        }
        return;
    }//end

    public void  setLocked (boolean param1 )
    {
        if (param1 !=null)
        {
            this.setTabAsset(new ZooDialog.assetDict.get("tab_locked"));
            this.m_isLocked = true;
        }
        else
        {
            this.m_isLocked = false;
            if (this.m_outAsset)
            {
                this.setTabAsset(this.m_outAsset);
            }
        }
        return;
    }//end

    private void  setTabAsset (DisplayObject param1 )
    {
        if (!this.m_isLocked && param1)
        {
            this.m_assetPane.unloadAsset();
            this.m_assetPane.setAsset(param1);
        }
        return;
    }//end

    private void  onMouseOver (MouseEvent event )
    {
        this.setTabAsset(this.m_overAsset);
        return;
    }//end

    private void  onMouseOut (MouseEvent event )
    {
        this.setTabAsset(this.m_outAsset);
        return;
    }//end

    private void  onOverUrlLoad (Event event )
    {
        _loc_2 =(LoaderInfo) event.currentTarget;
        this.m_overAsset =(DisplayObject) event.target.content;
        return;
    }//end

    private void  onOutUrlLoad (Event event )
    {
        _loc_2 =(LoaderInfo) event.currentTarget;
        this.m_outAsset =(DisplayObject) event.target.content;
        this.setTabAsset(this.m_outAsset);
        return;
    }//end





