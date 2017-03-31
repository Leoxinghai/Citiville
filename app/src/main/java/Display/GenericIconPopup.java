package Display;

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
import com.xiyu.util.Event;

import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;

    public class GenericIconPopup extends GenericPopup
    {
        protected Object m_icon ;
        protected Loader m_iconLoader ;
        public static  int MAX_ICON_SIZE =50;

        public  GenericIconPopup (String param1 ,int param2 =1,Function param3 =null ,Object param4 =null )
        {
            m_dialogAsset = "assets/dialogs/TI_GenericIconPopUp.swf";
            this.m_icon = param4;
            super(param1, param2, param3, m_dialogAsset);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            m_window =(MovieClip) m_loader.content;
            m_window.callOutBox_mc.callOut_tf.text = m_message;
            m_window.callOutBox_mc.okay_bt.visible = false;
            if (this.m_icon instanceof String)
            {
                this.m_iconLoader = LoadingManager.load((String)this.m_icon, this.onIconLoaded);
            }
            else if (this.m_icon instanceof DisplayObject)
            {
                this.handleContent((DisplayObject)this.m_icon);
            }
            m_window.callOutBox_mc.close_bt.addEventListener(MouseEvent.CLICK, onCancel);
            setupButtons(m_window.callOutBox_mc.share_bt, m_window.callOutBox_mc.cancel_bt);
            addChild(m_window);
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            _loc_2 = this.m_iconLoader.content;
            this.handleContent(_loc_2);
            return;
        }//end

        protected void  handleContent (DisplayObject param1 )
        {
            MovieClip _loc_2 =null ;
            _loc_2 = m_window.callOutBox_mc.icon_mc;
            _loc_2.scaleY = 1;
            _loc_2.scaleX = 1;
            while (_loc_2.numChildren)
            {

                _loc_2.removeChildAt(0);
            }

            param1.scaleY = Math.min(1, param1.width / MAX_ICON_SIZE, param1.height / MAX_ICON_SIZE);
            param1.scaleX = Math.min(1, param1.width / MAX_ICON_SIZE, param1.height / MAX_ICON_SIZE);
            param1.x = (-param1.width) / 2;
            param1.y = (-param1.height) / 2;
            _loc_2.addChild(param1);
            return;
        }//end

    }



