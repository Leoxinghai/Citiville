package com.facebook.data.stream;

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

    public class StreamStoryData
    {
        public CommentsData comments ;
        public Date created_time ;
        public String message ;
        public String target_id ;
        public XML sourceXML ;
        public String privacy ;
        public Date updated_time ;
        public String source_id ;
        public String filter_key ;
        public String post_id ;
        public Array action_links ;
        public AttachmentData attachment ;
        public String actor_id ;
        public boolean is_hidden ;
        public String viewer_id ;
        public String permalink ;
        public Object metadata ;
        public String app_id ;
        public int type ;
        public LikesData likes ;
        public String attribution ;

        public  StreamStoryData ()
        {
            return;
        }//end

    }


