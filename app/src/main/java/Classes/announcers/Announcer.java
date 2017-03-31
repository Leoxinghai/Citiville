package Classes.announcers;

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
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;

    public class Announcer extends NPC
    {
        private String m_customLayerName ;
        private String m_tooltip ;
        private Sprite m_tooltipBillboard ;

        public  Announcer (String param1 ,boolean param2 ,double param3 =-1)
        {
            super(param1, param2, param3);
            this.m_customLayerName = "npc";
            return;
        }//end  

        public String  customLayerName ()
        {
            return this.m_customLayerName;
        }//end  

        public void  customlayerName (String param1 )
        {
            this.m_customLayerName = param1;
            return;
        }//end  

        public String  tooltip ()
        {
            return this.m_tooltip;
        }//end  

        public void  tooltip (String param1 )
        {
            this.m_tooltip = param1;
            this.updateCustomTitle();
            return;
        }//end  

        private void  updateCustomTitle ()
        {
            AssetPane _loc_1 =null ;
            if (this.m_tooltip)
            {
                this.m_tooltipBillboard = new Sprite();
                _loc_1 = ASwingHelper.makeMultilineText(this.m_tooltip, 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 14, EmbeddedArt.whiteTextColor, .get(new GlowFilter(0, 1, 4, 4, 10, BitmapFilterQuality.LOW)));
                _loc_1.x = (-_loc_1.width) * 0.5 + 10;
                _loc_1.y = -25;
                this.m_tooltipBillboard.addChild(_loc_1);
                boolean _loc_2 =false ;
                this.m_tooltipBillboard.mouseEnabled = false;
                this.m_tooltipBillboard.mouseChildren = _loc_2;
            }
            else
            {
                this.m_tooltipBillboard = null;
            }
            return;
        }//end  

         protected String  getLayerName ()
        {
            return this.m_customLayerName;
        }//end  

         public void  onPlayAction ()
        {
            super.onPlayAction();
            return;
        }//end  

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            if (this.m_tooltipBillboard)
            {
                Global.ui.addChildAt(this.m_tooltipBillboard, 0);
                Global.stage.addEventListener(Event.ENTER_FRAME, this.onEnterFrame, false, 0, true);
            }
            return;
        }//end  

         public void  onMouseOut ()
        {
            super.onMouseOut();
            if (this.m_tooltipBillboard && this.m_tooltipBillboard.parent)
            {
                this.m_tooltipBillboard.parent.removeChild(this.m_tooltipBillboard);
                Global.stage.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            }
            return;
        }//end  

        private void  onEnterFrame (Event event )
        {
            Point _loc_2 =null ;
            if (this.m_tooltipBillboard && m_displayObject && m_displayObject.parent)
            {
                _loc_2 = m_displayObject.parent.localToGlobal(new Point(m_displayObject.x, m_displayObject.y));
                _loc_2 = Global.ui.globalToLocal(_loc_2);
                this.m_tooltipBillboard.x = _loc_2.x;
                this.m_tooltipBillboard.y = _loc_2.y;
            }
            return;
        }//end  

    }



