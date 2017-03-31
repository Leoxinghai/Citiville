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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.*;
import Events.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class StorageDialogView extends GenericDialogView
    {
        protected Array marketCells ;
        public static  int NUM_MARKET_CELLS =3;

        public  StorageDialogView (Dictionary param1 ,String param2 )
        {
            super(param1, ZLoc.t("Dialogs", param2 + "_message"), param2, TYPE_OK, null, "none", GenericDialogView.ICON_POS_BOTTOM);
            return;
        }//end

         protected JPanel  createIconPane ()
        {
            MarketCell _loc_4 =null ;
            int _loc_5 =0;
            Item _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.marketCells = new Array();
            _loc_2 = Global.gameSettings().getBuyableItems("storage");
            int _loc_3 =0;
            while (_loc_3 < NUM_MARKET_CELLS)
            {

                _loc_4 = new MarketCell();
                _loc_5 = MathUtil.randomIndex(_loc_2);
                _loc_6 = _loc_2.get(_loc_5);
                _loc_2.splice(_loc_5, 1);
                _loc_4.setAssetDict(m_assetDict);
                _loc_4.setCellValue(_loc_6);
                _loc_4.setBuyable(false);
                _loc_4.addEventListener(DataItemEvent.MARKET_BUY, this.onBuy, false, 0, true);
                _loc_1.append(_loc_4);
                if ((_loc_3 + 1) < NUM_MARKET_CELLS)
                {
                    _loc_1.append(ASwingHelper.horizontalStrut(30));
                }
                _loc_3++;
            }
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JButton _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (!Global.isVisiting())
            {
                _loc_3 = new CustomButton(ZLoc.t("Dialogs", m_titleString + "_accept"), null, "GreenButtonUI");
                _loc_3.addEventListener(MouseEvent.MOUSE_UP, this.onOpenClick, false, 0, true);
                _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(5));
            }
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","Cancel"),null ,"RedButtonUI");
            _loc_2.addEventListener(MouseEvent.MOUSE_UP, this.onCancelClick, false, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected void  onOpenClick (MouseEvent event )
        {
            countDialogViewAction(ZLoc.t("Dialogs", m_titleString + "_accept"));
            UI.displayCatalog(new CatalogParams("farming"));
            closeMe();
            return;
        }//end

        protected void  onCancelClick (MouseEvent event )
        {
            countDialogViewAction(ZLoc.t("Dialogs", "Cancel"));
            closeMe();
            return;
        }//end

        protected void  onBuy (DataItemEvent event )
        {
            closeMe();
            MarketEvent _loc_2 =new MarketEvent(MarketEvent.MARKET_BUY ,MarketEvent.GENERIC ,event.item.name );
            UI.onMarketClick(_loc_2);
            return;
        }//end

    }




