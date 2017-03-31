package GameMode;

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
import Events.*;
//import flash.events.*;

    public class GMMultiPick extends GMDefault
    {

        public  GMMultiPick ()
        {
            m_uiMode = UIEvent.CURSOR;
            return;
        }//end  

         public boolean  supportsEditing ()
        {
            return false;
        }//end  

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return false;
        }//end  

         protected boolean  isObjectDraggable (GameObject param1 )
        {
            return false;
        }//end  

         public boolean  onMouseDown (MouseEvent event )
        {
            return super.onMouseDown(event);
        }//end  

         protected void  handleClick (MouseEvent event )
        {
            return;
        }//end  

    }



