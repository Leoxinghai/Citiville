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

import Engine.Classes.*;
import Engine.Managers.*;
import Modules.guide.*;

    public class GuideAction extends State
    {
        protected Guide m_guide ;
        protected GuideSequence m_seq ;

        public  GuideAction ()
        {
            return;
        }//end  

        public void  setGuide (Guide param1 ,GuideSequence param2 )
        {
            this.m_guide = param1;
            this.m_seq = param2;
            return;
        }//end  

        public boolean  createFromXml (XML param1 )
        {
            return false;
        }//end  

        protected void  removeState (GuideAction param1 )
        {
            if (this.m_seq)
            {
                this.m_seq.getActionExec().removeState(param1);
            }
            return;
        }//end  

        protected XMLList  checkAndGetElement (XML param1 ,String param2 )
        {
            if (param1.hasOwnProperty(param2))
            {
                return param1.elements(param2);
            }
            ErrorManager.addError("XML element named \'" + param2 + "\' not found in tutorial definition!");
            return null;
        }//end  

         public void  enter ()
        {
            this.m_guide.displayLoadingMask();
            return;
        }//end  

         public void  update (double param1 )
        {
            if (this.allAssetsLoaded() && this.m_guide)
            {
                this.m_guide.removeLoadingMask();
            }
            return;
        }//end  

        protected boolean  allAssetsLoaded ()
        {
            return true;
        }//end  

    }



