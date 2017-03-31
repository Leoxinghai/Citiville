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

import Classes.util.*;
import Engine.Managers.*;
import Events.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.flyfish.*;

    public class FlyFishDialog extends GenericDialog
    {
        private String m_xmlPath ;
        private FlyFishPane m_pane ;
        private Object m_dialogViewData ;

        public  FlyFishDialog (String param1 )
        {
            this.m_dialogViewData = new Object();
            this.m_xmlPath = param1;
            super("", "", 0, null, "", "", true, 0, "", null, "", true);
            return;
        }//end  

         protected void  onAssetsLoaded (Event event =null )
        {
            Global.delayedAssets.get(DelayedAssetLoader.CSS, this.styleLoaded, DelayedAssetLoader.LOADTYPE_TEXT);
            return;
        }//end  

         protected void  processSelection (GenericPopupEvent event )
        {
            String _loc_2 ="";
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = this.dialogViewData.get("acceptText");
                if (m_callback != null)
                {
                    m_callback(event);
                }
            }
            else if (event.button == GenericDialogView.NO)
            {
                _loc_2 = this.dialogViewData.get("cancelText");
                if (m_callback != null)
                {
                    m_callback(event);
                }
            }
            else if (event.button == GenericDialogView.SKIP)
            {
                _loc_2 = this.dialogViewData.get("skipText");
                if (m_SkipCallback != null)
                {
                    m_SkipCallback(event);
                }
            }
            else if (event.button == GenericDialogView.SHARE)
            {
                if (m_callback != null)
                {
                    m_callback(event);
                }
            }
            if (_loc_2.length > 0)
            {
                countDialogAction(_loc_2);
            }
            event.stopPropagation();
            return;
        }//end  

        protected FlyFishPane  pane ()
        {
            return this.m_pane;
        }//end  

        protected Object  dialogViewData ()
        {
            return this.m_dialogViewData;
        }//end  

        protected void  performDialogActions ()
        {
            return;
        }//end  

        private void  styleLoaded (Object param1 ,String param2 )
        {
            FlyFish.loadAWML(AssetUrlManager.instance.lookUpUrl(Global.getAssetURL(this.m_xmlPath)), this.xmlLoaded, [param1]);
            return;
        }//end  

        private void  xmlLoaded (FlyFishPane param1 )
        {
            if (param1 == null)
            {
                return;
            }
            m_jpanel =(JPanel) param1.getRoot();
            this.m_pane = param1;
            this.performDialogActions();
            finalizeAndShow();
            return;
        }//end  

    }



