package Classes.sim;

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
import Classes.Desires.*;
import Classes.actions.*;

    public class HotelCrowdManager extends MerchantCrowdManager
    {
        protected int m_waveSizeMax ;

        public  HotelCrowdManager (Hotel param1 )
        {
            super(param1);
            return;
        }//end

         public void  makeNpcEnterMerchant (NPC param1 )
        {
            boolean isNotPeep ;
            Peep peep ;
            npc = param1;
            isNotPeep = !(npc instanceof Peep);
            peep =(Peep) npc;
            npc .getStateMachine ().addActions (new ActionFn (npc ,void  ()
            {
                if (!Global.isVisiting() && !isNotPeep)
                {
                    ((Hotel)m_merchant).performVisit(peep);
                }
                _loc_1 = npc.getHome()? (npc.getHome()) : (Global.world.citySim.npcManager.getRandomReturnHome());
                if (_loc_1)
                {
                    Global.world.citySim.npcManager.onResidentReturnHome(_loc_1);
                }
                return;
            }//end
            ), new ActionDie(npc));
            return;
        }//end

         public void  makeNpcFailEnter (NPC param1 )
        {
            npc = param1;
            isNotPeep =             !(npc instanceof Peep);
            peep = (Peep)npc
            npc .getStateMachine ().addActions (new ActionFn (npc ,void  ()
            {
                _loc_1 = null;
                _loc_2 = null;
                _loc_3 = null;
                npc.showCannotEnterHotelFeedbackBubble();
                Global.world.citySim.resortManager.checkNPCCannotCheckInTutorial(npc);
                if (npc instanceof DesirePeep)
                {
                    _loc_1 =(DesirePeep) npc;
                    _loc_2 = _loc_1.findDesireByType(DesireTypes.GO_HOTEL);
                    if (_loc_2 != null)
                    {
                        if (!_loc_1.wanderer)
                        {
                            _loc_3 = new DWanderRoads(_loc_1);
                            _loc_1.insertDesireBeforePivot(_loc_3, DesireTypes.GO_HOTEL);
                            _loc_2.setState(Desire.STATE_NOT_STARTED);
                            _loc_1.wanderer = true;
                        }
                        else
                        {
                            _loc_2.setState(Desire.STATE_FINISHED);
                        }
                    }
                }
                return;
            }//end
            ));
            return;
        }//end

         public void  performShockwave ()
        {
            m_wave = new HotelShockwave((MapResource)m_merchant, 9, 15, null, onWaveFinished);
            m_wave.start();
            return;
        }//end

         public void  releasePeeps ()
        {
            if (((Hotel)m_merchant).getState() == Hotel.STATE_CLOSED)
            {
                Global.world.citySim.npcManager.startSpawningVacationersFromResource((MapResource)m_merchant, int(((Hotel)m_merchant).guestCapacity * Global.gameSettings().getNumber("HotelGuestReleaseMultiplier", 0.2)));
            }
            return;
        }//end

    }



