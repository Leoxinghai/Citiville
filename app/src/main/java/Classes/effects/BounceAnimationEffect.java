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
import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Helpers.*;

import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.geom.*;

    public class BounceAnimationEffect extends AnimationEffect
    {
        private TimelineLite m_animTimeline ;
        private DisplayObject m_spark ;
        private boolean m_isFirstReattach ;
        private ProxyValues m_animProxyValues ;
        private boolean m_useHotspot ;

        public  BounceAnimationEffect (MapResource param1 ,String param2 ,boolean param3 =false )
        {
            super(param1, param2, param3, true);
            this.m_spark = new EmbeddedArt.spark();
            this.m_isFirstReattach = true;
            this.m_animProxyValues = new ProxyValues();
            return;
        }//end

        public void  useHotspot (boolean param1 )
        {
            this.m_useHotspot = param1;
            return;
        }//end

        private void  beginBounceAnimation ()
        {
            Point _loc_1 =null ;
            Point _loc_2 =null ;
            Point _loc_3 =null ;
            Point _loc_4 =null ;
            Point _loc_5 =null ;
            Vector _loc_6.<Vector3 >=null ;
            Vector3 _loc_7 =null ;
            Vector2 _loc_8 =null ;
            Point _loc_9 =null ;
            Point _loc_10 =null ;
            if (m_effectImage)
            {
                _loc_1 = new Point(m_mapResource.content.width >> 1, 0);
                if (this.m_useHotspot)
                {
                    _loc_6 = m_mapResource.getHotspots();
                    _loc_7 = m_mapResource.getHotspots().get((_loc_6.length - 1));
                    _loc_8 = IsoMath.tilePosToPixelPos(_loc_7.x, _loc_7.y);
                    _loc_9 = IsoMath.viewportToStage(_loc_8);
                    _loc_10 = m_mapResource.content.globalToLocal(_loc_9);
                    _loc_1.x = _loc_10.x;
                }
                _loc_2 = new Point(_loc_1.x, _loc_1.y - 20);
                _loc_3 = new Point(_loc_1.x, _loc_1.y - 60);
                _loc_4 = new Point(-(m_effectImage.width >> 1), -(m_effectImage.height >> 1));
                _loc_5 = new Point(-(this.m_spark.width >> 1), -(this.m_spark.height >> 1));
                m_effectImage.x = _loc_1.x + _loc_4.x;
                m_effectImage.y = _loc_1.y + _loc_4.y;
                this.m_animProxyValues.x = m_effectImage.x;
                this.m_animProxyValues.y = m_effectImage.y;
                this.m_animProxyValues.scaleX = m_effectImage.scaleX;
                this.m_animProxyValues.scaleY = m_effectImage.scaleY;
                this.m_animProxyValues.alpha = m_effectImage.alpha;
                this.m_spark.x = _loc_2.x;
                this.m_spark.y = _loc_2.y;
                int _loc_11 =0;
                this.m_spark.scaleY = 0;
                this.m_spark.scaleX = _loc_11;
                this.m_animTimeline = new TimelineLite({onComplete:this.endItAll, onUpdate:this.proxyUpdate});
                this.m_animTimeline.appendMultiple(.get(new TweenLite(this.m_animProxyValues, 0.3, {y:_loc_3.y + _loc_4.y, ease:Quad.easeOut}), new TweenLite(this.m_animProxyValues, 0.3, {y:_loc_2.y + _loc_4.y, ease:Quad.easeIn}), new TweenLite(this.m_animProxyValues, 0.5, {scaleX:2, scaleY:2, x:_loc_2.x - m_effectImage.width, y:_loc_2.y - m_effectImage.height, alpha:0, delay:0.5}), new TweenLite(this.m_spark, 0.001, {scaleX:1, scaleY:1, x:_loc_2.x + _loc_5.x, y:_loc_2.y + _loc_5.y, onStart:this.onSparkInit}), new TweenLite(this.m_spark, 0.2, {scaleX:0, scaleY:0, x:_loc_2.x, y:_loc_2.y})), 0, TweenAlign.SEQUENCE);
            }
            return;
        }//end

         public void  updateAnimatedBitmap (double param1 )
        {
            super.updateAnimatedBitmap(param1);
            return;
        }//end

        private void  proxyUpdate ()
        {
            if (m_effectImage)
            {
                m_effectImage.x = this.m_animProxyValues.x;
                m_effectImage.y = this.m_animProxyValues.y;
                m_effectImage.scaleX = this.m_animProxyValues.scaleX;
                m_effectImage.scaleY = this.m_animProxyValues.scaleY;
                m_effectImage.alpha = this.m_animProxyValues.alpha;
            }
            return;
        }//end

        private void  onSparkInit ()
        {
            getAttachParent().addChild(this.m_spark);
            return;
        }//end

        private void  endItAll ()
        {
            AnimatedBitmap _loc_1 =null ;
            if (this.m_spark && this.m_spark.parent)
            {
                this.m_spark.parent.removeChild(this.m_spark);
            }
            if (m_effectImage)
            {
                _loc_1 =(AnimatedBitmap) m_effectImage;
                _loc_1.stop();
                m_mapResource.removeAnimatedEffect(effectType);
                m_isActive = false;
            }
            return;
        }//end

         public void  cleanUp ()
        {
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
            }
            if (this.m_spark && this.m_spark.parent)
            {
                this.m_spark.parent.removeChild(this.m_spark);
                this.m_spark = null;
            }
            super.cleanUp();
            return;
        }//end

         protected void  onItemImageLoaded (LoaderEvent event )
        {
            super.onItemImageLoaded(event);
            this.beginBounceAnimation();
            return;
        }//end

    }
class ProxyValues
    public double x ;
    public double y ;
    public double scaleX ;
    public double scaleY ;
    public double alpha ;

     ProxyValues ()
    {
        return;
    }//end





