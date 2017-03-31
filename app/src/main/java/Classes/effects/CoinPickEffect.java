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
import Engine.Classes.*;
import Engine.Events.*;
import Engine.Helpers.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
import com.xinghai.Debug;

    public class CoinPickEffect extends MapResourceEffect
    {
        private  String EFFECT_NAME ="coinPick";
        private  String PICK_4 ="coinPick4";
        private  double BOUNCE_HEIGHT =75;
        private Array m_pickSizeOrder ;
        private boolean m_isComplete =false ;
        private boolean m_isLoading =false ;
        private boolean m_isItemImagesLoaded =false ;
        private Array m_itemImageInstances ;
        private Object m_itemImageLoadedHash ;
        private String m_currentPickName ="";
        private String m_targetPickName ="";
        private String m_coinSpinName ="coinSpin1";
        private Sprite m_pickContainer ;
        private DisplayObject m_currentPickImage ;
        private AnimatedBitmap m_currentBounceImage ;
        private TimelineLite m_animTimeline ;
        private double m_effectX ;
        private double m_effectY ;
        public static  String PICK_0 ="";
        public static  String PICK_1 ="coinPick1";
        public static  String PICK_2 ="coinPick2";
        public static  String PICK_3 ="coinPick3";
        public static  String COIN_SPIN_1 ="coinSpin1";
        public static  String COIN_SPIN_1_DOUBLE ="coinSpin1double";

        public  CoinPickEffect (MapResource param1 )
        {
            Debug.debug4("CoinPickEffect");
            this.m_pickSizeOrder = .get(PICK_1, PICK_2, PICK_3, this.PICK_4);
            super(param1);
            this.animate(1);
            return;
        }//end

         public boolean  animate (int param1 )
        {
            if (this.m_isComplete)
            {
                return false;
            }
            if (isMapResourceLoaded && !this.m_isLoading)
            {
                this.loadAnimationEffect();
            }
            else if (this.m_currentBounceImage)
            {
                this.m_currentBounceImage.onUpdate(1 / 15);
            }
            return true;
        }//end

         public void  cleanUp ()
        {
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
            }
            if (this.m_currentPickImage)
            {
                this.removeFromParent(this.m_currentPickImage);
                this.m_currentPickImage = null;
            }
            if (this.m_currentBounceImage)
            {
                this.removeFromParent(this.m_currentBounceImage);
                this.m_currentBounceImage = null;
            }
            this.m_itemImageLoadedHash = null;
            this.m_isComplete = true;
            return;
        }//end

        public void  setPickType (String param1 )
        {
            if (!this.m_currentPickName)
            {
                this.m_currentPickName = param1;
            }
            if (!this.m_currentPickImage)
            {
                this.m_currentPickName = param1;
                this.reattach();
            }
            else if (this.m_targetPickName != param1)
            {
                this.m_targetPickName = param1;
                this.startBubble();
            }
            return;
        }//end

        public void  setBounceType (String param1 )
        {
            this.m_coinSpinName = param1;
            return;
        }//end

        public void  bounceToType (String param1 ="")
        {
            this.m_targetPickName = param1;
            if (this.m_isItemImagesLoaded && isMapResourceLoaded && !this.m_animTimeline)
            {
                this.startBounce();
            }
            return;
        }//end

         public void  reattach ()
        {
            Debug.debug4("CoinPickEffect.reattach");

            if (!this.m_isItemImagesLoaded || !isMapResourceLoaded)
            {
                return;
            }
            _loc_1 = m_mapResource.getReference().getSize();
            _loc_2 = IsoRect.getIsoRectFromSize(_loc_1);
            _loc_3 = m_mapResource.displayObjectOffsetX;
            _loc_4 = m_mapResource(-.displayObjectOffsetY)/m_mapResource.displayObject.scaleY;
            _loc_5 = m_mapResource(-.displayObjectOffsetY)/m_mapResource.displayObject.scaleY-_loc_2.height+(_loc_2.height-_loc_2.bottom.y);
            this.m_effectX = _loc_3 + (_loc_2.width >> 1);
            this.m_effectY = _loc_5 - _loc_2.height;
            if (!this.m_pickContainer)
            {
                this.m_pickContainer = new Sprite();
            }
            _loc_6 = this.getAttachParent ();
            if (!this.m_pickContainer.parent || this.m_pickContainer.parent && this.m_pickContainer.parent != _loc_6)
            {
                _loc_6.addChild(this.m_pickContainer);
            }
            this.setPickImage(this.m_currentPickName);
            if (this.m_currentPickImage)
            {
                this.m_currentPickImage.visible = false;
            }
            return;
        }//end

        private void  setPickImage (String param1 )
        {
            this.removeFromParent(this.m_currentPickImage);
            if (param1 && this.m_pickContainer)
            {
                this.m_currentPickImage =(DisplayObject) this.getImage(param1);
                this.m_currentPickImage.x = -this.m_currentPickImage.width >> 1;
                this.m_currentPickImage.y = -this.m_currentPickImage.height >> 1;
                this.m_pickContainer.addChild(this.m_currentPickImage);
                this.m_pickContainer.x = this.m_effectX;
                this.m_pickContainer.y = this.m_effectY;
                this.m_pickContainer.width = this.m_currentPickImage.width;
                this.m_pickContainer.height = this.m_currentPickImage.height;
            }
            return;
        }//end

        private void  startBounce ()
        {
            Debug.debug4("CoinPickEffect.startBounce");

            this.removeFromParent(this.m_currentBounceImage);
            this.m_currentBounceImage =(AnimatedBitmap) this.getImage(this.m_coinSpinName);
            _loc_1 = this.getAttachParent ();
            int _loc_2 =-1;
            if (this.m_pickContainer)
            {
                if (!this.m_pickContainer.parent)
                {
                    this.reattach();
                }
                _loc_2 = _loc_1.getChildIndex(this.m_pickContainer);
            }
            if (_loc_2 < 0)
            {
                return;
            }
            _loc_1.addChildAt(this.m_currentBounceImage, _loc_2);
            this.m_currentBounceImage.x = this.m_effectX - (this.m_currentBounceImage.width >> 1);
            this.m_currentBounceImage.y = this.m_effectY - (this.m_currentBounceImage.height >> 1);
            this.m_animTimeline = new TimelineLite({onComplete:this.bounceCompleteHandler});
            this.m_animTimeline.appendMultiple(.get(new TweenLite(this.m_currentBounceImage, 0.3, {y:this.m_currentBounceImage.y - this.BOUNCE_HEIGHT, ease:Quad.easeOut}), new TweenLite(this.m_currentBounceImage, 0.3, {y:this.m_currentBounceImage.y, ease:Quad.easeIn})), 0, TweenAlign.SEQUENCE);
            return;
        }//end

        private void  bounceCompleteHandler ()
        {
            this.m_animTimeline = null;
            this.removeFromParent(this.m_currentBounceImage);
            if (this.m_currentPickImage)
            {
                this.m_currentPickImage.visible = true;
                this.startBubble();
            }
            return;
        }//end

        private void  startBubble ()
        {

            Debug.debug4("CoinPickEffect.startBubble");
            if (this.m_animTimeline)
            {
                return;
            }
            if (this.m_targetPickName == "")
            {
                this.m_targetPickName = this.m_currentPickName || PICK_1;
            }
            _loc_1 = this.m_pickContainer.width ;
            _loc_2 = this.m_pickContainer.height ;
            _loc_3 = this.getImage(this.m_targetPickName )as DisplayObject ;
            _loc_4 = this.getNextSizePickName(this.m_targetPickName );
            this.setPickImage(_loc_4);
            this.m_pickContainer.width = _loc_1;
            this.m_pickContainer.height = _loc_2;
            this.m_animTimeline = new TimelineLite({onComplete:this.bubbleCompleteHandler});
            this.m_animTimeline.appendMultiple(.get(new TweenLite(this.m_pickContainer, 0.2, {width:this.m_currentPickImage.width, height:this.m_currentPickImage.height, ease:Quad.easeOut}), new TweenLite(this.m_pickContainer, 0.2, {width:_loc_3.width, height:_loc_3.height, ease:Quad.easeIn})), 0, TweenAlign.SEQUENCE);
            return;
        }//end

        private void  bubbleCompleteHandler ()
        {
            this.m_animTimeline = null;
            this.setPickImage(this.m_targetPickName);
            this.m_currentPickImage.visible = false;
            return;
        }//end

        private void  removeFromParent (DisplayObject param1 )
        {
            if (param1 && param1.parent)
            {
                param1.parent.removeChild(param1);
            }
            return;
        }//end

        private Object  getImage (String param1 )
        {
            _loc_2 = this.m_itemImageLoadedHash.get(param1) ;
            _loc_3 = _loc_2.image ;
            return _loc_3;
        }//end

        private Sprite  getAttachParent ()
        {
            _loc_1 = (Sprite)m_mapResource.getDisplayObject()
            return _loc_1;
        }//end

        private String  getNextSizePickName (String param1 )
        {
            _loc_2 = this.m_pickSizeOrder.indexOf(param1 );
            _loc_3 = this.m_pickSizeOrder.get((_loc_2 +1)) ;
            return _loc_3;
        }//end

        private void  loadAnimationEffect ()
        {

            Debug.debug4("CoinPickEffect.loadAnimationEffect");

            XML _loc_3 =null ;
            ItemImage _loc_4 =null ;
            String _loc_5 =null ;
            _loc_1 =Global.gameSettings().getEffectByName(this.EFFECT_NAME );
            _loc_2 = _loc_1.image ;
            this.m_itemImageLoadedHash = {};
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_5 = _loc_3.attribute("name") || this.m_coinSpinName;
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
            if (!this.m_isItemImagesLoaded)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onItemImageLoaded);
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                _loc_4 = _loc_2.name || this.m_coinSpinName;
                if (this.m_itemImageLoadedHash)
                {
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
                }
                if (_loc_5)
                {
                    this.m_isItemImagesLoaded = true;
                    this.reattach();
                }
            }
            return;
        }//end

    }



