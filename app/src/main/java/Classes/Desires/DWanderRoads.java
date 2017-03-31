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
import Engine.*;

    public class DWanderRoads extends Desire
    {

        public  DWanderRoads (DesirePeep param1 )
        {
            super(param1);
            return;
        }//end

         public SelectionResult  getSelection ()
        {
            double timeout ;
            double plusminus ;
            double final ;
            Desire thisDesire ;
            if (!m_peep.canWander())
            {
                return new SelectionResult(null, [new ActionDie(m_peep)]);
            }
            timeout = Global.gameSettings().getNumber("npcWanderTimeSec", 15);
            plusminus = Global.gameSettings().getNumber("npcWanderTimePlusMinus", 5);
            final = MathUtil.random(timeout + plusminus, timeout - plusminus);
            //thisDesire;
            return new SelectionResult (null ,[new ActionEnableFreedom (m_peep ,false ),new ActionNavigateRandom (m_peep ).setTimeout (final ),new ActionEnableFreedom (m_peep ,true ),new ActionFn (m_peep ,void  ()
            {
                //thisDesire.setState(STATE_FINISHED);
                setState(STATE_FINISHED);
                return;
            }//end
            )]);
        }//end

    }



