package Engine.Managers;

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

import Engine.Events.*;
import com.adobe.images.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import mx.utils.*;

    public class FreezeManager extends EventDispatcher
    {
        public boolean freezingUnlocked =false ;
        private Bitmap m_frozenBitmap ;
        private Sprite m_frozenSprite ;
        private Array m_frozenDispObj =null ;
        protected int m_state =0;
        protected  int STATE_THAWED =0;
        protected  int STATE_RENDER_FROZEN =1;
        protected  int STATE_ALL_FROZEN =2;
        protected double m_frozenTime =0;
        protected  int CLICK_TO_THAW_TIME =3000;
        protected Array m_postThawCallbackArray ;
        private static FreezeManager m_instance ;

        public  FreezeManager ()
        {
            this.m_postThawCallbackArray = new Array();
            return;
        }//end

        public void  freezeAll ()
        {
            Stage _loc_1 =null ;
            BitmapData _loc_2 =null ;
            Shape _loc_3 =null ;
            if (this.freezingUnlocked && this.isAllFrozen() == false)
            {
                this.thawRender();
                if (GlobalEngine.stage.displayState == StageDisplayState.FULL_SCREEN)
                {
                    GlobalEngine.stage.displayState = StageDisplayState.NORMAL;
                }
                _loc_1 = GlobalEngine.stage;
                _loc_2 = new BitmapData(_loc_1.stageWidth, _loc_1.stageHeight, false, 0);
                _loc_2.draw(_loc_1);
                _loc_3 = new Shape();
                _loc_3.graphics.beginFill(0, 0.3);
                _loc_3.graphics.drawRect(0, 0, _loc_1.stageWidth, _loc_1.stageHeight);
                _loc_3.graphics.endFill();
                _loc_2.draw(_loc_3);
                this.createFrozenSprite(_loc_2, this.onFrozenAllClick);
                this.freezeObjects();
                _loc_1.addChild(this.m_frozenSprite);
                this.m_frozenBitmap.addEventListener(Event.ENTER_FRAME, this.onEnterFrame);
                this.m_frozenTime = GlobalEngine.currentTime;
                this.m_state = this.STATE_ALL_FROZEN;
            }
            return;
        }//end

        public void  freezeRender ()
        {
            Stage _loc_1 =null ;
            Sprite _loc_2 =null ;
            DisplayObjectContainer _loc_3 =null ;
            BitmapData _loc_4 =null ;
            Shape _loc_5 =null ;
            int _loc_6 =0;
            if (this.freezingUnlocked && this.isAnythingFrozen() == false)
            {
                _loc_1 = GlobalEngine.stage;
                _loc_2 = GlobalEngine.viewport;
                _loc_3 = _loc_2.parent;
                _loc_4 = new BitmapData(_loc_1.stageWidth, _loc_1.stageHeight, false, 0);
                _loc_4.draw(_loc_2);
                _loc_5 = new Shape();
                _loc_5.graphics.beginFill(0, 0.3);
                _loc_5.graphics.drawRect(0, 0, _loc_1.stageWidth, _loc_1.stageHeight);
                _loc_5.graphics.endFill();
                _loc_4.draw(_loc_5);
                this.createFrozenSprite(_loc_4, this.onFrozenRenderClick);
                _loc_6 = _loc_3.getChildIndex(_loc_2);
                _loc_3.removeChild(_loc_2);
                _loc_3.addChildAt(this.m_frozenSprite, _loc_6);
                this.m_state = this.STATE_RENDER_FROZEN;
                getInstance().dispatchEvent(new FreezeEvent(FreezeEvent.FROZEN));
            }
            return;
        }//end

        protected void  freezeObjects ()
        {
            _loc_1 = GlobalEngine.stage ;
            if (this.m_frozenDispObj == null)
            {
                this.m_frozenDispObj = new Array();
            }
            int _loc_2 =0;
            _loc_2 = 0;
            while (_loc_2 < _loc_1.numChildren)
            {

                this.m_frozenDispObj.push(_loc_1.getChildAt(_loc_2));
                _loc_2++;
            }
            _loc_2 = _loc_1.numChildren - 1;
            while (_loc_2 >= 0)
            {

                _loc_1.removeChildAt(_loc_2);
                _loc_2 = _loc_2 - 1;
            }
            return;
        }//end

        public void  pushPostThawCallback (Function param1 )
        {
            this.m_postThawCallbackArray.push(param1);
            return;
        }//end

        public void  thaw (boolean param1 =false )
        {
            Function _loc_2 =null ;
            Stage _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            if (this.isAnythingFrozen())
            {
                if (this.isRenderFrozen() || PopupQueueManager.getInstance().hasActiveDialog() || param1)
                {
                    this.thawRender();
                }
                if (this.isAllFrozen())
                {
                    _loc_3 = GlobalEngine.stage;
                    _loc_3.removeChild(this.m_frozenSprite);
                    this.m_frozenBitmap.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
                    if (this.m_frozenBitmap.bitmapData)
                    {
                        this.m_frozenBitmap.bitmapData.dispose();
                    }
                    this.m_frozenSprite.removeEventListener(MouseEvent.CLICK, this.onFrozenAllClick);
                    this.m_frozenSprite = null;
                    this.m_frozenBitmap = null;
                    this.freezeObjects();
                    for(int i0 = 0; i0 < this.m_frozenDispObj.size(); i0++)
                    {
                    	_loc_4 = this.m_frozenDispObj.get(i0);

                        if (_loc_4)
                        {
                            GlobalEngine.stage.addChildAt(_loc_4, GlobalEngine.stage.numChildren);
                        }
                    }
                    _loc_5 = GlobalEngine.stage.getChildByName("root1");
                    if (_loc_5 && _loc_5 instanceof BaseGame)
                    {
                        _loc_3.setChildIndex(_loc_5, 0);
                    }
                    this.m_frozenDispObj.splice(0, this.m_frozenDispObj.length());
                    this.m_state = this.STATE_THAWED;
                    InputManager.enableInput();
                    if (PopupQueueManager.getInstance().hasActiveDialog())
                    {
                        this.freezeRender();
                    }
                    else
                    {
                        PopupQueueManager.getInstance().pumpPopupQueue();
                    }
                    getInstance().dispatchEvent(new FreezeEvent(FreezeEvent.THAWED));
                }
                while (this.m_postThawCallbackArray.length > 0)
                {

                    _loc_2 = this.m_postThawCallbackArray.shift();
                    _loc_2();
                }
            }
            return;
        }//end

        protected void  thawRender ()
        {
            DisplayObjectContainer _loc_1 =null ;
            int _loc_2 =0;
            if (this.isRenderFrozen())
            {
                _loc_1 = this.m_frozenSprite.parent;
                _loc_2 = _loc_1.getChildIndex(this.m_frozenSprite);
                _loc_1.removeChild(this.m_frozenSprite);
                _loc_1.addChildAt(GlobalEngine.viewport, _loc_2);
                this.m_frozenSprite.removeEventListener(MouseEvent.CLICK, this.onFrozenRenderClick);
                if (this.m_frozenBitmap.bitmapData)
                {
                    this.m_frozenBitmap.bitmapData.dispose();
                }
                this.m_frozenBitmap = null;
                this.m_frozenSprite = null;
                this.m_state = this.STATE_THAWED;
            }
            return;
        }//end

        public boolean  isRenderFrozen ()
        {
            return this.STATE_RENDER_FROZEN == this.m_state;
        }//end

        public boolean  isAllFrozen ()
        {
            return this.STATE_ALL_FROZEN == this.m_state;
        }//end

        public boolean  isAnythingFrozen ()
        {
            return this.STATE_THAWED != this.m_state;
        }//end

        protected void  onFrozenRenderClick (Event event )
        {
            if (this.isRenderFrozen() && PopupQueueManager.getInstance().hasActiveDialog() == false)
            {
                this.thaw();
            }
            return;
        }//end

        protected void  onFrozenAllClick (Event event )
        {
            if (this.isAllFrozen() && this.CLICK_TO_THAW_TIME + this.m_frozenTime < GlobalEngine.currentTime)
            {
                this.thaw();
            }
            return;
        }//end

        public void  removeFrozenPopup (DisplayObject param1 )
        {
            int _loc_2 =0;
            if (this.m_frozenDispObj)
            {
                _loc_2 = 0;
                while (_loc_2 < this.m_frozenDispObj.length())
                {

                    if (this.m_frozenDispObj.get(_loc_2) == param1)
                    {
                        this.m_frozenDispObj.splice(_loc_2, 1);
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        protected void  onEnterFrame (Event event )
        {
            if (this.isAllFrozen())
            {
                GlobalEngine.zaspManager.preUpdate();
                getInstance().dispatchEvent(new FreezeEvent(FreezeEvent.UPDATE));
                GlobalEngine.zaspManager.postUpdate();
            }
            return;
        }//end

        public String  exportScreenshot ()
        {
            Base64Encoder _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_1 =null ;
            _loc_2 = GlobalEngine.stage ;
            BitmapData _loc_3 =new BitmapData(_loc_2.stageWidth ,_loc_2.stageHeight ,false ,0);
            _loc_3.draw(_loc_2);
            Shape _loc_4 =new Shape ();
            _loc_4.graphics.beginFill(0, 0.3);
            _loc_4.graphics.drawRect(0, 0, _loc_2.stageWidth, _loc_2.stageHeight);
            _loc_4.graphics.endFill();
            _loc_3.draw(_loc_4);
            _loc_5 = PNGEncoder.encode(_loc_3);
            if (PNGEncoder.encode(_loc_3))
            {
                _loc_6 = new Base64Encoder();
                _loc_6.encodeBytes(_loc_5);
                _loc_7 = _loc_6.toString();
                if (_loc_7)
                {
                    _loc_1 = _loc_7;
                }
            }
            return _loc_1;
        }//end

        protected void  createFrozenSprite (BitmapData param1 ,Function param2 )
        {
            this.m_frozenBitmap = new Bitmap(param1);
            this.m_frozenSprite = new Sprite();
            this.m_frozenSprite.addChild(this.m_frozenBitmap);
            if (param2 != null)
            {
                this.m_frozenSprite.mouseEnabled = true;
                this.m_frozenSprite.mouseChildren = true;
                this.m_frozenSprite.addEventListener(MouseEvent.CLICK, param2);
            }
            return;
        }//end

        public static FreezeManager  getInstance ()
        {
            if (m_instance == null)
            {
                m_instance = new FreezeManager;
            }
            return m_instance;
        }//end

    }



