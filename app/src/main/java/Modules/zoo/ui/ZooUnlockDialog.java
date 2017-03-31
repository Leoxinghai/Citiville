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
import Display.*;
import Display.DialogUI.*;
import Events.*;

    public class ZooUnlockDialog extends GenericDialog
    {
        protected MechanicMapResource m_spawner ;

        public  ZooUnlockDialog (MechanicMapResource param1 )
        {
            this.m_spawner = param1;
            _loc_2 = ZLoc.t("Items",this.m_spawner.getItem ().unlocksItem +"_friendlyName");
            _loc_3 = ZLoc.t("Dialogs","ZooUnlock_ok");
            _loc_4 =Global.gameSettings().getItemByName(this.m_spawner.getItem ().unlocksItem ).iconRelative ;
            super(ZLoc.t("Dialogs", "ZooUnlock_message", {item:_loc_2}), "ZooUnlock", GenericDialogView.TYPE_CUSTOM_OK, this.onZooUnlockClose, "ZooUnlock", _loc_4, true, GenericDialogView.ICON_POS_LEFT, "", null, _loc_3);
            addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, true);
            return;
        }//end

        protected void  onZooUnlockClose (GenericPopupEvent event )
        {
            MarketEvent _loc_2 =null ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, this.m_spawner.getItem().unlocksItem);
                _loc_2.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_2);
                if (ZooDialogView.currentInstance)
                {
                    ZooDialogView.currentInstance.close();
                }
            }
            return;
        }//end

    }



