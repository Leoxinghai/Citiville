package Modules.guide.ui;

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

import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class GuideArrow
    {
        private int m_direction ;
        private boolean m_useRotate ;
        private Loader m_loader ;
        private DisplayObject m_asset ;
        private boolean m_active =true ;
        private double m_tweenTime =0.3;
        protected boolean m_aboveHud ;
        protected double m_locX ;
        protected double m_locY ;
        protected double m_uiX ;
        protected double m_uiY ;
        protected double m_width ;
        protected double m_height ;
        protected boolean m_leftSnap ;
        protected boolean m_bottomSnap ;
        protected DisplayObjectContainer m_parent ;
        public static  int ARROW_UP =1;
        public static  int ARROW_RIGHT =2;
        public static  int ARROW_DOWN =3;
        public static  int ARROW_LEFT =4;

        public  GuideArrow (String param1 ,double param2 ,double param3 ,double param4 =0,double param5 =0,int param6 =4,boolean param7 =true ,boolean param8 =false ,boolean param9 =false ,DisplayObjectContainer param10 =null )
        {
            this.m_locX = param2;
            this.m_locY = param3;
            this.m_width = param4;
            this.m_height = param5;
            this.m_direction = param6;
            this.m_useRotate = param7;
            this.m_aboveHud = param8;
            this.m_parent = param10 ? (param10) : (Global.ui);
            if (param8)
            {
                this.m_leftSnap = param9;
                this.m_bottomSnap = param3 > this.m_parent.height / 2;
            }
            this.m_loader = LoadingManager.load(param1, this.onLoaderEvent, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        public DisplayObject  getAsset ()
        {
            return this.m_asset;
        }//end

        public void  release ()
        {
            this.m_active = false;
            if (this.m_asset != null)
            {
                TweenLite.to(this.m_asset, this.m_tweenTime, {alpha:0, onComplete:this.onFinishRemoveMask});
            }
            return;
        }//end

        public void  onResize ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            if (!this.m_asset)
            {
                return;
            }
            if (this.m_aboveHud)
            {
                if (this.m_leftSnap)
                {
                    _loc_1 = this.m_uiX - this.m_parent.x;
                    this.m_asset.x = this.m_asset.x + _loc_1;
                    this.m_uiX = this.m_parent.x;
                }
                if (this.m_bottomSnap)
                {
                    _loc_2 = this.m_uiY - this.m_parent.height;
                    this.m_asset.y = this.m_asset.y - _loc_2;
                    this.m_uiY = this.m_parent.height;
                }
            }
            else
            {
                _loc_3 = this.m_uiX - this.m_parent.x;
                _loc_4 = this.m_uiY - this.m_parent.y;
                this.m_asset.x = this.m_asset.x + _loc_3;
                this.m_asset.y = this.m_asset.y + _loc_4;
                this.m_uiX = this.m_parent.x;
                this.m_uiY = this.m_parent.y;
            }
            return;
        }//end

        private void  onFinishRemoveMask ()
        {
            if (this.m_asset && this.m_parent)
            {
                if (this.m_parent.contains(this.m_asset))
                {
                    this.m_parent.removeChild(this.m_asset);
                }
            }
            return;
        }//end

        private void  onLoaderEvent (Event event )
        {
            if (this.m_loader && this.m_loader.content && this.m_active)
            {
                this.m_asset = this.m_loader.content;
                if (this.m_width != 0)
                {
                    this.m_asset.width = this.m_width;
                }
                if (this.m_height != 0)
                {
                    this.m_asset.height = this.m_height;
                }
                if (this.m_useRotate && this.m_direction != ARROW_RIGHT)
                {
                    switch(this.m_direction)
                    {
                        case ARROW_LEFT:
                        {
                            this.m_asset.rotation = 180;
                            break;
                        }
                        case ARROW_DOWN:
                        {
                            this.m_asset.rotation = 90;
                            break;
                        }
                        case ARROW_UP:
                        {
                            this.m_asset.rotation = 270;
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
                this.updatePosition();
                this.m_asset.visible = true;
                this.m_asset.scaleX = 1;
                this.m_asset.scaleY = 1;
                if (this.m_aboveHud)
                {
                    this.m_parent.addChild(this.m_asset);
                    this.m_parent.setChildIndex(this.m_asset, (this.m_parent.numChildren - 1));
                }
                else
                {
                    this.m_parent.addChildAt(this.m_asset, 0);
                }
            }
            return;
        }//end

        public void  setPosition (double param1 ,double param2 )
        {
            this.m_locX = param1;
            this.m_locY = param2;
            this.updatePosition();
            return;
        }//end

        private void  updatePosition ()
        {
            Point _loc_1 =null ;
            if (!this.m_asset)
            {
                return;
            }
            switch(this.m_direction)
            {
                case ARROW_LEFT:
                {
                    this.m_asset.x = this.m_locX;
                    this.m_asset.y = this.m_locY + this.m_asset.height / 2;
                    break;
                }
                case ARROW_RIGHT:
                {
                    this.m_asset.x = this.m_locX;
                    this.m_asset.y = this.m_locY - this.m_asset.height / 2;
                    break;
                }
                case ARROW_DOWN:
                {
                    _loc_1 = this.m_parent.globalToLocal(new Point(this.m_locX, this.m_locY));
                    this.m_asset.x = _loc_1.x + this.m_asset.width / 2;
                    this.m_asset.y = this.m_locY;
                    break;
                }
                case ARROW_UP:
                {
                    _loc_1 = this.m_parent.globalToLocal(new Point(this.m_locX, this.m_locY));
                    this.m_asset.x = _loc_1.x + this.m_asset.width / 2;
                    this.m_asset.y = this.m_locY;
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.m_uiX = this.m_parent.x;
            if (this.m_bottomSnap)
            {
                this.m_uiY = this.m_parent.height;
            }
            else
            {
                this.m_uiY = this.m_parent.y;
            }
            return;
        }//end

        public boolean  hasLoaded ()
        {
            return this.m_loader.contentLoaderInfo.bytesLoaded >= this.m_loader.contentLoaderInfo.bytesTotal;
        }//end

    }



