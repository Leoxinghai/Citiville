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
import com.xinghai.manager.*;
import mx.binding.*;
import mx.containers.*;
import mx.core.*;
import mx.events.*;

    public class WallowPanel extends Canvas implements IBindingClient
    {
        Object _bindingsByDestination ;
        public GlowText _WallowPanel_GlowText1 ;
        Object _bindingsBeginWithWord ;
        private int _state ;
        Array _watchers ;
        private String _2067273540showMsg ;
        private Timer closeTimer ;
        Array _bindings ;
        private UIComponentDescriptor _documentDescriptor_ ;
        private static IWatcherSetupUtil _watcherSetupUtil ;

        public  WallowPanel ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
void                 return {300width ,120height ,[new childDescriptors UIComponentDescriptor ({GlowText type ,"_WallowPanel_GlowText1"id , stylesFactory ()
                {
                    this.fontSize = 16;
                    return;
                }//end
Object                 , propertiesFactory ()
                {
                    return {x:10, y:10, width:280, height:70, selectable:false};
                }//end
Object                 }),new UIComponentDescriptor ({GlowButton type ,{events "___WallowPanel_GlowButton1_click"click }, propertiesFactory ()
                {
                    return {label:"??", styleName:"Button113", x:50, y:82};
                }//end
Object                 }),new UIComponentDescriptor ({GlowButton type ,{events "___WallowPanel_GlowButton2_click"click }, propertiesFactory ()
                {
                    return {label:"???????", styleName:"Button113", x:167, y:82};
                }//end
                })]};
            }//end
            });
            this._bindings = new Array();
            this._watchers = new Array();
            this._bindingsByDestination = {};
            this._bindingsBeginWithWord = {};
            mx_internal::_document = this;
            this.width = 300;
            this.height = 120;
            this.styleName = "Border154";
            return;
        }//end

        private String  showMsg ()
        {
            return this._2067273540showMsg;
        }//end

        private void  _WallowPanel_bindingExprs ()
        {
            _loc_1 = null;
            _loc_1 = this.showMsg;
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

        public void  ___WallowPanel_GlowButton1_click (MouseEvent event )
        {
            this.close(null);
            return;
        }//end

         public void  initialize ()
        {
            WallowPanel target ;
            Object watcherSetupUtilClass ;
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            bindings = this._WallowPanel_bindingsSetup();
            Array watchers ;
            target;
            if (_watcherSetupUtil == null)
            {
                watcherSetupUtilClass = getDefinitionByName("_ui_WallowPanelWatcherSetupUtil");
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

        public void  ___WallowPanel_GlowButton2_click (MouseEvent event )
        {
            this.gotoWallow();
            return;
        }//end

        private Array  _WallowPanel_bindingsSetup ()
        {
            Binding binding ;
            Array result ;
            binding =new Binding (this ,String  ()
            {
                _loc_1 = showMsg;
                _loc_2 = _loc_1==undefined ? (null) : (String(_loc_1));
                return _loc_2;
            }//end
            ,void  (String param1 )
            {
                _WallowPanel_GlowText1.htmlText = param1;
                return;
            }//end
            , "_WallowPanel_GlowText1.htmlText");
            result.put(0,  binding);
            return result;
        }//end

        private void  gotoWallow ()
        {
            ShowResult.inst.showResult(-101);
            return;
        }//end

        private void  close (TimerEvent event )
        {
            this.closeTimer.removeEventListener(TimerEvent.TIMER_COMPLETE, this.close);
            this.closeTimer = null;
            visible = false;
            if (this._state == 4 || this._state == 5)
            {
                ShowResult.inst.showResult(-103);
            }
            return;
        }//end

        private void  show ()
        {
            visible = true;
            this.closeTimer = new Timer(50000, 1);
            this.closeTimer.addEventListener(TimerEvent.TIMER_COMPLETE, this.close);
            this.closeTimer.start();
            return;
        }//end

        public void  wallowState (int param1 )
        {
            if (this._state != param1)
            {
                DataManager.Instance.wallowBtn = true;
                this._state = param1;
                switch(this._state)
                {
                    case 2:
                    {
                        this.showMsg = "<font color=\'#00ff00\'>" + "?????????1????" + "</font>";
                        this.show();
                        break;
                    }
                    case 3:
                    {
                        this.showMsg = "<font color=\'#00ff00\'>" + "?????????2????" + "</font>";
                        this.show();
                        break;
                    }
                    case 4:
                    {
                        this.showMsg = "<font color=\'#ff0000\'>" + "?????????3????????????????????????????????1???????????" + "</font>";
                        this.show();
                        break;
                    }
                    case 5:
                    {
                        this.showMsg = "<font color=\'#ff0000\'>" + "?????????5????????????????????????????????1???????????" + "</font>";
                        this.show();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public static void  watcherSetupUtil (IWatcherSetupUtil param1 )
        {
            WallowPanel._watcherSetupUtil = param1;
            return;
        }//end

    }

