package Display.HunterAndPreyUI;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.bandits.*;
import Modules.bandits.transactions.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class UpgradesCell extends DataItemCell
    {
        private UpgradeDefinition m_upgrade ;
        private DisplayObject m_bg ;
        private int m_position ;
        private boolean m_dummy =false ;
        private JPanel m_headerPanel ;
        private JPanel m_rewardPanel ;
        private JPanel m_claimPanel ;
        protected boolean m_metRequirements =false ;
        protected Loader m_iconLoader ;
        protected DisplayObject m_icon ;
        protected JPanel m_iconHolder ;
        protected Loader m_rewardLoader ;
        protected DisplayObject m_rewardIcon ;
        protected JPanel m_rewardHolder ;
        protected int m_numAssetLoads =0;
        protected AssetPane m_titlePanel ;
        protected CustomButton m_upgradeButton ;
        protected int m_curLevel ;
        protected int m_requiredLevel ;
        public static  int CELL_WIDTH =147;
        public static  int CELL_HEIGHT =340;

        public  UpgradesCell (int param1 )
        {
            this.m_position = param1;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

        public int  position ()
        {
            return this.m_position;
        }//end

        public UpgradeDefinition  cellValue ()
        {
            return this.m_upgrade;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_upgrade =(UpgradeDefinition) param1;
            if (this.m_upgrade.oldItemName == null)
            {
                this.m_dummy = true;
            }
            else
            {
                this.m_curLevel = PreyUtil.getHubLevel(HunterDialog.groupId);
                this.m_requiredLevel = Global.gameSettings().getHubLevel(HunterDialog.groupId, this.m_upgrade.newItemName);
                this.m_metRequirements = this.m_upgrade.requirements.checkRequirements(null, this.m_upgrade.newItemName) || this.m_curLevel >= this.m_requiredLevel;
            }
            this.buildCell();
            this.loadAssets();
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_rewardPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 10);
            this.m_claimPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 10);
            this.buildBackground();
            if (this.m_dummy == false)
            {
                this.makeHeaderPanel();
                this.makeRewardPanel();
                this.append(this.makeClaimPanel());
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_blue");
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_disabled");
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_white");
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_blue");
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_disabled");
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_white");
            DisplayObject _loc_7 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_blue");
            DisplayObject _loc_8 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_disabled");
            DisplayObject _loc_9 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_white");
            _loc_10 = this.m_position % UpgradesPanel.UPS_PER_PAGE;
            if (this.m_position % UpgradesPanel.UPS_PER_PAGE == 1)
            {
                this.m_bg = _loc_3;
            }
            else if (_loc_10 == 2)
            {
                this.m_bg = _loc_6;
            }
            else if (_loc_10 == 3)
            {
                this.m_bg = _loc_6;
            }
            else
            {
                this.m_bg = _loc_9;
            }
            ASwingHelper.setBackground(this, this.m_bg);
            ASwingHelper.setForcedSize(this, new IntDimension(CELL_WIDTH, CELL_HEIGHT));
            return;
        }//end

        protected void  changeHeaderColor ()
        {
            _loc_1 = this.m_titlePanel.getAsset ()as TextField ;
            _loc_2 = _loc_1.getTextFormat ();
            _loc_2.color = EmbeddedArt.blueTextColor;
            _loc_1.setTextFormat(_loc_2);
            return;
        }//end

        protected void  makeHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = EmbeddedArt.blueTextColor;
            if (!this.m_metRequirements)
            {
                _loc_2 = EmbeddedArt.lightGrayTextColor;
            }
            _loc_3 = ZLoc.t("Items",this.m_upgrade.newItemName +"_upgradeName");
            this.m_titlePanel = ASwingHelper.makeMultilineCapsText(_loc_3, 130, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 13, _loc_2);
            _loc_1.append(this.m_titlePanel);
            this.m_headerPanel.append(_loc_1);
            ASwingHelper.setForcedHeight(this.m_headerPanel, 30);
            this.append(this.m_headerPanel);
            return;
        }//end

        protected void  makeRewardPanel ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "police_arrow");
            this.m_iconHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setForcedHeight(this.m_iconHolder, 85);
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3.append(_loc_2);
            this.m_rewardHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setForcedHeight(this.m_rewardHolder, 80);
            this.m_rewardPanel.appendAll(this.m_iconHolder, _loc_3, this.m_rewardHolder);
            ASwingHelper.setForcedWidth(this.m_rewardPanel, CELL_WIDTH);
            this.append(this.m_rewardPanel);
            return;
        }//end

        protected JPanel  makeClaimPanel ()
        {
            DisplayObject _loc_2 =null ;
            JPanel _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            LocalizationObjectToken _loc_6 =null ;
            String _loc_7 =null ;
            AssetPane _loc_8 =null ;
            AssetPane _loc_9 =null ;
            JPanel _loc_10 =null ;
            String _loc_11 =null ;
            JLabel _loc_12 =null ;
            String _loc_13 =null ;
            AssetIcon _loc_14 =null ;
            int _loc_15 =0;
            int _loc_16 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_curLevel = PreyUtil.getHubLevel(HunterDialog.groupId);
            this.m_requiredLevel = Global.gameSettings().getHubLevel(HunterDialog.groupId, this.m_upgrade.newItemName);
            if (this.m_curLevel >= this.m_requiredLevel)
            {
                _loc_2 =(DisplayObject) new HunterDialog.assetDict.get("police_checkmarkBig");
                _loc_1.append(new AssetPane(_loc_2));
            }
            else
            {
                _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_4 = EmbeddedArt.blueTextColor;
                if (!this.m_metRequirements)
                {
                    _loc_4 = EmbeddedArt.lightGrayTextColor;
                }
                _loc_5 = this.m_upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_SPECIAL_PREY);
                _loc_6 = ZLoc.tk("Dialogs", HunterDialog.groupId + "_preyName", "", int(_loc_5));
                _loc_7 = ZLoc.t("Dialogs", HunterDialog.groupId + "_PreyRequiredText", {prey:_loc_6});
                _loc_8 = ASwingHelper.makeMultilineCapsText(_loc_5, 130, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 13, _loc_4);
                _loc_9 = ASwingHelper.makeMultilineText(_loc_7, 130, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 10, _loc_4);
                _loc_3.appendAll(_loc_8, _loc_9);
                _loc_3.append(ASwingHelper.verticalStrut(5));
                _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_11 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "UpgradeCostLabelText"));
                _loc_12 = ASwingHelper.makeLabel(_loc_11, EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.brownTextColor);
                _loc_13 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "UpgradeButtonText"));
                if (this.m_metRequirements)
                {
                    this.m_upgradeButton = new CustomButton(_loc_13, null, "GreenButtonUI");
                    this.m_upgradeButton.setFont(ASwingHelper.getTitleFont(13));
                    this.m_upgradeButton.addEventListener(MouseEvent.CLICK, this.onUpgrade, false, 0, true);
                    this.m_upgradeButton.setEnabled(true);
                }
                else
                {
                    _loc_13 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Main", "SpendCash", {cash:this.m_upgrade.cashCost}));
                    _loc_14 = new AssetIcon(new EmbeddedArt.icon_cash());
                    this.m_upgradeButton = new CustomButton(_loc_13, _loc_14, "CashButtonUI");
                    if (Global.localizer.langCode != "en")
                    {
                        this.m_upgradeButton.setFont(new ASFont(EmbeddedArt.titleFont, 10, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
                        this.m_upgradeButton.setMargin(new Insets(-3, 0, -3, 2));
                    }
                    this.m_upgradeButton.addEventListener(MouseEvent.CLICK, this.onCashBuy, false, 0, true);
                    _loc_15 = int(Global.gameSettings().getHubLevel(HunterDialog.groupId, this.m_upgrade.oldItemName));
                    _loc_16 = PreyUtil.getHubLevel(HunterDialog.groupId);
                    if (_loc_15 != _loc_16)
                    {
                        this.m_upgradeButton.setEnabled(false);
                    }
                }
                _loc_10.setPreferredWidth(this.m_upgradeButton.getPreferredWidth());
                _loc_10.appendAll(_loc_12, this.m_upgradeButton, ASwingHelper.verticalStrut(10));
                _loc_3.append(_loc_10);
                _loc_1.append(_loc_3);
            }
            this.m_claimPanel.append(_loc_1);
            ASwingHelper.setForcedWidth(this.m_claimPanel, CELL_WIDTH);
            ASwingHelper.prepare(this.m_claimPanel);
            return this.m_claimPanel;
        }//end

        public void  enableUpgradeButton ()
        {
            this.m_upgradeButton.setEnabled(true);
            return;
        }//end

        protected void  onUpgrade (MouseEvent event )
        {
            if (PreyManager.upgradeAllHubs(HunterDialog.groupId))
            {
                GameTransactionManager.addTransaction(new TProcessHubWorkers(HunterDialog.groupId));
                this.refreshClaimPanel();
            }
            return;
        }//end

        protected void  onCashBuy (MouseEvent event )
        {
            event = event;
            if (Global.player.canBuyCash(this.m_upgrade.cashCost, true))
            {
                UI .displayMessage (ZLoc .t ("Dialogs","BuyCashUpgradeQuestion",{String amount (this .m_upgrade .cashCost )}),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (PreyManager.upgradeAllHubs(HunterDialog.groupId, m_upgrade.cashCost, m_upgrade.newItemName))
                    {
                        GameTransactionManager.addTransaction(new TProcessHubWorkers(HunterDialog.groupId));
                        PreyUtil.logDialogStats(HunterDialog.groupId, "unlock_" + m_upgrade.newItemName, "hub_ui");
                        refreshClaimPanel();
                    }
                }
                return;
            }//end
            );
            }
            return;
        }//end

        protected void  loadAssets ()
        {
            Item _loc_1 =null ;
            Object _loc_2 =null ;
            int _loc_3 =0;
            String _loc_4 =null ;
            if (this.m_dummy == false)
            {
                _loc_1 = Global.gameSettings().getItemByName(this.m_upgrade.newItemName);
                if (_loc_1 && _loc_1.icon)
                {
                    this.m_numAssetLoads++;
                    this.m_iconLoader = LoadingManager.load(_loc_1.icon, this.onIconLoad, LoadingManager.PRIORITY_HIGH);
                }
                _loc_2 = Global.gameSettings().getPreyGroupSettings(HunterDialog.groupId);
                _loc_3 = Global.gameSettings().getHubLevel(HunterDialog.groupId, this.m_upgrade.newItemName);
                if (_loc_2 && _loc_2.get("hubs").get(_loc_3).get("upgradeRewardIcon"))
                {
                    _loc_4 = String(_loc_2.get("hubs").get(_loc_3).get("upgradeRewardIcon"));
                    this.m_numAssetLoads++;
                    this.m_rewardLoader = LoadingManager.load(Global.getAssetURL(_loc_4), this.onIconLoad, LoadingManager.PRIORITY_HIGH);
                }
            }
            return;
        }//end

         protected void  onIconLoad (Event event )
        {
            if (this.m_numAssetLoads > 1)
            {
                this.m_numAssetLoads--;
                return;
            }
            if (this.m_iconLoader && this.m_iconLoader.content)
            {
                this.m_icon = this.m_iconLoader.content;
            }
            if (this.m_rewardLoader && this.m_rewardLoader.content)
            {
                this.m_rewardIcon = this.m_rewardLoader.content;
            }
            this.placeIcons();
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

        protected void  placeIcons ()
        {
            if (this.m_icon)
            {
                this.m_iconHolder.append(new AssetPane(this.m_icon));
            }
            if (this.m_rewardIcon)
            {
                this.m_rewardHolder.append(new AssetPane(this.m_rewardIcon));
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  refreshClaimPanel ()
        {
            this.m_claimPanel.removeAll();
            this.makeClaimPanel();
            this.changeHeaderColor();
            dispatchEvent(new UIEvent(UIEvent.CHANGE_CREW_STATE, "", true));
            return;
        }//end

    }



