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

import Classes.*;
import Engine.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class ResourceDialog extends SWFDialog
    {
        protected MapResource m_resource ;
        protected int m_width ;
        protected int m_height ;
        protected int m_yOffset =0;
        protected int m_xOffset =0;
        protected MovieClip m_dialog ;

        public  ResourceDialog (MapResource param1 ,boolean param2 =true )
        {
            super(false);
            this.m_resource = param1;
            return;
        }//end

         protected void  onLoadComplete ()
        {
            this.m_dialog =(MovieClip) m_loader.content;
            m_centered = false;
            this.m_width = this.m_dialog.mc.width;
            this.m_height = this.m_dialog.mc.height;
            this.positionDialog();
            addEventListener(Event.ENTER_FRAME, this.updatePosition);
            this.onInitialize();
            addChild(this.m_dialog);
            return;
        }//end

        protected void  onInitialize ()
        {
            return;
        }//end

        protected void  updatePosition (Event event )
        {
            if (this.m_resource == null)
            {
                this.close();
            }
            if (this.m_dialog.visible && Global.world.getTopGameMode().isDragging())
            {
                this.positionDialog();
            }
            return;
        }//end

         public void  close ()
        {
            this.m_resource = null;
            removeEventListener(Event.ENTER_FRAME, this.updatePosition);
            super.close();
            return;
        }//end

        public void  xOffset (int param1 )
        {
            this.m_xOffset = param1;
            this.positionDialog();
            return;
        }//end

        public void  yOffset (int param1 )
        {
            this.m_yOffset = param1;
            this.positionDialog();
            return;
        }//end

        protected void  positionDialog ()
        {
            if (this.m_resource == null || this.m_resource.getDisplayObject() == null)
            {
                return;
            }
            _loc_1 = this.m_resource.getPositionNoClone ();
            _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x ,_loc_1.y );
            _loc_2 = IsoMath.viewportToStage(_loc_2);
            _loc_2.x = _loc_2.x - this.m_width / 2;
            _loc_2.y = _loc_2.y - (this.m_height + this.m_resource.getDisplayObject().height / 2);
            this.x = _loc_2.x + this.m_xOffset;
            this.y = _loc_2.y + this.m_yOffset;
            return;
        }//end

    }



