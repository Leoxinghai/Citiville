package GameMode;

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
import Engine.Managers.*;
//import flash.events.*;

    public class GMLinkedObjectEdit extends GMEdit
    {
        protected MapResource m_parentObject ;
        protected Array m_linkedObjects ;
        protected Array m_gmObjectEditModes ;
        protected boolean m_parentOnlyMode ;

        public  GMLinkedObjectEdit (MapResource param1 ,boolean param2 =false )
        {
            this.m_gmObjectEditModes = new Array();
            this.m_parentObject = param1;
            this.m_parentOnlyMode = param2;
            this.m_linkedObjects = Global.world.getLinkedObjects(this.m_parentObject);
            this.initializeGameModes();
            return;
        }//end  

        protected void  initializeGameModes ()
        {
            return;
        }//end  

         public void  disableMode ()
        {
            GameMode _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_gmObjectEditModes.size(); i0++) 
            {
            	_loc_1 = this.m_gmObjectEditModes.get(i0);
                
                _loc_1.disableMode();
            }
            super.disableMode();
            return;
        }//end  

         public void  enableMode ()
        {
            GameMode _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_gmObjectEditModes.size(); i0++) 
            {
            	_loc_1 = this.m_gmObjectEditModes.get(i0);
                
                _loc_1.enableMode();
                InputManager.removeHandler(_loc_1);
            }
            super.enableMode();
            return;
        }//end  

         public boolean  onMouseMove (MouseEvent event )
        {
            GameMode _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_gmObjectEditModes.size(); i0++) 
            {
            	_loc_2 = this.m_gmObjectEditModes.get(i0);
                
                _loc_2.onMouseMove(event);
            }
            super.onMouseMove(event);
            return true;
        }//end  

         public boolean  onMouseDown (MouseEvent event )
        {
            GameMode _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_gmObjectEditModes.size(); i0++) 
            {
            	_loc_2 = this.m_gmObjectEditModes.get(i0);
                
                _loc_2.onMouseDown(event);
            }
            super.onMouseDown(event);
            return true;
        }//end  

         public boolean  onMouseUp (MouseEvent event )
        {
            super.onMouseUp(event);
            return false;
        }//end  

    }



