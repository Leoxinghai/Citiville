package Classes;

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

//import flash.events.*;
    public class Dock extends MapResource
    {
        protected  String STATE_STATIC ="static";
        private  String DOCK ="dock";

        public  Dock (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.DOCK;
            setState(this.STATE_STATIC);
            m_typeName = this.DOCK;
            return;
        }//end  

         public boolean  isSellable ()
        {
            return false;
        }//end  

         public boolean  canBeDragged ()
        {
            return false;
        }//end  

         public void  onMouseOver (MouseEvent event )
        {
            return;
        }//end  

         public void  onMouseOut ()
        {
            return;
        }//end  

         public String  getToolTipHeader ()
        {
            return null;
        }//end  

    }



