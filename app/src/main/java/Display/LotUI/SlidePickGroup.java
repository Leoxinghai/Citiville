package Display.LotUI;

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

import Classes.sim.*;
import Display.FactoryUI.*;
//import flash.display.*;
//import flash.events.*;

    public class SlidePickGroup extends Sprite
    {
        protected  double PICK_OFFSET_X =5;
        protected  double PICK_OFFSET_Y =-5;
        protected LotManager m_lotManager ;
        protected Array m_picks ;
        protected boolean m_isFirstClicked =false ;
        protected boolean m_isTopSlidePickOpened =false ;

        public  SlidePickGroup (LotManager param1 ,Array param2 ,boolean param3 =false )
        {
            this.m_lotManager = param1;
            this.m_picks = param2 || new Array();
            if (param3)
            {
                this.init();
            }
            addEventListener(MouseEvent.CLICK, this.onClick);
            return;
        }//end

        public int  numPicks ()
        {
            return this.m_picks ? (this.m_picks.length()) : (0);
        }//end

        public boolean  isTopSlidePickOpened ()
        {
            return this.m_isTopSlidePickOpened;
        }//end

        public void  init ()
        {
            this.draw();
            this.addListeners();
            return;
        }//end

        public boolean  isInGroup (SlidePick param1 )
        {
            _loc_2 = this.m_picks.indexOf(param1 );
            _loc_3 = boolean(_loc_2>=0);
            return _loc_3;
        }//end

        public SlidePick  getPickByPropValue (String param1 , Object param2)
        {
            SlidePick _loc_3 =null ;
            SlidePick _loc_4 =null ;
            for(int i0 = 0; i0 < this.m_picks.size(); i0++)
            {
            		_loc_4 = this.m_picks.get(i0);

                if (_loc_4.hasOwnProperty(param1) && _loc_4.get(param1) == param2)
                {
                    _loc_3 = _loc_4;
                    break;
                }
            }
            return _loc_3;
        }//end

        public void  addPick (SlidePick param1 )
        {
            this.m_picks.put(this.m_picks.length,  param1);
            return;
        }//end

        public void  removePick (SlidePick param1 )
        {
            _loc_2 = this.m_picks.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_picks.splice(_loc_2, 1);
            }
            if (param1.parent == this)
            {
                param1.parent.removeChild(param1);
            }
            return;
        }//end

        public void  redraw ()
        {
            this.draw();
            this.m_isTopSlidePickOpened = false;
            if (this.m_picks && this.m_picks.length > 1)
            {
                if (this.m_isTopSlidePickOpened)
                {
                    this.openTopSlidePick();
                }
                else
                {
                    this.closeTopSlidePick();
                }
            }
            return;
        }//end

        public void  cleanUp ()
        {
            this.removeListeners();
            this.m_picks = null;
            this.m_lotManager = null;
            return;
        }//end

        public SlidePick  getTopPick ()
        {
            return this.numPicks ? (this.m_picks.get(0)) : (null);
        }//end

        public void  onClick ()
        {
            if (this.m_isTopSlidePickOpened)
            {
                this.closeTopSlidePick();
            }
            else
            {
                this.openTopSlidePick();
            }
            return;
        }//end

        protected void  draw ()
        {
            SlidePick _loc_3 =null ;
            double _loc_1 =0;
            double _loc_2 =0;
            for(int i0 = 0; i0 < this.m_picks.size(); i0++)
            {
            		_loc_3 = this.m_picks.get(i0);

                _loc_3.x = _loc_1;
                _loc_3.y = _loc_2;
                addChildAt(_loc_3, 0);
                _loc_1 = _loc_1 + this.PICK_OFFSET_X;
                _loc_2 = _loc_2 + this.PICK_OFFSET_Y;
            }
            return;
        }//end

        protected void  openTopSlidePick ()
        {
            SlidePick _loc_1 =null ;
            LotSlidePick _loc_2 =null ;
            if (this.m_picks.length())
            {
                _loc_1 = this.getTopPick();
                if (_loc_1 instanceof LotSlidePick)
                {
                    _loc_2 =(LotSlidePick) _loc_1;
                    _loc_2.openInnerPane();
                    this.m_isTopSlidePickOpened = true;
                }
            }
            return;
        }//end

        protected void  closeTopSlidePick ()
        {
            SlidePick _loc_1 =null ;
            LotSlidePick _loc_2 =null ;
            if (this.m_picks.length())
            {
                _loc_1 = this.getTopPick();
                if (_loc_1 instanceof LotSlidePick)
                {
                    _loc_2 =(LotSlidePick) _loc_1;
                    _loc_2.closeInnerPane();
                    this.m_isTopSlidePickOpened = false;
                }
            }
            return;
        }//end

        protected void  addListeners ()
        {
            addEventListener(MouseEvent.CLICK, this.mouseClickHandler, false, 0, true);
            return;
        }//end

        protected void  removeListeners ()
        {
            removeEventListener(MouseEvent.CLICK, this.mouseClickHandler);
            return;
        }//end

        protected void  mouseClickHandler (MouseEvent event )
        {
            this.onClick();
            return;
        }//end

    }



