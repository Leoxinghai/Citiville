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
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;

    public class SpriteButton extends GameSprite
    {
        protected  int NORMAL =0;
        protected  int HOVER =1;
        protected  int DOWN =2;
        protected  int MAX_STATES =3;
        protected boolean m_selected =false ;
        protected boolean m_down =false ;
        protected boolean m_over =false ;
        protected boolean m_enabled =true ;
        protected Function m_clickCallback ;
        protected Array m_imageInst ;

        public  SpriteButton (Class param1 ,Class param2 ,Class param3 ,Function param4 =null ,String param5 ="")
        {
            this.m_imageInst = .get(null, null, null);
            addEventListener(MouseEvent.MOUSE_DOWN, this.onMouseDown);
            addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut);
            this.m_clickCallback = param4;
            addEventListener(MouseEvent.CLICK, this.onMouseClick);
            this.buttonMode = true;
            this.useHandCursor = true;
            this.m_imageInst.put(this.NORMAL,  new param1);
            this.m_imageInst.put(this.DOWN,  new param3);
            this.m_imageInst.put(this.HOVER,  new param2);
            int _loc_6 =0;
            while (_loc_6 < this.MAX_STATES)
            {
                
                addChild(this.m_imageInst.get(_loc_6));
                _loc_6++;
            }
            if (param5 != "")
            {
                this.makeText(param5);
            }
            this.updateImageState();
            return;
        }//end  

        public void  setEnabled (boolean param1 )
        {
            this.m_enabled = param1;
            return;
        }//end  

        protected void  makeText (String param1 )
        {
            TextField _loc_2 =new TextField ();
            _loc_2.multiline = false;
            _loc_2.wordWrap = false;
            _loc_2.text = param1;
            _loc_2.width = this.m_imageInst.get(this.NORMAL).width;
            _loc_2.height = 0;
            _loc_2.selectable = false;
            _loc_2.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_2.antiAliasType = AntiAliasType.ADVANCED;
            _loc_2.gridFitType = GridFitType.PIXEL;
            _loc_2.autoSize = TextFieldAutoSize.CENTER;
            TextFormat _loc_3 =new TextFormat ();
            _loc_4 = TextFieldUtil.getLocaleFontSize(24,13,[{localesize"de",24locale},{"fr",size24},locale{"es",14},{"ja",24});
            _loc_3.font = EmbeddedArt.defaultFontNameBold;
            _loc_3.color = 16579429;
            _loc_3.align = TextFormatAlign.CENTER;
            _loc_3.size = _loc_4;
            Array _loc_5 =.get(new GlowFilter(0,1,3,3,10),new GlowFilter(16777215,1,2,2,10),new DropShadowFilter(2,90,0,0.5)) ;
            _loc_2.setTextFormat(_loc_3);
            _loc_2.filters = _loc_5;
            addChild(_loc_2);
            _loc_2.y = 15;
            _loc_2.mouseEnabled = false;
            return;
        }//end  

        protected void  updateImageState ()
        {
            int _loc_1 =0;
            if (this.m_down)
            {
                _loc_1 = this.DOWN;
            }
            else if (this.m_over)
            {
                _loc_1 = this.HOVER;
            }
            else
            {
                _loc_1 = this.NORMAL;
            }
            int _loc_2 =0;
            while (_loc_2 < this.MAX_STATES)
            {
                
                this.m_imageInst.get(_loc_2).visible = _loc_1 == _loc_2;
                _loc_2++;
            }
            return;
        }//end  

        protected void  onMouseOver (MouseEvent event )
        {
            if (this.m_enabled)
            {
                this.m_over = true;
                this.updateImageState();
            }
            return;
        }//end  

        protected void  onMouseOut (MouseEvent event )
        {
            if (this.m_enabled)
            {
                this.m_over = false;
                this.updateImageState();
            }
            return;
        }//end  

        protected void  onMouseDown (MouseEvent event )
        {
            if (this.m_enabled)
            {
                this.m_down = true;
                this.updateImageState();
                Global.stage.addEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
                event.stopPropagation();
                event.stopImmediatePropagation();
            }
            return;
        }//end  

        protected void  onMouseUp (MouseEvent event )
        {
            if (this.m_enabled)
            {
                this.m_down = false;
                this.updateImageState();
                Global.stage.removeEventListener(MouseEvent.MOUSE_UP, this.onMouseUp);
                event.stopPropagation();
                event.stopImmediatePropagation();
            }
            return;
        }//end  

        protected void  onMouseClick (MouseEvent event )
        {
            if (this.m_enabled && this.m_clickCallback != null)
            {
                this.m_clickCallback(event);
            }
            event.stopPropagation();
            event.stopImmediatePropagation();
            return;
        }//end  

    }



