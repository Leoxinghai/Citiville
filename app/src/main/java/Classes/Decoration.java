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

import Display.*;
import Engine.Helpers.*;
import GameMode.*;
import Modules.stats.experiments.*;
//import flash.events.*;

    public class Decoration extends MechanicMapResource
    {
        private  String DECORATION ="decoration";
        public boolean isSelectionOutsideCityAllowed =false ;
        private static  Stadium m_stadium =null ;
        private static int m_optimizeOnUpdate =-1;

        public  Decoration (String param1)
        {
            super(param1);
            m_objectType = WorldObjectTypes.DECORATION;
            setState(STATE_STATIC);
            m_typeName = this.DECORATION;
            if (m_optimizeOnUpdate < 0)
            {
                m_optimizeOnUpdate = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_OFFSCREEN_DECO) ? (1) : (0);
            }
            return;
        }//end

         public boolean  autoRotate ()
        {
            return false;
        }//end

         public boolean  isSellable ()
        {
            return m_ownable;
        }//end

        public boolean  isBranded ()
        {
            return m_item.isBrandedBusiness;
        }//end

         protected int  getSellPrice ()
        {
            return m_item.sellPrice;
        }//end

         public boolean  isPickable ()
        {
            return super.isPickable() && (this.isSelectionOutsideCityAllowed || Global.world.territory.pointInTerritory(m_position.x, m_position.y));
        }//end

         public boolean  canBeDragged ()
        {
            return m_ownable;
        }//end

         public void  onMenuClick (MouseEvent event )
        {
            ContextMenuItem _loc_2 =null ;
            if (event.target instanceof ContextMenuItem)
            {
                _loc_2 =(ContextMenuItem) event.target;
                switch(_loc_2.action)
                {
                    case MOVE_OBJECT:
                    {
                        this.move();
                        break;
                    }
                    case SELL_OBJECT:
                    {
                        this.sell();
                        break;
                    }
                    default:
                    {
                        super.onMenuClick(event);
                        break;
                        break;
                    }
                }
            }
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            if (Global.world.getTopGameMode() instanceof GMEdit || m_actionMechanics.get("GMPlay") != null)
            {
                super.setHighlighted(param1);
            }
            else
            {
                super.setHighlighted(false);
            }
            return;
        }//end

         public Vector3  findValidCrowdPosition ()
        {
            Vector3 _loc_1 =new Vector3(Math.random (),Math.random ());
            if (_loc_1.y > _loc_1.x)
            {
                _loc_1 = new Vector3(_loc_1.y, _loc_1.x);
            }
            _loc_1.x = 1 - _loc_1.x;
            _loc_2 = this.getSize ();
            _loc_1.x = _loc_1.x * _loc_2.x;
            _loc_1.y = _loc_1.y * _loc_2.y;
            return new Vector3(m_position.x + _loc_1.x, m_position.y + _loc_1.y);
        }//end

         public boolean  isPlacedObjectNonBuilding ()
        {
            return true;
        }//end

         public void  calcNextUpdateTime (int param1 )
        {
            if (m_optimizeOnUpdate && !isVisible())
            {
                nextUpdateTime = int.MAX_VALUE;
            }
            else
            {
                super.calcNextUpdateTime(param1);
            }
            return;
        }//end

    }




