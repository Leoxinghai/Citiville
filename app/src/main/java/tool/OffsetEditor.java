package tool;

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
import Display.*;
import Display.DialogUI.*;
import com.zynga.skelly.render.*;
import com.zynga.skelly.animation.*;

    public class OffsetEditor implements IAnimated
    {
        private OffsetEditorDialog m_dialog ;
        public boolean visible =false ;
        private static boolean _active =false ;
        private static OffsetEditor _instance ;
        private static GameObject _selected ;
        public static boolean captureMode =false ;

        public  OffsetEditor ()
        {
            this.m_dialog = new OffsetEditorDialog();
            this.init();
            return;
        }//end

        public void  init ()
        {
            return;
        }//end

        public boolean  animate (int param1 )
        {
            this.m_dialog.checkObject();
            return this.visible;
        }//end

        public void  showDialog ()
        {
            this.visible = true;
            UI.displayPopup(this.m_dialog, false);
            RenderManager.addAnimationByFPS(12, this);
            return;
        }//end

        private void  hideDialog ()
        {
            this.m_dialog.hide();
            this.visible = false;
            _selected = null;
            return;
        }//end

        public void  consider (GameObject param1 )
        {
            if (!_selected || captureMode)
            {
                this.m_dialog.displayOffsets(param1, captureMode);
            }
            return;
        }//end

        public static OffsetEditor  instance ()
        {
            if (!_instance)
            {
                _instance = new OffsetEditor;
            }
            return _instance;
        }//end

        public static boolean  active ()
        {
            return _active;
        }//end

        public static void  selected (GameObject param1 )
        {
            instance.m_dialog.displayOffsets(param1, true);
            _selected = param1;
            return;
        }//end

        public static void  active (boolean param1 )
        {
            _active = param1;
            if (param1 !=null)
            {
                instance.showDialog();
            }
            else if (_instance)
            {
                instance.hideDialog();
            }
            return;
        }//end

    }



