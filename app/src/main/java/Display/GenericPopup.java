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

import Events.*;
//import flash.display.*;
//import flash.events.*;

    public class GenericPopup extends SWFDialog
    {
        protected MovieClip m_window ;
        protected String m_message ;
        protected int m_type ;
        protected Function m_closeCallback ;
        protected GenericButton m_accept ;
        protected GenericButton m_reject ;
        protected String m_acceptTextName ="Accept";
        protected String m_cancelTextName ="Cancel";
        public static  int SHARE =1;
        public static  int ACCEPT =1;
        public static  int OK =1;
        public static  int YES =1;
        public static  int SAVE =1;
        public static  int CANCEL =0;
        public static  int NO =0;
        public static  int TYPE_OK =0;
        public static  int TYPE_ACCEPTCANCEL =1;
        public static  int TYPE_YESNO =2;
        public static  int TYPE_MODAL =3;
        public static  int TYPE_SAVESHARESAVE =4;
        public static  int TYPE_SHARECANCEL =5;

        public  GenericPopup (String param1 ,int param2 =0,Function param3 =null ,String param4 ="assets/dialogs/TI_GenericPopUp.swf")
        {
            m_dialogAsset = param4;
            this.m_type = param2;
            this.m_message = param1;
            if (this.m_type == TYPE_OK)
            {
                this.m_acceptTextName = "Okay";
            }
            else if (this.m_type == TYPE_YESNO)
            {
                this.m_acceptTextName = "Yes";
                this.m_cancelTextName = "No";
            }
            else if (this.m_type == TYPE_ACCEPTCANCEL)
            {
                this.m_acceptTextName = "Accept";
                this.m_cancelTextName = "Cancel";
            }
            else if (this.m_type == TYPE_SHARECANCEL)
            {
                this.m_acceptTextName = "Share";
                this.m_cancelTextName = "Cancel";
            }
            if (param3 != null)
            {
                this.m_closeCallback = param3;
                addEventListener(GenericPopupEvent.SELECTED, param3);
            }
            return;
        }//end

         protected void  onLoadComplete ()
        {
            Array _loc_1 =new Array();
            this.m_window =(MovieClip) m_loader.content;
            if (this.m_message != null)
            {
                this.m_window.popupBox_mc.popupBox_tf.text = this.m_message;
            }
            this.setupButtons(this.m_window.popupBox_mc.accept_bt, this.m_window.popupBox_mc.cancel_bt);
            addChild(this.m_window);
            return;
        }//end

        protected void  setupButtons (MovieClip param1 ,MovieClip param2 )
        {
            if (this.m_type == TYPE_OK)
            {
                param2.visible = false;
                param1.x = (this.m_window.width - param1.width) / 2;
            }
            else if (this.m_type == TYPE_YESNO)
            {
                param1.visible = true;
                param2.visible = true;
            }
            else if (this.m_type == TYPE_ACCEPTCANCEL)
            {
                param1.visible = true;
                param2.visible = true;
            }
            else if (this.m_type == TYPE_MODAL)
            {
                param1.visible = false;
                param2.visible = false;
            }
            else if (this.m_type == TYPE_SHARECANCEL)
            {
                param1.visible = true;
                param2.visible = true;
            }
            if (param1.visible)
            {
                this.m_accept = new GenericButton(param1, this.onAccept);
                this.m_accept.text = getLocalizedString(this.m_acceptTextName);
            }
            if (param2.visible)
            {
                this.m_reject = new GenericButton(param2, this.onCancel);
                this.m_reject.text = getLocalizedString(this.m_cancelTextName);
            }
            return;
        }//end

        public void  setAcceptTextName (String param1 )
        {
            this.m_acceptTextName = param1;
            return;
        }//end

        public void  setCancelTextName (String param1 )
        {
            this.m_cancelTextName = param1;
            return;
        }//end

        protected void  onAccept (MouseEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES));
            if (this.m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, this.m_closeCallback);
            }
            close();
            return;
        }//end

        protected void  onCancel (MouseEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, NO));
            if (this.m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, this.m_closeCallback);
            }
            close();
            return;
        }//end

        public static String  getLocalizedString (String param1 ,Array param2 )
        {
            _loc_3 = param1.split(":",2);
            if (_loc_3.length == 1)
            {
                _loc_3.splice(0, 0, "Dialogs");
            }
            _loc_3.push(param2);
            return ZLoc.t.apply(null, _loc_3);
        }//end

    }



