package Display.hud;

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

import Display.*;
import Display.DialogUI.*;
//import flash.utils.*;
import Classes.sim.*;

    public class CommodityPopup extends NotifyPopup implements IPlayerStateObserver
    {
        public Class m_supplyTooltipAsset ;
        public CommodityPopup_m_supplyTooltipAsset m_supplyTooltip ;
        protected String m_commodityName ;
        public static  int CHANGE_THRESHOLD =2;

        public  CommodityPopup (String param1 ,Function param2 )
        {
            this.m_supplyTooltipAsset = CommodityPopup_m_supplyTooltipAsset;
            this.m_supplyTooltip =(CommodityPopup_m_supplyTooltipAsset) new CommodityPopup_m_supplyTooltipAsset();
            this.m_commodityName = param1;
            super(param2, null, 3000);
            Global.player.addObserver(this);
            return;
        }//end

         protected void  loadAssets ()
        {
            onAssetsLoaded();
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("bgAsset", this.m_supplyTooltip);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new CommodityPopupView(param1, this.m_commodityName);
        }//end

        public void  forceUpdateValue (int param1 )
        {
            CommodityPopupView _loc_2 =null ;
            if (m_jpanel instanceof CommodityPopupView)
            {
                _loc_2 =(CommodityPopupView) m_jpanel;
                _loc_2.forceUpdateValue(param1);
            }
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            showTimed();
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

    }



