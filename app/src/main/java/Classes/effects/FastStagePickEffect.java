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
import Classes.util.*;
import Engine.Classes.*;
import Engine.Events.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class FastStagePickEffect extends StagePickEffect
    {
        private boolean m_isLoading =false ;
        private static boolean s_isLoading =false ;
        private static boolean s_isItemImagesLoaded =false ;
        private static Object s_itemImagesLoadedHash ;
        private static EventDispatcher s_dispatcher =new EventDispatcher ();
        private static FastStagePickEffect s_boss =null ;

        public  FastStagePickEffect (MapResource param1 )
        {
            super(param1);
            return;
        }//end

         public void  setFadeTime (int param1 )
        {
            seconds = param1;
            if (int(m_fadeTime) == -1 && int(seconds) > 0)
            {
                m_fadeTime = seconds;
                TimerUtil .callLater (void  ()
            {
                if (m_animTimeline)
                {
                    m_animTimeline.kill();
                }
                m_animTimeline = new TimelineLite();
                m_animTimeline.appendMultiple(.get(new TweenLite(this, 1, {alpha:0}), new TweenLite(this, 0.1, {onStart:fadeCallback})), 0, TweenAlign.SEQUENCE);
                return;
            }//end
            , m_fadeTime * 1000);
            }
            else if (!s_isItemImagesLoaded || !isMapResourceLoaded)
            {
                queueFade(seconds);
            }
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (m_isComplete)
            {
                return false;
            }
            _loc_2 = getTimer();
            if (m_startTime == -1)
            {
                m_startTime = _loc_2;
            }
            if (_loc_2 < m_startTime + PICK_ANIMATE_DELAY)
            {
                return true;
            }
            if (isMapResourceLoaded && !this.m_isLoading)
            {
                this.loadAnimationEffect();
                if (s_isItemImagesLoaded)
                {
                    s_dispatcher.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
                }
            }
            else if (m_guitarPickAnimation)
            {
                if (m_guitarPickAnimation.currentFrame >= m_guitarPickAnimation.numFrames - 2)
                {
                    m_guitarPickAnimation.stop();
                    m_guitarPickAnimation.currentFrame = 0;
                }
                else
                {
                    m_guitarPickAnimation.onUpdate(1 / 10);
                }
            }
            return true;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            return;
        }//end

        private void  loadAnimationEffect ()
        {
            XML _loc_1 =null ;
            XMLList _loc_2 =null ;
            XML _loc_3 =null ;
            ItemImage _loc_4 =null ;
            String _loc_5 =null ;
            if (!s_isLoading)
            {
                _loc_1 = Global.gameSettings().getEffectByName(EFFECT_NAME);
                _loc_2 = _loc_1.image;
                s_itemImagesLoadedHash = {};
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_5 = _loc_3.attribute("name");
                    s_itemImagesLoadedHash.put(_loc_5,  null);
                }
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_4 = new ItemImage(_loc_3);
                    _loc_4.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                    _loc_4.load();
                }
                s_boss = this;
                s_isLoading = true;
            }
            s_dispatcher.addEventListener(LoaderEvent.LOADED, this.onItemImagesLoaded);
            this.m_isLoading = true;
            return;
        }//end

        private void  onItemImagesLoaded (LoaderEvent event )
        {
            s_dispatcher.removeEventListener(LoaderEvent.LOADED, this.onItemImagesLoaded);
            this.reattach();
            dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        private void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            if (!s_isItemImagesLoaded && s_itemImagesLoadedHash)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                _loc_4 = _loc_2.name;
                s_itemImagesLoadedHash.put(_loc_4,  _loc_3);
                _loc_5 = true;
                for(int i0 = 0; i0 < s_itemImagesLoadedHash.size(); i0++)
                {
                		_loc_3 = s_itemImagesLoadedHash.get(i0);

                    if (!_loc_3)
                    {
                        _loc_5 = false;
                        break;
                    }
                }
                if (_loc_5)
                {
                    s_isItemImagesLoaded = true;
                    s_dispatcher.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
                }
            }
            return;
        }//end

         public void  reattach ()
        {
            if (!s_isItemImagesLoaded || !isMapResourceLoaded)
            {
                return;
            }
            super.reattach();
            return;
        }//end

         protected Object  getImage (String param1 )
        {
            _loc_2 = s_itemImagesLoadedHash.get(param1);
            _loc_3 = _loc_2.image ;
            return cloneImage(_loc_3);
        }//end

        private static Object cloneImage (Object param1 )
        {
            Bitmap _loc_2 =null ;
            Bitmap _loc_3 =null ;
            if (param1 instanceof AnimatedBitmap)
            {
                return param1.clone();
            }
            if (param1 instanceof Bitmap)
            {
                _loc_2 =(Bitmap) param1;
                _loc_3 = new Bitmap(_loc_2.bitmapData);
                _loc_3.smoothing = _loc_2.smoothing;
                return _loc_3;
            }
            return null;
        }//end

    }



