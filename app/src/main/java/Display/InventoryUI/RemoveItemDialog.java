package Display.InventoryUI;

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
import Display.DialogUI.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class RemoveItemDialog extends GenericDialog
    {
        protected String m_itemName ;
        protected int m_numAssetLoads =3;
        protected int m_imagesLoaded =0;
        protected Object m_genAssetObject ;
        protected Loader m_loader ;

        public  RemoveItemDialog (String param1 ,Function param2 ,boolean param3 =true ,Function param4 =null )
        {
            this.m_itemName = param1;
            _loc_5 = ZLoc.t("Items",param1 +"_friendlyName");
            _loc_6 = ZLoc.t("Dialogs","RemoveItemTitle",{itemName _loc_5 });
            _loc_7 = ZLoc.t("Dialogs","RemoveItemMessage",{numItem itemName 1,});
            super(_loc_7, "", GenericDialogView.TYPE_REMOVECANCEL, param2, _loc_6, "", param3, 0, "", param4);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, this.makeGenAssets);
            Global.delayedAssets.get(DelayedAssetLoader.INVENTORY_ASSETS, makeAssets);
            _loc_1 =Global.gameSettings().getImageByName(this.m_itemName ,"icon");
            this.m_loader = LoadingManager.load(_loc_1, this.onAssetsLoaded, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        protected void  makeGenAssets (DisplayObject param1 ,String param2 )
        {
            this.m_genAssetObject = param1;
            this.onAssetsLoaded();
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_numAssetLoads)
            {
                return;
            }
            super.onAssetsLoaded(event);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg", (DisplayObject) new this.m_genAssetObject.dialog_bg_noclose());
            m_assetBG = _loc_1.get("dialog_bg");
            _loc_1.put("countBG",  m_comObject.countBG);
            _loc_1.put("countMaxBG",  m_comObject.countMaxBG);
            _loc_1.put("cell_bg",  m_comObject.inventoryCard);
            _loc_1.put("btn_minus_up",  m_comObject.btn_minus_up);
            _loc_1.put("btn_minus_over",  m_comObject.btn_minus_over);
            _loc_1.put("btn_minus_down",  m_comObject.btn_minus_down);
            _loc_1.put("btn_plus_up",  m_comObject.btn_plus_up);
            _loc_1.put("btn_plus_over",  m_comObject.btn_plus_over);
            _loc_1.put("btn_plus_down",  m_comObject.btn_plus_down);
            _loc_1.put("itemIcon",  this.m_loader.content);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            RemoveItemDialogView _loc_2 =new RemoveItemDialogView(param1 ,this.m_itemName ,m_message ,m_dialogTitle ,m_callback ,m_SkipCallback );
            return _loc_2;
        }//end

    }



