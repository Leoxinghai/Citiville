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
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class GetCashDialogView extends GenericDialogView
    {
        private int m_experimentVariant ;
        private Item m_dataItem ;

        public  GetCashDialogView (Dictionary param1 ,Item param2 )
        {
            this.m_experimentVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OUT_OF_CASH_SALE);
            this.m_dataItem = param2;
            super(param1, ZLoc.t("Dialogs", "limited_time_sale_title"), "limited_time_sale", TYPE_OK, null, "", GenericDialogView.ICON_POS_BOTTOM);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            MarginBackground _loc_5 =null ;
            JTextField _loc_9 =null ;
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,5);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            Object _loc_6 ={};
            _loc_7 = this.determinePackageDiscount();
            DisplayObject _loc_8 =new EmbeddedArt.cashSaleBackground ()as DisplayObject ;
            _loc_5 = new MarginBackground(_loc_8, new Insets(0, 0, 0, 0));
            _loc_4.setBackgroundDecorator(_loc_5);
            _loc_4.setPreferredSize(new IntDimension(_loc_8.width, _loc_8.height));
            _loc_4.setMinimumSize(new IntDimension(_loc_8.width, _loc_8.height));
            _loc_4.setMaximumSize(new IntDimension(_loc_8.width, _loc_8.height));
            _loc_9 = ASwingHelper.makeTextField("-" + _loc_7 + "%", EmbeddedArt.titleFont, 40, EmbeddedArt.whiteTextColor, 3);
            _loc_9.filters = EmbeddedArt.toolTipNormalTitleFilters;
            TextFormat _loc_10 =new TextFormat ();
            _loc_10.size = 50;
            TextFieldUtil.formatSmallCaps(_loc_9.getTextField(), _loc_10);
            _loc_4.append(_loc_9);
            _loc_9.getTextField().height = 40 * 1.5;
            _loc_6.put("percentage",  _loc_7);
            _loc_11 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","limited_time_sale_text_body"),param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,18,EmbeddedArt.brownTextColor);
            _loc_12 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","limited_time_sale_discount_text",_loc_6),param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,18,EmbeddedArt.dialogHintColor);
            _loc_3.appendAll(_loc_11, ASwingHelper.horizontalStrut(15), _loc_12);
            _loc_2.appendAll(_loc_4, ASwingHelper.horizontalStrut(15), _loc_3);
            return _loc_2;
        }//end

        private int  determinePackageDiscount ()
        {
            switch(this.m_experimentVariant)
            {
                case ExperimentDefinitions.OUT_OF_CASH_SALE_TWO:
                {
                    return 20;
                }
                case ExperimentDefinitions.OUT_OF_CASH_SALE_THREE:
                {
                    return 30;
                }
                case ExperimentDefinitions.OUT_OF_CASH_SALE_FOUR:
                {
                    return 40;
                }
                case ExperimentDefinitions.OUT_OF_CASH_SALE_FIVE:
                {
                    return 50;
                }
                default:
                {
                    break;
                }
            }
            return 0;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","limited_time_sale_button"),new AssetIcon(new m_assetDict.get( "icon_cash") ),"BigCashButtonUI");
            _loc_2.addEventListener(MouseEvent.MOUSE_UP, this.sendOffToMoneyPage, false, 0, true);
            _loc_1.appendAll(ASwingHelper.verticalStrut(15), _loc_2);
            return _loc_1;
        }//end

        public void  sendOffToMoneyPage (Event event )
        {
            if (Global.player.hasSaleTransactionCompleted == true)
            {
                StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, "out_of_cash_sale", "clicked");
                GlobalEngine.socialNetwork.redirect("money.php?ref=getCashDia");
            }
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, "out_of_cash_sale", "closed");
            closeMe();
            return;
        }//end

    }



