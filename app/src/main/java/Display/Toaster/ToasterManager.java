package Display.Toaster;

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


import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class ToasterManager extends Sprite
    {
        private Vector<Toaster> m_toasterQueue;
        private Toaster m_displayedToaster ;
        private int m_displayTimeout ;
        private boolean m_isHidingInProgress ;

        public  ToasterManager ()
        {
            this.m_toasterQueue = new Vector<Toaster>();
            this.m_isHidingInProgress = false;
            return;
        }//end

        public void  show (Toaster param1 ,boolean param2 =false )
        {
            if (!this.m_displayedToaster || !param2)
            {
                this.m_toasterQueue.push(param1);
                this.pump();
            }
            return;
        }//end

        private void  pump ()
        {
            if (!this.m_displayedToaster && this.m_toasterQueue.length > 0)
            {
                this.m_displayedToaster = this.m_toasterQueue.shift();
                int _loc_1 =0;
                this.m_displayedToaster.y = 0;
                this.m_displayedToaster.x = _loc_1;
                addChild(this.m_displayedToaster);
                TweenLite.to(this.m_displayedToaster, 0.3, {y:-this.m_displayedToaster.displayHeight, ease:Back.easeOut, onComplete:this.showCompleteHandler});
                if (this.m_displayedToaster.statName)
                {
                    this.m_displayedToaster.countView();
                }
            }
            return;
        }//end

        public void  hide (boolean param1 =false )
        {
            if (!this.m_isHidingInProgress && this.m_displayedToaster)
            {
                this.m_isHidingInProgress = true;
                this.m_displayedToaster.removeEventListener(Event.CLOSE, this.toasterCloseHandler);
                if (this.m_displayTimeout)
                {
                    clearTimeout(this.m_displayTimeout);
                }
                TweenLite.to(this.m_displayedToaster, 0.3, {y:0, ease:Back.easeIn, onComplete:this.hideCompleteHandler});
                if (this.m_displayedToaster.statName)
                {
                    if (param1 !=null)
                    {
                        this.m_displayedToaster.countForcedClose();
                    }
                    else
                    {
                        this.m_displayedToaster.countAutoClose();
                    }
                }
            }
            return;
        }//end

        private void  showCompleteHandler ()
        {
            if (this.m_displayedToaster)
            {
                this.m_displayedToaster.addEventListener(Event.CLOSE, this.toasterCloseHandler);
                if (this.m_displayedToaster.duration > -1)
                {
                    this.m_displayTimeout = setTimeout(this.hide, this.m_displayedToaster.duration);
                }
            }
            return;
        }//end

        private void  hideCompleteHandler ()
        {
            this.m_isHidingInProgress = false;
            removeChild(this.m_displayedToaster);
            this.m_displayedToaster = null;
            this.pump();
            return;
        }//end

        private void  toasterCloseHandler (Event event )
        {
            this.hide(true);
            return;
        }//end

    }



