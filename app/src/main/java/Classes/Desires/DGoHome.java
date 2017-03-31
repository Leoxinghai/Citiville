package Classes.Desires;

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
import Classes.actions.*;
import Classes.sim.*;

    public class DGoHome extends Desire
    {

        public  DGoHome (DesirePeep param1 )
        {
            super(param1);
            return;
        }//end

         public SelectionResult  getSelection ()
        {
            Residence home ;
            Desire thisDesire ;
            _loc_3 = m_resists+1;
            m_resists = _loc_3;
            if (m_peep.canWander())
            {
                if (m_resists < m_resistThreshold && !m_peep.isTired)
                {
                    return SelectionResult.FAIL;
                }
            }
            Array actions =new Array ();
            home = m_peep.getHome();
            if (home == null)
            {
                home = Global.world.citySim.npcManager.getRandomReturnHome();
            }
            if (home != null)
            {


            }
            actions.push(new ActionDie(m_peep));
            return new SelectionResult(home, actions);
        }//end

    }



