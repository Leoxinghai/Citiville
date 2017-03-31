package Modules.quest.Display;

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
import Engine.Events.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
import Modules.saga.*;
//import flash.display.*;
//import flash.events.*;

    public class QuestSignetIconSprite extends Sprite
    {
        protected DisplayObject m_icon =null ;
        protected AutoAnimatedBitmap m_auto =null ;
        protected boolean m_loading =false ;
        protected boolean m_loaded =false ;

        public  QuestSignetIconSprite (String param1 )
        {
            this.name = param1;
            this.m_icon = null;
            return;
        }//end

        public void  load ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 =Global.questManager.getQuestByName(this.name );
            if (_loc_1.icon && (!this.m_loading || !this.m_loaded))
            {
                this.m_loading = true;
                this.m_loaded = false;
                _loc_2 = SagaManager.instance.getSagaNameByQuestName(this.name);
                if (_loc_2)
                {
                    SagaManager.instance.requestSagaIcon(_loc_2, this.onSagaIconLoaded);
                }
                else
                {
                    _loc_3 = Global.getAssetURL(_loc_1.icon);
                    LoadingManager.load(_loc_3, this.onIconLoaded);
                }
            }
            else
            {
                this.dispatchLoaded();
            }
            return;
        }//end

        protected void  onSagaIconLoaded (DisplayObject param1 )
        {
            this.addChild(param1);
            this.dispatchLoaded();
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            GameQuest _loc_3 =null ;
            Bitmap _loc_4 =null ;
            Object _loc_5 =null ;
            event.stopImmediatePropagation();
            _loc_2 =(Loader) event.target.loader;
            this.m_icon = _loc_2.content;
            if (this.m_icon)
            {
                _loc_3 = Global.questManager.getQuestByName(this.name);
                if (_loc_3)
                {
                    if (_loc_3.hasIconAnimation && (Bitmap)this.m_icon)
                    {
                        _loc_4 =(Bitmap) this.m_icon;
                        _loc_5 = _loc_3.getIconAnimation();
                        this.m_auto = new AutoAnimatedBitmap(_loc_4.bitmapData, _loc_5.numFrames, _loc_5.frameWidth, _loc_5.frameHeight, _loc_5.fps);
                        this.m_auto.play();
                        this.addChild(this.m_auto);
                    }
                    else
                    {
                        this.addChild(this.m_icon);
                    }
                }
                else
                {
                    this.detach();
                }
                this.dispatchLoaded();
            }
            return;
        }//end

        protected void  dispatchLoaded ()
        {
            this.m_loading = false;
            this.m_loaded = true;
            this.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        public void  detach ()
        {
            if (this.parent && this.parent.contains(this))
            {
                this.parent.removeChild(this);
            }
            return;
        }//end

    }



