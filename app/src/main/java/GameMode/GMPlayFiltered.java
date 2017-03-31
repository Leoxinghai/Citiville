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
//import flash.events.*;

    public class GMPlayFiltered extends GMPlay
    {
        protected Function m_predicate =null ;

        public  GMPlayFiltered (Function param1 )
        {
            this.m_predicate = param1;
            return;
        }//end  

         protected boolean  isObjectHighlightable (GameObject param1 )
        {
            return this.m_predicate(param1);
        }//end  

         public boolean  onMouseMove (MouseEvent event )
        {
            _loc_2 = super.onMouseMove(event);
            m_viewDragStartPos = null;
            return _loc_2;
        }//end  

         protected void  handleClick (MouseEvent event )
        {
            if (m_highlightedObject != null && this.m_predicate != null && this.m_predicate(m_highlightedObject))
            {
                super.handleClick(event);
            }
            return;
        }//end  

    }



