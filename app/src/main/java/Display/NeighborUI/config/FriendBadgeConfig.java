package Display.NeighborUI.config;

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

import Classes.*;
    public class FriendBadgeConfig
    {
        protected String m_name ;
        protected String m_asset ;
        protected int m_priority ;
        protected String m_validateOn ;

        public  FriendBadgeConfig (String param1 ,String param2 ,int param3 =0,String param4 =null )
        {
            this.m_name = param1;
            this.m_asset = param2;
            this.m_priority = param3;
            this.m_validateOn = param4;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  asset ()
        {
            return this.m_asset;
        }//end

        public int  priority ()
        {
            return this.m_priority;
        }//end

        public boolean  isValid (Friend param1 )
        {
            _loc_2 =Global.validationManager.getValidationFunction("FriendValidationUtil",this.m_validateOn );
            if (_loc_2 != null)
            {
                return _loc_2.call(param1, null);
            }
            return false;
        }//end

    }



