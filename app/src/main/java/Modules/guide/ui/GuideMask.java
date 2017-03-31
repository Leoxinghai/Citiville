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

import Classes.*;
import Display.*;
import Modules.guide.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class GuideMask extends Sprite
    {
        private boolean m_shown =false ;
        private int m_maskType =0;
        private double m_bgAlpha =0;
        private boolean m_showOutline =false ;
        private Point m_prevStageSize =null ;
        private Function m_onResize =null ;
        private Point m_spotCenter ;
        private Point m_spotSize ;
        private int m_spotStyle ;
        private double m_tweenTime =0.3;
        private static  int MASK_TYPE_NONE =0;
        private static  int MASK_TYPE_NORMAL =1;
        private static  int MASK_TYPE_SPOTLIGHT =2;

        public  GuideMask ()
        {
            return;
        }//end

        public int  getMaskType ()
        {
            return this.m_maskType;
        }//end

        public void  setTweenTime (double param1 )
        {
            this.m_tweenTime = param1;
            return;
        }//end

        public void  displayMaskWithParent (DisplayObjectContainer param1 ,int param2 =-1,boolean param3 =true )
        {
            Point _loc_4 =null ;
            Graphics _loc_5 =null ;
            if (!this.m_shown && param1 != null)
            {
                param1.addChild(this);
                if (param2 != -1)
                {
                    param1.setChildIndex(this, param2);
                }
                else
                {
                    param1.setChildIndex(this, (numChildren - 1));
                }
                if (param3)
                {
                    this.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                    this.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove, false, 0, true);
                    this.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver, false, 0, true);
                    this.addEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut, false, 0, true);
                }
                this.m_shown = true;
                this.m_maskType = MASK_TYPE_NORMAL;
                _loc_4 = this.globalToLocal(new Point(0, 0));
                _loc_5 = this.graphics;
                _loc_5.clear();
                _loc_5.beginFill(0, 0.75);
                _loc_5.drawRect(_loc_4.x, _loc_4.y, param1.width, param1.height);
                _loc_5.endFill();
                TweenLite.from(this, this.m_tweenTime, {alpha:0});
            }
            return;
        }//end

        public void  displayMask (int param1 =0,double param2 =0,boolean param3 =false )
        {
            if (!this.m_shown)
            {
                switch(param1)
                {
                    case GuideConstants.MASK_GAME_AND_BOTTOMBAR:
                    {
                        Global.ui.addChildAt(this, (Global.ui.numChildren - 1));
                        this.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut, false, 0, true);
                        break;
                    }
                    case GuideConstants.MASK_ALL_UI:
                    {
                        Global.ui.addChild(this);
                        Global.ui.setChildIndex(this, (Global.ui.numChildren - 1));
                        this.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut, false, 0, true);
                        break;
                    }
                    case GuideConstants.MASK_GAME:
                    {
                    }
                    default:
                    {
                        Global.ui.addChildAt(this, 0);
                        break;
                        break;
                    }
                }
                this.m_shown = true;
                this.m_maskType = MASK_TYPE_NORMAL;
                this.m_bgAlpha = param2;
                this.m_showOutline = param3;
                this.redrawMask();
                TweenLite.from(this, this.m_tweenTime, {alpha:0});
            }
            return;
        }//end

        public void  removeMask ()
        {
            if (this.m_shown && this.parent != null)
            {
                TweenLite.to(this, this.m_tweenTime, {alpha:0, onComplete:this.onFinishRemoveMask});
            }
            return;
        }//end

        private void  onFinishRemoveMask ()
        {
            parent.removeChild(this);
            removeEventListener(MouseEvent.CLICK, this.onMaskClicked);
            removeEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove);
            removeEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver);
            removeEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut);
            UI.popBlankCursor();
            this.m_shown = false;
            this.m_spotCenter = null;
            this.m_spotSize = null;
            this.m_prevStageSize = null;
            this.m_onResize = null;
            this.m_maskType = MASK_TYPE_NONE;
            return;
        }//end

        public void  displaySpotLight (DisplayObjectContainer param1 ,Point param2 ,Point param3 ,int param4 ,int param5 =0,double param6 =0,boolean param7 =false ,Function param8 =null )
        {
            if (!this.m_shown && param1 != null)
            {
                switch(param5)
                {
                    case GuideConstants.MASK_GAME:
                    {
                        param1.addChildAt(this, 0);
                        this.addEventListener(MouseEvent.MOUSE_UP, this.onLotSitesUp, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_MOVE, this.onLotSitesMove, false, 0, true);
                        break;
                    }
                    default:
                    {
                        param1.addChildAt(this, 0);
                        break;
                    }
                    case GuideConstants.MASK_ALL_UI:
                    {
                        param1.addChildAt(this, (param1.numChildren - 1));
                        this.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut, false, 0, true);
                        break;
                    }
                    case GuideConstants.MASK_LOT_SITES:
                    {
                        param1.addChild(this);
                        param1.setChildIndex(this, (param1.numChildren - 1));
                        this.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskMouseMove, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskMouseOver, false, 0, true);
                        this.addEventListener(MouseEvent.MOUSE_OUT, this.onMaskMouseOut, false, 0, true);
                        break;
                    }
                }
                this.m_shown = true;
                this.m_maskType = MASK_TYPE_SPOTLIGHT;
                this.m_spotCenter = param2;
                this.m_spotSize = param3;
                this.m_spotStyle = param4;
                this.m_bgAlpha = param6;
                this.m_showOutline = param7;
                this.m_onResize = param8;
                this.redrawMask();
            }
            return;
        }//end

        public void  resize ()
        {
            if (this.m_spotCenter !== null)
            {
                if (this.m_onResize !== null)
                {
                    this.m_spotCenter = this.m_onResize();
                }
                else if (this.m_prevStageSize !== null)
                {
                    this.m_spotCenter.x = this.m_spotCenter.x + (Global.ui.screenWidth - this.m_prevStageSize.x) / 2;
                    this.m_spotCenter.y = this.m_spotCenter.y + (Global.ui.screenHeight - this.m_prevStageSize.y) / 2;
                }
            }
            this.redrawMask();
            return;
        }//end

        private void  redrawMask ()
        {
            Point _loc_3 =null ;
            Rectangle _loc_4 =null ;
            Rectangle _loc_5 =null ;
            this.m_prevStageSize = new Point(Global.ui.screenWidth, Global.ui.screenHeight);
            _loc_1 = this.globalToLocal(new Point(0,0));
            _loc_2 = this.graphics ;
            _loc_2.clear();
            if (this.m_maskType == MASK_TYPE_SPOTLIGHT)
            {
                _loc_3 = this.globalToLocal(this.m_spotCenter);
                _loc_4 = new Rectangle(_loc_3.x - this.m_spotSize.x / 2, _loc_3.y - this.m_spotSize.y / 2, this.m_spotSize.x, this.m_spotSize.y);
                _loc_2.beginFill(0, this.m_bgAlpha);
                _loc_2.drawRect(_loc_1.x, _loc_1.y, Global.ui.screenWidth, Global.ui.screenHeight);
                if (this.m_spotStyle == GuideConstants.MASK_STYLE_RECTANGLE)
                {
                    _loc_2.drawRoundRect(_loc_4.x, _loc_4.y, _loc_4.width, _loc_4.height, 5);
                }
                else
                {
                    _loc_2.drawEllipse(_loc_4.x, _loc_4.y, _loc_4.width, _loc_4.height);
                }
                _loc_2.endFill();
                if (this.m_showOutline)
                {
                    _loc_5 = new Rectangle(_loc_4.x + 2, _loc_4.y + 2, _loc_4.width - 4, _loc_4.height - 4);
                    _loc_2.lineStyle(5, EmbeddedArt.BORDER_MAIN_COLOR, 1, true);
                    if (this.m_spotStyle == GuideConstants.MASK_STYLE_RECTANGLE)
                    {
                        _loc_2.drawRoundRect(_loc_5.x, _loc_5.y, _loc_5.width, _loc_5.height, 5);
                    }
                    else
                    {
                        _loc_2.drawEllipse(_loc_5.x, _loc_5.y, _loc_5.width, _loc_5.height);
                    }
                }
            }
            else
            {
                _loc_2.beginFill(0, this.m_bgAlpha);
                _loc_2.drawRect(_loc_1.x, _loc_1.y, Global.ui.screenWidth, Global.ui.screenHeight);
                _loc_2.endFill();
            }
            return;
        }//end

        protected void  onMaskClicked (MouseEvent event )
        {
            event.stopPropagation();
            return;
        }//end

        private void  onMaskMouseMove (MouseEvent event )
        {
            Global.ui.hideAnyToolTip();
            UI.pushBlankCursor();
            event.stopPropagation();
            return;
        }//end

        private void  onMaskMouseOver (MouseEvent event )
        {
            UI.pushBlankCursor();
            event.stopPropagation();
            return;
        }//end

        private void  onMaskMouseOut (MouseEvent event )
        {
            UI.popBlankCursor();
            event.stopPropagation();
            return;
        }//end

        private void  onLotSitesUp (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  onLotSitesMove (MouseEvent event )
        {
            Global.world.getTopGameMode().onMouseMove(event);
            return;
        }//end

    }


