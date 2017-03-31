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

import Engine.Managers.*;
    public class EngineObjectComponent
    {
        protected EngineObject m_owner =null ;
        protected String m_name =null ;

        public  EngineObjectComponent ()
        {
            return;
        }//end

        public void  initializeComponent ()
        {
            return;
        }//end

        public EngineObject  owner ()
        {
            return this.m_owner;
        }//end

        public void  owner (EngineObject param1 )
        {
            this.m_owner = param1;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public void  register (EngineObject param1 )
        {
            if (this.m_owner)
            {
                ErrorManager.addError("Trying to register an already-registered component!");
            }
            this.m_owner = param1;
            this.onAdd();
            return;
        }//end

        public void  unregister ()
        {
            if (!this.m_owner)
            {
                ErrorManager.addError("Trying to unregister an unregistered component!");
            }
            this.onRemove();
            this.m_owner = null;
            return;
        }//end

        public Object  getSaveObject ()
        {
            return null;
        }//end

        public void  loadObject (Object param1 )
        {
            return;
        }//end

        protected void  onAdd ()
        {
            return;
        }//end

        protected void  onRemove ()
        {
            return;
        }//end

    }



