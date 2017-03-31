package com.facebook.data.users;

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

import com.facebook.data.*;
    public class FacebookUser extends FacebookData
    {
        public StatusData status ;
        public boolean has_added_app ;
        public String pic_with_logo ;
        public String pic_big_with_logo ;
        public int notes_count ;
        public String pic_small ;
        public String political ;
        public String music ;
        public String religion ;
        public int significant_other_id ;
        public Array email_hashes ;
        public String movies ;
        public String uid ;
        public FacebookLocation hometown_location ;
        public int wall_count ;
        public String hs2_name ;
        public String proxied_email ;
        public String sex ;
        public String hs_info ;
        public Array work_history ;
        public Array meeting_sex ;
        public String pic_square ;
        public String quotes ;
        public int hs1_id ;
        public String locale ;
        public String birthday ;
        public String tv ;
        public Array affiliations ;
        public String interests ;
        public String pic ;
        public String name ="";
        public String grad_year ;
        public String about_me ;
        public String last_name ="";
        public String pic_small_with_logo ;
        public boolean is_app_user ;
        public String hs1_name ;
        public String books ;
        public String first_name ="";
        public FacebookLocation current_location ;
        public Array meeting_for ;
        public Date birthdayDate ;
        public Array networkAffiliations ;
        public String pic_big ;
        public String relationship_status ;
        public int hs2_id ;
        public String profile_url ;
        public Date profile_update_time ;
        public String activities ;
        public String pic_square_with_logo ;
        public int timezone ;
        public boolean isLoggedInUser ;
        public Array education_history ;

        public void  FacebookUser ()
        {
            return;
        }//end  

    }


