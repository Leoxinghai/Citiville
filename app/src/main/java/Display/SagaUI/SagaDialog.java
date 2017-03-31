package Display.SagaUI;

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
//import flash.display.*;
//import flash.utils.*;

    public class SagaDialog extends GenericDialog
    {
        private String m_sagaId ;
        private String m_sagaDefaultAssetsPath ;
        private Object m_sagaDefaultAssets ;
        private String m_sagaSkinAssetsName ;
        private String m_sagaSkinAssetsPath ;
        private Object m_sagaSkinAssets ;
        public static Dictionary assetDict =new Dictionary ();

        public  SagaDialog (String param1 )
        {
            this.m_sagaId = param1;
            this.m_sagaDefaultAssetsPath = DelayedAssetLoader.SAGA_ASSETS;
            this.m_sagaSkinAssetsName = param1.toUpperCase() + "_SAGA_ASSETS";
            if (this.m_sagaSkinAssetsName in DelayedAssetLoader)
            {
                this.m_sagaSkinAssetsPath = DelayedAssetLoader.get(this.m_sagaSkinAssetsName);
            }
            super("", "", 0, null, param1);
            return;
        }//end  

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =.get(this.m_sagaDefaultAssetsPath) ;
            if (this.m_sagaSkinAssetsPath)
            {
                _loc_1.push(this.m_sagaSkinAssetsPath);
            }
            return _loc_1;
        }//end  

        protected Class  getSagaAssetClass (String param1 )
        {
            if (this.m_sagaSkinAssets && param1 in this.m_sagaSkinAssets)
            {
                return this.m_sagaSkinAssets.get(param1);
            }
            return this.m_sagaDefaultAssets.get(param1);
        }//end  

         protected Dictionary  createAssetDict ()
        {
            this.m_sagaDefaultAssets = m_assetDependencies.get(this.m_sagaDefaultAssetsPath);
            if (this.m_sagaSkinAssetsPath in m_assetDependencies)
            {
                this.m_sagaSkinAssets = m_assetDependencies.get(this.m_sagaSkinAssetsPath);
            }
            Class _loc_1 =this.getSagaAssetClass("dialog_bg");
            
            assetDict.put("dialog_bg",  new (DisplayObject)_loc_1);
            return assetDict;
        }//end  

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new SagaDialogView(param1, m_message, m_title, this.getSagaAssetClass);
        }//end  

    }



