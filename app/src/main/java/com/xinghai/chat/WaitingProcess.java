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
import mx.binding.*;
import mx.containers.*;
import mx.controls.*;
import mx.core.*;
import mx.events.*;
import mx.styles.*;

    public class WaitingProcess extends Canvas implements IBindingClient
    {
        private boolean loaded =false ;
        private SWFLoader _1627480838mainProgress ;
        Object _bindingsBeginWithWord ;
        Object _bindingsByDestination ;
        private MovieClip mc ;
        public SWFLoader _WaitingProcess_SWFLoader1 ;
        Array _watchers ;
        private String _2067273540showMsg ;
        public GlowLabel _WaitingProcess_GlowLabel1 ;
        Array _bindings ;
        private String _336631465loadUrl ;
        private UIComponentDescriptor _documentDescriptor_ ;
        private static IWatcherSetupUtil _watcherSetupUtil ;

        public  WaitingProcess ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
void                 return {1000width ,600height ,[new childDescriptors UIComponentDescriptor ({SWFLoader type ,"_WaitingProcess_SWFLoader1"id }),new UIComponentDescriptor ({SWFLoader type ,"mainProgress"id ,{events "__mainProgress_complete"complete }, stylesFactory ()
                {
                    this.horizontalCenter = "0";
                    return;
                }//end
Object                 , propertiesFactory ()
                {
                    return {source:"../assets/images/UI/Content/progress.swf", y:500};
                }//end
void                 }),new UIComponentDescriptor ({GlowLabel type ,"_WaitingProcess_GlowLabel1"id , stylesFactory ()
                {
                    this.fontSize = 16;
                    this.color = 16777215;
                    return;
                }//end
Object                 , propertiesFactory ()
                {
                    return {width:200, x:400, y:529};
                }//end
                })]};
            }//end
            });
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
                this.backgroundAlpha = 1;
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
            visible = false;
            return;
        }//end

        private void  showMsg (String param1 )
        {
            _loc_2 = this._2067273540showMsg ;
            if (_loc_2 !== param1)
            {
                this._2067273540showMsg = param1;
                this.dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "showMsg", _loc_2, param1));
            }
            return;
        }//end

        private String  showMsg ()
        {
            return this._2067273540showMsg;
        }//end

        public SWFLoader  mainProgress ()
        {
            return this._1627480838mainProgress;
        }//end

         public void  initialize ()
        {
            WaitingProcess target ;
            Object watcherSetupUtilClass ;
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            bindings = this._WaitingProcess_bindingsSetup();
            Array watchers ;
            target;
            if (_watcherSetupUtil == null)
            {
                watcherSetupUtilClass = getDefinitionByName("_ui_WaitingProcessWatcherSetupUtil");
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

        private void  loadUrl (String param1 )
        {
            _loc_2 = this._336631465loadUrl ;
            if (_loc_2 !== param1)
            {
                this._336631465loadUrl = param1;
                this.dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "loadUrl", _loc_2, param1));
            }
            return;
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

        public void  showWaiting (String param1 ,int param2 )
        {
            if (sanguo.inst.serverName == "100")
            {
                this.loadUrl = "../assets/images/UI/Border/loadingBg1.swf";
            }
            else
            {
                this.loadUrl = "../assets/images/UI/Border/loadingBg.swf";
            }
            this.showMsg = param1 + param2 + "%";
            visible = true;
            if (this.mc)
            {
                this.mc.gotoAndStop(param2);
            }
            return;
        }//end

        private String  loadUrl ()
        {
            return this._336631465loadUrl;
        }//end

        private Array  _WaitingProcess_bindingsSetup ()
        {
            Binding binding ;
            Array result ;
            binding =new Binding (this ,Object  ()
            {
                return loadUrl;
            }//end
            ,void  (Object param1 )
            {
                _WaitingProcess_SWFLoader1.source = param1;
                return;
            }//end
            , "_WaitingProcess_SWFLoader1.source");
            result.put(0,  binding);
            binding =new Binding (this ,String  ()
            {
                _loc_1 = showMsg;
                _loc_2 = _loc_1==undefined ? (null) : (String(_loc_1));
                return _loc_2;
            }//end
            ,void  (String param1 )
            {
                _WaitingProcess_GlowLabel1.text = param1;
                return;
            }//end
            , "_WaitingProcess_GlowLabel1.text");
            result.put(1,  binding);
            return result;
        }//end

        public void  __mainProgress_complete (Event event )
        {
            this.onComplete(event);
            return;
        }//end

        private void  _WaitingProcess_bindingExprs ()
        {
            _loc_1 = null;
            _loc_1 = this.loadUrl;
            _loc_1 = this.showMsg;
            return;
        }//end

        private void  onComplete (Event event )
        {
            this.mc =(SWFLoader).content as MovieClip) (event.target;
            return;
        }//end

        public static void  watcherSetupUtil (IWatcherSetupUtil param1 )
        {
            WaitingProcess._watcherSetupUtil = param1;
            return;
        }//end

    }

