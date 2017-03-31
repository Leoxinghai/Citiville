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

import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class DailyDripDialog extends GenericDialog
    {
        protected int m_numAssetLoads =0;
        protected int m_imagesLoaded =0;
        protected Object m_dripAssetObject ;
        protected Loader m_loader ;

        public  DailyDripDialog ()
        {
            Function _loc_1 =null ;
            boolean _loc_2 =true ;
            Function _loc_3 =null ;
            String _loc_4 ="DailyDrip";
            _loc_5 = ZLoc.t("Dialogs","DailyDripMessage");
            super(_loc_5, "", GenericDialogView.TYPE_OK, _loc_1, _loc_4, "", _loc_2, 0, "", _loc_3);
            return;
        }//end

         protected void  loadAssets ()
        {
            this.m_numAssetLoads = 1;
            _loc_1 = Global.getAssetURL("assets/dialogs/DailyDripAssets.swf");
            this.m_loader = LoadingManager.load(_loc_1, this.makeDripAssets, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        protected void  makeDripAssets (Event event )
        {
            _loc_2 = (Loader)event.target.loader
            m_comObject = _loc_2.content;
            this.onAssetsLoaded(event);
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
            _loc_1.put("dialog_bg",  new m_comObject.drip_bg());
            m_assetBG = _loc_1.get("dialog_bg");
            _loc_1.put("check_mark",  m_comObject.drip_iconGreenCheck);
            _loc_1.put("cell_bg",  m_comObject.drip_buttonBlue);
            _loc_1.put("cell_bg_ready",  m_comObject.drip_buttonGreen);
            _loc_1.put("itemIcon",  new Array());
            _loc_1.get("itemIcon").put(0,  m_comObject.drip_iconCrew);
            _loc_1.get("itemIcon").put(1,  m_comObject.drip_iconEnergy);
            _loc_1.get("itemIcon").put(2,  m_comObject.drip_iconMystery);
            _loc_1.put("itemLabel",  new Array());
            _loc_1.get("itemLabel").put(0,  ZLoc.t("Dialogs", "DailyDripCrew"));
            _loc_1.get("itemLabel").put(1,  ZLoc.t("Dialogs", "DailyDripEnergy"));
            _loc_1.get("itemLabel").put(2,  ZLoc.t("Dialogs", "DailyDripMystery"));
            _loc_1.put("rewardType",  new Array());
            _loc_1.get("rewardType").put(0,  "building");
            _loc_1.get("rewardType").put(1,  "energy");
            _loc_1.get("rewardType").put(2,  "mystery");
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            _loc_2 = ZLoc.t("Dialogs","DailyDripSubtitle");
            DailyDripDialogView _loc_3 =new DailyDripDialogView(param1 ,m_message ,m_dialogTitle ,_loc_2 ,m_callback ,m_SkipCallback );
            return _loc_3;
        }//end

    }



