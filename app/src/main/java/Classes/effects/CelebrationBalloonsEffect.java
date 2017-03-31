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
import Engine.Events.*;
import Engine.Helpers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.geom.*;

    public class CelebrationBalloonsEffect extends AnimationEffect
    {
        private  double ANIM_DURATION =1;
        private  double TARGET_PADDING_Y =-20;
        private  double BALLOON_OFFSET_Y =-8;
        private Array m_balloons ;
        private boolean m_isAnimating ;
        private Object m_itemImageLoadedHash ;
        private Array m_itemImageInstances ;
        private boolean m_isItemImagesLoaded =false ;

        public  CelebrationBalloonsEffect (MapResource param1 ,String param2 ,boolean param3 =true )
        {
            this.m_isAnimating = param3;
            super(param1, param2);
            return;
        }//end

         public void  cleanUp ()
        {
            DisplayObject _loc_1 =null ;
            if (this.m_balloons)
            {
                for(int i0 = 0; i0 < this.m_balloons.size(); i0++)
                {
                		_loc_1 = this.m_balloons.get(i0);

                    this.cleanUpBalloon(_loc_1);
                }
            }
            this.m_balloons = null;
            super.cleanUp();
            return;
        }//end

         public void  reattach ()
        {
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            _loc_1 = m_mapResource.content;
            if (!this.m_isItemImagesLoaded || !_loc_1)
            {
                return;
            }
            _loc_2 = m_mapResource.getReference().getSize();
            _loc_3 = IsoRect.getIsoRectFromSize(_loc_2);
            double _loc_4 =0;
            _loc_5 = m_mapResource(-.displayObjectOffsetY)/m_mapResource.displayObject.scaleY;
            _loc_6 = getAttachParent();
            _loc_7 = _loc_5-_loc_3.height +(_loc_3.height -_loc_3.bottom.y );
            Point _loc_8 =new Point ();
            _loc_8.x = _loc_4;
            _loc_8.y = _loc_7 + this.BALLOON_OFFSET_Y;
            this.m_balloons = new Array();
            this.makeBalloon(_loc_3.left, _loc_8, _loc_6);
            this.makeBalloon(_loc_3.right, _loc_8, _loc_6);
            this.makeBalloon(_loc_3.bottom, _loc_8, _loc_6);
            _loc_9 = this.makeBalloon(_loc_3.top ,_loc_8 );
            _loc_6.addChildAt(_loc_9, _loc_1.parent.getChildIndex(_loc_1));
            if (this.m_isAnimating)
            {
                _loc_10 = -_loc_5 + this.TARGET_PADDING_Y + _loc_7;
                for(int i0 = 0; i0 < this.m_balloons.size(); i0++)
                {
                		_loc_9 = this.m_balloons.get(i0);

                    _loc_11 = MathUtil.random(3) * 0.1;
                    _loc_12 = _loc_9.y + _loc_10 + MathUtil.random(4);
                    TweenLite.to(_loc_9, this.ANIM_DURATION, {y:_loc_12, delay:_loc_11});
                    TweenLite.to(_loc_9, 0.5, {alpha:0, delay:_loc_11 + this.ANIM_DURATION, overwrite:0, onComplete:this.tweenCompleteHandler, onCompleteParams:.get(_loc_9)});
                }
            }
            m_isActive = false;
            return;
        }//end

        private DisplayObject  makeBalloon (Point param1 ,Point param2 ,DisplayObjectContainer param3 =null )
        {
            param2 = param2 || new Point();
            _loc_4 = this.getBalloonInstance ();
            this.m_balloons.put(this.m_balloons.length,  _loc_4);
            _loc_4.x = param1.x + param2.x - (_loc_4.width >> 1);
            _loc_4.y = param1.y + param2.y - _loc_4.height;
            if (param3)
            {
                param3.addChild(_loc_4);
            }
            return _loc_4;
        }//end

        private DisplayObject  getBalloonInstance ()
        {
            int _loc_1 =0;
            _loc_2 = this.m_itemImageInstances.get(_loc_1) ;
            if (this.m_itemImageInstances.length > 1)
            {
                this.m_itemImageInstances.splice(_loc_1, 1);
            }
            _loc_3 =(DisplayObject) _loc_2.image;
            return _loc_3;
        }//end

         protected void  loadAnimationEffect ()
        {
            XML _loc_2 =null ;
            XMLList _loc_3 =null ;
            XML _loc_4 =null ;
            ItemImage _loc_5 =null ;
            String _loc_6 =null ;
            _loc_1 = m_mapResource.content&& m_mapResource.getItemImage() && !m_mapResource.isCurrentImageLoading();
            if (_loc_1 && !m_itemImage)
            {
                _loc_2 = Global.gameSettings().getEffectByName(m_effectName);
                _loc_3 = _loc_2.image;
                this.m_itemImageLoadedHash = {};
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_6 = _loc_4.attribute("name");
                    this.m_itemImageLoadedHash.put(_loc_6,  false);
                }
                this.m_itemImageInstances = new Array();
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_5 = new ItemImage(_loc_4);
                    _loc_5.addEventListener(LoaderEvent.LOADED, this.onItemImageLoaded, false, 0, true);
                    _loc_5.load();
                }
            }
            return;
        }//end

         protected void  onItemImageLoaded (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            if (!this.m_isItemImagesLoaded)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                this.m_itemImageInstances.put(this.m_itemImageInstances.length,  _loc_3);
                _loc_4 = _loc_2.name;
                this.m_itemImageLoadedHash.put(_loc_4,  true);
                _loc_5 = true;
                for(int i0 = 0; i0 < this.m_itemImageLoadedHash.size(); i0++)
                {
                		_loc_6 = this.m_itemImageLoadedHash.get(i0);

                    if (!_loc_6)
                    {
                        _loc_5 = false;
                        break;
                    }
                }
                if (_loc_5)
                {
                    this.m_isItemImagesLoaded = true;
                    this.reattach();
                }
            }
            return;
        }//end

        private void  cleanUpBalloon (DisplayObject param1 )
        {
            if (param1 !=null)
            {
                TweenLite.killTweensOf(param1);
                if (param1.parent)
                {
                    param1.parent.removeChild(param1);
                }
            }
            return;
        }//end

        private void  tweenCompleteHandler (DisplayObject param1 )
        {
            int _loc_2 =0;
            this.cleanUpBalloon(param1);
            if (this.m_balloons)
            {
                _loc_2 = this.m_balloons.indexOf(param1);
                if (_loc_2 >= 0)
                {
                    this.m_balloons.splice(_loc_2, 1);
                }
            }
            if (!this.m_balloons || this.m_balloons.length <= 0)
            {
                this.cleanUp();
            }
            return;
        }//end

    }



