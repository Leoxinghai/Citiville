package com.xiyu.flash.framework.widgets;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
import com.xiyu.util.*;

//import flash.geom.Rectangle;
//import flash.geom.Point;
import com.xiyu.flash.framework.graphics.Color;
//import flash.ui.Keyboard;
import com.xiyu.flash.framework.graphics.Graphics2D;
import java.util.Vector;
import java.util.*;

    public class CWidget extends CWidgetContainer {

        public static final int LAY_Right =0x0400 ;
        public static final int LAY_Above =0x0100 ;
        public static final int LAY_GrowToBottom =524288;
        public static final int LAY_SameLeft =0x1000 ;
        public static final int LAY_SetHeight =128;
        public static final int LAY_Left =0x0800 ;
        public static final int LAY_SetTop =32;
        public static final int LAY_Below =0x0200 ;
        public static final int LAY_GrowToTop =262144;
        public static final int LAY_HCenter =0x100000 ;
        public static final int LAY_GrowToLeft =131072;
        public static final int LAY_Max =0x400000 ;
        public static final int LAY_SameWidth =1;
        public static final int LAY_SameHeight =2;
        public static final int LAY_SetLeft =16;
        public static final int LAY_VCenter =0x200000 ;
        public static final int LAY_SameBottom =0x8000 ;
        public static final int LAY_SameTop =0x4000 ;
        public static final int LAY_GrowToRight =65536;
        public static final int LAY_SameRight =0x2000 ;
        public static final int LAY_SetWidth =64;
        public static final int LAY_SetPos =(LAY_SetLeft | LAY_SetTop);
        public static final int LAY_SetSize =(LAY_SetWidth | LAY_SetHeight);
        public static final int LAY_SameCorner =(LAY_SameLeft | LAY_SameTop);
        public static final int LAY_SameSize =(LAY_SameWidth | LAY_SameHeight);

        public void  setColors (Array theColors ){
            this.colors = new Array();
            int aNumColors =theColors.length();
            int i = 0;
            while (i < aNumColors)
            {
            	this.setColor(i,(Color)this.colors.elementAt(i));
                i++;
            };
            markDirty(null);
        }

        protected Rectangle mBounds ;

        public void  deferOverlay (int priority ){
            widgetManager.deferOverlay(this, priority);
        }
        public boolean  isPointVisible (int x ,int y ){
            return (true);
        }
        public void  lostFocus (){
            this.hasFocus = false;
        }

        public boolean isOver =false ;

        public boolean  contains (int x0 ,int y0 ){
            Point a ;
            Point b ;

//            System.out.println("contains."+this.x+":"+this.y+"::"+this.width+":"+this.height+"::"+x0+":"+y0+":"+this);
            if ((((this.width == 0)) || ((this.height == 0))))
            {
                return (false);
            };
            if (x0 < this.x)
            {
                return (false);
            };
            if (y0 < this.y)
            {
                return (false);
            };
            if (x0 >= (this.x + this.width))
            {
                return (false);
            };
            if (y0 >= (this.y + this.height))
            {
                return (false);
            };
            if (!this.mUsePolyShape)
            {
                return (true);
            };
            if (this.mPolyShape.size() < 3)
            {
                return (false);
            };
            boolean inside = false;
            int i =0;
            int j =0;
            int numVerts =this.mPolyShape.size() ;
            i = 0;
            j = (numVerts - 1);
            while (i < numVerts)
            {
            	a=this.mPolyShape.elementAt(i);
            	b=this.mPolyShape.elementAt(j);
                if (((!(((a.y > y0) == (b.y > y0)))) && ((x0 < ((((b.x - a.x) * (y0 - a.y)) / (b.y - a.y)) + a.x)))))
                {
                    if(inside)
                    	inside = false;
                    else
                    	inside = true;
                };
                j = i++;
            };
            return (inside);
        }
        public void  setColor (int index ,Color color ){
        	this.colors.add(index,color);
            markDirty(null);
        }

        public CWidget tabNext =null ;

        public void  layout (int layoutFlags ,CWidget relative ,int theLeftPad ,int theTopPad ,int theWidthPad ,int theHeightPad ){
            int aRelLeft = (int)relative.x ;
            int aRelTop =(int)relative.y ;
            if (relative == parent)
            {
                aRelLeft = 0;
                aRelTop = 0;
            };
            int aRelWidth =relative.width;
            int aRelHeight =relative.height;
            int aRelRight =(aRelLeft +aRelWidth );
            int aRelBottom =(aRelTop +aRelHeight );
            int aLeft =(int)x ;
            int aTop =(int)y ;
            int aWidth =width ;
            int aHeight =height ;
            int aType =1;
            while (aType < LAY_Max)
            {
                if ((layoutFlags & aType)!=0)
                {
                    switch (aType)
                    {
                        case LAY_SameWidth:
                            aWidth = (aRelWidth + theWidthPad);
                            break;
                        case LAY_SameHeight:
                            aHeight = (aRelHeight + theHeightPad);
                            break;
                        case LAY_Above:
                            aTop = ((aRelTop - aHeight) + theTopPad);
                            break;
                        case LAY_Below:
                            aTop = (aRelBottom + theTopPad);
                            break;
                        case LAY_Right:
                            aLeft = (aRelRight + theLeftPad);
                            break;
                        case LAY_Left:
                            aLeft = ((aRelLeft - aWidth) + theLeftPad);
                            break;
                        case LAY_SameLeft:
                            aLeft = (aRelLeft + theLeftPad);
                            break;
                        case LAY_SameRight:
                            aLeft = ((aRelRight - aWidth) + theLeftPad);
                            break;
                        case LAY_SameTop:
                            aTop = (aRelTop + theTopPad);
                            break;
                        case LAY_SameBottom:
                            aTop = ((aRelBottom - aHeight) + theTopPad);
                            break;
                        case LAY_GrowToRight:
                            aWidth = ((aRelRight - aLeft) + theWidthPad);
                            break;
                        case LAY_GrowToLeft:
                            aWidth = ((aRelLeft - aLeft) + theWidthPad);
                            break;
                        case LAY_GrowToTop:
                            aHeight = ((aRelTop - aTop) + theHeightPad);
                            break;
                        case LAY_GrowToBottom:
                            aHeight = ((aRelBottom - aTop) + theHeightPad);
                            break;
                        case LAY_SetLeft:
                            aLeft = theLeftPad;
                            break;
                        case LAY_SetTop:
                            aTop = theTopPad;
                            break;
                        case LAY_SetWidth:
                            aWidth = theWidthPad;
                            break;
                        case LAY_SetHeight:
                            aHeight = theHeightPad;
                            break;
                        case LAY_HCenter:
                            aLeft = ((aRelLeft + ((aRelWidth - aWidth) / 2)) + theLeftPad);
                            break;
                        case LAY_VCenter:
                            aTop = ((aRelTop + ((aRelHeight - aHeight) / 2)) + theTopPad);
                            break;
                    };
                };
                aType = (aType << 1);
            };
            this.resize(aLeft, aTop, aWidth, aHeight);
        }
        public void  gotFocus (){
            this.hasFocus = true;
        }
        public Color  getColor (int index ){
            if ((((index < 0)) || ((index >= this.colors.length()))))
            {
                return (null);
            };
            return(Color)(this.colors.elementAt(index));
        }
        public void  setVisible (boolean isVisible ){
            if (this.visible == isVisible)
            {
                return;
            };
            this.visible = isVisible;
            if (this.visible == true)
            {
                markDirty(null);
            }
            else
            {
                markDirtyFull(null);
            };
            if (widgetManager != null)
            {
                widgetManager.rehupMouse();
            };
        }
        public void  resize (double x ,double y ,int width ,int height ){
            if ((((((((this.x == x)) && ((this.y == y)))) && ((this.width == width)))) && ((this.height == height))))
            {
                return;
            };
            markDirtyFull(null);
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.mBounds.x = (int)x;
            this.mBounds.y = (int)y;
            this.mBounds.width = (int)width;
            this.mBounds.height = (int)height;

            markDirty(null);
            if (widgetManager != null)
            {
                widgetManager.rehupMouse();
            };
        }

        public boolean wantsFocus =false ;
        public boolean hasTransparencies =false ;

        public void  orderInManagerChanged (){
        }
         public void  onKeyUp (int keyCode ){
        }
         public void  onMouseUp (int x ,int y ){
        }
         public void  onKeyDown (int keyCode ){
        }
         public void  draw (Graphics2D g ){
        }
        public void  showFinger (boolean on ){
            if (widgetManager == null)
            {
                return;
            };
            widgetManager.showFinger(on, this);
        }

        protected Vector<Point> mPolyShape;

         public void  update (){
            super.update();
        }
        public void  widgetRemovedHelper (){
            CWidget w ;
            CPreModalInfo info ;
            if (widgetManager == null)
            {
                return;
            };
            int aNumWidgets =widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)widgets.elementAt(i);
                w.widgetRemovedHelper();
                i++;
            };
            widgetManager.disableWidget(this);
            Array modal =widgetManager.preModalInfoList ;
            int aNumInfos =modal.length();
            i = 0;
            while (i < aNumInfos)
            {
            	info=(CPreModalInfo)modal.elementAt(i);
                if (info.prevBaseModalWidget == this)
                {
                    info.prevBaseModalWidget = null;
                };
                if (info.prevFocusWidget == this)
                {
                    info.prevFocusWidget = null;
                };
                i++;
            };
            removedFromManager(widgetManager);
            markDirtyFull(this);
            widgetManager = null;
        }
         public void  onMouseDown (int x ,int y ){
        }
        public void  move (int x ,int y ){
            this.resize(x, y, width, height);
        }

        public boolean doFinger =false ;

         public void  onMouseDrag (int x ,int y ){
        }

        public boolean hasFocus =false ;
        public CWidget tabPrev =null ;

         public void  onMouseLeave (){
        }
        public void  drawOverlay (Graphics2D g ,int priority){
        }

        public Array colors ;

         public void  onMouseEnter (){
        }

        public boolean isDown =false ;

         public void  onMouseWheel (int delta ){
        }
         public void  onMouseMove (int x ,int y ){
        }

        public boolean visible =true ;

        public void  setPolyShape (Vector<Point> vertices){
            Point p ;
            this.mPolyShape = vertices;
            int left =NumberX.MAX_VALUE ;
            int right =NumberX.MIN_VALUE ;
            int top =NumberX.MAX_VALUE ;
            int bottom =NumberX.MIN_VALUE ;
            int numVerts =vertices.size();
            int i =0;
            while (i < numVerts)
            {
            	p=vertices.elementAt(i);
                left = Math.min(p.x, left);
                right = Math.max(p.x, right);
                top = Math.min(p.y, top);
                bottom = Math.max(p.y, bottom);
                i++;
            };
            this.mBounds.left(left);
            this.mBounds.right(right);
            this.mBounds.top(top);
            this.mBounds.bottom(bottom);
            this.x = this.mBounds.x;
            this.y = this.mBounds.y;
            this.width = this.mBounds.width();
            this.height = this.mBounds.height();
            this.mUsePolyShape = true;
        }

        public boolean mUsePolyShape =false ;
        public boolean disabled =false ;

         public void  onKeyChar (int charCode ){
        }
        public void  setDisabled (boolean isDisabled ){
            if (this.disabled == isDisabled)
            {
                return;
            };
            this.disabled = isDisabled;
            if (((isDisabled) && (!((widgetManager == null)))))
            {
                widgetManager.disableWidget(this);
            };
            markDirty(null);
            if (((((!(isDisabled)) && (!((widgetManager == null))))) && (this.contains(widgetManager.lastMouseX, widgetManager.lastMouseY))))
            {
                widgetManager.setMousePosition(widgetManager.lastMouseX, widgetManager.lastMouseY);
            };
        }

        public  CWidget (){
            super();
            this.colors = new Array();
            this.mPolyShape = new Vector<Point>();
            this.mBounds = new Rectangle();
        }
    }


