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

import Display.*;
    public class GAShowNews extends GuideAction
    {
        protected String m_locPrefix ="";
        protected String m_newsImage ="";

        public  GAShowNews ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"news");
            if (!_loc_2)
            {
                return false;
            }
            this.m_locPrefix = String(_loc_2.@locname);
            this.m_newsImage = String(_loc_2.@url);
            if (this.m_newsImage == null || this.m_newsImage.length == 0)
            {
                return false;
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            _loc_2 = ZLoc.t("Tutorials",this.m_locPrefix +"_newsTitle");
            _loc_3 = ZLoc.t("Tutorials",this.m_locPrefix +"_newsContent");
            _loc_4 = ZLoc.t("Tutorials",this.m_locPrefix +"_newsSubHead");
            UI.displayNewsFlash(_loc_3, 0, this.m_newsImage, _loc_4, _loc_2, true);
            removeState(this);
            return;
        }//end

    }



