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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
//import Modules.quest.Display.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.Component;
import org.aswing.AssetPane;
import org.aswing.AsWingConstants;
import org.aswing.Container;
import org.aswing.JPanel;
import Modules.quest.Display.QuestPopupView;

    public class PlaceNowFromInventory implements ITaskFooter
    {
        protected String m_type ;
        protected GenericDialogView m_dialogView ;
        protected Component m_inventoryList ;
public static  int ACTION_EVENT_PRIORITY =1;
public static  int CLEANUP_EVENT_PRIORITY =0;

        public  PlaceNowFromInventory (GenericDialogView param1 ,String param2 )
        {
            this.m_type = param2;
            this.m_dialogView = param1;
            this.m_dialogView.addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, ACTION_EVENT_PRIORITY, true);
            return;
        }//end

        public Component  getComponent ()
        {
            Component _loc_1 =null ;
            if (Global.player.inventory.getItemCountByName(this.m_type) > 0)
            {
                _loc_1 = this.getFooterComponent();
                this.m_dialogView.addEventListener(Event.CLOSE, this.onClose, false, CLEANUP_EVENT_PRIORITY);
            }
            return _loc_1;
        }//end

        public Component  getFooterComponent ()
        {
            Container _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.RIGHT);
            _loc_2 =Global.gameSettings().getItemByName(this.m_type );
            this.m_inventoryList = new MarketCellCustomText(ZLoc.t("Dialogs", "Place"), null, true);
            (this.m_inventoryList as MarketCell).setCellValue(_loc_2);
            (this.m_inventoryList as MarketCell).setBuyable(true);
            this.m_inventoryList.addEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick, false, ACTION_EVENT_PRIORITY);
            _loc_3 =(Class) this.m_dialogView.assetDict.get( "placeNowArrow");
            DisplayObject _loc_4 =(DisplayObject)new _loc_3;
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            AssetPane _loc_6 =new AssetPane(_loc_4 );
            _loc_5.append(_loc_6);
            _loc_5.append(ASwingHelper.verticalStrut(5));
            _loc_1.append(this.m_inventoryList);
            _loc_1.append(ASwingHelper.horizontalStrut(-10));
            _loc_1.append(_loc_5);
            return _loc_1;
        }//end

        public void  onContainerClick (DataItemEvent event )
        {
            this.m_dialogView.countDialogViewAction("PLACE");
            _loc_2 = event.item ;
            this.placeItem(_loc_2);
            this.m_dialogView.close();
            this.removeListeners();
            return;
        }//end

        protected void  onButtonClick (Event event )
        {
            this.placeItem(Global.gameSettings().getItemByName(this.m_type));
            this.removeListeners();
            return;
        }//end

        protected void  onClose (Event event )
        {
            this.removeListeners();
            return;
        }//end

        protected void  placeItem (Item param1 )
        {
            Sounds.playFromSet(Sounds.SET_CLICK);
            _loc_2 = param1? (param1) : (null);
            Market_loc_3 = event.build(MarketEvent.MARKET_BUY ,_loc_2 ,MarketEvent.SOURCE_INVENTORY );
            this.m_dialogView.dispatchEvent(_loc_3);
            return;
        }//end

        protected void  removeListeners ()
        {
            if (this.m_inventoryList)
            {
                this.m_inventoryList.removeEventListener(DataItemEvent.MARKET_BUY, this.onContainerClick);
            }
            if (this.m_dialogView)
            {
                this.m_dialogView.removeEventListener(Event.CLOSE, this.onClose);
                this.m_dialogView.removeEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick);
            }
            _loc_1 =(QuestPopupView) this.m_dialogView;
            if (_loc_1 != null)
            {
                _loc_1.okayButton.removeActionListener(this.onButtonClick);
            }
            return;
        }//end

    }



