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
//import flash.net.*;
import mx.containers.*;
import mx.controls.*;
import mx.core.*;
import mx.events.*;

    public class NoticePanel extends Canvas
    {
        private Array lefts ;
        private boolean playing =false ;
        private int count =0;
        private UIComponentDescriptor _documentDescriptor_ ;
        private GlowTextArea _136074796textContainer ;

        public  NoticePanel ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
void                 return {500width ,28height ,[new childDescriptors UIComponentDescriptor ({GlowTextArea type ,"textContainer"id ,{events "__textContainer_link"link }, stylesFactory ()
                {
                    this.color = 16776960;
                    this.fontSize = 12;
                    this.fontWeight = "bold";
                    this.textAlign = "center";
                    this.backgroundAlpha = 0;
                    this.borderStyle = "none";
                    return;
                }//end
Object                 , propertiesFactory ()
                {
                    return {y:3, percentWidth:100, selectable:false};
                }//end
Object                 }),new UIComponentDescriptor ({Button type ,{events "___NoticePanel_Button1_click"click }, propertiesFactory ()
                {
                    return {styleName:"Button123", x:470, width:21, height:21, y:3};
                }//end
                })]};
            }//end
            });
            this.lefts = new Array();
            mx_internal::_document = this;
            this.styleName = "Border124";
            this.horizontalScrollPolicy = "off";
            this.verticalScrollPolicy = "off";
            this.width = 500;
            this.height = 28;
            return;
        }//end

        private void  nextText ()
        {
            this.count = 0;
            this.playing = false;
            this.checkPlay();
            return;
        }//end

        public void  textContainer (GlowTextArea param1 )
        {
            _loc_2 = this._136074796textContainer ;
            if (_loc_2 !== param1)
            {
                this._136074796textContainer = param1;
                this.dispatchEvent(PropertyChangeEvent.createUpdateEvent(this, "textContainer", _loc_2, param1));
            }
            return;
        }//end

        private void  checkPlay ()
        {
            if (this.lefts.length == 0)
            {
                this.playing = false;
                visible = false;
                if (hasEventListener(Event.ENTER_FRAME))
                {
                    removeEventListener(Event.ENTER_FRAME, this.enterframe);
                }
            }
            else if (this.playing == false)
            {
                this.playing = true;
                this.textContainer.htmlText = this.lefts.shift().content;
                if (!hasEventListener(Event.ENTER_FRAME))
                {
                    addEventListener(Event.ENTER_FRAME, this.enterframe);
                }
            }
            return;
        }//end

        public void  __textContainer_link (TextEvent event )
        {
            this.link(event);
            return;
        }//end

        public void  ___NoticePanel_Button1_click (MouseEvent event )
        {
            this.nextText();
            return;
        }//end

         public void  initialize ()
        {
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            super.initialize();
            return;
        }//end

        public GlowTextArea  textContainer ()
        {
            return this._136074796textContainer;
        }//end

        private void  enterframe (Event event )
        {
            if (this.playing == true)
            {
                String _loc_2 =this ;
                _loc_3 = this.count +1;
                _loc_2.count = _loc_3;
                if (this.count > 80)
                {
                    this.nextText();
                }
            }
            else
            {
                removeEventListener(Event.ENTER_FRAME, this.enterframe);
            }
            return;
        }//end

        private void  link (TextEvent event )
        {
            navigateToURL(new URLRequest(event.text));
            return;
        }//end

        public void  show (Array param1 )
        {
            visible = true;
            this.lefts = this.lefts.concat(param1);
            this.checkPlay();
            return;
        }//end

    }

