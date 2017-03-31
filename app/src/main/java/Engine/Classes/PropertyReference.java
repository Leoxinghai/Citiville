package Engine.Classes;

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

    public class PropertyReference
    {
        protected String m_property =null ;
        protected String m_propertyParent =null ;
        protected String m_propertyName =null ;

        public  PropertyReference (String param1)
        {
            this.property = param1;
            return;
        }//end

        public String  property ()
        {
            return this.m_property;
        }//end

        public void  property (String param1 )
        {
            this.m_property = param1;
            _loc_2 = this.m_property.split(".");
            this.m_propertyParent = _loc_2.get(0);
            this.m_propertyName = _loc_2.get(1);
            return;
        }//end

        public String  propertyParent ()
        {
            return this.m_propertyParent;
        }//end

        public String  propertyName ()
        {
            return this.m_propertyName;
        }//end

        public String  toString ()
        {
            return this.m_property;
        }//end

    }



