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

import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class ComboEnergyDialogView extends GenericDialogView
    {
        private Function m_buyCallback ;
        private Function m_askCallback ;

        public  ComboEnergyDialogView (Dictionary param1 ,Function param2 ,Function param3 =null ,Function param4 =null )
        {
            super(param1);
            this.m_buyCallback = param2;
            this.m_askCallback = param3;
            m_closeCallback = param4;
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_8 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -25, SoftBoxLayout.TOP));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            m_titleString = "OutOfEnergyVisitMarket";
            m_message = ZLoc.t("Dialogs", "OutOfEnergyComboMessage");
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 0);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_6 = m_assetDict.get("energyDialog_energyGuy") ;
            AssetPane _loc_7 =new AssetPane(_loc_6 );
            ASwingHelper.setSizedBackground(_loc_5, _loc_6);
            _loc_4.append(_loc_5);
            ASwingHelper.setEasyBorder(_loc_4, 0, 0);
            textArea = createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_3.appendAll(_loc_4, textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(_loc_3);
            boolean _loc_9 =false ;
            _loc_3.mouseEnabled = false;
            _loc_3.mouseChildren = _loc_9;
            if (m_type != TYPE_MODAL)
            {
                _loc_8 = this.createButtonPanel();
                _loc_1.append(_loc_8);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            double _loc_2 =284;
            return _loc_2;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","GetNow"),null ,"GreenButtonUI");
            CustomButton _loc_3 =new CustomButton(ZLoc.t("Dialogs","AskFriends"),null ,"GreenButtonUI");
            _loc_2.addActionListener(this.closeWithBuy, 0, true);
            _loc_3.addActionListener(this.closeWithAsk, 0, true);
            _loc_1.appendAll(_loc_2, _loc_3);
            return _loc_1;
        }//end

        protected void  closeWithBuy (Object param1)
        {
            countDialogViewAction("GetNow");
            StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_ENABLED, "buyenergy");
            this.m_buyCallback(null);
            this.m_buyCallback = null;
            this.m_askCallback = null;
            m_closeCallback = null;
            closeMe();
            return;
        }//end

        protected void  closeWithAsk (Object param1)
        {
            countDialogViewAction("AskFriends");
            this.m_askCallback(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES_NOMARKET, true));
            StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_ENABLED, "askfriends");
            this.m_buyCallback = null;
            this.m_askCallback = null;
            m_closeCallback = null;
            closeMe();
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            countDialogViewAction("X");
            StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.OUT_OF_ENERGY, StatsPhylumType.ENERGY_COMBO_ENABLED, "closed");
            this.m_buyCallback = null;
            this.m_askCallback = null;
            m_closeCallback = null;
            closeMe();
            return;
        }//end

    }



