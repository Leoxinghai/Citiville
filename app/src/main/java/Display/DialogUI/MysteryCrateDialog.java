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
import Events.*;
//import flash.utils.*;

    public class MysteryCrateDialog extends GenericDialog
    {
        private MysteryCrateDialogView m_dialogView ;
        private String m_itemNameToPlace ="";
        private Item m_winnigItem ;

        public  MysteryCrateDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true )
        {
            super(param1, param2, param3, this.onPlaceNowClose, param5, param6, param7, param8, param9, param10, param11);
            addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, true);
            return;
        }//end

        public String  itemNameToPlace ()
        {
            return this.m_itemNameToPlace;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.m_dialogView = new MysteryCrateDialogView(param1, m_message, m_dialogTitle, m_type, m_callback, m_icon, m_iconPos, "", null, m_customOk);
            return this.m_dialogView;
        }//end

        protected void  onPlaceNowClose (GenericPopupEvent event )
        {
            MarketEvent _loc_2 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, this.m_itemNameToPlace);
                _loc_2.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_2);
                UI.closeCatalog();
            }
            return;
        }//end

        public void  showWinnings (Item param1 )
        {
            int _loc_3 =0;
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            boolean _loc_2 =false ;
            switch(param1.type)
            {
                case "residence":
                {
                    _loc_3 = param1.populationBase;
                    _loc_4 = param1.populationType;
                    _loc_5 = Global.world.citySim.getTotalPopulation(_loc_4);
                    _loc_6 = Global.world.citySim.getPopulationCap(_loc_4);
                    _loc_2 = _loc_5 + _loc_3 > _loc_6 ? (false) : (true);
                    break;
                }
                case "business":
                {
                    _loc_7 = Math.floor(Global.world.citySim.getPopulationCap() * Global.gameSettings().getNumber("businessLimitByPopulationMax"));
                    _loc_2 = (Global.world.citySim.getTotalBusinesses() + 1) > _loc_7 ? (false) : (true);
                    break;
                }
                case "decoration":
                {
                    _loc_2 = true;
                }
                default:
                {
                    break;
                }
            }
            this.m_itemNameToPlace = param1.name;
            this.m_winnigItem = param1;
            this.m_dialogView.showWinnings(param1, _loc_2);
            return;
        }//end

    }


