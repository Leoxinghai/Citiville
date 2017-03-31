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

import Classes.util.*;
import Display.DialogUI.*;
//import flash.geom.*;
//import flash.utils.*;

    public class FrameLoaderDialog extends GenericDialog
    {
        protected Point m_targetPos ;

        public  FrameLoaderDialog (String param1 ,Point param2 )
        {
            this.m_targetPos = param2;
            super(param1);
            return;
        }//end

         protected boolean  doTrackDialogActions ()
        {
            return false;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.FRAME_MANAGER_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("frameLoader_bg", m_comObject.frameLoader_bg);
            _loc_1.put("frameLoader_beak", m_comObject.frameLoader_beak);
            _loc_1.put("frameLoader_waiting", m_comObject.frameLoader_waiting);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new FrameLoaderDialogView(param1, m_message, this.m_targetPos != null);
        }//end

         public void  centerPopup ()
        {
            if (this.m_targetPos)
            {
                this.x = this.m_targetPos.x - this.width / 2;
                this.y = this.m_targetPos.y - this.height;
            }
            else
            {
                super.centerPopup();
            }
            return;
        }//end

        public void  setupDefaultSizeAndPosition ()
        {
            Point _loc_1 =null ;
            this.width = 62.6;
            this.height = 48.2;
            m_centered = true;
            _loc_1 = getDialogOffset();
            this.x = Global.ui.screenWidth / 2 + _loc_1.x;
            this.y = Global.ui.screenHeight / 2 + _loc_1.y;
            return;
        }//end

    }



