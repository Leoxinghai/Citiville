package Modules.quest.Display.TaskFooters;

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
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
import org.aswing.*;

    public class PlaceNowFromInventoryNoPic extends PlaceNowFromInventory
    {

        public  PlaceNowFromInventoryNoPic (String param1 ,GenericDialogView param2 )
        {
            super(param2, param1);
            return;
        }//end

         public Component  getFooterComponent ()
        {
            Container _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.RIGHT);
            _loc_2 =Global.gameSettings().getItemByName(m_type );
            m_inventoryList = new MarketCellCustomText(ZLoc.t("Dialogs", "Place"), null, true);
            ((MarketCell)m_inventoryList).setImageVisibility(false);
            ((MarketCell)m_inventoryList).setCellValue(_loc_2);
            ((MarketCell)m_inventoryList).setBuyable(true);
            m_inventoryList.addEventListener(DataItemEvent.MARKET_BUY, onContainerClick, false, ACTION_EVENT_PRIORITY);
            _loc_3 =(Class) m_dialogView.assetDict.get( "placeNowArrow");
            DisplayObject _loc_4 =(DisplayObject)new _loc_3;
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            AssetPane _loc_6 =new AssetPane(_loc_4 );
            _loc_5.append(_loc_6);
            _loc_5.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(m_inventoryList);
            _loc_1.append(ASwingHelper.horizontalStrut(-10));
            _loc_1.append(_loc_5);
            return _loc_1;
        }//end

    }



