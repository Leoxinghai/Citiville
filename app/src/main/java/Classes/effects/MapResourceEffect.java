package Classes.effects;

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
import com.zynga.skelly.render.*;
//import flash.display.*;
import com.zynga.skelly.animation.*;

    public class MapResourceEffect extends Sprite implements IAnimated
    {
        protected MapResource m_mapResource ;
        protected double m_fps ;
        protected boolean m_isCompleted =false ;
        protected EffectType m_effectType ;

        public  MapResourceEffect (MapResource param1 ,double param2 =12)
        {
            this.m_mapResource = param1;
            this.m_fps = param2;
            RenderManager.addAnimationByFPS(this.m_fps, this);
            return;
        }//end

        public void  setEffectType (EffectType param1 )
        {
            this.m_effectType = param1;
            return;
        }//end

        public EffectType  effectType ()
        {
            return this.m_effectType;
        }//end

        public boolean  isCompleted ()
        {
            return this.m_isCompleted;
        }//end

        public boolean  isMapResourceLoaded ()
        {
            _loc_1 = this.m_mapResource && this.m_mapResource.content && this.m_mapResource.getItemImage() && !this.m_mapResource.isCurrentImageLoading();
            return _loc_1;
        }//end

        public boolean  animate (int param1 )
        {
            return false;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  reattach ()
        {
            return;
        }//end

        public boolean  allowReattachOnReplaceContent ()
        {
            return false;
        }//end

    }



