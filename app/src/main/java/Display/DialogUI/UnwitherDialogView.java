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
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class UnwitherDialogView extends GenericDialogView
    {
        private HarvestableResource m_harvestable ;
        private int m_experimentVariant ;
        private static boolean m_confirmationFlag_single_plot =false ;
        private static boolean m_confirmationFlag_single_ship =false ;
        private static boolean m_confirmationFlag_all_plot =false ;
        private static boolean m_confirmationFlag_all_ship =false ;

        public  UnwitherDialogView (Dictionary param1 ,HarvestableResource param2 )
        {
            this.m_experimentVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CV_UNWITHER);
            this.m_harvestable = param2;
            super(param1, ZLoc.t("Dialogs", "InstantUnwitherTitle_" + this.m_harvestable.getTypeName()), "instant_unwither_" + this.m_harvestable.getTypeName(), TYPE_OK, null, "none", GenericDialogView.ICON_POS_BOTTOM);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_5 =0;
            Container _loc_6 =null ;
            _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","InstantUnwitherTitle_"+this.m_harvestable.getTypeName()),param1,EmbeddedArt.defaultFontNameBold,m_align,18,EmbeddedArt.brownTextColor);
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
            CustomButton _loc_11 =null ;
            JPanel _loc_12 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            MarketCell _loc_3 =new MarketCell ();
            _loc_3.setAssetDict(m_assetDict);
            _loc_3.setCellValue(this.getItemFromObject());
            _loc_3.setBuyable(false);
            _loc_2.append(_loc_3);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            if (this.isSingleAll() == true)
            {
                _loc_11 = new CustomButton(String(this.getSingleUnwitherCost()), new AssetIcon(new m_assetDict.get("icon_cash")), "CashButtonUI");
                _loc_11.addEventListener(MouseEvent.MOUSE_UP, this.onReadyOneType, false, 0, true);
                _loc_11.setPreferredWidth(80);
                _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_12.appendAll(_loc_11);
            }
            CustomButton _loc_5 =new CustomButton(String(this.getInstantUnwitherCashCost ()),new AssetIcon(new m_assetDict.get( "icon_cash") ),"CashButtonUI");
            _loc_5.addEventListener(MouseEvent.MOUSE_UP, this.onReadyAllItemsOfType, false, 0, true);
            _loc_5.setPreferredWidth(80);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6.appendAll(_loc_5);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            Object _loc_8 ={};
            _loc_8.get(this.m_harvestable.getTypeName()) = ZLoc.t("Items", this.m_harvestable.getHarvestingDefinition().name + "_friendlyName");
            if (this.isSingleAll() == true)
            {
                _loc_7.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantUnwitherThis_" + this.m_harvestable.getTypeName(), _loc_8), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor), _loc_12);
            }
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            _loc_9.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantUnwitherAll_" + this.m_harvestable.getTypeName()), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor), _loc_6);
            _loc_4.appendAll(_loc_7, _loc_9);
            _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(15), _loc_4);
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            _loc_10.appendAll(_loc_1, ASwingHelper.makeLabel(ZLoc.t("Dialogs", "InstantDialogDisclaimer"), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.dialogHintColor));
            return _loc_10;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_1.appendAll(ASwingHelper.verticalStrut(45));
            return _loc_1;
        }//end

         protected void  onCancel (Object param1)
        {
            String _loc_2 =null ;
            _loc_3 = this.m_harvestable.getTypeName();
            _loc_2 = "unwither_" + _loc_3;
            countDialogViewAction(ZLoc.t("Dialogs", "Cancel"));
            StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "closed");
            switch(this.m_harvestable.getTypeName())
            {
                case "plot":
                {
                    Global.player.hasSeenWitherPopup_plot = true;
                    break;
                }
                case "ship":
                {
                    Global.player.hasSeenWitherPopup_ship = true;
                    break;
                }
                default:
                {
                    break;
                }
            }
            closeMe();
            return;
        }//end

        private Item  getItemFromObject ()
        {
            return this.m_harvestable.getHarvestingDefinition();
        }//end

        private boolean  updateConfirmationFlag (String param1 ,boolean param2 ,boolean param3 )
        {
            switch(param1)
            {
                case "plot":
                {
                    if (param3 == true)
                    {
                        UnwitherDialogView.m_confirmationFlag_single_plot = param2;
                    }
                    else
                    {
                        UnwitherDialogView.m_confirmationFlag_all_plot = param2;
                    }
                    break;
                }
                case "ship":
                {
                    if (param3 == true)
                    {
                        UnwitherDialogView.m_confirmationFlag_single_ship = param2;
                    }
                    else
                    {
                        UnwitherDialogView.m_confirmationFlag_all_ship = param2;
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        private boolean  getConfirmationFlag (String param1 ,boolean param2 )
        {
            switch(param1)
            {
                case "plot":
                {
                    if (param2 == true)
                    {
                        return UnwitherDialogView.m_confirmationFlag_single_plot;
                    }
                    return UnwitherDialogView.m_confirmationFlag_all_plot;
                }
                case "ship":
                {
                    if (param2 == true)
                    {
                        return UnwitherDialogView.m_confirmationFlag_single_ship;
                    }
                    return UnwitherDialogView.m_confirmationFlag_all_ship;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        private void  onReadyOneType (Event event )
        {
            String _loc_2 =null ;
            Object _loc_6 =null ;
            _loc_3 = this.m_harvestable.getTypeName();
            _loc_2 = "unwither_" + _loc_3;
            if (event instanceof GenericPopupEvent && (event as GenericPopupEvent).button != GenericDialogView.YES)
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "confirmation", "unwither_one", "no");
                this.updateConfirmationFlag(this.m_harvestable.getTypeName(), false, true);
                closeMe();
                return;
            }
            _loc_4 = this.getSingleUnwitherCost();
            boolean _loc_5 =false ;
            _loc_5 = this.getConfirmationFlag(this.m_harvestable.getTypeName(), true);
            if (_loc_5 == false)
            {
                this.updateConfirmationFlag(this.m_harvestable.getTypeName(), true, true);
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "clicked", "unwither_one");
                _loc_6 = {};
                _loc_6.put(this.m_harvestable.getTypeName(),  ZLoc.t("Items", this.m_harvestable.getHarvestingDefinition().name + "_friendlyName"));
                _loc_6.put("amount", _loc_4);
                UI.displayMessage(ZLoc.t("Dialogs", "InstantUnwitherThis_" + this.m_harvestable.getTypeName() + "_buy", _loc_6), GenericDialogView.TYPE_YESNO, this.onReadyOneType, "", true);
                closeMe();
            }
            else if (Global.player.cash < _loc_4)
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                closeMe();
            }
            else
            {
                Global.player.cash = Global.player.cash - _loc_4;
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "confirmation", "unwither_one", "yes");
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "clicked", "unwither_one");
                this.m_harvestable.setFullGrown();
                GameTransactionManager.addTransaction(new TUnwither(TUnwither.UNWITHER_ONE, {objectId:this.m_harvestable.getId()}));
                closeMe();
            }
            return;
        }//end

        private void  onReadyAllItemsOfType (Event event )
        {
            String _loc_2 =null ;
            Object _loc_6 =null ;
            _loc_3 = this.m_harvestable.getTypeName();
            _loc_2 = "unwither_" + _loc_3;
            if (event instanceof GenericPopupEvent && (event as GenericPopupEvent).button != GenericDialogView.YES)
            {
                this.updateConfirmationFlag(this.m_harvestable.getTypeName(), false, false);
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "confirmation", "unwither_all", "no");
                closeMe();
                return;
            }
            _loc_4 = this.getInstantUnwitherCashCost();
            boolean _loc_5 =false ;
            _loc_5 = this.getConfirmationFlag(this.m_harvestable.getTypeName(), false);
            if (_loc_5 == false)
            {
                this.updateConfirmationFlag(this.m_harvestable.getTypeName(), true, false);
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "clicked", "unwither_all");
                _loc_6 = {};
                _loc_6.put("amount", _loc_4);
                UI.displayMessage(ZLoc.t("Dialogs", "InstantUnwitherAll_" + this.m_harvestable.getTypeName() + "_buy", _loc_6), GenericDialogView.TYPE_YESNO, this.onReadyAllItemsOfType, "", true);
                closeMe();
            }
            else if (Global.player.cash < _loc_4)
            {
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                closeMe();
            }
            else
            {
                Global.player.cash = Global.player.cash - _loc_4;
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "confirmation", "unwither_all", "yes");
                StatsManager.sample(100, StatsCounterType.DIALOG, _loc_2, "clicked", "unwither_all");
                this.unwitherAllOfType(_loc_3);
                GameTransactionManager.addTransaction(new TUnwither(TUnwither.UNWITHER_TYPE, {type:_loc_3}));
                closeMe();
            }
            return;
        }//end

        private void  unwitherAllOfType (String param1 )
        {
            HarvestableResource _loc_4 =null ;
            _loc_2 = Global.world.getObjectsByTypes(.get(this.getWorldObjectType(this.m_harvestable.getTypeName())));
            double _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = HarvestableResource(_loc_2.get(_loc_3));
                if (_loc_4.getState() == HarvestableResource.STATE_WITHERED)
                {
                    _loc_4.setFullGrown();
                }
                _loc_3 = _loc_3 + 1;
            }
            return;
        }//end

        private int  getInstantUnwitherCashCost ()
        {
            Array _loc_3 =null ;
            _loc_1 = this.m_harvestable.getTypeName ();
            double _loc_2 =0;
            switch(_loc_1)
            {
                case "plot":
                case "ship":
                {
                    _loc_3 = this.getAllGoodsOfItemsByType(_loc_1);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_2 = this.getCropCost(_loc_3);
            if (this.m_experimentVariant == ExperimentDefinitions.UNWITHER_ALL_PRICE_1 && _loc_2 > 6)
            {
                _loc_2 = 6;
            }
            return _loc_2;
        }//end

        private int  getSingleUnwitherCost ()
        {
            Item _loc_1 =null ;
            double _loc_2 =0;
            Array _loc_3 =null ;
            double _loc_4 =0;
            _loc_5 = this.m_harvestable.getTypeName ();
            switch(_loc_5)
            {
                case "plot":
                case "ship":
                {
                    _loc_1 = this.m_harvestable.getHarvestingDefinition();
                    _loc_2 = Global.player.GetDooberMinimums(_loc_1, Doober.DOOBER_GOODS);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_3 = .get(_loc_2, 1);
            _loc_4 = this.getCropCost(_loc_3);
            return _loc_4;
        }//end

        private Array  getAllGoodsOfItemsByType (String param1 )
        {
            Item _loc_5 =null ;
            HarvestableResource _loc_8 =null ;
            _loc_2 = this.getWorldObjectType(param1 );
            _loc_3 =Global.world.getObjectsByTypes(.get(_loc_2) );
            double _loc_4 =0;
            double _loc_6 =0;
            int _loc_7 =0;
            while (_loc_7 < _loc_3.length())
            {

                _loc_8 = _loc_3.get(_loc_7);
                if (_loc_8.getState() == HarvestableResource.STATE_WITHERED)
                {
                    _loc_5 = _loc_8.getHarvestingDefinition();
                    _loc_4 = _loc_4 + Global.player.GetDooberMinimums(_loc_5, Doober.DOOBER_GOODS);
                    _loc_6 = _loc_6 + 1;
                }
                _loc_7++;
            }
            return .get(_loc_4, _loc_6);
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
                default:
                {
                    break;
                }
            }
            return Number.NaN;
        }//end

        private int  getCropCost (Array param1 )
        {
            int _loc_2 =0;
            _loc_3 = param1.get(0);
            _loc_4 = param1.get(1);
            _loc_5 = this.determineConstantK(_loc_3 );
            _loc_6 = this.determineExponentP(_loc_3 );
            _loc_7 = this.determineExponentQ(_loc_3 );
            _loc_2 = Math.max(Math.ceil(_loc_5 * Math.pow(_loc_3, _loc_6) + Math.pow(_loc_4, _loc_7)), 1);
            return _loc_2;
        }//end

        private double  determineExponentP (int param1 )
        {
            if (param1 < 1000)
            {
                return 0.75;
            }
            return 0.55;
        }//end

        private double  determineExponentQ (int param1 )
        {
            if (param1 < 1000)
            {
                return 0.6;
            }
            return 0.6;
        }//end

        private double  determineConstantK (int param1 )
        {
            switch(this.m_experimentVariant)
            {
                case 2:
                case 3:
                {
                }
                case 4:
                case 5:
                {
                }
                case 6:
                case 7:
                {
                }
                default:
                {
                    break;
                }
            }
            switch(this.m_experimentVariant)
            {
                case 2:
                case 3:
                {
                }
                case 4:
                case 5:
                {
                }
                case 6:
                case 7:
                {
                }
                default:
                {
                    break;
                }
            }
            return 0;
        }//end

        protected boolean  isSingleAll ()
        {
            switch(this.m_experimentVariant)
            {
                case ExperimentDefinitions.UNWITHER_SINGLE_ALL_PRICE_1:
                case ExperimentDefinitions.UNWITHER_SINGLE_ALL_PRICE_2:
                case ExperimentDefinitions.UNWITHER_SINGLE_ALL_PRICE_3:
                {
                    return true;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

    }




