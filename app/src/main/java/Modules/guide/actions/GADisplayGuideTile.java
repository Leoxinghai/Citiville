package Modules.guide.actions;

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

import Engine.Helpers.*;
import Modules.guide.ui.*;

    public class GADisplayGuideTile extends GADisplayTarget
    {
        private  String TARGET_GUIDE_TILE ="guidetile";
        protected GuideTile m_guideTile =null ;

        public  GADisplayGuideTile ()
        {
            m_targetType = this.TARGET_GUIDE_TILE;
            m_showOutline = false;
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = super.createFromXml(param1);
            Vector3 _loc_3 =new Vector3 ();
            _loc_3.x = this.m_focus.x;
            _loc_3.y = this.m_focus.y;
            return _loc_2;
        }//end  

         public void  enter ()
        {
            m_guide.displayGuideTile(new Vector3(m_focus.x, m_focus.y, 0), m_size.x, m_size.y);
            return;
        }//end  

    }



