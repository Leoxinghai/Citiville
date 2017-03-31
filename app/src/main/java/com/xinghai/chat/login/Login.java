package com.xinghai.ui.login;

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

import com.xinghai.manager.*;
import mx.containers.*;
import mx.core.*;
import mx.rpc.events.*;
import mx.styles.*;
import com.xinghai.server.*;

    public class Login extends Canvas
    {
        private UIComponentDescriptor _documentDescriptor_ ;

        public  Login ()
        {
Object             this ._documentDescriptor_ =new UIComponentDescriptor ({Canvas type , propertiesFactory ()
            {
                return {width:1000, height:600};
            }//end  
            });
            mx_internal::_document = this;
            if (!this.styleDeclaration)
            {
                this.styleDeclaration = new CSSStyleDeclaration();
            }
            this .styleDeclaration .defaultFactory =void  ()
            {
                this.backgroundColor = 0;
                this.fontSize = 12;
                return;
            }//end  
            ;
            this.width = 1000;
            this.height = 600;
            return;
        }//end  

         public void  initialize ()
        {
            .mx_internal::setDocumentDescriptor(this._documentDescriptor_);
            super.initialize();
            return;
        }//end  

        private void  onLogin (ResultEvent event )
        {
            if (event.result.error_code == -1 && parent)
            {
                parent.removeChild(this);
            }
            DataManager.Instance.onLogin(event);
            return;
        }//end  

        public void  autoLogin (String param1 ,String param2 )
        {
            HttpServerManager.getInstance().callServer("loginService", "login", this.onLogin, [param1]);
            return;
        }//end  

    }

