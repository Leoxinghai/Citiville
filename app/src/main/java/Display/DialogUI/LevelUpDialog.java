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

//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class LevelUpDialog extends GenericDialog
    {
        protected int m_level ;
        protected Array m_buyableItems ;
        protected Array m_giftableItems ;
        protected Array m_specialBuyableItems ;

        public  LevelUpDialog (int param1 ,Array param2 ,Array param3 ,Array param4 )
        {
            this.m_level = param1;
            this.m_buyableItems = param2;
            this.m_giftableItems = param3;
            this.m_specialBuyableItems = param4;
            super("", ZLoc.t("Dialogs", "Congratulations"), GenericDialogView.TYPE_SHARECANCEL, null, "", "", true);
            return;
        }//end

         public boolean  isLockable ()
        {
            return true;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_levelUp_bg());
            _loc_2.put("dialog_horizontalRule",  m_comObject.dialog_horizontalRule);
            _loc_2.put("dialog_bg_special",  new (DisplayObject)m_comObject.dialog_levelUp_bg_special());
            _loc_2.put("dialog_item",  m_comObject.dialog_levelUp_item);
            _loc_2.put("dialog_item_special",  m_comObject.dialog_offline_specialItem);
            m_jpanel = new LevelUpDialogView(_loc_2, m_message, m_title, m_type, m_callback, this.m_level, this.m_buyableItems, this.m_giftableItems, this.m_specialBuyableItems);
            finalizeAndShow();
            return;
        }//end

    }



