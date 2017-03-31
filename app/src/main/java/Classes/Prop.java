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

    public class Prop extends Decoration
    {
        private  String PROP ="prop";
        protected Function m_playActionCallback ;
        protected boolean m_enableHighlight =true ;

        public  Prop (String param1)
        {
            super(param1);
            setState(STATE_STATIC);
            m_typeName = this.PROP;
            return;
        }//end

        public void  setPlayActionCallback (Function param1 )
        {
            this.m_playActionCallback = param1;
            return;
        }//end

        public void  setEnableHighlight (boolean param1 )
        {
            this.m_enableHighlight = param1;
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public void  onPlayAction ()
        {
            super.onPlayAction();
            if (this.m_playActionCallback != null)
            {
                this.m_playActionCallback();
            }
            return;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public void  setShowHighlight (boolean param1 )
        {
            m_showHighlight = this.m_enableHighlight && param1;
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            if (!Global.world.isEditMode)
            {
                super.setHighlighted(param1, param2);
            }
            return;
        }//end

         public void  showObjectBusy ()
        {
            return;
        }//end

    }


