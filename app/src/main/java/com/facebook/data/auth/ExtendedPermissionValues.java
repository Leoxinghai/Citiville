package com.facebook.data.auth;

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

    public class ExtendedPermissionValues
    {
        public static  String EMAIL ="email";
        public static  String RSVP_EVENT ="rsvp_event";
        public static  String OFFLINE_ACCESS ="offline_access";
        public static  String CREATE_NOTE ="create_note";
        public static  String STATUS_UPDATE ="status_update";
        public static  String CREATE_EVENT ="create_event";
        public static  String SMS ="sms";
        public static  String SHARE_ITEM ="share_item";
        public static  String PHOTO_UPLOAD ="photo_upload";

        public  ExtendedPermissionValues ()
        {
            return;
        }//end

    }


