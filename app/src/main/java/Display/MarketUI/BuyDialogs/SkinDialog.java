package Display.MarketUI.BuyDialogs;

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
import Modules.remodel.*;
//import flash.utils.*;

    public class SkinDialog extends GenericDialog
    {
        protected Catalog m_catalog ;
        protected Item m_item ;

        public  SkinDialog (Catalog param1 ,Item param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            this.m_catalog = param1;
            this.m_item = param2;
            _loc_5 =Global.gameSettings().getItemByName(this.m_item.derivedItemName );
            if (RemodelManager.hasRemodelEligibleResidence(this.m_item))
            {
                if (this.m_item.derivedItemName == "res_simpsonmegabrick")
                {
                    _loc_3 = ZLoc.t("Dialogs", "skin_message_simpsonsmega", {item_name:_loc_5.localizedName});
                    _loc_4 = "skin_remodel";
                }
                else
                {
                    _loc_3 = ZLoc.t("Dialogs", "skin_message", {item_name:_loc_5.localizedName});
                    _loc_4 = "skin";
                }
            }
            else if (this.m_item.derivedItemName == "res_simpsonmegabrick" || this.m_item.derivedItemName == "res_beachfrontapt_a")
            {
                _loc_3 = ZLoc.t("Dialogs", "skin_buy_simpsonsmega", {item_name:_loc_5.localizedName});
                _loc_4 = "skin";
            }
            else
            {
                _loc_4 = "skin_buy";
                _loc_3 = ZLoc.t("Dialogs", "skin_buymessage", {item_name:_loc_5.localizedName});
            }
            super(_loc_3, "skin", 0, null, _loc_4);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            param1.put("catalog",  this.m_catalog);
            param1.put("item",  this.m_item);
            m_icon = Global.gameSettings().getImageByName(this.m_item.name, this.m_item.iconImageName);
            SkinDialogView _loc_2 =new SkinDialogView(param1 ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,m_feedShareViralType ,m_SkipCallback ,m_customOk ,false );
            return _loc_2;
        }//end

    }



