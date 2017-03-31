package Display.DialogUI;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.stats.experiments.*;
import Transactions.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class InstantReadyDialogView extends GenericDialogView
    {
        private HarvestableResource m_harvestable ;
        private static boolean m_confirmationFlag =false ;

        public  InstantReadyDialogView (Dictionary param1 ,HarvestableResource param2 )
        {
            this.m_harvestable = param2;
            super(param1, ZLoc.t("Dialogs", "InstantHarvestTitle_" + this.m_harvestable.getTypeName()), "instant_harvest_" + this.m_harvestable.getTypeName(), TYPE_OK, null, "none", GenericDialogView.ICON_POS_BOTTOM);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_5 =0;
            Container _loc_6 =null ;
            _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","InstantHarvestTitle_"+this.m_harvestable.getTypeName()),param1,EmbeddedArt.defaultFontNameBold,m_align,18,EmbeddedArt.brownTextColor);
            double _loc_4 =40;
            if (_loc_3.getHeight() < _loc_4)
            {
                _loc_5 = _loc_4 - _loc_3.getHeight();
                _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_6.append(_loc_3);
                _loc_6.append(ASwingHelper.verticalStrut(_loc_5));
                _loc_2 = _loc_6;
            }
            else
            {
                _loc_2 = _loc_3;
            }
            return _loc_2;
        }//end

         protected JPanel  createIconPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            MarketCell _loc_3 =new MarketCell ();
            _loc_3.setAssetDict(m_assetDict);
            _loc_3.setCellValue(this.getItemFromObject());
            _loc_3.setBuyable(false);
            _loc_2.append(_loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            CustomButton _loc_5 =new CustomButton(String(this.getSingleItemCost ()),new AssetIcon(new m_assetDict.get( "icon_cash") ),"CashButtonUI");
            _loc_5.addEventListener(MouseEvent.MOUSE_UP, this.onReadyOneType, false, 0, true);
            _loc_5.setPreferredWidth(80);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6.appendAll(_loc_5);
            CustomButton _loc_7 =new CustomButton(String(this.getInstantReadyCashCost ()),new AssetIcon(new m_assetDict.get( "icon_cash") ),"CashButtonUI");
            _loc_7.addEventListener(MouseEvent.MOUSE_UP, this.onReadyAllItemsOfType, false, 0, true);
            _loc_7.setPreferredWidth(80);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_8.appendAll(_loc_7);
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            Object _loc_10 ={};
            _loc_10.put(this.m_harvestable.getTypeName(),  ZLoc.t("Items", this.m_harvestable.getHarvestingDefinition().name + "_friendlyName"));
            _loc_9.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantHarvestThis_" + this.m_harvestable.getTypeName(), _loc_10), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor), _loc_6);
            _loc_11 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            _loc_11.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantHarvestAll_" + this.m_harvestable.getTypeName()), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor), _loc_8);
            _loc_4.appendAll(_loc_9, _loc_11);
            _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(15), _loc_4);
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            _loc_12.appendAll(_loc_1, ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantDialogDisclaimer"), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.dialogHintColor));
            return _loc_12;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1.appendAll(ASwingHelper.verticalStrut(45));
            return _loc_1;
        }//end

         protected void  onCancel (Object param1)
        {
            switch(this.m_harvestable.getTypeName())
            {
                case "plot":
                {
                    Global.player.hasSeenInstantPopup_plot = true;
                    break;
                }
                case "ship":
                {
                    Global.player.hasSeenInstantPopup_ship = true;
                    break;
                }
                case "residence":
                {
                    Global.player.hasSeenInstantPopup_residence = true;
                    break;
                }
                default:
                {
                    break;
                }
            }
            countDialogViewAction(ZLoc.t("Dialogs", "Cancel"));
            closeMe();
            return;
        }//end

        private Item  getItemFromObject ()
        {
            return this.m_harvestable.getHarvestingDefinition();
        }//end

        private void  onReadyOneType (Event event )
        {
            Object _loc_3 =null ;
            if (event instanceof GenericPopupEvent && (event as GenericPopupEvent).button != GenericDialogView.YES)
            {
                InstantReadyDialogView.m_confirmationFlag = false;
                closeMe();
                return;
            }
            _loc_2 = this.getSingleItemCost();
            if (InstantReadyDialogView.m_confirmationFlag == false)
            {
                InstantReadyDialogView.m_confirmationFlag = true;
                _loc_3 = {};
                _loc_3.put(this.m_harvestable.getTypeName(),  ZLoc.t("Items", this.m_harvestable.getHarvestingDefinition().name + "_friendlyName"));
                _loc_3.put("amount",  _loc_2);
                UI.displayMessage(ZLoc.t("Dialogs", "InstantHarvestThis_" + this.m_harvestable.getTypeName() + "_buy", _loc_3), GenericDialogView.TYPE_YESNO, this.onReadyOneType, "", true);
                closeMe();
            }
            else if (Global.player.cash < _loc_2)
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                closeMe();
            }
            else
            {
                Global.player.cash = Global.player.cash - _loc_2;
                GameTransactionManager.addTransaction(new TInstantReady(this.m_harvestable), true);
                closeMe();
            }
            return;
        }//end

        private void  onReadyAllItemsOfType (Event event )
        {
            Object _loc_3 =null ;
            if (event instanceof GenericPopupEvent && (event as GenericPopupEvent).button != GenericDialogView.YES)
            {
                InstantReadyDialogView.m_confirmationFlag = false;
                closeMe();
                return;
            }
            _loc_2 = this.getInstantReadyCashCost();
            if (InstantReadyDialogView.m_confirmationFlag == false)
            {
                InstantReadyDialogView.m_confirmationFlag = true;
                _loc_3 = {};
                _loc_3.put("amount",  _loc_2);
                UI.displayMessage(ZLoc.t("Dialogs", "InstantHarvestAll_" + this.m_harvestable.getTypeName() + "_buy", _loc_3), GenericDialogView.TYPE_YESNO, this.onReadyAllItemsOfType, "", true);
                closeMe();
            }
            else if (Global.player.cash < _loc_2)
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                closeMe();
            }
            else
            {
                Global.player.cash = Global.player.cash - _loc_2;
                GameTransactionManager.addTransaction(new TInstantReady(null, this.m_harvestable.getTypeName()), true);
                closeMe();
            }
            return;
        }//end

        private int  getInstantReadyCashCost ()
        {
            int _loc_1 =0;
            HarvestableResource _loc_11 =null ;
            double _loc_2 =1;
            double _loc_3 =1;
            _loc_4 = this.m_harvestable.getTypeName();
            switch(_loc_4)
            {
                case "plot":
                case "ship":
                {
                    _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_CROPS_SHIPS);
                    _loc_2 = Number(RuntimeVariableManager.getString("INSTANT_READY_CROP_EXPONENT", "0.4"));
                    _loc_3 = Global.gameSettings().getInstantReadyCropMultiplier(_loc_1);
                    break;
                }
                case "residence":
                {
                    _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_RESIDENCES);
                    _loc_2 = Number(RuntimeVariableManager.getString("INSTANT_READY_RESIDENCE_EXPONENT", "0.25"));
                    _loc_3 = Global.gameSettings().getInstantReadyResidenceMultiplier(_loc_1);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_5 = this.getWorldObjectType(_loc_4);
            _loc_6 = Global.world.getObjectsByTypes(.get(_loc_5));
            double _loc_7 =0;
            double _loc_8 =0;
            int _loc_9 =0;
            while (_loc_9 < _loc_6.length())
            {

                _loc_11 = _loc_6.get(_loc_9);
                if (_loc_11.getState() == HarvestableResource.STATE_PLANTED)
                {
                    _loc_7 = Number(_loc_11.getGrowTimeLeft()) / 3600;
                    _loc_8 = _loc_8 + _loc_3 * Math.pow(_loc_7, _loc_2);
                }
                _loc_9++;
            }
            _loc_10 = Math.max(Math.ceil(_loc_8),1);
            return Math.max(Math.ceil(_loc_8), 1);
        }//end

        private int  getSingleItemCost ()
        {
            int _loc_2 =0;
            _loc_1 =(double)(this.m_harvestable.getGrowTimeLeft ())/3600;
            double _loc_3 =1;
            double _loc_4 =1;
            _loc_5 = this.m_harvestable.getTypeName();
            switch(_loc_5)
            {
                case "plot":
                case "ship":
                {
                    _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_CROPS_SHIPS);
                    _loc_3 = Number(RuntimeVariableManager.getString("INSTANT_READY_CROP_EXPONENT", "0.4"));
                    _loc_4 = Global.gameSettings().getInstantReadyCropMultiplier(_loc_2);
                    break;
                }
                case "residence":
                {
                    _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_RESIDENCES);
                    _loc_3 = Number(RuntimeVariableManager.getString("INSTANT_READY_RESIDENCE_EXPONENT", "0.25"));
                    _loc_4 = Global.gameSettings().getInstantReadyResidenceMultiplier(_loc_2);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_6 = _loc_4*Math.pow(_loc_1 ,_loc_3 );
            _loc_6 = Math.max(Math.ceil(_loc_6), 1);
            return _loc_6;
        }//end

        private int  getAllItemCost ()
        {
            int _loc_1 =0;
            _loc_2 = this.getNumPlantedItems(this.m_harvestable.getTypeName());
            _loc_3 = this.getHoursLeftForType(this.m_harvestable.getTypeName());
            switch(this.m_harvestable.getTypeName())
            {
                case "plot":
                case "ship":
                {
                    _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_CROPS_SHIPS);
                    return this.getCropCost(_loc_3, _loc_2, _loc_1);
                }
                case "residence":
                {
                    _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_INSTANT_READY_RESIDENCES);
                    return this.getResidenceCost(_loc_3, _loc_2, _loc_1);
                }
                default:
                {
                    break;
                }
            }
            return Number.POSITIVE_INFINITY;
        }//end

        private double  getHoursLeftForType (String param1 )
        {
            HarvestableResource _loc_6 =null ;
            _loc_2 = this.getWorldObjectType(param1);
            _loc_3 = Global.world.getObjectsByTypes(.get(_loc_2));
            double _loc_4 =0;
            int _loc_5 =0;
            while (_loc_5 < _loc_3.length())
            {

                _loc_6 = _loc_3.get(_loc_5);
                if (_loc_6.getState() == HarvestableResource.STATE_PLANTED)
                {
                    _loc_4 = _loc_4 + Number(_loc_6.getGrowTimeLeft()) / 3600;
                }
                _loc_5++;
            }
            return _loc_4;
        }//end

        private double  getNumPlantedItems (String param1 )
        {
            HarvestableResource _loc_6 =null ;
            _loc_2 = this.getWorldObjectType(param1);
            _loc_3 = Global.world.getObjectsByTypes(.get(_loc_2));
            double _loc_4 =0;
            int _loc_5 =0;
            while (_loc_5 < _loc_3.length())
            {

                _loc_6 = _loc_3.get(_loc_5);
                if (_loc_6.getState() == HarvestableResource.STATE_PLANTED)
                {
                    _loc_4 = _loc_4 + 1;
                }
                _loc_5++;
            }
            return _loc_4;
        }//end

        private int  getWorldObjectType (String param1 )
        {
            switch(param1)
            {
                case "plot":
                {
                    return WorldObjectTypes.PLOT;
                }
                case "ship":
                {
                    return WorldObjectTypes.SHIP;
                }
                case "residence":
                {
                    return WorldObjectTypes.RESIDENCE;
                }
                default:
                {
                    break;
                }
            }
            return Number.NaN;
        }//end

        private int  getCropCost (double param1 ,int param2 ,int param3 )
        {
            if (param2 <= 0)
            {
                return 1;
            }
            _loc_4 =(double)(RuntimeVariableManager.getString("INSTANT_READY_CROP_EXPONENT","0.4"));
            _loc_5 = Global.gameSettings().getInstantReadyCropMultiplier(param3);
            _loc_6 = Math.max(Math.ceil(param2*_loc_5*Math.pow(param1/param2,_loc_4)),1);
            return Math.max(Math.ceil(param2 * _loc_5 * Math.pow(param1 / param2, _loc_4)), 1);
        }//end

        private int  getResidenceCost (double param1 ,int param2 ,int param3 )
        {
            if (param2 <= 0)
            {
                return 1;
            }
            _loc_4 =(double)(RuntimeVariableManager.getString("INSTANT_READY_RESIDENCE_EXPONENT","0.25"));
            _loc_5 = Global.gameSettings().getInstantReadyResidenceMultiplier(param3);
            _loc_6 = Math.max(Math.ceil(param2*_loc_5*Math.pow(param1/param2,_loc_4)),1);
            return Math.max(Math.ceil(param2 * _loc_5 * Math.pow(param1 / param2, _loc_4)), 1);
        }//end

    }




