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
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.lang.*;
import mx.binding.*;
import mx.containers.*;
import mx.controls.*;
import mx.core.*;
import mx.events.*;
import mx.styles.*;

    public class WaitingInitPanel extends Canvas implements IBindingClient
    {
        private SWFLoader _1627480838mainProgress ;
        private Timer timer ;
        Object _bindingsBeginWithWord ;
        Object _bindingsByDestination ;
        Array _watchers ;
        public GlowLabel _WaitingInitPanel_GlowLabel1 ;
        private MovieClip mc ;
        Array _bindings ;
        private UIComponentDescriptor _documentDescriptor_ ;
        private static IWatcherSetupUtil _watcherSetupUtil ;

        public  WaitingInitPanel ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
Object                 return {1000width ,600height ,[new childDescriptors UIComponentDescriptor ({Canvas type , propertiesFactory ()
                {
void                     return {"Border217"styleName ,250width ,50height ,375x ,297y ,[new childDescriptors UIComponentDescriptor ({GlowLabel type ,"_WaitingInitPanel_GlowLabel1"id , stylesFactory ()
                    {
                        this.color = 16777215;
                        return;
                    }//end
Object                     , propertiesFactory ()
                    {
                        return {x:92, y:10};
                    }//end
Object                     }),new UIComponentDescriptor ({SWFLoader type ,"mainProgress"id ,{events "__mainProgress_complete"complete }, propertiesFactory ()
                    {
                        return {source:"../assets/images/UI/Content/progress1.swf", y:34, x:9};
                    }//end
                    })]};
                }//end
                })]};
            }//end
            });
            this.timer = new Timer(50);
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
                this.fontSize = 12;
                this.backgroundAlpha = 0.01;
                this.backgroundColor = 16777215;
                return;
            }//end
            ;
            this.width = 1000;
            this.height = 600;
            return;
        }//end

        public void  hide ()
        {
            visible = false;
            this.timer.removeEventListener(TimerEvent.TIMER, this.enterFrame);
            this.timer.stop();
            this.mc.gotoAndStop(1);
            return;
        }//end

        public SWFLoader  mainProgress ()
        {
            return this._1627480838mainProgress;
        }//end

        public void  mainProgress (SWFLoader param1 )
        {
            _loc_2 = this._1627480838mainProgress ;
            if (_loc_2 !== param1)
            {
                this._1627480838mainProgress = param1;
                this.dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "mainProgress", _loc_2, param1));
            }
            return;
        }//end

        private Array  _WaitingInitPanel_bindingsSetup ()
        {
            Binding binding ;
            Array result ;
            binding =new Binding (this ,String  ()
            {
                _loc_1 = UILang.UILOADING;
                _loc_2 = _loc_1==undefined ? (null) : (String(_loc_1));
                return _loc_2;
            }//end
            ,void  (String param1 )
            {
                _WaitingInitPanel_GlowLabel1.text = param1;
                return;
            }//end
            , "_WaitingInitPanel_GlowLabel1.text");
            result.put(0,  binding);
            return result;
        }//end

         public void  initialize ()
        {
            WaitingInitPanel target ;
            Object watcherSetupUtilClass ;
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            bindings = this._WaitingInitPanel_bindingsSetup();
            Array watchers ;
            target;
            if (_watcherSetupUtil == null)
            {
                watcherSetupUtilClass = getDefinitionByName("_ui_WaitingInitPanelWatcherSetupUtil");
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

        public void  __mainProgress_complete (Event event )
        {
            this.onComplete(event);
            return;
        }//end

        private void  enterFrame (Event event )
        {
            if (this.mc && this.mc.currentFrame < 95)
            {
                this.mc.gotoAndStop(this.mc.currentFrame + 5);
            }
            return;
        }//end

        private void  onComplete (Event event )
        {
            this.mc =(SWFLoader).content as MovieClip) (event.target;
            this.mc.gotoAndStop(1);
            return;
        }//end

        private void  _WaitingInitPanel_bindingExprs ()
        {
            _loc_1 = null;
            _loc_1 = UILang.UILOADING;
            return;
        }//end

        public void  show ()
        {
            visible = true;
            this.timer.addEventListener(TimerEvent.TIMER, this.enterFrame);
            this.timer.start();
            return;
        }//end

        public static void  watcherSetupUtil (IWatcherSetupUtil param1 )
        {
            WaitingInitPanel._watcherSetupUtil = param1;
            return;
        }//end

    }

