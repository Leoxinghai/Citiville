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

    public class GARemoveElements extends GuideAction
    {
        protected boolean m_removeDialogs =false ;
        protected boolean m_removeMasks =false ;
        protected boolean m_removeArrows =false ;
        protected boolean m_removeModes =false ;
        protected boolean m_removeGuideTiles =false ;
        protected boolean m_npcReset =false ;

        public  GARemoveElements ()
        {
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"remove");
            if (!_loc_2)
            {
                return false;
            }
            if (String(_loc_2.@dialogs) == "true")
            {
                this.m_removeDialogs = true;
            }
            if (String(_loc_2.@masks) == "true")
            {
                this.m_removeMasks = true;
            }
            if (String(_loc_2.@arrows) == "true")
            {
                this.m_removeArrows = true;
            }
            if (String(_loc_2.@modes) == "true")
            {
                this.m_removeModes = true;
            }
            if (String(_loc_2.@guidetiles) == "true")
            {
                this.m_removeGuideTiles = true;
            }
            if (String(_loc_2.@npcreset) == "true")
            {
                this.m_npcReset = true;
            }
            return true;
        }//end  

         public void  update (double param1 )
        {
            super.update(param1);
            if (this.m_removeDialogs)
            {
                m_guide.removeDialogs();
            }
            if (this.m_removeMasks)
            {
                m_guide.removeMask();
            }
            if (this.m_removeArrows)
            {
                m_guide.removeArrows();
            }
            if (this.m_removeModes)
            {
                Global.world.setDefaultGameMode();
            }
            if (this.m_removeGuideTiles)
            {
                m_guide.removeGuideTiles();
            }
            if (this.m_npcReset)
            {
                Global.world.citySim.resortManager.clearFocusNPC();
            }
            removeState(this);
            return;
        }//end  

    }



