package com.xiyu.flash.games.pvz.renderables;
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

import com.xiyu.flash.framework.render.Renderable;
import com.xiyu.flash.games.pvz.logic.GridItem;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class GridItemRenderable implements Renderable {

        public boolean  getIsDisposable (){
            return (this.mGridItem.mDead);
        }

        private GridItem mGridItem ;

        public void  draw (Graphics2D g ){
            this.mGridItem.Draw(g);
        }
        public int  getDepth (){
            return (this.mDepth);
        }
        public String  toString (){
            return (("GridItem@" + this.mDepth));
        }
        public void  update (){
            this.mGridItem.Update();
        }
        public boolean  getIsVisible (){
            return (true);
        }

        private int mDepth ;

        public  GridItemRenderable (GridItem gridItem ,int depth){
            this.mGridItem = gridItem;
            this.mDepth = depth;
        }
    }


