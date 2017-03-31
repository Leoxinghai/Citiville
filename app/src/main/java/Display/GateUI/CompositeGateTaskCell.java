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
import Classes.gates.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import com.adobe.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class CompositeGateTaskCell extends JPanel
    {
        protected Object m_data ;
        protected AbstractGate m_gate ;
        protected int m_gateIndex ;
        protected Dictionary m_assetDict ;
        protected Class m_checkMark ;
        protected Class m_checkList ;
        protected Dictionary m_buttonsListenerRefDict ;
        protected GenericDialogView m_parentView ;
        protected JPanel m_iconComponent ;
        protected int m_textWidth =320;
        protected JPanel m_taskTextComponent ;
        protected int m_cellWidth ;
        protected JPanel m_leftHolder ;
        private String m_skipButtonLocKey ;
public static  int TASK_TEXT_WIDTH =320;
        private static boolean m_buyConfirmationNeeded =true ;

        public  CompositeGateTaskCell (Object param1 ,int param2 ,AbstractGate param3 ,Dictionary param4 ,GenericDialogView param5 ,int param6 ,String param7 )
        {
            super(new BorderLayout());
            this.m_data = param1;
            this.m_gateIndex = param2;
            this.m_gate = param3;
            this.m_assetDict = param4;
            this.m_parentView = param5;
            this.m_cellWidth = param6;
            this.m_skipButtonLocKey = param7;
            this.m_buttonsListenerRefDict = new Dictionary();
            this.init();
            this.makeCell();
            return;
        }//end

        protected void  init ()
        {
            this.m_checkMark = this.m_assetDict.get("checkMark");
            this.m_checkList = this.m_assetDict.get("checkList");
            return;
        }//end

        protected void  makeCell ()
        {
            ASwingHelper.setForcedWidth(this, this.m_cellWidth);
            this.m_leftHolder = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            this.m_leftHolder.append(ASwingHelper.horizontalStrut(5));
            this.makeIconComponent();
            this.makeTextComponent();
            this.append(this.m_leftHolder, BorderLayout.WEST);
            this.makeMiscComponent();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeIconComponent ()
        {
            _loc_1 = this.m_assetDict.get("gateInfo").get(this.m_gate).icon.content;
            int _loc_4 =60;
            _loc_1.height = 60;
            _loc_1.width = _loc_4;
            if (_loc_1 instanceof Bitmap)
            {
                ((Bitmap)_loc_1).smoothing = true;
            }
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            this.m_iconComponent = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3.append(_loc_2);
            this.m_iconComponent.append(_loc_3);
            ASwingHelper.prepare(this.m_iconComponent);
            this.m_leftHolder.append(this.m_iconComponent);
            return;
        }//end

        protected void  makeTextComponent ()
        {
            this.m_taskTextComponent = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.updateTextComponent();
            this.m_leftHolder.append(this.m_taskTextComponent);
            return;
        }//end

        protected void  makeMiscComponent ()
        {
            DisplayObject _loc_4 =null ;
            int _loc_5 =0;
            double _loc_6 =0;
            AssetIcon _loc_7 =null ;
            String _loc_8 =null ;
            CustomButton _loc_9 =null ;
            CustomButton _loc_10 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            boolean _loc_2 =false ;
            if (this.m_gate.checkForKeys())
            {
                _loc_4 =(DisplayObject) new this.m_checkMark();
                _loc_1.append(new AssetPane(_loc_4));
                _loc_5 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TIKI_SOCIAL);
                if (_loc_5 > 0 && _loc_5 < 4)
                {
                    StatsManager.count(StatsKingdomType.DIALOG_STATS, "pre_build", this.m_gate.name, this.m_gate.unlockItemName);
                }
                else
                {
                    StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "pre_build", this.m_gate.name, this.m_gate.unlockItemName);
                }
            }
            else
            {
                if (this.m_gate instanceof InventoryGate)
                {
                    _loc_10 = new CustomButton(ZLoc.t("Dialogs", "HotelDialog_askFriends"), null, "GreenSmallButtonUI");
                    _loc_10.setPreferredHeight(30);
                    _loc_10.setMargin(new Insets(2, 10, 0, 10));
                    _loc_10.addActionListener(this.openPartsMenu);
                    _loc_1.append(_loc_10);
                }
                _loc_6 = this.m_gate.unlockCost;
                _loc_7 = new AssetIcon(new (DisplayObject)EmbeddedArt.icon_cash());
                _loc_8 = ZLoc.t("Dialogs", this.m_skipButtonLocKey, {price:_loc_6});
                _loc_9 = new CustomButton(_loc_8, _loc_7, "CashButtonUI");
                _loc_9.setPreferredHeight(30);
                _loc_9.setMargin(new Insets(2, 10, 0, 10));
                this.m_buttonsListenerRefDict.put(_loc_9,  this.makePurchaseCallback(this.m_gateIndex, _loc_6));
                _loc_9.addActionListener(this.m_buttonsListenerRefDict.get(_loc_9));
                _loc_1.append(_loc_9);
            }
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            _loc_3.appendAll(_loc_1, ASwingHelper.horizontalStrut(30));
            this.append(_loc_3, BorderLayout.EAST);
            return;
        }//end

        protected Function  makePaymentConfirmationCallback (int param1 ,int param2 )
        {
            gateIndex = param1;
            cashCost = param2;
            return void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    CompositeGateTaskCell.setBuyConfirmationNeeded = false;
                    if (Global.player.cash < cashCost)
                    {
                        cleanUp();
                        m_parentView.dispatchEvent(new Event(Event.CLOSE, false, false));
                        UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                    }
                    else
                    {
                        fireSkipStat(m_gate);
                        m_gate.buyAll();
                        invalidateCell();
                    }
                }
                return;
            }//end
            ;
        }//end

        protected Function  makePurchaseCallback (int param1 ,int param2 )
        {
            gateIndex = param1;
            cashCost = param2;
            return void  (AWEvent event )
            {
                _loc_2 = undefined;
                m_parentView.countDialogViewAction("PURCHASE", false);
                if (Global.player.cash < cashCost)
                {
                    cleanUp();
                    m_parentView.dispatchEvent(new Event(Event.CLOSE, false, false));
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                }
                else
                {
                    if (CompositeGateTaskCell.isBuyConfirmationNeeded)
                    {
                        _loc_2 = {};
                        _loc_2.put("amount",  cashCost);
                        UI.displayMessage(ZLoc.t("Dialogs", "LandmarkCashConfirmation", _loc_2), GenericDialogView.TYPE_YESNO, makePaymentConfirmationCallback(gateIndex, cashCost), "", true);
                        return;
                    }
                    fireSkipStat(m_gate);
                    m_gate.buyAll();
                    invalidateCell();
                }
                return;
            }//end
            ;
        }//end

        private void  fireSkipStat (AbstractGate param1 )
        {
            MasteryGate _loc_2 =null ;
            if (param1 instanceof InventoryGate)
            {
                StatsManager.count(StatsKingdomType.DIALOG_STATS, "multi_gate", "skip_buildables", this.m_gate.unlockItemName);
            }
            else if (param1 instanceof MasteryGate)
            {
                _loc_2 =(MasteryGate) param1;
                switch(_loc_2.masteryType)
                {
                    case MasteryGate.BUS_MODE:
                    {
                        StatsManager.count(StatsKingdomType.DIALOG_STATS, "multi_gate", "skip_bus_mastery", this.m_gate.unlockItemName);
                        return;
                    }
                    case MasteryGate.CROP_MODE:
                    {
                        StatsManager.count(StatsKingdomType.DIALOG_STATS, "multi_gate", "skip_crop_mastery", this.m_gate.unlockItemName);
                        return;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        private void  openPartsMenu (AWEvent event )
        {
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "multi_gate", "ask_for_parts", this.m_gate.unlockItemName);
            _loc_2 = (InventoryGate)this.m_gate
            _loc_2.setDisplayProperties(false, false);
            dispatchEvent(new Event(Event.CLOSE, false, false));
            Global.ui.addEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.onPartsUpdate);
            _loc_2.displayGate(null, ZLoc.t("Dialogs", "HotelDialog_askFriends"));
            return;
        }//end

        private void  onPartsUpdate (GenericObjectEvent event )
        {
            this.invalidateCell();
            return;
        }//end

        public void  cleanUp ()
        {
            CustomButton _loc_2 =null ;
            Global.ui.removeEventListener(GenericObjectEvent.BUILDING_UPGRADE, this.onPartsUpdate);
            _loc_1 = DictionaryUtil.getKeys(this.m_buttonsListenerRefDict);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_2.removeActionListener(this.m_buttonsListenerRefDict.get(_loc_2));
            }
            return;
        }//end

        public void  invalidateCell ()
        {
            this.cleanUp();
            this.removeAll();
            this.makeCell();
            dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "", true));
            return;
        }//end

        public void  updateTextComponent ()
        {
            AssetPane _loc_2 =null ;
            this.m_taskTextComponent.removeAll();
            _loc_1 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",this.m_gate.locKey),this.m_textWidth,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,18,EmbeddedArt.brownTextColor);
            this.m_taskTextComponent.append(_loc_1);
            if (this.m_gate.subLocKey)
            {
                _loc_2 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", this.m_gate.subLocKey), this.m_textWidth, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 14, EmbeddedArt.orangeTextColor);
                this.m_taskTextComponent.append(_loc_2);
            }
            ASwingHelper.prepare(this.m_taskTextComponent);
            return;
        }//end

        public JPanel  iconComponent ()
        {
            return this.m_iconComponent;
        }//end

        public int  taskTextWidth ()
        {
            return this.m_textWidth;
        }//end

        public void  taskTextWidth (int param1 )
        {
            this.m_textWidth = param1;
            return;
        }//end

        public Dictionary  buttonsListenerRefDict ()
        {
            return this.m_buttonsListenerRefDict;
        }//end

        public static boolean  isBuyConfirmationNeeded ()
        {
            return m_buyConfirmationNeeded;
        }//end

        public static void  setBuyConfirmationNeeded (boolean param1 )
        {
            m_buyConfirmationNeeded = param1;
            return;
        }//end

    }




