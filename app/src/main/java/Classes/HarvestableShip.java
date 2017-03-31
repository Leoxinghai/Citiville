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

import Classes.LogicComponents.*;
import Modules.ships.cruise.*;
import Modules.stats.types.*;
//import flash.geom.*;

    public class HarvestableShip extends HarvestableResource
    {
        private  String HARVESTABLE_SHIP ="harvestableShip";
        protected HarvestableShipComponentBase m_logic =null ;
        public static  Object COMPONENTS ={cruise CruiseShipLogicComponent };
        public static  String DEFAULT_HARVESTABLE_SHIP_IMAGE ="grown";

        public  HarvestableShip (String param1 )
        {
            super(param1);
            setState(STATE_PLANTED);
            if (this.m_logic == null)
            {
                this.m_logic = this.getLogicComponent();
            }
            m_objectType = WorldObjectTypes.HARVESTABLE_SHIP;
            m_typeName = this.HARVESTABLE_SHIP;
            return;
        }//end

         public void  setItem (Item param1 )
        {
            super.setItem(param1);
            if (this.m_logic == null)
            {
                this.m_logic = this.getLogicComponent();
            }
            return;
        }//end

         public boolean  isHighlightable ()
        {
            return super.isHighlightable && (m_state != STATE_PLANTED || Global.world.isEditMode);
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            registerStatsActionName(TrackedActionType.HARVEST, "collect");
            return;
        }//end

        public HarvestableShipComponentBase  getLogicComponent ()
        {
            Class _loc_1 =null ;
            HarvestableShipComponentBase _loc_2 =null ;
            if (COMPONENTS.hasOwnProperty(m_item.behavior))
            {
                _loc_1 = COMPONENTS.get(m_item.behavior);
                _loc_2 =(HarvestableShipComponentBase) new _loc_1(this);
                return _loc_2;
            }
            return new HarvestableShipComponentBase(this);
        }//end

         public String  getToolTipStatus ()
        {
            return this.m_logic.getToolTipStatus();
        }//end

         public String  getToolTipAction ()
        {
            return this.m_logic.getToolTipAction();
        }//end

         public void  onPlayAction ()
        {
            if (m_visitReplayLock > 0)
            {
                return;
            }
            super.onPlayAction();
            this.m_logic.handlePlayAction();
            return;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            _loc_1 = this.m_logic.getProgressBarCancelFunction();
            return _loc_1 != null ? (_loc_1) : (super.getProgressBarCancelFunction());
        }//end

         public Function  getProgressBarStartFunction ()
        {
            _loc_1 = this.m_logic.getProgressBarStartFunction();
            return _loc_1 != null ? (_loc_1) : (super.getProgressBarStartFunction());
        }//end

         public Function  getProgressBarEndFunction ()
        {
            _loc_1 = this.m_logic.getProgressBarEndFunction();
            return _loc_1 != null ? (_loc_1) : (super.getProgressBarEndFunction());
        }//end

         protected void  createStagePickEffect ()
        {
            this.m_logic.createStagePickEffect();
            return;
        }//end

         protected void  updateArrow ()
        {
            if (this.m_logic.enableUpdateArrow())
            {
                this.createStagePickEffect();
            }
            else
            {
                removeStagePickEffect();
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.m_logic.onUpdate(param1);
            return;
        }//end

         protected boolean  updateState (double param1 )
        {
            return this.m_logic.updateState(param1);
        }//end

         public boolean  harvest ()
        {
            return this.m_logic.harvest();
        }//end

        public void  doDoobers ()
        {
            m_doobersArray = this.makeDoobers();
            spawnDoobers();
            return;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            return this.m_logic.makeDoobers(harvestingDefinition, m_secureRands, param1);
        }//end

         public Object  doHarvestDropOff (boolean param1 =true )
        {
            if (param1 !=null)
            {
                displayDelayedResourceChanges();
            }
            return super.doHarvestDropOff(param1);
        }//end

         public double  getHarvestTime ()
        {
            return this.m_logic.getHarvestTime();
        }//end

         public boolean  checkPlacementRequirements (int param1 ,int param2 )
        {
            if (!super.checkPlacementRequirements(param1, param2))
            {
                return false;
            }
            return this.m_logic.checkPlacementRequirements(param1, param2);
        }//end

         public boolean  isHarvestable ()
        {
            return this.m_logic.isHarvestable();
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            Point _loc_2 =null ;
            if (m_state == STATE_PLANTED)
            {
                if (m_maskBitmap)
                {
                    _loc_2 = param1.subtract(new Point(m_displayObject.x, m_displayObject.y));
                    _loc_2.x = _loc_2.x / m_displayObject.scaleX;
                    _loc_2.y = _loc_2.y / m_displayObject.scaleY;
                    _loc_2.x = _loc_2.x - m_content.x;
                    _loc_2.y = _loc_2.y - m_content.y;
                    if (m_maskBitmap.bitmapData.hitTest(new Point(0, 0), 0, _loc_2))
                    {
                        return true;
                    }
                }
            }
            return super.isPixelInside(param1);
        }//end

         protected void  onStateChanged (String param1 ,String param2 )
        {
            super.onStateChanged(param1, param2);
            this.m_logic.onStateChanged(param1, param2);
            return;
        }//end

         public String  getActionText ()
        {
            return this.m_logic.getActionText();
        }//end

        public void  setPlantedWhenShipArrives ()
        {
            this.m_logic.setPlantedWhenShipArrives();
            return;
        }//end

         public boolean  isSellable ()
        {
            return this.m_logic.isSellable();
        }//end

    }



