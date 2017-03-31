package Classes;

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

import Engine.*;
//import flash.events.*;

    public class MissionObject extends MapResource
    {
        protected Mission m_mission ;
public static  String ASSET_NAME ="asset";

        public  MissionObject (Mission param1 )
        {
            super(param1.assetItemName);
            this.m_mission = param1;
            m_collisionFlags = Constants.COLLISION_NONE;
            return;
        }//end  

         protected ItemImageInstance  getCurrentImage ()
        {
            return m_item.getCachedImage(ASSET_NAME);
        }//end  

         public void  onClick ()
        {
            return;
        }//end  

         public void  onMouseOver (MouseEvent event )
        {
            return;
        }//end  

         public void  onMouseOut ()
        {
            return;
        }//end  

        public Mission  getMission ()
        {
            return this.m_mission;
        }//end  

    }


