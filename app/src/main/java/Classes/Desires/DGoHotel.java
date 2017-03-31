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

    public class DGoHotel extends Desire
    {

        public  DGoHotel (DesirePeep param1 )
        {
            super(param1);
            return;
        }//end

         public SelectionResult  getSelection ()
        {
            Desire thisDesire ;
            Array hotels ;
            _loc_3 = m_resists+1;
            m_resists = _loc_3;
            if (m_resists < m_resistThreshold)
            {
                return SelectionResult.FAIL;
            }
            double hotelId ;
            if (m_peep.spawnSource == Peep.SOURCE_CRUISESHIP && Global.gameSettings().getNumber("HotelChance_" + m_peep.spawnSource, -1) == 100)
            {
                hotels = Global.world.getObjectsByClass(Hotel);
                if (hotels.length())
                {
                    hotelId = ((WorldObject)hotels.get(int(Math.random() * hotels.length()))).getId();
                }
            }
            else
            {
                hotelId = Global.world.citySim.resortManager.getHotelForResourceId(m_peep.lastMerchantId);
            }
            target = (MapResource)Global.world.getObjectById(hotelId)
            //thisDesire;
            if (hotelId == 0 || target == null)
            {
                return SelectionResult.FAIL;
            }
            return new SelectionResult (target ,[new ActionEnableFreedom (m_peep ,false ),new ActionNavigate (m_peep ,target ,null ).setPathType (RoadManager .PATH_TO_FRONT_ENTRANCE ).setTeleportOnFailure (false ),new ActionEnableFreedom (m_peep ,true ),new ActionFn (m_peep ,void  ()
            {
                //thisDesire.setState(STATE_FINISHED);
                this.setState(STATE_FINISHED);
                return;
            }//end
            ), new ActionMerchantEnter(m_peep, (IMerchant)target)]);
        }//end

        protected boolean  hotelChecker (WorldObject param1 )
        {
            return param1 instanceof Hotel && ((Hotel)param1).isRouteable();
        }//end

    }



