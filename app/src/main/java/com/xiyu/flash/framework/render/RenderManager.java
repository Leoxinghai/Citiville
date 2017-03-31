package com.xiyu.flash.framework.render;
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

import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.renderables.GridItemRenderable;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.games.pvz.renderables.ProjectileRenderable;
import com.xinghai.debug.Debug;

    public class RenderManager {

        public void  add (Renderable renderable ){
            Renderable item ;
            int len =this.mItems.length();
            int depth =renderable.getDepth ();
            int i =0;
            while (i < len)
            {
            	item=(Renderable)this.mItems.elementAt(i);
                if (item.getDepth() > depth)
                {
                    this.mItems.splice(i, 0, renderable);
                    return;
                };
                i++;
            };
            this.mItems.push(renderable);
        }
        private int  depthCompare (Renderable a ,Renderable b ){
            return ((a.getDepth() - b.getDepth()));
        }
        public void  draw (Graphics2D g ){
            Renderable anItem ;
            int aNumItems =this.mItems.length();
            int i =0;
            while (i < aNumItems)
            {
            	anItem=(Renderable)this.mItems.elementAt(i);
                if (anItem.getIsDisposable())
                {
                }
                else
                {
                    if (!anItem.getIsVisible())
                    {
                    }
                    else
                    {
                        if(anItem instanceof GridItemRenderable ) {

						} else {
						}
						anItem.draw(g);
                    };
                };
                i++;
            };
        }
        public void  update (){
            Renderable anItem ;
            int aNumItems =this.mItems.length();
            int i =0;
			if(aNumItems>1 ){//&& this.mItems.get(0) instanceof ProjectileRenderable) {
			}
            while (i < aNumItems)
            {
            	anItem=(Renderable)this.mItems.elementAt(i);
                if (anItem.getIsDisposable())
                {
                }
                else
                {
                    anItem.update();
                };
                i++;
            };
            this.mItems = this.mItems.filter(new AliveFilter());
        }

        private Array mItems ;

        class AliveFilter implements Filter {
        	public boolean doFilt(Object item) {
                Renderable aRenderable =(Renderable)item;
                boolean result =!(aRenderable.getIsDisposable());
                return (result);
        	}
        }
        private boolean  isAliveFilter (Object item ,int index ,Array array ){
            Renderable aRenderable =(Renderable)item;
            boolean result =!(aRenderable.getIsDisposable());
            return (result);
        }

        public  RenderManager (){
            this.mItems = new Array();
        }
    }


