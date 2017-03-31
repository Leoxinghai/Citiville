package Modules.automation.ui;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class AutomationUI extends ItemCatalogUI
    {
        protected JPanel m_body ;
        protected Dictionary m_gasButtonMapping ;
        protected Dictionary m_gasContainerMapping ;
        protected Dictionary m_gasTotalMapping ;
        protected JPanel m_automatorProfilePanel ;
        protected JPanel m_automatorPanelContainer ;
        protected JLabel m_txtGasCountdown ;
        protected JTextField m_txtNumGas ;
        protected CustomButton m_btnAskForGas ;
        protected Timer m_moreGasTimer ;
        protected JPanel m_gasCountdownPanelContainer ;
        protected JPanel m_gasCountdownPanel ;
        protected JLabel m_txtFull ;
        protected boolean m_closing =false ;
        protected JWindow m_toolTipWindow ;

        public  AutomationUI ()
        {
            this.m_gasContainerMapping = new Dictionary();
            this.m_gasButtonMapping = new Dictionary();
            this.m_gasTotalMapping = new Dictionary();
            return;
        }//end

        public int  getScrollHeight ()
        {
            return 240 + Catalog.TAB_HEIGHTOFFFSET;
        }//end

        private void  showToolTipGas (MouseEvent event )
        {
            if (!this.m_toolTipWindow)
            {
                this.m_toolTipWindow = new JWindow(this);
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ItemCatalogToolTip _loc_3 =new ItemCatalogToolTip ();
            _loc_3.changeInfo(this.m_gasTotalMapping.get(event.currentTarget));
            _loc_2.appendAll(_loc_3);
            ASwingHelper.prepare(_loc_3);
            this.m_toolTipWindow.setContentPane(_loc_2);
            this.m_toolTipWindow.setX(event.currentTarget.x);
            this.m_toolTipWindow.setY(event.currentTarget.y - _loc_3.height + 150);
            ASwingHelper.prepare(this.m_toolTipWindow);
            this.m_toolTipWindow.mouseChildren = false;
            this.m_toolTipWindow.mouseEnabled = false;
            this.m_toolTipWindow.show();
            Sounds.play("UI_mouseover");
            return;
        }//end

        private void  hideToolTipGas (MouseEvent event )
        {
            this.m_toolTipWindow.hide();
            return;
        }//end

         protected void  renderTab (String param1 )
        {
            JPanel tab ;
            JPanel vert ;
            String requiredBuildingNameForTab ;
            boolean isLocked ;
            String upUrl ;
            String overUrl ;
            Object assetObj ;
            ImageTabButton btn ;
            str = param1;
            tab = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            vert = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, -6);
            int _loc_4 =0;
            _loc_5 =Global.gameSettings().getAutomationMenuItems ();
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("type") == str)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            requiredBuildingNameForTab = _loc_3.attribute("requireOnMap").toString();
            isLocked = Global.world.getObjectsByNames(.get(requiredBuildingNameForTab)).length == 0;
            upUrl = "assets/automation/ui/" + str + "_icon_off.png";
            overUrl = "assets/automation/ui/" + str + "_icon_on.png";
            if (isLocked)
            {
                upUrl = "assets/automation/ui/" + str + "_icon_locked.png";
                overUrl = "assets/automation/ui/" + str + "_icon_locked.png";
            }
            assetObj;
            btn = new ImageTabButton(upUrl, overUrl, assetObj);
            assetObj.put("tab",  vert);
            assetObj.put("jbtn",  btn);
            m_iconsDict.put(str,  assetObj);
            m_tabsDict.put(btn,  str);
            btn.name = str;
            vert.append(btn);
            ASwingHelper.setEasyBorder(vert, 14);
            tab.append(vert);
            if (!isLocked)
            {
                btn.addActionListener(changeCategory, 0, true);
            }
            ASwingHelper.prepare(tab);
            m_tabsPanel.append(tab);
            btn.addEventListener(MouseEvent.ROLL_OVER, this.showToolTip, false, 0, true);
            btn.addEventListener(MouseEvent.ROLL_OUT, hideToolTip, false, 0, true);
            return;
        }//end

         public void  switchType (String param1 )
        {
            Object btn ;
            TextFormat bigForm ;
            JTextField automatorTitle ;
            JPanel automatorTitlePanel ;
            String automatorItemName ;
            Item automatorItem ;
            AssetPane automatorAP ;
            JPanel automatorIconPanel ;
            JLabel txtCostPerTarget ;
            JPanel costDisplay ;
            AssetPane clock ;
            JPanel numGasPanel ;
            JLabel moreGasInLabel ;
            JPanel gasRight ;
            JPanel horizontalDividerPanel ;
            JPanel gasReadoutContainer ;
            JPanel gasReadoutPanel ;
            AssetPane verticalDiv ;
            Array gasItems ;
            Array eligibleItems ;
            Item candidateItem ;
            int i ;
            JLabel subtitle ;
            JPanel verticalPanel ;
            boolean isEligible ;
            String key ;
            int currVariant ;
            Item currGasItem ;
            JLabel txtItemName ;
            AssetPane gasItemAsset ;
            JPanel gasItemAssetPanel ;
            JPanel gasItemContainer ;
            AssetIcon icon ;
            CustomButton gasBuyButton ;
            JPanel outerGasBox ;
            type = param1;
            boolean areButtonsFullyLoaded ;
            int _loc_3 =0;
            _loc_4 = m_tabsDict;
            for(int i0 = 0; i0 < m_tabsDict.size(); i0++)
            {
            		btn = m_tabsDict.get(i0);


                if (btn instanceof ImageTabButton && !btn.isFullyLoaded)
                {
                    areButtonsFullyLoaded;
                    break;
                }
            }
            if (areButtonsFullyLoaded)
            {
                switchActiveTab(m_type, type);
                m_type = type;
                ActionAutomationManager.instance.mode = m_type;
                m_sectionTitle.setText(ZLoc.t("Dialogs", m_type + "_menu") + " ");
                bigForm = new TextFormat();
                bigForm.size = BIG_FONT_SIZE;
                TextFieldUtil.formatSmallCaps(m_sectionTitle.getTextField(), bigForm);
                this.m_body = ASwingHelper.makeSoftBoxJPanel();
                automatorTitle = ASwingHelper.makeTextField(ZLoc.t("Dialogs", type + "_menu") + " ", EmbeddedArt.titleFont, 12, EmbeddedArt.titleColor);
                automatorTitle.filters = EmbeddedArt.newtitleFilters;
                TextFieldUtil.formatSmallCaps(automatorTitle.getTextField(), new TextFormat(null, 14));
                automatorTitlePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                automatorTitlePanel.appendAll(automatorTitle);
                int _loc_41 =0;
                _loc_51 = Global.gameSettings().getAutomationMenuItems();
                XMLList _loc_31 =new XMLList("");
                Object _loc_61;
                for(int i0 = 0; i0 < _loc_51.size(); i0++)
                {
                		_loc_61 = _loc_51.get(i0);


                    with (_loc_61)
                    {
                        if (attribute("type") == m_type)
                        {
                            _loc_31.put(_loc_41++,  _loc_61);
                        }
                    }
                }
                automatorItemName = _loc_31.attribute("automator").toString();
                automatorItem = Global.gameSettings().getItemByName(automatorItemName);
                automatorAP = new AssetPane();
                LoadingManager.load(automatorItem.getImageByName("icon"), this.getIconLoadCallback(automatorAP));
                automatorIconPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                automatorIconPanel.appendAll(automatorAP);
                automatorIconPanel.setPreferredHeight(50);
                txtCostPerTarget = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Automation_" + type + "_costper"), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.blueTextColor);
                costDisplay = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                costDisplay.appendAll(new AssetPane(new Catalog.assetDict.get("gas_icon")), ASwingHelper.makeLabel("1", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBlueTextColor), ASwingHelper.makeLabel(" " + ZLoc.t("Dialogs", "And") + " ", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBlueTextColor), new AssetPane(new Catalog.assetDict.get("energy_icon")), ASwingHelper.makeLabel("1", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBlueTextColor));
                this.m_automatorProfilePanel = ASwingHelper.makeSoftBoxJPanelVertical();
                this.m_automatorProfilePanel.setPreferredWidth(150);
                this.m_automatorProfilePanel.appendAll(automatorTitlePanel, automatorIconPanel, txtCostPerTarget, costDisplay);
                this.m_automatorPanelContainer = ASwingHelper.makeSoftBoxJPanelVertical();
                this.m_automatorPanelContainer.setPreferredWidth(150);
                this.m_automatorPanelContainer.append(this.m_automatorProfilePanel);
                this.m_body.appendAll(this.m_automatorPanelContainer);
                clock = new AssetPane(new Catalog.assetDict.get("automation_clock"));
                this.m_txtNumGas = ASwingHelper.makeTextField(Global.player.getRegenerableResource("gas").toString(), EmbeddedArt.titleFont, 20, EmbeddedArt.titleColor);
                this.m_txtNumGas.filters = EmbeddedArt.newtitleFilters;
                numGasPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                numGasPanel.appendAll(new AssetPane(new Catalog.assetDict.get("gas_icon")), this.m_txtNumGas);
                moreGasInLabel = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Automation_moreGasIn"), EmbeddedArt.defaultFontNameBold, 11, EmbeddedArt.darkBlueTextColor);
                this.m_txtGasCountdown = ASwingHelper.makeLabel("00:00", EmbeddedArt.titleFont, 14, EmbeddedArt.darkBlueTextColor);
                gasRight = ASwingHelper.makeSoftBoxJPanelVertical();
                gasRight.appendAll(moreGasInLabel, ASwingHelper.verticalStrut(-5), this.m_txtGasCountdown);
                this.m_gasCountdownPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                this.m_gasCountdownPanel.appendAll(clock, gasRight);
                this.m_txtFull = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Automation_full"), EmbeddedArt.titleFont, 16, EmbeddedArt.darkBlueTextColor);
                this.m_gasCountdownPanelContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                this.m_gasCountdownPanelContainer.append(this.m_gasCountdownPanel);
                horizontalDividerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                horizontalDividerPanel.appendAll(new AssetPane(new Catalog.assetDict.get("blue_divider_horizontal")));
                gasReadoutContainer = ASwingHelper.makeSoftBoxJPanelVertical();
                ASwingHelper.setBackground(gasReadoutContainer, new Catalog.assetDict.get("blue_box"));
                gasReadoutContainer.setPreferredSize(new IntDimension(190, 95));
                gasReadoutContainer.appendAll(ASwingHelper.verticalStrut(10), numGasPanel, ASwingHelper.verticalStrut(5), horizontalDividerPanel, ASwingHelper.verticalStrut(3), this.m_gasCountdownPanelContainer);
                this.m_btnAskForGas = new CustomButton(ZLoc.t("Dialogs", "Automation_askForGas"), null, "GreenSmallButtonUI");
                this.m_btnAskForGas.setEnabled(Global.world.viralMgr.canPost("AskForGas") && !Global.player.isResourceAtSoftCap(RegenerableResource.GAS));
                this.m_btnAskForGas.addActionListener(this.onAskForGasButtonClick, 0, true);
                gasReadoutPanel = ASwingHelper.makeSoftBoxJPanelVertical();
                gasReadoutPanel.appendAll(gasReadoutContainer, this.m_btnAskForGas);
                verticalDiv = new AssetPane(new Catalog.assetDict.get("blue_divider_vertical"));
                this.m_body.appendAll(gasReadoutPanel, ASwingHelper.horizontalStrut(10), verticalDiv);
                gasItems = Global.gameSettings().getItemsByType("gas");
                eligibleItems;
                int _loc32 =0;
                _loc42 = gasItems;
                for(int i0 = 0; i0 < gasItems.size(); i0++)
                {
                		candidateItem = gasItems.get(i0);


                    if (candidateItem.experiments)
                    {
                        isEligible;
                        int _loc52 =0;
                        _loc62 = candidateItem.experiments;
                        for(int i0 = 0; i0 < candidateItem.experiments.size(); i0++)
                        {
                        		key = candidateItem.experiments.get(i0);


                            currVariant = Global.experimentManager.getVariant(key);
                            if (((Array)candidateItem.experiments.get(key)).indexOf(currVariant) < 0)
                            {
                                isEligible;
                                break;
                            }
                        }
                        if (isEligible)
                        {
                            eligibleItems.push(candidateItem);
                        }
                    }
                }
                i = 0;
                while (i < 3 && i < eligibleItems.length())
                {

                    currGasItem =(Item) eligibleItems.get(i);
                    txtItemName = ASwingHelper.makeLabel(currGasItem.localizedName, EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBlueTextColor);
                    gasItemAsset = new AssetPane();
                    LoadingManager.load(currGasItem.getImageByName("icon"), this.getIconLoadCallback(gasItemAsset));
                    gasItemAssetPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    gasItemAssetPanel.appendAll(gasItemAsset);
                    gasItemContainer = ASwingHelper.makeSoftBoxJPanelVertical();
                    ASwingHelper.setBackground(gasItemContainer, new Catalog.assetDict.get("blue_box"));
                    gasItemContainer.setPreferredSize(new IntDimension(90, 95));
                    gasItemContainer.appendAll(txtItemName, gasItemAssetPanel);
                    gasItemContainer.addEventListener(MouseEvent.CLICK, this.onBuyGasButtonClick, false, 0, true);
                    this.m_gasContainerMapping.put(currGasItem.name,  gasItemContainer);
                    icon = new AssetIcon(new Catalog.assetDict.get("icon_cash"));
                    gasBuyButton = new CustomButton(currGasItem.cash.toString(), icon, "CashButtonUI");
                    gasBuyButton.addActionListener(this.onBuyGasButtonClick, 0, true);
                    this.m_gasButtonMapping.put(currGasItem.name,  gasBuyButton);
                    outerGasBox = ASwingHelper.makeSoftBoxJPanelVertical();
                    outerGasBox.appendAll(gasItemContainer, gasBuyButton);
                    this.m_gasTotalMapping.put(outerGasBox,  currGasItem);
                    outerGasBox.addEventListener(MouseEvent.MOUSE_OVER, this.showToolTipGas, false, 0, true);
                    outerGasBox.addEventListener(MouseEvent.MOUSE_OUT, this.hideToolTipGas, false, 0, true);
                    this.m_body.appendAll(ASwingHelper.horizontalStrut(10), outerGasBox);
                    i = (i + 1);
                }
                subtitle = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Automation_" + type + "_subtitle"), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBlueTextColor);
                verticalPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                verticalPanel.appendAll(ASwingHelper.verticalStrut(-10), subtitle, this.m_body);
                m_centerPanel.removeAll();
                m_centerPanel.append(verticalPanel);
                m_centerPanel.setPreferredHeight(140);
                ASwingHelper.prepare(m_centerPanel);
                this.onMoreGasTimerTick(null);
                if (!this.m_moreGasTimer)
                {
                    this.m_moreGasTimer = new Timer(1000);
                    this.m_moreGasTimer.addEventListener(TimerEvent.TIMER, this.onMoreGasTimerTick);
                    this.m_moreGasTimer.start();
                }
            }
            else
            {
                setTimeout(this.switchType, 10, type);
            }
            return;
        }//end

        private Function  getIconLoadCallback (AssetPane param1 )
        {
            targetPane = param1;
            return void  (Event event )
            {
                targetPane.setAsset((event.currentTarget as LoaderInfo).content);
                return;
            }//end
            ;
        }//end

        private Function  getImageButtonLoadCallback (JButton param1 )
        {
            targetButton = param1;
            return void  (Event event )
            {
                _loc_2 =(LoaderInfo ).content) event(.currentTarget;
                SimpleButton _loc_3 =new SimpleButton(_loc_2 ,_loc_2 ,_loc_2 ,_loc_2 );
                targetButton.wrapSimpleButton(_loc_3);
                return;
            }//end
            ;
        }//end

         public void  goToItem (String param1 )
        {
            return;
        }//end

         protected void  makeTopTabs (Array param1)
        {
            super.makeTopTabs(param1);
            m_rightPanel.removeAll();
            m_rightPanel.append(m_closeButtonPanel);
            return;
        }//end

         protected void  addListeners ()
        {
            ItemCatalogUI me ;
            if (stage != null)
            {
                stage.addEventListener(MouseEvent.CLICK, onStageClick);
            }
            else
            {
                me = new ItemCatalogUI();
                this .addEventListener (Event .ADDED_TO_STAGE ,void  (Event event )
            {
                me.removeEventListener(Event.ADDED_TO_STAGE, arguments.callee);
                stage.addEventListener(MouseEvent.CLICK, onStageClick);
                return;
            }//end
            );
            }
            return;
        }//end

         protected void  removeListeners ()
        {
            super.removeListeners();
            this.m_moreGasTimer.removeEventListener(TimerEvent.TIMER, this.onMoreGasTimerTick);
            this.m_moreGasTimer.stop();
            this.m_moreGasTimer = null;
            return;
        }//end

        protected void  onMoreGasTimerTick (TimerEvent event )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            String _loc_8 =null ;
            this.m_txtNumGas.setText(Global.player.getRegenerableResource(RegenerableResource.GAS).toString() + " ");
            if (this.m_btnAskForGas)
            {
                this.m_btnAskForGas.setEnabled(Global.world.viralMgr.canPost("AskForGas") && !Global.player.isResourceAtSoftCap(RegenerableResource.GAS));
            }
            _loc_2 =Global.gameSettings().getRegenerableResourceByName(RegenerableResource.GAS );
            if (Global.player.getRegenerableResource(RegenerableResource.GAS) < _loc_2.regenCap)
            {
                if (!this.m_gasCountdownPanelContainer.containsChild(this.m_gasCountdownPanel))
                {
                    this.m_gasCountdownPanelContainer.removeAll();
                    this.m_gasCountdownPanelContainer.append(this.m_gasCountdownPanel);
                }
                _loc_3 = _loc_2.regenInterval;
                _loc_4 = GlobalEngine.getTimer() / 1000;
                _loc_5 = Global.player.getLastRegenerableResourceCheck(RegenerableResource.GAS);
                _loc_6 = _loc_4 - _loc_5;
                _loc_7 = _loc_3 - _loc_6;
                if (_loc_7 < 0)
                {
                    _loc_7 = _loc_7 + _loc_3;
                    Global.player.regenerateRegenerableResource(RegenerableResource.GAS);
                }
                _loc_8 = GameUtil.formatMinutesSeconds(_loc_7);
                if (this.m_txtGasCountdown)
                {
                    this.m_txtGasCountdown.setText(_loc_8);
                }
            }
            else if (!this.m_gasCountdownPanelContainer.containsChild(this.m_txtFull))
            {
                this.m_gasCountdownPanelContainer.removeAll();
            }
            return;
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "no_scroll_bg");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(Catalog.TAB_HEIGHTOFFFSET ));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height + Catalog.TAB_HEIGHTOFFFSET));
            return;
        }//end

         protected void  showToolTip (MouseEvent event )
        {
            JButton btn ;
            String str ;
            String requiredBuildingNameForTab ;
            boolean isLocked ;
            IntPoint ip ;
            double posX ;
            Object obj ;
            e = event;
            btn =(JButton) e.currentTarget;
            str = m_tabsDict.get(e.currentTarget as JButton);
            int _loc_4 =0;
            _loc_5 =Global.gameSettings().getAutomationMenuItems ();
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("type") == str)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            requiredBuildingNameForTab = _loc_3.attribute("requireOnMap").toString();
            isLocked = Global.world.getObjectsByNames(.get(requiredBuildingNameForTab)).length == 0;
            if (isLocked)
            {
                str = str + "_locked";
            }
            ip = btn.getGlobalLocation();
            posX = ip.x;
            obj = new Object();
            obj.category = str;
            obj.posX = posX;
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.ROLLOVER_MARKET_CATEGORY, obj));
            return;
        }//end

        protected void  onAskForGasButtonClick (AWEvent event )
        {
            Global.world.viralMgr.sendAskForGas();
            this.m_btnAskForGas.setEnabled(false);
            return;
        }//end

        protected void  onBuyGasButtonClick (Event event )
        {
            String _loc_3 =null ;
            String _loc_2 ="";
            for(int i0 = 0; i0 < this.m_gasButtonMapping.size(); i0++)
            {
            		_loc_3 = this.m_gasButtonMapping.get(i0);

                if (event.currentTarget == this.m_gasButtonMapping.get(_loc_3) || event.currentTarget == this.m_gasContainerMapping.get(_loc_3))
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            if (_loc_2 != "" && Global.player.canBuyCash(Global.gameSettings().getItemByName(_loc_2).cash, true))
            {
                TransactionManager.addTransaction(new TBuyRegenerableResource(_loc_2, false));
                this.onMoreGasTimerTick(null);
            }
            return;
        }//end

        protected void  onClose (TimerEvent event )
        {
            this.m_closing = true;
            UI.hideAutomationCatalog();
            return;
        }//end

         public void  onTweenOut ()
        {
            if (this.m_closing)
            {
                return;
            }
            Global.world.setDefaultGameMode();
            double _loc_1 =500;
            Timer _loc_2 =new Timer(_loc_1 ,1);
            _loc_2.addEventListener(TimerEvent.TIMER_COMPLETE, this.onClose, false, 0, true);
            _loc_2.start();
            return;
        }//end

         public void  onTweenIn ()
        {
            if (!(Global.world.getTopGameMode() instanceof GMAutomation))
            {
                Global.world.addGameMode(new GMAutomation());
            }
            return;
        }//end

    }



