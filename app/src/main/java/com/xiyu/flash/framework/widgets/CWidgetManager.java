package com.xiyu.flash.framework.widgets;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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

//import flash.geom.Point;
//import flash.ui.Keyboard;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.AppBase;
import com.xinghai.debug.Debug;

    public class CWidgetManager extends CWidgetContainer {

        public static final int WIDGETFLAGS_DRAW =4;
        public static final int WIDGETFLAGS_UPDATE =1;
        public static final int WIDGETFLAGS_CLIP =8;
        public static final int WIDGETFLAGS_ALLOW_FOCUS =32;
        public static final int WIDGETFLAGS_ALLOW_MOUSE =16;
        public static final int WIDGETFLAGS_MARK_DIRTY =2;

        public int minDeferredOverlayPriority =0;
        public Array deferredOverlayWidgets ;
        public int lastMouseX =0;
        public int lastMouseY =0;

        public CWidget  getWidgetAt (int x ,int y ,Point localPos ){
            CWidget w =this.getAnyWidgetAt(x ,y ,localPos );
            if (((!((w == null))) && (w.disabled)))
            {
                w = null;
            };
            return (w);
        }
        public void  doKeyUp (int keyCode ){
            this.lastInputUpdateCount = updateCount;
            this.keyDown.add(keyCode,false);
            if (this.focusWidget != null)
            {
                this.focusWidget.onKeyUp(keyCode);
            };
        }
        public void  initModalFlags (CModalFlags modalFlags ){
            modalFlags.isOver = (this.baseModalWidget == null);
            modalFlags.overFlags = this.getWidgetFlags();
            modalFlags.underFlags = CFlagsMod.getModFlags(modalFlags.overFlags, this.belowModalFlagsMod);
        }

        public boolean mShowFinger =false ;

        public void  rehupMouse (){
            CWidget widget ;
            CWidget tmpWidget ;
            if (this.lastDownWidget != null)
            {
                if (this.overWidget != null)
                {
                    widget = this.getWidgetAt(this.lastMouseX, this.lastMouseY, new Point());
                    if (widget != this.lastDownWidget)
                    {
                        tmpWidget = this.overWidget;
                        this.overWidget = null;
                        this.doMouseLeave(tmpWidget);
                    };
                };
            }
            else
            {
                if (this.mouseIn)
                {
                    this.setMousePosition(this.lastMouseX, this.lastMouseY);
                };
            };
        }
        public void  doMouseUps (CWidget widget,int downCode){
            if (widget == null)
            {
                if (((!((this.lastDownWidget == null))) && (!((this.downButtons == 0)))))
                {
                    this.doMouseUps(this.lastDownWidget, this.downButtons);
                    this.downButtons = 0;
                    this.lastDownWidget = null;
                };
                return;
            };
            widget.isDown = false;
            widget.onMouseUp((int)(this.lastMouseX - widget.x), (int)(this.lastMouseY - widget.y));
        }

        public CFlagsMod belowModalFlagsMod ;

        public void  doMouseExit (int x ,int y ){
            this.lastInputUpdateCount = updateCount;
            this.mouseIn = false;
            if (this.overWidget != null)
            {
                this.doMouseLeave(this.overWidget);
                this.overWidget = null;
            };
        }
        public void  setBaseModal (CWidget widget ,CFlagsMod flagsMod ){
            int aDownButtons ;
            this.baseModalWidget = widget;
            this.belowModalFlagsMod = flagsMod;
            CWidget tmpWidget ;
            if (((this.overWidget != null && (this.belowModalFlagsMod.removeFlags & WIDGETFLAGS_ALLOW_MOUSE)!=0) && (isBelow(this.overWidget, this.baseModalWidget))))
            {
                tmpWidget = this.overWidget;
                this.overWidget = null;
                this.doMouseLeave(tmpWidget);
            };
            if (((this.lastDownWidget != null && (this.belowModalFlagsMod.removeFlags & WIDGETFLAGS_ALLOW_MOUSE)!=0 ) && (isBelow(this.lastDownWidget, this.baseModalWidget))))
            {
                tmpWidget = this.lastDownWidget;
                aDownButtons = this.downButtons;
                this.downButtons = 0;
                this.lastDownWidget = null;
                this.doMouseUps(tmpWidget, aDownButtons);
            };
            if (((this.focusWidget != null && (this.belowModalFlagsMod.removeFlags & WIDGETFLAGS_ALLOW_FOCUS)!=0) && (isBelow(this.focusWidget, this.baseModalWidget))))
            {
                tmpWidget = this.focusWidget;
                this.focusWidget = null;
                tmpWidget.lostFocus();
            };
        }

        public CWidget defaultTab =null ;

        public void  doMouseUp (int x ,int y ){
            CWidget widget ;
            this.lastInputUpdateCount = updateCount;
            System.out.println("CWidgetManager.doMouseUp."+this.lastDownWidget+":"+this.downButtons);
            if (this.lastDownWidget != null && this.downButtons != 0)
            {
                widget = this.lastDownWidget;
                this.downButtons = 0;
                this.lastDownWidget = null;
                widget.isDown = false;
                widget.onMouseUp((int)(x - widget.x), (int)(y - widget.y));
            };
			if(this.focusWidget != null)
				this.focusWidget.isDown = false;
            this.setMousePosition(x, y);
        }
         public void  setFocus (CWidget widget ){
            if (widget == this.focusWidget)
            {
                return;
            };
            if (this.focusWidget != null)
            {
                this.focusWidget.lostFocus();
            };
            if (((!((widget == null))) && ((widget.widgetManager == this))))
            {
                this.focusWidget = widget;
                if (((this.hasFocus) && (!((this.focusWidget == null)))))
                {
                    this.focusWidget.gotFocus();
                };
            }
            else
            {
                this.focusWidget = null;
            };
        }
        public void  doMouseEnter (CWidget widget ){
            widget.isOver = true;
            widget.onMouseEnter();
            if (widget.doFinger)
            {
                widget.showFinger(true);
            };
        }
        public void  deferOverlay (CWidget widget ,int priority ){
            Object[] o = { widget, priority};

            this.deferredOverlayWidgets.push(o);
            if (priority < this.minDeferredOverlayPriority)
            {
                this.minDeferredOverlayPriority = priority;
            };
        }

        public Array keyDown ;

        public void  flushDeferredOverlayWidgets (Graphics2D g ,int maxPriority ){
            int nextMinPriority ;
            int aNumWidgets ;
            int i =0;
            Object[] o ;
            CWidget w ;
            int priority ;
            while (true)
            {
                nextMinPriority = NumberX.MAX_VALUE;
                aNumWidgets = this.deferredOverlayWidgets.length();
                i = 0;
                while (i < aNumWidgets)
                {
                	o=(Object[])this.deferredOverlayWidgets.elementAt(i);
                    w = (CWidget)o.get(0);
                    if (w != null)
                    {
                        priority = ((Integer)o.get(1)).intValue();
                        if (priority == this.minDeferredOverlayPriority)
                        {
                            g.pushState();
                            g.translate(w.x, w.y);
                            w.drawOverlay(g, priority);
                            g.popState();
                            o.put(0,  null);
                        }
                        else
                        {
                            if (priority < nextMinPriority)
                            {
                                nextMinPriority = priority;
                            };
                        };
                    };
                    i++;
                };
                this.minDeferredOverlayPriority = nextMinPriority;
                if (nextMinPriority == NumberX.MAX_VALUE)
                {
                    this.deferredOverlayWidgets = new Array();
                    break;
                };
                if (nextMinPriority >= maxPriority)
                {
                    break;
                };
            };
        }

        public CWidget popupCommandWidget ;

        public void  setPopupCommandWidget (CWidget widget ){
            this.popupCommandWidget = widget;
            addWidget(this.popupCommandWidget);
        }

        public AppBase app ;
        public boolean mouseIn =true ;

        public boolean  updateFrame (){
            this.initModalFlags(this.modalFlags);
            updateCount++;
            lastWMUpdateCount = updateCount;
            updateAll(this.modalFlags);
            return (dirty);
        }

        public CFlagsMod lostFocusFlagsMod ;

        public void  doMouseWheel (int delta ){
            this.lastInputUpdateCount = updateCount;
            if (this.focusWidget != null)
            {
                this.focusWidget.onMouseWheel(delta);
            };
        }

        public int widgetFlags =0;

        public void  doMouseDrag (int x ,int y ){
            CWidget tmpWidget ;
            Point absPos ;
            Point localPos ;
            this.lastInputUpdateCount = updateCount;
            this.mouseIn = true;
            this.lastMouseX = x;
            this.lastMouseY = y;
            if (((!((this.overWidget == null))) && (!((this.overWidget == this.lastDownWidget)))))
            {
                tmpWidget = this.overWidget;
                this.overWidget = null;
                this.doMouseLeave(tmpWidget);
            };
            if (this.lastDownWidget != null)
            {
                absPos = this.lastDownWidget.getAbsPos();
                localPos = new Point((int)(x - absPos.x), (int)(y - absPos.y));
                this.lastDownWidget.onMouseDrag(localPos.x, localPos.y);
                tmpWidget = this.getWidgetAt(x, y, new Point());
                if ((((tmpWidget == this.lastDownWidget)) && (!((tmpWidget == null)))))
                {
                    if (this.overWidget == null)
                    {
                        this.overWidget = this.lastDownWidget;
                        this.doMouseEnter(this.overWidget);
                    };
                }
                else
                {
                    if (this.overWidget != null)
                    {
                        tmpWidget = this.overWidget;
                        this.overWidget = null;
                        this.doMouseLeave(tmpWidget);
                    };
                };
            };
        }
        public void  lostFocus (){
            int i =0;
            if (this.hasFocus)
            {
                this.downButtons = 0;
                i = 0;
                while (i < this.keyDown.length())
                {
                	if(((Boolean)this.keyDown.elementAt(i)).booleanValue())
                    {
                        onKeyUp(i);
                    };
                    this.hasFocus = false;
                    if (this.focusWidget != null)
                    {
                        this.focusWidget.lostFocus();
                    };
                    i++;
                };
            };
        }

        public CWidget overWidget ;

        public void  addBaseModal (CWidget widget ,CFlagsMod flagsMod ){
            CPreModalInfo info =new CPreModalInfo ();
            info.baseModalWidget = widget;
            info.prevBaseModalWidget = this.baseModalWidget;
            info.prevFocusWidget = this.focusWidget;
            info.prevBelowModalFlagsMod = this.belowModalFlagsMod;
            this.preModalInfoList.push(info);
            this.setBaseModal(widget, flagsMod);
        }

        public CWidget lastDownWidget ;

        public void  doMouseDown (int x ,int y ){
            this.lastInputUpdateCount = updateCount;
            this.downButtons = 1;

            Point localPos =new Point ();
            CWidget widget =this.getWidgetAt(x ,y ,localPos );
            if (this.lastDownWidget != null)
            {
                widget = this.lastDownWidget;
            };
            this.lastDownWidget = widget;
            System.out.println("CWidgetManager.doMouseDown."+widget+":");
            if (widget != null)
            {
                if (widget.wantsFocus)
                {
                    this.setFocus(widget);
                };
                widget.isDown = true;
                widget.onMouseDown(localPos.x, localPos.y);
            };
        }

        public CFlagsMod defaultBelowModalFlagsMod ;
        public CWidget baseModalWidget ;

        public void  gotFocus (){
            if (!this.hasFocus)
            {
                this.hasFocus = true;
                if (this.focusWidget != null)
                {
                    this.focusWidget.gotFocus();
                };
            };
        }
        public void  showFinger (boolean on ,CWidget target ){
            if ((((this.overWidget == target)) || ((this.overWidget == null))))
            {
                this.mShowFinger = on;
            };
        }
        public void  removeBaseModal (CWidget widget ){
            CPreModalInfo info ;
            boolean done ;
            if (this.preModalInfoList.length() == 0)
            {
                //throw (new Error("Empty modal list."));
            };
            boolean first =true ;
            while (this.preModalInfoList.length() > 0)
            {
            	info=(CPreModalInfo)this.preModalInfoList.elementAt((this.preModalInfoList.length()-1));
                if (((first) && (!((info.baseModalWidget == widget)))))
                {
                    return;
                };
                done = ((!((info.prevBaseModalWidget == null))) || ((this.preModalInfoList.length() == 1)));
                this.setBaseModal(info.prevBaseModalWidget, info.prevBelowModalFlagsMod);
                if (this.focusWidget == null)
                {
                    this.focusWidget = info.prevFocusWidget;
                    if (this.focusWidget != null)
                    {
                        this.focusWidget.gotFocus();
                    };
                };
                this.preModalInfoList.pop();
                if (done)
                {
                    break;
                };
                first = false;
            };
        }
        public void  doKeyChar (int charCode ){
            this.lastInputUpdateCount = updateCount;
            if (this.focusWidget != null)
            {
                this.focusWidget.onKeyChar(charCode);
            };
        }

        public CWidget focusWidget ;

         public void  disableWidget (CWidget widget ){
            CWidget tmpWidget ;
            if (this.overWidget == widget)
            {
                tmpWidget = this.overWidget;
                this.overWidget = null;
                this.doMouseLeave(tmpWidget);
            };
            if (this.lastDownWidget == widget)
            {
                tmpWidget = this.lastDownWidget;
                this.lastDownWidget = null;
                this.doMouseUps(tmpWidget, this.downButtons);
                this.downButtons = 0;
            };
            if (this.focusWidget == widget)
            {
                tmpWidget = this.focusWidget;
                this.focusWidget = null;
                tmpWidget.lostFocus();
            };
            if (this.baseModalWidget == widget)
            {
                this.baseModalWidget = null;
            };
        }
        public void  removePopupCommandWidget (){
            CWidget tmpWidget ;
            if (this.popupCommandWidget != null)
            {
                tmpWidget = this.popupCommandWidget;
                this.popupCommandWidget = null;
                removeWidget(tmpWidget);
            };
        }
        public void  doKeyDown (int keyCode ){
            this.lastInputUpdateCount = updateCount;
            this.keyDown.add(keyCode,true);
            if (this.focusWidget != null)
            {
                this.focusWidget.onKeyDown(keyCode);
            };
        }
        public void  drawScreen (Graphics2D g ){
            this.initModalFlags(this.modalFlags);
            boolean drewStuff ;
            int dirtyCount =0;
            boolean hasTransients ;
            boolean hasDirtyTransients ;
            int i =0;
            CWidget w ;
            int aNumWidgets =widgets.length() ;
            i = 0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)widgets.elementAt(i);
                if (w.dirty)
                {
                    dirtyCount++;
                };
                i++;
            };
            this.minDeferredOverlayPriority = NumberX.MAX_VALUE;
            this.deferredOverlayWidgets = new Array();
            if (dirtyCount > 0)
            {
                g.pushState();
                aNumWidgets = widgets.length();
                i = 0;
                while (i < aNumWidgets)
                {
                	w=(CWidget)widgets.elementAt(i);
                    if (w == widgetManager.baseModalWidget)
                    {
                        this.modalFlags.isOver = true;
                    };
                    if (((w.dirty) && (w.visible)))
                    {
                        g.pushState();
                        g.translate(w.x, w.y);

                        w.drawAll(this.modalFlags, g);
                        g.popState();
                        dirtyCount++;
                        drewStuff = true;
                    };
                    i++;
                };
                g.popState();
            };
            this.flushDeferredOverlayWidgets(g, NumberX.MAX_VALUE);
        }

        public int lastInputUpdateCount =0;
        public Array preModalInfoList ;

        public void  setMousePosition (int x ,int y ){
            CWidget tmpWidget ;
            int lastX =this.lastMouseX ;
            int lastY =this.lastMouseY ;
            Point localPos =new Point(0,0);
            CWidget widget =this.getWidgetAt(x ,y ,localPos );


            if (widget != this.overWidget)
            {
                tmpWidget = this.overWidget;
                this.overWidget = null;
                if (tmpWidget != null)
                {
                    this.doMouseLeave(tmpWidget);
                };
                this.overWidget = widget;
                if (widget != null)
                {
                    this.doMouseEnter(widget);
                    widget.onMouseMove(localPos.x, localPos.y);
                };
            }
            else
            {
				if (((!((lastX == x))) || (!((lastY == y)))))
                {
                    if (widget != null)
                    {
                        widget.onMouseMove(localPos.x, localPos.y);
                    };
                };
            };
//            System.out.println("CWidgetManager.x " + x+":"+y);
			this.lastMouseX = x;
			this.lastMouseY = y;
        }

        private CModalFlags modalFlags ;

        public void  remapMouse (int x ,int y ){
        }

        public boolean hasFocus =true ;

        public void  doMouseMove (int x ,int y ){
            this.lastInputUpdateCount = updateCount;
            if (this.downButtons != 0)
            {
                this.doMouseDrag(x, y);
            };
            this.mouseIn = true;
			if(this.focusWidget != null && this.focusWidget.isDown) {
				this.focusWidget.onMouseMove(x, y);
			} else {
            	this.setMousePosition(x, y);
			}
        }
        public CWidget  getAnyWidgetAt (int x ,int y ,Point localPos ){
            return (getWidgetAtHelper(x, y, this.getWidgetFlags(), localPos));
        }
        public int  getWidgetFlags (){
            return (((this.hasFocus) ? this.widgetFlags : CFlagsMod.getModFlags(this.widgetFlags, this.lostFocusFlagsMod)));
        }
        public void  doMouseLeave (CWidget widget ){
            widget.isOver = false;
            widget.onMouseLeave();
            if (widget.doFinger)
            {
                widget.showFinger(false);
            };
        }

        public int downButtons =0;

        public  CWidgetManager (AppBase app ){
            super();
            this.modalFlags = new CModalFlags();
            this.keyDown = new Array();
            this.deferredOverlayWidgets = new Array();
            this.preModalInfoList = new Array();
            this.lostFocusFlagsMod = new CFlagsMod();
            this.belowModalFlagsMod = new CFlagsMod();
            this.defaultBelowModalFlagsMod = new CFlagsMod();
            this.app = app;
            widgetManager = this;
            this.widgetFlags = ((((WIDGETFLAGS_UPDATE | WIDGETFLAGS_DRAW) | WIDGETFLAGS_CLIP) | WIDGETFLAGS_ALLOW_MOUSE) | WIDGETFLAGS_ALLOW_FOCUS);
            int i =0;
            while (i < 0xFF)
            {
            	this.keyDown.add(i,false);
                i++;
            };
        }
    }


