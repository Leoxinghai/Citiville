package com.xinghai.chat;

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

import com.xinghai.ExtendComp.*;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.lang.*;
import mx.binding.*;
import mx.containers.*;
import mx.core.*;
import mx.events.*;
import mx.styles.*;

    public class StopUserActionPanel extends Canvas implements IBindingClient
    {
        private Timer timer ;
        public GlowLabel _StopUserActionPanel_GlowLabel1 ;
        Object _bindingsBeginWithWord ;
        private int count =1;
        Object _bindingsByDestination ;
        Array _watchers ;
        private Canvas _3556653text ;
        Array _bindings ;
        private UIComponentDescriptor _documentDescriptor_ ;
        private static IWatcherSetupUtil _watcherSetupUtil ;

        public  StopUserActionPanel ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
void                 return {1000width ,600height ,[new childDescriptors UIComponentDescriptor ({Canvas type ,"text"id , stylesFactory ()
                {
                    this.horizontalCenter = "0";
                    return;
                }//end
Object                 , propertiesFactory ()
                {
void                     return {200width ,40height ,"Border124"styleName ,250y ,false visible ,[new childDescriptors UIComponentDescriptor ({GlowLabel type ,"_StopUserActionPanel_GlowLabel1"id , stylesFactory ()
                    {
                        this.color = 16777215;
                        this.fontSize = 14;
                        return;
                    }//end
Object                     , propertiesFactory ()
                    {
                        return {y:9, x:26};
                    }//end
                    })]};
                }//end
                })]};
            }//end
            });
            this.timer = new Timer(1000, 15);
            this._bindings = new Array();
            this._watchers = new Array();
            this._bindingsByDestination = {};
            this._bindingsBeginWithWord = {};
            mx_internal::_document = this;
            if (!this.styleDeclaration)
            {
                this.styleDeclaration = new CSSStyleDeclaration();
            }
            this .styleDeclaration .defaultFactory =void  ()
            {
                this.backgroundAlpha = 0.01;
                this.backgroundColor = 0;
                return;
            }//end
            ;
            this.width = 1000;
            this.height = 600;
            return;
        }//end

        public void  hide ()
        {
            if (this.timer.hasEventListener(TimerEvent.TIMER))
            {
                this.timer.removeEventListener(TimerEvent.TIMER, this.showWaitingText);
            }
            if (this.timer.hasEventListener(TimerEvent.TIMER_COMPLETE))
            {
                this.timer.removeEventListener(TimerEvent.TIMER_COMPLETE, this.onTimeUp);
            }
            visible = false;
            this.text.visible = false;
            return;
        }//end

        private void  onTimeUp (TimerEvent event )
        {
            this.hide();
            return;
        }//end

         public void  initialize ()
        {
            StopUserActionPanel target ;
            Object watcherSetupUtilClass ;
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            bindings = this._StopUserActionPanel_bindingsSetup();
            Array watchers ;
            target;
            if (_watcherSetupUtil == null)
            {
                watcherSetupUtilClass = getDefinitionByName("_ui_StopUserActionPanelWatcherSetupUtil");
                _loc_2 = watcherSetupUtilClass;
                _loc_2.watcherSetupUtilClass.get("init")(null);
            }
            _watcherSetupUtil .setup (this , (String param1 )
            {
                return target.get(param1);
            }//end
            , bindings, watchers);
            int i ;
            while (i < bindings.length())
            {

                Binding(bindings.get(i)).execute();
                i = (i + 1);
            }
            mx_internal::_bindings = mx_internal::_bindings.concat(bindings);
            mx_internal::_watchers = mx_internal::_watchers.concat(watchers);
            super.initialize();
            return;
        }//end

        private void  showWaitingText (TimerEvent event )
        {
            if (this.count == 3)
            {
                this.text.visible = true;
            }
            return;
        }//end

        public void  text (Canvas param1 )
        {
            _loc_2 = this._3556653text ;
            if (_loc_2 !== param1)
            {
                this._3556653text = param1;
                this.dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "text", _loc_2, param1));
            }
            return;
        }//end

        private void  _StopUserActionPanel_bindingExprs ()
        {
            _loc_1 = null;
            _loc_1 = UILang.CONNECTSERVER;
            return;
        }//end

        private Array  _StopUserActionPanel_bindingsSetup ()
        {
            Binding binding ;
            Array result ;
            binding =new Binding (this ,String  ()
            {
                _loc_1 = UILang.CONNECTSERVER;
                _loc_2 = _loc_1==undefined ? (null) : (String(_loc_1));
                return _loc_2;
            }//end
            ,void  (String param1 )
            {
                _StopUserActionPanel_GlowLabel1.text = param1;
                return;
            }//end
            , "_StopUserActionPanel_GlowLabel1.text");
            result.put(0,  binding);
            return result;
        }//end

        public Canvas  text ()
        {
            return this._3556653text;
        }//end

        public void  show ()
        {
            this.timer.reset();
            this.timer.addEventListener(TimerEvent.TIMER, this.showWaitingText);
            this.timer.addEventListener(TimerEvent.TIMER_COMPLETE, this.onTimeUp);
            this.timer.start();
            visible = true;
            return;
        }//end

        public static void  watcherSetupUtil (IWatcherSetupUtil param1 )
        {
            StopUserActionPanel._watcherSetupUtil = param1;
            return;
        }//end

    }

