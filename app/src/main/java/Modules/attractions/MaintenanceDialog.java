package Modules.attractions;

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
import Display.DialogUI.*;
//import flash.display.*;
//import flash.utils.*;

    public class MaintenanceDialog extends GenericDialog
    {
        private MechanicMapResource m_spawner ;
        protected String m_consumableName ;
        protected int m_numRequired ;
        protected int m_finishWithCash ;
        protected Function m_onAskFriends ;
        protected Function m_onUseConsumables ;
        protected Function m_onFinishWithCash ;
        protected String m_customRewardIcon ;
        protected String m_customRewardCaption ;
        protected String m_customNpcUrl ;

        public  MaintenanceDialog (MechanicMapResource param1 ,String param2 ,int param3 ,int param4 ,Function param5 ,Function param6 ,Function param7 ,String param8 ="",String param9 ="",String param10 ="",String param11 ="")
        {
            this.m_spawner = param1;
            this.m_consumableName = param2;
            this.m_numRequired = param3;
            this.m_finishWithCash = param4;
            this.m_onAskFriends = param5;
            this.m_onUseConsumables = param6;
            this.m_onFinishWithCash = param7;
            this.m_customRewardIcon = param9;
            this.m_customRewardCaption = param10;
            this.m_customNpcUrl = param11;
            String _loc_12 ="Dialog1";
            int _loc_13 =0;
            String _loc_14 ="maintenance_dialog";
            super(param8, _loc_12, _loc_13, null, _loc_14);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =.get(DelayedAssetLoader.ATTRACTIONS_ASSETS) ;
            if (this.m_customNpcUrl != "")
            {
                _loc_1.push(this.m_customNpcUrl);
            }
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            MaintenanceDialogView _loc_2 =new MaintenanceDialogView(this.m_spawner ,this.m_consumableName ,this.m_numRequired ,this.m_finishWithCash ,this.m_onAskFriends ,this.m_onUseConsumables ,this.m_onFinishWithCash ,param1 ,m_dialogTitle ,m_message ,this.m_customRewardIcon ,this.m_customRewardCaption );
            return _loc_2;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.ATTRACTIONS_ASSETS) ;
            _loc_1.put("attractions_blueBox",  _loc_2.attractions_blueBox);
            _loc_1.put("attractions_citySam", (DisplayObject) new _loc_2.attractions_citySam());
            _loc_1.put("attractions_clockDetailBg",  _loc_2.attractions_clockDetailBg);
            _loc_1.put("attractions_dialogBox",  _loc_2.attractions_dialogBox);
            _loc_1.put("attractions_dialogBoxDivider",  _loc_2.attractions_dialogBoxDivider);
            _loc_1.put("attractions_diamondImage",  _loc_2.attractions_diamondImage);
            _loc_1.put("attractions_infographic",  _loc_2.attractions_infographic);
            _loc_1.put("attractions_moduleBG",  _loc_2.attractions_moduleBG);
            _loc_1.put("attractions_whiteBottomBox",  _loc_2.attractions_whiteBottomBox);
            _loc_1.put("attractions_wrench",  _loc_2.attractions_wrench);
            _loc_1.put("attractions_wrenchDetailBox",  _loc_2.attractions_wrenchDetailBox);
            _loc_1.put("maintenance_checkbox",  _loc_2.maintenance_checkbox);
            _loc_1.put("maintenance_checkmark",  _loc_2.maintenance_checkmark);
            if (this.m_customNpcUrl != "")
            {
                _loc_1.put("attractions_citySam", (DisplayObject) m_assetDependencies.get(this.m_customNpcUrl));
            }
            return _loc_1;
        }//end

         public void  countDialogAction (String param1 ="view",int param2 =1,String param3 ="",String param4 ="",String param5 ="")
        {
            super.countDialogAction(param1, param2, "wonder", this.m_spawner.getItemName(), "get_keys");
            return;
        }//end

    }



