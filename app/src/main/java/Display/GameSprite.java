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

import Engine.Helpers.*;
import root.Global;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;

    public class GameSprite extends Sprite implements IToolTipTarget
    {
        protected String m_toolTip ;
        protected double m_normalScale =-1;
        protected double m_rollOverScale =1.1;
        protected TextField m_textField =null ;
        protected boolean m_hideCursor =false ;
        public static  int PADDING =5;
        public static  int HPADDING =2;

        public  GameSprite ()
        {
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOut);
            return;
        }//end  

        public void  toolTip (String param1 )
        {
            if (param1 != this.m_toolTip)
            {
                this.m_toolTip = param1;
            }
            return;
        }//end  

        public void  hideCursor (boolean param1 )
        {
            this.m_hideCursor = param1;
            return;
        }//end  

        public void  normalScale (double param1 )
        {
            this.m_normalScale = param1;
            return;
        }//end  

        public void  rollOverScale (double param1 )
        {
            this.m_rollOverScale = param1;
            return;
        }//end  

        protected void  onRollOver (MouseEvent event )
        {
            Global.ui.showToolTip(this);
            if (this.m_hideCursor)
            {
                UI.pushBlankCursor();
            }
            return;
        }//end  

        protected void  onRollOut (MouseEvent event )
        {
            if (this.m_normalScale > 0)
            {
                this.scaleY = this.m_normalScale;
                this.scaleX = this.m_normalScale;
            }
            if (this.m_hideCursor)
            {
                UI.popBlankCursor();
            }
            Global.ui.hideToolTip(this);
            return;
        }//end  

        public String  getToolTipStatus ()
        {
            return this.m_toolTip;
        }//end  

        public String  getToolTipHeader ()
        {
            return null;
        }//end  

        public String  getToolTipNotes ()
        {
            return null;
        }//end  

        public String  getToolTipAction ()
        {
            return null;
        }//end  

        public boolean  toolTipFollowsMouse ()
        {
            return true;
        }//end  

        public boolean  getToolTipVisibility ()
        {
            return true;
        }//end  

        public Vector3  getPosition ()
        {
            return new Vector3(this.x, this.y);
        }//end  

        public Vector3  getToolTipPosition ()
        {
            return this.getPosition();
        }//end  

        public Vector2  getToolTipScreenOffset ()
        {
            return null;
        }//end  

        public Vector3  getDimensions ()
        {
            return new Vector3(this.width, this.height);
        }//end  

        public int  getToolTipFloatOffset ()
        {
            return 0;
        }//end  

        public ToolTipSchema  getToolTipSchema ()
        {
            return null;
        }//end  

    }



