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

import Engine.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;

    public class PaintSelector extends SWFDialog
    {
        private  int SWATCH_SIZE =24;
        private  int SWATCH_PADDING =6;
        private  double PREVIEWHOLDERWIDTH =115;
        private  double PREVIEWHOLDERHEIGHT =95;
        private MovieClip m_window ;
        private GenericButton m_accept ;
        private GenericButton m_close ;
        private Array m_colors ;
        private int m_selectedColor ;
        private Sprite m_preview ;

        public  PaintSelector ()
        {
            String _loc_3 =null ;
            this.m_colors = new Array();
            m_dialogAsset = "assets/dialogs/Paint_Selector.swf";
            _loc_1 =Global.gameSettings().getXML ();
            int _loc_2 =0;
            while (_loc_2 < _loc_1.colors.color.length())
            {

                _loc_3 = _loc_1.colors.color.get(_loc_2).@rgb.toString();
                this.m_colors.push({color:Utilities.hexColorToIntColor(_loc_3)});
                _loc_2++;
            }
            super(true);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            MovieClip _loc_7 =null ;
            ColorTransform _loc_8 =null ;
            this.m_window =(MovieClip) m_loader.content;
            this.m_accept = new GenericButton(this.m_window.paintSelector_mc.ok_bt, this.onOkClick);
            this.m_accept.text = ZLoc.t("Dialogs", "Accept");
            this.m_window.paintSelector_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCancelClick, false, 0, true);
            int _loc_1 =90;
            _loc_2 = _loc_1;
            int _loc_3 =128;
            int _loc_4 =130;
            _loc_5 = this.m_window.loaderInfo.applicationDomain.getDefinition("paint_bevel")as Class ;
            this.m_preview = new Sprite();
            this.m_selectedColor = this.m_colors.get(0).color;
            this.updatePreview();
            this.m_preview.x = this.m_window.paintSelector_mc.getChildByName("preview_mc").x;
            this.m_preview.y = this.m_window.paintSelector_mc.getChildByName("preview_mc").y;
            this.m_preview.scaleX = this.PREVIEWHOLDERWIDTH / this.m_preview.width;
            this.m_preview.scaleY = this.PREVIEWHOLDERHEIGHT / this.m_preview.height;
            this.m_preview.visible = true;
            this.m_window.addChild(this.m_preview);
            int _loc_6 =0;
            while (_loc_6 < this.m_colors.length())
            {

                _loc_7 = new _loc_5;
                _loc_8 = new ColorTransform();
                _loc_8.color = this.m_colors.get(_loc_6).color;
                _loc_7.swatch_mc.transform.colorTransform = _loc_8;
                _loc_7.filters = .get(new DropShadowFilter());
                _loc_7.x = _loc_2;
                _loc_7.y = _loc_3;
                _loc_7.addEventListener(MouseEvent.CLICK, this.onClickSwatch);
                this.m_colors.get(_loc_6).swatch = _loc_7;
                this.m_window.addChild(_loc_7);
                _loc_2 = _loc_2 + (this.SWATCH_SIZE + this.SWATCH_PADDING);
                if (_loc_2 - _loc_1 > _loc_4)
                {
                    _loc_2 = _loc_1;
                    _loc_3 = _loc_3 + (this.SWATCH_SIZE + this.SWATCH_PADDING);
                }
                _loc_6++;
            }
            addChild(this.m_window);
            return;
        }//end

        protected void  updatePreview ()
        {
            this.m_preview.graphics.beginFill(this.m_selectedColor);
            this.m_preview.graphics.drawRect(0, 0, 20, 20);
            this.m_preview.graphics.endFill();
            return;
        }//end

        private void  onClickSwatch (MouseEvent event )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_colors.length())
            {

                if (this.m_colors.get(_loc_2).swatch == event.currentTarget)
                {
                    this.m_selectedColor = this.m_colors.get(_loc_2).color;
                    this.updatePreview();
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

        private void  onOkClick (MouseEvent event )
        {
            event.stopPropagation();
            close();
            dispatchEvent(new Event("PaintSelector_onOkClick"));
            return;
        }//end

        private void  onCancelClick (MouseEvent event )
        {
            event.stopPropagation();
            close();
            dispatchEvent(new Event("PaintSelector_onCancelClick"));
            return;
        }//end

        public int  selectedColor ()
        {
            return this.m_selectedColor;
        }//end

    }



