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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
//import flash.events.*;

    public class OpenMarketFooter extends BasicButtonFooter
    {

        public  OpenMarketFooter (GenericDialogView param1 ,String param2 )
        {
            super(param1, param2, ZLoc.t("Dialogs", "OpenMarket"));
            return;
        }//end  

         protected void  onButtonClick (Event event )
        {
            Sounds.playFromSet(Sounds.SET_CLICK);
            m_dialogView.countDialogViewAction("OPEN_MARKET");
            m_dialogView.close();
            _loc_2 = m_type.split("|");
            CatalogParams _loc_3 =null ;
            if (_loc_2.length > 1)
            {
                if (_loc_2.get(1).length > 0)
                {
                    _loc_3 = new CatalogParams(_loc_2.get(0), _loc_2.get(1));
                }
                else
                {
                    _loc_3 = new CatalogParams(_loc_2.get(0));
                }
            }
            else
            {
                _loc_3 = new CatalogParams(m_type);
            }
            if (_loc_2.length == 3)
            {
                _loc_3.setItemName(_loc_2.get(2));
            }
            UI.displayCatalog(_loc_3);
            return;
        }//end  

    }



