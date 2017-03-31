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
import com.xiyu.flash.framework.graphics.Graphics2D;
//import flash.geom.Rectangle;
import com.xinghai.debug.Debug;
import com.xiyu.flash.games.pvz.logic.Board;


    public class CWidgetContainer {

        public static int depthCount =0;

        public double y =0;

        public void  onMouseEnter (){
        }
        public void  markDirty (CWidgetContainer container){
            boolean found ;
            int aNumWidgets ;
            int i =0;
            CWidget w ;
            if (container == null)
            {
                if (this.parent != null)
                {
                    this.parent.markDirty(this);
                }
                else
                {
                    this.dirty = true;
                };
                return;
            };
            if (container.dirty == true)
            {
                return;
            };
            this.markDirty(null);
            container.dirty = true;
            if (this.parent != null)
            {
                return;
            };
            if (container.hasAlpha)
            {
                this.markDirtyFull(container);
            }
            else
            {
                found = false;
                aNumWidgets = this.widgets.length();
                i = 0;
                while (i < aNumWidgets)
                {
                	w=(CWidget)this.widgets.elementAt(i);
                    if (w == container)
                    {
                        found = true;
                    }
                    else
                    {
                        if (found)
                        {
                            if ((((w.visible == true)) && (w.intersects(container))))
                            {
                                this.markDirty(w);
                            };
                        };
                    };
                    i++;
                };
            };
        }
        public void  sysColorChangesAll (){
            CWidget w ;
            this.sysColorChanged();
            depthCount = 0;
            if (this.widgets.length() > 0)
            {
                depthCount++;
            };
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                w.sysColorChangesAll();
                i++;
            };
        }
        public void  putInfront (CWidget widget ,CWidget reference ){
            CWidget w ;
            int index =this.widgets.indexOf(widget );
            if (index < 0)
            {
                return;
            };
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (w == reference)
                {
                    this.widgets = this.widgets.splice(i, 0, widget);
                    return;
                };
                i++;
            };
        }
        public void  setFocus (CWidget widget ){
        }
        public void  updateAll (CModalFlags flags ){
            CWidget w ;
            if ((flags.getFlags() & CWidgetManager.WIDGETFLAGS_MARK_DIRTY) != 0)
            {
                this.markDirty(null);
            };
            if (this.widgetManager == null)
            {
                return;
            };
            if ((flags.getFlags() & CWidgetManager.WIDGETFLAGS_UPDATE)!=0)
            {
                if (this.lastWMUpdateCount != this.widgetManager.updateCount)
                {
                    this.lastWMUpdateCount = this.widgetManager.updateCount;
                    this.update();
                };
            };
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (w == this.widgetManager.baseModalWidget)
                {
                    flags.isOver = true;
                };
                w.updateAll(flags);
                i++;
            };
        }
        public void  addedToManager (CWidgetManager manager ){
            CWidget w ;
            int aNumWidgets =this.widgets.length() ;
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                w.widgetManager = manager;
                w.addedToManager(manager);
                this.markDirty(null);
                i++;
            };
        }
        public CWidget  getWidgetAtHelper (int x ,int y ,int flags ,Point local ){
            CWidget widget ;
            CWidget child ;
            int aNumWidgets =this.widgets.length();
            int i =(aNumWidgets -1);
            while (i >= 0)
            {
            	widget=(CWidget)this.widgets.elementAt(i);
            	if(widget instanceof Board) {
                  System.out.println("Board."+widget.x+":"+widget.y+":"+widget.width+":"+widget.height+":"+x+":"+y);            		
            	}
                if (!widget.visible)
                {
                }
                else
                {
                    child = widget.getWidgetAtHelper(x, y, flags, local);
//                    System.out.println("getWidgetAtHelper."+widget+":"+child+":"+widget.x+":"+widget.y+":"+widget.width+":"+widget.height+"::"+x+":"+y);
                    if (child != null)
                    {
                        return (child);
                    };
                    if (widget.contains(x, y))
                    {
                        local.x = (int)(x - widget.x);
                        local.y = (int)(y - widget.y);
                        System.out.println("getWidgetAtHelper." + widget);
                        return (widget);
                    };
                };
                i--;
            };
            return (null);
        }
        public void  markAllDirty (){
            CWidget w ;
            this.markDirty(null);
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                w.dirty = true;
                w.markAllDirty();
                i++;
            };
        }
        public void  removedFromManager (CWidgetManager manager ){
            CWidget w ;
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                manager.disableWidget(w);
                w.removedFromManager(manager);
                w.widgetManager = null;
                i++;
            };
            if (manager.popupCommandWidget == this)
            {
                manager.popupCommandWidget = null;
            };
        }

        public int height =0;

        public void  putBehind (CWidget widget ,CWidget reference ){
            CWidget w ;
            int index =this.widgets.indexOf(widget );
            if (index < 0)
            {
                return;
            };
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (w == reference)
                {
                    this.widgets = this.widgets.splice((i + 1), 0, widget);
                    return;
                };
                i++;
            };
        }

        public CWidgetContainer parent =null ;
        public int priority =0;

        public boolean  isBelow (CWidget a ,CWidget b ){
            return (this.isBelowHelper(a, b));
        }

        public Array widgets ;

        public boolean  intersects (CWidgetContainer widget ){
            return (this.getRect().intersects(widget.getRect()));
        }
        public boolean  isBelowHelper (CWidget a ,CWidget b ){
            CWidget w ;
            boolean result ;
            int aNumWidgets =this.widgets.length() ;
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (w == a)
                {
                    return (true);
                };
                if (w == b)
                {
                    return (false);
                };
                result = w.isBelowHelper(a, b);
                if (result)
                {
                    return (true);
                };
                i++;
            };
            return (false);
        }

        public int lastWMUpdateCount =0;

        public void  bringToBack (CWidget widget ){
            int index =this.widgets.indexOf(widget );
            if (index < 0)
            {
                return;
            };
            this.widgets = this.widgets.splice(index, 1);
            this.widgets.push(widget);
            widget.orderInManagerChanged();
        }
        public void  drawAll (CModalFlags flags ,Graphics2D g ){
            CWidget w ;
            if(this.widgetManager == null)
            	return;
            if (this.priority > this.widgetManager.minDeferredOverlayPriority)
            {
                this.widgetManager.flushDeferredOverlayWidgets(g, this.priority);
            };
            if (((this.clip) && ((flags.getFlags() & CWidgetManager.WIDGETFLAGS_CLIP)!=0)))
            {
            };
            if (this.widgets.length() == 0)
            {
                if ((flags.getFlags() & CWidgetManager.WIDGETFLAGS_DRAW)!=0)
                {
                    this.draw(g);
                };
                return;
            };
            if ((flags.getFlags() & CWidgetManager.WIDGETFLAGS_DRAW)!=0)
            {
                g.pushState();
                this.draw(g);
                g.popState();
            };
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (w.visible == true)
                {
                    g.pushState();
                    g.translate(w.x, w.y);
                    w.drawAll(flags, g);
                    g.popState();
                    w.dirty = false;
                };
                i++;
            };
        }
        public void  sysColorChanged (){
        }
        public void  onMouseUp (int x ,int y ){
        }
        public void  onKeyDown (int keyCode ){
        }
        public void  markDirtyFull (CWidgetContainer widget){
            Rectangle rect ;
            if (widget == null)
            {
                if (this.parent != null)
                {
                    this.parent.markDirtyFull(this);
                }
                else
                {
                    this.dirty = true;
                };
                return;
            };
            this.markDirtyFull(null);
            widget.dirty = true;
            if (this.parent != null)
            {
                return;
            };
            int index =this.widgets.indexOf(widget );
            if (index == -1)
            {
                return;
            };
            int i =index ;
            int j =0;
            CWidget w ;
            i = 0;
            while (i >= 0)
            {
                j = i;
                while (j >= 0)
                {
                	w=(CWidget)this.widgets.elementAt(j);
                    if (w.visible == true)
                    {
                        if (((!(w.hasTransparencies)) && (!(w.hasAlpha))))
                        {
                            rect = new Rectangle(w.x, w.y, w.width, w.height).intersection(new Rectangle(0, 0, this.width, this.height));
                            if (((w.contains(rect.x, rect.y)) && (w.contains(((rect.x + rect.width()) - 1), ((rect.y + rect.height()) - 1)))))
                            {
                                w.markDirty(null);
                                break;
                            };
                        };
                    };
                    j = (j + -1);
                };
                i = j;
                i = (i + -1);
            };
            i = index;
            int aNumWidgets =this.widgets.length();
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (((w.visible) && (w.intersects(widget))))
                {
                    w.markDirty(null);
                };
                i++;
            };
        }
        public void  draw (Graphics2D g ){
        }
        public void  addWidget (CWidget widget ){
            if (widget.parent == this)
            {
                return;
            };
            if (widget.parent != null)
            {
                widget.parent.removeWidget(widget);
            };
            this.widgets.push(widget);
            widget.widgetManager = this.widgetManager;
            widget.parent = this;
            if (this.widgetManager != null)
            {
                widget.addedToManager(this.widgetManager);
                widget.markDirtyFull(null);
                this.widgetManager.rehupMouse();
            };
            this.markDirty(null);
        }

        public int width =0;
        public CWidgetManager widgetManager =null ;

        public void  onKeyUp (int keyCode ){
        }
        public void  disableWidget (CWidget widget ){
        }

        public int updateCount =0;
        public boolean clip =true ;

        public void  onMouseLeave (){
        }

        public boolean dirty =false ;

        public void  onMouseDown (int x ,int y ){
        }
        public void  update (){
            this.updateCount++;
        }
        public Rectangle  getRect (){
            return (new Rectangle((int)this.x, (int)this.y, (int)this.width, (int)this.height));
        }
        public void  onMouseDrag (int x ,int y ){
        }

        public boolean hasAlpha =false ;

        public void  insertWidgetHelper (CWidget widget ){
            CWidget w ;
            int aNumWidgets =this.widgets.length();
            int i =0;
            while (i < aNumWidgets)
            {
            	w=(CWidget)this.widgets.elementAt(i);
                if (widget.zOrder < w.zOrder)
                {
                    this.widgets = this.widgets.splice(i, 0, widget);
                    return;
                };
                i++;
            };
        }
        public void  removeWidget (CWidget widget ){
            if (widget.parent != this)
            {
                return;
            };
            widget.widgetRemovedHelper();
            widget.parent = null;
            int index =this.widgets.indexOf(widget );
            this.widgets.splice(index, 1);
        }
        public void  removeAllWidgets (boolean recursive){
            CWidget w ;
            while (this.widgets.length() > 0)
            {
                w = (CWidget)this.widgets.shift();
                if (recursive)
                {
                    w.removeAllWidgets(recursive);
                };
            };
        }

        public int zOrder =0;

        public void  onMouseWheel (int delta ){
        }
        public void  onKeyChar (int charCode ){
        }
        public void  onMouseMove (int x ,int y ){
        Debug.debug2("CWidgetContainer.onMouseMove");
        }

        public double x =0;

        public void  bringToFront (CWidget widget ){
            int index =this.widgets.indexOf(widget );
            if (index < 0)
            {
                return;
            };
            this.widgets = this.widgets.splice(index, 1);
            this.widgets.unshift(widget);
            widget.orderInManagerChanged();
        }
        public boolean  hasWidget (CWidget widget ){
            return ((widget.parent == this));
        }
        public Point  getAbsPos (){
            Point p =new Point((int)this.x ,(int)this.y );
            if (this.parent != null)
            {
            	//p.add(this.parent.getAbsPos());
            	p= this.parent.getAbsPos();
            };
            return (p);
        }

        public  CWidgetContainer (){
            super();
            this.widgets = new Array();
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
            this.parent = null;
            this.widgetManager = null;
            this.dirty = false;
            this.hasAlpha = false;
            this.clip = true;
            this.priority = 0;
            this.zOrder = 0;
        }
    }


