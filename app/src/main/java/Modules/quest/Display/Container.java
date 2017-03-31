package Modules.quest.Display;

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

import Classes.util.*;
import Display.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;

    public class Container extends Sprite
    {
        protected  int CSLOT_HEIGHT =64;
        protected  int SLOT_Y_SPACING =70;
        public boolean m_containerLock ;
        public int num_slots =5;
        public Array resourcePool ;
        public int m_slotDistance ;
        public int m_box_width ;
        public int m_box_height ;
        public int totalDistance ;
        public int constantDistance ;
        public Array images ;
        public int m_i =0;
        public int direction ;
        protected double visibleWidth ;
        protected double visibleHeight ;
        protected double m_fadeOutTweenTime =0.3;
        protected double m_shiftTweenTime =0.8;
        protected Sprite m_mask ;
        public static  int DIRECTION_VERT =1;
        public static  int DIRECTION_HORI =2;
public static  boolean DEBUG_MODE =false ;

        public void  Container (int param1 ,int param2 ,int param3 ,int param4 ,int param5 ,int param6 ,int param7 =500,int param8 =350)
        {
            ContainerSlot _loc_10 =null ;
            this.resourcePool = new Array();
            this.images = new Array();
            this.m_box_width = param7;
            this.m_box_height = param8;
            this.x = param3;
            this.y = param4;
            this.direction = param2;
            this.num_slots = param1;
            this.visibleHeight = param6;
            this.visibleWidth = param5;
            switch(param2)
            {
                case DIRECTION_HORI:
                {
                    this.totalDistance = this.visibleWidth;
                    this.constantDistance = this.visibleHeight;
                    break;
                }
                case DIRECTION_VERT:
                {
                    this.totalDistance = this.visibleHeight;
                    this.constantDistance = this.visibleWidth;
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.m_slotDistance = 70;
            this.m_mask = this.generateMask();
            this.addChild(this.m_mask);
            this.mask = this.m_mask;
            int _loc_9 =0;
            while (_loc_9 < this.num_slots * 3)
            {

                _loc_10 = new ContainerSlot();
                this.resourcePool.push(_loc_10);
                addChild(_loc_10);
                _loc_9++;
            }
            if (DEBUG_MODE)
            {
                this.debugMode();
            }
            return;
        }//end

        public DisplayObject  getDisplayObjectByName (String param1 )
        {
            ContainerSlot _loc_2 =null ;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		_loc_2 = this.resourcePool.get(i0);

                if (!_loc_2.available)
                {
                    if (_loc_2.getChildAt(0).name == param1)
                    {
                        return _loc_2.getChildAt(0) as GameSprite;
                    }
                }
            }
            return null;
        }//end

        public void  mask_height (double param1 )
        {
            this.m_box_height = param1;
            this.m_mask.height = param1;
            return;
        }//end

        public ContainerSlot  getAvailableResource ()
        {
            ContainerSlot _loc_1 =null ;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		_loc_1 = this.resourcePool.get(i0);

                if (_loc_1.available)
                {
                    return _loc_1;
                }
            }
            return null;
        }//end

        public int  getNextAvailableSlot ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.num_slots)
            {

                if ((this.resourcePool.get(_loc_1) as ContainerSlot).available)
                {
                    return _loc_1;
                }
                _loc_1++;
            }
            return (this.num_slots - 1);
        }//end

        public boolean  push (Object param1 ,int param2 =0,String param3 ="sidebar")
        {
            ContainerSlot _loc_5 =null ;
            ButtonManager.buttonize(param1, null, EffectsUtil.getGlowEffectByAssignment(param3));
            _loc_4 = this.getAvailableResource ();
            if (this.getAvailableResource() != null)
            {
                _loc_4.available = false;
                _loc_4.addChild(param1);
                _loc_4.position = param2;
                _loc_4.addEventListener(MouseEvent.CLICK, this.slotClick);
                switch(this.direction)
                {
                    case DIRECTION_HORI:
                    {
                        _loc_4.x = (this.CSLOT_HEIGHT + this.m_slotDistance) / 2 + param2 * this.m_slotDistance;
                        _loc_4.y = (-_loc_4.height) / 2 + height / 2;
                        break;
                    }
                    case DIRECTION_VERT:
                    {
                        _loc_4.y = (-(_loc_4.height + this.m_slotDistance)) / 2 + param2 * this.m_slotDistance;
                        _loc_4.x = (-_loc_4.width) / 2 + width / 2;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
                {
                		_loc_5 = this.resourcePool.get(i0);

                    if (_loc_5.available == false && _loc_5.position >= param2)
                    {
                        if (param2 == 0 || param2 > 0 && (_loc_5 == _loc_4 || _loc_5.position > param2))
                        {
                            this.shift(_loc_5);
                        }
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }//end

        public void  slotClick (MouseEvent event =null ,ContainerSlot param2 ,boolean param3 =true )
        {
            ContainerSlot _loc_4 =null ;
            this.m_containerLock = true;
            if (event == null)
            {
                _loc_4 = param2;
            }
            else
            {
                _loc_4 =(ContainerSlot) event.currentTarget;
            }
            _loc_5 = _loc_4.position ;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		param2 = this.resourcePool.get(i0);

                if (param2.position >= _loc_5)
                {
                    this.unshift(param2);
                }
            }
            _loc_4.removeEventListener(MouseEvent.CLICK, this.slotClick);
            if (!param3)
            {
                this.tweenComplete(_loc_4);
                return;
            }
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    TweenLite.to(_loc_4, this.m_fadeOutTweenTime, {y:_loc_4.y + 80, alpha:0, onComplete:this.tweenComplete, onCompleteParams:.get(_loc_4)});
                    break;
                }
                case DIRECTION_VERT:
                {
                    TweenLite.to(_loc_4, this.m_fadeOutTweenTime, {x:_loc_4.x + 80, alpha:0, onComplete:this.tweenComplete, onCompleteParams:.get(_loc_4)});
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  shift (ContainerSlot param1 )
        {
            int _loc_2 =0;
            boolean _loc_4 =false ;
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    _loc_2 = param1.width;
                    break;
                }
                case DIRECTION_VERT:
                {
                    _loc_2 = this.CSLOT_HEIGHT;
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_5 = param1;
            _loc_6 = param1.position +1;
            _loc_5.position = _loc_6;
            _loc_3 = _loc_2(-(+this.m_slotDistance ))/2+param1.position *this.m_slotDistance ;
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    TweenLite.to(param1, this.m_shiftTweenTime, {y:0, x:_loc_3, alpha:1});
                    break;
                }
                case DIRECTION_VERT:
                {
                    _loc_4 = RuntimeVariableManager.getBoolean("CLIENT_TWEEN_QUESTS", true);
                    if (_loc_4 == true)
                    {
                        TweenLite.to(param1, this.m_shiftTweenTime, {x:0, y:_loc_3, alpha:1});
                    }
                    else
                    {
                        param1.x = 0;
                        param1.y = _loc_3;
                        param1.alpha = 1;
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  unshift (ContainerSlot param1 )
        {
            int _loc_2 =0;
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    _loc_2 = param1.width;
                    break;
                }
                case DIRECTION_VERT:
                {
                    _loc_2 = this.CSLOT_HEIGHT;
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_4 = param1;
            _loc_5 = param1.position -1;
            _loc_4.position = _loc_5;
            _loc_3 = _loc_2(-(+this.m_slotDistance ))/2+param1.position *this.m_slotDistance ;
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    TweenLite.to(param1, this.m_shiftTweenTime, {y:0, x:_loc_3});
                    break;
                }
                case DIRECTION_VERT:
                {
                    TweenLite.to(param1, this.m_shiftTweenTime, {x:0, y:_loc_3});
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  removeSlot (ContainerSlot param1 )
        {
            ContainerSlot _loc_2 =null ;
            int _loc_3 =0;
            ContainerSlot _loc_4 =null ;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		_loc_2 = this.resourcePool.get(i0);

                if (_loc_2.position == param1.position)
                {
                    param1 = _loc_2;
                }
            }
            _loc_3 = param1.position;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		_loc_4 = this.resourcePool.get(i0);

                if (_loc_4.position >= _loc_3)
                {
                    this.unshift(_loc_4);
                }
            }
            param1.removeEventListener(MouseEvent.CLICK, this.slotClick);
            switch(this.direction)
            {
                case DIRECTION_HORI:
                {
                    TweenLite.to(param1, this.m_fadeOutTweenTime, {y:param1.y + 80, alpha:0, onComplete:this.tweenComplete, onCompleteParams:.get(param1)});
                    break;
                }
                case DIRECTION_VERT:
                {
                    TweenLite.to(param1, this.m_fadeOutTweenTime, {x:param1.x + 80, alpha:0, onComplete:this.tweenComplete, onCompleteParams:.get(param1)});
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  debugMode ()
        {
            int _loc_3 =0;
            MovieClip _loc_1 =new MovieClip ();
            _loc_1.graphics.beginFill(16711680, 1);
            _loc_1.graphics.drawRect(0, 0, 15, 15);
            _loc_1.graphics.endFill();
            _loc_1.addEventListener(MouseEvent.CLICK, this.debugPush);
            addChild(_loc_1);
            int _loc_2 =-1;
            while (_loc_2 < this.num_slots)
            {

                _loc_1 = new MovieClip();
                _loc_1.graphics.beginFill(16711680, 1);
                _loc_1.graphics.drawCircle(0, 0, 2);
                _loc_1.graphics.endFill();
                _loc_3 = _loc_2 * (this.totalDistance / this.num_slots) + this.m_slotDistance / 2;
                switch(this.direction)
                {
                    case DIRECTION_HORI:
                    {
                        _loc_1.x = _loc_3;
                        _loc_1.y = this.constantDistance / 2;
                        break;
                    }
                    case DIRECTION_VERT:
                    {
                        _loc_1.y = _loc_3;
                        _loc_1.x = this.constantDistance / 2;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                addChild(_loc_1);
                _loc_2++;
            }
            return;
        }//end

        public void  debugPush (MouseEvent event )
        {
            MovieClip _loc_2 =null ;
            if (this.m_containerLock == false)
            {
                _loc_2 = new MovieClip();
                _loc_2.graphics.beginFill(65280, 0.6);
                _loc_2.graphics.drawRect(0, 0, 40, 40);
                _loc_2.graphics.endFill();
                this.push(_loc_2);
            }
            return;
        }//end

        public void  debugOutput ()
        {
            ContainerSlot _loc_3 =null ;
            int _loc_1 =0;
            int _loc_2 =0;
            for(int i0 = 0; i0 < this.resourcePool.size(); i0++)
            {
            		_loc_3 = this.resourcePool.get(i0);

                if (_loc_3.available)
                {
                    _loc_1 = _loc_1 + 1;
                    continue;
                }
                _loc_2 = _loc_2 + 1;
            }
            return;
        }//end

        private Sprite  generateMask ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.graphics.beginFill(16711680, 0.5);
            _loc_1.graphics.drawRect(-10, 0, this.m_box_width, this.m_box_height);
            _loc_1.graphics.endFill();
            return _loc_1;
        }//end

        private void  tweenComplete (ContainerSlot param1 )
        {
            param1.removeEventListener(MouseEvent.CLICK, this.slotClick);
            param1.reset();
            this.m_containerLock = false;
            return;
        }//end

        public double  tweenTime ()
        {
            return this.m_shiftTweenTime + this.m_fadeOutTweenTime;
        }//end

    }



