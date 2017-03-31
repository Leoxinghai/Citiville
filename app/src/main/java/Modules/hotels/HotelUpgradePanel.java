package Modules.hotels;

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
import Classes.gates.*;
import Classes.sim.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.zynga.*;

    public class HotelUpgradePanel extends JPanel
    {
        private Dictionary m_assetDict ;
        private MechanicMapResource m_owner ;
        private int m_width ;
        private CompositeGate m_compositeGate ;
        private Array m_gateKeys ;
        private Array m_gates ;
        private IUpgradeMechanic m_upgradeMechanic ;
        private Function m_closer ;
        protected IBaseLocKey m_view ;
        public static  String GATE_NAME ="pre_upgrade";
        public static  String BUILD_GATE ="upgrade_buildable";

        public  HotelUpgradePanel (Dictionary param1 ,int param2 ,Function param3 ,IBaseLocKey param4 )
        {
            this.m_gateKeys = new Array();
            this.m_gates = new Array();
            super(new SoftBoxLayout());
            this.m_assetDict = param1;
            this.m_width = param2;
            this.m_owner = this.m_assetDict.get("spawner");
            this.m_closer = param3;
            this.m_view = param4;
            this.updateUpgradeMechanic();
            this.initializeUpgradePanel();
            return;
        }//end

        protected void  updateUpgradeMechanic ()
        {
            GateFactory _loc_1 =null ;
            int _loc_2 =0;
            this.m_upgradeMechanic =(IUpgradeMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_owner, "upgrade", "all");
            if (this.m_upgradeMechanic != null)
            {
                this.m_upgradeMechanic.initialize(this.m_owner, this.m_owner.getMechanicConfig("upgrade", "all"));
                Global.ui.addEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.initializeUpgradePanel);
                _loc_1 = new GateFactory();
                this.m_compositeGate =(CompositeGate) _loc_1.loadGateFromXML(this.m_owner.getItem(), this.m_owner, GATE_NAME, this.m_upgradeMechanic.performUpgrade);
                this.m_gateKeys = this.m_compositeGate.getKeyArray();
                this.m_gates = new Array();
                _loc_2 = 0;
                while (_loc_2 < this.m_gateKeys.length())
                {

                    this.m_gates.push(_loc_1.loadGateFromXML(this.m_owner.getItem(), this.m_owner, this.m_gateKeys.get(_loc_2), null));
                    _loc_2++;
                }
            }
            return;
        }//end

        protected void  initializeUpgradePanel (GenericObjectEvent event =null )
        {
            JPanel _loc_2 =null ;
            if (event != null)
            {
                this.removeAll();
                this.updateUpgradeMechanic();
            }
            if (this.m_upgradeMechanic != null)
            {
                _loc_2 = this.makeUpgradePanel();
                this.append(_loc_2);
            }
            return;
        }//end

        private JPanel  makeUpgradePanel ()
        {
            AssetPane upgradeImages ;
            upgradePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            upgradePanel.setPreferredWidth(this.m_width);
            int i ;
            while (i < this.m_gates.length())
            {

                upgradePanel.append(this.buildTask(i));
                i = (i + 1);
            }
            upgradeImages = new AssetPane();
            LoadingManager .load (Global .getAssetURL (this .m_compositeGate .iconURL ),void  (Event event )
            {
                upgradeImages.setAsset(event.target.content);
                return;
            }//end
            );
            ASwingHelper.setEasyBorder(upgradeImages, 5, 20, 5, 20);
            upgradePanel.append(this.createUpgrade(upgradeImages, ZLoc.t("Dialogs", this.m_view.getLocKeyBaseDialog("upgrade")), ZLoc.t("Dialogs", this.m_compositeGate.locKey)));
            ASwingHelper.setEasyBorder(upgradePanel, 12, 10, 10, 10);
            return upgradePanel;
        }//end

        private JPanel  buildTask (int param1 )
        {
            AssetPane icon ;
            String questText ;
            CustomButton button ;
            AssetPane checkMark ;
            gateKey = param1;
            gate = this.m_gates.get(gateKey) ;
            keys = gate.getKeyArray();
            icon = new AssetPane();
            LoadingManager .load (Global .getAssetURL (gate .iconURL ),void  (Event event )
            {
                icon.setAsset(event.target.content);
                return;
            }//end
            );
            panel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM,5);
            ASwingHelper.setEasyBorder(panel, 0, 0, 0, 15);
            int progress ;
            int total ;
            int i ;
            while (i < keys.length())
            {

                progress = progress + gate.keyProgress(keys.get(i));
                total = total + gate.getKey(keys.get(i));
                i = (i + 1);
            }
            if (gate.name == BUILD_GATE && progress < total)
            {
                button = new CustomButton(ZLoc.t("Dialogs", this.m_view.getLocKeyBaseDialog("askFriends")), null, "GreenSmallButtonUI");
                button.addActionListener(this.openPartsMenu);
                button.name = gateKey.toString();
                ASwingHelper.setEasyBorder(button, 0, 0, 10);
                panel.append(button);
                questText = ZLoc.t("Dialogs", gate.locKey);
            }
            else
            {
                questText = ZLoc.t("Dialogs", gate.locKey, {amount:total});
            }
            if (progress == total)
            {
                checkMark = new AssetPane(new this.m_assetDict.get("payroll_checkin_confirmCheckmark"));
                ASwingHelper.setEasyBorder(checkMark, 0, 0, 10);
                panel.append(checkMark);
            }
            return this.createTask(icon, questText, progress + "/" + total, panel);
        }//end

        private JPanel  createTask (AssetPane param1 ,String param2 ,String param3 ,JPanel param4 )
        {
            JPanel _loc_5 =new JPanel(new BorderLayout ());
            ASwingHelper.setEasyBorder(_loc_5, 0, 10, 0, 10);
            _loc_5.setPreferredHeight(90);
            _loc_6 =(DisplayObject) new this.m_assetDict.get( "hotels_goalsBackground");
            _loc_5.setBackgroundDecorator(new MarginBackground(_loc_6, new Insets(0, 0, 0, 1)));
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            JPanel _loc_8 =new JPanel(new LayeredLayout ());
            ASwingHelper.setEasyBorder(_loc_8, 0, 5, 5);
            if (param1 != null)
            {
                ASwingHelper.setEasyBorder(param1, 10, 10);
                _loc_8.append(param1);
            }
            _loc_9 = ASwingHelper.makeMultilineText(param3 ,90,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,18,EmbeddedArt.whiteTextColor );
            _loc_9.filters = .get(new GlowFilter(EmbeddedArt.blueTextColor, 1, 4, 4, 10, BitmapFilterQuality.LOW));
            ASwingHelper.setEasyBorder(_loc_9, 60);
            _loc_8.append(_loc_9);
            _loc_7.append(_loc_8);
            _loc_10 = ASwingHelper.makeMultilineText(param2 ,230,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor );
            _loc_11 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,0);
            _loc_11.append(_loc_10);
            _loc_7.append(_loc_11);
            _loc_5.append(_loc_7, BorderLayout.WEST);
            _loc_5.append(param4, BorderLayout.EAST);
            return _loc_5;
        }//end

        private JPanel  createUpgrade (AssetPane param1 ,String param2 ,String param3 )
        {
            CustomButton _loc_15 =null ;
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            ASwingHelper.setEasyBorder(_loc_4, 0, 15, 0, 15);
            _loc_4.setPreferredHeight(153);
            _loc_5 =(DisplayObject) new this.m_assetDict.get( "hotels_upgradeBackground");
            _loc_4.setBackgroundDecorator(new MarginBackground(_loc_5, new Insets(0, 0, 0, 0)));
            _loc_4.append(param1);
            JPanel _loc_6 =new JPanel(new BorderLayout ());
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT ,0);
            _loc_8 = ASwingHelper.makeMultilineText(param2 ,300,EmbeddedArt.titleFont ,TextFormatAlign.LEFT ,18,EmbeddedArt.darkBlueTextColor );
            _loc_7.append(_loc_8);
            _loc_9 = ASwingHelper.makeMultilineText(param3 ,300,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.darkBlueTextColor );
            _loc_7.append(_loc_9);
            _loc_6.append(_loc_7, BorderLayout.NORTH);
            _loc_10 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,5,0);
            AssetIcon _loc_11 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            _loc_12 = this.m_owner.getItem ();
            _loc_13 = this.m_owner.getItem ().upgrade.cashCost ;
            _loc_14 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","BusinessUpgradesUI_buy",{amount_loc_13}));
            if (this.m_compositeGate.checkForKeys())
            {
                _loc_15 = new CustomButton(ZLoc.t("Dialogs", "FinishBuilding"), null, "GreenButtonUI");
                _loc_15.addActionListener(this.upgradeHotel);
            }
            else
            {
                _loc_15 = new CustomButton(_loc_14, _loc_11, "CashButtonUI");
                _loc_15.addActionListener(this.buyUpgradeHotel);
            }
            _loc_10.append(_loc_15);
            ASwingHelper.setEasyBorder(_loc_10, 20, 20, 20, 50);
            _loc_6.append(_loc_10, BorderLayout.SOUTH);
            _loc_4.append(_loc_6);
            return _loc_4;
        }//end

        protected JPanel  makeComingSoonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,15);
            ASwingHelper.setEasyBorder(_loc_2, 15, 15, 15, 15);
            _loc_2.setPreferredHeight(460);
            _loc_2.setPreferredWidth(this.m_width);
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "hotels_upgradeBackground");
            _loc_2.setBackgroundDecorator(new MarginBackground(_loc_3, new Insets(0, 0, 0, 0)));
            _loc_4 = ZLoc.t("Dialogs","ComingSoon");
            _loc_5 = ASwingHelper.makeMultilineText(_loc_4 ,this.m_width -50,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,24,EmbeddedArt.darkBlueTextColor );
            ASwingHelper.setEasyBorder(_loc_5, 15);
            AssetPane _loc_6 =new AssetPane ();
            _loc_2.appendAll(_loc_5, _loc_6);
            _loc_1.appendAll(_loc_2);
            return _loc_1;
        }//end

        private void  upgradeHotel (AWEvent event )
        {
            Global.ui.removeEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.initializeUpgradePanel);
            this.m_compositeGate.unlockGate();
            this.m_closer();
            return;
        }//end

        private void  buyUpgradeHotel (AWEvent event )
        {
            _loc_2 = this.m_owner.getItem ().upgrade ;
            if (Global.player.canBuyCash(_loc_2.cashCost))
            {
                UI.displayMessage(ZLoc.t("Dialogs", "UpgradePurchaseConfirm", {cost:_loc_2.cashCost, newItem:this.m_owner.getItemFriendlyName()}), GenericPopup.TYPE_YESNO, this.buyUpgradeConfirm, "", true);
            }
            return;
        }//end

        private void  buyUpgradeConfirm (GenericPopupEvent event )
        {
            Item _loc_2 =null ;
            UpgradeDefinition _loc_3 =null ;
            if (event.button == GenericPopup.ACCEPT)
            {
                _loc_2 = this.m_owner.getItem();
                _loc_3 = _loc_2.upgrade;
                Global.ui.removeEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.initializeUpgradePanel);
                TransactionManager.addTransaction(new TBuyUpgradeBuildings([this.m_owner], [_loc_3.newItemName], _loc_3.cashCost));
                this.m_owner.onUpgrade(_loc_2, Global.gameSettings().getItemByName(_loc_3.newItemName), false);
                this.m_upgradeMechanic.doUpgradeEffect();
                this.m_closer();
            }
            return;
        }//end

        private void  openPartsMenu (AWEvent event )
        {
            _loc_2 =(InventoryGate) this.m_gates.get(int(event.currentTarget.name ));
            _loc_2.setDisplayProperties(false, false);
            _loc_2.displayGate(null, ZLoc.t("Dialogs", "UpgradeYourX", {itemName:this.m_owner.getItem().localizedName}));
            return;
        }//end

    }



