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

//import flash.display.*;
//import flash.events.*;

    public class ToolTip extends GameSprite
    {
        protected Function m_generatorFn ;
        protected DisplayObject m_owner ;
        protected boolean m_hidden =false ;

        public  ToolTip (Function param1)
        {
            this.m_generatorFn = param1;
            return;
        }//end

        public void  attachToolTip (DisplayObject param1 )
        {
            this.m_owner = param1;
            param1.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver);
            param1.addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            return;
        }//end

        public void  detachToolTip (DisplayObject param1 )
        {
            this.m_owner = null;
            param1.removeEventListener(MouseEvent.ROLL_OVER, this.onRollOver);
            param1.removeEventListener(MouseEvent.ROLL_OUT, onRollOut);
            return;
        }//end

         protected void  onRollOver (MouseEvent event )
        {
            if (this.m_generatorFn != null)
            {
                this.toolTip = this.m_generatorFn();
            }
            if (!this.m_hidden)
            {
                super.onRollOver(event);
            }
            return;
        }//end

        public DisplayObject  owner ()
        {
            return this.m_owner;
        }//end

        public void  hidden (boolean param1 )
        {
            this.m_hidden = param1;
            return;
        }//end

    }



