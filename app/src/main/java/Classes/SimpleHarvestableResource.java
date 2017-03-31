package Classes;

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

import Classes.util.*;
import Modules.bandits.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;

    public class SimpleHarvestableResource extends HarvestableResource
    {

        public  SimpleHarvestableResource (String param1)
        {
            super(param1);
            m_state = STATE_PLANTED;
            return;
        }//end

         public boolean  isWitherOn ()
        {
            return false;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.sellPrice;
        }//end

         public boolean  warnForStorage ()
        {
            return true;
        }//end

         public void  restoreFromStorage (MapResource param1)
        {
            setState(STATE_PLANTED);
            plantTime = GlobalEngine.serverTime;
            super.restoreFromStorage(param1);
            return;
        }//end

         public void  onPlayAction ()
        {
            if (m_visitReplayLock > 0)
            {
                return;
            }
            m_actionMode = PLAY_ACTION;
            super.onPlayAction();
            if (Global.isVisiting())
            {
                return;
            }
            if (this.isHarvestable())
            {
                this.startHarvest();
            }
            return;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            updateObjectIndicator();
            return;
        }//end

         public void  onMouseOut ()
        {
            super.onMouseOut();
            updateObjectIndicator();
            return;
        }//end

         protected boolean  isObjectIndicatorVisible ()
        {
            return false;
        }//end

         protected boolean  updateState (double param1 )
        {
            if (this.isHarvestable() && m_state == STATE_PLANTED)
            {
                setState(STATE_GROWN);
                return true;
            }
            return false;
        }//end

        protected void  startHarvest ()
        {
            if (this.isHarvestable())
            {
                if (this.harvest())
                {
                    this.doHarvestDropOff();
                }
            }
            return;
        }//end

         public boolean  isHarvestable ()
        {
            return getGrowPercentage() == 100 || m_state == STATE_GROWN;
        }//end

         public Object  doHarvestDropOff (boolean param1 =true )
        {
            if (param1 !=null)
            {
                displayDelayedResourceChanges();
            }
            return super.doHarvestDropOff(param1);
        }//end

         public boolean  harvest ()
        {
            String _loc_2 =null ;
            boolean _loc_1 =false ;
            if (this.isHarvestable())
            {
                if (!Global.isVisiting())
                {
                    m_doobersArray = makeDoobers();
                    spawnDoobers();
                    if (!m_isBeingAutoHarvested && m_actionMode != VISIT_REPLAY_ACTION)
                    {
                        GameTransactionManager.addTransaction(new THarvest(this));
                        trackAction(TrackedActionType.HARVEST);
                    }
                    m_isBeingAutoHarvested = false;
                    _loc_2 = Wonder.itemBonusFlyout(this);
                    if (_loc_2 != null)
                    {
                        this.displayStatus(_loc_2, null, Wonder.wonderTextColor);
                    }
                    PreyManager.processHarvest(this);
                }
                plantTime = GlobalEngine.getTimer();
                setState(STATE_PLANTED);
                _loc_1 = true;
            }
            _loc_4 = m_harvestCounter+1;
            m_harvestCounter = _loc_4;
            updateObjectIndicator();
            return _loc_1;
        }//end

         public String  getVisitReplayEquivalentActionString ()
        {
            return "harvest";
        }//end

    }


