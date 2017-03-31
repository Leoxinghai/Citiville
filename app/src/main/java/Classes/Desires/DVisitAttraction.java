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
import Engine.Classes.*;
import Engine.Helpers.*;

    public class DVisitAttraction extends Desire
    {

        public  DVisitAttraction (DesirePeep param1 )
        {
            super(param1);
            return;
        }//end

         public SelectionResult  getSelection ()
        {
            Desire thisDesire ;
            choices = Global.world.getObjectsByPredicate(this.openChecker);
            if (choices.length == 0)
            {
                return SelectionResult.FAIL;
            }
            randomIndex = Math.floor(Math.random()*choices.length());
            target = choices.get(randomIndex);
            thisDesire;

            return new SelectionResult (target ,[new ActionEnableFreedom (m_peep ,false ),new ActionNavigate (m_peep ,target ,null ).setPathType (RoadManager .PATH_TO_FRONT_ENTRANCE ).setTeleportOnFailure (false ),new ActionEnableFreedom (m_peep ,true ),new ActionFn (m_peep ,void  ()
            {
                //thisDesire.setState(STATE_FINISHED);
                this.setState(STATE_FINISHED);
                return;
            }//end
            ), new ActionMerchantEnter(m_peep, (IMerchant)target)]);


        }//end

        protected boolean  openChecker (WorldObject param1 )
        {
            return param1 instanceof Attraction && param1 instanceof IMerchant && ((IMerchant)param1).isRouteable();
        }//end

    }



