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
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class CharacterDialog extends GenericDialog
    {
        protected String m_characterUrl ="assets/dialogs/quests/citysam_80.png";
        protected Loader m_characterLoader ;
        protected String m_acceptButtonText ;
        protected Array m_dependencies ;
        protected int m_imagesLoaded =0;
        protected int m_totalImages =0;
        protected Object m_formatParams ;

        public  CharacterDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,Function param5 =null ,boolean param6 =true ,String param7 ="",String param8 ="Okay",Object param9 =null )
        {
            this.m_formatParams = new Object();
            this.m_characterUrl = param7 != "" ? (param7) : (this.m_characterUrl);
            this.m_acceptButtonText = param8;
            this.m_dependencies = new Array();
            this.m_formatParams = param9 ? (param9) : (this.m_formatParams);
            super(param1, param2, param3, param4, param2, "", param6, 0, "", param5, "");
            return;
        }//end

         protected void  loadAssets ()
        {
            this.m_imagesLoaded = 0;
            this.m_totalImages = 2;
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, makeAssets);
            this.m_characterLoader = LoadingManager.load(Global.getAssetURL(this.m_characterUrl), this.onAssetsLoaded, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            _loc_1.put("character", this.m_characterLoader.content);
            return _loc_1;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_totalImages)
            {
                return;
            }
            super.onAssetsLoaded(event);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            CharacterDialogView _loc_2 =new CharacterDialogView(param1 ,this.m_formatParams ,m_message ,m_dialogTitle ,m_type ,m_callback ,m_icon ,m_iconPos ,this.m_acceptButtonText );
            return _loc_2;
        }//end

    }




