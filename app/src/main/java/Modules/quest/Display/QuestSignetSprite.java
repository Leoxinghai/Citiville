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

import Engine.Events.*;
import Modules.quest.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.utils.*;

    public class QuestSignetSprite extends Sprite
    {
        protected QuestSignetIconSprite m_icon =null ;
        protected Dictionary m_tierToOverlay =null ;
        protected double m_queuedEvents =0;
        protected Dictionary m_options =null ;
        protected String m_lastOverlay =null ;
        protected String m_nextOverlay =null ;
        protected double m_animateEventCount =0;
        protected boolean m_loading =false ;
        protected boolean m_loaded =true ;
public static  double FOLD_OFFSET_Y =2;
        public static  String OPTION_ANIMATE_ON_SET ="OptionAnimateOnSet";

        public  QuestSignetSprite (String param1 )
        {
            this.name = param1;
            this.m_icon = null;
            this.m_tierToOverlay = new Dictionary();
            this.setDefaultOptions();
            return;
        }//end

        protected void  setDefaultOptions ()
        {
            this.m_options = new Dictionary();
            this.m_options.put(OPTION_ANIMATE_ON_SET,  false);
            return;
        }//end

        public void  load ()
        {
            boolean _loc_2 =false ;
            QuestSignetOverlaySprite _loc_3 =null ;
            GameQuestTier _loc_4 =null ;
            this.addEventListener(LoaderEvent.LOADED, this.onLoaded, false, 0, true);
            _loc_1 =Global.questManager.getQuestByName(this.name );
            if (!this.m_loading || !this.m_loaded)
            {
                this.m_queuedEvents++;
                this.m_loading = true;
                this.m_loaded = false;
                _loc_2 = false;
                _loc_3 = null;
                for(int i0 = 0; i0 < _loc_1.tiers.size(); i0++) 
                {
                		_loc_4 = _loc_1.tiers.get(i0);

                    _loc_3 = new QuestSignetOverlaySprite(this.name, _loc_4.name);
                    _loc_3.addEventListener(LoaderEvent.LOADED, this.onSpriteLoaded, false, 0, true);
                    this.m_tierToOverlay.put(_loc_4.name,  _loc_3);
                    this.m_queuedEvents++;
                    _loc_3.load();
                    if (!_loc_2 && _loc_4.lastChance > 0)
                    {
                        _loc_2 = true;
                    }
                }
                if (_loc_1.getRemainingTimeForExpirableQuest() && _loc_1.canShowQuestIconUI())
                {
                    _loc_3 = new QuestSignetOverlaySprite(this.name, QuestTierConfig.TIER_SUNSET);
                    _loc_3.addEventListener(LoaderEvent.LOADED, this.onSpriteLoaded, false, 0, true);
                    this.m_tierToOverlay.put(QuestTierConfig.TIER_SUNSET,  _loc_3);
                    this.m_queuedEvents++;
                    _loc_3.load();
                }
                if (_loc_1.isActivatable())
                {
                    _loc_3 = new QuestSignetOverlaySprite(this.name, QuestTierConfig.TIER_START);
                    _loc_3.addEventListener(LoaderEvent.LOADED, this.onSpriteLoaded, false, 0, true);
                    this.m_tierToOverlay.put(QuestTierConfig.TIER_START,  _loc_3);
                    this.m_queuedEvents++;
                    _loc_3.load();
                }
                if (_loc_2)
                {
                    _loc_3 = new QuestSignetOverlaySprite(this.name, QuestTierConfig.TIER_LAST_CHANCE);
                    _loc_3.addEventListener(LoaderEvent.LOADED, this.onSpriteLoaded, false, 0, true);
                    this.m_tierToOverlay.put(QuestTierConfig.TIER_LAST_CHANCE,  _loc_3);
                    this.m_queuedEvents++;
                    _loc_3.load();
                }
                if (_loc_1.icon)
                {
                    this.m_icon = new QuestSignetIconSprite(this.name);
                    this.m_icon.addEventListener(LoaderEvent.LOADED, this.onSpriteLoaded, false, 0, true);
                    this.m_queuedEvents++;
                    this.m_icon.load();
                }
                this.m_queuedEvents--;
                if (!this.m_queuedEvents)
                {
                    this.dispatchLoaded();
                }
            }
            else
            {
                this.dispatchLoaded();
            }
            return;
        }//end

        protected void  onSpriteLoaded (LoaderEvent event )
        {
            event.stopImmediatePropagation();
            _loc_2 =(Sprite) event.target;
            if (_loc_2 && (_loc_2 as QuestSignetOverlaySprite || _loc_2 as QuestSignetIconSprite))
            {
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.onSpriteLoaded);
                _loc_2.visible = false;
                this.addChild(_loc_2);
                this.m_queuedEvents--;
                if (!this.m_queuedEvents)
                {
                    this.dispatchLoaded();
                }
            }
            return;
        }//end

        protected void  onLoaded (LoaderEvent event )
        {
            String _loc_3 =null ;
            this.removeEventListener(LoaderEvent.LOADED, this.onLoaded);
            this.m_icon.visible = true;
            QuestSignetOverlaySprite _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_tierToOverlay.size(); i0++) 
            {
            		_loc_3 = this.m_tierToOverlay.get(i0);

                _loc_2 = this.m_tierToOverlay.get(_loc_3);
                _loc_2.x = this.x;
                _loc_2.y = this.y + this.m_icon.height - _loc_2.shape.height;
                _loc_2.alpha = 0;
                if (this.m_lastOverlay && _loc_3 == this.m_lastOverlay)
                {
                    _loc_2.alpha = 1;
                }
                _loc_2.visible = true;
            }
            this.addChildAt(this.m_icon, 0);
            return;
        }//end

        protected void  dispatchLoaded ()
        {
            this.m_loaded = true;
            this.m_loading = false;
            this.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        public String  overlay ()
        {
            return this.m_lastOverlay;
        }//end

        public void  overlay (String param1 )
        {
            if (this.m_tierToOverlay.get(param1))
            {
                this.m_nextOverlay = param1;
                this.setOverlayFolded();
            }
            return;
        }//end

        protected void  animateOverlayUnfold ()
        {
            this.m_animateEventCount = 1;
            _loc_1 = this.m_tierToOverlay.get(this.m_nextOverlay).height /2;
            TweenLite.to(this.m_tierToOverlay.get(this.m_nextOverlay), 0.3, {y:1 * (this.m_tierToOverlay.get(this.m_nextOverlay).height / 2), alpha:0.75, onComplete:this.animateOverlayUnfolded});
            if (this.m_lastOverlay && this.m_lastOverlay != this.m_nextOverlay)
            {
                this.m_animateEventCount++;
                TweenLite.to(this.m_tierToOverlay.get(this.m_lastOverlay), 0.3, {y:-1 * (this.m_tierToOverlay.get(this.m_lastOverlay).height / 2), alpha:0.75, onComplete:this.animateOverlayUnfolded});
            }
            return;
        }//end

        protected void  animateOverlayUnfolded ()
        {
            this.m_animateEventCount--;
            if (!this.m_animateEventCount)
            {
                this.m_animateEventCount = 1;
                this.addChildAt(this.m_tierToOverlay.get(this.m_nextOverlay), (this.numChildren - 1));
                TweenLite.to(this.m_tierToOverlay.get(this.m_nextOverlay), 0.3, {y:-1 * (this.m_tierToOverlay.get(this.m_nextOverlay).height / 2), alpha:1, onComplete:this.animateOverlayFolded});
                if (this.m_lastOverlay && this.m_lastOverlay != this.m_nextOverlay)
                {
                    this.m_animateEventCount++;
                    TweenLite.to(this.m_tierToOverlay.get(this.m_lastOverlay), 0.3, {y:1 * (this.m_tierToOverlay.get(this.m_lastOverlay).height / 2), alpha:0, onComplete:this.animateOverlayFolded});
                }
            }
            return;
        }//end

        protected void  animateOverlayFolded ()
        {
            this.m_animateEventCount--;
            if (!this.m_animateEventCount)
            {
                this.m_lastOverlay = this.m_nextOverlay;
            }
            return;
        }//end

        protected void  setOverlayFolded ()
        {
            this.addChildAt(this.m_tierToOverlay.get(this.m_nextOverlay), (this.numChildren - 1));
            this.m_tierToOverlay.get(this.m_nextOverlay).alpha = 1;
            if (this.m_lastOverlay && this.m_lastOverlay != this.m_nextOverlay)
            {
                this.m_tierToOverlay.get(this.m_lastOverlay).alpha = 0;
            }
            this.m_lastOverlay = this.m_nextOverlay;
            return;
        }//end

        public String  text ()
        {
            String _loc_1 =null ;
            if (this.m_lastOverlay)
            {
                _loc_1 = this.m_tierToOverlay.get(this.m_lastOverlay).text;
            }
            return _loc_1;
        }//end

        public void  text (String param1 )
        {
            if (this.m_lastOverlay)
            {
                this.m_tierToOverlay.get(this.m_lastOverlay).text = param1;
            }
            return;
        }//end

        public boolean  loaded ()
        {
            return this.m_loaded;
        }//end

        public boolean  getOption (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_options.get(param1))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public void  setOption (String param1 ,boolean param2 )
        {
            if (this.m_options.get(param1) !== null)
            {
                this.m_options.put(param1,  param2);
            }
            return;
        }//end

        public void  detach ()
        {
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            return;
        }//end

    }



