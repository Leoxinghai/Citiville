package Classes.actions;

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
import Classes.sim.*;
import Engine.Helpers.*;

    public class ActionNavigateHotspots extends ActionNavigateBeeline
    {
        protected MapResource m_targetResource ;

        public  ActionNavigateHotspots (NPC param1 ,MapResource param2 ,Vector3 param3 =null )
        {
            super(param1, param2.getHotspot(), param3);
            this.m_targetResource = param2;
            return;
        }//end

         protected void  updatePath ()
        {
            Vector3 _loc_1 =null ;
            if (m_source == null)
            {
                m_source = m_npc.getPosition();
            }
            m_npc.setPosition(m_source.x, m_source.y);
            m_path = new Array();
            for(int i0 = 0; i0 < this.m_targetResource.getHotspots().size(); i0++) 
            {
            		_loc_1 = this.m_targetResource.getHotspots().get(i0);

                m_path.push(new PathElement(_loc_1, null, PathElement.TYPE_NONROAD));
            }
            debugPrintPath();
            return;
        }//end

    }



