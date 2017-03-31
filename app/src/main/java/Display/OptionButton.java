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

//import flash.events.*;
    public class OptionButton extends GameSprite
    {
        public Class selectedImage ;
        public Class nonSelectedImage ;
        public Class hoverImage ;
        protected boolean m_selected =false ;
        protected boolean m_down =false ;
        protected boolean m_over =false ;

        public  OptionButton (Class param1 ,Class param2 ,Class param3 ,Function param4 =null )
        {
            this.selectedImage = param1;
            this.nonSelectedImage = param3;
            this.hoverImage = param2;
            addEventListener(MouseEvent.CLICK, this.onClick, false, 10);
            addEventListener(MouseEvent.MOUSE_DOWN, this.onMouseDown);
            addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut);
            if (param4 != null)
            {
                addEventListener(MouseEvent.CLICK, param4);
            }
            this.buttonMode = true;
            this.useHandCursor = true;
            this.updateImageState();
            return;
        }//end  

        public void  selected (boolean param1 )
        {
            if (this.m_selected != param1)
            {
                this.m_selected = param1;
                this.updateImageState();
            }
            return;
        }//end  

        public void  updateImageState ()
        {
            Class _loc_1 =null ;
            while (this.numChildren > 0)
            {
                
                this.removeChildAt(0);
            }
            if (this.m_over)
            {
                _loc_1 = this.hoverImage;
            }
            else if (this.selected)
            {
                _loc_1 = this.selectedImage;
            }
            else
            {
                _loc_1 = this.nonSelectedImage;
            }
            addChild(new _loc_1);
            return;
        }//end  

        public boolean  selected ()
        {
            return this.m_selected;
        }//end  

        protected void  onClick (MouseEvent event )
        {
            this.selected = !this.selected;
            return;
        }//end  

        protected void  onMouseOver (MouseEvent event )
        {
            this.m_over = true;
            this.updateImageState();
            return;
        }//end  

        protected void  onMouseOut (MouseEvent event )
        {
            this.m_over = false;
            this.updateImageState();
            return;
        }//end  

        protected void  onMouseDown (MouseEvent event )
        {
            this.m_down = true;
            (this.x + 1);
            (this.y + 1);
            Global.stage.addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
            return;
        }//end  

        protected void  onMouseUp (MouseEvent event )
        {
            if (this.m_down)
            {
                (this.x - 1);
                (this.y - 1);
            }
            this.m_down = false;
            Global.stage.removeEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
            return;
        }//end  

    }



