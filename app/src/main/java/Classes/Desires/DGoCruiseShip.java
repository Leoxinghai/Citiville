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
import Modules.ships.cruise.*;

    public class DGoCruiseShip extends Desire
    {

        public  DGoCruiseShip (DesirePeep param1 )
        {
            super(param1);
            return;
        }//end  

         public SelectionResult  getSelection ()
        {
            Desire thisDesire ;
            Array graphs ;
            RoadGraph graph ;
            MapResource result ;
            
            m_resists++;
            if (m_resists < m_resistThreshold)
            {
                return SelectionResult.FAIL;
            }
            Array actions ;
            dock = CruiseShipUtil.getDockBuilding();
            if (dock == null)
            {
                return SelectionResult.FAIL;
            }
            MapResource fallbackDestination ;
            thisTile = Global.world.citySim.roadManager.findClosestWalkableTile(m_peep.getPosition());
            path = Global.world.citySim.roadManager.findPath(thisTile,dock,RoadManager.PATH_TO_FRONT_ENTRANCE);
            if (path == null)
            {
                graphs = Global.world.citySim.roadManager.findAllGraphsForWalkableTile(thisTile);
                
                
                for(int i0 = 0; i0 < graphs.size(); i0++) 
                {
                		graph = graphs.get(i0);
                    
                    
                    result = Global.world.citySim.roadManager.findClosestWalkableTileInGraph(dock.getPosition(), graph);
                    if (result != null)
                    {
                        fallbackDestination = result;
                        break;
                    }
                }
            }
            thisDesire;
            actions;
            if (fallbackDestination == null)
            {
                actions.push(new ActionNavigate(m_peep, dock, null).setPathType(RoadManager.PATH_FULL).setTeleportOnFailure(true));
            }
            else
            {
                actions.push(new ActionNavigate(m_peep, fallbackDestination, null).setPathType(RoadManager.PATH_FULL));
                actions.push(new ActionNavigateBeeline(m_peep, CruiseShipUtil.getRoadsideDockEntrance(dock)));
            }
            actions .push (new ActionFn (m_peep ,void  ()
            {
                //thisDesire.setState(STATE_FINISHED);
                this.setState(STATE_FINISHED);
                return;
            }//end  
            ));
            actions.push(new ActionDie(m_peep));
            return new SelectionResult(dock, actions);
        }//end  

    }



