package Display.HunterAndPreyUI;

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
import Modules.bandits.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class PreyEscapedDialog extends GenericDialog
    {
        protected PreyData m_bandit ;
        protected Loader m_iconLoader ;
        protected int m_numAssetLoads =2;
        protected int m_imagesLoaded =0;
        protected boolean m_moreCops ;
        protected String m_groupId ="";

        public  PreyEscapedDialog (String param1 ,int param2 ,boolean param3 =false ,Function param4 =null ,boolean param5 =true ,Function param6 =null )
        {
            this.m_groupId = param1;
            Object _loc_7 ={id param2 };
            this.m_bandit = PreyManager.getPreyData(this.m_groupId, _loc_7);
            this.m_moreCops = param3;
            _loc_8 = param1+"_escaped";
            String _loc_9 ="";
            if (param3)
            {
                _loc_9 = ZLoc.t("Dialogs", param1 + "_escaped_moreHunters_message");
            }
            else
            {
                _loc_9 = ZLoc.t("Dialogs", param1 + "_escaped_moreResources_message");
            }
            super(_loc_9, _loc_8, GenericDialogView.TYPE_OK, param4, _loc_8, "", param5, 0, "", param6);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, makeAssets);
            if (this.m_bandit.portraitUrl)
            {
                this.m_iconLoader = LoadingManager.load(Global.getAssetURL(this.m_bandit.portraitUrl), this.onAssetsLoaded, LoadingManager.PRIORITY_HIGH);
            }
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
            _loc_1.put("dialog_bg", (DisplayObject) new m_comObject.dialog_bg());
            m_assetBG = _loc_1.get("dialog_bg");
            _loc_1.put("preyIcon",  this.m_iconLoader.content);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            PreyEscapedDialogView _loc_2 =new PreyEscapedDialogView(param1 ,this.m_bandit ,this.m_moreCops ,m_message ,m_dialogTitle ,m_callback ,m_SkipCallback );
            return _loc_2;
        }//end

    }



