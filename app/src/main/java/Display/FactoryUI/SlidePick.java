package Display.FactoryUI;

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
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;
import Engine.Interfaces.*;

    public class SlidePick extends Sprite implements IInputHandler
    {
        protected JWindow m_jw ;
        protected JWindow m_innerWindow ;
        protected JPanel m_jpanel ;
        protected JPanel m_innerPanel ;
        protected Bitmap m_icon ;
        protected AssetPane m_assetPane ;
        protected Sprite m_mainSprite ;
        protected Sprite m_innerSprite ;
        protected Sprite m_innerMask ;
        protected int m_offXPos ;
        protected DisplayObject m_bgAsset ;
        protected int m_state =0;
        protected int m_visibility ;
        protected TimelineLite m_tween ;
        protected boolean m_sliderActive ;
        protected boolean m_animationActive ;
        protected boolean m_modal ;
        protected int m_slideWidth ;
        protected int m_slideHeight ;
        protected Sprite m_iconBackground ;
        protected Insets m_innerBackgroundMargins ;
        protected boolean m_stopPropogatingMouseEvents =false ;
        public static  int VISIBLE =0;
        public static  int VISIBLE_ON_ROLLOVER =1;
        public static  int NOT_VISIBLE =2;
        public static  int USE_BG_WIDTH =-1;
        public static  int USE_BG_HEIGHT =-1;
        public static  int DEFAULT_PICK_WIDTH =62;
        public static  int DEFAULT_PICK_HEIGHT =77;
        public static  int PICK_WIDTH_OFFSET =15;
        public static  int STATE_CLOSED =0;
        public static  int STATE_INFO =1;
        public static  int STATE_ACCEPT_DECLINE =2;
        public static  int PICK_FADE_DURATION =1;
        public static  int INNER_Y_OFFSET =3;
        public static  int HEIGHT_OFFSET =20;

        public  SlidePick (String param1 ,boolean param2 =false ,boolean param3 =true ,int param4 =0,boolean param5 =false ,int param6 =-1,int param7 =-1,int param8 =0)
        {
            this.m_slideWidth = param6;
            this.m_slideHeight = param7;
            this.m_innerPanel = ASwingHelper.makeSoftBoxJPanel(param8);
            this.init(param1);
            this.initPick();
            this.initInner();
            this.m_innerSprite.x = -this.slideWidth;
            this.m_offXPos = -this.slideWidth;
            this.m_innerWindow.setX(this.pickWidth);
            this.m_innerWindow.setY(INNER_Y_OFFSET);
            this.m_innerWindow.setContentPane(this.m_innerPanel);
            this.prepareWindow();
            this.redrawMask();
            this.m_innerWindow.show();
            this.m_sliderActive = param2;
            this.m_visibility = param4;
            this.m_modal = param5;
            this.m_tween = new TimelineLite({onComplete:this.onAnimationFinished});
            this.m_tween.appendMultiple(.get(new Z_TweenLite(this, 0.5, {y:this.y - HEIGHT_OFFSET, ease:Back.easeOut}), new Z_TweenLite(this, 0.5, {y:this.y, ease:Back.easeOut})), 0, TweenAlign.SEQUENCE);
            this.m_tween.gotoAndPlay(0);
            this.setActive(param3);
            if (this.m_visibility == VISIBLE_ON_ROLLOVER || this.m_visibility == NOT_VISIBLE)
            {
                this.alpha = 0;
            }
            return;
        }//end

        protected void  init (String param1 )
        {
            this.m_innerSprite = new Sprite();
            this.m_innerWindow = new JWindow(this.m_innerSprite);
            addChild(this.m_innerSprite);
            this.m_jw = new JWindow(this);
            _loc_2 = this.getPickBackground();
            _loc_3 = this.getInnerBackground();
            this.m_mainSprite = new Sprite();
            DisplayObject _loc_4 =(DisplayObject)new _loc_2;
            this.m_mainSprite.addChild(_loc_4);
            this.m_bgAsset =(DisplayObject) new _loc_3;
            this.m_innerPanel.setBackgroundDecorator(new MarginBackground(this.m_bgAsset, new Insets()));
            this.m_innerPanel.setPreferredHeight(this.m_bgAsset.height);
            this.m_innerPanel.setMinimumHeight(this.m_bgAsset.height);
            if (param1 == null)
            {
                param1 = Global.getAssetURL("assets/hud/Friendbar/portrait_noProfileImg.png");
            }
            LoadingManager.load(param1, this.onIconLoaded, 0, this.onIconFail);
            return;
        }//end

        protected void  initPick ()
        {
            this.m_jpanel = ASwingHelper.makeSoftBoxJPanel();
            this.m_innerMask = new Sprite();
            this.redrawMask();
            addChild(this.m_innerMask);
            this.m_innerSprite.mask = this.m_innerMask;
            this.m_assetPane = new AssetPane(this.m_mainSprite);
            this.m_jpanel.append(this.m_assetPane);
            this.m_jw.setContentPane(this.m_jpanel);
            this.prepareWindow();
            this.m_jw.show();
            this.addEventListener(MouseEvent.MOUSE_OVER, this.removeSelectedItem, false, 0, true);
            addEventListener(MouseEvent.MOUSE_MOVE, this.catchMousePropagation, false, 0, true);
            addEventListener(MouseEvent.CLICK, this.catchMousePropagation, false, 0, true);
            return;
        }//end

        protected void  initInner ()
        {
            return;
        }//end

        protected void  rebuildInner ()
        {
            this.m_innerPanel.removeAll();
            this.initInner();
            this.prepareWindow();
            return;
        }//end

        protected void  innerAppend (Component param1 ,Object param2 )
        {
            this.m_innerPanel.append(param1, param2);
            ASwingHelper.prepare(this.m_innerPanel);
            return;
        }//end

        protected void  innerAppendAll (...args )
        {
            this.m_innerPanel.appendAll.apply(this, args);
            ASwingHelper.prepare(this.m_innerPanel);
            return;
        }//end

        protected void  removeSelectedItem (Event event )
        {
            Global.ui.hideAnyToolTip();
            return;
        }//end

        protected void  prepareWindow ()
        {
            if (this.m_jw)
            {
                ASwingHelper.prepare(this.m_jw);
            }
            if (this.m_innerWindow)
            {
                ASwingHelper.prepare(this.m_innerWindow);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  redrawMask ()
        {
            _loc_1 = this.slideWidth+this.innerBackgroundMargins.left+this.innerBackgroundMargins.right;
            _loc_2 = this.m_bgAsset.height+this.innerBackgroundMargins.top+this.innerBackgroundMargins.bottom;
            this.m_innerMask.graphics.beginFill(0, 1);
            this.m_innerMask.graphics.drawRect(this.pickWidth, INNER_Y_OFFSET, this.slideWidth, this.slideHeight);
            this.m_innerMask.graphics.endFill();
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            int _loc_2 =50;
            int _loc_3 =62;
            int _loc_4 =72;
            this.m_icon =(Bitmap) event.target.content;
            this.m_icon.smoothing = true;
            this.m_icon.width = _loc_2;
            this.m_icon.height = _loc_2;
            this.m_icon.x = this.m_assetPane.x + (_loc_3 >> 1) - (_loc_2 >> 1);
            this.m_icon.y = this.m_assetPane.y + (_loc_4 >> 1) - (_loc_2 >> 1);
            this.m_iconBackground = new Sprite();
            this.m_iconBackground.graphics.beginFill(16777215);
            this.m_iconBackground.graphics.drawRect(this.m_icon.x, this.m_icon.y, this.m_icon.width, this.m_icon.height);
            this.m_iconBackground.graphics.endFill();
            this.m_mainSprite.addChildAt(this.m_icon, 0);
            this.m_mainSprite.addChildAt(this.m_iconBackground, 0);
            this.prepareWindow();
            return;
        }//end

        protected void  onIconFail (Event event )
        {
            _loc_2 = Global.getAssetURL("assets/hud/Friendbar/portrait_noProfileImg.png");
            LoadingManager.load(_loc_2, this.onIconLoaded);
            return;
        }//end

        public Sprite  mainSprite ()
        {
            return this.m_mainSprite;
        }//end

        public Rectangle  getMainSpriteRect (DisplayObject param1 )
        {
            return this.m_mainSprite.getRect(param1);
        }//end

        protected int  slideWidth ()
        {
            if (this.m_slideWidth < 0 && this.m_bgAsset != null)
            {
                return this.m_bgAsset.width;
            }
            return this.m_slideWidth;
        }//end

        protected int  slideHeight ()
        {
            if (this.m_slideHeight < 0 && this.m_bgAsset != null)
            {
                return this.m_bgAsset.height;
            }
            return this.m_slideHeight;
        }//end

        protected int  pickWidth ()
        {
            if (this.m_mainSprite)
            {
                return this.m_mainSprite.width - PICK_WIDTH_OFFSET;
            }
            return DEFAULT_PICK_WIDTH - PICK_WIDTH_OFFSET;
        }//end

        protected double  innerYOffset ()
        {
            return INNER_Y_OFFSET;
        }//end

        protected Class  getPickBackground ()
        {
            return EmbeddedArt.deliveryPick;
        }//end

        protected Class  getInnerBackground ()
        {
            return EmbeddedArt.deliveryInfoField;
        }//end

        protected Insets  innerBackgroundMargins ()
        {
            if (!this.m_innerBackgroundMargins)
            {
                this.m_innerBackgroundMargins = new Insets();
            }
            return this.m_innerBackgroundMargins;
        }//end

        public void  enableInteraction ()
        {
            addEventListener(MouseEvent.MOUSE_DOWN, this.onClick, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OVER, this.onOver, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OUT, this.onOut, false, 0, true);
            addEventListener(MouseEvent.MOUSE_MOVE, this.onMove, false, 0, true);
            addEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false, 0, true);
            addEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false, 0, true);
            return;
        }//end

        public void  disableInteraction ()
        {
            removeEventListener(MouseEvent.MOUSE_DOWN, this.onClick, false);
            removeEventListener(MouseEvent.MOUSE_OVER, this.onOver, false);
            removeEventListener(MouseEvent.MOUSE_OUT, this.onOut, false);
            removeEventListener(MouseEvent.MOUSE_MOVE, this.onMove, false);
            removeEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false);
            removeEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false);
            return;
        }//end

        protected void  onClick (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  onMove (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  onOver (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  onOut (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  onRollOver (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  onRollOut (MouseEvent event )
        {
            this.postProcessMouseEvent(event);
            return;
        }//end

        protected void  postProcessMouseEvent (MouseEvent event )
        {
            if (this.m_stopPropogatingMouseEvents && event != null)
            {
                event.stopPropagation();
                event.stopImmediatePropagation();
            }
            return;
        }//end

        public void  enableStageInputHandling ()
        {
            InputManager.addHandler(this);
            return;
        }//end

        public void  disableStageInputHandling ()
        {
            InputManager.removeHandler(this);
            return;
        }//end

        public boolean  onMouseDown (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseUp (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseOut (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseMove (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseDoubleClick (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onMouseWheel (MouseEvent event )
        {
            return false;
        }//end

        public boolean  onKeyDown (KeyboardEvent event )
        {
            return false;
        }//end

        public boolean  onKeyUp (KeyboardEvent event )
        {
            return false;
        }//end

        public void  setPosition (double param1 ,double param2 )
        {
            this.x = param1;
            this.y = param2;
            this.m_tween.kill();
            this.m_tween = null;
            this.m_tween = new TimelineLite({onComplete:this.onAnimationFinished});
            this.m_tween.appendMultiple(.get(new Z_TweenLite(this, 0.5, {y:this.y - HEIGHT_OFFSET, ease:Back.easeOut}), new Z_TweenLite(this, 0.5, {y:this.y, ease:Back.easeOut})), 0, TweenAlign.SEQUENCE);
            this.m_tween.gotoAndPlay(0);
            this.setActive(this.m_animationActive);
            return;
        }//end

        public void  visibility (int param1 )
        {
            this.m_visibility = param1;
            if (this.m_visibility == VISIBLE_ON_ROLLOVER || this.m_visibility == NOT_VISIBLE)
            {
                Z_TweenLite.to(this, 0.3, {alpha:0});
            }
            return;
        }//end

        public void  buildAndShowInnerPane ()
        {
            this.rebuildInner();
            this.showInnerPane();
            return;
        }//end

        public void  showInnerPane ()
        {
            if (this.m_visibility == VISIBLE_ON_ROLLOVER)
            {
                Z_TweenLite.to(this, 0.3, {alpha:1});
            }
            if (this.m_sliderActive)
            {
                this.m_innerSprite.visible = true;
                this.m_innerMask.visible = true;
                if (this.m_modal)
                {
                    InputManager.disableInput();
                    Global.ui.mouseEnabled = false;
                    Global.ui.mouseChildren = false;
                }
                Z_TweenLite.to(this.m_innerSprite, 0.5, {x:0});
                if (this.m_animationActive)
                {
                    this.m_tween.pause();
                }
            }
            return;
        }//end

        public void  hideInnerPane ()
        {
            if (this.m_visibility == VISIBLE_ON_ROLLOVER)
            {
                Z_TweenLite.to(this, 0.3, {alpha:0});
            }
            if (this.m_sliderActive)
            {
                Z_TweenLite.to(this.m_innerSprite, 0.5, {x:this.m_offXPos, onComplete:this.turnOff});
                if (this.m_animationActive)
                {
                    this.m_tween.play();
                }
            }
            return;
        }//end

        private void  turnOff ()
        {
            if (this.alpha > 0)
            {
                this.alpha = 0;
            }
            this.m_innerSprite.visible = false;
            this.m_innerMask.visible = false;
            if (this.m_modal)
            {
                InputManager.enableInput();
                Global.ui.mouseEnabled = true;
                Global.ui.mouseChildren = true;
            }
            return;
        }//end

        public void  setActive (boolean param1 ,boolean param2 =false )
        {
            this.m_animationActive = param1;
            if (param2)
            {
                this.m_tween.restart();
                this.m_tween.pause();
            }
            if (this.m_animationActive)
            {
                this.m_tween.play();
            }
            else
            {
                this.m_tween.pause();
            }
            return;
        }//end

        public void  kill (Function param1)
        {
            Object _loc_2 ={alpha 0};
            if (param1 != null)
            {
                _loc_2.onComplete = param1;
            }
            Z_TweenLite.to(this, 0.3, _loc_2);
            this.disableInteraction();
            this.disableStageInputHandling();
            return;
        }//end

        private void  catchMousePropagation (MouseEvent event )
        {
            Global.ui.turnOffHighlightedObject();
            event.stopPropagation();
            return;
        }//end

        private void  onAnimationFinished ()
        {
            if (this.m_tween)
            {
                this.m_tween.gotoAndPlay(0);
            }
            return;
        }//end

        public void  cleanUp ()
        {
            if (this.m_tween != null)
            {
                this.m_tween.kill();
                this.m_tween = null;
            }
            return;
        }//end

    }



