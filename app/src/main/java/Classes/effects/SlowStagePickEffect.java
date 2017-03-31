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
import Engine.Events.*;
import com.greensock.*;
//import flash.utils.*;

    public class SlowStagePickEffect extends StagePickEffect
    {
        private boolean m_isLoading =false ;
        private boolean m_isItemImagesLoaded =false ;
        private Object m_itemImageLoadedHash ;

        public  SlowStagePickEffect (MapResource param1 )
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
            else if (!this.m_isItemImagesLoaded || !isMapResourceLoaded)
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
            this.m_itemImageLoadedHash = null;
            return;
        }//end

        private void  loadAnimationEffect ()
        {
            XML _loc_3 =null ;
            ItemImage _loc_4 =null ;
            String _loc_5 =null ;
            _loc_1 =Global.gameSettings().getEffectByName(EFFECT_NAME );
            _loc_2 = _loc_1.image ;
            this.m_itemImageLoadedHash = {};
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_5 = _loc_3.attribute("name");
                this.m_itemImageLoadedHash.put(_loc_5,  null);
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = new ItemImage(_loc_3);
                _loc_4.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                _loc_4.load();
            }
            this.m_isLoading = true;
            return;
        }//end

        private void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            if (!this.m_isItemImagesLoaded && this.m_itemImageLoadedHash)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                _loc_4 = _loc_2.name;
                this.m_itemImageLoadedHash.put(_loc_4,  _loc_3);
                _loc_5 = true;
                for(int i0 = 0; i0 < this.m_itemImageLoadedHash.size(); i0++)
                {
                		_loc_3 = this.m_itemImageLoadedHash.get(i0);

                    if (!_loc_3)
                    {
                        _loc_5 = false;
                        break;
                    }
                }
                if (_loc_5)
                {
                    this.m_isItemImagesLoaded = true;
                    this.reattach();
                    dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
                }
            }
            return;
        }//end

         public void  reattach ()
        {
            if (!this.m_isItemImagesLoaded || !isMapResourceLoaded || !m_mapResource.canShowStagePick())
            {
                return;
            }
            super.reattach();
            return;
        }//end

         protected Object  getImage (String param1 )
        {
            _loc_2 = this.m_itemImageLoadedHash.get(param1) ;
            _loc_3 = _loc_2.image ;
            return _loc_3;
        }//end

    }



