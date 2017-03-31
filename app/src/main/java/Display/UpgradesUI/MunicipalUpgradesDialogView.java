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
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;

    public class MunicipalUpgradesDialogView extends GenericDialogView
    {
        protected String m_buildingName ;
        protected Array m_crewData ;
        protected JPanel m_buttonsPanel ;
        protected JLabel m_numbersLabel ;
        protected JLabel m_discountInfoLabel ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        protected Function m_buyAllHandler ;
        protected UpgradesCrewScrollingList m_scrollingCrew ;
        protected double m_targetObjectId ;
        protected boolean m_flagDiscountInfoPanel ;
        protected JPanel m_discountInfoPanel ;
        protected boolean m_enableVolumeDiscountVariant =false ;
        public static  String UPDATE_POSITIONS ="update_positions";

        public  MunicipalUpgradesDialogView (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 )
        {
            _loc_7 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT );
            this.m_enableVolumeDiscountVariant = ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT_VARIANT3 == _loc_7;
            this.m_buildingName = param1;
            this.m_crewData = param2;
            this.m_finishCheckCB = param3;
            this.m_finishCB = param4;
            this.m_buyAllHandler = param5;
            this.m_targetObjectId = param6;
            super(null);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = UpgradesCommonAssets.assetDict.get("dialogBG");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset);
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredWidth(m_bgAsset.width);
                this.setMinimumWidth(m_bgAsset.width);
                this.setMaximumWidth(m_bgAsset.width);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            this.append(createHeaderPanel());
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.createUpgradeInfoPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.createPositionsInfoPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.createCrewPanel());
            this.append(ASwingHelper.verticalStrut(15));
            if (this.m_enableVolumeDiscountVariant)
            {
                _loc_1 = CrewGate.getFilledPositions(this.m_crewData);
                _loc_2 = this.m_crewData.length;
                _loc_3 = _loc_2 - _loc_1;
                if (_loc_3 > 1 && _loc_3 / _loc_2 >= RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100)
                {
                    this.append(this.createDiscountInfoPanel());
                    this.append(ASwingHelper.verticalStrut(15));
                    this.m_flagDiscountInfoPanel = true;
                }
            }
            this.append(this.createButtonsPanel());
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(27, 20, [{locale:"ja", size:27}]);
            _loc_2 = ZLoc.t("Dialogs","UpgradeX",{itemName ZLoc.t("Items",this.m_buildingName +"_friendlyName")});
            _loc_2 = TextFieldUtil.formatSmallCapsString(_loc_2);
            title = ASwingHelper.makeTextField(_loc_2 + " ", EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
            title.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = this.getTitleTextSizeHeader(_loc_2.length());
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        public int  getTitleTextSizeHeader (int param1 )
        {
            if (param1 > 30)
            {
                return m_titleFontSize * 0.89;
            }
            return m_titleFontSize;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 3, 0, 0, 5);
            return _loc_1;
        }//end

         protected void  onCancelX (Object param1)
        {
            this.m_cancelTextName = "";
            _loc_2 =Global.world.getObjectById(this.m_targetObjectId )as MapResource ;
            if (_loc_2)
            {
                _loc_2.setShowUpgradeArrow(false);
                _loc_2.setState(_loc_2.getState());
            }
            _loc_3 =Global.gameSettings().getItemByName(this.m_buildingName );
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, StatsPhylumType.X, _loc_3.upgrade.newItemName);
            onCancel(param1);
            return;
        }//end

        protected JPanel  createUpgradeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 =Global.gameSettings().getItemByName(this.m_buildingName );
            UpgradesInfoBox _loc_3 =new UpgradesInfoBox(UpgradesCommonAssets.assetDict.get( "manilaCard") ,this.m_buildingName );
            UpgradesInfoBox _loc_4 =new UpgradesInfoBox(UpgradesCommonAssets.assetDict.get( "manilaCard") ,_loc_2.upgrade.newItemName );
            DisplayObject _loc_5 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "upgradeArrow");
            AssetPane _loc_6 =new AssetPane(_loc_5 );
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_7.append(_loc_6);
            _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(8), _loc_7, ASwingHelper.horizontalStrut(8), _loc_4);
            return _loc_1;
        }//end

        protected JPanel  createDiscountInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = CrewGate.getFilledPositions(this.m_crewData);
            _loc_3 = this.m_crewData.length ;
            _loc_4 = _loc_3-_loc_2 ;
            _loc_5 = CrewGate.getCrewCost(this.m_buildingName,"pre_upgrade");
            _loc_6 = _loc_4*_loc_5 ;
            _loc_7 = ZLoc.t("Dialogs","DiscountLabel",{amount 20});
            this.m_discountInfoLabel = new JLabel(_loc_7);
            this.addEventListener(UPDATE_POSITIONS, this.updatePositionsInfo, false, 0, true);
            this.m_discountInfoLabel.setFont(ASwingHelper.getTitleFont(16));
            this.m_discountInfoLabel.setForeground(new ASColor(EmbeddedArt.lightOrangeTextColor));
            _loc_1.append(this.m_discountInfoLabel);
            this.m_discountInfoPanel = _loc_1;
            return _loc_1;
        }//end

        protected JPanel  createPositionsInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            DisplayObject _loc_2 =(DisplayObject)new UpgradesCommonAssets.assetDict.get( "crewIcon");
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_7 = CrewGate.getFilledPositions(this.m_crewData);
            _loc_8 = this.m_crewData.length ;
            _loc_9 = _loc_7.toString ()+"/"+_loc_8.toString ();
            this.m_numbersLabel = new JLabel(_loc_9);
            this.addEventListener(UPDATE_POSITIONS, this.updatePositionsInfo, false, 0, true);
            this.m_numbersLabel.setFont(ASwingHelper.getTitleFont(26));
            this.m_numbersLabel.setForeground(new ASColor(EmbeddedArt.lightOrangeTextColor));
            _loc_10 = ZLoc.t("Dialogs","PositionsFilled");
            JLabel _loc_11 =new JLabel(TextFieldUtil.formatSmallCapsString(_loc_10 ));
            _loc_11.setFont(ASwingHelper.getBoldFont(12));
            _loc_11.setForeground(new ASColor(EmbeddedArt.brownTextColor));
            _loc_5.append(this.m_numbersLabel);
            _loc_6.append(_loc_11);
            _loc_4.appendAll(_loc_5, ASwingHelper.verticalStrut(-10), _loc_6);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(18), _loc_3, _loc_4);
            return _loc_1;
        }//end

        protected void  updatePositionsInfo (Object param1)
        {
            CustomButton _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            String _loc_10 =null ;
            _loc_2 = CrewGate.getFilledPositions(this.m_crewData);
            _loc_3 = this.m_crewData.length ;
            _loc_4 = _loc_2.toString ()+"/"+_loc_3.toString ();
            this.m_numbersLabel.setText(_loc_4);
            if (this.m_enableVolumeDiscountVariant)
            {
                if (!this.m_finishCheckCB())
                {
                    _loc_5 =(CustomButton) this.m_buttonsPanel.getChildByName("JButton");
                    _loc_6 = _loc_3 - _loc_2;
                    _loc_7 = CrewGate.getCrewCost(this.m_buildingName, "pre_upgrade");
                    _loc_8 = _loc_6 * _loc_7;
                    _loc_9 = _loc_8;
                    if (_loc_6 > 1 && _loc_6 / _loc_3 >= RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100)
                    {
                        _loc_9 = Math.floor(_loc_9 * (100 - RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 20)) / 100);
                    }
                    else if (this.m_flagDiscountInfoPanel)
                    {
                        this.remove(this.m_discountInfoPanel);
                        this.m_flagDiscountInfoPanel = false;
                    }
                    if (_loc_6 > 1)
                    {
                        _loc_10 = ZLoc.t("Dialogs", "BuyAllForX", {cash:_loc_9});
                        _loc_5.setText(_loc_10);
                    }
                    else
                    {
                        this.m_buttonsPanel.remove(_loc_5);
                    }
                }
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createCrewPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_scrollingCrew = new UpgradesCrewScrollingList(UpgradesCommonAssets.assetDict, this.m_crewData);
            _loc_1.append(this.m_scrollingCrew);
            return _loc_1;
        }//end

        protected JPanel  createButtonsPanel ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            AssetIcon _loc_4 =null ;
            CustomButton _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            String _loc_10 =null ;
            CustomButton _loc_11 =null ;
            CustomButton _loc_12 =null ;
            if (this.m_buttonsPanel)
            {
                this.m_buttonsPanel.removeAll();
            }
            this.m_buttonsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (!this.m_finishCheckCB())
            {
                _loc_1 = CrewGate.getCrewCost(this.m_buildingName, "pre_upgrade");
                _loc_2 = this.m_crewData.length - CrewGate.getFilledPositions(this.m_crewData);
                _loc_3 = _loc_1 * _loc_2;
                _loc_4 = new AssetIcon(new EmbeddedArt.icon_cash());
                _loc_5 = null;
                if (this.m_enableVolumeDiscountVariant)
                {
                    _loc_6 = CrewGate.getFilledPositions(this.m_crewData);
                    _loc_7 = this.m_crewData.length;
                    _loc_8 = _loc_7 - _loc_6;
                    _loc_9 = _loc_1 * _loc_2;
                    if (_loc_8 > 1 && _loc_8 / _loc_7 >= RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100)
                    {
                        _loc_9 = Math.floor(_loc_9 * (100 - RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 20)) / 100);
                    }
                    if (_loc_8 > 1)
                    {
                        _loc_10 = ZLoc.t("Dialogs", "BuyAllForX", {cash:_loc_9});
                        _loc_5 = new CustomButton(TextFieldUtil.formatSmallCapsString(_loc_10), _loc_4, "BigCashButtonUI");
                    }
                }
                else
                {
                    _loc_5 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "FillAll")), _loc_4, "BigCashButtonUI");
                }
                if (_loc_5)
                {
                    _loc_5.addEventListener(MouseEvent.CLICK, this.onFillAll, false, 0, true);
                    _loc_5.setPreferredHeight(45);
                    _loc_5.setMinimumHeight(45);
                    _loc_5.setMaximumHeight(45);
                    this.m_buttonsPanel.append(_loc_5);
                    this.m_buttonsPanel.append(ASwingHelper.horizontalStrut(20));
                }
            }
            if (this.m_finishCheckCB())
            {
                _loc_11 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "FinishBuilding")), null, "BigGreenButtonUI");
                _loc_11.addEventListener(MouseEvent.CLICK, this.onFinishClick, false, 0, true);
                _loc_11.setPreferredHeight(45);
                _loc_11.setMinimumHeight(45);
                _loc_11.setMaximumHeight(45);
                this.m_buttonsPanel.append(_loc_11);
            }
            else
            {
                _loc_12 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "HireFriendsButtonLabel")), null, "BigGreenButtonUI");
                _loc_12.addEventListener(MouseEvent.CLICK, this.onFinishClick, false, 0, true);
                _loc_12.setPreferredHeight(45);
                _loc_12.setMinimumHeight(45);
                _loc_12.setMaximumHeight(45);
                this.m_buttonsPanel.append(_loc_12);
            }
            ASwingHelper.prepare(this.m_buttonsPanel);
            return this.m_buttonsPanel;
        }//end

        public void  rebuildButtons ()
        {
            this.remove(this.m_buttonsPanel);
            this.append(this.createButtonsPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  onFinishClick (Event event )
        {
            _loc_2 =Global.world.getObjectById(this.m_targetObjectId );
            if (!_loc_2)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "CannotUpgradeMunicipalInMunicipalCenter", {buildingName:ZLoc.t("Items", this.m_buildingName + "_friendlyName")}));
                onCancel(event);
                return;
            }
            this.m_finishCB();
            return;
        }//end

        protected void  onFillAll (Event event )
        {
            e = event;
            baseCost = CrewGate.getCrewCost(this.m_buildingName,"pre_upgrade");
            buyCount = this.m_crewData.length-CrewGate.getFilledPositions(this.m_crewData);
            totalCost = baseCost*buyCount;
            if (this.m_enableVolumeDiscountVariant)
            {
                if (buyCount / this.m_crewData.length >= RuntimeVariableManager.getInt("BUY_ALL_CREW_DISCOUNT_THRESHOLD", 25) / 100)
                {
                    totalCost = Math.floor(totalCost * (100 - RuntimeVariableManager.getInt("BUY_ALL_DISCOUNT_RATE", 20)) / 100);
                }
            }
            UI .displayMessage (ZLoc .t ("Dialogs","CrewCashConfirm",{buyCount crewAmount ,totalCost cashAmount }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    m_buyAllHandler(m_crewData);
                    m_scrollingCrew.rebuildCellsAfterFillAll();
                    updatePositionsInfo(event);
                    rebuildButtons();
                }
                return;
            }//end
            , "", true);
            return;
        }//end

    }



