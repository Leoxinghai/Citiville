package Modules.zoo.ui;

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
//import flash.events.*;
import org.aswing.*;

    public class ZooPlaceFromInventoryModule extends JPanel
    {
        private JWindow m_win ;
        private MarketCellCustomText m_marketCell ;
        private Item m_itemDefinition ;

        public  ZooPlaceFromInventoryModule (String param1 )
        {
            super(null);
            this.m_itemDefinition = Global.gameSettings().getItemByName(param1);
            this.m_marketCell = new MarketCellCustomText(ZLoc.t("Dialogs", "Place"));
            this.m_marketCell.setCellValue(this.m_itemDefinition);
            this.m_marketCell.addEventListener(DataItemEvent.MARKET_BUY, this.onMarketCellClick, false, 0);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_2.append(this.m_marketCell);
            append(_loc_2);
            addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, true);
            return;
        }//end

        private void  onMarketCellClick (DataItemEvent event )
        {
            MarketEvent _loc_3 =null ;
            Sounds.playFromSet(Sounds.SET_CLICK);
            _loc_2 = event.item ? (event.item) : (null);
            if (_loc_2)
            {
                _loc_3 = new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, _loc_2.name);
                _loc_3.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_3);
            }
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

    }



