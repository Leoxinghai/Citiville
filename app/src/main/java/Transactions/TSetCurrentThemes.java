package Transactions;

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
import Init.*;

    public class TSetCurrentThemes extends TWorldLoad
    {
        private String m_themeCollectionName ;
        private boolean m_enable ;

        public  TSetCurrentThemes (String param1 ,boolean param2 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            super(Global.player.uid);
            this.m_themeCollectionName = param1;
            this.m_enable = param2;
            _loc_3 =Global.gameSettings().getItemByName(this.m_themeCollectionName );
            if (_loc_3.endDate <= GameSettingsInit.getCurrentTime())
            {
                _loc_4 = ZLoc.t("Items", _loc_3.name + "_friendlyName");
                _loc_5 = ZLoc.t("Dialogs", "CatalogItemExpired", {itemName:_loc_3.localizedName});
                UI.displayMessage(_loc_5, GenericPopup.TYPE_OK);
            }
            else if (_loc_3.cost > 0)
            {
                Global.player.gold = Global.player.gold - _loc_3.cost;
            }
            else if (_loc_3.cash > 0)
            {
                Global.player.cash = Global.player.cash - _loc_3.cash;
            }
            else
            {
                GlobalEngine.msg(this.m_themeCollectionName + " cost nothing, make sure this is intentional behavior.");
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("WorldService.setCurrentThemes", this.m_themeCollectionName, this.m_enable);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Array _loc_2 =null ;
            super.onComplete(param1);
            if (UI.m_catalog == UI.m_newCatalog && this.m_themeCollectionName)
            {
                _loc_2 = new Array();
                _loc_2.push(this.m_themeCollectionName);
                UI.m_catalog.asset.updateElementsByItemNames(_loc_2);
            }
            else
            {
                UI.refreshCatalog(true);
            }
            Global.questManager.refreshAllQuests();
            return;
        }//end

    }



