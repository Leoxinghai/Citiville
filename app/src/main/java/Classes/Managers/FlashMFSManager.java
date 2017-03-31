package Classes.Managers;

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

//import flash.utils.*;
    public class FlashMFSManager
    {
        protected Dictionary m_managers ;
        public static  String TYPE_RECENTLY_PLAYED_MFS ="TYPE_RECENTLY_PLAYED_MFS";
        public static  String TYPE_FLASH_MFS_LIST ="TYPE_FLASH_MFS_LIST";
        private static  Object typeMap ={TYPE_RECENTLY_PLAYED_MFS RecentlyPlayedMFSManager ,TYPE_FLASH_MFS_LIST };

        public  FlashMFSManager ()
        {
            this.m_managers = new Dictionary();
            return;
        }//end

        public BaseMFSManager  getManager (String param1 )
        {
            if (!this.m_managers.hasOwnProperty(param1))
            {
                this.m_managers.put(param1,  new typeMap.get(param1));
            }
            return this.m_managers.get(param1);
        }//end

    }


