package com.facebook.session;

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

import com.facebook.delegates.*;
import com.facebook.net.*;
//import flash.events.*;

    public interface IFacebookSession extends IEventDispatcher
    {


        void  secret (String param1 );

        boolean  waiting_for_login ();

        void  refreshSession ();

        String  rest_url ();

        IFacebookCallDelegate  post (FacebookCall param1 );

        void  rest_url (String param1 );

        void  login (boolean param1 );

        void  session_key (String param1 );

        String  secret ();

        String  api_version ();

        Date  expires ();

        String  session_key ();

        String  uid ();

        String  api_key ();

        boolean  is_connected ();

        void  verifySession ();

    }


