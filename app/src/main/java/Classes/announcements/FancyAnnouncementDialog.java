package Classes.announcements;

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
//import flash.geom.*;
//import flash.utils.*;

    public class FancyAnnouncementDialog extends GenericDialog
    {
        private AnnouncementData m_announcementData ;
        private int m_numLoadDependencies =1;
        private DisplayObject m_customBg ;
        private DisplayObject m_pic ;

        public  FancyAnnouncementDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="SendEmails",AnnouncementData param10 =null )
        {
            this.m_announcementData = param10;
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  this.m_customBg ? (this.m_customBg) : ((DisplayObject)m_comObject.dialog_bg_picture()));
            _loc_1.put("image", this.m_pic);
            _loc_2 = this.m_announcementData.view;
            if (_loc_2.hasOwnProperty("picWidth") && _loc_2.hasOwnProperty("picHeight"))
            {
                _loc_1.put("image_size",  new Point(_loc_2.get("picWidth"), _loc_2.get("picHeight")));
            }
            if (_loc_2.hasOwnProperty("picLeft") && _loc_2.hasOwnProperty("picTop"))
            {
                _loc_1.put("image_offset",  new Point(_loc_2.get("picLeft"), _loc_2.get("picTop")));
            }
            return _loc_1;
        }//end

         protected void  loadAssets ()
        {
            if (this.m_announcementData.view.hasOwnProperty("bgUrl"))
            {
                this.m_numLoadDependencies++;
                Global.delayedAssets.get(this.m_announcementData.view.get("bgUrl"), this.onCustomBgLoaded);
            }
            if (this.m_announcementData.view.hasOwnProperty("picUrl"))
            {
                this.m_numLoadDependencies++;
                Global.delayedAssets.get(this.m_announcementData.view.get("picUrl"), this.onPictureLoaded);
            }
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, this.onDialogAssetsLoaded);
            return;
        }//end

        protected void  onCustomBgLoaded (DisplayObject param1 ,String param2 )
        {
            this.m_customBg = param1;
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  onPictureLoaded (DisplayObject param1 ,String param2 )
        {
            this.m_pic = param1;
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  onDialogAssetsLoaded (DisplayObject param1 ,String param2 )
        {
            m_comObject = param1;
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  checkAssetsLoaded ()
        {
             this.m_numLoadDependencies--;
            if (--this.m_numLoadDependencies == 0)
            {
                onAssetsLoaded();
            }
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new FancyAnnouncementDialogView(param1, m_message, m_dialogTitle, m_type, m_callback);
        }//end

    }




